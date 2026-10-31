package hun.mesh.carwithenhance.utils;

import android.content.Context;
import android.os.Bundle;
import de.robv.android.xposed.XposedHelpers;
import java.lang.reflect.Method;

/* JADX INFO: compiled from: r8-map-id-2844820780df513d20833d92dac6e5f4b7fbbd08283aea6743910c9549ac13d9 */
/* JADX INFO: loaded from: classes.dex */
public class AppUtils {
    private static Method systemPropertiesGetMethod;

    public static String bundleToString(Bundle bundle) {
        if (bundle == null) {
            return "null";
        }
        StringBuilder sb = new StringBuilder("{");
        for (String str : bundle.keySet()) {
            sb.append(str);
            sb.append("=");
            sb.append(bundle.get(str));
            sb.append(", ");
        }
        if (sb.length() > 1) {
            sb.setLength(sb.length() - 2);
        }
        sb.append("}");
        return sb.toString();
    }

    public static Context getAppContext(ClassLoader classLoader) {
        try {
            Object objCallStaticMethod = XposedHelpers.callStaticMethod(XposedHelpers.findClass("android.app.ActivityThread", (ClassLoader) null), "currentActivityThread", new Object[0]);
            if (objCallStaticMethod != null) {
                return (Context) XposedHelpers.callMethod(objCallStaticMethod, "getApplication", new Object[0]);
            }
        } catch (Throwable th) {
            XLog.e("getAppContext 失败: ", th);
        }
        return null;
    }

    public static String getSystemProperty(String str, String str2) {
        String str3;
        try {
            if (systemPropertiesGetMethod == null) {
                systemPropertiesGetMethod = Class.forName("android.os.SystemProperties").getMethod("get", String.class);
            }
            str3 = (String) systemPropertiesGetMethod.invoke(null, str);
        } catch (Exception e) {
            XLog.e("AppUtils,读取系统属性异常", e);
            str3 = str2;
        }
        return (str3 == null || str3.length() == 0) ? str2 : str3;
    }
}
