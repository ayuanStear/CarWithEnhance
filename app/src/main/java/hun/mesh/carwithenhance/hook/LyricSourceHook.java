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

/** 歌词专用：原生 lyricInfo 优先；无原生词时复用 HyperMusicCover 多曲库在线取词；取不到则显示固定占位。 */
public class LyricSourceHook implements IHook {
    private static final String LYRIC_INFO_KEY = "lyricInfo";
    private static final String KEY_TITLE = "android.media.metadata.TITLE";
    private static final String KEY_ARTIST = "android.media.metadata.ARTIST";
    private static final String KEY_ALBUM = "android.media.metadata.ALBUM";
    private static final String KEY_DURATION = "android.media.metadata.DURATION";
    private static final String PLACEHOLDER_LRC = "[00:00.00]此歌曲没有填词,请欣赏";

    private static final ExecutorService EXEC = Executors.newCachedThreadPool();
    private static final Set<String> IN_FLIGHT = ConcurrentHashMap.newKeySet();
    private static final Set<String> MISS_KEYS = ConcurrentHashMap.newKeySet();
    private static final Handler MAIN = new Handler(Looper.getMainLooper());
    private static final Map<String, String> LRC_CACHE =
            new LinkedHashMap<String, String>(32, 0.75f, true) {
                @Override
                protected boolean removeEldestEntry(Map.Entry<String, String> eldest) {
                    return size() > 32;
                }
            };

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
                        // 原生有歌词：完全不干预，直接放行走官方原生逻辑。
                        return;
                    }
                    String title = bundle.getString(KEY_TITLE);
                    String artist = bundle.getString(KEY_ARTIST);
                    String album = bundle.getString(KEY_ALBUM);
                    long durationMs = bundle.getLong(KEY_DURATION, 0L);
                    if (isEmpty(title) && isEmpty(artist)) {
                        return;
                    }
                    String cacheKey = title + '|' + artist + '|' + album;
                    synchronized (LRC_CACHE) {
                        String hit = LRC_CACHE.get(cacheKey);
                        if (hit != null && !hit.trim().isEmpty()) {
                            param.setResult(hit);
                            return;
                        }
                    }
                    if (MISS_KEYS.contains(cacheKey)) {
                        param.setResult(PLACEHOLDER_LRC);
                        return;
                    }
                    // 无原生词：后台在线取词，命中/未命中后再刷新替换。
                    scheduleFetch(param.thisObject, cacheKey, title, artist, album, durationMs);
                }
            });
        } catch (Throwable th) {
            XLog.e(">> [歌词] lyricInfo Hook 注册失败", th);
        }
    }

    private static void scheduleFetch(final Object host, final String cacheKey,
                                      final String title, final String artist,
                                      final String album, final long durationMs) {
        if (!IN_FLIGHT.add(cacheKey)) {
            return;
        }
        EXEC.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    String lrc = doFetch(title, artist, album, durationMs);
                    if (lrc == null || lrc.trim().isEmpty()) {
                        XLog.i(">> [歌词] 在线取词未命中: " + title + " - " + artist);
                        MISS_KEYS.add(cacheKey);
                        if (MISS_KEYS.size() > 64) {
                            MISS_KEYS.clear();
                        }
                        MAIN.post(new Runnable() {
                            @Override
                            public void run() {
                                triggerRefresh(host);
                            }
                        });
                        return;
                    }
                    XLog.i(">> [歌词] 在线取词命中: " + title + " - " + artist + " len=" + lrc.length());
                    synchronized (LRC_CACHE) {
                        LRC_CACHE.put(cacheKey, lrc);
                    }
                    MAIN.post(new Runnable() {
                        @Override
                        public void run() {
                            triggerRefresh(host);
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
            return LyricConvert.toLrc(r.body, r.words);
        } catch (Throwable th) {
            XLog.e(">> [歌词] 多曲库取词失败", th);
            return null;
        }
    }

    private static void triggerRefresh(Object host) {
        Method handler = CarWithMediaCapabilities.INSTANCE.getMetadataHandlerMethod();
        if (host != null && handler != null) {
            try {
                handler.invoke(host);
            } catch (Throwable th) {
                XLog.e(">> [歌词] 即时刷新调用失败", th);
            }
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

    private static boolean isEmpty(String s) {
        return s == null || s.trim().isEmpty();
    }
}