package defpackage;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public abstract class r extends x {
    public static String m(ArrayList arrayList, String str, o0 o0Var, int i) {
        if ((i & 32) != 0) {
            o0Var = null;
        }
        arrayList.getClass();
        StringBuilder sb = new StringBuilder();
        sb.append((CharSequence) "");
        Iterator it = arrayList.iterator();
        int i2 = 0;
        while (it.hasNext()) {
            Object next = it.next();
            i2++;
            if (i2 > 1) {
                sb.append((CharSequence) str);
            }
            if (o0Var != null) {
                sb.append((CharSequence) o0Var.b(next));
            } else {
                if (next != null ? next instanceof CharSequence : true) {
                    sb.append((CharSequence) next);
                } else if (next instanceof Character) {
                    sb.append(((Character) next).charValue());
                } else {
                    sb.append((CharSequence) next.toString());
                }
            }
        }
        sb.append((CharSequence) "");
        return sb.toString();
    }

    public static List n(ArrayList arrayList, Comparator comparator) {
        if (arrayList.size() > 1) {
            Object[] array = arrayList.toArray(new Object[0]);
            array.getClass();
            if (array.length > 1) {
                Arrays.sort(array, comparator);
            }
            List listAsList = Arrays.asList(array);
            listAsList.getClass();
            return listAsList;
        }
        int size = arrayList.size();
        if (size == 0) {
            return e0.a;
        }
        if (size != 1) {
            return new ArrayList(arrayList);
        }
        List listSingletonList = Collections.singletonList(arrayList.get(0));
        listSingletonList.getClass();
        return listSingletonList;
    }

    public static int[] o(ArrayList arrayList) {
        int[] iArr = new int[arrayList.size()];
        Iterator it = arrayList.iterator();
        int i = 0;
        while (it.hasNext()) {
            iArr[i] = ((Number) it.next()).intValue();
            i++;
        }
        return iArr;
    }
}
