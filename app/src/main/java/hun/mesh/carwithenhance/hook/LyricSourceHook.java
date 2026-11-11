package hun.mesh.carwithenhance.hook;

import android.os.Bundle;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedBridge;
import hun.mesh.carwithenhance.media.CarWithMediaCapabilities;
import hun.mesh.carwithenhance.utils.XLog;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

/** 词源优先：优先读取 metadata 中的 lyricInfo，缺失则保留 CarWith 原生歌词读取。 */
public class LyricSourceHook implements IHook {
    private static final String LYRIC_INFO_KEY = "lyricInfo";

    @Override
    public void onHook(ClassLoader classLoader) {
        Method lyricSource = CarWithMediaCapabilities.INSTANCE.getLyricSourceMethod();
        if (lyricSource == null) {
            XLog.e(">> [词源] 未解析到歌词入口，保留官方原生歌词行为");
            return;
        }
        final Class<?> metadataClass = lyricSource.getParameterTypes().length == 1
                ? lyricSource.getParameterTypes()[0]
                : null;
        final Field bundleField = resolveBundleField(metadataClass);
        final Method stringGetter = resolveStringGetter(metadataClass);
        try {
            XposedBridge.hookMethod(lyricSource, new XC_MethodHook() {
                @Override
                protected void beforeHookedMethod(MethodHookParam param) {
                    Object metadata = param.args != null && param.args.length > 0
                            ? param.args[0]
                            : null;
                    if (metadata == null) {
                        return;
                    }
                    String lyricInfo = readString(
                            metadata, bundleField, stringGetter, LYRIC_INFO_KEY);
                    if (lyricInfo != null && !lyricInfo.trim().isEmpty()) {
                        param.setResult(lyricInfo);
                    }
                }
            });
            XLog.i(">> [词源] lyricInfo 优先、原生兜底 Hook 已注册 -> "
                    + lyricSource.getDeclaringClass().getName()
                    + "#" + lyricSource.getName());
        } catch (Throwable th) {
            XLog.e(">> [词源] lyricInfo Hook 注册失败", th);
        }
    }

    private static Field resolveBundleField(Class<?> metadataClass) {
        if (metadataClass == null) {
            return null;
        }
        Class<?> cursor = metadataClass;
        while (cursor != null && cursor != Object.class) {
            Field found = null;
            for (Field field : cursor.getDeclaredFields()) {
                if (Modifier.isStatic(field.getModifiers())
                        || field.isSynthetic()
                        || field.getType() != Bundle.class) {
                    continue;
                }
                if (found != null) {
                    return null;
                }
                found = field;
            }
            if (found != null) {
                try {
                    found.setAccessible(true);
                } catch (Throwable ignored) {
                }
                return found;
            }
            cursor = cursor.getSuperclass();
        }
        return null;
    }

    private static Method resolveStringGetter(Class<?> metadataClass) {
        if (metadataClass == null) {
            return null;
        }
        Method found = null;
        for (Method method : metadataClass.getDeclaredMethods()) {
            if (method.getReturnType() != String.class
                    || method.getParameterTypes().length != 1
                    || method.getParameterTypes()[0] != String.class) {
                continue;
            }
            if (found != null) {
                return null;
            }
            found = method;
        }
        if (found != null) {
            try {
                found.setAccessible(true);
            } catch (Throwable ignored) {
            }
        }
        return found;
    }

    private static String readString(
            Object metadata, Field bundleField, Method stringGetter, String key) {
        if (bundleField != null) {
            try {
                Object value = bundleField.get(metadata);
                if (value instanceof Bundle) {
                    String fromBundle = ((Bundle) value).getString(key);
                    if (fromBundle != null) {
                        return fromBundle;
                    }
                }
            } catch (Throwable ignored) {
            }
        }
        if (stringGetter != null) {
            try {
                Object value = stringGetter.invoke(metadata, key);
                if (value instanceof String) {
                    return (String) value;
                }
            } catch (Throwable ignored) {
            }
        }
        return null;
    }
}
