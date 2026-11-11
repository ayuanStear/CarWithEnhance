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

    private static boolean isBlank(String value) {
        return value == null || value.trim().isEmpty();
    }
}
