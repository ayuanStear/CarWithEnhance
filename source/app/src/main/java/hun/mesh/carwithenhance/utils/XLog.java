package hun.mesh.carwithenhance.utils;

import android.util.Log;
import de.robv.android.xposed.XposedBridge;

/* JADX INFO: compiled from: r8-map-id-2844820780df513d20833d92dac6e5f4b7fbbd08283aea6743910c9549ac13d9 */
/* JADX INFO: loaded from: classes.dex */
public class XLog {
    private static final String TAG = "carwithenhance";

    public static void e(String str, Throwable th) {
        String str2;
        Log.e(TAG, str, th);
        StringBuilder sb = new StringBuilder("carwithenhance ERROR: ");
        sb.append(str);
        if (th != null) {
            str2 = " (" + th.getMessage() + ")";
        } else {
            str2 = "";
        }
        sb.append(str2);
        XposedBridge.log(sb.toString());
        if (th != null) {
            XposedBridge.log(th);
        }
    }

    public static void i(String str) {
        Log.i(TAG, str);
        XposedBridge.log("carwithenhance: " + str);
    }

    public static void e(String str) {
        Log.e(TAG, str);
        XposedBridge.log("carwithenhance ERROR: " + str);
    }
}
