package hun.mesh.carwithenhance.hook;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.CompoundButton;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;
import hun.mesh.carwithenhance.config.HookConfigs;
import hun.mesh.carwithenhance.config.HookItem;
import hun.mesh.carwithenhance.dexkit.CarWithDexKitManager;
import hun.mesh.carwithenhance.utils.XLog;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.lang.reflect.Type;
import java.util.List;

/* JADX INFO: compiled from: r8-map-id-2844820780df513d20833d92dac6e5f4b7fbbd08283aea6743910c9549ac13d9 */
/* JADX INFO: loaded from: classes.dex */
public class SettingsHook implements IHook {
    public static Object sDelayPref;

    /* JADX INFO: Access modifiers changed from: private */
    public static void refreshSwitchPref(Object obj, String str, boolean z) {
        try {
            Object objCallMethod = XposedHelpers.callMethod(obj, "findPreference", new Object[]{str});
            if (objCallMethod != null) {
                XposedHelpers.callMethod(objCallMethod, "setChecked", new Object[]{Boolean.valueOf(z)});
            }
        } catch (Throwable unused) {
        }
    }

    public static void setDelayPrefVisible(boolean visible) {
        Object preference = sDelayPref;
        if (preference == null) {
            return;
        }
        try {
            XposedHelpers.callMethod(preference, "setVisible", new Object[]{Boolean.valueOf(visible)});
        } catch (Throwable th) {
            XLog.e(">> [手机端UI] 更新延迟配置可见性失败", th);
        }
    }

    private static int parseDelaySeconds(String raw) {
        String digits = raw == null ? "" : raw.replaceAll("\\D+", "");
        if (digits.isEmpty()) {
            return 0;
        }
        try {
            long parsed = Long.parseLong(digits);
            return HookConfigs.clampDelaySeconds(parsed > Integer.MAX_VALUE ? Integer.MAX_VALUE : (int) parsed);
        } catch (NumberFormatException ignored) {
            return HookConfigs.MAX_DISCONNECT_KILL_DELAY_SEC;
        }
    }

    @Override // hun.mesh.carwithenhance.hook.IHook
    public void onHook(final ClassLoader classLoader) {
        try {
            XposedHelpers.findAndHookMethod(XposedHelpers.findClass("com.google.gson.Gson", classLoader), "fromJson", new Object[]{String.class, Type.class, new XC_MethodHook() { // from class: hun.mesh.carwithenhance.hook.SettingsHook.1
                public void beforeHookedMethod(XC_MethodHook.MethodHookParam methodHookParam) {
                    String str = (String) methodHookParam.args[0];
                    if (str == null || !str.contains("carwith_ui_size") || !str.contains("user_setting_framerate") || str.contains("mesh_hook_optimization_menu")) {
                        return;
                    }
                    StringBuilder sb = new StringBuilder("{\"type\":\"router\",\"title\":\"CarWith Enhance\",\"router\":\"mesh_hook_optimization_menu\",\"value\":[");
                    List<HookItem> allItems = HookConfigs.getAllItems();
                    for (int i = 0; i < allItems.size(); i++) {
                        HookItem hookItem = allItems.get(i);
                        sb.append("{\"type\":\"switch\",\"title\":\"");
                        sb.append(HookConfigs.escapeJson(hookItem.title));
                        sb.append("\",\"subTitle\":\"");
                        sb.append(HookConfigs.escapeJson(hookItem.subTitle));
                        sb.append("\",\"key\":\"");
                        sb.append(HookConfigs.escapeJson(hookItem.key));
                        sb.append("\",\"spValue\":1}");
                        if (i < allItems.size() - 1) {
                            sb.append(",");
                        }
                    }
                    sb.append("]}");
                    String string = sb.toString();
                    int iLastIndexOf = str.lastIndexOf(93);
                    if (iLastIndexOf != -1) {
                        methodHookParam.args[0] = str.substring(0, iLastIndexOf) + "," + string + str.substring(iLastIndexOf);
                        XLog.i(">> [车机端UI] 成功动态注入[CarWith Enhance]二级菜单！");
                    }
                }
            }});
        } catch (Throwable th) {
            XLog.e("❌ Hook 3-1 (车机端 JSON 二级菜单注入) 失败: ", th);
        }
        try {
            CarWithDexKitManager carWithDexKitManager = CarWithDexKitManager.INSTANCE;
            String settingsClass = carWithDexKitManager.getSettingsClass();
            String settingsMethod = carWithDexKitManager.getSettingsMethod();
            if (settingsClass != null && !settingsClass.isEmpty()) {
                XposedHelpers.findAndHookMethod(XposedHelpers.findClass(settingsClass, classLoader), settingsMethod, new Object[]{Context.class, String.class, SharedPreferences.class, new XC_MethodHook() { // from class: hun.mesh.carwithenhance.hook.SettingsHook.2
                    public void beforeHookedMethod(XC_MethodHook.MethodHookParam methodHookParam) {
                        String str = (String) methodHookParam.args[1];
                        HookItem hookItemFindByKey = HookConfigs.findByKey(str);
                        if (hookItemFindByKey != null) {
                            boolean z = ((Context) methodHookParam.args[0]).getSharedPreferences(HookConfigs.PREFS_FILE, 0).getBoolean(str, hookItemFindByKey.defaultValue);
                            methodHookParam.setResult(Boolean.valueOf(z));
                            XLog.i(">> [车机端UI] 拦截读取状态：" + str + " = " + z);
                        }
                    }
                }});
                try {
                    CarWithDexKitManager carWithDexKitManager2 = CarWithDexKitManager.INSTANCE;
                    String settingsAdapterClass = carWithDexKitManager2.getSettingsAdapterClass();
                    String settingsAdapterMethods = carWithDexKitManager2.getSettingsAdapterMethods();
                    if (settingsAdapterClass == null || settingsAdapterClass.isEmpty() || settingsAdapterMethods == null || settingsAdapterMethods.isEmpty()) {
                        XLog.e("SettingsHook 动态目标 adapter 未找到，跳过 Hook 3-3");
                    } else {
                        Class clsFindClass = XposedHelpers.findClass(settingsAdapterClass, classLoader);
                        for (String str : settingsAdapterMethods.split(",")) {
                            if (!str.trim().isEmpty()) {
                                XposedBridge.hookAllMethods(clsFindClass, str.trim(), new XC_MethodHook() { // from class: hun.mesh.carwithenhance.hook.SettingsHook.3
                                    public void afterHookedMethod(XC_MethodHook.MethodHookParam methodHookParam) {
                                        Object[] args = methodHookParam.args;
                                        if (args == null || args.length < 2 || args[0] == null || args[1] == null) {
                                            return;
                                        }
                                        try {
                                            final String key = (String) XposedHelpers.callMethod(args[1], "getKey", new Object[0]);
                                            if (key == null || HookConfigs.findByKey(key) == null) {
                                                return;
                                            }
                                            View view = (View) XposedHelpers.getObjectField(args[0], "itemView");
                                            if (view == null) {
                                                return;
                                            }
                                            CompoundButton foundButton = null;
                                            for (Field field : args[0].getClass().getDeclaredFields()) {
                                                field.setAccessible(true);
                                                Object value = field.get(args[0]);
                                                if (value instanceof CompoundButton) {
                                                    foundButton = (CompoundButton) value;
                                                    break;
                                                }
                                            }
                                            final CompoundButton compoundButton = foundButton;
                                            view.setOnClickListener(new View.OnClickListener() {
                                                @Override
                                                public void onClick(View clickedView) {
                                                    if (compoundButton == null) {
                                                        return;
                                                    }
                                                    boolean checked = !compoundButton.isChecked();
                                                    compoundButton.setChecked(checked);
                                                    Context context = clickedView.getContext();
                                                    context.getSharedPreferences(HookConfigs.PREFS_FILE, 0).edit().putBoolean(key, checked).apply();
                                                    try {
                                                        Settings.Global.putInt(context.getContentResolver(), key, checked ? 1 : 0);
                                                        XLog.i(">> [车机端UI] 同步写入 Settings.Global: " + key + " = " + checked);
                                                    } catch (Throwable th) {
                                                        XLog.e(">> [车机端UI] 写入 Settings.Global 失败: " + key, th);
                                                    }
                                                    XLog.i(">> [车机端UI] 开关 " + key + " 点击翻转，写入 SP: " + checked);
                                                }
                                            });
                                            XLog.i(">> [车机端UI] 绑定完成，已成功接管 itemView 点击: key=" + key);
                                        } catch (Throwable th) {
                                            XLog.e(">> [车机端UI] 接管开关点击失败", th);
                                        }
                                    }
                                });
                            }
                        }
                    }
                } catch (Throwable th2) {
                    XLog.e("❌ Hook 3-3 (车机端UI点击接管) 失败: ", th2);
                }
                try {
                    String ucarSettingsFragmentClass = CarWithDexKitManager.INSTANCE.getUcarSettingsFragmentClass();
                    if (ucarSettingsFragmentClass == null || ucarSettingsFragmentClass.isEmpty()) {
                        XLog.e("SettingsHook 动态目标 UCarScreenSettingsFragment 未找到，跳过 Hook 4");
                    } else {
                        Class clsFindClass2 = XposedHelpers.findClass(ucarSettingsFragmentClass, classLoader);
                        XposedHelpers.findAndHookMethod(clsFindClass2, "onCreatePreferences", new Object[]{Bundle.class, String.class, new XC_MethodHook() { // from class: hun.mesh.carwithenhance.hook.SettingsHook.4
                            public void afterHookedMethod(XC_MethodHook.MethodHookParam methodHookParam) {
                                Object objCallMethod;
                                final Activity activity = (Activity) XposedHelpers.callMethod(methodHookParam.thisObject, "getActivity", new Object[0]);
                                if (activity == null || (objCallMethod = XposedHelpers.callMethod(methodHookParam.thisObject, "getPreferenceScreen", new Object[0])) == null) {
                                    return;
                                }
                                final SharedPreferences sharedPreferences = activity.getSharedPreferences(HookConfigs.PREFS_FILE, 0);
                                Object objNewInstance = XposedHelpers.newInstance(XposedHelpers.findClass("androidx.preference.PreferenceCategory", classLoader), new Object[]{activity});
                                XposedHelpers.callMethod(objNewInstance, "setTitle", new Object[]{"CarWith Enhance"});
                                XposedHelpers.callMethod(objCallMethod, "addPreference", new Object[]{objNewInstance});
                                Class clsFindClass3 = XposedHelpers.findClass("androidx.preference.SwitchPreference", classLoader);
                                Class clsFindClass4 = XposedHelpers.findClass("androidx.preference.Preference$OnPreferenceChangeListener", classLoader);
                                for (final HookItem hookItem : HookConfigs.getAllItems()) {
                                    Object objNewInstance2 = XposedHelpers.newInstance(clsFindClass3, new Object[]{activity});
                                    XposedHelpers.callMethod(objNewInstance2, "setKey", new Object[]{hookItem.key});
                                    XposedHelpers.callMethod(objNewInstance2, "setTitle", new Object[]{hookItem.title});
                                    XposedHelpers.callMethod(objNewInstance2, "setSummary", new Object[]{hookItem.subTitle});
                                    XposedHelpers.callMethod(objNewInstance2, "setDefaultValue", new Object[]{Boolean.valueOf(hookItem.defaultValue)});
                                    XposedHelpers.callMethod(objNewInstance2, "setChecked", new Object[]{Boolean.valueOf(sharedPreferences.getBoolean(hookItem.key, hookItem.defaultValue))});
                                    XposedHelpers.callMethod(objNewInstance2, "setOnPreferenceChangeListener", new Object[]{Proxy.newProxyInstance(classLoader, new Class[]{clsFindClass4}, new InvocationHandler() { // from class: hun.mesh.carwithenhance.hook.SettingsHook.4.1
                                        @Override // java.lang.reflect.InvocationHandler
                                        public Object invoke(Object obj, Method method, Object[] objArr) {
                                            if (!"onPreferenceChange".equals(method.getName())) {
                                                return null;
                                            }
                                            boolean zBooleanValue = ((Boolean) objArr[1]).booleanValue();
                                            sharedPreferences.edit().putBoolean(hookItem.key, zBooleanValue).apply();
                                            if (hookItem.key.equals(HookConfigs.DISCONNECT_KILL_MUSIC.key)) {
                                                SettingsHook.setDelayPrefVisible(zBooleanValue);
                                            }
                                            try {
                                                Settings.Global.putInt(activity.getContentResolver(), hookItem.key, zBooleanValue ? 1 : 0);
                                                XLog.i(">> [手机端UI] 同步写入 Settings.Global: " + hookItem.key + " = " + zBooleanValue);
                                            } catch (Throwable th3) {
                                                XLog.e(">> [手机端UI] 写入 Settings.Global 失败: " + hookItem.key, th3);
                                            }
                                            XLog.i(">> [手机端UI] 开关已更新: " + hookItem.key + " = " + zBooleanValue);
                                            return Boolean.TRUE;
                                        }
                                    })});
                                    XposedHelpers.callMethod(objNewInstance, "addPreference", new Object[]{objNewInstance2});
                                }
                                try {
                                    final Object objNewInstance3 = XposedHelpers.newInstance(XposedHelpers.findClass("androidx.preference.EditTextPreference", classLoader), new Object[]{activity});
                                    SettingsHook.sDelayPref = objNewInstance3;
                                    XposedHelpers.callMethod(objNewInstance3, "setKey", new Object[]{"hook_disconnect_kill_delay_ui"});
                                    XposedHelpers.callMethod(objNewInstance3, "setTitle", new Object[]{"断开后延迟关闭秒数"});
                                    int i = HookConfigs.clampDelaySeconds(sharedPreferences.getInt(HookConfigs.DISCONNECT_KILL_DELAY_KEY, HookConfigs.DEFAULT_DISCONNECT_KILL_DELAY_SEC));
                                    XposedHelpers.callMethod(objNewInstance3, "setText", new Object[]{String.valueOf(i)});
                                    XposedHelpers.callMethod(objNewInstance3, "setSummary", new Object[]{"当前 " + i + " 秒（0=立即，最大 " + HookConfigs.MAX_DISCONNECT_KILL_DELAY_SEC + "）"});
                                    XposedHelpers.callMethod(objNewInstance3, "setVisible", new Object[]{Boolean.valueOf(sharedPreferences.getBoolean(HookConfigs.DISCONNECT_KILL_MUSIC.key, HookConfigs.DISCONNECT_KILL_MUSIC.defaultValue))});
                                    XposedHelpers.callMethod(objNewInstance3, "setOnPreferenceChangeListener", new Object[]{Proxy.newProxyInstance(classLoader, new Class[]{clsFindClass4}, new InvocationHandler() { // from class: hun.mesh.carwithenhance.hook.SettingsHook.4.2
                                        @Override // java.lang.reflect.InvocationHandler
                                        public Object invoke(Object obj, Method method, Object[] objArr) {
                                            if (!"onPreferenceChange".equals(method.getName())) {
                                                return null;
                                            }
                                            String strReplaceAll = ((String) objArr[1]).replaceAll("\\D+", "");
                                            if (strReplaceAll.isEmpty()) {
                                                strReplaceAll = "0";
                                            }
                                            int i2 = parseDelaySeconds(strReplaceAll);
                                            sharedPreferences.edit().putInt(HookConfigs.DISCONNECT_KILL_DELAY_KEY, i2).apply();
                                            XposedHelpers.callMethod(objNewInstance3, "setSummary", new Object[]{"当前 " + i2 + " 秒（0=立即，最大 " + HookConfigs.MAX_DISCONNECT_KILL_DELAY_SEC + "）"});
                                            XposedHelpers.callMethod(objNewInstance3, "setText", new Object[]{String.valueOf(i2)});
                                            return Boolean.TRUE;
                                        }
                                    })});
                                    XposedHelpers.callMethod(objNewInstance, "addPreference", new Object[]{objNewInstance3});
                                } catch (Throwable th3) {
                                    XLog.e(">> [手机端UI] 注入延迟关闭配置失败", th3);
                                }
                                XLog.i(">> [手机端UI] 成功全动态注入[CarWith Enhance]优化设置UI");
                            }
                        }});
                        XposedHelpers.findAndHookMethod(clsFindClass2, "onResume", new Object[]{new XC_MethodHook() { // from class: hun.mesh.carwithenhance.hook.SettingsHook.5
                            public void afterHookedMethod(XC_MethodHook.MethodHookParam methodHookParam) {
                                Activity activity = (Activity) XposedHelpers.callMethod(methodHookParam.thisObject, "getActivity", new Object[0]);
                                if (activity == null) {
                                    return;
                                }
                                SharedPreferences sharedPreferences = activity.getSharedPreferences(HookConfigs.PREFS_FILE, 0);
                                Object objCallMethod = XposedHelpers.callMethod(methodHookParam.thisObject, "getPreferenceScreen", new Object[0]);
                                if (objCallMethod == null) {
                                    return;
                                }
                                for (HookItem hookItem : HookConfigs.getAllItems()) {
                                    String str2 = hookItem.key;
                                    SettingsHook.refreshSwitchPref(objCallMethod, str2, sharedPreferences.getBoolean(str2, hookItem.defaultValue));
                                }
                                XLog.i(">> [手机端UI] onResume 动态刷新所有开关状态完成");
                            }
                        }});
                    }
                    return;
                } catch (Throwable th3) {
                    XLog.e("❌ Hook 4 (手机端UI界面动态注入与同步) 失败: ", th3);
                    return;
                }
            }
            XLog.e("SettingsHook 动态目标未找到，跳过 Hook");
        } catch (Throwable th4) {
            XLog.e("❌ Hook 3-2 (车机端UI状态同步) 失败: ", th4);
        }
    }
}
