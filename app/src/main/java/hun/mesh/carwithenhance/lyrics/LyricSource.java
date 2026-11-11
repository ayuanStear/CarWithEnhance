package hun.mesh.carwithenhance.lyrics;

import android.media.MediaMetadata;
import android.media.session.MediaController;

/** 只为移植过来的 OnlineLyrics 提供 SRC_ 常量和 idOf。 */
public final class LyricSource {
    public static final int SRC_NONE = 0;
    public static final int SRC_LYRIC_INFO = 1;
    public static final int SRC_DATABASE = 2;
    public static final int SRC_NETEASE = 3;
    public static final int SRC_KUGOU = 4;
    public static final int SRC_LRCLIB = 5;
    public static final int SRC_LOCAL = 6;
    public static final int SRC_LYRICON = 7;
    public static final int SRC_QQ = 8;
    public static final int SRC_KUWO = 9;
    public static final int SRC_HUB = 10;

    private LyricSource() {
    }

    public static String idOf(MediaController c) {
        try {
            MediaMetadata md = c == null ? null : c.getMetadata();
            return md == null ? null : md.getString(MediaMetadata.METADATA_KEY_MEDIA_ID);
        } catch (Throwable ignored) {
            return null;
        }
    }
}
