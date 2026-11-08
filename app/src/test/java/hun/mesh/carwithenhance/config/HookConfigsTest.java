package hun.mesh.carwithenhance.config;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class HookConfigsTest {
    @Test
    public void removedSwitchesAreNotExposedInSettings() {
        assertEquals(4, HookConfigs.getAllItems().size());
        for (HookItem item : HookConfigs.getAllItems()) {
            assertFalse("hook_carwith_keepalive_enabled".equals(item.key));
            assertFalse("hook_coldstart_antikill_enabled".equals(item.key));
        }
    }

    @Test
    public void switchPlayerKillToggleIsExposedAndDefaultsEnabled() {
        HookItem item = HookConfigs.SWITCH_PLAYER_KILL_PREVIOUS;
        assertEquals("hook_switch_player_kill_previous_enabled", item.key);
        assertEquals("切换播放器播放后结束上个进程", item.title);
        assertTrue(item.subTitle.contains("真正开始播放"));
        assertTrue(item.defaultValue);
    }


    @Test
    public void autoplaySwitchDescribesOnlyQqPlaylistProtection() {
        assertEquals("hook_autoplay_enabled", HookConfigs.AUTOPLAY.key);
        assertEquals("QQ音乐完美续播", HookConfigs.AUTOPLAY.title);
        assertTrue(HookConfigs.AUTOPLAY.subTitle.contains("巅峰音乐榜"));
        assertFalse(HookConfigs.AUTOPLAY.title.contains("全应用"));
    }

    @Test
    public void disconnectDelayIsClampedToSafeRange() {
        assertEquals(0, HookConfigs.clampDelaySeconds(-1));
        assertEquals(5, HookConfigs.clampDelaySeconds(5));
        assertEquals(HookConfigs.MAX_DISCONNECT_KILL_DELAY_SEC,
                HookConfigs.clampDelaySeconds(Integer.MAX_VALUE));
    }

    @Test
    public void menuTextIsJsonEscaped() {
        String escaped = HookConfigs.escapeJson("a\"b\\c\n");
        assertEquals("a\\\"b\\\\c\\n", escaped);
        assertFalse(escaped.contains("\n"));
    }
}
