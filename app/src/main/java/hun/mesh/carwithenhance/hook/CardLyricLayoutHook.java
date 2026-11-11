package hun.mesh.carwithenhance.hook;

import android.view.View;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedHelpers;
import hun.mesh.carwithenhance.utils.XLog;

/** 无歌词时放大封面：先隐藏 MinLrcView，并输出卡片内各 ImageView 尺寸用于定位封面控件。 */
public class CardLyricLayoutHook implements IHook {
    private static volatile boolean sLoggedViews;

    @Override
    public void onHook(ClassLoader classLoader) {
        try {
            Class<?> cardClass = XposedHelpers.findClass(
                    "com.carwith.launcher.card.CardMusicView", classLoader);
            XposedHelpers.findAndHookMethod(cardClass, "setLrcLyricText",
                    String.class, new XC_MethodHook() {
                        @Override
                        protected void afterHookedMethod(MethodHookParam param) {
                            try {
                                String text = (String) param.args[0];
                                boolean hasLyric = text != null && !text.trim().isEmpty();
                                Object minLrc = XposedHelpers.getObjectField(param.thisObject, "r");
                                if (minLrc instanceof View) {
                                    View v = (View) minLrc;
                                    int vis = hasLyric ? View.VISIBLE : View.GONE;
                                    if (v.getVisibility() != vis) {
                                        v.post(() -> v.setVisibility(vis));
                                        XLog.i(">> [歌词布局] 歌词区可见性 -> "
                                                + (hasLyric ? "VISIBLE" : "GONE"));
                                    }
                                }
                                logViewsOnce(param.thisObject);
                            } catch (Throwable th) {
                                XLog.e(">> [歌词布局] 处理失败", th);
                            }
                        }
                    });
            XLog.i(">> [歌词布局] setLrcLyricText Hook 已注册");
        } catch (Throwable th) {
            XLog.e(">> [歌词布局] Hook 注册失败", th);
        }
    }

    private static void logViewsOnce(Object card) {
        if (sLoggedViews) {
            return;
        }
        synchronized (CardLyricLayoutHook.class) {
            if (sLoggedViews) {
                return;
            }
            sLoggedViews = true;
        }
        for (String name : new String[]{"i", "l", "A", "B", "R"}) {
            try {
                Object f = XposedHelpers.getObjectField(card, name);
                if (f instanceof View) {
                    View v = (View) f;
                    XLog.i(">> [歌词布局] " + name + " " + v.getClass().getSimpleName()
                            + " " + v.getWidth() + "x" + v.getHeight()
                            + " vis=" + v.getVisibility());
                }
            } catch (Throwable ignored) {
            }
        }
    }
}
