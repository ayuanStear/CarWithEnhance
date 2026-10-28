.class Lhun/mesh/carwithenhance/hook/SettingsHook$4$1;
.super Ljava/lang/Object;
.source "r8-map-id-2844820780df513d20833d92dac6e5f4b7fbbd08283aea6743910c9549ac13d9"

# interfaces
.implements Ljava/lang/reflect/InvocationHandler;


# annotations
.annotation system Ldalvik/annotation/EnclosingMethod;
    value = Lhun/mesh/carwithenhance/hook/SettingsHook$4;->afterHookedMethod(Lde/robv/android/xposed/XC_MethodHook$MethodHookParam;)V
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0x1
    name = null
.end annotation


# instance fields
.field final synthetic this$1:Lhun/mesh/carwithenhance/hook/SettingsHook$4;

.field final synthetic val$ctx:Landroid/content/Context;

.field final synthetic val$item:Lhun/mesh/carwithenhance/config/HookItem;

.field final synthetic val$prefs:Landroid/content/SharedPreferences;


# direct methods
.method public constructor <init>(Lhun/mesh/carwithenhance/hook/SettingsHook$4;Landroid/content/SharedPreferences;Lhun/mesh/carwithenhance/config/HookItem;Landroid/content/Context;)V
    .locals 0
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "()V"
        }
    .end annotation

    .line 1
    iput-object p1, p0, Lhun/mesh/carwithenhance/hook/SettingsHook$4$1;->this$1:Lhun/mesh/carwithenhance/hook/SettingsHook$4;

    .line 2
    .line 3
    iput-object p2, p0, Lhun/mesh/carwithenhance/hook/SettingsHook$4$1;->val$prefs:Landroid/content/SharedPreferences;

    .line 4
    .line 5
    iput-object p3, p0, Lhun/mesh/carwithenhance/hook/SettingsHook$4$1;->val$item:Lhun/mesh/carwithenhance/config/HookItem;

    .line 6
    .line 7
    iput-object p4, p0, Lhun/mesh/carwithenhance/hook/SettingsHook$4$1;->val$ctx:Landroid/content/Context;

    .line 8
    .line 9
    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    .line 10
    .line 11
    .line 12
    return-void
.end method


# virtual methods
.method public invoke(Ljava/lang/Object;Ljava/lang/reflect/Method;[Ljava/lang/Object;)Ljava/lang/Object;
    .locals 2

    .line 1
    const-string p1, " = "

    .line 2
    .line 3
    const-string v0, ">> [\u624b\u673a\u7aefUI] \u540c\u6b65\u5199\u5165 Settings.Global: "

    .line 4
    .line 5
    const-string v1, "onPreferenceChange"

    .line 6
    .line 7
    invoke-virtual {p2}, Ljava/lang/reflect/Method;->getName()Ljava/lang/String;

    .line 8
    .line 9
    .line 10
    move-result-object p2

    .line 11
    invoke-virtual {v1, p2}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    .line 12
    .line 13
    .line 14
    move-result p2

    .line 15
    if-eqz p2, :cond_0

    .line 16
    .line 17
    const/4 p2, 0x1

    .line 18
    aget-object p2, p3, p2

    .line 19
    .line 20
    check-cast p2, Ljava/lang/Boolean;

    .line 21
    .line 22
    invoke-virtual {p2}, Ljava/lang/Boolean;->booleanValue()Z

    .line 23
    .line 24
    .line 25
    move-result p2

    .line 26
    iget-object p3, p0, Lhun/mesh/carwithenhance/hook/SettingsHook$4$1;->val$prefs:Landroid/content/SharedPreferences;

    .line 27
    .line 28
    invoke-interface {p3}, Landroid/content/SharedPreferences;->edit()Landroid/content/SharedPreferences$Editor;

    .line 29
    .line 30
    .line 31
    move-result-object p3

    .line 32
    iget-object v1, p0, Lhun/mesh/carwithenhance/hook/SettingsHook$4$1;->val$item:Lhun/mesh/carwithenhance/config/HookItem;

    .line 33
    .line 34
    iget-object v1, v1, Lhun/mesh/carwithenhance/config/HookItem;->key:Ljava/lang/String;

    .line 35
    .line 36
    invoke-interface {p3, v1, p2}, Landroid/content/SharedPreferences$Editor;->putBoolean(Ljava/lang/String;Z)Landroid/content/SharedPreferences$Editor;

    .line 37
    .line 38
    .line 39
    move-result-object p3

    .line 40
    invoke-interface {p3}, Landroid/content/SharedPreferences$Editor;->apply()V
# 当    iget-object p3, p0, Lhun/mesh/carwithenhance/hook/SettingsHook$4$1;->val$item:Lhun/mesh/carwithenhance/config/HookItem;

    iget-object p3, p3, Lhun/mesh/carwithenhance/config/HookItem;->key:Ljava/lang/String;

    const-string v1, "hook_disconnect_kill_music_enabled"

    invoke-virtual {p3, v1}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result p3

    if-eqz p3, :cond_vis

    invoke-static {p2}, Lhun/mesh/carwithenhance/hook/SettingsHook;->setDelayPrefVisible(Z)V

    :cond_vis

    .line 41
    .line 42
    .line 43
    :try_start_0
    iget-object p3, p0, Lhun/mesh/carwithenhance/hook/SettingsHook$4$1;->val$ctx:Landroid/content/Context;

    .line 44
    .line 45
    invoke-virtual {p3}, Landroid/content/Context;->getContentResolver()Landroid/content/ContentResolver;

    .line 46
    .line 47
    .line 48
    move-result-object p3

    .line 49
    iget-object v1, p0, Lhun/mesh/carwithenhance/hook/SettingsHook$4$1;->val$item:Lhun/mesh/carwithenhance/config/HookItem;

    .line 50
    .line 51
    iget-object v1, v1, Lhun/mesh/carwithenhance/config/HookItem;->key:Ljava/lang/String;

    .line 52
    .line 53
    invoke-static {p3, v1, p2}, Landroid/provider/Settings$Global;->putInt(Landroid/content/ContentResolver;Ljava/lang/String;I)Z

    .line 54
    .line 55
    .line 56
    new-instance p3, Ljava/lang/StringBuilder;

    .line 57
    .line 58
    invoke-direct {p3, v0}, Ljava/lang/StringBuilder;-><init>(Ljava/lang/String;)V

    .line 59
    .line 60
    .line 61
    iget-object v0, p0, Lhun/mesh/carwithenhance/hook/SettingsHook$4$1;->val$item:Lhun/mesh/carwithenhance/config/HookItem;

    .line 62
    .line 63
    iget-object v0, v0, Lhun/mesh/carwithenhance/config/HookItem;->key:Ljava/lang/String;

    .line 64
    .line 65
    invoke-virtual {p3, v0}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    .line 66
    .line 67
    .line 68
    invoke-virtual {p3, p1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    .line 69
    .line 70
    .line 71
    invoke-virtual {p3, p2}, Ljava/lang/StringBuilder;->append(Z)Ljava/lang/StringBuilder;

    .line 72
    .line 73
    .line 74
    invoke-virtual {p3}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    .line 75
    .line 76
    .line 77
    move-result-object p3

    .line 78
    invoke-static {p3}, Lhun/mesh/carwithenhance/utils/XLog;->i(Ljava/lang/String;)V
    :try_end_0
    .catchall {:try_start_0 .. :try_end_0} :catchall_0

    .line 79
    .line 80
    .line 81
    goto :goto_0

    .line 82
    :catchall_0
    move-exception p3

    .line 83
    new-instance v0, Ljava/lang/StringBuilder;

    .line 84
    .line 85
    const-string v1, ">> [\u624b\u673a\u7aefUI] \u5199\u5165 Settings.Global \u5931\u8d25: "

    .line 86
    .line 87
    invoke-direct {v0, v1}, Ljava/lang/StringBuilder;-><init>(Ljava/lang/String;)V

    .line 88
    .line 89
    .line 90
    iget-object v1, p0, Lhun/mesh/carwithenhance/hook/SettingsHook$4$1;->val$item:Lhun/mesh/carwithenhance/config/HookItem;

    .line 91
    .line 92
    iget-object v1, v1, Lhun/mesh/carwithenhance/config/HookItem;->key:Ljava/lang/String;

    .line 93
    .line 94
    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    .line 95
    .line 96
    .line 97
    invoke-virtual {v0}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    .line 98
    .line 99
    .line 100
    move-result-object v0

    .line 101
    invoke-static {v0, p3}, Lhun/mesh/carwithenhance/utils/XLog;->e(Ljava/lang/String;Ljava/lang/Throwable;)V

    .line 102
    .line 103
    .line 104
    :goto_0
    new-instance p3, Ljava/lang/StringBuilder;

    .line 105
    .line 106
    const-string v0, ">> [\u624b\u673a\u7aefUI] \u5f00\u5173\u5df2\u66f4\u65b0: "

    .line 107
    .line 108
    invoke-direct {p3, v0}, Ljava/lang/StringBuilder;-><init>(Ljava/lang/String;)V

    .line 109
    .line 110
    .line 111
    iget-object p0, p0, Lhun/mesh/carwithenhance/hook/SettingsHook$4$1;->val$item:Lhun/mesh/carwithenhance/config/HookItem;

    .line 112
    .line 113
    iget-object p0, p0, Lhun/mesh/carwithenhance/config/HookItem;->key:Ljava/lang/String;

    .line 114
    .line 115
    invoke-virtual {p3, p0}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    .line 116
    .line 117
    .line 118
    invoke-virtual {p3, p1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    .line 119
    .line 120
    .line 121
    invoke-virtual {p3, p2}, Ljava/lang/StringBuilder;->append(Z)Ljava/lang/StringBuilder;

    .line 122
    .line 123
    .line 124
    invoke-virtual {p3}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    .line 125
    .line 126
    .line 127
    move-result-object p0

    .line 128
    invoke-static {p0}, Lhun/mesh/carwithenhance/utils/XLog;->i(Ljava/lang/String;)V

    .line 129
    .line 130
    .line 131
    sget-object p0, Ljava/lang/Boolean;->TRUE:Ljava/lang/Boolean;

    .line 132
    .line 133
    return-object p0

    .line 134
    :cond_0
    const/4 p0, 0x0

    .line 135
    return-object p0
.end method
