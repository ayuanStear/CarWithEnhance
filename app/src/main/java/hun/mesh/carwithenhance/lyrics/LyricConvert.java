package hun.mesh.carwithenhance.lyrics;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/** 把 QRC/word-timed 文本转成 CarWith 能解析的普通 LRC。 */
public final class LyricConvert {
    private static final Pattern LINE_TIME =
            Pattern.compile("^(\\[\\d{1,3}:\\d{2}(?:[.:]\\d{1,3})?\\])");

    private LyricConvert() {
    }

    /** body 是 LRC 就直接返回；带 <...> 词时间标签就折叠成普通 LRC。 */
    public static String toLrc(String body) {
        if (body == null) {
            return null;
        }
        if (!body.contains("<")) {
            return body;
        }
        StringBuilder sb = new StringBuilder(body.length());
        String[] lines = body.split("\n");
        for (String line : lines) {
            Matcher m = LINE_TIME.matcher(line);
            if (m.find()) {
                String time = m.group(1);
                String rest = line.substring(m.end());
                rest = rest.replaceAll("<[^>]*>", "");
                sb.append(time).append(rest.trim()).append('\n');
            } else {
                // 保留 [ti:] 等非行时间标签，但去掉词标签
                sb.append(line.replaceAll("<[^>]*>", "")).append('\n');
            }
        }
        return sb.toString();
    }
}
