package hun.mesh.carwithenhance.hook;

import android.app.ActivityManager;
import android.app.Application;
import android.content.BroadcastReceiver;
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
import android.util.Log;
import android.view.KeyEvent;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;
import hun.mesh.carwithenhance.BuildConfig;
import hun.mesh.carwithenhance.dexkit.CarWithDexKitManager;
import hun.mesh.carwithenhance.dexkit.GenericAutoPlayHooks;
import hun.mesh.carwithenhance.utils.AppUtils;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
    private static final String APPS_VIEW_MODEL = "com.carwith.launcher.apps.AppsViewModel";
    private static final String BROWSE_ACTION = "android.media.browse.MediaBrowserService";
    private static final String CARWITH_APP_MGR = "com.carwith.common.utils.c0";
    private static final String EXPERIENCE_PREF = "ucar_experience_app_added_category";
    private static final String FPS_HELPER = "com.carwith.common.utils.s2";
    private static final String FPS_HELPER_NEW = "com.carwith.common.utils.d";
    private static final int FPS_INDEX_50 = 3;
    private static final int FPS_INDEX_60 = 4;
    private static final String FPS_LABEL_CLASS = "p6.o0";
    private static final String FPS_PREF_FILE = "spUtils";
    private static final String FPS_PREF_KEY = "user_setting_framerate";
    private static final String GSON_CLASS = "com.google.gson.Gson";
    private static final String KEY_CARWITH_AUTOPLAY = "prefer_auto_play";
    private static final String KEY_COLDSTART_ANTIKILL = "hook_coldstart_antikill_enabled";
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
    private static volatile String sResumedPkg;
    private static volatile long sRetryGen;
    private static volatile String sSelectedApp;
    private static MediaSessionManager.OnActiveSessionsChangedListener sSessionListener;
    private static ExecutorService sSwitchExec;
    private static volatile long sWaitStartAt;
    private static volatile String sWaitingPkg;
    private static final String[] FALLBACK = {"com.netease.cloudmusic", "com.luna.music", "com.kugou.android", "cmccwm.mobilemusic", "cn.kuwo.player", "com.miui.player"};
    private static final Map<String, MediaBrowser> BROWSERS = new ConcurrentHashMap();
    private static final Map<String, Long> ATTEMPTS = new ConcurrentHashMap();
    private static final Map<String, Long> CAR_OPEN_AT = new ConcurrentHashMap();
    private static final Set<String> USER_KILLED = ConcurrentHashMap.newKeySet();
    private static final Map<String, Long> SWITCH_DROPPED = new ConcurrentHashMap();
    private static final Map<String, Long> OWN_KILL_AT = new ConcurrentHashMap();
    private static final Set<String> WOKE = ConcurrentHashMap.newKeySet();
    private static final Set<String> SESSION_MEDIA = ConcurrentHashMap.newKeySet();
    private static final Set<String> CANNOT_WAKE = ConcurrentHashMap.newKeySet();
    private static final long LOG_MAX_BYTES = 512 * 1024;
    private static final Object LOG_LOCK = new Object();
    private static final ExecutorService LOG_EXECUTOR = Executors.newSingleThreadExecutor(runnable -> {
        Thread thread = new Thread(runnable, "CarWithEnhance-Log");
        thread.setDaemon(true);
        return thread;
    });
    private static final long KILL_MIN_SETTLE_MS = 800;
    private static final long KILL_GRACE_MAX_WAIT_MS = 1200;
    private static final long[] RETRY_DELAYS = {400, KILL_MIN_SETTLE_MS, KILL_GRACE_MAX_WAIT_MS, 1800, 2500, 3200, 4000, 5000, 6000, 8000, 10000, 13000, 16000, 20000, 24000};

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

    static /* synthetic */ long access$1308() {
        long j = sRetryGen;
        sRetryGen = 1 + j;
        return j;
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
        hookMediaAppPref(classLoader);
        hookSessionAvailability(classLoader);
        hookExperienceApps(classLoader);
        hookOwnPrefs(classLoader);
    }

    private static void hookFiftyFps(final ClassLoader classLoader) {
        Context appContext = sAppCtx;
        if (appContext == null) {
            appContext = AppUtils.getAppContext(classLoader);
        }
        if (appContext == null || !"com.miui.carlink".equals(appContext.getPackageName())) {
            return;
        }
        setAppContext(appContext);
        try {
            XposedHelpers.findAndHookMethod(GSON_CLASS, classLoader, "fromJson", new Object[]{String.class, Type.class, new XC_MethodHook() { // from class: hun.mesh.carwithenhance.hook.AutoPlayAllHook.2
                protected void beforeHookedMethod(XC_MethodHook.MethodHookParam methodHookParam) {
                    Context context;
                    String strInsertFiftyFpsItem;
                    try {
                        Object obj = methodHookParam.args[0];
                        if (!(obj instanceof String)) {
                            return;
                        }
                        String str = (String) obj;
                        if (str.contains(AutoPlayAllHook.FPS_PREF_KEY) && !str.contains("\"50FPS\"") && str.contains("60FPS") && (context = AutoPlayAllHook.sAppCtx) != null && (strInsertFiftyFpsItem = AutoPlayAllHook.insertFiftyFpsItem(str)) != null) {
                            methodHookParam.args[0] = strInsertFiftyFpsItem;
                            AutoPlayAllHook.log(context, "车机端帧率菜单已加入 50FPS 档位");
                        }
                    } catch (Throwable th) {
                    }
                }
            }});
            log(null, "帧率菜单注入钩子已注册");
        } catch (Throwable th) {
            logErr(null, "帧率菜单注入钩子注册失败", th);
        }
        hookFpsConvert(classLoader);
        try {
            XposedHelpers.findAndHookMethod(FPS_LABEL_CLASS, classLoader, "l", new Object[]{Context.class, String.class, new XC_MethodHook() { // from class: hun.mesh.carwithenhance.hook.AutoPlayAllHook.3
                protected void afterHookedMethod(XC_MethodHook.MethodHookParam methodHookParam) {
                    try {
                        Context context = AutoPlayAllHook.sAppCtx;
                        if (context == null) {
                            return;
                        }
                        Object obj = methodHookParam.args[1];
                        if (!AutoPlayAllHook.FPS_PREF_KEY.equals(obj)) {
                            return;
                        }
                        Object objCallStaticMethod = XposedHelpers.callStaticMethod(XposedHelpers.findClass(AutoPlayAllHook.FPS_LABEL_CLASS, classLoader), "d", new Object[]{obj});
                        if ((objCallStaticMethod instanceof Integer) && ((Integer) objCallStaticMethod).intValue() == 3) {
                            methodHookParam.setResult(AutoPlayAllHook.fiftyFpsLabel(context, classLoader));
                        }
                    } catch (Throwable th2) {
                    }
                }
            }});
            log(null, "帧率菜单外层文字钩子已注册");
        } catch (Throwable th2) {
            logErr(null, "帧率菜单外层文字钩子注册失败", th2);
        }
    }

    private static void hookFpsConvert(ClassLoader classLoader) {
        try {
            XposedHelpers.findAndHookMethod(FPS_HELPER_NEW, classLoader, "d", new Object[]{new XC_MethodHook() { // from class: hun.mesh.carwithenhance.hook.AutoPlayAllHook.4
                protected void afterHookedMethod(XC_MethodHook.MethodHookParam methodHookParam) {
                    try {
                        Object result = methodHookParam.getResult();
                        if (!(result instanceof Integer)) {
                            return;
                        }
                        int iIntValue = ((Integer) result).intValue();
                        int fpsIndex = AutoPlayAllHook.readFpsIndex();
                        if (fpsIndex == 3 && iIntValue == 60) {
                            methodHookParam.setResult(50);
                        } else if (fpsIndex > 3 && iIntValue == fpsIndex) {
                            methodHookParam.setResult(60);
                        }
                    } catch (Throwable th) {
                    }
                }
            }});
            log(null, "帧率换算钩子已注册（新版 com.carwith.common.utils.d#d）");
        } catch (Throwable th) {
            log(null, "新版帧率换算入口不存在，尝试旧版入口");
            try {
                XposedHelpers.findAndHookMethod(FPS_HELPER, classLoader, "b", new Object[]{Integer.TYPE, new XC_MethodHook() { // from class: hun.mesh.carwithenhance.hook.AutoPlayAllHook.5
                    protected void beforeHookedMethod(XC_MethodHook.MethodHookParam methodHookParam) {
                        try {
                            Object obj = methodHookParam.args[0];
                            if (!(obj instanceof Integer)) {
                                return;
                            }
                            int iIntValue = ((Integer) obj).intValue();
                            if (iIntValue == 4) {
                                methodHookParam.setResult(60);
                            } else if (iIntValue == 3) {
                                methodHookParam.setResult(50);
                            }
                        } catch (Throwable th2) {
                        }
                    }
                }});
                log(null, "帧率换算钩子已注册（旧版 com.carwith.common.utils.s2#b）");
            } catch (Throwable th2) {
                logErr(null, "帧率换算钩子注册失败（新老入口都没找到）", th2);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static int readFpsIndex() {
        try {
            Context context = sAppCtx;
            if (context == null) {
                return -1;
            }
            return context.getSharedPreferences(FPS_PREF_FILE, 0).getInt(FPS_PREF_KEY, -1);
        } catch (Throwable th) {
            return -1;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static String fiftyFpsLabel(Context context, ClassLoader classLoader) {
        try {
            String string = context.getString(XposedHelpers.findClass("com.carwith.launcher.R$string", classLoader).getField("text_settings_fps_60").getInt(null));
            if (string != null && string.length() > 0) {
                return string.replace("60", "50");
            }
            return "50FPS";
        } catch (Throwable th) {
            return "50FPS";
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static String findFpsOptions(Object obj, int i) {
        if (obj == null || i > 4) {
            return null;
        }
        if (obj instanceof List) {
            Iterator it = ((List) obj).iterator();
            while (it.hasNext()) {
                String strFindFpsOptions = findFpsOptions(it.next(), i + 1);
                if (strFindFpsOptions != null) {
                    return strFindFpsOptions;
                }
            }
            return null;
        }
        if (!(obj instanceof Map)) {
            return null;
        }
        Map map = (Map) obj;
        Object obj2 = map.get("key");
        Object obj3 = map.get("value");
        if (FPS_PREF_KEY.equals(obj2) && (obj3 instanceof List)) {
            StringBuilder sb = new StringBuilder();
            Iterator it2 = ((List) obj3).iterator();
            while (it2.hasNext()) {
                Object next = it2.next();
                if (next instanceof Map) {
                    next = ((Map) next).get("title");
                }
                if (sb.length() > 0) {
                    sb.append('/');
                }
                sb.append(next);
            }
            return sb.toString();
        }
        Iterator it3 = map.values().iterator();
        while (it3.hasNext()) {
            String strFindFpsOptions2 = findFpsOptions(it3.next(), i + 1);
            if (strFindFpsOptions2 != null) {
                return strFindFpsOptions2;
            }
        }
        return null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static String insertFiftyFpsItem(String str) {
        int iLastIndexOf;
        try {
            int iIndexOf = str.indexOf("\"title\": \"60FPS\"");
            if (iIndexOf < 0) {
                iIndexOf = str.indexOf("\"title\":\"60FPS\"");
            }
            if (iIndexOf < 0) {
                iIndexOf = str.indexOf("60FPS");
            }
            if (iIndexOf < 0 || (iLastIndexOf = str.lastIndexOf(123, iIndexOf)) < 0) {
                return null;
            }
            return str.substring(0, iLastIndexOf) + "{\"type\":\"check\",\"title\":\"50FPS\",\"key\":\"user_setting_framerate\"}," + str.substring(iLastIndexOf);
        } catch (Throwable th) {
            return null;
        }
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
            }
        } catch (Throwable th) {
            logErr(null, "体验应用转普通列表的钩子注册失败", th);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static List<String> experiencePackages(ClassLoader classLoader) {
        int iIntValue;
        LinkedHashSet linkedHashSet = new LinkedHashSet();
        try {
            Object objCallStaticMethod = XposedHelpers.callStaticMethod(XposedHelpers.findClass(CARWITH_APP_MGR, classLoader), "O", new Object[0]);
            if (objCallStaticMethod == null) {
                return new ArrayList(linkedHashSet);
            }
            try {
                Object objCallMethod = XposedHelpers.callMethod(XposedHelpers.callStaticMethod(XposedHelpers.findClass("com.carwith.common.utils.g3", classLoader), "e", new Object[0]), "d", new Object[0]);
                if (!(objCallMethod instanceof Integer)) {
                    iIntValue = 0;
                } else {
                    iIntValue = ((Integer) objCallMethod).intValue();
                }
            } catch (Throwable th) {
                iIntValue = 0;
            }
            addAllStrings(linkedHashSet, XposedHelpers.callMethod(objCallStaticMethod, "R", new Object[]{Integer.valueOf(iIntValue)}));
            if (iIntValue != 0) {
                addAllStrings(linkedHashSet, XposedHelpers.callMethod(objCallStaticMethod, "R", new Object[]{0}));
            }
            return new ArrayList(linkedHashSet);
        } catch (Throwable th2) {
            logErr(null, "读取体验应用名单失败", th2);
            return new ArrayList<>();
        }
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
    public static int applyExperienceToNormal(Context context, ClassLoader classLoader, boolean z) {
        if (context == null || classLoader == null) {
            return 0;
        }
        try {
            List<String> listExperiencePackages = experiencePackages(classLoader);
            if (listExperiencePackages.isEmpty()) {
                return 0;
            }
            SharedPreferences sharedPreferences = context.getSharedPreferences(EXPERIENCE_PREF, 0);
            SharedPreferences sharedPreferences2 = context.getSharedPreferences("carwithenhance_hooksettings", 0);
            HashSet hashSet = new HashSet(sharedPreferences2.getStringSet(KEY_EXP_FLAGGED, new HashSet()));
            SharedPreferences.Editor editorEdit = null;
            boolean z2 = false;
            int i = 0;
            for (String str : listExperiencePackages) {
                if (z) {
                    if (sharedPreferences.getBoolean(str, false)) {
                        if (hashSet.add(str)) {
                            z2 = true;
                        }
                    } else if (!hashSet.contains(str)) {
                        if (editorEdit == null) {
                            editorEdit = sharedPreferences.edit();
                        }
                        editorEdit.putBoolean(str, z);
                        if (z && hashSet.add(str)) {
                            z2 = true;
                        }
                        i++;
                    }
                } else if (sharedPreferences.getBoolean(str, false)) {
                    if (editorEdit == null) {
                        editorEdit = sharedPreferences.edit();
                    }
                    editorEdit.putBoolean(str, z);
                    if (z) {
                        z2 = true;
                    }
                    i++;
                }
            }
            if (!z && !hashSet.isEmpty()) {
                hashSet.clear();
                z2 = true;
            }
            if (z2) {
                sharedPreferences2.edit().putStringSet(KEY_EXP_FLAGGED, new HashSet(hashSet)).apply();
            }
            if (editorEdit != null) {
                editorEdit.apply();
                log(context, (z ? "体验应用已放到应用列表: " : "已还原体验应用: ") + i + " 个 / 名单共 " + listExperiencePackages.size() + " 个");
            }
            return i;
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
                        if (context2 != null && !AutoPlayAllHook.blockedBySwitchDrop(AutoPlayAllHook.sResumedPkg) && AutoPlayAllHook.blockedByUserKill(context2, AutoPlayAllHook.sResumedPkg) && (str = AutoPlayAllHook.sResumedPkg) != null && str.equals(AutoPlayAllHook.sWaitingPkg)) {
                            AutoPlayAllHook.access$1308();
                            String unused = AutoPlayAllHook.sWaitingPkg = null;
                        }
                        String str2 = AutoPlayAllHook.sWaitingPkg;
                        if (str2 != null && list != null) {
                            for (MediaController mediaController : list) {
                                if (mediaController != null && str2.equals(mediaController.getPackageName())) {
                                    long jElapsedRealtime = SystemClock.elapsedRealtime();
                                    if (str2.equals(AutoPlayAllHook.sLastResumePkg) && jElapsedRealtime - AutoPlayAllHook.sLastResumeAt < 1500) {
                                        return;
                                    }
                                    if (mediaController.getPlaybackState() == null || mediaController.getPlaybackState().getState() != 0) {
                                        AutoPlayAllHook.log(AutoPlayAllHook.sAppCtx, "监听到 " + str2 + " 会话已出现，立即续播");
                                        AutoPlayAllHook.resumeCarWith(classLoader, str2);
                                        return;
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
            log(null, "媒体会话监听已注册");
        } catch (Throwable th) {
            logErr(null, "媒体会话监听注册失败", th);
        }
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
        try {
            Object[] objArrResolveColdStartAntiKill = GenericAutoPlayHooks.resolveColdStartAntiKill(classLoader);
            XposedBridge.hookAllMethods((Class) objArrResolveColdStartAntiKill[0], (String) objArrResolveColdStartAntiKill[1], new XC_MethodHook() { // from class: hun.mesh.carwithenhance.hook.AutoPlayAllHook.12
                protected void beforeHookedMethod(XC_MethodHook.MethodHookParam methodHookParam) {
                    try {
                        Object obj = methodHookParam.args[1];
                        if (!(obj instanceof String)) {
                            return;
                        }
                        String str = (String) obj;
                        Context context = AutoPlayAllHook.sAppCtx;
                        if (context != null && AutoPlayAllHook.isEnabled(context) && AutoPlayAllHook.isPrefEnabled(context, AutoPlayAllHook.KEY_COLDSTART_ANTIKILL, true) && !AutoPlayAllHook.blockedByUserKill(context, str) && !AutoPlayAllHook.blockedBySwitchDrop(str) && AutoPlayAllHook.isNowDesired(str) && AutoPlayAllHook.isTarget(classLoader, str) && AutoPlayAllHook.recentlyOpenedOnCar(str)) {
                            AutoPlayAllHook.log(context, "冷启动期间阻止 CarWith 结束应用，并补一次唤醒: " + str);
                            AutoPlayAllHook.sendMediaButton(context, str);
                            AutoPlayAllHook.requestModuleBind(context, str);
                            methodHookParam.setResult((Object) null);
                        }
                    } catch (Throwable th3) {
                    }
                }
            });
            log(null, "阻止误杀冷启动应用的钩子已注册");
        } catch (Throwable th3) {
            logErr(null, "阻止误杀冷启动应用的钩子注册失败", th3);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void preWakeForCarOpen(String str, String str2) {
        Context context;
        if (str == null) {
            return;
        }
        try {
            if (str.length() != 0 && (context = sAppCtx) != null && isEnabled(context) && !blockedByUserKill(context, str) && !blockedBySwitchDrop(str) && !isProcessRunning(context, str) && isTarget(sCl, str)) {
                log(context, str2 + " " + str + "，先把它后台唤醒");
                CAR_OPEN_AT.put(str, Long.valueOf(SystemClock.elapsedRealtime()));
                markSessionMedia(str);
                WOKE.add(str);
                sActiveWokePkg = str;
                sendMediaButton(context, str);
                requestModuleBind(context, str);
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
            if (str.length() == 0 || !isTarget(classLoader, str) || (objCallStaticMethod = XposedHelpers.callStaticMethod(XposedHelpers.findClass("xa.c", classLoader), "h", new Object[0])) == null) {
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
        sConnectGen++;
        sSelectedApp = readPrefMediaApp();
        SESSION_MEDIA.clear();
        WOKE.clear();
        sActiveWokePkg = null;
        sLastAcceptedPkg = null;
        sCurrentMediaPkg = null;
        sWaitingPkg = null;
        sLastSelectPkg = null;
        sRetryGen++;
        USER_KILLED.clear();
        OWN_KILL_AT.clear();
        SWITCH_DROPPED.clear();
        sLastStatePkg = null;
        sResumedPkg = null;
        sInitialResumeDone = false;
        sCarConnected = true;
        log(null, "CarWith 已连接");
        logAudioState(sAppCtx, "车机连接时");
        ClassLoader classLoader = sCl;
        Context context = sAppCtx;
        applyExperienceToNormal(context, classLoader, isPrefEnabled(context, KEY_EXPERIENCE_TO_NORMAL, true));
        scheduleExperienceApply(classLoader, 4000L);
        scheduleExperienceApply(classLoader, 12000L);
        preWakeLastUsedApp();
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

    private static void preWakeLastUsedApp() {
        String lastUsedMediaApp;
        if (sInitialResumeDone) {
            return;
        }
        sInitialResumeDone = true;
        try {
            Context context = sAppCtx;
            if (context != null && isEnabled(context) && (lastUsedMediaApp = readLastUsedMediaApp()) != null && lastUsedMediaApp.length() != 0 && isMediaApp(sCl, lastUsedMediaApp) && !isNeverKill(lastUsedMediaApp)) {
                log(context, "本次连接待续播: " + lastUsedMediaApp + " (CarWith自带续播开关=" + readPrefAutoPlay() + ")");
                learnCurrentMedia(lastUsedMediaApp);
                if (!isProcessRunning(context, lastUsedMediaApp) && isTarget(sCl, lastUsedMediaApp)) {
                    log(context, "连接即唤醒上次使用的播放器: " + lastUsedMediaApp);
                    CAR_OPEN_AT.put(lastUsedMediaApp, Long.valueOf(SystemClock.elapsedRealtime()));
                    markSessionMedia(lastUsedMediaApp);
                    WOKE.add(lastUsedMediaApp);
                    sActiveWokePkg = lastUsedMediaApp;
                    sendMediaButton(context, lastUsedMediaApp);
                    startPlayerService(context, lastUsedMediaApp);
                    requestModuleBind(context, lastUsedMediaApp);
                    startRetryLoop(sCl, lastUsedMediaApp);
                    return;
                }
                startRetryLoop(sCl, lastUsedMediaApp);
            }
        } catch (Throwable th) {
        }
    }

    private static String readLastUsedMediaApp() {
        String strSelectedApp = selectedApp();
        if (strSelectedApp != null && strSelectedApp.length() > 0) {
            return strSelectedApp;
        }
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

    private static String selectedApp() {
        if (isTarget(sCl, sSelectedApp)) {
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
        try {
            return sAppCtx.getSharedPreferences(PREF_MEDIA_APP, 0).getBoolean(KEY_CARWITH_AUTOPLAY, true);
        } catch (Throwable th) {
            return true;
        }
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
                if (i != 1) {
                    long jElapsedRealtime = SystemClock.elapsedRealtime();
                    if (!str.equals(sLastStatePkg) || jElapsedRealtime - sLastStateAt > 10000) {
                        sLastStatePkg = str;
                        sLastStateAt = jElapsedRealtime;
                        log(context, "车机界面状态刷新（" + sourceName(i) + "）：" + str + "，只记录不动播放器 | " + callerTrace(4));
                        return;
                    }
                    return;
                }
                if (SWITCH_DROPPED.remove(str) != null) {
                    log(context, "你在车机上重新选了 " + str + "，重新允许唤醒它");
                }
                if (USER_KILLED.remove(str)) {
                    log(context, "你在车机上重新选了 " + str + "，恢复自动唤醒");
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

    private static void runSwitchAsync(final String str) {
        try {
            if (sSwitchExec == null) {
                sSwitchExec = Executors.newSingleThreadExecutor(new ThreadFactory() { // from class: hun.mesh.carwithenhance.hook.AutoPlayAllHook.16
                    @Override // java.util.concurrent.ThreadFactory
                    public Thread newThread(Runnable runnable) {
                        Thread thread = new Thread(runnable, "carwith-enhance-switch");
                        thread.setDaemon(true);
                        return thread;
                    }
                });
            }
            sSwitchExec.execute(new Runnable() { // from class: hun.mesh.carwithenhance.hook.AutoPlayAllHook.17
                @Override // java.lang.Runnable
                public void run() {
                    try {
                        AutoPlayAllHook.doSwitch(AutoPlayAllHook.sAppCtx, str);
                    } catch (Throwable th) {
                        AutoPlayAllHook.logErr(AutoPlayAllHook.sAppCtx, "切换处理失败: " + str, th);
                    }
                }
            });
        } catch (Throwable th) {
            doSwitch(sAppCtx, str);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void doSwitch(Context context, String str) {
        if (context == null || str == null || !isEnabled(context)) {
            return;
        }
        if (!str.equals(sSelectedApp)) {
            log(context, "切换目标已变化，跳过对 " + str + " 的处理");
            return;
        }
        LinkedHashSet linkedHashSet = new LinkedHashSet();
        linkedHashSet.add(sCurrentMediaPkg);
        linkedHashSet.add(sActiveWokePkg);
        Iterator it = linkedHashSet.iterator();
        while (it.hasNext()) {
            killSwitchTarget(context, (String) it.next(), str, "车机端切换到 " + str);
        }
        String str2 = sActiveWokePkg;
        if (str2 != null && !str2.equals(str)) {
            sActiveWokePkg = null;
        }
        sCurrentMediaPkg = str;
        if (QQ_MUSIC.equals(str) || isProcessRunning(context, str) || !isTarget(sCl, str)) {
            return;
        }
        log(context, "车机端切换到 " + str + "，先后台唤醒它");
        WOKE.add(str);
        sActiveWokePkg = str;
        sendMediaButton(context, str);
        startPlayerService(context, str);
        requestModuleBind(context, str);
    }

    private static void killSwitchTarget(Context context, String str, String str2, String str3) {
        if (str == null) {
            return;
        }
        try {
            if (str.length() == 0 || str.equals(str2) || !isKillCandidate(str)) {
                return;
            }
            if (!isProcessRunning(context, str) && !hasSession(context, str) && !isPlaying(context, str)) {
                return;
            }
            WOKE.add(str);
            log(context, "切换清理，结束上一个播放器: " + str);
            releaseWoke(context, str, str3);
            markSwitchDropped(context, str, str2);
        } catch (Throwable th) {
            logErr(context, "切换清理失败: " + str, th);
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
        sLastAcceptedPkg = null;
        sActiveWokePkg = null;
        SESSION_MEDIA.clear();
        WOKE.clear();
        sRetryGen++;
        log(null, "连接已断开，仅复位续播状态");
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
        LinkedHashSet<String> linkedHashSet = new LinkedHashSet();
        linkedHashSet.addAll(SESSION_MEDIA);
        linkedHashSet.addAll(WOKE);
        linkedHashSet.add(sLastAcceptedPkg);
        linkedHashSet.add(sActiveWokePkg);
        linkedHashSet.add(sSelectedApp);
        linkedHashSet.add(readPrefMediaApp());
        linkedHashSet.add(readPrefMusicApp());
        linkedHashSet.add(carwithCurrentMediaApp());
        linkedHashSet.add(carwithLastUsedMediaApp());
        ArrayList arrayList = new ArrayList();
        for (String str : linkedHashSet) {
            if (isKillCandidate(str)) {
                if (CANNOT_WAKE.contains(str)) {
                    log(context, "断开处理: " + str + " 无法后台拉起，保留它以便下次秒续播");
                } else if (!isProcessRunning(context, str) && !hasSession(context, str) && !isPlaying(context, str)) {
                    log(context, "断开处理: " + str + " 已不在运行，跳过");
                } else {
                    arrayList.add(str);
                }
            }
        }
        for (String str2 : activeSessionPackages(context)) {
            if (!arrayList.contains(str2) && isKillCandidate(str2) && isPlaying(context, str2)) {
                arrayList.add(str2);
            }
        }
        return arrayList;
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
    public static void dumpAppList(final Context context, ClassLoader classLoader) {
        try {
            final Object objNewInstance = XposedHelpers.newInstance(XposedHelpers.findClass(GenericAutoPlayHooks.getExperienceClassName(), classLoader), new Object[]{(Application) context.getApplicationContext()});
            log(context, "应用列表自检: 开始构建");
            XposedHelpers.callMethod(objNewInstance, GenericAutoPlayHooks.getExperienceMethodName(), new Object[]{null});
            if (sHandler == null) {
                sHandler = new Handler(Looper.getMainLooper());
            }
            sHandler.postDelayed(new Runnable() { // from class: hun.mesh.carwithenhance.hook.AutoPlayAllHook.19
                @Override // java.lang.Runnable
                public void run() {
                    try {
                        int i = 0;
                        Object objCallMethod = XposedHelpers.callMethod(XposedHelpers.callMethod(objNewInstance, "j", new Object[0]), "getValue", new Object[0]);
                        if (objCallMethod == null) {
                            AutoPlayAllHook.log(context, "应用列表自检: 还没有数据");
                            return;
                        }
                        AutoPlayAllHook.log(context, "应用列表自检: 标题=" + XposedHelpers.getObjectField(objCallMethod, "b"));
                        Object objectField = XposedHelpers.getObjectField(objCallMethod, "a");
                        if (objectField instanceof List) {
                            for (Object obj : (List) objectField) {
                                StringBuilder sb = new StringBuilder();
                                if (obj instanceof List) {
                                    for (Object obj2 : (List) obj) {
                                        if (sb.length() > 0) {
                                            sb.append(',');
                                        }
                                        sb.append(XposedHelpers.getObjectField(obj2, "packageName"));
                                    }
                                }
                                AutoPlayAllHook.log(context, "应用列表自检: 第" + i + "页=" + ((Object) sb));
                                i++;
                            }
                        }
                        AutoPlayAllHook.log(context, "应用列表自检: 共 " + i + " 页");
                    } catch (Throwable th) {
                        AutoPlayAllHook.logErr(context, "应用列表自检失败", th);
                    }
                }
            }, 4000L);
        } catch (Throwable th) {
            logErr(context, "应用列表自检启动失败", th);
        }
    }

    private static void registerTestReceiver(final ClassLoader classLoader) {
        try {
            Context appContext = AppUtils.getAppContext(classLoader);
            if (appContext == null) {
                return;
            }
            setAppContext(appContext);
            appContext.registerReceiver(new BroadcastReceiver() { // from class: hun.mesh.carwithenhance.hook.AutoPlayAllHook.20
                @Override // android.content.BroadcastReceiver
                public void onReceive(final Context context, Intent intent) {
                    Class clsFindClass;
                    Class clsFindClass2;
                    Object objCallStaticMethod;
                    String strFindFpsOptions;
                    final String stringExtra = intent.getStringExtra(WakeReceiver.EXTRA_PKG);
                    String stringExtra2 = intent.getStringExtra("mode");
                    AutoPlayAllHook.log(context, "测试请求: " + stringExtra + " mode=" + stringExtra2);
                    if ("disconnect".equals(stringExtra2)) {
                        AutoPlayAllHook.onCarDisconnected();
                        return;
                    }
                    if ("lyric".equals(stringExtra2)) {
                        AutoPlayAllHook.dumpLyric(stringExtra, classLoader);
                        return;
                    }
                    if ("connect".equals(stringExtra2)) {
                        AutoPlayAllHook.onCarConnected();
                        return;
                    }
                    if ("select".equals(stringExtra2)) {
                        AutoPlayAllHook.onMediaAppEvent(stringExtra, 1);
                        AutoPlayAllHook.log(context, "模拟车机端用户切换: " + stringExtra);
                        return;
                    }
                    if ("state".equals(stringExtra2)) {
                        AutoPlayAllHook.onMediaAppEvent(stringExtra, 3);
                        AutoPlayAllHook.log(context, "模拟车机卡片刷新: " + stringExtra);
                        return;
                    }
                    if ("pemi".equals(stringExtra2)) {
                        try {
                            XposedHelpers.callStaticMethod(XposedHelpers.findClass("l6.e", classLoader), "m", new Object[]{context, stringExtra});
                            AutoPlayAllHook.log(context, "直接调用 p6.e.m: " + stringExtra);
                            return;
                        } catch (Throwable th) {
                            AutoPlayAllHook.logErr(context, "模拟选择失败", th);
                            return;
                        }
                    }
                    if ("expdump".equals(stringExtra2)) {
                        List listExperiencePackages = AutoPlayAllHook.experiencePackages(classLoader);
                        AutoPlayAllHook.log(context, "体验应用名单(" + listExperiencePackages.size() + "): " + listExperiencePackages);
                        AutoPlayAllHook.log(context, "已加入普通列表的标记: " + context.getSharedPreferences(AutoPlayAllHook.EXPERIENCE_PREF, 0).getAll());
                        return;
                    }
                    if ("expfix".equals(stringExtra2)) {
                        AutoPlayAllHook.log(context, "体验应用已放到应用列表: 改动 " + AutoPlayAllHook.applyExperienceToNormal(context, classLoader, true) + " 个");
                        return;
                    }
                    if ("expoff".equals(stringExtra2)) {
                        AutoPlayAllHook.log(context, "已还原体验应用标记: 改动 " + AutoPlayAllHook.applyExperienceToNormal(context, classLoader, false) + " 个");
                        return;
                    }
                    if ("applist".equals(stringExtra2)) {
                        AutoPlayAllHook.dumpAppList(context, classLoader);
                        return;
                    }
                    if ("fps".equals(stringExtra2)) {
                        try {
                            SharedPreferences sharedPreferences = context.getSharedPreferences(AutoPlayAllHook.FPS_PREF_FILE, 0);
                            int i = sharedPreferences.getInt(AutoPlayAllHook.FPS_PREF_KEY, -1);
                            try {
                                clsFindClass = XposedHelpers.findClass(AutoPlayAllHook.FPS_HELPER_NEW, classLoader);
                            } catch (Throwable th2) {
                                clsFindClass = null;
                            }
                            try {
                                clsFindClass2 = XposedHelpers.findClass(AutoPlayAllHook.FPS_HELPER, classLoader);
                            } catch (Throwable th3) {
                                clsFindClass2 = null;
                            }
                            StringBuilder sb = new StringBuilder();
                            int i2 = 0;
                            for (int i3 = 4; i2 <= i3; i3 = 4) {
                                sharedPreferences.edit().putInt(AutoPlayAllHook.FPS_PREF_KEY, i2).apply();
                                if (clsFindClass != null) {
                                    objCallStaticMethod = XposedHelpers.callStaticMethod(clsFindClass, "d", new Object[0]);
                                } else if (clsFindClass2 != null) {
                                    objCallStaticMethod = XposedHelpers.callStaticMethod(clsFindClass2, "b", new Object[]{Integer.valueOf(i2)});
                                } else {
                                    objCallStaticMethod = "?";
                                }
                                if (sb.length() > 0) {
                                    sb.append(", ");
                                }
                                sb.append(i2).append("->").append(objCallStaticMethod);
                                i2++;
                            }
                            if (i >= 0) {
                                sharedPreferences.edit().putInt(AutoPlayAllHook.FPS_PREF_KEY, i).apply();
                            }
                            AutoPlayAllHook.log(context, "帧率档位自检: " + ((Object) sb) + " (已恢复为 " + i + ")");
                            return;
                        } catch (Throwable th4) {
                            AutoPlayAllHook.logErr(context, "帧率档位自检失败", th4);
                            return;
                        }
                    }
                    if ("json".equals(stringExtra2)) {
                        try {
                            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(context.getAssets().open("settings_config.json"), "UTF-8"));
                            StringBuilder sb2 = new StringBuilder();
                            while (true) {
                                String line = bufferedReader.readLine();
                                if (line == null) {
                                    break;
                                } else {
                                    sb2.append(line).append('\n');
                                }
                            }
                            bufferedReader.close();
                            String strInsertFiftyFpsItem = AutoPlayAllHook.insertFiftyFpsItem(sb2.toString());
                            if (strInsertFiftyFpsItem == null) {
                                AutoPlayAllHook.log(context, "菜单自检: 未找到 60FPS 项");
                                return;
                            } else {
                                int iIndexOf = strInsertFiftyFpsItem.indexOf("50FPS");
                                AutoPlayAllHook.log(context, "菜单自检: 已插入 50FPS 档位, 周围=" + strInsertFiftyFpsItem.substring(Math.max(0, iIndexOf - 60), Math.min(strInsertFiftyFpsItem.length(), iIndexOf + 90)).replace('\n', ' '));
                                return;
                            }
                        } catch (Throwable th5) {
                            AutoPlayAllHook.logErr(context, "菜单自检失败", th5);
                            return;
                        }
                    }
                    if ("jsonparse".equals(stringExtra2)) {
                        try {
                            BufferedReader bufferedReader2 = new BufferedReader(new InputStreamReader(context.getAssets().open("settings_config.json"), "UTF-8"));
                            StringBuilder sb3 = new StringBuilder();
                            while (true) {
                                String line2 = bufferedReader2.readLine();
                                if (line2 == null) {
                                    break;
                                } else {
                                    sb3.append(line2).append('\n');
                                }
                            }
                            bufferedReader2.close();
                            Class clsFindClass3 = XposedHelpers.findClass(AutoPlayAllHook.GSON_CLASS, classLoader);
                            Object objInvoke = clsFindClass3.getMethod("fromJson", String.class, Type.class).invoke(XposedHelpers.newInstance(clsFindClass3, new Object[0]), sb3.toString(), List.class);
                            if (!(objInvoke instanceof List)) {
                                strFindFpsOptions = null;
                            } else {
                                Iterator it = ((List) objInvoke).iterator();
                                strFindFpsOptions = null;
                                while (it.hasNext() && (strFindFpsOptions = AutoPlayAllHook.findFpsOptions(it.next(), 0)) == null) {
                                }
                            }
                            StringBuilder sbAppend = new StringBuilder().append("菜单解析自检: 帧率选项 = ");
                            if (strFindFpsOptions == null) {
                                strFindFpsOptions = "(未找到)";
                            }
                            AutoPlayAllHook.log(context, sbAppend.append(strFindFpsOptions).toString());
                            return;
                        } catch (Throwable th6) {
                            AutoPlayAllHook.logErr(context, "菜单解析自检失败", th6);
                            return;
                        }
                    }
                    if ("items".equals(stringExtra2)) {
                        try {
                            Object objInvoke2 = AutoPlayAllHook.class.getClassLoader().loadClass("hun.mesh.carwithenhance.config.HookConfigs").getMethod("getAllItems", new Class[0]).invoke(null, new Object[0]);
                            StringBuilder sb4 = new StringBuilder();
                            if (objInvoke2 instanceof List) {
                                for (Object obj : (List) objInvoke2) {
                                    Object obj2 = obj.getClass().getField("key").get(obj);
                                    Object obj3 = obj.getClass().getField("title").get(obj);
                                    if (sb4.length() > 0) {
                                        sb4.append(" | ");
                                    }
                                    sb4.append(obj3).append('(').append(obj2).append(')');
                                }
                            }
                            AutoPlayAllHook.log(context, "设置项自检: " + ((Object) sb4));
                            return;
                        } catch (Throwable th7) {
                            AutoPlayAllHook.logErr(context, "设置项自检失败", th7);
                            return;
                        }
                    }
                    if ("autoplay".equals(stringExtra2)) {
                        try {
                            String stringExtra3 = intent.getStringExtra("value");
                            if (stringExtra3 != null) {
                                context.getSharedPreferences(AutoPlayAllHook.PREF_MEDIA_APP, 0).edit().putBoolean(AutoPlayAllHook.KEY_CARWITH_AUTOPLAY, Boolean.parseBoolean(stringExtra3)).apply();
                            }
                            AutoPlayAllHook.log(context, "续播开关自检: CarWith上车续播=" + AutoPlayAllHook.readPrefAutoPlay() + ", 本功能启用=" + AutoPlayAllHook.isEnabled(context));
                            return;
                        } catch (Throwable th8) {
                            AutoPlayAllHook.logErr(context, "续播开关自检失败", th8);
                            return;
                        }
                    }
                    if ("fpslabel".equals(stringExtra2)) {
                        try {
                            SharedPreferences sharedPreferences2 = context.getSharedPreferences(AutoPlayAllHook.FPS_PREF_FILE, 0);
                            int i4 = sharedPreferences2.getInt(AutoPlayAllHook.FPS_PREF_KEY, -1);
                            Class clsFindClass4 = XposedHelpers.findClass(AutoPlayAllHook.FPS_LABEL_CLASS, classLoader);
                            StringBuilder sb5 = new StringBuilder();
                            for (int i5 = 0; i5 <= 4; i5++) {
                                sharedPreferences2.edit().putInt(AutoPlayAllHook.FPS_PREF_KEY, i5).apply();
                                Object objCallStaticMethod2 = XposedHelpers.callStaticMethod(clsFindClass4, "l", new Object[]{context, AutoPlayAllHook.FPS_PREF_KEY});
                                if (sb5.length() > 0) {
                                    sb5.append(", ");
                                }
                                sb5.append(i5).append("->").append(objCallStaticMethod2);
                            }
                            if (i4 >= 0) {
                                sharedPreferences2.edit().putInt(AutoPlayAllHook.FPS_PREF_KEY, i4).apply();
                            }
                            AutoPlayAllHook.log(context, "帧率行文字自检: " + ((Object) sb5) + " (已恢复为 " + i4 + ")");
                            return;
                        } catch (Throwable th9) {
                            AutoPlayAllHook.logErr(context, "帧率行文字自检失败", th9);
                            return;
                        }
                    }
                    if ("setexp".equals(stringExtra2)) {
                        boolean z = !"false".equals(intent.getStringExtra("value"));
                        context.getSharedPreferences("carwithenhance_hooksettings", 0).edit().putBoolean(AutoPlayAllHook.KEY_EXPERIENCE_TO_NORMAL, z).apply();
                        AutoPlayAllHook.log(context, "已把开关置为: " + z);
                        return;
                    }
                    if ("mbonly".equals(stringExtra2)) {
                        AutoPlayAllHook.sendMediaButton(context, stringExtra);
                        AutoPlayAllHook.requestModuleBind(context, stringExtra);
                        return;
                    }
                    if ("startsvc".equals(stringExtra2)) {
                        AutoPlayAllHook.log(context, "测试启动播放服务: " + stringExtra + " -> " + AutoPlayAllHook.startPlayerService(context, stringExtra));
                        return;
                    }
                    if ("sessionplay".equals(stringExtra2)) {
                        AutoPlayAllHook.log(context, "测试会话播放: " + stringExtra + " -> " + AutoPlayAllHook.playViaSession(context, stringExtra));
                        return;
                    }
                    if ("pauseall".equals(stringExtra2)) {
                        AutoPlayAllHook.log(context, "测试断开前暂停: " + (AutoPlayAllHook.pauseBeforeDisconnect(context) ? "已暂停" : "无需暂停"));
                        return;
                    }
                    if ("killpkg".equals(stringExtra2)) {
                        AutoPlayAllHook.WOKE.add(stringExtra);
                        AutoPlayAllHook.log(context, "测试结束播放器: " + stringExtra);
                        AutoPlayAllHook.releaseWoke(context, stringExtra, "测试结束播放器");
                        AutoPlayAllHook.log(context, "测试结束播放器完成: " + stringExtra);
                        return;
                    }
                    if ("coldtest".equals(stringExtra2)) {
                        AutoPlayAllHook.forceStop(context, stringExtra, "冷启动测试");
                        AutoPlayAllHook.log(context, "冷启动测试开始: " + stringExtra);
                        long unused = AutoPlayAllHook.sWaitStartAt = SystemClock.elapsedRealtime();
                        AutoPlayAllHook.access$1308();
                        final ClassLoader classLoader2 = classLoader;
                        if (AutoPlayAllHook.sHandler == null) {
                            Handler unused2 = AutoPlayAllHook.sHandler = new Handler(Looper.getMainLooper());
                        }
                        AutoPlayAllHook.sHandler.postDelayed(new Runnable() { // from class: hun.mesh.carwithenhance.hook.AutoPlayAllHook.20.1
                            @Override // java.lang.Runnable
                            public void run() {
                                try {
                                    AutoPlayAllHook.wakeInBackground(context, stringExtra);
                                    AutoPlayAllHook.startRetryLoop(classLoader2, stringExtra);
                                } catch (Throwable th10) {
                                    AutoPlayAllHook.logErr(context, "冷启动测试失败", th10);
                                }
                            }
                        }, 1000L);
                        return;
                    }
                    if ("carshim".equals(stringExtra2)) {
                        try {
                            AutoPlayAllHook.log(context, "模拟轮转: tag=" + XposedHelpers.callStaticMethod(XposedHelpers.findClass("com.carwith.launcher.ams.UCarFragmentManager", classLoader), "getFragmentTagFromPackageName", new Object[]{stringExtra}));
                            XposedHelpers.callStaticMethod(XposedHelpers.findClass("com.carwith.common.utils.m", classLoader), "g", new Object[]{context, stringExtra});
                            AutoPlayAllHook.log(context, "模拟流程完成: " + stringExtra);
                            return;
                        } catch (Throwable th10) {
                            AutoPlayAllHook.logErr(context, "模拟失败", th10);
                            return;
                        }
                    }
                    AutoPlayAllHook.handle(stringExtra, classLoader);
                }
            }, new IntentFilter("hun.mesh.carwithenhance.action.TEST"));
            log(null, "测试入口已注册");
        } catch (Throwable th) {
            logErr(null, "测试入口注册失败", th);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void dumpLyric(String str, ClassLoader classLoader) {
        Context context = sAppCtx;
        try {
            MediaController mediaController = null;
            for (MediaController mediaController2 : ((MediaSessionManager) context.getSystemService("media_session")).getActiveSessions(null)) {
                if (mediaController2 != null && str.equals(mediaController2.getPackageName())) {
                    mediaController = mediaController2;
                    break;
                }
            }
            if (mediaController == null) {
                log(context, "[" + str + "] 没有活跃的媒体会话");
                return;
            }
            MediaMetadata metadata = mediaController.getMetadata();
            if (metadata == null) {
                log(context, "[" + str + "] 媒体会话没有任何元数据");
                return;
            }
            StringBuilder sb = new StringBuilder("[" + str + "] 元数据键: ");
            Iterator<String> it = metadata.keySet().iterator();
            while (it.hasNext()) {
                sb.append(it.next()).append(' ');
            }
            log(context, sb.toString());
            for (String str2 : metadata.keySet()) {
                String lowerCase = str2.toLowerCase(Locale.US);
                if (str2.startsWith("ucar.") || lowerCase.contains("lyric")) {
                    log(context, "   " + str2 + " = " + quote(metadata.getString(str2)));
                }
            }
        } catch (Throwable th) {
            logErr(context, "读取歌词状态失败: " + str, th);
        }
    }

    private static int length(Object obj) {
        if (obj instanceof String) {
            return ((String) obj).length();
        }
        return -1;
    }

    private static String quote(Object obj) {
        if (!(obj instanceof String)) {
            return "null";
        }
        String str = (String) obj;
        return (str.length() > 40 ? new StringBuilder().append("\"").append(str.substring(0, 40)).append("...\"") : new StringBuilder().append("\"").append(str).append("\"")).toString();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void releaseWoke(Context context, String str, String str2) {
        try {
            if (!WOKE.remove(str)) {
                return;
            }
            releaseCarwithBinding(str);
            releaseOwnBrowsers(str);
            gracefulStopBeforeKill(context, str);
            forceStop(context, str, str2);
        } catch (Throwable th) {
            logErr(context, "释放失败: " + str, th);
        }
    }

    private static void gracefulStopBeforeKill(Context context, String str) {
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
        } catch (Throwable th2) {
            logErr(context, "结束进程前的音频释放失败: " + str, th2);
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
        sRetryGen++;
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
        markSessionMedia(str);
        String strSelectedApp = selectedApp();
        if (strSelectedApp != null && strSelectedApp.length() > 0 && !strSelectedApp.equals(str) && !isProcessRunning(appContext, str) && !hasSession(appContext, str) && isTarget(classLoader, strSelectedApp)) {
            log(appContext, "CarWith 请求的 " + str + " 已结束，改用当前选中的 " + strSelectedApp);
            str = strSelectedApp;
        }
        String str2 = sLastAcceptedPkg;
        if (str2 == null || !str2.equals(str)) {
            sRetryGen++;
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
        if (!isTarget(classLoader, str)) {
            log(appContext, str + " 不在续播名单内，不处理");
            return;
        }
        if (hasSession(appContext, str) && isProcessRunning(appContext, str)) {
            log(appContext, str + " 已有媒体会话，交给 CarWith");
            sResumedPkg = str;
            learnCurrentMedia(str);
            resumeCarWith(classLoader, str);
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
            resumeCarWith(classLoader, str);
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
        sRetryGen++;
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

    private static boolean playingOtherMedia(Context context, String str) {
        try {
            for (String str2 : activeSessionPackages(context)) {
                if (!str2.equals(str) && isMediaApp(sCl, str2) && isPlaying(context, str2)) {
                    return true;
                }
            }
            return false;
        } catch (Throwable th) {
            return false;
        }
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
        requestModuleBind(context, str);
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
            try {
                if (str.length() != 0) {
                    String strGuessPlaybackService = PLAYBACK_SERVICES.get(str);
                    if (strGuessPlaybackService == null) {
                        strGuessPlaybackService = guessPlaybackService(context, str);
                    }
                    if (strGuessPlaybackService == null) {
                        return false;
                    }
                    Intent intent = new Intent();
                    intent.setComponent(new ComponentName(str, strGuessPlaybackService));
                    intent.putExtra("is_started_by_foreground", true);
                    try {
                        context.startForegroundService(intent);
                    } catch (Throwable th) {
                        context.startService(intent);
                    }
                    return true;
                }
            } catch (Throwable th2) {
                logErr(sAppCtx, "启动播放服务失败: " + str, th2);
                return false;
            }
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
    public static void requestModuleBind(Context context, String str) {
        try {
            Intent intent = new Intent(WakeReceiver.ACTION);
            intent.setComponent(new ComponentName(BuildConfig.APPLICATION_ID, "hun.mesh.carwithenhance.hook.WakeReceiver"));
            intent.putExtra(WakeReceiver.EXTRA_PKG, str);
            intent.addFlags(285212672);
            context.sendBroadcast(intent);
        } catch (Throwable th) {
            logErr(context, "请求后台绑定失败: " + str, th);
        }
    }

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
                connect(context, new ComponentName(str, str2));
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
                        log(context, "  候选: " + serviceInfo.name + " exported=" + serviceInfo.exported);
                        connect(context, new ComponentName(serviceInfo.packageName, serviceInfo.name));
                        return;
                    }
                }
            }
        } catch (Throwable th) {
            logErr(context, "后台绑定媒体服务失败: " + str, th);
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
            return context.getSharedPreferences(PREF_MEDIA_APP, 0).getBoolean(KEY_CARWITH_AUTOPLAY, true);
        } catch (Throwable th) {
            return true;
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

    private static void schedule(final ClassLoader classLoader, final String str, long j, final long j2, final boolean z, final boolean z2) {
        try {
            if (sHandler == null) {
                sHandler = new Handler(Looper.getMainLooper());
            }
            sHandler.postDelayed(new Runnable() { // from class: hun.mesh.carwithenhance.hook.AutoPlayAllHook.22
                @Override // java.lang.Runnable
                public void run() {
                    try {
                        if (j2 == AutoPlayAllHook.sRetryGen) {
                            Context context = AutoPlayAllHook.sAppCtx;
                            if (context != null) {
                                if (AutoPlayAllHook.isEnabled(context)) {
                                    if (!AutoPlayAllHook.blockedByUserKill(context, str)) {
                                        if (!AutoPlayAllHook.blockedBySwitchDrop(str)) {
                                            if (!AutoPlayAllHook.isPlaying(context, str)) {
                                                boolean zIsProcessRunning = AutoPlayAllHook.isProcessRunning(context, str);
                                                boolean zHasSession = AutoPlayAllHook.hasSession(context, str);
                                                if (!zIsProcessRunning && !AutoPlayAllHook.isPlaying(context, str)) {
                                                    if (z || zHasSession) {
                                                        AutoPlayAllHook.log(context, "等待 " + str + " 启动，补一次唤醒");
                                                        AutoPlayAllHook.sendMediaButton(context, str);
                                                        AutoPlayAllHook.startPlayerService(context, str);
                                                        AutoPlayAllHook.playViaSession(context, str);
                                                        AutoPlayAllHook.requestModuleBind(context, str);
                                                    }
                                                    if (z2) {
                                                        AutoPlayAllHook.CANNOT_WAKE.add(str);
                                                        AutoPlayAllHook.log(context, "后台无法拉起 " + str + "，断开时会保留它以免下次无法续播");
                                                    }
                                                    return;
                                                }
                                                AutoPlayAllHook.CANNOT_WAKE.remove(str);
                                                String unused = AutoPlayAllHook.sResumedPkg = str;
                                                if (zHasSession) {
                                                    AutoPlayAllHook.log(context, "会话已恢复，立即续播 " + str);
                                                } else {
                                                    AutoPlayAllHook.log(context, "进程已起来，立即下发续播 " + str);
                                                }
                                                AutoPlayAllHook.resumeCarWith(classLoader, str);
                                                return;
                                            }
                                            AutoPlayAllHook.access$1308();
                                            String unused2 = AutoPlayAllHook.sWaitingPkg = null;
                                            String unused3 = AutoPlayAllHook.sResumedPkg = str;
                                            AutoPlayAllHook.log(context, "续播已生效，停止轮询 " + str);
                                            return;
                                        }
                                        AutoPlayAllHook.access$1308();
                                        String unused4 = AutoPlayAllHook.sWaitingPkg = null;
                                        return;
                                    }
                                    AutoPlayAllHook.access$1308();
                                    String unused5 = AutoPlayAllHook.sWaitingPkg = null;
                                    return;
                                }
                                AutoPlayAllHook.access$1308();
                                String unused6 = AutoPlayAllHook.sWaitingPkg = null;
                                return;
                            }
                            return;
                        }
                        AutoPlayAllHook.log(null, "旧轮询已结束（目标变更或已续播）: " + str);
                    } catch (Throwable th) {
                        AutoPlayAllHook.logErr(AutoPlayAllHook.sAppCtx, "重试失败: " + str, th);
                    }
                }
            }, j);
        } catch (Throwable th) {
        }
    }
}
