package hun.mesh.carwithenhance.lyrics;

import org.json.JSONArray;
import org.json.JSONObject;
import java.net.URLEncoder;

/** 复刻自 HyperMusicCover 的 WebLyrics 中 LrcLib 部分：精确匹配优先，搜索兜底。 */
public final class LrcLibLyrics {
    private static final String GET =
            "https://lrclib.net/api/get?track_name=%s&artist_name=%s&album_name=%s&duration=%d";
    private static final String SEARCH =
            "https://lrclib.net/api/search?track_name=%s&artist_name=%s";

    private LrcLibLyrics() {
    }

    public static String fetch(String title, String artist, String album, long durationMs) {
        if (title == null || title.trim().isEmpty() || durationMs <= 0) {
            return null;
        }
        String art = TrackNames.firstArtist(artist);
        String url = String.format(GET, enc(title), enc(art), enc(album),
                Math.round(durationMs / 1000.0));
        String lrc = synced(OnlineLyricClient.get(url));
        if (lrc != null) {
            return lrc;
        }
        String sj = OnlineLyricClient.get(String.format(SEARCH, enc(title), enc(art)));
        return searchSynced(sj, title, artist, durationMs);
    }

    private static String synced(String json) {
        if (json == null) {
            return null;
        }
        try {
            JSONObject o = new JSONObject(json);
            if (o.optBoolean("instrumental", false)) {
                return null;
            }
            String body = str(o, "syncedLyrics");
            if (body == null || !body.contains("[")) {
                return null;
            }
            return body;
        } catch (Throwable ignored) {
            return null;
        }
    }

    private static String searchSynced(
            String json, String title, String artist, long durationMs) {
        if (json == null) {
            return null;
        }
        try {
            JSONArray arr = new JSONArray(json);
            String best = null;
            int bestScore = -1;
            for (int i = 0; i < arr.length(); i++) {
                JSONObject o = arr.optJSONObject(i);
                if (o == null) {
                    continue;
                }
                String body = str(o, "syncedLyrics");
                if (body == null || !body.contains("[")) {
                    continue;
                }
                long candDur = Math.round(o.optDouble("duration", 0) * 1000.0);
                int score = LyricMatcher.score(title, artist, durationMs,
                        str(o, "trackName"), str(o, "artistName"), candDur);
                if (score >= LyricMatcher.PASS_SCORE && score > bestScore) {
                    bestScore = score;
                    best = body;
                }
            }
            return best;
        } catch (Throwable ignored) {
            return null;
        }
    }

    static String str(JSONObject o, String key) {
        Object v = o.opt(key);
        if (!(v instanceof String)) {
            return null;
        }
        String s = ((String) v).trim();
        return s.isEmpty() ? null : s;
    }

    private static String enc(String s) {
        if (s == null) {
            return "";
        }
        try {
            return URLEncoder.encode(s, "UTF-8");
        } catch (Throwable ignored) {
            return s;
        }
    }
}
