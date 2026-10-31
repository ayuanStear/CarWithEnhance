# CarWith Enhance

面向小米 CarWith 的 Xposed/LSPosed 功能增强模块。当前仓库提供可直接构建的 Java 源码，并保留早期 2.2.0 版本的 smali 补丁作为历史参考。

当前源码版本：

- `versionCode`: 34
- `versionName`: `2.3.3-skip-active`

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
