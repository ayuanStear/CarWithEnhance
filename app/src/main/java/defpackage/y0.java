package defpackage;

import java.util.ArrayList;
import java.util.List;

/* JADX INFO: compiled from: r8-map-id-2844820780df513d20833d92dac6e5f4b7fbbd08283aea6743910c9549ac13d9 */
/* JADX INFO: loaded from: classes.dex */
public final class y0 extends q {
    public List b;
    public p0 c;

    @Override // defpackage.q
    public final int h(m0 m0Var) {
        int iD;
        List<x0> list = this.b;
        if (list != null) {
            ArrayList arrayList = new ArrayList(t.k(list));
            for (x0 x0Var : list) {
                if (x0Var == null) {
                    x0Var = new x0();
                }
                arrayList.add(Integer.valueOf(x0Var.h(m0Var)));
            }
            iD = m0Var.d(r.o(arrayList));
        } else {
            iD = 0;
        }
        p0 p0Var = this.c;
        int iH = p0Var != null ? p0Var.h(m0Var) : 0;
        m0Var.i(2);
        m0Var.a(1, iH);
        m0Var.a(0, iD);
        int iE = m0Var.e();
        m0Var.f(iE);
        return iE;
    }
}
