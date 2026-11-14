package hun.mesh.carwithenhance;

import android.app.Application;
import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage;
import hun.mesh.carwithenhance.dexkit.CarWithDexKitManager;
import hun.mesh.carwithenhance.hook.AutoPlayAllHook;
import hun.mesh.carwithenhance.hook.AutoPlayHook;
import hun.mesh.carwithenhance.hook.CarLinkStateHook;
import hun.mesh.carwithenhance.hook.CarWakeVoiceHook;
import hun.mesh.carwithenhance.hook.DisconnectMusicKillHook;
import hun.mesh.carwithenhance.hook.IHook;
import hun.mesh.carwithenhance.hook.MediaRouteHook;
import hun.mesh.carwithenhance.hook.MediaTabSelectionHook;
import hun.mesh.carwithenhance.hook.SettingsHook;
import hun.mesh.carwithenhance.media.CarWithMediaCapabilities;
import hun.mesh.carwithenhance.utils.XLog;

/* JADX INFO: compiled from: r8-map-id-2844820780df513d20833d92dac6e5f4b7fbbd08283aea6743910c9549ac13d9 */
/* JADX INFO: loaded from: classes.dex */
public class MainHook implements IXposedHookLoadPackage {
    /* JADX WARN: switch over string: strings are not added: [[com.tencent.qqmusic]] */
    public void handleLoadPackage(final XC_LoadPackage.LoadPackageParam loadPackageParam) {
        String str = loadPackageParam.packageName;
        str.getClass();
        CarWakeVoiceHook.install(loadPackageParam);
        switch (str) {
            case "android":
                DisconnectMusicKillHook.hook(loadPackageParam);
                break;
            case "com.miui.carlink":
                final ClassLoader classLoader = loadPackageParam.classLoader;
                XposedHelpers.findAndHookMethod("android.app.Application", classLoader, "onCreate", new Object[]{new XC_MethodHook() { // from class: hun.mesh.carwithenhance.MainHook.1
                    public void beforeHookedMethod(XC_MethodHook.MethodHookParam methodHookParam) {
                        Application application = (Application) methodHookParam.thisObject;
                        CarWithDexKitManager.INSTANCE.initAndResolve(loadPackageParam, application);
                        CarWithMediaCapabilities.INSTANCE.resolve(application, classLoader);
                        IHook[] iHookArr = {
                                new AutoPlayHook(),
                                new AutoPlayAllHook(),
                                new MediaRouteHook(),
                                new MediaTabSelectionHook(),
                                new SettingsHook(),
                                new CarLinkStateHook(application)
                        };
                        for (int i = 0; i < iHookArr.length; i++) {
                            IHook iHook = iHookArr[i];
                            String simpleName = iHook.getClass().getSimpleName();
                            try {
                                iHook.onHook(classLoader);
                            } catch (Throwable th) {
                                XLog.e(">> [CarWith Enhance] 挂载 " + simpleName + " 异常失败: ", th);
                            }
                        }
                    }
                }});
                break;
        }
    }
}
