package hun.mesh.carwithenhance.config;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public final class HookConfigs {
    public static final String PREFS_FILE = "carwithenhance_hooksettings";
    public static final String DISCONNECT_KILL_DELAY_KEY = "hook_disconnect_kill_delay_sec";
    public static final int DEFAULT_DISCONNECT_KILL_DELAY_SEC = 5;
    public static final int MAX_DISCONNECT_KILL_DELAY_SEC = 300;
    public static final String DISCONNECT_STOP_AT_KEY = "carwithenhance_disconnect_stop_at";
    public static final String DISCONNECT_TARGETS_KEY = "carwithenhance_disconnect_targets";

    public static final HookItem AUTOPLAY = new HookItem(
            "hook_autoplay_enabled",
            "QQ音乐完美续播",
            "开启后阻止上车续播自动播放歌单《巅峰潮流榜》",
            true);
    public static final HookItem COLDSTART = new HookItem(
            "hook_coldstart_antikill_enabled",
            "冷启动防误杀",
            "阻止 CarWith 在媒体应用冷启动未完成时强制结束进程，并补一次后台唤醒",
            true);
    public static final HookItem EXPERIENCE = new HookItem(
            "hook_experience_to_normal_enabled",
            "体验应用专区转普通列表",
            "开启后把体验应用专区中的应用直接显示到普通应用列表；关闭后恢复专区显示",
            true);
    public static final HookItem CARWITH_KEEPALIVE = new HookItem(
            "hook_carwith_keepalive_enabled",
            "进程保活",
            "解决因进程误杀而造成的断连、音乐中断",
            true);
    public static final HookItem DISCONNECT_KILL_MUSIC = new HookItem(
            "hook_disconnect_kill_music_enabled",
            "断开连接后关闭音乐",
            "开启后，断开时立即结束 CarWith 进程；延迟到设定秒数后关闭当前音乐播放器，"
                    + "若音乐应用在前台或正在播放则跳过；"
                    + "下次蓝牙连接仍由 CarWith 官方接收器自动唤醒",
            true);

    private static final List<HookItem> ALL_ITEMS = Collections.unmodifiableList(Arrays.asList(
            AUTOPLAY,
            COLDSTART,
            EXPERIENCE,
            CARWITH_KEEPALIVE,
            DISCONNECT_KILL_MUSIC));

    private HookConfigs() {
    }

    public static HookItem findByKey(String key) {
        for (HookItem item : ALL_ITEMS) {
            if (item.key.equals(key)) {
                return item;
            }
        }
        return null;
    }

    public static List<HookItem> getAllItems() {
        return ALL_ITEMS;
    }

    public static int clampDelaySeconds(int seconds) {
        return Math.max(0, Math.min(MAX_DISCONNECT_KILL_DELAY_SEC, seconds));
    }

    public static String escapeJson(String value) {
        if (value == null) {
            return "";
        }
        StringBuilder escaped = new StringBuilder(value.length() + 16);
        for (int i = 0; i < value.length(); i++) {
            char c = value.charAt(i);
            switch (c) {
                case '"':
                    escaped.append("\\\"");
                    break;
                case '\\':
                    escaped.append("\\\\");
                    break;
                case '\n':
                    escaped.append("\\n");
                    break;
                case '\r':
                    escaped.append("\\r");
                    break;
                case '\t':
                    escaped.append("\\t");
                    break;
                default:
                    escaped.append(c);
                    break;
            }
        }
        return escaped.toString();
    }
}
