package hun.mesh.carwithenhance.hook;

import android.content.Context;
import android.content.SharedPreferences;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;
import hun.mesh.carwithenhance.config.HookConfigs;
import hun.mesh.carwithenhance.dexkit.CarWithDexKitManager;
import hun.mesh.carwithenhance.dexkit.GenericAutoPlayHooks;
import hun.mesh.carwithenhance.policy.QqAutoPlayPolicy;
import hun.mesh.carwithenhance.utils.AppUtils;
import hun.mesh.carwithenhance.utils.XLog;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.LinkedHashSet;

/**
 * QQ 音乐完美续播：只拦截 QQ 音乐的媒体浏览器列表回调，避免 CarWith
 * 自动把续播目标切换为“巅峰音乐榜”等歌单。全应用上车续播由
 * CarWith 设置中的 prefer_auto_play 单独控制，本 Hook 不再干预。
 */
public class AutoPlayHook implements IHook {
    private static final String[] FALLBACK_CALLBACK_CLASSES = {"pa.a$b"};

    @Override
    public void onHook(final ClassLoader classLoader) {
        Class<?> callbackClass = resolveCallbackClass(classLoader);
        if (callbackClass == null) {
            XLog.e(">> [QQ完美续播] 未找到兼容的媒体列表回调，跳过 Hook");
            return;
        }

        int hooked = 0;
        for (final Method method : callbackClass.getDeclaredMethods()) {
            if (method.getReturnType() != Void.TYPE
                    || !QqAutoPlayPolicy.isCompatibleCallback(method.getParameterTypes())) {
                continue;
            }
            try {
                XposedBridge.hookMethod(method, new XC_MethodHook() {
                    @Override
                    protected void beforeHookedMethod(MethodHookParam param) {
                        try {
                            Context context = AppUtils.getAppContext(classLoader);
                            if (context == null) {
                                return;
                            }
                            SharedPreferences preferences = context.getSharedPreferences(
                                    HookConfigs.PREFS_FILE, 0);
                            boolean enabled = preferences.getBoolean(
                                    HookConfigs.AUTOPLAY.key,
                                    HookConfigs.AUTOPLAY.defaultValue);
                            if (!enabled) {
                                return;
                            }
                            String targetPackage = extractTargetPackage(param.thisObject);
                            if (!QqAutoPlayPolicy.shouldIntercept(true, targetPackage)) {
                                return;
                            }
                            XLog.i(">> [QQ完美续播] 已拦截 QQ 音乐的巅峰音乐榜队列，保留当前续播状态");
                            param.setResult(null);
                        } catch (Throwable th) {
                            XLog.e(">> [QQ完美续播] 拦截媒体列表回调失败", th);
                        }
                    }
                });
                hooked++;
            } catch (Throwable th) {
                XLog.e(">> [QQ完美续播] Hook 回调 " + method.getName() + " 失败", th);
            }
        }

        if (hooked > 0) {
            XLog.i(">> [QQ完美续播] Hook 注入成功，兼容回调数=" + hooked
                    + "，目标类=" + callbackClass.getName());
        } else {
            XLog.e(">> [QQ完美续播] 目标类存在，但没有兼容的媒体列表回调");
        }
    }

    private static Class<?> resolveCallbackClass(ClassLoader classLoader) {
        try {
            String dynamicClass = CarWithDexKitManager.INSTANCE.getAutoPlayClass();
            Class<?> resolved = findCompatibleClass(classLoader, dynamicClass);
            if (resolved != null) {
                return resolved;
            }
        } catch (Throwable th) {
            XLog.e(">> [QQ完美续播] 读取 DexKit 目标失败，继续尝试兼容回退", th);
        }

        try {
            Object[] target = GenericAutoPlayHooks.resolveQqPlaylistCallback(classLoader);
            if (target != null && target.length >= 1 && target[0] instanceof Class) {
                XLog.i(">> [QQ完美续播] 使用 DexKit 兼容查询找到回调类");
                return (Class<?>) target[0];
            }
        } catch (Throwable th) {
            XLog.i(">> [QQ完美续播] DexKit 兼容查询未命中，尝试旧版类名");
        }

        for (String className : FALLBACK_CALLBACK_CLASSES) {
            Class<?> fallback = findCompatibleClass(classLoader, className);
            if (fallback != null) {
                XLog.i(">> [QQ完美续播] 使用旧版类名兼容: " + className);
                return fallback;
            }
        }
        return null;
    }

    private static Class<?> findCompatibleClass(ClassLoader classLoader, String className) {
        if (className == null || className.isEmpty()) {
            return null;
        }
        try {
            Class<?> cls = XposedHelpers.findClass(className, classLoader);
            for (Method method : cls.getDeclaredMethods()) {
                if (method.getReturnType() == Void.TYPE
                        && QqAutoPlayPolicy.isCompatibleCallback(method.getParameterTypes())) {
                    return cls;
                }
            }
        } catch (Throwable ignored) {
        }
        return null;
    }

    private static String extractTargetPackage(Object callback) {
        LinkedHashSet<String> values = new LinkedHashSet<>();
        collectStringFields(callback, values, 0);
        return QqAutoPlayPolicy.chooseTargetPackage(values);
    }

    private static void collectStringFields(Object object, LinkedHashSet<String> values, int depth) {
        if (object == null || depth > 1) {
            return;
        }
        Class<?> type = object.getClass();
        while (type != null && type != Object.class) {
            for (Field field : type.getDeclaredFields()) {
                if (Modifier.isStatic(field.getModifiers())) {
                    continue;
                }
                try {
                    field.setAccessible(true);
                    Object value = field.get(object);
                    if (value instanceof String) {
                        String text = (String) value;
                        if (!text.isEmpty()) {
                            values.add(text);
                        }
                    } else if (value != null && field.getName().startsWith("this$")) {
                        collectStringFields(value, values, depth + 1);
                    }
                } catch (Throwable ignored) {
                }
            }
            type = type.getSuperclass();
        }
    }
}