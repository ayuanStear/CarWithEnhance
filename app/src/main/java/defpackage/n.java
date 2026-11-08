package defpackage;

import java.lang.reflect.Modifier;
import java.util.ArrayList;
import org.luckypray.dexkit.DexKitBridge;

/* JADX INFO: compiled from: r8-map-id-2844820780df513d20833d92dac6e5f4b7fbbd08283aea6743910c9549ac13d9 */
/* JADX INFO: loaded from: classes.dex */
public final class n extends e {
    public final int b;
    public final String c;
    public final Integer d;
    public final ArrayList e;
    public final n1 f;
    public final n1 g;
    public final n1 h;

    public n(DexKitBridge dexKitBridge, int i, int i2, int i3, String str, Integer num, ArrayList arrayList, ArrayList arrayList2, ArrayList arrayList3) {
        super(dexKitBridge, i, i2);
        this.b = i3;
        this.c = str;
        this.d = num;
        this.e = arrayList;
        int i4 = 0;
        this.f = new n1(new k(i4, this));
        this.g = new n1(new l(this, dexKitBridge, i2));
        this.h = new n1(new l(dexKitBridge, this, i2, i4));
    }

    public final String b() {
        return ((y) this.f.a()).a;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof n) {
            return q.a(this.c, ((n) obj).c);
        }
        return false;
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
        sb.append("class " + b());
        n nVar = (n) this.g.a();
        if (nVar != null) {
            sb.append(" extends ");
            sb.append(nVar.b());
        }
        if (this.e.size() > 0) {
            sb.append(" implements ");
            sb.append(r.m((o) this.h.a(), ", ", m.b, 30));
        }
        return sb.toString();
    }
}
