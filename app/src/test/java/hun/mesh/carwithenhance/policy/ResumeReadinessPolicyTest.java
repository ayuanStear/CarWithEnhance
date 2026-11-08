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
}
