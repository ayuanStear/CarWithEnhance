package hun.mesh.carwithenhance.hook;

import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.pm.ServiceInfo;
import android.media.AudioDeviceInfo;
import android.media.AudioManager;
import android.media.MediaMetadata;
import android.media.browse.MediaBrowser;
import android.media.session.MediaController;
import android.media.session.MediaSessionManager;
import android.media.session.PlaybackState;
import android.os.Handler;
import android.os.Looper;
import android.os.SystemClock;
import android.provider.Settings;
import android.util.Log;
import android.view.KeyEvent;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;
import hun.mesh.carwithenhance.BuildConfig;
import hun.mesh.carwithenhance.config.HookConfigs;
import hun.mesh.carwithenhance.dexkit.CarWithDexKitManager;
import hun.mesh.carwithenhance.dexkit.GenericAutoPlayHooks;
import hun.mesh.carwithenhance.media.CarWithMediaCapabilities;
import hun.mesh.carwithenhance.policy.ExperienceAppPolicy;
import hun.mesh.carwithenhance.policy.MediaPlaybackPolicy;
import hun.mesh.carwithenhance.policy.MediaSnapshot;
import hun.mesh.carwithenhance.policy.MediaSnapshotPolicy;
import hun.mesh.carwithenhance.policy.ResumeReadinessPolicy;
import hun.mesh.carwithenhance.utils.AppUtils;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

/* JADX INFO: loaded from: classes2.dex */
public class AutoPlayAllHook implements IHook {
    private static final String APK_LOAD_UTIL = "p6.e";
    private static final String APPS_VIEW_MODEL = "com.carwith.launcher.apps.AppsViewModel";
    private static final String MEDIA_OPEN_APP_HELPER = "e5.s";
    private static final String BROWSE_ACTION = "android.media.browse.MediaBrowserService";
    private static final String CARWITH_APP_MGR = "com.carwith.common.utils.c0";
    private static final String EXPERIENCE_PREF = "ucar_experience_app_added_category";
    private static final String KEY_CARWITH_AUTOPLAY = "prefer_auto_play";
    private static final String PREF_LAST_MEDIA_APP = "last_media_app";
    private static final String KEY_EXPERIENCE_TO_NORMAL = "hook_experience_to_normal_enabled";
    private static final String KEY_EXP_FLAGGED = "carwithenhance_exp_flagged";
    private static final String KEY_KILL_IMMEDIATELY = "hook_disconnect_kill_immediately_enabled";
    private static final String KEY_PAUSE_BEFORE_DISCONNECT = "hook_pause_before_disconnect_enabled";
    private static final String KEY_SKIP_USER_KILLED = "hook_skip_user_killed_enabled";
    private static final long KILL_SESSION_WAIT_MS = 1600;
    private static final Map<String, String> KNOWN_SERVICES;
    private static final String LOG_TAG = "carwithenhance";
    private static final String MEDIA_PLAY_HELPER = "ja.a";
    private static final String[] NEVER_KILL;
    private static final long PAUSE_MAX_WAIT_MS = 1600;
    private static final long PAUSE_POLL_MS = 100;
    private static final long DUPLICATE_RESUME_WINDOW_MS = 600L;
    private static final long PANEL_READY_MAX_WAIT_MS = 6000L;
    private static final long ONE_KEY_CLEAN_MAX_AGE_MS = 5 * 60 * 1000L;
    private static final Map<String, String> PLAYBACK_SERVICES;
    private static final String PREFS = "carwithenhance_hooksettings";
    private static final String PREF_MEDIA_APP = "file_prefer_app_091703";
    private static final String QQ_MUSIC = "com.tencent.qqmusic";
    private static final int SRC_CARD = 2;
    private static final int SRC_UNKNOWN = 3;
    private static final int SRC_USER = 1;
    private static final long STOP_MAX_WAIT_MS = 1600;
    private static final long SWITCH_DROP_TTL_MS = 240000;
    private static final String TAG = ">> [全应用续播] ";
    private static String sActiveWokePkg;
    private static Context sAppCtx;
    private static volatile Object sAppsVm;
    private static volatile boolean sCarConnected;
    private static ClassLoader sCl;
    private static volatile long sConnectGen;
    private static String sCurrentMediaPkg;
    private static Handler sHandler;
    private static boolean sInitialResumeDone;
    private static String sLastAcceptedPkg;
    private static String sLastPkg;
    private static long sLastResumeAt;
    private static String sLastResumePkg;
    private static volatile long sLastSelectAt;
    private static volatile String sLastSelectPkg;
    private static volatile long sLastStateAt;
    private static volatile String sLastStatePkg;
    private static long sLastWake;
    private static SharedPreferences.OnSharedPreferenceChangeListener sOwnPrefListener;
    private static SharedPreferences.OnSharedPreferenceChangeListener sPrefListener;
    private static volatile boolean sResumeInFlight;
    private static volatile boolean sSuppressAutoResumeForConnection;
    private static volatile String sResumedPkg;
    private static volatile long sRetryGen;
    private static final Set<Runnable> sPendingRetryTasks = ConcurrentHashMap.newKeySet();
    private static volatile String sSelectedApp;
    private static volatile String sPlayingPkg;
    private static volatile String sConfirmedPlayingPkg;
    private static volatile Set<String> sExperiencePackages = Collections.emptySet();
    private static final Map<String, MediaSnapshot> PANEL_SNAPSHOTS = new ConcurrentHashMap();
    private static final Map<String, MediaController.Callback> SESSION_CALLBACKS = new ConcurrentHashMap();
    private static final Map<String, MediaController> SESSION_CONTROLLERS = new ConcurrentHashMap();
    private static MediaSessionManager.OnActiveSessionsChangedListener sSessionListener;
    private static ExecutorService sSwitchExec;
    private static volatile long sWaitStartAt;
    private static volatile String sWaitingPkg;
    private static final String[] FALLBACK = {"com.netease.cloudmusic", "com.luna.music", "com.kugou.android", "cmccwm.mobilemusic", "cn.kuwo.player", "com.miui.player"};
    private static final Set<String> EXPERIENCE_EXCLUDE = ExperienceAppPolicy.knownMapPackages();
    private static final Map<String, MediaBrowser> BROWSERS = new ConcurrentHashMap();
    private static final Map<String, Long> ATTEMPTS = new ConcurrentHashMap();
    private static final Map<String, Long> CAR_OPEN_AT = new ConcurrentHashMap();
    private static final Set<String> USER_KILLED = ConcurrentHashMap.newKeySet();
    private static final Map<String, Long> SWITCH_DROPPED = new ConcurrentHashMap();
    private static final Map<String, Long> OWN_KILL_AT = new ConcurrentHashMap();
    private static final Set<String> WOKE = ConcurrentHashMap.newKeySet();
    private static final Set<String> SESSION_MEDIA = ConcurrentHashMap.newKeySet();
    private static final Set<String> CANNOT_WAKE = ConcurrentHashMap.newKeySet();
    private static final Set<String> SERVICE_START_UNAVAILABLE = ConcurrentHashMap.newKeySet();
    private static final long LOG_MAX_BYTES = 512 * 1024;
    private static final Object LOG_LOCK = new Object();
    private static final ExecutorService LOG_EXECUTOR = Executors.newSingleThreadExecutor(runnable -> {
        Thread thread = new Thread(runnable, "CarWithEnhance-Log");
        thread.setDaemon(true);
        return thread;
    });
    private static final long KILL_MIN_SETTLE_MS = 800;
    private static final long KILL_GRACE_MAX_WAIT_MS = 1200;
    private static final long[] RETRY_DELAYS = {250, 600, KILL_GRACE_MAX_WAIT_MS, 1800, 2500, 3200, 4000, 5000, 6000, 8000, 10000, 13000, 16000, 20000, 24000};
    private static final long[] PANEL_RECONCILE_DELAYS = {250, 600, 1200, 2500, 5000};

    /* JADX WARN: Unreachable blocks removed: 1, instructions: 1 */
    private static boolean isForeground(Context context, String str) {
        List<ActivityManager.RunningAppProcessInfo> runningAppProcesses = ((ActivityManager) context.getSystemService("activity")).getRunningAppProcesses();
        if (runningAppProcesses == null) {
            return false;
        }
        for (ActivityManager.RunningAppProcessInfo runningAppProcessInfo : runningAppProcesses) {
            if (runningAppProcessInfo != null && runningAppProcessInfo.importance == 100 && str.equals(runningAppProcessInfo.processName)) {
                return true;
            }
        }
        return false;
    }

    private static boolean shouldSkipDisconnectKill(Context context, String str) {
        return isForeground(context, str) || isPlaying(context, str);
    }

    static /* synthetic */ void access$1308() {
        cancelRetryTasks();
    }

    private static void cancelRetryTasks() {
        sRetryGen = sRetryGen + 1;
        List<Runnable> pending;
        synchronized (sPendingRetryTasks) {
            pending = new ArrayList<>(sPendingRetryTasks);
            sPendingRetryTasks.clear();
        }
        Handler handler = sHandler;
        if (handler != null) {
            for (Runnable task : pending) {
                handler.removeCallbacks(task);
            }
        }
        sWaitingPkg = null;
    }

    static {
        HashMap map = new HashMap();
        PLAYBACK_SERVICES = map;
        NEVER_KILL = new String[]{"com.autonavi.minimap", "com.baidu.BaiduMap", "com.tencent.map", "com.google.android.apps.maps", "com.miui.carlink", BuildConfig.APPLICATION_ID};
        HashMap map2 = new HashMap();
        KNOWN_SERVICES = map2;
        map2.put("com.luna.music", "com.luna.biz.playing.player.PlayerService");
        map2.put("com.netease.cloudmusic", "com.netease.cloudmusic.module.ucar.UCarService");
        map2.put("com.baidu.netdisk", "com.baidu.netdisk.service.NetdiskMediaBrowserService");
        map.put("com.miui.player", "com.miui.player.service.MediaPlaybackService");
        map.put("com.luna.music", "com.luna.biz.playing.player.PlayerService");
    }

    public static void setAppContext(Context context) {
        if (context != null) {
            sAppCtx = context.getApplicationContext();
        }
    }

    public static void log(Context context, String str) {
        Log.i(LOG_TAG, TAG + str);
        writeFile(context, str, null);
    }

    public static void logErr(Context context, String str, Throwable th) {
        Log.e(LOG_TAG, TAG + str, th);
        writeFile(context, str + " : " + th, null);
    }

    private static void writeFile(Context context, String str, Throwable th) {
        final Context appContext = context == null ? sAppCtx : context.getApplicationContext();
        if (appContext == null || str == null) {
            return;
        }
        final String message = str;
        LOG_EXECUTOR.execute(() -> {
            synchronized (LOG_LOCK) {
                try {
                    File file = new File(appContext.getFilesDir(), "wake.log");
                    if (file.length() > LOG_MAX_BYTES) {
                        File backup = new File(appContext.getFilesDir(), "wake.log.1");
                        if (backup.exists() && !backup.delete()) {
                            // A failed rotation should not prevent new diagnostics.
                        }
                        if (!file.renameTo(backup)) {
                            // Continue appending to the original file if rotation fails.
                        }
                    }
                    try (BufferedWriter writer = new BufferedWriter(new FileWriter(file, true))) {
                        writer.write(new SimpleDateFormat("MM-dd HH:mm:ss", Locale.US).format(new Date()) + " " + message);
                        writer.newLine();
                    }
                } catch (Throwable ignored) {
                }
            }
        });
    }
    @Override // hun.mesh.carwithenhance.hook.IHook
    public void onHook(final ClassLoader classLoader) {
        sCl = classLoader;
        try {
            Object[] objArrResolveMediaAutoPlay = GenericAutoPlayHooks.resolveMediaAutoPlay(classLoader);
            XposedBridge.hookAllMethods((Class) objArrResolveMediaAutoPlay[0], (String) objArrResolveMediaAutoPlay[1], new XC_MethodHook() { // from class: hun.mesh.carwithenhance.hook.AutoPlayAllHook.1
                protected void beforeHookedMethod(XC_MethodHook.MethodHookParam methodHookParam) {
                    try {
                        Object obj = methodHookParam.args != null && methodHookParam.args.length > 0 ? methodHookParam.args[0] : null;
                        Context context = AutoPlayAllHook.sAppCtx;
                        if (obj instanceof String && context != null) {
                            String pkg = (String) obj;
                            boolean autoResume = methodHookParam.args.length > 1
                                    && Boolean.TRUE.equals(methodHookParam.args[1]);
                            if (autoResume) {
                                String lastLaunched = AutoPlayAllHook.readLastUsedMediaApp();
                                if (lastLaunched != null && !lastLaunched.isEmpty()
                                        && !lastLaunched.equals(pkg)) {
                                    AutoPlayAllHook.log(context,
                                            "官方续播目标改为最后启动的播放器: " + pkg + " -> " + lastLaunched);
                                    methodHookParam.args[0] = lastLaunched;
                                    pkg = lastLaunched;
                                }
                            }
                            if (MediaPlaybackPolicy.shouldSuppressPlayback(
                                    AutoPlayAllHook.isPlaying(context, pkg),
                                    AutoPlayAllHook.playingOtherMedia(context, pkg))) {
                                AutoPlayAllHook.log(context, "其他音乐正在播放，拦截自动播放: " + pkg);
                                methodHookParam.setResult(null);
                            }
                        }
                    } catch (Throwable ignored) {
                    }
                }

                protected void afterHookedMethod(XC_MethodHook.MethodHookParam methodHookParam) {
                    try {
                        Object[] objArr = methodHookParam.args;
                        if (objArr != null && objArr.length != 0) {
                            Object obj = objArr[0];
                            if (!(obj instanceof String) || AutoPlayAllHook.sResumeInFlight) {
                                return;
                            }
                            AutoPlayAllHook.handle((String) obj, classLoader);
                        }
                    } catch (Throwable th) {
                        AutoPlayAllHook.logErr(null, "处理失败", th);
                    }
                }
            });
            log(null, "Hook 注入成功");
        } catch (Throwable th) {
            logErr(null, "Hook 注入失败", th);
        }
        hookCastState(classLoader);
        hookMediaAppOpen(classLoader);
        hookDockIconPreservation(classLoader);
        hookMediaAppPref(classLoader);
        hookSessionAvailability(classLoader);
        hookExperienceApps(classLoader);
        hookOwnPrefs(classLoader);
    }

    private static void hookExperienceApps(final ClassLoader classLoader) {
        try {
            Context appContext = sAppCtx;
            if (appContext == null) {
                appContext = AppUtils.getAppContext(classLoader);
            }
            if (appContext != null && "com.miui.carlink".equals(appContext.getPackageName())) {
                Object[] objArrResolveExperienceToNormal = GenericAutoPlayHooks.resolveExperienceToNormal(classLoader);
                XposedBridge.hookAllMethods((Class) objArrResolveExperienceToNormal[0], (String) objArrResolveExperienceToNormal[1], new XC_MethodHook() { // from class: hun.mesh.carwithenhance.hook.AutoPlayAllHook.6
                    protected void beforeHookedMethod(XC_MethodHook.MethodHookParam methodHookParam) {
                        try {
                            Object unused = AutoPlayAllHook.sAppsVm = methodHookParam.thisObject;
                            Context appContext2 = AutoPlayAllHook.sAppCtx;
                            if (appContext2 == null) {
                                appContext2 = AppUtils.getAppContext(classLoader);
                                AutoPlayAllHook.setAppContext(appContext2);
                            }
                            AutoPlayAllHook.applyExperienceToNormal(appContext2, classLoader, AutoPlayAllHook.isPrefEnabled(appContext2, AutoPlayAllHook.KEY_EXPERIENCE_TO_NORMAL, true));
                        } catch (Throwable th) {
                        }
                    }
                });
                log(null, "体验应用转普通列表的钩子已注册");
                try {
                    XposedBridge.hookAllMethods(
                            XposedHelpers.findClass("com.carwith.common.utils.d0", classLoader),
                            "U", new XC_MethodHook() {
                                @Override
                                protected void afterHookedMethod(MethodHookParam param) {
                                    try {
                                        Object result = param.getResult();
                                        if (result instanceof List) {
                                            ((List) result).removeAll(EXPERIENCE_EXCLUDE);
                                        }
                                    } catch (Throwable ignored) {
                                    }
                                }
                            });
                    log(null, "体验应用列表过滤已注册（排除地图/官方应用）");
                } catch (Throwable th) {
                    logErr(null, "体验应用列表过滤注册失败", th);
                }
                hookExperienceOrdering(classLoader);
            }
        } catch (Throwable th) {
            logErr(null, "体验应用转普通列表的钩子注册失败", th);
        }
    }

    private static void hookExperienceOrdering(final ClassLoader classLoader) {
        try {
            XposedBridge.hookAllMethods(
                    XposedHelpers.findClass(APPS_VIEW_MODEL, classLoader), "y", new XC_MethodHook() {
                        @Override
                        protected void afterHookedMethod(MethodHookParam param) {
                            try {
                                Context context = sAppCtx;
                                if (context == null || !isPrefEnabled(
                                        context, KEY_EXPERIENCE_TO_NORMAL, true)
                                        || param.args == null || param.args.length == 0
                                        || !(param.args[0] instanceof List)) {
                                    return;
                                }
                                Set<String> experience = sExperiencePackages;
                                if (experience.isEmpty()) {
                                    experience = new HashSet<>(experiencePackages(classLoader));
                                    sExperiencePackages = Collections.unmodifiableSet(experience);
                                }
                                ExperienceAppPolicy.moveExperienceToEnd(
                                        (List) param.args[0], experience,
                                        app -> (String) XposedHelpers.getObjectField(app, "packageName"));
                            } catch (Throwable th) {
                                logErr(null, "体验应用末尾排序失败", th);
                            }
                        }
                    });
            log(null, "体验应用末尾排序钩子已注册");
        } catch (Throwable th) {
            logErr(null, "体验应用末尾排序钩子注册失败", th);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static List<String> experiencePackages(ClassLoader classLoader) {
        LinkedHashSet linkedHashSet = new LinkedHashSet();
        try {
            Object manager = XposedHelpers.callStaticMethod(
                    XposedHelpers.findClass("com.carwith.common.utils.d0", classLoader), "R", new Object[0]);
            if (manager != null) {
                int workMode = -1;
                try {
                    Object helper = XposedHelpers.callStaticMethod(
                            XposedHelpers.findClass("com.carwith.common.utils.i3", classLoader),
                            "e", new Object[0]);
                    workMode = ((Integer) XposedHelpers.callMethod(
                            helper, "d", new Object[0])).intValue();
                } catch (Throwable th) {
                    logErr(null, "读取当前工作模式失败", th);
                }
                if (workMode >= 0) {
                    addAllStrings(linkedHashSet,
                            XposedHelpers.callMethod(manager, "U", new Object[]{Integer.valueOf(workMode)}));
                }
                if (linkedHashSet.isEmpty()) {
                    int[] fallbackModes = {0, 1, 4, 8, 256, 512};
                    for (int mode : fallbackModes) {
                        addAllStrings(linkedHashSet,
                                XposedHelpers.callMethod(manager, "U", new Object[]{Integer.valueOf(mode)}));
                    }
                }
            }
        } catch (Throwable th) {
            logErr(null, "读取体验应用名单失败", th);
        }
        linkedHashSet.removeAll(officialPackages(classLoader));
        for (String excluded : EXPERIENCE_EXCLUDE) {
            linkedHashSet.remove(excluded);
        }
        return new ArrayList<>(linkedHashSet);
    }

    private static Set<String> officialPackages(ClassLoader classLoader) {
        HashSet<String> result = new HashSet<>();
        try {
            Object manager = XposedHelpers.callStaticMethod(
                    XposedHelpers.findClass("com.carwith.common.utils.d0", classLoader), "R", new Object[0]);
            Object list = XposedHelpers.callMethod(manager, "P", new Object[0]);
            if (list instanceof List) {
                for (Object item : (List) list) {
                    if (item == null) {
                        continue;
                    }
                    int mode = ((Integer) XposedHelpers.callMethod(
                            item, "getAppMode", new Object[0])).intValue();
                    if (mode == 4 || mode == 5 || mode == 6 || mode == 7) {
                        continue;
                    }
                    String pkg = (String) XposedHelpers.callMethod(
                            item, "getPackageName", new Object[0]);
                    if (pkg != null && pkg.length() > 0) {
                        result.add(pkg);
                    }
                }
            }
        } catch (Throwable th) {
            logErr(null, "读取官方适配应用失败", th);
        }
        return result;
    }

    private static void hookOwnPrefs(final ClassLoader classLoader) {
        try {
            Context appContext = sAppCtx;
            if (appContext == null) {
                appContext = AppUtils.getAppContext(classLoader);
            }
            if (appContext != null && "com.miui.carlink".equals(appContext.getPackageName())) {
                setAppContext(appContext);
                SharedPreferences sharedPreferences = appContext.getSharedPreferences("carwithenhance_hooksettings", 0);
                sharedPreferences.edit()
                        .remove("hook_coldstart_antikill_enabled")
                        .remove("hook_carwith_keepalive_enabled")
                        .apply();
                SharedPreferences.OnSharedPreferenceChangeListener onSharedPreferenceChangeListener = new SharedPreferences.OnSharedPreferenceChangeListener() { // from class: hun.mesh.carwithenhance.hook.AutoPlayAllHook.7
                    @Override // android.content.SharedPreferences.OnSharedPreferenceChangeListener
                    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences2, String str) {
                        if (!AutoPlayAllHook.KEY_EXPERIENCE_TO_NORMAL.equals(str)) {
                            return;
                        }
                        try {
                            boolean z = sharedPreferences2.getBoolean(str, true);
                            AutoPlayAllHook.applyExperienceToNormal(AutoPlayAllHook.sAppCtx, classLoader, z);
                            AutoPlayAllHook.reloadAppList();
                            AutoPlayAllHook.log(AutoPlayAllHook.sAppCtx, "开关已切换，车机应用列表重新构建（体验应用" + (z ? "并入列表" : "还原为专区") + "）");
                        } catch (Throwable th) {
                        }
                    }
                };
                sOwnPrefListener = onSharedPreferenceChangeListener;
                sharedPreferences.registerOnSharedPreferenceChangeListener(onSharedPreferenceChangeListener);
                log(null, "模块开关监听已注册");
            }
        } catch (Throwable th) {
            logErr(null, "模块开关监听注册失败", th);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void reloadAppList() {
        Object obj = sAppsVm;
        if (obj == null) {
            return;
        }
        try {
            XposedHelpers.callMethod(obj, "i", new Object[]{null});
            log(sAppCtx, "已请求重新构建车机应用列表");
        } catch (Throwable th) {
            logErr(sAppCtx, "重建车机应用列表失败", th);
        }
    }

    private static void addAllStrings(Set<String> set, Object obj) {
        if (!(obj instanceof List) || set == null) {
            return;
        }
        for (Object obj2 : (List) obj) {
            if (obj2 instanceof String) {
                String str = (String) obj2;
                if (str.length() > 0) {
                    set.add(str);
                }
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Code duplicated, block: B:26:0x0065 A[Catch: all -> 0x00cb, TryCatch #0 {all -> 0x00cb, blocks: (B:6:0x0009, B:9:0x0014, B:10:0x0038, B:12:0x003f, B:14:0x0047, B:16:0x004d, B:19:0x0055, B:26:0x0065, B:27:0x0069, B:29:0x006e, B:32:0x0075, B:22:0x005c, B:34:0x007a, B:36:0x0080, B:38:0x0086, B:40:0x0098, B:44:0x00a7), top: B:50:0x0009 }] */
    public static int applyExperienceToNormal(Context context, ClassLoader classLoader, boolean enabled) {
        if (context == null || classLoader == null) {
            return 0;
        }
        try {
            Set<String> desired = enabled
                    ? new HashSet<>(experiencePackages(classLoader))
                    : Collections.emptySet();
            SharedPreferences sortPrefs = context.getSharedPreferences("ucar_settings_added_app_sort", 0);
            SharedPreferences experiencePrefs = context.getSharedPreferences(EXPERIENCE_PREF, 0);
            SharedPreferences ownPrefs = context.getSharedPreferences("carwithenhance_hooksettings", 0);
            Set<String> flagged = new HashSet<>(ownPrefs.getStringSet(KEY_EXP_FLAGGED, new HashSet()));
            Set<String> known = new HashSet<>(flagged);
            known.addAll(desired);
            known.addAll(EXPERIENCE_EXCLUDE);

            SharedPreferences.Editor experienceEditor = null;
            SharedPreferences.Editor sortEditor = null;
            int touched = 0;
            for (String pkg : known) {
                if (pkg == null || pkg.isEmpty()) {
                    continue;
                }
                boolean packageChanged = false;
                boolean shouldEnable = enabled && desired.contains(pkg);
                if (experiencePrefs.getBoolean(pkg, false) != shouldEnable) {
                    if (experienceEditor == null) {
                        experienceEditor = experiencePrefs.edit();
                    }
                    experienceEditor.putBoolean(pkg, shouldEnable);
                    packageChanged = true;
                }
                if (sortPrefs.contains(pkg)) {
                    if (sortEditor == null) {
                        sortEditor = sortPrefs.edit();
                    }
                    sortEditor.remove(pkg);
                    packageChanged = true;
                }
                if (packageChanged) {
                    touched++;
                }
            }

            if (experienceEditor != null) {
                experienceEditor.apply();
            }
            if (sortEditor != null) {
                sortEditor.apply();
            }
            if (!flagged.equals(desired)) {
                ownPrefs.edit().putStringSet(KEY_EXP_FLAGGED, new HashSet<>(desired)).apply();
            }
            sExperiencePackages = enabled
                    ? Collections.unmodifiableSet(new HashSet<>(desired))
                    : Collections.emptySet();
            log(context, (enabled ? "体验应用已放到普通列表末尾: " : "已还原体验应用: ")
                    + desired.size() + " 个，本次清理旧标记 " + touched + " 个");
            return desired.size();
        } catch (Throwable th) {
            logErr(context, "处理体验应用失败", th);
            return 0;
        }
    }

    private static void hookSessionAvailability(final ClassLoader classLoader) {
        MediaSessionManager mediaSessionManager;
        try {
            Context context = sAppCtx;
            if (context == null || (mediaSessionManager = (MediaSessionManager) context.getSystemService("media_session")) == null) {
                return;
            }
            MediaSessionManager.OnActiveSessionsChangedListener onActiveSessionsChangedListener = new MediaSessionManager.OnActiveSessionsChangedListener() { // from class: hun.mesh.carwithenhance.hook.AutoPlayAllHook.8
                @Override // android.media.session.MediaSessionManager.OnActiveSessionsChangedListener
                public void onActiveSessionsChanged(List<MediaController> list) {
                    String str;
                    try {
                        Context context2 = AutoPlayAllHook.sAppCtx;
                        AutoPlayAllHook.registerSessionCallbacks(list);
                        if (context2 != null && AutoPlayAllHook.sPlayingPkg == null) {
                            AutoPlayAllHook.sPlayingPkg = AutoPlayAllHook.findPlayingPackage(context2, null);
                        }
                        if (context2 != null && !AutoPlayAllHook.blockedBySwitchDrop(AutoPlayAllHook.sResumedPkg) && AutoPlayAllHook.blockedByUserKill(context2, AutoPlayAllHook.sResumedPkg) && (str = AutoPlayAllHook.sResumedPkg) != null && str.equals(AutoPlayAllHook.sWaitingPkg)) {
                            AutoPlayAllHook.access$1308();
                            String unused = AutoPlayAllHook.sWaitingPkg = null;
                        }
                        String str2 = AutoPlayAllHook.sWaitingPkg;
                        if (AutoPlayAllHook.sSuppressAutoResumeForConnection) {
                            AutoPlayAllHook.cancelRetryTasks();
                            return;
                        }
                        if (str2 != null && list != null) {
                            for (MediaController mediaController : list) {
                                if (mediaController != null && str2.equals(mediaController.getPackageName())) {
                                    long jElapsedRealtime = SystemClock.elapsedRealtime();
                                    if (str2.equals(AutoPlayAllHook.sLastResumePkg) && jElapsedRealtime - AutoPlayAllHook.sLastResumeAt < 1500) {
                                        return;
                                    }
                                    PlaybackState playbackState = mediaController.getPlaybackState();
                                    boolean targetPlaying = playbackState != null
                                            && playbackState.getState() == PlaybackState.STATE_PLAYING;
                                    boolean sessionReady = playbackState == null
                                            || playbackState.getState() != PlaybackState.STATE_NONE;
                                    boolean autoPlayEnabled = AutoPlayAllHook.readPrefAutoPlay();
                                    if (sessionReady
                                            && ResumeReadinessPolicy.canSendOfficialResume(
                                                    autoPlayEnabled,
                                                    AutoPlayAllHook.isProcessRunning(context2, str2),
                                                    true,
                                                    targetPlaying,
                                                    AutoPlayAllHook.playingOtherMedia(context2, str2))) {
                                        if (AutoPlayAllHook.resumeAfterPanelIfReady(classLoader, str2)) {
                                            AutoPlayAllHook.log(AutoPlayAllHook.sAppCtx, "监听到 " + str2 + " 会话已出现，面板就绪，立即续播");
                                        } else {
                                            AutoPlayAllHook.log(AutoPlayAllHook.sAppCtx, "监听到 " + str2 + " 会话已出现，等待面板元数据");
                                        }
                                    } else if (targetPlaying || !autoPlayEnabled) {
                                        AutoPlayAllHook.cancelRetryTasks();
                                    }
                                    return;
                                }
                            }
                        }
                    } catch (Throwable th) {
                    }
                }
            };
            sSessionListener = onActiveSessionsChangedListener;
            mediaSessionManager.addOnActiveSessionsChangedListener(onActiveSessionsChangedListener, null);
            registerSessionCallbacks(mediaSessionManager.getActiveSessions(null));
            String currentPlaying = findPlayingPackage(context, null);
            if (currentPlaying != null) {
                sPlayingPkg = currentPlaying;
                sCurrentMediaPkg = currentPlaying;
            }
            log(null, "媒体会话监听已注册");
        } catch (Throwable th) {
            logErr(null, "媒体会话监听注册失败", th);
        }
    }

    private static void registerSessionCallbacks(List<MediaController> sessions) {
        if (sessions == null) {
            return;
        }
        Set<String> activeKeys = new HashSet<>();
        for (MediaController controller : sessions) {
            String key = callbackKey(controller);
            if (key != null) {
                activeKeys.add(key);
            }
        }
        unregisterStaleSessionCallbacks(activeKeys);
        Handler handler = new Handler(Looper.getMainLooper());
        for (MediaController controller : sessions) {
            if (controller == null) {
                continue;
            }
            String pkg = controller.getPackageName();
            if (pkg == null) {
                continue;
            }
            String callbackKey = callbackKey(controller);
            if (callbackKey == null) {
                continue;
            }
            if (SESSION_CALLBACKS.containsKey(callbackKey)) {
                continue;
            }
            MediaController.Callback callback = new MediaController.Callback() {
                @Override
                public void onPlaybackStateChanged(PlaybackState state) {
                    if (state != null && state.getState() == PlaybackState.STATE_PLAYING) {
                        onMediaActuallyPlaying(pkg, controller);
                    }
                }

                @Override
                public void onMetadataChanged(MediaMetadata metadata) {
                    if (metadata != null) {
                        onMediaMetadataChanged(pkg, controller);
                    }
                }
            };
            try {
                controller.registerCallback(callback, handler);
                SESSION_CALLBACKS.put(callbackKey, callback);
                SESSION_CONTROLLERS.put(callbackKey, controller);
                PlaybackState currentState = controller.getPlaybackState();
                if (currentState != null && currentState.getState() == PlaybackState.STATE_PLAYING) {
                    onMediaActuallyPlaying(pkg, controller);
                }
            } catch (Throwable ignored) {
            }
        }
    }

    private static String findPlayingPackage(Context context, String exclude) {
        try {
            MediaSessionManager manager =
                    (MediaSessionManager) context.getSystemService("media_session");
            if (manager == null) {
                return null;
            }
            List<MediaController> sessions = manager.getActiveSessions(null);
            if (sessions == null) {
                return null;
            }
            for (MediaController controller : sessions) {
                if (controller == null) {
                    continue;
                }
                String pkg = controller.getPackageName();
                if (pkg == null || pkg.equals(exclude)) {
                    continue;
                }
                PlaybackState state = controller.getPlaybackState();
                if (state != null && state.getState() == PlaybackState.STATE_PLAYING) {
                    return pkg;
                }
            }
        } catch (Throwable ignored) {
        }
        return null;
    }

    private static void onMediaActuallyPlaying(String pkg, MediaController controller) {
        Context context = sAppCtx;
        if (context == null || pkg == null || !isMediaApp(sCl, pkg) || isNeverKill(pkg)) {
            return;
        }
        if (pkg.equals(sPlayingPkg)) {
            if (sCarConnected) {
                sConfirmedPlayingPkg = pkg;
                refreshCarWithMediaState(context, pkg, controller);
            }
            return;
        }
        saveLastOpenedMediaApp(context, pkg);
        final String previous = MediaPlaybackPolicy.previousPackageToStop(sPlayingPkg, pkg, null);
        sPlayingPkg = pkg;
        sCurrentMediaPkg = pkg;
        sConfirmedPlayingPkg = pkg;
        if (!sCarConnected) {
            return;
        }
        sSelectedApp = pkg;
        rememberUsedMediaApp(sCl, pkg);
        markSessionMedia(pkg);
        refreshCarWithMediaState(context, pkg, controller);
        updateDockIconToPlaying(pkg);
        if (previous != null && isMediaApp(sCl, previous) && !isNeverKill(previous)) {
            boolean switchKillEnabled = isPrefEnabled(
                    context,
                    HookConfigs.SWITCH_PLAYER_KILL_PREVIOUS.key,
                    HookConfigs.SWITCH_PLAYER_KILL_PREVIOUS.defaultValue);
            if (MediaPlaybackPolicy.shouldKillPreviousAfterSwitch(
                    switchKillEnabled,
                    stateOf(controller) == PlaybackState.STATE_PLAYING,
                    previous,
                    pkg)) {
                log(context, "检测到 " + pkg + " 真正开始播放，结束上一个播放器: " + previous);
                runOffMain(new Runnable() {
                    @Override
                    public void run() {
                        killPreviousPlaying(context, previous, pkg);
                    }
                });
            } else {
                log(context, "切换播放器结束旧进程开关关闭，保留上一个播放器: " + previous);
                WOKE.remove(previous);
                markSwitchDropped(context, previous, pkg);
            }
        }
    }

    private static void onMediaMetadataChanged(String pkg, MediaController controller) {
        Context context = sAppCtx;
        if (context == null || pkg == null || !sCarConnected
                || !isMediaApp(sCl, pkg) || isNeverKill(pkg)) {
            return;
        }
        PlaybackState state = controller.getPlaybackState();
        if (state != null && state.getState() == PlaybackState.STATE_PLAYING) {
            refreshCarWithMediaState(context, pkg, controller);
        }
    }

    public static boolean isCarConnected() {
        return sCarConnected;
    }

    public static Context getAppContext() {
        return sAppCtx;
    }

    public static boolean isCurrentMediaTarget(String pkg) {
        return pkg != null && sCarConnected
                && (pkg.equals(sCurrentMediaPkg)
                || pkg.equals(sPlayingPkg)
                || pkg.equals(sConfirmedPlayingPkg));
    }

    public static boolean isMediaSessionReady(String pkg) {
        Context context = sAppCtx;
        if (context == null || pkg == null) {
            return false;
        }
        MediaController controller = findController(context, pkg);
        if (controller == null || controller.getMetadata() == null) {
            return false;
        }
        PlaybackState state = controller.getPlaybackState();
        return state != null && state.getState() != PlaybackState.STATE_NONE;
    }

    private static void refreshCarWithMediaState(
            final Context context, final String pkg, final MediaController controller) {
        if (Looper.myLooper() != Looper.getMainLooper()) {
            if (sHandler == null) {
                sHandler = new Handler(Looper.getMainLooper());
            }
            sHandler.post(new Runnable() {
                @Override
                public void run() {
                    refreshCarWithMediaState(context, pkg, controller);
                }
            });
            return;
        }
        long startedAt = SystemClock.elapsedRealtime();
        String snapshotKey = panelSnapshotKey(pkg, controller);
        MediaSnapshot previous = PANEL_SNAPSHOTS.get(snapshotKey);
        MediaSnapshot next = MediaSnapshotPolicy.coalesce(
                previous, createMediaSnapshot(pkg, controller));
        if (!MediaSnapshotPolicy.shouldRefresh(previous, next)) {
            return;
        }
        if (CarWithMediaCapabilities.INSTANCE.tryRefreshPanel(context, pkg, controller)) {
            PANEL_SNAPSHOTS.put(snapshotKey, next);
            log(context, "媒体面板快照已刷新: " + pkg
                    + " track=" + (next.title.isEmpty() ? "(unknown)" : next.title)
                    + " state=" + next.playbackState
                    + " (" + (SystemClock.elapsedRealtime() - startedAt) + "ms)");
            return;
        }
        Object mediaManager = null;
        try {
            Object sessionManager = XposedHelpers.callStaticMethod(
                    XposedHelpers.findClass("xa.c", sCl), "h", new Object[0]);
            Object compatibilityController = XposedHelpers.callMethod(
                    sessionManager, "p", new Object[]{context, controller});
            mediaManager = XposedHelpers.callStaticMethod(
                    XposedHelpers.findClass("ma.a", sCl), "p", new Object[0]);
            if (compatibilityController != null && mediaManager != null) {
                XposedHelpers.callMethod(mediaManager, "c", new Object[]{compatibilityController});
            }
        } catch (Throwable th) {
            logErr(context, "刷新 CarWith 媒体控制器失败: " + pkg, th);
        }

        try {
            if (mediaManager == null) {
                mediaManager = XposedHelpers.callStaticMethod(
                        XposedHelpers.findClass("ma.a", sCl), "p", new Object[0]);
            }
            if (mediaManager != null) {
                try {
                    XposedHelpers.callMethod(mediaManager, "f", new Object[]{pkg});
                } catch (Throwable ignored) {
                }
                Object current = XposedHelpers.callMethod(mediaManager, "o", new Object[0]);
                if (!pkg.equals(current)) {
                    XposedHelpers.setObjectField(mediaManager, "c", pkg);
                }
            }
        } catch (Throwable th) {
            logErr(context, "切换 CarWith 当前媒体应用失败: " + pkg, th);
        }

        try {
            Object callbackHelper = XposedHelpers.callStaticMethod(
                    XposedHelpers.findClass("pa.b", sCl), "e", new Object[0]);
            if (callbackHelper != null) {
                XposedHelpers.callMethod(callbackHelper, "d", new Object[]{pkg});
                XposedHelpers.callMethod(callbackHelper, "h", new Object[]{pkg});
            }
            PANEL_SNAPSHOTS.put(snapshotKey, next);
            log(context, "播放器已播放，控制面板同步: " + pkg
                    + " (" + (SystemClock.elapsedRealtime() - startedAt) + "ms)");
        } catch (Throwable th) {
            logErr(context, "通知 CarWith 媒体面板失败: " + pkg, th);
        }
    }

    /** 面板身份就绪：目标会话存在且元数据已有歌名/歌手 */
    private static boolean hasPanelIdentity(String pkg) {
        MediaController controller = findController(sAppCtx, pkg);
        if (controller == null) {
            return false;
        }
        MediaMetadata metadata = controller.getMetadata();
        if (metadata == null) {
            return false;
        }
        return firstNonEmpty(
                metadata.getString(MediaMetadata.METADATA_KEY_TITLE),
                metadata.getString(MediaMetadata.METADATA_KEY_ARTIST)).length() > 0;
    }

    private static boolean panelWaitExceeded() {
        return sWaitStartAt > 0
                && SystemClock.elapsedRealtime() - sWaitStartAt >= PANEL_READY_MAX_WAIT_MS;
    }

    /** 面板先行：面板就绪（或超时兜底）后先推面板，再下发续播；返回是否已下发 */
    private static boolean resumeAfterPanelIfReady(ClassLoader classLoader, String pkg) {
        if (!hasPanelIdentity(pkg) && !panelWaitExceeded()) {
            return false;
        }
        MediaController controller = findController(sAppCtx, pkg);
        if (controller != null) {
            refreshCarWithMediaState(sAppCtx, pkg, controller);
        }
        resumeCarWith(classLoader, pkg);
        return true;
    }

    /** 供歌词时钟读取当前播放中的媒体包名 */
    public static String currentPlayingPackageForLyric() {
        if (sConfirmedPlayingPkg != null) {
            return sConfirmedPlayingPkg;
        }
        if (sPlayingPkg != null) {
            return sPlayingPkg;
        }
        return sCurrentMediaPkg;
    }

    /** 外推播放位置（毫秒），无效返回 -1；供歌词时钟做实时推进 */
    public static long extrapolatedPlaybackPosition(String pkg) {
        if (pkg == null) {
            return -1;
        }
        MediaController controller = findController(sAppCtx, pkg);
        if (controller == null) {
            return -1;
        }
        PlaybackState state = controller.getPlaybackState();
        if (state == null) {
            return -1;
        }
        long position = state.getPosition();
        if (position < 0) {
            return -1;
        }
        float speed = state.getPlaybackSpeed();
        if (speed <= 0f) {
            return position;
        }
        long elapsed = SystemClock.elapsedRealtime() - state.getLastPositionUpdateTime();
        return position + (long) (elapsed * speed);
    }

    private static MediaSnapshot createMediaSnapshot(String pkg, MediaController controller) {
        MediaMetadata metadata = controller == null ? null : controller.getMetadata();
        PlaybackState state = controller == null ? null : controller.getPlaybackState();
        String artwork = "";
        if (metadata != null) {
            artwork = firstNonEmpty(
                    metadata.getString(MediaMetadata.METADATA_KEY_ART_URI),
                    metadata.getString(MediaMetadata.METADATA_KEY_ALBUM_ART_URI),
                    metadata.getString(MediaMetadata.METADATA_KEY_DISPLAY_ICON_URI));
        }
        return new MediaSnapshot(
                pkg,
                metadata == null ? "" : metadata.getString(MediaMetadata.METADATA_KEY_MEDIA_ID),
                metadata == null ? "" : metadata.getString(MediaMetadata.METADATA_KEY_TITLE),
                metadata == null ? "" : metadata.getString(MediaMetadata.METADATA_KEY_ARTIST),
                metadata == null ? "" : metadata.getString(MediaMetadata.METADATA_KEY_ALBUM),
                metadata == null ? 0L : metadata.getLong(MediaMetadata.METADATA_KEY_DURATION),
                artwork,
                state == null ? PlaybackState.STATE_NONE : state.getState(),
                state == null ? 0.0f : state.getPlaybackSpeed(),
                state == null ? 0L : state.getActions());
    }

    private static String firstNonEmpty(String... values) {
        if (values != null) {
            for (String value : values) {
                if (value != null && !value.isEmpty()) {
                    return value;
                }
            }
        }
        return "";
    }

    private static String panelSnapshotKey(String pkg, MediaController controller) {
        try {
            return pkg + "#" + controller.getSessionToken().hashCode();
        } catch (Throwable ignored) {
            return pkg;
        }
    }

    private static void killPreviousPlaying(Context context, String previous, String current) {
        try {
            releaseCarwithBinding(previous);
            releaseOwnBrowsers(previous);
            boolean sessionReleased = gracefulStopBeforeKill(context, previous);
            if (!sessionReleased) {
                log(context, "旧播放器会话仍未释放，但菜单开关已开启，执行强制结束: "
                        + previous);
            }
            forceStop(context, previous, "切换到 " + current);
            WOKE.remove(previous);
            markSwitchDropped(context, previous, current);
        } catch (Throwable th) {
            logErr(context, "结束上一个播放器失败: " + previous, th);
        }
    }

    private static void hookDockIconPreservation(final ClassLoader classLoader) {
        try {
            XposedHelpers.findAndHookMethod(APK_LOAD_UTIL, classLoader, "m",
                    new Object[]{Context.class, String.class, new XC_MethodHook() {
                        @Override
                        protected void beforeHookedMethod(MethodHookParam param) {
                            try {
                                Context context = param.args[0] instanceof Context
                                        ? (Context) param.args[0] : sAppCtx;
                                Object value = param.args[1];
                                if (context != null && value instanceof String
                                        && shouldPreserveDockIcon(context, (String) value)) {
                                    log(context, "新播放器尚未播放，保留左侧媒体图标: " + value);
                                    param.setResult(null);
                                }
                            } catch (Throwable ignored) {
                            }
                        }
                    }});
            log(null, "媒体图标替换保护钩子已注册");
        } catch (Throwable th) {
            logErr(null, "媒体图标替换保护钩子注册失败", th);
        }
        try {
            XposedHelpers.findAndHookMethod(MEDIA_OPEN_APP_HELPER, classLoader, "f",
                    new Object[]{String.class, new XC_MethodHook() {
                        @Override
                        protected void beforeHookedMethod(MethodHookParam param) {
                            try {
                                Context context = sAppCtx;
                                Object value = param.args[0];
                                if (context != null && value instanceof String
                                        && shouldPreserveDockIcon(context, (String) value)) {
                                    param.setResult(null);
                                }
                            } catch (Throwable ignored) {
                            }
                        }
                    }});
            log(null, "媒体打开状态保护钩子已注册");
        } catch (Throwable th) {
            logErr(null, "媒体打开状态保护钩子注册失败", th);
        }
    }

    private static void updateDockIconToPlaying(String pkg) {
        Context context = sAppCtx;
        if (context == null || pkg == null || pkg.isEmpty()) {
            return;
        }
        try {
            XposedHelpers.callStaticMethod(
                    XposedHelpers.findClass(APK_LOAD_UTIL, sCl), "m",
                    new Object[]{context, pkg});
        } catch (Throwable th) {
            logErr(context, "更新正在播放的媒体图标失败", th);
        }
        try {
            Object helper = XposedHelpers.callStaticMethod(
                    XposedHelpers.findClass(MEDIA_OPEN_APP_HELPER, sCl), "d", new Object[0]);
            if (helper != null) {
                XposedHelpers.callMethod(helper, "f", new Object[]{pkg});
            }
        } catch (Throwable th) {
            logErr(context, "更新媒体打开状态失败", th);
        }
    }

    private static boolean shouldPreserveDockIcon(Context context, String pkg) {
        return context != null && isMediaApp(sCl, pkg)
                && MediaPlaybackPolicy.shouldPreserveDockIcon(
                        isPlaying(context, pkg), playingOtherMedia(context, pkg));
    }

    private static void hookMediaAppPref(ClassLoader classLoader) {
        try {
            Context appContext = AppUtils.getAppContext(classLoader);
            if (appContext == null) {
                return;
            }
            setAppContext(appContext);
            SharedPreferences sharedPreferences = appContext.getSharedPreferences(PREF_MEDIA_APP, 0);
            SharedPreferences.OnSharedPreferenceChangeListener onSharedPreferenceChangeListener = new SharedPreferences.OnSharedPreferenceChangeListener() { // from class: hun.mesh.carwithenhance.hook.AutoPlayAllHook.9
                @Override // android.content.SharedPreferences.OnSharedPreferenceChangeListener
                public void onSharedPreferenceChanged(SharedPreferences sharedPreferences2, String str) {
                    String string;
                    try {
                        if (("prefer_media_app".equals(str) || "prefer_music_app".equals(str)) && (string = sharedPreferences2.getString(str, null)) != null && string.length() != 0) {
                            int iCallerSource = AutoPlayAllHook.callerSource();
                            AutoPlayAllHook.log(AutoPlayAllHook.sAppCtx, "车机端媒体应用设置变化: " + str + " = " + string + " (" + AutoPlayAllHook.sourceName(iCallerSource) + ")");
                            AutoPlayAllHook.markSessionMedia(string);
                            AutoPlayAllHook.onMediaAppEvent(string, iCallerSource);
                        }
                    } catch (Throwable th) {
                    }
                }
            };
            sPrefListener = onSharedPreferenceChangeListener;
            sharedPreferences.registerOnSharedPreferenceChangeListener(onSharedPreferenceChangeListener);
            log(null, "车机端媒体应用设置监听已注册");
        } catch (Throwable th) {
            logErr(null, "车机端媒体应用设置监听注册失败", th);
        }
    }

    private static void hookMediaAppOpen(final ClassLoader classLoader) {
        try {
            Object[] objArrResolveMediaAppOpen = GenericAutoPlayHooks.resolveMediaAppOpen(classLoader);
            XposedBridge.hookAllMethods((Class) objArrResolveMediaAppOpen[0], (String) objArrResolveMediaAppOpen[1], new XC_MethodHook() { // from class: hun.mesh.carwithenhance.hook.AutoPlayAllHook.10
                protected void afterHookedMethod(XC_MethodHook.MethodHookParam methodHookParam) {
                    try {
                        Object obj = methodHookParam.args[1];
                        if (obj instanceof String) {
                            AutoPlayAllHook.onMediaAppEvent((String) obj, AutoPlayAllHook.callerSource());
                        }
                    } catch (Throwable th) {
                    }
                }
            });
            log(null, "车机端选择媒体应用的钩子已注册");
        } catch (Throwable th) {
            logErr(null, "车机端选择媒体应用的钩子注册失败", th);
        }
        try {
            XposedHelpers.findAndHookMethod("com.carwith.launcher.ams.UCarFragmentManager", classLoader, "getFragmentTagFromPackageName", new Object[]{String.class, new XC_MethodHook() { // from class: hun.mesh.carwithenhance.hook.AutoPlayAllHook.11
                protected void beforeHookedMethod(XC_MethodHook.MethodHookParam methodHookParam) {
                    try {
                        Object obj = methodHookParam.args[0];
                        if (obj instanceof String) {
                            AutoPlayAllHook.preWakeForCarOpen((String) obj, "车机端打开");
                        }
                    } catch (Throwable th2) {
                    }
                }
            }});
            log(null, "车机端打开应用钩子已注册");
        } catch (Throwable th2) {
            logErr(null, "车机端打开应用钩子注册失败", th2);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void preWakeForCarOpen(String str, String str2) {
        if (str == null || str.length() == 0) {
            return;
        }
        try {
            Context context = sAppCtx;
            if (context == null || !isEnabled(context) || !isMediaApp(sCl, str)) {
                return;
            }
            saveLastOpenedMediaApp(context, str);
            if (MediaPlaybackPolicy.shouldSuppressPlayback(
                    isPlaying(context, str), playingOtherMedia(context, str))) {
                log(context, str2 + " " + str + "，其他音乐正在播放，仅记录最后启动应用，不自动播放");
                return;
            }
            if (!blockedByUserKill(context, str) && !blockedBySwitchDrop(str)
                    && !isProcessRunning(context, str)) {
                log(context, str2 + " " + str + "，先把它后台唤醒");
                CAR_OPEN_AT.put(str, Long.valueOf(SystemClock.elapsedRealtime()));
                markSessionMedia(str);
                WOKE.add(str);
                sActiveWokePkg = str;
                sendMediaButton(context, str);
                bindBrowseService(context, str);
            }
        } catch (Throwable th) {
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static boolean recentlyOpenedOnCar(String str) {
        Map<String, Long> map = CAR_OPEN_AT;
        Long l = map.get(str);
        if (l == null) {
            return false;
        }
        if (SystemClock.elapsedRealtime() - l.longValue() > 15000) {
            map.remove(str);
            return false;
        }
        return true;
    }

    private static void rememberUsedMediaApp(ClassLoader classLoader, String str) {
        Object objCallStaticMethod;
        if (classLoader == null || str == null) {
            return;
        }
        try {
            if (str.length() == 0 || !isMediaApp(classLoader, str) || (objCallStaticMethod = XposedHelpers.callStaticMethod(XposedHelpers.findClass("xa.c", classLoader), "h", new Object[0])) == null) {
                return;
            }
            Object objectField = XposedHelpers.getObjectField(objCallStaticMethod, "d");
            if (str.equals(objectField)) {
                return;
            }
            XposedHelpers.setObjectField(objCallStaticMethod, "d", str);
            log(sAppCtx, "同步 CarWith 当前媒体应用: " + objectField + " -> " + str);
        } catch (Throwable th) {
            logErr(sAppCtx, "同步 CarWith 媒体应用失败", th);
        }
    }

    private static void hookCastState(ClassLoader classLoader) {
        try {
            CarWithDexKitManager carWithDexKitManager = CarWithDexKitManager.INSTANCE;
            String castControllerClass = carWithDexKitManager.getCastControllerClass();
            if (castControllerClass != null && !castControllerClass.isEmpty()) {
                Class clsFindClass = XposedHelpers.findClass(castControllerClass, classLoader);
                String castControllerConnectMethod = carWithDexKitManager.getCastControllerConnectMethod();
                if (castControllerConnectMethod != null && !castControllerConnectMethod.isEmpty()) {
                    XposedBridge.hookAllMethods(clsFindClass, castControllerConnectMethod, new XC_MethodHook() { // from class: hun.mesh.carwithenhance.hook.AutoPlayAllHook.13
                        protected void afterHookedMethod(XC_MethodHook.MethodHookParam methodHookParam) {
                            AutoPlayAllHook.onCarConnected();
                        }
                    });
                }
                log(null, "连接状态钩子已注册（仅续播）");
                return;
            }
            log(null, "未找到 CastController，跳过连接状态钩子");
        } catch (Throwable th) {
            logErr(null, "连接状态钩子注册失败", th);
        }
    }

    public static void onCarConnected() {
        if (sCarConnected) {
            log(sAppCtx, "忽略重复的已连接回调");
            return;
        }
        sConnectGen++;
        sSelectedApp = readLastUsedMediaApp();
        SESSION_MEDIA.clear();
        WOKE.clear();
        sActiveWokePkg = null;
        sLastAcceptedPkg = null;
        sCurrentMediaPkg = null;
        sWaitingPkg = null;
        sLastSelectPkg = null;
        cancelRetryTasks();
        USER_KILLED.clear();
        OWN_KILL_AT.clear();
        SWITCH_DROPPED.clear();
        sLastStatePkg = null;
        sResumedPkg = null;
        sInitialResumeDone = false;
        sPlayingPkg = null;
        sConfirmedPlayingPkg = null;
        PANEL_SNAPSHOTS.clear();
        sLastPkg = null;
        sLastWake = 0L;
        sLastResumePkg = null;
        sLastResumeAt = 0L;
        sSuppressAutoResumeForConnection = false;
        SERVICE_START_UNAVAILABLE.clear();
        sCarConnected = true;
        log(null, "CarWith 已连接");
        sSuppressAutoResumeForConnection = consumeOneKeyCleanSuppression(sAppCtx);
        if (sSuppressAutoResumeForConnection) {
            log(sAppCtx, "一键清理后本次连接跳过自动续播");
        }
        if (sAppCtx != null) {
            sPlayingPkg = findPlayingPackage(sAppCtx, null);
            if (sPlayingPkg != null) {
                sConfirmedPlayingPkg = sPlayingPkg;
                MediaController activeController = findController(sAppCtx, sPlayingPkg);
                if (activeController != null) {
                    refreshCarWithMediaState(sAppCtx, sPlayingPkg, activeController);
                }
            }
        }
        scheduleMediaPanelReconcile(sConnectGen);
        logAudioState(sAppCtx, "车机连接时");
        ClassLoader classLoader = sCl;
        Context context = sAppCtx;
        applyExperienceToNormal(context, classLoader, isPrefEnabled(context, KEY_EXPERIENCE_TO_NORMAL, true));
        scheduleExperienceApply(classLoader, 4000L);
        scheduleExperienceApply(classLoader, 12000L);
        preWakeLastUsedApp();
    }

    private static void scheduleMediaPanelReconcile(final long generation) {
        if (sHandler == null) {
            sHandler = new Handler(Looper.getMainLooper());
        }
        for (long delay : PANEL_RECONCILE_DELAYS) {
            sHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    Context context = sAppCtx;
                    if (context == null || !sCarConnected || generation != sConnectGen) {
                        return;
                    }
                    try {
                        MediaSessionManager manager =
                                (MediaSessionManager) context.getSystemService("media_session");
                        List<MediaController> sessions =
                                manager == null ? null : manager.getActiveSessions(null);
                        if (sessions != null && !sessions.isEmpty()) {
                            registerSessionCallbacks(sessions);
                            String playing = findPlayingPackage(context, null);
                            if (playing != null) {
                                sPlayingPkg = playing;
                                MediaController controller = findController(context, playing);
                                if (controller != null) {
                                    refreshCarWithMediaState(context, playing, controller);
                                }
                            }
                        }
                    } catch (Throwable th) {
                        logErr(context, "连接后面板状态校准失败", th);
                    }
                }
            }, delay);
        }
    }

    private static void scheduleExperienceApply(final ClassLoader classLoader, long j) {
        try {
            if (sHandler == null) {
                sHandler = new Handler(Looper.getMainLooper());
            }
            sHandler.postDelayed(new Runnable() { // from class: hun.mesh.carwithenhance.hook.AutoPlayAllHook.14
                @Override // java.lang.Runnable
                public void run() {
                    try {
                        Context context = AutoPlayAllHook.sAppCtx;
                        AutoPlayAllHook.applyExperienceToNormal(context, classLoader, AutoPlayAllHook.isPrefEnabled(context, AutoPlayAllHook.KEY_EXPERIENCE_TO_NORMAL, true));
                    } catch (Throwable th) {
                    }
                }
            }, j);
        } catch (Throwable th) {
        }
    }

    private static boolean consumeOneKeyCleanSuppression(Context context) {
        if (context == null) {
            return false;
        }
        try {
            long cleanAt = Settings.Global.getLong(
                    context.getContentResolver(), HookConfigs.ONE_KEY_CLEAN_AT_KEY, 0L);
            if (cleanAt <= 0L) {
                return false;
            }
            Settings.Global.putLong(
                    context.getContentResolver(), HookConfigs.ONE_KEY_CLEAN_AT_KEY, 0L);
            return ResumeReadinessPolicy.shouldSuppressAfterOneKeyClean(
                    cleanAt, System.currentTimeMillis(), ONE_KEY_CLEAN_MAX_AGE_MS);
        } catch (Throwable th) {
            logErr(context, "读取一键清理抑制标记失败", th);
            return false;
        }
    }

    private static void preWakeLastUsedApp() {
        String lastUsedMediaApp;
        if (sInitialResumeDone) {
            return;
        }
        sInitialResumeDone = true;
        if (sSuppressAutoResumeForConnection) {
            return;
        }
        try {
            Context context = sAppCtx;
            if (context != null && isEnabled(context) && (lastUsedMediaApp = readLastUsedMediaApp()) != null && lastUsedMediaApp.length() != 0 && isMediaApp(sCl, lastUsedMediaApp) && !isNeverKill(lastUsedMediaApp)) {
                boolean otherMediaPlaying = playingOtherMedia(context, lastUsedMediaApp);
                if (otherMediaPlaying) {
                    log(context, "连接时其他音乐正在播放，暂不自动续播 " + lastUsedMediaApp);
                    return;
                }
                boolean autoPlayEnabled = readPrefAutoPlay();
                boolean processRunning = isProcessRunning(context, lastUsedMediaApp);
                boolean hasSession = hasSession(context, lastUsedMediaApp);
                boolean targetPlaying = isPlaying(context, lastUsedMediaApp);
                log(context, "本次连接待续播: " + lastUsedMediaApp + " (CarWith自带续播开关=" + autoPlayEnabled + ")");
                learnCurrentMedia(lastUsedMediaApp);
                if (!processRunning && isMediaApp(sCl, lastUsedMediaApp)) {
                    log(context, "连接即唤醒上次使用的播放器: " + lastUsedMediaApp);
                    CAR_OPEN_AT.put(lastUsedMediaApp, Long.valueOf(SystemClock.elapsedRealtime()));
                    markSessionMedia(lastUsedMediaApp);
                    WOKE.add(lastUsedMediaApp);
                    sActiveWokePkg = lastUsedMediaApp;
                    sendMediaButton(context, lastUsedMediaApp);
                    sWaitStartAt = SystemClock.elapsedRealtime();
                    sWaitingPkg = lastUsedMediaApp;
                    if (ResumeReadinessPolicy.canSendOfficialResume(
                            autoPlayEnabled, processRunning, hasSession, targetPlaying, otherMediaPlaying)) {
                        resumeCarWith(sCl, lastUsedMediaApp);
                    }
                    startPlayerService(context, lastUsedMediaApp);
                    bindBrowseService(context, lastUsedMediaApp);
                    startRetryLoop(sCl, lastUsedMediaApp);
                    return;
                }
                sWaitStartAt = SystemClock.elapsedRealtime();
                sWaitingPkg = lastUsedMediaApp;
                if (ResumeReadinessPolicy.canSendOfficialResume(
                        autoPlayEnabled, processRunning, hasSession, targetPlaying, otherMediaPlaying)) {
                    resumeAfterPanelIfReady(sCl, lastUsedMediaApp);
                } else if (processRunning && !hasSession) {
                    log(context, "播放器进程已就绪但媒体会话尚未出现，等待会话后官方续播 " + lastUsedMediaApp);
                }
                startRetryLoop(sCl, lastUsedMediaApp);
            }
        } catch (Throwable th) {
        }
    }

    private static String readLastUsedMediaApp() {
        String lastLaunched = null;
        Context context = sAppCtx;
        if (context != null) {
            lastLaunched = context.getSharedPreferences(PREFS, 0)
                    .getString(PREF_LAST_MEDIA_APP, null);
        }
        return MediaPlaybackPolicy.chooseLastLaunched(
                lastLaunched,
                carwithLastUsedMediaApp(),
                carwithCurrentMediaApp(),
                selectedApp(),
                pkg -> isMediaApp(sCl, pkg));
    }

    private static void saveLastOpenedMediaApp(Context context, String pkg) {
        if (context == null || pkg == null || pkg.length() == 0 || !isMediaApp(sCl, pkg)) {
            return;
        }
        context.getSharedPreferences(PREFS, 0).edit().putString(PREF_LAST_MEDIA_APP, pkg).apply();
    }

    private static String selectedApp() {
        if (sSelectedApp != null && sSelectedApp.length() > 0 && isMediaApp(sCl, sSelectedApp)) {
            return sSelectedApp;
        }
        return readPrefMediaApp();
    }

    private static String readPrefMediaApp() {
        try {
            return sAppCtx.getSharedPreferences(PREF_MEDIA_APP, 0).getString("prefer_media_app", null);
        } catch (Throwable th) {
            return null;
        }
    }

    private static String readPrefMusicApp() {
        try {
            return sAppCtx.getSharedPreferences(PREF_MEDIA_APP, 0).getString("prefer_music_app", null);
        } catch (Throwable th) {
            return null;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static boolean readPrefAutoPlay() {
        return autoplayEnabled(sAppCtx);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static int callerSource() {
        String className;
        try {
            for (StackTraceElement stackTraceElement : new Throwable().getStackTrace()) {
                if (stackTraceElement != null && (className = stackTraceElement.getClassName()) != null && className.length() != 0) {
                    if (className.startsWith("com.carwith.launcher.card")) {
                        return 2;
                    }
                    if (className.startsWith("com.carwith.launcher.ams") || className.startsWith("com.carwith.launcher.surface") || className.startsWith("com.carwith.launcher.home") || className.startsWith("com.carwith.launcher.miuiembedding") || className.startsWith("com.carwith.launcher.settings")) {
                        return 1;
                    }
                }
            }
            return 3;
        } catch (Throwable th) {
            return 3;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static String sourceName(int i) {
        if (i == 1) {
            return "用户操作";
        }
        if (i == 2) {
            return "卡片状态刷新";
        }
        return "未知来源";
    }

    private static String callerTrace(int i) {
        String className;
        try {
            StringBuilder sb = new StringBuilder();
            int i2 = 0;
            for (StackTraceElement stackTraceElement : new Throwable().getStackTrace()) {
                if (stackTraceElement != null && (className = stackTraceElement.getClassName()) != null && !className.startsWith("android.") && !className.startsWith("java.") && !className.startsWith("com.android.") && !className.startsWith("de.robv") && !className.startsWith("hun.mesh")) {
                    if (sb.length() > 0) {
                        sb.append(" <- ");
                    }
                    sb.append(className).append('#').append(stackTraceElement.getMethodName());
                    i2++;
                    if (i2 >= i) {
                        break;
                    }
                }
            }
            return sb.length() == 0 ? "(看不到 CarWith 栈)" : sb.toString();
        } catch (Throwable th) {
            return "(栈读取失败)";
        }
    }

    private static void onUserSelectedMediaApp(String str) {
        onMediaAppEvent(str, 1);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Unreachable blocks removed: 9, instructions: 25 */
    public static void onMediaAppEvent(String str, int i) {
        try {
            Context context = sAppCtx;
            if (context != null && str != null && str.length() != 0 && isMediaApp(sCl, str) && !isNeverKill(str)) {
                markSessionMedia(str);
                if (!MediaPlaybackPolicy.shouldRecordLastLaunchedEvent(i)) {
                    long jElapsedRealtime = SystemClock.elapsedRealtime();
                    if (!str.equals(sLastStatePkg) || jElapsedRealtime - sLastStateAt > 10000) {
                        sLastStatePkg = str;
                        sLastStateAt = jElapsedRealtime;
                        log(context, "车机界面状态刷新（" + sourceName(i) + "）：" + str + "，只记录不动播放器 | " + callerTrace(4));
                        return;
                    }
                    return;
                }
                saveLastOpenedMediaApp(context, str);
                if (SWITCH_DROPPED.remove(str) != null) {
                    log(context, "你在车机上重新选了 " + str + "，重新允许唤醒它");
                }
                if (USER_KILLED.remove(str)) {
                    log(context, "你在车机上重新选了 " + str + "，恢复自动唤醒");
                }
                if (MediaPlaybackPolicy.shouldSuppressPlayback(
                        isPlaying(context, str), playingOtherMedia(context, str))) {
                    log(context, "其他音乐正在播放，仅记录最后启动，不切换当前媒体/图标: " + str);
                    return;
                }
                sSelectedApp = str;
                CAR_OPEN_AT.put(str, Long.valueOf(SystemClock.elapsedRealtime()));
                rememberUsedMediaApp(sCl, str);
            }
        } catch (Throwable th) {
        }
    }

    private static void runOffMain(Runnable runnable) {
        try {
            if (sSwitchExec == null) {
                sSwitchExec = Executors.newSingleThreadExecutor(new ThreadFactory() { // from class: hun.mesh.carwithenhance.hook.AutoPlayAllHook.15
                    @Override // java.util.concurrent.ThreadFactory
                    public Thread newThread(Runnable runnable2) {
                        Thread thread = new Thread(runnable2, "carwith-enhance-switch");
                        thread.setDaemon(true);
                        return thread;
                    }
                });
            }
            sSwitchExec.execute(runnable);
        } catch (Throwable th) {
            runnable.run();
        }
    }

    private static void markSwitchDropped(Context context, String str, String str2) {
        if (str == null || str.length() == 0 || str.equals(str2)) {
            return;
        }
        Map<String, Long> map = SWITCH_DROPPED;
        if (map.containsKey(str)) {
            return;
        }
        map.put(str, Long.valueOf(SystemClock.elapsedRealtime()));
        log(context, "上一个播放器已按你的切换结束，本次连接内不再自动拉起它: " + str);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static boolean blockedBySwitchDrop(String str) {
        Map<String, Long> map;
        Long l;
        if (str == null || str.length() == 0 || (l = (map = SWITCH_DROPPED).get(str)) == null) {
            return false;
        }
        if (SystemClock.elapsedRealtime() - l.longValue() > SWITCH_DROP_TTL_MS) {
            map.remove(str);
            return false;
        }
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static boolean isNowDesired(String str) {
        if (str == null || str.length() == 0) {
            return false;
        }
        return str.equals(sSelectedApp) || str.equals(sCurrentMediaPkg) || str.equals(sWaitingPkg) || str.equals(sActiveWokePkg) || str.equals(sLastAcceptedPkg);
    }

    private static int pausePlayingSessions(Context context) {
        return pausePlayingSessions(context, true);
    }

    private static int pausePlayingSessions(Context context, boolean z) {
        List<MediaController> activeSessions;
        String packageName;
        try {
            MediaSessionManager mediaSessionManager = (MediaSessionManager) context.getSystemService("media_session");
            if (mediaSessionManager == null || (activeSessions = mediaSessionManager.getActiveSessions(null)) == null) {
                return 0;
            }
            int paused = 0;
            for (MediaController mediaController : activeSessions) {
                try {
                    if (mediaController != null && (packageName = mediaController.getPackageName()) != null && packageName.length() != 0 && !"com.miui.carlink".equals(packageName) && !isNeverKill(packageName)) {
                        PlaybackState playbackState = mediaController.getPlaybackState();
                        int state = playbackState == null ? 0 : playbackState.getState();
                        if (state == 3 || state == 6) {
                            MediaController.TransportControls transportControls = mediaController.getTransportControls();
                            if (transportControls != null) {
                                transportControls.pause();
                                paused++;
                                log(null, (z ? "断开前已暂停: " : "断开前补暂停: ") + packageName + " (state=" + state + ")");
                            }
                        }
                    }
                } catch (Throwable th) {
                    logErr(null, "断开前暂停单个播放器失败", th);
                }
            }
            return paused;
        } catch (Throwable th) {
            logErr(null, "断开前暂停失败", th);
            return 0;
        }
    }
    public static boolean pauseBeforeDisconnect(Context context) {
        if (context == null) {
            return false;
        }
        int iPausePlayingSessions = pausePlayingSessions(context);
        if (iPausePlayingSessions <= 0) {
            if (isMusicActive(context)) {
                boolean zDispatchMediaPauseKey = dispatchMediaPauseKey(context);
                log(null, "断开前未发现可暂停的会话，但系统仍有音乐在播，" + (zDispatchMediaPauseKey ? "已补发媒体暂停键" : "补发媒体暂停键失败"));
                if (zDispatchMediaPauseKey) {
                    log(null, "补暂停后音频输出" + (waitAudioSilent(context, 1600L) ? "已静默" : "仍活动") + "，继续断开流程");
                }
                return zDispatchMediaPauseKey;
            }
            log(null, "断开前无音乐在播，无需暂停 (系统音频状态=无音乐)");
            return false;
        }
        log(null, "断开前先暂停播放: 已暂停 " + iPausePlayingSessions + " 个会话");
        log(null, "播放已停下(" + (SystemClock.elapsedRealtime() - SystemClock.elapsedRealtime()) + "ms, 音频输出" + (waitAudioSilent(context, 1600L) ? "已静默" : "仍活动") + ")，继续断开流程");
        if (isPrefEnabled(context, KEY_KILL_IMMEDIATELY, true)) {
            log(null, "已请求播放器释放音轨: " + stopPlayingSessions(context) + " 个 (等了 " + (SystemClock.elapsedRealtime() - SystemClock.elapsedRealtime()) + "ms, 释放" + (waitAudioSilent(context, 1600L) ? "完成" : "超时") + ")");
        }
        return true;
    }

    private static int stopPlayingSessions(Context context) {
        List<MediaController> activeSessions;
        String packageName;
        int iStateOf;
        int i = 0;
        try {
            MediaSessionManager mediaSessionManager = (MediaSessionManager) context.getSystemService("media_session");
            if (mediaSessionManager == null || (activeSessions = mediaSessionManager.getActiveSessions(null)) == null) {
                return 0;
            }
            for (MediaController mediaController : activeSessions) {
                if (mediaController != null && (packageName = mediaController.getPackageName()) != null && packageName.length() != 0 && !"com.miui.carlink".equals(packageName) && !isNeverKill(packageName) && ((iStateOf = stateOf(mediaController)) == 3 || iStateOf == 6 || iStateOf == 2)) {
                    MediaController.TransportControls transportControls = mediaController.getTransportControls();
                    if (transportControls != null) {
                        transportControls.stop();
                        i++;
                    }
                }
            }
        } catch (Throwable th) {
            logErr(null, "请求播放器释放音轨失败", th);
        }
        return i;
    }

    private static int stateOf(MediaController mediaController) {
        try {
            PlaybackState playbackState = mediaController.getPlaybackState();
            if (playbackState == null) {
                return 0;
            }
            return playbackState.getState();
        } catch (Throwable th) {
            return 0;
        }
    }

    private static boolean isAnyTargetPlaying(Context context) {
        List<MediaController> activeSessions;
        String packageName;
        int iStateOf;
        try {
            MediaSessionManager mediaSessionManager = (MediaSessionManager) context.getSystemService("media_session");
            if (mediaSessionManager == null || (activeSessions = mediaSessionManager.getActiveSessions(null)) == null) {
                return false;
            }
            for (MediaController mediaController : activeSessions) {
                if (mediaController != null && (packageName = mediaController.getPackageName()) != null && packageName.length() != 0 && !"com.miui.carlink".equals(packageName) && !isNeverKill(packageName) && ((iStateOf = stateOf(mediaController)) == 3 || iStateOf == 6)) {
                    return true;
                }
            }
        } catch (Throwable th) {
        }
        return false;
    }

    private static boolean waitAudioSilent(Context context, long j) {
        long jElapsedRealtime = SystemClock.elapsedRealtime();
        while (true) {
            if (!isMusicActive(context) && !isAnyTargetPlaying(context)) {
                return true;
            }
            if (SystemClock.elapsedRealtime() - jElapsedRealtime >= j) {
                return false;
            }
            sleepQuietly(PAUSE_POLL_MS);
        }
    }

    public static void onCarDisconnected() {
        sCarConnected = false;
        unregisterSessionCallbacks();
        sConfirmedPlayingPkg = null;
        PANEL_SNAPSHOTS.clear();
        sLastAcceptedPkg = null;
        sActiveWokePkg = null;
        SESSION_MEDIA.clear();
        WOKE.clear();
        cancelRetryTasks();
        log(null, "连接已断开，仅复位续播状态");
    }

    private static void unregisterSessionCallbacks() {
        for (Map.Entry<String, MediaController.Callback> entry
                : new ArrayList<>(SESSION_CALLBACKS.entrySet())) {
            MediaController controller = SESSION_CONTROLLERS.get(entry.getKey());
            try {
                if (controller != null) {
                    controller.unregisterCallback(entry.getValue());
                }
            } catch (Throwable ignored) {
            }
        }
        SESSION_CALLBACKS.clear();
        SESSION_CONTROLLERS.clear();
    }

    private static void unregisterStaleSessionCallbacks(Set<String> activeKeys) {
        for (String key : new ArrayList<>(SESSION_CALLBACKS.keySet())) {
            if (activeKeys.contains(key)) {
                continue;
            }
            MediaController controller = SESSION_CONTROLLERS.remove(key);
            MediaController.Callback callback = SESSION_CALLBACKS.remove(key);
            try {
                if (controller != null && callback != null) {
                    controller.unregisterCallback(callback);
                }
            } catch (Throwable ignored) {
            }
        }
    }

    private static String callbackKey(MediaController controller) {
        if (controller == null || controller.getPackageName() == null) {
            return null;
        }
        try {
            return controller.getPackageName() + "#"
                    + controller.getSessionToken().hashCode();
        } catch (Throwable ignored) {
            return null;
        }
    }

    private static void scheduleDisconnectAudioCheck(final Context context) {
        if (context == null) {
            return;
        }
        try {
            if (sHandler == null) {
                sHandler = new Handler(Looper.getMainLooper());
            }
            sHandler.postDelayed(new Runnable() { // from class: hun.mesh.carwithenhance.hook.AutoPlayAllHook.18
                @Override // java.lang.Runnable
                public void run() {
                    AutoPlayAllHook.logAudioState(context, "断开后 2 秒");
                }
            }, 2000L);
        } catch (Throwable th) {
            logErr(context, "断开后音频自检调度失败", th);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void logAudioState(Context context, String str) {
        if (context == null) {
            return;
        }
        try {
            AudioManager audioManager = (AudioManager) context.getSystemService("audio");
            if (audioManager == null) {
                return;
            }
            int streamVolume = audioManager.getStreamVolume(3);
            int streamMaxVolume = audioManager.getStreamMaxVolume(3);
            boolean zIsMusicActive = audioManager.isMusicActive();
            StringBuilder sb = new StringBuilder();
            AudioDeviceInfo[] devices = audioManager.getDevices(2);
            if (devices != null) {
                for (AudioDeviceInfo audioDeviceInfo : devices) {
                    if (audioDeviceInfo != null && audioDeviceInfo.isSink()) {
                        if (sb.length() > 0) {
                            sb.append('/');
                        }
                        sb.append(audioDeviceInfo.getType());
                    }
                }
            }
            log(context, "音频状态(" + str + "): 媒体音量=" + streamVolume + "/" + streamMaxVolume + " 有音乐在播=" + zIsMusicActive + " 输出设备=" + ((Object) sb));
        } catch (Throwable th) {
            logErr(context, "读取音频状态失败", th);
        }
    }

    private static List<String> collectDisconnectTargets(Context context) {
        LinkedHashSet<String> candidates = new LinkedHashSet<>();
        candidates.add(sPlayingPkg);
        candidates.add(sConfirmedPlayingPkg);
        candidates.add(sLastAcceptedPkg);
        candidates.add(sActiveWokePkg);
        candidates.add(sSelectedApp);
        candidates.add(carwithCurrentMediaApp());
        candidates.addAll(SESSION_MEDIA);
        candidates.addAll(WOKE);
        candidates.add(readPrefMediaApp());
        candidates.add(carwithLastUsedMediaApp());
        candidates.add(readPrefMusicApp());
        candidates.addAll(activeSessionPackages(context));

        for (String candidate : candidates) {
            if (isKillCandidate(candidate)
                    && !CANNOT_WAKE.contains(candidate)
                    && isPlaying(context, candidate)) {
                log(context, "断开处理: 选择当前正在播放的 " + candidate);
                return new ArrayList<>(Collections.singletonList(candidate));
            }
        }

        for (String candidate : candidates) {
            if (!isKillCandidate(candidate)) {
                continue;
            }
            if (CANNOT_WAKE.contains(candidate)) {
                log(context, "断开处理: " + candidate + " 无法后台拉起，保留它");
                continue;
            }
            if (isProcessRunning(context, candidate) || hasSession(context, candidate)) {
                log(context, "断开处理: 选择最近活动的 " + candidate);
                return new ArrayList<>(Collections.singletonList(candidate));
            }
        }
        return Collections.emptyList();
    }

    private static boolean isKillCandidate(String str) {
        return str != null && str.length() > 0 && !isNeverKill(str) && isMediaApp(sCl, str);
    }

    private static String describe(List<String> list) {
        if (list == null || list.isEmpty()) {
            return "(无)";
        }
        StringBuilder sb = new StringBuilder();
        for (String str : list) {
            if (sb.length() > 0) {
                sb.append(", ");
            }
            sb.append(str);
        }
        return sb.toString();
    }

    private static Set<String> activeSessionPackages(Context context) {
        List<MediaController> activeSessions;
        LinkedHashSet linkedHashSet = new LinkedHashSet();
        try {
            MediaSessionManager mediaSessionManager = (MediaSessionManager) context.getSystemService("media_session");
            if (mediaSessionManager == null || (activeSessions = mediaSessionManager.getActiveSessions(null)) == null) {
                return linkedHashSet;
            }
            for (MediaController mediaController : activeSessions) {
                if (mediaController != null) {
                    linkedHashSet.add(mediaController.getPackageName());
                }
            }
        } catch (Throwable th) {
        }
        return linkedHashSet;
    }

    private static String carwithCurrentMediaApp() {
        try {
            Object objCallMethod = XposedHelpers.callMethod(XposedHelpers.callStaticMethod(XposedHelpers.findClass("ma.a", sCl), "p", new Object[0]), "o", new Object[0]);
            if ((objCallMethod instanceof String) && ((String) objCallMethod).length() > 0) {
                return (String) objCallMethod;
            }
            return null;
        } catch (Throwable th) {
            return null;
        }
    }

    private static String carwithLastUsedMediaApp() {
        try {
            Object objectField = XposedHelpers.getObjectField(XposedHelpers.callStaticMethod(XposedHelpers.findClass("xa.c", sCl), "h", new Object[0]), "d");
            if ((objectField instanceof String) && ((String) objectField).length() > 0) {
                return (String) objectField;
            }
            return null;
        } catch (Throwable th) {
            return null;
        }
    }

    private static boolean isMediaApp(ClassLoader classLoader, String str) {
        if (str == null || str.length() == 0) {
            return false;
        }
        if (QQ_MUSIC.equals(str)) {
            return true;
        }
        return isTarget(classLoader, str);
    }

    private static boolean isNeverKill(String str) {
        if (str == null || str.length() == 0) {
            return true;
        }
        for (String str2 : NEVER_KILL) {
            if (str2.equals(str)) {
                return true;
            }
        }
        try {
            String string = sAppCtx.getSharedPreferences(PREF_MEDIA_APP, 0).getString("prefer_map_app", null);
            return string != null && string.equals(str);
        } catch (Throwable th) {
            return false;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void markSessionMedia(String str) {
        if (isNeverKill(str) || !isMediaApp(sCl, str)) {
            return;
        }
        SESSION_MEDIA.add(str);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static boolean isPrefEnabled(Context context, String str, boolean z) {
        try {
            return context.getSharedPreferences("carwithenhance_hooksettings", 0).getBoolean(str, z);
        } catch (Throwable th) {
            return z;
        }
    }

    private static void sleepQuietly(long j) {
        try {
            Thread.sleep(j);
        } catch (InterruptedException e) {
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void releaseWoke(Context context, String str, String str2) {
        try {
            if (!WOKE.remove(str)) {
                return;
            }
            releaseCarwithBinding(str);
            releaseOwnBrowsers(str);
            if (!gracefulStopBeforeKill(context, str)) {
                log(context, "媒体会话仍未释放，保留播放器进程: " + str);
                return;
            }
            forceStop(context, str, str2);
        } catch (Throwable th) {
            logErr(context, "释放失败: " + str, th);
        }
    }

    private static boolean gracefulStopBeforeKill(Context context, String str) {
        int iStateOf;
        MediaController.TransportControls transportControls;
        try {
            MediaController mediaControllerFindController = findController(context, str);
            if (mediaControllerFindController != null && (((iStateOf = stateOf(mediaControllerFindController)) == 3 || iStateOf == 6 || iStateOf == 2) && (transportControls = mediaControllerFindController.getTransportControls()) != null)) {
                try {
                    transportControls.stop();
                    log(context, "结束进程前先请播放器释放音轨: " + str + " (state=" + iStateOf + ")");
                } catch (Throwable th) {
                }
            }
            long jElapsedRealtime = SystemClock.elapsedRealtime();
            boolean zWaitAudioSilent = waitAudioSilent(context, KILL_GRACE_MAX_WAIT_MS);
            boolean zWaitSessionReleased = waitSessionReleased(context, str, 1600L);
            long jElapsedRealtime2 = SystemClock.elapsedRealtime() - jElapsedRealtime;
            if (jElapsedRealtime2 < KILL_MIN_SETTLE_MS) {
                sleepQuietly(KILL_MIN_SETTLE_MS - jElapsedRealtime2);
                jElapsedRealtime2 = SystemClock.elapsedRealtime() - jElapsedRealtime;
            }
            log(context, "结束进程前音频已交接: " + str + " (等了 " + jElapsedRealtime2 + "ms, 音频输出" + (zWaitAudioSilent ? "已静默" : "仍活动") + ", 媒体会话" + (zWaitSessionReleased ? "已释放" : "仍在") + ")");
            return zWaitSessionReleased;
        } catch (Throwable th2) {
            logErr(context, "结束进程前的音频释放失败: " + str, th2);
            return false;
        }
    }

    private static boolean waitSessionReleased(Context context, String str, long j) {
        if (str == null || str.length() == 0) {
            return true;
        }
        long jElapsedRealtime = SystemClock.elapsedRealtime();
        while (hasSession(context, str)) {
            if (SystemClock.elapsedRealtime() - jElapsedRealtime >= j) {
                return false;
            }
            sleepQuietly(PAUSE_POLL_MS);
        }
        return true;
    }

    private static MediaController findController(Context context, String str) {
        List<MediaController> activeSessions;
        try {
            MediaSessionManager mediaSessionManager = (MediaSessionManager) context.getSystemService("media_session");
            if (mediaSessionManager == null || (activeSessions = mediaSessionManager.getActiveSessions(null)) == null) {
                return null;
            }
            for (MediaController mediaController : activeSessions) {
                if (mediaController != null && str != null && str.equals(mediaController.getPackageName())) {
                    return mediaController;
                }
            }
        } catch (Throwable th) {
        }
        return null;
    }

    private static void releaseCarwithBinding(String str) {
        Object objCallStaticMethod;
        try {
            ClassLoader classLoader = sCl;
            if (classLoader != null && (objCallStaticMethod = XposedHelpers.callStaticMethod(XposedHelpers.findClass("ra.a", classLoader), "c", new Object[0])) != null) {
                XposedHelpers.callMethod(objCallStaticMethod, "h", new Object[]{str});
                log(null, "已释放 CarWith 对 " + str + " 的媒体绑定");
            }
        } catch (Throwable th) {
            logErr(null, "释放 CarWith 媒体绑定失败: " + str, th);
        }
    }

    private static void releaseOwnBrowsers(String str) {
        try {
            for (String str2 : new ArrayList<String>(BROWSERS.keySet())) {
                if (str2.startsWith(str + "/")) {
                    MediaBrowser mediaBrowserRemove = BROWSERS.remove(str2);
                    ATTEMPTS.remove(str2);
                    if (mediaBrowserRemove != null) {
                        mediaBrowserRemove.disconnect();
                    }
                }
            }
        } catch (Throwable th) {
        }
    }

    private static void releaseAllWoke(String str) {
        Context context = sAppCtx;
        ArrayList arrayList = new ArrayList(WOKE);
        sActiveWokePkg = null;
        sLastAcceptedPkg = null;
        cancelRetryTasks();
        Iterator it = arrayList.iterator();
        while (it.hasNext()) {
            releaseWoke(context, (String) it.next(), str);
        }
        WOKE.clear();
        SESSION_MEDIA.clear();
        log(context, "已结束本功能唤醒过的播放器 (" + str + ")");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void forceStop(Context context, String str, String str2) {
        if (str != null) {
            try {
                OWN_KILL_AT.put(str, Long.valueOf(SystemClock.elapsedRealtime()));
            } catch (Throwable th) {
                logErr(context, "结束播放器失败: " + str, th);
                return;
            }
        }
        XposedHelpers.callMethod((ActivityManager) context.getSystemService("activity"), "forceStopPackage", new Object[]{str});
        log(context, "已结束播放器: " + str + " (" + str2 + ")");
    }

    private static boolean looksUserKilled(Context context, String str) {
        if (context == null || str == null || !sCarConnected || !str.equals(sResumedPkg)) {
            return false;
        }
        if (str.equals(sWaitingPkg)) {
            return false;
        }
        if (str.equals(sResumedPkg) && sLastResumeAt > 0
                && SystemClock.elapsedRealtime() - sLastResumeAt < 30000L) {
            return false;
        }
        Long l = OWN_KILL_AT.get(str);
        return ((l != null && SystemClock.elapsedRealtime() - l.longValue() < 20000) || isProcessRunning(context, str) || hasSession(context, str)) ? false : true;
    }

    private static void markUserKilled(Context context, String str) {
        if (str != null && USER_KILLED.add(str)) {
            log(context, "你手动结束了 " + str + "，本次连接内不再自动拉起它（下次上车续播照常）");
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static boolean blockedByUserKill(Context context, String str) {
        if (str == null || !isPrefEnabled(context, KEY_SKIP_USER_KILLED, true)) {
            return false;
        }
        if (USER_KILLED.contains(str)) {
            return true;
        }
        if (!looksUserKilled(context, str)) {
            return false;
        }
        markUserKilled(context, str);
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static boolean isProcessRunning(Context context, String str) {
        try {
            List<ActivityManager.RunningAppProcessInfo> runningAppProcesses = ((ActivityManager) context.getSystemService("activity")).getRunningAppProcesses();
            if (runningAppProcesses != null) {
                for (ActivityManager.RunningAppProcessInfo runningAppProcessInfo : runningAppProcesses) {
                    if (runningAppProcessInfo != null && str.equals(runningAppProcessInfo.processName)) {
                        return true;
                    }
                }
                return false;
            }
            return false;
        } catch (Throwable th) {
            return false;
        }
    }

    /* JADX WARN: Code duplicated, block: B:34:0x00a2  */
    public static void handle(String str, ClassLoader classLoader) {
        Context appContext;
        boolean z;
        if (str == null || str.length() == 0 || (appContext = AppUtils.getAppContext(classLoader)) == null) {
            return;
        }
        setAppContext(appContext);
        if (!isEnabled(appContext)) {
            return;
        }
        if (isMediaApp(classLoader, str)) {
            saveLastOpenedMediaApp(appContext, str);
        }
        markSessionMedia(str);
        if (MediaPlaybackPolicy.shouldSuppressPlayback(
                isPlaying(appContext, str), playingOtherMedia(appContext, str))) {
            log(appContext, "其他音乐正在播放，跳过自动播放: " + str);
            return;
        }
        String strSelectedApp = selectedApp();
        if (strSelectedApp != null && strSelectedApp.length() > 0 && !strSelectedApp.equals(str) && !isProcessRunning(appContext, str) && !hasSession(appContext, str) && isMediaApp(classLoader, strSelectedApp)) {
            log(appContext, "CarWith 请求的 " + str + " 已结束，改用当前选中的 " + strSelectedApp);
            str = strSelectedApp;
        }
        String str2 = sLastAcceptedPkg;
        if (str2 == null || !str2.equals(str)) {
            cancelRetryTasks();
            sLastAcceptedPkg = str;
            rememberUsedMediaApp(classLoader, str);
            String str3 = sActiveWokePkg;
            if (str3 != null && !str3.equals(str)) {
                sActiveWokePkg = null;
                releaseWoke(appContext, str3, "切换到 " + str);
                markSwitchDropped(appContext, str3, str);
                z = true;
            } else {
                z = false;
            }
        } else {
            z = false;
        }
        if (!isMediaApp(classLoader, str)) {
            log(appContext, str + " 不在续播名单内，不处理");
            return;
        }
        if (hasSession(appContext, str) && isProcessRunning(appContext, str)) {
            boolean targetPlaying = isPlaying(appContext, str);
            boolean otherMediaPlaying = playingOtherMedia(appContext, str);
            if (!ResumeReadinessPolicy.canSendResumeForExistingSession(
                    readPrefAutoPlay(),
                    targetPlaying,
                    str.equals(sConfirmedPlayingPkg),
                    otherMediaPlaying)) {
                if (targetPlaying) {
                    MediaController active = findController(appContext, str);
                    if (active != null) {
                        refreshCarWithMediaState(appContext, str, active);
                    }
                }
                log(appContext, "已有会话无需再次续播: " + str
                        + " (playing=" + targetPlaying
                        + ", confirmed=" + str.equals(sConfirmedPlayingPkg) + ")");
                return;
            }
            if (resumeAfterPanelIfReady(classLoader, str)) {
                log(appContext, str + " 已有媒体会话，面板就绪，交给 CarWith");
                sResumedPkg = str;
                learnCurrentMedia(str);
            } else {
                log(appContext, str + " 已有媒体会话，但面板元数据未就绪，等待续播");
            }
            return;
        }
        if (!z && playingOtherMedia(appContext, str)) {
            log(appContext, "已有其它音乐在播，跳过唤醒 " + str);
            return;
        }
        if (blockedByUserKill(appContext, str)) {
            log(appContext, str + " 是你手动结束的，本次连接不自动拉起");
            return;
        }
        if (blockedBySwitchDrop(str)) {
            log(appContext, str + " 是你在车机上切走的，本次连接不自动拉起");
            return;
        }
        long jElapsedRealtime = SystemClock.elapsedRealtime();
        if (str.equals(sLastPkg) && jElapsedRealtime - sLastWake < 30000) {
            boolean processRunning = isProcessRunning(appContext, str);
            boolean hasSession = hasSession(appContext, str);
            if (ResumeReadinessPolicy.canSendOfficialResume(
                    readPrefAutoPlay(), processRunning, hasSession,
                    isPlaying(appContext, str), playingOtherMedia(appContext, str))) {
                if (resumeAfterPanelIfReady(classLoader, str)) {
                    log(appContext, "重复打开回调，面板就绪，续播 " + str);
                } else {
                    log(appContext, "重复打开回调暂不续播，等待面板元数据 " + str);
                }
            } else if (processRunning && !hasSession) {
                log(appContext, "重复打开回调暂不续播，等待媒体会话 " + str);
            }
            return;
        }
        sLastPkg = str;
        sLastWake = jElapsedRealtime;
        wakeInBackground(appContext, str);
        startRetryLoop(classLoader, str);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void startRetryLoop(ClassLoader classLoader, String str) {
        boolean z;
        boolean z2;
        cancelRetryTasks();
        long j = sRetryGen;
        sWaitingPkg = str;
        sWaitStartAt = SystemClock.elapsedRealtime();
        int i = 0;
        while (true) {
            long[] jArr = RETRY_DELAYS;
            if (i < jArr.length) {
                if (i != 1 && i != 2 && i != 3 && i != 6) {
                    z = false;
                } else {
                    z = true;
                }
                long j2 = jArr[i];
                if (i != jArr.length - 1) {
                    z2 = false;
                } else {
                    z2 = true;
                }
                schedule(classLoader, str, j2, j, z, z2);
                i++;
            } else {
                return;
            }
        }
    }

    private static boolean playingOtherMedia(Context context, String exclude) {
        try {
            MediaSessionManager manager =
                    (MediaSessionManager) context.getSystemService("media_session");
            if (manager == null) {
                return false;
            }
            List<MediaController> sessions = manager.getActiveSessions(null);
            if (sessions == null) {
                return false;
            }
            for (MediaController controller : sessions) {
                if (controller == null) {
                    continue;
                }
                String pkg = controller.getPackageName();
                if (pkg == null || pkg.equals(exclude) || !isMediaApp(sCl, pkg)) {
                    continue;
                }
                PlaybackState state = controller.getPlaybackState();
                if (state != null && state.getState() == PlaybackState.STATE_PLAYING) {
                    return true;
                }
            }
        } catch (Throwable th) {
            logErr(context, "读取其他播放器状态失败", th);
        }
        return false;
    }

    private static void learnCurrentMedia(String str) {
        if (isMediaApp(sCl, str) && !isNeverKill(str)) {
            sCurrentMediaPkg = str;
            markSessionMedia(str);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void wakeInBackground(Context context, String str) {
        boolean zIsProcessRunning = isProcessRunning(context, str);
        boolean zSendMediaButton = sendMediaButton(context, str);
        boolean zStartPlayerService = startPlayerService(context, str);
        boolean zPlayViaSession = playViaSession(context, str);
        bindBrowseService(context, str);
        learnCurrentMedia(str);
        if (!zIsProcessRunning) {
            WOKE.add(str);
            sActiveWokePkg = str;
        }
        log(context, "后台唤醒 " + str + " (媒体按键=" + zSendMediaButton + ", 启动服务=" + zStartPlayerService + ", 会话播放=" + zPlayViaSession + ", 原本已运行=" + zIsProcessRunning + ")");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static boolean startPlayerService(Context context, String str) {
        if (context != null && str != null) {
            if (str.length() != 0 && SERVICE_START_UNAVAILABLE.contains(str)) {
                return false;
            }
            try {
                if (str.length() != 0) {
                    String strGuessPlaybackService = PLAYBACK_SERVICES.get(str);
                    if (strGuessPlaybackService == null) {
                        strGuessPlaybackService = guessPlaybackService(context, str);
                    }
                    if (strGuessPlaybackService == null) {
                        return false;
                    }
                    ComponentName component = new ComponentName(str, strGuessPlaybackService);
                    if (isServiceAccessProtected(context, component)) {
                        return false;
                    }
                    Intent intent = new Intent();
                    intent.setComponent(component);
                    intent.putExtra("is_started_by_foreground", true);
                    try {
                        context.startForegroundService(intent);
                    } catch (Throwable th) {
                        context.startService(intent);
                    }
                    return true;
                }
            } catch (Throwable th2) {
                if (isUnexportedServiceFailure(th2)) {
                    if (SERVICE_START_UNAVAILABLE.add(str)) {
                        log(sAppCtx, "播放器服务不可直接启动，改用媒体键兜底: " + str);
                    }
                } else {
                    logErr(sAppCtx, "启动播放服务失败: " + str, th2);
                }
                return false;
            }
        }
        return false;
    }

    private static boolean isUnexportedServiceFailure(Throwable throwable) {
        Throwable current = throwable;
        while (current != null) {
            if (current instanceof SecurityException) {
                String message = current.getMessage();
                if (message != null) {
                    String lower = message.toLowerCase(Locale.US);
                    if (lower.contains("not exported") || lower.contains("without permission")) {
                        return true;
                    }
                }
            }
            current = current.getCause();
        }
        return false;
    }

    private static String guessPlaybackService(Context context, String str) {
        try {
            PackageInfo packageInfo = context.getPackageManager().getPackageInfo(str, 4);
            if (packageInfo != null && packageInfo.services != null) {
                String str2 = null;
                for (ServiceInfo serviceInfo : packageInfo.services) {
                    if (serviceInfo != null && serviceInfo.name != null) {
                        String lowerCase = serviceInfo.name.toLowerCase(Locale.US);
                        if ((lowerCase.contains("mediaplayback") || lowerCase.contains("playbackservice") || lowerCase.contains("playerservice") || lowerCase.contains("musicservice")) && (str2 == null || lowerCase.contains("playback"))) {
                            str2 = serviceInfo.name;
                        }
                    }
                }
                return str2;
            }
            return null;
        } catch (Throwable th) {
            return null;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void resumeCarWith(ClassLoader classLoader, String str) {
        Context context = sAppCtx;
        if (context != null && isPlaying(context, str)) {
            log(context, "跳过重复续播，播放器已经进入播放: " + str);
            return;
        }
        long now = SystemClock.elapsedRealtime();
        if (ResumeReadinessPolicy.isDuplicateResume(
                sLastResumePkg, sLastResumeAt, str, now, DUPLICATE_RESUME_WINDOW_MS)) {
            log(sAppCtx, "跳过重复续播: " + str);
            return;
        }
        try {
            Object mediaHelperInstance = GenericAutoPlayHooks.getMediaHelperInstance(classLoader);
            if (mediaHelperInstance == null) {
                return;
            }
            sResumeInFlight = true;
            try {
                XposedHelpers.callMethod(mediaHelperInstance, GenericAutoPlayHooks.getMediaMethodName(), new Object[]{str, Boolean.TRUE});
                sResumeInFlight = false;
                sLastResumePkg = str;
                sLastResumeAt = SystemClock.elapsedRealtime();
                log(sAppCtx, "已下发续播指令: " + str + " (距开始 " + (sWaitStartAt > 0 ? SystemClock.elapsedRealtime() - sWaitStartAt : -1L) + "ms)");
            } catch (Throwable th) {
                sResumeInFlight = false;
                throw th;
            }
        } catch (Throwable th2) {
            logErr(sAppCtx, "下发续播指令失败: " + str, th2);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static boolean playViaSession(Context context, String str) {
        List<MediaController> activeSessions;
        MediaController.TransportControls transportControls;
        try {
            MediaSessionManager mediaSessionManager = (MediaSessionManager) context.getSystemService("media_session");
            if (mediaSessionManager == null || (activeSessions = mediaSessionManager.getActiveSessions(null)) == null) {
                return false;
            }
            for (MediaController mediaController : activeSessions) {
                if (mediaController != null && str.equals(mediaController.getPackageName()) && (transportControls = mediaController.getTransportControls()) != null) {
                    transportControls.play();
                    return true;
                }
            }
        } catch (Throwable th) {
            logErr(sAppCtx, "会话播放失败: " + str, th);
        }
        return false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    private static boolean dispatchMediaPauseKey(Context context) {
        try {
            AudioManager audioManager = (AudioManager) context.getSystemService("audio");
            if (audioManager == null) {
                return false;
            }
            Method method = AudioManager.class.getMethod("dispatchMediaKeyEvent", KeyEvent.class);
            method.setAccessible(true);
            method.invoke(audioManager, new KeyEvent(0, 127));
            method.invoke(audioManager, new KeyEvent(1, 127));
            return true;
        } catch (Throwable th) {
            logErr(null, "补发媒体暂停键失败", th);
            return false;
        }
    }

    public static boolean sendMediaButton(Context context, String str) {
        try {
            PackageManager packageManager = context.getPackageManager();
            Intent intent = new Intent("android.intent.action.MEDIA_BUTTON");
            intent.setPackage(str);
            List<ResolveInfo> listQueryBroadcastReceivers = packageManager.queryBroadcastReceivers(intent, 0);
            if (listQueryBroadcastReceivers != null && !listQueryBroadcastReceivers.isEmpty()) {
                for (ResolveInfo resolveInfo : listQueryBroadcastReceivers) {
                    if (resolveInfo.activityInfo != null) {
                        ComponentName componentName = new ComponentName(resolveInfo.activityInfo.packageName, resolveInfo.activityInfo.name);
                        int[] iArr = {0, 1};
                        for (int i = 0; i < 2; i++) {
                            int i2 = iArr[i];
                            Intent intent2 = new Intent("android.intent.action.MEDIA_BUTTON");
                            intent2.setComponent(componentName);
                            intent2.putExtra("android.intent.extra.KEY_EVENT", new KeyEvent(i2, 126));
                            context.sendBroadcast(intent2);
                        }
                        return true;
                    }
                }
                return false;
            }
            return false;
        } catch (Throwable th) {
            logErr(context, "媒体按键广播失败: " + str, th);
            return false;
        }
    }

    public static void bindBrowseService(Context context, String str) {
        if (context == null || str == null || str.length() == 0) {
            return;
        }
        try {
            String str2 = KNOWN_SERVICES.get(str);
            if (str2 != null) {
                ComponentName knownComponent = new ComponentName(str, str2);
                if (!isServiceAccessProtected(context, knownComponent)) {
                    connect(context, knownComponent);
                }
            }
            PackageManager packageManager = context.getPackageManager();
            Intent intent = new Intent(BROWSE_ACTION);
            intent.setPackage(str);
            List<ResolveInfo> listQueryIntentServices = packageManager.queryIntentServices(intent, 0);
            log(context, str + " 媒体服务查询结果: " + (listQueryIntentServices == null ? -1 : listQueryIntentServices.size()));
            if (listQueryIntentServices != null && !listQueryIntentServices.isEmpty()) {
                Iterator<ResolveInfo> it = listQueryIntentServices.iterator();
                while (it.hasNext()) {
                    ServiceInfo serviceInfo = it.next().serviceInfo;
                    if (serviceInfo != null) {
                        ComponentName component = new ComponentName(
                                serviceInfo.packageName, serviceInfo.name);
                        if (isServiceAccessProtected(context, component)) {
                            continue;
                        }
                        log(context, "  候选: " + serviceInfo.name + " exported=" + serviceInfo.exported);
                        connect(context, component);
                        return;
                    }
                }
            }
        } catch (Throwable th) {
            logErr(context, "后台绑定媒体服务失败: " + str, th);
        }
    }

    private static boolean isServiceAccessProtected(
            Context context, ComponentName componentName) {
        try {
            ServiceInfo serviceInfo = context.getPackageManager()
                    .getServiceInfo(componentName, 0);
            String permission = serviceInfo == null ? null : serviceInfo.permission;
            return permission != null && !permission.isEmpty()
                    && context.checkSelfPermission(permission)
                    != PackageManager.PERMISSION_GRANTED;
        } catch (Throwable ignored) {
            return false;
        }
    }

    private static void connect(Context context, final ComponentName componentName) {
        final String strFlattenToShortString = componentName.flattenToShortString();
        Map<String, MediaBrowser> map = BROWSERS;
        synchronized (map) {
            long jElapsedRealtime = SystemClock.elapsedRealtime();
            Map<String, Long> map2 = ATTEMPTS;
            Long l = map2.get(strFlattenToShortString);
            if (l != null && jElapsedRealtime - l.longValue() < 5000) {
                log(context, "略过重复绑定: " + strFlattenToShortString);
                return;
            }
            map2.put(strFlattenToShortString, Long.valueOf(jElapsedRealtime));
            MediaBrowser mediaBrowserRemove = map.remove(strFlattenToShortString);
            if (mediaBrowserRemove != null) {
                try {
                    mediaBrowserRemove.disconnect();
                } catch (Throwable th) {
                }
            }
            try {
                MediaBrowser mediaBrowser = new MediaBrowser(context, componentName, new MediaBrowser.ConnectionCallback() { // from class: hun.mesh.carwithenhance.hook.AutoPlayAllHook.21
                    @Override // android.media.browse.MediaBrowser.ConnectionCallback
                    public void onConnected() {
                        AutoPlayAllHook.log(null, "后台已连接媒体服务: " + componentName.flattenToShortString());
                    }

                    @Override // android.media.browse.MediaBrowser.ConnectionCallback
                    public void onConnectionFailed() {
                        AutoPlayAllHook.log(null, "后台连接失败: " + componentName.flattenToShortString());
                        synchronized (AutoPlayAllHook.BROWSERS) {
                            AutoPlayAllHook.BROWSERS.remove(strFlattenToShortString);
                        }
                    }

                    @Override // android.media.browse.MediaBrowser.ConnectionCallback
                    public void onConnectionSuspended() {
                    }
                }, null);
                mediaBrowser.connect();
                BROWSERS.put(strFlattenToShortString, mediaBrowser);
                log(context, "后台绑定媒体服务: " + strFlattenToShortString);
            } catch (Throwable th2) {
                logErr(context, "绑定异常: " + strFlattenToShortString, th2);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static boolean isEnabled(Context context) {
        return autoplayEnabled(context);
    }

    private static boolean autoplayEnabled(Context context) {
        if (context == null) {
            return false;
        }
        try {
            return context.getSharedPreferences(PREF_MEDIA_APP, 0)
                    .getBoolean(KEY_CARWITH_AUTOPLAY, false);
        } catch (Throwable th) {
            return false;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static boolean isTarget(ClassLoader classLoader, String str) {
        if (QQ_MUSIC.equals(str)) {
            return false;
        }
        try {
            Object objCallMethod = XposedHelpers.callMethod(XposedHelpers.callStaticMethod(XposedHelpers.findClass(CARWITH_APP_MGR, classLoader), "U", new Object[0]), "K", new Object[]{"media_app"});
            if (objCallMethod instanceof List) {
                return ((List) objCallMethod).contains(str);
            }
        } catch (Throwable th) {
        }
        for (String str2 : FALLBACK) {
            if (str2.equals(str)) {
                return true;
            }
        }
        return false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static boolean hasSession(Context context, String str) {
        List<MediaController> activeSessions;
        try {
            MediaSessionManager mediaSessionManager = (MediaSessionManager) context.getSystemService("media_session");
            if (mediaSessionManager == null || (activeSessions = mediaSessionManager.getActiveSessions(null)) == null) {
                return false;
            }
            for (MediaController mediaController : activeSessions) {
                if (mediaController != null && str.equals(mediaController.getPackageName())) {
                    return true;
                }
            }
        } catch (Throwable th) {
        }
        return false;
    }

    private static boolean isMusicActive(Context context) {
        try {
            AudioManager audioManager = (AudioManager) context.getSystemService("audio");
            return audioManager != null && audioManager.isMusicActive();
        } catch (Throwable th) {
            return false;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static boolean isPlaying(Context context, String str) {
        List<MediaController> activeSessions;
        try {
            MediaSessionManager mediaSessionManager = (MediaSessionManager) context.getSystemService("media_session");
            if (mediaSessionManager == null || (activeSessions = mediaSessionManager.getActiveSessions(null)) == null) {
                return false;
            }
            for (MediaController mediaController : activeSessions) {
                if (mediaController != null && str.equals(mediaController.getPackageName())) {
                    PlaybackState playbackState = mediaController.getPlaybackState();
                    return playbackState != null && playbackState.getState() == 3;
                }
            }
        } catch (Throwable th) {
        }
        return false;
    }

    private static void schedule(final ClassLoader classLoader, final String str, long delay,
            final long generation, final boolean allowWake, final boolean finalAttempt) {
        try {
            if (sHandler == null) {
                sHandler = new Handler(Looper.getMainLooper());
            }
            final Runnable retryTask = new Runnable() {
                @Override
                public void run() {
                    synchronized (sPendingRetryTasks) {
                        sPendingRetryTasks.remove(this);
                    }
                    if (generation != sRetryGen) {
                        return;
                    }
                    try {
                        Context context = sAppCtx;
                        if (context == null) {
                            return;
                        }
                        if (!isEnabled(context)) {
                            cancelRetryTasks();
                            return;
                        }
                        if (sSuppressAutoResumeForConnection) {
                            log(context, "一键清理后停止自动续播轮询: " + str);
                            cancelRetryTasks();
                            return;
                        }
                        boolean targetPlaying = isPlaying(context, str);
                        boolean otherMediaPlaying = playingOtherMedia(context, str);
                        if (MediaPlaybackPolicy.shouldSuppressPlayback(
                                targetPlaying, otherMediaPlaying)) {
                            log(context, "其他音乐正在播放，停止对 " + str + " 的自动唤醒轮询");
                            cancelRetryTasks();
                            return;
                        }
                        if (blockedByUserKill(context, str) || blockedBySwitchDrop(str)) {
                            cancelRetryTasks();
                            return;
                        }
                        if (!targetPlaying) {
                            boolean processRunning = isProcessRunning(context, str);
                            boolean hasSession = hasSession(context, str);
                            if (!processRunning) {
                                if (ResumeReadinessPolicy.shouldWake(
                                        processRunning, hasSession, finalAttempt)
                                        && (allowWake || hasSession)) {
                                    log(context, "等待 " + str + " 启动，补一次唤醒");
                                    sendMediaButton(context, str);
                                    startPlayerService(context, str);
                                    playViaSession(context, str);
                                    bindBrowseService(context, str);
                                }
                                if (finalAttempt) {
                                    CANNOT_WAKE.add(str);
                                    log(context, "后台无法拉起 " + str + "，断开时会保留它以免下次无法续播");
                                    cancelRetryTasks();
                                }
                                return;
                            }
                            CANNOT_WAKE.remove(str);
                            if (!hasSession) {
                                log(context, "等待媒体会话出现 " + str);
                                return;
                            }
                            boolean autoPlayEnabled = readPrefAutoPlay();
                            if (!ResumeReadinessPolicy.canSendOfficialResume(
                                    autoPlayEnabled, processRunning, hasSession,
                                    targetPlaying, otherMediaPlaying)) {
                                if (!autoPlayEnabled) {
                                    log(context, "CarWith自带续播开关已关闭，停止自动唤醒轮询");
                                    cancelRetryTasks();
                                }
                                return;
                            }
                            if (resumeAfterPanelIfReady(classLoader, str)) {
                                sResumedPkg = str;
                                log(context, "会话已就绪，面板已同步，下发官方续播 " + str);
                            } else {
                                log(context, "会话已就绪，等待面板元数据 " + str);
                            }
                            return;
                        }
                        cancelRetryTasks();
                        sResumedPkg = str;
                        log(context, "续播已生效，停止轮询 " + str);
                    } catch (Throwable th) {
                        logErr(sAppCtx, "重试失败: " + str, th);
                    }
                }
            };
            synchronized (sPendingRetryTasks) {
                sPendingRetryTasks.add(retryTask);
            }
            if (!sHandler.postDelayed(retryTask, delay)) {
                synchronized (sPendingRetryTasks) {
                    sPendingRetryTasks.remove(retryTask);
                }
            }
        } catch (Throwable th) {
            logErr(sAppCtx, "调度续播重试失败: " + str, th);
        }
    }

}
