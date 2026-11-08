package hun.mesh.carwithenhance.policy;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class MediaRoutePolicyTest {
    @Test
    public void readyCurrentCustomPageCanOverrideTransientOfficialRejection() {
        assertTrue(MediaRoutePolicy.shouldPreferCustom(
                true, true, true, false, true, false));
    }

    @Test
    public void inactiveRouteNeverOverridesOfficialDecision() {
        assertFalse(MediaRoutePolicy.shouldPreferCustom(
                false, true, true, false, true, false));
    }

    @Test
    public void unreadySessionKeepsOfficialEmbeddingRoute() {
        assertFalse(MediaRoutePolicy.shouldPreferCustom(
                true, true, false, false, true, false));
    }

    @Test
    public void anotherMediaPackageIsNotForcedIntoCustomPage() {
        assertFalse(MediaRoutePolicy.shouldPreferCustom(
                true, false, true, false, true, false));
    }

    @Test
    public void unsupportedPackageKeepsOfficialRoute() {
        assertFalse(MediaRoutePolicy.shouldPreferCustom(
                true, true, true, false, false, false));
    }

    @Test
    public void qqNativePagePreferenceWins() {
        assertFalse(MediaRoutePolicy.shouldPreferCustom(
                true, true, true, false, true, true));
    }

    @Test
    public void alreadyAcceptedOfficialRouteDoesNotNeedOverride() {
        assertFalse(MediaRoutePolicy.shouldPreferCustom(
                true, true, true, true, true, false));
    }
}
