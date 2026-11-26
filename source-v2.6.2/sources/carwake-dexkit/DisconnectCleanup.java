import android.app.ActivityManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.drawable.GradientDrawable;
import android.media.session.MediaController;
import android.media.session.MediaSessionManager;
import android.os.Handler;
import android.os.Looper;
import android.provider.Settings;
import android.text.Editable;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Set;

/** Stops the last music app after a user-configurable disconnect delay. */
public final class DisconnectCleanup {
    private static final String TAG = "CarWithX DisconnectCleanup";
    private static final String CONFIG_PREFS = "carwake_config";
    private static final String ENABLED_KEY = "disconnect_music_cleanup_enabled";
    private static final String DELAY_KEY = "disconnect_cleanup_delay_sec";
    private static final String RESUME_PREFS = "carwake_all_app_resume";
    private static final String LAST_PACKAGE_KEY = "last_package";
    private static final String MODULE_PACKAGE = "com.xiaomi.miCarWithX";
    private static final int DEFAULT_DELAY_SECONDS = 30;
    private static final int MAX_DELAY_SECONDS = 3600;

    private static final Handler MAIN = new Handler(Looper.getMainLooper());
    private static boolean knownConnected;
    private static boolean disconnectHandled;
    private static int cleanupToken;

    private DisconnectCleanup() {
    }

    public static synchronized void onConnectionChanged(Context context, boolean connected) {
        if (context == null) {
            return;
        }
        if (connected) {
            knownConnected = true;
            disconnectHandled = false;
            cancelPending();
            return;
        }
        boolean wasConnected = knownConnected;
        knownConnected = false;
        if (wasConnected && !disconnectHandled) {
            disconnectHandled = true;
            if (isCleanupEnabled(context)) {
                schedule(context);
            }
        }
    }

    /** Called directly by CarWake's cast teardown hook for an immediate timer start. */
    public static synchronized void onCarDisconnected(Context context) {
        if (context == null) {
            return;
        }
        knownConnected = false;
        if (!disconnectHandled) {
            disconnectHandled = true;
            if (isCleanupEnabled(context)) {
                schedule(context);
            }
        }
    }

    private static void schedule(Context context) {
        if (!isCleanupEnabled(context)) {
            cancelPending();
            Log.i(TAG, "disconnect music cleanup disabled");
            return;
        }
        final int seconds = readDelaySeconds(context);
        if (seconds <= 0) {
            cancelPending();
            Log.i(TAG, "disconnect cleanup disabled");
            return;
        }
        final Context appContext = context.getApplicationContext() == null
                ? context : context.getApplicationContext();
        final int token = ++cleanupToken;
        Log.i(TAG, "disconnect cleanup scheduled in " + seconds + "s");
        MAIN.postDelayed(new Runnable() {
            @Override
            public void run() {
                runCleanup(appContext, token);
            }
        }, seconds * 1000L);
    }

    private static void cancelPending() {
        cleanupToken++;
    }

    private static void runCleanup(Context context, int token) {
        if (token != cleanupToken || context == null || !isCleanupEnabled(context)) {
            return;
        }
        if (isCarConnected(context)) {
            Log.i(TAG, "disconnect cleanup canceled because CarWith reconnected");
            return;
        }
        if (readDelaySeconds(context) <= 0) {
            Log.i(TAG, "disconnect cleanup skipped because it was disabled");
            return;
        }

        String lastPackage = loadLastPackage(context);
        if (TextUtils.isEmpty(lastPackage)
                || lastPackage.equals(MODULE_PACKAGE)
                || lastPackage.equals(context.getPackageName())
                || !isPackageInstalled(context, lastPackage)) {
            Log.i(TAG, "last music package skipped: " + lastPackage);
            return;
        }
        endPackage(context, lastPackage);
    }

    private static void endPackage(Context context, String packageName) {
        boolean stopped = forceStopPackage(context, packageName);
        if (!stopped) {
            stopped = killBackgroundProcesses(context, packageName);
        }
        if (!stopped) {
            stopMediaPlayback(context, packageName);
        }
        Log.i(TAG, "disconnect cleanup package=" + packageName + " forceStopped=" + stopped);
    }

    private static boolean forceStopPackage(Context context, String packageName) {
        try {
            Class<?> invoker = Class.forName("DisconnectCleanupInvoker");
            Method method = invoker.getMethod(
                    "forceStop", Context.class, String.class);
            Object result = method.invoke(null, context, packageName);
            return Boolean.TRUE.equals(result);
        } catch (Throwable throwable) {
            Log.w(TAG, "forceStopPackage unavailable for " + packageName, throwable);
            return false;
        }
    }

    private static boolean killBackgroundProcesses(Context context, String packageName) {
        try {
            ActivityManager manager = (ActivityManager)
                    context.getSystemService(Context.ACTIVITY_SERVICE);
            if (manager == null) {
                return false;
            }
            manager.killBackgroundProcesses(packageName);
            return true;
        } catch (Throwable throwable) {
            Log.w(TAG, "killBackgroundProcesses failed for " + packageName, throwable);
            return false;
        }
    }

    private static void stopMediaPlayback(Context context, String packageName) {
        try {
            MediaSessionManager manager = (MediaSessionManager)
                    context.getSystemService(Context.MEDIA_SESSION_SERVICE);
            if (manager == null) {
                return;
            }
            List<MediaController> controllers = manager.getActiveSessions(null);
            if (controllers == null) {
                return;
            }
            for (MediaController controller : controllers) {
                if (controller != null && packageName.equals(controller.getPackageName())) {
                    controller.getTransportControls().pause();
                    controller.getTransportControls().stop();
                    Log.i(TAG, "media fallback stopped " + packageName);
                    return;
                }
            }
        } catch (Throwable throwable) {
            Log.w(TAG, "media fallback failed for " + packageName, throwable);
        }
    }

    private static boolean isCarConnected(Context context) {
        try {
            return Settings.Global.getInt(
                    context.getContentResolver(), "carwithenhance_is_connected", 0) == 1
                    || "1".equals(Settings.Global.getString(
                            context.getContentResolver(), "ucar_casting_state"));
        } catch (Throwable throwable) {
            Log.w(TAG, "connection state read failed", throwable);
            return false;
        }
    }

    private static boolean isPackageInstalled(Context context, String packageName) {
        try {
            context.getPackageManager().getPackageInfo(packageName, 0);
            return true;
        } catch (Throwable ignored) {
            return false;
        }
    }

    private static String loadLastPackage(Context context) {
        try {
            return context.getSharedPreferences(RESUME_PREFS, 0)
                    .getString(LAST_PACKAGE_KEY, null);
        } catch (Throwable throwable) {
            Log.w(TAG, "last music package read failed", throwable);
            return null;
        }
    }

    private static boolean isCleanupEnabled(Context context) {
        try {
            return context.getSharedPreferences(CONFIG_PREFS, 0)
                    .getBoolean(ENABLED_KEY, false);
        } catch (Throwable throwable) {
            Log.w(TAG, "disconnect music cleanup switch read failed", throwable);
            return false;
        }
    }

    static int readDelaySeconds(Context context) {
        try {
            SharedPreferences preferences = context.getSharedPreferences(CONFIG_PREFS, 0);
            try {
                return clampDelaySeconds(
                        preferences.getInt(DELAY_KEY, DEFAULT_DELAY_SECONDS));
            } catch (ClassCastException castException) {
                String raw = preferences.getString(
                        DELAY_KEY, String.valueOf(DEFAULT_DELAY_SECONDS));
                return clampDelaySeconds(parseDelaySeconds(raw, DEFAULT_DELAY_SECONDS));
            }
        } catch (Throwable throwable) {
            Log.w(TAG, "disconnect cleanup delay read failed", throwable);
            return DEFAULT_DELAY_SECONDS;
        }
    }

    static void saveDelaySeconds(Context context, int seconds) {
        try {
            context.getSharedPreferences(CONFIG_PREFS, 0)
                    .edit()
                    .putInt(DELAY_KEY, clampDelaySeconds(seconds))
                    .apply();
        } catch (Throwable throwable) {
            Log.w(TAG, "disconnect cleanup delay write failed", throwable);
        }
    }

    private static int parseDelaySeconds(String value, int fallback) {
        if (TextUtils.isEmpty(value)) {
            return fallback;
        }
        try {
            return Integer.parseInt(value.trim());
        } catch (Throwable ignored) {
            return fallback;
        }
    }

    private static int clampDelaySeconds(int seconds) {
        if (seconds < 0) {
            return 0;
        }
        return Math.min(seconds, MAX_DELAY_SECONDS);
    }

    /** Adds a manual seconds field to the CarWake settings page. */
    public static void addSettingsUi(Context context, LinearLayout root) {
        if (context == null || root == null) {
            return;
        }

        TextView section = new TextView(context);
        section.setText("断开后清理");
        section.setTextSize(TypedValue.COMPLEX_UNIT_SP, 13.0f);
        section.setTextColor(0xFF8A8A93);
        section.setPadding(dp(context, 6), dp(context, 18), dp(context, 6), dp(context, 8));
        root.addView(section, new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT));

        LinearLayout card = new LinearLayout(context);
        card.setOrientation(LinearLayout.VERTICAL);
        GradientDrawable background = new GradientDrawable();
        background.setColor(0xFFFFFFFF);
        background.setCornerRadius(dp(context, 12));
        card.setBackground(background);
        card.setPadding(dp(context, 2), dp(context, 2), dp(context, 2), dp(context, 2));
        root.addView(card, new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT));

        LinearLayout row = new LinearLayout(context);
        row.setOrientation(LinearLayout.HORIZONTAL);
        row.setGravity(Gravity.CENTER_VERTICAL);
        row.setPadding(dp(context, 18), dp(context, 10), dp(context, 18), dp(context, 6));
        card.addView(row, new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT));

        TextView title = new TextView(context);
        title.setText("倒计时（秒）");
        title.setTextSize(TypedValue.COMPLEX_UNIT_SP, 17.0f);
        title.setTextColor(0xFF1A1A1E);
        row.addView(title, new LinearLayout.LayoutParams(
                0, LinearLayout.LayoutParams.WRAP_CONTENT, 1.0f));

        final EditText input = new EditText(context);
        input.setText(String.valueOf(readDelaySeconds(context)));
        input.setHint(String.valueOf(DEFAULT_DELAY_SECONDS));
        input.setTextSize(TypedValue.COMPLEX_UNIT_SP, 17.0f);
        input.setTextColor(0xFF1A1A1E);
        input.setGravity(Gravity.END | Gravity.CENTER_VERTICAL);
        input.setSingleLine(true);
        input.setInputType(InputType.TYPE_CLASS_NUMBER);
        input.setImeOptions(EditorInfo.IME_ACTION_DONE);
        input.setSelectAllOnFocus(true);
        row.addView(input, new LinearLayout.LayoutParams(
                dp(context, 110), LinearLayout.LayoutParams.WRAP_CONTENT));

        input.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence text, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence text, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                String raw = editable == null ? "" : editable.toString().trim();
                if (raw.length() == 0) {
                    return;
                }
                try {
                    saveDelaySeconds(context, Integer.parseInt(raw));
                } catch (Throwable ignored) {
                }
            }
        });
        input.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (!hasFocus) {
                    normalizeInput(context, input);
                }
            }
        });
        input.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView view, int actionId, android.view.KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    normalizeInput(context, input);
                    InputMethodManager manager = (InputMethodManager)
                            context.getSystemService(Context.INPUT_METHOD_SERVICE);
                    if (manager != null) {
                        manager.hideSoftInputFromWindow(input.getWindowToken(), 0);
                    }
                    input.clearFocus();
                    return true;
                }
                return false;
            }
        });

        TextView help = new TextView(context);
        help.setText("输入 0-3600 秒，0 表示关闭。倒计时结束后在后台结束后台记录的最后音乐 App；倒计时期间重新连接车机会自动取消。");
        help.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12.0f);
        help.setTextColor(0xFF8A8A93);
        help.setPadding(dp(context, 18), dp(context, 0), dp(context, 18), dp(context, 20));
        card.addView(help, new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT));
    }

    private static void normalizeInput(Context context, EditText input) {
        String raw = input.getText() == null ? "" : input.getText().toString().trim();
        int seconds = raw.length() == 0
                ? readDelaySeconds(context)
                : clampDelaySeconds(parseDelaySeconds(raw, readDelaySeconds(context)));
        saveDelaySeconds(context, seconds);
        input.setText(String.valueOf(seconds));
        input.setSelection(input.getText().length());
    }

    private static int dp(Context context, int value) {
        return Math.round(TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, value, context.getResources().getDisplayMetrics()));
    }
}
