package defpackage;

import hun.mesh.carwithenhance.dexkit.CarWithDexKitManager;
import hun.mesh.carwithenhance.dexkit.GenericAutoPlayHooks;

/* JADX INFO: compiled from: r8-map-id-2844820780df513d20833d92dac6e5f4b7fbbd08283aea6743910c9549ac13d9 */
/* JADX INFO: loaded from: classes.dex */
public final /* synthetic */ class h implements o0 {
    public final /* synthetic */ int a;

    public /* synthetic */ h(int i) {
        this.a = i;
    }

    @Override // defpackage.o0
    public final Object b(Object obj) {
        switch (this.a) {
            case 0:
                return CarWithDexKitManager.resolveTargets$lambda$2((k0) obj);
            case GenericAutoPlayHooks.MEDIA_AUTO_PLAY /* 1 */:
                return CarWithDexKitManager.resolveTargets$lambda$27$lambda$21((k0) obj);
            case GenericAutoPlayHooks.MEDIA_APP_OPEN /* 2 */:
                return CarWithDexKitManager.resolveTargets$lambda$27$lambda$24((k0) obj);
            case GenericAutoPlayHooks.COLD_START_ANTI_KILL /* 3 */:
                return CarWithDexKitManager.resolveTargets$lambda$30((k0) obj);
            case GenericAutoPlayHooks.EXPERIENCE_TO_NORMAL /* 4 */:
                return CarWithDexKitManager.resolveTargets$lambda$34((k0) obj);
            case 5:
                return CarWithDexKitManager.resolveTargets$lambda$38((k0) obj);
            case 6:
                return CarWithDexKitManager.resolveTargets$lambda$42((k0) obj);
            case 7:
                return CarWithDexKitManager.resolveTargets$lambda$45$lambda$44((k0) obj);
            case 8:
                return CarWithDexKitManager.resolveTargets$lambda$49((k0) obj);
            case 9:
                return CarWithDexKitManager.resolveTargets$lambda$53((k0) obj);
            case 10:
                return CarWithDexKitManager.resolveTargets$lambda$6((k0) obj);
            case 11:
                return CarWithDexKitManager.resolveTargets$lambda$10((k0) obj);
            case 12:
                return CarWithDexKitManager.resolveTargets$lambda$14((k0) obj);
            default:
                return Boolean.valueOf(CarWithDexKitManager.resolveTargets$lambda$19((n) obj));
        }
    }
}
