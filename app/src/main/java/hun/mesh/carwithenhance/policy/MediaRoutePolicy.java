package hun.mesh.carwithenhance.policy;

/** Narrow, request-scoped rules for preserving a known custom media page. */
public final class MediaRoutePolicy {
    private MediaRoutePolicy() {
    }

    public static boolean shouldPreferCustom(
            boolean routeRequestActive,
            boolean targetIsCurrentMedia,
            boolean sessionAndMetadataReady,
            boolean officialAlreadyAccepted,
            boolean currentMediaPreviouslyAcceptedCustom,
            boolean userPrefersNative) {
        return routeRequestActive
                && targetIsCurrentMedia
                && sessionAndMetadataReady
                && !officialAlreadyAccepted
                && currentMediaPreviouslyAcceptedCustom
                && !userPrefersNative;
    }
}
