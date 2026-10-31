package hun.mesh.carwithenhance.hook;

import android.app.Application;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageItemInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.UserHandle;
import android.util.Log;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedHelpers;
import java.io.InputStream;

/* JADX INFO: loaded from: classes2.dex */
public final class CarWithIconHook {
    private static final String ASSET = "carwith_wifi_icon.png";
    private static final String MODULE = "hun.mesh.carwithenhance";
    private static final String TAG = "carwithenhance";
    private static final String TARGET = "com.miui.carlink";
    private static Drawable replacement;

    private CarWithIconHook() {
    }

    public static void install(ClassLoader classLoader) {
        hookApplicationPackageManager(classLoader, "getApplicationIcon", new Class[]{String.class});
        hookApplicationPackageManager(classLoader, "getApplicationIcon", new Class[]{ApplicationInfo.class});
        hookApplicationPackageManager(classLoader, "getApplicationIconForUser", new Class[]{ApplicationInfo.class, UserHandle.class});
        hookPackageItem(classLoader, "loadIcon", new Class[]{PackageManager.class});
        hookPackageItem(classLoader, "loadUnbadgedIcon", new Class[]{PackageManager.class});
    }

    private static void hookApplicationPackageManager(ClassLoader classLoader, String str, Class<?>[] clsArr) {
        try {
            Object[] objArr = new Object[clsArr.length + 1];
            System.arraycopy(clsArr, 0, objArr, 0, clsArr.length);
            objArr[clsArr.length] = new XC_MethodHook() { // from class: hun.mesh.carwithenhance.hook.CarWithIconHook.1
                protected void beforeHookedMethod(XC_MethodHook.MethodHookParam methodHookParam) {
                    CarWithIconHook.apply(methodHookParam, methodHookParam.args.length > 0 ? methodHookParam.args[0] : null);
                }
            };
            XposedHelpers.findAndHookMethod("android.app.ApplicationPackageManager", classLoader, str, objArr);
            Log.i(TAG, "[图标] 已挂载 ApplicationPackageManager#" + str);
        } catch (Throwable th) {
            Log.d(TAG, "[图标] 跳过 ApplicationPackageManager#" + str + ": " + th);
        }
    }

    private static void hookPackageItem(ClassLoader classLoader, String str, Class<?>[] clsArr) {
        try {
            Object[] objArr = new Object[clsArr.length + 1];
            System.arraycopy(clsArr, 0, objArr, 0, clsArr.length);
            objArr[clsArr.length] = new XC_MethodHook() { // from class: hun.mesh.carwithenhance.hook.CarWithIconHook.2
                protected void beforeHookedMethod(XC_MethodHook.MethodHookParam methodHookParam) {
                    CarWithIconHook.apply(methodHookParam, methodHookParam.thisObject);
                }
            };
            XposedHelpers.findAndHookMethod("android.content.pm.PackageItemInfo", classLoader, str, objArr);
            Log.i(TAG, "[图标] 已挂载 PackageItemInfo#" + str);
        } catch (Throwable th) {
            Log.d(TAG, "[图标] 跳过 PackageItemInfo#" + str + ": " + th);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void apply(XC_MethodHook.MethodHookParam methodHookParam, Object obj) {
        if (isTarget(obj)) {
            try {
                Drawable drawableReplacementDrawable = replacementDrawable();
                if (drawableReplacementDrawable != null) {
                    methodHookParam.setResult(drawableReplacementDrawable);
                }
            } catch (Throwable th) {
                Log.w(TAG, "[图标] 替换失败", th);
            }
        }
    }

    private static boolean isTarget(Object obj) {
        if (obj instanceof String) {
            return TARGET.equals(obj);
        }
        if (obj instanceof ApplicationInfo) {
            return TARGET.equals(((ApplicationInfo) obj).packageName);
        }
        if (obj instanceof PackageItemInfo) {
            return TARGET.equals(((PackageItemInfo) obj).packageName);
        }
        return false;
    }

    private static synchronized Drawable replacementDrawable() throws Exception {
        Drawable drawable = replacement;
        if (drawable != null) {
            return drawable;
        }
        Application applicationCurrentApplication = currentApplication();
        if (applicationCurrentApplication == null) {
            return null;
        }
        InputStream inputStreamOpen = applicationCurrentApplication.getPackageManager().getResourcesForApplication("hun.mesh.carwithenhance").getAssets().open(ASSET);
        try {
            Bitmap bitmapDecodeStream = BitmapFactory.decodeStream(inputStreamOpen);
            if (bitmapDecodeStream != null) {
                BitmapDrawable bitmapDrawable = new BitmapDrawable(applicationCurrentApplication.getResources(), bitmapDecodeStream);
                bitmapDrawable.setTargetDensity(applicationCurrentApplication.getResources().getDisplayMetrics().densityDpi);
                replacement = bitmapDrawable;
                inputStreamOpen.close();
                return bitmapDrawable;
            }
            inputStreamOpen.close();
            return null;
        } catch (Throwable th) {
            inputStreamOpen.close();
            throw th;
        }
    }

    private static Application currentApplication() {
        try {
            Class[] clsArr = new Class[0];
            return (Application) Class.forName("android.app.ActivityThread").getMethod("currentApplication", new Class<?>[0]).invoke(null, new Object[0]);
        } catch (Throwable unused) {
            return null;
        }
    }
}
