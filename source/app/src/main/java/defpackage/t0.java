package defpackage;

import java.lang.reflect.Modifier;
import java.util.ArrayList;
import org.luckypray.dexkit.DexKitBridge;

/* JADX INFO: compiled from: r8-map-id-2844820780df513d20833d92dac6e5f4b7fbbd08283aea6743910c9549ac13d9 */
/* JADX INFO: loaded from: classes.dex */
public final class t0 extends e {
    public final int b;
    public final String c;
    public final n1 d;

    public t0(DexKitBridge dexKitBridge, int i, int i2, int i3, int i4, String str, int i5, ArrayList arrayList) {
        super(dexKitBridge, i, i2);
        this.b = i4;
        this.c = str;
        this.d = new n1(new k(4, this));
    }

    public final b0 b() {
        return (b0) this.d.a();
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        return (obj instanceof t0) && q.a(((t0) obj).c, this.c);
    }

    public final int hashCode() {
        return this.c.hashCode();
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder();
        int i = this.b;
        if (i != 0) {
            sb.append(Modifier.toString(i) + " ");
        }
        sb.append(b().d);
        sb.append(" ");
        sb.append(b().a);
        sb.append(".");
        sb.append(b().b);
        sb.append("(");
        sb.append(r.m(b().c, ", ", null, 62));
        sb.append(")");
        return sb.toString();
    }
}
