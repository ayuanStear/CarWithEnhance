/** Pure selection policy for native CarWith auto-play target replacement. */
public final class NativeResumePolicy {
    private NativeResumePolicy() {
    }

    public static boolean shouldHandleNativeCall(
            boolean autoResume,
            boolean currentPrewake,
            boolean moduleCompletedResume,
            boolean targetSameAsPrewake) {
        return autoResume || currentPrewake && moduleCompletedResume
                && targetSameAsPrewake;
    }

    public static boolean shouldSuppressNativeCall(
            boolean currentPrewake,
            boolean moduleCompletedResume,
            boolean targetPlaying) {
        return currentPrewake && (moduleCompletedResume || targetPlaying);
    }

    public static boolean shouldAllowRetry(
            boolean enabled,
            boolean currentPrewake,
            boolean moduleCompletedResume,
            boolean playing) {
        return enabled && currentPrewake && !moduleCompletedResume && !playing;
    }

    public static boolean isPrewakeCurrent(long connectedAtElapsed, long nowElapsed) {
        return connectedAtElapsed > 0L && nowElapsed >= connectedAtElapsed
                && nowElapsed - connectedAtElapsed <= 30000L;
    }

    public static String selectTarget(boolean enabled,
                                      String requestedPackage,
                                      String savedPackage,
                                      boolean savedInstalled) {
        if (!enabled || !savedInstalled || savedPackage == null || savedPackage.length() == 0) {
            return requestedPackage;
        }
        return savedPackage;
    }
}
