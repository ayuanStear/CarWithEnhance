package hun.mesh.carwithenhance.hook;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;
import hun.mesh.carwithenhance.utils.XLog;
import java.lang.ref.WeakReference;
import java.util.List;
import java.util.Map;
import java.util.WeakHashMap;

/**
 * 无歌词时自动放大封面；有歌词时恢复普通布局。
 *
 * 逆向结论（当前版本走 v2 卡片系统，旧 CardMusicView 已不再使用）：
 *  - 实际媒体卡片：com.carwith.launcher.card.v2.widget.MusicViewA（父类 BaseMusicView）。
 *  - 歌词存在性由 na.c.v0() 可靠给出（字段 n 为已解析歌词列表，非空即有词）。
 *  - MusicViewA 的封面为 ImageView（字段 a0 或通过最大 ImageView 兜底定位）。
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
                    applyManualScale(card, !hasLyric);
                } catch (Throwable t) {
                    XLog.e(">> [歌词布局] 应用封面失败", t);
                }
            }
        });
    }

    private void applyManualScale(View card, boolean noLyric) {
        View cover = findCover(card);
        if (cover == null) {
            XLog.i(">> [歌词布局] 未找到封面 ImageView");
            return;
        }
        float target = noLyric ? 1.45f : 1f;
        if (Math.abs(cover.getScaleX() - target) < 0.01f) {
            return;
        }
        cover.setPivotX(cover.getWidth() / 2f);
        cover.setPivotY(cover.getHeight() / 2f);
        cover.setScaleX(target);
        cover.setScaleY(target);
        XLog.i(">> [歌词布局] " + (noLyric ? "无歌词 → 放大封面 " : "有歌词 → 恢复封面 ")
                + cover.getClass().getSimpleName() + " " + cover.getWidth() + "x" + cover.getHeight());
    }

    private View findCover(View card) {
        // 专辑封面通常是卡片中最大的 ImageView，优先取最大者，避免误缩放小图标
        View largest = findLargestImageView(card);
        if (largest != null && largest.getWidth() >= 80 && largest.getHeight() >= 80) {
            return largest;
        }
        View k0 = readField(card, "k0");
        if (k0 != null && k0.getWidth() > 0 && k0.getHeight() > 0) {
            if (k0 instanceof ImageView) {
                return k0;
            }
            return findLargestImageView(k0);
        }
        return largest;
    }

    private View findLargestImageView(View root) {
        View best = null;
        int bestArea = 0;
        if (root instanceof ImageView) {
            int a = root.getWidth() * root.getHeight();
            if (a > bestArea) {
                best = root;
                bestArea = a;
            }
        }
        if (root instanceof ViewGroup) {
            ViewGroup g = (ViewGroup) root;
            for (int i = 0; i < g.getChildCount(); i++) {
                View v = findLargestImageView(g.getChildAt(i));
                if (v != null) {
                    int a = v.getWidth() * v.getHeight();
                    if (a > bestArea) {
                        best = v;
                        bestArea = a;
                    }
                }
            }
        }
        return best;
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
