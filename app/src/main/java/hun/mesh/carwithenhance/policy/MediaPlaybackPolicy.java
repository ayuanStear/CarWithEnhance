package hun.mesh.carwithenhance.policy;

import java.util.Set;

public final class MediaPlaybackPolicy {
    public interface PackageValidator {
        boolean isValid(String packageName);
    }

    private MediaPlaybackPolicy() {
    }

    public static String chooseLastLaunched(
            String lastLaunched,
            String officialLast,
            String current,
            String preferred,
            PackageValidator validator) {
        String[] candidates = {lastLaunched, officialLast, current, preferred};
        for (String candidate : candidates) {
            if (candidate != null && !candidate.isEmpty() && validator.isValid(candidate)) {
                return candidate;
            }
        }
        return null;
    }

    public static boolean shouldRecordLastLaunchedEvent(int source) {
        return source == 1;
    }

    public static boolean shouldSuppressPlayback(boolean targetPlaying, boolean otherPlaying) {
        return !targetPlaying && otherPlaying;
    }

    public static boolean shouldPreserveDockIcon(boolean targetPlaying, boolean otherPlaying) {
        return !targetPlaying && otherPlaying;
    }

    /**
     * Returns the player that was known to be playing before the new player started.
     * The old player may already have paused by the time MediaController reports the
     * new STATE_PLAYING, so this must not re-query the current session state.
     */
    public static String previousPackageToStop(
            String knownPlaying, String newlyPlaying, Set<String> protectedPackages) {
        if (knownPlaying == null || knownPlaying.isEmpty()
                || newlyPlaying == null || newlyPlaying.isEmpty()
                || knownPlaying.equals(newlyPlaying)
                || (protectedPackages != null && protectedPackages.contains(knownPlaying))) {
            return null;
        }
        return knownPlaying;
    }

    public static boolean shouldKillPreviousAfterSwitch(
            boolean switchKillEnabled,
            boolean newPlayerPlaying,
            String previousPackage,
            String currentPackage) {
        return switchKillEnabled
                && newPlayerPlaying
                && previousPackage != null
                && !previousPackage.isEmpty()
                && currentPackage != null
                && !currentPackage.isEmpty()
                && !previousPackage.equals(currentPackage);
    }
}
