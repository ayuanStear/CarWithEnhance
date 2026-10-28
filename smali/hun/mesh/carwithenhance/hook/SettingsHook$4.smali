.class Lhun/mesh/carwithenhance/hook/SettingsHook$4;
.super Lde/robv/android/xposed/XC_MethodHook;
.source "r8-map-id-2844820780df513d20833d92dac6e5f4b7fbbd08283aea6743910c9549ac13d9"


# annotations
.annotation system Ldalvik/annotation/EnclosingMethod;
    value = Lhun/mesh/carwithenhance/hook/SettingsHook;->onHook(Ljava/lang/ClassLoader;)V
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0x1
    name = null
.end annotation


# instance fields
.field final synthetic this$0:Lhun/mesh/carwithenhance/hook/SettingsHook;

.field final synthetic val$cl:Ljava/lang/ClassLoader;


# direct methods
.method public constructor <init>(Lhun/mesh/carwithenhance/hook/SettingsHook;Ljava/lang/ClassLoader;)V
    .locals 0

    .line 1
    iput-object p1, p0, Lhun/mesh/carwithenhance/hook/SettingsHook$4;->this$0:Lhun/mesh/carwithenhance/hook/SettingsHook;

    .line 2
    .line 3
    iput-object p2, p0, Lhun/mesh/carwithenhance/hook/SettingsHook$4;->val$cl:Ljava/lang/ClassLoader;

    .line 4
    .line 5
    invoke-direct {p0}, Lde/robv/android/xposed/XC_MethodHook;-><init>()V

    .line 6
    .line 7
    .line 8
    return-void
.end method


# virtual methods
.method public afterHookedMethod(Lde/robv/android/xposed/XC_MethodHook$MethodHookParam;)V
    .locals 12

    .line 1
    iget-object v0, p1, Lde/robv/android/xposed/XC_MethodHook$MethodHookParam;->thisObject:Ljava/lang/Object;

    .line 2
    .line 3
    const/4 v1, 0x0

    .line 4
    new-array v2, v1, [Ljava/lang/Object;

    .line 5
    .line 6
    const-string v3, "getActivity"

    .line 7
    .line 8
    invoke-static {v0, v3, v2}, Lde/robv/android/xposed/XposedHelpers;->callMethod(Ljava/lang/Object;Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/Object;

    .line 9
    .line 10
    .line 11
    move-result-object v0

    .line 12
    check-cast v0, Landroid/app/Activity;

    .line 13
    .line 14
    if-nez v0, :cond_0

    .line 15
    .line 16
    goto :goto_0

    .line 17
    :cond_0
    iget-object p1, p1, Lde/robv/android/xposed/XC_MethodHook$MethodHookParam;->thisObject:Ljava/lang/Object;

    .line 18
    .line 19
    const-string v2, "getPreferenceScreen"

    .line 20
    .line 21
    new-array v3, v1, [Ljava/lang/Object;

    .line 22
    .line 23
    invoke-static {p1, v2, v3}, Lde/robv/android/xposed/XposedHelpers;->callMethod(Ljava/lang/Object;Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/Object;

    .line 24
    .line 25
    .line 26
    move-result-object p1

    .line 27
    if-nez p1, :cond_1

    .line 28
    .line 29
    :goto_0
    return-void

    .line 30
    :cond_1
    const-string v2, "carwithenhance_hooksettings"

    .line 31
    .line 32
    invoke-virtual {v0, v2, v1}, Landroid/content/Context;->getSharedPreferences(Ljava/lang/String;I)Landroid/content/SharedPreferences;

    .line 33
    .line 34
    .line 35
    move-result-object v1

    .line 36
    const-string v2, "androidx.preference.PreferenceCategory"

    .line 37
    .line 38
    iget-object v3, p0, Lhun/mesh/carwithenhance/hook/SettingsHook$4;->val$cl:Ljava/lang/ClassLoader;

    .line 39
    .line 40
    invoke-static {v2, v3}, Lde/robv/android/xposed/XposedHelpers;->findClass(Ljava/lang/String;Ljava/lang/ClassLoader;)Ljava/lang/Class;

    .line 41
    .line 42
    .line 43
    move-result-object v2

    .line 44
    filled-new-array {v0}, [Ljava/lang/Object;

    .line 45
    .line 46
    .line 47
    move-result-object v3

    .line 48
    invoke-static {v2, v3}, Lde/robv/android/xposed/XposedHelpers;->newInstance(Ljava/lang/Class;[Ljava/lang/Object;)Ljava/lang/Object;

    .line 49
    .line 50
    .line 51
    move-result-object v2

    .line 52
    const-string v3, "CarWith Enhance"

    .line 53
    .line 54
    filled-new-array {v3}, [Ljava/lang/Object;

    .line 55
    .line 56
    .line 57
    move-result-object v3

    .line 58
    const-string v4, "setTitle"

    .line 59
    .line 60
    invoke-static {v2, v4, v3}, Lde/robv/android/xposed/XposedHelpers;->callMethod(Ljava/lang/Object;Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/Object;

    .line 61
    .line 62
    .line 63
    filled-new-array {v2}, [Ljava/lang/Object;

    .line 64
    .line 65
    .line 66
    move-result-object v3

    .line 67
    const-string v5, "addPreference"

    .line 68
    .line 69
    invoke-static {p1, v5, v3}, Lde/robv/android/xposed/XposedHelpers;->callMethod(Ljava/lang/Object;Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/Object;

    .line 70
    .line 71
    .line 72
    const-string p1, "androidx.preference.SwitchPreference"

    .line 73
    .line 74
    iget-object v3, p0, Lhun/mesh/carwithenhance/hook/SettingsHook$4;->val$cl:Ljava/lang/ClassLoader;

    .line 75
    .line 76
    invoke-static {p1, v3}, Lde/robv/android/xposed/XposedHelpers;->findClass(Ljava/lang/String;Ljava/lang/ClassLoader;)Ljava/lang/Class;

    .line 77
    .line 78
    .line 79
    move-result-object p1

    .line 80
    const-string v3, "androidx.preference.Preference$OnPreferenceChangeListener"

    .line 81
    .line 82
    iget-object v6, p0, Lhun/mesh/carwithenhance/hook/SettingsHook$4;->val$cl:Ljava/lang/ClassLoader;

    .line 83
    .line 84
    invoke-static {v3, v6}, Lde/robv/android/xposed/XposedHelpers;->findClass(Ljava/lang/String;Ljava/lang/ClassLoader;)Ljava/lang/Class;

    .line 85
    .line 86
    .line 87
    move-result-object v3

    .line 88
    invoke-static {}, Lhun/mesh/carwithenhance/config/HookConfigs;->getAllItems()Ljava/util/List;

    .line 89
    .line 90
    .line 91
    move-result-object v6

    .line 92
    invoke-interface {v6}, Ljava/util/List;->iterator()Ljava/util/Iterator;

    .line 93
    .line 94
    .line 95
    move-result-object v6

    .line 96
    :goto_1
    invoke-interface {v6}, Ljava/util/Iterator;->hasNext()Z

    .line 97
    .line 98
    .line 99
    move-result v7

    .line 100
    if-eqz v7, :cond_2

    .line 101
    .line 102
    invoke-interface {v6}, Ljava/util/Iterator;->next()Ljava/lang/Object;

    .line 103
    .line 104
    .line 105
    move-result-object v7

    .line 106
    check-cast v7, Lhun/mesh/carwithenhance/config/HookItem;

    .line 107
    .line 108
    filled-new-array {v0}, [Ljava/lang/Object;

    .line 109
    .line 110
    .line 111
    move-result-object v8

    .line 112
    invoke-static {p1, v8}, Lde/robv/android/xposed/XposedHelpers;->newInstance(Ljava/lang/Class;[Ljava/lang/Object;)Ljava/lang/Object;

    .line 113
    .line 114
    .line 115
    move-result-object v8

    .line 116
    iget-object v9, v7, Lhun/mesh/carwithenhance/config/HookItem;->key:Ljava/lang/String;

    .line 117
    .line 118
    filled-new-array {v9}, [Ljava/lang/Object;

    .line 119
    .line 120
    .line 121
    move-result-object v9

    .line 122
    const-string v10, "setKey"

    .line 123
    .line 124
    invoke-static {v8, v10, v9}, Lde/robv/android/xposed/XposedHelpers;->callMethod(Ljava/lang/Object;Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/Object;

    .line 125
    .line 126
    .line 127
    iget-object v9, v7, Lhun/mesh/carwithenhance/config/HookItem;->title:Ljava/lang/String;

    .line 128
    .line 129
    filled-new-array {v9}, [Ljava/lang/Object;

    .line 130
    .line 131
    .line 132
    move-result-object v9

    .line 133
    invoke-static {v8, v4, v9}, Lde/robv/android/xposed/XposedHelpers;->callMethod(Ljava/lang/Object;Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/Object;

    .line 134
    .line 135
    .line 136
    iget-object v9, v7, Lhun/mesh/carwithenhance/config/HookItem;->subTitle:Ljava/lang/String;

    .line 137
    .line 138
    filled-new-array {v9}, [Ljava/lang/Object;

    .line 139
    .line 140
    .line 141
    move-result-object v9

    .line 142
    const-string v10, "setSummary"

    .line 143
    .line 144
    invoke-static {v8, v10, v9}, Lde/robv/android/xposed/XposedHelpers;->callMethod(Ljava/lang/Object;Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/Object;

    .line 145
    .line 146
    .line 147
    iget-boolean v9, v7, Lhun/mesh/carwithenhance/config/HookItem;->defaultValue:Z

    .line 148
    .line 149
    invoke-static {v9}, Ljava/lang/Boolean;->valueOf(Z)Ljava/lang/Boolean;

    .line 150
    .line 151
    .line 152
    move-result-object v9

    .line 153
    filled-new-array {v9}, [Ljava/lang/Object;

    .line 154
    .line 155
    .line 156
    move-result-object v9

    .line 157
    const-string v10, "setDefaultValue"

    .line 158
    .line 159
    invoke-static {v8, v10, v9}, Lde/robv/android/xposed/XposedHelpers;->callMethod(Ljava/lang/Object;Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/Object;

    .line 160
    .line 161
    .line 162
    iget-object v9, v7, Lhun/mesh/carwithenhance/config/HookItem;->key:Ljava/lang/String;

    .line 163
    .line 164
    iget-boolean v10, v7, Lhun/mesh/carwithenhance/config/HookItem;->defaultValue:Z

    .line 165
    .line 166
    invoke-interface {v1, v9, v10}, Landroid/content/SharedPreferences;->getBoolean(Ljava/lang/String;Z)Z

    .line 167
    .line 168
    .line 169
    move-result v9

    .line 170
    invoke-static {v9}, Ljava/lang/Boolean;->valueOf(Z)Ljava/lang/Boolean;

    .line 171
    .line 172
    .line 173
    move-result-object v9

    .line 174
    filled-new-array {v9}, [Ljava/lang/Object;

    .line 175
    .line 176
    .line 177
    move-result-object v9

    .line 178
    const-string v10, "setChecked"

    .line 179
    .line 180
    invoke-static {v8, v10, v9}, Lde/robv/android/xposed/XposedHelpers;->callMethod(Ljava/lang/Object;Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/Object;

    .line 181
    .line 182
    .line 183
    iget-object v9, p0, Lhun/mesh/carwithenhance/hook/SettingsHook$4;->val$cl:Ljava/lang/ClassLoader;

    .line 184
    .line 185
    filled-new-array {v3}, [Ljava/lang/Class;

    .line 186
    .line 187
    .line 188
    move-result-object v10

    .line 189
    new-instance v11, Lhun/mesh/carwithenhance/hook/SettingsHook$4$1;

    .line 190
    .line 191
    invoke-direct {v11, p0, v1, v7, v0}, Lhun/mesh/carwithenhance/hook/SettingsHook$4$1;-><init>(Lhun/mesh/carwithenhance/hook/SettingsHook$4;Landroid/content/SharedPreferences;Lhun/mesh/carwithenhance/config/HookItem;Landroid/content/Context;)V

    .line 192
    .line 193
    .line 194
    invoke-static {v9, v10, v11}, Ljava/lang/reflect/Proxy;->newProxyInstance(Ljava/lang/ClassLoader;[Ljava/lang/Class;Ljava/lang/reflect/InvocationHandler;)Ljava/lang/Object;

    .line 195
    .line 196
    .line 197
    move-result-object v7

    .line 198
    const-string v9, "setOnPreferenceChangeListener"

    .line 199
    .line 200
    filled-new-array {v7}, [Ljava/lang/Object;

    .line 201
    .line 202
    .line 203
    move-result-object v7

    .line 204
    invoke-static {v8, v9, v7}, Lde/robv/android/xposed/XposedHelpers;->callMethod(Ljava/lang/Object;Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/Object;

    .line 205
    .line 206
    .line 207
    filled-new-array {v8}, [Ljava/lang/Object;

    .line 208
    .line 209
    .line 210
    move-result-object v7

    .line 211
    invoke-static {v2, v5, v7}, Lde/robv/android/xposed/XposedHelpers;->callMethod(Ljava/lang/Object;Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/Object;

    .line 212
    .line 213
    .line 214
    goto :goto_1

    .line 215
    :cond_2
# ============================================================
# [新增] 断开后延迟关闭秒数输入框（EditTextPreference）
# - 摘要/输入框回显当前值
# - 手动输入，只保留数字
# - 总开关关闭时自动隐藏
# ============================================================
    :try_start_delay
    const-string v6, "androidx.preference.EditTextPreference"

    iget-object v7, p0, Lhun/mesh/carwithenhance/hook/SettingsHook$4;->val$cl:Ljava/lang/ClassLoader;

    invoke-static {v6, v7}, Lde/robv/android/xposed/XposedHelpers;->findClass(Ljava/lang/String;Ljava/lang/ClassLoader;)Ljava/lang/Class;

    move-result-object v6

    filled-new-array {v0}, [Ljava/lang/Object;

    move-result-object v7

    invoke-static {v6, v7}, Lde/robv/android/xposed/XposedHelpers;->newInstance(Ljava/lang/Class;[Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v6

    sput-object v6, Lhun/mesh/carwithenhance/hook/SettingsHook;->sDelayPref:Ljava/lang/Object;

    const-string v7, "hook_disconnect_kill_delay_ui"

    filled-new-array {v7}, [Ljava/lang/Object;

    move-result-object v8

    const-string v9, "setKey"

    invoke-static {v6, v9, v8}, Lde/robv/android/xposed/XposedHelpers;->callMethod(Ljava/lang/Object;Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/Object;

    const-string v7, "\u65ad\u5f00\u540e\u5ef6\u8fdf\u5173\u95ed\u79d2\u6570"

    filled-new-array {v7}, [Ljava/lang/Object;

    move-result-object v8

    invoke-static {v6, v4, v8}, Lde/robv/android/xposed/XposedHelpers;->callMethod(Ljava/lang/Object;Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/Object;

    const-string v7, "hook_disconnect_kill_delay_sec"

    const/4 v8, 0x5

    invoke-interface {v1, v7, v8}, Landroid/content/SharedPreferences;->getInt(Ljava/lang/String;I)I

    move-result v7

    invoke-static {v7}, Ljava/lang/String;->valueOf(I)Ljava/lang/String;

    move-result-object v8

    filled-new-array {v8}, [Ljava/lang/Object;

    move-result-object v8

    const-string v9, "setText"

    invoke-static {v6, v9, v8}, Lde/robv/android/xposed/XposedHelpers;->callMethod(Ljava/lang/Object;Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/Object;

    new-instance v8, Ljava/lang/StringBuilder;

    invoke-direct {v8}, Ljava/lang/StringBuilder;-><init>()V

    const-string v9, "\u5f53\u524d "

    invoke-virtual {v8, v9}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v8

    invoke-virtual {v8, v7}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    move-result-object v8

    const-string v9, " \u79d2\uff080=\u7acb\u5373\uff09"

    invoke-virtual {v8, v9}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v8

    invoke-virtual {v8}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v8

    filled-new-array {v8}, [Ljava/lang/Object;

    move-result-object v8

    const-string v9, "setSummary"

    invoke-static {v6, v9, v8}, Lde/robv/android/xposed/XposedHelpers;->callMethod(Ljava/lang/Object;Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/Object;

    const-string v7, "hook_disconnect_kill_music_enabled"

    const/4 v8, 0x1

    invoke-interface {v1, v7, v8}, Landroid/content/SharedPreferences;->getBoolean(Ljava/lang/String;Z)Z

    move-result v7

    invoke-static {v7}, Ljava/lang/Boolean;->valueOf(Z)Ljava/lang/Boolean;

    move-result-object v7

    filled-new-array {v7}, [Ljava/lang/Object;

    move-result-object v7

    const-string v8, "setVisible"

    invoke-static {v6, v8, v7}, Lde/robv/android/xposed/XposedHelpers;->callMethod(Ljava/lang/Object;Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/Object;

    iget-object v7, p0, Lhun/mesh/carwithenhance/hook/SettingsHook$4;->val$cl:Ljava/lang/ClassLoader;

    filled-new-array {v3}, [Ljava/lang/Class;

    move-result-object v8

    new-instance v9, Lhun/mesh/carwithenhance/hook/SettingsHook$4$2;

    invoke-direct {v9, v1, v6}, Lhun/mesh/carwithenhance/hook/SettingsHook$4$2;-><init>(Landroid/content/SharedPreferences;Ljava/lang/Object;)V

    invoke-static {v7, v8, v9}, Ljava/lang/reflect/Proxy;->newProxyInstance(Ljava/lang/ClassLoader;[Ljava/lang/Class;Ljava/lang/reflect/InvocationHandler;)Ljava/lang/Object;

    move-result-object v7

    filled-new-array {v7}, [Ljava/lang/Object;

    move-result-object v7

    const-string v8, "setOnPreferenceChangeListener"

    invoke-static {v6, v8, v7}, Lde/robv/android/xposed/XposedHelpers;->callMethod(Ljava/lang/Object;Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/Object;

    filled-new-array {v6}, [Ljava/lang/Object;

    move-result-object v7

    invoke-static {v2, v5, v7}, Lde/robv/android/xposed/XposedHelpers;->callMethod(Ljava/lang/Object;Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/Object;

    :try_end_delay
    .catch Ljava/lang/Throwable; {:try_start_delay .. :try_end_delay} :catch_delay

    goto :goto_delay

    :catch_delay
    move-exception v6

    const-string v7, ">> [\u624b\u673a\u7aefUI] \u6ce8\u5165\u5ef6\u8fdf\u5173\u95ed\u914d\u7f6e\u5931\u8d25"

    invoke-static {v7, v6}, Lhun/mesh/carwithenhance/utils/XLog;->e(Ljava/lang/String;Ljava/lang/Throwable;)V

    :goto_delay
    const-string p0, ">> [\u624b\u673a\u7aefUI] \u6210\u529f\u5168\u52a8\u6001\u6ce8\u5165[CarWith Enhance]\u4f18\u5316\u8bbe\u7f6eUI"

    .line 216
    .line 217
    invoke-static {p0}, Lhun/mesh/carwithenhance/utils/XLog;->i(Ljava/lang/String;)V

    .line 218
    .line 219
    .line 220
    return-void
.end method
