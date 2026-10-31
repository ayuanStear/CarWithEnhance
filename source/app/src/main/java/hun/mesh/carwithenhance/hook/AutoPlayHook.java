package hun.mesh.carwithenhance.hook;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XC_MethodReplacement;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;
import hun.mesh.carwithenhance.config.HookConfigs;
import hun.mesh.carwithenhance.config.HookItem;
import hun.mesh.carwithenhance.dexkit.CarWithDexKitManager;
import hun.mesh.carwithenhance.utils.AppUtils;
import hun.mesh.carwithenhance.utils.XLog;
import java.lang.reflect.Field;
import java.util.List;

/* JADX INFO: compiled from: r8-map-id-2844820780df513d20833d92dac6e5f4b7fbbd08283aea6743910c9549ac13d9 */
/* JADX INFO: loaded from: classes.dex */
public class AutoPlayHook implements IHook {
    @Override // hun.mesh.carwithenhance.hook.IHook
    public void onHook(final ClassLoader classLoader) {
        try {
            CarWithDexKitManager carWithDexKitManager = CarWithDexKitManager.INSTANCE;
            String autoPlayClass = carWithDexKitManager.getAutoPlayClass();
            String autoPlayMethod = carWithDexKitManager.getAutoPlayMethod();
            if (autoPlayClass != null && !autoPlayClass.isEmpty()) {
                Class cls = Boolean.TYPE;
                Class cls2 = Integer.TYPE;
                XposedHelpers.findAndHookMethod(autoPlayClass, classLoader, autoPlayMethod, new Object[]{cls, cls2, List.class, cls2, String.class, Bundle.class, new XC_MethodReplacement() { // from class: hun.mesh.carwithenhance.hook.AutoPlayHook.1
                    public Object replaceHookedMethod(XC_MethodHook.MethodHookParam methodHookParam) {
                        try {
                            Context appContext = AppUtils.getAppContext(classLoader);
                            if (appContext != null) {
                                SharedPreferences sharedPreferences = appContext.getSharedPreferences(HookConfigs.PREFS_FILE, 0);
                                HookItem hookItem = HookConfigs.AUTOPLAY;
                                if (!sharedPreferences.getBoolean(hookItem.key, hookItem.defaultValue)) {
                                    XLog.i(">> [续播优化] QQ续播优化关闭，走默认逻辑");
                                    return invokeOriginalSafely(methodHookParam);
                                }
                            }
                            String targetPackage = null;
                            for (Field field : methodHookParam.thisObject.getClass().getDeclaredFields()) {
                                field.setAccessible(true);
                                if (field.getType() == String.class) {
                                    targetPackage = (String) field.get(methodHookParam.thisObject);
                                    break;
                                }
                            }
                            if ("com.tencent.qqmusic".equals(targetPackage)) {
                                XLog.i(">> [续播优化] 拦截 QQ 音乐列表请求，丢弃指定歌单，保留监听器");
                                return null;
                            }
                            XLog.i(targetPackage + "！！跳过，放行非 QQ 音乐的合法回调");
                            return invokeOriginalSafely(methodHookParam);
                        } catch (Throwable th) {
                            XLog.e("❌[续播优化] 替换方法执行异常", th);
                            return null;
                        }
                    }

                    private Object invokeOriginalSafely(XC_MethodHook.MethodHookParam methodHookParam) {
                        try {
                            return XposedBridge.invokeOriginalMethod(methodHookParam.method, methodHookParam.thisObject, methodHookParam.args);
                        } catch (Throwable th) {
                            XLog.e("❌[续播优化] 调用原方法失败", th);
                            return null;
                        }
                    }                }});
                XLog.i(">> [续播优化] 续播 Hook 注入成功！");
                return;
            }
            XLog.e("AutoPlayHook 动态目标未找到，跳过 Hook");
        } catch (Throwable th) {
            XLog.e("❌[续播优化] 续播 Hook 注入失败: ", th);
        }
    }
}
