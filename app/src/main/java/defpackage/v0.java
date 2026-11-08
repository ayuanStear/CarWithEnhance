package defpackage;

import java.util.ArrayList;
import java.util.List;

/* JADX INFO: compiled from: r8-map-id-2844820780df513d20833d92dac6e5f4b7fbbd08283aea6743910c9549ac13d9 */
/* JADX INFO: loaded from: classes.dex */
public final class v0 extends q {
    public List b;

    @Override // defpackage.q
    public final int h(m0 m0Var) {
        int iD;
        List<u0> list = this.b;
        if (list != null) {
            ArrayList arrayList = new ArrayList(t.k(list));
            for (u0 u0Var : list) {
                u0Var.getClass();
                arrayList.add(Integer.valueOf(u0Var.h(m0Var)));
            }
            iD = m0Var.d(r.o(arrayList));
        } else {
            iD = 0;
        }
        m0Var.i(3);
        m0Var.a(2, 0);
        m0Var.a(0, iD);
        int iE = m0Var.e();
        m0Var.f(iE);
        return iE;
    }
}
