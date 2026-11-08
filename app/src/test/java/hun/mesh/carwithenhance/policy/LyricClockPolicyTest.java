package hun.mesh.carwithenhance.policy;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class LyricClockPolicyTest {
    @Test
    public void advancesForForwardPlaybackProgress() {
        assertTrue(LyricClockPolicy.shouldAdvance(1_000L, 2_000L, true));
    }

    @Test
    public void advancesAfterBackwardSeek() {
        assertTrue(LyricClockPolicy.shouldAdvance(120_000L, 5_000L, true));
    }

    @Test
    public void duplicatePositionDoesNothing() {
        assertFalse(LyricClockPolicy.shouldAdvance(5_000L, 5_000L, true));
    }

    @Test
    public void unknownPositionDoesNothing() {
        assertFalse(LyricClockPolicy.shouldAdvance(5_000L, -1L, true));
    }

    @Test
    public void disconnectedMediaDoesNotAdvance() {
        assertFalse(LyricClockPolicy.shouldAdvance(1_000L, 2_000L, false));
    }
}
