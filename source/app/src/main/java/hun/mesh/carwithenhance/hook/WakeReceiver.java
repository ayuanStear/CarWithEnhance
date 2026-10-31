package hun.mesh.carwithenhance.hook;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;

public class WakeReceiver extends BroadcastReceiver {
    public static final String ACTION = "hun.mesh.carwithenhance.action.WAKE";
    public static final String EXTRA_PKG = "pkg";

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent == null || context == null) {
            return;
        }
        String packageName = intent.getStringExtra(EXTRA_PKG);
        if (packageName == null || packageName.isEmpty() || packageName.contains(":")) {
            AutoPlayAllHook.log(context, "WakeReceiver 忽略无效包名: " + packageName);
            return;
        }
        try {
            context.getPackageManager().getPackageInfo(packageName, 0);
        } catch (PackageManager.NameNotFoundException e) {
            AutoPlayAllHook.log(context, "WakeReceiver 忽略未安装应用: " + packageName);
            return;
        }
        AutoPlayAllHook.setAppContext(context);
        String mode = intent.getStringExtra("mode");
        AutoPlayAllHook.log(context, "WakeReceiver 收到请求: " + packageName + " mode=" + mode);
        if ("mb".equals(mode)) {
            AutoPlayAllHook.log(context, "媒体按键广播已发送: " + AutoPlayAllHook.sendMediaButton(context, packageName));
        } else {
            AutoPlayAllHook.bindBrowseService(context, packageName);
        }
    }
}
