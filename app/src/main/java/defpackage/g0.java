package defpackage;

/* JADX INFO: compiled from: r8-map-id-2844820780df513d20833d92dac6e5f4b7fbbd08283aea6743910c9549ac13d9 */
/* JADX INFO: loaded from: classes.dex */
public final class g0 extends f {
    public final /* synthetic */ int a;

    public /* synthetic */ g0(int i) {
        this.a = i;
    }

    @Override // java.util.ArrayList, java.util.AbstractCollection, java.util.Collection, java.util.List
    public final /* bridge */ boolean contains(Object obj) {
        switch (this.a) {
            case 0:
                if (obj instanceof f0) {
                    return super.contains((f0) obj);
                }
                return false;
            default:
                if (obj instanceof t0) {
                    return super.contains((t0) obj);
                }
                return false;
        }
    }

    @Override // java.util.ArrayList, java.util.AbstractList, java.util.List
    public final /* bridge */ int indexOf(Object obj) {
        switch (this.a) {
            case 0:
                if (obj instanceof f0) {
                    return super.indexOf((f0) obj);
                }
                return -1;
            default:
                if (obj instanceof t0) {
                    return super.indexOf((t0) obj);
                }
                return -1;
        }
    }

    @Override // java.util.ArrayList, java.util.AbstractList, java.util.List
    public final /* bridge */ int lastIndexOf(Object obj) {
        switch (this.a) {
            case 0:
                if (obj instanceof f0) {
                    return super.lastIndexOf((f0) obj);
                }
                return -1;
            default:
                if (obj instanceof t0) {
                    return super.lastIndexOf((t0) obj);
                }
                return -1;
        }
    }

    @Override // java.util.ArrayList, java.util.AbstractCollection, java.util.Collection, java.util.List
    public final /* bridge */ boolean remove(Object obj) {
        switch (this.a) {
            case 0:
                if (obj instanceof f0) {
                    return super.remove((f0) obj);
                }
                return false;
            default:
                if (obj instanceof t0) {
                    return super.remove((t0) obj);
                }
                return false;
        }
    }
}
