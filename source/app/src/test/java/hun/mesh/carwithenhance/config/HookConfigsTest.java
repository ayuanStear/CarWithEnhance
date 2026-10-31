package hun.mesh.carwithenhance.config;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class HookConfigsTest {
    @Test
    public void keepAliveSwitchIsExposedInSettings() {
        boolean found = false;
        for (HookItem item : HookConfigs.getAllItems()) {
            if (HookConfigs.CARWITH_KEEPALIVE.key.equals(item.key)) {
                found = true;
                break;
            }
        }
        assertTrue(found);
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
