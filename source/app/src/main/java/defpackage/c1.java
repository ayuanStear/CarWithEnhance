package defpackage;

/* JADX INFO: compiled from: r8-map-id-2844820780df513d20833d92dac6e5f4b7fbbd08283aea6743910c9549ac13d9 */
/* JADX INFO: loaded from: classes.dex */
public abstract class c1 {
    public static final d1 a;

    static {
        d1 d1Var = null;
        try {
            d1Var = (d1) Class.forName("kotlin.reflect.jvm.internal.ReflectionFactoryImpl").newInstance();
        } catch (ClassCastException | ClassNotFoundException | IllegalAccessException | InstantiationException unused) {
        }
        if (d1Var == null) {
            d1Var = new d1();
        }
        a = d1Var;
    }
}
