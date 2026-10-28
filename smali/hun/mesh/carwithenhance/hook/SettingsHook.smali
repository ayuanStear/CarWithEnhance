.class public Lhun/mesh/carwithenhance/hook/SettingsHook;
.super Ljava/lang/Object;
.source "r8-map-id-2844820780df513d20833d92dac6e5f4b7fbbd08283aea6743910c9549ac13d9"

# interfaces
.implements Lhun/mesh/carwithenhance/hook/IHook;
# static fields
# 保存.field public static sDelayPref:Ljava/lang/Object;



# direct methods
.method public constructor <init>()V
    .locals 0

    .line 1
    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    .line 2
    .line 3
    .line 4
    return-void
.end method

.method public static bridge synthetic a(Ljava/lang/Object;Ljava/lang/String;Z)V
    .locals 0

    .line 1
    invoke-static {p0, p1, p2}, Lhun/mesh/carwithenhance/hook/SettingsHook;->refreshSwitchPref(Ljava/lang/Object;Ljava/lang/String;Z)V

    .line 2
    .line 3
    .line 4
    return-void
.end method

.method private static refreshSwitchPref(Ljava/lang/Object;Ljava/lang/String;Z)V
    .locals 1

    .line 1
    :try_start_0
    const-string v0, "findPreference"

    .line 2
    .line 3
    filled-new-array {p1}, [Ljava/lang/Object;

    .line 4
    .line 5
    .line 6
    move-result-object p1

    .line 7
    invoke-static {p0, v0, p1}, Lde/robv/android/xposed/XposedHelpers;->callMethod(Ljava/lang/Object;Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/Object;

    .line 8
    .line 9
    .line 10
    move-result-object p0

    .line 11
    if-eqz p0, :cond_0

    .line 12
    .line 13
    const-string p1, "setChecked"

    .line 14
    .line 15
    invoke-static {p2}, Ljava/lang/Boolean;->valueOf(Z)Ljava/lang/Boolean;

    .line 16
    .line 17
    .line 18
    move-result-object p2

    .line 19
    filled-new-array {p2}, [Ljava/lang/Object;

    .line 20
    .line 21
    .line 22
    move-result-object p2

    .line 23
    invoke-static {p0, p1, p2}, Lde/robv/android/xposed/XposedHelpers;->callMethod(Ljava/lang/Object;Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/Object;
    :try_end_0
    .catchall {:try_start_0 .. :try_end_0} :catchall_0

    .line 24
    .line 25
    .line 26
    :catchall_0
    :cond_0
    return-void
.end method


# 根据.method public static setDelayPrefVisible(Z)V
    .locals 3

    sget-object v0, Lhun/mesh/carwithenhance/hook/SettingsHook;->sDelayPref:Ljava/lang/Object;

    if-eqz v0, :cond_0

    invoke-static {p0}, Ljava/lang/Boolean;->valueOf(Z)Ljava/lang/Boolean;

    move-result-object v1

    filled-new-array {v1}, [Ljava/lang/Object;

    move-result-object v1

    const-string v2, "setVisible"

    invoke-static {v0, v2, v1}, Lde/robv/android/xposed/XposedHelpers;->callMethod(Ljava/lang/Object;Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/Object;

    :cond_0
    return-void
.end method

# virtual methods
.method public onHook(Ljava/lang/ClassLoader;)V
    .locals 7

    .line 1
    const-class v0, Ljava/lang/String;

    .line 2
    .line 3
    :try_start_0
    const-string v1, "com.google.gson.Gson"

    .line 4
    .line 5
    invoke-static {v1, p1}, Lde/robv/android/xposed/XposedHelpers;->findClass(Ljava/lang/String;Ljava/lang/ClassLoader;)Ljava/lang/Class;

    .line 6
    .line 7
    .line 8
    move-result-object v1

    .line 9
    const-string v2, "fromJson"

    .line 10
    .line 11
    const-class v3, Ljava/lang/reflect/Type;

    .line 12
    .line 13
    new-instance v4, Lhun/mesh/carwithenhance/hook/SettingsHook$1;

    .line 14
    .line 15
    invoke-direct {v4, p0}, Lhun/mesh/carwithenhance/hook/SettingsHook$1;-><init>(Lhun/mesh/carwithenhance/hook/SettingsHook;)V

    .line 16
    .line 17
    .line 18
    filled-new-array {v0, v3, v4}, [Ljava/lang/Object;

    .line 19
    .line 20
    .line 21
    move-result-object v3

    .line 22
    invoke-static {v1, v2, v3}, Lde/robv/android/xposed/XposedHelpers;->findAndHookMethod(Ljava/lang/Class;Ljava/lang/String;[Ljava/lang/Object;)Lde/robv/android/xposed/XC_MethodHook$Unhook;
    :try_end_0
    .catchall {:try_start_0 .. :try_end_0} :catchall_0

    .line 23
    .line 24
    .line 25
    goto :goto_0

    .line 26
    :catchall_0
    move-exception v1

    .line 27
    const-string v2, "\u274c Hook 3-1 (\u8f66\u673a\u7aef JSON \u4e8c\u7ea7\u83dc\u5355\u6ce8\u5165) \u5931\u8d25: "

    .line 28
    .line 29
    invoke-static {v2, v1}, Lhun/mesh/carwithenhance/utils/XLog;->e(Ljava/lang/String;Ljava/lang/Throwable;)V

    .line 30
    .line 31
    .line 32
    :goto_0
    :try_start_1
    sget-object v1, Lhun/mesh/carwithenhance/dexkit/CarWithDexKitManager;->INSTANCE:Lhun/mesh/carwithenhance/dexkit/CarWithDexKitManager;

    .line 33
    .line 34
    invoke-virtual {v1}, Lhun/mesh/carwithenhance/dexkit/CarWithDexKitManager;->getSettingsClass()Ljava/lang/String;

    .line 35
    .line 36
    .line 37
    move-result-object v2

    .line 38
    invoke-virtual {v1}, Lhun/mesh/carwithenhance/dexkit/CarWithDexKitManager;->getSettingsMethod()Ljava/lang/String;

    .line 39
    .line 40
    .line 41
    move-result-object v1

    .line 42
    if-eqz v2, :cond_1

    .line 43
    .line 44
    invoke-virtual {v2}, Ljava/lang/String;->isEmpty()Z

    .line 45
    .line 46
    .line 47
    move-result v3

    .line 48
    if-eqz v3, :cond_0

    .line 49
    .line 50
    goto :goto_1

    .line 51
    :cond_0
    invoke-static {v2, p1}, Lde/robv/android/xposed/XposedHelpers;->findClass(Ljava/lang/String;Ljava/lang/ClassLoader;)Ljava/lang/Class;

    .line 52
    .line 53
    .line 54
    move-result-object v2

    .line 55
    const-class v3, Landroid/content/Context;

    .line 56
    .line 57
    const-class v4, Landroid/content/SharedPreferences;

    .line 58
    .line 59
    new-instance v5, Lhun/mesh/carwithenhance/hook/SettingsHook$2;

    .line 60
    .line 61
    invoke-direct {v5, p0}, Lhun/mesh/carwithenhance/hook/SettingsHook$2;-><init>(Lhun/mesh/carwithenhance/hook/SettingsHook;)V

    .line 62
    .line 63
    .line 64
    filled-new-array {v3, v0, v4, v5}, [Ljava/lang/Object;

    .line 65
    .line 66
    .line 67
    move-result-object v3

    .line 68
    invoke-static {v2, v1, v3}, Lde/robv/android/xposed/XposedHelpers;->findAndHookMethod(Ljava/lang/Class;Ljava/lang/String;[Ljava/lang/Object;)Lde/robv/android/xposed/XC_MethodHook$Unhook;

    .line 69
    .line 70
    .line 71
    goto :goto_3

    .line 72
    :catchall_1
    move-exception v1

    .line 73
    goto :goto_2

    .line 74
    :cond_1
    :goto_1
    const-string v1, "SettingsHook \u52a8\u6001\u76ee\u6807\u672a\u627e\u5230\uff0c\u8df3\u8fc7 Hook"

    .line 75
    .line 76
    invoke-static {v1}, Lhun/mesh/carwithenhance/utils/XLog;->e(Ljava/lang/String;)V
    :try_end_1
    .catchall {:try_start_1 .. :try_end_1} :catchall_1

    .line 77
    .line 78
    .line 79
    goto/16 :goto_a

    .line 80
    .line 81
    :goto_2
    const-string v2, "\u274c Hook 3-2 (\u8f66\u673a\u7aefUI\u72b6\u6001\u540c\u6b65) \u5931\u8d25: "

    .line 82
    .line 83
    invoke-static {v2, v1}, Lhun/mesh/carwithenhance/utils/XLog;->e(Ljava/lang/String;Ljava/lang/Throwable;)V

    .line 84
    .line 85
    .line 86
    :goto_3
    :try_start_2
    sget-object v1, Lhun/mesh/carwithenhance/dexkit/CarWithDexKitManager;->INSTANCE:Lhun/mesh/carwithenhance/dexkit/CarWithDexKitManager;

    .line 87
    .line 88
    invoke-virtual {v1}, Lhun/mesh/carwithenhance/dexkit/CarWithDexKitManager;->getSettingsAdapterClass()Ljava/lang/String;

    .line 89
    .line 90
    .line 91
    move-result-object v2

    .line 92
    invoke-virtual {v1}, Lhun/mesh/carwithenhance/dexkit/CarWithDexKitManager;->getSettingsAdapterMethods()Ljava/lang/String;

    .line 93
    .line 94
    .line 95
    move-result-object v1

    .line 96
    if-eqz v2, :cond_4

    .line 97
    .line 98
    invoke-virtual {v2}, Ljava/lang/String;->isEmpty()Z

    .line 99
    .line 100
    .line 101
    move-result v3

    .line 102
    if-nez v3, :cond_4

    .line 103
    .line 104
    if-eqz v1, :cond_4

    .line 105
    .line 106
    invoke-virtual {v1}, Ljava/lang/String;->isEmpty()Z

    .line 107
    .line 108
    .line 109
    move-result v3

    .line 110
    if-eqz v3, :cond_2

    .line 111
    .line 112
    goto :goto_6

    .line 113
    :cond_2
    invoke-static {v2, p1}, Lde/robv/android/xposed/XposedHelpers;->findClass(Ljava/lang/String;Ljava/lang/ClassLoader;)Ljava/lang/Class;

    .line 114
    .line 115
    .line 116
    move-result-object v2

    .line 117
    const-string v3, ","

    .line 118
    .line 119
    invoke-virtual {v1, v3}, Ljava/lang/String;->split(Ljava/lang/String;)[Ljava/lang/String;

    .line 120
    .line 121
    .line 122
    move-result-object v1

    .line 123
    array-length v3, v1

    .line 124
    const/4 v4, 0x0

    .line 125
    :goto_4
    if-ge v4, v3, :cond_5

    .line 126
    .line 127
    aget-object v5, v1, v4

    .line 128
    .line 129
    invoke-virtual {v5}, Ljava/lang/String;->trim()Ljava/lang/String;

    .line 130
    .line 131
    .line 132
    move-result-object v6

    .line 133
    invoke-virtual {v6}, Ljava/lang/String;->isEmpty()Z

    .line 134
    .line 135
    .line 136
    move-result v6

    .line 137
    if-eqz v6, :cond_3

    .line 138
    .line 139
    goto :goto_5

    .line 140
    :cond_3
    invoke-virtual {v5}, Ljava/lang/String;->trim()Ljava/lang/String;

    .line 141
    .line 142
    .line 143
    move-result-object v5

    .line 144
    new-instance v6, Lhun/mesh/carwithenhance/hook/SettingsHook$3;

    .line 145
    .line 146
    invoke-direct {v6, p0}, Lhun/mesh/carwithenhance/hook/SettingsHook$3;-><init>(Lhun/mesh/carwithenhance/hook/SettingsHook;)V

    .line 147
    .line 148
    .line 149
    invoke-static {v2, v5, v6}, Lde/robv/android/xposed/XposedBridge;->hookAllMethods(Ljava/lang/Class;Ljava/lang/String;Lde/robv/android/xposed/XC_MethodHook;)Ljava/util/Set;

    .line 150
    .line 151
    .line 152
    :goto_5
    add-int/lit8 v4, v4, 0x1

    .line 153
    .line 154
    goto :goto_4

    .line 155
    :catchall_2
    move-exception v1

    .line 156
    goto :goto_7

    .line 157
    :cond_4
    :goto_6
    const-string v1, "SettingsHook \u52a8\u6001\u76ee\u6807 adapter \u672a\u627e\u5230\uff0c\u8df3\u8fc7 Hook 3-3"

    .line 158
    .line 159
    invoke-static {v1}, Lhun/mesh/carwithenhance/utils/XLog;->e(Ljava/lang/String;)V
    :try_end_2
    .catchall {:try_start_2 .. :try_end_2} :catchall_2

    .line 160
    .line 161
    .line 162
    goto :goto_8

    .line 163
    :goto_7
    const-string v2, "\u274c Hook 3-3 (\u8f66\u673a\u7aefUI\u70b9\u51fb\u63a5\u7ba1) \u5931\u8d25: "

    .line 164
    .line 165
    invoke-static {v2, v1}, Lhun/mesh/carwithenhance/utils/XLog;->e(Ljava/lang/String;Ljava/lang/Throwable;)V

    .line 166
    .line 167
    .line 168
    :cond_5
    :goto_8
    :try_start_3
    sget-object v1, Lhun/mesh/carwithenhance/dexkit/CarWithDexKitManager;->INSTANCE:Lhun/mesh/carwithenhance/dexkit/CarWithDexKitManager;

    .line 169
    .line 170
    invoke-virtual {v1}, Lhun/mesh/carwithenhance/dexkit/CarWithDexKitManager;->getUcarSettingsFragmentClass()Ljava/lang/String;

    .line 171
    .line 172
    .line 173
    move-result-object v1

    .line 174
    if-eqz v1, :cond_7

    .line 175
    .line 176
    invoke-virtual {v1}, Ljava/lang/String;->isEmpty()Z

    .line 177
    .line 178
    .line 179
    move-result v2

    .line 180
    if-eqz v2, :cond_6

    .line 181
    .line 182
    goto :goto_9

    .line 183
    :cond_6
    invoke-static {v1, p1}, Lde/robv/android/xposed/XposedHelpers;->findClass(Ljava/lang/String;Ljava/lang/ClassLoader;)Ljava/lang/Class;

    .line 184
    .line 185
    .line 186
    move-result-object v1

    .line 187
    const-string v2, "onCreatePreferences"

    .line 188
    .line 189
    const-class v3, Landroid/os/Bundle;

    .line 190
    .line 191
    new-instance v4, Lhun/mesh/carwithenhance/hook/SettingsHook$4;

    .line 192
    .line 193
    invoke-direct {v4, p0, p1}, Lhun/mesh/carwithenhance/hook/SettingsHook$4;-><init>(Lhun/mesh/carwithenhance/hook/SettingsHook;Ljava/lang/ClassLoader;)V

    .line 194
    .line 195
    .line 196
    filled-new-array {v3, v0, v4}, [Ljava/lang/Object;

    .line 197
    .line 198
    .line 199
    move-result-object p1

    .line 200
    invoke-static {v1, v2, p1}, Lde/robv/android/xposed/XposedHelpers;->findAndHookMethod(Ljava/lang/Class;Ljava/lang/String;[Ljava/lang/Object;)Lde/robv/android/xposed/XC_MethodHook$Unhook;

    .line 201
    .line 202
    .line 203
    const-string p1, "onResume"

    .line 204
    .line 205
    new-instance v0, Lhun/mesh/carwithenhance/hook/SettingsHook$5;

    .line 206
    .line 207
    invoke-direct {v0, p0}, Lhun/mesh/carwithenhance/hook/SettingsHook$5;-><init>(Lhun/mesh/carwithenhance/hook/SettingsHook;)V

    .line 208
    .line 209
    .line 210
    filled-new-array {v0}, [Ljava/lang/Object;

    .line 211
    .line 212
    .line 213
    move-result-object p0

    .line 214
    invoke-static {v1, p1, p0}, Lde/robv/android/xposed/XposedHelpers;->findAndHookMethod(Ljava/lang/Class;Ljava/lang/String;[Ljava/lang/Object;)Lde/robv/android/xposed/XC_MethodHook$Unhook;

    .line 215
    .line 216
    .line 217
    goto :goto_a

    .line 218
    :cond_7
    :goto_9
    const-string p0, "SettingsHook \u52a8\u6001\u76ee\u6807 UCarScreenSettingsFragment \u672a\u627e\u5230\uff0c\u8df3\u8fc7 Hook 4"

    .line 219
    .line 220
    invoke-static {p0}, Lhun/mesh/carwithenhance/utils/XLog;->e(Ljava/lang/String;)V
    :try_end_3
    .catchall {:try_start_3 .. :try_end_3} :catchall_3

    .line 221
    .line 222
    .line 223
    goto :goto_a

    .line 224
    :catchall_3
    move-exception p0

    .line 225
    const-string p1, "\u274c Hook 4 (\u624b\u673a\u7aefUI\u754c\u9762\u52a8\u6001\u6ce8\u5165\u4e0e\u540c\u6b65) \u5931\u8d25: "

    .line 226
    .line 227
    invoke-static {p1, p0}, Lhun/mesh/carwithenhance/utils/XLog;->e(Ljava/lang/String;Ljava/lang/Throwable;)V

    .line 228
    .line 229
    .line 230
    :goto_a
    return-void
.end method
