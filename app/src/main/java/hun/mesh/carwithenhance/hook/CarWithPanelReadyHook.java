package hun.mesh.carwithenhance.hook;

import android.view.View;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;
import hun.mesh.carwithenhance.policy.PanelReadyPolicy;
import hun.mesh.carwithenhance.utils.XLog;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import java.util.WeakHashMap;

/**
 * Detects when CarWith has really rendered media metadata on the home music
 * card or the full media page. Resume/playback must not start before this
 * callback, otherwise audio starts while the panel is still blank.
 */
public final class CarWithPanelReadyHook implements IHook {
    private static final String BASE_MUSIC_VIEW =
            "com.carwith.launcher.card.v2.widget.BaseMusicView";
    private static final String CARD_MUSIC_VIEW =
            "com.carwith.launcher.card.CardMusicView";
    private static final String[] EXTRA_MUSIC_VIEWS = {
            "com.carwith.launcher.card.v2.widget.MusicViewA",
            "com.carwith.launcher.card.v2.widget.MusicView1A",
            "com.carwith.launcher.card.v2.widget.MusicView2A",
            "com.carwith.launcher.card.v2.widget.MusicView3A",
            "com.carwith.launcher.card.ModerateCardMusicView",
            "com.carwith.launcher.card.DockerMusicView"
    };
    private static final String MEDIA_VIEW_PAGER =
            "com.carwith.launcher.media.MediaViewPager";
    private static final String MEDIA_PLAY_FRAGMENT =
            "com.carwith.launcher.media.MediaPlayFragment";

    private static final Map<Object, String> VIEW_PACKAGE =
            Collections.synchronizedMap(new WeakHashMap<Object, String>());
    private static volatile boolean installed;

    @Override
    public void onHook(ClassLoader classLoader) {
        if (installed) {
            return;
        }
        boolean card = hookMusicCard(classLoader);
        boolean page = hookMediaPage(classLoader);
        installed = card || page;
        if (card) {
            log("媒体卡片渲染钩子已注册");
        }
        if (page) {
            log("媒体页面渲染钩子已注册");
        }
        if (!installed) {
            XLog.e("[面板就绪] 未找到 CarWith 媒体卡片/页面，保留默认续播时机");
        }
    }

    private static boolean hookMusicCard(ClassLoader classLoader) {
        boolean hooked = false;
        hooked |= hookCardOwner(classLoader, BASE_MUSIC_VIEW, "v", true);
        hooked |= hookCardOwner(classLoader, CARD_MUSIC_VIEW, "X", false);
        for (String className : EXTRA_MUSIC_VIEWS) {
            hooked |= hookMetadataMethods(classLoader, className);
        }
        return hooked;
    }

    private static boolean hookCardOwner(
            ClassLoader classLoader, String className, String bindMethodName,
            boolean bindReturnsBoolean) {
        Class<?> owner = XposedHelpers.findClassIfExists(className, classLoader);
        if (owner == null) {
            XLog.e("[面板就绪] 未找到媒体卡片类: " + className);
            return false;
        }
        boolean hooked = false;
        try {
            for (Method method : owner.getDeclaredMethods()) {
                if (!bindMethodName.equals(method.getName())
                        || method.getParameterTypes().length != 1
                        || method.getParameterTypes()[0] != String.class) {
                    continue;
                }
                if (bindReturnsBoolean && method.getReturnType() != Boolean.TYPE) {
                    continue;
                }
                if (!bindReturnsBoolean && method.getReturnType() != Void.TYPE) {
                    continue;
                }
                XposedBridge.hookMethod(method, new XC_MethodHook() {
                    @Override
                    protected void afterHookedMethod(MethodHookParam param) {
                        if ((!bindReturnsBoolean || Boolean.TRUE.equals(param.getResult()))
                                && param.args[0] instanceof String) {
                            VIEW_PACKAGE.put(param.thisObject, (String) param.args[0]);
                        }
                    }
                });
                hooked = true;
            }
        } catch (Throwable th) {
            XLog.e("[面板就绪] Hook 媒体卡片绑定失败: " + className, th);
        }

        Set<Class<?>> callbacks = new LinkedHashSet<>();
        try {
            for (String suffix : new String[]{"$c", "$b", "$a", "$d"}) {
                Class<?> direct = XposedHelpers.findClassIfExists(
                        className + suffix, classLoader);
                if (direct != null) {
                    callbacks.add(direct);
                }
            }
            for (Class<?> inner : owner.getDeclaredClasses()) {
                if (hasMetadataCallback(inner)) {
                    callbacks.add(inner);
                }
            }
        } catch (Throwable ignored) {
        }
        for (Class<?> callback : callbacks) {
            for (Method method : callback.getDeclaredMethods()) {
                if (method.getReturnType() != Void.TYPE) {
                    continue;
                }
                Class<?>[] params = method.getParameterTypes();
                int metadataIndex = -1;
                int packageIndex = -1;
                if ("i".equals(method.getName())
                        && params.length == 1
                        && isMetadataType(params[0])) {
                    metadataIndex = 0;
                } else if ("j".equals(method.getName())
                        && params.length == 2
                        && params[0] == String.class
                        && isMetadataType(params[1])) {
                    metadataIndex = 1;
                    packageIndex = 0;
                }
                if (metadataIndex < 0) {
                    continue;
                }
                final int metaIndex = metadataIndex;
                final int pkgIndex = packageIndex;
                try {
                    final Class<?> ownerClass = owner;
                    XposedBridge.hookMethod(method, new XC_MethodHook() {
                        @Override
                        protected void afterHookedMethod(MethodHookParam param) {
                            if (param.args.length <= metaIndex) {
                                return;
                            }
                            String pkgHint = pkgIndex >= 0
                                    && param.args.length > pkgIndex
                                    && param.args[pkgIndex] instanceof String
                                    ? (String) param.args[pkgIndex] : null;
                            handleCardRender(param, ownerClass,
                                    param.args[metaIndex], pkgHint);
                        }
                    });
                    hooked = true;
                } catch (Throwable th) {
                    XLog.e("[面板就绪] Hook 媒体卡片回调失败: " + className, th);
                }
            }
        }
        if (hooked) {
            log("媒体卡片变体已注册: " + className);
        }
        return hooked;
    }

    private static boolean hookMetadataMethods(ClassLoader classLoader, String className) {
        Class<?> owner = XposedHelpers.findClassIfExists(className, classLoader);
        if (owner == null) {
            return false;
        }
        boolean hooked = false;
        for (Method method : owner.getDeclaredMethods()) {
            if (method.getReturnType() != Void.TYPE) {
                continue;
            }
            Class<?>[] params = method.getParameterTypes();
            final boolean hasMetadata = params.length >= 1
                    && params.length <= 2
                    && isMetadataType(params[0]);
            boolean progressUpdate = "setCurrentProgress".equals(method.getName())
                    && params.length == 1
                    && params[0] == Long.TYPE;
            if (!hasMetadata && !progressUpdate) {
                continue;
            }
            try {
                XposedBridge.hookMethod(method, new XC_MethodHook() {
                    @Override
                    protected void afterHookedMethod(MethodHookParam param) {
                        if (hasMetadata
                                && (param.args.length == 0
                                || !hasTitleOrArtist(param.args[0]))) {
                            return;
                        }
                        markMusicViewRendered(param.thisObject);
                    }
                });
                hooked = true;
            } catch (Throwable th) {
                XLog.e("[面板就绪] Hook 媒体卡片签名回调失败: " + className, th);
            }
        }
        return hooked;
    }

    private static void markMusicViewRendered(Object target) {
        if (!(target instanceof View)) {
            return;
        }
        View view = (View) target;
        // The dock/home music card can be laid out and bound while the map is
        // the foreground activity; don't require isShown(), otherwise cold
        // start on a map-first layout never marks the panel rendered.
        boolean ready = view.isAttachedToWindow()
                && view.getWidth() > 0
                && view.getHeight() > 0;
        if (!ready) {
            return;
        }
        String pkg = AutoPlayAllHook.getPanelRenderPackage();
        if (pkg == null) {
            return;
        }
        VIEW_PACKAGE.put(target, pkg);
        AutoPlayAllHook.markPanelRendered(pkg);
    }

    private static void handleCardRender(
            XC_MethodHook.MethodHookParam param, Class<?> baseClass,
            Object metadata, String pkgHint) {
        if (metadata == null || !hasTitleOrArtist(metadata)) {
            return;
        }
        String pkg = pkgHint;
        if (pkg == null) {
            Object view = findOuter(param.thisObject, baseClass);
            pkg = view == null ? null : VIEW_PACKAGE.get(view);
        }
        if (pkg == null) {
            pkg = AutoPlayAllHook.getPanelRenderPackage();
        }
        if (pkg != null) {
            AutoPlayAllHook.markPanelRendered(pkg);
        } else {
            AutoPlayAllHook.log(AutoPlayAllHook.getAppContext(),
                    "卡片渲染回调无法归属播放器: pkgHint=" + pkgHint);
        }
    }

    private static boolean hookMediaPage(ClassLoader classLoader) {
        boolean hooked = false;
        for (String className : new String[]{MEDIA_VIEW_PAGER, MEDIA_PLAY_FRAGMENT}) {
            Class<?> owner = XposedHelpers.findClassIfExists(className, classLoader);
            if (owner == null) {
                XLog.e("[面板就绪] 未找到媒体页面类: " + className);
                continue;
            }
            Set<Class<?>> classes = new LinkedHashSet<>();
            classes.add(owner);
            try {
                Collections.addAll(classes, owner.getDeclaredClasses());
            } catch (Throwable ignored) {
            }
            // getDeclaredClasses() is not reliable for every host class loader;
            // add the known callback classes explicitly as well.
            for (String suffix : new String[]{"$c", "$d", "$a", "$b"}) {
                Class<?> inner = XposedHelpers.findClassIfExists(className + suffix, classLoader);
                if (inner != null) {
                    classes.add(inner);
                }
            }
            for (Class<?> type : classes) {
                for (Method method : type.getDeclaredMethods()) {
                    if (method.getReturnType() != Void.TYPE) {
                        continue;
                    }
                    Class<?>[] params = method.getParameterTypes();
                    int metadataIndex = -1;
                    if ("i".equals(method.getName())
                            && params.length == 1
                            && isMetadataType(params[0])) {
                        metadataIndex = 0;
                    } else if ("j".equals(method.getName())
                            && params.length == 2
                            && params[0] == String.class
                            && isMetadataType(params[1])) {
                        metadataIndex = 1;
                    }
                    if (metadataIndex < 0) {
                        continue;
                    }
                    final int index = metadataIndex;
                    try {
                        final Class<?> ownerClass = owner;
                        XposedBridge.hookMethod(method, new XC_MethodHook() {
                            @Override
                            protected void afterHookedMethod(MethodHookParam param) {
                                if (param.args.length > index) {
                                    handlePageRender(param, ownerClass, param.args[index]);
                                }
                            }
                        });
                        hooked = true;
                    } catch (Throwable th) {
                        XLog.e("[面板就绪] Hook 媒体页面回调失败", th);
                    }
                }
            }
        }
        if (!hooked) {
            XLog.e("[面板就绪] 媒体页面没有元数据回调: " + MEDIA_PLAY_FRAGMENT);
        }
        return hooked;
    }

    private static void handlePageRender(
            XC_MethodHook.MethodHookParam param, Class<?> pagerClass, Object metadata) {
        if (metadata == null || !hasTitleOrArtist(metadata)) {
            return;
        }
        String pkg = AutoPlayAllHook.getPanelRenderPackage();
        if (pkg == null) {
            Object outer = findOuter(param.thisObject, pagerClass);
            pkg = findMediaPackageField(outer);
        }
        if (pkg != null) {
            AutoPlayAllHook.markPanelRendered(pkg);
        } else {
            AutoPlayAllHook.log(AutoPlayAllHook.getAppContext(),
                    "媒体页渲染回调无法归属播放器");
        }
    }

    private static boolean hasMetadataCallback(Class<?> type) {
        try {
            for (Method method : type.getDeclaredMethods()) {
                if ("i".equals(method.getName())
                        && method.getReturnType() == Void.TYPE
                        && method.getParameterTypes().length == 1
                        && isMetadataType(method.getParameterTypes()[0])) {
                    return true;
                }
            }
        } catch (Throwable ignored) {
        }
        return false;
    }

    private static boolean isMetadataType(Class<?> type) {
        String name = type == null ? null : type.getName();
        return "android.support.v4.media.MediaMetadataCompat".equals(name)
                || "android.media.MediaMetadata".equals(name);
    }

    private static boolean hasTitleOrArtist(Object metadata) {
        return !isBlank(stringValue(metadata, "android.media.metadata.TITLE"))
                || !isBlank(stringValue(metadata, "android.media.metadata.ARTIST"));
    }

    private static String stringValue(Object metadata, String key) {
        try {
            Object value = XposedHelpers.callMethod(metadata, "getString", key);
            return value instanceof String ? (String) value : null;
        } catch (Throwable ignored) {
            return null;
        }
    }

    private static Object findOuter(Object callback, Class<?> expected) {
        if (callback == null) {
            return null;
        }
        Class<?> type = callback.getClass();
        while (type != null && type != Object.class) {
            for (Field field : type.getDeclaredFields()) {
                try {
                    field.setAccessible(true);
                    Object value = field.get(callback);
                    if (value != null
                            && (field.getName().startsWith("this$")
                            || expected.isInstance(value))) {
                        return value;
                    }
                } catch (Throwable ignored) {
                }
            }
            type = type.getSuperclass();
        }
        return null;
    }

    private static String findMediaPackageField(Object owner) {
        if (owner == null) {
            return null;
        }
        Class<?> type = owner.getClass();
        while (type != null && type != Object.class) {
            for (Field field : type.getDeclaredFields()) {
                if (field.getType() != String.class) {
                    continue;
                }
                try {
                    field.setAccessible(true);
                    Object value = field.get(owner);
                    if (value instanceof String
                            && AutoPlayAllHook.isMediaAppName((String) value)) {
                        return (String) value;
                    }
                } catch (Throwable ignored) {
                }
            }
            type = type.getSuperclass();
        }
        return null;
    }

    private static boolean isBlank(String value) {
        return value == null || value.trim().isEmpty();
    }

    private static void log(String message) {
        AutoPlayAllHook.log(AutoPlayAllHook.getAppContext(), message);
    }
}
