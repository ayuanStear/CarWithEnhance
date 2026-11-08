package defpackage;

import java.util.Arrays;

/* JADX INFO: compiled from: r8-map-id-2844820780df513d20833d92dac6e5f4b7fbbd08283aea6743910c9549ac13d9 */
/* JADX INFO: loaded from: classes.dex */
public abstract class g extends q {
    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || !getClass().equals(obj.getClass())) {
            return false;
        }
        return Arrays.equals(k(), ((g) obj).k());
    }

    public final int hashCode() {
        int i = -2128831035;
        for (byte b : k()) {
            i = (i ^ (b & 255)) * 16777619;
        }
        return i;
    }

    public final byte[] k() {
        m0 m0Var = new m0();
        h(m0Var);
        int i = m0Var.b;
        int iCapacity = m0Var.a.capacity() - m0Var.b;
        if (!m0Var.g) {
            throw new AssertionError("FlatBuffers: you can only access the serialized buffer after it has been finished by FlatBufferBuilder.finish().");
        }
        byte[] bArr = new byte[iCapacity];
        m0Var.a.position(i);
        m0Var.a.get(bArr);
        return bArr;
    }
}
