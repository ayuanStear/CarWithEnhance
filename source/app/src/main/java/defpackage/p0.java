package defpackage;

import java.nio.ByteBuffer;

/* JADX INFO: compiled from: r8-map-id-2844820780df513d20833d92dac6e5f4b7fbbd08283aea6743910c9549ac13d9 */
/* JADX INFO: loaded from: classes.dex */
public final class p0 extends q {
    public int b;
    public int c;

    @Override // defpackage.q
    public final int h(m0 m0Var) {
        int i = this.b;
        int i2 = this.c;
        m0Var.i(2);
        if (i2 != 0) {
            m0Var.h(4, 0);
            ByteBuffer byteBuffer = m0Var.a;
            int i3 = m0Var.b - 4;
            m0Var.b = i3;
            byteBuffer.putInt(i3, i2);
            m0Var.d[1] = m0Var.g();
        }
        if (i != 0) {
            m0Var.h(4, 0);
            ByteBuffer byteBuffer2 = m0Var.a;
            int i4 = m0Var.b - 4;
            m0Var.b = i4;
            byteBuffer2.putInt(i4, i);
            m0Var.d[0] = m0Var.g();
        }
        int iE = m0Var.e();
        m0Var.f(iE);
        return iE;
    }
}
