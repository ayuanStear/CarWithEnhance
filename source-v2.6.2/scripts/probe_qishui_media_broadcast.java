import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Looper;
import android.view.KeyEvent;

/** One-shot diagnostic probe: deliver an explicit media play key to Qishui while stopped. */
public final class probe_qishui_media_broadcast {
    private static final String PKG = "com.luna.music";
    private static final String RECEIVER = "com.luna.biz.playing.player.BachMediaButtonReceiver";

    public static void main(String[] args) throws Exception {
        Looper.prepareMainLooper();
        Class<?> activityThreadClass = Class.forName("android.app.ActivityThread");
        Object activityThread = activityThreadClass.getMethod("systemMain").invoke(null);
        Context context = (Context) activityThreadClass
                .getMethod("getSystemContext")
                .invoke(activityThread);
        send(context);
        Thread.sleep(3000L);
    }

    private static void send(Context context) {
        Intent intent = new Intent(Intent.ACTION_MEDIA_BUTTON);
        intent.setComponent(new ComponentName(PKG, RECEIVER));
        intent.addFlags(Intent.FLAG_INCLUDE_STOPPED_PACKAGES
                | Intent.FLAG_RECEIVER_FOREGROUND);
        intent.putExtra(Intent.EXTRA_KEY_EVENT,
                new KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_MEDIA_PLAY));
        context.sendBroadcast(intent);
        intent.putExtra(Intent.EXTRA_KEY_EVENT,
                new KeyEvent(KeyEvent.ACTION_UP, KeyEvent.KEYCODE_MEDIA_PLAY));
        context.sendBroadcast(intent);
        System.out.println("sent explicit Qishui media play broadcasts");
    }
}
