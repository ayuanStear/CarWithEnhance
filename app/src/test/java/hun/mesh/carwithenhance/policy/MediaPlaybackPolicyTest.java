package hun.mesh.carwithenhance.policy;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.HashSet;
import org.junit.Test;

public class MediaPlaybackPolicyTest {
    @Test
    public void choosesLastLaunchedPlayerBeforeOfficialFallbacks() {
        assertEquals("com.netease.cloudmusic", MediaPlaybackPolicy.chooseLastLaunched(
                "com.netease.cloudmusic",
                "com.tencent.qqmusic",
                "com.luna.music",
                "com.kugou.android",
                value -> true));
    }

    @Test
    public void fallsBackPastEmptyAndInvalidPlayers() {
        assertEquals("com.luna.music", MediaPlaybackPolicy.chooseLastLaunched(
                "",
                null,
                "com.luna.music",
                "com.kugou.android",
                "com.luna.music"::equals));
    }


    @Test
    public void recordsOnlyExplicitLaunchEventsAsLastLaunched() {
        assertTrue(MediaPlaybackPolicy.shouldRecordLastLaunchedEvent(1));
        assertFalse(MediaPlaybackPolicy.shouldRecordLastLaunchedEvent(2));
        assertFalse(MediaPlaybackPolicy.shouldRecordLastLaunchedEvent(3));
    }

    @Test
    public void blocksAutoPlayOnlyWhenAnotherPlayerIsPlaying() {
        assertTrue(MediaPlaybackPolicy.shouldSuppressPlayback(false, true));
        assertFalse(MediaPlaybackPolicy.shouldSuppressPlayback(true, true));
        assertFalse(MediaPlaybackPolicy.shouldSuppressPlayback(false, false));
    }

    @Test
    public void preservesDockIconUntilTheOpenedPlayerActuallyStarts() {
        assertTrue(MediaPlaybackPolicy.shouldPreserveDockIcon(false, true));
        assertFalse(MediaPlaybackPolicy.shouldPreserveDockIcon(true, true));
        assertFalse(MediaPlaybackPolicy.shouldPreserveDockIcon(false, false));
    }

    @Test
    public void stopsLastKnownPlayerAfterTheNewPlayerStarts() {
        assertEquals("com.netease.cloudmusic", MediaPlaybackPolicy.previousPackageToStop(
                "com.netease.cloudmusic", "com.tencent.qqmusic", new HashSet<String>()));
    }

    @Test
    public void neverStopsTheNewSameOrProtectedPlayer() {
        assertNull(MediaPlaybackPolicy.previousPackageToStop(
                "com.tencent.qqmusic", "com.tencent.qqmusic", new HashSet<String>()));
        assertNull(MediaPlaybackPolicy.previousPackageToStop(
                null, "com.tencent.qqmusic", new HashSet<String>()));
        assertNull(MediaPlaybackPolicy.previousPackageToStop(
                "com.autonavi.minimap", "com.tencent.qqmusic",
                new HashSet<>(Arrays.asList("com.autonavi.minimap"))));
    }

    @Test
    public void killsPreviousPlayerOnlyWhenSwitchToggleIsEnabledAndNewPlayerIsPlaying() {
        assertTrue(MediaPlaybackPolicy.shouldKillPreviousAfterSwitch(
                true, true, "com.netease.cloudmusic", "com.tencent.qqmusic"));
    }

    @Test
    public void disabledSwitchToggleNeverKillsPreviousPlayer() {
        assertFalse(MediaPlaybackPolicy.shouldKillPreviousAfterSwitch(
                false, true, "com.netease.cloudmusic", "com.tencent.qqmusic"));
    }

    @Test
    public void previousPlayerIsKeptUntilNewPlayerActuallyPlays() {
        assertFalse(MediaPlaybackPolicy.shouldKillPreviousAfterSwitch(
                true, false, "com.netease.cloudmusic", "com.tencent.qqmusic"));
    }
}
