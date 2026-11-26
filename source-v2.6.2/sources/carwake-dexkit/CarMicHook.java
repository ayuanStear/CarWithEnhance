import android.util.Log;

import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;

/** Requests the CarLife head-unit microphone while the XiaoAi VR channel is active. */
public final class CarMicHook {
    private static final String TAG = "CarWithX CarMic";
    private static final String TARGET_CLASS =
            "com.baidu.carlife.mixing.wrappers.SystemAudioCallbackWrapper";
    private static final String TARGET_METHOD = "onAudioStateChange";
    private static final String SDK_CLASS = "com.baidu.carlife.sdk.CarlifeCoreSDK";
    private static final int CMD_MIC_RECORD_END = 0x10023;
    private static final int CMD_MIC_RECORD_START = 0x10024;
    private static final int VR_CHANNEL = 3;
    private static volatile int lastState = -1;

    private CarMicHook() {
    }

    public static void install(final ClassLoader classLoader) {
        try {
            Class<?> target = XposedHelpers.findClass(TARGET_CLASS, classLoader);
            XposedHelpers.findAndHookMethod(
                    target,
                    TARGET_METHOD,
                    int.class,
                    int.class,
                    new XC_MethodHook() {
                        @Override
                        protected void afterHookedMethod(MethodHookParam param) {
                            if (param.args == null || param.args.length < 2) {
                                return;
                            }
                            int channel = ((Integer) param.args[0]).intValue();
                            int state = ((Integer) param.args[1]).intValue();
                            if (channel != VR_CHANNEL) {
                                return;
                            }
                            if (state == 1 && lastState != 1) {
                                lastState = 1;
                                sendCommand(classLoader, CMD_MIC_RECORD_START, "start");
                            } else if (state == 0) {
                                lastState = 0;
                                sendCommand(classLoader, CMD_MIC_RECORD_END, "stop");
                            }
                        }
                    });
            Log.i(TAG, "head-unit microphone hook installed");
            XposedBridge.log(TAG + ": head-unit microphone hook installed");
        } catch (Throwable throwable) {
            Log.e(TAG, "head-unit microphone hook failed", throwable);
            XposedBridge.log(TAG + ": head-unit microphone hook failed: " + throwable);
        }
    }

    private static void sendCommand(ClassLoader classLoader, int command, String reason) {
        try {
            Class<?> sdk = XposedHelpers.findClass(SDK_CLASS, classLoader);
            Object instance = XposedHelpers.callStaticMethod(sdk, "getInstance");
            if (instance == null) {
                XposedBridge.log(TAG + ": CarLifeCoreSDK instance unavailable");
                return;
            }
            XposedHelpers.callMethod(instance, "sendEmptyCMDToHu", Integer.valueOf(command));
            Log.i(TAG, "sent head-unit microphone " + reason + " command 0x"
                    + Integer.toHexString(command));
            XposedBridge.log(TAG + ": sent head-unit microphone " + reason
                    + " command 0x" + Integer.toHexString(command));
        } catch (Throwable throwable) {
            Log.e(TAG, "send head-unit microphone command failed", throwable);
            XposedBridge.log(TAG + ": send command failed: " + throwable);
        }
    }
}
