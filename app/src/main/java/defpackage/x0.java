package defpackage;

/* JADX INFO: compiled from: r8-map-id-2844820780df513d20833d92dac6e5f4b7fbbd08283aea6743910c9549ac13d9 */
/* JADX INFO: loaded from: classes.dex */
public final class x0 extends q {
    public p b;

    public static void k(x0 x0Var, String str) {
        p pVar = new p();
        pVar.k(str, 5);
        x0Var.b = pVar;
    }

    @Override // defpackage.q
    public final int h(m0 m0Var) {
        p pVar = this.b;
        int iH = pVar != null ? pVar.h(m0Var) : 0;
        m0Var.i(2);
        m0Var.a(1, iH);
        m0Var.a(0, 0);
        int iE = m0Var.e();
        m0Var.f(iE);
        return iE;
    }
}
