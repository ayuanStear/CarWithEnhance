package hun.mesh.carwithenhance.policy;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class FramePacerPolicyTest {
    @Test
    public void sixtyFpsDoesNotDriftOverSixtySeconds() {
        FramePacerPolicy policy = new FramePacerPolicy();
        long nowNanos = 0L;
        long totalDelayMillis = 0L;
        int saw16 = 0;
        int saw17 = 0;

        for (int i = 0; i < 3_600; i++) {
            long frameStartNanos = nowNanos;
            int delay = policy.nextDelayMillis(60, frameStartNanos, nowNanos);
            totalDelayMillis += delay;
            nowNanos += delay * 1_000_000L;
            if (delay == 16) {
                saw16++;
            } else if (delay == 17) {
                saw17++;
            }
        }

        assertTrue(Math.abs(totalDelayMillis - 60_000L) <= 2L);
        assertTrue(saw16 > 0);
        assertTrue(saw17 > 0);
    }

    @Test
    public void repeatedSameFpsCallDoesNotResetPhase() {
        FramePacerPolicy policy = new FramePacerPolicy();
        assertEquals(17, policy.nextDelayMillis(60, 0L, 0L));
        assertEquals(17, policy.nextDelayMillis(60, 17_000_000L, 17_000_000L));
        assertEquals(16, policy.nextDelayMillis(60, 34_000_000L, 34_000_000L));
    }

    @Test
    public void lateFrameSkipsInsteadOfBursting() {
        FramePacerPolicy policy = new FramePacerPolicy();
        policy.nextDelayMillis(60, 0L, 0L);
        int delay = policy.nextDelayMillis(60, 100_000_000L, 102_000_000L);
        assertEquals(15, delay);
    }

    @Test
    public void fpsChangeResetsDeadline() {
        FramePacerPolicy policy = new FramePacerPolicy();
        policy.nextDelayMillis(60, 0L, 0L);
        assertEquals(34, policy.nextDelayMillis(30, 20_000_000L, 20_000_000L));
    }

    @Test
    public void officialNonSixtyFrameRatesStayValid() {
        assertEquals(66, FramePacerPolicy.fallbackIntervalMillis(15));
        assertEquals(50, FramePacerPolicy.fallbackIntervalMillis(20));
        assertEquals(33, FramePacerPolicy.fallbackIntervalMillis(30));
        assertEquals(17, FramePacerPolicy.fallbackIntervalMillis(60));
    }

    @Test
    public void gatesProtectUnsupportedSchedulingAndSurface() {
        assertTrue(FramePacerPolicy.shouldOverrideRenderer(true, 60));
        assertFalse(FramePacerPolicy.shouldOverrideRenderer(false, 60));
        assertFalse(FramePacerPolicy.shouldOverrideRenderer(true, 30));
        assertTrue(FramePacerPolicy.shouldApplySurfaceFrameRate(true, 60, 30, true));
        assertFalse(FramePacerPolicy.shouldApplySurfaceFrameRate(true, 60, 29, true));
        assertFalse(FramePacerPolicy.shouldApplySurfaceFrameRate(true, 60, 30, false));
        assertFalse(FramePacerPolicy.shouldApplySurfaceFrameRate(true, 30, 30, true));
    }

    @Test
    public void resetStartsFreshPhase() {
        FramePacerPolicy policy = new FramePacerPolicy();
        policy.nextDelayMillis(60, 0L, 0L);
        policy.reset();
        assertEquals(17, policy.nextDelayMillis(60, 50_000_000L, 50_000_000L));
    }

    @Test(expected = IllegalArgumentException.class)
    public void rejectsUnsupportedFrameRate() {
        new FramePacerPolicy().nextDelayMillis(10, 0L, 0L);
    }
}
