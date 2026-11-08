package hun.mesh.carwithenhance.hook;

import android.os.Build;
import android.os.SystemClock;
import android.view.Surface;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;
import hun.mesh.carwithenhance.dexkit.CarLifeFrameTargets;
import hun.mesh.carwithenhance.policy.FramePacerPolicy;
import hun.mesh.carwithenhance.utils.XLog;
import java.lang.reflect.Field;

public final class CarLifeFramePacingHook implements IHook {
    private static final int MSG_RENDER_DISPLAY = 2;

    private final FramePacerPolicy pacer = new FramePacerPolicy();
    private volatile int requestedFps;
    private volatile boolean preciseRendererInstalled;
    private volatile boolean degradedStateLogged;

    @Override
    public void onHook(ClassLoader classLoader) {
        final CarLifeFrameTargets targets = CarLifeFrameTargets.resolve(classLoader);
        XLog.i("[CarLifePacing] 60FPS自动精确调度已启用, " + targets.describe());

        installFrameRateHook(targets);
        installConnectionHook(targets);
        installRendererHook(targets);
    }

    private void installFrameRateHook(final CarLifeFrameTargets targets) {
        if (targets.changeFrameRate == null) {
            XLog.e("[CarLifePacing] changeFrameRate 未找到，无法跟踪目标帧率");
            return;
        }
        try {
            XposedBridge.hookMethod(targets.changeFrameRate, new XC_MethodHook() {
                @Override
                protected void afterHookedMethod(MethodHookParam param) {
                    if (param.args == null || param.args.length == 0
                            || !(param.args[0] instanceof Integer)) {
                        return;
                    }
                    updateRequestedFps((Integer) param.args[0]);
                    if (requestedFps == 60) {
                        applySurfaceFrameRate(targets, param.thisObject);
                        if (!preciseRendererInstalled) {
                            applyDegradedInterval(targets);
                        }
                    }
                }
            });
        } catch (Throwable th) {
            XLog.e("[CarLifePacing] changeFrameRate Hook 安装失败", th);
        }
    }

    private void installConnectionHook(final CarLifeFrameTargets targets) {
        if (targets.handleConnectEstablished == null) {
            return;
        }
        try {
            XposedBridge.hookMethod(targets.handleConnectEstablished, new XC_MethodHook() {
                @Override
                protected void afterHookedMethod(MethodHookParam param) {
                    if (requestedFps == 60) {
                        applySurfaceFrameRate(targets, param.thisObject);
                    }
                }
            });
        } catch (Throwable th) {
            XLog.e("[CarLifePacing] handleConnectEstablished Hook 安装失败", th);
        }
    }

    private void installRendererHook(CarLifeFrameTargets targets) {
        if (!targets.hasPreciseRenderer()) {
            XLog.e("[CarLifePacing] 精确渲染调度目标缺失，使用 DEGRADED_17MS");
            return;
        }
        try {
            XposedBridge.hookMethod(targets.renderDisplayMsg, new XC_MethodHook() {
                @Override
                protected void beforeHookedMethod(MethodHookParam param) {
                    if (!FramePacerPolicy.shouldOverrideRenderer(requestedFps)) {
                        return;
                    }
                    try {
                        long nowNanos = SystemClock.elapsedRealtimeNanos();
                        long elapsedMillis = param.args != null && param.args.length > 0
                                && param.args[0] instanceof Long
                                ? (Long) param.args[0]
                                : 0L;
                        long frameStartNanos =
                                FramePacerPolicy.frameStartNanos(nowNanos, elapsedMillis);
                        int delayMillis =
                                pacer.nextDelayMillis(requestedFps, frameStartNanos, nowNanos);
                        Object scheduled = XposedHelpers.callMethod(
                                param.thisObject,
                                "sendEmptyMessageDelayed",
                                MSG_RENDER_DISPLAY,
                                delayMillis);
                        if (Boolean.FALSE.equals(scheduled)) {
                            XLog.e("[CarLifePacing] RenderHandler 已停止，保留原调度逻辑");
                            return;
                        }
                        param.setResult(null);
                    } catch (Throwable th) {
                        XLog.e("[CarLifePacing] 精确调度本次计算失败，回退原逻辑", th);
                    }
                }
            });
            preciseRendererInstalled = true;
            XLog.i("[CarLifePacing] 精确渲染调度 Hook 安装成功");
        } catch (Throwable th) {
            preciseRendererInstalled = false;
            XLog.e("[CarLifePacing] renderDisplayMsg Hook 安装失败", th);
        }
    }

    private void applySurfaceFrameRate(CarLifeFrameTargets targets, Object recorder) {
        Surface surface = readSurface(targets, recorder);
        boolean hasValidSurface = surface != null && surface.isValid();
        if (!FramePacerPolicy.shouldApplySurfaceFrameRate(
                requestedFps, Build.VERSION.SDK_INT, hasValidSurface)) {
            return;
        }
        try {
            surface.setFrameRate(
                    requestedFps,
                    Surface.FRAME_RATE_COMPATIBILITY_FIXED_SOURCE);
            XLog.i("[CarLifePacing] 编码 Surface 固定帧率=" + requestedFps);
        } catch (Throwable th) {
            XLog.e("[CarLifePacing] 设置编码 Surface 帧率失败", th);
        }
    }

    private void applyDegradedInterval(CarLifeFrameTargets targets) {
        if (targets.destFrameInterval == null) {
            return;
        }
        try {
            targets.destFrameInterval.setInt(
                    null, FramePacerPolicy.fallbackIntervalMillis(60));
            if (!degradedStateLogged) {
                degradedStateLogged = true;
                XLog.i("[CarLifePacing] DEGRADED_17MS: mDestFrameInterval=17");
            }
        } catch (Throwable th) {
            XLog.e("[CarLifePacing] 写入 DEGRADED_17MS 失败", th);
        }
    }

    private static Surface readSurface(CarLifeFrameTargets targets, Object recorder) {
        Field field = targets.encodeSurface;
        if (field == null || recorder == null) {
            return null;
        }
        try {
            Object value = field.get(recorder);
            return value instanceof Surface ? (Surface) value : null;
        } catch (Throwable ignored) {
            return null;
        }
    }

    private synchronized void updateRequestedFps(int fps) {
        if (FramePacerPolicy.shouldResetForFrameRateChange(requestedFps, fps)) {
            pacer.reset();
        }
        requestedFps = fps;
    }

}
