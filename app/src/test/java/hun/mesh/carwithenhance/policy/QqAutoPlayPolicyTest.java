package hun.mesh.carwithenhance.policy;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.junit.Test;

public class QqAutoPlayPolicyTest {
    @Test
    public void interceptsOnlyQqMusicWhileModuleSwitchIsEnabled() {
        assertTrue(QqAutoPlayPolicy.shouldIntercept(true, "com.tencent.qqmusic"));
        assertFalse(QqAutoPlayPolicy.shouldIntercept(false, "com.tencent.qqmusic"));
        assertFalse(QqAutoPlayPolicy.shouldIntercept(true, "com.netease.cloudmusic"));
        assertFalse(QqAutoPlayPolicy.shouldIntercept(true, null));
    }

    @Test
    public void choosesQqPackageFromCallbackStringFields() {
        assertEquals("com.tencent.qqmusic", QqAutoPlayPolicy.chooseTargetPackage(
                Arrays.asList("com.tencent.qqmusic", "unrelated")));
        assertEquals("com.netease.cloudmusic", QqAutoPlayPolicy.chooseTargetPackage(
                Collections.singletonList("com.netease.cloudmusic")));
        assertNull(QqAutoPlayPolicy.chooseTargetPackage(
                Arrays.asList("one", "two")));
        assertNull(QqAutoPlayPolicy.chooseTargetPackage(new ArrayList<String>()));
    }

    @Test
    public void acceptsOldAndNewBrowserCallbackShapes() {
        assertTrue(QqAutoPlayPolicy.isCompatibleCallback(new Class<?>[]{
                boolean.class, int.class, List.class, int.class, String.class, Object.class}));
        assertTrue(QqAutoPlayPolicy.isCompatibleCallback(new Class<?>[]{
                List.class, String.class}));
        assertTrue(QqAutoPlayPolicy.isCompatibleCallback(new Class<?>[]{
                boolean.class, List.class, String.class, Object.class}));
        assertFalse(QqAutoPlayPolicy.isCompatibleCallback(new Class<?>[]{
                String.class, boolean.class}));
        assertFalse(QqAutoPlayPolicy.isCompatibleCallback(new Class<?>[]{
                List.class, Object.class}));
    }
}