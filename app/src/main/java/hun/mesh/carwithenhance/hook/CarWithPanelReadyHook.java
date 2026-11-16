package hun.mesh.carwithenhance.hook;

import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;
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
        Class<?> base = XposedHelpers.findClassIfExists(BASE_MUSIC_VIEW, classLoader);
        if (base == null) {
            return false;
        }
        boolean hooked = false;
        try {
            for (Method method : base.getDeclaredMethods()) {
                if ("v".equals(method.getName())
                        && method.getReturnType() == Boolean.TYPE
                        && method.getParameterTypes().length == 1
                        && method.getParameterTypes()[0] == String.class) {
                    XposedBridge.hookMethod(method, new XC_MethodHook() {
                        @Override
                        protected void afterHookedMethod(MethodHookParam param) {
                            if (Boolean.TRUE.equals(param.getResult())
                                    && param.args[0] instanceof String) {
                                VIEW_PACKAGE.put(param.thisObject, (String) param.args[0]);
                            }
                        }
                    });
                    hooked = true;
                }
            }
        } catch (Throwable th) {
            XLog.e("[面板就绪] Hook 媒体卡片绑定失败", th);
        }

        Set<Class<?>> callbacks = new LinkedHashSet<>();
        try {
            Class<?> direct = XposedHelpers.findClassIfExists(
                    BASE_MUSIC_VIEW + "$c", classLoader);
            if (direct != null) {
                callbacks.add(direct);
            }
            for (Class<?> inner : base.getDeclaredClasses()) {
                if (hasMetadataCallback(inner)) {
                    callbacks.add(inner);
                }
            }
        } catch (Throwable ignored) {
        }
        for (Class<?> callback : callbacks) {
            for (Method method : callback.getDeclaredMethods()) {
                if (!"i".equals(method.getName())
                        || method.getReturnType() != Void.TYPE
                        || method.getParameterTypes().length != 1
                        || !isMetadataType(method.getParameterTypes()[0])) {
                    continue;
                }
                try {
                    final Class<?> baseClass = base;
                    XposedBridge.hookMethod(method, new XC_MethodHook() {
                        @Override
                        protected void afterHookedMethod(MethodHookParam param) {
                            handleCardRender(param, baseClass, param.args[0]);
                        }
                    });
                    hooked = true;
                } catch (Throwable th) {
                    XLog.e("[面板就绪] Hook 媒体卡片回调失败", th);
                }
            }
        }
        return hooked;
    }

    private static void handleCardRender(
            XC_MethodHook.MethodHookParam param, Class<?> baseClass, Object metadata) {
        if (metadata == null || !hasTitleOrArtist(metadata)) {
            return;
        }
        Object view = findOuter(param.thisObject, baseClass);
        String pkg = view == null ? null : VIEW_PACKAGE.get(view);
        if (pkg == null) {
            pkg = AutoPlayAllHook.getCurrentMediaPackage();
        }
        if (pkg != null) {
            AutoPlayAllHook.markPanelRendered(pkg);
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
                    if (!"i".equals(method.getName())
                            || method.getReturnType() != Void.TYPE
                            || method.getParameterTypes().length != 1
                            || !isMetadataType(method.getParameterTypes()[0])) {
                        continue;
                    }
                    try {
                        final Class<?> ownerClass = owner;
                        XposedBridge.hookMethod(method, new XC_MethodHook() {
                            @Override
                            protected void afterHookedMethod(MethodHookParam param) {
                                handlePageRender(param, ownerClass, param.args[0]);
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
        String pkg = AutoPlayAllHook.getCurrentMediaPackage();
        if (pkg == null) {
            Object outer = findOuter(param.thisObject, pagerClass);
            pkg = findMediaPackageField(outer);
        }
        if (pkg != null) {
            AutoPlayAllHook.markPanelRendered(pkg);
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
