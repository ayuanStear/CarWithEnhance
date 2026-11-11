package hun.mesh.carwithenhance.hook;

import android.view.View;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;
import hun.mesh.carwithenhance.utils.XLog;
import java.lang.ref.WeakReference;
import java.util.List;
import java.util.Map;
import java.util.WeakHashMap;

/**
 * 无歌词时放大封面；有歌词时恢复普通布局。
 *
 * 当前版本使用 v2 卡片系统：
 *  - 媒体卡片：com.carwith.launcher.card.v2.widget.MusicViewA（父类 BaseMusicView）。
 *  - 歌词有无由 na.c.v0() 的字段 n（歌词列表）给出，非空即有词。
 *  - 原生封面放大由 MusicViewA.v0(Z)（写 prefer_media_card_bg）+
 *    MusicViewA.H0()（读取该 pref 并通过 Handler 调用 I0(Z) 切布局）完成，
 *    即点击封面“变大变小”的同一套原生逻辑。
 */
public class CardLyricLayoutHook implements IHook {
    private static final String CARD_V2 = "com.carwith.launcher.card.v2.widget.MusicViewA";
    private static final String CARD_V2_BASE = "com.carwith.launcher.card.v2.widget.BaseMusicView";
    private static final String NA_C = "na.c";

    private static final Map<Object, WeakReference<View>> NA_C_TO_CARD = new WeakHashMap<>();
    private static final Map<View, Boolean> LAST_NO_LYRIC = new WeakHashMap<>();
    private static volatile WeakReference<View> LAST_CARD = new WeakReference<>(null);

    @Override
    public void onHook(ClassLoader classLoader) {
        hookCardCapture(classLoader);
        hookMetadataHandler(classLoader);
    }

    private void hookCardCapture(ClassLoader cl) {
        for (String name : new String[]{CARD_V2, CARD_V2_BASE}) {
            try {
                Class<?> c = XposedHelpers.findClass(name, cl);
                XposedBridge.hookAllConstructors(c, new XC_MethodHook() {
                    @Override
                    protected void afterHookedMethod(MethodHookParam param) {
                        register((View) param.thisObject);
                    }
                });
                try {
                    XposedHelpers.findAndHookMethod(c, "onAttachedToWindow", new XC_MethodHook() {
                        @Override
                        protected void afterHookedMethod(MethodHookParam param) {
                            register((View) param.thisObject);
                        }
                    });
                } catch (Throwable ignored) {
                }
                XLog.i(">> [歌词布局] 卡片捕获 Hook 已注册 " + c.getSimpleName());
            } catch (Throwable t) {
                XLog.e(">> [歌词布局] 卡片捕获 Hook 失败 " + name, t);
            }
        }
    }

    private void hookMetadataHandler(ClassLoader cl) {
        try {
            Class<?> naC = XposedHelpers.findClass(NA_C, cl);
            XposedHelpers.findAndHookMethod(naC, "v0", new XC_MethodHook() {
                @Override
                protected void afterHookedMethod(MethodHookParam param) {
                    try {
                        Object ctl = param.thisObject;
                        List<?> n = readField(ctl, "n");
                        boolean hasLyric = n != null && !n.isEmpty();
                        View card = resolveCard(ctl);
                        if (card == null) {
                            XLog.i(">> [歌词布局] v0 hasLyric=" + hasLyric + " 未匹配卡片");
                            return;
                        }
                        applyCover(card, hasLyric);
                    } catch (Throwable t) {
                        XLog.e(">> [歌词布局] v0 处理失败", t);
                    }
                }
            });
            XLog.i(">> [歌词布局] v0 封面联动 Hook 已注册");
        } catch (Throwable t) {
            XLog.e(">> [歌词布局] v0 Hook 失败", t);
        }
    }

    private void register(View card) {
        if (card == null) {
            return;
        }
        LAST_CARD = new WeakReference<>(card);
        try {
            Object naC = XposedHelpers.getObjectField(card, "b");
            if (naC != null) {
                NA_C_TO_CARD.put(naC, new WeakReference<>(card));
            }
        } catch (Throwable ignored) {
        }
    }

    private View resolveCard(Object ctl) {
        WeakReference<View> ref = NA_C_TO_CARD.get(ctl);
        if (ref != null && ref.get() != null) {
            return ref.get();
        }
        return LAST_CARD.get();
    }

    private void applyCover(final View card, final boolean hasLyric) {
        boolean noLyric = !hasLyric;
        Boolean prev = LAST_NO_LYRIC.get(card);
        if (prev != null && prev == noLyric) {
            return;
        }
        LAST_NO_LYRIC.put(card, noLyric);
        card.post(new Runnable() {
            @Override
            public void run() {
                try {
                    applyNativeCover(card, noLyric);
                } catch (Throwable t) {
                    XLog.e(">> [歌词布局] 原生封面切换失败", t);
                }
            }
        });
    }

    private void applyNativeCover(View card, boolean enlarge) {
        XposedHelpers.callMethod(card, "v0", enlarge); // 写 prefer_media_card_bg
        XposedHelpers.callMethod(card, "H0");          // 读 pref 并走 Handler 切布局
        XLog.i(">> [歌词布局] " + (enlarge ? "无歌词 → 原生放大封面" : "有歌词 → 原生恢复封面"));
    }

    @SuppressWarnings("unchecked")
    private static <T> T readField(Object target, String name) {
        try {
            return (T) XposedHelpers.getObjectField(target, name);
        } catch (Throwable ignored) {
            return null;
        }
    }
}
