import android.app.Activity;
import android.app.AndroidAppHelper;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;

import java.io.File;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.callbacks.XC_LoadPackage;
import de.robv.android.xposed.XposedHelpers;

/** Runtime resolver for CarWake's CarWith, CarLife, EasyConn and voice targets. */
public final class CarWakeDexKitTargets {
    public static final int RESOLVER_VERSION = 3;
    public static final String CACHE_NAME = "carwake_dexkit_cache";

    private static final String CACHE_FINGERPRINT = "fingerprint";
    private static final String TARGET_PREFIX = "target.";
    private static final String[] TARGET_KEYS = {
        "home_activity_on_resume",
        "music_open",
        "cast_teardown",
        "map_config_get_instance",
        "map_config_current_pkg",
        "navigation_get_instance",
        "navigation_go",
        "navigation_go_back",
        "navigation_go_back_all_noarg",
        "navigation_go_back_all_bool",
        "map_navigation_params_constructor",
        "fragment_active_tag",
        "fragment_jump_to",
        "home_view_click",
        "engine_asr_start",
        "engine_asr_stop",
        "engine_asr_timeout",
        "engine_force_stop",
        "easyconn_process_audio",
        "carlife_music_audio",
        "econnect_audio",
        "media_auto_play_trigger",
        "system_audio_callback",

    };
    private static final String[] EMPTY_PARAMS = new String[0];
    private static final String MAP_CONFIG_CLASS = "com.carwith.launcher.map.MapConfigManager";
    private static final String NAVIGATION_CLASS = "com.carwith.launcher.navigationrouting.NavigationRouting";
    private static final String MAP_PARAMS_CLASS = "com.carwith.launcher.map.wms.MapNavigationParams";
    private static final String FRAGMENT_CLASS = "com.carwith.launcher.ams.UCarFragmentManager";
    private static final String HOME_VIEW_CLASS = "com.carwith.launcher.docker.home.HomeView";


    private static boolean resolved;
    private static final Map<String, String> resolvedTargets = new HashMap<>();

    private static String homeActivityClass;
    private static String homeResumeMethod;
    private static String musicOpenClass;
    private static String musicOpenMethod;
    private static String castTeardownClass;
    private static String castTeardownMethod;
    private static String mapConfigClass;
    private static String mapConfigGetInstanceMethod;
    private static String mapConfigCurrentPkgMethod;
    private static String navigationClass;
    private static String navigationGetInstanceMethod;
    private static String navigationGoMethod;
    private static String navigationGoBackMethod;
    private static String navigationGoBackAllMethod;
    private static String navigationGoBackAllBoolMethod;
    private static String mapNavigationParamsClass;
    private static String displayTypeClass;
    private static String navigationFromClass;
    private static String fragmentManagerClass;
    private static String fragmentActiveTagMethod;
    private static String fragmentJumpToMethod;
    private static String homeViewClass;
    private static String homeViewClickMethod;
    private static String autoPlayTriggerClass;
    private static String autoPlayTriggerMethod;
    private static String engineBridgeClass;
    private static String asrStartMethod;
    private static String asrTimeoutMethod;
    private static String[] asrStopMethods = new String[0];
    private static String easyConnAudioClass;
    private static String easyConnProcessAudioMethod;
    private static String carAudioSourceClass;
    private static String musicAudioMethod;
    private static String eConnectAudioMethod;
    private static String systemAudioCallbackClass;
    private static String systemAudioCallbackMethod;

    private CarWakeDexKitTargets() {
    }

    private static u0 newMatcher() {
        return CarWakeDexKitFactory.newQueryMatcher();
    }

    private static v0 newInvocationMatcher() {
        return CarWakeDexKitFactory.newInvocationMatcher();
    }

    private static final class Invocation {
        final String className;
        final String methodName;
        final String returnType;
        final String[] params;

        Invocation(String className, String methodName, String returnType, String... params) {
            this.className = className;
            this.methodName = methodName;
            this.returnType = returnType;
            this.params = params;
        }
    }

    public static boolean resolveForLoadPackage(XC_LoadPackage.LoadPackageParam loadPackageParam) {
        Context context = null;
        try {
            context = AndroidAppHelper.currentApplication();
        } catch (Throwable ignored) {
        }
        return resolve(context, loadPackageParam.appInfo.sourceDir);
    }

    public static synchronized boolean resolve(Context context, String sourceDir) {
        if (resolved) {
            return true;
        }
        clearFields();
        resolvedTargets.clear();
        String fingerprint;
        try {
            fingerprint = buildFingerprint(sourceDir);
        } catch (Throwable throwable) {
            fingerprint = null;
        }
        if (context != null && loadCache(context, fingerprint)) {
            resolved = true;
            XposedBridge.log("CarWake DexKit: loaded cached targets for " + sourceDir);
            return true;
        }

        BridgeHandle bridge = null;
        try {
            System.loadLibrary("dexkit");
            bridge = BridgeHandle.open(sourceDir);
            resolveAll(bridge);
        } catch (Throwable throwable) {
            XposedBridge.log("CarWake DexKit: resolution failed: " + throwable);
            XposedBridge.log(throwable);
        } finally {
            if (bridge != null) {
                try {
                    bridge.close();
                } catch (Throwable ignored) {
                }
            }
        }
        boolean complete = allTargetsResolved();
        resolved = true;
        if (context != null && complete) {
            saveCache(context, fingerprint);
        }
        XposedBridge.log("CarWake DexKit: resolved " + resolvedTargets.size() + "/" + TARGET_KEYS.length + " targets");
        return complete;
    }

    public static boolean isResolved() {
        return resolved;
    }

    private static void clearFields() {
        homeActivityClass = null;
        homeResumeMethod = null;
        musicOpenClass = null;
        musicOpenMethod = null;
        castTeardownClass = null;
        castTeardownMethod = null;
        mapConfigClass = null;
        mapConfigGetInstanceMethod = null;
        mapConfigCurrentPkgMethod = null;
        navigationClass = null;
        navigationGetInstanceMethod = null;
        navigationGoMethod = null;
        navigationGoBackMethod = null;
        navigationGoBackAllMethod = null;
        navigationGoBackAllBoolMethod = null;
        mapNavigationParamsClass = null;
        displayTypeClass = null;
        navigationFromClass = null;
        fragmentManagerClass = null;
        fragmentActiveTagMethod = null;
        fragmentJumpToMethod = null;
        homeViewClass = null;
        homeViewClickMethod = null;
        autoPlayTriggerClass = null;
        autoPlayTriggerMethod = null;
        engineBridgeClass = null;
        asrStartMethod = null;
        asrTimeoutMethod = null;
        asrStopMethods = new String[0];
        easyConnAudioClass = null;
        easyConnProcessAudioMethod = null;
        carAudioSourceClass = null;
        musicAudioMethod = null;
        eConnectAudioMethod = null;
        systemAudioCallbackClass = null;
        systemAudioCallbackMethod = null;
    }

    private static boolean allTargetsResolved() {
        if (resolvedTargets.size() != TARGET_KEYS.length) {
            return false;
        }
        for (String key : TARGET_KEYS) {
            if (!resolvedTargets.containsKey(key)) {
                return false;
            }
        }
        return true;
    }

    private static String buildFingerprint(String sourceDir) throws Exception {
        File file = new File(sourceDir);
        return file.getCanonicalPath() + "|" + file.length() + "|" + file.lastModified() + "|" + RESOLVER_VERSION;
    }

    private static final class BridgeHandle {
        private final Object bridge;
        private final Method queryMethod;
        private final Method closeMethod;

        private BridgeHandle(Object bridge, Method queryMethod, Method closeMethod) {
            this.bridge = bridge;
            this.queryMethod = queryMethod;
            this.closeMethod = closeMethod;
        }

        static BridgeHandle open(String sourceDir) throws Exception {
            Class<?> bridgeClass = Class.forName("org.luckypray.dexkit.DexKitBridge");
            Object bridge = bridgeClass.getConstructor(String.class).newInstance(sourceDir);
            Method query = bridgeClass.getMethod("c", o0.class);
            Method close = bridgeClass.getMethod("close");
            return new BridgeHandle(bridge, query, close);
        }

        g0 query(final u0 matcher) throws Exception {
            return (g0) queryMethod.invoke(bridge, new o0() {
                @Override
                public Object b(Object queryObject) {
                    ((k0) queryObject).c = matcher;
                    return o1.a;
                }
            });
        }

        void close() throws Exception {
            closeMethod.invoke(bridge);
        }
    }

    private static g0 query(BridgeHandle bridge, final u0 matcher) {
        try {
            return bridge.query(matcher);
        } catch (Throwable throwable) {
            XposedBridge.log("CarWake DexKit: query failed: " + throwable);
            return null;
        }
    }

    private static u0 methodMatcher(
            String className,
            String methodName,
            String returnType,
            String[] params,
            String[] strings,
            Invocation[] invocations) {
        u0 matcher = newMatcher();
        if (className != null) {
            matcher.o(className);
        }
        if (methodName != null) {
            u0.l(methodName, matcher);
        }
        if (returnType != null) {
            u0.n(returnType, matcher);
        }
        if (params != null) {
            matcher.m(params);
        }
        if (strings != null && strings.length > 0) {
            matcher.q(strings);
        }
        if (invocations != null && invocations.length > 0) {
            v0 invokeMatcher = newInvocationMatcher();
            invokeMatcher.b = new ArrayList<>();
            for (Invocation invocation : invocations) {
                u0 child = newMatcher();
                if (invocation.className != null) {
                    u0.k(invocation.className, child);
                }
                u0.l(invocation.methodName, child);
                if (invocation.returnType != null) {
                    u0.n(invocation.returnType, child);
                }
                if (invocation.params != null) {
                    child.m(invocation.params);
                }
                invokeMatcher.b.add(child);
            }
            matcher.g = invokeMatcher;
        }
        return matcher;
    }

    private static List<t0> queryMethods(
            BridgeHandle bridge,
            String className,
            String methodName,
            String returnType,
            String[] params,
            String[] strings,
            Invocation[] invocations) {
        g0 result = query(bridge, methodMatcher(className, methodName, returnType, params, strings, invocations));
        List<t0> methods = new ArrayList<>();
        if (result != null) {
            methods.addAll(result);
        }
        return methods;
    }

    private static String descriptor(t0 method) {
        b0 data = method.b();
        return data.a + "#" + data.b + "(" + TextUtils.join(",", data.c) + ")" + data.d;
    }

    private static t0 unique(String key, List<t0> candidates) {
        if (candidates.size() == 1) {
            return candidates.get(0);
        }
        XposedBridge.log("CarWake DexKit: " + key + " candidates=" + candidates.size());
        return null;
    }

    private static t0 uniqueByClassSuffix(String key, List<t0> candidates, String suffix) {
        List<t0> filtered = new ArrayList<>();
        for (t0 candidate : candidates) {
            String className = candidate.b().a;
            if (className.equals(suffix) || className.endsWith("." + suffix)) {
                filtered.add(candidate);
            }
        }
        return unique(key, filtered);
    }

    private static t0 resolveSimple(
            BridgeHandle bridge,
            String key,
            String className,
            String methodName,
            String returnType,
            String[] params,
            String[] strings,
            Invocation[] invocations) {
        t0 method = unique(key, queryMethods(bridge, className, methodName, returnType, params, strings, invocations));
        if (method != null) {
            resolvedTargets.put(key, descriptor(method));
        }
        return method;
    }

    private static void resolveAll(BridgeHandle bridge) {
        List<t0> homeMethods = queryMethods(
                bridge, null, "onResume", "void", EMPTY_PARAMS, null, null);
        t0 home = uniqueByClassSuffix("home_activity_on_resume", homeMethods, "HomeActivity");
        if (home != null) {
            homeActivityClass = home.b().a;
            homeResumeMethod = home.b().b;
            resolvedTargets.put("home_activity_on_resume", descriptor(home));
        }

        t0 music = resolveSimple(
                bridge,
                "music_open",
                null,
                "k",
                "void",
                new String[]{"android.content.Context"},
                new String[]{"RecentAppUtil", " openMediaApp: supportMediaPkg is empty."},
                null);
        if (music != null) {
            musicOpenClass = music.b().a;
            musicOpenMethod = music.b().b;
        }

        t0 teardown = resolveSimple(
                bridge,
                "cast_teardown",
                null,
                "u",
                "void",
                EMPTY_PARAMS,
                new String[]{"audio_record_start_event"},
                new Invocation[]{
                    new Invocation("cc.a", "c", "com.thkj.liveeventbus.core.b", "java.lang.String", "java.lang.Class"),
                    new Invocation("com.thkj.liveeventbus.core.b", "b", "void", "androidx.lifecycle.Observer")
                });
        if (teardown != null) {
            castTeardownClass = teardown.b().a;
            castTeardownMethod = teardown.b().b;
        }

        t0 mapGetInstance = resolveSimple(
                bridge, "map_config_get_instance", MAP_CONFIG_CLASS, "getInstance", MAP_CONFIG_CLASS, EMPTY_PARAMS, null, null);
        if (mapGetInstance != null) {
            mapConfigClass = mapGetInstance.b().a;
            mapConfigGetInstanceMethod = mapGetInstance.b().b;
        }
        t0 mapCurrentPkg = resolveSimple(
                bridge, "map_config_current_pkg", MAP_CONFIG_CLASS, "getCurrentMapPkg", "java.lang.String", EMPTY_PARAMS, null, null);
        if (mapCurrentPkg != null) {
            mapConfigClass = mapCurrentPkg.b().a;
            mapConfigCurrentPkgMethod = mapCurrentPkg.b().b;
        }

        t0 navigationGetInstance = resolveSimple(
                bridge, "navigation_get_instance", NAVIGATION_CLASS, "getInstance", NAVIGATION_CLASS, EMPTY_PARAMS, null, null);
        if (navigationGetInstance != null) {
            navigationClass = navigationGetInstance.b().a;
            navigationGetInstanceMethod = navigationGetInstance.b().b;
        }
        t0 navigationGo = resolveSimple(
                bridge, "navigation_go", NAVIGATION_CLASS, "go", "void",
                new String[]{"com.carwith.common.navigationrouting.NavigationParams"}, null, null);
        if (navigationGo != null) {
            navigationClass = navigationGo.b().a;
            navigationGoMethod = navigationGo.b().b;
        }
        t0 navigationBack = resolveSimple(
                bridge, "navigation_go_back", NAVIGATION_CLASS, "goBack", "boolean", EMPTY_PARAMS, null, null);
        if (navigationBack != null) {
            navigationClass = navigationBack.b().a;
            navigationGoBackMethod = navigationBack.b().b;
        }
        t0 navigationBackAll = resolveSimple(
                bridge, "navigation_go_back_all_noarg", NAVIGATION_CLASS, "goBackAll", "void", EMPTY_PARAMS, null, null);
        if (navigationBackAll != null) {
            navigationClass = navigationBackAll.b().a;
            navigationGoBackAllMethod = navigationBackAll.b().b;
        }
        t0 navigationBackAllBool = resolveSimple(
                bridge, "navigation_go_back_all_bool", NAVIGATION_CLASS, "goBackAll", "void",
                new String[]{"boolean"}, null, null);
        if (navigationBackAllBool != null) {
            navigationClass = navigationBackAllBool.b().a;
            navigationGoBackAllBoolMethod = navigationBackAllBool.b().b;
        }

        List<t0> constructorCandidates = queryMethods(
                bridge, MAP_PARAMS_CLASS, "<init>", "void", null, null, null);
        List<t0> matchingConstructors = new ArrayList<>();
        for (t0 candidate : constructorCandidates) {
            List<String> parameters = candidate.b().c;
            if (parameters.size() == 5
                    && "java.lang.String".equals(parameters.get(0))
                    && parameters.get(1).endsWith("$MyDisplayType")
                    && parameters.get(2).endsWith("$NavigationFrom")
                    && "boolean".equals(parameters.get(3))
                    && "android.view.View".equals(parameters.get(4))) {
                matchingConstructors.add(candidate);
            }
        }
        t0 constructor = unique("map_navigation_params_constructor", matchingConstructors);
        if (constructor != null) {
            List<String> parameters = constructor.b().c;
            mapNavigationParamsClass = constructor.b().a;
            displayTypeClass = parameters.get(1);
            navigationFromClass = parameters.get(2);
            resolvedTargets.put("map_navigation_params_constructor", descriptor(constructor));
        }

        t0 activeTag = resolveSimple(
                bridge, "fragment_active_tag", FRAGMENT_CLASS, "getmActiveFragmentTag", "java.lang.String", EMPTY_PARAMS, null, null);
        if (activeTag != null) {
            fragmentManagerClass = activeTag.b().a;
            fragmentActiveTagMethod = activeTag.b().b;
        }
        t0 jumpTo = resolveSimple(
                bridge, "fragment_jump_to", FRAGMENT_CLASS, "jumpTo", "void",
                new String[]{"java.lang.String"}, null, null);
        if (jumpTo != null) {
            fragmentManagerClass = jumpTo.b().a;
            fragmentJumpToMethod = jumpTo.b().b;
        }

        t0 homeClick = resolveSimple(
                bridge, "home_view_click", HOME_VIEW_CLASS, "i", "void", EMPTY_PARAMS,
                new String[]{"toHome"}, null);
        if (homeClick != null) {
            homeViewClass = homeClick.b().a;
            homeViewClickMethod = homeClick.b().b;
        }

        t0 autoPlayTrigger = resolveSimple(
                bridge, "media_auto_play_trigger", null, null, "void",
                new String[]{"java.lang.String", "boolean"},
                new String[]{"MediaAutoPlayHelper", "auto play "}, null);
        if (autoPlayTrigger != null) {
            autoPlayTriggerClass = autoPlayTrigger.b().a;
            autoPlayTriggerMethod = autoPlayTrigger.b().b;
        }

        List<t0> audioCallbackCandidates = queryMethods(
                bridge, null, "onAudioStateChange", "void",
                new String[]{"int", "int"}, null, null);
        t0 systemAudioCallback = uniqueByClassSuffix(
                "system_audio_callback", audioCallbackCandidates, "SystemAudioCallbackWrapper");
        if (systemAudioCallback != null) {
            systemAudioCallbackClass = systemAudioCallback.b().a;
            systemAudioCallbackMethod = systemAudioCallback.b().b;
            resolvedTargets.put("system_audio_callback", descriptor(systemAudioCallback));
        }

        t0 asrStart = resolveSimple(
                bridge, "engine_asr_start", null, null, "void", null,
                new String[]{"EngineBridge", "startSpeechRecognize"}, null);
        if (asrStart != null) {
            engineBridgeClass = asrStart.b().a;
            asrStartMethod = asrStart.b().b;
        }
        if (engineBridgeClass != null) {
            t0 asrStop = resolveSimple(
                    bridge, "engine_asr_stop", engineBridgeClass, null, "void", EMPTY_PARAMS, null,
                    new Invocation[]{
                        new Invocation("java.util.concurrent.atomic.AtomicBoolean", "get", "boolean"),
                        new Invocation(null, "R", "void"),
                        new Invocation(null, "z", "void"),
                        new Invocation(null, "c", "boolean")
                    });
            t0 asrTimeout = resolveSimple(
                    bridge, "engine_asr_timeout", engineBridgeClass, null, "void", EMPTY_PARAMS,
                    new String[]{"EngineBridge", "asrTimeOutOccurs"}, null);
            t0 asrForce = resolveSimple(
                    bridge, "engine_force_stop", engineBridgeClass, null, "void",
                    new String[]{"boolean", "int"},
                    new String[]{"EngineBridge", "forceStop"}, null);
            String[] stopMethods = new String[3];
            if (asrStop != null) {
                stopMethods[0] = asrStop.b().b;
            }
            if (asrTimeout != null) {
                asrTimeoutMethod = asrTimeout.b().b;
                stopMethods[1] = asrTimeoutMethod;
            }
            if (asrForce != null) {
                stopMethods[2] = asrForce.b().b;
            }
            asrStopMethods = stopMethods;
        }

        List<t0> processAudioCandidates = queryMethods(
                bridge, null, "processAudio", "void", null, null, null);
        t0 easyConn = uniqueByClassSuffix(
                "easyconn_process_audio", processAudioCandidates, "EcSdkManager");
        if (easyConn != null) {
            easyConnAudioClass = easyConn.b().a;
            easyConnProcessAudioMethod = easyConn.b().b;
            resolvedTargets.put("easyconn_process_audio", descriptor(easyConn));
        }

        t0 carlifeAudio = resolveSimple(
                bridge, "carlife_music_audio", null, null, "void", new String[]{"byte[]"},
                new String[]{"start send music data to carlife"}, null);
        if (carlifeAudio != null) {
            carAudioSourceClass = carlifeAudio.b().a;
            musicAudioMethod = carlifeAudio.b().b;
        }
        t0 eConnectAudio = resolveSimple(
                bridge, "econnect_audio", null, null, "void", new String[]{"byte[]"},
                new String[]{"start send music data to EConnect"}, null);
        if (eConnectAudio != null) {
            carAudioSourceClass = eConnectAudio.b().a;
            eConnectAudioMethod = eConnectAudio.b().b;
        }
    }

    private static void saveCache(Context context, String fingerprint) {
        try {
            SharedPreferences preferences = context.getSharedPreferences(CACHE_NAME, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = preferences.edit().clear();
            editor.putString(CACHE_FINGERPRINT, fingerprint);
            for (Map.Entry<String, String> entry : resolvedTargets.entrySet()) {
                editor.putString(TARGET_PREFIX + entry.getKey(), entry.getValue());
            }
            editor.putString("homeActivityClass", homeActivityClass);
            editor.putString("homeResumeMethod", homeResumeMethod);
            editor.putString("musicOpenClass", musicOpenClass);
            editor.putString("musicOpenMethod", musicOpenMethod);
            editor.putString("castTeardownClass", castTeardownClass);
            editor.putString("castTeardownMethod", castTeardownMethod);
            editor.putString("mapConfigClass", mapConfigClass);
            editor.putString("mapConfigGetInstanceMethod", mapConfigGetInstanceMethod);
            editor.putString("mapConfigCurrentPkgMethod", mapConfigCurrentPkgMethod);
            editor.putString("navigationClass", navigationClass);
            editor.putString("navigationGetInstanceMethod", navigationGetInstanceMethod);
            editor.putString("navigationGoMethod", navigationGoMethod);
            editor.putString("navigationGoBackMethod", navigationGoBackMethod);
            editor.putString("navigationGoBackAllMethod", navigationGoBackAllMethod);
            editor.putString("navigationGoBackAllBoolMethod", navigationGoBackAllBoolMethod);
            editor.putString("mapNavigationParamsClass", mapNavigationParamsClass);
            editor.putString("displayTypeClass", displayTypeClass);
            editor.putString("navigationFromClass", navigationFromClass);
            editor.putString("fragmentManagerClass", fragmentManagerClass);
            editor.putString("fragmentActiveTagMethod", fragmentActiveTagMethod);
            editor.putString("fragmentJumpToMethod", fragmentJumpToMethod);
            editor.putString("homeViewClass", homeViewClass);
            editor.putString("homeViewClickMethod", homeViewClickMethod);
            editor.putString("autoPlayTriggerClass", autoPlayTriggerClass);
            editor.putString("autoPlayTriggerMethod", autoPlayTriggerMethod);
            editor.putString("engineBridgeClass", engineBridgeClass);
            editor.putString("asrStartMethod", asrStartMethod);
            editor.putString("asrTimeoutMethod", asrTimeoutMethod);
            editor.putString("asrStopMethods", TextUtils.join(",", asrStopMethods));
            editor.putString("easyConnAudioClass", easyConnAudioClass);
            editor.putString("easyConnProcessAudioMethod", easyConnProcessAudioMethod);
            editor.putString("carAudioSourceClass", carAudioSourceClass);
            editor.putString("musicAudioMethod", musicAudioMethod);
            editor.putString("eConnectAudioMethod", eConnectAudioMethod);
            editor.putString("systemAudioCallbackClass", systemAudioCallbackClass);
            editor.putString("systemAudioCallbackMethod", systemAudioCallbackMethod);
            editor.apply();
            XposedBridge.log("CarWake DexKit: saved target cache");
        } catch (Throwable throwable) {
            XposedBridge.log("CarWake DexKit: cache save failed: " + throwable);
        }
    }

    private static boolean loadCache(Context context, String fingerprint) {
        if (fingerprint == null) {
            return false;
        }
        try {
            SharedPreferences preferences = context.getSharedPreferences(CACHE_NAME, Context.MODE_PRIVATE);
            if (!fingerprint.equals(preferences.getString(CACHE_FINGERPRINT, null))) {
                return false;
            }
            for (String key : TARGET_KEYS) {
                String value = preferences.getString(TARGET_PREFIX + key, null);
                if (value == null) {
                    return false;
                }
                resolvedTargets.put(key, value);
            }
            homeActivityClass = preferences.getString("homeActivityClass", null);
            homeResumeMethod = preferences.getString("homeResumeMethod", null);
            musicOpenClass = preferences.getString("musicOpenClass", null);
            musicOpenMethod = preferences.getString("musicOpenMethod", null);
            castTeardownClass = preferences.getString("castTeardownClass", null);
            castTeardownMethod = preferences.getString("castTeardownMethod", null);
            mapConfigClass = preferences.getString("mapConfigClass", null);
            mapConfigGetInstanceMethod = preferences.getString("mapConfigGetInstanceMethod", null);
            mapConfigCurrentPkgMethod = preferences.getString("mapConfigCurrentPkgMethod", null);
            navigationClass = preferences.getString("navigationClass", null);
            navigationGetInstanceMethod = preferences.getString("navigationGetInstanceMethod", null);
            navigationGoMethod = preferences.getString("navigationGoMethod", null);
            navigationGoBackMethod = preferences.getString("navigationGoBackMethod", null);
            navigationGoBackAllMethod = preferences.getString("navigationGoBackAllMethod", null);
            navigationGoBackAllBoolMethod = preferences.getString("navigationGoBackAllBoolMethod", null);
            mapNavigationParamsClass = preferences.getString("mapNavigationParamsClass", null);
            displayTypeClass = preferences.getString("displayTypeClass", null);
            navigationFromClass = preferences.getString("navigationFromClass", null);
            fragmentManagerClass = preferences.getString("fragmentManagerClass", null);
            fragmentActiveTagMethod = preferences.getString("fragmentActiveTagMethod", null);
            fragmentJumpToMethod = preferences.getString("fragmentJumpToMethod", null);
            homeViewClass = preferences.getString("homeViewClass", null);
            homeViewClickMethod = preferences.getString("homeViewClickMethod", null);
            autoPlayTriggerClass = preferences.getString("autoPlayTriggerClass", null);
            autoPlayTriggerMethod = preferences.getString("autoPlayTriggerMethod", null);
            engineBridgeClass = preferences.getString("engineBridgeClass", null);
            asrStartMethod = preferences.getString("asrStartMethod", null);
            asrTimeoutMethod = preferences.getString("asrTimeoutMethod", null);
            String stops = preferences.getString("asrStopMethods", "");
            asrStopMethods = TextUtils.isEmpty(stops) ? new String[0] : stops.split(",");
            easyConnAudioClass = preferences.getString("easyConnAudioClass", null);
            easyConnProcessAudioMethod = preferences.getString("easyConnProcessAudioMethod", null);
            carAudioSourceClass = preferences.getString("carAudioSourceClass", null);
            musicAudioMethod = preferences.getString("musicAudioMethod", null);
            eConnectAudioMethod = preferences.getString("eConnectAudioMethod", null);
            systemAudioCallbackClass = preferences.getString("systemAudioCallbackClass", null);
            systemAudioCallbackMethod = preferences.getString("systemAudioCallbackMethod", null);
            return true;
        } catch (Throwable throwable) {
            XposedBridge.log("CarWake DexKit: cache load failed: " + throwable);
            return false;
        }
    }

    public static String getHomeActivityClass() { return homeActivityClass; }
    public static String getHomeResumeMethod() { return homeResumeMethod; }
    public static String getMusicOpenClass() { return musicOpenClass; }
    public static String getMusicOpenMethod() { return musicOpenMethod; }
    public static String getCastTeardownClass() { return castTeardownClass; }
    public static String getCastTeardownMethod() { return castTeardownMethod; }
    public static String getMapConfigClass() { return mapConfigClass; }
    public static String getMapConfigGetInstanceMethod() { return mapConfigGetInstanceMethod; }
    public static String getMapConfigCurrentPkgMethod() { return mapConfigCurrentPkgMethod; }
    public static String getNavigationClass() { return navigationClass; }
    public static String getNavigationGetInstanceMethod() { return navigationGetInstanceMethod; }
    public static String getNavigationGoMethod() { return navigationGoMethod; }
    public static String getNavigationGoBackMethod() { return navigationGoBackMethod; }
    public static String getNavigationGoBackAllMethod() { return navigationGoBackAllMethod; }
    public static String getNavigationGoBackAllBoolMethod() { return navigationGoBackAllBoolMethod; }
    public static String getMapNavigationParamsClass() { return mapNavigationParamsClass; }
    public static String getDisplayTypeClass() { return displayTypeClass; }
    public static String getNavigationFromClass() { return navigationFromClass; }
    public static String getFragmentManagerClass() { return fragmentManagerClass; }
    public static String getFragmentActiveTagMethod() { return fragmentActiveTagMethod; }
    public static String getFragmentJumpToMethod() { return fragmentJumpToMethod; }
    public static String getHomeViewClass() { return homeViewClass; }
    public static String getHomeViewClickMethod() { return homeViewClickMethod; }
    public static String getAutoPlayTriggerClass() { return autoPlayTriggerClass; }
    public static String getAutoPlayTriggerMethod() { return autoPlayTriggerMethod; }
    public static String getEngineBridgeClass() { return engineBridgeClass; }
    public static String getAsrStartMethod() { return asrStartMethod; }
    public static String[] getAsrStopMethods() {
        int count = 0;
        for (String method : asrStopMethods) {
            if (method != null && method.length() > 0) {
                count++;
            }
        }
        String[] result = new String[count];
        int index = 0;
        for (String method : asrStopMethods) {
            if (method != null && method.length() > 0) {
                result[index++] = method;
            }
        }
        return result;
    }
    public static String getAsrTimeoutMethod() { return asrTimeoutMethod; }
    public static String getEasyConnAudioClass() { return easyConnAudioClass; }
    public static String getEasyConnProcessAudioMethod() { return easyConnProcessAudioMethod; }
    public static String getCarAudioSourceClass() { return carAudioSourceClass; }
    public static String getMusicAudioMethod() { return musicAudioMethod; }
    public static String getEConnectAudioMethod() { return eConnectAudioMethod; }
    public static String getSystemAudioCallbackClass() { return systemAudioCallbackClass; }
    public static String getSystemAudioCallbackMethod() { return systemAudioCallbackMethod; }

    public static void openMusic(ClassLoader classLoader, Context context) {
        if (musicOpenClass == null || musicOpenMethod == null) {
            XposedBridge.log("CarWake DexKit: music target unavailable");
            return;
        }
        try {
            Class<?> target = XposedHelpers.findClass(musicOpenClass, classLoader);
            XposedHelpers.callStaticMethod(target, musicOpenMethod, context);
        } catch (Throwable throwable) {
            XposedBridge.log("CarWake DexKit: openMusic failed: " + throwable);
        }
    }

    public static void openDockNavigation(ClassLoader classLoader) {
        if (navigationClass == null || navigationGetInstanceMethod == null || navigationGoMethod == null
                || mapNavigationParamsClass == null || displayTypeClass == null || navigationFromClass == null) {
            XposedBridge.log("CarWake DexKit: navigation targets unavailable");
            return;
        }
        try {
            String currentMapPkg = getCurrentMapPkg(classLoader);
            if (currentMapPkg == null || currentMapPkg.length() == 0) {
                XposedBridge.log("CarWake DexKit: navigation skipped, no current map package");
                return;
            }
            Class<?> displayClass = XposedHelpers.findClass(displayTypeClass, classLoader);
            Class<?> fromClass = XposedHelpers.findClass(navigationFromClass, classLoader);
            Object bigMap = Enum.valueOf((Class) displayClass, "BIG_MAP");
            Object fromDocker = Enum.valueOf((Class) fromClass, "FROM_DOCKER");
            Class<?> paramsClass = XposedHelpers.findClass(mapNavigationParamsClass, classLoader);
            Constructor<?> constructor = paramsClass.getDeclaredConstructor(
                    String.class, displayClass, fromClass, boolean.class, View.class);
            constructor.setAccessible(true);
            Object params = constructor.newInstance(null, bigMap, fromDocker, Boolean.FALSE, null);
            Class<?> navigationTarget = XposedHelpers.findClass(navigationClass, classLoader);
            Object routing = XposedHelpers.callStaticMethod(navigationTarget, navigationGetInstanceMethod);
            XposedHelpers.callMethod(routing, navigationGoMethod, params);
        } catch (Throwable throwable) {
            XposedBridge.log("CarWake DexKit: openDockNavigation failed: " + throwable);
        }
    }

    public static boolean clickHome(ClassLoader classLoader, Activity activity) {
        if (homeViewClass == null || homeViewClickMethod == null || activity == null) {
            return false;
        }
        try {
            View decor = activity.getWindow() == null ? null : activity.getWindow().peekDecorView();
            View homeView = findHomeView(decor, 0);
            if (homeView != null) {
                XposedHelpers.callMethod(homeView, homeViewClickMethod);
                return true;
            }
            boolean handled = false;
            if (navigationClass != null && navigationGetInstanceMethod != null) {
                Class<?> navigationTarget = XposedHelpers.findClass(navigationClass, classLoader);
                Object routing = XposedHelpers.callStaticMethod(navigationTarget, navigationGetInstanceMethod);
                if (navigationGoBackMethod != null) {
                    Object result = XposedHelpers.callMethod(routing, navigationGoBackMethod);
                    handled = Boolean.TRUE.equals(result);
                }
                if (!handled) {
                    if (navigationGoBackAllMethod != null) {
                        XposedHelpers.callMethod(routing, navigationGoBackAllMethod);
                        handled = true;
                    } else if (navigationGoBackAllBoolMethod != null) {
                        XposedHelpers.callMethod(routing, navigationGoBackAllBoolMethod, Boolean.FALSE);
                        handled = true;
                    }
                }
            }
            ensureHomeFragment(classLoader);
            final ClassLoader finalClassLoader = classLoader;
            new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                @Override
                public void run() {
                    ensureHomeFragment(finalClassLoader);
                }
            }, 800L);
            return handled;
        } catch (Throwable throwable) {
            XposedBridge.log("CarWake DexKit: clickHome failed: " + throwable);
            return false;
        }
    }

    private static View findHomeView(View view, int depth) {
        if (view == null || depth > 40) {
            return null;
        }
        if (homeViewClass.equals(view.getClass().getName())) {
            return view;
        }
        if (view instanceof ViewGroup) {
            ViewGroup group = (ViewGroup) view;
            for (int index = 0; index < group.getChildCount(); index++) {
                View found = findHomeView(group.getChildAt(index), depth + 1);
                if (found != null) {
                    return found;
                }
            }
        }
        return null;
    }

    public static void ensureHomeFragment(ClassLoader classLoader) {
        if (fragmentManagerClass == null || fragmentActiveTagMethod == null || fragmentJumpToMethod == null) {
            return;
        }
        try {
            Class<?> target = XposedHelpers.findClass(fragmentManagerClass, classLoader);
            String active = (String) XposedHelpers.callStaticMethod(target, fragmentActiveTagMethod);
            if (!"home_card_v2_fragment".equals(active)) {
                XposedHelpers.callStaticMethod(target, fragmentJumpToMethod, "home_card_v2_fragment");
            }
        } catch (Throwable throwable) {
            XposedBridge.log("CarWake DexKit: ensureHomeFragment failed: " + throwable);
        }
    }

    public static String getCurrentMapPkg(ClassLoader classLoader) {
        if (mapConfigClass == null || mapConfigGetInstanceMethod == null || mapConfigCurrentPkgMethod == null) {
            return null;
        }
        try {
            Class<?> target = XposedHelpers.findClass(mapConfigClass, classLoader);
            Object instance = XposedHelpers.callStaticMethod(target, mapConfigGetInstanceMethod);
            return (String) XposedHelpers.callMethod(instance, mapConfigCurrentPkgMethod);
        } catch (Throwable throwable) {
            XposedBridge.log("CarWake DexKit: getCurrentMapPkg failed: " + throwable);
            return null;
        }
    }
}
