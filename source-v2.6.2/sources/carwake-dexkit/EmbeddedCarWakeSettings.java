import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import de.robv.android.xposed.XposedHelpers;

/** Injects CarWake preferences into CarWithEnhance's phone-side settings screen. */
public final class EmbeddedCarWakeSettings {
    private static final String TAG = "CarWithX EmbeddedSettings";
    private static final String PREFS = "carwake_config";
    private static final String AUTHORITY_URI = "content://com.xiaomi.miCarWithX.config";
    private static final String KEY_VOICE = "voice_wake_car_xiaoai";
    private static final String KEY_ENABLED = "disconnect_music_cleanup_enabled";
    private static final String KEY_DELAY = "disconnect_cleanup_delay_sec";
    private static final String KEY_CATEGORY = "carwake_category";
    private static final String KEY_VOICE_UI = "carwake_voice_wake";
    private static final String KEY_ENABLED_UI = "carwake_disconnect_music_cleanup";
    private static final String KEY_DELAY_UI = "carwake_cleanup_delay";
    private static final int DEFAULT_DELAY = 30;
    private static final int MAX_DELAY = 3600;

    private EmbeddedCarWakeSettings() {
    }

    public static void inject(Object screen) {
        if (screen == null) {
            return;
        }
        try {
            Context context = (Context) XposedHelpers.callMethod(screen, "getContext");
            if (context == null) {
                return;
            }
            Object existing = XposedHelpers.callMethod(screen, "findPreference", KEY_CATEGORY);
            if (existing == null) {
                createCategory(screen, context);
            } else {
                refresh(screen, context);
            }
        } catch (Throwable throwable) {
            Log.e(TAG, "inject failed", throwable);
        }
    }

    private static void createCategory(Object screen, Context context) {
        ClassLoader host = screen.getClass().getClassLoader();
        Object category = XposedHelpers.newInstance(
                XposedHelpers.findClass("androidx.preference.PreferenceCategory", host),
                context);
        XposedHelpers.callMethod(category, "setKey", KEY_CATEGORY);
        XposedHelpers.callMethod(category, "setTitle", "CarWake");
        XposedHelpers.callMethod(screen, "addPreference", category);

        Object voice = newSwitch(host, context, KEY_VOICE_UI, "车机端小爱");
        XposedHelpers.callMethod(voice, "setSummary", "车机端小爱使用车机麦克风（VR 通道）");
        Object enabled = newSwitch(host, context, KEY_ENABLED_UI, "断开连接时结束音乐");
        XposedHelpers.callMethod(enabled, "setSummary", "结束后台记录的最后音乐 App");

        Object delay = XposedHelpers.newInstance(
                XposedHelpers.findClass("androidx.preference.EditTextPreference", host),
                context);
        XposedHelpers.callMethod(delay, "setKey", KEY_DELAY_UI);
        XposedHelpers.callMethod(delay, "setTitle", "断开后清理倒计时（秒）");

        XposedHelpers.callMethod(category, "addPreference", voice);
        XposedHelpers.callMethod(category, "addPreference", enabled);
        XposedHelpers.callMethod(category, "addPreference", delay);

        attachSwitchListener(host, context, voice, KEY_VOICE);
        attachEnabledListener(host, context, enabled, delay);
        attachDelayListener(host, context, delay);
        refresh(screen, context);
        Log.i(TAG, "CarWake category injected");
    }

    private static Object newSwitch(ClassLoader host, Context context, String key, String title) {
        Object preference = XposedHelpers.newInstance(
                XposedHelpers.findClass("androidx.preference.SwitchPreference", host),
                context);
        XposedHelpers.callMethod(preference, "setKey", key);
        XposedHelpers.callMethod(preference, "setTitle", title);
        return preference;
    }

    private static void attachSwitchListener(
            final ClassLoader host,
            final Context context,
            final Object preference,
            final String storageKey) {
        Object listener = Proxy.newProxyInstance(
                host,
                new Class[]{XposedHelpers.findClass(
                        "androidx.preference.Preference$OnPreferenceChangeListener", host)},
                new InvocationHandler() {
                    @Override
                    public Object invoke(Object proxy, Method method, Object[] args) {
                        if (!"onPreferenceChange".equals(method.getName()) || args == null
                                || args.length < 2) {
                            return null;
                        }
                        boolean value = Boolean.TRUE.equals(args[1]);
                        XposedHelpers.callMethod(preference, "setChecked", value);
                        writeBoolean(context, storageKey, value);
                        return Boolean.TRUE;
                    }
                });
        XposedHelpers.callMethod(preference, "setOnPreferenceChangeListener", listener);
    }

    private static void attachEnabledListener(
            final ClassLoader host,
            final Context context,
            final Object preference,
            final Object delayPreference) {
        Object listener = Proxy.newProxyInstance(
                host,
                new Class[]{XposedHelpers.findClass(
                        "androidx.preference.Preference$OnPreferenceChangeListener", host)},
                new InvocationHandler() {
                    @Override
                    public Object invoke(Object proxy, Method method, Object[] args) {
                        if (!"onPreferenceChange".equals(method.getName()) || args == null
                                || args.length < 2) {
                            return null;
                        }
                        boolean enabled = Boolean.TRUE.equals(args[1]);
                        XposedHelpers.callMethod(preference, "setChecked", enabled);
                        writeBoolean(context, KEY_ENABLED, enabled);
                        XposedHelpers.callMethod(delayPreference, "setVisible", enabled);
                        return Boolean.TRUE;
                    }
                });
        XposedHelpers.callMethod(preference, "setOnPreferenceChangeListener", listener);
    }

    private static void attachDelayListener(
            final ClassLoader host,
            final Context context,
            final Object delayPreference) {
        Object listener = Proxy.newProxyInstance(
                host,
                new Class[]{XposedHelpers.findClass(
                        "androidx.preference.Preference$OnPreferenceChangeListener", host)},
                new InvocationHandler() {
                    @Override
                    public Object invoke(Object proxy, Method method, Object[] args) {
                        if (!"onPreferenceChange".equals(method.getName()) || args == null
                                || args.length < 2) {
                            return null;
                        }
                        int seconds = clampDelay(parseDelay(args[1]));
                        writeInt(context, KEY_DELAY, seconds);
                        XposedHelpers.callMethod(delayPreference, "setText", String.valueOf(seconds));
                        XposedHelpers.callMethod(delayPreference, "setSummary", delaySummary(seconds));
                        return Boolean.TRUE;
                    }
                });
        XposedHelpers.callMethod(delayPreference, "setOnPreferenceChangeListener", listener);
    }

    private static void refresh(Object screen, Context context) {
        Bundle provider = providerGet(context);
        Object voice = XposedHelpers.callMethod(screen, "findPreference", KEY_VOICE_UI);
        Object enabled = XposedHelpers.callMethod(screen, "findPreference", KEY_ENABLED_UI);
        Object delay = XposedHelpers.callMethod(screen, "findPreference", KEY_DELAY_UI);
        if (voice == null || enabled == null || delay == null) {
            return;
        }

        XposedHelpers.callMethod(voice, "setChecked", readBoolean(context, provider, KEY_VOICE, true));
        boolean cleanupEnabled = readBoolean(context, provider, KEY_ENABLED, false);
        XposedHelpers.callMethod(enabled, "setChecked", cleanupEnabled);

        int seconds = readInt(context, provider, KEY_DELAY, DEFAULT_DELAY);
        XposedHelpers.callMethod(delay, "setText", String.valueOf(seconds));
        XposedHelpers.callMethod(delay, "setSummary", delaySummary(seconds));
        XposedHelpers.callMethod(delay, "setVisible", cleanupEnabled);
    }

    private static void writeBoolean(Context context, String key, boolean value) {
        context.getSharedPreferences(PREFS, 0).edit().putBoolean(key, value).apply();
        Bundle bundle = new Bundle();
        bundle.putBoolean(key, value);
        publish(context, bundle);
    }

    private static void writeInt(Context context, String key, int value) {
        context.getSharedPreferences(PREFS, 0).edit().putInt(key, value).apply();
        Bundle bundle = new Bundle();
        bundle.putInt(key, value);
        publish(context, bundle);
    }

    private static void publish(Context context, Bundle bundle) {
        try {
            context.getContentResolver().call(Uri.parse(AUTHORITY_URI), "set", null, bundle);
        } catch (Throwable throwable) {
            Log.w(TAG, "provider update failed", throwable);
        }
    }

    private static Bundle providerGet(Context context) {
        try {
            return context.getContentResolver().call(Uri.parse(AUTHORITY_URI), "get", null, null);
        } catch (Throwable throwable) {
            return null;
        }
    }

    private static boolean readBoolean(
            Context context, Bundle provider, String key, boolean fallback) {
        SharedPreferences preferences = context.getSharedPreferences(PREFS, 0);
        if (preferences.contains(key)) {
            return preferences.getBoolean(key, fallback);
        }
        return provider != null ? provider.getBoolean(key, fallback) : fallback;
    }

    private static int readInt(Context context, Bundle provider, String key, int fallback) {
        SharedPreferences preferences = context.getSharedPreferences(PREFS, 0);
        if (preferences.contains(key)) {
            try {
                return clampDelay(preferences.getInt(key, fallback));
            } catch (ClassCastException ignored) {
            }
        }
        return provider != null ? clampDelay(provider.getInt(key, fallback)) : clampDelay(fallback);
    }

    private static int parseDelay(Object value) {
        try {
            return Integer.parseInt(String.valueOf(value).trim());
        } catch (Throwable ignored) {
            return DEFAULT_DELAY;
        }
    }

    private static int clampDelay(int seconds) {
        if (seconds < 0) {
            return 0;
        }
        return Math.min(seconds, MAX_DELAY);
    }

    private static String delaySummary(int seconds) {
        return seconds == 0
                ? "0 秒：已关闭"
                : "倒计时 " + seconds + " 秒；结束后台最后音乐 App；0 表示关闭";
    }
}
