package hun.mesh.carwithenhance.dexkit;

import defpackage.b0;
import defpackage.g0;
import defpackage.k0;
import defpackage.o0;
import defpackage.t0;
import defpackage.u0;
import java.io.Closeable;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.luckypray.dexkit.DexKitBridge;

/** Version-tolerant DexKit method lookup based on names, signatures, and literals. */
public final class StructuralMethodLookup implements Closeable {
    private final DexKitBridge bridge;
    private final ClassLoader hostLoader;

    public StructuralMethodLookup(String apkPath, ClassLoader hostLoader) {
        try {
            System.loadLibrary("dexkit");
        } catch (Throwable ignored) {
        }
        this.bridge = new DexKitBridge(apkPath);
        this.hostLoader = hostLoader;
    }

    public List<Target> find(QuerySpec spec) {
        if (spec == null) {
            return Collections.emptyList();
        }
        final QuerySpec querySpec = spec;
        g0 result = bridge.c(new o0() {
            @Override
            public Object b(Object queryObject) {
                k0 query = (k0) queryObject;
                u0 method = new u0();
                if (querySpec.name != null) {
                    u0.l(querySpec.name, method);
                }
                if (querySpec.returnType != null) {
                    u0.n(querySpec.returnType, method);
                }
                if (querySpec.parameterTypes != null) {
                    method.m(querySpec.parameterTypes);
                } else if (querySpec.parameterCount >= 0) {
                    method.p(querySpec.parameterCount);
                }
                if (querySpec.literal != null) {
                    method.q(querySpec.literal);
                }
                query.c = method;
                return Boolean.TRUE;
            }
        });
        ArrayList<Target> targets = new ArrayList<>();
        Set<String> seen = new HashSet<>();
        for (Object item : result) {
            t0 candidate = (t0) item;
            b0 descriptor = candidate.b();
            String className = descriptor.a;
            String methodName = descriptor.b;
            String key = className + "#" + methodName;
            if (className == null || methodName == null || !seen.add(key)) {
                continue;
            }
            try {
                Class<?> owner = Class.forName(className, false, hostLoader);
                for (Method method : owner.getDeclaredMethods()) {
                    if (!methodName.equals(method.getName())
                            || !matchesReturnType(method, querySpec.returnType)
                            || !matchesParameters(method, querySpec)) {
                        continue;
                    }
                    try {
                        method.setAccessible(true);
                    } catch (Throwable ignored) {
                    }
                    targets.add(new Target(owner, method));
                }
            } catch (Throwable ignored) {
            }
        }
        return targets;
    }

    private static boolean matchesReturnType(Method method, String expected) {
        return expected == null || typeMatches(method.getReturnType(), expected);
    }

    private static boolean matchesParameters(Method method, QuerySpec spec) {
        Class<?>[] actual = method.getParameterTypes();
        if (spec.parameterTypes != null) {
            if (actual.length != spec.parameterTypes.length) {
                return false;
            }
            for (int i = 0; i < actual.length; i++) {
                if (!typeMatches(actual[i], spec.parameterTypes[i])) {
                    return false;
                }
            }
            return true;
        }
        return spec.parameterCount < 0 || actual.length == spec.parameterCount;
    }

    private static boolean typeMatches(Class<?> type, String expected) {
        return expected != null
                && (expected.equals(type.getName()) || expected.equals(type.getCanonicalName()));
    }

    @Override
    public void close() {
        bridge.close();
    }

    public static final class QuerySpec {
        final String name;
        final String returnType;
        final String[] parameterTypes;
        final int parameterCount;
        final String literal;

        private QuerySpec(
                String name,
                String returnType,
                String[] parameterTypes,
                int parameterCount,
                String literal) {
            this.name = name;
            this.returnType = returnType;
            this.parameterTypes = parameterTypes;
            this.parameterCount = parameterCount;
            this.literal = literal;
        }

        public static QuerySpec named(
                String name,
                String returnType,
                String... parameterTypes) {
            return new QuerySpec(name, returnType, parameterTypes, -1, null);
        }

        public static QuerySpec containing(
                String literal,
                String returnType,
                int parameterCount) {
            return new QuerySpec(null, returnType, null, parameterCount, literal);
        }
    }

    public static final class Target {
        public final Class<?> owner;
        public final Method method;

        Target(Class<?> owner, Method method) {
            this.owner = owner;
            this.method = method;
        }

        public String describe() {
            return owner.getName() + "#" + method.getName();
        }
    }
}
