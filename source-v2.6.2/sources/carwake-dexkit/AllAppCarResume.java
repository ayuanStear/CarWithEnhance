import android.app.AndroidAppHelper;
import android.content.Context;
import android.content.SharedPreferences;
import android.media.session.MediaController;
import android.media.session.MediaSessionManager;
import android.media.session.PlaybackState;
import android.os.Handler;
import android.os.Looper;
import android.os.SystemClock;
import android.provider.Settings;
import android.text.TextUtils;

import java.util.List;

import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;

/**
 * Records the last playing media app and rewrites CarWith's native auto-play target.
 * The original CarWith method always continues. Qishui gets one explicit media-key
 * fallback only when its stopped process has no active MediaSession.
 */
public final class AllAppCarResume {
    private static final String TAG = "CarWithX AllAppResume";
    private static final String CARWITH_PREFS = "file_prefer_app_091703";
    private static final String AUTO_PLAY_KEY = "prefer_auto_play";
    private static final String RESUME_PREFS = "carwake_all_app_resume";
    private static final String LAST_PACKAGE_KEY = "last_package";
    private static final long TRACK_INTERVAL_MS = 3000L;
    private static final long TRACK_ARM_DELAY_MS = 15000L;

    private static final Handler MAIN = new Handler(Looper.getMainLooper());
    private static boolean installed;
    private static boolean trackingArmed;
    private static boolean previousConnected;
    private static long connectedSince;
    private static String lastRecordedPackage;

    private AllAppCarResume() {
    }

    private static final Runnable TRACKER = new Runnable() {
        @Override
        public void run() {
            Context context = AndroidAppHelper.currentApplication();
            if (context != null) {
                try {
                    long now = SystemClock.elapsedRealtime();
                    if (!isCarWithConnected(context)) {
                        previousConnected = false;
                        connectedSince = 0L;
                        trackingArmed = false;
                    } else {
                        if (!previousConnected) {
                            previousConnected = true;
                            connectedSince = now;
                            lastRecordedPackage = null;
                        }
                        if (!trackingArmed && now - connectedSince >= TRACK_ARM_DELAY_MS) {
                            trackingArmed = true;
                        }
                        if (trackingArmed) {
                            recordPlayingPackage(context);
                        }
                    }
                } catch (Throwable throwable) {
                    XposedBridge.log(TAG + ": tracker error: " + throwable);
                }
            }
            MAIN.postDelayed(this, TRACK_INTERVAL_MS);
        }
    };

    public static synchronized void install(final ClassLoader classLoader) {
        if (installed) {
            return;
        }
        String[] target = resolveAutoPlayTarget(classLoader);
        if (target == null) {
            XposedBridge.log(TAG + ": auto-play trigger unavailable");
            return;
        }
        install(classLoader, target[0], target[1]);
    }

    public static synchronized void install(final ClassLoader classLoader,
                                             final String targetClass,
                                             final String targetMethod) {
        if (installed) {
            return;
        }
        if (targetClass == null || targetMethod == null) {
            XposedBridge.log(TAG + ": auto-play trigger unavailable");
            return;
        }
        installed = true;
        MAIN.removeCallbacks(TRACKER);
        MAIN.post(TRACKER);
        try {
            Class<?> trigger = XposedHelpers.findClass(targetClass, classLoader);
            XposedHelpers.findAndHookMethod(
                    trigger,
                    targetMethod,
                    String.class,
                    boolean.class,
                    new XC_MethodHook() {
                        @Override
                        protected void beforeHookedMethod(MethodHookParam param) {
                            try {
                                Context context = AndroidAppHelper.currentApplication();
                                if (context == null || param.args == null || param.args.length < 1) {
                                    return;
                                }
                                String requested = (String) param.args[0];
                                boolean enabled = isSwitchEnabled(context);
                                String saved = enabled ? loadLastPackage(context) : null;
                                boolean savedInstalled = enabled
                                        && !TextUtils.isEmpty(saved)
                                        && isPackageInstalled(context, saved);
                                String selected = NativeResumePolicy.selectTarget(
                                        enabled, requested, saved, savedInstalled);
                                if (!TextUtils.equals(selected, requested)) {
                                    param.args[0] = selected;
                                    XposedBridge.log(TAG + ": native auto-play target "
                                            + requested + " -> " + selected);
                                }
                                if (enabled) {
                                    QishuiMediaKeyFallback.resumeIfNeeded(context, selected);
                                }
                            } catch (Throwable throwable) {
                                XposedBridge.log(TAG + ": target rewrite failed: " + throwable);
                            }
                        }
                    });
            XposedBridge.log(TAG + ": installed native target rewrite");
        } catch (Throwable throwable) {
            XposedBridge.log(TAG + ": install failed: " + throwable);
            XposedBridge.log(throwable);
        }
    }

    private static String[] resolveAutoPlayTarget(ClassLoader hostClassLoader) {
        ClassLoader moduleLoader = AllAppCarResume.class.getClassLoader();
        if (moduleLoader == null) {
            moduleLoader = hostClassLoader;
        }
        try {
            Class<?> carWakeTargets = Class.forName("CarWakeDexKitTargets", false, moduleLoader);
            String targetClass = (String) XposedHelpers.callStaticMethod(
                    carWakeTargets, "getAutoPlayTriggerClass");
            String targetMethod = (String) XposedHelpers.callStaticMethod(
                    carWakeTargets, "getAutoPlayTriggerMethod");
            if (!TextUtils.isEmpty(targetClass) && !TextUtils.isEmpty(targetMethod)) {
                return new String[]{targetClass, targetMethod};
            }
        } catch (Throwable ignored) {
        }
        try {
            Class<?> generic = Class.forName(
                    "hun.mesh.carwithenhance.dexkit.GenericAutoPlayHooks", false, moduleLoader);
            Object[] target = (Object[]) XposedHelpers.callStaticMethod(
                    generic, "resolveMediaAutoPlay", hostClassLoader);
            if (target != null && target.length >= 2
                    && target[0] instanceof Class && target[1] instanceof String) {
                String targetClass = ((Class<?>) target[0]).getName();
                String targetMethod = (String) target[1];
                if (!TextUtils.isEmpty(targetClass) && !TextUtils.isEmpty(targetMethod)) {
                    return new String[]{targetClass, targetMethod};
                }
            }
        } catch (Throwable throwable) {
            XposedBridge.log(TAG + ": 2.6.1 auto-play target resolution failed: " + throwable);
        }
        return null;
    }

    private static boolean isCarWithConnected(Context context) {
        try {
            return Settings.Global.getInt(
                    context.getContentResolver(), "carwithenhance_is_connected", 0) == 1
                    || "1".equals(Settings.Global.getString(
                            context.getContentResolver(), "ucar_casting_state"));
        } catch (Throwable throwable) {
            return false;
        }
    }

    private static void recordPlayingPackage(Context context) {
        try {
            MediaSessionManager manager = (MediaSessionManager)
                    context.getSystemService(Context.MEDIA_SESSION_SERVICE);
            if (manager == null) {
                return;
            }
            List<MediaController> controllers = manager.getActiveSessions(null);
            if (controllers == null) {
                return;
            }
            for (MediaController controller : controllers) {
                if (controller == null) {
                    continue;
                }
                PlaybackState state = controller.getPlaybackState();
                if (state != null && state.getState() == PlaybackState.STATE_PLAYING) {
                    String packageName = controller.getPackageName();
                    if (!TextUtils.isEmpty(packageName)
                            && !packageName.equals(lastRecordedPackage)
                            && !packageName.equals(context.getPackageName())) {
                        lastRecordedPackage = packageName;
                        saveLastPackage(context, packageName);
                        XposedBridge.log(TAG + ": recorded playing package " + packageName);
                    }
                    return;
                }
            }
        } catch (Throwable throwable) {
            XposedBridge.log(TAG + ": media tracking failed: " + throwable);
        }
    }

    private static boolean isSwitchEnabled(Context context) {
        try {
            SharedPreferences preferences = context.getSharedPreferences(CARWITH_PREFS, 0);
            return preferences.getBoolean(AUTO_PLAY_KEY, false);
        } catch (Throwable throwable) {
            XposedBridge.log(TAG + ": switch read failed: " + throwable);
            return false;
        }
    }

    private static boolean isPackageInstalled(Context context, String packageName) {
        try {
            context.getPackageManager().getPackageInfo(packageName, 0);
            return true;
        } catch (Throwable ignored) {
            return false;
        }
    }

    private static String loadLastPackage(Context context) {
        try {
            return context.getSharedPreferences(RESUME_PREFS, 0)
                    .getString(LAST_PACKAGE_KEY, null);
        } catch (Throwable throwable) {
            XposedBridge.log(TAG + ": history read failed: " + throwable);
            return null;
        }
    }

    private static void saveLastPackage(Context context, String packageName) {
        try {
            context.getSharedPreferences(RESUME_PREFS, 0)
                    .edit()
                    .putString(LAST_PACKAGE_KEY, packageName)
                    .putLong("updated_at", System.currentTimeMillis())
                    .apply();
        } catch (Throwable throwable) {
            XposedBridge.log(TAG + ": history save failed: " + throwable);
        }
    }
}
