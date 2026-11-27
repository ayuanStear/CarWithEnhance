package hun.mesh.carwithenhance.policy;

/** Narrow, request-scoped rules for preserving a known custom media page. */
public final class MediaRoutePolicy {
    private MediaRoutePolicy() {
    }

    public static boolean shouldPreferCustom(
            boolean routeRequestActive,
            boolean targetIsCurrentMedia,
            boolean currentMediaPreviouslyAcceptedCustom,
            boolean userPrefersNative) {
        // Keep the custom page during song switches and metadata refreshes.
        // The page only falls back when the user explicitly prefers the
        // native player, or when the current media app was never accepted as
        // custom before (first-time routing still follows the host).
        return routeRequestActive
                && targetIsCurrentMedia
                && currentMediaPreviouslyAcceptedCustom
                && !userPrefersNative;
    }
}
