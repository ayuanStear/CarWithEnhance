package hun.mesh.carwithenhance.policy;

import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public final class QqAutoPlayPolicy {
    public static final String QQ_MUSIC = "com.tencent.qqmusic";

    private QqAutoPlayPolicy() {
    }

    public static boolean shouldIntercept(boolean moduleEnabled, String targetPackage) {
        return moduleEnabled && QQ_MUSIC.equals(targetPackage);
    }

    public static String chooseTargetPackage(Collection<String> candidates) {
        if (candidates == null || candidates.isEmpty()) {
            return null;
        }
        Set<String> values = new LinkedHashSet<>();
        for (String candidate : candidates) {
            if (candidate != null && !candidate.isEmpty()) {
                values.add(candidate);
            }
        }
        if (values.contains(QQ_MUSIC)) {
            return QQ_MUSIC;
        }
        return values.size() == 1 ? values.iterator().next() : null;
    }

    public static boolean isCompatibleCallback(Class<?>[] parameterTypes) {
        if (parameterTypes == null || parameterTypes.length < 2) {
            return false;
        }
        boolean hasList = false;
        boolean hasString = false;
        for (Class<?> parameterType : parameterTypes) {
            if (parameterType == null) {
                continue;
            }
            if (List.class.isAssignableFrom(parameterType)) {
                hasList = true;
            } else if (parameterType == String.class) {
                hasString = true;
            }
        }
        return hasList && hasString;
    }
}