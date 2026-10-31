package defpackage;

import hun.mesh.carwithenhance.dexkit.GenericAutoPlayHooks;
import java.util.Comparator;

/* JADX INFO: compiled from: r8-map-id-2844820780df513d20833d92dac6e5f4b7fbbd08283aea6743910c9549ac13d9 */
/* JADX INFO: loaded from: classes.dex */
public final class a0 implements Comparator {
    public final /* synthetic */ int a;

    public /* synthetic */ a0(int i) {
        this.a = i;
    }

    @Override // java.util.Comparator
    public final int compare(Object obj, Object obj2) {
        switch (this.a) {
            case 0:
                return q.c(((n) obj).c, ((n) obj2).c);
            case GenericAutoPlayHooks.MEDIA_AUTO_PLAY /* 1 */:
                return q.c(((f0) obj).c, ((f0) obj2).c);
            default:
                return q.c(((t0) obj).c, ((t0) obj2).c);
        }
    }
}
