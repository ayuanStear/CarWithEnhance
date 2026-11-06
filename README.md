# CarWith Enhance

面向小米 CarWith 的 Xposed/LSPosed 功能增强模块。当前仓库提供可直接构建的 Java 源码，并保留早期 2.2.0 版本的 smali 补丁作为历史参考。

当前源码版本：

- `versionCode`: 40
- `versionName`: `2.3.9-official-routing`

## 当前断连行为

当「断开连接后关闭音乐」开关开启时：

1. CarWith 检测到连接断开；
2. 立即记录断连状态；
3. 约 250 ms 后使用 `Process.killProcess(Process.myPid())` 结束 CarWith 自身进程；
4. 模块在 system_server 中安排一次性 Handler 任务；
5. 到达配置的延迟秒数后重新检查音乐应用状态：
   - 处于前台：跳过；
   - MediaSession 为 `STATE_PLAYING`：跳过；
   - 其他情况：停止音乐目标；
6. 一次性任务执行后清空 `Settings.Global` 中的临时命令。

不会对 `com.miui.carlink` 调用 `forceStopPackage`。force-stop 会把包标记为 `stopped=true`，导致 CarWith 官方蓝牙静态接收器无法在下次连接时被系统唤醒。

## 官方蓝牙唤醒

CarWith 官方 APK 已包含：

- `com.miui.carlink.BluetoothReceiver`
- `com.miui.carlink.castfwk.wireless.bt.BluetoothBroadcastReceiver`

其中 `BluetoothBroadcastReceiver` 监听：

```text
miui.carlink.intent.action.bluetooth.connect.state.changed
```

收到 `ACL_CONNECTED` 后，由官方代码执行自动连接/驾驶模式流程。本模块不重复注册蓝牙 ACL 唤醒逻辑，只保证断开时使用 kill-only 方式结束进程，不破坏官方接收器。

## 配置键

- `hook_autoplay_enabled`：QQ音乐完美续播，只拦截 QQ 音乐的巅峰音乐榜队列，不控制其他播放器续播
- `hook_experience_to_normal_enabled`：体验应用并入普通列表并排到末尾
- `hook_disconnect_kill_music_enabled`：断连关闭音乐总开关
- `hook_disconnect_kill_delay_sec`：音乐延迟关闭秒数，范围 `0..300`
- 前台或播放中的音乐应用不会被停止
- `carwithenhance_disconnect_stop_at`：system_server 一次性任务时间戳
- `carwithenhance_disconnect_targets`：待停止的音乐包名列表

## 源码目录

```text
source/
├─ app/
│  ├─ libs/xposed-api-82.jar
│  └─ src/main/
│     ├─ AndroidManifest.xml
│     ├─ assets/
│     ├─ java/
│     ├─ jniLibs/arm64-v8a/libdexkit.so
│     └─ res/
├─ gradle/wrapper/
├─ build.gradle
├─ settings.gradle
├─ gradlew
└─ gradlew.bat
```

关键实现：

- `source/app/src/main/java/hun/mesh/carwithenhance/hook/CarLinkStateHook.java`
- `source/app/src/main/java/hun/mesh/carwithenhance/hook/SystemServerHook.java`
- `source/app/src/main/java/hun/mesh/carwithenhance/config/HookConfigs.java`

## 构建

需要 JDK 17、Android SDK 36。

```powershell
cd source
.\gradlew.bat testDebugUnitTest assembleRelease
```

Release APK：

```text
source/app/build/outputs/apk/release/app-release.apk
```

如本机没有配置 `ANDROID_HOME`，请自行创建不提交的 `source/local.properties`：

```properties
sdk.dir=C:/path/to/Android/Sdk
```

## 安装

```powershell
adb push source/app/build/outputs/apk/release/app-release.apk /data/local/tmp/CarWithEnhance.apk
adb shell su -c "pm install -r -d --user 0 /data/local/tmp/CarWithEnhance.apk"
```

安装后：

1. 在 LSPosed 中启用模块；
2. 作用域勾选 `com.miui.carlink` 和 `android`；
3. 重启手机，使 system_server Hook 生效。

## 历史 smali 补丁（2.2.0）

根目录 `smali/` 保留早期版本针对 CarWith Enhance 2.2.0 的 smali 修改，包括：

- 延迟关闭音乐；
- 延迟秒数设置项；
- 前台或播放中跳过关闭；
- 设置面板联动。

该目录仅作历史参考。当前推荐直接修改并构建 `source/` 下的 Java 工程。

## 说明

本项目用于个人学习与自用设备功能增强。原始 CarWith Enhance 由 `Mesh_Hun` 开发；本仓库在其反编译源码基础上进行可构建化整理和功能调整。

## 全应用续播与媒体切换（2026-11-06）

- LSPosed 作用域包含 `com.miui.carlink`、`android`（system_server）和 `com.tencent.qqmusic`，QQ 音乐作为推荐应用列出。
- 全应用上车续播只跟随 CarWith「设置 → 个性化 → 上车续播」按钮，即 `prefer_auto_play`；模块里的 `hook_autoplay_enabled` 不参与全应用续播控制。
- 模块菜单第一项恢复为「QQ音乐完美续播」，只阻止 QQ 音乐自动跳转到“巅峰音乐榜”队列；关闭后 QQ 音乐恢复官方行为。
- 优先恢复 `last_media_app` 记录的上次最后启动播放器，再回退到 CarWith 官方记录、当前媒体应用和 `prefer_media_app`。
- 官方 `MediaAutoPlayHelper.g(..., true)` 的上车续播目标会被校正到 `last_media_app`，不再优先播放旧的默认媒体应用。
- 播放中打开另一个音乐播放器时，只记录“最后启动”，不会自动播放，也不会提前替换左侧媒体图标；真正进入 `STATE_PLAYING` 后才更新媒体图标。
- 新播放器真正开始播放后，使用切换前记录的 `sPlayingPkg` 结束上一个播放器进程，不依赖已被暂停的旧会话再次被查询到。
- 体验应用在官方排序完成后稳定移动到普通应用列表末尾；高德/百度/腾讯/Google 地图及已安装的官方应用不会再被标记为体验应用，并会清理旧体验标记。

## 版本兼容策略（2026-11-06）

- 保留原配置键 `hook_autoplay_enabled`，升级后已有开关值继续生效，不需要清除数据。
- QQ 音乐回调优先使用 CarWithDexKit 动态结果，再使用 `MediaAutoPlayHelper` 字符串特征做多签名 DexKit 回退，最后尝试旧版 `pa.a$b` 类名。
- 不绑定单一方法名和参数表；同一目标类内会扫描所有 `void`、同时包含 `List` 与 `String` 参数的兼容回调。
- 目标播放器从回调对象及其内部类字段动态读取，兼容旧版和新版字段重命名。
- 全应用续播仍通过 `MediaAutoPlayHelper.g(String, boolean)` 动态解析，并保留 `last_media_app` 持久化数据。

## 运行期清理与兼容（2026-11-06）

- `WakeReceiver` 不再通过模块进程查询音乐应用包信息，消除错误的“包查询失败”日志。
- Manifest 增加 `MediaBrowserService` 查询声明，并为网易云、QQ 音乐、酷狗、咪咕、酷我、MIUI 音乐等保留显式包可见性。
- 媒体服务绑定改为优先在 CarWith 主进程完成，兼容仍使用旧版 WakeReceiver 广播的历史版本。
- 续播成功后直接移除所有尚未执行的 Handler 任务，不再出现“旧轮询已结束”刷屏，也避免无效后台唤醒。
- 续播目标切换、用户终止、断开连接和开关关闭时都会同步取消旧重试任务。

## 功能删除（2026-11-06）

- 已完整删除冷启动防误杀逻辑、system_server 进程保活逻辑、进程 adj 注入和受保护进程列表。
- 已删除仅受进程保活开关控制的 `ScreenOnHook` 及对应 DexKit 目标解析。
- 旧配置键 `hook_coldstart_antikill_enabled`、`hook_carwith_keepalive_enabled` 会在模块加载时从模块设置中清理，不再出现在设置界面。

Release APK：`release/CarWithEnhance-2.3.9-official-routing.apk`

SHA-256：`8705A3941F569FE0B320A8608807BB6A2EFB54563BF1B0DC76C9EFD486849F77`

自检报告：[docs/releases/2026-11-06-carwith-enhance-2.3.9.md](docs/releases/2026-11-06-carwith-enhance-2.3.9.md)

## 应用启动分流（2026-11-06）

- 已删除 `MediaCustomPageHook` / `MediaCustomPagePolicy` 及对应测试。
- 已删除旧版 `QqMusicCustomPageHook`。
- `MainHook` 不再挂载任何媒体定制页 Hook；模块不再拦截 `y3.a#b`、`y3.c#d/e`，也不主动调用 `UCarFragmentManager.jumpTo`。
- 非体验应用点击按官方 `AppsRecyclerViewAdapter#E -> y3.a#b -> e5.t#j` 执行；体验应用按官方 `y3.c#d/e` 执行。
- 续播、体验应用合并排序、断连停止、QQ音乐完美续播保护等功能保持不变。

