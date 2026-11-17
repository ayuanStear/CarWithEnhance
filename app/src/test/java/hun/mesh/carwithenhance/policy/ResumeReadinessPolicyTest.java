package hun.mesh.carwithenhance.policy;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class ResumeReadinessPolicyTest {
    @Test
    public void autoPlayDisabledNeverResumes() {
        assertFalse(ResumeReadinessPolicy.canSendOfficialResume(false, true, true, false, false));
    }

    @Test
    public void missingProcessNeverResumes() {
        assertFalse(ResumeReadinessPolicy.canSendOfficialResume(true, false, true, false, false));
    }

    @Test
    public void missingSessionNeverResumes() {
        assertFalse(ResumeReadinessPolicy.canSendOfficialResume(true, true, false, false, false));
    }

    @Test
    public void processAndSessionAllowResume() {
        assertTrue(ResumeReadinessPolicy.canSendOfficialResume(true, true, true, false, false));
    }

    @Test
    public void alreadyPlayingDoesNotNeedAnotherResume() {
        assertFalse(ResumeReadinessPolicy.canSendOfficialResume(true, true, true, true, false));
    }

    @Test
    public void otherMediaPlayingBlocksResume() {
        assertFalse(ResumeReadinessPolicy.canSendOfficialResume(true, true, true, false, true));
    }

    @Test
    public void confirmedPlayingPackageDoesNotReceiveSecondResumeInSameConnection() {
        assertFalse(ResumeReadinessPolicy.canSendResumeForExistingSession(
                true, false, true, false));
    }

    @Test
    public void alreadyPlayingPackageDoesNotReceiveAnotherResume() {
        assertFalse(ResumeReadinessPolicy.canSendResumeForExistingSession(
                true, true, false, false));
    }

    @Test
    public void readyExistingSessionCanResumeOnceBeforePlaybackIsConfirmed() {
        assertTrue(ResumeReadinessPolicy.canSendResumeForExistingSession(
                true, false, false, false));
    }

    @Test
    public void otherPlayerStillBlocksExistingSessionResume() {
        assertFalse(ResumeReadinessPolicy.canSendResumeForExistingSession(
                true, false, false, true));
    }

    @Test
    public void wakeIsAllowedForMissingProcessBeforeFinalAttempt() {
        assertTrue(ResumeReadinessPolicy.shouldWake(false, false, false));
        assertTrue(ResumeReadinessPolicy.shouldWake(false, true, false));
        assertFalse(ResumeReadinessPolicy.shouldWake(false, false, true));
        assertFalse(ResumeReadinessPolicy.shouldWake(true, true, false));
    }

    @Test
    public void oneKeyCleanSuppressionIsBoundedAndOneShotCompatible() {
        long now = 1_000_000L;
        assertTrue(ResumeReadinessPolicy.shouldSuppressAfterOneKeyClean(
                now - 10_000L, now, 300_000L));
        assertTrue(ResumeReadinessPolicy.shouldSuppressAfterOneKeyClean(
                now - 300_000L, now, 300_000L));
        assertFalse(ResumeReadinessPolicy.shouldSuppressAfterOneKeyClean(
                now - 300_001L, now, 300_000L));
        assertFalse(ResumeReadinessPolicy.shouldSuppressAfterOneKeyClean(0L, now, 300_000L));
        assertFalse(ResumeReadinessPolicy.shouldSuppressAfterOneKeyClean(
                now + 1L, now, 300_000L));
    }

    @Test
    public void duplicateResumeIsBlockedOnlyForSamePackageInsideWindow() {
        long now = 10_000L;
        assertTrue(ResumeReadinessPolicy.isDuplicateResume(
                "com.tencent.qqmusic", now - 599L,
                "com.tencent.qqmusic", now, 600L));
        assertFalse(ResumeReadinessPolicy.isDuplicateResume(
                "com.tencent.qqmusic", now - 600L,
                "com.tencent.qqmusic", now, 600L));
        assertFalse(ResumeReadinessPolicy.isDuplicateResume(
                "com.tencent.qqmusic", now - 100L,
                "com.netease.cloudmusic", now, 600L));
        assertFalse(ResumeReadinessPolicy.isDuplicateResume(
                null, now - 100L,
                "com.netease.cloudmusic", now, 600L));
    }

    @Test
    public void officialResumeGetsShortHeadStartBeforeFallbackBinding() {
        long now = 30_000L;
        assertTrue(ResumeReadinessPolicy.shouldWaitForOfficialResumeBeforeFallback(
                true, 0L, now - 1_499L, now, 1_500L));
        assertFalse(ResumeReadinessPolicy.shouldWaitForOfficialResumeBeforeFallback(
                true, 0L, now - 1_500L, now, 1_500L));
        assertFalse(ResumeReadinessPolicy.shouldWaitForOfficialResumeBeforeFallback(
                false, 0L, now - 100L, now, 1_500L));
        assertFalse(ResumeReadinessPolicy.shouldWaitForOfficialResumeBeforeFallback(
                true, now - 100L, now - 200L, now, 1_500L));
    }

    @Test
    public void officialResumeColdStartGraceCoversSlowMediaApps() {
        long startedAt = 40_000L;
        assertTrue(ResumeReadinessPolicy.isWithinOfficialResumeColdStartGrace(
                startedAt, startedAt + 2_999L));
        assertFalse(ResumeReadinessPolicy.isWithinOfficialResumeColdStartGrace(
                startedAt, startedAt + 3_000L));
        assertFalse(ResumeReadinessPolicy.isWithinOfficialResumeColdStartGrace(
                0L, startedAt));
        assertFalse(ResumeReadinessPolicy.isWithinOfficialResumeColdStartGrace(
                startedAt, startedAt - 1L));
    }

    @Test
    public void fallbackWakeIsRetriedAfterTheColdStartGraceExpires() {
        assertTrue(ResumeReadinessPolicy.shouldAllowFallbackWakeAttempt(1));
        assertTrue(ResumeReadinessPolicy.shouldAllowFallbackWakeAttempt(2));
        assertTrue(ResumeReadinessPolicy.shouldAllowFallbackWakeAttempt(3));
        assertTrue(ResumeReadinessPolicy.shouldAllowFallbackWakeAttempt(6));
        assertTrue(ResumeReadinessPolicy.shouldAllowFallbackWakeAttempt(9));
        assertTrue(ResumeReadinessPolicy.shouldAllowFallbackWakeAttempt(10));
        assertFalse(ResumeReadinessPolicy.shouldAllowFallbackWakeAttempt(0));
        assertFalse(ResumeReadinessPolicy.shouldAllowFallbackWakeAttempt(8));
        assertFalse(ResumeReadinessPolicy.shouldAllowFallbackWakeAttempt(11));
    }
}
