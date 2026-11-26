import android.app.AndroidAppHelper;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;

/** Gates the CarLink head-unit microphone request with the CarWake XiaoAi switch. */
public final class CarMicSwitch {
    private static final String TAG = "CarWithX CarMic";
    private static final String AUTHORITY_URI = "content://com.xiaomi.miCarWithX.config";
    private static final String KEY_VOICE_WAKE = "voice_wake_car_xiaoai";

    private CarMicSwitch() {
    }

    public static boolean isEnabled() {
        Context context = AndroidAppHelper.currentApplication();
        if (context == null) {
            return false;
        }
        try {
            Bundle result = context.getContentResolver().call(
                    Uri.parse(AUTHORITY_URI), "get", null, null);
            boolean enabled = result == null
                    || result.getBoolean(KEY_VOICE_WAKE, true);
            Log.i(TAG, "head-unit microphone request enabled=" + enabled);
            return enabled;
        } catch (Throwable throwable) {
            Log.w(TAG, "voice switch read failed", throwable);
            return true;
        }
    }
}
