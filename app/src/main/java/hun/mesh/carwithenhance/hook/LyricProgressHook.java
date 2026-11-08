package hun.mesh.carwithenhance.hook;

import android.os.Handler;
import android.os.Looper;
import android.view.View;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedBridge;
import hun.mesh.carwithenhance.dexkit.StructuralMethodLookup;
import hun.mesh.carwithenhance.media.CarWithMediaCapabilities;
import hun.mesh.carwithenhance.policy.LyricClockPolicy;
import hun.mesh.carwithenhance.utils.XLog;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.WeakHashMap;

/** Advances the custom media card lyric control from the same position as its seek bar. */
public final class LyricProgressHook implements IHook {
    private static final Handler MAIN = new Handler(Looper.getMainLooper());
    private static final Map<Object, Long> LAST_POSITION = new WeakHashMap<>();
    private static final Map<Class<?>, Field> FIELD_CACHE = new WeakHashMap<>();
    private static final Set<Class<?>> FIELD_MISSES = new HashSet<>();
    private static final Set<String> LOGGED_FAILURES = new HashSet<>();
    private static final Set<Class<?>> LOGGED_INITIAL_SUCCESS = new HashSet<>();
    private static volatile boolean installed;

    @Override
    public void onHook(ClassLoader classLoader) {
        if (installed) {
            return;
        }
        int hooked = 0;
        for (StructuralMethodLookup.Target target
                : CarWithMediaCapabilities.INSTANCE.getLyricProgressTargets()) {
            try {
                XposedBridge.hookMethod(target.method, new XC_MethodHook() {
                    @Override
                    protected void afterHookedMethod(MethodHookParam param) {
                        try {
                            forwardPosition(param.thisObject, ((Number) param.args[0]).longValue());
                        } catch (Throwable th) {
                            logOnce("forward:" + param.thisObject.getClass().getName(), th);
                        }
                    }
                });
                hooked++;
                XLog.i("[歌词时钟] 进度 Hook -> " + target.describe());
            } catch (Throwable th) {
                XLog.e("[歌词时钟] Hook 失败: " + target.describe(), th);
            }
        }
        installed = hooked > 0;
        if (!installed) {
            XLog.e("[歌词时钟] 未找到可用的媒体进度控件，保留官方歌词行为");
        }
    }

    private static void forwardPosition(Object mediaView, long positionMs) {
        if (!(mediaView instanceof View)
                || !LyricClockPolicy.shouldAdvance(
                lastPosition(mediaView), positionMs, AutoPlayAllHook.isCarConnected())) {
            return;
        }
        rememberPosition(mediaView, positionMs);
        final Field field = lyricField(mediaView.getClass());
        if (field == null) {
            return;
        }
        Object lyricView;
        try {
            lyricView = field.get(mediaView);
        } catch (Throwable th) {
            forgetField(mediaView.getClass());
            return;
        }
        if (lyricView == null) {
            return;
        }
        Method lyricMethod = CarWithMediaCapabilities.lyricMethod(lyricView.getClass());
        if (lyricMethod == null && field.getType() != lyricView.getClass()) {
            lyricMethod = CarWithMediaCapabilities.lyricMethod(field.getType());
        }
        if (lyricMethod == null) {
            return;
        }
        final Object target = lyricView;
        final Method method = lyricMethod;
        Runnable action = () -> {
            try {
                method.invoke(target, positionMs);
                logInitialSuccess(target.getClass(), positionMs);
            } catch (Throwable th) {
                logOnce("invoke:" + target.getClass().getName(), th);
            }
        };
        if (Looper.myLooper() == Looper.getMainLooper()) {
            action.run();
        } else if (target instanceof View) {
            ((View) target).post(action);
        } else {
            MAIN.post(action);
        }
    }

    private static long lastPosition(Object view) {
        synchronized (LAST_POSITION) {
            Long previous = LAST_POSITION.get(view);
            return previous == null ? Long.MIN_VALUE : previous.longValue();
        }
    }

    private static void rememberPosition(Object view, long positionMs) {
        synchronized (LAST_POSITION) {
            LAST_POSITION.put(view, Long.valueOf(positionMs));
        }
    }

    private static Field lyricField(Class<?> owner) {
        synchronized (FIELD_CACHE) {
            Field cached = FIELD_CACHE.get(owner);
            if (cached != null || FIELD_MISSES.contains(owner)) {
                return cached;
            }
            Field found = CarWithMediaCapabilities.lyricField(owner);
            if (found == null) {
                FIELD_MISSES.add(owner);
            } else {
                FIELD_CACHE.put(owner, found);
            }
            return found;
        }
    }

    private static void forgetField(Class<?> owner) {
        synchronized (FIELD_CACHE) {
            FIELD_CACHE.remove(owner);
            FIELD_MISSES.remove(owner);
        }
    }

    private static void logOnce(String key, Throwable throwable) {
        synchronized (LOGGED_FAILURES) {
            if (!LOGGED_FAILURES.add(key)) {
                return;
            }
        }
        XLog.e("[歌词时钟] 更新失败: " + key, throwable);
    }

    private static void logInitialSuccess(Class<?> lyricType, long positionMs) {
        synchronized (LOGGED_INITIAL_SUCCESS) {
            if (!LOGGED_INITIAL_SUCCESS.add(lyricType)) {
                return;
            }
        }
        XLog.i("[歌词时钟] 已开始按播放进度推进歌词: "
                + lyricType.getName() + " position=" + positionMs);
    }
}
