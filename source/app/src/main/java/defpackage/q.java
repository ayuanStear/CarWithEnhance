package defpackage;

import java.lang.reflect.Array;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import org.luckypray.dexkit.DexKitBridge;

/* JADX INFO: compiled from: r8-map-id-2844820780df513d20833d92dac6e5f4b7fbbd08283aea6743910c9549ac13d9 */
/* JADX INFO: loaded from: classes.dex */
public abstract class q {
    public static final Object[] a = new Object[0];

    public static boolean a(Object obj, Object obj2) {
        if (obj == null) {
            return obj2 == null;
        }
        return obj.equals(obj2);
    }

    public static final void b(DexKitBridge dexKitBridge, Throwable th) throws IllegalAccessException, InvocationTargetException {
        try {
            dexKitBridge.close();
        } catch (Throwable th2) {
            if (th != th2) {
                Integer num = q0.a;
                if (num == null || num.intValue() >= 19) {
                    th.addSuppressed(th2);
                    return;
                }
                Method method = z0.a;
                if (method != null) {
                    method.invoke(th, th2);
                }
            }
        }
    }

    public static int c(String str, String str2) {
        if (str == str2) {
            return 0;
        }
        if (str == null) {
            return -1;
        }
        if (str2 == null) {
            return 1;
        }
        return str.compareTo(str2);
    }

    public static n d(DexKitBridge dexKitBridge, a aVar) {
        dexKitBridge.getClass();
        int iB = aVar.b(4);
        int i = iB != 0 ? aVar.b.getInt(iB + aVar.a) : 0;
        int iB2 = aVar.b(6);
        int i2 = iB2 != 0 ? aVar.b.getInt(iB2 + aVar.a) : 0;
        int iB3 = aVar.b(8);
        if (iB3 != 0) {
            aVar.d(iB3 + aVar.a);
        }
        int iB4 = aVar.b(10);
        int i3 = iB4 != 0 ? aVar.b.getInt(iB4 + aVar.a) : 0;
        int iB5 = aVar.b(12);
        String strD = iB5 != 0 ? aVar.d(iB5 + aVar.a) : null;
        if (strD == null) {
            strD = "";
        }
        String str = strD;
        int iB6 = aVar.b(14);
        int i4 = iB6 != 0 ? aVar.b.getInt(iB6 + aVar.a) : 0;
        Integer numValueOf = i4 != -1 ? Integer.valueOf(i4) : null;
        ArrayList arrayList = new ArrayList();
        int iB7 = aVar.b(16);
        int iF = iB7 != 0 ? aVar.f(iB7) : 0;
        for (int i5 = 0; i5 < iF; i5++) {
            int iB8 = aVar.b(16);
            arrayList.add(Integer.valueOf(iB8 != 0 ? aVar.b.getInt((i5 * 4) + aVar.e(iB8)) : 0));
        }
        ArrayList arrayList2 = new ArrayList();
        int iB9 = aVar.b(18);
        int iF2 = iB9 != 0 ? aVar.f(iB9) : 0;
        for (int i6 = 0; i6 < iF2; i6++) {
            int iB10 = aVar.b(18);
            arrayList2.add(Integer.valueOf(iB10 != 0 ? aVar.b.getInt((i6 * 4) + aVar.e(iB10)) : 0));
        }
        ArrayList arrayList3 = new ArrayList();
        int iB11 = aVar.b(20);
        int iF3 = iB11 != 0 ? aVar.f(iB11) : 0;
        for (int i7 = 0; i7 < iF3; i7++) {
            int iB12 = aVar.b(20);
            arrayList3.add(Integer.valueOf(iB12 != 0 ? aVar.b.getInt((i7 * 4) + aVar.e(iB12)) : 0));
        }
        return new n(dexKitBridge, i, i2, i3, str, numValueOf, arrayList, arrayList2, arrayList3);
    }

    public static void e(byte b, byte b2, byte b3, byte b4, char[] cArr, int i) {
        if (!i(b2)) {
            if ((((b2 + 112) + (b << 28)) >> 30) == 0 && !i(b3) && !i(b4)) {
                int i2 = ((b & 7) << 18) | ((b2 & 63) << 12) | ((b3 & 63) << 6) | (b4 & 63);
                cArr[i] = (char) ((i2 >>> 10) + 55232);
                cArr[i + 1] = (char) ((i2 & 1023) + 56320);
                return;
            }
        }
        throw new IllegalArgumentException("Invalid UTF-8");
    }

    public static void f(byte b, byte b2, byte b3, char[] cArr, int i) {
        if (i(b2) || ((b == -32 && b2 < -96) || ((b == -19 && b2 >= -96) || i(b3)))) {
            throw new IllegalArgumentException("Invalid UTF-8");
        }
        cArr[i] = (char) (((b & 15) << 12) | ((b2 & 63) << 6) | (b3 & 63));
    }

    public static void g(byte b, byte b2, char[] cArr, int i) {
        if (b < -62) {
            throw new IllegalArgumentException("Invalid UTF-8: Illegal leading byte in 2 bytes utf");
        }
        if (i(b2)) {
            throw new IllegalArgumentException("Invalid UTF-8: Illegal trailing byte in 2 bytes utf");
        }
        cArr[i] = (char) (((b & 31) << 6) | (b2 & 63));
    }

    public static boolean i(byte b) {
        return b > -65;
    }

    public static final Object[] j(Collection collection, Object[] objArr) {
        Object[] objArrCopyOf;
        int size = collection.size();
        int i = 0;
        if (size != 0) {
            Iterator it = collection.iterator();
            if (it.hasNext()) {
                if (size <= objArr.length) {
                    objArrCopyOf = objArr;
                } else {
                    Object objNewInstance = Array.newInstance(objArr.getClass().getComponentType(), size);
                    objNewInstance.getClass();
                    objArrCopyOf = (Object[]) objNewInstance;
                }
                while (true) {
                    int i2 = i + 1;
                    objArrCopyOf[i] = it.next();
                    if (i2 >= objArrCopyOf.length) {
                        if (!it.hasNext()) {
                            return objArrCopyOf;
                        }
                        int i3 = ((i2 * 3) + 1) >>> 1;
                        if (i3 <= i2) {
                            i3 = 2147483645;
                            if (i2 >= 2147483645) {
                                throw new OutOfMemoryError();
                            }
                        }
                        objArrCopyOf = Arrays.copyOf(objArrCopyOf, i3);
                    } else if (!it.hasNext()) {
                        if (objArrCopyOf != objArr) {
                            return Arrays.copyOf(objArrCopyOf, i2);
                        }
                        objArr[i2] = null;
                        return objArr;
                    }
                    i = i2;
                }
            } else if (objArr.length > 0) {
                objArr[0] = null;
            }
        } else if (objArr.length > 0) {
            objArr[0] = null;
            return objArr;
        }
        return objArr;
    }

    public abstract int h(m0 m0Var);
}
