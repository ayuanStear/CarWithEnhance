/** Pure selection policy for native CarWith auto-play target replacement. */
public final class NativeResumePolicy {
    private NativeResumePolicy() {
    }

    public static String selectTarget(boolean enabled,
                                      String requestedPackage,
                                      String savedPackage,
                                      boolean savedInstalled) {
        if (!enabled || !savedInstalled || savedPackage == null || savedPackage.length() == 0) {
            return requestedPackage;
        }
        return savedPackage;
    }
}
