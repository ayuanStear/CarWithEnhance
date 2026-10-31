package defpackage;

import java.lang.reflect.Modifier;
import org.luckypray.dexkit.DexKitBridge;

/* JADX INFO: compiled from: r8-map-id-2844820780df513d20833d92dac6e5f4b7fbbd08283aea6743910c9549ac13d9 */
/* JADX INFO: loaded from: classes.dex */
public final class f0 extends e {
    public final int b;
    public final String c;
    public final int d;
    public final n1 e;
    public final n1 f;

    public f0(DexKitBridge dexKitBridge, int i, int i2, int i3, int i4, String str, int i5) {
        super(dexKitBridge, i, i2);
        this.b = i4;
        this.c = str;
        this.d = i5;
        this.e = new n1(new k(3, this));
        this.f = new n1(new l(dexKitBridge, this, i2, 2));
    }

    public final z b() {
        return (z) this.e.a();
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        return (obj instanceof f0) && q.a(((f0) obj).c, this.c);
    }

    public final int hashCode() {
        return this.c.hashCode();
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder();
        int i = this.b;
        if (i > 0) {
            sb.append(Modifier.toString(i) + " ");
        }
        sb.append(b().c);
        sb.append(" ");
        sb.append(b().a);
        sb.append(".");
        sb.append(b().b);
        return sb.toString();
    }
}
