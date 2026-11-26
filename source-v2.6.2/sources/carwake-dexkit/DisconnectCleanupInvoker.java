import android.app.ActivityManager;
import android.content.Context;

import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;

/** Xposed-only bridge for the hidden ActivityManager force-stop API. */
public final class DisconnectCleanupInvoker {
    private static final String TAG = "CarWithX DisconnectCleanup";

    private DisconnectCleanupInvoker() {
    }

    public static boolean forceStop(Context context, String packageName) {
        try {
            ActivityManager manager = (ActivityManager)
                    context.getSystemService(Context.ACTIVITY_SERVICE);
            if (manager == null) {
                return false;
            }
            try {
                XposedHelpers.callMethod(manager, "forceStopPackage", packageName);
            } catch (Throwable oneArgFailure) {
                int userId = android.os.Process.myUid() / 100000;
                XposedHelpers.callMethod(manager, "forceStopPackage", packageName, userId);
            }
            XposedBridge.log(TAG + ": force-stopped " + packageName);
            return true;
        } catch (Throwable throwable) {
            XposedBridge.log(TAG + ": forceStopPackage failed for "
                    + packageName + ": " + throwable);
            return false;
        }
    }
}
