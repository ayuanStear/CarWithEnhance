package hun.mesh.carwithenhance.policy;

/**
 * Narrow, request-scoped rules for routing an active media-app open back to the
 * custom car page. The official capability verdict is deliberately not trusted:
 * CarWith can report true yet still fall through to the embedded native page
 * when the cloud whitelist or session state shifts mid-request.
 */
public final class MediaRoutePolicy {
    private MediaRoutePolicy() {
    }

    public static boolean shouldPreferCustom(
            boolean routeRequestActive,
            boolean targetIsCurrentMedia,
            boolean sessionAndMetadataReady,
            boolean currentMediaPreviouslyAcceptedCustom,
            boolean userPrefersNative) {
        return routeRequestActive
                && targetIsCurrentMedia
                && sessionAndMetadataReady
                && currentMediaPreviouslyAcceptedCustom
                && !userPrefersNative;
    }
}
