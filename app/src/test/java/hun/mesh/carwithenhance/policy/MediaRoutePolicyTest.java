package hun.mesh.carwithenhance.policy;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class MediaRoutePolicyTest {
    @Test
    public void readyCurrentCustomPageIsRoutedToCustomPage() {
        assertTrue(MediaRoutePolicy.shouldPreferCustom(
                true, true, true, true, false));
    }

    @Test
    public void inactiveRouteNeverOverridesOfficialDecision() {
        assertFalse(MediaRoutePolicy.shouldPreferCustom(
                false, true, true, true, false));
    }

    @Test
    public void unreadySessionKeepsOfficialEmbeddingRoute() {
        assertFalse(MediaRoutePolicy.shouldPreferCustom(
                true, true, false, true, false));
    }

    @Test
    public void anotherMediaPackageIsNotForcedIntoCustomPage() {
        assertFalse(MediaRoutePolicy.shouldPreferCustom(
                true, false, true, true, false));
    }

    @Test
    public void unsupportedPackageKeepsOfficialRoute() {
        assertFalse(MediaRoutePolicy.shouldPreferCustom(
                true, true, true, false, false));
    }

    @Test
    public void qqNativePagePreferenceWins() {
        assertFalse(MediaRoutePolicy.shouldPreferCustom(
                true, true, true, true, true));
    }

    @Test
    public void officialTrueVerdictIsNoLongerTrustedAndStillOverrides() {
        // CarWith can report the capability as allowed yet still fall back to
        // the embedded native page; the override must not depend on it.
        assertTrue(MediaRoutePolicy.shouldPreferCustom(
                true, true, true, true, false));
    }
}
