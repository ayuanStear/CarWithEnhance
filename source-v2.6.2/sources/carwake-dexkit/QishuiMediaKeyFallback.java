import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.media.session.MediaController;
import android.media.session.MediaSessionManager;
import android.os.SystemClock;
import android.view.KeyEvent;

import java.util.List;

import de.robv.android.xposed.XposedBridge;

/**
 * Cold-starts Qishui with an explicit media button only after CarWith reaches its
 * native auto-play entry point. The entry point runs after the car audio channel
 * is established; no Activity is launched and no broadcast is sent from a timer.
 */
public final class QishuiMediaKeyFallback {
    private static final String TAG = "CarWithX QishuiFallback";
    private static final String QISHUI_PACKAGE = "com.luna.music";
    private static final String QISHUI_RECEIVER =
            "com.luna.biz.playing.player.BachMediaButtonReceiver";
    private static final long DEDUPE_MS = 10000L;

    private static long lastDispatchAt;

    private QishuiMediaKeyFallback() {
    }

    public static synchronized void resumeIfNeeded(Context context, String packageName) {
        if (context == null) {
            return;
        }
        boolean hasActiveSession = hasActiveSession(context, packageName);
        if (!QishuiMediaKeyFallbackPolicy.shouldDispatch(packageName, hasActiveSession)) {
            return;
        }
        long now = SystemClock.elapsedRealtime();
        if (lastDispatchAt != 0L && now - lastDispatchAt < DEDUPE_MS) {
            return;
        }
        lastDispatchAt = now;
        dispatchPlayKey(context, KeyEvent.ACTION_DOWN);
        dispatchPlayKey(context, KeyEvent.ACTION_UP);
        XposedBridge.log(TAG + ": dispatched stopped-package media play to " + packageName);
    }

    private static boolean hasActiveSession(Context context, String packageName) {
        try {
            MediaSessionManager manager = (MediaSessionManager)
                    context.getSystemService(Context.MEDIA_SESSION_SERVICE);
            if (manager == null) {
                return false;
            }
            List<MediaController> controllers = manager.getActiveSessions(null);
            if (controllers == null) {
                return false;
            }
            for (MediaController controller : controllers) {
                if (controller != null && packageName != null
                        && packageName.equals(controller.getPackageName())) {
                    return true;
                }
            }
        } catch (Throwable throwable) {
            XposedBridge.log(TAG + ": active-session check failed: " + throwable);
        }
        return false;
    }

    private static void dispatchPlayKey(Context context, int action) {
        Intent intent = new Intent(Intent.ACTION_MEDIA_BUTTON);
        intent.setComponent(new ComponentName(QISHUI_PACKAGE, QISHUI_RECEIVER));
        intent.addFlags(Intent.FLAG_INCLUDE_STOPPED_PACKAGES
                | Intent.FLAG_RECEIVER_FOREGROUND);
        intent.putExtra(Intent.EXTRA_KEY_EVENT,
                new KeyEvent(action, KeyEvent.KEYCODE_MEDIA_PLAY));
        context.sendBroadcast(intent);
    }
}
