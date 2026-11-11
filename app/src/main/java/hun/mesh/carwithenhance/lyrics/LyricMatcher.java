package hun.mesh.carwithenhance.lyrics;

import java.util.List;

/** 复刻自 HyperMusicCover 的 LyricMatch 打分核心：title +50 / artist +30 / 时长修正，85 分过关。 */
public final class LyricMatcher {
    public static final int PASS_SCORE = 80;

    private LyricMatcher() {
    }

    public static int score(
            String title, String artist, long durationMs,
            String candTitle, String candArtist, long candDuration) {
        int s = 0;
        if (titleMatch(title, candTitle)) {
            s += 50;
        }
        if (artistMatch(artist, candArtist)) {
            s += 30;
        }
        if (durationMs > 0 && candDuration > 0) {
            long gap = Math.abs(durationMs - candDuration);
            if (gap <= 1500) {
                s += 15;
            } else if (gap <= 5000) {
                s += 10;
            }
        }
        return s;
    }

    static boolean titleMatch(String a, String b) {
        if (a == null || b == null) {
            return false;
        }
        String ca = TrackNames.clean(a);
        String cb = TrackNames.clean(b);
        if (ca.isEmpty() || cb.isEmpty()) {
            return false;
        }
        return ca.equals(cb) || ca.contains(cb) || cb.contains(ca);
    }

    static boolean artistMatch(String sessionArtist, String candArtist) {
        if (sessionArtist == null || candArtist == null) {
            return false;
        }
        List<String> as = TrackNames.splitArtists(sessionArtist);
        List<String> bs = TrackNames.splitArtists(candArtist);
        for (String a : as) {
            String ca = TrackNames.clean(a);
            if (ca.isEmpty()) {
                continue;
            }
            for (String b : bs) {
                String cb = TrackNames.clean(b);
                if (cb.isEmpty()) {
                    continue;
                }
                if (ca.equals(cb) || ca.contains(cb) || cb.contains(ca)) {
                    return true;
                }
            }
        }
        return false;
    }
}
