package hun.mesh.carwithenhance.policy;

/** Resolves the root media tab to restore after a detail page is popped. */
public final class MediaTabSelectionPolicy {
    private MediaTabSelectionPolicy() {
    }

    public static int restoreIndex(int savedIndex, int adapterCount, int currentIndex) {
        if (savedIndex >= 0 && savedIndex < adapterCount) {
            return savedIndex;
        }
        return currentIndex;
    }
}
