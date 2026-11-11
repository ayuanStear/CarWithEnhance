package hun.mesh.carwithenhance.lyrics;

import hun.mesh.carwithenhance.utils.XLog;
import org.json.JSONArray;
import org.json.JSONObject;
import java.net.URLEncoder;

/** 复刻自 HyperMusicCover 的 NcmLyrics：按歌名+歌手搜索，打分挑歌，再取 LRC。 */
public final class NetEaseLyrics {
    private static final String SEARCH =
            "https://music.163.com/api/search/get?s=%s&type=1&limit=10";
    private static final String LYRIC =
            "https://music.163.com/api/song/lyric/v1?id=%s&cp=false&lv=0&kv=0&tv=0&rv=0&yv=0&ytv=0&yrv=0";

    private NetEaseLyrics() {
    }

    /** 返回标准 LRC 文本，找不到返回 null。 */
    public static String fetch(String title, String artist, long durationMs) {
        String terms = join(TrackNames.undecorated(title), TrackNames.firstArtist(artist));
        if (terms.isEmpty()) {
            return null;
        }
        XLog.i(">> [网易] 搜索: " + terms + " dur=" + durationMs);
        String json = OnlineLyricClient.get(String.format(SEARCH, enc(terms)));
        if (json == null) {
            XLog.i(">> [网易] 搜索请求失败或网络不可用: " + terms);
            return null;
        }
        String id = pick(json, title, artist, durationMs);
        if (id == null) {
            XLog.i(">> [网易] 未匹配到歌曲: " + terms);
            return null;
        }
        XLog.i(">> [网易] 匹配到 id=" + id);
        String lyricJson = OnlineLyricClient.get(String.format(LYRIC, id));
        if (lyricJson == null) {
            XLog.i(">> [网易] 歌词请求失败 id=" + id);
            return null;
        }
        try {
            JSONObject o = new JSONObject(lyricJson);
            String lrc = body(o, "lrc");
            if (lrc == null) {
                lrc = body(o, "yrc");
            }
            return lrc;
        } catch (Throwable ignored) {
            return null;
        }
    }

    private static String pick(String json, String title, String artist, long durationMs) {
        try {
            JSONArray songs = new JSONObject(json).getJSONObject("result").getJSONArray("songs");
            String bestId = null;
            int bestScore = -1;
            for (int i = 0; i < songs.length(); i++) {
                JSONObject s = songs.optJSONObject(i);
                if (s == null) {
                    continue;
                }
                String name = str(s, "name");
                if (name == null) {
                    continue;
                }
                String candArtist = artists(s);
                long candDur = s.optLong("duration", 0L);
                int score = LyricMatcher.score(
                        title, artist, durationMs, name, candArtist, candDur);
                if (score >= LyricMatcher.PASS_SCORE && score > bestScore) {
                    bestScore = score;
                    bestId = String.valueOf(s.optLong("id", 0L));
                }
            }
            return bestId;
        } catch (Throwable ignored) {
            return null;
        }
    }

    private static String artists(JSONObject s) {
        JSONArray ar = s.optJSONArray("artists");
        if (ar == null) {
            ar = s.optJSONArray("ar");
        }
        StringBuilder sb = new StringBuilder();
        if (ar != null) {
            for (int i = 0; i < ar.length(); i++) {
                JSONObject a = ar.optJSONObject(i);
                if (a == null) {
                    continue;
                }
                String n = str(a, "name");
                if (n != null) {
                    if (sb.length() > 0) {
                        sb.append('/');
                    }
                    sb.append(n);
                }
            }
        }
        return sb.toString();
    }

    private static String body(JSONObject o, String key) {
        JSONObject nested = o.optJSONObject(key);
        return nested == null ? null : str(nested, "lyric");
    }

    static String str(JSONObject o, String key) {
        Object v = o.opt(key);
        if (!(v instanceof String)) {
            return null;
        }
        String s = ((String) v).trim();
        return s.isEmpty() ? null : s;
    }

    private static String join(String a, String b) {
        if (a == null || a.isEmpty()) {
            return b == null ? "" : b;
        }
        return b == null || b.isEmpty() ? a : a + ' ' + b;
    }

    private static String enc(String s) {
        try {
            return URLEncoder.encode(s, "UTF-8");
        } catch (Throwable ignored) {
            return s;
        }
    }
}
