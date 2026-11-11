package hun.mesh.carwithenhance.hook;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;
import hun.mesh.carwithenhance.media.CarWithMediaCapabilities;
import hun.mesh.carwithenhance.utils.XLog;
import java.lang.ref.WeakReference;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;
import java.util.WeakHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

/** 无歌词时放大封面：维护卡片实例表，按控制器匹配卡片后缩放最大封面 ImageView。 */
public class CardLyricLayoutHook implements IHook {
    private static final List<WeakReference<View>> CARDS = new CopyOnWriteArrayList<>();
    private static final Map<View, float[]> ORIGINAL_SCALE = new WeakHashMap<>();

    @Override
    public void onHook(ClassLoader classLoader) {
        String[] names = {
            "com.carwith.launcher.card.CardMusicView",
            "com.carwith.launcher.card.ModerateCardMusicView",
            "com.carwith.launcher.card.MusicControlView",
            "com.carwith.launcher.card.MusicPlayView",
            "com.carwith.launcher.card.DockerMusicView"
        };
        for (String cn : names) {
            try {
                Class<?> c = XposedHelpers.findClass(cn, classLoader);
                XposedHelpers.findAndHookConstructor(c, Context.class, AttributeSet.class,
                    new XC_MethodHook() {
                        @Override
                        protected void afterHookedMethod(MethodHookParam param) {
                            View v = (View) param.thisObject;
                            CARDS.add(new WeakReference<>(v));
                            XLog.i(">> [歌词布局] 卡片实例注册 " + v.getClass().getSimpleName());
                        }
                    });
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
                        View card = findCard(ctl);
                        if (card == null) {
                            XLog.i(">> [歌词布局] v0 hasLyric=" + hasLyric + " 未匹配到卡片");
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

    private static View findCard(Object ctl) {
        for (WeakReference<View> r : CARDS) {
            View v = r.get();
            if (v == null) {
                continue;
            }
            try {
                Object s = XposedHelpers.getObjectField(v, "s");
                if (s == ctl) {
                    return v;
                }
            } catch (Throwable ignored) {
            }
        }
        return null;
    }

    private static void applyCover(final View card, final boolean noLyric) {
        card.post(new Runnable() {
            @Override
            public void run() {
                View cover = findLargestImageView(card);
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
                    XLog.i(">> [歌词布局] 放大封面 " + cover.getWidth() + "x" + cover.getHeight()
                            + " " + cover.getClass().getSimpleName());
                } else {
                    float[] o = ORIGINAL_SCALE.get(cover);
                    if (o != null) {
                        cover.setScaleX(o[0]);
                        cover.setScaleY(o[1]);
                        XLog.i(">> [歌词布局] 恢复封面");
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
