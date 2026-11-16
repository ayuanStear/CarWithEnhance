package hun.mesh.carwithenhance.policy;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class PanelReadyPolicyTest {
    @Test
    public void mediaIdMustBePresent() {
        assertFalse(PanelReadyPolicy.hasPanelIdentity("", "标题", "歌手"));
        assertFalse(PanelReadyPolicy.hasPanelIdentity(null, "标题", "歌手"));
        assertTrue(PanelReadyPolicy.hasPanelIdentity("media-1", "标题", "歌手"));
    }

    @Test
    public void titleOrArtistMustBePresent() {
        assertTrue(PanelReadyPolicy.hasPanelIdentity("media-1", "标题", ""));
        assertTrue(PanelReadyPolicy.hasPanelIdentity("media-1", "", "歌手"));
        assertFalse(PanelReadyPolicy.hasPanelIdentity("media-1", "", ""));
    }

    @Test
    public void waitTimeoutDoesNotCarryAcrossCalls() {
        assertFalse(PanelReadyPolicy.panelWaitExceeded(0L, 1000L, 6000L));
        assertTrue(PanelReadyPolicy.panelWaitExceeded(1000L, 7000L, 6000L));
        assertFalse(PanelReadyPolicy.panelWaitExceeded(1000L, 1000L, 6000L));
    }

    @Test
    public void playbackWaitsForPanelRenderUntilRenderTimeout() {
        long start = 1000L;
        assertFalse(PanelReadyPolicy.canStartPlayback(
                true, false, start, 3000L, 4000L, 6000L));
        assertFalse(PanelReadyPolicy.canStartPlayback(
                false, false, start, 3000L, 4000L, 6000L));
        assertTrue(PanelReadyPolicy.canStartPlayback(
                true, true, start, 3000L, 4000L, 6000L));
        assertTrue(PanelReadyPolicy.canStartPlayback(
                true, false, start, 7000L, 4000L, 6000L));
    }
}
