package defpackage;

/* JADX INFO: compiled from: r8-map-id-2844820780df513d20833d92dac6e5f4b7fbbd08283aea6743910c9549ac13d9 */
/* JADX INFO: loaded from: classes.dex */
public final class m extends r0 implements o0 {
    public static final m b = new m(0);
    public static final m c = new m(1);
    public final /* synthetic */ int a;

    public /* synthetic */ m(int i) {
        this.a = i;
    }

    @Override // defpackage.o0
    public final Object b(Object obj) {
        switch (this.a) {
            case 0:
                n nVar = (n) obj;
                nVar.getClass();
                return nVar.b();
            default:
                String str = (String) obj;
                str.getClass();
                return c0.b(str);
        }
    }
}
