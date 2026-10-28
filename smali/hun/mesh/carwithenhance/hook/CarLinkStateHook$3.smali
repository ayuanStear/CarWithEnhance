.class Lhun/mesh/carwithenhance/hook/CarLinkStateHook$3;
.super Ljava/lang/Object;
.source "r8-map-id-2844820780df513d20833d92dac6e5f4b7fbbd08283aea6743910c9549ac13d9"

# interfaces
.implements Ljava/lang/Runnable;


# annotations
.annotation system Ldalvik/annotation/EnclosingMethod;
    value = Lhun/mesh/carwithenhance/hook/CarLinkStateHook;->closeMusicPlayerIfNeeded()V
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0x1
    name = null
.end annotation


# instance fields
.field final synthetic this$0:Lhun/mesh/carwithenhance/hook/CarLinkStateHook;


# 延迟关闭音乐播放器的 Runnable：run() 回调 CarLinkStateHook.doCloseMusicPlayers()
# direct methods
.method public constructor <init>(Lhun/mesh/carwithenhance/hook/CarLinkStateHook;)V
    .locals 0

    iput-object p1, p0, Lhun/mesh/carwithenhance/hook/CarLinkStateHook$3;->this$0:Lhun/mesh/carwithenhance/hook/CarLinkStateHook;

    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    return-void
.end method


# virtual methods
.method public run()V
    .locals 1

    iget-object v0, p0, Lhun/mesh/carwithenhance/hook/CarLinkStateHook$3;->this$0:Lhun/mesh/carwithenhance/hook/CarLinkStateHook;

    invoke-static {v0}, Lhun/mesh/carwithenhance/hook/CarLinkStateHook;->c(Lhun/mesh/carwithenhance/hook/CarLinkStateHook;)V

    return-void
.end method