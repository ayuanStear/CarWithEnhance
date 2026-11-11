package hun.mesh.carwithenhance.hook;

import android.view.View;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedHelpers;
import hun.mesh.carwithenhance.utils.XLog;

/** 观察 CarWith 原生“无歌词视图”切换逻辑，并按歌词有无驱动封面/歌词区布局。 */
public class CardLyricLayoutHook implements IHook {
    @Override
    public void onHook(ClassLoader classLoader) {
        // 基础卡片的歌词文本入口
        try {
            Class<?> base = XposedHelpers.findClass(
                    "com.carwith.launcher.card.CardMusicView", classLoader);
            XposedHelpers.findAndHookMethod(base, "setLrcLyricText",
                    String.class, new XC_MethodHook() {
                        @Override
                        protected void afterHookedMethod(MethodHookParam param) {
                            String text = (String) param.args[0];
                            boolean has = text != null && !text.trim().isEmpty();
                            XLog.i(">> [歌词布局] setLrcLyricText: has=" + has);
                            toggleNoLyric(param.thisObject, !has);
                        }
                    });
        } catch (Throwable t) {
            XLog.e(">> [歌词布局] setLrcLyricText 挂钩失败", t);
        }
        // Moderate 卡片原生就重写了 setNoLyricViewVisible，观察它的调用
        try {
            Class<?> moderate = XposedHelpers.findClass(
                    "com.carwith.launcher.card.ModerateCardMusicView", classLoader);
            XposedHelpers.findAndHookMethod(moderate, "setNoLyricViewVisible",
                    boolean.class, new XC_MethodHook() {
                        @Override
                        protected void beforeHookedMethod(MethodHookParam param) {
                            XLog.i(">> [歌词布局] 原生 setNoLyricViewVisible(" + param.args[0] + ")");
                        }
                    });
        } catch (Throwable t) {
            XLog.e(">> [歌词布局] setNoLyricViewVisible 挂钩失败", t);
        }
    }

    private static void toggleNoLyric(Object card, boolean noLyric) {
        try {
            XposedHelpers.callMethod(card, "setNoLyricViewVisible", noLyric);
        } catch (Throwable t) {
            XLog.e(">> [歌词布局] 切换无歌词视图失败", t);
        }
    }
}
