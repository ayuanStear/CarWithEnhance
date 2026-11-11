package hun.mesh.carwithenhance.hook;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;
import hun.mesh.carwithenhance.media.CarWithMediaCapabilities;
import hun.mesh.carwithenhance.utils.XLog;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;
import java.util.WeakHashMap;

/** 无歌词时放大封面：通过 na.c.v0 判断歌词列表是否为空，找到当前卡片封面并缩放。 */
public class CardLyricLayoutHook implements IHook {
    private static final Map<Object, View> VIEW_BY_CTL = new WeakHashMap<>();
    private static final Map<View, float[]> ORIGINAL_SCALE = new WeakHashMap<>();
    private static volatile boolean sDumped;

    @Override
    public void onHook(ClassLoader classLoader) {
        Class<?> mmc = XposedHelpers.findClass(
                "android.support.v4.media.MediaMetadataCompat", classLoader);
        // 在元数据更新时把控制器->卡片视图 建立映射
        for (String cn : new String[]{
                "com.carwith.launcher.card.CardMusicView",
                "com.carwith.launcher.card.ModerateCardMusicView",
                "com.carwith.launcher.card.MusicControlView",
                "com.carwith.launcher.card.MusicPlayView"}) {
            try {
                Class<?> c = XposedHelpers.findClass(cn, classLoader);
                for (String m : new String[]{"m0", "n0"}) {
                    try {
                        XposedHelpers.findAndHookMethod(c, m,
                            mmc,
                            new XC_MethodHook() {
                                @Override
                                protected void afterHookedMethod(MethodHookParam param) {
                                    register(param.thisObject);
                                }
                            });
                    } catch (Throwable ignored) {
                    }
                }
            } catch (Throwable ignored) {
            }
        }

        Method v0 = CarWithMediaCapabilities.INSTANCE.getMetadataHandlerMethod();
        if (v0 == null) {
            XLog.e(">> [歌词布局] 未解析到 v0");
            return;
        }
        try {
            XposedBridge.hookMethod(v0, new XC_MethodHook() {
                @Override
                protected void afterHookedMethod(MethodHookParam param) {
                    try {
                        Object ctl = param.thisObject;
                        List<?> n = (List<?>) XposedHelpers.getObjectField(ctl, "n");
                        boolean hasLyric = n != null && !n.isEmpty();
                        XLog.i(">> [歌词布局] v0 hasLyric=" + hasLyric + " list=" + (n == null ? -1 : n.size()));
                        View card = VIEW_BY_CTL.get(ctl);
                        if (card == null) {
                            return;
                        }
                        applyCover(card, !hasLyric);
                    } catch (Throwable t) {
                        XLog.e(">> [歌词布局] v0 处理失败", t);
                    }
                }
            });
            XLog.i(">> [歌词布局] v0 Hook 已注册");
        } catch (Throwable t) {
            XLog.e(">> [歌词布局] v0 Hook 失败", t);
        }
    }

    private static void register(Object cardView) {
        try {
            Object ctl = XposedHelpers.getObjectField(cardView, "s");
            if (ctl != null) {
                VIEW_BY_CTL.put(ctl, (View) cardView);
                XLog.i(">> [歌词布局] 注册卡片 " + cardView.getClass().getSimpleName());
            }
        } catch (Throwable ignored) {
        }
    }

    private static void applyCover(final View card, final boolean noLyric) {
        card.post(new Runnable() {
            @Override
            public void run() {
                final View cover = findLargestImageView(card);
                if (cover == null) {
                    return;
                }
                if (noLyric) {
                    if (!ORIGINAL_SCALE.containsKey(cover)) {
                        ORIGINAL_SCALE.put(cover, new float[]{cover.getScaleX(), cover.getScaleY()});
                    }
                    cover.setPivotX(cover.getWidth() / 2f);
                    cover.setPivotY(cover.getHeight() / 2f);
                    cover.setScaleX(1.35f);
                    cover.setScaleY(1.35f);
                    XLog.i(">> [歌词布局] 无歌词，放大封面 " + cover.getWidth() + "x" + cover.getHeight());
                } else {
                    float[] o = ORIGINAL_SCALE.get(cover);
                    if (o != null) {
                        cover.setScaleX(o[0]);
                        cover.setScaleY(o[1]);
                        XLog.i(">> [歌词布局] 有歌词，恢复封面");
                    }
                }
            }
        });
    }

    private static View findLargestImageView(View root) {
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
}
