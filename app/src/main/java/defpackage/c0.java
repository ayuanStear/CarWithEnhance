package defpackage;

import java.util.LinkedHashMap;

/* JADX INFO: compiled from: r8-map-id-2844820780df513d20833d92dac6e5f4b7fbbd08283aea6743910c9549ac13d9 */
/* JADX INFO: loaded from: classes.dex */
public abstract class c0 {
    public static final LinkedHashMap a = s0.k(new w0("boolean", "Z"), new w0("byte", "B"), new w0("char", "C"), new w0("short", "S"), new w0("int", "I"), new w0("float", "F"), new w0("long", "J"), new w0("double", "D"), new w0("void", "V"));
    public static final LinkedHashMap b = s0.k(new w0("Z", "boolean"), new w0("B", "byte"), new w0("C", "char"), new w0("S", "short"), new w0("I", "int"), new w0("F", "float"), new w0("J", "long"), new w0("D", "double"), new w0("V", "void"));

    public static final String a(String str) {
        str.getClass();
        if (str.charAt(0) == '[') {
            return a(str.substring(1)).concat("[]");
        }
        if (str.length() == 1) {
            String str2 = (String) b.get(str);
            if (str2 != null) {
                return str2;
            }
            throw new IllegalArgumentException("Unknown primitive typeSign: ".concat(str));
        }
        if (str.charAt(0) != 'L' || str.charAt(str.length() - 1) != ';') {
            throw new IllegalStateException("Unknown class sign: ".concat(str));
        }
        String strReplace = str.substring(1, str.length() - 1).replace('/', '.');
        strReplace.getClass();
        return strReplace;
    }

    public static final String b(String str) {
        str.getClass();
        if (str.endsWith("[]")) {
            return "[".concat(b(str.substring(0, str.length() - 2)));
        }
        String str2 = (String) a.get(str);
        if (str2 != null) {
            return str2;
        }
        String strReplace = str.replace('.', '/');
        strReplace.getClass();
        return "L" + strReplace + ";";
    }
}
