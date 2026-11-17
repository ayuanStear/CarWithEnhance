package hun.mesh.carwithenhance.policy;

/** Pure readiness rules for the official CarWith resume helper. */
public final class ResumeReadinessPolicy {
    public static final long OFFICIAL_RESUME_COLD_START_GRACE_MS = 3000L;

    private ResumeReadinessPolicy() {
    }

    public static boolean canSendOfficialResume(
            boolean autoPlayEnabled,
            boolean processRunning,
            boolean hasSession,
            boolean targetPlaying,
            boolean otherMediaPlaying) {
        return autoPlayEnabled
                && processRunning
                && hasSession
                && !targetPlaying
                && !otherMediaPlaying;
    }

    public static boolean canSendResumeForExistingSession(
            boolean autoPlayEnabled,
            boolean targetPlaying,
            boolean alreadyResumedThisConnection,
            boolean otherMediaPlaying) {
        return autoPlayEnabled
                && !targetPlaying
                && !alreadyResumedThisConnection
                && !otherMediaPlaying;
    }

    public static boolean shouldWake(
            boolean processRunning,
            boolean hasSession,
            boolean finalAttempt) {
        return !processRunning && !finalAttempt;
    }

    public static boolean shouldSuppressAfterOneKeyClean(
            long cleanAtMillis,
            long nowMillis,
            long maxAgeMillis) {
        return cleanAtMillis > 0L
                && maxAgeMillis > 0L
                && nowMillis >= cleanAtMillis
                && nowMillis - cleanAtMillis <= maxAgeMillis;
    }

    public static boolean isDuplicateResume(
            String lastPackage,
            long lastResumeAtElapsed,
            String packageName,
            long nowElapsed,
            long minIntervalMillis) {
        return packageName != null
                && packageName.equals(lastPackage)
                && lastResumeAtElapsed > 0L
                && minIntervalMillis > 0L
                && nowElapsed >= lastResumeAtElapsed
                && nowElapsed - lastResumeAtElapsed < minIntervalMillis;
    }

    public static boolean shouldWaitForOfficialResumeBeforeFallback(
            boolean autoPlayEnabled,
            long officialRequestAtElapsed,
            long connectionStartedAtElapsed,
            long nowElapsed,
            long graceMillis) {
        return autoPlayEnabled
                && officialRequestAtElapsed <= 0L
                && connectionStartedAtElapsed > 0L
                && graceMillis > 0L
                && nowElapsed >= connectionStartedAtElapsed
                && nowElapsed - connectionStartedAtElapsed < graceMillis;
    }

    public static boolean isWithinOfficialResumeColdStartGrace(
            long officialStartedAtElapsed, long nowElapsed) {
        return officialStartedAtElapsed > 0L
                && nowElapsed >= officialStartedAtElapsed
                && nowElapsed - officialStartedAtElapsed
                < OFFICIAL_RESUME_COLD_START_GRACE_MS;
    }

    public static boolean shouldAllowFallbackWakeAttempt(int attemptIndex) {
        return attemptIndex == 1
                || attemptIndex == 2
                || attemptIndex == 3
                || attemptIndex == 6
                || attemptIndex == 9
                || attemptIndex == 10;
    }
}
