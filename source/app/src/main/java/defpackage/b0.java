package defpackage;

import java.util.ArrayList;

/* JADX INFO: compiled from: r8-map-id-2844820780df513d20833d92dac6e5f4b7fbbd08283aea6743910c9549ac13d9 */
/* JADX INFO: loaded from: classes.dex */
public final class b0 {
    public final String a;
    public final String b;
    public final ArrayList c;
    public final String d;
    public final n1 e;

    public b0(String str) {
        int i;
        char cCharAt;
        str.getClass();
        this.e = new n1(new k(2, this));
        int iIndexOf = 0;
        int iK = m1.k(str, "->", 0, 6);
        int iK2 = m1.k(str, "(", iK + 1, 4);
        int i2 = iK2 + 1;
        int iK3 = m1.k(str, ")", i2, 4);
        if (iK == -1 || iK2 == -1 || iK3 == -1) {
            throw new IllegalAccessError("not method descriptor: ".concat(str));
        }
        this.a = c0.a(str.substring(0, iK));
        this.b = str.substring(iK + 2, iK2);
        String strSubstring = str.substring(i2, iK3);
        ArrayList arrayList = new ArrayList();
        while (iIndexOf < strSubstring.length()) {
            int start = iIndexOf;
            while (iIndexOf < strSubstring.length() && strSubstring.charAt(iIndexOf) == '[') {
                iIndexOf++;
            }
            if (iIndexOf >= strSubstring.length()) {
                throw new IllegalStateException("Unknown signString: ".concat(strSubstring));
            }
            char type = strSubstring.charAt(iIndexOf);
            if (type == 'L') {
                int end = strSubstring.indexOf(';', iIndexOf);
                if (end < 0) {
                    throw new IllegalStateException("Unknown signString: ".concat(strSubstring));
                }
                iIndexOf = end;
            }
            iIndexOf++;
            arrayList.add(c0.a(strSubstring.substring(start, iIndexOf)));
        }        this.c = arrayList;
        this.d = c0.a(str.substring(iK3 + 1));
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof b0)) {
            return false;
        }
        b0 b0Var = (b0) obj;
        return q.a(this.a, b0Var.a) && q.a(this.b, b0Var.b) && q.a(this.c, b0Var.c) && q.a(this.d, b0Var.d);
    }

    public final int hashCode() {
        return this.d.hashCode() + (this.c.hashCode() * 31) + (this.b.hashCode() * 31) + (this.a.hashCode() * 31);
    }

    public final String toString() {
        return c0.b(this.a) + "->" + this.b + ((String) this.e.a());
    }
}
