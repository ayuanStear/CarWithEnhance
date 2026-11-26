/**
 * Drops routine registration/status/polling noise from CarWith's informational
 * log path. Error and failure paths intentionally bypass this policy.
 */
public final class RuntimeLogPolicy {
    private RuntimeLogPolicy() {
    }

    public static boolean keepAutoPlayLog(String message) {
        String text = normalize(message);
        if (text == null) {
            return false;
        }
        return !(text.contains("已注册")
                || text.contains("已记录")
                || text.contains("媒体根页签")
                || text.contains("媒体路由请求")
                || text.contains("车机界面状态刷新")
                || text.contains("新播放器尚未播放，保留左侧媒体图标")
                || text.contains("音频状态(")
                || text.contains("体验应用已放到普通列表末尾")
                || text.contains("已还原体验应用")
                || text.contains("清理旧标记")
                || text.contains("忽略重复的已连接回调"));
    }

    public static boolean keepXLog(String message) {
        String text = normalize(message);
        if (text == null) {
            return false;
        }
        if (text.contains("mask ucar_casting_state=")) {
            return false;
        }
        if (text.contains("hook installed in ")
                || text.contains("hooks installed in ")) {
            return false;
        }
        if (text.contains("原生页面防护已注册")) {
            return false;
        }
        if (text.startsWith("CarWake DexKit: ")) {
            return !(text.contains("resolved ")
                    || text.contains("candidates=")
                    || text.contains("loaded cached targets")
                    || text.contains("saved target cache"));
        }
        return true;
    }

    private static String normalize(String message) {
        if (message == null) {
            return null;
        }
        String text = message.trim();
        return text.length() == 0 ? null : text;
    }
}
