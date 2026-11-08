package hun.mesh.carwithenhance.policy;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public final class ExperienceAppPolicy {
    public interface PackageNameExtractor<T> {
        String packageNameOf(T value);
    }

    private static final Set<String> KNOWN_MAP_PACKAGES = Collections.unmodifiableSet(
            new HashSet<>(Arrays.asList(
                    "com.autonavi.minimap",
                    "com.autonavi.amapauto",
                    "com.baidu.BaiduMap",
                    "com.baidu.naviauto",
                    "com.tencent.map",
                    "com.google.android.apps.maps")));

    private ExperienceAppPolicy() {
    }

    public static boolean isKnownMapPackage(String packageName) {
        return packageName != null && KNOWN_MAP_PACKAGES.contains(packageName);
    }

    public static Set<String> knownMapPackages() {
        return KNOWN_MAP_PACKAGES;
    }

    public static <T> void moveExperienceToEnd(
            List<T> apps, Set<String> experiencePackages, PackageNameExtractor<T> extractor) {
        if (apps == null || apps.size() < 2) {
            return;
        }
        List<T> normal = new ArrayList<>(apps.size());
        List<T> experience = new ArrayList<>();
        for (T app : apps) {
            String packageName = app == null ? null : extractor.packageNameOf(app);
            if (packageName != null && experiencePackages != null
                    && experiencePackages.contains(packageName)) {
                experience.add(app);
            } else {
                normal.add(app);
            }
        }
        apps.clear();
        apps.addAll(normal);
        apps.addAll(experience);
    }
}