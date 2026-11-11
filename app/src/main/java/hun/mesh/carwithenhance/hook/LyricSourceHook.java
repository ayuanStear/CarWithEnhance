package hun.mesh.carwithenhance.hook;

import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedHelpers;
import hun.mesh.carwithenhance.utils.XLog;

/** 词源优先：CarWith 读取整段歌词时，优先取 lyricInfo 模块注入的 lyricInfo 字段，缺失则走原生。 */
public class LyricSourceHook implements IHook {
    @Override
    public void onHook(final ClassLoader classLoader) {
        try {
            Class<?> metadataClass = XposedHelpers.findClass(
                    "android.support.v4.media.MediaMetadataCompat", classLoader);
            XposedHelpers.findAndHookMethod(
                    "na.c", classLoader, "A", metadataClass,
                    new XC_MethodHook() {
                        @Override
                        protected void beforeHookedMethod(MethodHookParam param) {
                            Object metadata = param.args[0];
                            if (metadata == null) {
                                return;
                            }
                            // MediaMetadataCompat#i(String) == getString
                            String lyricInfo = (String) XposedHelpers.callMethod(
                                    metadata, "i", "lyricInfo");
                            if (lyricInfo != null && !lyricInfo.isEmpty()) {
                                param.setResult(lyricInfo);
                            }
                        }
                    });
            XLog.i(">> [词源] lyricInfo 优先、原生兜底 Hook 已注册");
        } catch (Throwable th) {
            XLog.e(">> [词源] lyricInfo Hook 注册失败", th);
        }
    }
}
