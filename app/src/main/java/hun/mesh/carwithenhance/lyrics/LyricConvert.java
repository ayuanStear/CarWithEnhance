package hun.mesh.carwithenhance.lyrics;

import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/** 把 yrc/word-timed 文本转成 CarWith 能解析的普通 LRC。 */
public final class LyricConvert {
    private static final Pattern YRC_LINE =
            Pattern.compile("^\\[(\\d+),(\\d+)\\](.*)$");
    private static final Pattern YRC_WORD =
            Pattern.compile("\\(\\d+,\\d+,\\d+\\)([^()]*)");

    private LyricConvert() {
    }

    /** words 为 true 时是 yrc（词级时间轴），转成 LRC；否则原样返回。 */
    public static String toLrc(String body, boolean words) {
        if (body == null) {
            return null;
        }
        if (!words) {
            return body;
        }
        String converted = yrcToLrc(body);
        return converted.isEmpty() ? body : converted;
    }

    private static String yrcToLrc(String body) {
        StringBuilder sb = new StringBuilder(body.length());
        for (String line : body.split("\n")) {
            Matcher lm = YRC_LINE.matcher(line.trim());
            if (!lm.find()) {
                continue;
            }
            long start = Long.parseLong(lm.group(1));
            StringBuilder text = new StringBuilder();
            Matcher wm = YRC_WORD.matcher(lm.group(2));
            while (wm.find()) {
                text.append(wm.group(1));
            }
            if (text.length() == 0) {
                continue;
            }
            sb.append(formatTime(start)).append(text).append('\n');
        }
        return sb.toString();
    }

    private static String formatTime(long ms) {
        return String.format(Locale.ROOT, "[%02d:%02d.%02d]", ms / 60000L,
                ms / 1000L % 60L, ms % 1000L / 10L);
    }
}
