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
            "阻止 QQ 音乐上车续播自动跳转到“巅峰音乐榜”，只保留当前播放状态",
            true);
    public static final HookItem EXPERIENCE = new HookItem(
            "hook_experience_to_normal_enabled",
            "体验应用专区转普通列表",
            "开启后把体验应用放到普通应用列表末尾，地图等官方应用不会被标记为体验应用",
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
            EXPERIENCE,
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
