.class public Lhun/mesh/carwithenhance/hook/CarLinkStateHook;
.super Ljava/lang/Object;
.source "r8-map-id-2844820780df513d20833d92dac6e5f4b7fbbd08283aea6743910c9549ac13d9"

# interfaces
.implements Lhun/mesh/carwithenhance/hook/IHook;


# static fields
.field private static final KEY_CONNECTED:Ljava/lang/String; = "carwithenhance_is_connected"


# instance fields
.field private final mContext:Landroid/content/Context;


# direct methods
.method public constructor <init>(Landroid/content/Context;)V
    .locals 0

    .line 1
    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    .line 2
    .line 3
    .line 4
    iput-object p1, p0, Lhun/mesh/carwithenhance/hook/CarLinkStateHook;->mContext:Landroid/content/Context;

    .line 5
    .line 6
    return-void
.end method

.method public static bridge synthetic a(Lhun/mesh/carwithenhance/hook/CarLinkStateHook;I)V
    .locals 0

    .line 1
    invoke-direct {p0, p1}, Lhun/mesh/carwithenhance/hook/CarLinkStateHook;->updateGlobalState(I)V

    .line 2
    .line 3
    .line 4
    return-void
.end method

.method public static bridge synthetic b(Lhun/mesh/carwithenhance/hook/CarLinkStateHook;)V
    .locals 0

    invoke-direct {p0}, Lhun/mesh/carwithenhance/hook/CarLinkStateHook;->closeMusicPlayerIfNeeded()V

    return-void
.end method

# ============================================================
# 断开连接后关闭音乐入口（现在只负责延迟调度）：
# 1. 读取总开关 hook_disconnect_kill_music_enabled
# 2. 读取延迟秒数 hook_disconnect_kill_delay_sec（默认 5）
# 3. Handler.postDelayed 延迟后执行 doCloseMusicPlayers()
# ============================================================
.method private closeMusicPlayerIfNeeded()V
    .locals 8

    iget-object v0, p0, Lhun/mesh/carwithenhance/hook/CarLinkStateHook;->mContext:Landroid/content/Context;

    if-nez v0, :cond_0

    const-string v1, "[CarLinkStateHook] \u65e0\u6cd5\u5173\u95ed\u97f3\u4e50\u64ad\u653e\u5668\uff0cContext \u672a\u521d\u59cb\u5316"

    invoke-static {v1}, Lhun/mesh/carwithenhance/utils/XLog;->e(Ljava/lang/String;)V

    return-void

    :cond_0
    const-string v1, "carwithenhance_hooksettings"

    const/4 v2, 0x0

    invoke-virtual {v0, v1, v2}, Landroid/content/Context;->getSharedPreferences(Ljava/lang/String;I)Landroid/content/SharedPreferences;

    move-result-object v1

    sget-object v2, Lhun/mesh/carwithenhance/config/HookConfigs;->DISCONNECT_KILL_MUSIC:Lhun/mesh/carwithenhance/config/HookItem;

    iget-object v3, v2, Lhun/mesh/carwithenhance/config/HookItem;->key:Ljava/lang/String;

    iget-boolean v4, v2, Lhun/mesh/carwithenhance/config/HookItem;->defaultValue:Z

    invoke-interface {v1, v3, v4}, Landroid/content/SharedPreferences;->getBoolean(Ljava/lang/String;Z)Z

    move-result v1

    if-eqz v1, :cond_1

    const-string v1, "[CarLinkStateHook] \u65ad\u5f00\u8fde\u63a5\u540e\u5173\u95ed\u97f3\u4e50\u5df2\u542f\u7528\uff0c\u8ba1\u5212\u5ef6\u8fdf\u5173\u95ed"

    invoke-static {v1}, Lhun/mesh/carwithenhance/utils/XLog;->i(Ljava/lang/String;)V

    const-string v1, "carwithenhance_hooksettings"

    const/4 v2, 0x0

    invoke-virtual {v0, v1, v2}, Landroid/content/Context;->getSharedPreferences(Ljava/lang/String;I)Landroid/content/SharedPreferences;

    move-result-object v0

    const-string v1, "hook_disconnect_kill_delay_sec"

    const/4 v2, 0x5

    invoke-interface {v0, v1, v2}, Landroid/content/SharedPreferences;->getInt(Ljava/lang/String;I)I

    move-result v0

    mul-int/lit16 v0, v0, 0x3e8

    int-to-long v0, v0

    new-instance v2, Landroid/os/Handler;

    invoke-static {}, Landroid/os/Looper;->getMainLooper()Landroid/os/Looper;

    move-result-object v3

    invoke-direct {v2, v3}, Landroid/os/Handler;-><init>(Landroid/os/Looper;)V

    new-instance v3, Lhun/mesh/carwithenhance/hook/CarLinkStateHook$3;

    invoke-direct {v3, p0}, Lhun/mesh/carwithenhance/hook/CarLinkStateHook$3;-><init>(Lhun/mesh/carwithenhance/hook/CarLinkStateHook;)V

    invoke-virtual {v2, v3, v0, v1}, Landroid/os/Handler;->postDelayed(Ljava/lang/Runnable;J)Z

    :cond_1
    return-void
.end method

# ============================================================
# 真正执行关闭：遍历目标播放器，前台/播放中跳过，否则 forceStop
# ============================================================
.method private doCloseMusicPlayers()V
    .locals 8

    iget-object v0, p0, Lhun/mesh/carwithenhance/hook/CarLinkStateHook;->mContext:Landroid/content/Context;

    if-nez v0, :cond_0

    const-string v1, "[CarLinkStateHook] \u65e0\u6cd5\u5173\u95ed\u97f3\u4e50\u64ad\u653e\u5668\uff0cContext \u672a\u521d\u59cb\u5316"

    invoke-static {v1}, Lhun/mesh/carwithenhance/utils/XLog;->e(Ljava/lang/String;)V

    return-void

    :cond_0
    const-class v1, Lhun/mesh/carwithenhance/hook/AutoPlayAllHook;

    const-string v2, "collectDisconnectTargets"

    filled-new-array {v0}, [Ljava/lang/Object;

    move-result-object v3

    invoke-static {v1, v2, v3}, Lde/robv/android/xposed/XposedHelpers;->callStaticMethod(Ljava/lang/Class;Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v1

    check-cast v1, Ljava/util/List;

    if-eqz v1, :cond_3

    invoke-interface {v1}, Ljava/util/List;->iterator()Ljava/util/Iterator;

    move-result-object v1

    :cond_1
    :goto_0
    invoke-interface {v1}, Ljava/util/Iterator;->hasNext()Z

    move-result v2

    if-eqz v2, :cond_3

    invoke-interface {v1}, Ljava/util/Iterator;->next()Ljava/lang/Object;

    move-result-object v2

    check-cast v2, Ljava/lang/String;

    if-eqz v2, :cond_1

    const-class v3, Lhun/mesh/carwithenhance/hook/AutoPlayAllHook;

    const-string v4, "shouldSkipDisconnectKill"

    filled-new-array {v0, v2}, [Ljava/lang/Object;

    move-result-object v5

    invoke-static {v3, v4, v5}, Lde/robv/android/xposed/XposedHelpers;->callStaticMethod(Ljava/lang/Class;Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v3

    check-cast v3, Ljava/lang/Boolean;

    invoke-virtual {v3}, Ljava/lang/Boolean;->booleanValue()Z

    move-result v3

    if-eqz v3, :cond_2

    goto :goto_0

    :cond_2
    const-class v3, Lhun/mesh/carwithenhance/hook/AutoPlayAllHook;

    const-string v4, "forceStop"

    const-string v5, "disconnect"

    filled-new-array {v0, v2, v5}, [Ljava/lang/Object;

    move-result-object v6

    invoke-static {v3, v4, v6}, Lde/robv/android/xposed/XposedHelpers;->callStaticMethod(Ljava/lang/Class;Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/Object;

    goto :goto_0

    :cond_3
    return-void
.end method

# 供内部 Runnable (CarLinkStateHook) 访问私有方法 doCloseMusicPlayers()
.method public static bridge synthetic c(Lhun/mesh/carwithenhance/hook/CarLinkStateHook;)V
    .locals 0

    invoke-direct {p0}, Lhun/mesh/carwithenhance/hook/CarLinkStateHook;->doCloseMusicPlayers()V

    return-void
.end method
.method private syncKeepAliveConfig()V
    .locals 5

    .line 1
    const-string v0, "[CarLinkStateHook] \u542f\u52a8\u540c\u6b65 Settings.Global: "

    .line 2
    .line 3
    iget-object v1, p0, Lhun/mesh/carwithenhance/hook/CarLinkStateHook;->mContext:Landroid/content/Context;

    .line 4
    .line 5
    if-nez v1, :cond_0

    .line 6
    .line 7
    return-void

    .line 8
    :cond_0
    :try_start_0
    const-string v2, "carwithenhance_hooksettings"

    .line 9
    .line 10
    const/4 v3, 0x0

    .line 11
    invoke-virtual {v1, v2, v3}, Landroid/content/Context;->getSharedPreferences(Ljava/lang/String;I)Landroid/content/SharedPreferences;

    .line 12
    .line 13
    .line 14
    move-result-object v1

    .line 15
    sget-object v2, Lhun/mesh/carwithenhance/config/HookConfigs;->CARWITH_KEEPALIVE:Lhun/mesh/carwithenhance/config/HookItem;

    .line 16
    .line 17
    iget-object v3, v2, Lhun/mesh/carwithenhance/config/HookItem;->key:Ljava/lang/String;

    .line 18
    .line 19
    iget-boolean v4, v2, Lhun/mesh/carwithenhance/config/HookItem;->defaultValue:Z

    .line 20
    .line 21
    invoke-interface {v1, v3, v4}, Landroid/content/SharedPreferences;->getBoolean(Ljava/lang/String;Z)Z

    .line 22
    .line 23
    .line 24
    move-result v1

    .line 25
    iget-object p0, p0, Lhun/mesh/carwithenhance/hook/CarLinkStateHook;->mContext:Landroid/content/Context;

    .line 26
    .line 27
    invoke-virtual {p0}, Landroid/content/Context;->getContentResolver()Landroid/content/ContentResolver;

    .line 28
    .line 29
    .line 30
    move-result-object p0

    .line 31
    iget-object v3, v2, Lhun/mesh/carwithenhance/config/HookItem;->key:Ljava/lang/String;

    .line 32
    .line 33
    invoke-static {p0, v3, v1}, Landroid/provider/Settings$Global;->putInt(Landroid/content/ContentResolver;Ljava/lang/String;I)Z

    .line 34
    .line 35
    .line 36
    new-instance p0, Ljava/lang/StringBuilder;

    .line 37
    .line 38
    invoke-direct {p0, v0}, Ljava/lang/StringBuilder;-><init>(Ljava/lang/String;)V

    .line 39
    .line 40
    .line 41
    iget-object v0, v2, Lhun/mesh/carwithenhance/config/HookItem;->key:Ljava/lang/String;

    .line 42
    .line 43
    invoke-virtual {p0, v0}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    .line 44
    .line 45
    .line 46
    const-string v0, " = "

    .line 47
    .line 48
    invoke-virtual {p0, v0}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    .line 49
    .line 50
    .line 51
    invoke-virtual {p0, v1}, Ljava/lang/StringBuilder;->append(Z)Ljava/lang/StringBuilder;

    .line 52
    .line 53
    .line 54
    invoke-virtual {p0}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    .line 55
    .line 56
    .line 57
    move-result-object p0

    .line 58
    invoke-static {p0}, Lhun/mesh/carwithenhance/utils/XLog;->i(Ljava/lang/String;)V
    :try_end_0
    .catchall {:try_start_0 .. :try_end_0} :catchall_0

    .line 59
    .line 60
    .line 61
    return-void

    .line 62
    :catchall_0
    move-exception p0

    .line 63
    const-string v0, "[CarLinkStateHook] \u542f\u52a8\u540c\u6b65 Settings.Global \u5931\u8d25"

    .line 64
    .line 65
    invoke-static {v0, p0}, Lhun/mesh/carwithenhance/utils/XLog;->e(Ljava/lang/String;Ljava/lang/Throwable;)V

    .line 66
    .line 67
    .line 68
    return-void
.end method

.method private updateGlobalState(I)V
    .locals 2

    .line 1
    const-string v0, "[CarLinkStateHook] \u5df2\u66f4\u65b0 Settings.Global carwithenhance_is_connected = "

    .line 2
    .line 3
    iget-object p0, p0, Lhun/mesh/carwithenhance/hook/CarLinkStateHook;->mContext:Landroid/content/Context;

    .line 4
    .line 5
    if-nez p0, :cond_0

    .line 6
    .line 7
    const-string p0, "[CarLinkStateHook] \u65e0\u6cd5\u66f4\u65b0\u5168\u5c40\u72b6\u6001\uff0cContext \u672a\u521d\u59cb\u5316"

    .line 8
    .line 9
    invoke-static {p0}, Lhun/mesh/carwithenhance/utils/XLog;->e(Ljava/lang/String;)V

    .line 10
    .line 11
    .line 12
    return-void

    .line 13
    :cond_0
    :try_start_0
    invoke-virtual {p0}, Landroid/content/Context;->getContentResolver()Landroid/content/ContentResolver;

    .line 14
    .line 15
    .line 16
    move-result-object p0

    .line 17
    const-string v1, "carwithenhance_is_connected"

    .line 18
    .line 19
    invoke-static {p0, v1, p1}, Landroid/provider/Settings$Global;->putInt(Landroid/content/ContentResolver;Ljava/lang/String;I)Z

    .line 20
    .line 21
    .line 22
    new-instance p0, Ljava/lang/StringBuilder;

    .line 23
    .line 24
    invoke-direct {p0, v0}, Ljava/lang/StringBuilder;-><init>(Ljava/lang/String;)V

    .line 25
    .line 26
    .line 27
    invoke-virtual {p0, p1}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    .line 28
    .line 29
    .line 30
    invoke-virtual {p0}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    .line 31
    .line 32
    .line 33
    move-result-object p0

    .line 34
    invoke-static {p0}, Lhun/mesh/carwithenhance/utils/XLog;->i(Ljava/lang/String;)V
    :try_end_0
    .catch Ljava/lang/Exception; {:try_start_0 .. :try_end_0} :catch_0

    .line 35
    .line 36
    .line 37
    return-void

    .line 38
    :catch_0
    move-exception p0

    .line 39
    const-string p1, "[CarLinkStateHook] \u66f4\u65b0 Settings \u5931\u8d25\uff0c\u8bf7\u68c0\u67e5\u662f\u5426\u6709 WRITE_SECURE_SETTINGS \u6743\u9650"

    .line 40
    .line 41
    invoke-static {p1, p0}, Lhun/mesh/carwithenhance/utils/XLog;->e(Ljava/lang/String;Ljava/lang/Throwable;)V

    .line 42
    .line 43
    .line 44
    return-void
.end method


# virtual methods
.method public onHook(Ljava/lang/ClassLoader;)V
    .locals 10

    .line 1
    invoke-direct {p0}, Lhun/mesh/carwithenhance/hook/CarLinkStateHook;->syncKeepAliveConfig()V

    .line 2
    .line 3
    .line 4
    sget-object v0, Lhun/mesh/carwithenhance/dexkit/CarWithDexKitManager;->INSTANCE:Lhun/mesh/carwithenhance/dexkit/CarWithDexKitManager;

    .line 5
    .line 6
    invoke-virtual {v0}, Lhun/mesh/carwithenhance/dexkit/CarWithDexKitManager;->getCastControllerClass()Ljava/lang/String;

    .line 7
    .line 8
    .line 9
    move-result-object v1

    .line 10
    if-eqz v1, :cond_1

    .line 11
    .line 12
    invoke-virtual {v1}, Ljava/lang/String;->isEmpty()Z

    .line 13
    .line 14
    .line 15
    move-result v2

    .line 16
    if-eqz v2, :cond_0

    .line 17
    .line 18
    goto :goto_2

    .line 19
    :cond_0
    :try_start_0
    invoke-virtual {v0}, Lhun/mesh/carwithenhance/dexkit/CarWithDexKitManager;->getCastControllerConnectMethod()Ljava/lang/String;

    .line 20
    .line 21
    .line 22
    move-result-object v0

    .line 23
    const-class v2, Landroid/view/Surface;

    .line 24
    .line 25
    sget-object v3, Ljava/lang/Integer;->TYPE:Ljava/lang/Class;

    .line 26
    .line 27
    new-instance v9, Lhun/mesh/carwithenhance/hook/CarLinkStateHook$1;

    .line 28
    .line 29
    invoke-direct {v9, p0}, Lhun/mesh/carwithenhance/hook/CarLinkStateHook$1;-><init>(Lhun/mesh/carwithenhance/hook/CarLinkStateHook;)V

    .line 30
    .line 31
    .line 32
    move-object v4, v3

    .line 33
    move-object v5, v3

    .line 34
    move-object v6, v3

    .line 35
    move-object v7, v3

    .line 36
    move-object v8, v3

    .line 37
    filled-new-array/range {v2 .. v9}, [Ljava/lang/Object;

    .line 38
    .line 39
    .line 40
    move-result-object v2

    .line 41
    invoke-static {v1, p1, v0, v2}, Lde/robv/android/xposed/XposedHelpers;->findAndHookMethod(Ljava/lang/String;Ljava/lang/ClassLoader;Ljava/lang/String;[Ljava/lang/Object;)Lde/robv/android/xposed/XC_MethodHook$Unhook;
    :try_end_0
    .catchall {:try_start_0 .. :try_end_0} :catchall_0

    .line 42
    .line 43
    .line 44
    goto :goto_0

    .line 45
    :catchall_0
    move-exception v0

    .line 46
    const-string v2, "[CarLinkStateHook] Hook \u8fde\u63a5\u65b9\u6cd5\u5931\u8d25"

    .line 47
    .line 48
    invoke-static {v2, v0}, Lhun/mesh/carwithenhance/utils/XLog;->e(Ljava/lang/String;Ljava/lang/Throwable;)V

    .line 49
    .line 50
    .line 51
    :goto_0
    :try_start_1
    sget-object v0, Lhun/mesh/carwithenhance/dexkit/CarWithDexKitManager;->INSTANCE:Lhun/mesh/carwithenhance/dexkit/CarWithDexKitManager;

    .line 52
    .line 53
    invoke-virtual {v0}, Lhun/mesh/carwithenhance/dexkit/CarWithDexKitManager;->getCastControllerDisconnectMethod()Ljava/lang/String;

    .line 54
    .line 55
    .line 56
    move-result-object v0

    .line 57
    new-instance v2, Lhun/mesh/carwithenhance/hook/CarLinkStateHook$2;

    .line 58
    .line 59
    invoke-direct {v2, p0}, Lhun/mesh/carwithenhance/hook/CarLinkStateHook$2;-><init>(Lhun/mesh/carwithenhance/hook/CarLinkStateHook;)V

    .line 60
    .line 61
    .line 62
    filled-new-array {v2}, [Ljava/lang/Object;

    .line 63
    .line 64
    .line 65
    move-result-object p0

    .line 66
    invoke-static {v1, p1, v0, p0}, Lde/robv/android/xposed/XposedHelpers;->findAndHookMethod(Ljava/lang/String;Ljava/lang/ClassLoader;Ljava/lang/String;[Ljava/lang/Object;)Lde/robv/android/xposed/XC_MethodHook$Unhook;
    :try_end_1
    .catchall {:try_start_1 .. :try_end_1} :catchall_1

    .line 67
    .line 68
    .line 69
    goto :goto_1

    .line 70
    :catchall_1
    move-exception v0

    .line 71
    move-object p0, v0

    .line 72
    const-string p1, "[CarLinkStateHook] Hook \u65ad\u5f00\u65b9\u6cd5\u5931\u8d25"

    .line 73
    .line 74
    invoke-static {p1, p0}, Lhun/mesh/carwithenhance/utils/XLog;->e(Ljava/lang/String;Ljava/lang/Throwable;)V

    .line 75
    .line 76
    .line 77
    :goto_1
    return-void

    .line 78
    :cond_1
    :goto_2
    const-string p0, "[CarLinkStateHook] \u8b66\u544a\uff1aDexkit\u4e2d\u672a\u627e\u5230 CastController \u76ee\u6807\u7c7b\u540d"

    .line 79
    .line 80
    invoke-static {p0}, Lhun/mesh/carwithenhance/utils/XLog;->e(Ljava/lang/String;)V

    .line 81
    .line 82
    .line 83
    return-void
.end method
