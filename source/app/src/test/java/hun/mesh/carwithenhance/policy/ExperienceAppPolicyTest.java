package hun.mesh.carwithenhance.policy;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import org.junit.Test;

public class ExperienceAppPolicyTest {
    @Test
    public void movesExperienceAppsBehindNormalAppsAndKeepsRelativeOrder() {
        ArrayList<String> apps = new ArrayList<>(Arrays.asList("office", "video", "music", "browser", "radio"));
        HashSet<String> experience = new HashSet<>(Arrays.asList("music", "radio"));

        ExperienceAppPolicy.moveExperienceToEnd(apps, experience, value -> value);

        assertEquals(Arrays.asList("office", "video", "browser", "music", "radio"), apps);
    }

    @Test
    public void recognizesAmapAndOtherKnownMapsAsNonExperienceApps() {
        assertTrue(ExperienceAppPolicy.isKnownMapPackage("com.autonavi.minimap"));
        assertTrue(ExperienceAppPolicy.isKnownMapPackage("com.autonavi.amapauto"));
        assertTrue(ExperienceAppPolicy.isKnownMapPackage("com.baidu.BaiduMap"));
        assertTrue(ExperienceAppPolicy.isKnownMapPackage("com.tencent.map"));
        assertFalse(ExperienceAppPolicy.isKnownMapPackage("com.netease.cloudmusic"));
    }
}