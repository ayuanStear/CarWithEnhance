package hun.mesh.carwithenhance.dexkit;

import android.app.Application;
import android.content.Context;
import android.util.Log;
import defpackage.o0;
import defpackage.u0;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/* JADX INFO: loaded from: classes2.dex */
public final class GenericAutoPlayHooks {
    public static final int COLD_START_ANTI_KILL = 3;
    public static final int EXPERIENCE_TO_NORMAL = 4;
    public static final int MEDIA_APP_OPEN = 2;
    public static final int MEDIA_AUTO_PLAY = 1;
    private static final String MODULE_PACKAGE = "hun.mesh.carwithenhance";
    private static final String TAG = "carwithenhance";
    private static Object bridge;
    private static Method bridgeFindMethod;
    private static ClassLoader dexKitLoader;
    private static String dexPath;
    private static String experienceClass;
    private static String experienceMethod;
    private static String mediaClass;
    private static String mediaMethod;

    private GenericAutoPlayHooks() {
    }

    public static Object[] resolveMediaAutoPlay(ClassLoader classLoader) throws Exception {
        return resolve(1, classLoader);
    }

    public static Object[] resolveMediaAppOpen(ClassLoader classLoader) throws Exception {
        return resolve(2, classLoader);
    }

    public static Object[] resolveColdStartAntiKill(ClassLoader classLoader) throws Exception {
        return resolve(3, classLoader);
    }

    public static Object[] resolveExperienceToNormal(ClassLoader classLoader) throws Exception {
        return resolve(4, classLoader);
    }

    private static synchronized Object[] resolve(int i, ClassLoader classLoader) throws Exception {
        List<Candidate> candidates = query(i, classLoader);
        HashSet<String> seen = new HashSet<>();
        for (Candidate candidate : candidates) {
            if (!seen.add(candidate.className + "#" + candidate.methodName)) {
                continue;
            }
            Class<?> cls = Class.forName(candidate.className, false, classLoader);
            for (Method method : cls.getDeclaredMethods()) {
                if (!candidate.methodName.equals(method.getName()) || !accepts(i, method)) {
                    continue;
                }
                if (i == 1) {
                    mediaClass = candidate.className;
                    mediaMethod = candidate.methodName;
                } else if (i == 4) {
                    experienceClass = candidate.className;
                    experienceMethod = candidate.methodName;
                }
                Log.i(TAG, "[通用Hook] 已解析 " + nameOf(i) + " -> " + candidate.className + "#" + candidate.methodName);
                return new Object[]{cls, candidate.methodName};
            }
        }
        throw new IllegalStateException("[通用Hook] 未解析到 " + nameOf(i) + " 目标");
    }
    private static List<Candidate> query(int i, ClassLoader classLoader) throws Exception {
        Context contextAppContext = appContext(classLoader);
        ensureBridge(contextAppContext, contextAppContext.getApplicationInfo().sourceDir);
        Object objInvoke = bridgeFindMethod.invoke(bridge, Proxy.newProxyInstance(dexKitLoader, new Class[]{Class.forName("o0", false, dexKitLoader)}, new QueryHandler(i, dexKitLoader)));
        ArrayList arrayList = new ArrayList();
        if (!(objInvoke instanceof List)) {
            return arrayList;
        }
        for (Object obj : (List) objInvoke) {
            try {
                Class[] clsArr = new Class[0];
                Object objInvoke2 = obj.getClass().getMethod("b", new Class<?>[0]).invoke(obj, new Object[0]);
                String str = (String) objInvoke2.getClass().getField("a").get(objInvoke2);
                String str2 = (String) objInvoke2.getClass().getField("b").get(objInvoke2);
                if (str != null && str2 != null && str.length() > 0) {
                    arrayList.add(new Candidate(str, str2));
                }
            } catch (Throwable th) {
                Log.w(TAG, "[通用Hook] 忽略无效 DexKit 结果", th);
            }
        }
        Log.i(TAG, "[通用Hook] " + nameOf(i) + " 候选数量=" + arrayList.size());
        return arrayList;
    }

    private static synchronized void ensureBridge(Context context, String str) throws Exception {
        if (bridge == null || !str.equals(dexPath)) {
            try {
                System.loadLibrary("dexkit");
            } catch (Throwable unused) {
            }
            ClassLoader classLoader = GenericAutoPlayHooks.class.getClassLoader();
            dexKitLoader = classLoader;
            Class<?> cls = Class.forName("org.luckypray.dexkit.DexKitBridge", true, classLoader);
            bridge = cls.getConstructor(String.class).newInstance(str);
            bridgeFindMethod = cls.getMethod("c", Class.forName("o0", false, dexKitLoader));
            dexPath = str;
        }
    }

    public static Object getMediaHelperInstance(ClassLoader classLoader) throws Exception {
        if (mediaClass == null) {
            resolveMediaAutoPlay(classLoader);
        }
        Class<?> cls = Class.forName(mediaClass, false, classLoader);
        for (Method method : cls.getDeclaredMethods()) {
            if (Modifier.isStatic(method.getModifiers()) && method.getParameterTypes().length == 0 && method.getReturnType() == cls) {
                method.setAccessible(true);
                return method.invoke(null, new Object[0]);
            }
        }
        throw new IllegalStateException("未找到 MediaAutoPlayHelper 单例入口");
    }

    public static String getMediaMethodName() {
        return mediaMethod;
    }

    public static String getExperienceClassName() {
        return experienceClass;
    }

    public static String getExperienceMethodName() {
        return experienceMethod;
    }

    private static boolean accepts(int i, Method method) {
        Class<?>[] parameterTypes = method.getParameterTypes();
        if (method.getReturnType() != Void.TYPE) {
            return false;
        }
        if (i == 1) {
            return parameterTypes.length == 2 && parameterTypes[0] == String.class && parameterTypes[1] == Boolean.TYPE;
        }
        if (i == 2) {
            return parameterTypes.length >= 2 && parameterTypes[0] == Context.class && parameterTypes[1] == String.class;
        }
        if (i != 3) {
            return i == 4 && parameterTypes.length == 1 && parameterTypes[0] == Set.class;
        }
        return parameterTypes.length == 2 && parameterTypes[0] == Context.class && parameterTypes[1] == String.class;
    }

    private static Context appContext(ClassLoader classLoader) throws Exception {
        try {
            Class[] clsArr = new Class[0];
            Application application = (Application) Class.forName("android.app.ActivityThread").getMethod("currentApplication", new Class<?>[0]).invoke(null, new Object[0]);
            if (application != null) {
                return application;
            }
        } catch (Throwable unused) {
        }
        Context context = (Context) Class.forName("hun.mesh.carwithenhance.utils.AppUtils", false, classLoader).getMethod("getAppContext", ClassLoader.class).invoke(null, classLoader);
        if (context != null) {
            return context;
        }
        throw new IllegalStateException("无法获取 CarWith Context");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static Object allocate(Class<?> cls) throws Exception {
        Class<?> cls2 = Class.forName("sun.misc.Unsafe");
        Field declaredField = cls2.getDeclaredField("theUnsafe");
        declaredField.setAccessible(true);
        return cls2.getMethod("allocateInstance", Class.class).invoke(declaredField.get(null), cls);
    }

    private static String nameOf(int i) {
        if (i == 1) {
            return "MEDIA_AUTO_PLAY";
        }
        if (i == 2) {
            return "MEDIA_APP_OPEN";
        }
        if (i == 3) {
            return "COLD_START_ANTI_KILL";
        }
        if (i == 4) {
            return "EXPERIENCE_TO_NORMAL";
        }
        return "UNKNOWN";
    }

    private static final class Candidate {
        final String className;
        final String methodName;

        Candidate(String str, String str2) {
            this.className = str;
            this.methodName = str2;
        }
    }

    private static final class QueryHandler implements InvocationHandler {
        private final int kind;
        private final ClassLoader loader;

        QueryHandler(int i, ClassLoader classLoader) {
            this.kind = i;
            this.loader = classLoader;
        }

        @Override // java.lang.reflect.InvocationHandler
        public Object invoke(Object obj, Method method, Object[] objArr) throws Exception {
            if (method.getDeclaringClass() == Object.class) {
                if ("toString".equals(method.getName())) {
                    return "GenericDexKitQuery";
                }
                if ("hashCode".equals(method.getName())) {
                    return Integer.valueOf(System.identityHashCode(obj));
                }
                if ("equals".equals(method.getName())) {
                    return Boolean.valueOf(obj == objArr[0]);
                }
            }
            if (!"b".equals(method.getName()) || objArr == null || objArr.length != 1) {
                return Boolean.TRUE;
            }
            Object obj2 = objArr[0];
            Class<?> cls = Class.forName("u0", false, this.loader);
            Object objAllocate = GenericAutoPlayHooks.allocate(cls);
            cls.getMethod("n", String.class, cls).invoke(null, "void", objAllocate);
            Method method2 = cls.getMethod("m", String[].class);
            Method method3 = cls.getMethod("q", String[].class);
            int i = this.kind;
            if (i == 1) {
                method2.invoke(objAllocate, (Object) new String[]{"java.lang.String", "boolean"});
                method3.invoke(objAllocate, (Object) new String[]{"MediaAutoPlayHelper"});
            } else if (i == 2) {
                method3.invoke(objAllocate, (Object) new String[]{"startMediaApp isNormalCastMode="});
            } else if (i == 3) {
                method2.invoke(objAllocate, (Object) new String[]{"android.content.Context", "java.lang.String"});
                method3.invoke(objAllocate, (Object) new String[]{"forceStopApp:"});
            } else if (i == 4) {
                method2.invoke(objAllocate, (Object) new String[]{"java.util.Set"});
                method3.invoke(objAllocate, (Object) new String[]{"publish data, pages="});
            } else {
                throw new IllegalArgumentException("unknown query kind " + this.kind);
            }
            obj2.getClass().getField("c").set(obj2, objAllocate);
            return Boolean.TRUE;
        }
    }

    private static final class Query implements o0 {
        private final int kind;

        Query(int i) {
            this.kind = i;
        }

        public Object b(Object obj) {
            u0 u0Var = (u0) obj;
            u0.n("void", u0Var);
            int i = this.kind;
            if (i == 1) {
                u0Var.m(new String[]{"java.lang.String", "boolean"});
                u0Var.q(new String[]{"MediaAutoPlayHelper"});
            } else if (i == 2) {
                u0Var.q(new String[]{"startMediaApp isNormalCastMode="});
            } else if (i == 3) {
                u0Var.m(new String[]{"android.content.Context", "java.lang.String"});
                u0Var.q(new String[]{"forceStopApp:"});
            } else if (i == 4) {
                u0Var.m(new String[]{"java.util.Set"});
                u0Var.q(new String[]{"publish data, pages="});
            } else {
                throw new IllegalArgumentException("unknown query kind " + this.kind);
            }
            return Boolean.TRUE;
        }
    }
}
