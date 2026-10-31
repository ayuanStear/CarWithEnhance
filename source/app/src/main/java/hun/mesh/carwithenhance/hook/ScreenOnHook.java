package hun.mesh.carwithenhance.hook;

import android.content.Context;
import android.content.SharedPreferences;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XC_MethodReplacement;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;
import hun.mesh.carwithenhance.config.HookConfigs;
import hun.mesh.carwithenhance.config.HookItem;
import hun.mesh.carwithenhance.dexkit.CarWithDexKitManager;
import hun.mesh.carwithenhance.utils.AppUtils;
import hun.mesh.carwithenhance.utils.XLog;

/* JADX INFO: compiled from: r8-map-id-2844820780df513d20833d92dac6e5f4b7fbbd08283aea6743910c9549ac13d9 */
/* JADX INFO: loaded from: classes.dex */
public class ScreenOnHook implements IHook {
    @Override // hun.mesh.carwithenhance.hook.IHook
    public void onHook(final ClassLoader classLoader) {
        try {
            CarWithDexKitManager carWithDexKitManager = CarWithDexKitManager.INSTANCE;
            String carlinkStateMachineClass = carWithDexKitManager.getCarlinkStateMachineClass();
            String screenOnMethod = carWithDexKitManager.getScreenOnMethod();
            if (carlinkStateMachineClass != null && !carlinkStateMachineClass.isEmpty() && screenOnMethod != null && !screenOnMethod.isEmpty()) {
                XposedBridge.hookAllMethods(XposedHelpers.findClass(carlinkStateMachineClass, classLoader), screenOnMethod, new XC_MethodReplacement() { // from class: hun.mesh.carwithenhance.hook.ScreenOnHook.1
                    public Object replaceHookedMethod(XC_MethodHook.MethodHookParam methodHookParam) {
                        Context appContext = AppUtils.getAppContext(classLoader);
                        if (appContext != null) {
                            SharedPreferences sharedPreferences = appContext.getSharedPreferences(HookConfigs.PREFS_FILE, 0);
                            HookItem hookItem = HookConfigs.CARWITH_KEEPALIVE;
                            if (!sharedPreferences.getBoolean(hookItem.key, hookItem.defaultValue)) {
                                try {
                                    return XposedBridge.invokeOriginalMethod(methodHookParam.method, methodHookParam.thisObject, methodHookParam.args);
                                } catch (Throwable th) {
                                    XLog.e("❌ [唤醒阻止] 调用原方法失败", th);
                                    return null;
                                }
                            }
                        }
                        XLog.i(">> [唤醒阻止] 拦截到 20 分钟亮屏唤醒任务，已禁用");
                        return null;
                    }
                });
                XLog.i(">> [唤醒阻止] 禁用20分钟自动亮屏 Hook 注入成功！");
                return;
            }
            XLog.e("ScreenOnHook 动态目标未找到，跳过 Hook");
        } catch (Throwable th) {
            XLog.e("❌ [唤醒阻止] 禁用20分钟自动亮屏 Hook 失败: ", th);
        }
    }
}
