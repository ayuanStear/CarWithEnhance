package defpackage;

import java.util.ArrayList;

/* JADX INFO: compiled from: r8-map-id-2844820780df513d20833d92dac6e5f4b7fbbd08283aea6743910c9549ac13d9 */
/* JADX INFO: loaded from: classes.dex */
public abstract class f extends ArrayList {
    public final Object a() {
        if (super.size() == 0) {
            return null;
        }
        Object e = get(0);
        int size = super.size();
        for (int i = 1; i < size; i++) {
            if (!q.a(e, get(i))) {
                return null;
            }
        }
        return e;
    }

    public final Object b(h hVar) {
        if (super.size() != 0) {
            Object obj = null;
            for (Object obj2 : this) {
                if (((Boolean) hVar.b(obj2)).booleanValue()) {
                    if (obj == null || obj.equals(obj2)) {
                        obj = obj2;
                    }
                }
            }
            return obj;
        }
        return null;
    }

    public final Object first() {
        if (isEmpty()) {
            throw new IllegalStateException("list is empty");
        }
        return get(0);
    }
}
