package hun.mesh.carwithenhance.dexkit;

import android.content.Context;
import android.content.SharedPreferences;
import de.robv.android.xposed.callbacks.XC_LoadPackage;
import defpackage.a;
import defpackage.a0;
import defpackage.b0;
import defpackage.d;
import defpackage.e0;
import defpackage.f1;
import defpackage.h;
import defpackage.i0;
import defpackage.k0;
import defpackage.m1;
import defpackage.n;
import defpackage.o;
import defpackage.o1;
import defpackage.p;
import defpackage.q;
import defpackage.r;
import defpackage.t0;
import defpackage.u0;
import defpackage.v0;
import defpackage.w;
import defpackage.x0;
import defpackage.y0;
import hun.mesh.carwithenhance.BuildConfig;
import hun.mesh.carwithenhance.utils.XLog;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import org.luckypray.dexkit.DexKitBridge;

/* JADX INFO: compiled from: r8-map-id-2844820780df513d20833d92dac6e5f4b7fbbd08283aea6743910c9549ac13d9 */
/* JADX INFO: loaded from: classes.dex */
public final class CarWithDexKitManager {
    private static final String PREFS_DEXKIT = "carwithenhance_dexkit_cache";
    public static final CarWithDexKitManager INSTANCE = new CarWithDexKitManager();
    private static String autoPlayClass = "";
    private static String autoPlayMethod = "";
    private static String qpClass = "";
    private static String qpMethod = "";
    private static String blurClass = "";
    private static String blurMethod = "";
    private static String settingsClass = "";
    private static String settingsMethod = "";
    private static String settingsAdapterClass = "";
    private static String settingsAdapterMethods = "";
    private static String ucarSettingsFragmentClass = "";
    private static String carlinkStateMachineClass = "";
    private static String screenOnMethod = "";
    private static String bluetoothClass = "";
    private static String bluetoothMethod = "";
    private static String castControllerClass = "";
    private static String castControllerConnectMethod = "";
    private static String castControllerDisconnectMethod = "";
    private static String mapConfigClass = "";
    private static String cruiseMethod = "";
    private static String onShowNavInfoClass = "";
    private static String onShowNavInfoMethod = "";

    private CarWithDexKitManager() {
    }

    private final void loadFromCache(SharedPreferences sharedPreferences) {
        String string = sharedPreferences.getString("autoPlayClass", "");
        if (string == null) {
            string = "";
        }
        autoPlayClass = string;
        String string2 = sharedPreferences.getString("autoPlayMethod", "");
        if (string2 == null) {
            string2 = "";
        }
        autoPlayMethod = string2;
        String string3 = sharedPreferences.getString("qpClass", "");
        if (string3 == null) {
            string3 = "";
        }
        qpClass = string3;
        String string4 = sharedPreferences.getString("qpMethod", "");
        if (string4 == null) {
            string4 = "";
        }
        qpMethod = string4;
        String string5 = sharedPreferences.getString("blurClass", "");
        if (string5 == null) {
            string5 = "";
        }
        blurClass = string5;
        String string6 = sharedPreferences.getString("blurMethod", "");
        if (string6 == null) {
            string6 = "";
        }
        blurMethod = string6;
        String string7 = sharedPreferences.getString("settingsClass", "");
        if (string7 == null) {
            string7 = "";
        }
        settingsClass = string7;
        String string8 = sharedPreferences.getString("settingsMethod", "");
        if (string8 == null) {
            string8 = "";
        }
        settingsMethod = string8;
        String string9 = sharedPreferences.getString("settingsAdapterClass", "");
        if (string9 == null) {
            string9 = "";
        }
        settingsAdapterClass = string9;
        String string10 = sharedPreferences.getString("settingsAdapterMethods", "");
        if (string10 == null) {
            string10 = "";
        }
        settingsAdapterMethods = string10;
        String string11 = sharedPreferences.getString("ucarSettingsFragmentClass", "");
        if (string11 == null) {
            string11 = "";
        }
        ucarSettingsFragmentClass = string11;
        String string12 = sharedPreferences.getString("carlinkStateMachineClass", "");
        if (string12 == null) {
            string12 = "";
        }
        carlinkStateMachineClass = string12;
        String string13 = sharedPreferences.getString("screenOnMethod", "");
        if (string13 == null) {
            string13 = "";
        }
        screenOnMethod = string13;
        String string14 = sharedPreferences.getString("bluetoothClass", "");
        if (string14 == null) {
            string14 = "";
        }
        bluetoothClass = string14;
        String string15 = sharedPreferences.getString("bluetoothMethod", "");
        if (string15 == null) {
            string15 = "";
        }
        bluetoothMethod = string15;
        String string16 = sharedPreferences.getString("castControllerClass", "");
        if (string16 == null) {
            string16 = "";
        }
        castControllerClass = string16;
        String string17 = sharedPreferences.getString("castControllerConnectMethod", "");
        if (string17 == null) {
            string17 = "";
        }
        castControllerConnectMethod = string17;
        String string18 = sharedPreferences.getString("castControllerDisconnectMethod", "");
        if (string18 == null) {
            string18 = "";
        }
        castControllerDisconnectMethod = string18;
        String string19 = sharedPreferences.getString("mapConfigClass", "");
        if (string19 == null) {
            string19 = "";
        }
        mapConfigClass = string19;
        String string20 = sharedPreferences.getString("cruiseMethod", "");
        if (string20 == null) {
            string20 = "";
        }
        cruiseMethod = string20;
        String string21 = sharedPreferences.getString("onShowNavInfoClass", "");
        if (string21 == null) {
            string21 = "";
        }
        onShowNavInfoClass = string21;
        String string22 = sharedPreferences.getString("onShowNavInfoMethod", "");
        onShowNavInfoMethod = string22 != null ? string22 : "";
    }

    private final void resolveTargets(DexKitBridge dexKitBridge) {
        String str;
        int i = 0;
        t0 t0Var = (t0) dexKitBridge.c(new h(i)).a();
        if (t0Var != null) {
            autoPlayClass = t0Var.b().a;
            String str2 = t0Var.b().b;
            autoPlayMethod = str2;
            XLog.i("[CarWithDexKit]: 找到 AutoPlayHook 目标 -> " + autoPlayClass + "." + str2);
        } else {
            XLog.e("[CarWithDexKit]: 未找到或匹配到多个 AutoPlayHook 目标，请检查匹配条件");
        }
        t0 t0Var2 = (t0) dexKitBridge.c(new h(10)).a();
        if (t0Var2 != null) {
            qpClass = t0Var2.b().a;
            String str3 = t0Var2.b().b;
            qpMethod = str3;
            XLog.i("[CarWithDexKit]: 找到 QPHook 目标 -> " + qpClass + "." + str3);
        } else {
            XLog.e("[CarWithDexKit]: 未找到或匹配到多个 QPHook 目标，请检查匹配条件");
        }
        t0 t0Var3 = (t0) dexKitBridge.c(new h(11)).a();
        if (t0Var3 != null) {
            blurClass = t0Var3.b().a;
            String str4 = t0Var3.b().b;
            blurMethod = str4;
            XLog.i("[CarWithDexKit]: 找到 BlurCapabilityHook 目标 -> " + blurClass + "." + str4);
        } else {
            XLog.e("[CarWithDexKit]: 未找到或匹配到多个 BlurCapabilityHook 目标，请检查匹配条件");
        }
        t0 t0Var4 = (t0) dexKitBridge.c(new h(12)).a();
        if (t0Var4 != null) {
            settingsClass = t0Var4.b().a;
            String str5 = t0Var4.b().b;
            settingsMethod = str5;
            XLog.i("[CarWithDexKit]: 找到 SettingsHook 目标 -> " + settingsClass + "." + str5);
        } else {
            XLog.e("[CarWithDexKit]: 未找到或匹配到多个 SettingsHook 目标，请检查匹配条件");
        }
        i0 i0Var = new i0();
        resolveTargets$lambda$18(i0Var);
        byte[] bArrK = i0Var.k();
        ReentrantReadWriteLock.ReadLock lock = dexKitBridge.b.readLock();
        lock.lock();
        try {
            long j = dexKitBridge.a;
            if (j == 0) {
                throw new IllegalStateException("DexKitBridge is not valid");
            }
            byte[] bArrNativeFindClass = DexKitBridge.nativeFindClass(j, bArrK);
            lock.unlock();
            ByteBuffer byteBufferWrap = ByteBuffer.wrap(bArrNativeFindClass);
            byteBufferWrap.getClass();
            a aVar = new a();
            byteBufferWrap.order(ByteOrder.LITTLE_ENDIAN);
            aVar.c(byteBufferWrap.position() + byteBufferWrap.getInt(byteBufferWrap.position()), byteBufferWrap);
            o oVar = new o();
            int i2 = 4;
            int iB = aVar.b(4);
            int iF = iB != 0 ? aVar.f(iB) : 0;
            for (int i3 = 0; i3 < iF; i3++) {
                a aVarG = aVar.g(i3);
                aVarG.getClass();
                oVar.add(q.d(dexKitBridge, aVarG));
            }
            int i4 = 1;
            if (oVar.size() > 1) {
                w.l(oVar, new a0(i));
            }
            n nVar = (n) oVar.b(new h(13));
            if (nVar != null) {
                String strB = nVar.b();
                settingsAdapterClass = strB;
                XLog.i("[CarWithDexKit]: 找到 SettingsAdapter 类 -> " + strB);
                t0 t0Var5 = (t0) dexKitBridge.c(new h(i4)).a();
                String str6 = t0Var5 != null ? t0Var5.b().b : "";
                t0 t0Var6 = (t0) dexKitBridge.c(new h(2)).a();
                List listAsList = Arrays.asList(str6, t0Var6 != null ? t0Var6.b().b : "");
                listAsList.getClass();
                ArrayList arrayList = new ArrayList();
                for (Object obj : listAsList) {
                    if (((String) obj).length() > 0) {
                        arrayList.add(obj);
                    }
                }
                String strM = r.m(arrayList, ",", null, 62);
                settingsAdapterMethods = strM;
                XLog.i("[CarWithDexKit]: 找到 SettingsAdapter 目标方法 -> ".concat(strM));
            } else {
                XLog.e("[CarWithDexKit]: 未找到或匹配到多个 SettingsAdapter 类目标，请检查匹配条件");
            }
            t0 t0Var7 = (t0) dexKitBridge.c(new h(3)).a();
            if (t0Var7 != null) {
                String str7 = t0Var7.b().a;
                ucarSettingsFragmentClass = str7;
                XLog.i("[CarWithDexKit]: 找到 UCarScreenSettingsFragment 目标 -> " + str7);
            } else {
                XLog.e("[CarWithDexKit]: 未找到或匹配到多个 UCarScreenSettingsFragment 目标，请检查匹配条件");
            }
            t0 t0Var8 = (t0) dexKitBridge.c(new h(i2)).a();
            if (t0Var8 != null) {
                carlinkStateMachineClass = t0Var8.b().a;
                String str8 = t0Var8.b().b;
                screenOnMethod = str8;
                XLog.i("[CarWithDexKit]: 找到 ScreenOnHook 目标 -> " + carlinkStateMachineClass + "." + str8);
            } else {
                XLog.e("[CarWithDexKit]: 未找到或匹配到多个 ScreenOnHook 目标，请检查匹配条件");
            }
            t0 t0Var9 = (t0) dexKitBridge.c(new h(5)).a();
            if (t0Var9 != null) {
                bluetoothClass = t0Var9.b().a;
                String str9 = t0Var9.b().b;
                bluetoothMethod = str9;
                XLog.i("[CarWithDexKit]: 找到 BluetoothHook 目标 -> " + bluetoothClass + "." + str9);
            } else {
                XLog.e("[CarWithDexKit]: 未找到或匹配到多个 BluetoothHook 目标，请检查匹配条件");
            }
            t0 t0Var10 = (t0) dexKitBridge.c(new h(6)).a();
            if (t0Var10 != null) {
                castControllerClass = t0Var10.b().a;
                String str10 = t0Var10.b().b;
                castControllerConnectMethod = str10;
                XLog.i("[CarWithDexKit]: 找到 CastController 连接目标 -> " + castControllerClass + "." + str10);
                t0 t0Var11 = (t0) dexKitBridge.c(new h(7)).a();
                if (t0Var11 == null || (str = t0Var11.b().b) == null) {
                    str = "displayDisconnected";
                }
                castControllerDisconnectMethod = str;
            } else {
                XLog.e("[CarWithDexKit]: 未找到 CastController 目标");
            }
            t0 t0Var12 = (t0) dexKitBridge.c(new h(8)).a();
            if (t0Var12 != null) {
                mapConfigClass = t0Var12.b().a;
                String str11 = t0Var12.b().b;
                cruiseMethod = str11;
                XLog.i("[CarWithDexKit]: 找到 isCruise 目标 -> " + mapConfigClass + "." + str11);
            } else {
                XLog.e("[CarWithDexKit]: 未找到 isCruise 目标方法");
            }
            t0 t0Var13 = (t0) dexKitBridge.c(new h(9)).a();
            if (t0Var13 == null) {
                XLog.e("[CarWithDexKit]: 未找到 onShowNavInfo 目标方法");
                return;
            }
            onShowNavInfoClass = t0Var13.b().a;
            String str12 = t0Var13.b().b;
            onShowNavInfoMethod = str12;
            XLog.i("[CarWithDexKit]: 找到 onShowNavInfo 目标 -> " + onShowNavInfoClass + "." + str12);
        } catch (Throwable th) {
            lock.unlock();
            throw th;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final o1 resolveTargets$lambda$10(k0 k0Var) {
        k0Var.getClass();
        u0 u0Var = new u0();
        resolveTargets$lambda$10$lambda$9(u0Var);
        k0Var.c = u0Var;
        return o1.a;
    }

    private static final o1 resolveTargets$lambda$10$lambda$9(u0 u0Var) {
        u0Var.getClass();
        u0.n("boolean", u0Var);
        u0Var.m("android.content.Context");
        u0Var.q("background_blur_enable");
        return o1.a;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final o1 resolveTargets$lambda$14(k0 k0Var) {
        k0Var.getClass();
        u0 u0Var = new u0();
        resolveTargets$lambda$14$lambda$13(u0Var);
        k0Var.c = u0Var;
        return o1.a;
    }

    private static final o1 resolveTargets$lambda$14$lambda$13(u0 u0Var) {
        u0Var.getClass();
        u0.n("boolean", u0Var);
        u0Var.m("android.content.Context", "java.lang.String", "android.content.SharedPreferences");
        return o1.a;
    }

    private static final o1 resolveTargets$lambda$18(i0 i0Var) {
        i0Var.getClass();
        i0Var.b = d.k(new String[]{"com.carwith.launcher.settings"});
        p pVar = new p();
        resolveTargets$lambda$18$lambda$17(pVar);
        i0Var.c = pVar;
        return o1.a;
    }

    private static final o1 resolveTargets$lambda$18$lambda$17(p pVar) {
        pVar.getClass();
        ArrayList arrayList = new ArrayList(1);
        arrayList.add(new f1(new String[]{"prefer_dock_style"}[0], 1));
        pVar.c = new ArrayList(arrayList);
        return o1.a;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final boolean resolveTargets$lambda$19(n nVar) {
        nVar.getClass();
        String strB = nVar.b();
        strB.getClass();
        int iLastIndexOf = strB.lastIndexOf(".", strB.length() - 1);
        if (iLastIndexOf != -1) {
            strB = strB.substring(iLastIndexOf + 1, strB.length());
        }
        return !(m1.k(strB, "$", 0, 2) >= 0);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final o1 resolveTargets$lambda$2(k0 k0Var) {
        k0Var.getClass();
        u0 u0Var = new u0();
        resolveTargets$lambda$2$lambda$1(u0Var);
        k0Var.c = u0Var;
        return o1.a;
    }

    private static final o1 resolveTargets$lambda$2$lambda$1(u0 u0Var) {
        u0Var.getClass();
        u0.n("void", u0Var);
        u0Var.m("boolean", "int", "java.util.List", "int", "java.lang.String", "android.os.Bundle");
        u0Var.q("MediaAutoPlayHelper");
        return o1.a;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final o1 resolveTargets$lambda$27$lambda$21(k0 k0Var) {
        k0Var.getClass();
        u0 u0Var = new u0();
        resolveTargets$lambda$27$lambda$21$lambda$20(u0Var);
        k0Var.c = u0Var;
        return o1.a;
    }

    private static final o1 resolveTargets$lambda$27$lambda$21$lambda$20(u0 u0Var) {
        u0Var.getClass();
        u0Var.o(settingsAdapterClass);
        u0Var.q("prefer_dock_style");
        return o1.a;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final o1 resolveTargets$lambda$27$lambda$24(k0 k0Var) {
        k0Var.getClass();
        u0 u0Var = new u0();
        resolveTargets$lambda$27$lambda$24$lambda$23(u0Var);
        k0Var.c = u0Var;
        return o1.a;
    }

    private static final o1 resolveTargets$lambda$27$lambda$24$lambda$23(u0 u0Var) {
        x0 x0Var;
        u0Var.getClass();
        u0Var.o(settingsAdapterClass);
        v0 v0Var = u0Var.g;
        if (v0Var == null) {
            v0Var = new v0();
        }
        u0Var.g = v0Var;
        u0 u0Var2 = new u0();
        b0 b0Var = new b0("Landroid/widget/CompoundButton;->setOnCheckedChangeListener(Landroid/widget/CompoundButton$OnCheckedChangeListener;)V");
        u0.l(b0Var.b, u0Var2);
        u0.k(b0Var.a, u0Var2);
        u0.n(b0Var.d, u0Var2);
        ArrayList<String> arrayList = b0Var.c;
        arrayList.getClass();
        y0 y0Var = new y0();
        y0Var.b = new ArrayList(e0.a);
        for (String str : arrayList) {
            if (str != null) {
                x0Var = new x0();
                x0.k(x0Var, str);
            } else {
                x0Var = null;
            }
            List arrayList2 = y0Var.b;
            if (arrayList2 == null) {
                arrayList2 = new ArrayList();
            }
            y0Var.b = arrayList2;
            arrayList2.add(x0Var);
        }
        u0Var2.e = y0Var;
        List arrayList3 = v0Var.b;
        if (arrayList3 == null) {
            arrayList3 = new ArrayList();
        }
        v0Var.b = arrayList3;
        arrayList3.add(u0Var2);
        return o1.a;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final o1 resolveTargets$lambda$30(k0 k0Var) {
        k0Var.getClass();
        u0 u0Var = new u0();
        resolveTargets$lambda$30$lambda$29(u0Var);
        k0Var.c = u0Var;
        return o1.a;
    }

    private static final o1 resolveTargets$lambda$30$lambda$29(u0 u0Var) {
        u0Var.getClass();
        u0.l("onCreatePreferences", u0Var);
        u0Var.m("android.os.Bundle", "java.lang.String");
        u0Var.q("UCarScreenSettingsFragment");
        return o1.a;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final o1 resolveTargets$lambda$34(k0 k0Var) {
        k0Var.getClass();
        u0 u0Var = new u0();
        resolveTargets$lambda$34$lambda$33(u0Var);
        k0Var.c = u0Var;
        return o1.a;
    }

    private static final o1 resolveTargets$lambda$34$lambda$33(u0 u0Var) {
        u0Var.getClass();
        u0Var.q("start ScreenOnTask");
        u0.n("void", u0Var);
        return o1.a;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final o1 resolveTargets$lambda$38(k0 k0Var) {
        k0Var.getClass();
        u0 u0Var = new u0();
        resolveTargets$lambda$38$lambda$37(u0Var);
        k0Var.c = u0Var;
        return o1.a;
    }

    private static final o1 resolveTargets$lambda$38$lambda$37(u0 u0Var) {
        u0Var.getClass();
        u0.n("void", u0Var);
        u0Var.p(3);
        u0Var.q("target carSsid: ");
        return o1.a;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final o1 resolveTargets$lambda$42(k0 k0Var) {
        k0Var.getClass();
        k0Var.b = d.k(new String[]{"com.miui.carlink"});
        u0 u0Var = new u0();
        resolveTargets$lambda$42$lambda$41(u0Var);
        k0Var.c = u0Var;
        return o1.a;
    }

    private static final o1 resolveTargets$lambda$42$lambda$41(u0 u0Var) {
        u0Var.getClass();
        u0Var.m("android.view.Surface", "int", "int", "int", "int", "int", "int");
        u0Var.q("displayConnected:");
        return o1.a;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final o1 resolveTargets$lambda$45$lambda$44(k0 k0Var) {
        k0Var.getClass();
        u0 u0Var = new u0();
        resolveTargets$lambda$45$lambda$44$lambda$43(u0Var);
        k0Var.c = u0Var;
        return o1.a;
    }

    private static final o1 resolveTargets$lambda$45$lambda$44$lambda$43(u0 u0Var) {
        u0Var.getClass();
        u0Var.o(castControllerClass);
        u0.n("void", u0Var);
        u0Var.p(0);
        u0Var.q("displayDisconnected start: ");
        return o1.a;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final o1 resolveTargets$lambda$49(k0 k0Var) {
        k0Var.getClass();
        k0Var.b = d.k(new String[]{"com.carwith.launcher.map"});
        u0 u0Var = new u0();
        resolveTargets$lambda$49$lambda$48(u0Var);
        k0Var.c = u0Var;
        return o1.a;
    }

    private static final o1 resolveTargets$lambda$49$lambda$48(u0 u0Var) {
        u0Var.getClass();
        u0.n("boolean", u0Var);
        u0Var.p(0);
        u0 u0Var2 = new u0();
        resolveTargets$lambda$49$lambda$48$lambda$47(u0Var2);
        v0 v0Var = u0Var.h;
        if (v0Var == null) {
            v0Var = new v0();
        }
        u0Var.h = v0Var;
        List arrayList = v0Var.b;
        if (arrayList == null) {
            arrayList = new ArrayList();
        }
        v0Var.b = arrayList;
        arrayList.add(u0Var2);
        return o1.a;
    }

    private static final o1 resolveTargets$lambda$49$lambda$48$lambda$47(u0 u0Var) {
        u0Var.getClass();
        u0Var.q("notifyStatusChanged pkg=");
        return o1.a;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final o1 resolveTargets$lambda$53(k0 k0Var) {
        k0Var.getClass();
        k0Var.b = d.k(new String[]{"com.carwith.launcher.map"});
        u0 u0Var = new u0();
        resolveTargets$lambda$53$lambda$52(u0Var);
        k0Var.c = u0Var;
        return o1.a;
    }

    private static final o1 resolveTargets$lambda$53$lambda$52(u0 u0Var) {
        u0Var.getClass();
        u0.n("void", u0Var);
        u0Var.p(2);
        u0Var.q("onShowNavInfo: pkg=");
        return o1.a;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final o1 resolveTargets$lambda$6(k0 k0Var) {
        k0Var.getClass();
        k0Var.b = d.k(new String[]{"com.xiaomi.ucar.carlife"});
        u0 u0Var = new u0();
        resolveTargets$lambda$6$lambda$5(u0Var);
        k0Var.c = u0Var;
        return o1.a;
    }

    private static final o1 resolveTargets$lambda$6$lambda$5(u0 u0Var) {
        u0Var.getClass();
        u0.n("void", u0Var);
        u0Var.m("boolean", "android.content.Intent", "android.os.Bundle");
        u0Var.q("params", "default_width");
        return o1.a;
    }

    private final void saveToCache(SharedPreferences sharedPreferences, String str) {
        SharedPreferences.Editor editorEdit = sharedPreferences.edit();
        if (str == null) {
            str = "";
        }
        editorEdit.putString("KEY_LAST_APP_VERSION_NAME", str);
        editorEdit.putLong("KEY_LAST_MODULE_BUILD_TIME", BuildConfig.BUILD_TIME);
        editorEdit.putString("autoPlayClass", autoPlayClass);
        editorEdit.putString("autoPlayMethod", autoPlayMethod);
        editorEdit.putString("qpClass", qpClass);
        editorEdit.putString("qpMethod", qpMethod);
        editorEdit.putString("blurClass", blurClass);
        editorEdit.putString("blurMethod", blurMethod);
        editorEdit.putString("settingsClass", settingsClass);
        editorEdit.putString("settingsMethod", settingsMethod);
        editorEdit.putString("settingsAdapterClass", settingsAdapterClass);
        editorEdit.putString("settingsAdapterMethods", settingsAdapterMethods);
        editorEdit.putString("ucarSettingsFragmentClass", ucarSettingsFragmentClass);
        editorEdit.putString("carlinkStateMachineClass", carlinkStateMachineClass);
        editorEdit.putString("screenOnMethod", screenOnMethod);
        editorEdit.putString("bluetoothClass", bluetoothClass);
        editorEdit.putString("bluetoothMethod", bluetoothMethod);
        editorEdit.putString("castControllerClass", castControllerClass);
        editorEdit.putString("castControllerConnectMethod", castControllerConnectMethod);
        editorEdit.putString("castControllerDisconnectMethod", castControllerDisconnectMethod);
        editorEdit.putString("mapConfigClass", mapConfigClass);
        editorEdit.putString("cruiseMethod", cruiseMethod);
        editorEdit.putString("onShowNavInfoClass", onShowNavInfoClass);
        editorEdit.putString("onShowNavInfoMethod", onShowNavInfoMethod);
        editorEdit.apply();
    }

    public final String getAutoPlayClass() {
        return autoPlayClass;
    }

    public final String getAutoPlayMethod() {
        return autoPlayMethod;
    }

    public final String getBluetoothClass() {
        return bluetoothClass;
    }

    public final String getBluetoothMethod() {
        return bluetoothMethod;
    }

    public final String getBlurClass() {
        return blurClass;
    }

    public final String getBlurMethod() {
        return blurMethod;
    }

    public final String getCarlinkStateMachineClass() {
        return carlinkStateMachineClass;
    }

    public final String getCastControllerClass() {
        return castControllerClass;
    }

    public final String getCastControllerConnectMethod() {
        return castControllerConnectMethod;
    }

    public final String getCastControllerDisconnectMethod() {
        return castControllerDisconnectMethod;
    }

    public final String getCruiseMethod() {
        return cruiseMethod;
    }

    public final String getMapConfigClass() {
        return mapConfigClass;
    }

    public final String getOnShowNavInfoClass() {
        return onShowNavInfoClass;
    }

    public final String getOnShowNavInfoMethod() {
        return onShowNavInfoMethod;
    }

    public final String getQpClass() {
        return qpClass;
    }

    public final String getQpMethod() {
        return qpMethod;
    }

    public final String getScreenOnMethod() {
        return screenOnMethod;
    }

    public final String getSettingsAdapterClass() {
        return settingsAdapterClass;
    }

    public final String getSettingsAdapterMethods() {
        return settingsAdapterMethods;
    }

    public final String getSettingsClass() {
        return settingsClass;
    }

    public final String getSettingsMethod() {
        return settingsMethod;
    }

    public final String getUcarSettingsFragmentClass() {
        return ucarSettingsFragmentClass;
    }

    public final void initAndResolve(XC_LoadPackage.LoadPackageParam loadPackageParam, Context context) {
        loadPackageParam.getClass();
        context.getClass();
        try {
            String str = context.getPackageManager().getPackageInfo(loadPackageParam.packageName, 0).versionName;
            SharedPreferences sharedPreferences = context.getSharedPreferences(PREFS_DEXKIT, 0);
            String string = sharedPreferences.getString("KEY_LAST_APP_VERSION_NAME", "");
            long j = sharedPreferences.getLong("KEY_LAST_MODULE_BUILD_TIME", -1L);
            if (q.a(str, string) && BuildConfig.BUILD_TIME == j) {
                XLog.i("[CarWithDexKit]: 宿主版本未变，且模块未重新编译，直接使用缓存。");
                loadFromCache(sharedPreferences);
                return;
            }
            XLog.i("[CarWithDexKit]: 宿主升级或模块重新编译，开始全量搜索。");
            System.loadLibrary("dexkit");
            String str2 = loadPackageParam.appInfo.sourceDir;
            str2.getClass();
            DexKitBridge dexKitBridge = new DexKitBridge(str2);
            try {
                CarWithDexKitManager carWithDexKitManager = INSTANCE;
                carWithDexKitManager.resolveTargets(dexKitBridge);
                carWithDexKitManager.saveToCache(sharedPreferences, str);
                XLog.i("[CarWithDexKit]: 全量搜索完成并已更新缓存。");
                dexKitBridge.close();
            } catch (Throwable th) {
                try {
                    throw th;
                } catch (Throwable th2) {
                    q.b(dexKitBridge, th);
                    throw th2;
                }
            }
        } catch (Throwable th3) {
            XLog.e("[CarWithDexKit]: 初始化异常: ", th3);
        }
    }

    public final void setAutoPlayClass(String str) {
        str.getClass();
        autoPlayClass = str;
    }

    public final void setAutoPlayMethod(String str) {
        str.getClass();
        autoPlayMethod = str;
    }

    public final void setBluetoothClass(String str) {
        str.getClass();
        bluetoothClass = str;
    }

    public final void setBluetoothMethod(String str) {
        str.getClass();
        bluetoothMethod = str;
    }

    public final void setBlurClass(String str) {
        str.getClass();
        blurClass = str;
    }

    public final void setBlurMethod(String str) {
        str.getClass();
        blurMethod = str;
    }

    public final void setCarlinkStateMachineClass(String str) {
        str.getClass();
        carlinkStateMachineClass = str;
    }

    public final void setCastControllerClass(String str) {
        str.getClass();
        castControllerClass = str;
    }

    public final void setCastControllerConnectMethod(String str) {
        str.getClass();
        castControllerConnectMethod = str;
    }

    public final void setCastControllerDisconnectMethod(String str) {
        str.getClass();
        castControllerDisconnectMethod = str;
    }

    public final void setCruiseMethod(String str) {
        str.getClass();
        cruiseMethod = str;
    }

    public final void setMapConfigClass(String str) {
        str.getClass();
        mapConfigClass = str;
    }

    public final void setOnShowNavInfoClass(String str) {
        str.getClass();
        onShowNavInfoClass = str;
    }

    public final void setOnShowNavInfoMethod(String str) {
        str.getClass();
        onShowNavInfoMethod = str;
    }

    public final void setQpClass(String str) {
        str.getClass();
        qpClass = str;
    }

    public final void setQpMethod(String str) {
        str.getClass();
        qpMethod = str;
    }

    public final void setScreenOnMethod(String str) {
        str.getClass();
        screenOnMethod = str;
    }

    public final void setSettingsAdapterClass(String str) {
        str.getClass();
        settingsAdapterClass = str;
    }

    public final void setSettingsAdapterMethods(String str) {
        str.getClass();
        settingsAdapterMethods = str;
    }

    public final void setSettingsClass(String str) {
        str.getClass();
        settingsClass = str;
    }

    public final void setSettingsMethod(String str) {
        str.getClass();
        settingsMethod = str;
    }

    public final void setUcarSettingsFragmentClass(String str) {
        str.getClass();
        ucarSettingsFragmentClass = str;
    }
}
