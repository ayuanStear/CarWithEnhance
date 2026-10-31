package defpackage;

import hun.mesh.carwithenhance.dexkit.GenericAutoPlayHooks;

/* JADX INFO: compiled from: r8-map-id-2844820780df513d20833d92dac6e5f4b7fbbd08283aea6743910c9549ac13d9 */
/* JADX INFO: loaded from: classes.dex */
public final class k extends r0 implements n0 {
    public final /* synthetic */ int a;
    public final /* synthetic */ Object b;

    public /* synthetic */ k(int i, Object obj) {
        this.a = i;
        this.b = obj;
    }

    @Override // defpackage.n0
    public final Object a() {
        int i = this.a;
        Object obj = this.b;
        switch (i) {
            case 0:
                return new y(((n) obj).c);
            case GenericAutoPlayHooks.MEDIA_AUTO_PLAY /* 1 */:
                return c0.b(((z) obj).c);
            case GenericAutoPlayHooks.MEDIA_APP_OPEN /* 2 */:
                b0 b0Var = (b0) obj;
                return "(" + r.m(b0Var.c, "", m.c, 30) + ")" + c0.b(b0Var.d);
            case GenericAutoPlayHooks.COLD_START_ANTI_KILL /* 3 */:
                return new z(((f0) obj).c);
            default:
                return new b0(((t0) obj).c);
        }
    }
}
