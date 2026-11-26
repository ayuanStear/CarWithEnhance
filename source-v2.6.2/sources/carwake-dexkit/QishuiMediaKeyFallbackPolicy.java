/** Pure selection policy for the Qishui stopped-package media-key fallback. */
public final class QishuiMediaKeyFallbackPolicy {
    private static final String QISHUI_PACKAGE = "com.luna.music";

    private QishuiMediaKeyFallbackPolicy() {
    }

    public static boolean shouldDispatch(String packageName, boolean hasActiveSession) {
        return !hasActiveSession && QISHUI_PACKAGE.equals(packageName);
    }
}
