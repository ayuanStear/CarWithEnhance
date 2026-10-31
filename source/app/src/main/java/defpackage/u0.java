package defpackage;

import java.util.ArrayList;
import java.util.List;

/* JADX INFO: compiled from: r8-map-id-2844820780df513d20833d92dac6e5f4b7fbbd08283aea6743910c9549ac13d9 */
/* JADX INFO: loaded from: classes.dex */
public final class u0 extends q {
    public f1 b;
    public p c;
    public p d;
    public y0 e;
    public ArrayList f;
    public v0 g;
    public v0 h;

    public static void k(String str, u0 u0Var) {
        str.getClass();
        p pVar = new p();
        pVar.k(str, 5);
        u0Var.c = pVar;
    }

    public static void l(String str, u0 u0Var) {
        str.getClass();
        u0Var.b = new f1(str, 5);
    }

    public static void n(String str, u0 u0Var) {
        str.getClass();
        p pVar = new p();
        pVar.k(str, 5);
        u0Var.d = pVar;
    }

    @Override // defpackage.q
    public final int h(m0 m0Var) {
        int iD;
        f1 f1Var = this.b;
        int iH = f1Var != null ? f1Var.h(m0Var) : 0;
        p pVar = this.c;
        int iH2 = pVar != null ? pVar.h(m0Var) : 0;
        p pVar2 = this.d;
        int iH3 = pVar2 != null ? pVar2.h(m0Var) : 0;
        y0 y0Var = this.e;
        int iH4 = y0Var != null ? y0Var.h(m0Var) : 0;
        ArrayList<f1> arrayList = this.f;
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
        v0 v0Var = this.g;
        int iH5 = v0Var != null ? v0Var.h(m0Var) : 0;
        v0 v0Var2 = this.h;
        int iH6 = v0Var2 != null ? v0Var2.h(m0Var) : 0;
        m0Var.i(17);
        m0Var.a(16, 0);
        m0Var.a(15, 0);
        m0Var.a(14, 0);
        m0Var.a(13, 0);
        m0Var.a(12, iH6);
        m0Var.a(11, iH5);
        m0Var.a(10, 0);
        m0Var.a(9, 0);
        m0Var.a(8, 0);
        m0Var.a(7, iD);
        m0Var.a(6, 0);
        m0Var.a(5, 0);
        m0Var.a(4, iH4);
        m0Var.a(3, iH3);
        m0Var.a(2, iH2);
        m0Var.a(1, 0);
        m0Var.a(0, iH);
        int iE = m0Var.e();
        m0Var.f(iE);
        return iE;
    }

    public final void m(String... strArr) {
        x0 x0Var;
        y0 y0Var = new y0();
        y0Var.b = new ArrayList(e0.a);
        for (String str : strArr) {
            if (str != null) {
                x0Var = new x0();
                x0.k(x0Var, str);
            } else {
                x0Var = null;
            }
            List arrayList = y0Var.b;
            if (arrayList == null) {
                arrayList = new ArrayList();
            }
            y0Var.b = arrayList;
            arrayList.add(x0Var);
        }
        this.e = y0Var;
    }

    public final /* synthetic */ void o(String str) {
        str.getClass();
        k(str, this);
    }

    public final void p(int i) {
        if (this.e == null) {
            this.e = new y0();
        }
        y0 y0Var = this.e;
        y0Var.getClass();
        p0 p0Var = new p0();
        p0Var.b = i;
        p0Var.c = i;
        y0Var.c = p0Var;
    }

    public final void q(String... strArr) {
        ArrayList arrayList = new ArrayList(strArr.length);
        for (String str : strArr) {
            arrayList.add(new f1(str, 1));
        }
        this.f = new ArrayList(arrayList);
    }
}
