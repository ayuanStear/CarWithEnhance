package defpackage;

import java.nio.ByteBuffer;

/* JADX INFO: compiled from: r8-map-id-2844820780df513d20833d92dac6e5f4b7fbbd08283aea6743910c9549ac13d9 */
/* JADX INFO: loaded from: classes.dex */
public final class f1 extends q {
    public final String b;
    public int c;

    public f1(String str, int i) {
        str.getClass();
        if (i == 0) {
            throw null;
        }
        this.b = str;
        this.c = i;
    }

    @Override // defpackage.q
    public final int h(m0 m0Var) {
        byte b;
        String str = this.b;
        if (str == null) {
            throw new IllegalArgumentException("value must be specified");
        }
        if (str.length() == 0 && this.c != 5) {
            this.c = 5;
        }
        int iC = m0Var.c(str);
        int i = this.c;
        if (i != 1) {
            b = 2;
            if (i == 2) {
                b = 1;
            } else if (i != 3) {
                b = 4;
                if (i == 4) {
                    b = 3;
                } else if (i != 5) {
                    throw null;
                }
            }
        } else {
            b = 0;
        }
        m0Var.i(3);
        m0Var.a(0, iC);
        if (b != 0) {
            m0Var.h(1, 0);
            ByteBuffer byteBuffer = m0Var.a;
            int i2 = m0Var.b - 1;
            m0Var.b = i2;
            byteBuffer.put(i2, b);
            m0Var.d[1] = m0Var.g();
        }
        int iE = m0Var.e();
        m0Var.f(iE);
        return iE;
    }
}
