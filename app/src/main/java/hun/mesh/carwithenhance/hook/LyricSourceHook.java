package hun.mesh.carwithenhance.hook;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedBridge;
import hun.mesh.carwithenhance.lyrics.OnlineLyricFetcher;
import hun.mesh.carwithenhance.media.CarWithMediaCapabilities;
import hun.mesh.carwithenhance.utils.XLog;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/** 词源优先：lyricInfo 优先，其次在线取词，最后保留 CarWith 原生歌词读取。 */
public class LyricSourceHook implements IHook {
    private static final String LYRIC_INFO_KEY = "lyricInfo";
    private static final String NATIVE_LYRIC_KEY = "ucar.media.metadata.LYRICS_WHOLE";
    private static final String KEY_TITLE = "android.media.metadata.TITLE";
    private static final String KEY_ARTIST = "android.media.metadata.ARTIST";
    private static final String KEY_ALBUM = "android.media.metadata.ALBUM";
    private static final String KEY_DURATION = "android.media.metadata.DURATION";

    private static final ExecutorService EXEC = Executors.newSingleThreadExecutor();
    private static final Set<String> IN_FLIGHT = ConcurrentHashMap.newKeySet();
    private static final Handler MAIN = new Handler(Looper.getMainLooper());

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
                    Bundle bundle = readBundle(metadata, bundleField);
                    if (bundle == null) {
                        return;
                    }
                    String lyricInfo = bundle.getString(LYRIC_INFO_KEY);
                    if (lyricInfo != null && !lyricInfo.trim().isEmpty()) {
                        param.setResult(lyricInfo);
                        return;
                    }
                    String title = bundle.getString(KEY_TITLE);
                    String artist = bundle.getString(KEY_ARTIST);
                    String album = bundle.getString(KEY_ALBUM);
                    long durationMs = bundle.getLong(KEY_DURATION, 0L);
                    if (isEmpty(title) && isEmpty(artist)) {
                        return;
                    }
                    String cacheKey = OnlineLyricFetcher.key(title, artist, album, durationMs);
                    String cached = OnlineLyricFetcher.cached(cacheKey);
                    if (cached != null && !cached.trim().isEmpty()) {
                        param.setResult(cached);
                        return;
                    }
                    scheduleFetch(cacheKey, title, artist, album, durationMs, bundle);
                }
            });
            XLog.i(">> [词源] lyricInfo/在线取词优先 + 原生兜底 Hook 已注册 -> "
                    + lyricSource.getDeclaringClass().getName()
                    + "#" + lyricSource.getName()
                    + " bundle=" + (bundleField != null)
                    + " getter=" + (stringGetter != null));
        } catch (Throwable th) {
            XLog.e(">> [词源] lyricInfo Hook 注册失败", th);
        }
    }

    private static void scheduleFetch(final String cacheKey, final String title,
                                      final String artist, final String album,
                                      final long durationMs, final Bundle bundle) {
        if (!IN_FLIGHT.add(cacheKey)) {
            return;
        }
        EXEC.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    final String lrc = OnlineLyricFetcher.fetch(
                            cacheKey, title, artist, album, durationMs);
                    if (lrc == null || lrc.trim().isEmpty()) {
                        return;
                    }
                    MAIN.post(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                bundle.putString(NATIVE_LYRIC_KEY, lrc);
                            } catch (Throwable ignored) {
                            }
                        }
                    });
                    XLog.i(">> [词源] 在线歌词已获取: " + title + " - " + artist
                            + " (" + lrc.length() + " chars)");
                } finally {
                    IN_FLIGHT.remove(cacheKey);
                }
            }
        });
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

    private static boolean isEmpty(String s) {
        return s == null || s.trim().isEmpty();
    }
}
