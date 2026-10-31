package defpackage;

import java.util.ArrayList;

/* JADX INFO: compiled from: r8-map-id-2844820780df513d20833d92dac6e5f4b7fbbd08283aea6743910c9549ac13d9 */
/* JADX INFO: loaded from: classes.dex */
public final class p extends q {
    public f1 b;
    public ArrayList c;

    @Override // defpackage.q
    public final int h(m0 m0Var) {
        int iD;
        f1 f1Var = this.b;
        int iH = f1Var != null ? f1Var.h(m0Var) : 0;
        ArrayList<f1> arrayList = this.c;
        if (arrayList != null) {
            ArrayList arrayList2 = new ArrayList(t.k(arrayList));
            for (f1 f1Var2 : arrayList) {
                f1Var2.getClass();
                arrayList2.add(Integer.valueOf(f1Var2.h(m0Var)));
            }
            iD = m0Var.d(r.o(arrayList2));
        } else {
            iD = 0;
        }
        m0Var.i(12);
        m0Var.a(11, 0);
        m0Var.a(10, 0);
        m0Var.a(9, 0);
        m0Var.a(8, iD);
        m0Var.a(7, 0);
        m0Var.a(6, 0);
        m0Var.a(5, 0);
        m0Var.a(4, 0);
        m0Var.a(3, 0);
        m0Var.a(2, 0);
        m0Var.a(1, iH);
        m0Var.a(0, 0);
        int iE = m0Var.e();
        m0Var.f(iE);
        return iE;
    }

    public final void k(String str, int i) {
        str.getClass();
        if (i == 0) {
            throw null;
        }
        this.b = new f1(str, i);
    }
}
