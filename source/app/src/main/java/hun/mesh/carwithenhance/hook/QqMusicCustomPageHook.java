package hun.mesh.carwithenhance.hook;

import android.util.Log;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;
import hun.mesh.carwithenhance.utils.XLog;

/**
 * 让 QQ 音乐和网易云一样走 CarWith 1.0 定制页，而不是 QQ 原生车机页。
 *
 * 根因：应用列表菜单点击走 y3.c.StartAppHelper.startActivity，对 QQ 音乐会直接
 * NavigationRouting 打开 com.tencent.qqmusic/.business.carwith.ui.CarWithPlayerActivity。
 * 导航栏入口走 OtherView -> UCarFragmentManager.jumpTo，所以是定制页。
 */
public class QqMusicCustomPageHook implements IHook {
    private static final String QQ_MUSIC = "com.tencent.qqmusic";
    private static final String UFRAGMENT_MGR = "com.carwith.launcher.ams.UCarFragmentManager";

    @Override
    public void onHook(ClassLoader classLoader) {
        try {
            Class<?> startAppHelper = XposedHelpers.findClass("y3.c", classLoader);
            XposedBridge.hookAllMethods(startAppHelper, "e", new XC_MethodHook() {
                @Override
                protected void beforeHookedMethod(MethodHookParam param) {
                    try {
                        Object pkg = param.args.length > 1 ? param.args[1] : null;
                        if (!QQ_MUSIC.equals(pkg)) {
                            return;
                        }
                        Object context = param.args.length > 0 ? param.args[0] : null;
                        XposedHelpers.callStaticMethod(
                                XposedHelpers.findClass(UFRAGMENT_MGR, classLoader),
                                "jumpTo",
                                pkg,
                                "source_from_apps");
                        param.setResult(null);
                        XLog.i(">> [QQ定制页] 菜单入口已把 QQ 音乐切到 CarWith 定制页");
                    } catch (Throwable t) {
                        Log.w("QQ定制页", t);
                    }
                }
            });
            XLog.i(">> [QQ定制页] StartAppHelper 菜单入口 Hook 已注册");
        } catch (Throwable t) {
            XLog.e(">> [QQ定制页] StartAppHelper Hook 失败", t);
        }
    }
}
