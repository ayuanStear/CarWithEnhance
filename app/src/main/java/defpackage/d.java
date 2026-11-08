package defpackage;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public abstract class d extends q {
    public static List k(Object[] objArr) {
        objArr.getClass();
        int length = objArr.length;
        if (length == 0) {
            return e0.a;
        }
        if (length != 1) {
            return new ArrayList(new b(objArr));
        }
        List listSingletonList = Collections.singletonList(objArr[0]);
        listSingletonList.getClass();
        return listSingletonList;
    }
}
