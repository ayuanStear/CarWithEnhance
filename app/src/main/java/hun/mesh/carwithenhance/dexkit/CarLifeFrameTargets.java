package hun.mesh.carwithenhance.dexkit;

import android.app.AndroidAppHelper;
import android.app.Application;
import de.robv.android.xposed.XposedHelpers;
import hun.mesh.carwithenhance.utils.XLog;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;

public final class CarLifeFrameTargets {
    private static final String RECORDER_CLASS =
            "com.baidu.carlife.core.screen.video.Recorder";
    private static final String RENDER_HANDLER_CLASS =
            "com.baidu.carlife.core.screen.video.FrameRender$RenderHandler";

    public final Class<?> recorderClass;
    public final Method changeFrameRate;
    public final Method handleConnectEstablished;
    public final Field destFrameInterval;
    public final Field encodeSurface;
    public final Class<?> renderHandlerClass;
    public final Method renderDisplayMsg;

    private CarLifeFrameTargets(
            Class<?> recorderClass,
            Method changeFrameRate,
            Method handleConnectEstablished,
            Field destFrameInterval,
            Field encodeSurface,
            Class<?> renderHandlerClass,
            Method renderDisplayMsg) {
        this.recorderClass = recorderClass;
        this.changeFrameRate = changeFrameRate;
        this.handleConnectEstablished = handleConnectEstablished;
        this.destFrameInterval = destFrameInterval;
        this.encodeSurface = encodeSurface;
        this.renderHandlerClass = renderHandlerClass;
        this.renderDisplayMsg = renderDisplayMsg;
    }

    public static CarLifeFrameTargets resolve(ClassLoader classLoader) {
        Class<?> recorderClass = findClass(RECORDER_CLASS, classLoader);
        Method changeFrameRate = findMethod(recorderClass, "changeFrameRate", int.class);
        if (changeFrameRate == null) {
            StructuralTarget structuralTarget = findChangeFrameRateStructurally(classLoader);
            if (structuralTarget != null) {
                changeFrameRate = structuralTarget.method;
                recorderClass = structuralTarget.owner;
            }
        }
        if (recorderClass == null && changeFrameRate != null) {
            recorderClass = changeFrameRate.getDeclaringClass();
        }

        Method handleConnectEstablished =
                findMethod(recorderClass, "handleConnectEstablished");
        Field destFrameInterval = findField(recorderClass, "mDestFrameInterval");
        Field encodeSurface = findField(recorderClass, "mEncSurface");

        Class<?> renderHandlerClass = findClass(RENDER_HANDLER_CLASS, classLoader);
        Method renderDisplayMsg = renderHandlerClass == null
                ? null
                : selectRendererMethod(renderHandlerClass.getDeclaredMethods());
        makeAccessible(renderDisplayMsg);

        CarLifeFrameTargets targets = new CarLifeFrameTargets(
                recorderClass,
                changeFrameRate,
                handleConnectEstablished,
                destFrameInterval,
                encodeSurface,
                renderHandlerClass,
                renderDisplayMsg);
        XLog.i(targets.describe());
        return targets;
    }

    public boolean hasPreciseRenderer() {
        return renderHandlerClass != null && renderDisplayMsg != null;
    }

    public String describe() {
        String recorderName = recorderClass == null ? "MISSING" : recorderClass.getName();
        String rendererName = hasPreciseRenderer()
                ? renderHandlerClass.getName() + "#" + renderDisplayMsg.getName()
                : "MISSING";
        return "[CarLifePacing] recorder=" + recorderName
                + ", changeFrameRate=" + (changeFrameRate != null)
                + ", renderer=" + rendererName
                + ", mode=" + (hasPreciseRenderer() ? "PRECISE" : "DEGRADED_17MS");
    }

    static Method selectRendererMethod(Method[] methods) {
        if (methods == null) {
            return null;
        }
        for (Method method : methods) {
            Class<?>[] parameterTypes = method.getParameterTypes();
            if ("renderDisplayMsg".equals(method.getName())
                    && method.getReturnType() == void.class
                    && parameterTypes.length == 1
                    && parameterTypes[0] == long.class) {
                return method;
            }
        }
        return null;
    }

    private static StructuralTarget findChangeFrameRateStructurally(ClassLoader classLoader) {
        Application application = AndroidAppHelper.currentApplication();
        if (application == null || application.getApplicationInfo() == null) {
            return null;
        }
        String apkPath = application.getApplicationInfo().sourceDir;
        if (apkPath == null) {
            return null;
        }
        try (StructuralMethodLookup lookup = new StructuralMethodLookup(apkPath, classLoader)) {
            List<StructuralMethodLookup.Target> targets = lookup.find(
                    StructuralMethodLookup.QuerySpec.containing(
                            "changeFrameRate frame===:", "void", 1));
            for (StructuralMethodLookup.Target target : targets) {
                Class<?>[] parameterTypes = target.method.getParameterTypes();
                if (parameterTypes.length == 1 && parameterTypes[0] == int.class) {
                    makeAccessible(target.method);
                    return new StructuralTarget(target.owner, target.method);
                }
            }
        } catch (Throwable th) {
            XLog.e("[CarLifePacing] 结构化查找 changeFrameRate 失败", th);
        }
        return null;
    }

    private static Class<?> findClass(String name, ClassLoader classLoader) {
        try {
            return XposedHelpers.findClass(name, classLoader);
        } catch (Throwable ignored) {
            return null;
        }
    }

    private static Method findMethod(Class<?> owner, String name, Class<?>... parameterTypes) {
        if (owner == null) {
            return null;
        }
        try {
            Method method = owner.getDeclaredMethod(name, parameterTypes);
            makeAccessible(method);
            return method;
        } catch (Throwable ignored) {
            return null;
        }
    }

    private static Field findField(Class<?> owner, String name) {
        if (owner == null) {
            return null;
        }
        try {
            Field field = owner.getDeclaredField(name);
            field.setAccessible(true);
            return field;
        } catch (Throwable ignored) {
            return null;
        }
    }

    private static void makeAccessible(java.lang.reflect.AccessibleObject object) {
        if (object == null) {
            return;
        }
        try {
            object.setAccessible(true);
        } catch (Throwable ignored) {
        }
    }

    private static final class StructuralTarget {
        final Class<?> owner;
        final Method method;

        StructuralTarget(Class<?> owner, Method method) {
            this.owner = owner;
            this.method = method;
        }
    }
}
