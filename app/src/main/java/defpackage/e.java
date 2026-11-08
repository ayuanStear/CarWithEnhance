package defpackage;

import org.luckypray.dexkit.DexKitBridge;

/* JADX INFO: compiled from: r8-map-id-2844820780df513d20833d92dac6e5f4b7fbbd08283aea6743910c9549ac13d9 */
/* JADX INFO: loaded from: classes.dex */
public abstract class e {
    public final DexKitBridge a;

    public e(DexKitBridge dexKitBridge, int i, int i2) {
        dexKitBridge.getClass();
        this.a = dexKitBridge;
    }

    public static long a(int i, int i2) {
        return ((long) i2) | (((long) i) << 32);
    }
}
