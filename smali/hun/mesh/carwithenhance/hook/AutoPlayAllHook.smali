.class public Lhun/mesh/carwithenhance/hook/AutoPlayAllHook;
.super Ljava/lang/Object;
.source "AutoPlayAllHook.java"

# interfaces
.implements Lhun/mesh/carwithenhance/hook/IHook;


# static fields
.field private static final APPS_VIEW_MODEL:Ljava/lang/String; = "com.carwith.launcher.apps.AppsViewModel"

.field private static final ATTEMPTS:Ljava/util/Map;
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "Ljava/util/Map<",
            "Ljava/lang/String;",
            "Ljava/lang/Long;",
            ">;"
        }
    .end annotation
.end field

.field private static final BROWSERS:Ljava/util/Map;
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "Ljava/util/Map<",
            "Ljava/lang/String;",
            "Landroid/media/browse/MediaBrowser;",
            ">;"
        }
    .end annotation
.end field

.field private static final BROWSE_ACTION:Ljava/lang/String; = "android.media.browse.MediaBrowserService"

.field private static final CANNOT_WAKE:Ljava/util/Set;
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "Ljava/util/Set<",
            "Ljava/lang/String;",
            ">;"
        }
    .end annotation
.end field

.field private static final CARWITH_APP_MGR:Ljava/lang/String; = "com.carwith.common.utils.c0"

.field private static final CAR_OPEN_AT:Ljava/util/Map;
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "Ljava/util/Map<",
            "Ljava/lang/String;",
            "Ljava/lang/Long;",
            ">;"
        }
    .end annotation
.end field

.field private static final EXPERIENCE_PREF:Ljava/lang/String; = "ucar_experience_app_added_category"

.field private static final FALLBACK:[Ljava/lang/String;

.field private static final FPS_HELPER:Ljava/lang/String; = "com.carwith.common.utils.s2"

.field private static final FPS_HELPER_NEW:Ljava/lang/String; = "com.carwith.common.utils.d"

.field private static final FPS_INDEX_50:I = 0x3

.field private static final FPS_INDEX_60:I = 0x4

.field private static final FPS_LABEL_CLASS:Ljava/lang/String; = "p6.o0"

.field private static final FPS_PREF_FILE:Ljava/lang/String; = "spUtils"

.field private static final FPS_PREF_KEY:Ljava/lang/String; = "user_setting_framerate"

.field private static final GSON_CLASS:Ljava/lang/String; = "com.google.gson.Gson"

.field private static final KEY_CARWITH_AUTOPLAY:Ljava/lang/String; = "prefer_auto_play"

.field private static final KEY_COLDSTART_ANTIKILL:Ljava/lang/String; = "hook_coldstart_antikill_enabled"

.field private static final KEY_EXPERIENCE_TO_NORMAL:Ljava/lang/String; = "hook_experience_to_normal_enabled"

.field private static final KEY_EXP_FLAGGED:Ljava/lang/String; = "carwithenhance_exp_flagged"

.field private static final KEY_KILL_IMMEDIATELY:Ljava/lang/String; = "hook_disconnect_kill_immediately_enabled"

.field private static final KEY_PAUSE_BEFORE_DISCONNECT:Ljava/lang/String; = "hook_pause_before_disconnect_enabled"

.field private static final KEY_SKIP_USER_KILLED:Ljava/lang/String; = "hook_skip_user_killed_enabled"

.field private static final KILL_GRACE_MAX_WAIT_MS:J = 0x4b0L

.field private static final KILL_MIN_SETTLE_MS:J = 0x320L

.field private static final KILL_SESSION_WAIT_MS:J = 0x640L

.field private static final KNOWN_SERVICES:Ljava/util/Map;
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "Ljava/util/Map<",
            "Ljava/lang/String;",
            "Ljava/lang/String;",
            ">;"
        }
    .end annotation
.end field

.field private static final LOG_TAG:Ljava/lang/String; = "carwithenhance"

.field private static final MEDIA_PLAY_HELPER:Ljava/lang/String; = "ja.a"

.field private static final NEVER_KILL:[Ljava/lang/String;

.field private static final OWN_KILL_AT:Ljava/util/Map;
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "Ljava/util/Map<",
            "Ljava/lang/String;",
            "Ljava/lang/Long;",
            ">;"
        }
    .end annotation
.end field

.field private static final PAUSE_MAX_WAIT_MS:J = 0x640L

.field private static final PAUSE_POLL_MS:J = 0x64L

.field private static final PLAYBACK_SERVICES:Ljava/util/Map;
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "Ljava/util/Map<",
            "Ljava/lang/String;",
            "Ljava/lang/String;",
            ">;"
        }
    .end annotation
.end field

.field private static final PREFS:Ljava/lang/String; = "carwithenhance_hooksettings"

.field private static final PREF_MEDIA_APP:Ljava/lang/String; = "file_prefer_app_091703"

.field private static final QQ_MUSIC:Ljava/lang/String; = "com.tencent.qqmusic"

.field private static final RETRY_DELAYS:[J

.field private static final SESSION_MEDIA:Ljava/util/Set;
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "Ljava/util/Set<",
            "Ljava/lang/String;",
            ">;"
        }
    .end annotation
.end field

.field private static final SRC_CARD:I = 0x2

.field private static final SRC_UNKNOWN:I = 0x3

.field private static final SRC_USER:I = 0x1

.field private static final STOP_MAX_WAIT_MS:J = 0x640L

.field private static final SWITCH_DROPPED:Ljava/util/Map;
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "Ljava/util/Map<",
            "Ljava/lang/String;",
            "Ljava/lang/Long;",
            ">;"
        }
    .end annotation
.end field

.field private static final SWITCH_DROP_TTL_MS:J = 0x3a980L

.field private static final TAG:Ljava/lang/String; = ">> [\u5168\u5e94\u7528\u7eed\u64ad] "

.field private static final USER_KILLED:Ljava/util/Set;
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "Ljava/util/Set<",
            "Ljava/lang/String;",
            ">;"
        }
    .end annotation
.end field

.field private static final WOKE:Ljava/util/Set;
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "Ljava/util/Set<",
            "Ljava/lang/String;",
            ">;"
        }
    .end annotation
.end field

.field private static sActiveWokePkg:Ljava/lang/String;

.field private static sAppCtx:Landroid/content/Context;

.field private static volatile sAppsVm:Ljava/lang/Object;

.field private static volatile sCarConnected:Z

.field private static sCl:Ljava/lang/ClassLoader;

.field private static volatile sConnectGen:J

.field private static sCurrentMediaPkg:Ljava/lang/String;

.field private static sHandler:Landroid/os/Handler;

.field private static sInitialResumeDone:Z

.field private static sLastAcceptedPkg:Ljava/lang/String;

.field private static sLastPkg:Ljava/lang/String;

.field private static sLastResumeAt:J

.field private static sLastResumePkg:Ljava/lang/String;

.field private static volatile sLastSelectAt:J

.field private static volatile sLastSelectPkg:Ljava/lang/String;

.field private static volatile sLastStateAt:J

.field private static volatile sLastStatePkg:Ljava/lang/String;

.field private static sLastWake:J

.field private static sOwnPrefListener:Landroid/content/SharedPreferences$OnSharedPreferenceChangeListener;

.field private static sPrefListener:Landroid/content/SharedPreferences$OnSharedPreferenceChangeListener;

.field private static volatile sResumeInFlight:Z

.field private static volatile sResumedPkg:Ljava/lang/String;

.field private static volatile sRetryGen:J

.field private static volatile sSelectedApp:Ljava/lang/String;

.field private static sSessionListener:Landroid/media/session/MediaSessionManager$OnActiveSessionsChangedListener;

.field private static sSwitchExec:Ljava/util/concurrent/ExecutorService;

.field private static volatile sWaitStartAt:J

.field private static volatile sWaitingPkg:Ljava/lang/String;


# direct methods
.method static constructor <clinit>()V
    .locals 7

    .line 141
    const-string v0, "com.netease.cloudmusic"

    const-string v1, "com.luna.music"

    const-string v2, "com.kugou.android"

    const-string v3, "cmccwm.mobilemusic"

    const-string v4, "cn.kuwo.player"

    const-string v5, "com.miui.player"

    filled-new-array/range {v0 .. v5}, [Ljava/lang/String;

    move-result-object v0

    sput-object v0, Lhun/mesh/carwithenhance/hook/AutoPlayAllHook;->FALLBACK:[Ljava/lang/String;

    .line 153
    new-instance v0, Ljava/util/concurrent/ConcurrentHashMap;

    invoke-direct {v0}, Ljava/util/concurrent/ConcurrentHashMap;-><init>()V

    sput-object v0, Lhun/mesh/carwithenhance/hook/AutoPlayAllHook;->BROWSERS:Ljava/util/Map;

    .line 154
    new-instance v0, Ljava/util/concurrent/ConcurrentHashMap;

    invoke-direct {v0}, Ljava/util/concurrent/ConcurrentHashMap;-><init>()V

    sput-object v0, Lhun/mesh/carwithenhance/hook/AutoPlayAllHook;->ATTEMPTS:Ljava/util/Map;

    .line 156
    new-instance v0, Ljava/util/concurrent/ConcurrentHashMap;

    invoke-direct {v0}, Ljava/util/concurrent/ConcurrentHashMap;-><init>()V

    sput-object v0, Lhun/mesh/carwithenhance/hook/AutoPlayAllHook;->CAR_OPEN_AT:Ljava/util/Map;

    .line 162
    invoke-static {}, Ljava/util/concurrent/ConcurrentHashMap;->newKeySet()Ljava/util/concurrent/ConcurrentHashMap$KeySetView;

    move-result-object v0

    sput-object v0, Lhun/mesh/carwithenhance/hook/AutoPlayAllHook;->USER_KILLED:Ljava/util/Set;

    .line 167
    new-instance v0, Ljava/util/concurrent/ConcurrentHashMap;

    invoke-direct {v0}, Ljava/util/concurrent/ConcurrentHashMap;-><init>()V

    sput-object v0, Lhun/mesh/carwithenhance/hook/AutoPlayAllHook;->SWITCH_DROPPED:Ljava/util/Map;

    .line 171
    new-instance v0, Ljava/util/concurrent/ConcurrentHashMap;

    invoke-direct {v0}, Ljava/util/concurrent/ConcurrentHashMap;-><init>()V

    sput-object v0, Lhun/mesh/carwithenhance/hook/AutoPlayAllHook;->OWN_KILL_AT:Ljava/util/Map;

    .line 177
    invoke-static {}, Ljava/util/concurrent/ConcurrentHashMap;->newKeySet()Ljava/util/concurrent/ConcurrentHashMap$KeySetView;

    move-result-object v0

    sput-object v0, Lhun/mesh/carwithenhance/hook/AutoPlayAllHook;->WOKE:Ljava/util/Set;

    .line 187
    invoke-static {}, Ljava/util/concurrent/ConcurrentHashMap;->newKeySet()Ljava/util/concurrent/ConcurrentHashMap$KeySetView;

    move-result-object v0

    sput-object v0, Lhun/mesh/carwithenhance/hook/AutoPlayAllHook;->SESSION_MEDIA:Ljava/util/Set;

    .line 192
    invoke-static {}, Ljava/util/concurrent/ConcurrentHashMap;->newKeySet()Ljava/util/concurrent/ConcurrentHashMap$KeySetView;

    move-result-object v0

    sput-object v0, Lhun/mesh/carwithenhance/hook/AutoPlayAllHook;->CANNOT_WAKE:Ljava/util/Set;

    .line 199
    const/16 v0, 0xf

    new-array v0, v0, [J

    fill-array-data v0, :array_0

    sput-object v0, Lhun/mesh/carwithenhance/hook/AutoPlayAllHook;->RETRY_DELAYS:[J

    .line 204
    new-instance v0, Ljava/util/HashMap;

    invoke-direct {v0}, Ljava/util/HashMap;-><init>()V

    sput-object v0, Lhun/mesh/carwithenhance/hook/AutoPlayAllHook;->PLAYBACK_SERVICES:Ljava/util/Map;

    .line 206
    const-string v1, "com.autonavi.minimap"

    const-string v2, "com.baidu.BaiduMap"

    const-string v3, "com.tencent.map"

    const-string v4, "com.google.android.apps.maps"

    const-string v5, "com.miui.carlink"

    const-string v6, "hun.mesh.carwithenhance"

    filled-new-array/range {v1 .. v6}, [Ljava/lang/String;

    move-result-object v1

    sput-object v1, Lhun/mesh/carwithenhance/hook/AutoPlayAllHook;->NEVER_KILL:[Ljava/lang/String;

    .line 229
    new-instance v1, Ljava/util/HashMap;

    invoke-direct {v1}, Ljava/util/HashMap;-><init>()V

    sput-object v1, Lhun/mesh/carwithenhance/hook/AutoPlayAllHook;->KNOWN_SERVICES:Ljava/util/Map;

    .line 232
    const-string v2, "com.luna.music"

    const-string v3, "com.luna.biz.playing.player.PlayerService"

    invoke-interface {v1, v2, v3}, Ljava/util/Map;->put(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;

    .line 233
    const-string v4, "com.netease.cloudmusic"

    const-string v5, "com.netease.cloudmusic.module.ucar.UCarService"

    invoke-interface {v1, v4, v5}, Ljava/util/Map;->put(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;

    .line 234
    const-string v4, "com.baidu.netdisk"

    const-string v5, "com.baidu.netdisk.service.NetdiskMediaBrowserService"

    invoke-interface {v1, v4, v5}, Ljava/util/Map;->put(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;

    .line 236
    const-string v1, "com.miui.player"

    const-string v4, "com.miui.player.service.MediaPlaybackService"

    invoke-interface {v0, v1, v4}, Ljava/util/Map;->put(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;

    .line 237
    invoke-interface {v0, v2, v3}, Ljava/util/Map;->put(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;

    .line 238
    return-void

    :array_0
    .array-data 8
        0x190
        0x320
        0x4b0
        0x708
        0x9c4
        0xc80
        0xfa0
        0x1388
        0x1770
        0x1f40
        0x2710
        0x32c8
        0x3e80
        0x4e20
        0x5dc0
    .end array-data
.end method

.method public constructor <init>()V
    .locals 0

    .line 54
    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    return-void
.end method

.method static synthetic access$000()Z
    .locals 1

    .line 54
    sget-boolean v0, Lhun/mesh/carwithenhance/hook/AutoPlayAllHook;->sResumeInFlight:Z

    return v0
.end method

.method static synthetic access$100()Landroid/content/Context;
    .locals 1

    .line 54
    sget-object v0, Lhun/mesh/carwithenhance/hook/AutoPlayAllHook;->sAppCtx:Landroid/content/Context;

    return-object v0
.end method

.method static synthetic access$1000(Ljava/lang/String;)Z
    .locals 0

    .line 54
    invoke-static {p0}, Lhun/mesh/carwithenhance/hook/AutoPlayAllHook;->blockedBySwitchDrop(Ljava/lang/String;)Z

    move-result p0

    return p0
.end method

.method static synthetic access$1100(Landroid/content/Context;Ljava/lang/String;)Z
    .locals 0

    .line 54
    invoke-static {p0, p1}, Lhun/mesh/carwithenhance/hook/AutoPlayAllHook;->blockedByUserKill(Landroid/content/Context;Ljava/lang/String;)Z

    move-result p0

    return p0
.end method

.method static synthetic access$1200()Ljava/lang/String;
    .locals 1

    .line 54
    sget-object v0, Lhun/mesh/carwithenhance/hook/AutoPlayAllHook;->sWaitingPkg:Ljava/lang/String;

    return-object v0
.end method

.method static synthetic access$1202(Ljava/lang/String;)Ljava/lang/String;
    .locals 0

    .line 54
    sput-object p0, Lhun/mesh/carwithenhance/hook/AutoPlayAllHook;->sWaitingPkg:Ljava/lang/String;

    return-object p0
.end method

.method static synthetic access$1300()J
    .locals 2

    .line 54
    sget-wide v0, Lhun/mesh/carwithenhance/hook/AutoPlayAllHook;->sRetryGen:J

    return-wide v0
.end method

.method static synthetic access$1308()J
    .locals 4

    .line 54
    sget-wide v0, Lhun/mesh/carwithenhance/hook/AutoPlayAllHook;->sRetryGen:J

    const-wide/16 v2, 0x1

    add-long/2addr v2, v0

    sput-wide v2, Lhun/mesh/carwithenhance/hook/AutoPlayAllHook;->sRetryGen:J

    return-wide v0
.end method

.method static synthetic access$1400()Ljava/lang/String;
    .locals 1

    .line 54
    sget-object v0, Lhun/mesh/carwithenhance/hook/AutoPlayAllHook;->sLastResumePkg:Ljava/lang/String;

    return-object v0
.end method

.method static synthetic access$1500()J
    .locals 2

    .line 54
    sget-wide v0, Lhun/mesh/carwithenhance/hook/AutoPlayAllHook;->sLastResumeAt:J

    return-wide v0
.end method

.method static synthetic access$1600(Ljava/lang/ClassLoader;Ljava/lang/String;)V
    .locals 0

    .line 54
    invoke-static {p0, p1}, Lhun/mesh/carwithenhance/hook/AutoPlayAllHook;->resumeCarWith(Ljava/lang/ClassLoader;Ljava/lang/String;)V

    return-void
.end method

.method static synthetic access$1700()I
    .locals 1

    .line 54
    invoke-static {}, Lhun/mesh/carwithenhance/hook/AutoPlayAllHook;->callerSource()I

    move-result v0

    return v0
.end method

.method static synthetic access$1800(I)Ljava/lang/String;
    .locals 0

    .line 54
    invoke-static {p0}, Lhun/mesh/carwithenhance/hook/AutoPlayAllHook;->sourceName(I)Ljava/lang/String;

    move-result-object p0

    return-object p0
.end method

.method static synthetic access$1900(Ljava/lang/String;)V
    .locals 0

    .line 54
    invoke-static {p0}, Lhun/mesh/carwithenhance/hook/AutoPlayAllHook;->markSessionMedia(Ljava/lang/String;)V

    return-void
.end method

.method static synthetic access$200(Ljava/lang/String;)Ljava/lang/String;
    .locals 0

    .line 54
    invoke-static {p0}, Lhun/mesh/carwithenhance/hook/AutoPlayAllHook;->insertFiftyFpsItem(Ljava/lang/String;)Ljava/lang/String;

    move-result-object p0

    return-object p0
.end method

.method static synthetic access$2000(Ljava/lang/String;I)V
    .locals 0

    .line 54
    invoke-static {p0, p1}, Lhun/mesh/carwithenhance/hook/AutoPlayAllHook;->onMediaAppEvent(Ljava/lang/String;I)V

    return-void
.end method

.method static synthetic access$2100(Ljava/lang/String;Ljava/lang/String;)V
    .locals 0

    .line 54
    invoke-static {p0, p1}, Lhun/mesh/carwithenhance/hook/AutoPlayAllHook;->preWakeForCarOpen(Ljava/lang/String;Ljava/lang/String;)V

    return-void
.end method

.method static synthetic access$2200(Landroid/content/Context;)Z
    .locals 0

    .line 54
    invoke-static {p0}, Lhun/mesh/carwithenhance/hook/AutoPlayAllHook;->isEnabled(Landroid/content/Context;)Z

    move-result p0

    return p0
.end method

.method static synthetic access$2300(Ljava/lang/String;)Z
    .locals 0

    .line 54
    invoke-static {p0}, Lhun/mesh/carwithenhance/hook/AutoPlayAllHook;->isNowDesired(Ljava/lang/String;)Z

    move-result p0

    return p0
.end method

.method static synthetic access$2400(Ljava/lang/ClassLoader;Ljava/lang/String;)Z
    .locals 0

    .line 54
    invoke-static {p0, p1}, Lhun/mesh/carwithenhance/hook/AutoPlayAllHook;->isTarget(Ljava/lang/ClassLoader;Ljava/lang/String;)Z

    move-result p0

    return p0
.end method

.method static synthetic access$2500(Ljava/lang/String;)Z
    .locals 0

    .line 54
    invoke-static {p0}, Lhun/mesh/carwithenhance/hook/AutoPlayAllHook;->recentlyOpenedOnCar(Ljava/lang/String;)Z

    move-result p0

    return p0
.end method

.method static synthetic access$2600(Landroid/content/Context;Ljava/lang/String;)V
    .locals 0

    .line 54
    invoke-static {p0, p1}, Lhun/mesh/carwithenhance/hook/AutoPlayAllHook;->requestModuleBind(Landroid/content/Context;Ljava/lang/String;)V

    return-void
.end method

.method static synthetic access$2700(Landroid/content/Context;Ljava/lang/String;)V
    .locals 0

    .line 54
    invoke-static {p0, p1}, Lhun/mesh/carwithenhance/hook/AutoPlayAllHook;->doSwitch(Landroid/content/Context;Ljava/lang/String;)V

    return-void
.end method

.method static synthetic access$2800(Landroid/content/Context;Ljava/lang/String;)V
    .locals 0

    .line 54
    invoke-static {p0, p1}, Lhun/mesh/carwithenhance/hook/AutoPlayAllHook;->logAudioState(Landroid/content/Context;Ljava/lang/String;)V

    return-void
.end method

.method static synthetic access$2900(Ljava/lang/String;Ljava/lang/ClassLoader;)V
    .locals 0

    .line 54
    invoke-static {p0, p1}, Lhun/mesh/carwithenhance/hook/AutoPlayAllHook;->dumpLyric(Ljava/lang/String;Ljava/lang/ClassLoader;)V

    return-void
.end method

.method static synthetic access$300(Landroid/content/Context;Ljava/lang/ClassLoader;)Ljava/lang/String;
    .locals 0

    .line 54
    invoke-static {p0, p1}, Lhun/mesh/carwithenhance/hook/AutoPlayAllHook;->fiftyFpsLabel(Landroid/content/Context;Ljava/lang/ClassLoader;)Ljava/lang/String;

    move-result-object p0

    return-object p0
.end method

.method static synthetic access$3000(Ljava/lang/ClassLoader;)Ljava/util/List;
    .locals 0

    .line 54
    invoke-static {p0}, Lhun/mesh/carwithenhance/hook/AutoPlayAllHook;->experiencePackages(Ljava/lang/ClassLoader;)Ljava/util/List;

    move-result-object p0

    return-object p0
.end method

.method static synthetic access$3100(Landroid/content/Context;Ljava/lang/ClassLoader;)V
    .locals 0

    .line 54
    invoke-static {p0, p1}, Lhun/mesh/carwithenhance/hook/AutoPlayAllHook;->dumpAppList(Landroid/content/Context;Ljava/lang/ClassLoader;)V

    return-void
.end method

.method static synthetic access$3200(Ljava/lang/Object;I)Ljava/lang/String;
    .locals 0

    .line 54
    invoke-static {p0, p1}, Lhun/mesh/carwithenhance/hook/AutoPlayAllHook;->findFpsOptions(Ljava/lang/Object;I)Ljava/lang/String;

    move-result-object p0

    return-object p0
.end method

.method static synthetic access$3300()Z
    .locals 1

    .line 54
    invoke-static {}, Lhun/mesh/carwithenhance/hook/AutoPlayAllHook;->readPrefAutoPlay()Z

    move-result v0

    return v0
.end method

.method static synthetic access$3400(Landroid/content/Context;Ljava/lang/String;)Z
    .locals 0

    .line 54
    invoke-static {p0, p1}, Lhun/mesh/carwithenhance/hook/AutoPlayAllHook;->startPlayerService(Landroid/content/Context;Ljava/lang/String;)Z

    move-result p0

    return p0
.end method

.method static synthetic access$3500(Landroid/content/Context;Ljava/lang/String;)Z
    .locals 0

    .line 54
    invoke-static {p0, p1}, Lhun/mesh/carwithenhance/hook/AutoPlayAllHook;->playViaSession(Landroid/content/Context;Ljava/lang/String;)Z

    move-result p0

    return p0
.end method

.method static synthetic access$3600()Ljava/util/Set;
    .locals 1

    .line 54
    sget-object v0, Lhun/mesh/carwithenhance/hook/AutoPlayAllHook;->WOKE:Ljava/util/Set;

    return-object v0
.end method

.method static synthetic access$3700(Landroid/content/Context;Ljava/lang/String;Ljava/lang/String;)V
    .locals 0

    .line 54
    invoke-static {p0, p1, p2}, Lhun/mesh/carwithenhance/hook/AutoPlayAllHook;->releaseWoke(Landroid/content/Context;Ljava/lang/String;Ljava/lang/String;)V

    return-void
.end method

.method static synthetic access$3800(Landroid/content/Context;Ljava/lang/String;Ljava/lang/String;)V
    .locals 0

    .line 54
    invoke-static {p0, p1, p2}, Lhun/mesh/carwithenhance/hook/AutoPlayAllHook;->forceStop(Landroid/content/Context;Ljava/lang/String;Ljava/lang/String;)V

    return-void
.end method

.method static synthetic access$3902(J)J
    .locals 0

    .line 54
    sput-wide p0, Lhun/mesh/carwithenhance/hook/AutoPlayAllHook;->sWaitStartAt:J

    return-wide p0
.end method

.method static synthetic access$400()I
    .locals 1

    .line 54
    invoke-static {}, Lhun/mesh/carwithenhance/hook/AutoPlayAllHook;->readFpsIndex()I

    move-result v0

    return v0
.end method

.method static synthetic access$4000()Landroid/os/Handler;
    .locals 1

    .line 54
    sget-object v0, Lhun/mesh/carwithenhance/hook/AutoPlayAllHook;->sHandler:Landroid/os/Handler;

    return-object v0
.end method

.method static synthetic access$4002(Landroid/os/Handler;)Landroid/os/Handler;
    .locals 0

    .line 54
    sput-object p0, Lhun/mesh/carwithenhance/hook/AutoPlayAllHook;->sHandler:Landroid/os/Handler;

    return-object p0
.end method

.method static synthetic access$4100(Landroid/content/Context;Ljava/lang/String;)V
    .locals 0

    .line 54
    invoke-static {p0, p1}, Lhun/mesh/carwithenhance/hook/AutoPlayAllHook;->wakeInBackground(Landroid/content/Context;Ljava/lang/String;)V

    return-void
.end method

.method static synthetic access$4200(Ljava/lang/ClassLoader;Ljava/lang/String;)V
    .locals 0

    .line 54
    invoke-static {p0, p1}, Lhun/mesh/carwithenhance/hook/AutoPlayAllHook;->startRetryLoop(Ljava/lang/ClassLoader;Ljava/lang/String;)V

    return-void
.end method

.method static synthetic access$4300()Ljava/util/Map;
    .locals 1

    .line 54
    sget-object v0, Lhun/mesh/carwithenhance/hook/AutoPlayAllHook;->BROWSERS:Ljava/util/Map;

    return-object v0
.end method

.method static synthetic access$4400(Landroid/content/Context;Ljava/lang/String;)Z
    .locals 0

    .line 54
    invoke-static {p0, p1}, Lhun/mesh/carwithenhance/hook/AutoPlayAllHook;->isPlaying(Landroid/content/Context;Ljava/lang/String;)Z

    move-result p0

    return p0
.end method

.method static synthetic access$4500(Landroid/content/Context;Ljava/lang/String;)Z
    .locals 0

    .line 54
    invoke-static {p0, p1}, Lhun/mesh/carwithenhance/hook/AutoPlayAllHook;->isProcessRunning(Landroid/content/Context;Ljava/lang/String;)Z

    move-result p0

    return p0
.end method

.method static synthetic access$4600(Landroid/content/Context;Ljava/lang/String;)Z
    .locals 0

    .line 54
    invoke-static {p0, p1}, Lhun/mesh/carwithenhance/hook/AutoPlayAllHook;->hasSession(Landroid/content/Context;Ljava/lang/String;)Z

    move-result p0

    return p0
.end method

.method static synthetic access$4700()Ljava/util/Set;
    .locals 1

    .line 54
    sget-object v0, Lhun/mesh/carwithenhance/hook/AutoPlayAllHook;->CANNOT_WAKE:Ljava/util/Set;

    return-object v0
.end method

.method static synthetic access$502(Ljava/lang/Object;)Ljava/lang/Object;
    .locals 0

    .line 54
    sput-object p0, Lhun/mesh/carwithenhance/hook/AutoPlayAllHook;->sAppsVm:Ljava/lang/Object;

    return-object p0
.end method

.method static synthetic access$600(Landroid/content/Context;Ljava/lang/String;Z)Z
    .locals 0

    .line 54
    invoke-static {p0, p1, p2}, Lhun/mesh/carwithenhance/hook/AutoPlayAllHook;->isPrefEnabled(Landroid/content/Context;Ljava/lang/String;Z)Z

    move-result p0

    return p0
.end method

.method static synthetic access$700(Landroid/content/Context;Ljava/lang/ClassLoader;Z)I
    .locals 0

    .line 54
    invoke-static {p0, p1, p2}, Lhun/mesh/carwithenhance/hook/AutoPlayAllHook;->applyExperienceToNormal(Landroid/content/Context;Ljava/lang/ClassLoader;Z)I

    move-result p0

    return p0
.end method

.method static synthetic access$800()V
    .locals 0

    .line 54
    invoke-static {}, Lhun/mesh/carwithenhance/hook/AutoPlayAllHook;->reloadAppList()V

    return-void
.end method

.method static synthetic access$900()Ljava/lang/String;
    .locals 1

    .line 54
    sget-object v0, Lhun/mesh/carwithenhance/hook/AutoPlayAllHook;->sResumedPkg:Ljava/lang/String;

    return-object v0
.end method

.method static synthetic access$902(Ljava/lang/String;)Ljava/lang/String;
    .locals 0

    .line 54
    sput-object p0, Lhun/mesh/carwithenhance/hook/AutoPlayAllHook;->sResumedPkg:Ljava/lang/String;

    return-object p0
.end method

.method private static activeSessionPackages(Landroid/content/Context;)Ljava/util/Set;
    .locals 2
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "(",
            "Landroid/content/Context;",
            ")",
            "Ljava/util/Set<",
            "Ljava/lang/String;",
            ">;"
        }
    .end annotation

    .line 1852
    new-instance v0, Ljava/util/LinkedHashSet;

    invoke-direct {v0}, Ljava/util/LinkedHashSet;-><init>()V

    .line 1854
    :try_start_0
    const-string v1, "media_session"

    .line 1855
    invoke-virtual {p0, v1}, Landroid/content/Context;->getSystemService(Ljava/lang/String;)Ljava/lang/Object;

    move-result-object p0

    check-cast p0, Landroid/media/session/MediaSessionManager;

    .line 1856
    if-nez p0, :cond_0

    .line 1857
    return-object v0

    .line 1859
    :cond_0
    const/4 v1, 0x0

    invoke-virtual {p0, v1}, Landroid/media/session/MediaSessionManager;->getActiveSessions(Landroid/content/ComponentName;)Ljava/util/List;

    move-result-object p0

    .line 1860
    if-nez p0, :cond_1

    .line 1861
    return-object v0

    .line 1863
    :cond_1
    invoke-interface {p0}, Ljava/util/List;->iterator()Ljava/util/Iterator;

    move-result-object p0

    :goto_0
    invoke-interface {p0}, Ljava/util/Iterator;->hasNext()Z

    move-result v1

    if-eqz v1, :cond_3

    invoke-interface {p0}, Ljava/util/Iterator;->next()Ljava/lang/Object;

    move-result-object v1

    check-cast v1, Landroid/media/session/MediaController;

    .line 1864
    if-eqz v1, :cond_2

    .line 1865
    invoke-virtual {v1}, Landroid/media/session/MediaController;->getPackageName()Ljava/lang/String;

    move-result-object v1

    invoke-interface {v0, v1}, Ljava/util/Set;->add(Ljava/lang/Object;)Z
    :try_end_0
    .catchall {:try_start_0 .. :try_end_0} :catchall_0

    .line 1867
    :cond_2
    goto :goto_0

    .line 1869
    :cond_3
    goto :goto_1

    .line 1868
    :catchall_0
    move-exception p0

    .line 1870
    :goto_1
    return-object v0
.end method

.method private static addAllStrings(Ljava/util/Set;Ljava/lang/Object;)V
    .locals 2
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "(",
            "Ljava/util/Set<",
            "Ljava/lang/String;",
            ">;",
            "Ljava/lang/Object;",
            ")V"
        }
    .end annotation

    .line 697
    instance-of v0, p1, Ljava/util/List;

    if-eqz v0, :cond_3

    if-nez p0, :cond_0

    goto :goto_1

    .line 700
    :cond_0
    check-cast p1, Ljava/util/List;

    invoke-interface {p1}, Ljava/util/List;->iterator()Ljava/util/Iterator;

    move-result-object p1

    :goto_0
    invoke-interface {p1}, Ljava/util/Iterator;->hasNext()Z

    move-result v0

    if-eqz v0, :cond_2

    invoke-interface {p1}, Ljava/util/Iterator;->next()Ljava/lang/Object;

    move-result-object v0

    .line 701
    instance-of v1, v0, Ljava/lang/String;

    if-eqz v1, :cond_1

    check-cast v0, Ljava/lang/String;

    invoke-virtual {v0}, Ljava/lang/String;->length()I

    move-result v1

    if-lez v1, :cond_1

    .line 702
    invoke-interface {p0, v0}, Ljava/util/Set;->add(Ljava/lang/Object;)Z

    .line 704
    :cond_1
    goto :goto_0

    .line 705
    :cond_2
    return-void

    .line 698
    :cond_3
    :goto_1
    return-void
.end method

.method private static applyExperienceToNormal(Landroid/content/Context;Ljava/lang/ClassLoader;Z)I
    .locals 12

    .line 714
    const-string v0, "carwithenhance_exp_flagged"

    const/4 v1, 0x0

    if-eqz p0, :cond_d

    if-nez p1, :cond_0

    goto/16 :goto_2

    .line 718
    :cond_0
    :try_start_0
    invoke-static {p1}, Lhun/mesh/carwithenhance/hook/AutoPlayAllHook;->experiencePackages(Ljava/lang/ClassLoader;)Ljava/util/List;

    move-result-object p1

    .line 719
    invoke-interface {p1}, Ljava/util/List;->isEmpty()Z

    move-result v2

    if-eqz v2, :cond_1

    .line 720
    return v1

    .line 722
    :cond_1
    const-string v2, "ucar_experience_app_added_category"

    invoke-virtual {p0, v2, v1}, Landroid/content/Context;->getSharedPreferences(Ljava/lang/String;I)Landroid/content/SharedPreferences;

    move-result-object v2

    .line 723
    const-string v3, "carwithenhance_hooksettings"

    invoke-virtual {p0, v3, v1}, Landroid/content/Context;->getSharedPreferences(Ljava/lang/String;I)Landroid/content/SharedPreferences;

    move-result-object v3

    .line 724
    new-instance v4, Ljava/util/HashSet;

    new-instance v5, Ljava/util/HashSet;

    invoke-direct {v5}, Ljava/util/HashSet;-><init>()V

    .line 725
    invoke-interface {v3, v0, v5}, Landroid/content/SharedPreferences;->getStringSet(Ljava/lang/String;Ljava/util/Set;)Ljava/util/Set;

    move-result-object v5

    invoke-direct {v4, v5}, Ljava/util/HashSet;-><init>(Ljava/util/Collection;)V

    .line 726
    nop

    .line 727
    nop

    .line 728
    nop

    .line 729
    invoke-interface {p1}, Ljava/util/List;->iterator()Ljava/util/Iterator;

    move-result-object v5

    const/4 v6, 0x0

    move v7, v1

    move v8, v7

    :cond_2
    :goto_0
    invoke-interface {v5}, Ljava/util/Iterator;->hasNext()Z

    move-result v9

    const/4 v10, 0x1

    if-eqz v9, :cond_8

    invoke-interface {v5}, Ljava/util/Iterator;->next()Ljava/lang/Object;

    move-result-object v9

    check-cast v9, Ljava/lang/String;

    .line 730
    if-eqz p2, :cond_4

    .line 731
    invoke-interface {v2, v9, v1}, Landroid/content/SharedPreferences;->getBoolean(Ljava/lang/String;Z)Z

    move-result v11

    if-eqz v11, :cond_3

    .line 733
    invoke-interface {v4, v9}, Ljava/util/Set;->add(Ljava/lang/Object;)Z

    move-result v9

    if-eqz v9, :cond_2

    .line 734
    move v7, v10

    goto :goto_0

    .line 738
    :cond_3
    invoke-interface {v4, v9}, Ljava/util/Set;->contains(Ljava/lang/Object;)Z

    move-result v11

    if-eqz v11, :cond_5

    .line 740
    goto :goto_0

    .line 742
    :cond_4
    invoke-interface {v2, v9, v1}, Landroid/content/SharedPreferences;->getBoolean(Ljava/lang/String;Z)Z

    move-result v11

    if-nez v11, :cond_5

    .line 743
    goto :goto_0

    .line 745
    :cond_5
    if-nez v6, :cond_6

    .line 746
    invoke-interface {v2}, Landroid/content/SharedPreferences;->edit()Landroid/content/SharedPreferences$Editor;

    move-result-object v6

    .line 748
    :cond_6
    invoke-interface {v6, v9, p2}, Landroid/content/SharedPreferences$Editor;->putBoolean(Ljava/lang/String;Z)Landroid/content/SharedPreferences$Editor;

    .line 749
    if-eqz p2, :cond_7

    invoke-interface {v4, v9}, Ljava/util/Set;->add(Ljava/lang/Object;)Z

    move-result v9

    if-eqz v9, :cond_7

    .line 750
    move v7, v10

    .line 752
    :cond_7
    add-int/lit8 v8, v8, 0x1

    .line 753
    goto :goto_0

    .line 755
    :cond_8
    if-nez p2, :cond_9

    invoke-interface {v4}, Ljava/util/Set;->isEmpty()Z

    move-result v2

    if-nez v2, :cond_9

    .line 756
    invoke-interface {v4}, Ljava/util/Set;->clear()V

    .line 757
    move v7, v10

    .line 759
    :cond_9
    if-eqz v7, :cond_a

    .line 760
    invoke-interface {v3}, Landroid/content/SharedPreferences;->edit()Landroid/content/SharedPreferences$Editor;

    move-result-object v2

    new-instance v3, Ljava/util/HashSet;

    invoke-direct {v3, v4}, Ljava/util/HashSet;-><init>(Ljava/util/Collection;)V

    invoke-interface {v2, v0, v3}, Landroid/content/SharedPreferences$Editor;->putStringSet(Ljava/lang/String;Ljava/util/Set;)Landroid/content/SharedPreferences$Editor;

    move-result-object v0

    .line 761
    invoke-interface {v0}, Landroid/content/SharedPreferences$Editor;->apply()V

    .line 763
    :cond_a
    if-eqz v6, :cond_c

    .line 764
    invoke-interface {v6}, Landroid/content/SharedPreferences$Editor;->apply()V

    .line 765
    new-instance v0, Ljava/lang/StringBuilder;

    invoke-direct {v0}, Ljava/lang/StringBuilder;-><init>()V

    if-eqz p2, :cond_b

    const-string p2, "\u4f53\u9a8c\u5e94\u7528\u5df2\u653e\u5230\u5e94\u7528\u5217\u8868: "

    goto :goto_1

    :cond_b
    const-string p2, "\u5df2\u8fd8\u539f\u4f53\u9a8c\u5e94\u7528: "

    :goto_1
    invoke-virtual {v0, p2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object p2

    invoke-virtual {p2, v8}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    move-result-object p2

    const-string v0, " \u4e2a / \u540d\u5355\u5171 "

    invoke-virtual {p2, v0}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object p2

    .line 766
    invoke-interface {p1}, Ljava/util/List;->size()I

    move-result p1

    invoke-virtual {p2, p1}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    move-result-object p1

    const-string p2, " \u4e2a"

    invoke-virtual {p1, p2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object p1

    invoke-virtual {p1}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object p1

    .line 765
    invoke-static {p0, p1}, Lhun/mesh/carwithenhance/hook/AutoPlayAllHook;->log(Landroid/content/Context;Ljava/lang/String;)V
    :try_end_0
    .catchall {:try_start_0 .. :try_end_0} :catchall_0

    .line 768
    :cond_c
    return v8

    .line 769
    :catchall_0
    move-exception p1

    .line 770
    const-string p2, "\u5904\u7406\u4f53\u9a8c\u5e94\u7528\u5931\u8d25"

    invoke-static {p0, p2, p1}, Lhun/mesh/carwithenhance/hook/AutoPlayAllHook;->logErr(Landroid/content/Context;Ljava/lang/String;Ljava/lang/Throwable;)V

    .line 771
    return v1

    .line 715
    :cond_d
    :goto_2
    return v1
.end method

.method private static autoplayEnabled(Landroid/content/Context;)Z
    .locals 3

    .line 3001
    const/4 v0, 0x0

    if-nez p0, :cond_0

    .line 3002
    return v0

    .line 3004
    :cond_0
    const/4 v1, 0x1

    :try_start_0
    const-string v2, "file_prefer_app_091703"

    invoke-virtual {p0, v2, v0}, Landroid/content/Context;->getSharedPreferences(Ljava/lang/String;I)Landroid/content/SharedPreferences;

    move-result-object p0

    .line 3006
    const-string v0, "prefer_auto_play"

    invoke-interface {p0, v0, v1}, Landroid/content/SharedPreferences;->getBoolean(Ljava/lang/String;Z)Z

    move-result p0
    :try_end_0
    .catchall {:try_start_0 .. :try_end_0} :catchall_0

    return p0

    .line 3007
    :catchall_0
    move-exception p0

    .line 3008
    return v1
.end method

.method public static bindBrowseService(Landroid/content/Context;Ljava/lang/String;)V
    .locals 3

    .line 2918
    if-eqz p0, :cond_7

    if-eqz p1, :cond_7

    invoke-virtual {p1}, Ljava/lang/String;->length()I

    move-result v0

    if-nez v0, :cond_0

    goto/16 :goto_4

    .line 2922
    :cond_0
    :try_start_0
    sget-object v0, Lhun/mesh/carwithenhance/hook/AutoPlayAllHook;->KNOWN_SERVICES:Ljava/util/Map;

    invoke-interface {v0, p1}, Ljava/util/Map;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v0

    check-cast v0, Ljava/lang/String;

    .line 2923
    if-eqz v0, :cond_1

    .line 2924
    new-instance v1, Landroid/content/ComponentName;

    invoke-direct {v1, p1, v0}, Landroid/content/ComponentName;-><init>(Ljava/lang/String;Ljava/lang/String;)V

    invoke-static {p0, v1}, Lhun/mesh/carwithenhance/hook/AutoPlayAllHook;->connect(Landroid/content/Context;Landroid/content/ComponentName;)V

    .line 2926
    :cond_1
    invoke-virtual {p0}, Landroid/content/Context;->getPackageManager()Landroid/content/pm/PackageManager;

    move-result-object v0

    .line 2927
    new-instance v1, Landroid/content/Intent;

    const-string v2, "android.media.browse.MediaBrowserService"

    invoke-direct {v1, v2}, Landroid/content/Intent;-><init>(Ljava/lang/String;)V

    .line 2928
    invoke-virtual {v1, p1}, Landroid/content/Intent;->setPackage(Ljava/lang/String;)Landroid/content/Intent;

    .line 2929
    const/4 v2, 0x0

    invoke-virtual {v0, v1, v2}, Landroid/content/pm/PackageManager;->queryIntentServices(Landroid/content/Intent;I)Ljava/util/List;

    move-result-object v0

    .line 2930
    new-instance v1, Ljava/lang/StringBuilder;

    invoke-direct {v1}, Ljava/lang/StringBuilder;-><init>()V

    invoke-virtual {v1, p1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v1

    const-string v2, " \u5a92\u4f53\u670d\u52a1\u67e5\u8be2\u7ed3\u679c: "

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v1

    if-nez v0, :cond_2

    const/4 v2, -0x1

    goto :goto_0

    :cond_2
    invoke-interface {v0}, Ljava/util/List;->size()I

    move-result v2

    :goto_0
    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    move-result-object v1

    invoke-virtual {v1}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v1

    invoke-static {p0, v1}, Lhun/mesh/carwithenhance/hook/AutoPlayAllHook;->log(Landroid/content/Context;Ljava/lang/String;)V

    .line 2931
    if-eqz v0, :cond_6

    invoke-interface {v0}, Ljava/util/List;->isEmpty()Z

    move-result v1

    if-eqz v1, :cond_3

    goto :goto_2

    .line 2934
    :cond_3
    invoke-interface {v0}, Ljava/util/List;->iterator()Ljava/util/Iterator;

    move-result-object v0

    :goto_1
    invoke-interface {v0}, Ljava/util/Iterator;->hasNext()Z

    move-result v1

    if-eqz v1, :cond_5

    invoke-interface {v0}, Ljava/util/Iterator;->next()Ljava/lang/Object;

    move-result-object v1

    check-cast v1, Landroid/content/pm/ResolveInfo;

    .line 2935
    iget-object v1, v1, Landroid/content/pm/ResolveInfo;->serviceInfo:Landroid/content/pm/ServiceInfo;

    .line 2936
    if-nez v1, :cond_4

    .line 2937
    goto :goto_1

    .line 2939
    :cond_4
    new-instance v0, Ljava/lang/StringBuilder;

    invoke-direct {v0}, Ljava/lang/StringBuilder;-><init>()V

    const-string v2, "  \u5019\u9009: "

    invoke-virtual {v0, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v0

    iget-object v2, v1, Landroid/content/pm/ServiceInfo;->name:Ljava/lang/String;

    invoke-virtual {v0, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v0

    const-string v2, " exported="

    invoke-virtual {v0, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v0

    iget-boolean v2, v1, Landroid/content/pm/ServiceInfo;->exported:Z

    invoke-virtual {v0, v2}, Ljava/lang/StringBuilder;->append(Z)Ljava/lang/StringBuilder;

    move-result-object v0

    invoke-virtual {v0}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v0

    invoke-static {p0, v0}, Lhun/mesh/carwithenhance/hook/AutoPlayAllHook;->log(Landroid/content/Context;Ljava/lang/String;)V

    .line 2940
    new-instance v0, Landroid/content/ComponentName;

    iget-object v2, v1, Landroid/content/pm/ServiceInfo;->packageName:Ljava/lang/String;

    iget-object v1, v1, Landroid/content/pm/ServiceInfo;->name:Ljava/lang/String;

    invoke-direct {v0, v2, v1}, Landroid/content/ComponentName;-><init>(Ljava/lang/String;Ljava/lang/String;)V

    invoke-static {p0, v0}, Lhun/mesh/carwithenhance/hook/AutoPlayAllHook;->connect(Landroid/content/Context;Landroid/content/ComponentName;)V
    :try_end_0
    .catchall {:try_start_0 .. :try_end_0} :catchall_0

    .line 2941
    return-void

    .line 2945
    :cond_5
    goto :goto_3

    .line 2932
    :cond_6
    :goto_2
    return-void

    .line 2943
    :catchall_0
    move-exception v0

    .line 2944
    new-instance v1, Ljava/lang/StringBuilder;

    invoke-direct {v1}, Ljava/lang/StringBuilder;-><init>()V

    const-string v2, "\u540e\u53f0\u7ed1\u5b9a\u5a92\u4f53\u670d\u52a1\u5931\u8d25: "

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v1

    invoke-virtual {v1, p1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object p1

    invoke-virtual {p1}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object p1

    invoke-static {p0, p1, v0}, Lhun/mesh/carwithenhance/hook/AutoPlayAllHook;->logErr(Landroid/content/Context;Ljava/lang/String;Ljava/lang/Throwable;)V

    .line 2946
    :goto_3
    return-void

    .line 2919
    :cond_7
    :goto_4
    return-void
.end method

.method private static blockedBySwitchDrop(Ljava/lang/String;)Z
    .locals 7

    .line 1486
    const/4 v0, 0x0

    if-eqz p0, :cond_3

    invoke-virtual {p0}, Ljava/lang/String;->length()I

    move-result v1

    if-nez v1, :cond_0

    goto :goto_0

    .line 1489
    :cond_0
    sget-object v1, Lhun/mesh/carwithenhance/hook/AutoPlayAllHook;->SWITCH_DROPPED:Ljava/util/Map;

    invoke-interface {v1, p0}, Ljava/util/Map;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v2

    check-cast v2, Ljava/lang/Long;

    .line 1490
    if-nez v2, :cond_1

    .line 1491
    return v0

    .line 1493
    :cond_1
    invoke-static {}, Landroid/os/SystemClock;->elapsedRealtime()J

    move-result-wide v3

    invoke-virtual {v2}, Ljava/lang/Long;->longValue()J

    move-result-wide v5

    sub-long/2addr v3, v5

    const-wide/32 v5, 0x3a980

    cmp-long v2, v3, v5

    if-lez v2, :cond_2

    .line 1494
    invoke-interface {v1, p0}, Ljava/util/Map;->remove(Ljava/lang/Object;)Ljava/lang/Object;

    .line 1495
    return v0

    .line 1497
    :cond_2
    const/4 p0, 0x1

    return p0

    .line 1487
    :cond_3
    :goto_0
    return v0
.end method

.method private static blockedByUserKill(Landroid/content/Context;Ljava/lang/String;)Z
    .locals 3

    .line 2551
    const/4 v0, 0x0

    if-nez p1, :cond_0

    .line 2552
    return v0

    .line 2554
    :cond_0
    const-string v1, "hook_skip_user_killed_enabled"

    const/4 v2, 0x1

    invoke-static {p0, v1, v2}, Lhun/mesh/carwithenhance/hook/AutoPlayAllHook;->isPrefEnabled(Landroid/content/Context;Ljava/lang/String;Z)Z

    move-result v1

    if-nez v1, :cond_1

    .line 2555
    return v0

    .line 2557
    :cond_1
    sget-object v1, Lhun/mesh/carwithenhance/hook/AutoPlayAllHook;->USER_KILLED:Ljava/util/Set;

    invoke-interface {v1, p1}, Ljava/util/Set;->contains(Ljava/lang/Object;)Z

    move-result v1

    if-eqz v1, :cond_2

    .line 2558
    return v2

    .line 2560
    :cond_2
    invoke-static {p0, p1}, Lhun/mesh/carwithenhance/hook/AutoPlayAllHook;->looksUserKilled(Landroid/content/Context;Ljava/lang/String;)Z

    move-result v1

    if-eqz v1, :cond_3

    .line 2561
    invoke-static {p0, p1}, Lhun/mesh/carwithenhance/hook/AutoPlayAllHook;->markUserKilled(Landroid/content/Context;Ljava/lang/String;)V

    .line 2562
    return v2

    .line 2564
    :cond_3
    return v0
.end method

.method private static callerSource()I
    .locals 5

    .line 1234
    :try_start_0
    new-instance v0, Ljava/lang/Throwable;

    invoke-direct {v0}, Ljava/lang/Throwable;-><init>()V

    invoke-virtual {v0}, Ljava/lang/Throwable;->getStackTrace()[Ljava/lang/StackTraceElement;

    move-result-object v0

    .line 1235
    array-length v1, v0

    const/4 v2, 0x0

    :goto_0
    if-ge v2, v1, :cond_5

    aget-object v3, v0, v2

    .line 1236
    if-nez v3, :cond_0

    .line 1237
    goto :goto_1

    .line 1239
    :cond_0
    invoke-virtual {v3}, Ljava/lang/StackTraceElement;->getClassName()Ljava/lang/String;

    move-result-object v3

    .line 1240
    if-eqz v3, :cond_4

    invoke-virtual {v3}, Ljava/lang/String;->length()I

    move-result v4

    if-nez v4, :cond_1

    .line 1241
    goto :goto_1

    .line 1244
    :cond_1
    const-string v4, "com.carwith.launcher.card"

    invoke-virtual {v3, v4}, Ljava/lang/String;->startsWith(Ljava/lang/String;)Z

    move-result v4

    if-eqz v4, :cond_2

    .line 1245
    const/4 v0, 0x2

    return v0

    .line 1248
    :cond_2
    const-string v4, "com.carwith.launcher.ams"

    invoke-virtual {v3, v4}, Ljava/lang/String;->startsWith(Ljava/lang/String;)Z

    move-result v4

    if-nez v4, :cond_3

    const-string v4, "com.carwith.launcher.surface"

    .line 1249
    invoke-virtual {v3, v4}, Ljava/lang/String;->startsWith(Ljava/lang/String;)Z

    move-result v4

    if-nez v4, :cond_3

    const-string v4, "com.carwith.launcher.home"

    .line 1250
    invoke-virtual {v3, v4}, Ljava/lang/String;->startsWith(Ljava/lang/String;)Z

    move-result v4

    if-nez v4, :cond_3

    const-string v4, "com.carwith.launcher.miuiembedding"

    .line 1251
    invoke-virtual {v3, v4}, Ljava/lang/String;->startsWith(Ljava/lang/String;)Z

    move-result v4

    if-nez v4, :cond_3

    const-string v4, "com.carwith.launcher.settings"

    .line 1252
    invoke-virtual {v3, v4}, Ljava/lang/String;->startsWith(Ljava/lang/String;)Z

    move-result v3
    :try_end_0
    .catchall {:try_start_0 .. :try_end_0} :catchall_0

    if-eqz v3, :cond_4

    .line 1253
    :cond_3
    const/4 v0, 0x1

    return v0

    .line 1235
    :cond_4
    :goto_1
    add-int/lit8 v2, v2, 0x1

    goto :goto_0

    .line 1257
    :cond_5
    goto :goto_2

    .line 1256
    :catchall_0
    move-exception v0

    .line 1258
    :goto_2
    const/4 v0, 0x3

    return v0
.end method

.method private static callerTrace(I)Ljava/lang/String;
    .locals 8

    .line 1274
    :try_start_0
    new-instance v0, Ljava/lang/StringBuilder;

    invoke-direct {v0}, Ljava/lang/StringBuilder;-><init>()V

    .line 1275
    new-instance v1, Ljava/lang/Throwable;

    invoke-direct {v1}, Ljava/lang/Throwable;-><init>()V

    invoke-virtual {v1}, Ljava/lang/Throwable;->getStackTrace()[Ljava/lang/StackTraceElement;

    move-result-object v1

    .line 1276
    nop

    .line 1277
    array-length v2, v1

    const/4 v3, 0x0

    move v4, v3

    :goto_0
    if-ge v3, v2, :cond_4

    aget-object v5, v1, v3

    .line 1278
    if-nez v5, :cond_0

    .line 1279
    goto :goto_1

    .line 1281
    :cond_0
    invoke-virtual {v5}, Ljava/lang/StackTraceElement;->getClassName()Ljava/lang/String;

    move-result-object v6

    .line 1282
    if-eqz v6, :cond_3

    const-string v7, "android."

    invoke-virtual {v6, v7}, Ljava/lang/String;->startsWith(Ljava/lang/String;)Z

    move-result v7

    if-nez v7, :cond_3

    const-string v7, "java."

    invoke-virtual {v6, v7}, Ljava/lang/String;->startsWith(Ljava/lang/String;)Z

    move-result v7

    if-nez v7, :cond_3

    const-string v7, "com.android."

    .line 1283
    invoke-virtual {v6, v7}, Ljava/lang/String;->startsWith(Ljava/lang/String;)Z

    move-result v7

    if-nez v7, :cond_3

    const-string v7, "de.robv"

    invoke-virtual {v6, v7}, Ljava/lang/String;->startsWith(Ljava/lang/String;)Z

    move-result v7

    if-nez v7, :cond_3

    const-string v7, "hun.mesh"

    .line 1284
    invoke-virtual {v6, v7}, Ljava/lang/String;->startsWith(Ljava/lang/String;)Z

    move-result v7

    if-eqz v7, :cond_1

    .line 1285
    goto :goto_1

    .line 1287
    :cond_1
    invoke-virtual {v0}, Ljava/lang/StringBuilder;->length()I

    move-result v7

    if-lez v7, :cond_2

    .line 1288
    const-string v7, " <- "

    invoke-virtual {v0, v7}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    .line 1290
    :cond_2
    invoke-virtual {v0, v6}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v6

    const/16 v7, 0x23

    invoke-virtual {v6, v7}, Ljava/lang/StringBuilder;->append(C)Ljava/lang/StringBuilder;

    move-result-object v6

    invoke-virtual {v5}, Ljava/lang/StackTraceElement;->getMethodName()Ljava/lang/String;

    move-result-object v5

    invoke-virtual {v6, v5}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    .line 1291
    add-int/lit8 v4, v4, 0x1

    if-lt v4, p0, :cond_3

    .line 1292
    goto :goto_2

    .line 1277
    :cond_3
    :goto_1
    add-int/lit8 v3, v3, 0x1

    goto :goto_0

    .line 1295
    :cond_4
    :goto_2
    invoke-virtual {v0}, Ljava/lang/StringBuilder;->length()I

    move-result p0

    if-nez p0, :cond_5

    const-string p0, "(\u770b\u4e0d\u5230 CarWith \u6808)"

    goto :goto_3

    :cond_5
    invoke-virtual {v0}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object p0
    :try_end_0
    .catchall {:try_start_0 .. :try_end_0} :catchall_0

    :goto_3
    return-object p0

    .line 1296
    :catchall_0
    move-exception p0

    .line 1297
    const-string p0, "(\u6808\u8bfb\u53d6\u5931\u8d25)"

    return-object p0
.end method

.method private static carwithCurrentMediaApp()Ljava/lang/String;
    .locals 4

    .line 1876
    :try_start_0
    const-string v0, "ma.a"

    sget-object v1, Lhun/mesh/carwithenhance/hook/AutoPlayAllHook;->sCl:Ljava/lang/ClassLoader;

    invoke-static {v0, v1}, Lde/robv/android/xposed/XposedHelpers;->findClass(Ljava/lang/String;Ljava/lang/ClassLoader;)Ljava/lang/Class;

    move-result-object v0

    .line 1877
    const-string v1, "p"

    const/4 v2, 0x0

    new-array v3, v2, [Ljava/lang/Object;

    invoke-static {v0, v1, v3}, Lde/robv/android/xposed/XposedHelpers;->callStaticMethod(Ljava/lang/Class;Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v0

    .line 1878
    const-string v1, "o"

    new-array v2, v2, [Ljava/lang/Object;

    invoke-static {v0, v1, v2}, Lde/robv/android/xposed/XposedHelpers;->callMethod(Ljava/lang/Object;Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v0

    .line 1879
    instance-of v1, v0, Ljava/lang/String;

    if-eqz v1, :cond_0

    move-object v1, v0

    check-cast v1, Ljava/lang/String;

    invoke-virtual {v1}, Ljava/lang/String;->length()I

    move-result v1

    if-lez v1, :cond_0

    .line 1880
    check-cast v0, Ljava/lang/String;
    :try_end_0
    .catchall {:try_start_0 .. :try_end_0} :catchall_0

    return-object v0

    .line 1883
    :cond_0
    goto :goto_0

    .line 1882
    :catchall_0
    move-exception v0

    .line 1884
    :goto_0
    const/4 v0, 0x0

    return-object v0
.end method

.method private static carwithLastUsedMediaApp()Ljava/lang/String;
    .locals 3

    .line 1890
    :try_start_0
    const-string v0, "xa.c"

    sget-object v1, Lhun/mesh/carwithenhance/hook/AutoPlayAllHook;->sCl:Ljava/lang/ClassLoader;

    invoke-static {v0, v1}, Lde/robv/android/xposed/XposedHelpers;->findClass(Ljava/lang/String;Ljava/lang/ClassLoader;)Ljava/lang/Class;

    move-result-object v0

    .line 1891
    const-string v1, "h"

    const/4 v2, 0x0

    new-array v2, v2, [Ljava/lang/Object;

    invoke-static {v0, v1, v2}, Lde/robv/android/xposed/XposedHelpers;->callStaticMethod(Ljava/lang/Class;Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v0

    .line 1892
    const-string v1, "d"

    invoke-static {v0, v1}, Lde/robv/android/xposed/XposedHelpers;->getObjectField(Ljava/lang/Object;Ljava/lang/String;)Ljava/lang/Object;

    move-result-object v0

    .line 1893
    instance-of v1, v0, Ljava/lang/String;

    if-eqz v1, :cond_0

    move-object v1, v0

    check-cast v1, Ljava/lang/String;

    invoke-virtual {v1}, Ljava/lang/String;->length()I

    move-result v1

    if-lez v1, :cond_0

    .line 1894
    check-cast v0, Ljava/lang/String;
    :try_end_0
    .catchall {:try_start_0 .. :try_end_0} :catchall_0

    return-object v0

    .line 1897
    :cond_0
    goto :goto_0

    .line 1896
    :catchall_0
    move-exception v0

    .line 1898
    :goto_0
    const/4 v0, 0x0

    return-object v0
.end method

.method private static collectDisconnectTargets(Landroid/content/Context;)Ljava/util/List;
    .locals 5
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "(",
            "Landroid/content/Context;",
            ")",
            "Ljava/util/List<",
            "Ljava/lang/String;",
            ">;"
        }
    .end annotation

    .line 1795
    new-instance v0, Ljava/util/LinkedHashSet;

    invoke-direct {v0}, Ljava/util/LinkedHashSet;-><init>()V

    .line 1796
    sget-object v1, Lhun/mesh/carwithenhance/hook/AutoPlayAllHook;->SESSION_MEDIA:Ljava/util/Set;

    invoke-interface {v0, v1}, Ljava/util/Set;->addAll(Ljava/util/Collection;)Z

    .line 1797
    sget-object v1, Lhun/mesh/carwithenhance/hook/AutoPlayAllHook;->WOKE:Ljava/util/Set;

    invoke-interface {v0, v1}, Ljava/util/Set;->addAll(Ljava/util/Collection;)Z

    .line 1798
    sget-object v1, Lhun/mesh/carwithenhance/hook/AutoPlayAllHook;->sLastAcceptedPkg:Ljava/lang/String;

    invoke-interface {v0, v1}, Ljava/util/Set;->add(Ljava/lang/Object;)Z

    .line 1799
    sget-object v1, Lhun/mesh/carwithenhance/hook/AutoPlayAllHook;->sActiveWokePkg:Ljava/lang/String;

    invoke-interface {v0, v1}, Ljava/util/Set;->add(Ljava/lang/Object;)Z

    .line 1800
    sget-object v1, Lhun/mesh/carwithenhance/hook/AutoPlayAllHook;->sSelectedApp:Ljava/lang/String;

    invoke-interface {v0, v1}, Ljava/util/Set;->add(Ljava/lang/Object;)Z

    .line 1801
    invoke-static {}, Lhun/mesh/carwithenhance/hook/AutoPlayAllHook;->readPrefMediaApp()Ljava/lang/String;

    move-result-object v1

    invoke-interface {v0, v1}, Ljava/util/Set;->add(Ljava/lang/Object;)Z

    .line 1802
    invoke-static {}, Lhun/mesh/carwithenhance/hook/AutoPlayAllHook;->readPrefMusicApp()Ljava/lang/String;

    move-result-object v1

    invoke-interface {v0, v1}, Ljava/util/Set;->add(Ljava/lang/Object;)Z

    .line 1803
    invoke-static {}, Lhun/mesh/carwithenhance/hook/AutoPlayAllHook;->carwithCurrentMediaApp()Ljava/lang/String;

    move-result-object v1

    invoke-interface {v0, v1}, Ljava/util/Set;->add(Ljava/lang/Object;)Z

    .line 1804
    invoke-static {}, Lhun/mesh/carwithenhance/hook/AutoPlayAllHook;->carwithLastUsedMediaApp()Ljava/lang/String;

    move-result-object v1

    invoke-interface {v0, v1}, Ljava/util/Set;->add(Ljava/lang/Object;)Z

    .line 1806
    new-instance v1, Ljava/util/ArrayList;

    invoke-direct {v1}, Ljava/util/ArrayList;-><init>()V

    .line 1807
    invoke-interface {v0}, Ljava/util/Set;->iterator()Ljava/util/Iterator;

    move-result-object v0

    :goto_0
    invoke-interface {v0}, Ljava/util/Iterator;->hasNext()Z

    move-result v2

    if-eqz v2, :cond_3

    invoke-interface {v0}, Ljava/util/Iterator;->next()Ljava/lang/Object;

    move-result-object v2

    check-cast v2, Ljava/lang/String;

    .line 1808
    invoke-static {v2}, Lhun/mesh/carwithenhance/hook/AutoPlayAllHook;->isKillCandidate(Ljava/lang/String;)Z

    move-result v3

    if-nez v3, :cond_0

    .line 1809
    goto :goto_0

    .line 1811
    :cond_0
    sget-object v3, Lhun/mesh/carwithenhance/hook/AutoPlayAllHook;->CANNOT_WAKE:Ljava/util/Set;

    invoke-interface {v3, v2}, Ljava/util/Set;->contains(Ljava/lang/Object;)Z

    move-result v3

    const-string v4, "\u65ad\u5f00\u5904\u7406: "

    if-eqz v3, :cond_1

    .line 1812
    new-instance v3, Ljava/lang/StringBuilder;

    invoke-direct {v3}, Ljava/lang/StringBuilder;-><init>()V

    invoke-virtual {v3, v4}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v3

    invoke-virtual {v3, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v2

    const-string v3, " \u65e0\u6cd5\u540e\u53f0\u62c9\u8d77\uff0c\u4fdd\u7559\u5b83\u4ee5\u4fbf\u4e0b\u6b21\u79d2\u7eed\u64ad"

    invoke-virtual {v2, v3}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v2

    invoke-virtual {v2}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v2

    invoke-static {p0, v2}, Lhun/mesh/carwithenhance/hook/AutoPlayAllHook;->log(Landroid/content/Context;Ljava/lang/String;)V

    .line 1814
    goto :goto_0

    .line 1816
    :cond_1
    invoke-static {p0, v2}, Lhun/mesh/carwithenhance/hook/AutoPlayAllHook;->isProcessRunning(Landroid/content/Context;Ljava/lang/String;)Z

    move-result v3

    if-nez v3, :cond_2

    invoke-static {p0, v2}, Lhun/mesh/carwithenhance/hook/AutoPlayAllHook;->hasSession(Landroid/content/Context;Ljava/lang/String;)Z

    move-result v3

    if-nez v3, :cond_2

    invoke-static {p0, v2}, Lhun/mesh/carwithenhance/hook/AutoPlayAllHook;->isPlaying(Landroid/content/Context;Ljava/lang/String;)Z

    move-result v3

    if-nez v3, :cond_2

    .line 1817
    new-instance v3, Ljava/lang/StringBuilder;

    invoke-direct {v3}, Ljava/lang/StringBuilder;-><init>()V

    invoke-virtual {v3, v4}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v3

    invoke-virtual {v3, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v2

    const-string v3, " \u5df2\u4e0d\u5728\u8fd0\u884c\uff0c\u8df3\u8fc7"

    invoke-virtual {v2, v3}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v2

    invoke-virtual {v2}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v2

    invoke-static {p0, v2}, Lhun/mesh/carwithenhance/hook/AutoPlayAllHook;->log(Landroid/content/Context;Ljava/lang/String;)V

    .line 1818
    goto :goto_0

    .line 1820
    :cond_2
    invoke-interface {v1, v2}, Ljava/util/List;->add(Ljava/lang/Object;)Z

    .line 1821
    goto :goto_0

    .line 1823
    :cond_3
    invoke-static {p0}, Lhun/mesh/carwithenhance/hook/AutoPlayAllHook;->activeSessionPackages(Landroid/content/Context;)Ljava/util/Set;

    move-result-object v0

    invoke-interface {v0}, Ljava/util/Set;->iterator()Ljava/util/Iterator;

    move-result-object v0

    :cond_4
    :goto_1
    invoke-interface {v0}, Ljava/util/Iterator;->hasNext()Z

    move-result v2

    if-eqz v2, :cond_6

    invoke-interface {v0}, Ljava/util/Iterator;->next()Ljava/lang/Object;

    move-result-object v2

    check-cast v2, Ljava/lang/String;

    .line 1824
    invoke-interface {v1, v2}, Ljava/util/List;->contains(Ljava/lang/Object;)Z

    move-result v3

    if-nez v3, :cond_4

    invoke-static {v2}, Lhun/mesh/carwithenhance/hook/AutoPlayAllHook;->isKillCandidate(Ljava/lang/String;)Z

    move-result v3

    if-eqz v3, :cond_4

    invoke-static {p0, v2}, Lhun/mesh/carwithenhance/hook/AutoPlayAllHook;->isPlaying(Landroid/content/Context;Ljava/lang/String;)Z

    move-result v3

    if-nez v3, :cond_5

    .line 1825
    goto :goto_1

    .line 1827
    :cond_5
    invoke-interface {v1, v2}, Ljava/util/List;->add(Ljava/lang/Object;)Z

    .line 1828
    goto :goto_1

    .line 1829
    :cond_6
    return-object v1
.end method

.method private static connect(Landroid/content/Context;Landroid/content/ComponentName;)V
    .locals 9

    .line 2949
    invoke-virtual {p1}, Landroid/content/ComponentName;->flattenToShortString()Ljava/lang/String;

    move-result-object v0

    .line 2950
    sget-object v1, Lhun/mesh/carwithenhance/hook/AutoPlayAllHook;->BROWSERS:Ljava/util/Map;

    monitor-enter v1

    .line 2951
    :try_start_0
    invoke-static {}, Landroid/os/SystemClock;->elapsedRealtime()J

    move-result-wide v2

    .line 2952
    sget-object v4, Lhun/mesh/carwithenhance/hook/AutoPlayAllHook;->ATTEMPTS:Ljava/util/Map;

    invoke-interface {v4, v0}, Ljava/util/Map;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v5

    check-cast v5, Ljava/lang/Long;

    .line 2953
    if-eqz v5, :cond_0

    invoke-virtual {v5}, Ljava/lang/Long;->longValue()J

    move-result-wide v5

    sub-long v5, v2, v5

    const-wide/16 v7, 0x1388

    cmp-long v5, v5, v7

    if-gez v5, :cond_0

    .line 2954
    new-instance p1, Ljava/lang/StringBuilder;

    invoke-direct {p1}, Ljava/lang/StringBuilder;-><init>()V

    const-string v2, "\u7565\u8fc7\u91cd\u590d\u7ed1\u5b9a: "

    invoke-virtual {p1, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object p1

    invoke-virtual {p1, v0}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object p1

    invoke-virtual {p1}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object p1

    invoke-static {p0, p1}, Lhun/mesh/carwithenhance/hook/AutoPlayAllHook;->log(Landroid/content/Context;Ljava/lang/String;)V

    .line 2955
    monitor-exit v1

    return-void

    .line 2957
    :cond_0
    invoke-static {v2, v3}, Ljava/lang/Long;->valueOf(J)Ljava/lang/Long;

    move-result-object v2

    invoke-interface {v4, v0, v2}, Ljava/util/Map;->put(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;

    .line 2958
    invoke-interface {v1, v0}, Ljava/util/Map;->remove(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v2

    check-cast v2, Landroid/media/browse/MediaBrowser;
    :try_end_0
    .catchall {:try_start_0 .. :try_end_0} :catchall_2

    .line 2959
    if-eqz v2, :cond_1

    .line 2961
    :try_start_1
    invoke-virtual {v2}, Landroid/media/browse/MediaBrowser;->disconnect()V
    :try_end_1
    .catchall {:try_start_1 .. :try_end_1} :catchall_0

    .line 2963
    goto :goto_0

    .line 2962
    :catchall_0
    move-exception v2

    .line 2966
    :cond_1
    :goto_0
    :try_start_2
    new-instance v2, Landroid/media/browse/MediaBrowser;

    new-instance v3, Lhun/mesh/carwithenhance/hook/AutoPlayAllHook$21;

    invoke-direct {v3, p1, v0}, Lhun/mesh/carwithenhance/hook/AutoPlayAllHook$21;-><init>(Landroid/content/ComponentName;Ljava/lang/String;)V

    const/4 v4, 0x0

    invoke-direct {v2, p0, p1, v3, v4}, Landroid/media/browse/MediaBrowser;-><init>(Landroid/content/Context;Landroid/content/ComponentName;Landroid/media/browse/MediaBrowser$ConnectionCallback;Landroid/os/Bundle;)V

    .line 2984
    invoke-virtual {v2}, Landroid/media/browse/MediaBrowser;->connect()V

    .line 2985
    sget-object p1, Lhun/mesh/carwithenhance/hook/AutoPlayAllHook;->BROWSERS:Ljava/util/Map;

    invoke-interface {p1, v0, v2}, Ljava/util/Map;->put(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;

    .line 2986
    new-instance p1, Ljava/lang/StringBuilder;

    invoke-direct {p1}, Ljava/lang/StringBuilder;-><init>()V

    const-string v2, "\u540e\u53f0\u7ed1\u5b9a\u5a92\u4f53\u670d\u52a1: "

    invoke-virtual {p1, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object p1

    invoke-virtual {p1, v0}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object p1

    invoke-virtual {p1}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object p1

    invoke-static {p0, p1}, Lhun/mesh/carwithenhance/hook/AutoPlayAllHook;->log(Landroid/content/Context;Ljava/lang/String;)V
    :try_end_2
    .catchall {:try_start_2 .. :try_end_2} :catchall_1

    .line 2989
    goto :goto_1

    .line 2987
    :catchall_1
    move-exception p1

    .line 2988
    :try_start_3
    new-instance v2, Ljava/lang/StringBuilder;

    invoke-direct {v2}, Ljava/lang/StringBuilder;-><init>()V

    const-string v3, "\u7ed1\u5b9a\u5f02\u5e38: "

    invoke-virtual {v2, v3}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v2

    invoke-virtual {v2, v0}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v0

    invoke-virtual {v0}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v0

    invoke-static {p0, v0, p1}, Lhun/mesh/carwithenhance/hook/AutoPlayAllHook;->logErr(Landroid/content/Context;Ljava/lang/String;Ljava/lang/Throwable;)V

    .line 2990
    :goto_1
    monitor-exit v1

    .line 2991
    return-void

    .line 2990
    :catchall_2
    move-exception p0

    monitor-exit v1
    :try_end_3
    .catchall {:try_start_3 .. :try_end_3} :catchall_2

    throw p0
.end method

.method private static describe(Ljava/util/List;)Ljava/lang/String;
    .locals 3
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "(",
            "Ljava/util/List<",
            "Ljava/lang/String;",
            ">;)",
            "Ljava/lang/String;"
        }
    .end annotation

    .line 1837
    if-eqz p0, :cond_3

    invoke-interface {p0}, Ljava/util/List;->isEmpty()Z

    move-result v0

    if-eqz v0, :cond_0

    goto :goto_1

    .line 1840
    :cond_0
    new-instance v0, Ljava/lang/StringBuilder;

    invoke-direct {v0}, Ljava/lang/StringBuilder;-><init>()V

    .line 1841
    invoke-interface {p0}, Ljava/util/List;->iterator()Ljava/util/Iterator;

    move-result-object p0

    :goto_0
    invoke-interface {p0}, Ljava/util/Iterator;->hasNext()Z

    move-result v1

    if-eqz v1, :cond_2

    invoke-interface {p0}, Ljava/util/Iterator;->next()Ljava/lang/Object;

    move-result-object v1

    check-cast v1, Ljava/lang/String;

    .line 1842
    invoke-virtual {v0}, Ljava/lang/StringBuilder;->length()I

    move-result v2

    if-lez v2, :cond_1

    .line 1843
    const-string v2, ", "

    invoke-virtual {v0, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    .line 1845
    :cond_1
    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    .line 1846
    goto :goto_0

    .line 1847
    :cond_2
    invoke-virtual {v0}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object p0

    return-object p0

    .line 1838
    :cond_3
    :goto_1
    const-string p0, "(\u65e0)"

    return-object p0
.end method

.method private static dispatchMediaPauseKey(Landroid/content/Context;)Z
    .locals 6

    .line 2869
    const/4 v0, 0x0

    :try_start_0
    const-string v1, "audio"

    invoke-virtual {p0, v1}, Landroid/content/Context;->getSystemService(Ljava/lang/String;)Ljava/lang/Object;

    move-result-object p0

    check-cast p0, Landroid/media/AudioManager;

    .line 2870
    if-nez p0, :cond_0

    .line 2871
    return v0

    .line 2873
    :cond_0
    const-class v1, Landroid/media/AudioManager;

    const-string v2, "dispatchMediaKeyEvent"

    const/4 v3, 0x1

    new-array v4, v3, [Ljava/lang/Class;

    const-class v5, Landroid/view/KeyEvent;

    aput-object v5, v4, v0

    invoke-virtual {v1, v2, v4}, Ljava/lang/Class;->getMethod(Ljava/lang/String;[Ljava/lang/Class;)Ljava/lang/reflect/Method;

    move-result-object v1

    .line 2875
    invoke-virtual {v1, v3}, Ljava/lang/reflect/Method;->setAccessible(Z)V

    .line 2876
    new-array v2, v3, [Ljava/lang/Object;

    new-instance v4, Landroid/view/KeyEvent;

    const/16 v5, 0x7f

    invoke-direct {v4, v0, v5}, Landroid/view/KeyEvent;-><init>(II)V

    aput-object v4, v2, v0

    invoke-virtual {v1, p0, v2}, Ljava/lang/reflect/Method;->invoke(Ljava/lang/Object;[Ljava/lang/Object;)Ljava/lang/Object;

    .line 2877
    new-array v2, v3, [Ljava/lang/Object;

    new-instance v4, Landroid/view/KeyEvent;

    invoke-direct {v4, v3, v5}, Landroid/view/KeyEvent;-><init>(II)V

    aput-object v4, v2, v0

    invoke-virtual {v1, p0, v2}, Ljava/lang/reflect/Method;->invoke(Ljava/lang/Object;[Ljava/lang/Object;)Ljava/lang/Object;
    :try_end_0
    .catchall {:try_start_0 .. :try_end_0} :catchall_0

    .line 2878
    return v3

    .line 2879
    :catchall_0
    move-exception p0

    .line 2880
    const/4 v1, 0x0

    const-string v2, "\u8865\u53d1\u5a92\u4f53\u6682\u505c\u952e\u5931\u8d25"

    invoke-static {v1, v2, p0}, Lhun/mesh/carwithenhance/hook/AutoPlayAllHook;->logErr(Landroid/content/Context;Ljava/lang/String;Ljava/lang/Throwable;)V

    .line 2881
    return v0
.end method

.method private static doSwitch(Landroid/content/Context;Ljava/lang/String;)V
    .locals 4

    .line 1412
    if-eqz p0, :cond_8

    if-nez p1, :cond_0

    goto/16 :goto_2

    .line 1415
    :cond_0
    invoke-static {p0}, Lhun/mesh/carwithenhance/hook/AutoPlayAllHook;->isEnabled(Landroid/content/Context;)Z

    move-result v0

    if-nez v0, :cond_1

    .line 1416
    return-void

    .line 1418
    :cond_1
    sget-object v0, Lhun/mesh/carwithenhance/hook/AutoPlayAllHook;->sSelectedApp:Ljava/lang/String;

    invoke-virtual {p1, v0}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v0

    if-nez v0, :cond_2

    .line 1420
    new-instance v0, Ljava/lang/StringBuilder;

    invoke-direct {v0}, Ljava/lang/StringBuilder;-><init>()V

    const-string v1, "\u5207\u6362\u76ee\u6807\u5df2\u53d8\u5316\uff0c\u8df3\u8fc7\u5bf9 "

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v0

    invoke-virtual {v0, p1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object p1

    const-string v0, " \u7684\u5904\u7406"

    invoke-virtual {p1, v0}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object p1

    invoke-virtual {p1}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object p1

    invoke-static {p0, p1}, Lhun/mesh/carwithenhance/hook/AutoPlayAllHook;->log(Landroid/content/Context;Ljava/lang/String;)V

    .line 1421
    return-void

    .line 1425
    :cond_2
    new-instance v0, Ljava/util/LinkedHashSet;

    invoke-direct {v0}, Ljava/util/LinkedHashSet;-><init>()V

    .line 1426
    sget-object v1, Lhun/mesh/carwithenhance/hook/AutoPlayAllHook;->sCurrentMediaPkg:Ljava/lang/String;

    invoke-virtual {v0, v1}, Ljava/util/LinkedHashSet;->add(Ljava/lang/Object;)Z

    .line 1427
    sget-object v1, Lhun/mesh/carwithenhance/hook/AutoPlayAllHook;->sActiveWokePkg:Ljava/lang/String;

    invoke-virtual {v0, v1}, Ljava/util/LinkedHashSet;->add(Ljava/lang/Object;)Z

    .line 1428
    invoke-virtual {v0}, Ljava/util/LinkedHashSet;->iterator()Ljava/util/Iterator;

    move-result-object v0

    :goto_0
    invoke-interface {v0}, Ljava/util/Iterator;->hasNext()Z

    move-result v1

    const-string v2, "\u8f66\u673a\u7aef\u5207\u6362\u5230 "

    if-eqz v1, :cond_3

    invoke-interface {v0}, Ljava/util/Iterator;->next()Ljava/lang/Object;

    move-result-object v1

    check-cast v1, Ljava/lang/String;

    .line 1429
    new-instance v3, Ljava/lang/StringBuilder;

    invoke-direct {v3}, Ljava/lang/StringBuilder;-><init>()V

    invoke-virtual {v3, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v2

    invoke-virtual {v2, p1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v2

    invoke-virtual {v2}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v2

    invoke-static {p0, v1, p1, v2}, Lhun/mesh/carwithenhance/hook/AutoPlayAllHook;->killSwitchTarget(Landroid/content/Context;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)V

    .line 1430
    goto :goto_0

    .line 1431
    :cond_3
    sget-object v0, Lhun/mesh/carwithenhance/hook/AutoPlayAllHook;->sActiveWokePkg:Ljava/lang/String;

    if-eqz v0, :cond_4

    invoke-virtual {v0, p1}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v0

    if-nez v0, :cond_4

    .line 1432
    const/4 v0, 0x0

    sput-object v0, Lhun/mesh/carwithenhance/hook/AutoPlayAllHook;->sActiveWokePkg:Ljava/lang/String;

    .line 1434
    :cond_4
    sput-object p1, Lhun/mesh/carwithenhance/hook/AutoPlayAllHook;->sCurrentMediaPkg:Ljava/lang/String;

    .line 1435
    const-string v0, "com.tencent.qqmusic"

    invoke-virtual {v0, p1}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v0

    if-eqz v0, :cond_5

    .line 1437
    return-void

    .line 1439
    :cond_5
    invoke-static {p0, p1}, Lhun/mesh/carwithenhance/hook/AutoPlayAllHook;->isProcessRunning(Landroid/content/Context;Ljava/lang/String;)Z

    move-result v0

    if-nez v0, :cond_7

    sget-object v0, Lhun/mesh/carwithenhance/hook/AutoPlayAllHook;->sCl:Ljava/lang/ClassLoader;

    invoke-static {v0, p1}, Lhun/mesh/carwithenhance/hook/AutoPlayAllHook;->isTarget(Ljava/lang/ClassLoader;Ljava/lang/String;)Z

    move-result v0

    if-nez v0, :cond_6

    goto :goto_1

    .line 1443
    :cond_6
    new-instance v0, Ljava/lang/StringBuilder;

    invoke-direct {v0}, Ljava/lang/StringBuilder;-><init>()V

    invoke-virtual {v0, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v0

    invoke-virtual {v0, p1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v0

    const-string v1, "\uff0c\u5148\u540e\u53f0\u5524\u9192\u5b83"

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v0

    invoke-virtual {v0}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v0

    invoke-static {p0, v0}, Lhun/mesh/carwithenhance/hook/AutoPlayAllHook;->log(Landroid/content/Context;Ljava/lang/String;)V

    .line 1444
    sget-object v0, Lhun/mesh/carwithenhance/hook/AutoPlayAllHook;->WOKE:Ljava/util/Set;

    invoke-interface {v0, p1}, Ljava/util/Set;->add(Ljava/lang/Object;)Z

    .line 1445
    sput-object p1, Lhun/mesh/carwithenhance/hook/AutoPlayAllHook;->sActiveWokePkg:Ljava/lang/String;

    .line 1446
    invoke-static {p0, p1}, Lhun/mesh/carwithenhance/hook/AutoPlayAllHook;->sendMediaButton(Landroid/content/Context;Ljava/lang/String;)Z

    .line 1447
    invoke-static {p0, p1}, Lhun/mesh/carwithenhance/hook/AutoPlayAllHook;->startPlayerService(Landroid/content/Context;Ljava/lang/String;)Z

    .line 1448
    invoke-static {p0, p1}, Lhun/mesh/carwithenhance/hook/AutoPlayAllHook;->requestModuleBind(Landroid/content/Context;Ljava/lang/String;)V

    .line 1449
    return-void

    .line 1440
    :cond_7
    :goto_1
    return-void

    .line 1413
    :cond_8
    :goto_2
    return-void
.end method

.method private static dumpAppList(Landroid/content/Context;Ljava/lang/ClassLoader;)V
    .locals 4

    .line 1958
    :try_start_0
    invoke-static {}, Lhun/mesh/carwithenhance/dexkit/GenericAutoPlayHooks;->getExperienceClassName()Ljava/lang/String;

    move-result-object v0

    invoke-static {v0, p1}, Lde/robv/android/xposed/XposedHelpers;->findClass(Ljava/lang/String;Ljava/lang/ClassLoader;)Ljava/lang/Class;

    move-result-object p1

    .line 1959
    const/4 v0, 0x1

    new-array v0, v0, [Ljava/lang/Object;

    .line 1960
    invoke-virtual {p0}, Landroid/content/Context;->getApplicationContext()Landroid/content/Context;

    move-result-object v1

    check-cast v1, Landroid/app/Application;

    const/4 v2, 0x0

    aput-object v1, v0, v2

    .line 1959
    invoke-static {p1, v0}, Lde/robv/android/xposed/XposedHelpers;->newInstance(Ljava/lang/Class;[Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object p1

    .line 1961
    const-string v0, "\u5e94\u7528\u5217\u8868\u81ea\u68c0: \u5f00\u59cb\u6784\u5efa"

    invoke-static {p0, v0}, Lhun/mesh/carwithenhance/hook/AutoPlayAllHook;->log(Landroid/content/Context;Ljava/lang/String;)V

    .line 1962
    invoke-static {}, Lhun/mesh/carwithenhance/dexkit/GenericAutoPlayHooks;->getExperienceMethodName()Ljava/lang/String;

    move-result-object v0

    const/4 v1, 0x0

    filled-new-array {v1}, [Ljava/lang/Object;

    move-result-object v1

    invoke-static {p1, v0, v1}, Lde/robv/android/xposed/XposedHelpers;->callMethod(Ljava/lang/Object;Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/Object;

    .line 1963
    sget-object v0, Lhun/mesh/carwithenhance/hook/AutoPlayAllHook;->sHandler:Landroid/os/Handler;

    if-nez v0, :cond_0

    .line 1964
    new-instance v0, Landroid/os/Handler;

    invoke-static {}, Landroid/os/Looper;->getMainLooper()Landroid/os/Looper;

    move-result-object v1

    invoke-direct {v0, v1}, Landroid/os/Handler;-><init>(Landroid/os/Looper;)V

    sput-object v0, Lhun/mesh/carwithenhance/hook/AutoPlayAllHook;->sHandler:Landroid/os/Handler;

    .line 1966
    :cond_0
    sget-object v0, Lhun/mesh/carwithenhance/hook/AutoPlayAllHook;->sHandler:Landroid/os/Handler;

    new-instance v1, Lhun/mesh/carwithenhance/hook/AutoPlayAllHook$19;

    invoke-direct {v1, p1, p0}, Lhun/mesh/carwithenhance/hook/AutoPlayAllHook$19;-><init>(Ljava/lang/Object;Landroid/content/Context;)V

    const-wide/16 v2, 0xfa0

    invoke-virtual {v0, v1, v2, v3}, Landroid/os/Handler;->postDelayed(Ljava/lang/Runnable;J)Z
    :try_end_0
    .catchall {:try_start_0 .. :try_end_0} :catchall_0

    .line 2004
    goto :goto_0

    .line 2002
    :catchall_0
    move-exception p1

    .line 2003
    const-string v0, "\u5e94\u7528\u5217\u8868\u81ea\u68c0\u542f\u52a8\u5931\u8d25"

    invoke-static {p0, v0, p1}, Lhun/mesh/carwithenhance/hook/AutoPlayAllHook;->logErr(Landroid/content/Context;Ljava/lang/String;Ljava/lang/Throwable;)V

    .line 2005
    :goto_0
    return-void
.end method

.method private static dumpLyric(Ljava/lang/String;Ljava/lang/ClassLoader;)V
    .locals 5

    .line 2300
    sget-object p1, Lhun/mesh/carwithenhance/hook/AutoPlayAllHook;->sAppCtx:Landroid/content/Context;

    .line 2302
    :try_start_0
    const-string v0, "media_session"

    .line 2303
    invoke-virtual {p1, v0}, Landroid/content/Context;->getSystemService(Ljava/lang/String;)Ljava/lang/Object;

    move-result-object v0

    check-cast v0, Landroid/media/session/MediaSessionManager;

    .line 2304
    const/4 v1, 0x0

    invoke-virtual {v0, v1}, Landroid/media/session/MediaSessionManager;->getActiveSessions(Landroid/content/ComponentName;)Ljava/util/List;

    move-result-object v0

    .line 2305
    nop

    .line 2306
    invoke-interface {v0}, Ljava/util/List;->iterator()Ljava/util/Iterator;

    move-result-object v0

    :goto_0
    invoke-interface {v0}, Ljava/util/Iterator;->hasNext()Z

    move-result v2

    if-eqz v2, :cond_1

    invoke-interface {v0}, Ljava/util/Iterator;->next()Ljava/lang/Object;

    move-result-object v2

    check-cast v2, Landroid/media/session/MediaController;

    .line 2307
    if-eqz v2, :cond_0

    invoke-virtual {v2}, Landroid/media/session/MediaController;->getPackageName()Ljava/lang/String;

    move-result-object v3

    invoke-virtual {p0, v3}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v3
    :try_end_0
    .catchall {:try_start_0 .. :try_end_0} :catchall_0

    if-eqz v3, :cond_0

    .line 2308
    nop

    .line 2309
    move-object v1, v2

    goto :goto_1

    .line 2311
    :cond_0
    goto :goto_0

    .line 2312
    :cond_1
    :goto_1
    const-string v0, "["

    if-nez v1, :cond_2

    .line 2313
    :try_start_1
    new-instance v1, Ljava/lang/StringBuilder;

    invoke-direct {v1}, Ljava/lang/StringBuilder;-><init>()V

    invoke-virtual {v1, v0}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v0

    invoke-virtual {v0, p0}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v0

    const-string v1, "] \u6ca1\u6709\u6d3b\u8dc3\u7684\u5a92\u4f53\u4f1a\u8bdd"

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v0

    invoke-virtual {v0}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v0

    invoke-static {p1, v0}, Lhun/mesh/carwithenhance/hook/AutoPlayAllHook;->log(Landroid/content/Context;Ljava/lang/String;)V

    .line 2314
    return-void

    .line 2316
    :cond_2
    invoke-virtual {v1}, Landroid/media/session/MediaController;->getMetadata()Landroid/media/MediaMetadata;

    move-result-object v1

    .line 2317
    if-nez v1, :cond_3

    .line 2318
    new-instance v1, Ljava/lang/StringBuilder;

    invoke-direct {v1}, Ljava/lang/StringBuilder;-><init>()V

    invoke-virtual {v1, v0}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v0

    invoke-virtual {v0, p0}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v0

    const-string v1, "] \u5a92\u4f53\u4f1a\u8bdd\u6ca1\u6709\u4efb\u4f55\u5143\u6570\u636e"

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v0

    invoke-virtual {v0}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v0

    invoke-static {p1, v0}, Lhun/mesh/carwithenhance/hook/AutoPlayAllHook;->log(Landroid/content/Context;Ljava/lang/String;)V

    .line 2319
    return-void

    .line 2321
    :cond_3
    new-instance v2, Ljava/lang/StringBuilder;

    new-instance v3, Ljava/lang/StringBuilder;

    invoke-direct {v3}, Ljava/lang/StringBuilder;-><init>()V

    invoke-virtual {v3, v0}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v0

    invoke-virtual {v0, p0}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v0

    const-string v3, "] \u5143\u6570\u636e\u952e: "

    invoke-virtual {v0, v3}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v0

    invoke-virtual {v0}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v0

    invoke-direct {v2, v0}, Ljava/lang/StringBuilder;-><init>(Ljava/lang/String;)V

    .line 2322
    invoke-virtual {v1}, Landroid/media/MediaMetadata;->keySet()Ljava/util/Set;

    move-result-object v0

    invoke-interface {v0}, Ljava/util/Set;->iterator()Ljava/util/Iterator;

    move-result-object v0

    :goto_2
    invoke-interface {v0}, Ljava/util/Iterator;->hasNext()Z

    move-result v3

    if-eqz v3, :cond_4

    invoke-interface {v0}, Ljava/util/Iterator;->next()Ljava/lang/Object;

    move-result-object v3

    check-cast v3, Ljava/lang/String;

    .line 2323
    invoke-virtual {v2, v3}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v3

    const/16 v4, 0x20

    invoke-virtual {v3, v4}, Ljava/lang/StringBuilder;->append(C)Ljava/lang/StringBuilder;

    .line 2324
    goto :goto_2

    .line 2325
    :cond_4
    invoke-virtual {v2}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v0

    invoke-static {p1, v0}, Lhun/mesh/carwithenhance/hook/AutoPlayAllHook;->log(Landroid/content/Context;Ljava/lang/String;)V

    .line 2326
    invoke-virtual {v1}, Landroid/media/MediaMetadata;->keySet()Ljava/util/Set;

    move-result-object v0

    invoke-interface {v0}, Ljava/util/Set;->iterator()Ljava/util/Iterator;

    move-result-object v0

    :goto_3
    invoke-interface {v0}, Ljava/util/Iterator;->hasNext()Z

    move-result v2

    if-eqz v2, :cond_7

    invoke-interface {v0}, Ljava/util/Iterator;->next()Ljava/lang/Object;

    move-result-object v2

    check-cast v2, Ljava/lang/String;

    .line 2327
    sget-object v3, Ljava/util/Locale;->US:Ljava/util/Locale;

    invoke-virtual {v2, v3}, Ljava/lang/String;->toLowerCase(Ljava/util/Locale;)Ljava/lang/String;

    move-result-object v3

    .line 2328
    const-string v4, "ucar."

    invoke-virtual {v2, v4}, Ljava/lang/String;->startsWith(Ljava/lang/String;)Z

    move-result v4

    if-nez v4, :cond_5

    const-string v4, "lyric"

    invoke-virtual {v3, v4}, Ljava/lang/String;->contains(Ljava/lang/CharSequence;)Z

    move-result v3

    if-eqz v3, :cond_6

    .line 2329
    :cond_5
    new-instance v3, Ljava/lang/StringBuilder;

    invoke-direct {v3}, Ljava/lang/StringBuilder;-><init>()V

    const-string v4, "   "

    invoke-virtual {v3, v4}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v3

    invoke-virtual {v3, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v3

    const-string v4, " = "

    invoke-virtual {v3, v4}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v3

    invoke-virtual {v1, v2}, Landroid/media/MediaMetadata;->getString(Ljava/lang/String;)Ljava/lang/String;

    move-result-object v2

    invoke-static {v2}, Lhun/mesh/carwithenhance/hook/AutoPlayAllHook;->quote(Ljava/lang/Object;)Ljava/lang/String;

    move-result-object v2

    invoke-virtual {v3, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v2

    invoke-virtual {v2}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v2

    invoke-static {p1, v2}, Lhun/mesh/carwithenhance/hook/AutoPlayAllHook;->log(Landroid/content/Context;Ljava/lang/String;)V
    :try_end_1
    .catchall {:try_start_1 .. :try_end_1} :catchall_0

    .line 2331
    :cond_6
    goto :goto_3

    .line 2334
    :cond_7
    goto :goto_4

    .line 2332
    :catchall_0
    move-exception v0

    .line 2333
    new-instance v1, Ljava/lang/StringBuilder;

    invoke-direct {v1}, Ljava/lang/StringBuilder;-><init>()V

    const-string v2, "\u8bfb\u53d6\u6b4c\u8bcd\u72b6\u6001\u5931\u8d25: "

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v1

    invoke-virtual {v1, p0}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object p0

    invoke-virtual {p0}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object p0

    invoke-static {p1, p0, v0}, Lhun/mesh/carwithenhance/hook/AutoPlayAllHook;->logErr(Landroid/content/Context;Ljava/lang/String;Ljava/lang/Throwable;)V

    .line 2335
    :goto_4
    return-void
.end method

.method private static experiencePackages(Ljava/lang/ClassLoader;)Ljava/util/List;
    .locals 7
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "(",
            "Ljava/lang/ClassLoader;",
            ")",
            "Ljava/util/List<",
            "Ljava/lang/String;",
            ">;"
        }
    .end annotation

    .line 618
    const-string v0, "R"

    new-instance v1, Ljava/util/LinkedHashSet;

    invoke-direct {v1}, Ljava/util/LinkedHashSet;-><init>()V

    .line 620
    :try_start_0
    const-string v2, "com.carwith.common.utils.c0"

    invoke-static {v2, p0}, Lde/robv/android/xposed/XposedHelpers;->findClass(Ljava/lang/String;Ljava/lang/ClassLoader;)Ljava/lang/Class;

    move-result-object v2

    .line 621
    const-string v3, "O"

    const/4 v4, 0x0

    new-array v5, v4, [Ljava/lang/Object;

    invoke-static {v2, v3, v5}, Lde/robv/android/xposed/XposedHelpers;->callStaticMethod(Ljava/lang/Class;Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v2

    .line 622
    if-nez v2, :cond_0

    .line 623
    new-instance p0, Ljava/util/ArrayList;

    invoke-direct {p0, v1}, Ljava/util/ArrayList;-><init>(Ljava/util/Collection;)V
    :try_end_0
    .catchall {:try_start_0 .. :try_end_0} :catchall_1

    return-object p0

    .line 625
    :cond_0
    nop

    .line 627
    :try_start_1
    const-string v3, "com.carwith.common.utils.g3"

    invoke-static {v3, p0}, Lde/robv/android/xposed/XposedHelpers;->findClass(Ljava/lang/String;Ljava/lang/ClassLoader;)Ljava/lang/Class;

    move-result-object p0

    .line 628
    const-string v3, "e"

    new-array v5, v4, [Ljava/lang/Object;

    invoke-static {p0, v3, v5}, Lde/robv/android/xposed/XposedHelpers;->callStaticMethod(Ljava/lang/Class;Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object p0

    .line 629
    const-string v3, "d"

    new-array v5, v4, [Ljava/lang/Object;

    invoke-static {p0, v3, v5}, Lde/robv/android/xposed/XposedHelpers;->callMethod(Ljava/lang/Object;Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object p0

    .line 630
    instance-of v3, p0, Ljava/lang/Integer;

    if-eqz v3, :cond_1

    .line 631
    check-cast p0, Ljava/lang/Integer;

    invoke-virtual {p0}, Ljava/lang/Integer;->intValue()I

    move-result p0
    :try_end_1
    .catchall {:try_start_1 .. :try_end_1} :catchall_0

    goto :goto_0

    .line 630
    :cond_1
    move p0, v4

    .line 634
    :goto_0
    goto :goto_1

    .line 633
    :catchall_0
    move-exception p0

    move p0, v4

    .line 635
    :goto_1
    const/4 v3, 0x1

    :try_start_2
    new-array v5, v3, [Ljava/lang/Object;

    invoke-static {p0}, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;

    move-result-object v6

    aput-object v6, v5, v4

    invoke-static {v2, v0, v5}, Lde/robv/android/xposed/XposedHelpers;->callMethod(Ljava/lang/Object;Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v5

    invoke-static {v1, v5}, Lhun/mesh/carwithenhance/hook/AutoPlayAllHook;->addAllStrings(Ljava/util/Set;Ljava/lang/Object;)V

    .line 636
    if-eqz p0, :cond_2

    .line 637
    new-array p0, v3, [Ljava/lang/Object;

    invoke-static {v4}, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;

    move-result-object v3

    aput-object v3, p0, v4

    invoke-static {v2, v0, p0}, Lde/robv/android/xposed/XposedHelpers;->callMethod(Ljava/lang/Object;Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object p0

    invoke-static {v1, p0}, Lhun/mesh/carwithenhance/hook/AutoPlayAllHook;->addAllStrings(Ljava/util/Set;Ljava/lang/Object;)V
    :try_end_2
    .catchall {:try_start_2 .. :try_end_2} :catchall_1

    .line 641
    :cond_2
    goto :goto_2

    .line 639
    :catchall_1
    move-exception p0

    .line 640
    const/4 v0, 0x0

    const-string v2, "\u8bfb\u53d6\u4f53\u9a8c\u5e94\u7528\u540d\u5355\u5931\u8d25"

    invoke-static {v0, v2, p0}, Lhun/mesh/carwithenhance/hook/AutoPlayAllHook;->logErr(Landroid/content/Context;Ljava/lang/String;Ljava/lang/Throwable;)V

    .line 642
    :goto_2
    new-instance p0, Ljava/util/ArrayList;

    invoke-direct {p0, v1}, Ljava/util/ArrayList;-><init>(Ljava/util/Collection;)V

    return-object p0
.end method

.method private static fiftyFpsLabel(Landroid/content/Context;Ljava/lang/ClassLoader;)Ljava/lang/String;
    .locals 1

    .line 497
    :try_start_0
    const-string v0, "com.carwith.launcher.R$string"

    invoke-static {v0, p1}, Lde/robv/android/xposed/XposedHelpers;->findClass(Ljava/lang/String;Ljava/lang/ClassLoader;)Ljava/lang/Class;

    move-result-object p1

    .line 498
    const-string v0, "text_settings_fps_60"

    invoke-virtual {p1, v0}, Ljava/lang/Class;->getField(Ljava/lang/String;)Ljava/lang/reflect/Field;

    move-result-object p1

    const/4 v0, 0x0

    invoke-virtual {p1, v0}, Ljava/lang/reflect/Field;->getInt(Ljava/lang/Object;)I

    move-result p1

    .line 499
    invoke-virtual {p0, p1}, Landroid/content/Context;->getString(I)Ljava/lang/String;

    move-result-object p0

    .line 500
    if-eqz p0, :cond_0

    invoke-virtual {p0}, Ljava/lang/String;->length()I

    move-result p1

    if-lez p1, :cond_0

    .line 501
    const-string p1, "60"

    const-string v0, "50"

    invoke-virtual {p0, p1, v0}, Ljava/lang/String;->replace(Ljava/lang/CharSequence;Ljava/lang/CharSequence;)Ljava/lang/String;

    move-result-object p0
    :try_end_0
    .catchall {:try_start_0 .. :try_end_0} :catchall_0

    return-object p0

    .line 504
    :cond_0
    goto :goto_0

    .line 503
    :catchall_0
    move-exception p0

    .line 505
    :goto_0
    const-string p0, "50FPS"

    return-object p0
.end method

.method private static findController(Landroid/content/Context;Ljava/lang/String;)Landroid/media/session/MediaController;
    .locals 3

    .line 2437
    const/4 v0, 0x0

    :try_start_0
    const-string v1, "media_session"

    .line 2438
    invoke-virtual {p0, v1}, Landroid/content/Context;->getSystemService(Ljava/lang/String;)Ljava/lang/Object;

    move-result-object p0

    check-cast p0, Landroid/media/session/MediaSessionManager;

    .line 2439
    if-nez p0, :cond_0

    .line 2440
    return-object v0

    .line 2442
    :cond_0
    invoke-virtual {p0, v0}, Landroid/media/session/MediaSessionManager;->getActiveSessions(Landroid/content/ComponentName;)Ljava/util/List;

    move-result-object p0

    .line 2443
    if-nez p0, :cond_1

    .line 2444
    return-object v0

    .line 2446
    :cond_1
    invoke-interface {p0}, Ljava/util/List;->iterator()Ljava/util/Iterator;

    move-result-object p0

    :goto_0
    invoke-interface {p0}, Ljava/util/Iterator;->hasNext()Z

    move-result v1

    if-eqz v1, :cond_3

    invoke-interface {p0}, Ljava/util/Iterator;->next()Ljava/lang/Object;

    move-result-object v1

    check-cast v1, Landroid/media/session/MediaController;

    .line 2447
    if-eqz v1, :cond_2

    if-eqz p1, :cond_2

    .line 2448
    invoke-virtual {v1}, Landroid/media/session/MediaController;->getPackageName()Ljava/lang/String;

    move-result-object v2

    invoke-virtual {p1, v2}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v2
    :try_end_0
    .catchall {:try_start_0 .. :try_end_0} :catchall_0

    if-eqz v2, :cond_2

    .line 2449
    return-object v1

    .line 2451
    :cond_2
    goto :goto_0

    .line 2453
    :cond_3
    goto :goto_1

    .line 2452
    :catchall_0
    move-exception p0

    .line 2454
    :goto_1
    return-object v0
.end method

.method private static findFpsOptions(Ljava/lang/Object;I)Ljava/lang/String;
    .locals 4

    .line 510
    const/4 v0, 0x0

    if-eqz p0, :cond_b

    const/4 v1, 0x4

    if-le p1, v1, :cond_0

    goto/16 :goto_4

    .line 513
    :cond_0
    instance-of v1, p0, Ljava/util/List;

    if-eqz v1, :cond_3

    .line 514
    check-cast p0, Ljava/util/List;

    invoke-interface {p0}, Ljava/util/List;->iterator()Ljava/util/Iterator;

    move-result-object p0

    :goto_0
    invoke-interface {p0}, Ljava/util/Iterator;->hasNext()Z

    move-result v1

    if-eqz v1, :cond_2

    invoke-interface {p0}, Ljava/util/Iterator;->next()Ljava/lang/Object;

    move-result-object v1

    .line 515
    add-int/lit8 v2, p1, 0x1

    invoke-static {v1, v2}, Lhun/mesh/carwithenhance/hook/AutoPlayAllHook;->findFpsOptions(Ljava/lang/Object;I)Ljava/lang/String;

    move-result-object v1

    .line 516
    if-eqz v1, :cond_1

    .line 517
    return-object v1

    .line 519
    :cond_1
    goto :goto_0

    .line 520
    :cond_2
    return-object v0

    .line 522
    :cond_3
    instance-of v1, p0, Ljava/util/Map;

    if-nez v1, :cond_4

    .line 523
    return-object v0

    .line 525
    :cond_4
    check-cast p0, Ljava/util/Map;

    .line 526
    const-string v1, "key"

    invoke-interface {p0, v1}, Ljava/util/Map;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v1

    .line 527
    const-string v2, "value"

    invoke-interface {p0, v2}, Ljava/util/Map;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v2

    .line 528
    const-string v3, "user_setting_framerate"

    invoke-virtual {v3, v1}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v1

    if-eqz v1, :cond_8

    instance-of v1, v2, Ljava/util/List;

    if-eqz v1, :cond_8

    .line 529
    new-instance p0, Ljava/lang/StringBuilder;

    invoke-direct {p0}, Ljava/lang/StringBuilder;-><init>()V

    .line 530
    check-cast v2, Ljava/util/List;

    invoke-interface {v2}, Ljava/util/List;->iterator()Ljava/util/Iterator;

    move-result-object p1

    :goto_1
    invoke-interface {p1}, Ljava/util/Iterator;->hasNext()Z

    move-result v0

    if-eqz v0, :cond_7

    invoke-interface {p1}, Ljava/util/Iterator;->next()Ljava/lang/Object;

    move-result-object v0

    .line 531
    instance-of v1, v0, Ljava/util/Map;

    if-eqz v1, :cond_5

    .line 532
    check-cast v0, Ljava/util/Map;

    const-string v1, "title"

    invoke-interface {v0, v1}, Ljava/util/Map;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v0

    goto :goto_2

    :cond_5
    nop

    .line 533
    :goto_2
    invoke-virtual {p0}, Ljava/lang/StringBuilder;->length()I

    move-result v1

    if-lez v1, :cond_6

    .line 534
    const/16 v1, 0x2f

    invoke-virtual {p0, v1}, Ljava/lang/StringBuilder;->append(C)Ljava/lang/StringBuilder;

    .line 536
    :cond_6
    invoke-virtual {p0, v0}, Ljava/lang/StringBuilder;->append(Ljava/lang/Object;)Ljava/lang/StringBuilder;

    .line 537
    goto :goto_1

    .line 538
    :cond_7
    invoke-virtual {p0}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object p0

    return-object p0

    .line 540
    :cond_8
    invoke-interface {p0}, Ljava/util/Map;->values()Ljava/util/Collection;

    move-result-object p0

    invoke-interface {p0}, Ljava/util/Collection;->iterator()Ljava/util/Iterator;

    move-result-object p0

    :goto_3
    invoke-interface {p0}, Ljava/util/Iterator;->hasNext()Z

    move-result v1

    if-eqz v1, :cond_a

    invoke-interface {p0}, Ljava/util/Iterator;->next()Ljava/lang/Object;

    move-result-object v1

    .line 541
    add-int/lit8 v2, p1, 0x1

    invoke-static {v1, v2}, Lhun/mesh/carwithenhance/hook/AutoPlayAllHook;->findFpsOptions(Ljava/lang/Object;I)Ljava/lang/String;

    move-result-object v1

    .line 542
    if-eqz v1, :cond_9

    .line 543
    return-object v1

    .line 545
    :cond_9
    goto :goto_3

    .line 546
    :cond_a
    return-object v0

    .line 511
    :cond_b
    :goto_4
    return-object v0
.end method

.method private static forceStop(Landroid/content/Context;Ljava/lang/String;Ljava/lang/String;)V
    .locals 3

    .line 2509
    if-eqz p1, :cond_0

    .line 2510
    :try_start_0
    sget-object v0, Lhun/mesh/carwithenhance/hook/AutoPlayAllHook;->OWN_KILL_AT:Ljava/util/Map;

    invoke-static {}, Landroid/os/SystemClock;->elapsedRealtime()J

    move-result-wide v1

    invoke-static {v1, v2}, Ljava/lang/Long;->valueOf(J)Ljava/lang/Long;

    move-result-object v1

    invoke-interface {v0, p1, v1}, Ljava/util/Map;->put(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;

    .line 2512
    :cond_0
    const-string v0, "activity"

    invoke-virtual {p0, v0}, Landroid/content/Context;->getSystemService(Ljava/lang/String;)Ljava/lang/Object;

    move-result-object v0

    check-cast v0, Landroid/app/ActivityManager;

    .line 2513
    const-string v1, "forceStopPackage"

    filled-new-array {p1}, [Ljava/lang/Object;

    move-result-object v2

    invoke-static {v0, v1, v2}, Lde/robv/android/xposed/XposedHelpers;->callMethod(Ljava/lang/Object;Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/Object;

    .line 2514
    new-instance v0, Ljava/lang/StringBuilder;

    invoke-direct {v0}, Ljava/lang/StringBuilder;-><init>()V

    const-string v1, "\u5df2\u7ed3\u675f\u64ad\u653e\u5668: "

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v0

    invoke-virtual {v0, p1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v0

    const-string v1, " ("

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v0

    invoke-virtual {v0, p2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object p2

    const-string v0, ")"

    invoke-virtual {p2, v0}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object p2

    invoke-virtual {p2}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object p2

    invoke-static {p0, p2}, Lhun/mesh/carwithenhance/hook/AutoPlayAllHook;->log(Landroid/content/Context;Ljava/lang/String;)V
    :try_end_0
    .catchall {:try_start_0 .. :try_end_0} :catchall_0

    .line 2517
    goto :goto_0

    .line 2515
    :catchall_0
    move-exception p2

    .line 2516
    new-instance v0, Ljava/lang/StringBuilder;

    invoke-direct {v0}, Ljava/lang/StringBuilder;-><init>()V

    const-string v1, "\u7ed3\u675f\u64ad\u653e\u5668\u5931\u8d25: "

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v0

    invoke-virtual {v0, p1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object p1

    invoke-virtual {p1}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object p1

    invoke-static {p0, p1, p2}, Lhun/mesh/carwithenhance/hook/AutoPlayAllHook;->logErr(Landroid/content/Context;Ljava/lang/String;Ljava/lang/Throwable;)V

    .line 2518
    :goto_0
    return-void
.end method

.method private static gracefulStopBeforeKill(Landroid/content/Context;Ljava/lang/String;)V
    .locals 10

    .line 2377
    :try_start_0
    invoke-static {p0, p1}, Lhun/mesh/carwithenhance/hook/AutoPlayAllHook;->findController(Landroid/content/Context;Ljava/lang/String;)Landroid/media/session/MediaController;

    move-result-object v0
    :try_end_0
    .catchall {:try_start_0 .. :try_end_0} :catchall_1

    .line 2378
    const-string v1, ")"

    if-eqz v0, :cond_1

    .line 2379
    :try_start_1
    invoke-static {v0}, Lhun/mesh/carwithenhance/hook/AutoPlayAllHook;->stateOf(Landroid/media/session/MediaController;)I

    move-result v2

    .line 2380
    const/4 v3, 0x3

    if-eq v2, v3, :cond_0

    const/4 v3, 0x6

    if-eq v2, v3, :cond_0

    const/4 v3, 0x2

    if-ne v2, v3, :cond_1

    .line 2383
    :cond_0
    invoke-virtual {v0}, Landroid/media/session/MediaController;->getTransportControls()Landroid/media/session/MediaController$TransportControls;

    move-result-object v0
    :try_end_1
    .catchall {:try_start_1 .. :try_end_1} :catchall_1

    .line 2384
    if-eqz v0, :cond_1

    .line 2386
    :try_start_2
    invoke-virtual {v0}, Landroid/media/session/MediaController$TransportControls;->stop()V

    .line 2387
    new-instance v0, Ljava/lang/StringBuilder;

    invoke-direct {v0}, Ljava/lang/StringBuilder;-><init>()V

    const-string v3, "\u7ed3\u675f\u8fdb\u7a0b\u524d\u5148\u8bf7\u64ad\u653e\u5668\u91ca\u653e\u97f3\u8f68: "

    invoke-virtual {v0, v3}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v0

    invoke-virtual {v0, p1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v0

    const-string v3, " (state="

    invoke-virtual {v0, v3}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v0

    invoke-virtual {v0, v2}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    move-result-object v0

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v0

    invoke-virtual {v0}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v0

    invoke-static {p0, v0}, Lhun/mesh/carwithenhance/hook/AutoPlayAllHook;->log(Landroid/content/Context;Ljava/lang/String;)V
    :try_end_2
    .catchall {:try_start_2 .. :try_end_2} :catchall_0

    .line 2389
    goto :goto_0

    .line 2388
    :catchall_0
    move-exception v0

    .line 2393
    :cond_1
    :goto_0
    :try_start_3
    invoke-static {}, Landroid/os/SystemClock;->elapsedRealtime()J

    move-result-wide v2

    .line 2394
    const-wide/16 v4, 0x4b0

    invoke-static {p0, v4, v5}, Lhun/mesh/carwithenhance/hook/AutoPlayAllHook;->waitAudioSilent(Landroid/content/Context;J)Z

    move-result v0

    .line 2395
    const-wide/16 v4, 0x640

    invoke-static {p0, p1, v4, v5}, Lhun/mesh/carwithenhance/hook/AutoPlayAllHook;->waitSessionReleased(Landroid/content/Context;Ljava/lang/String;J)Z

    move-result v4

    .line 2398
    invoke-static {}, Landroid/os/SystemClock;->elapsedRealtime()J

    move-result-wide v5

    sub-long/2addr v5, v2

    .line 2399
    const-wide/16 v7, 0x320

    cmp-long v9, v5, v7

    if-gez v9, :cond_2

    .line 2400
    sub-long/2addr v7, v5

    invoke-static {v7, v8}, Lhun/mesh/carwithenhance/hook/AutoPlayAllHook;->sleepQuietly(J)V

    .line 2401
    invoke-static {}, Landroid/os/SystemClock;->elapsedRealtime()J

    move-result-wide v5

    sub-long/2addr v5, v2

    .line 2403
    :cond_2
    new-instance v2, Ljava/lang/StringBuilder;

    invoke-direct {v2}, Ljava/lang/StringBuilder;-><init>()V

    const-string v3, "\u7ed3\u675f\u8fdb\u7a0b\u524d\u97f3\u9891\u5df2\u4ea4\u63a5: "

    invoke-virtual {v2, v3}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v2

    invoke-virtual {v2, p1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v2

    const-string v3, " (\u7b49\u4e86 "

    invoke-virtual {v2, v3}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v2

    invoke-virtual {v2, v5, v6}, Ljava/lang/StringBuilder;->append(J)Ljava/lang/StringBuilder;

    move-result-object v2

    const-string v3, "ms, \u97f3\u9891\u8f93\u51fa"

    invoke-virtual {v2, v3}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v2

    .line 2404
    if-eqz v0, :cond_3

    const-string v0, "\u5df2\u9759\u9ed8"

    goto :goto_1

    :cond_3
    const-string v0, "\u4ecd\u6d3b\u52a8"

    :goto_1
    invoke-virtual {v2, v0}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v0

    const-string v2, ", \u5a92\u4f53\u4f1a\u8bdd"

    invoke-virtual {v0, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v0

    .line 2405
    if-eqz v4, :cond_4

    const-string v2, "\u5df2\u91ca\u653e"

    goto :goto_2

    :cond_4
    const-string v2, "\u4ecd\u5728"

    :goto_2
    invoke-virtual {v0, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v0

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v0

    invoke-virtual {v0}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v0

    .line 2403
    invoke-static {p0, v0}, Lhun/mesh/carwithenhance/hook/AutoPlayAllHook;->log(Landroid/content/Context;Ljava/lang/String;)V
    :try_end_3
    .catchall {:try_start_3 .. :try_end_3} :catchall_1

    .line 2408
    goto :goto_3

    .line 2406
    :catchall_1
    move-exception v0

    .line 2407
    new-instance v1, Ljava/lang/StringBuilder;

    invoke-direct {v1}, Ljava/lang/StringBuilder;-><init>()V

    const-string v2, "\u7ed3\u675f\u8fdb\u7a0b\u524d\u7684\u97f3\u9891\u91ca\u653e\u5931\u8d25: "

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v1

    invoke-virtual {v1, p1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object p1

    invoke-virtual {p1}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object p1

    invoke-static {p0, p1, v0}, Lhun/mesh/carwithenhance/hook/AutoPlayAllHook;->logErr(Landroid/content/Context;Ljava/lang/String;Ljava/lang/Throwable;)V

    .line 2409
    :goto_3
    return-void
.end method

.method private static guessPlaybackService(Landroid/content/Context;Ljava/lang/String;)Ljava/lang/String;
    .locals 7

    .line 2763
    const/4 v0, 0x0

    :try_start_0
    invoke-virtual {p0}, Landroid/content/Context;->getPackageManager()Landroid/content/pm/PackageManager;

    move-result-object p0

    .line 2764
    nop

    .line 2765
    const/4 v1, 0x4

    invoke-virtual {p0, p1, v1}, Landroid/content/pm/PackageManager;->getPackageInfo(Ljava/lang/String;I)Landroid/content/pm/PackageInfo;

    move-result-object p0

    .line 2766
    if-eqz p0, :cond_7

    iget-object p1, p0, Landroid/content/pm/PackageInfo;->services:[Landroid/content/pm/ServiceInfo;

    if-nez p1, :cond_0

    goto :goto_4

    .line 2769
    :cond_0
    nop

    .line 2770
    iget-object p0, p0, Landroid/content/pm/PackageInfo;->services:[Landroid/content/pm/ServiceInfo;

    array-length p1, p0

    const/4 v1, 0x0

    move-object v3, v0

    move v2, v1

    :goto_0
    if-ge v2, p1, :cond_6

    aget-object v4, p0, v2

    .line 2771
    if-eqz v4, :cond_5

    iget-object v5, v4, Landroid/content/pm/ServiceInfo;->name:Ljava/lang/String;

    if-nez v5, :cond_1

    .line 2772
    goto :goto_3

    .line 2774
    :cond_1
    iget-object v5, v4, Landroid/content/pm/ServiceInfo;->name:Ljava/lang/String;

    sget-object v6, Ljava/util/Locale;->US:Ljava/util/Locale;

    invoke-virtual {v5, v6}, Ljava/lang/String;->toLowerCase(Ljava/util/Locale;)Ljava/lang/String;

    move-result-object v5

    .line 2775
    const-string v6, "mediaplayback"

    invoke-virtual {v5, v6}, Ljava/lang/String;->contains(Ljava/lang/CharSequence;)Z

    move-result v6

    if-nez v6, :cond_3

    const-string v6, "playbackservice"

    .line 2776
    invoke-virtual {v5, v6}, Ljava/lang/String;->contains(Ljava/lang/CharSequence;)Z

    move-result v6

    if-nez v6, :cond_3

    const-string v6, "playerservice"

    .line 2777
    invoke-virtual {v5, v6}, Ljava/lang/String;->contains(Ljava/lang/CharSequence;)Z

    move-result v6

    if-nez v6, :cond_3

    const-string v6, "musicservice"

    .line 2778
    invoke-virtual {v5, v6}, Ljava/lang/String;->contains(Ljava/lang/CharSequence;)Z

    move-result v6

    if-eqz v6, :cond_2

    goto :goto_1

    :cond_2
    move v6, v1

    goto :goto_2

    :cond_3
    :goto_1
    const/4 v6, 0x1

    .line 2779
    :goto_2
    if-eqz v6, :cond_5

    if-eqz v3, :cond_4

    const-string v6, "playback"

    invoke-virtual {v5, v6}, Ljava/lang/String;->contains(Ljava/lang/CharSequence;)Z

    move-result v5

    if-eqz v5, :cond_5

    .line 2780
    :cond_4
    iget-object v3, v4, Landroid/content/pm/ServiceInfo;->name:Ljava/lang/String;
    :try_end_0
    .catchall {:try_start_0 .. :try_end_0} :catchall_0

    .line 2770
    :cond_5
    :goto_3
    add-int/lit8 v2, v2, 0x1

    goto :goto_0

    .line 2783
    :cond_6
    return-object v3

    .line 2767
    :cond_7
    :goto_4
    return-object v0

    .line 2784
    :catchall_0
    move-exception p0

    .line 2785
    return-object v0
.end method

.method public static handle(Ljava/lang/String;Ljava/lang/ClassLoader;)V
    .locals 7

    .line 2584
    if-eqz p0, :cond_c

    invoke-virtual {p0}, Ljava/lang/String;->length()I

    move-result v0

    if-nez v0, :cond_0

    goto/16 :goto_1

    .line 2587
    :cond_0
    invoke-static {p1}, Lhun/mesh/carwithenhance/utils/AppUtils;->getAppContext(Ljava/lang/ClassLoader;)Landroid/content/Context;

    move-result-object v0

    .line 2588
    if-nez v0, :cond_1

    .line 2589
    return-void

    .line 2591
    :cond_1
    invoke-static {v0}, Lhun/mesh/carwithenhance/hook/AutoPlayAllHook;->setAppContext(Landroid/content/Context;)V

    .line 2592
    invoke-static {v0}, Lhun/mesh/carwithenhance/hook/AutoPlayAllHook;->isEnabled(Landroid/content/Context;)Z

    move-result v1

    if-nez v1, :cond_2

    .line 2593
    return-void

    .line 2596
    :cond_2
    invoke-static {p0}, Lhun/mesh/carwithenhance/hook/AutoPlayAllHook;->markSessionMedia(Ljava/lang/String;)V

    .line 2599
    invoke-static {}, Lhun/mesh/carwithenhance/hook/AutoPlayAllHook;->selectedApp()Ljava/lang/String;

    move-result-object v1

    .line 2600
    if-eqz v1, :cond_3

    invoke-virtual {v1}, Ljava/lang/String;->length()I

    move-result v2

    if-lez v2, :cond_3

    invoke-virtual {v1, p0}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v2

    if-nez v2, :cond_3

    .line 2601
    invoke-static {v0, p0}, Lhun/mesh/carwithenhance/hook/AutoPlayAllHook;->isProcessRunning(Landroid/content/Context;Ljava/lang/String;)Z

    move-result v2

    if-nez v2, :cond_3

    invoke-static {v0, p0}, Lhun/mesh/carwithenhance/hook/AutoPlayAllHook;->hasSession(Landroid/content/Context;Ljava/lang/String;)Z

    move-result v2

    if-nez v2, :cond_3

    .line 2602
    invoke-static {p1, v1}, Lhun/mesh/carwithenhance/hook/AutoPlayAllHook;->isTarget(Ljava/lang/ClassLoader;Ljava/lang/String;)Z

    move-result v2

    if-eqz v2, :cond_3

    .line 2603
    new-instance v2, Ljava/lang/StringBuilder;

    invoke-direct {v2}, Ljava/lang/StringBuilder;-><init>()V

    const-string v3, "CarWith \u8bf7\u6c42\u7684 "

    invoke-virtual {v2, v3}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v2

    invoke-virtual {v2, p0}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object p0

    const-string v2, " \u5df2\u7ed3\u675f\uff0c\u6539\u7528\u5f53\u524d\u9009\u4e2d\u7684 "

    invoke-virtual {p0, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object p0

    invoke-virtual {p0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object p0

    invoke-virtual {p0}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object p0

    invoke-static {v0, p0}, Lhun/mesh/carwithenhance/hook/AutoPlayAllHook;->log(Landroid/content/Context;Ljava/lang/String;)V

    .line 2604
    move-object p0, v1

    .line 2606
    :cond_3
    nop

    .line 2607
    sget-object v1, Lhun/mesh/carwithenhance/hook/AutoPlayAllHook;->sLastAcceptedPkg:Ljava/lang/String;

    if-eqz v1, :cond_4

    invoke-virtual {v1, p0}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v1

    if-nez v1, :cond_5

    .line 2608
    :cond_4
    sget-wide v1, Lhun/mesh/carwithenhance/hook/AutoPlayAllHook;->sRetryGen:J

    const-wide/16 v3, 0x1

    add-long/2addr v1, v3

    sput-wide v1, Lhun/mesh/carwithenhance/hook/AutoPlayAllHook;->sRetryGen:J

    .line 2609
    sput-object p0, Lhun/mesh/carwithenhance/hook/AutoPlayAllHook;->sLastAcceptedPkg:Ljava/lang/String;

    .line 2613
    invoke-static {p1, p0}, Lhun/mesh/carwithenhance/hook/AutoPlayAllHook;->rememberUsedMediaApp(Ljava/lang/ClassLoader;Ljava/lang/String;)V

    .line 2616
    sget-object v1, Lhun/mesh/carwithenhance/hook/AutoPlayAllHook;->sActiveWokePkg:Ljava/lang/String;

    .line 2617
    if-eqz v1, :cond_5

    invoke-virtual {v1, p0}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v2

    if-nez v2, :cond_5

    .line 2618
    const/4 v2, 0x0

    sput-object v2, Lhun/mesh/carwithenhance/hook/AutoPlayAllHook;->sActiveWokePkg:Ljava/lang/String;

    .line 2619
    new-instance v2, Ljava/lang/StringBuilder;

    invoke-direct {v2}, Ljava/lang/StringBuilder;-><init>()V

    const-string v3, "\u5207\u6362\u5230 "

    invoke-virtual {v2, v3}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v2

    invoke-virtual {v2, p0}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v2

    invoke-virtual {v2}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v2

    invoke-static {v0, v1, v2}, Lhun/mesh/carwithenhance/hook/AutoPlayAllHook;->releaseWoke(Landroid/content/Context;Ljava/lang/String;Ljava/lang/String;)V

    .line 2620
    invoke-static {v0, v1, p0}, Lhun/mesh/carwithenhance/hook/AutoPlayAllHook;->markSwitchDropped(Landroid/content/Context;Ljava/lang/String;Ljava/lang/String;)V

    .line 2621
    const/4 v1, 0x1

    goto :goto_0

    .line 2624
    :cond_5
    const/4 v1, 0x0

    :goto_0
    invoke-static {p1, p0}, Lhun/mesh/carwithenhance/hook/AutoPlayAllHook;->isTarget(Ljava/lang/ClassLoader;Ljava/lang/String;)Z

    move-result v2

    if-nez v2, :cond_6

    .line 2625
    new-instance p1, Ljava/lang/StringBuilder;

    invoke-direct {p1}, Ljava/lang/StringBuilder;-><init>()V

    invoke-virtual {p1, p0}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object p0

    const-string p1, " \u4e0d\u5728\u7eed\u64ad\u540d\u5355\u5185\uff0c\u4e0d\u5904\u7406"

    invoke-virtual {p0, p1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object p0

    invoke-virtual {p0}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object p0

    invoke-static {v0, p0}, Lhun/mesh/carwithenhance/hook/AutoPlayAllHook;->log(Landroid/content/Context;Ljava/lang/String;)V

    .line 2626
    return-void

    .line 2628
    :cond_6
    invoke-static {v0, p0}, Lhun/mesh/carwithenhance/hook/AutoPlayAllHook;->hasSession(Landroid/content/Context;Ljava/lang/String;)Z

    move-result v2

    if-eqz v2, :cond_7

    invoke-static {v0, p0}, Lhun/mesh/carwithenhance/hook/AutoPlayAllHook;->isProcessRunning(Landroid/content/Context;Ljava/lang/String;)Z

    move-result v2

    if-eqz v2, :cond_7

    .line 2630
    new-instance v1, Ljava/lang/StringBuilder;

    invoke-direct {v1}, Ljava/lang/StringBuilder;-><init>()V

    invoke-virtual {v1, p0}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v1

    const-string v2, " \u5df2\u6709\u5a92\u4f53\u4f1a\u8bdd\uff0c\u4ea4\u7ed9 CarWith"

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v1

    invoke-virtual {v1}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v1

    invoke-static {v0, v1}, Lhun/mesh/carwithenhance/hook/AutoPlayAllHook;->log(Landroid/content/Context;Ljava/lang/String;)V

    .line 2631
    sput-object p0, Lhun/mesh/carwithenhance/hook/AutoPlayAllHook;->sResumedPkg:Ljava/lang/String;

    .line 2632
    invoke-static {p0}, Lhun/mesh/carwithenhance/hook/AutoPlayAllHook;->learnCurrentMedia(Ljava/lang/String;)V

    .line 2633
    invoke-static {p1, p0}, Lhun/mesh/carwithenhance/hook/AutoPlayAllHook;->resumeCarWith(Ljava/lang/ClassLoader;Ljava/lang/String;)V

    .line 2634
    return-void

    .line 2636
    :cond_7
    if-nez v1, :cond_8

    invoke-static {v0, p0}, Lhun/mesh/carwithenhance/hook/AutoPlayAllHook;->playingOtherMedia(Landroid/content/Context;Ljava/lang/String;)Z

    move-result v1

    if-eqz v1, :cond_8

    .line 2638
    new-instance p1, Ljava/lang/StringBuilder;

    invoke-direct {p1}, Ljava/lang/StringBuilder;-><init>()V

    const-string v1, "\u5df2\u6709\u5176\u5b83\u97f3\u4e50\u5728\u64ad\uff0c\u8df3\u8fc7\u5524\u9192 "

    invoke-virtual {p1, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object p1

    invoke-virtual {p1, p0}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object p0

    invoke-virtual {p0}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object p0

    invoke-static {v0, p0}, Lhun/mesh/carwithenhance/hook/AutoPlayAllHook;->log(Landroid/content/Context;Ljava/lang/String;)V

    .line 2639
    return-void

    .line 2641
    :cond_8
    invoke-static {v0, p0}, Lhun/mesh/carwithenhance/hook/AutoPlayAllHook;->blockedByUserKill(Landroid/content/Context;Ljava/lang/String;)Z

    move-result v1

    if-eqz v1, :cond_9

    .line 2643
    new-instance p1, Ljava/lang/StringBuilder;

    invoke-direct {p1}, Ljava/lang/StringBuilder;-><init>()V

    invoke-virtual {p1, p0}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object p0

    const-string p1, " \u662f\u4f60\u624b\u52a8\u7ed3\u675f\u7684\uff0c\u672c\u6b21\u8fde\u63a5\u4e0d\u81ea\u52a8\u62c9\u8d77"

    invoke-virtual {p0, p1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object p0

    invoke-virtual {p0}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object p0

    invoke-static {v0, p0}, Lhun/mesh/carwithenhance/hook/AutoPlayAllHook;->log(Landroid/content/Context;Ljava/lang/String;)V

    .line 2644
    return-void

    .line 2646
    :cond_9
    invoke-static {p0}, Lhun/mesh/carwithenhance/hook/AutoPlayAllHook;->blockedBySwitchDrop(Ljava/lang/String;)Z

    move-result v1

    if-eqz v1, :cond_a

    .line 2648
    new-instance p1, Ljava/lang/StringBuilder;

    invoke-direct {p1}, Ljava/lang/StringBuilder;-><init>()V

    invoke-virtual {p1, p0}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object p0

    const-string p1, " \u662f\u4f60\u5728\u8f66\u673a\u4e0a\u5207\u8d70\u7684\uff0c\u672c\u6b21\u8fde\u63a5\u4e0d\u81ea\u52a8\u62c9\u8d77"

    invoke-virtual {p0, p1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object p0

    invoke-virtual {p0}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object p0

    invoke-static {v0, p0}, Lhun/mesh/carwithenhance/hook/AutoPlayAllHook;->log(Landroid/content/Context;Ljava/lang/String;)V

    .line 2649
    return-void

    .line 2652
    :cond_a
    invoke-static {}, Landroid/os/SystemClock;->elapsedRealtime()J

    move-result-wide v1

    .line 2653
    sget-object v3, Lhun/mesh/carwithenhance/hook/AutoPlayAllHook;->sLastPkg:Ljava/lang/String;

    invoke-virtual {p0, v3}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v3

    if-eqz v3, :cond_b

    sget-wide v3, Lhun/mesh/carwithenhance/hook/AutoPlayAllHook;->sLastWake:J

    sub-long v3, v1, v3

    const-wide/16 v5, 0x7530

    cmp-long v3, v3, v5

    if-gez v3, :cond_b

    .line 2655
    invoke-static {p1, p0}, Lhun/mesh/carwithenhance/hook/AutoPlayAllHook;->resumeCarWith(Ljava/lang/ClassLoader;Ljava/lang/String;)V

    .line 2656
    return-void

    .line 2658
    :cond_b
    sput-object p0, Lhun/mesh/carwithenhance/hook/AutoPlayAllHook;->sLastPkg:Ljava/lang/String;

    .line 2659
    sput-wide v1, Lhun/mesh/carwithenhance/hook/AutoPlayAllHook;->sLastWake:J

    .line 2661
    invoke-static {v0, p0}, Lhun/mesh/carwithenhance/hook/AutoPlayAllHook;->wakeInBackground(Landroid/content/Context;Ljava/lang/String;)V

    .line 2662
    invoke-static {p1, p0}, Lhun/mesh/carwithenhance/hook/AutoPlayAllHook;->startRetryLoop(Ljava/lang/ClassLoader;Ljava/lang/String;)V

    .line 2663
    return-void

    .line 2585
    :cond_c
    :goto_1
    return-void
.end method

.method private static hasSession(Landroid/content/Context;Ljava/lang/String;)Z
    .locals 2

    .line 3037
    const/4 v0, 0x0

    :try_start_0
    const-string v1, "media_session"

    .line 3038
    invoke-virtual {p0, v1}, Landroid/content/Context;->getSystemService(Ljava/lang/String;)Ljava/lang/Object;

    move-result-object p0

    check-cast p0, Landroid/media/session/MediaSessionManager;

    .line 3039
    if-nez p0, :cond_0

    .line 3040
    return v0

    .line 3042
    :cond_0
    const/4 v1, 0x0

    invoke-virtual {p0, v1}, Landroid/media/session/MediaSessionManager;->getActiveSessions(Landroid/content/ComponentName;)Ljava/util/List;

    move-result-object p0

    .line 3043
    if-nez p0, :cond_1

    .line 3044
    return v0

    .line 3046
    :cond_1
    invoke-interface {p0}, Ljava/util/List;->iterator()Ljava/util/Iterator;

    move-result-object p0

    :goto_0
    invoke-interface {p0}, Ljava/util/Iterator;->hasNext()Z

    move-result v1

    if-eqz v1, :cond_3

    invoke-interface {p0}, Ljava/util/Iterator;->next()Ljava/lang/Object;

    move-result-object v1

    check-cast v1, Landroid/media/session/MediaController;

    .line 3047
    if-eqz v1, :cond_2

    invoke-virtual {v1}, Landroid/media/session/MediaController;->getPackageName()Ljava/lang/String;

    move-result-object v1

    invoke-virtual {p1, v1}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v1
    :try_end_0
    .catchall {:try_start_0 .. :try_end_0} :catchall_0

    if-eqz v1, :cond_2

    .line 3048
    const/4 p0, 0x1

    return p0

    .line 3050
    :cond_2
    goto :goto_0

    .line 3053
    :cond_3
    goto :goto_1

    .line 3051
    :catchall_0
    move-exception p0

    .line 3054
    :goto_1
    return v0
.end method

.method private static hookCastState(Ljava/lang/ClassLoader;)V
    .locals 4

    .line 1052
    const/4 v0, 0x0

    :try_start_0
    sget-object v1, Lhun/mesh/carwithenhance/dexkit/CarWithDexKitManager;->INSTANCE:Lhun/mesh/carwithenhance/dexkit/CarWithDexKitManager;

    .line 1054
    invoke-virtual {v1}, Lhun/mesh/carwithenhance/dexkit/CarWithDexKitManager;->getCastControllerClass()Ljava/lang/String;

    move-result-object v2

    .line 1055
    if-eqz v2, :cond_2

    invoke-virtual {v2}, Ljava/lang/String;->isEmpty()Z

    move-result v3

    if-eqz v3, :cond_0

    goto :goto_0

    .line 1059
    :cond_0
    invoke-static {v2, p0}, Lde/robv/android/xposed/XposedHelpers;->findClass(Ljava/lang/String;Ljava/lang/ClassLoader;)Ljava/lang/Class;

    move-result-object p0

    .line 1060
    invoke-virtual {v1}, Lhun/mesh/carwithenhance/dexkit/CarWithDexKitManager;->getCastControllerConnectMethod()Ljava/lang/String;

    move-result-object v1

    .line 1061
    if-eqz v1, :cond_1

    invoke-virtual {v1}, Ljava/lang/String;->isEmpty()Z

    move-result v2

    if-nez v2, :cond_1

    .line 1062
    new-instance v2, Lhun/mesh/carwithenhance/hook/AutoPlayAllHook$13;

    invoke-direct {v2}, Lhun/mesh/carwithenhance/hook/AutoPlayAllHook$13;-><init>()V

    invoke-static {p0, v1, v2}, Lde/robv/android/xposed/XposedBridge;->hookAllMethods(Ljava/lang/Class;Ljava/lang/String;Lde/robv/android/xposed/XC_MethodHook;)Ljava/util/Set;

    .line 1069
    :cond_1
    const-string p0, "\u8fde\u63a5\u72b6\u6001\u94a9\u5b50\u5df2\u6ce8\u518c\uff08\u4ec5\u7eed\u64ad\uff09"

    invoke-static {v0, p0}, Lhun/mesh/carwithenhance/hook/AutoPlayAllHook;->log(Landroid/content/Context;Ljava/lang/String;)V

    .line 1072
    goto :goto_1

    .line 1056
    :cond_2
    :goto_0
    const-string p0, "\u672a\u627e\u5230 CastController\uff0c\u8df3\u8fc7\u8fde\u63a5\u72b6\u6001\u94a9\u5b50"

    invoke-static {v0, p0}, Lhun/mesh/carwithenhance/hook/AutoPlayAllHook;->log(Landroid/content/Context;Ljava/lang/String;)V
    :try_end_0
    .catchall {:try_start_0 .. :try_end_0} :catchall_0

    .line 1057
    return-void

    .line 1070
    :catchall_0
    move-exception p0

    .line 1071
    const-string v1, "\u8fde\u63a5\u72b6\u6001\u94a9\u5b50\u6ce8\u518c\u5931\u8d25"

    invoke-static {v0, v1, p0}, Lhun/mesh/carwithenhance/hook/AutoPlayAllHook;->logErr(Landroid/content/Context;Ljava/lang/String;Ljava/lang/Throwable;)V

    .line 1073
    :goto_1
    return-void
.end method

.method private static hookExperienceApps(Ljava/lang/ClassLoader;)V
    .locals 6

    .line 587
    const/4 v0, 0x0

    :try_start_0
    sget-object v1, Lhun/mesh/carwithenhance/hook/AutoPlayAllHook;->sAppCtx:Landroid/content/Context;

    if-eqz v1, :cond_0

    goto :goto_0

    :cond_0
    invoke-static {p0}, Lhun/mesh/carwithenhance/utils/AppUtils;->getAppContext(Ljava/lang/ClassLoader;)Landroid/content/Context;

    move-result-object v1

    .line 588
    :goto_0
    if-eqz v1, :cond_2

    const-string v2, "com.miui.carlink"

    invoke-virtual {v1}, Landroid/content/Context;->getPackageName()Ljava/lang/String;

    move-result-object v1

    invoke-virtual {v2, v1}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v1

    if-nez v1, :cond_1

    goto :goto_1

    .line 592
    :cond_1
    invoke-static {p0}, Lhun/mesh/carwithenhance/dexkit/GenericAutoPlayHooks;->resolveExperienceToNormal(Ljava/lang/ClassLoader;)[Ljava/lang/Object;

    move-result-object v1

    const/4 v2, 0x0

    aget-object v3, v1, v2

    check-cast v3, Ljava/lang/Class;

    const/4 v2, 0x1

    aget-object v1, v1, v2

    check-cast v1, Ljava/lang/String;

    new-instance v4, Lhun/mesh/carwithenhance/hook/AutoPlayAllHook$6;

    invoke-direct {v4, p0}, Lhun/mesh/carwithenhance/hook/AutoPlayAllHook$6;-><init>(Ljava/lang/ClassLoader;)V

    invoke-static {v3, v1, v4}, Lde/robv/android/xposed/XposedBridge;->hookAllMethods(Ljava/lang/Class;Ljava/lang/String;Lde/robv/android/xposed/XC_MethodHook;)Ljava/util/Set;

    .line 610
    const-string p0, "\u4f53\u9a8c\u5e94\u7528\u8f6c\u666e\u901a\u5217\u8868\u7684\u94a9\u5b50\u5df2\u6ce8\u518c"

    invoke-static {v0, p0}, Lhun/mesh/carwithenhance/hook/AutoPlayAllHook;->log(Landroid/content/Context;Ljava/lang/String;)V
    :try_end_0
    .catchall {:try_start_0 .. :try_end_0} :catchall_0

    .line 613
    goto :goto_2

    .line 590
    :cond_2
    :goto_1
    return-void

    .line 611
    :catchall_0
    move-exception p0

    .line 612
    const-string v1, "\u4f53\u9a8c\u5e94\u7528\u8f6c\u666e\u901a\u5217\u8868\u7684\u94a9\u5b50\u6ce8\u518c\u5931\u8d25"

    invoke-static {v0, v1, p0}, Lhun/mesh/carwithenhance/hook/AutoPlayAllHook;->logErr(Landroid/content/Context;Ljava/lang/String;Ljava/lang/Throwable;)V

    .line 614
    :goto_2
    return-void
.end method

.method private static hookFiftyFps(Ljava/lang/ClassLoader;)V
    .locals 9

    .line 340
    sget-object v0, Lhun/mesh/carwithenhance/hook/AutoPlayAllHook;->sAppCtx:Landroid/content/Context;

    if-eqz v0, :cond_0

    goto :goto_0

    :cond_0
    invoke-static {p0}, Lhun/mesh/carwithenhance/utils/AppUtils;->getAppContext(Ljava/lang/ClassLoader;)Landroid/content/Context;

    move-result-object v0

    .line 341
    :goto_0
    if-eqz v0, :cond_2

    const-string v1, "com.miui.carlink"

    invoke-virtual {v0}, Landroid/content/Context;->getPackageName()Ljava/lang/String;

    move-result-object v2

    invoke-virtual {v1, v2}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v1

    if-nez v1, :cond_1

    goto :goto_3

    .line 344
    :cond_1
    invoke-static {v0}, Lhun/mesh/carwithenhance/hook/AutoPlayAllHook;->setAppContext(Landroid/content/Context;)V

    .line 346
    const/4 v0, 0x2

    const/4 v1, 0x1

    const/4 v2, 0x0

    const/4 v3, 0x3

    const/4 v4, 0x0

    :try_start_0
    const-string v5, "com.google.gson.Gson"

    const-string v6, "fromJson"

    new-array v7, v3, [Ljava/lang/Object;

    const-class v8, Ljava/lang/String;

    aput-object v8, v7, v2

    const-class v8, Ljava/lang/reflect/Type;

    aput-object v8, v7, v1

    new-instance v8, Lhun/mesh/carwithenhance/hook/AutoPlayAllHook$2;

    invoke-direct {v8}, Lhun/mesh/carwithenhance/hook/AutoPlayAllHook$2;-><init>()V

    aput-object v8, v7, v0

    invoke-static {v5, p0, v6, v7}, Lde/robv/android/xposed/XposedHelpers;->findAndHookMethod(Ljava/lang/String;Ljava/lang/ClassLoader;Ljava/lang/String;[Ljava/lang/Object;)Lde/robv/android/xposed/XC_MethodHook$Unhook;

    .line 374
    const-string v5, "\u5e27\u7387\u83dc\u5355\u6ce8\u5165\u94a9\u5b50\u5df2\u6ce8\u518c"

    invoke-static {v4, v5}, Lhun/mesh/carwithenhance/hook/AutoPlayAllHook;->log(Landroid/content/Context;Ljava/lang/String;)V
    :try_end_0
    .catchall {:try_start_0 .. :try_end_0} :catchall_0

    .line 377
    goto :goto_1

    .line 375
    :catchall_0
    move-exception v5

    .line 376
    const-string v6, "\u5e27\u7387\u83dc\u5355\u6ce8\u5165\u94a9\u5b50\u6ce8\u518c\u5931\u8d25"

    invoke-static {v4, v6, v5}, Lhun/mesh/carwithenhance/hook/AutoPlayAllHook;->logErr(Landroid/content/Context;Ljava/lang/String;Ljava/lang/Throwable;)V

    .line 378
    :goto_1
    invoke-static {p0}, Lhun/mesh/carwithenhance/hook/AutoPlayAllHook;->hookFpsConvert(Ljava/lang/ClassLoader;)V

    .line 383
    :try_start_1
    const-string v5, "p6.o0"

    const-string v6, "l"

    new-array v3, v3, [Ljava/lang/Object;

    const-class v7, Landroid/content/Context;

    aput-object v7, v3, v2

    const-class v2, Ljava/lang/String;

    aput-object v2, v3, v1

    new-instance v1, Lhun/mesh/carwithenhance/hook/AutoPlayAllHook$3;

    invoke-direct {v1, p0}, Lhun/mesh/carwithenhance/hook/AutoPlayAllHook$3;-><init>(Ljava/lang/ClassLoader;)V

    aput-object v1, v3, v0

    invoke-static {v5, p0, v6, v3}, Lde/robv/android/xposed/XposedHelpers;->findAndHookMethod(Ljava/lang/String;Ljava/lang/ClassLoader;Ljava/lang/String;[Ljava/lang/Object;)Lde/robv/android/xposed/XC_MethodHook$Unhook;

    .line 409
    const-string p0, "\u5e27\u7387\u83dc\u5355\u5916\u5c42\u6587\u5b57\u94a9\u5b50\u5df2\u6ce8\u518c"

    invoke-static {v4, p0}, Lhun/mesh/carwithenhance/hook/AutoPlayAllHook;->log(Landroid/content/Context;Ljava/lang/String;)V
    :try_end_1
    .catchall {:try_start_1 .. :try_end_1} :catchall_1

    .line 412
    goto :goto_2

    .line 410
    :catchall_1
    move-exception p0

    .line 411
    const-string v0, "\u5e27\u7387\u83dc\u5355\u5916\u5c42\u6587\u5b57\u94a9\u5b50\u6ce8\u518c\u5931\u8d25"

    invoke-static {v4, v0, p0}, Lhun/mesh/carwithenhance/hook/AutoPlayAllHook;->logErr(Landroid/content/Context;Ljava/lang/String;Ljava/lang/Throwable;)V

    .line 413
    :goto_2
    return-void

    .line 342
    :cond_2
    :goto_3
    return-void
.end method

.method private static hookFpsConvert(Ljava/lang/ClassLoader;)V
    .locals 7

    .line 425
    const/4 v0, 0x0

    const/4 v1, 0x1

    const/4 v2, 0x0

    :try_start_0
    const-string v3, "com.carwith.common.utils.d"

    const-string v4, "d"

    new-array v5, v1, [Ljava/lang/Object;

    new-instance v6, Lhun/mesh/carwithenhance/hook/AutoPlayAllHook$4;

    invoke-direct {v6}, Lhun/mesh/carwithenhance/hook/AutoPlayAllHook$4;-><init>()V

    aput-object v6, v5, v0

    invoke-static {v3, p0, v4, v5}, Lde/robv/android/xposed/XposedHelpers;->findAndHookMethod(Ljava/lang/String;Ljava/lang/ClassLoader;Ljava/lang/String;[Ljava/lang/Object;)Lde/robv/android/xposed/XC_MethodHook$Unhook;

    .line 446
    const-string v3, "\u5e27\u7387\u6362\u7b97\u94a9\u5b50\u5df2\u6ce8\u518c\uff08\u65b0\u7248 com.carwith.common.utils.d#d\uff09"

    invoke-static {v2, v3}, Lhun/mesh/carwithenhance/hook/AutoPlayAllHook;->log(Landroid/content/Context;Ljava/lang/String;)V
    :try_end_0
    .catchall {:try_start_0 .. :try_end_0} :catchall_0

    .line 448
    return-void

    .line 449
    :catchall_0
    move-exception v3

    .line 450
    const-string v3, "\u65b0\u7248\u5e27\u7387\u6362\u7b97\u5165\u53e3\u4e0d\u5b58\u5728\uff0c\u5c1d\u8bd5\u65e7\u7248\u5165\u53e3"

    invoke-static {v2, v3}, Lhun/mesh/carwithenhance/hook/AutoPlayAllHook;->log(Landroid/content/Context;Ljava/lang/String;)V

    .line 454
    :try_start_1
    const-string v3, "com.carwith.common.utils.s2"

    const-string v4, "b"

    const/4 v5, 0x2

    new-array v5, v5, [Ljava/lang/Object;

    sget-object v6, Ljava/lang/Integer;->TYPE:Ljava/lang/Class;

    aput-object v6, v5, v0

    new-instance v0, Lhun/mesh/carwithenhance/hook/AutoPlayAllHook$5;

    invoke-direct {v0}, Lhun/mesh/carwithenhance/hook/AutoPlayAllHook$5;-><init>()V

    aput-object v0, v5, v1

    invoke-static {v3, p0, v4, v5}, Lde/robv/android/xposed/XposedHelpers;->findAndHookMethod(Ljava/lang/String;Ljava/lang/ClassLoader;Ljava/lang/String;[Ljava/lang/Object;)Lde/robv/android/xposed/XC_MethodHook$Unhook;

    .line 473
    const-string p0, "\u5e27\u7387\u6362\u7b97\u94a9\u5b50\u5df2\u6ce8\u518c\uff08\u65e7\u7248 com.carwith.common.utils.s2#b\uff09"

    invoke-static {v2, p0}, Lhun/mesh/carwithenhance/hook/AutoPlayAllHook;->log(Landroid/content/Context;Ljava/lang/String;)V
    :try_end_1
    .catchall {:try_start_1 .. :try_end_1} :catchall_1

    .line 477
    goto :goto_0

    .line 475
    :catchall_1
    move-exception p0

    .line 476
    const-string v0, "\u5e27\u7387\u6362\u7b97\u94a9\u5b50\u6ce8\u518c\u5931\u8d25\uff08\u65b0\u8001\u5165\u53e3\u90fd\u6ca1\u627e\u5230\uff09"

    invoke-static {v2, v0, p0}, Lhun/mesh/carwithenhance/hook/AutoPlayAllHook;->logErr(Landroid/content/Context;Ljava/lang/String;Ljava/lang/Throwable;)V

    .line 478
    :goto_0
    return-void
.end method

.method private static hookMediaAppOpen(Ljava/lang/ClassLoader;)V
    .locals 9

    .line 891
    const/4 v0, 0x3

    const/4 v1, 0x1

    const/4 v2, 0x2

    const/4 v3, 0x0

    const/4 v4, 0x0

    :try_start_0
    invoke-static {p0}, Lhun/mesh/carwithenhance/dexkit/GenericAutoPlayHooks;->resolveMediaAppOpen(Ljava/lang/ClassLoader;)[Ljava/lang/Object;

    move-result-object v5

    const/4 v6, 0x0

    aget-object v7, v5, v6

    check-cast v7, Ljava/lang/Class;

    const/4 v6, 0x1

    aget-object v5, v5, v6

    check-cast v5, Ljava/lang/String;

    new-instance v8, Lhun/mesh/carwithenhance/hook/AutoPlayAllHook$10;

    invoke-direct {v8}, Lhun/mesh/carwithenhance/hook/AutoPlayAllHook$10;-><init>()V

    invoke-static {v7, v5, v8}, Lde/robv/android/xposed/XposedBridge;->hookAllMethods(Ljava/lang/Class;Ljava/lang/String;Lde/robv/android/xposed/XC_MethodHook;)Ljava/util/Set;

    .line 905
    const-string v5, "\u8f66\u673a\u7aef\u9009\u62e9\u5a92\u4f53\u5e94\u7528\u7684\u94a9\u5b50\u5df2\u6ce8\u518c"

    invoke-static {v4, v5}, Lhun/mesh/carwithenhance/hook/AutoPlayAllHook;->log(Landroid/content/Context;Ljava/lang/String;)V
    :try_end_0
    .catchall {:try_start_0 .. :try_end_0} :catchall_0

    .line 908
    goto :goto_0

    .line 906
    :catchall_0
    move-exception v5

    .line 907
    const-string v6, "\u8f66\u673a\u7aef\u9009\u62e9\u5a92\u4f53\u5e94\u7528\u7684\u94a9\u5b50\u6ce8\u518c\u5931\u8d25"

    invoke-static {v4, v6, v5}, Lhun/mesh/carwithenhance/hook/AutoPlayAllHook;->logErr(Landroid/content/Context;Ljava/lang/String;Ljava/lang/Throwable;)V

    .line 910
    :goto_0
    :try_start_1
    const-string v5, "com.carwith.launcher.ams.UCarFragmentManager"

    const-string v6, "getFragmentTagFromPackageName"

    new-array v7, v2, [Ljava/lang/Object;

    const-class v8, Ljava/lang/String;

    aput-object v8, v7, v3

    new-instance v8, Lhun/mesh/carwithenhance/hook/AutoPlayAllHook$11;

    invoke-direct {v8}, Lhun/mesh/carwithenhance/hook/AutoPlayAllHook$11;-><init>()V

    aput-object v8, v7, v1

    invoke-static {v5, p0, v6, v7}, Lde/robv/android/xposed/XposedHelpers;->findAndHookMethod(Ljava/lang/String;Ljava/lang/ClassLoader;Ljava/lang/String;[Ljava/lang/Object;)Lde/robv/android/xposed/XC_MethodHook$Unhook;

    .line 924
    const-string v5, "\u8f66\u673a\u7aef\u6253\u5f00\u5e94\u7528\u94a9\u5b50\u5df2\u6ce8\u518c"

    invoke-static {v4, v5}, Lhun/mesh/carwithenhance/hook/AutoPlayAllHook;->log(Landroid/content/Context;Ljava/lang/String;)V
    :try_end_1
    .catchall {:try_start_1 .. :try_end_1} :catchall_1

    .line 927
    goto :goto_1

    .line 925
    :catchall_1
    move-exception v5

    .line 926
    const-string v6, "\u8f66\u673a\u7aef\u6253\u5f00\u5e94\u7528\u94a9\u5b50\u6ce8\u518c\u5931\u8d25"

    invoke-static {v4, v6, v5}, Lhun/mesh/carwithenhance/hook/AutoPlayAllHook;->logErr(Landroid/content/Context;Ljava/lang/String;Ljava/lang/Throwable;)V

    .line 929
    :goto_1
    :try_start_2
    invoke-static {p0}, Lhun/mesh/carwithenhance/dexkit/GenericAutoPlayHooks;->resolveColdStartAntiKill(Ljava/lang/ClassLoader;)[Ljava/lang/Object;

    move-result-object v5

    const/4 v6, 0x0

    aget-object v7, v5, v6

    check-cast v7, Ljava/lang/Class;

    const/4 v6, 0x1

    aget-object v5, v5, v6

    check-cast v5, Ljava/lang/String;

    new-instance v8, Lhun/mesh/carwithenhance/hook/AutoPlayAllHook$12;

    invoke-direct {v8, p0}, Lhun/mesh/carwithenhance/hook/AutoPlayAllHook$12;-><init>(Ljava/lang/ClassLoader;)V

    invoke-static {v7, v5, v8}, Lde/robv/android/xposed/XposedBridge;->hookAllMethods(Ljava/lang/Class;Ljava/lang/String;Lde/robv/android/xposed/XC_MethodHook;)Ljava/util/Set;

    .line 970
    const-string p0, "\u963b\u6b62\u8bef\u6740\u51b7\u542f\u52a8\u5e94\u7528\u7684\u94a9\u5b50\u5df2\u6ce8\u518c"

    invoke-static {v4, p0}, Lhun/mesh/carwithenhance/hook/AutoPlayAllHook;->log(Landroid/content/Context;Ljava/lang/String;)V
    :try_end_2
    .catchall {:try_start_2 .. :try_end_2} :catchall_2

    .line 973
    goto :goto_2

    .line 971
    :catchall_2
    move-exception p0

    .line 972
    const-string v0, "\u963b\u6b62\u8bef\u6740\u51b7\u542f\u52a8\u5e94\u7528\u7684\u94a9\u5b50\u6ce8\u518c\u5931\u8d25"

    invoke-static {v4, v0, p0}, Lhun/mesh/carwithenhance/hook/AutoPlayAllHook;->logErr(Landroid/content/Context;Ljava/lang/String;Ljava/lang/Throwable;)V

    .line 974
    :goto_2
    return-void
.end method

.method private static hookMediaAppPref(Ljava/lang/ClassLoader;)V
    .locals 3

    .line 847
    const/4 v0, 0x0

    :try_start_0
    invoke-static {p0}, Lhun/mesh/carwithenhance/utils/AppUtils;->getAppContext(Ljava/lang/ClassLoader;)Landroid/content/Context;

    move-result-object p0

    .line 848
    if-nez p0, :cond_0

    .line 849
    return-void

    .line 851
    :cond_0
    invoke-static {p0}, Lhun/mesh/carwithenhance/hook/AutoPlayAllHook;->setAppContext(Landroid/content/Context;)V

    .line 852
    const-string v1, "file_prefer_app_091703"

    const/4 v2, 0x0

    invoke-virtual {p0, v1, v2}, Landroid/content/Context;->getSharedPreferences(Ljava/lang/String;I)Landroid/content/SharedPreferences;

    move-result-object p0

    .line 853
    new-instance v1, Lhun/mesh/carwithenhance/hook/AutoPlayAllHook$9;

    invoke-direct {v1}, Lhun/mesh/carwithenhance/hook/AutoPlayAllHook$9;-><init>()V

    sput-object v1, Lhun/mesh/carwithenhance/hook/AutoPlayAllHook;->sPrefListener:Landroid/content/SharedPreferences$OnSharedPreferenceChangeListener;

    .line 874
    invoke-interface {p0, v1}, Landroid/content/SharedPreferences;->registerOnSharedPreferenceChangeListener(Landroid/content/SharedPreferences$OnSharedPreferenceChangeListener;)V

    .line 875
    const-string p0, "\u8f66\u673a\u7aef\u5a92\u4f53\u5e94\u7528\u8bbe\u7f6e\u76d1\u542c\u5df2\u6ce8\u518c"

    invoke-static {v0, p0}, Lhun/mesh/carwithenhance/hook/AutoPlayAllHook;->log(Landroid/content/Context;Ljava/lang/String;)V
    :try_end_0
    .catchall {:try_start_0 .. :try_end_0} :catchall_0

    .line 878
    goto :goto_0

    .line 876
    :catchall_0
    move-exception p0

    .line 877
    const-string v1, "\u8f66\u673a\u7aef\u5a92\u4f53\u5e94\u7528\u8bbe\u7f6e\u76d1\u542c\u6ce8\u518c\u5931\u8d25"

    invoke-static {v0, v1, p0}, Lhun/mesh/carwithenhance/hook/AutoPlayAllHook;->logErr(Landroid/content/Context;Ljava/lang/String;Ljava/lang/Throwable;)V

    .line 879
    :goto_0
    return-void
.end method

.method private static hookOwnPrefs(Ljava/lang/ClassLoader;)V
    .locals 4

    .line 652
    const/4 v0, 0x0

    :try_start_0
    sget-object v1, Lhun/mesh/carwithenhance/hook/AutoPlayAllHook;->sAppCtx:Landroid/content/Context;

    if-eqz v1, :cond_0

    goto :goto_0

    :cond_0
    invoke-static {p0}, Lhun/mesh/carwithenhance/utils/AppUtils;->getAppContext(Ljava/lang/ClassLoader;)Landroid/content/Context;

    move-result-object v1

    .line 653
    :goto_0
    if-eqz v1, :cond_2

    const-string v2, "com.miui.carlink"

    invoke-virtual {v1}, Landroid/content/Context;->getPackageName()Ljava/lang/String;

    move-result-object v3

    invoke-virtual {v2, v3}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v2

    if-nez v2, :cond_1

    goto :goto_1

    .line 656
    :cond_1
    invoke-static {v1}, Lhun/mesh/carwithenhance/hook/AutoPlayAllHook;->setAppContext(Landroid/content/Context;)V

    .line 657
    const-string v2, "carwithenhance_hooksettings"

    const/4 v3, 0x0

    invoke-virtual {v1, v2, v3}, Landroid/content/Context;->getSharedPreferences(Ljava/lang/String;I)Landroid/content/SharedPreferences;

    move-result-object v1

    .line 658
    new-instance v2, Lhun/mesh/carwithenhance/hook/AutoPlayAllHook$7;

    invoke-direct {v2, p0}, Lhun/mesh/carwithenhance/hook/AutoPlayAllHook$7;-><init>(Ljava/lang/ClassLoader;)V

    sput-object v2, Lhun/mesh/carwithenhance/hook/AutoPlayAllHook;->sOwnPrefListener:Landroid/content/SharedPreferences$OnSharedPreferenceChangeListener;

    .line 675
    invoke-interface {v1, v2}, Landroid/content/SharedPreferences;->registerOnSharedPreferenceChangeListener(Landroid/content/SharedPreferences$OnSharedPreferenceChangeListener;)V

    .line 676
    const-string p0, "\u6a21\u5757\u5f00\u5173\u76d1\u542c\u5df2\u6ce8\u518c"

    invoke-static {v0, p0}, Lhun/mesh/carwithenhance/hook/AutoPlayAllHook;->log(Landroid/content/Context;Ljava/lang/String;)V
    :try_end_0
    .catchall {:try_start_0 .. :try_end_0} :catchall_0

    .line 679
    goto :goto_2

    .line 654
    :cond_2
    :goto_1
    return-void

    .line 677
    :catchall_0
    move-exception p0

    .line 678
    const-string v1, "\u6a21\u5757\u5f00\u5173\u76d1\u542c\u6ce8\u518c\u5931\u8d25"

    invoke-static {v0, v1, p0}, Lhun/mesh/carwithenhance/hook/AutoPlayAllHook;->logErr(Landroid/content/Context;Ljava/lang/String;Ljava/lang/Throwable;)V

    .line 680
    :goto_2
    return-void
.end method

.method private static hookSessionAvailability(Ljava/lang/ClassLoader;)V
    .locals 3

    .line 781
    const/4 v0, 0x0

    :try_start_0
    sget-object v1, Lhun/mesh/carwithenhance/hook/AutoPlayAllHook;->sAppCtx:Landroid/content/Context;

    .line 782
    if-nez v1, :cond_0

    .line 783
    return-void

    .line 785
    :cond_0
    const-string v2, "media_session"

    .line 786
    invoke-virtual {v1, v2}, Landroid/content/Context;->getSystemService(Ljava/lang/String;)Ljava/lang/Object;

    move-result-object v1

    check-cast v1, Landroid/media/session/MediaSessionManager;

    .line 787
    if-nez v1, :cond_1

    .line 788
    return-void

    .line 790
    :cond_1
    new-instance v2, Lhun/mesh/carwithenhance/hook/AutoPlayAllHook$8;

    invoke-direct {v2, p0}, Lhun/mesh/carwithenhance/hook/AutoPlayAllHook$8;-><init>(Ljava/lang/ClassLoader;)V

    sput-object v2, Lhun/mesh/carwithenhance/hook/AutoPlayAllHook;->sSessionListener:Landroid/media/session/MediaSessionManager$OnActiveSessionsChangedListener;

    .line 833
    invoke-virtual {v1, v2, v0}, Landroid/media/session/MediaSessionManager;->addOnActiveSessionsChangedListener(Landroid/media/session/MediaSessionManager$OnActiveSessionsChangedListener;Landroid/content/ComponentName;)V

    .line 834
    const-string p0, "\u5a92\u4f53\u4f1a\u8bdd\u76d1\u542c\u5df2\u6ce8\u518c"

    invoke-static {v0, p0}, Lhun/mesh/carwithenhance/hook/AutoPlayAllHook;->log(Landroid/content/Context;Ljava/lang/String;)V
    :try_end_0
    .catchall {:try_start_0 .. :try_end_0} :catchall_0

    .line 837
    goto :goto_0

    .line 835
    :catchall_0
    move-exception p0

    .line 836
    const-string v1, "\u5a92\u4f53\u4f1a\u8bdd\u76d1\u542c\u6ce8\u518c\u5931\u8d25"

    invoke-static {v0, v1, p0}, Lhun/mesh/carwithenhance/hook/AutoPlayAllHook;->logErr(Landroid/content/Context;Ljava/lang/String;Ljava/lang/Throwable;)V

    .line 838
    :goto_0
    return-void
.end method

.method private static insertFiftyFpsItem(Ljava/lang/String;)Ljava/lang/String;
    .locals 5

    .line 552
    const/4 v0, 0x0

    :try_start_0
    const-string v1, "\"title\": \"60FPS\""

    invoke-virtual {p0, v1}, Ljava/lang/String;->indexOf(Ljava/lang/String;)I

    move-result v1

    .line 553
    if-gez v1, :cond_0

    .line 554
    const-string v1, "\"title\":\"60FPS\""

    invoke-virtual {p0, v1}, Ljava/lang/String;->indexOf(Ljava/lang/String;)I

    move-result v1

    .line 556
    :cond_0
    if-gez v1, :cond_1

    .line 557
    const-string v1, "60FPS"

    invoke-virtual {p0, v1}, Ljava/lang/String;->indexOf(Ljava/lang/String;)I

    move-result v1

    .line 559
    :cond_1
    if-gez v1, :cond_2

    .line 560
    return-object v0

    .line 562
    :cond_2
    const/16 v2, 0x7b

    invoke-virtual {p0, v2, v1}, Ljava/lang/String;->lastIndexOf(II)I

    move-result v1

    .line 563
    if-gez v1, :cond_3

    .line 564
    return-object v0

    .line 566
    :cond_3
    const-string v2, "{\"type\":\"check\",\"title\":\"50FPS\",\"key\":\"user_setting_framerate\"},"

    .line 567
    new-instance v3, Ljava/lang/StringBuilder;

    invoke-direct {v3}, Ljava/lang/StringBuilder;-><init>()V

    const/4 v4, 0x0

    invoke-virtual {p0, v4, v1}, Ljava/lang/String;->substring(II)Ljava/lang/String;

    move-result-object v4

    invoke-virtual {v3, v4}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v3

    invoke-virtual {v3, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v2

    invoke-virtual {p0, v1}, Ljava/lang/String;->substring(I)Ljava/lang/String;

    move-result-object p0

    invoke-virtual {v2, p0}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object p0

    invoke-virtual {p0}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object p0
    :try_end_0
    .catchall {:try_start_0 .. :try_end_0} :catchall_0

    return-object p0

    .line 568
    :catchall_0
    move-exception p0

    .line 569
    return-object v0
.end method

.method private static isAnyTargetPlaying(Landroid/content/Context;)Z
    .locals 4

    .line 1684
    const/4 v0, 0x0

    :try_start_0
    const-string v1, "media_session"

    .line 1685
    invoke-virtual {p0, v1}, Landroid/content/Context;->getSystemService(Ljava/lang/String;)Ljava/lang/Object;

    move-result-object p0

    check-cast p0, Landroid/media/session/MediaSessionManager;

    .line 1686
    if-nez p0, :cond_0

    .line 1687
    return v0

    .line 1689
    :cond_0
    const/4 v1, 0x0

    invoke-virtual {p0, v1}, Landroid/media/session/MediaSessionManager;->getActiveSessions(Landroid/content/ComponentName;)Ljava/util/List;

    move-result-object p0

    .line 1690
    if-nez p0, :cond_1

    .line 1691
    return v0

    .line 1693
    :cond_1
    invoke-interface {p0}, Ljava/util/List;->iterator()Ljava/util/Iterator;

    move-result-object p0

    :cond_2
    :goto_0
    invoke-interface {p0}, Ljava/util/Iterator;->hasNext()Z

    move-result v1

    if-eqz v1, :cond_7

    invoke-interface {p0}, Ljava/util/Iterator;->next()Ljava/lang/Object;

    move-result-object v1

    check-cast v1, Landroid/media/session/MediaController;

    .line 1694
    if-nez v1, :cond_3

    .line 1695
    goto :goto_0

    .line 1697
    :cond_3
    invoke-virtual {v1}, Landroid/media/session/MediaController;->getPackageName()Ljava/lang/String;

    move-result-object v2

    .line 1698
    if-eqz v2, :cond_2

    invoke-virtual {v2}, Ljava/lang/String;->length()I

    move-result v3

    if-eqz v3, :cond_2

    const-string v3, "com.miui.carlink"

    .line 1699
    invoke-virtual {v3, v2}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v3

    if-nez v3, :cond_2

    invoke-static {v2}, Lhun/mesh/carwithenhance/hook/AutoPlayAllHook;->isNeverKill(Ljava/lang/String;)Z

    move-result v2

    if-eqz v2, :cond_4

    .line 1700
    goto :goto_0

    .line 1702
    :cond_4
    invoke-static {v1}, Lhun/mesh/carwithenhance/hook/AutoPlayAllHook;->stateOf(Landroid/media/session/MediaController;)I

    move-result v1
    :try_end_0
    .catchall {:try_start_0 .. :try_end_0} :catchall_0

    .line 1703
    const/4 v2, 0x3

    if-eq v1, v2, :cond_6

    const/4 v2, 0x6

    if-ne v1, v2, :cond_5

    goto :goto_1

    .line 1706
    :cond_5
    goto :goto_0

    .line 1704
    :cond_6
    :goto_1
    const/4 p0, 0x1

    return p0

    .line 1708
    :cond_7
    goto :goto_2

    .line 1707
    :catchall_0
    move-exception p0

    .line 1709
    :goto_2
    return v0
.end method

.method private static isEnabled(Landroid/content/Context;)Z
    .locals 0

    .line 2995
    invoke-static {p0}, Lhun/mesh/carwithenhance/hook/AutoPlayAllHook;->autoplayEnabled(Landroid/content/Context;)Z

    move-result p0

    return p0
.end method

.method private static isKillCandidate(Ljava/lang/String;)Z
    .locals 1

    .line 1833
    if-eqz p0, :cond_0

    invoke-virtual {p0}, Ljava/lang/String;->length()I

    move-result v0

    if-lez v0, :cond_0

    invoke-static {p0}, Lhun/mesh/carwithenhance/hook/AutoPlayAllHook;->isNeverKill(Ljava/lang/String;)Z

    move-result v0

    if-nez v0, :cond_0

    sget-object v0, Lhun/mesh/carwithenhance/hook/AutoPlayAllHook;->sCl:Ljava/lang/ClassLoader;

    invoke-static {v0, p0}, Lhun/mesh/carwithenhance/hook/AutoPlayAllHook;->isMediaApp(Ljava/lang/ClassLoader;Ljava/lang/String;)Z

    move-result p0

    if-eqz p0, :cond_0

    const/4 p0, 0x1

    goto :goto_0

    :cond_0
    const/4 p0, 0x0

    :goto_0
    return p0
.end method

.method private static isMediaApp(Ljava/lang/ClassLoader;Ljava/lang/String;)Z
    .locals 1

    .line 1902
    if-eqz p1, :cond_2

    invoke-virtual {p1}, Ljava/lang/String;->length()I

    move-result v0

    if-nez v0, :cond_0

    goto :goto_0

    .line 1906
    :cond_0
    const-string v0, "com.tencent.qqmusic"

    invoke-virtual {v0, p1}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v0

    if-eqz v0, :cond_1

    .line 1907
    const/4 p0, 0x1

    return p0

    .line 1909
    :cond_1
    invoke-static {p0, p1}, Lhun/mesh/carwithenhance/hook/AutoPlayAllHook;->isTarget(Ljava/lang/ClassLoader;Ljava/lang/String;)Z

    move-result p0

    return p0

    .line 1903
    :cond_2
    :goto_0
    const/4 p0, 0x0

    return p0
.end method

.method private static isMusicActive(Landroid/content/Context;)Z
    .locals 2

    .line 3059
    const/4 v0, 0x0

    :try_start_0
    const-string v1, "audio"

    invoke-virtual {p0, v1}, Landroid/content/Context;->getSystemService(Ljava/lang/String;)Ljava/lang/Object;

    move-result-object p0

    check-cast p0, Landroid/media/AudioManager;

    .line 3060
    if-eqz p0, :cond_0

    invoke-virtual {p0}, Landroid/media/AudioManager;->isMusicActive()Z

    move-result p0
    :try_end_0
    .catchall {:try_start_0 .. :try_end_0} :catchall_0

    if-eqz p0, :cond_0

    const/4 v0, 0x1

    :cond_0
    return v0

    .line 3061
    :catchall_0
    move-exception p0

    .line 3062
    return v0
.end method

.method private static isNeverKill(Ljava/lang/String;)Z
    .locals 6

    .line 1913
    const/4 v0, 0x1

    if-eqz p0, :cond_4

    invoke-virtual {p0}, Ljava/lang/String;->length()I

    move-result v1

    if-nez v1, :cond_0

    goto :goto_2

    .line 1916
    :cond_0
    sget-object v1, Lhun/mesh/carwithenhance/hook/AutoPlayAllHook;->NEVER_KILL:[Ljava/lang/String;

    array-length v2, v1

    const/4 v3, 0x0

    move v4, v3

    :goto_0
    if-ge v4, v2, :cond_2

    aget-object v5, v1, v4

    .line 1917
    invoke-virtual {v5, p0}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v5

    if-eqz v5, :cond_1

    .line 1918
    return v0

    .line 1916
    :cond_1
    add-int/lit8 v4, v4, 0x1

    goto :goto_0

    .line 1923
    :cond_2
    :try_start_0
    sget-object v1, Lhun/mesh/carwithenhance/hook/AutoPlayAllHook;->sAppCtx:Landroid/content/Context;

    const-string v2, "file_prefer_app_091703"

    invoke-virtual {v1, v2, v3}, Landroid/content/Context;->getSharedPreferences(Ljava/lang/String;I)Landroid/content/SharedPreferences;

    move-result-object v1

    const-string v2, "prefer_map_app"

    .line 1924
    const/4 v4, 0x0

    invoke-interface {v1, v2, v4}, Landroid/content/SharedPreferences;->getString(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;

    move-result-object v1

    .line 1925
    if-eqz v1, :cond_3

    invoke-virtual {v1, p0}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result p0
    :try_end_0
    .catchall {:try_start_0 .. :try_end_0} :catchall_0

    if-eqz p0, :cond_3

    goto :goto_1

    :cond_3
    move v0, v3

    :goto_1
    return v0

    .line 1926
    :catchall_0
    move-exception p0

    .line 1927
    return v3

    .line 1914
    :cond_4
    :goto_2
    return v0
.end method

.method private static isNowDesired(Ljava/lang/String;)Z
    .locals 2

    .line 1502
    const/4 v0, 0x0

    if-eqz p0, :cond_3

    invoke-virtual {p0}, Ljava/lang/String;->length()I

    move-result v1

    if-nez v1, :cond_0

    goto :goto_0

    .line 1505
    :cond_0
    sget-object v1, Lhun/mesh/carwithenhance/hook/AutoPlayAllHook;->sSelectedApp:Ljava/lang/String;

    invoke-virtual {p0, v1}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v1

    if-nez v1, :cond_1

    sget-object v1, Lhun/mesh/carwithenhance/hook/AutoPlayAllHook;->sCurrentMediaPkg:Ljava/lang/String;

    invoke-virtual {p0, v1}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v1

    if-nez v1, :cond_1

    sget-object v1, Lhun/mesh/carwithenhance/hook/AutoPlayAllHook;->sWaitingPkg:Ljava/lang/String;

    .line 1506
    invoke-virtual {p0, v1}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v1

    if-nez v1, :cond_1

    sget-object v1, Lhun/mesh/carwithenhance/hook/AutoPlayAllHook;->sActiveWokePkg:Ljava/lang/String;

    invoke-virtual {p0, v1}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v1

    if-nez v1, :cond_1

    sget-object v1, Lhun/mesh/carwithenhance/hook/AutoPlayAllHook;->sLastAcceptedPkg:Ljava/lang/String;

    .line 1507
    invoke-virtual {p0, v1}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result p0

    if-eqz p0, :cond_2

    :cond_1
    const/4 v0, 0x1

    .line 1505
    :cond_2
    return v0

    .line 1503
    :cond_3
    :goto_0
    return v0
.end method

.method private static isPlaying(Landroid/content/Context;Ljava/lang/String;)Z
    .locals 3

    .line 3069
    const/4 v0, 0x0

    :try_start_0
    const-string v1, "media_session"

    .line 3070
    invoke-virtual {p0, v1}, Landroid/content/Context;->getSystemService(Ljava/lang/String;)Ljava/lang/Object;

    move-result-object p0

    check-cast p0, Landroid/media/session/MediaSessionManager;

    .line 3071
    if-nez p0, :cond_0

    .line 3072
    return v0

    .line 3074
    :cond_0
    const/4 v1, 0x0

    invoke-virtual {p0, v1}, Landroid/media/session/MediaSessionManager;->getActiveSessions(Landroid/content/ComponentName;)Ljava/util/List;

    move-result-object p0

    .line 3075
    if-nez p0, :cond_1

    .line 3076
    return v0

    .line 3078
    :cond_1
    invoke-interface {p0}, Ljava/util/List;->iterator()Ljava/util/Iterator;

    move-result-object p0

    :cond_2
    :goto_0
    invoke-interface {p0}, Ljava/util/Iterator;->hasNext()Z

    move-result v1

    if-eqz v1, :cond_5

    invoke-interface {p0}, Ljava/util/Iterator;->next()Ljava/lang/Object;

    move-result-object v1

    check-cast v1, Landroid/media/session/MediaController;

    .line 3079
    if-eqz v1, :cond_2

    invoke-virtual {v1}, Landroid/media/session/MediaController;->getPackageName()Ljava/lang/String;

    move-result-object v2

    invoke-virtual {p1, v2}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v2

    if-nez v2, :cond_3

    .line 3080
    goto :goto_0

    .line 3082
    :cond_3
    invoke-virtual {v1}, Landroid/media/session/MediaController;->getPlaybackState()Landroid/media/session/PlaybackState;

    move-result-object p0

    .line 3083
    if-eqz p0, :cond_4

    invoke-virtual {p0}, Landroid/media/session/PlaybackState;->getState()I

    move-result p0
    :try_end_0
    .catchall {:try_start_0 .. :try_end_0} :catchall_0

    const/4 p1, 0x3

    if-ne p0, p1, :cond_4

    const/4 v0, 0x1

    :cond_4
    return v0

    .line 3086
    :cond_5
    goto :goto_1

    .line 3085
    :catchall_0
    move-exception p0

    .line 3087
    :goto_1
    return v0
.end method

.method private static isPrefEnabled(Landroid/content/Context;Ljava/lang/String;Z)Z
    .locals 2

    .line 1941
    :try_start_0
    const-string v0, "carwithenhance_hooksettings"

    const/4 v1, 0x0

    invoke-virtual {p0, v0, v1}, Landroid/content/Context;->getSharedPreferences(Ljava/lang/String;I)Landroid/content/SharedPreferences;

    move-result-object p0

    .line 1942
    invoke-interface {p0, p1, p2}, Landroid/content/SharedPreferences;->getBoolean(Ljava/lang/String;Z)Z

    move-result p0
    :try_end_0
    .catchall {:try_start_0 .. :try_end_0} :catchall_0

    .line 1941
    return p0

    .line 1943
    :catchall_0
    move-exception p0

    .line 1944
    return p2
.end method

.method private static isProcessRunning(Landroid/content/Context;Ljava/lang/String;)Z
    .locals 1

    .line 2569
    :try_start_0
    const-string v0, "activity"

    invoke-virtual {p0, v0}, Landroid/content/Context;->getSystemService(Ljava/lang/String;)Ljava/lang/Object;

    move-result-object p0

    check-cast p0, Landroid/app/ActivityManager;

    .line 2570
    invoke-virtual {p0}, Landroid/app/ActivityManager;->getRunningAppProcesses()Ljava/util/List;

    move-result-object p0

    .line 2571
    if-eqz p0, :cond_1

    .line 2572
    invoke-interface {p0}, Ljava/util/List;->iterator()Ljava/util/Iterator;

    move-result-object p0

    :goto_0
    invoke-interface {p0}, Ljava/util/Iterator;->hasNext()Z

    move-result v0

    if-eqz v0, :cond_1

    invoke-interface {p0}, Ljava/util/Iterator;->next()Ljava/lang/Object;

    move-result-object v0

    check-cast v0, Landroid/app/ActivityManager$RunningAppProcessInfo;

    .line 2573
    if-eqz v0, :cond_0

    iget-object v0, v0, Landroid/app/ActivityManager$RunningAppProcessInfo;->processName:Ljava/lang/String;

    invoke-virtual {p1, v0}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v0
    :try_end_0
    .catchall {:try_start_0 .. :try_end_0} :catchall_0

    if-eqz v0, :cond_0

    .line 2574
    const/4 p0, 0x1

    return p0

    .line 2576
    :cond_0
    goto :goto_0

    .line 2579
    :cond_1
    goto :goto_1

    .line 2578
    :catchall_0
    move-exception p0

    .line 2580
    :goto_1
    const/4 p0, 0x0

    return p0
.end method


# ============================================================
# [新增] 判断音乐播放器是否处于前台（importance == IMPORTANCE_FOREGROUND=100）
# 用于断开连接后关闭音乐时跳过前台播放器
# ============================================================
.method private static isForeground(Landroid/content/Context;Ljava/lang/String;)Z
    .locals 3

    :try_start_0
    const-string v0, "activity"

    invoke-virtual {p0, v0}, Landroid/content/Context;->getSystemService(Ljava/lang/String;)Ljava/lang/Object;

    move-result-object p0

    check-cast p0, Landroid/app/ActivityManager;

    invoke-virtual {p0}, Landroid/app/ActivityManager;->getRunningAppProcesses()Ljava/util/List;

    move-result-object p0

    if-eqz p0, :cond_1

    invoke-interface {p0}, Ljava/util/List;->iterator()Ljava/util/Iterator;

    move-result-object p0

    :goto_0
    invoke-interface {p0}, Ljava/util/Iterator;->hasNext()Z

    move-result v0

    if-eqz v0, :cond_1

    invoke-interface {p0}, Ljava/util/Iterator;->next()Ljava/lang/Object;

    move-result-object v0

    check-cast v0, Landroid/app/ActivityManager$RunningAppProcessInfo;

    if-eqz v0, :cond_0

    iget v1, v0, Landroid/app/ActivityManager$RunningAppProcessInfo;->importance:I

    const/16 v2, 0x64

    if-ne v1, v2, :cond_0

    iget-object v0, v0, Landroid/app/ActivityManager$RunningAppProcessInfo;->processName:Ljava/lang/String;

    invoke-virtual {p1, v0}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v0

    if-eqz v0, :cond_0

    const/4 p0, 0x1

    return p0

    :cond_0
    goto :goto_0

    :cond_1
    goto :goto_1

    :catchall_0
    move-exception p0

    :goto_1
    const/4 p0, 0x0

    return p0
.end method

# ============================================================
# [新增] 断开连接后是否跳过关闭：前台(isForeground) 或 播放中(isPlaying)
# ============================================================
.method private static shouldSkipDisconnectKill(Landroid/content/Context;Ljava/lang/String;)Z
    .locals 1

    invoke-static {p0, p1}, Lhun/mesh/carwithenhance/hook/AutoPlayAllHook;->isForeground(Landroid/content/Context;Ljava/lang/String;)Z

    move-result v0

    if-nez v0, :cond_0

    invoke-static {p0, p1}, Lhun/mesh/carwithenhance/hook/AutoPlayAllHook;->isPlaying(Landroid/content/Context;Ljava/lang/String;)Z

    move-result v0

    if-nez v0, :cond_0

    const/4 v0, 0x0

    return v0

    :cond_0
    const/4 v0, 0x1

    return v0
.end method

.method private static isTarget(Ljava/lang/ClassLoader;Ljava/lang/String;)Z
    .locals 5

    .line 3013
    const-string v0, "com.tencent.qqmusic"

    invoke-virtual {v0, p1}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v0

    const/4 v1, 0x0

    if-eqz v0, :cond_0

    .line 3015
    return v1

    .line 3018
    :cond_0
    const/4 v0, 0x1

    :try_start_0
    const-string v2, "com.carwith.common.utils.c0"

    invoke-static {v2, p0}, Lde/robv/android/xposed/XposedHelpers;->findClass(Ljava/lang/String;Ljava/lang/ClassLoader;)Ljava/lang/Class;

    move-result-object p0

    .line 3019
    const-string v2, "U"

    new-array v3, v1, [Ljava/lang/Object;

    invoke-static {p0, v2, v3}, Lde/robv/android/xposed/XposedHelpers;->callStaticMethod(Ljava/lang/Class;Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object p0

    .line 3020
    const-string v2, "K"

    new-array v3, v0, [Ljava/lang/Object;

    const-string v4, "media_app"

    aput-object v4, v3, v1

    invoke-static {p0, v2, v3}, Lde/robv/android/xposed/XposedHelpers;->callMethod(Ljava/lang/Object;Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object p0

    .line 3021
    instance-of v2, p0, Ljava/util/List;

    if-eqz v2, :cond_1

    .line 3022
    check-cast p0, Ljava/util/List;

    invoke-interface {p0, p1}, Ljava/util/List;->contains(Ljava/lang/Object;)Z

    move-result p0
    :try_end_0
    .catchall {:try_start_0 .. :try_end_0} :catchall_0

    return p0

    .line 3026
    :cond_1
    goto :goto_0

    .line 3024
    :catchall_0
    move-exception p0

    .line 3027
    :goto_0
    sget-object p0, Lhun/mesh/carwithenhance/hook/AutoPlayAllHook;->FALLBACK:[Ljava/lang/String;

    array-length v2, p0

    move v3, v1

    :goto_1
    if-ge v3, v2, :cond_3

    aget-object v4, p0, v3

    .line 3028
    invoke-virtual {v4, p1}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v4

    if-eqz v4, :cond_2

    .line 3029
    return v0

    .line 3027
    :cond_2
    add-int/lit8 v3, v3, 0x1

    goto :goto_1

    .line 3032
    :cond_3
    return v1
.end method

.method private static killSwitchTarget(Landroid/content/Context;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)V
    .locals 2

    .line 1454
    if-eqz p1, :cond_3

    :try_start_0
    invoke-virtual {p1}, Ljava/lang/String;->length()I

    move-result v0

    if-eqz v0, :cond_3

    invoke-virtual {p1, p2}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v0

    if-eqz v0, :cond_0

    goto :goto_1

    .line 1457
    :cond_0
    invoke-static {p1}, Lhun/mesh/carwithenhance/hook/AutoPlayAllHook;->isKillCandidate(Ljava/lang/String;)Z

    move-result v0

    if-nez v0, :cond_1

    .line 1458
    return-void

    .line 1460
    :cond_1
    invoke-static {p0, p1}, Lhun/mesh/carwithenhance/hook/AutoPlayAllHook;->isProcessRunning(Landroid/content/Context;Ljava/lang/String;)Z

    move-result v0

    if-nez v0, :cond_2

    invoke-static {p0, p1}, Lhun/mesh/carwithenhance/hook/AutoPlayAllHook;->hasSession(Landroid/content/Context;Ljava/lang/String;)Z

    move-result v0

    if-nez v0, :cond_2

    invoke-static {p0, p1}, Lhun/mesh/carwithenhance/hook/AutoPlayAllHook;->isPlaying(Landroid/content/Context;Ljava/lang/String;)Z

    move-result v0

    if-nez v0, :cond_2

    .line 1461
    return-void

    .line 1463
    :cond_2
    sget-object v0, Lhun/mesh/carwithenhance/hook/AutoPlayAllHook;->WOKE:Ljava/util/Set;

    invoke-interface {v0, p1}, Ljava/util/Set;->add(Ljava/lang/Object;)Z

    .line 1464
    new-instance v0, Ljava/lang/StringBuilder;

    invoke-direct {v0}, Ljava/lang/StringBuilder;-><init>()V

    const-string v1, "\u5207\u6362\u6e05\u7406\uff0c\u7ed3\u675f\u4e0a\u4e00\u4e2a\u64ad\u653e\u5668: "

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v0

    invoke-virtual {v0, p1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v0

    invoke-virtual {v0}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v0

    invoke-static {p0, v0}, Lhun/mesh/carwithenhance/hook/AutoPlayAllHook;->log(Landroid/content/Context;Ljava/lang/String;)V

    .line 1465
    invoke-static {p0, p1, p3}, Lhun/mesh/carwithenhance/hook/AutoPlayAllHook;->releaseWoke(Landroid/content/Context;Ljava/lang/String;Ljava/lang/String;)V

    .line 1467
    invoke-static {p0, p1, p2}, Lhun/mesh/carwithenhance/hook/AutoPlayAllHook;->markSwitchDropped(Landroid/content/Context;Ljava/lang/String;Ljava/lang/String;)V
    :try_end_0
    .catchall {:try_start_0 .. :try_end_0} :catchall_0

    .line 1470
    goto :goto_0

    .line 1468
    :catchall_0
    move-exception p2

    .line 1469
    new-instance p3, Ljava/lang/StringBuilder;

    invoke-direct {p3}, Ljava/lang/StringBuilder;-><init>()V

    const-string v0, "\u5207\u6362\u6e05\u7406\u5931\u8d25: "

    invoke-virtual {p3, v0}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object p3

    invoke-virtual {p3, p1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object p1

    invoke-virtual {p1}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object p1

    invoke-static {p0, p1, p2}, Lhun/mesh/carwithenhance/hook/AutoPlayAllHook;->logErr(Landroid/content/Context;Ljava/lang/String;Ljava/lang/Throwable;)V

    .line 1471
    :goto_0
    return-void

    .line 1455
    :cond_3
    :goto_1
    return-void
.end method

.method private static learnCurrentMedia(Ljava/lang/String;)V
    .locals 1

    .line 2698
    sget-object v0, Lhun/mesh/carwithenhance/hook/AutoPlayAllHook;->sCl:Ljava/lang/ClassLoader;

    invoke-static {v0, p0}, Lhun/mesh/carwithenhance/hook/AutoPlayAllHook;->isMediaApp(Ljava/lang/ClassLoader;Ljava/lang/String;)Z

    move-result v0

    if-eqz v0, :cond_0

    invoke-static {p0}, Lhun/mesh/carwithenhance/hook/AutoPlayAllHook;->isNeverKill(Ljava/lang/String;)Z

    move-result v0

    if-nez v0, :cond_0

    .line 2699
    sput-object p0, Lhun/mesh/carwithenhance/hook/AutoPlayAllHook;->sCurrentMediaPkg:Ljava/lang/String;

    .line 2700
    invoke-static {p0}, Lhun/mesh/carwithenhance/hook/AutoPlayAllHook;->markSessionMedia(Ljava/lang/String;)V

    .line 2702
    :cond_0
    return-void
.end method

.method private static length(Ljava/lang/Object;)I
    .locals 1

    .line 2338
    instance-of v0, p0, Ljava/lang/String;

    if-eqz v0, :cond_0

    check-cast p0, Ljava/lang/String;

    invoke-virtual {p0}, Ljava/lang/String;->length()I

    move-result p0

    goto :goto_0

    :cond_0
    const/4 p0, -0x1

    :goto_0
    return p0
.end method

.method public static log(Landroid/content/Context;Ljava/lang/String;)V
    .locals 2

    .line 262
    new-instance v0, Ljava/lang/StringBuilder;

    invoke-direct {v0}, Ljava/lang/StringBuilder;-><init>()V

    const-string v1, ">> [\u5168\u5e94\u7528\u7eed\u64ad] "

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v0

    invoke-virtual {v0, p1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v0

    invoke-virtual {v0}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v0

    const-string v1, "carwithenhance"

    invoke-static {v1, v0}, Landroid/util/Log;->i(Ljava/lang/String;Ljava/lang/String;)I

    .line 263
    const/4 v0, 0x0

    invoke-static {p0, p1, v0}, Lhun/mesh/carwithenhance/hook/AutoPlayAllHook;->writeFile(Landroid/content/Context;Ljava/lang/String;Ljava/lang/Throwable;)V

    .line 264
    return-void
.end method

.method private static logAudioState(Landroid/content/Context;Ljava/lang/String;)V
    .locals 9

    .line 1757
    if-nez p0, :cond_0

    .line 1758
    return-void

    .line 1761
    :cond_0
    :try_start_0
    const-string v0, "audio"

    invoke-virtual {p0, v0}, Landroid/content/Context;->getSystemService(Ljava/lang/String;)Ljava/lang/Object;

    move-result-object v0

    check-cast v0, Landroid/media/AudioManager;

    .line 1762
    if-nez v0, :cond_1

    .line 1763
    return-void

    .line 1765
    :cond_1
    const/4 v1, 0x3

    invoke-virtual {v0, v1}, Landroid/media/AudioManager;->getStreamVolume(I)I

    move-result v2

    .line 1766
    invoke-virtual {v0, v1}, Landroid/media/AudioManager;->getStreamMaxVolume(I)I

    move-result v1

    .line 1767
    invoke-virtual {v0}, Landroid/media/AudioManager;->isMusicActive()Z

    move-result v3

    .line 1768
    new-instance v4, Ljava/lang/StringBuilder;

    invoke-direct {v4}, Ljava/lang/StringBuilder;-><init>()V

    .line 1769
    const/4 v5, 0x2

    invoke-virtual {v0, v5}, Landroid/media/AudioManager;->getDevices(I)[Landroid/media/AudioDeviceInfo;

    move-result-object v0

    .line 1770
    if-eqz v0, :cond_5

    .line 1771
    array-length v5, v0

    const/4 v6, 0x0

    :goto_0
    if-ge v6, v5, :cond_5

    aget-object v7, v0, v6

    .line 1772
    if-eqz v7, :cond_4

    invoke-virtual {v7}, Landroid/media/AudioDeviceInfo;->isSink()Z

    move-result v8

    if-nez v8, :cond_2

    .line 1773
    goto :goto_1

    .line 1775
    :cond_2
    invoke-virtual {v4}, Ljava/lang/StringBuilder;->length()I

    move-result v8

    if-lez v8, :cond_3

    .line 1776
    const/16 v8, 0x2f

    invoke-virtual {v4, v8}, Ljava/lang/StringBuilder;->append(C)Ljava/lang/StringBuilder;

    .line 1778
    :cond_3
    invoke-virtual {v7}, Landroid/media/AudioDeviceInfo;->getType()I

    move-result v7

    invoke-virtual {v4, v7}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    .line 1771
    :cond_4
    :goto_1
    add-int/lit8 v6, v6, 0x1

    goto :goto_0

    .line 1781
    :cond_5
    new-instance v0, Ljava/lang/StringBuilder;

    invoke-direct {v0}, Ljava/lang/StringBuilder;-><init>()V

    const-string v5, "\u97f3\u9891\u72b6\u6001("

    invoke-virtual {v0, v5}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v0

    invoke-virtual {v0, p1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object p1

    const-string v0, "): \u5a92\u4f53\u97f3\u91cf="

    invoke-virtual {p1, v0}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object p1

    invoke-virtual {p1, v2}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    move-result-object p1

    const-string v0, "/"

    invoke-virtual {p1, v0}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object p1

    invoke-virtual {p1, v1}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    move-result-object p1

    const-string v0, " \u6709\u97f3\u4e50\u5728\u64ad="

    invoke-virtual {p1, v0}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object p1

    invoke-virtual {p1, v3}, Ljava/lang/StringBuilder;->append(Z)Ljava/lang/StringBuilder;

    move-result-object p1

    const-string v0, " \u8f93\u51fa\u8bbe\u5907="

    invoke-virtual {p1, v0}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object p1

    invoke-virtual {p1, v4}, Ljava/lang/StringBuilder;->append(Ljava/lang/Object;)Ljava/lang/StringBuilder;

    move-result-object p1

    invoke-virtual {p1}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object p1

    invoke-static {p0, p1}, Lhun/mesh/carwithenhance/hook/AutoPlayAllHook;->log(Landroid/content/Context;Ljava/lang/String;)V
    :try_end_0
    .catchall {:try_start_0 .. :try_end_0} :catchall_0

    .line 1785
    goto :goto_2

    .line 1783
    :catchall_0
    move-exception p1

    .line 1784
    const-string v0, "\u8bfb\u53d6\u97f3\u9891\u72b6\u6001\u5931\u8d25"

    invoke-static {p0, v0, p1}, Lhun/mesh/carwithenhance/hook/AutoPlayAllHook;->logErr(Landroid/content/Context;Ljava/lang/String;Ljava/lang/Throwable;)V

    .line 1786
    :goto_2
    return-void
.end method

.method public static logErr(Landroid/content/Context;Ljava/lang/String;Ljava/lang/Throwable;)V
    .locals 2

    .line 267
    new-instance v0, Ljava/lang/StringBuilder;

    invoke-direct {v0}, Ljava/lang/StringBuilder;-><init>()V

    const-string v1, ">> [\u5168\u5e94\u7528\u7eed\u64ad] "

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v0

    invoke-virtual {v0, p1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v0

    invoke-virtual {v0}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v0

    const-string v1, "carwithenhance"

    invoke-static {v1, v0, p2}, Landroid/util/Log;->e(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)I

    .line 268
    new-instance v0, Ljava/lang/StringBuilder;

    invoke-direct {v0}, Ljava/lang/StringBuilder;-><init>()V

    invoke-virtual {v0, p1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object p1

    const-string v0, " : "

    invoke-virtual {p1, v0}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object p1

    invoke-virtual {p1, p2}, Ljava/lang/StringBuilder;->append(Ljava/lang/Object;)Ljava/lang/StringBuilder;

    move-result-object p1

    invoke-virtual {p1}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object p1

    const/4 p2, 0x0

    invoke-static {p0, p1, p2}, Lhun/mesh/carwithenhance/hook/AutoPlayAllHook;->writeFile(Landroid/content/Context;Ljava/lang/String;Ljava/lang/Throwable;)V

    .line 269
    return-void
.end method

.method private static looksUserKilled(Landroid/content/Context;Ljava/lang/String;)Z
    .locals 6

    .line 2525
    const/4 v0, 0x0

    if-eqz p0, :cond_4

    if-eqz p1, :cond_4

    sget-boolean v1, Lhun/mesh/carwithenhance/hook/AutoPlayAllHook;->sCarConnected:Z

    if-nez v1, :cond_0

    goto :goto_0

    .line 2528
    :cond_0
    sget-object v1, Lhun/mesh/carwithenhance/hook/AutoPlayAllHook;->sResumedPkg:Ljava/lang/String;

    invoke-virtual {p1, v1}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v1

    if-nez v1, :cond_1

    .line 2529
    return v0

    .line 2531
    :cond_1
    sget-object v1, Lhun/mesh/carwithenhance/hook/AutoPlayAllHook;->OWN_KILL_AT:Ljava/util/Map;

    invoke-interface {v1, p1}, Ljava/util/Map;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v1

    check-cast v1, Ljava/lang/Long;

    .line 2532
    if-eqz v1, :cond_2

    invoke-static {}, Landroid/os/SystemClock;->elapsedRealtime()J

    move-result-wide v2

    invoke-virtual {v1}, Ljava/lang/Long;->longValue()J

    move-result-wide v4

    sub-long/2addr v2, v4

    const-wide/16 v4, 0x4e20

    cmp-long v1, v2, v4

    if-gez v1, :cond_2

    .line 2534
    return v0

    .line 2536
    :cond_2
    invoke-static {p0, p1}, Lhun/mesh/carwithenhance/hook/AutoPlayAllHook;->isProcessRunning(Landroid/content/Context;Ljava/lang/String;)Z

    move-result v1

    if-nez v1, :cond_3

    invoke-static {p0, p1}, Lhun/mesh/carwithenhance/hook/AutoPlayAllHook;->hasSession(Landroid/content/Context;Ljava/lang/String;)Z

    move-result p0

    if-nez p0, :cond_3

    const/4 v0, 0x1

    :cond_3
    return v0

    .line 2526
    :cond_4
    :goto_0
    return v0
.end method

.method private static markSessionMedia(Ljava/lang/String;)V
    .locals 1

    .line 1933
    invoke-static {p0}, Lhun/mesh/carwithenhance/hook/AutoPlayAllHook;->isNeverKill(Ljava/lang/String;)Z

    move-result v0

    if-nez v0, :cond_1

    sget-object v0, Lhun/mesh/carwithenhance/hook/AutoPlayAllHook;->sCl:Ljava/lang/ClassLoader;

    invoke-static {v0, p0}, Lhun/mesh/carwithenhance/hook/AutoPlayAllHook;->isMediaApp(Ljava/lang/ClassLoader;Ljava/lang/String;)Z

    move-result v0

    if-nez v0, :cond_0

    goto :goto_0

    .line 1936
    :cond_0
    sget-object v0, Lhun/mesh/carwithenhance/hook/AutoPlayAllHook;->SESSION_MEDIA:Ljava/util/Set;

    invoke-interface {v0, p0}, Ljava/util/Set;->add(Ljava/lang/Object;)Z

    .line 1937
    return-void

    .line 1934
    :cond_1
    :goto_0
    return-void
.end method

.method private static markSwitchDropped(Landroid/content/Context;Ljava/lang/String;Ljava/lang/String;)V
    .locals 2

    .line 1474
    if-eqz p1, :cond_2

    invoke-virtual {p1}, Ljava/lang/String;->length()I

    move-result v0

    if-eqz v0, :cond_2

    invoke-virtual {p1, p2}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result p2

    if-eqz p2, :cond_0

    goto :goto_0

    .line 1477
    :cond_0
    sget-object p2, Lhun/mesh/carwithenhance/hook/AutoPlayAllHook;->SWITCH_DROPPED:Ljava/util/Map;

    invoke-interface {p2, p1}, Ljava/util/Map;->containsKey(Ljava/lang/Object;)Z

    move-result v0

    if-eqz v0, :cond_1

    .line 1478
    return-void

    .line 1480
    :cond_1
    invoke-static {}, Landroid/os/SystemClock;->elapsedRealtime()J

    move-result-wide v0

    invoke-static {v0, v1}, Ljava/lang/Long;->valueOf(J)Ljava/lang/Long;

    move-result-object v0

    invoke-interface {p2, p1, v0}, Ljava/util/Map;->put(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;

    .line 1481
    new-instance p2, Ljava/lang/StringBuilder;

    invoke-direct {p2}, Ljava/lang/StringBuilder;-><init>()V

    const-string v0, "\u4e0a\u4e00\u4e2a\u64ad\u653e\u5668\u5df2\u6309\u4f60\u7684\u5207\u6362\u7ed3\u675f\uff0c\u672c\u6b21\u8fde\u63a5\u5185\u4e0d\u518d\u81ea\u52a8\u62c9\u8d77\u5b83: "

    invoke-virtual {p2, v0}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object p2

    invoke-virtual {p2, p1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object p1

    invoke-virtual {p1}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object p1

    invoke-static {p0, p1}, Lhun/mesh/carwithenhance/hook/AutoPlayAllHook;->log(Landroid/content/Context;Ljava/lang/String;)V

    .line 1482
    return-void

    .line 1475
    :cond_2
    :goto_0
    return-void
.end method

.method private static markUserKilled(Landroid/content/Context;Ljava/lang/String;)V
    .locals 2

    .line 2541
    if-nez p1, :cond_0

    .line 2542
    return-void

    .line 2544
    :cond_0
    sget-object v0, Lhun/mesh/carwithenhance/hook/AutoPlayAllHook;->USER_KILLED:Ljava/util/Set;

    invoke-interface {v0, p1}, Ljava/util/Set;->add(Ljava/lang/Object;)Z

    move-result v0

    if-eqz v0, :cond_1

    .line 2545
    new-instance v0, Ljava/lang/StringBuilder;

    invoke-direct {v0}, Ljava/lang/StringBuilder;-><init>()V

    const-string v1, "\u4f60\u624b\u52a8\u7ed3\u675f\u4e86 "

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v0

    invoke-virtual {v0, p1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object p1

    const-string v0, "\uff0c\u672c\u6b21\u8fde\u63a5\u5185\u4e0d\u518d\u81ea\u52a8\u62c9\u8d77\u5b83\uff08\u4e0b\u6b21\u4e0a\u8f66\u7eed\u64ad\u7167\u5e38\uff09"

    invoke-virtual {p1, v0}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object p1

    invoke-virtual {p1}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object p1

    invoke-static {p0, p1}, Lhun/mesh/carwithenhance/hook/AutoPlayAllHook;->log(Landroid/content/Context;Ljava/lang/String;)V

    .line 2547
    :cond_1
    return-void
.end method

.method public static onCarConnected()V
    .locals 6

    .line 1076
    sget-wide v0, Lhun/mesh/carwithenhance/hook/AutoPlayAllHook;->sConnectGen:J

    const-wide/16 v2, 0x1

    add-long/2addr v0, v2

    sput-wide v0, Lhun/mesh/carwithenhance/hook/AutoPlayAllHook;->sConnectGen:J

    .line 1077
    invoke-static {}, Lhun/mesh/carwithenhance/hook/AutoPlayAllHook;->readPrefMediaApp()Ljava/lang/String;

    move-result-object v0

    sput-object v0, Lhun/mesh/carwithenhance/hook/AutoPlayAllHook;->sSelectedApp:Ljava/lang/String;

    .line 1079
    sget-object v0, Lhun/mesh/carwithenhance/hook/AutoPlayAllHook;->SESSION_MEDIA:Ljava/util/Set;

    invoke-interface {v0}, Ljava/util/Set;->clear()V

    .line 1080
    sget-object v0, Lhun/mesh/carwithenhance/hook/AutoPlayAllHook;->WOKE:Ljava/util/Set;

    invoke-interface {v0}, Ljava/util/Set;->clear()V

    .line 1081
    const/4 v0, 0x0

    sput-object v0, Lhun/mesh/carwithenhance/hook/AutoPlayAllHook;->sActiveWokePkg:Ljava/lang/String;

    .line 1082
    sput-object v0, Lhun/mesh/carwithenhance/hook/AutoPlayAllHook;->sLastAcceptedPkg:Ljava/lang/String;

    .line 1083
    sput-object v0, Lhun/mesh/carwithenhance/hook/AutoPlayAllHook;->sCurrentMediaPkg:Ljava/lang/String;

    .line 1084
    sput-object v0, Lhun/mesh/carwithenhance/hook/AutoPlayAllHook;->sWaitingPkg:Ljava/lang/String;

    .line 1085
    sput-object v0, Lhun/mesh/carwithenhance/hook/AutoPlayAllHook;->sLastSelectPkg:Ljava/lang/String;

    .line 1086
    sget-wide v4, Lhun/mesh/carwithenhance/hook/AutoPlayAllHook;->sRetryGen:J

    add-long/2addr v4, v2

    sput-wide v4, Lhun/mesh/carwithenhance/hook/AutoPlayAllHook;->sRetryGen:J

    .line 1088
    sget-object v1, Lhun/mesh/carwithenhance/hook/AutoPlayAllHook;->USER_KILLED:Ljava/util/Set;

    invoke-interface {v1}, Ljava/util/Set;->clear()V

    .line 1089
    sget-object v1, Lhun/mesh/carwithenhance/hook/AutoPlayAllHook;->OWN_KILL_AT:Ljava/util/Map;

    invoke-interface {v1}, Ljava/util/Map;->clear()V

    .line 1090
    sget-object v1, Lhun/mesh/carwithenhance/hook/AutoPlayAllHook;->SWITCH_DROPPED:Ljava/util/Map;

    invoke-interface {v1}, Ljava/util/Map;->clear()V

    .line 1091
    sput-object v0, Lhun/mesh/carwithenhance/hook/AutoPlayAllHook;->sLastStatePkg:Ljava/lang/String;

    .line 1092
    sput-object v0, Lhun/mesh/carwithenhance/hook/AutoPlayAllHook;->sResumedPkg:Ljava/lang/String;

    .line 1093
    sput-boolean v0, Lhun/mesh/carwithenhance/hook/AutoPlayAllHook;->sInitialResumeDone:Z

    const/4 v1, 0x1

    sput-boolean v1, Lhun/mesh/carwithenhance/hook/AutoPlayAllHook;->sCarConnected:Z

    .line 1094
    const-string v2, "CarWith \u5df2\u8fde\u63a5"

    invoke-static {v0, v2}, Lhun/mesh/carwithenhance/hook/AutoPlayAllHook;->log(Landroid/content/Context;Ljava/lang/String;)V

    .line 1095
    sget-object v0, Lhun/mesh/carwithenhance/hook/AutoPlayAllHook;->sAppCtx:Landroid/content/Context;

    const-string v2, "\u8f66\u673a\u8fde\u63a5\u65f6"

    invoke-static {v0, v2}, Lhun/mesh/carwithenhance/hook/AutoPlayAllHook;->logAudioState(Landroid/content/Context;Ljava/lang/String;)V

    .line 1097
    sget-object v0, Lhun/mesh/carwithenhance/hook/AutoPlayAllHook;->sCl:Ljava/lang/ClassLoader;

    .line 1098
    sget-object v2, Lhun/mesh/carwithenhance/hook/AutoPlayAllHook;->sAppCtx:Landroid/content/Context;

    .line 1099
    const-string v3, "hook_experience_to_normal_enabled"

    invoke-static {v2, v3, v1}, Lhun/mesh/carwithenhance/hook/AutoPlayAllHook;->isPrefEnabled(Landroid/content/Context;Ljava/lang/String;Z)Z

    move-result v1

    .line 1098
    invoke-static {v2, v0, v1}, Lhun/mesh/carwithenhance/hook/AutoPlayAllHook;->applyExperienceToNormal(Landroid/content/Context;Ljava/lang/ClassLoader;Z)I

    .line 1100
    const-wide/16 v1, 0xfa0

    invoke-static {v0, v1, v2}, Lhun/mesh/carwithenhance/hook/AutoPlayAllHook;->scheduleExperienceApply(Ljava/lang/ClassLoader;J)V

    .line 1101
    const-wide/16 v1, 0x2ee0

    invoke-static {v0, v1, v2}, Lhun/mesh/carwithenhance/hook/AutoPlayAllHook;->scheduleExperienceApply(Ljava/lang/ClassLoader;J)V

    .line 1102
    invoke-static {}, Lhun/mesh/carwithenhance/hook/AutoPlayAllHook;->preWakeLastUsedApp()V

    .line 1103
    return-void
.end method

.method public static onCarDisconnected()V
    .locals 5

    .line 1727
    const/4 v0, 0x0

    sput-boolean v0, Lhun/mesh/carwithenhance/hook/AutoPlayAllHook;->sCarConnected:Z

    .line 1728
    const/4 v0, 0x0

    sput-object v0, Lhun/mesh/carwithenhance/hook/AutoPlayAllHook;->sLastAcceptedPkg:Ljava/lang/String;

    .line 1729
    sput-object v0, Lhun/mesh/carwithenhance/hook/AutoPlayAllHook;->sActiveWokePkg:Ljava/lang/String;

    .line 1730
    sget-object v1, Lhun/mesh/carwithenhance/hook/AutoPlayAllHook;->SESSION_MEDIA:Ljava/util/Set;

    invoke-interface {v1}, Ljava/util/Set;->clear()V

    .line 1731
    sget-object v1, Lhun/mesh/carwithenhance/hook/AutoPlayAllHook;->WOKE:Ljava/util/Set;

    invoke-interface {v1}, Ljava/util/Set;->clear()V

    .line 1732
    sget-wide v1, Lhun/mesh/carwithenhance/hook/AutoPlayAllHook;->sRetryGen:J

    const-wide/16 v3, 0x1

    add-long/2addr v1, v3

    sput-wide v1, Lhun/mesh/carwithenhance/hook/AutoPlayAllHook;->sRetryGen:J

    .line 1733
    const-string v1, "\u8fde\u63a5\u5df2\u65ad\u5f00\uff0c\u4ec5\u590d\u4f4d\u7eed\u64ad\u72b6\u6001"

    invoke-static {v0, v1}, Lhun/mesh/carwithenhance/hook/AutoPlayAllHook;->log(Landroid/content/Context;Ljava/lang/String;)V

    .line 1734
    return-void
.end method

.method private static onMediaAppEvent(Ljava/lang/String;I)V
    .locals 7

    .line 1314
    :try_start_0
    sget-object v0, Lhun/mesh/carwithenhance/hook/AutoPlayAllHook;->sAppCtx:Landroid/content/Context;

    .line 1315
    if-eqz v0, :cond_a

    if-eqz p0, :cond_a

    invoke-virtual {p0}, Ljava/lang/String;->length()I

    move-result v1

    if-nez v1, :cond_0

    goto/16 :goto_1

    .line 1318
    :cond_0
    sget-object v1, Lhun/mesh/carwithenhance/hook/AutoPlayAllHook;->sCl:Ljava/lang/ClassLoader;

    invoke-static {v1, p0}, Lhun/mesh/carwithenhance/hook/AutoPlayAllHook;->isMediaApp(Ljava/lang/ClassLoader;Ljava/lang/String;)Z

    move-result v1

    if-eqz v1, :cond_9

    invoke-static {p0}, Lhun/mesh/carwithenhance/hook/AutoPlayAllHook;->isNeverKill(Ljava/lang/String;)Z

    move-result v1

    if-eqz v1, :cond_1

    goto/16 :goto_0

    .line 1321
    :cond_1
    invoke-static {p0}, Lhun/mesh/carwithenhance/hook/AutoPlayAllHook;->markSessionMedia(Ljava/lang/String;)V

    .line 1322
    const/4 v1, 0x1

    if-eq p1, v1, :cond_4

    .line 1323
    invoke-static {}, Landroid/os/SystemClock;->elapsedRealtime()J

    move-result-wide v1

    .line 1324
    sget-object v3, Lhun/mesh/carwithenhance/hook/AutoPlayAllHook;->sLastStatePkg:Ljava/lang/String;

    invoke-virtual {p0, v3}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v3

    if-eqz v3, :cond_2

    sget-wide v3, Lhun/mesh/carwithenhance/hook/AutoPlayAllHook;->sLastStateAt:J

    sub-long v3, v1, v3

    const-wide/16 v5, 0x2710

    cmp-long v3, v3, v5

    if-lez v3, :cond_3

    .line 1325
    :cond_2
    sput-object p0, Lhun/mesh/carwithenhance/hook/AutoPlayAllHook;->sLastStatePkg:Ljava/lang/String;

    .line 1326
    sput-wide v1, Lhun/mesh/carwithenhance/hook/AutoPlayAllHook;->sLastStateAt:J

    .line 1327
    new-instance v1, Ljava/lang/StringBuilder;

    invoke-direct {v1}, Ljava/lang/StringBuilder;-><init>()V

    const-string v2, "\u8f66\u673a\u754c\u9762\u72b6\u6001\u5237\u65b0\uff08"

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v1

    invoke-static {p1}, Lhun/mesh/carwithenhance/hook/AutoPlayAllHook;->sourceName(I)Ljava/lang/String;

    move-result-object p1

    invoke-virtual {v1, p1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object p1

    const-string v1, "\uff09\uff1a"

    invoke-virtual {p1, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object p1

    invoke-virtual {p1, p0}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object p0

    const-string p1, "\uff0c\u53ea\u8bb0\u5f55\u4e0d\u52a8\u64ad\u653e\u5668 | "

    invoke-virtual {p0, p1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object p0

    .line 1328
    const/4 p1, 0x4

    invoke-static {p1}, Lhun/mesh/carwithenhance/hook/AutoPlayAllHook;->callerTrace(I)Ljava/lang/String;

    move-result-object p1

    invoke-virtual {p0, p1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object p0

    invoke-virtual {p0}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object p0

    .line 1327
    invoke-static {v0, p0}, Lhun/mesh/carwithenhance/hook/AutoPlayAllHook;->log(Landroid/content/Context;Ljava/lang/String;)V

    .line 1330
    :cond_3
    return-void

    .line 1333
    :cond_4
    sget-object p1, Lhun/mesh/carwithenhance/hook/AutoPlayAllHook;->SWITCH_DROPPED:Ljava/util/Map;

    invoke-interface {p1, p0}, Ljava/util/Map;->remove(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object p1
    :try_end_0
    .catchall {:try_start_0 .. :try_end_0} :catchall_0

    const-string v1, "\u4f60\u5728\u8f66\u673a\u4e0a\u91cd\u65b0\u9009\u4e86 "

    if-eqz p1, :cond_5

    .line 1334
    :try_start_1
    new-instance p1, Ljava/lang/StringBuilder;

    invoke-direct {p1}, Ljava/lang/StringBuilder;-><init>()V

    invoke-virtual {p1, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object p1

    invoke-virtual {p1, p0}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object p1

    const-string v2, "\uff0c\u91cd\u65b0\u5141\u8bb8\u5524\u9192\u5b83"

    invoke-virtual {p1, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object p1

    invoke-virtual {p1}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object p1

    invoke-static {v0, p1}, Lhun/mesh/carwithenhance/hook/AutoPlayAllHook;->log(Landroid/content/Context;Ljava/lang/String;)V

    .line 1336
    :cond_5
    sget-object p1, Lhun/mesh/carwithenhance/hook/AutoPlayAllHook;->USER_KILLED:Ljava/util/Set;

    invoke-interface {p1, p0}, Ljava/util/Set;->remove(Ljava/lang/Object;)Z

    move-result p1

    if-eqz p1, :cond_6

    .line 1337
    new-instance p1, Ljava/lang/StringBuilder;

    invoke-direct {p1}, Ljava/lang/StringBuilder;-><init>()V

    invoke-virtual {p1, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object p1

    invoke-virtual {p1, p0}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object p1

    const-string v1, "\uff0c\u6062\u590d\u81ea\u52a8\u5524\u9192"

    invoke-virtual {p1, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object p1

    invoke-virtual {p1}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object p1

    invoke-static {v0, p1}, Lhun/mesh/carwithenhance/hook/AutoPlayAllHook;->log(Landroid/content/Context;Ljava/lang/String;)V

    .line 1339
    :cond_6
    sput-object p0, Lhun/mesh/carwithenhance/hook/AutoPlayAllHook;->sSelectedApp:Ljava/lang/String;

    .line 1340
    sget-object p1, Lhun/mesh/carwithenhance/hook/AutoPlayAllHook;->CAR_OPEN_AT:Ljava/util/Map;

    invoke-static {}, Landroid/os/SystemClock;->elapsedRealtime()J

    move-result-wide v1

    invoke-static {v1, v2}, Ljava/lang/Long;->valueOf(J)Ljava/lang/Long;

    move-result-object v1

    invoke-interface {p1, p0, v1}, Ljava/util/Map;->put(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;

    .line 1342
    sget-object p1, Lhun/mesh/carwithenhance/hook/AutoPlayAllHook;->sCl:Ljava/lang/ClassLoader;

    invoke-static {p1, p0}, Lhun/mesh/carwithenhance/hook/AutoPlayAllHook;->rememberUsedMediaApp(Ljava/lang/ClassLoader;Ljava/lang/String;)V

    return-void

    .line 1343
    invoke-static {v0}, Lhun/mesh/carwithenhance/hook/AutoPlayAllHook;->isEnabled(Landroid/content/Context;)Z

    move-result p1

    if-nez p1, :cond_7

    .line 1344
    return-void

    .line 1348
    :cond_7
    invoke-static {}, Landroid/os/SystemClock;->elapsedRealtime()J

    move-result-wide v1

    .line 1349
    sget-object p1, Lhun/mesh/carwithenhance/hook/AutoPlayAllHook;->sLastSelectPkg:Ljava/lang/String;

    invoke-virtual {p0, p1}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result p1

    if-eqz p1, :cond_8

    sget-wide v3, Lhun/mesh/carwithenhance/hook/AutoPlayAllHook;->sLastSelectAt:J

    sub-long v3, v1, v3

    const-wide/16 v5, 0x9c4

    cmp-long p1, v3, v5

    if-gez p1, :cond_8

    .line 1350
    return-void

    .line 1352
    :cond_8
    sput-object p0, Lhun/mesh/carwithenhance/hook/AutoPlayAllHook;->sLastSelectPkg:Ljava/lang/String;

    .line 1353
    sput-wide v1, Lhun/mesh/carwithenhance/hook/AutoPlayAllHook;->sLastSelectAt:J

    .line 1354
    new-instance p1, Ljava/lang/StringBuilder;

    invoke-direct {p1}, Ljava/lang/StringBuilder;-><init>()V

    const-string v1, "\u8f66\u673a\u7aef\u5207\u6362\u5230 "

    invoke-virtual {p1, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object p1

    invoke-virtual {p1, p0}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object p1

    const-string v1, "\uff0c\u7ed3\u675f\u4e0a\u4e00\u4e2a\u64ad\u653e\u5668\u5e76\u540e\u53f0\u5524\u9192\u5b83"

    invoke-virtual {p1, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object p1

    invoke-virtual {p1}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object p1

    invoke-static {v0, p1}, Lhun/mesh/carwithenhance/hook/AutoPlayAllHook;->log(Landroid/content/Context;Ljava/lang/String;)V

    .line 1355
    invoke-static {p0}, Lhun/mesh/carwithenhance/hook/AutoPlayAllHook;->runSwitchAsync(Ljava/lang/String;)V
    :try_end_1
    .catchall {:try_start_1 .. :try_end_1} :catchall_0

    .line 1357
    goto :goto_2

    .line 1319
    :cond_9
    :goto_0
    return-void

    .line 1316
    :cond_a
    :goto_1
    return-void

    .line 1356
    :catchall_0
    move-exception p0

    .line 1358
    :goto_2
    return-void
.end method

.method private static onUserSelectedMediaApp(Ljava/lang/String;)V
    .locals 1

    .line 1303
    const/4 v0, 0x1

    invoke-static {p0, v0}, Lhun/mesh/carwithenhance/hook/AutoPlayAllHook;->onMediaAppEvent(Ljava/lang/String;I)V

    .line 1304
    return-void
.end method

.method public static pauseBeforeDisconnect(Landroid/content/Context;)Z
    .locals 11

    .line 1577
    const/4 v0, 0x0

    if-nez p0, :cond_0

    .line 1578
    return v0

    .line 1580
    :cond_0
    invoke-static {p0}, Lhun/mesh/carwithenhance/hook/AutoPlayAllHook;->pausePlayingSessions(Landroid/content/Context;)I

    move-result v1

    .line 1581
    const-string v2, "\u5df2\u9759\u9ed8"

    const-string v3, "\u4ecd\u6d3b\u52a8"

    const-wide/16 v4, 0x640

    const/4 v6, 0x0

    if-gtz v1, :cond_5

    .line 1584
    invoke-static {p0}, Lhun/mesh/carwithenhance/hook/AutoPlayAllHook;->isMusicActive(Landroid/content/Context;)Z

    move-result v1

    if-eqz v1, :cond_4

    .line 1585
    invoke-static {p0}, Lhun/mesh/carwithenhance/hook/AutoPlayAllHook;->dispatchMediaPauseKey(Landroid/content/Context;)Z

    move-result v0

    .line 1586
    new-instance v1, Ljava/lang/StringBuilder;

    invoke-direct {v1}, Ljava/lang/StringBuilder;-><init>()V

    const-string v7, "\u65ad\u5f00\u524d\u672a\u53d1\u73b0\u53ef\u6682\u505c\u7684\u4f1a\u8bdd\uff0c\u4f46\u7cfb\u7edf\u4ecd\u6709\u97f3\u4e50\u5728\u64ad\uff0c"

    invoke-virtual {v1, v7}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v1

    .line 1588
    if-eqz v0, :cond_1

    const-string v7, "\u5df2\u8865\u53d1\u5a92\u4f53\u6682\u505c\u952e"

    goto :goto_0

    :cond_1
    const-string v7, "\u8865\u53d1\u5a92\u4f53\u6682\u505c\u952e\u5931\u8d25"

    :goto_0
    invoke-virtual {v1, v7}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v1

    invoke-virtual {v1}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v1

    .line 1586
    invoke-static {v6, v1}, Lhun/mesh/carwithenhance/hook/AutoPlayAllHook;->log(Landroid/content/Context;Ljava/lang/String;)V

    .line 1589
    if-eqz v0, :cond_3

    .line 1590
    invoke-static {p0, v4, v5}, Lhun/mesh/carwithenhance/hook/AutoPlayAllHook;->waitAudioSilent(Landroid/content/Context;J)Z

    move-result p0

    .line 1591
    new-instance v1, Ljava/lang/StringBuilder;

    invoke-direct {v1}, Ljava/lang/StringBuilder;-><init>()V

    const-string v4, "\u8865\u6682\u505c\u540e\u97f3\u9891\u8f93\u51fa"

    invoke-virtual {v1, v4}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v1

    .line 1592
    if-eqz p0, :cond_2

    goto :goto_1

    :cond_2
    move-object v2, v3

    :goto_1
    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object p0

    const-string v1, "\uff0c\u7ee7\u7eed\u65ad\u5f00\u6d41\u7a0b"

    invoke-virtual {p0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object p0

    invoke-virtual {p0}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object p0

    .line 1591
    invoke-static {v6, p0}, Lhun/mesh/carwithenhance/hook/AutoPlayAllHook;->log(Landroid/content/Context;Ljava/lang/String;)V

    .line 1594
    :cond_3
    return v0

    .line 1596
    :cond_4
    const-string p0, "\u65ad\u5f00\u524d\u65e0\u97f3\u4e50\u5728\u64ad\uff0c\u65e0\u9700\u6682\u505c (\u7cfb\u7edf\u97f3\u9891\u72b6\u6001=\u65e0\u97f3\u4e50)"

    invoke-static {v6, p0}, Lhun/mesh/carwithenhance/hook/AutoPlayAllHook;->log(Landroid/content/Context;Ljava/lang/String;)V

    .line 1598
    return v0

    .line 1600
    :cond_5
    new-instance v0, Ljava/lang/StringBuilder;

    invoke-direct {v0}, Ljava/lang/StringBuilder;-><init>()V

    const-string v7, "\u65ad\u5f00\u524d\u5148\u6682\u505c\u64ad\u653e: \u5df2\u6682\u505c "

    invoke-virtual {v0, v7}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v0

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    move-result-object v0

    const-string v1, " \u4e2a\u4f1a\u8bdd"

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v0

    invoke-virtual {v0}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v0

    invoke-static {v6, v0}, Lhun/mesh/carwithenhance/hook/AutoPlayAllHook;->log(Landroid/content/Context;Ljava/lang/String;)V

    .line 1601
    invoke-static {}, Landroid/os/SystemClock;->elapsedRealtime()J

    move-result-wide v0

    .line 1604
    invoke-static {p0, v4, v5}, Lhun/mesh/carwithenhance/hook/AutoPlayAllHook;->waitAudioSilent(Landroid/content/Context;J)Z

    move-result v7

    .line 1605
    new-instance v8, Ljava/lang/StringBuilder;

    invoke-direct {v8}, Ljava/lang/StringBuilder;-><init>()V

    const-string v9, "\u64ad\u653e\u5df2\u505c\u4e0b("

    invoke-virtual {v8, v9}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v8

    invoke-static {}, Landroid/os/SystemClock;->elapsedRealtime()J

    move-result-wide v9

    sub-long/2addr v9, v0

    invoke-virtual {v8, v9, v10}, Ljava/lang/StringBuilder;->append(J)Ljava/lang/StringBuilder;

    move-result-object v0

    const-string v1, "ms, \u97f3\u9891\u8f93\u51fa"

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v0

    .line 1606
    if-eqz v7, :cond_6

    goto :goto_2

    :cond_6
    move-object v2, v3

    :goto_2
    invoke-virtual {v0, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v0

    const-string v1, ")\uff0c\u7ee7\u7eed\u65ad\u5f00\u6d41\u7a0b"

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v0

    invoke-virtual {v0}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v0

    .line 1605
    invoke-static {v6, v0}, Lhun/mesh/carwithenhance/hook/AutoPlayAllHook;->log(Landroid/content/Context;Ljava/lang/String;)V

    .line 1611
    const-string v0, "hook_disconnect_kill_immediately_enabled"

    const/4 v1, 0x1

    invoke-static {p0, v0, v1}, Lhun/mesh/carwithenhance/hook/AutoPlayAllHook;->isPrefEnabled(Landroid/content/Context;Ljava/lang/String;Z)Z

    move-result v0

    if-eqz v0, :cond_8

    .line 1612
    invoke-static {}, Landroid/os/SystemClock;->elapsedRealtime()J

    move-result-wide v2

    .line 1613
    invoke-static {p0}, Lhun/mesh/carwithenhance/hook/AutoPlayAllHook;->stopPlayingSessions(Landroid/content/Context;)I

    move-result v0

    .line 1614
    invoke-static {p0, v4, v5}, Lhun/mesh/carwithenhance/hook/AutoPlayAllHook;->waitAudioSilent(Landroid/content/Context;J)Z

    move-result p0

    .line 1615
    new-instance v4, Ljava/lang/StringBuilder;

    invoke-direct {v4}, Ljava/lang/StringBuilder;-><init>()V

    const-string v5, "\u5df2\u8bf7\u6c42\u64ad\u653e\u5668\u91ca\u653e\u97f3\u8f68: "

    invoke-virtual {v4, v5}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v4

    invoke-virtual {v4, v0}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    move-result-object v0

    const-string v4, " \u4e2a (\u7b49\u4e86 "

    invoke-virtual {v0, v4}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v0

    .line 1616
    invoke-static {}, Landroid/os/SystemClock;->elapsedRealtime()J

    move-result-wide v4

    sub-long/2addr v4, v2

    invoke-virtual {v0, v4, v5}, Ljava/lang/StringBuilder;->append(J)Ljava/lang/StringBuilder;

    move-result-object v0

    const-string v2, "ms, \u91ca\u653e"

    invoke-virtual {v0, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v0

    .line 1617
    if-eqz p0, :cond_7

    const-string p0, "\u5b8c\u6210"

    goto :goto_3

    :cond_7
    const-string p0, "\u8d85\u65f6"

    :goto_3
    invoke-virtual {v0, p0}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object p0

    const-string v0, ")"

    invoke-virtual {p0, v0}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object p0

    invoke-virtual {p0}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object p0

    .line 1615
    invoke-static {v6, p0}, Lhun/mesh/carwithenhance/hook/AutoPlayAllHook;->log(Landroid/content/Context;Ljava/lang/String;)V

    .line 1619
    :cond_8
    return v1
.end method

.method private static pausePlayingSessions(Landroid/content/Context;)I
    .locals 1

    .line 1522
    const/4 v0, 0x1

    invoke-static {p0, v0}, Lhun/mesh/carwithenhance/hook/AutoPlayAllHook;->pausePlayingSessions(Landroid/content/Context;Z)I

    move-result p0

    return p0
.end method

.method private static pausePlayingSessions(Landroid/content/Context;Z)I
    .locals 9

    .line 1526
    nop

    .line 1528
    const/4 v0, 0x0

    const/4 v1, 0x0

    :try_start_0
    const-string v2, "media_session"

    .line 1529
    invoke-virtual {p0, v2}, Landroid/content/Context;->getSystemService(Ljava/lang/String;)Ljava/lang/Object;

    move-result-object p0

    check-cast p0, Landroid/media/session/MediaSessionManager;

    .line 1530
    if-nez p0, :cond_0

    .line 1531
    return v1

    .line 1533
    :cond_0
    invoke-virtual {p0, v0}, Landroid/media/session/MediaSessionManager;->getActiveSessions(Landroid/content/ComponentName;)Ljava/util/List;

    move-result-object p0

    .line 1534
    if-nez p0, :cond_1

    .line 1535
    return v1

    .line 1537
    :cond_1
    invoke-interface {p0}, Ljava/util/List;->iterator()Ljava/util/Iterator;

    move-result-object p0
    :try_end_0
    .catchall {:try_start_0 .. :try_end_0} :catchall_1

    move v2, v1

    :cond_2
    :goto_0
    :try_start_1
    invoke-interface {p0}, Ljava/util/Iterator;->hasNext()Z

    move-result v3

    if-eqz v3, :cond_9

    invoke-interface {p0}, Ljava/util/Iterator;->next()Ljava/lang/Object;

    move-result-object v3

    check-cast v3, Landroid/media/session/MediaController;

    .line 1538
    if-nez v3, :cond_3

    .line 1539
    goto :goto_0

    .line 1541
    :cond_3
    invoke-virtual {v3}, Landroid/media/session/MediaController;->getPackageName()Ljava/lang/String;

    move-result-object v4

    .line 1543
    if-eqz v4, :cond_2

    invoke-virtual {v4}, Ljava/lang/String;->length()I

    move-result v5

    if-eqz v5, :cond_2

    const-string v5, "com.miui.carlink"

    .line 1544
    invoke-virtual {v5, v4}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v5

    if-nez v5, :cond_2

    invoke-static {v4}, Lhun/mesh/carwithenhance/hook/AutoPlayAllHook;->isNeverKill(Ljava/lang/String;)Z

    move-result v5

    if-eqz v5, :cond_4

    .line 1545
    goto :goto_0

    .line 1547
    :cond_4
    invoke-virtual {v3}, Landroid/media/session/MediaController;->getPlaybackState()Landroid/media/session/PlaybackState;

    move-result-object v5

    .line 1548
    if-nez v5, :cond_5

    move v5, v1

    goto :goto_1

    :cond_5
    invoke-virtual {v5}, Landroid/media/session/PlaybackState;->getState()I

    move-result v5

    .line 1549
    :goto_1
    const/4 v6, 0x3

    if-eq v5, v6, :cond_6

    const/4 v6, 0x6

    if-eq v5, v6, :cond_6

    .line 1550
    goto :goto_0

    .line 1552
    :cond_6
    invoke-virtual {v3}, Landroid/media/session/MediaController;->getTransportControls()Landroid/media/session/MediaController$TransportControls;

    move-result-object v3

    .line 1553
    if-nez v3, :cond_7

    .line 1554
    goto :goto_0

    .line 1556
    :cond_7
    invoke-virtual {v3}, Landroid/media/session/MediaController$TransportControls;->pause()V
    :try_end_1
    .catchall {:try_start_1 .. :try_end_1} :catchall_0

    .line 1557
    add-int/lit8 v2, v2, 0x1

    .line 1558
    const-string v3, ")"

    const-string v6, " (state="

    if-eqz p1, :cond_8

    .line 1559
    :try_start_2
    new-instance v7, Ljava/lang/StringBuilder;

    invoke-direct {v7}, Ljava/lang/StringBuilder;-><init>()V

    const-string v8, "\u65ad\u5f00\u524d\u5df2\u6682\u505c: "

    invoke-virtual {v7, v8}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v7

    invoke-virtual {v7, v4}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v4

    invoke-virtual {v4, v6}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v4

    invoke-virtual {v4, v5}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    move-result-object v4

    invoke-virtual {v4, v3}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v3

    invoke-virtual {v3}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v3

    invoke-static {v0, v3}, Lhun/mesh/carwithenhance/hook/AutoPlayAllHook;->log(Landroid/content/Context;Ljava/lang/String;)V

    goto :goto_2

    .line 1561
    :cond_8
    new-instance v7, Ljava/lang/StringBuilder;

    invoke-direct {v7}, Ljava/lang/StringBuilder;-><init>()V

    const-string v8, "\u65ad\u5f00\u524d\u8865\u6682\u505c: "

    invoke-virtual {v7, v8}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v7

    invoke-virtual {v7, v4}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v4

    invoke-virtual {v4, v6}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v4

    invoke-virtual {v4, v5}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    move-result-object v4

    invoke-virtual {v4, v3}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v3

    invoke-virtual {v3}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v3

    invoke-static {v0, v3}, Lhun/mesh/carwithenhance/hook/AutoPlayAllHook;->log(Landroid/content/Context;Ljava/lang/String;)V
    :try_end_2
    .catchall {:try_start_2 .. :try_end_2} :catchall_0

    .line 1563
    :goto_2
    goto/16 :goto_0

    .line 1566
    :cond_9
    goto :goto_4

    .line 1564
    :catchall_0
    move-exception p0

    move v1, v2

    goto :goto_3

    :catchall_1
    move-exception p0

    .line 1565
    :goto_3
    const-string p1, "\u65ad\u5f00\u524d\u6682\u505c\u5931\u8d25"

    invoke-static {v0, p1, p0}, Lhun/mesh/carwithenhance/hook/AutoPlayAllHook;->logErr(Landroid/content/Context;Ljava/lang/String;Ljava/lang/Throwable;)V

    move v2, v1

    .line 1567
    :goto_4
    return v2
.end method

.method private static playViaSession(Landroid/content/Context;Ljava/lang/String;)Z
    .locals 4

    .line 2821
    const/4 v0, 0x0

    :try_start_0
    const-string v1, "media_session"

    .line 2822
    invoke-virtual {p0, v1}, Landroid/content/Context;->getSystemService(Ljava/lang/String;)Ljava/lang/Object;

    move-result-object p0

    check-cast p0, Landroid/media/session/MediaSessionManager;

    .line 2823
    if-nez p0, :cond_0

    .line 2824
    return v0

    .line 2826
    :cond_0
    const/4 v1, 0x0

    invoke-virtual {p0, v1}, Landroid/media/session/MediaSessionManager;->getActiveSessions(Landroid/content/ComponentName;)Ljava/util/List;

    move-result-object p0

    .line 2827
    if-nez p0, :cond_1

    .line 2828
    return v0

    .line 2830
    :cond_1
    invoke-interface {p0}, Ljava/util/List;->iterator()Ljava/util/Iterator;

    move-result-object p0

    :cond_2
    :goto_0
    invoke-interface {p0}, Ljava/util/Iterator;->hasNext()Z

    move-result v1

    if-eqz v1, :cond_5

    invoke-interface {p0}, Ljava/util/Iterator;->next()Ljava/lang/Object;

    move-result-object v1

    check-cast v1, Landroid/media/session/MediaController;

    .line 2831
    if-eqz v1, :cond_2

    invoke-virtual {v1}, Landroid/media/session/MediaController;->getPackageName()Ljava/lang/String;

    move-result-object v2

    invoke-virtual {p1, v2}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v2

    if-nez v2, :cond_3

    .line 2832
    goto :goto_0

    .line 2834
    :cond_3
    nop

    .line 2835
    invoke-virtual {v1}, Landroid/media/session/MediaController;->getTransportControls()Landroid/media/session/MediaController$TransportControls;

    move-result-object v1

    .line 2836
    if-nez v1, :cond_4

    .line 2837
    goto :goto_0

    .line 2839
    :cond_4
    invoke-virtual {v1}, Landroid/media/session/MediaController$TransportControls;->play()V
    :try_end_0
    .catchall {:try_start_0 .. :try_end_0} :catchall_0

    .line 2840
    const/4 p0, 0x1

    return p0

    .line 2844
    :cond_5
    goto :goto_1

    .line 2842
    :catchall_0
    move-exception p0

    .line 2843
    sget-object v1, Lhun/mesh/carwithenhance/hook/AutoPlayAllHook;->sAppCtx:Landroid/content/Context;

    new-instance v2, Ljava/lang/StringBuilder;

    invoke-direct {v2}, Ljava/lang/StringBuilder;-><init>()V

    const-string v3, "\u4f1a\u8bdd\u64ad\u653e\u5931\u8d25: "

    invoke-virtual {v2, v3}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v2

    invoke-virtual {v2, p1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object p1

    invoke-virtual {p1}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object p1

    invoke-static {v1, p1, p0}, Lhun/mesh/carwithenhance/hook/AutoPlayAllHook;->logErr(Landroid/content/Context;Ljava/lang/String;Ljava/lang/Throwable;)V

    .line 2845
    :goto_1
    return v0
.end method

.method private static playingOtherMedia(Landroid/content/Context;Ljava/lang/String;)Z
    .locals 3

    .line 2683
    :try_start_0
    invoke-static {p0}, Lhun/mesh/carwithenhance/hook/AutoPlayAllHook;->activeSessionPackages(Landroid/content/Context;)Ljava/util/Set;

    move-result-object v0

    invoke-interface {v0}, Ljava/util/Set;->iterator()Ljava/util/Iterator;

    move-result-object v0

    :cond_0
    :goto_0
    invoke-interface {v0}, Ljava/util/Iterator;->hasNext()Z

    move-result v1

    if-eqz v1, :cond_3

    invoke-interface {v0}, Ljava/util/Iterator;->next()Ljava/lang/Object;

    move-result-object v1

    check-cast v1, Ljava/lang/String;

    .line 2684
    invoke-virtual {v1, p1}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v2

    if-nez v2, :cond_0

    sget-object v2, Lhun/mesh/carwithenhance/hook/AutoPlayAllHook;->sCl:Ljava/lang/ClassLoader;

    invoke-static {v2, v1}, Lhun/mesh/carwithenhance/hook/AutoPlayAllHook;->isMediaApp(Ljava/lang/ClassLoader;Ljava/lang/String;)Z

    move-result v2

    if-nez v2, :cond_1

    .line 2685
    goto :goto_0

    .line 2687
    :cond_1
    invoke-static {p0, v1}, Lhun/mesh/carwithenhance/hook/AutoPlayAllHook;->isPlaying(Landroid/content/Context;Ljava/lang/String;)Z

    move-result v1
    :try_end_0
    .catchall {:try_start_0 .. :try_end_0} :catchall_0

    if-eqz v1, :cond_2

    .line 2688
    const/4 p0, 0x1

    return p0

    .line 2690
    :cond_2
    goto :goto_0

    .line 2692
    :cond_3
    goto :goto_1

    .line 2691
    :catchall_0
    move-exception p0

    .line 2693
    :goto_1
    const/4 p0, 0x0

    return p0
.end method

.method private static preWakeForCarOpen(Ljava/lang/String;Ljava/lang/String;)V
    .locals 3

    .line 978
    if-eqz p0, :cond_7

    :try_start_0
    invoke-virtual {p0}, Ljava/lang/String;->length()I

    move-result v0

    if-nez v0, :cond_0

    goto :goto_3

    .line 981
    :cond_0
    sget-object v0, Lhun/mesh/carwithenhance/hook/AutoPlayAllHook;->sAppCtx:Landroid/content/Context;

    .line 982
    if-eqz v0, :cond_6

    invoke-static {v0}, Lhun/mesh/carwithenhance/hook/AutoPlayAllHook;->isEnabled(Landroid/content/Context;)Z

    move-result v1

    if-nez v1, :cond_1

    goto :goto_1

    .line 985
    :cond_1
    invoke-static {v0, p0}, Lhun/mesh/carwithenhance/hook/AutoPlayAllHook;->blockedByUserKill(Landroid/content/Context;Ljava/lang/String;)Z

    move-result v1

    if-eqz v1, :cond_2

    .line 987
    return-void

    .line 989
    :cond_2
    invoke-static {p0}, Lhun/mesh/carwithenhance/hook/AutoPlayAllHook;->blockedBySwitchDrop(Ljava/lang/String;)Z

    move-result v1

    if-eqz v1, :cond_3

    .line 991
    return-void

    .line 993
    :cond_3
    invoke-static {v0, p0}, Lhun/mesh/carwithenhance/hook/AutoPlayAllHook;->isProcessRunning(Landroid/content/Context;Ljava/lang/String;)Z

    move-result v1

    if-nez v1, :cond_5

    sget-object v1, Lhun/mesh/carwithenhance/hook/AutoPlayAllHook;->sCl:Ljava/lang/ClassLoader;

    invoke-static {v1, p0}, Lhun/mesh/carwithenhance/hook/AutoPlayAllHook;->isTarget(Ljava/lang/ClassLoader;Ljava/lang/String;)Z

    move-result v1

    if-nez v1, :cond_4

    goto :goto_0

    .line 996
    :cond_4
    new-instance v1, Ljava/lang/StringBuilder;

    invoke-direct {v1}, Ljava/lang/StringBuilder;-><init>()V

    invoke-virtual {v1, p1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object p1

    const-string v1, " "

    invoke-virtual {p1, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object p1

    invoke-virtual {p1, p0}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object p1

    const-string v1, "\uff0c\u5148\u628a\u5b83\u540e\u53f0\u5524\u9192"

    invoke-virtual {p1, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object p1

    invoke-virtual {p1}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object p1

    invoke-static {v0, p1}, Lhun/mesh/carwithenhance/hook/AutoPlayAllHook;->log(Landroid/content/Context;Ljava/lang/String;)V

    .line 997
    sget-object p1, Lhun/mesh/carwithenhance/hook/AutoPlayAllHook;->CAR_OPEN_AT:Ljava/util/Map;

    invoke-static {}, Landroid/os/SystemClock;->elapsedRealtime()J

    move-result-wide v1

    invoke-static {v1, v2}, Ljava/lang/Long;->valueOf(J)Ljava/lang/Long;

    move-result-object v1

    invoke-interface {p1, p0, v1}, Ljava/util/Map;->put(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;

    .line 998
    invoke-static {p0}, Lhun/mesh/carwithenhance/hook/AutoPlayAllHook;->markSessionMedia(Ljava/lang/String;)V

    .line 999
    sget-object p1, Lhun/mesh/carwithenhance/hook/AutoPlayAllHook;->WOKE:Ljava/util/Set;

    invoke-interface {p1, p0}, Ljava/util/Set;->add(Ljava/lang/Object;)Z

    .line 1000
    sput-object p0, Lhun/mesh/carwithenhance/hook/AutoPlayAllHook;->sActiveWokePkg:Ljava/lang/String;

    .line 1001
    invoke-static {v0, p0}, Lhun/mesh/carwithenhance/hook/AutoPlayAllHook;->sendMediaButton(Landroid/content/Context;Ljava/lang/String;)Z

    .line 1002
    invoke-static {v0, p0}, Lhun/mesh/carwithenhance/hook/AutoPlayAllHook;->requestModuleBind(Landroid/content/Context;Ljava/lang/String;)V
    :try_end_0
    .catchall {:try_start_0 .. :try_end_0} :catchall_0

    .line 1004
    goto :goto_2

    .line 994
    :cond_5
    :goto_0
    return-void

    .line 983
    :cond_6
    :goto_1
    return-void

    .line 1003
    :catchall_0
    move-exception p0

    .line 1005
    :goto_2
    return-void

    .line 979
    :cond_7
    :goto_3
    return-void
.end method

.method private static preWakeLastUsedApp()V
    .locals 5

    .line 1128
    sget-boolean v4, Lhun/mesh/carwithenhance/hook/AutoPlayAllHook;->sInitialResumeDone:Z

    if-eqz v4, :cond_0

    return-void

    :cond_0
    const/4 v4, 0x1

    sput-boolean v4, Lhun/mesh/carwithenhance/hook/AutoPlayAllHook;->sInitialResumeDone:Z

    :try_start_0
    sget-object v0, Lhun/mesh/carwithenhance/hook/AutoPlayAllHook;->sAppCtx:Landroid/content/Context;

    .line 1129
    if-eqz v0, :cond_8

    invoke-static {v0}, Lhun/mesh/carwithenhance/hook/AutoPlayAllHook;->isEnabled(Landroid/content/Context;)Z

    move-result v1

    if-nez v1, :cond_1

    goto/16 :goto_3

    .line 1132
    :cond_1
    invoke-static {}, Lhun/mesh/carwithenhance/hook/AutoPlayAllHook;->readLastUsedMediaApp()Ljava/lang/String;

    move-result-object v1

    .line 1133
    if-eqz v1, :cond_7

    invoke-virtual {v1}, Ljava/lang/String;->length()I

    move-result v2

    if-nez v2, :cond_2

    goto/16 :goto_2

    .line 1136
    :cond_2
    sget-object v2, Lhun/mesh/carwithenhance/hook/AutoPlayAllHook;->sCl:Ljava/lang/ClassLoader;

    invoke-static {v2, v1}, Lhun/mesh/carwithenhance/hook/AutoPlayAllHook;->isMediaApp(Ljava/lang/ClassLoader;Ljava/lang/String;)Z

    move-result v2

    if-eqz v2, :cond_6

    invoke-static {v1}, Lhun/mesh/carwithenhance/hook/AutoPlayAllHook;->isNeverKill(Ljava/lang/String;)Z

    move-result v2

    if-eqz v2, :cond_3

    goto/16 :goto_1

    .line 1140
    :cond_3
    new-instance v2, Ljava/lang/StringBuilder;

    invoke-direct {v2}, Ljava/lang/StringBuilder;-><init>()V

    const-string v3, "\u672c\u6b21\u8fde\u63a5\u5f85\u7eed\u64ad: "

    invoke-virtual {v2, v3}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v2

    invoke-virtual {v2, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v2

    const-string v3, " (CarWith\u81ea\u5e26\u7eed\u64ad\u5f00\u5173="

    invoke-virtual {v2, v3}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v2

    .line 1141
    invoke-static {}, Lhun/mesh/carwithenhance/hook/AutoPlayAllHook;->readPrefAutoPlay()Z

    move-result v3

    invoke-virtual {v2, v3}, Ljava/lang/StringBuilder;->append(Z)Ljava/lang/StringBuilder;

    move-result-object v2

    const-string v3, ")"

    invoke-virtual {v2, v3}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v2

    invoke-virtual {v2}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v2

    .line 1140
    invoke-static {v0, v2}, Lhun/mesh/carwithenhance/hook/AutoPlayAllHook;->log(Landroid/content/Context;Ljava/lang/String;)V

    .line 1142
    invoke-static {v1}, Lhun/mesh/carwithenhance/hook/AutoPlayAllHook;->learnCurrentMedia(Ljava/lang/String;)V

    .line 1143
    invoke-static {v0, v1}, Lhun/mesh/carwithenhance/hook/AutoPlayAllHook;->isProcessRunning(Landroid/content/Context;Ljava/lang/String;)Z

    move-result v2

    if-nez v2, :cond_5

    sget-object v2, Lhun/mesh/carwithenhance/hook/AutoPlayAllHook;->sCl:Ljava/lang/ClassLoader;

    invoke-static {v2, v1}, Lhun/mesh/carwithenhance/hook/AutoPlayAllHook;->isTarget(Ljava/lang/ClassLoader;Ljava/lang/String;)Z

    move-result v2

    if-nez v2, :cond_4

    goto :goto_0

    .line 1148
    :cond_4
    new-instance v2, Ljava/lang/StringBuilder;

    invoke-direct {v2}, Ljava/lang/StringBuilder;-><init>()V

    const-string v3, "\u8fde\u63a5\u5373\u5524\u9192\u4e0a\u6b21\u4f7f\u7528\u7684\u64ad\u653e\u5668: "

    invoke-virtual {v2, v3}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v2

    invoke-virtual {v2, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v2

    invoke-virtual {v2}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v2

    invoke-static {v0, v2}, Lhun/mesh/carwithenhance/hook/AutoPlayAllHook;->log(Landroid/content/Context;Ljava/lang/String;)V

    .line 1149
    sget-object v2, Lhun/mesh/carwithenhance/hook/AutoPlayAllHook;->CAR_OPEN_AT:Ljava/util/Map;

    invoke-static {}, Landroid/os/SystemClock;->elapsedRealtime()J

    move-result-wide v3

    invoke-static {v3, v4}, Ljava/lang/Long;->valueOf(J)Ljava/lang/Long;

    move-result-object v3

    invoke-interface {v2, v1, v3}, Ljava/util/Map;->put(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;

    .line 1150
    invoke-static {v1}, Lhun/mesh/carwithenhance/hook/AutoPlayAllHook;->markSessionMedia(Ljava/lang/String;)V

    .line 1151
    sget-object v2, Lhun/mesh/carwithenhance/hook/AutoPlayAllHook;->WOKE:Ljava/util/Set;

    invoke-interface {v2, v1}, Ljava/util/Set;->add(Ljava/lang/Object;)Z

    .line 1152
    sput-object v1, Lhun/mesh/carwithenhance/hook/AutoPlayAllHook;->sActiveWokePkg:Ljava/lang/String;

    .line 1153
    invoke-static {v0, v1}, Lhun/mesh/carwithenhance/hook/AutoPlayAllHook;->sendMediaButton(Landroid/content/Context;Ljava/lang/String;)Z

    .line 1154
    invoke-static {v0, v1}, Lhun/mesh/carwithenhance/hook/AutoPlayAllHook;->startPlayerService(Landroid/content/Context;Ljava/lang/String;)Z

    .line 1155
    invoke-static {v0, v1}, Lhun/mesh/carwithenhance/hook/AutoPlayAllHook;->requestModuleBind(Landroid/content/Context;Ljava/lang/String;)V

    .line 1158
    sget-object v0, Lhun/mesh/carwithenhance/hook/AutoPlayAllHook;->sCl:Ljava/lang/ClassLoader;

    invoke-static {v0, v1}, Lhun/mesh/carwithenhance/hook/AutoPlayAllHook;->startRetryLoop(Ljava/lang/ClassLoader;Ljava/lang/String;)V

    .line 1160
    goto :goto_4

    .line 1145
    :cond_5
    :goto_0
    sget-object v0, Lhun/mesh/carwithenhance/hook/AutoPlayAllHook;->sCl:Ljava/lang/ClassLoader;

    invoke-static {v0, v1}, Lhun/mesh/carwithenhance/hook/AutoPlayAllHook;->startRetryLoop(Ljava/lang/ClassLoader;Ljava/lang/String;)V
    :try_end_0
    .catchall {:try_start_0 .. :try_end_0} :catchall_0

    .line 1146
    return-void

    .line 1138
    :cond_6
    :goto_1
    return-void

    .line 1134
    :cond_7
    :goto_2
    return-void

    .line 1130
    :cond_8
    :goto_3
    return-void

    .line 1159
    :catchall_0
    move-exception v0

    .line 1161
    :goto_4
    return-void
.end method

.method private static quote(Ljava/lang/Object;)Ljava/lang/String;
    .locals 3

    .line 2342
    instance-of v0, p0, Ljava/lang/String;

    if-nez v0, :cond_0

    .line 2343
    const-string p0, "null"

    return-object p0

    .line 2345
    :cond_0
    check-cast p0, Ljava/lang/String;

    .line 2346
    invoke-virtual {p0}, Ljava/lang/String;->length()I

    move-result v0

    const/16 v1, 0x28

    const-string v2, "\""

    if-le v0, v1, :cond_1

    new-instance v0, Ljava/lang/StringBuilder;

    invoke-direct {v0}, Ljava/lang/StringBuilder;-><init>()V

    invoke-virtual {v0, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v0

    const/4 v2, 0x0

    invoke-virtual {p0, v2, v1}, Ljava/lang/String;->substring(II)Ljava/lang/String;

    move-result-object p0

    invoke-virtual {v0, p0}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object p0

    const-string v0, "...\""

    invoke-virtual {p0, v0}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object p0

    goto :goto_0

    :cond_1
    new-instance v0, Ljava/lang/StringBuilder;

    invoke-direct {v0}, Ljava/lang/StringBuilder;-><init>()V

    invoke-virtual {v0, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v0

    invoke-virtual {v0, p0}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object p0

    invoke-virtual {p0, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object p0

    :goto_0
    invoke-virtual {p0}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object p0

    return-object p0
.end method

.method private static readFpsIndex()I
    .locals 4

    .line 483
    const/4 v0, -0x1

    :try_start_0
    sget-object v1, Lhun/mesh/carwithenhance/hook/AutoPlayAllHook;->sAppCtx:Landroid/content/Context;

    .line 484
    if-nez v1, :cond_0

    .line 485
    return v0

    .line 487
    :cond_0
    const-string v2, "spUtils"

    const/4 v3, 0x0

    invoke-virtual {v1, v2, v3}, Landroid/content/Context;->getSharedPreferences(Ljava/lang/String;I)Landroid/content/SharedPreferences;

    move-result-object v1

    const-string v2, "user_setting_framerate"

    .line 488
    invoke-interface {v1, v2, v0}, Landroid/content/SharedPreferences;->getInt(Ljava/lang/String;I)I

    move-result v0
    :try_end_0
    .catchall {:try_start_0 .. :try_end_0} :catchall_0

    .line 487
    return v0

    .line 489
    :catchall_0
    move-exception v1

    .line 490
    return v0
.end method

.method private static readLastUsedMediaApp()Ljava/lang/String;
    .locals 4

    .line 1165
    invoke-static {}, Lhun/mesh/carwithenhance/hook/AutoPlayAllHook;->selectedApp()Ljava/lang/String;

    move-result-object v0

    .line 1166
    if-eqz v0, :cond_0

    invoke-virtual {v0}, Ljava/lang/String;->length()I

    move-result v1

    if-lez v1, :cond_0

    .line 1167
    return-object v0

    .line 1170
    :cond_0
    :try_start_0
    const-string v0, "ma.a"

    sget-object v1, Lhun/mesh/carwithenhance/hook/AutoPlayAllHook;->sCl:Ljava/lang/ClassLoader;

    invoke-static {v0, v1}, Lde/robv/android/xposed/XposedHelpers;->findClass(Ljava/lang/String;Ljava/lang/ClassLoader;)Ljava/lang/Class;

    move-result-object v0

    .line 1171
    const-string v1, "p"

    const/4 v2, 0x0

    new-array v3, v2, [Ljava/lang/Object;

    invoke-static {v0, v1, v3}, Lde/robv/android/xposed/XposedHelpers;->callStaticMethod(Ljava/lang/Class;Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v0

    .line 1172
    const-string v1, "o"

    new-array v2, v2, [Ljava/lang/Object;

    invoke-static {v0, v1, v2}, Lde/robv/android/xposed/XposedHelpers;->callMethod(Ljava/lang/Object;Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v0

    .line 1173
    instance-of v1, v0, Ljava/lang/String;

    if-eqz v1, :cond_1

    move-object v1, v0

    check-cast v1, Ljava/lang/String;

    invoke-virtual {v1}, Ljava/lang/String;->length()I

    move-result v1

    if-lez v1, :cond_1

    .line 1174
    check-cast v0, Ljava/lang/String;
    :try_end_0
    .catchall {:try_start_0 .. :try_end_0} :catchall_0

    return-object v0

    .line 1177
    :cond_1
    goto :goto_0

    .line 1176
    :catchall_0
    move-exception v0

    .line 1178
    :goto_0
    const/4 v0, 0x0

    return-object v0
.end method

.method private static readPrefAutoPlay()Z
    .locals 4

    .line 1216
    const/4 v0, 0x1

    :try_start_0
    sget-object v1, Lhun/mesh/carwithenhance/hook/AutoPlayAllHook;->sAppCtx:Landroid/content/Context;

    const-string v2, "file_prefer_app_091703"

    const/4 v3, 0x0

    invoke-virtual {v1, v2, v3}, Landroid/content/Context;->getSharedPreferences(Ljava/lang/String;I)Landroid/content/SharedPreferences;

    move-result-object v1

    const-string v2, "prefer_auto_play"

    .line 1217
    invoke-interface {v1, v2, v0}, Landroid/content/SharedPreferences;->getBoolean(Ljava/lang/String;Z)Z

    move-result v0
    :try_end_0
    .catchall {:try_start_0 .. :try_end_0} :catchall_0

    .line 1216
    return v0

    .line 1218
    :catchall_0
    move-exception v1

    .line 1219
    return v0
.end method

.method private static readPrefMediaApp()Ljava/lang/String;
    .locals 4

    .line 1197
    const/4 v0, 0x0

    :try_start_0
    sget-object v1, Lhun/mesh/carwithenhance/hook/AutoPlayAllHook;->sAppCtx:Landroid/content/Context;

    const-string v2, "file_prefer_app_091703"

    const/4 v3, 0x0

    invoke-virtual {v1, v2, v3}, Landroid/content/Context;->getSharedPreferences(Ljava/lang/String;I)Landroid/content/SharedPreferences;

    move-result-object v1

    const-string v2, "prefer_media_app"

    .line 1198
    invoke-interface {v1, v2, v0}, Landroid/content/SharedPreferences;->getString(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;

    move-result-object v0
    :try_end_0
    .catchall {:try_start_0 .. :try_end_0} :catchall_0

    .line 1197
    return-object v0

    .line 1199
    :catchall_0
    move-exception v1

    .line 1200
    return-object v0
.end method

.method private static readPrefMusicApp()Ljava/lang/String;
    .locals 4

    .line 1206
    const/4 v0, 0x0

    :try_start_0
    sget-object v1, Lhun/mesh/carwithenhance/hook/AutoPlayAllHook;->sAppCtx:Landroid/content/Context;

    const-string v2, "file_prefer_app_091703"

    const/4 v3, 0x0

    invoke-virtual {v1, v2, v3}, Landroid/content/Context;->getSharedPreferences(Ljava/lang/String;I)Landroid/content/SharedPreferences;

    move-result-object v1

    const-string v2, "prefer_music_app"

    .line 1207
    invoke-interface {v1, v2, v0}, Landroid/content/SharedPreferences;->getString(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;

    move-result-object v0
    :try_end_0
    .catchall {:try_start_0 .. :try_end_0} :catchall_0

    .line 1206
    return-object v0

    .line 1208
    :catchall_0
    move-exception v1

    .line 1209
    return-object v0
.end method

.method private static recentlyOpenedOnCar(Ljava/lang/String;)Z
    .locals 7

    .line 1009
    sget-object v0, Lhun/mesh/carwithenhance/hook/AutoPlayAllHook;->CAR_OPEN_AT:Ljava/util/Map;

    invoke-interface {v0, p0}, Ljava/util/Map;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v1

    check-cast v1, Ljava/lang/Long;

    .line 1010
    const/4 v2, 0x0

    if-nez v1, :cond_0

    .line 1011
    return v2

    .line 1013
    :cond_0
    invoke-static {}, Landroid/os/SystemClock;->elapsedRealtime()J

    move-result-wide v3

    invoke-virtual {v1}, Ljava/lang/Long;->longValue()J

    move-result-wide v5

    sub-long/2addr v3, v5

    const-wide/16 v5, 0x3a98

    cmp-long v1, v3, v5

    if-lez v1, :cond_1

    .line 1014
    invoke-interface {v0, p0}, Ljava/util/Map;->remove(Ljava/lang/Object;)Ljava/lang/Object;

    .line 1015
    return v2

    .line 1017
    :cond_1
    const/4 p0, 0x1

    return p0
.end method

.method private static registerTestReceiver(Ljava/lang/ClassLoader;)V
    .locals 4

    .line 2010
    const/4 v0, 0x0

    :try_start_0
    invoke-static {p0}, Lhun/mesh/carwithenhance/utils/AppUtils;->getAppContext(Ljava/lang/ClassLoader;)Landroid/content/Context;

    move-result-object v1

    .line 2011
    if-nez v1, :cond_0

    .line 2012
    return-void

    .line 2014
    :cond_0
    invoke-static {v1}, Lhun/mesh/carwithenhance/hook/AutoPlayAllHook;->setAppContext(Landroid/content/Context;)V

    .line 2015
    new-instance v2, Landroid/content/IntentFilter;

    const-string v3, "hun.mesh.carwithenhance.action.TEST"

    invoke-direct {v2, v3}, Landroid/content/IntentFilter;-><init>(Ljava/lang/String;)V

    .line 2016
    new-instance v3, Lhun/mesh/carwithenhance/hook/AutoPlayAllHook$20;

    invoke-direct {v3, p0}, Lhun/mesh/carwithenhance/hook/AutoPlayAllHook$20;-><init>(Ljava/lang/ClassLoader;)V

    invoke-virtual {v1, v3, v2}, Landroid/content/Context;->registerReceiver(Landroid/content/BroadcastReceiver;Landroid/content/IntentFilter;)Landroid/content/Intent;

    .line 2292
    const-string p0, "\u6d4b\u8bd5\u5165\u53e3\u5df2\u6ce8\u518c"

    invoke-static {v0, p0}, Lhun/mesh/carwithenhance/hook/AutoPlayAllHook;->log(Landroid/content/Context;Ljava/lang/String;)V
    :try_end_0
    .catchall {:try_start_0 .. :try_end_0} :catchall_0

    .line 2295
    goto :goto_0

    .line 2293
    :catchall_0
    move-exception p0

    .line 2294
    const-string v1, "\u6d4b\u8bd5\u5165\u53e3\u6ce8\u518c\u5931\u8d25"

    invoke-static {v0, v1, p0}, Lhun/mesh/carwithenhance/hook/AutoPlayAllHook;->logErr(Landroid/content/Context;Ljava/lang/String;Ljava/lang/Throwable;)V

    .line 2296
    :goto_0
    return-void
.end method

.method private static releaseAllWoke(Ljava/lang/String;)V
    .locals 6

    .line 2493
    sget-object v0, Lhun/mesh/carwithenhance/hook/AutoPlayAllHook;->sAppCtx:Landroid/content/Context;

    .line 2494
    new-instance v1, Ljava/util/ArrayList;

    sget-object v2, Lhun/mesh/carwithenhance/hook/AutoPlayAllHook;->WOKE:Ljava/util/Set;

    invoke-direct {v1, v2}, Ljava/util/ArrayList;-><init>(Ljava/util/Collection;)V

    .line 2495
    const/4 v2, 0x0

    sput-object v2, Lhun/mesh/carwithenhance/hook/AutoPlayAllHook;->sActiveWokePkg:Ljava/lang/String;

    .line 2496
    sput-object v2, Lhun/mesh/carwithenhance/hook/AutoPlayAllHook;->sLastAcceptedPkg:Ljava/lang/String;

    .line 2497
    sget-wide v2, Lhun/mesh/carwithenhance/hook/AutoPlayAllHook;->sRetryGen:J

    const-wide/16 v4, 0x1

    add-long/2addr v2, v4

    sput-wide v2, Lhun/mesh/carwithenhance/hook/AutoPlayAllHook;->sRetryGen:J

    .line 2498
    invoke-interface {v1}, Ljava/util/List;->iterator()Ljava/util/Iterator;

    move-result-object v1

    :goto_0
    invoke-interface {v1}, Ljava/util/Iterator;->hasNext()Z

    move-result v2

    if-eqz v2, :cond_0

    invoke-interface {v1}, Ljava/util/Iterator;->next()Ljava/lang/Object;

    move-result-object v2

    check-cast v2, Ljava/lang/String;

    .line 2499
    invoke-static {v0, v2, p0}, Lhun/mesh/carwithenhance/hook/AutoPlayAllHook;->releaseWoke(Landroid/content/Context;Ljava/lang/String;Ljava/lang/String;)V

    .line 2500
    goto :goto_0

    .line 2501
    :cond_0
    sget-object v1, Lhun/mesh/carwithenhance/hook/AutoPlayAllHook;->WOKE:Ljava/util/Set;

    invoke-interface {v1}, Ljava/util/Set;->clear()V

    .line 2502
    sget-object v1, Lhun/mesh/carwithenhance/hook/AutoPlayAllHook;->SESSION_MEDIA:Ljava/util/Set;

    invoke-interface {v1}, Ljava/util/Set;->clear()V

    .line 2503
    new-instance v1, Ljava/lang/StringBuilder;

    invoke-direct {v1}, Ljava/lang/StringBuilder;-><init>()V

    const-string v2, "\u5df2\u7ed3\u675f\u672c\u529f\u80fd\u5524\u9192\u8fc7\u7684\u64ad\u653e\u5668 ("

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v1

    invoke-virtual {v1, p0}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object p0

    const-string v1, ")"

    invoke-virtual {p0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object p0

    invoke-virtual {p0}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object p0

    invoke-static {v0, p0}, Lhun/mesh/carwithenhance/hook/AutoPlayAllHook;->log(Landroid/content/Context;Ljava/lang/String;)V

    .line 2504
    return-void
.end method

.method private static releaseCarwithBinding(Ljava/lang/String;)V
    .locals 4

    .line 2459
    const/4 v0, 0x0

    :try_start_0
    sget-object v1, Lhun/mesh/carwithenhance/hook/AutoPlayAllHook;->sCl:Ljava/lang/ClassLoader;

    .line 2460
    if-nez v1, :cond_0

    .line 2461
    return-void

    .line 2463
    :cond_0
    const-string v2, "ra.a"

    invoke-static {v2, v1}, Lde/robv/android/xposed/XposedHelpers;->findClass(Ljava/lang/String;Ljava/lang/ClassLoader;)Ljava/lang/Class;

    move-result-object v1

    .line 2464
    const-string v2, "c"

    const/4 v3, 0x0

    new-array v3, v3, [Ljava/lang/Object;

    invoke-static {v1, v2, v3}, Lde/robv/android/xposed/XposedHelpers;->callStaticMethod(Ljava/lang/Class;Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v1

    .line 2465
    if-eqz v1, :cond_1

    .line 2466
    const-string v2, "h"

    filled-new-array {p0}, [Ljava/lang/Object;

    move-result-object v3

    invoke-static {v1, v2, v3}, Lde/robv/android/xposed/XposedHelpers;->callMethod(Ljava/lang/Object;Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/Object;

    .line 2467
    new-instance v1, Ljava/lang/StringBuilder;

    invoke-direct {v1}, Ljava/lang/StringBuilder;-><init>()V

    const-string v2, "\u5df2\u91ca\u653e CarWith \u5bf9 "

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v1

    invoke-virtual {v1, p0}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v1

    const-string v2, " \u7684\u5a92\u4f53\u7ed1\u5b9a"

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v1

    invoke-virtual {v1}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v1

    invoke-static {v0, v1}, Lhun/mesh/carwithenhance/hook/AutoPlayAllHook;->log(Landroid/content/Context;Ljava/lang/String;)V
    :try_end_0
    .catchall {:try_start_0 .. :try_end_0} :catchall_0

    .line 2471
    :cond_1
    goto :goto_0

    .line 2469
    :catchall_0
    move-exception v1

    .line 2470
    new-instance v2, Ljava/lang/StringBuilder;

    invoke-direct {v2}, Ljava/lang/StringBuilder;-><init>()V

    const-string v3, "\u91ca\u653e CarWith \u5a92\u4f53\u7ed1\u5b9a\u5931\u8d25: "

    invoke-virtual {v2, v3}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v2

    invoke-virtual {v2, p0}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object p0

    invoke-virtual {p0}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object p0

    invoke-static {v0, p0, v1}, Lhun/mesh/carwithenhance/hook/AutoPlayAllHook;->logErr(Landroid/content/Context;Ljava/lang/String;Ljava/lang/Throwable;)V

    .line 2472
    :goto_0
    return-void
.end method

.method private static releaseOwnBrowsers(Ljava/lang/String;)V
    .locals 4

    .line 2476
    :try_start_0
    new-instance v0, Ljava/util/ArrayList;

    sget-object v1, Lhun/mesh/carwithenhance/hook/AutoPlayAllHook;->BROWSERS:Ljava/util/Map;

    invoke-interface {v1}, Ljava/util/Map;->keySet()Ljava/util/Set;

    move-result-object v1

    invoke-direct {v0, v1}, Ljava/util/ArrayList;-><init>(Ljava/util/Collection;)V

    invoke-virtual {v0}, Ljava/util/ArrayList;->iterator()Ljava/util/Iterator;

    move-result-object v0

    :goto_0
    invoke-interface {v0}, Ljava/util/Iterator;->hasNext()Z

    move-result v1

    if-eqz v1, :cond_1

    invoke-interface {v0}, Ljava/util/Iterator;->next()Ljava/lang/Object;

    move-result-object v1

    check-cast v1, Ljava/lang/String;

    .line 2477
    new-instance v2, Ljava/lang/StringBuilder;

    invoke-direct {v2}, Ljava/lang/StringBuilder;-><init>()V

    invoke-virtual {v2, p0}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v2

    const-string v3, "/"

    invoke-virtual {v2, v3}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v2

    invoke-virtual {v2}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v2

    invoke-virtual {v1, v2}, Ljava/lang/String;->startsWith(Ljava/lang/String;)Z

    move-result v2

    if-eqz v2, :cond_0

    .line 2478
    sget-object v2, Lhun/mesh/carwithenhance/hook/AutoPlayAllHook;->BROWSERS:Ljava/util/Map;

    invoke-interface {v2, v1}, Ljava/util/Map;->remove(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v2

    check-cast v2, Landroid/media/browse/MediaBrowser;

    .line 2479
    sget-object v3, Lhun/mesh/carwithenhance/hook/AutoPlayAllHook;->ATTEMPTS:Ljava/util/Map;

    invoke-interface {v3, v1}, Ljava/util/Map;->remove(Ljava/lang/Object;)Ljava/lang/Object;
    :try_end_0
    .catchall {:try_start_0 .. :try_end_0} :catchall_1

    .line 2480
    if-eqz v2, :cond_0

    .line 2482
    :try_start_1
    invoke-virtual {v2}, Landroid/media/browse/MediaBrowser;->disconnect()V
    :try_end_1
    .catchall {:try_start_1 .. :try_end_1} :catchall_0

    .line 2484
    goto :goto_1

    .line 2483
    :catchall_0
    move-exception v1

    .line 2487
    :cond_0
    :goto_1
    goto :goto_0

    .line 2489
    :cond_1
    goto :goto_2

    .line 2488
    :catchall_1
    move-exception p0

    .line 2490
    :goto_2
    return-void
.end method

.method private static releaseWoke(Landroid/content/Context;Ljava/lang/String;Ljava/lang/String;)V
    .locals 2

    .line 2352
    :try_start_0
    sget-object v0, Lhun/mesh/carwithenhance/hook/AutoPlayAllHook;->WOKE:Ljava/util/Set;

    invoke-interface {v0, p1}, Ljava/util/Set;->remove(Ljava/lang/Object;)Z

    move-result v0

    .line 2353
    if-nez v0, :cond_0

    .line 2354
    return-void

    .line 2358
    :cond_0
    invoke-static {p1}, Lhun/mesh/carwithenhance/hook/AutoPlayAllHook;->releaseCarwithBinding(Ljava/lang/String;)V

    .line 2359
    invoke-static {p1}, Lhun/mesh/carwithenhance/hook/AutoPlayAllHook;->releaseOwnBrowsers(Ljava/lang/String;)V

    .line 2360
    invoke-static {p0, p1}, Lhun/mesh/carwithenhance/hook/AutoPlayAllHook;->gracefulStopBeforeKill(Landroid/content/Context;Ljava/lang/String;)V

    .line 2361
    invoke-static {p0, p1, p2}, Lhun/mesh/carwithenhance/hook/AutoPlayAllHook;->forceStop(Landroid/content/Context;Ljava/lang/String;Ljava/lang/String;)V
    :try_end_0
    .catchall {:try_start_0 .. :try_end_0} :catchall_0

    .line 2364
    goto :goto_0

    .line 2362
    :catchall_0
    move-exception p2

    .line 2363
    new-instance v0, Ljava/lang/StringBuilder;

    invoke-direct {v0}, Ljava/lang/StringBuilder;-><init>()V

    const-string v1, "\u91ca\u653e\u5931\u8d25: "

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v0

    invoke-virtual {v0, p1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object p1

    invoke-virtual {p1}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object p1

    invoke-static {p0, p1, p2}, Lhun/mesh/carwithenhance/hook/AutoPlayAllHook;->logErr(Landroid/content/Context;Ljava/lang/String;Ljava/lang/Throwable;)V

    .line 2365
    :goto_0
    return-void
.end method

.method private static reloadAppList()V
    .locals 3

    .line 684
    sget-object v0, Lhun/mesh/carwithenhance/hook/AutoPlayAllHook;->sAppsVm:Ljava/lang/Object;

    .line 685
    if-nez v0, :cond_0

    .line 686
    return-void

    .line 689
    :cond_0
    :try_start_0
    const-string v1, "i"

    const/4 v2, 0x0

    filled-new-array {v2}, [Ljava/lang/Object;

    move-result-object v2

    invoke-static {v0, v1, v2}, Lde/robv/android/xposed/XposedHelpers;->callMethod(Ljava/lang/Object;Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/Object;

    .line 690
    sget-object v0, Lhun/mesh/carwithenhance/hook/AutoPlayAllHook;->sAppCtx:Landroid/content/Context;

    const-string v1, "\u5df2\u8bf7\u6c42\u91cd\u65b0\u6784\u5efa\u8f66\u673a\u5e94\u7528\u5217\u8868"

    invoke-static {v0, v1}, Lhun/mesh/carwithenhance/hook/AutoPlayAllHook;->log(Landroid/content/Context;Ljava/lang/String;)V
    :try_end_0
    .catchall {:try_start_0 .. :try_end_0} :catchall_0

    .line 693
    goto :goto_0

    .line 691
    :catchall_0
    move-exception v0

    .line 692
    sget-object v1, Lhun/mesh/carwithenhance/hook/AutoPlayAllHook;->sAppCtx:Landroid/content/Context;

    const-string v2, "\u91cd\u5efa\u8f66\u673a\u5e94\u7528\u5217\u8868\u5931\u8d25"

    invoke-static {v1, v2, v0}, Lhun/mesh/carwithenhance/hook/AutoPlayAllHook;->logErr(Landroid/content/Context;Ljava/lang/String;Ljava/lang/Throwable;)V

    .line 694
    :goto_0
    return-void
.end method

.method private static rememberUsedMediaApp(Ljava/lang/ClassLoader;Ljava/lang/String;)V
    .locals 3

    .line 1027
    const-string v0, "d"

    if-eqz p0, :cond_4

    if-eqz p1, :cond_4

    :try_start_0
    invoke-virtual {p1}, Ljava/lang/String;->length()I

    move-result v1

    if-nez v1, :cond_0

    goto :goto_1

    .line 1030
    :cond_0
    invoke-static {p0, p1}, Lhun/mesh/carwithenhance/hook/AutoPlayAllHook;->isTarget(Ljava/lang/ClassLoader;Ljava/lang/String;)Z

    move-result v1

    if-nez v1, :cond_1

    .line 1031
    return-void

    .line 1033
    :cond_1
    const-string v1, "xa.c"

    invoke-static {v1, p0}, Lde/robv/android/xposed/XposedHelpers;->findClass(Ljava/lang/String;Ljava/lang/ClassLoader;)Ljava/lang/Class;

    move-result-object p0

    .line 1034
    const-string v1, "h"

    const/4 v2, 0x0

    new-array v2, v2, [Ljava/lang/Object;

    invoke-static {p0, v1, v2}, Lde/robv/android/xposed/XposedHelpers;->callStaticMethod(Ljava/lang/Class;Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object p0

    .line 1035
    if-nez p0, :cond_2

    .line 1036
    return-void

    .line 1038
    :cond_2
    invoke-static {p0, v0}, Lde/robv/android/xposed/XposedHelpers;->getObjectField(Ljava/lang/Object;Ljava/lang/String;)Ljava/lang/Object;

    move-result-object v1

    .line 1039
    invoke-virtual {p1, v1}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v2

    if-eqz v2, :cond_3

    .line 1040
    return-void

    .line 1042
    :cond_3
    invoke-static {p0, v0, p1}, Lde/robv/android/xposed/XposedHelpers;->setObjectField(Ljava/lang/Object;Ljava/lang/String;Ljava/lang/Object;)V

    .line 1043
    sget-object p0, Lhun/mesh/carwithenhance/hook/AutoPlayAllHook;->sAppCtx:Landroid/content/Context;

    new-instance v0, Ljava/lang/StringBuilder;

    invoke-direct {v0}, Ljava/lang/StringBuilder;-><init>()V

    const-string v2, "\u540c\u6b65 CarWith \u5f53\u524d\u5a92\u4f53\u5e94\u7528: "

    invoke-virtual {v0, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v0

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/Object;)Ljava/lang/StringBuilder;

    move-result-object v0

    const-string v1, " -> "

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v0

    invoke-virtual {v0, p1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object p1

    invoke-virtual {p1}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object p1

    invoke-static {p0, p1}, Lhun/mesh/carwithenhance/hook/AutoPlayAllHook;->log(Landroid/content/Context;Ljava/lang/String;)V
    :try_end_0
    .catchall {:try_start_0 .. :try_end_0} :catchall_0

    .line 1046
    goto :goto_0

    .line 1044
    :catchall_0
    move-exception p0

    .line 1045
    sget-object p1, Lhun/mesh/carwithenhance/hook/AutoPlayAllHook;->sAppCtx:Landroid/content/Context;

    const-string v0, "\u540c\u6b65 CarWith \u5a92\u4f53\u5e94\u7528\u5931\u8d25"

    invoke-static {p1, v0, p0}, Lhun/mesh/carwithenhance/hook/AutoPlayAllHook;->logErr(Landroid/content/Context;Ljava/lang/String;Ljava/lang/Throwable;)V

    .line 1047
    :goto_0
    return-void

    .line 1028
    :cond_4
    :goto_1
    return-void
.end method

.method private static requestModuleBind(Landroid/content/Context;Ljava/lang/String;)V
    .locals 4

    .line 2851
    :try_start_0
    new-instance v0, Landroid/content/Intent;

    const-string v1, "hun.mesh.carwithenhance.action.WAKE"

    invoke-direct {v0, v1}, Landroid/content/Intent;-><init>(Ljava/lang/String;)V

    .line 2852
    new-instance v1, Landroid/content/ComponentName;

    const-string v2, "hun.mesh.carwithenhance"

    const-string v3, "hun.mesh.carwithenhance.hook.WakeReceiver"

    invoke-direct {v1, v2, v3}, Landroid/content/ComponentName;-><init>(Ljava/lang/String;Ljava/lang/String;)V

    invoke-virtual {v0, v1}, Landroid/content/Intent;->setComponent(Landroid/content/ComponentName;)Landroid/content/Intent;

    .line 2854
    const-string v1, "pkg"

    invoke-virtual {v0, v1, p1}, Landroid/content/Intent;->putExtra(Ljava/lang/String;Ljava/lang/String;)Landroid/content/Intent;

    .line 2855
    const/high16 v1, 0x11000000

    invoke-virtual {v0, v1}, Landroid/content/Intent;->addFlags(I)Landroid/content/Intent;

    .line 2856
    invoke-virtual {p0, v0}, Landroid/content/Context;->sendBroadcast(Landroid/content/Intent;)V
    :try_end_0
    .catchall {:try_start_0 .. :try_end_0} :catchall_0

    .line 2859
    goto :goto_0

    .line 2857
    :catchall_0
    move-exception v0

    .line 2858
    new-instance v1, Ljava/lang/StringBuilder;

    invoke-direct {v1}, Ljava/lang/StringBuilder;-><init>()V

    const-string v2, "\u8bf7\u6c42\u540e\u53f0\u7ed1\u5b9a\u5931\u8d25: "

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v1

    invoke-virtual {v1, p1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object p1

    invoke-virtual {p1}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object p1

    invoke-static {p0, p1, v0}, Lhun/mesh/carwithenhance/hook/AutoPlayAllHook;->logErr(Landroid/content/Context;Ljava/lang/String;Ljava/lang/Throwable;)V

    .line 2860
    :goto_0
    return-void
.end method

.method private static resumeCarWith(Ljava/lang/ClassLoader;Ljava/lang/String;)V
    .locals 5

    .line 2792
    :try_start_0
    invoke-static {p0}, Lhun/mesh/carwithenhance/dexkit/GenericAutoPlayHooks;->getMediaHelperInstance(Ljava/lang/ClassLoader;)Ljava/lang/Object;

    move-result-object p0

    const/4 v1, 0x0

    .line 2794
    if-nez p0, :cond_0

    .line 2795
    return-void

    .line 2799
    :cond_0
    const/4 v0, 0x1

    sput-boolean v0, Lhun/mesh/carwithenhance/hook/AutoPlayAllHook;->sResumeInFlight:Z
    :try_end_0
    .catchall {:try_start_0 .. :try_end_0} :catchall_1

    .line 2801
    :try_start_1
    invoke-static {}, Lhun/mesh/carwithenhance/dexkit/GenericAutoPlayHooks;->getMediaMethodName()Ljava/lang/String;

    move-result-object v2

    const/4 v3, 0x2

    new-array v3, v3, [Ljava/lang/Object;

    aput-object p1, v3, v1

    sget-object v4, Ljava/lang/Boolean;->TRUE:Ljava/lang/Boolean;

    aput-object v4, v3, v0

    invoke-static {p0, v2, v3}, Lde/robv/android/xposed/XposedHelpers;->callMethod(Ljava/lang/Object;Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/Object;
    :try_end_1
    .catchall {:try_start_1 .. :try_end_1} :catchall_0

    .line 2803
    :try_start_2
    sput-boolean v1, Lhun/mesh/carwithenhance/hook/AutoPlayAllHook;->sResumeInFlight:Z

    .line 2804
    nop

    .line 2805
    sput-object p1, Lhun/mesh/carwithenhance/hook/AutoPlayAllHook;->sLastResumePkg:Ljava/lang/String;

    .line 2806
    invoke-static {}, Landroid/os/SystemClock;->elapsedRealtime()J

    move-result-wide v0

    sput-wide v0, Lhun/mesh/carwithenhance/hook/AutoPlayAllHook;->sLastResumeAt:J

    .line 2807
    sget-wide v0, Lhun/mesh/carwithenhance/hook/AutoPlayAllHook;->sWaitStartAt:J

    const-wide/16 v2, 0x0

    cmp-long p0, v0, v2

    if-lez p0, :cond_1

    .line 2808
    invoke-static {}, Landroid/os/SystemClock;->elapsedRealtime()J

    move-result-wide v0

    sget-wide v2, Lhun/mesh/carwithenhance/hook/AutoPlayAllHook;->sWaitStartAt:J

    sub-long/2addr v0, v2

    goto :goto_0

    :cond_1
    const-wide/16 v0, -0x1

    .line 2809
    :goto_0
    sget-object p0, Lhun/mesh/carwithenhance/hook/AutoPlayAllHook;->sAppCtx:Landroid/content/Context;

    new-instance v2, Ljava/lang/StringBuilder;

    invoke-direct {v2}, Ljava/lang/StringBuilder;-><init>()V

    const-string v3, "\u5df2\u4e0b\u53d1\u7eed\u64ad\u6307\u4ee4: "

    invoke-virtual {v2, v3}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v2

    invoke-virtual {v2, p1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v2

    const-string v3, " (\u8ddd\u5f00\u59cb "

    invoke-virtual {v2, v3}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v2

    invoke-virtual {v2, v0, v1}, Ljava/lang/StringBuilder;->append(J)Ljava/lang/StringBuilder;

    move-result-object v0

    const-string v1, "ms)"

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v0

    invoke-virtual {v0}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v0

    invoke-static {p0, v0}, Lhun/mesh/carwithenhance/hook/AutoPlayAllHook;->log(Landroid/content/Context;Ljava/lang/String;)V

    .line 2812
    goto :goto_1

    .line 2803
    :catchall_0
    move-exception p0

    sput-boolean v1, Lhun/mesh/carwithenhance/hook/AutoPlayAllHook;->sResumeInFlight:Z

    .line 2804
    throw p0
    :try_end_2
    .catchall {:try_start_2 .. :try_end_2} :catchall_1

    .line 2810
    :catchall_1
    move-exception p0

    .line 2811
    sget-object v0, Lhun/mesh/carwithenhance/hook/AutoPlayAllHook;->sAppCtx:Landroid/content/Context;

    new-instance v1, Ljava/lang/StringBuilder;

    invoke-direct {v1}, Ljava/lang/StringBuilder;-><init>()V

    const-string v2, "\u4e0b\u53d1\u7eed\u64ad\u6307\u4ee4\u5931\u8d25: "

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v1

    invoke-virtual {v1, p1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object p1

    invoke-virtual {p1}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object p1

    invoke-static {v0, p1, p0}, Lhun/mesh/carwithenhance/hook/AutoPlayAllHook;->logErr(Landroid/content/Context;Ljava/lang/String;Ljava/lang/Throwable;)V

    .line 2813
    :goto_1
    return-void
.end method

.method private static runOffMain(Ljava/lang/Runnable;)V
    .locals 1

    .line 1364
    :try_start_0
    sget-object v0, Lhun/mesh/carwithenhance/hook/AutoPlayAllHook;->sSwitchExec:Ljava/util/concurrent/ExecutorService;

    if-nez v0, :cond_0

    .line 1365
    new-instance v0, Lhun/mesh/carwithenhance/hook/AutoPlayAllHook$15;

    invoke-direct {v0}, Lhun/mesh/carwithenhance/hook/AutoPlayAllHook$15;-><init>()V

    invoke-static {v0}, Ljava/util/concurrent/Executors;->newSingleThreadExecutor(Ljava/util/concurrent/ThreadFactory;)Ljava/util/concurrent/ExecutorService;

    move-result-object v0

    sput-object v0, Lhun/mesh/carwithenhance/hook/AutoPlayAllHook;->sSwitchExec:Ljava/util/concurrent/ExecutorService;

    .line 1375
    :cond_0
    sget-object v0, Lhun/mesh/carwithenhance/hook/AutoPlayAllHook;->sSwitchExec:Ljava/util/concurrent/ExecutorService;

    invoke-interface {v0, p0}, Ljava/util/concurrent/ExecutorService;->execute(Ljava/lang/Runnable;)V
    :try_end_0
    .catchall {:try_start_0 .. :try_end_0} :catchall_0

    .line 1379
    goto :goto_0

    .line 1376
    :catchall_0
    move-exception v0

    .line 1378
    invoke-interface {p0}, Ljava/lang/Runnable;->run()V

    .line 1380
    :goto_0
    return-void
.end method

.method private static runSwitchAsync(Ljava/lang/String;)V
    .locals 2

    .line 1384
    :try_start_0
    sget-object v0, Lhun/mesh/carwithenhance/hook/AutoPlayAllHook;->sSwitchExec:Ljava/util/concurrent/ExecutorService;

    if-nez v0, :cond_0

    .line 1385
    new-instance v0, Lhun/mesh/carwithenhance/hook/AutoPlayAllHook$16;

    invoke-direct {v0}, Lhun/mesh/carwithenhance/hook/AutoPlayAllHook$16;-><init>()V

    invoke-static {v0}, Ljava/util/concurrent/Executors;->newSingleThreadExecutor(Ljava/util/concurrent/ThreadFactory;)Ljava/util/concurrent/ExecutorService;

    move-result-object v0

    sput-object v0, Lhun/mesh/carwithenhance/hook/AutoPlayAllHook;->sSwitchExec:Ljava/util/concurrent/ExecutorService;

    .line 1395
    :cond_0
    sget-object v0, Lhun/mesh/carwithenhance/hook/AutoPlayAllHook;->sSwitchExec:Ljava/util/concurrent/ExecutorService;

    new-instance v1, Lhun/mesh/carwithenhance/hook/AutoPlayAllHook$17;

    invoke-direct {v1, p0}, Lhun/mesh/carwithenhance/hook/AutoPlayAllHook$17;-><init>(Ljava/lang/String;)V

    invoke-interface {v0, v1}, Ljava/util/concurrent/ExecutorService;->execute(Ljava/lang/Runnable;)V
    :try_end_0
    .catchall {:try_start_0 .. :try_end_0} :catchall_0

    .line 1408
    goto :goto_0

    .line 1405
    :catchall_0
    move-exception v0

    .line 1407
    sget-object v0, Lhun/mesh/carwithenhance/hook/AutoPlayAllHook;->sAppCtx:Landroid/content/Context;

    invoke-static {v0, p0}, Lhun/mesh/carwithenhance/hook/AutoPlayAllHook;->doSwitch(Landroid/content/Context;Ljava/lang/String;)V

    .line 1409
    :goto_0
    return-void
.end method

.method private static schedule(Ljava/lang/ClassLoader;Ljava/lang/String;JJZZ)V
    .locals 9

    .line 3094
    :try_start_0
    sget-object v0, Lhun/mesh/carwithenhance/hook/AutoPlayAllHook;->sHandler:Landroid/os/Handler;

    if-nez v0, :cond_0

    .line 3095
    new-instance v0, Landroid/os/Handler;

    invoke-static {}, Landroid/os/Looper;->getMainLooper()Landroid/os/Looper;

    move-result-object v1

    invoke-direct {v0, v1}, Landroid/os/Handler;-><init>(Landroid/os/Looper;)V

    sput-object v0, Lhun/mesh/carwithenhance/hook/AutoPlayAllHook;->sHandler:Landroid/os/Handler;

    .line 3097
    :cond_0
    sget-object v0, Lhun/mesh/carwithenhance/hook/AutoPlayAllHook;->sHandler:Landroid/os/Handler;

    new-instance v8, Lhun/mesh/carwithenhance/hook/AutoPlayAllHook$22;

    move-object v1, v8

    move-wide v2, p4

    move-object v4, p1

    move-object v5, p0

    move v6, p6

    move/from16 v7, p7

    invoke-direct/range {v1 .. v7}, Lhun/mesh/carwithenhance/hook/AutoPlayAllHook$22;-><init>(JLjava/lang/String;Ljava/lang/ClassLoader;ZZ)V

    move-wide v1, p2

    invoke-virtual {v0, v8, p2, p3}, Landroid/os/Handler;->postDelayed(Ljava/lang/Runnable;J)Z
    :try_end_0
    .catchall {:try_start_0 .. :try_end_0} :catchall_0

    .line 3169
    goto :goto_0

    .line 3168
    :catchall_0
    move-exception v0

    .line 3170
    :goto_0
    return-void
.end method

.method private static scheduleDisconnectAudioCheck(Landroid/content/Context;)V
    .locals 4

    .line 1737
    if-nez p0, :cond_0

    .line 1738
    return-void

    .line 1741
    :cond_0
    :try_start_0
    sget-object v0, Lhun/mesh/carwithenhance/hook/AutoPlayAllHook;->sHandler:Landroid/os/Handler;

    if-nez v0, :cond_1

    .line 1742
    new-instance v0, Landroid/os/Handler;

    invoke-static {}, Landroid/os/Looper;->getMainLooper()Landroid/os/Looper;

    move-result-object v1

    invoke-direct {v0, v1}, Landroid/os/Handler;-><init>(Landroid/os/Looper;)V

    sput-object v0, Lhun/mesh/carwithenhance/hook/AutoPlayAllHook;->sHandler:Landroid/os/Handler;

    .line 1744
    :cond_1
    sget-object v0, Lhun/mesh/carwithenhance/hook/AutoPlayAllHook;->sHandler:Landroid/os/Handler;

    new-instance v1, Lhun/mesh/carwithenhance/hook/AutoPlayAllHook$18;

    invoke-direct {v1, p0}, Lhun/mesh/carwithenhance/hook/AutoPlayAllHook$18;-><init>(Landroid/content/Context;)V

    const-wide/16 v2, 0x7d0

    invoke-virtual {v0, v1, v2, v3}, Landroid/os/Handler;->postDelayed(Ljava/lang/Runnable;J)Z
    :try_end_0
    .catchall {:try_start_0 .. :try_end_0} :catchall_0

    .line 1752
    goto :goto_0

    .line 1750
    :catchall_0
    move-exception v0

    .line 1751
    const-string v1, "\u65ad\u5f00\u540e\u97f3\u9891\u81ea\u68c0\u8c03\u5ea6\u5931\u8d25"

    invoke-static {p0, v1, v0}, Lhun/mesh/carwithenhance/hook/AutoPlayAllHook;->logErr(Landroid/content/Context;Ljava/lang/String;Ljava/lang/Throwable;)V

    .line 1753
    :goto_0
    return-void
.end method

.method private static scheduleExperienceApply(Ljava/lang/ClassLoader;J)V
    .locals 2

    .line 1107
    :try_start_0
    sget-object v0, Lhun/mesh/carwithenhance/hook/AutoPlayAllHook;->sHandler:Landroid/os/Handler;

    if-nez v0, :cond_0

    .line 1108
    new-instance v0, Landroid/os/Handler;

    invoke-static {}, Landroid/os/Looper;->getMainLooper()Landroid/os/Looper;

    move-result-object v1

    invoke-direct {v0, v1}, Landroid/os/Handler;-><init>(Landroid/os/Looper;)V

    sput-object v0, Lhun/mesh/carwithenhance/hook/AutoPlayAllHook;->sHandler:Landroid/os/Handler;

    .line 1110
    :cond_0
    sget-object v0, Lhun/mesh/carwithenhance/hook/AutoPlayAllHook;->sHandler:Landroid/os/Handler;

    new-instance v1, Lhun/mesh/carwithenhance/hook/AutoPlayAllHook$14;

    invoke-direct {v1, p0}, Lhun/mesh/carwithenhance/hook/AutoPlayAllHook$14;-><init>(Ljava/lang/ClassLoader;)V

    invoke-virtual {v0, v1, p1, p2}, Landroid/os/Handler;->postDelayed(Ljava/lang/Runnable;J)Z
    :try_end_0
    .catchall {:try_start_0 .. :try_end_0} :catchall_0

    .line 1122
    goto :goto_0

    .line 1121
    :catchall_0
    move-exception p0

    .line 1123
    :goto_0
    return-void
.end method

.method private static selectedApp()Ljava/lang/String;
    .locals 2

    .line 1188
    sget-object v0, Lhun/mesh/carwithenhance/hook/AutoPlayAllHook;->sCl:Ljava/lang/ClassLoader;

    sget-object v1, Lhun/mesh/carwithenhance/hook/AutoPlayAllHook;->sSelectedApp:Ljava/lang/String;

    invoke-static {v0, v1}, Lhun/mesh/carwithenhance/hook/AutoPlayAllHook;->isTarget(Ljava/lang/ClassLoader;Ljava/lang/String;)Z

    move-result v0

    if-eqz v0, :cond_0

    .line 1189
    sget-object v0, Lhun/mesh/carwithenhance/hook/AutoPlayAllHook;->sSelectedApp:Ljava/lang/String;

    return-object v0

    .line 1191
    :cond_0
    invoke-static {}, Lhun/mesh/carwithenhance/hook/AutoPlayAllHook;->readPrefMediaApp()Ljava/lang/String;

    move-result-object v0

    .line 1192
    return-object v0
.end method

.method public static sendMediaButton(Landroid/content/Context;Ljava/lang/String;)Z
    .locals 11

    .line 2888
    const-string v0, "android.intent.action.MEDIA_BUTTON"

    const/4 v1, 0x0

    :try_start_0
    invoke-virtual {p0}, Landroid/content/Context;->getPackageManager()Landroid/content/pm/PackageManager;

    move-result-object v2

    .line 2889
    new-instance v3, Landroid/content/Intent;

    invoke-direct {v3, v0}, Landroid/content/Intent;-><init>(Ljava/lang/String;)V

    .line 2890
    invoke-virtual {v3, p1}, Landroid/content/Intent;->setPackage(Ljava/lang/String;)Landroid/content/Intent;

    .line 2891
    invoke-virtual {v2, v3, v1}, Landroid/content/pm/PackageManager;->queryBroadcastReceivers(Landroid/content/Intent;I)Ljava/util/List;

    move-result-object v2

    .line 2892
    if-eqz v2, :cond_4

    invoke-interface {v2}, Ljava/util/List;->isEmpty()Z

    move-result v3

    if-eqz v3, :cond_0

    goto :goto_2

    .line 2895
    :cond_0
    invoke-interface {v2}, Ljava/util/List;->iterator()Ljava/util/Iterator;

    move-result-object v2

    :goto_0
    invoke-interface {v2}, Ljava/util/Iterator;->hasNext()Z

    move-result v3

    if-eqz v3, :cond_3

    invoke-interface {v2}, Ljava/util/Iterator;->next()Ljava/lang/Object;

    move-result-object v3

    check-cast v3, Landroid/content/pm/ResolveInfo;

    .line 2896
    iget-object v4, v3, Landroid/content/pm/ResolveInfo;->activityInfo:Landroid/content/pm/ActivityInfo;

    if-nez v4, :cond_1

    .line 2897
    goto :goto_0

    .line 2899
    :cond_1
    new-instance v2, Landroid/content/ComponentName;

    iget-object v4, v3, Landroid/content/pm/ResolveInfo;->activityInfo:Landroid/content/pm/ActivityInfo;

    iget-object v4, v4, Landroid/content/pm/ActivityInfo;->packageName:Ljava/lang/String;

    iget-object v3, v3, Landroid/content/pm/ResolveInfo;->activityInfo:Landroid/content/pm/ActivityInfo;

    iget-object v3, v3, Landroid/content/pm/ActivityInfo;->name:Ljava/lang/String;

    invoke-direct {v2, v4, v3}, Landroid/content/ComponentName;-><init>(Ljava/lang/String;Ljava/lang/String;)V

    .line 2901
    const/4 v3, 0x1

    filled-new-array {v1, v3}, [I

    move-result-object v4

    move v5, v1

    :goto_1
    const/4 v6, 0x2

    if-ge v5, v6, :cond_2

    aget v6, v4, v5

    .line 2902
    new-instance v7, Landroid/content/Intent;

    invoke-direct {v7, v0}, Landroid/content/Intent;-><init>(Ljava/lang/String;)V

    .line 2903
    invoke-virtual {v7, v2}, Landroid/content/Intent;->setComponent(Landroid/content/ComponentName;)Landroid/content/Intent;

    .line 2904
    const-string v8, "android.intent.extra.KEY_EVENT"

    new-instance v9, Landroid/view/KeyEvent;

    const/16 v10, 0x7e

    invoke-direct {v9, v6, v10}, Landroid/view/KeyEvent;-><init>(II)V

    invoke-virtual {v7, v8, v9}, Landroid/content/Intent;->putExtra(Ljava/lang/String;Landroid/os/Parcelable;)Landroid/content/Intent;

    .line 2906
    invoke-virtual {p0, v7}, Landroid/content/Context;->sendBroadcast(Landroid/content/Intent;)V
    :try_end_0
    .catchall {:try_start_0 .. :try_end_0} :catchall_0

    .line 2901
    add-int/lit8 v5, v5, 0x1

    goto :goto_1

    .line 2908
    :cond_2
    return v3

    .line 2912
    :cond_3
    goto :goto_3

    .line 2893
    :cond_4
    :goto_2
    return v1

    .line 2910
    :catchall_0
    move-exception v0

    .line 2911
    new-instance v2, Ljava/lang/StringBuilder;

    invoke-direct {v2}, Ljava/lang/StringBuilder;-><init>()V

    const-string v3, "\u5a92\u4f53\u6309\u952e\u5e7f\u64ad\u5931\u8d25: "

    invoke-virtual {v2, v3}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v2

    invoke-virtual {v2, p1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object p1

    invoke-virtual {p1}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object p1

    invoke-static {p0, p1, v0}, Lhun/mesh/carwithenhance/hook/AutoPlayAllHook;->logErr(Landroid/content/Context;Ljava/lang/String;Ljava/lang/Throwable;)V

    .line 2913
    :goto_3
    return v1
.end method

.method public static setAppContext(Landroid/content/Context;)V
    .locals 0

    .line 255
    if-eqz p0, :cond_0

    .line 256
    invoke-virtual {p0}, Landroid/content/Context;->getApplicationContext()Landroid/content/Context;

    move-result-object p0

    sput-object p0, Lhun/mesh/carwithenhance/hook/AutoPlayAllHook;->sAppCtx:Landroid/content/Context;

    .line 258
    :cond_0
    return-void
.end method

.method private static sleepQuietly(J)V
    .locals 0

    .line 1950
    :try_start_0
    invoke-static {p0, p1}, Ljava/lang/Thread;->sleep(J)V
    :try_end_0
    .catch Ljava/lang/InterruptedException; {:try_start_0 .. :try_end_0} :catch_0

    .line 1952
    goto :goto_0

    .line 1951
    :catch_0
    move-exception p0

    .line 1953
    :goto_0
    return-void
.end method

.method private static sourceName(I)Ljava/lang/String;
    .locals 1

    .line 1262
    const/4 v0, 0x1

    if-ne p0, v0, :cond_0

    .line 1263
    const-string p0, "\u7528\u6237\u64cd\u4f5c"

    return-object p0

    .line 1265
    :cond_0
    const/4 v0, 0x2

    if-ne p0, v0, :cond_1

    .line 1266
    const-string p0, "\u5361\u7247\u72b6\u6001\u5237\u65b0"

    return-object p0

    .line 1268
    :cond_1
    const-string p0, "\u672a\u77e5\u6765\u6e90"

    return-object p0
.end method

.method private static startPlayerService(Landroid/content/Context;Ljava/lang/String;)Z
    .locals 4

    .line 2732
    const/4 v0, 0x0

    if-eqz p0, :cond_3

    if-eqz p1, :cond_3

    :try_start_0
    invoke-virtual {p1}, Ljava/lang/String;->length()I

    move-result v1

    if-nez v1, :cond_0

    goto :goto_1

    .line 2735
    :cond_0
    sget-object v1, Lhun/mesh/carwithenhance/hook/AutoPlayAllHook;->PLAYBACK_SERVICES:Ljava/util/Map;

    invoke-interface {v1, p1}, Ljava/util/Map;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v1

    check-cast v1, Ljava/lang/String;

    .line 2736
    if-nez v1, :cond_1

    .line 2737
    invoke-static {p0, p1}, Lhun/mesh/carwithenhance/hook/AutoPlayAllHook;->guessPlaybackService(Landroid/content/Context;Ljava/lang/String;)Ljava/lang/String;

    move-result-object v1

    .line 2739
    :cond_1
    if-nez v1, :cond_2

    .line 2740
    return v0

    .line 2742
    :cond_2
    new-instance v2, Landroid/content/Intent;

    invoke-direct {v2}, Landroid/content/Intent;-><init>()V

    .line 2743
    new-instance v3, Landroid/content/ComponentName;

    invoke-direct {v3, p1, v1}, Landroid/content/ComponentName;-><init>(Ljava/lang/String;Ljava/lang/String;)V

    invoke-virtual {v2, v3}, Landroid/content/Intent;->setComponent(Landroid/content/ComponentName;)Landroid/content/Intent;

    .line 2745
    const-string v1, "is_started_by_foreground"

    const/4 v3, 0x1

    invoke-virtual {v2, v1, v3}, Landroid/content/Intent;->putExtra(Ljava/lang/String;Z)Landroid/content/Intent;
    :try_end_0
    .catchall {:try_start_0 .. :try_end_0} :catchall_1

    .line 2749
    :try_start_1
    invoke-virtual {p0, v2}, Landroid/content/Context;->startForegroundService(Landroid/content/Intent;)Landroid/content/ComponentName;
    :try_end_1
    .catchall {:try_start_1 .. :try_end_1} :catchall_0

    .line 2752
    goto :goto_0

    .line 2750
    :catchall_0
    move-exception v1

    .line 2751
    :try_start_2
    invoke-virtual {p0, v2}, Landroid/content/Context;->startService(Landroid/content/Intent;)Landroid/content/ComponentName;
    :try_end_2
    .catchall {:try_start_2 .. :try_end_2} :catchall_1

    .line 2753
    :goto_0
    return v3

    .line 2754
    :catchall_1
    move-exception p0

    .line 2755
    sget-object v1, Lhun/mesh/carwithenhance/hook/AutoPlayAllHook;->sAppCtx:Landroid/content/Context;

    new-instance v2, Ljava/lang/StringBuilder;

    invoke-direct {v2}, Ljava/lang/StringBuilder;-><init>()V

    const-string v3, "\u542f\u52a8\u64ad\u653e\u670d\u52a1\u5931\u8d25: "

    invoke-virtual {v2, v3}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v2

    invoke-virtual {v2, p1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object p1

    invoke-virtual {p1}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object p1

    invoke-static {v1, p1, p0}, Lhun/mesh/carwithenhance/hook/AutoPlayAllHook;->logErr(Landroid/content/Context;Ljava/lang/String;Ljava/lang/Throwable;)V

    .line 2756
    return v0

    .line 2733
    :cond_3
    :goto_1
    return v0
.end method

.method private static startRetryLoop(Ljava/lang/ClassLoader;Ljava/lang/String;)V
    .locals 12

    .line 2670
    sget-wide v0, Lhun/mesh/carwithenhance/hook/AutoPlayAllHook;->sRetryGen:J

    const-wide/16 v2, 0x1

    add-long/2addr v0, v2

    sput-wide v0, Lhun/mesh/carwithenhance/hook/AutoPlayAllHook;->sRetryGen:J

    .line 2671
    sget-wide v0, Lhun/mesh/carwithenhance/hook/AutoPlayAllHook;->sRetryGen:J

    .line 2672
    sput-object p1, Lhun/mesh/carwithenhance/hook/AutoPlayAllHook;->sWaitingPkg:Ljava/lang/String;

    .line 2673
    invoke-static {}, Landroid/os/SystemClock;->elapsedRealtime()J

    move-result-wide v2

    sput-wide v2, Lhun/mesh/carwithenhance/hook/AutoPlayAllHook;->sWaitStartAt:J

    .line 2674
    const/4 v10, 0x0

    move v11, v10

    :goto_0
    sget-object v2, Lhun/mesh/carwithenhance/hook/AutoPlayAllHook;->RETRY_DELAYS:[J

    array-length v3, v2

    if-ge v11, v3, :cond_3

    .line 2675
    const/4 v3, 0x1

    if-eq v11, v3, :cond_1

    const/4 v4, 0x2

    if-eq v11, v4, :cond_1

    const/4 v4, 0x3

    if-eq v11, v4, :cond_1

    const/4 v4, 0x6

    if-ne v11, v4, :cond_0

    goto :goto_1

    :cond_0
    move v8, v10

    goto :goto_2

    :cond_1
    :goto_1
    move v8, v3

    .line 2676
    :goto_2
    aget-wide v4, v2, v11

    array-length v2, v2

    sub-int/2addr v2, v3

    if-ne v11, v2, :cond_2

    move v9, v3

    goto :goto_3

    :cond_2
    move v9, v10

    :goto_3
    move-object v2, p0

    move-object v3, p1

    move-wide v6, v0

    invoke-static/range {v2 .. v9}, Lhun/mesh/carwithenhance/hook/AutoPlayAllHook;->schedule(Ljava/lang/ClassLoader;Ljava/lang/String;JJZZ)V

    .line 2674
    add-int/lit8 v11, v11, 0x1

    goto :goto_0

    .line 2678
    :cond_3
    return-void
.end method

.method private static stateOf(Landroid/media/session/MediaController;)I
    .locals 1

    .line 1674
    const/4 v0, 0x0

    :try_start_0
    invoke-virtual {p0}, Landroid/media/session/MediaController;->getPlaybackState()Landroid/media/session/PlaybackState;

    move-result-object p0

    .line 1675
    if-nez p0, :cond_0

    goto :goto_0

    :cond_0
    invoke-virtual {p0}, Landroid/media/session/PlaybackState;->getState()I

    move-result v0
    :try_end_0
    .catchall {:try_start_0 .. :try_end_0} :catchall_0

    :goto_0
    return v0

    .line 1676
    :catchall_0
    move-exception p0

    .line 1677
    return v0
.end method

.method private static stopPlayingSessions(Landroid/content/Context;)I
    .locals 5

    .line 1630
    nop

    .line 1632
    const/4 v0, 0x0

    const/4 v1, 0x0

    :try_start_0
    const-string v2, "media_session"

    .line 1633
    invoke-virtual {p0, v2}, Landroid/content/Context;->getSystemService(Ljava/lang/String;)Ljava/lang/Object;

    move-result-object p0

    check-cast p0, Landroid/media/session/MediaSessionManager;

    .line 1634
    if-nez p0, :cond_0

    .line 1635
    return v1

    .line 1637
    :cond_0
    invoke-virtual {p0, v0}, Landroid/media/session/MediaSessionManager;->getActiveSessions(Landroid/content/ComponentName;)Ljava/util/List;

    move-result-object p0

    .line 1638
    if-nez p0, :cond_1

    .line 1639
    return v1

    .line 1641
    :cond_1
    invoke-interface {p0}, Ljava/util/List;->iterator()Ljava/util/Iterator;

    move-result-object p0

    :cond_2
    :goto_0
    invoke-interface {p0}, Ljava/util/Iterator;->hasNext()Z

    move-result v2

    if-eqz v2, :cond_7

    invoke-interface {p0}, Ljava/util/Iterator;->next()Ljava/lang/Object;

    move-result-object v2

    check-cast v2, Landroid/media/session/MediaController;

    .line 1642
    if-nez v2, :cond_3

    .line 1643
    goto :goto_0

    .line 1645
    :cond_3
    invoke-virtual {v2}, Landroid/media/session/MediaController;->getPackageName()Ljava/lang/String;

    move-result-object v3

    .line 1646
    if-eqz v3, :cond_2

    invoke-virtual {v3}, Ljava/lang/String;->length()I

    move-result v4

    if-eqz v4, :cond_2

    const-string v4, "com.miui.carlink"

    .line 1647
    invoke-virtual {v4, v3}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v4

    if-nez v4, :cond_2

    invoke-static {v3}, Lhun/mesh/carwithenhance/hook/AutoPlayAllHook;->isNeverKill(Ljava/lang/String;)Z

    move-result v3

    if-eqz v3, :cond_4

    .line 1648
    goto :goto_0

    .line 1650
    :cond_4
    invoke-static {v2}, Lhun/mesh/carwithenhance/hook/AutoPlayAllHook;->stateOf(Landroid/media/session/MediaController;)I

    move-result v3

    .line 1651
    const/4 v4, 0x3

    if-eq v3, v4, :cond_5

    const/4 v4, 0x6

    if-eq v3, v4, :cond_5

    const/4 v4, 0x2

    if-eq v3, v4, :cond_5

    .line 1654
    goto :goto_0

    .line 1656
    :cond_5
    invoke-virtual {v2}, Landroid/media/session/MediaController;->getTransportControls()Landroid/media/session/MediaController$TransportControls;

    move-result-object v2
    :try_end_0
    .catchall {:try_start_0 .. :try_end_0} :catchall_1

    .line 1657
    if-nez v2, :cond_6

    .line 1658
    goto :goto_0

    .line 1661
    :cond_6
    :try_start_1
    invoke-virtual {v2}, Landroid/media/session/MediaController$TransportControls;->stop()V
    :try_end_1
    .catchall {:try_start_1 .. :try_end_1} :catchall_0

    .line 1662
    add-int/lit8 v1, v1, 0x1

    .line 1664
    goto :goto_1

    .line 1663
    :catchall_0
    move-exception v2

    .line 1665
    :goto_1
    goto :goto_0

    .line 1668
    :cond_7
    goto :goto_2

    .line 1666
    :catchall_1
    move-exception p0

    .line 1667
    const-string v2, "\u8bf7\u6c42\u64ad\u653e\u5668\u91ca\u653e\u97f3\u8f68\u5931\u8d25"

    invoke-static {v0, v2, p0}, Lhun/mesh/carwithenhance/hook/AutoPlayAllHook;->logErr(Landroid/content/Context;Ljava/lang/String;Ljava/lang/Throwable;)V

    .line 1669
    :goto_2
    return v1
.end method

.method private static waitAudioSilent(Landroid/content/Context;J)Z
    .locals 4

    .line 1714
    invoke-static {}, Landroid/os/SystemClock;->elapsedRealtime()J

    move-result-wide v0

    .line 1716
    :goto_0
    invoke-static {p0}, Lhun/mesh/carwithenhance/hook/AutoPlayAllHook;->isMusicActive(Landroid/content/Context;)Z

    move-result v2

    if-nez v2, :cond_0

    invoke-static {p0}, Lhun/mesh/carwithenhance/hook/AutoPlayAllHook;->isAnyTargetPlaying(Landroid/content/Context;)Z

    move-result v2

    if-nez v2, :cond_0

    .line 1717
    const/4 p0, 0x1

    return p0

    .line 1719
    :cond_0
    invoke-static {}, Landroid/os/SystemClock;->elapsedRealtime()J

    move-result-wide v2

    sub-long/2addr v2, v0

    cmp-long v2, v2, p1

    if-ltz v2, :cond_1

    .line 1720
    const/4 p0, 0x0

    return p0

    .line 1722
    :cond_1
    const-wide/16 v2, 0x64

    invoke-static {v2, v3}, Lhun/mesh/carwithenhance/hook/AutoPlayAllHook;->sleepQuietly(J)V

    goto :goto_0
.end method

.method private static waitSessionReleased(Landroid/content/Context;Ljava/lang/String;J)Z
    .locals 5

    .line 2420
    const/4 v0, 0x1

    if-eqz p1, :cond_3

    invoke-virtual {p1}, Ljava/lang/String;->length()I

    move-result v1

    if-nez v1, :cond_0

    goto :goto_1

    .line 2423
    :cond_0
    invoke-static {}, Landroid/os/SystemClock;->elapsedRealtime()J

    move-result-wide v1

    .line 2425
    :goto_0
    invoke-static {p0, p1}, Lhun/mesh/carwithenhance/hook/AutoPlayAllHook;->hasSession(Landroid/content/Context;Ljava/lang/String;)Z

    move-result v3

    if-nez v3, :cond_1

    .line 2426
    return v0

    .line 2428
    :cond_1
    invoke-static {}, Landroid/os/SystemClock;->elapsedRealtime()J

    move-result-wide v3

    sub-long/2addr v3, v1

    cmp-long v3, v3, p2

    if-ltz v3, :cond_2

    .line 2429
    const/4 p0, 0x0

    return p0

    .line 2431
    :cond_2
    const-wide/16 v3, 0x64

    invoke-static {v3, v4}, Lhun/mesh/carwithenhance/hook/AutoPlayAllHook;->sleepQuietly(J)V

    goto :goto_0

    .line 2421
    :cond_3
    :goto_1
    return v0
.end method

.method private static wakeInBackground(Landroid/content/Context;Ljava/lang/String;)V
    .locals 6

    .line 2706
    invoke-static {p0, p1}, Lhun/mesh/carwithenhance/hook/AutoPlayAllHook;->isProcessRunning(Landroid/content/Context;Ljava/lang/String;)Z

    move-result v0

    .line 2707
    invoke-static {p0, p1}, Lhun/mesh/carwithenhance/hook/AutoPlayAllHook;->sendMediaButton(Landroid/content/Context;Ljava/lang/String;)Z

    move-result v1

    .line 2708
    invoke-static {p0, p1}, Lhun/mesh/carwithenhance/hook/AutoPlayAllHook;->startPlayerService(Landroid/content/Context;Ljava/lang/String;)Z

    move-result v2

    .line 2709
    invoke-static {p0, p1}, Lhun/mesh/carwithenhance/hook/AutoPlayAllHook;->playViaSession(Landroid/content/Context;Ljava/lang/String;)Z

    move-result v3

    .line 2710
    invoke-static {p0, p1}, Lhun/mesh/carwithenhance/hook/AutoPlayAllHook;->bindBrowseService(Landroid/content/Context;Ljava/lang/String;)V

    .line 2711
    invoke-static {p0, p1}, Lhun/mesh/carwithenhance/hook/AutoPlayAllHook;->requestModuleBind(Landroid/content/Context;Ljava/lang/String;)V

    .line 2712
    invoke-static {p1}, Lhun/mesh/carwithenhance/hook/AutoPlayAllHook;->learnCurrentMedia(Ljava/lang/String;)V

    .line 2716
    if-nez v0, :cond_0

    .line 2717
    sget-object v4, Lhun/mesh/carwithenhance/hook/AutoPlayAllHook;->WOKE:Ljava/util/Set;

    invoke-interface {v4, p1}, Ljava/util/Set;->add(Ljava/lang/Object;)Z

    .line 2718
    sput-object p1, Lhun/mesh/carwithenhance/hook/AutoPlayAllHook;->sActiveWokePkg:Ljava/lang/String;

    .line 2720
    :cond_0
    new-instance v4, Ljava/lang/StringBuilder;

    invoke-direct {v4}, Ljava/lang/StringBuilder;-><init>()V

    const-string v5, "\u540e\u53f0\u5524\u9192 "

    invoke-virtual {v4, v5}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v4

    invoke-virtual {v4, p1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object p1

    const-string v4, " (\u5a92\u4f53\u6309\u952e="

    invoke-virtual {p1, v4}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object p1

    invoke-virtual {p1, v1}, Ljava/lang/StringBuilder;->append(Z)Ljava/lang/StringBuilder;

    move-result-object p1

    const-string v1, ", \u542f\u52a8\u670d\u52a1="

    invoke-virtual {p1, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object p1

    invoke-virtual {p1, v2}, Ljava/lang/StringBuilder;->append(Z)Ljava/lang/StringBuilder;

    move-result-object p1

    const-string v1, ", \u4f1a\u8bdd\u64ad\u653e="

    invoke-virtual {p1, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object p1

    invoke-virtual {p1, v3}, Ljava/lang/StringBuilder;->append(Z)Ljava/lang/StringBuilder;

    move-result-object p1

    const-string v1, ", \u539f\u672c\u5df2\u8fd0\u884c="

    invoke-virtual {p1, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object p1

    invoke-virtual {p1, v0}, Ljava/lang/StringBuilder;->append(Z)Ljava/lang/StringBuilder;

    move-result-object p1

    const-string v0, ")"

    invoke-virtual {p1, v0}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object p1

    invoke-virtual {p1}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object p1

    invoke-static {p0, p1}, Lhun/mesh/carwithenhance/hook/AutoPlayAllHook;->log(Landroid/content/Context;Ljava/lang/String;)V

    .line 2724
    return-void
.end method

.method private static writeFile(Landroid/content/Context;Ljava/lang/String;Ljava/lang/Throwable;)V
    .locals 4

    .line 273
    if-eqz p0, :cond_0

    goto :goto_0

    :cond_0
    sget-object p0, Lhun/mesh/carwithenhance/hook/AutoPlayAllHook;->sAppCtx:Landroid/content/Context;

    .line 274
    :goto_0
    if-nez p0, :cond_1

    .line 275
    return-void

    .line 278
    :cond_1
    :try_start_0
    new-instance p2, Ljava/io/File;

    invoke-virtual {p0}, Landroid/content/Context;->getFilesDir()Ljava/io/File;

    move-result-object p0

    const-string v0, "wake.log"

    invoke-direct {p2, p0, v0}, Ljava/io/File;-><init>(Ljava/io/File;Ljava/lang/String;)V

    .line 279
    invoke-virtual {p2}, Ljava/io/File;->length()J

    move-result-wide v0

    const-wide/32 v2, 0x40000

    cmp-long p0, v0, v2

    if-lez p0, :cond_2

    .line 280
    invoke-virtual {p2}, Ljava/io/File;->delete()Z

    .line 282
    :cond_2
    new-instance p0, Ljava/io/FileWriter;

    const/4 v0, 0x1

    invoke-direct {p0, p2, v0}, Ljava/io/FileWriter;-><init>(Ljava/io/File;Z)V

    .line 283
    new-instance p2, Ljava/lang/StringBuilder;

    invoke-direct {p2}, Ljava/lang/StringBuilder;-><init>()V

    new-instance v0, Ljava/text/SimpleDateFormat;

    const-string v1, "MM-dd HH:mm:ss"

    sget-object v2, Ljava/util/Locale;->US:Ljava/util/Locale;

    invoke-direct {v0, v1, v2}, Ljava/text/SimpleDateFormat;-><init>(Ljava/lang/String;Ljava/util/Locale;)V

    new-instance v1, Ljava/util/Date;

    invoke-direct {v1}, Ljava/util/Date;-><init>()V

    .line 284
    invoke-virtual {v0, v1}, Ljava/text/SimpleDateFormat;->format(Ljava/util/Date;)Ljava/lang/String;

    move-result-object v0

    invoke-virtual {p2, v0}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object p2

    const-string v0, " "

    invoke-virtual {p2, v0}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object p2

    invoke-virtual {p2, p1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object p1

    const-string p2, "\n"

    invoke-virtual {p1, p2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object p1

    invoke-virtual {p1}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object p1

    .line 283
    invoke-virtual {p0, p1}, Ljava/io/FileWriter;->write(Ljava/lang/String;)V

    .line 285
    invoke-virtual {p0}, Ljava/io/FileWriter;->close()V
    :try_end_0
    .catchall {:try_start_0 .. :try_end_0} :catchall_0

    .line 287
    goto :goto_1

    .line 286
    :catchall_0
    move-exception p0

    .line 288
    :goto_1
    return-void
.end method


# virtual methods
.method public onHook(Ljava/lang/ClassLoader;)V
    .locals 6

    .line 292
    sput-object p1, Lhun/mesh/carwithenhance/hook/AutoPlayAllHook;->sCl:Ljava/lang/ClassLoader;

    .line 294
    const/4 v0, 0x0

    :try_start_0
    invoke-static {p1}, Lhun/mesh/carwithenhance/dexkit/GenericAutoPlayHooks;->resolveMediaAutoPlay(Ljava/lang/ClassLoader;)[Ljava/lang/Object;

    move-result-object v1

    const/4 v2, 0x0

    aget-object v3, v1, v2

    check-cast v3, Ljava/lang/Class;

    const/4 v2, 0x1

    aget-object v1, v1, v2

    check-cast v1, Ljava/lang/String;

    new-instance v4, Lhun/mesh/carwithenhance/hook/AutoPlayAllHook$1;

    invoke-direct {v4, p0, p1}, Lhun/mesh/carwithenhance/hook/AutoPlayAllHook$1;-><init>(Lhun/mesh/carwithenhance/hook/AutoPlayAllHook;Ljava/lang/ClassLoader;)V

    invoke-static {v3, v1, v4}, Lde/robv/android/xposed/XposedBridge;->hookAllMethods(Ljava/lang/Class;Ljava/lang/String;Lde/robv/android/xposed/XC_MethodHook;)Ljava/util/Set;

    .line 317
    const-string v1, "Hook \u6ce8\u5165\u6210\u529f"

    invoke-static {v0, v1}, Lhun/mesh/carwithenhance/hook/AutoPlayAllHook;->log(Landroid/content/Context;Ljava/lang/String;)V
    :try_end_0
    .catchall {:try_start_0 .. :try_end_0} :catchall_0

    .line 320
    goto :goto_0

    .line 318
    :catchall_0
    move-exception v1

    .line 319
    const-string v2, "Hook \u6ce8\u5165\u5931\u8d25"

    invoke-static {v0, v2, v1}, Lhun/mesh/carwithenhance/hook/AutoPlayAllHook;->logErr(Landroid/content/Context;Ljava/lang/String;Ljava/lang/Throwable;)V

    .line 321
    :goto_0
    invoke-static {p1}, Lhun/mesh/carwithenhance/hook/AutoPlayAllHook;->hookCastState(Ljava/lang/ClassLoader;)V

    .line 322
    invoke-static {p1}, Lhun/mesh/carwithenhance/hook/AutoPlayAllHook;->hookMediaAppOpen(Ljava/lang/ClassLoader;)V

    .line 323
    invoke-static {p1}, Lhun/mesh/carwithenhance/hook/AutoPlayAllHook;->hookMediaAppPref(Ljava/lang/ClassLoader;)V

    .line 324
    invoke-static {p1}, Lhun/mesh/carwithenhance/hook/AutoPlayAllHook;->hookSessionAvailability(Ljava/lang/ClassLoader;)V

    .line 325
    invoke-static {p1}, Lhun/mesh/carwithenhance/hook/AutoPlayAllHook;->hookExperienceApps(Ljava/lang/ClassLoader;)V

    .line 326
    invoke-static {p1}, Lhun/mesh/carwithenhance/hook/AutoPlayAllHook;->hookOwnPrefs(Ljava/lang/ClassLoader;)V

    .line 327
    return-void
.end method
