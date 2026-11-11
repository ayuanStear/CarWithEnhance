package hun.mesh.carwithenhance.lyrics;

import java.util.ArrayList;
import java.util.List;

/** 复刻自 HyperMusicCover 的 TrackName：搜索前把歌名/歌手清理干净。 */
public final class TrackNames {
    private TrackNames() {
    }

    /** 去掉标题末尾的 feat./with 等修饰。 */
    static String undecorated(String title) {
        if (title == null) {
            return "";
        }
        String low = title.toLowerCase();
        int cut = -1;
        String[] marks = {"(feat", "(ft.", "(with ", "[feat", "[with ", "（feat"};
        for (String mark : marks) {
            int at = low.indexOf(mark);
            if (at > 0 && (cut < 0 || at < cut)) {
                cut = at;
            }
        }
        if (cut <= 0) {
            return title;
        }
        String head = title.substring(0, cut).trim();
        return head.isEmpty() ? title : head;
    }

    /** 取第一个署名歌手，用于搜索。 */
    static String firstArtist(String artist) {
        if (artist == null) {
            return "";
        }
        int slash = artist.indexOf('/');
        return (slash > 0 ? artist.substring(0, slash) : artist).trim();
    }

    /** 歌手可能用 / & 、 逗号 等分隔。 */
    static List<String> splitArtists(String artist) {
        List<String> out = new ArrayList<>();
        if (artist == null || artist.trim().isEmpty()) {
            return out;
        }
        for (String part : artist.split("[&,，、/／;；]")) {
            String s = part.trim();
            if (!s.isEmpty()) {
                out.add(s);
            }
        }
        return out;
    }

    /** 去括号、去空白与标点、小写，用于相等/包含比较。 */
    static String clean(String s) {
        if (s == null) {
            return "";
        }
        String v = java.text.Normalizer.normalize(s.trim(), java.text.Normalizer.Form.NFKC);
        v = v.toLowerCase(java.util.Locale.ROOT);
        v = v.replaceAll("\\(.*?\\)|\\[.*?]|\\{.*?}", "");
        v = v.replaceAll("[\\s\\p{Punct}，。、·—…′’‘“”]+", "");
        return v;
    }
}
