package defpackage;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* JADX INFO: compiled from: r8-map-id-2844820780df513d20833d92dac6e5f4b7fbbd08283aea6743910c9549ac13d9 */
/* JADX INFO: loaded from: classes.dex */
public final class i0 extends g {
    public List b;
    public p c;

    @Override // defpackage.q
    public final int h(m0 m0Var) {
        int iD;
        List list = this.b;
        if (list != null) {
            ArrayList arrayList = new ArrayList(t.k(list));
            Iterator it = list.iterator();
            while (it.hasNext()) {
                arrayList.add(Integer.valueOf(m0Var.c((String) it.next())));
            }
            iD = m0Var.d(r.o(arrayList));
        } else {
            iD = 0;
        }
        p pVar = this.c;
        int iH = pVar != null ? pVar.h(m0Var) : 0;
        m0Var.i(6);
        m0Var.a(5, iH);
        m0Var.a(3, 0);
        m0Var.a(1, 0);
        m0Var.a(0, iD);
        int iE = m0Var.e();
        m0Var.f(iE);
        return iE;
    }
}
