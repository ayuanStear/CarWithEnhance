package hun.mesh.carwithenhance.policy;

public final class PanelReadyPolicy {
    private PanelReadyPolicy() {
    }

    public static boolean hasPanelIdentity(String mediaId, String title, String artist) {
        if (isBlank(mediaId)) {
            return false;
        }
        return !isBlank(title) || !isBlank(artist);
    }

    public static boolean panelWaitExceeded(
            long startElapsedRealtime, long nowElapsedRealtime, long maxWaitMs) {
        if (startElapsedRealtime <= 0L || nowElapsedRealtime < startElapsedRealtime) {
            return false;
        }
        return nowElapsedRealtime - startElapsedRealtime >= maxWaitMs;
    }

    /**
     * A resume may start only after the player exposed track identity and the
     * CarWith panel has actually rendered it. Timeouts are a last-resort guard
     * so a missing/covered panel cannot block playback forever.
     */
    public static boolean canStartPlayback(
            boolean hasPanelIdentity,
            boolean panelRendered,
            long waitStartElapsedRealtime,
            long nowElapsedRealtime,
            long metadataTimeoutMs,
            long renderTimeoutMs) {
        if (!hasPanelIdentity
                && !panelWaitExceeded(waitStartElapsedRealtime, nowElapsedRealtime, metadataTimeoutMs)) {
            return false;
        }
        if (panelRendered) {
            return true;
        }
        return panelWaitExceeded(
                waitStartElapsedRealtime, nowElapsedRealtime, renderTimeoutMs);
    }

    public static boolean isViewReadyForRender(
            boolean attached,
            boolean shown,
            int width,
            int height,
            boolean hasMetadata) {
        return attached && shown && width > 0 && height > 0 && hasMetadata;
    }

    public static String choosePanelPackage(
            String waitingPackage, String currentPackage, String hostPackage) {
        if (!isBlank(waitingPackage)) {
            return waitingPackage;
        }
        if (!isBlank(currentPackage)) {
            return currentPackage;
        }
        return isBlank(hostPackage) ? null : hostPackage;
    }

    public static boolean shouldAcceptPanelRenderEvent(
            long previousEventElapsedRealtime,
            long nowElapsedRealtime,
            long minIntervalMillis) {
        if (previousEventElapsedRealtime <= 0L || minIntervalMillis <= 0L) {
            return true;
        }
        if (nowElapsedRealtime < previousEventElapsedRealtime) {
            return false;
        }
        return nowElapsedRealtime - previousEventElapsedRealtime >= minIntervalMillis;
    }

    private static boolean isBlank(String value) {
        return value == null || value.trim().isEmpty();
    }
}
