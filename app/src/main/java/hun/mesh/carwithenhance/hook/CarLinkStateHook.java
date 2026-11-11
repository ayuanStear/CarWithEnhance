package hun.mesh.carwithenhance.hook;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Looper;
import android.os.Process;
import android.provider.Settings;
import android.view.Surface;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedHelpers;
import hun.mesh.carwithenhance.config.HookConfigs;
import hun.mesh.carwithenhance.dexkit.CarWithDexKitManager;
import hun.mesh.carwithenhance.utils.XLog;
import java.util.List;

public class CarLinkStateHook implements IHook {
    private static final String KEY_CONNECTED = "carwithenhance_is_connected";
    private static final String MEDIA_PREFS = "file_prefer_app_091703";
    private static final String PREFER_MEDIA_APP = "prefer_media_app";

    private final Context mContext;
    private final Handler mHandler = new Handler(Looper.getMainLooper());
    private volatile boolean mConnected;

    public CarLinkStateHook(Context context) {
        Context appContext = context == null ? null : context.getApplicationContext();
        this.mContext = appContext != null ? appContext : context;
    }

    private boolean isDisconnectStopEnabled() {
        Context context = this.mContext;
        if (context == null) {
            return false;
        }
        SharedPreferences preferences = context.getSharedPreferences(HookConfigs.PREFS_FILE, 0);
        return preferences.getBoolean(
                HookConfigs.DISCONNECT_KILL_MUSIC.key,
                HookConfigs.DISCONNECT_KILL_MUSIC.defaultValue);
    }

    private int readDelaySeconds() {
        Context context = this.mContext;
        if (context == null) {
            return HookConfigs.DEFAULT_DISCONNECT_KILL_DELAY_SEC;
        }
        SharedPreferences preferences = context.getSharedPreferences(HookConfigs.PREFS_FILE, 0);
        return HookConfigs.clampDelaySeconds(preferences.getInt(
                HookConfigs.DISCONNECT_KILL_DELAY_KEY,
                HookConfigs.DEFAULT_DISCONNECT_KILL_DELAY_SEC));
    }

    private List<String> collectMusicTargets() {
        Context context = this.mContext;
        if (context == null) {
            return null;
        }
        try {
            return (List<String>) XposedHelpers.callStaticMethod(
                    AutoPlayAllHook.class, "collectDisconnectTargets", context);
        } catch (Throwable th) {
            XLog.e("[CarLinkStateHook] 收集断连音乐目标失败", th);
            return null;
        }
    }

    private String preferredMediaApp() {
        Context context = this.mContext;
        if (context == null) {
            return "";
        }
        return context.getSharedPreferences(MEDIA_PREFS, 0).getString(PREFER_MEDIA_APP, "");
    }

    private String joinTargets(List<String> targets) {
        StringBuilder builder = new StringBuilder();
        if (targets != null) {
            for (String packageName : targets) {
                if (packageName == null || packageName.isEmpty()) {
                    continue;
                }
                if (builder.length() > 0) {
                    builder.append(',');
                }
                builder.append(packageName);
            }
        }
        String preferred = preferredMediaApp();
        if (builder.length() == 0 && preferred != null && !preferred.isEmpty()) {
            builder.append(preferred);
        }
        return builder.toString();
    }

    private void publishDisconnectStopCommand(String targets, int delaySeconds) {
        Context context = this.mContext;
        if (context == null) {
            return;
        }
        try {
            long stopAt = System.currentTimeMillis() + (delaySeconds * 1000L);
            Settings.Global.putString(
                    context.getContentResolver(), HookConfigs.DISCONNECT_TARGETS_KEY, targets);
            Settings.Global.putLong(
                    context.getContentResolver(), HookConfigs.DISCONNECT_STOP_AT_KEY, stopAt);
        } catch (Throwable th) {
            XLog.e("[CarLinkStateHook] 发布断连停止任务失败", th);
        }
    }

    private void killCarWithProcessSoon() {
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Process.killProcess(Process.myPid());
            }
        }, 250L);
    }

    private boolean isGloballyConnected() {
        Context context = this.mContext;
        if (context == null) {
            return false;
        }
        try {
            return Settings.Global.getInt(context.getContentResolver(), KEY_CONNECTED, 0) == 1;
        } catch (Throwable th) {
            return false;
        }
    }

    private void updateGlobalState(int connected) {
        mConnected = connected == 1;
        Context context = this.mContext;
        if (context == null) {
            XLog.e("[CarLinkStateHook] 无法更新全局状态，Context 未初始化");
            return;
        }
        try {
            Settings.Global.putInt(context.getContentResolver(), KEY_CONNECTED, connected);
        } catch (Throwable th) {
            XLog.e("[CarLinkStateHook] 更新 Settings 失败，请检查是否有 WRITE_SECURE_SETTINGS 权限", th);
        }
    }

    private void handleDisconnect() {
        if (!isDisconnectStopEnabled()) {
            return;
        }
        int delaySeconds = readDelaySeconds();
        publishDisconnectStopCommand(joinTargets(collectMusicTargets()), delaySeconds);
        killCarWithProcessSoon();
    }

    @Override
    public void onHook(ClassLoader classLoader) {
        CarWithDexKitManager manager = CarWithDexKitManager.INSTANCE;
        String castControllerClass = manager.getCastControllerClass();
        if (castControllerClass == null || castControllerClass.isEmpty()) {
            XLog.e("[CarLinkStateHook] 警告：Dexkit中未找到 CastController 目标类名");
            return;
        }
        try {
            final String connectMethod = manager.getCastControllerConnectMethod();
            final Class<?> intClass = Integer.TYPE;
            XposedHelpers.findAndHookMethod(castControllerClass, classLoader, connectMethod,
                    new Object[]{Surface.class, intClass, intClass, intClass, intClass, intClass, intClass,
                    new XC_MethodHook() {
                        @Override
                        protected void afterHookedMethod(MethodHookParam param) {
                            mConnected = true;
                            updateGlobalState(1);
                        }
                    }});
        } catch (Throwable th) {
            XLog.e("[CarLinkStateHook] Hook 连接方法失败", th);
        }
        try {
            XposedHelpers.findAndHookMethod(castControllerClass, classLoader,
                    manager.getCastControllerDisconnectMethod(), new Object[]{new XC_MethodHook() {
                        @Override
                        protected void afterHookedMethod(MethodHookParam param) {
                            updateGlobalState(0);
                            if (!mConnected && !isGloballyConnected()) {
                                handleDisconnect();
                            }
                        }
                    }});
        } catch (Throwable th) {
            XLog.e("[CarLinkStateHook] Hook 断开方法失败", th);
        }
    }
}
