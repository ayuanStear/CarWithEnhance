package hun.mesh.carwithenhance.policy;

/** Pure readiness rules for the official CarWith resume helper. */
public final class ResumeReadinessPolicy {
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
}
