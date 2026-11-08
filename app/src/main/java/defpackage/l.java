package defpackage;

import hun.mesh.carwithenhance.dexkit.GenericAutoPlayHooks;
import java.util.ArrayList;
import java.util.Iterator;
import org.luckypray.dexkit.DexKitBridge;

/* JADX INFO: compiled from: r8-map-id-2844820780df513d20833d92dac6e5f4b7fbbd08283aea6743910c9549ac13d9 */
/* JADX INFO: loaded from: classes.dex */
public final class l extends r0 implements n0 {
    public final /* synthetic */ int a;
    public final /* synthetic */ e b;
    public final /* synthetic */ DexKitBridge c;
    public final /* synthetic */ int d;

    public l(n nVar, DexKitBridge dexKitBridge, int i) {
        this.a = 1;
        this.b = nVar;
        this.c = dexKitBridge;
        this.d = i;
    }

    @Override // defpackage.n0
    public final Object a() {
        int i = this.a;
        int i2 = 0;
        int i3 = this.d;
        e eVar = this.b;
        DexKitBridge dexKitBridge = this.c;
        switch (i) {
            case 0:
                ArrayList arrayList = ((n) eVar).e;
                ArrayList arrayList2 = new ArrayList(t.k(arrayList));
                Iterator it = arrayList.iterator();
                while (it.hasNext()) {
                    arrayList2.add(Long.valueOf(e.a(i3, ((Number) it.next()).intValue())));
                }
                long[] jArr = new long[arrayList2.size()];
                Iterator it2 = arrayList2.iterator();
                while (it2.hasNext()) {
                    jArr[i2] = ((Number) it2.next()).longValue();
                    i2++;
                }
                return dexKitBridge.d(jArr);
            case GenericAutoPlayHooks.MEDIA_AUTO_PLAY /* 1 */:
                Integer num = ((n) eVar).d;
                if (num == null) {
                    return null;
                }
                o oVarD = dexKitBridge.d(new long[]{e.a(i3, num.intValue())});
                return (n) (oVarD.isEmpty() ? null : oVarD.first());
            default:
                return (n) dexKitBridge.d(new long[]{e.a(i3, ((f0) eVar).d)}).first();
        }
    }

    public /* synthetic */ l(DexKitBridge dexKitBridge, e eVar, int i, int i2) {
        this.a = i2;
        this.c = dexKitBridge;
        this.b = eVar;
        this.d = i;
    }
}
