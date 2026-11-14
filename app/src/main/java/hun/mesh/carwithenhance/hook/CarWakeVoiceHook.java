package hun.mesh.carwithenhance.hook;

import android.app.AndroidAppHelper;
import android.app.Application;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.AudioPlaybackConfiguration;
import android.os.Handler;
import android.os.Looper;
import android.os.SystemClock;
import android.provider.Settings;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage;
import hun.mesh.carwithenhance.dexkit.StructuralMethodLookup;
import hun.mesh.carwithenhance.utils.XLog;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;

/**
 * CarWith 投屏时，把语音唤醒转为车机端小爱（等同点击小爱图标）。
 * 移植自 CarWake v1.18（com.eta.carwake.CarWakeHook）。
 *
 * 涉及三个进程：
 *  - com.miui.voiceassist / com.miui.voicetrigger：屏蔽 ucar_casting_state，避免手机端误触发；
 *  - com.miui.voicetrigger：投屏时把语音唤醒重定向到 CarWith 的 docker 小爱；
 *  - com.miui.carlink：语音交互期间的音频静音、ASR 超时重试、续轮接管。
 */
public final class CarWakeVoiceHook {

    private static final String ACTION_VA_WAKE =
            "com.miui.voicetrigger.ACTION_VOICE_TRIGGER_START_VOICEASSIST";
    private static final String CAR_ACTION_DOCKER_WAKE =
            "com.miui.carlink.ACTION_DOCKER_VOICE_ICON_START_VOICE_ASSIST";
    private static final String CAR_ACTION_FORCE_STOP_VOICE =
            "com.miui.carlink.ACTION_FORCE_STOP_VOICE_ASSIST";
    private static final String CAR_VOICE_SERVICE = "com.xiaomi.voiceassistant.VoiceService";

    private static final long CONVERSATION_GAP_MS = 120000L;
    private static final long DOCKER_START_GUARD_MS = 2500L;
    private static final long MAX_SILENCE_MS = 5000L;
    private static final long MUTE_WATCHDOG_MS = 10000L;
    private static final long TTS_RESTORE_DELAY_MS = 800L;

    private static final String KEY_UCAR_CASTING = "ucar_casting_state";
    private static final String PKG_CARLINK = "com.miui.carlink";
    private static final String PKG_VOICEASSIST = "com.miui.voiceassist";
    private static final String PKG_VOICETRIGGER = "com.miui.voicetrigger";
    private static final String[] BLIND_PKGS = {PKG_VOICEASSIST, PKG_VOICETRIGGER};

    private static volatile long sLastRetryMs = 0L;
    private static volatile int sRetryCount = 0;
    private static volatile long sLastDockerStartMs = 0L;
    private static volatile int sRoundCount = 0;
    private static volatile long sLastRoundActivityMs = 0L;
    private static volatile boolean sInTakeover = false;
    private static volatile boolean[] sPrevMuted = null;
    private static volatile long sWindowStartMs = 0L;
    private static volatile boolean sOthersPlaying = false;
    private static volatile boolean sPlaybackCbRegistered = false;
    private static volatile AudioManager.AudioPlaybackCallback sPlaybackCb = null;
    private static volatile boolean sSilenceCarAudio = false;
    private static volatile int sZeroMusic = 0;
    private static volatile int sSeenTts = 0;
    private static volatile Handler sMuteWatchdog = null;

    private static final Runnable MUTE_FORCE_RESTORE = new Runnable() {
        @Override
        public void run() {
            restoreOthers();
            XLog.i("CarWake: mute watchdog fired, audio restored");
        }
    };


    private static final Runnable TTS_RESTORE = new Runnable() {
        @Override
        public void run() {
            restoreOthers();
            XLog.i("CarWake: tts ended, audio restored");
        }
    };
    private CarWakeVoiceHook() {
    }

    public static void install(XC_LoadPackage.LoadPackageParam lpparam) {
        String pkg = lpparam.packageName;
        if (isBlindPkg(pkg)) {
            installBlindHook(lpparam);
        }
        if (PKG_VOICETRIGGER.equals(pkg)) {
            installRedirectHook(lpparam);
        }
        if (PKG_CARLINK.equals(pkg)) {
            installCarLinkHooks(lpparam);
        }
    }

    private static boolean isBlindPkg(String pkg) {
        for (String blind : BLIND_PKGS) {
            if (blind.equals(pkg)) {
                return true;
            }
        }
        return false;
    }

    private static Handler watchdog() {
        Handler handler = sMuteWatchdog;
        if (handler == null) {
            Looper mainLooper = Looper.getMainLooper();
            if (mainLooper != null) {
                handler = new Handler(mainLooper);
                sMuteWatchdog = handler;
            }
        }
        return handler;
    }

    private static Intent buildDockerIntent() {
        Intent intent = new Intent();
        intent.setClassName(PKG_CARLINK, CAR_VOICE_SERVICE);
        intent.setAction(CAR_ACTION_DOCKER_WAKE);
        intent.putExtra("fromTV", false);
        intent.putExtra("isCloseMic", false);
        intent.putExtra("queryOrigin", "VoiceButtonAtDocker");
        intent.putExtra("disableVad", false);
        intent.putExtra("need_tts", true);
        return intent;
    }

    private static void ensurePlaybackWatch(Context context, AudioManager audioManager) {
        if (sPlaybackCbRegistered) {
            return;
        }
        try {
            final int selfUid = context.getApplicationInfo().uid;
            sPlaybackCb = new AudioManager.AudioPlaybackCallback() {
                @Override
                public void onPlaybackConfigChanged(List<AudioPlaybackConfiguration> configs) {
                    boolean others = false;
                    try {
                        for (AudioPlaybackConfiguration config : configs) {
                            boolean active = (Boolean) XposedHelpers.callMethod(config, "isActive");
                            int uid = (Integer) XposedHelpers.callMethod(config, "getClientUid");
                            if (active && uid != selfUid) {
                                others = true;
                                break;
                            }
                        }
                    } catch (Throwable ignored) {
                    }
                    sOthersPlaying = others;
                }
            };
            Handler handler = watchdog();
            if (handler != null) {
                audioManager.registerAudioPlaybackCallback(sPlaybackCb, handler);
            } else {
                audioManager.registerAudioPlaybackCallback(sPlaybackCb, null);
            }
            sPlaybackCbRegistered = true;
            XLog.i("CarWake: playback watcher registered (selfUid=" + selfUid + ")");
        } catch (Throwable th) {
            XLog.e("CarWake: playback watcher failed", th);
        }
    }

    private static synchronized void muteOthers() {
        if (sPrevMuted != null) {
            return;
        }
        Application app = AndroidAppHelper.currentApplication();
        if (app == null) {
            return;
        }
        try {
            AudioManager audioManager = (AudioManager) app.getSystemService(Context.AUDIO_SERVICE);
            if (audioManager == null) {
                return;
            }
            ensurePlaybackWatch(app, audioManager);
            sSilenceCarAudio = true;
            sWindowStartMs = SystemClock.elapsedRealtime();
            sPrevMuted = new boolean[0];
            Handler handler = watchdog();
            if (handler != null) {
                handler.removeCallbacks(MUTE_FORCE_RESTORE);
                handler.postDelayed(MUTE_FORCE_RESTORE, MUTE_WATCHDOG_MS);
            }
            XLog.i("CarWake: listening starts, other audio muted");
        } catch (Throwable th) {
            XLog.e("CarWake: muteOthers failed", th);
        }
    }

    private static synchronized void restoreOthers() {
        if (sPrevMuted == null) {
            return;
        }
        sPrevMuted = null;
        sSilenceCarAudio = false;
        Handler handler = watchdog();
        if (handler != null) {
            handler.removeCallbacks(MUTE_FORCE_RESTORE);
        }
        XLog.i("CarWake: listening ends, car audio restored (mutedMusic=" + sZeroMusic
                + ", ttsFrames=" + sSeenTts + ")");
        sZeroMusic = 0;
        sSeenTts = 0;
    }

    private static void installBlindHook(final XC_LoadPackage.LoadPackageParam lpparam) {
        try {
            XC_MethodHook hook = new XC_MethodHook() {
                @Override
                protected void beforeHookedMethod(MethodHookParam param) {
                    try {
                        if (param.args != null && param.args.length >= 2
                                && KEY_UCAR_CASTING.equals(param.args[1])
                                && param.getResult() == null) {
                            param.setResult(0);
                            XLog.i("CarWake: mask ucar_casting_state=0 in " + lpparam.packageName);
                        }
                    } catch (Throwable ignored) {
                    }
                }
            };
            XposedHelpers.findAndHookMethod(
                    "android.provider.Settings$Global", lpparam.classLoader, "getInt",
                    ContentResolver.class, String.class, Integer.TYPE, hook);
            XposedHelpers.findAndHookMethod(
                    "android.provider.Settings$Global", lpparam.classLoader, "getInt",
                    ContentResolver.class, String.class, hook);
        } catch (Throwable th) {
            XLog.e("CarWake: blind hook failed in " + lpparam.packageName, th);
        }
    }

    private static void installRedirectHook(final XC_LoadPackage.LoadPackageParam lpparam) {
        try {
            XposedHelpers.findAndHookMethod(
                    "android.app.ContextImpl", lpparam.classLoader, "startForegroundService",
                    Intent.class, new XC_MethodHook() {
                        @Override
                        protected void beforeHookedMethod(MethodHookParam param) {
                            try {
                                if (param.args == null || param.args.length < 1
                                        || !(param.args[0] instanceof Intent)
                                        || !ACTION_VA_WAKE.equals(((Intent) param.args[0]).getAction())) {
                                    return;
                                }
                                Context context = (Context) param.thisObject;
                                String casting = null;
                                try {
                                    casting = Settings.Global.getString(
                                            context.getContentResolver(), KEY_UCAR_CASTING);
                                } catch (Throwable ignored) {
                                }
                                if (!"1".equals(casting)) {
                                    return;
                                }
                                if (context.startService(buildDockerIntent()) != null) {
                                    XLog.i("CarWake: casting, voice wake redirected to CarWith docker voice");
                                    param.setResult(new ComponentName(PKG_CARLINK, CAR_VOICE_SERVICE));
                                } else {
                                    XLog.i("CarWake: casting, docker voice unavailable, fallback to phone XiaoAi");
                                }
                            } catch (Throwable th) {
                                XLog.e("CarWake: redirect failed, fallback to phone XiaoAi", th);
                            }
                        }
                    });
            XLog.i("CarWake: redirect hook installed in " + lpparam.packageName);
        } catch (Throwable th) {
            XLog.e("CarWake: redirect hook failed", th);
        }
    }

    private static Class<?> ownerOf(StructuralMethodLookup lookup,
                                    String literal,
                                    String returnType,
                                    int parameterCount,
                                    String fallbackName,
                                    ClassLoader loader) {
        List<StructuralMethodLookup.Target> targets = lookup.find(
                StructuralMethodLookup.QuerySpec.containing(
                        literal, returnType, parameterCount));
        if (targets != null && targets.size() == 1) {
            return targets.get(0).owner;
        }
        try {
            return XposedHelpers.findClass(fallbackName, loader);
        } catch (Throwable ignored) {
            return null;
        }
    }

    private static void installCarLinkHooks(final XC_LoadPackage.LoadPackageParam lpparam) {
        final String pkg = lpparam.packageName;
        String apkPath = null;
        try {
            if (lpparam.appInfo != null) {
                apkPath = lpparam.appInfo.sourceDir;
            }
        } catch (Throwable ignored) {
        }
        StructuralMethodLookup lookup = null;
        try {
            lookup = new StructuralMethodLookup(apkPath, lpparam.classLoader);

            // 结构定位 ASR 引擎类：优先按方法体字面量，失败回退稳定类名。
            Class<?> engineClass = ownerOf(lookup, "startSpeechRecognize", "void", 3,
                    "com.xiaomi.ai.i", lpparam.classLoader);
            if (engineClass == null) {
                XLog.e("CarWake: ASR 引擎类未找到，跳过语音钩子");
            } else {
                // ASR 开始：静音其他音频。
                try {
                    XposedBridge.hookAllMethods(engineClass, "T0", new XC_MethodHook() {
                        @Override
                        protected void beforeHookedMethod(MethodHookParam param) {
                            muteOthers();
                        }
                    });
                    XLog.i("CarWake: asr-start mute hook installed in " + pkg);
                } catch (Throwable th) {
                    XLog.e("CarWake: asr-start mute hook failed", th);
                }

                // ASR 结束：恢复音频。
                XC_MethodHook stopHook = new XC_MethodHook() {
                    @Override
                    protected void afterHookedMethod(MethodHookParam param) {
                        restoreOthers();
                    }
                };
                for (String methodName : new String[]{"U0", "V", "c0"}) {
                    try {
                        XposedBridge.hookAllMethods(engineClass, methodName, stopHook);
                    } catch (Throwable th) {
                        XLog.e("CarWake: asr-stop hook " + methodName + " failed", th);
                    }
                }

                // ASR 超时重试。
                try {
                    XposedBridge.hookAllMethods(engineClass, "V", new XC_MethodHook() {
                        @Override
                        protected void afterHookedMethod(MethodHookParam param) {
                            try {
                                Application app = AndroidAppHelper.currentApplication();
                                if (app == null) {
                                    return;
                                }
                                try {
                                    if (Settings.Global.getInt(
                                            app.getContentResolver(), KEY_UCAR_CASTING, 0) != 1) {
                                        return;
                                    }
                                    long now = SystemClock.elapsedRealtime();
                                    if (now - sLastRetryMs > MUTE_WATCHDOG_MS) {
                                        sRetryCount = 0;
                                    }
                                    if (now - sLastRetryMs < 8000L) {
                                        return;
                                    }
                                    if (now - sLastDockerStartMs >= DOCKER_START_GUARD_MS) {
                                        if (sRetryCount < 2) {
                                            sLastRetryMs = now;
                                            sLastDockerStartMs = now;
                                            sRetryCount++;
                                            XLog.i("CarWake: asr timeout during casting, auto retry listen #"
                                                    + sRetryCount + " -> " + app.startService(buildDockerIntent()));
                                            return;
                                        }
                                        XLog.i("CarWake: asr timeout, retry limit reached, give up this turn");
                                        return;
                                    }
                                    XLog.i("CarWake: asr timeout, but a new round just started "
                                            + (now - sLastDockerStartMs) + "ms ago, skip retry");
                                } catch (Throwable ignored) {
                                }
                            } catch (Throwable th) {
                                XLog.e("CarWake: asr-timeout retry failed", th);
                            }
                        }
                    });
                    XLog.i("CarWake: asr-timeout retry hook installed in " + pkg);
                } catch (Throwable th) {
                    XLog.e("CarWake: asr-timeout retry hook failed", th);
                }
            }

            // EasyConn 车载音频：语音交互时把 MUSIC 帧清零，检测 TTS 帧。
            try {
                XposedBridge.hookAllMethods(
                        XposedHelpers.findClass(
                                "net.easyconn.carman.sdk_communication.EcSdkManager",
                                lpparam.classLoader),
                        "processAudio", new XC_MethodHook() {
                            @Override
                            protected void beforeHookedMethod(MethodHookParam param) {
                                try {
                                    if (!sSilenceCarAudio || param.args == null || param.args.length < 2) {
                                        return;
                                    }
                                    Object type = param.args[0];
                                    Object data = param.args[1];
                                    if (!(data instanceof byte[])) {
                                        return;
                                    }
                                    String typeText = String.valueOf(type);
                                    if (typeText.contains("MUSIC")) {
                                        boolean silence = true;
                                        if (SystemClock.elapsedRealtime() - sWindowStartMs >= MAX_SILENCE_MS) {
                                            silence = false;
                                        }
                                        if (sOthersPlaying && silence) {
                                            Arrays.fill((byte[]) data, (byte) 0);
                                            sZeroMusic++;
                                        }
                                    } else if (typeText.contains("TTS")) {
                                        sSeenTts++;
                                        Handler handler = watchdog();
                                        if (handler != null) {
                                            handler.removeCallbacks(TTS_RESTORE);
                                            handler.postDelayed(TTS_RESTORE, TTS_RESTORE_DELAY_MS);
                                        }
                                    }
                                } catch (Throwable ignored) {
                                }
                            }
                        });
                XLog.i("CarWake: easyconn car-audio silence hook installed in " + pkg);
            } catch (Throwable th) {
                XLog.e("CarWake: easyconn silence hook failed", th);
            }

            // 结构定位车载音频静音类：优先按方法体字面量，失败回退稳定类名。
            Class<?> audioClass = ownerOf(lookup, "start send music data to carlife", "void", 1,
                    "m1.b$f", lpparam.classLoader);
            if (audioClass == null) {
                audioClass = ownerOf(lookup, "current vr is active", "void", 1,
                        "m1.b$f", lpparam.classLoader);
            }
            if (audioClass == null) {
                XLog.e("CarWake: 车载音频类未找到，跳过音频静音钩子");
            } else {
                XC_MethodHook silenceHook = new XC_MethodHook() {
                    @Override
                    protected void beforeHookedMethod(MethodHookParam param) {
                        try {
                            if (sSilenceCarAudio && param.args != null && param.args.length >= 1
                                    && param.args[0] instanceof byte[]
                                    && SystemClock.elapsedRealtime() - sWindowStartMs < MAX_SILENCE_MS) {
                                Arrays.fill((byte[]) param.args[0], (byte) 0);
                            }
                        } catch (Throwable ignored) {
                        }
                    }
                };
                try {
                    XposedBridge.hookAllMethods(audioClass, "a", silenceHook);
                    XposedBridge.hookAllMethods(audioClass, "b", silenceHook);
                    XLog.i("CarWake: car-audio silence hooks installed in " + pkg);
                } catch (Throwable th) {
                    XLog.e("CarWake: car-audio silence hooks failed", th);
                }
            }

            // 续轮接管：120 秒内连续启动 docker 小爱时，阻止 CarWith 重启 VoiceService。
            try {
                XposedHelpers.findAndHookMethod(
                        "android.app.ContextImpl", lpparam.classLoader, "startService",
                        Intent.class, new XC_MethodHook() {
                            @Override
                            protected void beforeHookedMethod(MethodHookParam param) {
                                try {
                                    if (param.args == null || param.args.length < 1
                                            || !(param.args[0] instanceof Intent)
                                            || !CAR_ACTION_DOCKER_WAKE.equals(((Intent) param.args[0]).getAction())) {
                                        return;
                                    }
                                    final Context context = (Context) param.thisObject;
                                    long now = SystemClock.elapsedRealtime();
                                    boolean takeover = !sInTakeover && sRoundCount > 0
                                            && now - sLastRoundActivityMs < CONVERSATION_GAP_MS;
                                    sLastRoundActivityMs = now;
                                    sLastDockerStartMs = now;
                                    if (!takeover) {
                                        sRoundCount = 1;
                                        XLog.i("CarWake: docker voice first round start");
                                        return;
                                    }
                                    sInTakeover = true;
                                    param.setResult(new ComponentName(PKG_CARLINK, CAR_VOICE_SERVICE));
                                    XLog.i("CarWake: taken over continuation round (CarWith restart suppressed)");
                                    Intent stop = new Intent();
                                    stop.setClassName(PKG_CARLINK, CAR_VOICE_SERVICE);
                                    stop.setAction(CAR_ACTION_FORCE_STOP_VOICE);
                                    try {
                                        context.startService(stop);
                                    } catch (Throwable th) {
                                        XLog.e("CarWake: takeover force-stop failed", th);
                                    }
                                    Runnable restart = new Runnable() {
                                        @Override
                                        public void run() {
                                            try {
                                                context.startService(buildDockerIntent());
                                                XLog.i("CarWake: takeover round started (clean)");
                                            } catch (Throwable th) {
                                                XLog.e("CarWake: takeover start failed", th);
                                                sInTakeover = false;
                                            }
                                        }
                                    };
                                    Handler handler = watchdog();
                                    if (handler != null) {
                                        handler.postDelayed(restart, 420L);
                                    } else {
                                        restart.run();
                                    }
                                } catch (Throwable ignored) {
                                }
                            }
                        });
                XLog.i("CarWake: continuation takeover hook installed in " + pkg);
            } catch (Throwable th) {
                XLog.e("CarWake: continuation takeover hook failed", th);
            }
        } catch (Throwable th) {
            XLog.e("CarWake: carlink hooks failed", th);
        } finally {
            if (lookup != null) {
                try {
                    lookup.close();
                } catch (Throwable ignored) {
                }
            }
        }
    }
}






