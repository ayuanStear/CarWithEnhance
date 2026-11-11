package hun.mesh.carwithenhance.hook;

import android.view.View;
import android.widget.ImageView;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedHelpers;
import hun.mesh.carwithenhance.utils.XLog;

/** 覆盖所有音乐卡片子类，观察原生无歌词切换，并输出封面控件尺寸。 */
public class CardLyricLayoutHook implements IHook {
    private static final String[] CARDS = {
        "com.carwith.launcher.card.CardMusicView",
        "com.carwith.launcher.card.ModerateCardMusicView",
        "com.carwith.launcher.card.MusicControlView",
        "com.carwith.launcher.card.MusicPlayView",
        "com.carwith.launcher.card.DockerMusicView"
    };
    private static volatile boolean sDumped;

    @Override
    public void onHook(ClassLoader classLoader) {
        for (String cn : CARDS) {
            try {
                Class<?> c = XposedHelpers.findClass(cn, classLoader);
                hookNoLyric(c);
                hookLyricText(c);
                XLog.i(">> [歌词布局] 已挂钩 " + cn);
            } catch (Throwable ignored) {
            }
        }
    }

    private static void hookNoLyric(Class<?> c) {
        try {
            XposedHelpers.findAndHookMethod(c, "setNoLyricViewVisible", boolean.class,
                new XC_MethodHook() {
                    @Override
                    protected void beforeHookedMethod(MethodHookParam param) {
                        XLog.i(">> [歌词布局] " + c.getSimpleName()
                                + ".setNoLyricViewVisible(" + param.args[0] + ")");
                    }
                });
        } catch (Throwable ignored) {
        }
    }

    private static void hookLyricText(Class<?> c) {
        try {
            XposedHelpers.findAndHookMethod(c, "setLrcLyricText", String.class,
                new XC_MethodHook() {
                    @Override
                    protected void afterHookedMethod(MethodHookParam param) {
                        String t = (String) param.args[0];
                        boolean has = t != null && !t.trim().isEmpty();
                        XLog.i(">> [歌词布局] " + c.getSimpleName() + ".setLrcLyricText has=" + has);
                        dumpViewsOnce(param.thisObject);
                    }
                });
        } catch (Throwable ignored) {
        }
    }

    private static void dumpViewsOnce(Object card) {
        if (sDumped) {
            return;
        }
        synchronized (CardLyricLayoutHook.class) {
            if (sDumped) {
                return;
            }
            sDumped = true;
        }
        for (String n : new String[]{"i", "l", "z", "A", "B", "R", "m", "n", "p"}) {
            try {
                Object f = XposedHelpers.getObjectField(card, n);
                if (f instanceof ImageView) {
                    ImageView v = (ImageView) f;
                    XLog.i(">> [歌词布局] " + n + "=" + v.getWidth() + "x" + v.getHeight()
                            + " vis=" + v.getVisibility());
                }
            } catch (Throwable ignored) {
            }
        }
    }
}
