package hun.mesh.carwithenhance.hook;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedBridge;
import hun.mesh.carwithenhance.lyrics.LyricConvert;
import hun.mesh.carwithenhance.lyrics.NcmLyrics;
import hun.mesh.carwithenhance.lyrics.OnlineLyricResolver;
import hun.mesh.carwithenhance.media.CarWithMediaCapabilities;
import hun.mesh.carwithenhance.utils.XLog;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/** 词源优先：lyricInfo 优先，其次复用 HyperMusicCover 的多曲库在线取词，最后原生兜底。 */
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
    private static final Map<String, String> LRC_CACHE =
            new LinkedHashMap<String, String>(32, 0.75f, true) {
                @Override
                protected boolean removeEldestEntry(Map.Entry<String, String> eldest) {
                    return size() > 32;
                }
            };

    private static volatile String sCurrentSongKey;
    private static volatile Object sLyricHost;
    private static volatile Object sLyricMetadata;

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
                    sLyricHost = param.thisObject;
                    sLyricMetadata = metadata;
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
                    String cacheKey = title + '|' + artist + '|' + album + '|' + durationMs;
                    sCurrentSongKey = cacheKey;
                    synchronized (LRC_CACHE) {
                        String hit = LRC_CACHE.get(cacheKey);
                        if (hit != null && !hit.trim().isEmpty()) {
                            param.setResult(hit);
                            return;
                        }
                    }
                    scheduleFetch(cacheKey, title, artist, album, durationMs, bundle);
                }
            });
            XLog.i(">> [词源] lyricInfo/多曲库在线取词优先 + 即时刷新 + 原生兜底 Hook 已注册 -> "
                    + lyricSource.getDeclaringClass().getName()
                    + "#" + lyricSource.getName()
                    + " bundle=" + (bundleField != null)
                    + " handler=" + (CarWithMediaCapabilities.INSTANCE.getMetadataHandlerMethod() != null));
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
                    final String lrc = doFetch(title, artist, album, durationMs);
                    if (lrc == null || lrc.trim().isEmpty()) {
                        XLog.i(">> [词源] 多曲库在线取词未命中: " + title + " - " + artist);
                        return;
                    }
                    synchronized (LRC_CACHE) {
                        LRC_CACHE.put(cacheKey, lrc);
                    }
                    MAIN.post(new Runnable() {
                        @Override
                        public void run() {
                            applyLyric(cacheKey, bundle, lrc);
                        }
                    });
                } finally {
                    IN_FLIGHT.remove(cacheKey);
                }
            }
        });
    }

    private static String doFetch(String title, String artist, String album, long durationMs) {
        try {
            NcmLyrics.Query q = NcmLyrics.build(title, artist, album, durationMs);
            if (q == null) {
                return null;
            }
            OnlineLyricResolver.Result r = OnlineLyricResolver.resolve(null, q);
            if (r == null) {
                return null;
            }
            String lrc = LyricConvert.toLrc(r.body, r.words);
            if (lrc != null && !lrc.trim().isEmpty()) {
                XLog.i(">> [词源] 在线歌词命中: " + title + " - " + artist
                        + " id=" + r.id + " " + lrc.length() + " chars");
            }
            return lrc;
        } catch (Throwable th) {
            XLog.e(">> [词源] 多曲库取词失败", th);
            return null;
        }
    }

    private static void applyLyric(String cacheKey, Bundle bundle, String lrc) {
        if (!cacheKey.equals(sCurrentSongKey)) {
            XLog.i(">> [词源] 已切歌，丢弃过期在线歌词: " + cacheKey);
            return;
        }
        try {
            bundle.putString(NATIVE_LYRIC_KEY, lrc);
        } catch (Throwable ignored) {
        }
        Object host = sLyricHost;
        Object metadata = sLyricMetadata;
        Method handler = CarWithMediaCapabilities.INSTANCE.getMetadataHandlerMethod();
        if (host != null && metadata != null && handler != null) {
            try {
                handler.invoke(host);
                XLog.i(">> [词源] 在线歌词已即时注入并刷新: " + lrc.length() + " chars");
            } catch (Throwable th) {
                XLog.e(">> [词源] 即时刷新调用失败", th);
            }
        } else {
            XLog.i(">> [词源] 在线歌词已缓存（等待下次元数据变化显示）");
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
