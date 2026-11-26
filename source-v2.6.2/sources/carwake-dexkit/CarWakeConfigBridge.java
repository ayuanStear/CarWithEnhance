import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/** Backs CarWake's ContentProvider without keeping logic inside the CarWake smali UI. */
public final class CarWakeConfigBridge {
    private static final String TAG = "CarWithX ConfigBridge";
    private static final String PREFS = "carwake_config";

    private CarWakeConfigBridge() {
    }

    public static Bundle call(
            Context context, String method, String arg, Bundle extras) {
        if (context == null) {
            return null;
        }
        if ("get".equals(method)) {
            return get(context);
        }
        if ("set".equals(method)) {
            return set(context, extras);
        }
        return null;
    }

    private static Bundle get(Context context) {
        Bundle result = new Bundle();
        SharedPreferences preferences = context.getSharedPreferences(PREFS, 0);
        result.putBoolean("voice_wake_car_xiaoai",
                preferences.getBoolean("voice_wake_car_xiaoai", true));
        result.putBoolean("item_music_on", preferences.getBoolean("item_music_on", true));
        result.putBoolean("item_nav_on", preferences.getBoolean("item_nav_on", false));
        result.putBoolean("item_home_on", preferences.getBoolean("item_home_on", false));
        result.putString("queue_order", preferences.getString("queue_order", null));
        result.putInt("item_gap_sec", preferences.getInt("item_gap_sec", 4));
        result.putBoolean("disconnect_music_cleanup_enabled",
                preferences.getBoolean("disconnect_music_cleanup_enabled", false));
        result.putInt("disconnect_cleanup_delay_sec",
                preferences.getInt("disconnect_cleanup_delay_sec", 30));
        return result;
    }

    private static Bundle set(Context context, Bundle extras) {
        if (extras == null) {
            return new Bundle();
        }
        SharedPreferences.Editor editor = context.getSharedPreferences(PREFS, 0).edit();
        for (String key : extras.keySet()) {
            Object value = extras.get(key);
            if (value instanceof Boolean) {
                editor.putBoolean(key, (Boolean) value);
            } else if (value instanceof Integer) {
                editor.putInt(key, (Integer) value);
            } else if (value instanceof String) {
                editor.putString(key, (String) value);
            } else if (value instanceof Set<?>) {
                Set<String> strings = new HashSet<>();
                for (Object item : (Set<?>) value) {
                    if (item != null) {
                        strings.add(item.toString());
                    }
                }
                editor.putStringSet(key, strings);
            } else if (value instanceof ArrayList<?>) {
                Set<String> strings = new HashSet<>();
                for (Object item : (ArrayList<?>) value) {
                    if (item != null) {
                        strings.add(item.toString());
                    }
                }
                editor.putStringSet(key, strings);
            }
        }
        editor.commit();
        Log.i(TAG, "CarWake config updated");
        return new Bundle();
    }
}
