package hun.mesh.carwithenhance.hook;

import android.os.Bundle;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedBridge;
import hun.mesh.carwithenhance.media.CarWithMediaCapabilities;
import hun.mesh.carwithenhance.utils.XLog;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

/**
 * 歌词占位：原生 lyricInfo 优先；无歌词时把歌词结果写死为“暂无歌词”。
 * 不做任何在线取词。
 */
public class LyricSourceHook implements IHook {
    private static final String LYRIC_INFO_KEY = "lyricInfo";
    private static final String PLACEHOLDER_LRC = "[00:00.00]暂无歌词";

    @Override
    public void onHook(ClassLoader classLoader) {
        Method lyricSource = CarWithMediaCapabilities.INSTANCE.getLyricSourceMethod();
        if (lyricSource == null) {
            XLog.e(">> [歌词] 未解析到歌词入口，保留官方原生歌词行为");
            return;
        }
        Class<?> metadataClass = lyricSource.getParameterTypes().length == 1
                ? lyricSource.getParameterTypes()[0]
                : null;
        final Field bundleField = resolveBundleField(metadataClass);
        try {
            XposedBridge.hookMethod(lyricSource, new XC_MethodHook() {
                @Override
                protected void beforeHookedMethod(MethodHookParam param) {
                    try {
                        Object metadata = param.args != null && param.args.length > 0
                                ? param.args[0]
                                : null;
                        if (metadata == null) {
                            return;
                        }
                        Bundle bundle = readBundle(metadata, bundleField);
                        if (bundle == null) {
                            return;
                        }
                        String lyricInfo = bundle.getString(LYRIC_INFO_KEY);
                        if (lyricInfo != null && !lyricInfo.trim().isEmpty()) {
                            return;
                        }
                        param.setResult(PLACEHOLDER_LRC);
                        XLog.i(">> [歌词] 无原生歌词，写入占位“暂无歌词”");
                    } catch (Throwable th) {
                        XLog.e(">> [歌词] 歌词占位处理失败", th);
                    }
                }
            });
        } catch (Throwable th) {
            XLog.e(">> [歌词] lyricInfo Hook 注册失败", th);
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

    private static Bundle readBundle(Object metadata, Field bundleField) {
        if (bundleField == null) {
            return null;
        }
        try {
            Object value = bundleField.get(metadata);
            return value instanceof Bundle ? (Bundle) value : null;
        } catch (Throwable ignored) {
            return null;
        }
    }
}
