package hun.mesh.carwithenhance.media;

import android.content.Context;
import android.media.session.MediaController;
import hun.mesh.carwithenhance.dexkit.StructuralMethodLookup;
import hun.mesh.carwithenhance.utils.XLog;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.List;

/** Resolves the host media facilities used by optional enhancements. */
public final class CarWithMediaCapabilities {
    public static final CarWithMediaCapabilities INSTANCE = new CarWithMediaCapabilities();

    private volatile boolean resolved;
    private Method converterSingleton;
    private Method converterMethod;
    private Method mediaManagerSingleton;
    private Method mediaManagerPush;
    private Field mediaManagerCurrentPackage;
    private Method callbackSingleton;
    private Method callbackNotify;
    private Method callbackRebind;
    private StructuralMethodLookup.Target routeEntry;
    private Method routeCapability;
    private Method lyricSourceMethod;

    private CarWithMediaCapabilities() {
    }

    public synchronized void resolve(Context context, ClassLoader hostLoader) {
        if (resolved || context == null || hostLoader == null) {
            return;
        }
        resolved = true;
        String apkPath = context.getApplicationInfo().sourceDir;
        StructuralMethodLookup lookup = null;
        try {
            lookup = new StructuralMethodLookup(apkPath, hostLoader);
            resolvePanel(lookup);
            resolveRoute(lookup);
            resolveLyricSource(lookup);
        } catch (Throwable th) {
            XLog.e("[媒体能力] 结构解析失败，保留宿主官方行为", th);
        } finally {
            if (lookup != null) {
                try {
                    lookup.close();
                } catch (Throwable ignored) {
                }
            }
        }
    }

    private void resolvePanel(StructuralMethodLookup lookup) {
        List<StructuralMethodLookup.Target> converters = lookup.find(
                StructuralMethodLookup.QuerySpec.containing(
                        "toMediaControllerCompat failed !  ",
                        "android.support.v4.media.session.MediaControllerCompat",
                        2));
        StructuralMethodLookup.Target converter = unique(converters, "控制器转换");
        if (converter != null) {
            converterMethod = converter.method;
            converterSingleton = singletonFactory(converter.owner);
        }

        List<StructuralMethodLookup.Target> managers = lookup.find(
                StructuralMethodLookup.QuerySpec.containing(
                        "exclusivePauseOtherApp pause : ", "void", 1));
        StructuralMethodLookup.Target managerNoise = unique(managers, "媒体管理器");
        if (managerNoise != null) {
            mediaManagerSingleton = singletonFactory(managerNoise.owner);
            try {
                Class<?> compatibilityController = Class.forName(
                        "android.support.v4.media.session.MediaControllerCompat",
                        false,
                        managerNoise.owner.getClassLoader());
                mediaManagerPush = uniqueMethod(
                        managerNoise.owner,
                        Void.TYPE,
                        compatibilityController);
            } catch (Throwable ignored) {
                mediaManagerPush = null;
            }
            mediaManagerCurrentPackage = uniqueStringField(managerNoise.owner);
        }

        List<StructuralMethodLookup.Target> callbackTargets = lookup.find(
                StructuralMethodLookup.QuerySpec.containing(
                        "getMediaSdkCallbacks: params is empty", "java.util.List", 1));
        StructuralMethodLookup.Target callbackNoise = unique(callbackTargets, "媒体回调分发");
        if (callbackNoise != null) {
            callbackSingleton = singletonFactory(callbackNoise.owner);
            callbackNotify = namedMethod(callbackNoise.owner, "d", Void.TYPE, String.class);
            if (callbackNotify == null) {
                callbackNotify = firstSignatureMethod(callbackNoise.owner, Void.TYPE, String.class);
            }
            callbackRebind = namedMethod(callbackNoise.owner, "h", Void.TYPE, String.class);
            if (callbackRebind == null) {
                callbackRebind = firstSignatureMethod(callbackNoise.owner, Void.TYPE, String.class);
            }
        }
    }

    private void resolveRoute(StructuralMethodLookup lookup) {
        routeEntry = unique(lookup.find(
                StructuralMethodLookup.QuerySpec.containing(
                        "startMediaApp isNormalCastMode=", "void", 5)), "媒体启动入口");

        List<StructuralMethodLookup.Target> serviceTargets = lookup.find(
                StructuralMethodLookup.QuerySpec.containing(
                        "appContext or pkgName is empty", "void", 1));
        StructuralMethodLookup.Target service = unique(serviceTargets, "媒体路由判定");
        if (service != null) {
            routeCapability = uniqueMethod(service.owner, Boolean.TYPE, String.class);
        }
    }

    private void resolveLyricSource(StructuralMethodLookup lookup) {
        StructuralMethodLookup.Target target = pickByNamed(lookup);
        if (target == null) {
            target = pickBySignature(lookup);
        }
        if (target != null) {
            lyricSourceMethod = target.method;
        } else {
            lyricSourceMethod = null;
            XLog.e("[媒体能力] 词源入口 MISSING，保留原生歌词行为");
        }
    }

    private StructuralMethodLookup.Target pickByNamed(StructuralMethodLookup lookup) {
        List<StructuralMethodLookup.Target> candidates = lookup.find(
                StructuralMethodLookup.QuerySpec.named(
                        "A", "java.lang.String", "android.support.v4.media.MediaMetadataCompat"));
        StructuralMethodLookup.Target target = preferNaC(candidates);
        if (target == null && candidates.size() == 1) {
            target = candidates.get(0);
        }
        return target;
    }

    private StructuralMethodLookup.Target pickBySignature(StructuralMethodLookup lookup) {
        List<StructuralMethodLookup.Target> candidates = lookup.find(
                StructuralMethodLookup.QuerySpec.signature(
                        "java.lang.String", "android.support.v4.media.MediaMetadataCompat"));
        StructuralMethodLookup.Target target = preferNaC(candidates);
        if (target == null && candidates.size() == 1) {
            target = candidates.get(0);
        }
        return target;
    }

    private static StructuralMethodLookup.Target preferNaC(
            List<StructuralMethodLookup.Target> candidates) {
        for (StructuralMethodLookup.Target candidate : candidates) {
            if ("na.c".equals(candidate.owner.getName())) {
                return candidate;
            }
        }
        return null;
    }
    public boolean tryRefreshPanel(
            Context context, String packageName, MediaController controller) {
        if (!isPanelReady()) {
            return false;
        }
        try {
            Object converter = converterSingleton.invoke(null);
            Object compatibilityController = converterMethod.invoke(
                    converter, context, controller);
            if (compatibilityController == null) {
                return false;
            }
            Object manager = mediaManagerSingleton.invoke(null);
            mediaManagerPush.invoke(manager, compatibilityController);
            Object current = mediaManagerCurrentPackage.get(manager);
            if (!packageName.equals(current)) {
                mediaManagerCurrentPackage.set(manager, packageName);
            }
            Object callback = callbackSingleton.invoke(null);
            if (callbackNotify != null) {
                callbackNotify.invoke(callback, packageName);
            }
            if (callbackRebind != null) {
                callbackRebind.invoke(callback, packageName);
            }
            return callbackNotify != null || callbackRebind != null;
        } catch (Throwable th) {
            XLog.e("[媒体能力] 动态面板刷新失败: " + packageName, th);
            return false;
        }
    }

    public boolean isPanelReady() {
        return converterSingleton != null
                && converterMethod != null
                && mediaManagerSingleton != null
                && mediaManagerPush != null
                && mediaManagerCurrentPackage != null
                && callbackSingleton != null
                && (callbackNotify != null || callbackRebind != null);
    }

    public String getCurrentMediaPackage() {
        if (!isPanelReady()) {
            return null;
        }
        return getCurrentMediaPackageRaw();
    }

    public String getCurrentMediaPackageRaw() {
        if (mediaManagerSingleton == null || mediaManagerCurrentPackage == null) {
            return null;
        }
        try {
            Object manager = mediaManagerSingleton.invoke(null);
            Object value = mediaManagerCurrentPackage.get(manager);
            return value instanceof String ? (String) value : null;
        } catch (Throwable th) {
            return null;
        }
    }

    public boolean isRouteReady() {
        return routeEntry != null && routeCapability != null;
    }

    public StructuralMethodLookup.Target getRouteEntry() {
        return routeEntry;
    }

    public Method getRouteCapability() {
        return routeCapability;
    }

    public Method getLyricSourceMethod() {
        return lyricSourceMethod;
    }

    private static StructuralMethodLookup.Target unique(
            List<StructuralMethodLookup.Target> targets, String label) {
        if (targets.size() == 1) {
            return targets.get(0);
        }
        XLog.e("[媒体能力] " + label + " " + (targets.isEmpty() ? "MISSING" : "AMBIGUOUS")
                + "，候选数=" + targets.size());
        return null;
    }

    private static Method singletonFactory(Class<?> owner) {
        Method found = null;
        for (Method method : owner.getDeclaredMethods()) {
            if (!Modifier.isStatic(method.getModifiers())
                    || method.getParameterTypes().length != 0
                    || !owner.isAssignableFrom(method.getReturnType())) {
                continue;
            }
            if (found != null) {
                return null;
            }
            found = method;
            makeAccessible(found);
        }
        return found;
    }

    private static Method uniqueMethod(
            Class<?> owner, Class<?> returnType, Class<?>... parameterTypes) {
        Method found = null;
        for (Method method : owner.getDeclaredMethods()) {
            if (Modifier.isStatic(method.getModifiers())
                    || method.getReturnType() != returnType
                    || !sameParameters(method.getParameterTypes(), parameterTypes)) {
                continue;
            }
            if (found != null) {
                return null;
            }
            found = method;
            makeAccessible(found);
        }
        return found;
    }

    private static Method namedMethod(
            Class<?> owner,
            String name,
            Class<?> returnType,
            Class<?>... parameterTypes) {
        try {
            Method method = owner.getDeclaredMethod(name, parameterTypes);
            if (method.getReturnType() != returnType) {
                return null;
            }
            makeAccessible(method);
            return method;
        } catch (Throwable ignored) {
            return null;
        }
    }

    private static Method firstSignatureMethod(
            Class<?> owner,
            Class<?> returnType,
            Class<?>... parameterTypes) {
        for (Method method : owner.getDeclaredMethods()) {
            if (Modifier.isStatic(method.getModifiers())
                    || method.getReturnType() != returnType
                    || !sameParameters(method.getParameterTypes(), parameterTypes)) {
                continue;
            }
            makeAccessible(method);
            return method;
        }
        return null;
    }

    private static Field uniqueStringField(Class<?> owner) {
        Field found = null;
        for (Field field : owner.getDeclaredFields()) {
            if (Modifier.isStatic(field.getModifiers())
                    || field.getType() != String.class
                    || field.isSynthetic()) {
                continue;
            }
            if (found != null) {
                return null;
            }
            found = field;
            try {
                field.setAccessible(true);
            } catch (Throwable ignored) {
            }
        }
        return found;
    }

    private static boolean sameParameters(Class<?>[] actual, Class<?>[] expected) {
        if (actual.length != expected.length) {
            return false;
        }
        for (int i = 0; i < actual.length; i++) {
            if (actual[i] != expected[i]) {
                return false;
            }
        }
        return true;
    }

    private static void makeAccessible(Method method) {
        try {
            method.setAccessible(true);
        } catch (Throwable ignored) {
        }
    }
}


