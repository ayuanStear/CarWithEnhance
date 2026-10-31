package defpackage;

import java.io.Serializable;

/* JADX INFO: compiled from: r8-map-id-2844820780df513d20833d92dac6e5f4b7fbbd08283aea6743910c9549ac13d9 */
/* JADX INFO: loaded from: classes.dex */
public final class n1 implements Serializable {
    public n0 a;
    public volatile Object b = l0.b;
    public final Object c = this;

    public n1(n0 n0Var) {
        this.a = n0Var;
    }

    public final Object a() {
        Object objA;
        Object obj = this.b;
        l0 l0Var = l0.b;
        if (obj != l0Var) {
            return obj;
        }
        synchronized (this.c) {
            objA = this.b;
            if (objA == l0Var) {
                n0 n0Var = this.a;
                n0Var.getClass();
                objA = n0Var.a();
                this.b = objA;
                this.a = null;
            }
        }
        return objA;
    }

    public final String toString() {
        return this.b != l0.b ? String.valueOf(a()) : "Lazy value not initialized yet.";
    }
}
