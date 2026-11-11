package hun.mesh.carwithenhance.lyrics;

import java.util.LinkedHashMap;
import java.util.Map;

/** 在线取词编排：网易云优先，LrcLib 兜底；带小容量 LRU 缓存。 */
public final class OnlineLyricFetcher {
    private static final int CACHE_MAX = 16;
    private static final Map<String, String> CACHE =
            new LinkedHashMap<String, String>(CACHE_MAX + 1, 0.75f, true) {
                @Override
                protected boolean removeEldestEntry(Map.Entry<String, String> eldest) {
                    return size() > CACHE_MAX;
                }
            };
    private static final String NONE = "";

    private OnlineLyricFetcher() {
    }

    public static String key(String title, String artist, String album, long durationMs) {
        return title + '|' + artist + '|' + album + '|' + durationMs;
    }

    public static String cached(String cacheKey) {
        synchronized (CACHE) {
            String hit = CACHE.get(cacheKey);
            return hit == null ? null : (hit == NONE ? null : hit);
        }
    }

    /** 同步取词（调用方须在后台线程调用）。 */
    public static String fetch(String cacheKey, String title, String artist, String album,
                               long durationMs) {
        synchronized (CACHE) {
            String hit = CACHE.get(cacheKey);
            if (hit != null) {
                return hit == NONE ? null : hit;
            }
        }
        String lrc = null;
        try {
            lrc = NetEaseLyrics.fetch(title, artist, durationMs);
            if (lrc == null || lrc.trim().isEmpty()) {
                lrc = LrcLibLyrics.fetch(title, artist, album, durationMs);
            }
        } catch (Throwable ignored) {
        }
        synchronized (CACHE) {
            CACHE.put(cacheKey, lrc == null || lrc.trim().isEmpty() ? NONE : lrc);
        }
        return lrc == null || lrc.trim().isEmpty() ? null : lrc;
    }
}
