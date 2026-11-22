package hun.mesh.carwithenhance.hook;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.SystemClock;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;
import hun.mesh.carwithenhance.dexkit.StructuralMethodLookup;
import hun.mesh.carwithenhance.media.CarWithMediaCapabilities;
import hun.mesh.carwithenhance.policy.MediaRoutePolicy;
import hun.mesh.carwithenhance.utils.XLog;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Keeps a ready custom media page from falling back during a transient route
 * miss, and prepares a dead media app's MediaSession before CarWith decides to
 * open its native Activity instead of the custom media page.
 */
public final class MediaRouteHook implements IHook {
    private static final long REQUEST_TTL_MS = 4_000L;
    private static final long PREPARE_TIMEOUT_MS = 3_000L;
    private static final long PREPARE_POLL_MS = 150L;
    private static final long ROUTE_IN_FLIGHT_TTL_MS = 6_000L;
    private static final long NATIVE_REDIRECT_GUARD_MS = 4_000L;
    private static final long FORCED_CUSTOM_TTL_MS = 3_000L;
    private static final String PREF_MEDIA_APP = "file_prefer_app_091703";
    private static final String PREF_QQ_NATIVE = "prefer_qq_music_individuation";
    private static final String CUSTOM_FRAGMENT_PREFIX = "media_fragment_tag:";
    private static final Set<String> CUSTOM_CAPABLE = new HashSet<>(Arrays.asList(
            "com.netease.cloudmusic",
            "com.tencent.qqmusic",
            "com.kugou.android",
            "cmccwm.mobilemusic",
            "com.ifeng.fhdt",
            "com.shinyv.cnr",
            "com.baidu.netdisk"));

    private static final ThreadLocal<RouteRequest> CURRENT_REQUEST = new ThreadLocal<>();
    private static final ThreadLocal<Boolean> BYPASS = new ThreadLocal<>();
    private static final Map<String, Long> FORCED_CUSTOM = new ConcurrentHashMap<>();
    private static final Set<String> ACCEPTED_CUSTOM = new HashSet<>();
    private static final Map<String, Long> ROUTE_IN_FLIGHT = new ConcurrentHashMap<>();
    private static final Map<String, Long> NATIVE_REDIRECTED = new ConcurrentHashMap<>();
    private static final Map<String, Long> CUSTOM_OPEN_AT = new ConcurrentHashMap<>();
    private static final long CUSTOM_OPEN_DEBOUNCE_MS = 1_200L;
    private static volatile boolean installed;
    private static Handler sHandler;

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
                    if (Boolean.TRUE.equals(BYPASS.get())) {
                        return;
                    }
                    RouteRequest request = RouteRequest.from(param);
                    CURRENT_REQUEST.set(request);
                    if (request != null) {
                        holdUntilSessionReady(param, request);
                    }
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
            hookNativeLauncher(classLoader);
            installed = true;
        } catch (Throwable th) {
            XLog.e("[媒体路由] Hook 注册失败，保留官方路由", th);
        }
    }

    /**
     * CarWith builds the custom page around a MediaSession. When the target app
     * is dead there is no session yet, so the page connection fails and the
     * host falls back to the app's native Activity. Prepare the session first,
     * then let the untouched official route run.
     */
    private static void holdUntilSessionReady(
            XC_MethodHook.MethodHookParam param, RouteRequest request) {
        Context context = AutoPlayAllHook.getAppContext();
        String pkg = request.packageName;
        if (!shouldHandle(context, pkg)) {
            return;
        }
        boolean pageActive = isCustomPageActive(pkg);
        AutoPlayAllHook.log(context, "媒体路由请求: " + pkg
                + " (source=" + request.source
                + ", current=" + AutoPlayAllHook.isCurrentMediaTarget(pkg)
                + ", ready=" + AutoPlayAllHook.isMediaSessionReady(pkg)
                + ", active=" + pageActive + ")");
        if ("source_from_apps".equals(request.source)) {
            // 用户从应用列表手动打开媒体应用：只打开定制页，不自动续播。
            AutoPlayAllHook.markManualOpen(pkg);
        }
        if (pageActive) {
            // Consume the click instead of letting the host fall through to
            // its native Activity branch while the custom page is already up.
            param.setResult(null);
            return;
        }
        long now = SystemClock.elapsedRealtime();
        Long inFlight = ROUTE_IN_FLIGHT.get(pkg);
        if (inFlight != null && now - inFlight.longValue() < ROUTE_IN_FLIGHT_TTL_MS) {
            param.setResult(null);
            return;
        }
        if (AutoPlayAllHook.isMediaSessionReady(pkg)) {
            param.setResult(null);
            AutoPlayAllHook.learnCurrentMediaPublic(pkg);
            AutoPlayAllHook.refreshPanelFor(pkg);
            openCustomMediaPage(context, pkg, request.source);
            return;
        }
        ROUTE_IN_FLIGHT.put(pkg, Long.valueOf(now));
        AutoPlayAllHook.learnCurrentMediaPublic(pkg);
        AutoPlayAllHook.prepareMediaSession(context, pkg, false);
        AutoPlayAllHook.log(context, "媒体路由等待媒体会话就绪: " + pkg);
        param.setResult(null);
        pollAndReplay(request, now);
    }

    private static void pollAndReplay(final RouteRequest request, final long startedAt) {
        Runnable poll = new Runnable() {
            @Override
            public void run() {
                Context context = AutoPlayAllHook.getAppContext();
                if (context == null) {
                    ROUTE_IN_FLIGHT.remove(request.packageName);
                    invokeOriginal(request);
                    return;
                }
                if (AutoPlayAllHook.isMediaSessionReady(request.packageName)) {
                    ROUTE_IN_FLIGHT.remove(request.packageName);
                    AutoPlayAllHook.refreshPanelFor(request.packageName);
                    AutoPlayAllHook.log(context,
                            "媒体会话已就绪，打开定制媒体页: " + request.packageName);
                    openCustomMediaPage(context, request.packageName, request.source);
                    return;
                }
                long elapsed = SystemClock.elapsedRealtime() - startedAt;
                if (elapsed >= PREPARE_TIMEOUT_MS) {
                    ROUTE_IN_FLIGHT.remove(request.packageName);
                    // 不回退官方路由（那会打开原生 Activity），直接打开定制页，
                    // 定制页会自行等待媒体会话就绪。这样有定制页的应用只会
                    // 显示定制页，不会偶尔掉回原生页。
                    AutoPlayAllHook.log(context,
                            "媒体会话准备超时，仍打开定制媒体页: " + request.packageName);
                    openCustomMediaPage(context, request.packageName, request.source);
                    return;
                }
                handler().postDelayed(this, PREPARE_POLL_MS);
            }
        };
        handler().postDelayed(poll, PREPARE_POLL_MS);
    }

    private static void invokeOriginal(final RouteRequest request) {
        BYPASS.set(Boolean.TRUE);
        try {
            Method method = request.method;
            if (method != null) {
                method.setAccessible(true);
                Object target = Modifier.isStatic(method.getModifiers())
                        ? null : request.thisObject;
                method.invoke(target, request.args);
            }
        } catch (Throwable th) {
            XLog.e("[媒体路由] 恢复官方路由失败: " + request.packageName, th);
        } finally {
            BYPASS.remove();
        }
    }

    private static void hookNativeLauncher(ClassLoader classLoader) {
        try {
            Class<?> launcher = XposedHelpers.findClass(
                    "com.carwith.launcher.ams.a", classLoader);
            boolean hooked = false;
            for (Method method : launcher.getDeclaredMethods()) {
                Class<?>[] params = method.getParameterTypes();
                if (!"X".equals(method.getName())
                        || method.getReturnType() != Void.TYPE
                        || params.length != 7
                        || params[0] != Context.class
                        || params[3] != String.class) {
                    continue;
                }
                XposedBridge.hookMethod(method, new XC_MethodHook() {
                    @Override
                    protected void beforeHookedMethod(MethodHookParam param) {
                        try {
                            if (param.args == null || param.args.length < 4
                                    || !(param.args[3] instanceof String)) {
                                return;
                            }
                            Context context = param.args[0] instanceof Context
                                    ? (Context) param.args[0] : AutoPlayAllHook.getAppContext();
                            String pkg = (String) param.args[3];
                            if (!shouldHandle(context, pkg)) {
                                return;
                            }
                            // Always stop the native Activity path for a
                            // custom-capable media app. Even when the player
                            // session is not ready yet, prepare it and open the
                            // custom page instead of letting CarWith show the
                            // native Activity.
                            param.setResult(null);
                            long now = SystemClock.elapsedRealtime();
                            Long last = NATIVE_REDIRECTED.get(pkg);
                            if (last != null
                                    && now - last.longValue() < NATIVE_REDIRECT_GUARD_MS) {
                                AutoPlayAllHook.log(context,
                                        "原生页面启动已忽略（短时间内重复）: " + pkg);
                                return;
                            }
                            NATIVE_REDIRECTED.put(pkg, Long.valueOf(now));
                            AutoPlayAllHook.log(context,
                                    "拦截原生页面启动，改走定制媒体页: " + pkg);
                            redirectToCustom(context, pkg, now);
                        } catch (Throwable th) {
                            XLog.e("[媒体路由] 原生页面拦截失败", th);
                        }
                    }
                });
                hooked = true;
            }
            if (hooked) {
                XLog.i("[媒体路由] 原生页面防护已注册");
            }
        } catch (Throwable th) {
            XLog.e("[媒体路由] 原生页面防护注册失败", th);
        }
    }

    private static void redirectToCustom(
            final Context context, final String pkg, final long startedAt) {
        if (AutoPlayAllHook.isMediaSessionReady(pkg)) {
            openCustomMediaPage(context, pkg, "source_from_media_guard");
            return;
        }
        AutoPlayAllHook.prepareMediaSession(context, pkg, false);
        AutoPlayAllHook.log(context, "原生页面防护等待媒体会话: " + pkg);
        handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (AutoPlayAllHook.isMediaSessionReady(pkg)) {
                    AutoPlayAllHook.log(context,
                            "原生页面防护：媒体会话已就绪，打开定制页: " + pkg);
                    openCustomMediaPage(context, pkg, "source_from_media_guard");
                    return;
                }
                if (SystemClock.elapsedRealtime() - startedAt < PREPARE_TIMEOUT_MS) {
                    handler().postDelayed(this, PREPARE_POLL_MS);
                    return;
                }
                AutoPlayAllHook.log(context,
                        "原生页面防护：会话准备超时，仍保持定制页: " + pkg);
                openCustomMediaPage(context, pkg, "source_from_media_guard");
            }
        }, PREPARE_POLL_MS);
    }

    private static boolean isCustomPageActive(String pkg) {
        try {
            Class<?> manager = XposedHelpers.findClass(
                    "com.carwith.launcher.ams.UCarFragmentManager",
                    AutoPlayAllHook.getHostClassLoader());
            Object active = XposedHelpers.callStaticMethod(manager, "getmActiveFragmentTag");
            return active instanceof String
                    && (CUSTOM_FRAGMENT_PREFIX + pkg).equals(active);
        } catch (Throwable ignored) {
            return false;
        }
    }

    private static boolean shouldHandle(Context context, String pkg) {
        return context != null
                && pkg != null
                && pkg.indexOf('.') > 0
                && AutoPlayAllHook.isCarConnected()
                && CUSTOM_CAPABLE.contains(pkg)
                && AutoPlayAllHook.isMediaAppName(pkg)
                && !prefersNative(context, pkg);
    }

    private static boolean openCustomMediaPage(Context context, String pkg, String source) {
        if (isCustomPageActive(pkg)) {
            return true;
        }
        long now = SystemClock.elapsedRealtime();
        Long last = CUSTOM_OPEN_AT.get(pkg);
        if (last != null && now - last.longValue() < CUSTOM_OPEN_DEBOUNCE_MS) {
            return true;
        }
        CUSTOM_OPEN_AT.put(pkg, Long.valueOf(now));
        try {
            Class<?> manager = XposedHelpers.findClass(
                    "com.carwith.launcher.ams.UCarFragmentManager",
                    AutoPlayAllHook.getHostClassLoader());
            FORCED_CUSTOM.put(pkg, Long.valueOf(SystemClock.elapsedRealtime()));
            XposedHelpers.callStaticMethod(
                    manager, "jumpTo", pkg,
                    source == null || source.isEmpty() ? "source_from_home" : source);
            AutoPlayAllHook.log(context, "已跳转定制媒体页: " + pkg);
            return true;
        } catch (Throwable th) {
            XLog.e("[媒体路由] 跳转定制媒体页失败: " + pkg, th);
            return false;
        }
    }

    private static void applyRoutePolicy(XC_MethodHook.MethodHookParam param) {
        if (!(param.args.length == 1 && param.args[0] instanceof String)) {
            return;
        }
        String requestedPackage = (String) param.args[0];
        Long forcedAt = FORCED_CUSTOM.get(requestedPackage);
        if (forcedAt != null) {
            FORCED_CUSTOM.remove(requestedPackage);
            if (SystemClock.elapsedRealtime() - forcedAt.longValue() <= FORCED_CUSTOM_TTL_MS) {
                param.setResult(Boolean.TRUE);
                return;
            }
        }
        RouteRequest request = CURRENT_REQUEST.get();
        if (request == null || request.isExpired()) {
            return;
        }
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

    private static Handler handler() {
        if (sHandler == null) {
            sHandler = new Handler(Looper.getMainLooper());
        }
        return sHandler;
    }

    private static final class RouteRequest {
        final String packageName;
        final String source;
        final long startedAt;
        final Method method;
        final Object thisObject;
        final Object[] args;

        RouteRequest(String packageName, String source, Method method,
                     Object thisObject, Object[] args) {
            this.packageName = packageName;
            this.source = source;
            this.startedAt = SystemClock.elapsedRealtime();
            this.method = method;
            this.thisObject = thisObject;
            this.args = args;
        }

        static RouteRequest from(XC_MethodHook.MethodHookParam param) {
            if (!(param.method instanceof Method) || param.args == null) {
                return null;
            }
            Method method = (Method) param.method;
            Class<?>[] parameterTypes = method.getParameterTypes();
            String packageName = null;
            String source = null;
            for (int i = 0; i < param.args.length; i++) {
                if (parameterTypes[i] != String.class || !(param.args[i] instanceof String)) {
                    continue;
                }
                String value = (String) param.args[i];
                if (packageName == null && value.indexOf('.') > 0) {
                    packageName = value;
                } else if (source == null && value.indexOf('.') < 0) {
                    source = value;
                }
            }
            if (packageName == null) {
                return null;
            }
            return new RouteRequest(packageName, source, method,
                    param.thisObject, param.args.clone());
        }

        boolean isExpired() {
            return SystemClock.elapsedRealtime() - startedAt > REQUEST_TTL_MS;
        }
    }
}
