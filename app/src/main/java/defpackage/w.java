package defpackage;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/* JADX INFO: compiled from: r8-map-id-2844820780df513d20833d92dac6e5f4b7fbbd08283aea6743910c9549ac13d9 */
/* JADX INFO: loaded from: classes.dex */
public abstract class w extends v {
    public static void l(ArrayList arrayList, Comparator comparator) {
        if (arrayList.size() > 1) {
            Collections.sort(arrayList, comparator);
        }
    }
}
