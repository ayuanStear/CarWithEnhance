package hun.mesh.carwithenhance.hook;

import android.content.Context;
import android.os.SystemClock;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedBridge;
import hun.mesh.carwithenhance.dexkit.StructuralMethodLookup;
import hun.mesh.carwithenhance.media.CarWithMediaCapabilities;
import hun.mesh.carwithenhance.policy.MediaRoutePolicy;
import hun.mesh.carwithenhance.utils.XLog;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/** Keeps a ready custom media page from falling back during a transient route miss. */
public final class MediaRouteHook implements IHook {
    private static final long REQUEST_TTL_MS = 2_000L;
    private static final String PREF_MEDIA_APP = "file_prefer_app_091703";
    private static final String PREF_QQ_NATIVE = "prefer_qq_music_individuation";
    private static final Set<String> CUSTOM_CAPABLE = new HashSet<>(Arrays.asList(
            "com.netease.cloudmusic",
            "com.tencent.qqmusic",
            "com.kugou.android",
            "cmccwm.mobilemusic",
            "com.ifeng.fhdt",
            "com.shinyv.cnr",
            "com.baidu.netdisk"));

    private static final ThreadLocal<RouteRequest> CURRENT_REQUEST = new ThreadLocal<>();
    private static final Set<String> ACCEPTED_CUSTOM = new HashSet<>();
    private static volatile boolean installed;

    @Override
    public void onHook(ClassLoader classLoader) {
        if (installed || !CarWithMediaCapabilities.INSTANCE.isRouteReady()) {
            XLog.e("[媒体路由] 能力不可用，保留宿主官方页面路由");
            return;
        }
        StructuralMethodLookup.Target entry =
                CarWithMediaCapabilities.INSTANCE.getRouteEntry();
        Method capability = CarWithMediaCapabilities.INSTANCE.getRouteCapability();
        try {
            XposedBridge.hookMethod(entry.method, new XC_MethodHook() {
                @Override
                protected void beforeHookedMethod(MethodHookParam param) {
                    CURRENT_REQUEST.set(RouteRequest.from(param));
                }

                @Override
                protected void afterHookedMethod(MethodHookParam param) {
                    CURRENT_REQUEST.remove();
                }
            });
            XposedBridge.hookMethod(capability, new XC_MethodHook() {
                @Override
                protected void afterHookedMethod(MethodHookParam param) {
                    try {
                        applyRoutePolicy(param);
                    } catch (Throwable th) {
                        CURRENT_REQUEST.remove();
                        XLog.e("[媒体路由] 判定处理异常，使用官方结果", th);
                    }
                }
            });
            installed = true;
        } catch (Throwable th) {
            XLog.e("[媒体路由] Hook 注册失败，保留官方路由", th);
        }
    }

    private static void applyRoutePolicy(XC_MethodHook.MethodHookParam param) {
        RouteRequest request = CURRENT_REQUEST.get();
        if (request == null || request.isExpired()) {
            CURRENT_REQUEST.remove();
            return;
        }
        if (!(param.args.length == 1 && param.args[0] instanceof String)) {
            return;
        }
        String requestedPackage = (String) param.args[0];
        boolean officialAllows = Boolean.TRUE.equals(param.getResult());
        if (officialAllows && request.packageName.equals(requestedPackage)) {
            synchronized (ACCEPTED_CUSTOM) {
                ACCEPTED_CUSTOM.add(requestedPackage);
            }
        }
        Context context = AutoPlayAllHook.getAppContext();
        boolean prefersNative = prefersNative(context, requestedPackage);
        boolean targetIsCurrent = AutoPlayAllHook.isCurrentMediaTarget(requestedPackage);
        boolean sessionReady = AutoPlayAllHook.isMediaSessionReady(requestedPackage);
        boolean customCapable;
        synchronized (ACCEPTED_CUSTOM) {
            customCapable = CUSTOM_CAPABLE.contains(requestedPackage)
                    || ACCEPTED_CUSTOM.contains(requestedPackage);
        }
        boolean force = MediaRoutePolicy.shouldPreferCustom(
                request.packageName.equals(requestedPackage),
                targetIsCurrent,
                sessionReady,
                officialAllows,
                customCapable,
                prefersNative);
        if (!force) {
            if (request.packageName.equals(requestedPackage) && customCapable) {
                AutoPlayAllHook.log(context, "媒体路由保持官方结果: " + requestedPackage
                        + " (current=" + targetIsCurrent
                        + ", ready=" + sessionReady
                        + ", nativePref=" + prefersNative
                        + ", official=" + officialAllows + ")");
            }
            return;
        }
        param.setResult(Boolean.TRUE);
        AutoPlayAllHook.log(context, "媒体定制页路由已纠偏: " + requestedPackage
                + " (请求=" + request.packageName + ")");
    }

    private static boolean prefersNative(Context context, String packageName) {
        return context != null
                && "com.tencent.qqmusic".equals(packageName)
                && context.getSharedPreferences(PREF_MEDIA_APP, 0)
                .getBoolean(PREF_QQ_NATIVE, false);
    }

    private static final class RouteRequest {
        final String packageName;
        final long startedAt;

        RouteRequest(String packageName) {
            this.packageName = packageName;
            this.startedAt = SystemClock.elapsedRealtime();
        }

        static RouteRequest from(XC_MethodHook.MethodHookParam param) {
            if (param.method instanceof Method && param.args != null) {
                Class<?>[] parameterTypes = ((Method) param.method).getParameterTypes();
                for (int i = 0; i < param.args.length; i++) {
                    if (parameterTypes[i] == String.class && param.args[i] instanceof String) {
                        String packageName = (String) param.args[i];
                        if (packageName != null && packageName.contains(".")) {
                            return new RouteRequest(packageName);
                        }
                    }
                }
            }
            return null;
        }

        boolean isExpired() {
            return SystemClock.elapsedRealtime() - startedAt > REQUEST_TTL_MS;
        }
    }
}
