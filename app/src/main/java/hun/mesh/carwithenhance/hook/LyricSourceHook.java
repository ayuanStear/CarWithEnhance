package hun.mesh.carwithenhance.hook;

import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;
import hun.mesh.carwithenhance.media.CarWithMediaCapabilities;
import hun.mesh.carwithenhance.utils.XLog;
import java.lang.reflect.Method;

/** 词源优先：优先使用 metadata 中的 lyricInfo，缺失则保留 CarWith 原生歌词读取。 */
public class LyricSourceHook implements IHook {
    @Override
    public void onHook(ClassLoader classLoader) {
        Method lyricSource = CarWithMediaCapabilities.INSTANCE.getLyricSourceMethod();
        if (lyricSource == null) {
            XLog.e(">> [词源] 未解析到歌词入口，保留官方原生歌词行为");
            return;
        }
        try {
            XposedBridge.hookMethod(lyricSource, new XC_MethodHook() {
                @Override
                protected void beforeHookedMethod(MethodHookParam param) {
                    Object metadata = param.args != null && param.args.length > 0
                            ? param.args[0]
                            : null;
                    if (metadata == null) {
                        return;
                    }
                    String lyricInfo = (String) XposedHelpers.callMethod(
                            metadata, "getString", "lyricInfo");
                    if (lyricInfo != null && !lyricInfo.trim().isEmpty()) {
                        param.setResult(lyricInfo);
                    }
                }
            });
            XLog.i(">> [词源] lyricInfo 优先、原生兜底 Hook 已注册 -> "
                    + lyricSource.getDeclaringClass().getName()
                    + "#" + lyricSource.getName());
        } catch (Throwable th) {
            XLog.e(">> [词源] lyricInfo Hook 注册失败", th);
        }
    }
}
