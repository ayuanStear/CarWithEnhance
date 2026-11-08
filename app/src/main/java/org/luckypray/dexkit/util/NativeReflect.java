package org.luckypray.dexkit.util;

import java.lang.reflect.Field;
import java.lang.reflect.Member;

/* JADX INFO: compiled from: r8-map-id-2844820780df513d20833d92dac6e5f4b7fbbd08283aea6743910c9549ac13d9 */
/* JADX INFO: loaded from: classes.dex */
public final class NativeReflect {
    public static final native Field getReflectedField(Class<?> cls, String str, String str2, Boolean bool);

    public static final native Member getReflectedMethod(Class<?> cls, String str, String str2, Boolean bool);
}
