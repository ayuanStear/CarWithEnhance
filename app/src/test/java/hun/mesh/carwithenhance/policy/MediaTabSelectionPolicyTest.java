package hun.mesh.carwithenhance.policy;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class MediaTabSelectionPolicyTest {
    @Test
    public void restoresSelectedTabAfterBackStackViewPagerReset() {
        assertEquals(3, MediaTabSelectionPolicy.restoreIndex(3, 4, 0));
    }

    @Test
    public void keepsCurrentTabWhenNoSelectionWasSaved() {
        assertEquals(0, MediaTabSelectionPolicy.restoreIndex(-1, 4, 0));
    }

    @Test
    public void keepsCurrentTabWhenSavedSelectionIsStale() {
        assertEquals(2, MediaTabSelectionPolicy.restoreIndex(8, 4, 2));
    }

    @Test
    public void keepsCurrentTabWhenAdapterHasNoPages() {
        assertEquals(0, MediaTabSelectionPolicy.restoreIndex(3, 0, 0));
    }
}
