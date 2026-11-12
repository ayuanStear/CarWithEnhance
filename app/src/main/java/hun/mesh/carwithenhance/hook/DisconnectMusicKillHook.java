package hun.mesh.carwithenhance.hook;

import android.app.ActivityManager;
import android.app.AndroidAppHelper;
import android.app.Application;
import android.content.ContentResolver;
import android.content.Context;
import android.database.ContentObserver;
import android.media.session.MediaController;
import android.media.session.MediaSessionManager;
import android.media.session.PlaybackState;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.provider.Settings;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage;
import hun.mesh.carwithenhance.config.HookConfigs;
import hun.mesh.carwithenhance.utils.XLog;
import java.util.List;

/** 在 system_server 中执行“断开连接后关闭音乐”的延迟停止任务。 */
public class DisconnectMusicKillHook {
    private static final String KEY_CONNECTED = "carwithenhance_is_connected";
    private static Context sSystemContext;
    private static boolean sIsObserverRegistered;
    private static Handler sStopHandler;
    private static Runnable sPendingStop;
    private static long sPendingStopAt;

    public static void hook(XC_LoadPackage.LoadPackageParam loadPackageParam) {
        scheduleObserverRegistration(0);
    }

    private static Context getSystemContext() {
        Context context = sSystemContext;
        if (context != null) {
            return context;
        }
        try {
            Object activityThread = XposedHelpers.callStaticMethod(
                    XposedHelpers.findClass("android.app.ActivityThread", (ClassLoader) null),
                    "currentActivityThread", new Object[0]);
            if (activityThread != null) {
                Context systemContext = (Context) XposedHelpers.callMethod(
                        activityThread, "getSystemContext", new Object[0]);
                if (isSystemOwned(systemContext)) {
                    sSystemContext = systemContext;
                    return systemContext;
                }
            }
        } catch (Throwable ignored) {
        }
        try {
            Application application = AndroidAppHelper.currentApplication();
            if (isSystemOwned(application)) {
                sSystemContext = application;
                return application;
            }
        } catch (Throwable ignored) {
        }
        return sSystemContext;
    }

    private static boolean isSystemOwned(Context context) {
        if (context == null || context.getApplicationInfo() == null) {
            return false;
        }
        return context.getApplicationInfo().uid == 1000;
    }

    private static synchronized boolean registerObserverIfNeeded() {
        if (sIsObserverRegistered) {
            return true;
        }
        try {
            final Context systemContext = getSystemContext();
            if (systemContext == null) {
                return false;
            }
            final ContentResolver resolver = systemContext.getContentResolver();
            final Uri stopUri = Settings.Global.getUriFor(HookConfigs.DISCONNECT_STOP_AT_KEY);
            ContentObserver observer = new ContentObserver(new Handler(Looper.getMainLooper())) {
                @Override
                public void onChange(boolean selfChange, Uri uri) {
                    if (uri == null || uri.equals(stopUri)) {
                        long stopAt = Settings.Global.getLong(
                                resolver, HookConfigs.DISCONNECT_STOP_AT_KEY, 0L);
                        if (stopAt > 0L) {
                            scheduleDisconnectStop(stopAt);
                        }
                    }
                }
            };
            resolver.registerContentObserver(stopUri, false, observer);
            long pendingStopAt = Settings.Global.getLong(
                    resolver, HookConfigs.DISCONNECT_STOP_AT_KEY, 0L);
            if (pendingStopAt > 0L) {
                scheduleDisconnectStop(pendingStopAt);
            }
            sIsObserverRegistered = true;
            return true;
        } catch (Throwable th) {
            return false;
        }
    }

    private static void scheduleObserverRegistration(final int attempt) {
        if (sIsObserverRegistered) {
            return;
        }
        if (registerObserverIfNeeded()) {
            return;
        }
        if (sStopHandler == null) {
            sStopHandler = new Handler(Looper.getMainLooper());
        }
        if (attempt >= 60) {
            XLog.e(">> [断连停止] 全局状态监听器在启动阶段注册失败，已停止重试");
            return;
        }
        sStopHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                scheduleObserverRegistration(attempt + 1);
            }
        }, 1000L);
    }

    private static void scheduleDisconnectStop(long stopAt) {
        if (stopAt <= 0L) {
            return;
        }
        if (sStopHandler == null) {
            sStopHandler = new Handler(Looper.getMainLooper());
        }
        if (sPendingStop != null && sPendingStopAt == stopAt) {
            return;
        }
        if (sPendingStop != null) {
            sStopHandler.removeCallbacks(sPendingStop);
        }
        sPendingStopAt = stopAt;
        final long delayMillis = Math.max(0L, stopAt - System.currentTimeMillis());
        sPendingStop = new Runnable() {
            @Override
            public void run() {
                sPendingStop = null;
                sPendingStopAt = 0L;
                Context context = getSystemContext();
                if (context == null) {
                    XLog.e(">> [断连停止] system context 不可用");
                    return;
                }
                ContentResolver resolver = context.getContentResolver();
                if (Settings.Global.getInt(resolver, KEY_CONNECTED, 0) == 1) {
                    clearDisconnectStopCommand(resolver);
                    return;
                }
                String targets = Settings.Global.getString(
                        resolver, HookConfigs.DISCONNECT_TARGETS_KEY);
                if (targets != null && !targets.isEmpty()) {
                    for (String target : targets.split(",")) {
                        String packageName = target.trim();
                        if (packageName.isEmpty()) {
                            continue;
                        }
                        if (isMusicForeground(context, packageName)) {
                            continue;
                        }
                        if (isMusicPlaying(context, packageName)) {
                            continue;
                        }
                        forceStopMusicPackage(context, packageName);
                    }
                }
                clearDisconnectStopCommand(resolver);
            }
        };
        sStopHandler.postDelayed(sPendingStop, delayMillis);
    }

    private static void clearDisconnectStopCommand(ContentResolver resolver) {
        try {
            Settings.Global.putString(resolver, HookConfigs.DISCONNECT_TARGETS_KEY, "");
            Settings.Global.putLong(resolver, HookConfigs.DISCONNECT_STOP_AT_KEY, 0L);
        } catch (Throwable th) {
            XLog.e(">> [断连停止] 清理一次性任务失败", th);
        }
    }

    private static boolean isMusicForeground(Context context, String packageName) {
        try {
            ActivityManager activityManager =
                    (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
            if (activityManager == null) {
                return false;
            }
            List<ActivityManager.RunningAppProcessInfo> processes =
                    activityManager.getRunningAppProcesses();
            if (processes == null) {
                return false;
            }
            String packagePrefix = packageName + ":";
            for (ActivityManager.RunningAppProcessInfo process : processes) {
                if (process == null || process.processName == null) {
                    continue;
                }
                if ((process.processName.equals(packageName)
                        || process.processName.startsWith(packagePrefix))
                        && process.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
                    return true;
                }
            }
        } catch (Throwable th) {
            XLog.e(">> [断连停止] 检查音乐应用前台状态失败: " + packageName, th);
        }
        return false;
    }

    private static boolean isMusicPlaying(Context context, String packageName) {
        try {
            MediaSessionManager mediaSessionManager =
                    (MediaSessionManager) context.getSystemService(Context.MEDIA_SESSION_SERVICE);
            if (mediaSessionManager == null) {
                return false;
            }
            List<MediaController> sessions = mediaSessionManager.getActiveSessions(null);
            if (sessions == null) {
                return false;
            }
            for (MediaController controller : sessions) {
                if (controller == null || !packageName.equals(controller.getPackageName())) {
                    continue;
                }
                PlaybackState state = controller.getPlaybackState();
                if (state != null && state.getState() == PlaybackState.STATE_PLAYING) {
                    return true;
                }
            }
        } catch (Throwable th) {
            XLog.e(">> [断连停止] 检查音乐应用播放状态失败: " + packageName, th);
        }
        return false;
    }

    private static void forceStopMusicPackage(Context context, String packageName) {
        try {
            ActivityManager activityManager =
                    (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
            if (activityManager != null) {
                XposedHelpers.callMethod(activityManager, "forceStopPackage", packageName);
            }
        } catch (Throwable th) {
            XLog.e(">> [断连停止] 停止音乐进程失败: " + packageName, th);
        }
    }
}