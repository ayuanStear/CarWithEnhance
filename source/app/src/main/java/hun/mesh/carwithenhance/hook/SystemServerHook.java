package hun.mesh.carwithenhance.hook;

import android.app.ActivityManager;
import android.app.AndroidAppHelper;
import android.app.Application;
import android.content.ContentResolver;
import android.content.Context;
import android.database.ContentObserver;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.provider.Settings;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage;
import hun.mesh.carwithenhance.config.HookConfigs;
import hun.mesh.carwithenhance.utils.XLog;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class SystemServerHook {
    private static final String KEY_CONNECTED = "carwithenhance_is_connected";
    private static final Set<String> PROTECTED_PACKAGES = new HashSet(Arrays.asList(
            "com.baidu.carlife.xiaomi",
            "com.autonavi.minimap",
            "com.baidu.BaiduMap",
            "com.tencent.qqmusic",
            "com.netease.cloudmusic",
            "com.kugou.android",
            "cn.kuwo.player"));

    private static Context sSystemContext;
    private static boolean sIsKeepAliveEnabled = HookConfigs.CARWITH_KEEPALIVE.defaultValue;
    private static boolean sIsCarWithConnected;
    private static boolean sIsObserverRegistered;
    private static Handler sStopHandler;
    private static Runnable sPendingStop;
    private static long sPendingStopAt;

    private static String getProcessName(Object processRecord) {
        if (processRecord == null) {
            return null;
        }
        try {
            return (String) XposedHelpers.getObjectField(processRecord, "processName");
        } catch (Throwable ignored) {
            return null;
        }
    }

    private static Context getSystemContext() {
        Context context = sSystemContext;
        if (context != null) {
            return context;
        }
        try {
            Application application = AndroidAppHelper.currentApplication();
            if (application != null) {
                sSystemContext = application;
                return application;
            }
        } catch (Throwable ignored) {
        }
        try {
            Object activityThread = XposedHelpers.callStaticMethod(
                    XposedHelpers.findClass("android.app.ActivityThread", (ClassLoader) null),
                    "currentActivityThread", new Object[0]);
            if (activityThread != null) {
                sSystemContext = (Context) XposedHelpers.callMethod(
                        activityThread, "getSystemContext", new Object[0]);
            }
        } catch (Throwable ignored) {
        }
        return sSystemContext;
    }

    public static void hook(XC_LoadPackage.LoadPackageParam loadPackageParam) {
        try {
            XLog.i(">> [进程保护] 成功拦截系统底层 (system_server)");
            hookProcessCleanerBase(loadPackageParam.classLoader);
        } catch (Throwable th) {
            XLog.e("❌ [进程保护] SystemServerHook 初始化异常", th);
        }
        try {
            hookSubProcessAdjBindList(loadPackageParam.classLoader);
        } catch (Throwable th) {
            XLog.e("❌ [进程保护] 挂载 sSubProcessAdjBindList 失败", th);
        }
        scheduleObserverRegistration(0);
    }

    private static void hookProcessCleanerBase(ClassLoader classLoader) {
        try {
            XposedHelpers.findAndHookMethod(
                    "com.android.server.am.ProcessCleanerBase", classLoader, "killOnce",
                    new Object[]{"com.android.server.am.ProcessRecord", String.class, Integer.TYPE,
                            Handler.class, Context.class, new XC_MethodHook() {
                                @Override
                                protected void beforeHookedMethod(MethodHookParam param) {
                                    int level = ((Integer) param.args[2]).intValue();
                                    String processName = getProcessName(param.args[0]);
                                    if (level >= 102 && isKeepAliveConfigEnabled()
                                            && isCarWithConnected() && processName != null) {
                                        String packageName = processName.split(":")[0];
                                        if (PROTECTED_PACKAGES.contains(packageName)) {
                                            XLog.i(">> [进程保护] killOnce: [" + packageName
                                                    + "], level=" + level + ", reason="
                                                    + param.args[1] + " -> 已拦截");
                                            param.setResult(null);
                                        }
                                    }
                                }
                            }});
            XLog.i(">> [进程保护] Hook ProcessCleanerBase.killOnce 注册成功");
        } catch (Throwable th) {
            XLog.e("❌ [进程保护] Hook ProcessCleanerBase.killOnce 失败", th);
        }
    }

    private static void hookSubProcessAdjBindList(ClassLoader classLoader) {
    }

    private static void injectProtectedProcs(Class<?> cls) {
        try {
            Map map = (Map) XposedHelpers.getStaticObjectField(cls, "sSubProcessAdjBindList");
            if (map != null) {
                map.put("com.tencent.qqmusic", new ArrayList(Arrays.asList(
                        "com.tencent.qqmusic", "com.tencent.qqmusic:QQPlayerService")));
                XLog.i(">> [进程保护] sSubProcessAdjBindList 注入成功: 已保护 QQmusic");
            }
        } catch (Throwable th) {
            XLog.e(">> [进程保护] 注入 sSubProcessAdjBindList 数据失败", th);
        }
    }

    private static boolean isCarWithConnected() {
        if (!sIsObserverRegistered) {
            registerObserverIfNeeded();
        }
        return sIsCarWithConnected;
    }

    private static boolean isKeepAliveConfigEnabled() {
        if (!sIsObserverRegistered) {
            registerObserverIfNeeded();
        }
        return sIsKeepAliveEnabled;
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
            final Uri connectedUri = Settings.Global.getUriFor(KEY_CONNECTED);
            final Uri keepAliveUri = Settings.Global.getUriFor(HookConfigs.CARWITH_KEEPALIVE.key);
            final Uri stopUri = Settings.Global.getUriFor(HookConfigs.DISCONNECT_STOP_AT_KEY);
            ContentObserver observer = new ContentObserver(new Handler(Looper.getMainLooper())) {
                @Override
                public void onChange(boolean selfChange, Uri uri) {
                    if (uri == null || uri.equals(connectedUri)) {
                        sIsCarWithConnected = Settings.Global.getInt(resolver, KEY_CONNECTED, 0) == 1;
                    }
                    if (uri == null || uri.equals(keepAliveUri)) {
                        int keepAlive = Settings.Global.getInt(
                                resolver, HookConfigs.CARWITH_KEEPALIVE.key, -1);
                        if (keepAlive != -1) {
                            sIsKeepAliveEnabled = keepAlive == 1;
                        }
                    }
                    if (uri == null || uri.equals(stopUri)) {
                        long stopAt = Settings.Global.getLong(
                                resolver, HookConfigs.DISCONNECT_STOP_AT_KEY, 0L);
                        if (stopAt > 0L) {
                            scheduleDisconnectStop(stopAt);
                        }
                    }
                }
            };
            resolver.registerContentObserver(connectedUri, false, observer);
            resolver.registerContentObserver(keepAliveUri, false, observer);
            resolver.registerContentObserver(stopUri, false, observer);
            sIsCarWithConnected = Settings.Global.getInt(resolver, KEY_CONNECTED, 0) == 1;
            int keepAlive = Settings.Global.getInt(
                    resolver, HookConfigs.CARWITH_KEEPALIVE.key, -1);
            if (keepAlive != -1) {
                sIsKeepAliveEnabled = keepAlive == 1;
            }
            sIsObserverRegistered = true;
            XLog.i(">> [进程保护] 全局状态与一次性停止任务监听器注册成功");
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
            XLog.e(">> [进程保护] 全局状态监听器在启动阶段注册失败，已停止重试");
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
                    XLog.i(">> [断连停止] 已重新连接，取消音乐清理");
                    clearDisconnectStopCommand(resolver);
                    return;
                }
                String targets = Settings.Global.getString(
                        resolver, HookConfigs.DISCONNECT_TARGETS_KEY);
                if (targets != null && !targets.isEmpty()) {
                    for (String target : targets.split(",")) {
                        String packageName = target.trim();
                        if (!packageName.isEmpty()) {
                            forceStopMusicPackage(context, packageName);
                        }
                    }
                }
                clearDisconnectStopCommand(resolver);
                XLog.i(">> [断连停止] 一次性任务完成");
            }
        };
        sStopHandler.postDelayed(sPendingStop, delayMillis);
        XLog.i(">> [断连停止] 已安排一次性任务，延迟 " + delayMillis + " ms");
    }

    private static void clearDisconnectStopCommand(ContentResolver resolver) {
        try {
            Settings.Global.putString(resolver, HookConfigs.DISCONNECT_TARGETS_KEY, "");
            Settings.Global.putLong(resolver, HookConfigs.DISCONNECT_STOP_AT_KEY, 0L);
        } catch (Throwable th) {
            XLog.e(">> [断连停止] 清理一次性任务失败", th);
        }
    }

    // 仅用于用户配置的音乐目标；CarWith 必须走自身 killProcess，避免被标记为 stopped。
    private static void forceStopMusicPackage(Context context, String packageName) {
        try {
            ActivityManager activityManager =
                    (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
            if (activityManager != null) {
                XposedHelpers.callMethod(activityManager, "forceStopPackage", packageName);
                XLog.i(">> [断连停止] 已停止音乐进程: " + packageName);
            }
        } catch (Throwable th) {
            XLog.e(">> [断连停止] 停止音乐进程失败: " + packageName, th);
        }
    }
}
