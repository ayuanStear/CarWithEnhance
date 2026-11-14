# CarWith Enhance

面向小米 CarWith 的 Xposed / LSPosed 模块，用于改善媒体面板稳定性、上车续播、定制媒体页路由、语音唤醒与切换播放器体验。

## 版本

```text
versionCode: 53
versionName: 2.5.0
包名:        hun.mesh.carwithenhance
minSdk:      29
targetSdk:   36
```

## 功能

### 用户可开关（CarWith Enhance 设置菜单）

- **QQ音乐完美续播** — 阻止 QQ 音乐上车续播时自动跳到“巅峰音乐榜”，只保留当前播放状态。
- **体验应用专区转普通列表** — 把体验应用放到普通应用列表末尾，地图等官方应用不会被标记为体验应用。
- **切换播放器播放后结束上个进程** — 新播放器真正开始播放后结束旧播放器进程；关闭时保留旧进程。
- **断开连接后关闭音乐** — 断开后立即结束 CarWith，延迟指定秒数后关闭当前音乐播放器（前台或播放中则跳过）。

### 核心 Hook

- `AutoPlayHook` — QQ 音乐完美续播。
- `AutoPlayAllHook` — 全应用上车续播、媒体面板刷新、切换播放器后清理上个进程。
- `MediaRouteHook` — 官方路由短暂回退原生页面时，强制回到定制媒体页。
- `MediaTabSelectionHook` — 从媒体详情页返回时恢复之前选中的根页签（修复网易云“返回都回到精彩推荐”）。
- `SettingsHook` — 注入 CarWith Enhance 设置菜单。
- `CarLinkStateHook` — 连接状态跟踪，配合断连清理。
- `CarWakeVoiceHook` — CarWith 投屏时，语音唤醒转车机端小爱（移植自 CarWake v1.18），含语音交互期间的音频静音/恢复。
- `DisconnectMusicKillHook`（system_server）— 断开连接后延迟关闭音乐。

### 语音唤醒（CarWake）

投屏状态下，语音唤醒被重定向到车机端小爱（等同点击小爱图标）。语音交互期间静音媒体音频，结束后恢复：

- 音乐静音窗口：最长 5 秒
- 小爱 TTS 结束约 0.8 秒后恢复
- 10 秒看门狗兜底

## 架构

- **DexKit 结构定位** — `StructuralMethodLookup`、`CarWithMediaCapabilities`、`GenericAutoPlayHooks` 通过方法签名和字面量定位宿主类/方法，避免硬编码混淆名；每次定位在查询歧义时回退到稳定类名。
- **策略与 Hook 分离** — 纯 Java 策略类（媒体快照、续播就绪、路由、面板就绪、播放行为）保持可单测。
- **优雅降级** — 能力缺失或定位歧义时回退到 CarWith 官方行为。

## 环境要求

- JDK 17
- Android SDK 36
- Xposed API 82（`compileOnly`）
- Root + LSPosed

## 作用域

- `com.miui.carlink`
- `android`（system_server）
- `com.miui.voiceassist`
- `com.miui.voicetrigger`
- `com.baidu.carlife.xiaomi`
- `com.tencent.qqmusic`

## 构建

```bash
export ANDROID_HOME=/path/to/android-sdk
./gradlew assembleRelease
```

生成的 APK 位于 `app/build/outputs/apk/release/app-release.apk`。

## 发布

见 `releases/` 目录下的签名 APK 及 `SHA256SUMS.txt`。
