package hun.mesh.carwithenhance.dexkit;

import android.content.Context;
import android.content.SharedPreferences;
import defpackage.a1;
import defpackage.f0;
import defpackage.g0;
import defpackage.h0;
import defpackage.j0;
import defpackage.k0;
import defpackage.n;
import defpackage.o0;
import defpackage.o1;
import defpackage.p;
import defpackage.q;
import defpackage.r;
import defpackage.t0;
import defpackage.u0;
import hun.mesh.carwithenhance.BuildConfig;
import hun.mesh.carwithenhance.dexkit.QQMusicDexKitManager;
import hun.mesh.carwithenhance.utils.XLog;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import org.luckypray.dexkit.DexKitBridge;

/* JADX INFO: compiled from: r8-map-id-2844820780df513d20833d92dac6e5f4b7fbbd08283aea6743910c9549ac13d9 */
/* JADX INFO: loaded from: classes.dex */
public final class QQMusicDexKitManager {
    private static final String PREFS_DEXKIT = "carwithenhance_qqmusic_dexkit_cache";
    public static final QQMusicDexKitManager INSTANCE = new QQMusicDexKitManager();
    private static String classAudioRouteManager = "com.tencent.qqmusic.business.bluetooth.AudioRouteManager";
    private static String classAudioGearInfo = "com.tencent.qqmusic.business.bluetooth.AudioGearInfo";
    private static String methodGetInstance = "";
    private static String methodUpdateRoute = "";
    private static String methodGetGearType = "";
    private static String methodIsCarAudio = "";
    private static String methodGetGearInfo = "";
    private static String fieldGearType = "";
    private static String fieldBrand = "";
    private static String fieldModel = "";
    private static String fieldMac = "";
    private static String fieldIntG = "";
    private static String fieldIntH = "";

    private QQMusicDexKitManager() {
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final o1 initAndResolve$lambda$26$lambda$1(String str, k0 k0Var) {
        k0Var.getClass();
        u0 u0Var = new u0();
        initAndResolve$lambda$26$lambda$1$lambda$0(str, u0Var);
        k0Var.c = u0Var;
        return o1.a;
    }

    private static final o1 initAndResolve$lambda$26$lambda$1$lambda$0(String str, u0 u0Var) {
        u0Var.getClass();
        u0Var.o(str);
        u0.n(str, u0Var);
        u0Var.p(0);
        return o1.a;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final o1 initAndResolve$lambda$26$lambda$12(String str, k0 k0Var) {
        k0Var.getClass();
        u0 u0Var = new u0();
        initAndResolve$lambda$26$lambda$12$lambda$11(str, u0Var);
        k0Var.c = u0Var;
        return o1.a;
    }

    private static final o1 initAndResolve$lambda$26$lambda$12$lambda$11(String str, u0 u0Var) {
        u0Var.getClass();
        u0Var.o(str);
        u0.n("boolean", u0Var);
        u0Var.p(0);
        return o1.a;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final o1 initAndResolve$lambda$26$lambda$16(String str, String str2, k0 k0Var) {
        k0Var.getClass();
        u0 u0Var = new u0();
        initAndResolve$lambda$26$lambda$16$lambda$15(str, str2, u0Var);
        k0Var.c = u0Var;
        return o1.a;
    }

    private static final o1 initAndResolve$lambda$26$lambda$16$lambda$15(String str, String str2, u0 u0Var) {
        u0Var.getClass();
        u0Var.o(str);
        str2.getClass();
        u0.n(str2, u0Var);
        u0Var.p(0);
        return o1.a;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final o1 initAndResolve$lambda$26$lambda$20(String str, j0 j0Var) {
        j0Var.getClass();
        h0 h0Var = new h0();
        initAndResolve$lambda$26$lambda$20$lambda$19(str, h0Var);
        j0Var.b = h0Var;
        return o1.a;
    }

    private static final o1 initAndResolve$lambda$26$lambda$20$lambda$19(String str, h0 h0Var) {
        h0Var.getClass();
        str.getClass();
        p pVar = new p();
        pVar.k(str, 5);
        h0Var.b = pVar;
        return o1.a;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final o1 initAndResolve$lambda$26$lambda$5(String str, String str2, k0 k0Var) {
        k0Var.getClass();
        u0 u0Var = new u0();
        initAndResolve$lambda$26$lambda$5$lambda$4(str, str2, u0Var);
        k0Var.c = u0Var;
        return o1.a;
    }

    private static final o1 initAndResolve$lambda$26$lambda$5$lambda$4(String str, String str2, u0 u0Var) {
        u0Var.getClass();
        u0Var.o(str);
        u0Var.m("int", str2, "android.os.Bundle", "boolean");
        return o1.a;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final o1 initAndResolve$lambda$26$lambda$8(String str, k0 k0Var) {
        k0Var.getClass();
        u0 u0Var = new u0();
        initAndResolve$lambda$26$lambda$8$lambda$7(str, u0Var);
        k0Var.c = u0Var;
        return o1.a;
    }

    private static final o1 initAndResolve$lambda$26$lambda$8$lambda$7(String str, u0 u0Var) {
        u0Var.getClass();
        u0Var.o(str);
        u0.n("int", u0Var);
        u0Var.p(0);
        return o1.a;
    }

    private final void loadFromCache(SharedPreferences sharedPreferences) {
        String string = sharedPreferences.getString("classAudioRouteManager", classAudioRouteManager);
        if (string == null) {
            string = classAudioRouteManager;
        }
        classAudioRouteManager = string;
        String string2 = sharedPreferences.getString("classAudioGearInfo", classAudioGearInfo);
        if (string2 == null) {
            string2 = classAudioGearInfo;
        }
        classAudioGearInfo = string2;
        String string3 = sharedPreferences.getString("mGetInstance", "");
        if (string3 == null) {
            string3 = "";
        }
        methodGetInstance = string3;
        String string4 = sharedPreferences.getString("mUpdateRoute", "");
        if (string4 == null) {
            string4 = "";
        }
        methodUpdateRoute = string4;
        String string5 = sharedPreferences.getString("mGetGearType", "");
        if (string5 == null) {
            string5 = "";
        }
        methodGetGearType = string5;
        String string6 = sharedPreferences.getString("mIsCarAudio", "");
        if (string6 == null) {
            string6 = "";
        }
        methodIsCarAudio = string6;
        String string7 = sharedPreferences.getString("mGetGearInfo", "");
        if (string7 == null) {
            string7 = "";
        }
        methodGetGearInfo = string7;
        String string8 = sharedPreferences.getString("fieldGearType", "");
        if (string8 == null) {
            string8 = "";
        }
        fieldGearType = string8;
        String string9 = sharedPreferences.getString("fieldBrand", "");
        if (string9 == null) {
            string9 = "";
        }
        fieldBrand = string9;
        String string10 = sharedPreferences.getString("fieldModel", "");
        if (string10 == null) {
            string10 = "";
        }
        fieldModel = string10;
        String string11 = sharedPreferences.getString("fieldMac", "");
        if (string11 == null) {
            string11 = "";
        }
        fieldMac = string11;
        String string12 = sharedPreferences.getString("fieldIntG", "");
        if (string12 == null) {
            string12 = "";
        }
        fieldIntG = string12;
        String string13 = sharedPreferences.getString("fieldIntH", "");
        fieldIntH = string13 != null ? string13 : "";
    }

    private final void saveToCache(SharedPreferences sharedPreferences, long j) {
        SharedPreferences.Editor editorEdit = sharedPreferences.edit();
        editorEdit.putLong("KEY_LAST_VERSION_CODE", j);
        editorEdit.putLong("KEY_LAST_MODULE_BUILD_TIME", BuildConfig.BUILD_TIME);
        editorEdit.putString("classAudioRouteManager", classAudioRouteManager);
        editorEdit.putString("classAudioGearInfo", classAudioGearInfo);
        editorEdit.putString("mGetInstance", methodGetInstance);
        editorEdit.putString("mUpdateRoute", methodUpdateRoute);
        editorEdit.putString("mGetGearType", methodGetGearType);
        editorEdit.putString("mIsCarAudio", methodIsCarAudio);
        editorEdit.putString("mGetGearInfo", methodGetGearInfo);
        editorEdit.putString("fieldGearType", fieldGearType);
        editorEdit.putString("fieldBrand", fieldBrand);
        editorEdit.putString("fieldModel", fieldModel);
        editorEdit.putString("fieldMac", fieldMac);
        editorEdit.putString("fieldIntG", fieldIntG);
        editorEdit.putString("fieldIntH", fieldIntH);
        editorEdit.apply();
    }

    public final String getClassAudioGearInfo() {
        return classAudioGearInfo;
    }

    public final String getClassAudioRouteManager() {
        return classAudioRouteManager;
    }

    public final String getFieldBrand() {
        return fieldBrand;
    }

    public final String getFieldGearType() {
        return fieldGearType;
    }

    public final String getFieldIntG() {
        return fieldIntG;
    }

    public final String getFieldIntH() {
        return fieldIntH;
    }

    public final String getFieldMac() {
        return fieldMac;
    }

    public final String getFieldModel() {
        return fieldModel;
    }

    public final String getMethodGetGearInfo() {
        return methodGetGearInfo;
    }

    public final String getMethodGetGearType() {
        return methodGetGearType;
    }

    public final String getMethodGetInstance() {
        return methodGetInstance;
    }

    public final String getMethodIsCarAudio() {
        return methodIsCarAudio;
    }

    public final String getMethodUpdateRoute() {
        return methodUpdateRoute;
    }

    public final void initAndResolve(Context context) {
        Object obj;
        Object next;
        Object next2;
        Object next3;
        context.getClass();
        try {
            String str = context.getApplicationInfo().sourceDir;
            final int i = 0;
            long longVersionCode = context.getPackageManager().getPackageInfo(context.getPackageName(), 0).getLongVersionCode();
            SharedPreferences sharedPreferences = context.getSharedPreferences(PREFS_DEXKIT, 0);
            long j = sharedPreferences.getLong("KEY_LAST_VERSION_CODE", -1L);
            long j2 = sharedPreferences.getLong("KEY_LAST_MODULE_BUILD_TIME", -1L);
            if (longVersionCode == j && BuildConfig.BUILD_TIME == j2) {
                XLog.i("QQMusicDexKit: QQ音乐版本未变，使用缓存。");
                loadFromCache(sharedPreferences);
                return;
            }
            XLog.i("QQMusicDexKit: QQ音乐版本升级，开始全量搜索核心方法...");
            System.loadLibrary("dexkit");
            str.getClass();
            DexKitBridge dexKitBridge = new DexKitBridge(str);
            try {
                final String str2 = classAudioRouteManager;
                final String str3 = classAudioGearInfo;
                Iterator it = dexKitBridge.c(new a1(str2, i)).iterator();
                do {
                    obj = null;
                    if (!it.hasNext()) {
                        next = null;
                        break;
                    }
                    next = it.next();
                } while (!Modifier.isStatic(((t0) next).b));
                t0 t0Var = (t0) next;
                if (t0Var != null) {
                    methodGetInstance = t0Var.b().b;
                }
                g0 g0VarC = dexKitBridge.c(new o0() { // from class: b1
                    @Override // defpackage.o0
                    public final Object b(Object obj2) {
                        switch (i) {
                            case 0:
                                return QQMusicDexKitManager.initAndResolve$lambda$26$lambda$5(str2, str3, (k0) obj2);
                            default:
                                return QQMusicDexKitManager.initAndResolve$lambda$26$lambda$16(str2, str3, (k0) obj2);
                        }
                    }
                });
                t0 t0Var2 = (t0) (g0VarC.isEmpty() ? null : g0VarC.first());
                if (t0Var2 != null) {
                    methodUpdateRoute = t0Var2.b().b;
                }
                final int i2 = 1;
                Iterator it2 = dexKitBridge.c(new a1(str2, i2)).iterator();
                do {
                    if (!it2.hasNext()) {
                        next2 = null;
                        break;
                    }
                    next2 = it2.next();
                } while (Modifier.isStatic(((t0) next2).b));
                t0 t0Var3 = (t0) next2;
                if (t0Var3 != null) {
                    methodGetGearType = t0Var3.b().b;
                }
                Iterator it3 = dexKitBridge.c(new a1(str2, 2)).iterator();
                do {
                    if (!it3.hasNext()) {
                        next3 = null;
                        break;
                    }
                    next3 = it3.next();
                } while (Modifier.isStatic(((t0) next3).b));
                t0 t0Var4 = (t0) next3;
                if (t0Var4 != null) {
                    methodIsCarAudio = t0Var4.b().b;
                }
                for (Object obj2 : dexKitBridge.c(new o0() { // from class: b1
                    @Override // defpackage.o0
                    public final Object b(Object obj3) {
                        switch (i2) {
                            case 0:
                                return QQMusicDexKitManager.initAndResolve$lambda$26$lambda$5(str2, str3, (k0) obj3);
                            default:
                                return QQMusicDexKitManager.initAndResolve$lambda$26$lambda$16(str2, str3, (k0) obj3);
                        }
                    }
                })) {
                    if (!Modifier.isStatic(((t0) obj2).b)) {
                        obj = obj2;
                        break;
                    }
                }
                t0 t0Var5 = (t0) obj;
                if (t0Var5 != null) {
                    methodGetGearInfo = t0Var5.b().b;
                }
                g0 g0VarB = dexKitBridge.b(new a1(str3, 3));
                ArrayList arrayList = new ArrayList();
                for (Object obj3 : g0VarB) {
                    if (!Modifier.isStatic(((f0) obj3).b)) {
                        arrayList.add(obj3);
                    }
                }
                ArrayList arrayList2 = new ArrayList();
                for (Object obj4 : arrayList) {
                    if (q.a(((n) ((f0) obj4).f.a()).b(), "int")) {
                        arrayList2.add(obj4);
                    }
                }
                List listN = r.n(arrayList2, new Comparator<Object>() { // from class: hun.mesh.carwithenhance.dexkit.QQMusicDexKitManager$initAndResolve$lambda$26$$inlined$sortedBy$1
                    /* JADX WARN: Multi-variable type inference failed */
                    @Override // java.util.Comparator
                    public final int compare(Object t, Object t2) {
                        return q.c(((f0) t).b().b, ((f0) t2).b().b);
                    }
                });
                ArrayList arrayList3 = new ArrayList();
                for (Object obj5 : arrayList) {
                    if (q.a(((n) ((f0) obj5).f.a()).b(), "java.lang.String")) {
                        arrayList3.add(obj5);
                    }
                }
                List listN2 = r.n(arrayList3, new Comparator<Object>() { // from class: hun.mesh.carwithenhance.dexkit.QQMusicDexKitManager$initAndResolve$lambda$26$$inlined$sortedBy$2
                    /* JADX WARN: Multi-variable type inference failed */
                    @Override // java.util.Comparator
                    public final int compare(Object t, Object t2) {
                        return q.c(((f0) t).b().b, ((f0) t2).b().b);
                    }
                });
                if (listN.size() >= 3) {
                    fieldGearType = ((f0) listN.get(0)).b().b;
                    fieldIntG = ((f0) listN.get(1)).b().b;
                    fieldIntH = ((f0) listN.get(2)).b().b;
                }
                if (listN2.size() >= 3) {
                    fieldBrand = ((f0) listN2.get(0)).b().b;
                    fieldModel = ((f0) listN2.get(1)).b().b;
                    fieldMac = ((f0) listN2.get(2)).b().b;
                }
                INSTANCE.saveToCache(sharedPreferences, longVersionCode);
                XLog.i("QQMusicDexKit: 搜索完成！getInstance=" + methodGetInstance + ", updateRoute=" + methodUpdateRoute + ", GearInfoFileds:" + fieldGearType + "," + fieldIntG + "," + fieldIntH + "," + fieldBrand + "," + fieldModel + "," + fieldMac);
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
            XLog.e("QQMusicDexKit 初始化异常: ", th3);
        }
    }

    public final void setClassAudioGearInfo(String str) {
        str.getClass();
        classAudioGearInfo = str;
    }

    public final void setClassAudioRouteManager(String str) {
        str.getClass();
        classAudioRouteManager = str;
    }

    public final void setFieldBrand(String str) {
        str.getClass();
        fieldBrand = str;
    }

    public final void setFieldGearType(String str) {
        str.getClass();
        fieldGearType = str;
    }

    public final void setFieldIntG(String str) {
        str.getClass();
        fieldIntG = str;
    }

    public final void setFieldIntH(String str) {
        str.getClass();
        fieldIntH = str;
    }

    public final void setFieldMac(String str) {
        str.getClass();
        fieldMac = str;
    }

    public final void setFieldModel(String str) {
        str.getClass();
        fieldModel = str;
    }

    public final void setMethodGetGearInfo(String str) {
        str.getClass();
        methodGetGearInfo = str;
    }

    public final void setMethodGetGearType(String str) {
        str.getClass();
        methodGetGearType = str;
    }

    public final void setMethodGetInstance(String str) {
        str.getClass();
        methodGetInstance = str;
    }

    public final void setMethodIsCarAudio(String str) {
        str.getClass();
        methodIsCarAudio = str;
    }

    public final void setMethodUpdateRoute(String str) {
        str.getClass();
        methodUpdateRoute = str;
    }
}
