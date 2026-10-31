package defpackage;

import hun.mesh.carwithenhance.dexkit.GenericAutoPlayHooks;
import hun.mesh.carwithenhance.dexkit.QQMusicDexKitManager;

/* JADX INFO: compiled from: r8-map-id-2844820780df513d20833d92dac6e5f4b7fbbd08283aea6743910c9549ac13d9 */
/* JADX INFO: loaded from: classes.dex */
public final /* synthetic */ class a1 implements o0 {
    public final /* synthetic */ int a;
    public final /* synthetic */ String b;

    public /* synthetic */ a1(String str, int i) {
        this.a = i;
        this.b = str;
    }

    @Override // defpackage.o0
    public final Object b(Object obj) {
        int i = this.a;
        String str = this.b;
        switch (i) {
            case 0:
                return QQMusicDexKitManager.initAndResolve$lambda$26$lambda$1(str, (k0) obj);
            case GenericAutoPlayHooks.MEDIA_AUTO_PLAY /* 1 */:
                return QQMusicDexKitManager.initAndResolve$lambda$26$lambda$8(str, (k0) obj);
            case GenericAutoPlayHooks.MEDIA_APP_OPEN /* 2 */:
                return QQMusicDexKitManager.initAndResolve$lambda$26$lambda$12(str, (k0) obj);
            default:
                return QQMusicDexKitManager.initAndResolve$lambda$26$lambda$20(str, (j0) obj);
        }
    }
}
