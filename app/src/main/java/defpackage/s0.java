package defpackage;

import java.util.LinkedHashMap;

/* JADX INFO: compiled from: r8-map-id-2844820780df513d20833d92dac6e5f4b7fbbd08283aea6743910c9549ac13d9 */
/* JADX INFO: loaded from: classes.dex */
public abstract class s0 extends q {
    public static LinkedHashMap k(w0... w0VarArr) {
        int length = w0VarArr.length;
        if (length >= 0) {
            length = length < 3 ? length + 1 : length < 1073741824 ? (int) ((length / 0.75f) + 1.0f) : Integer.MAX_VALUE;
        }
        LinkedHashMap linkedHashMap = new LinkedHashMap(length);
        for (w0 w0Var : w0VarArr) {
            linkedHashMap.put(w0Var.a, w0Var.b);
        }
        return linkedHashMap;
    }
}
