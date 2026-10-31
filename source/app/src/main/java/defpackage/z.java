package defpackage;

/* JADX INFO: compiled from: r8-map-id-2844820780df513d20833d92dac6e5f4b7fbbd08283aea6743910c9549ac13d9 */
/* JADX INFO: loaded from: classes.dex */
public final class z {
    public final String a;
    public final String b;
    public final String c;
    public final n1 d;

    public z(String str) {
        str.getClass();
        this.d = new n1(new k(1, this));
        int iK = m1.k(str, "->", 0, 6);
        int iK2 = m1.k(str, ":", iK + 1, 4);
        if (iK == -1 || iK2 == -1) {
            throw new IllegalAccessError("not field descriptor: ".concat(str));
        }
        this.a = c0.a(str.substring(0, iK));
        this.b = str.substring(iK + 2, iK2);
        this.c = c0.a(str.substring(iK2 + 1));
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof z)) {
            return false;
        }
        z zVar = (z) obj;
        return q.a(this.a, zVar.a) && q.a(this.b, zVar.b) && q.a(this.c, zVar.c);
    }

    public final int hashCode() {
        return this.c.hashCode() + (this.b.hashCode() * 31) + (this.a.hashCode() * 31);
    }

    public final String toString() {
        return c0.b(this.a) + "->" + this.b + ":" + ((String) this.d.a());
    }
}
