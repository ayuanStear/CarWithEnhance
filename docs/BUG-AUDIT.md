# CarWith Enhance 2.3.9-resume-cleanup 基线问题审计

> 本文记录重构前 v42 的缺陷证据。2.4.0 和 2.4.1 已分别处理媒体状态、续播、歌词、路由和切换播放器相关项。

## 结论摘要

本轮以手机当前安装的 `versionCode=42` APK、对应可构建源码和
2026-11-08 的 LSPosed/CarWith 日志为准。没有发现 CarWithEnhance 自身崩溃或 ANR，
但确认了多个会直接影响续播、断连、播放器切换和系统日志的行为缺陷。

| 优先级 | 问题 | 证据状态 |
|---|---|---|
| P1 | 一键清理 Hook 每次都因错误 Context/UID 写入失败，抑制逻辑 100% 未生效 | 新日志确认，16 次 |
| P1 | 连接后约 12 秒，在音乐已经播放时再次下发续播 | 新日志确认，至少 4 次 |
| P1 | 断连延迟任务可能停止多个音乐进程，不只当前播放器 | 新日志确认 |
| P2 | 面板同步被高频重复调用，一天内当前日志已出现 288 次 | 新日志确认 |
| P2 | 切换播放器时，在旧播放器音频仍活动的情况下强制结束进程 | 新日志确认 |
| P2 | 重试仍一次性排入 15 个任务；600 ms 去重不足以完全避免重复 | 源码确认，日志有旁证 |
| P2 | `WakeReceiver` 对外导出且没有调用方、权限或包白名单校验 | 源码确认 |
| P2 | 断连停止 IPC 非事务化，快速重连也无法取消 250 ms 后杀 CarWith | 源码确认 |
| P3 | `AutoPlayAllHook` 仍是约 2792 行单体类，状态和线程边界复杂 | 源码确认 |
| P3 | MediaSession 回调只注册不移除 | 源码确认 |
| P3 | LSPosed 声明 QQ 作用域，但 `MainHook` 不处理 QQ 进程，相关 Hook 为死代码 | 源码确认 |

## P1-1 一键清理抑制功能实际失效

### 现象

最新 LSPosed modules 日志中，同一错误出现 16 次：

```text
>> [一键清理] 记录清理时间失败
java.lang.SecurityException: Package android does not belong to 10152
at hun.mesh.carwithenhance.hook.SystemServerHook$1.afterHookedMethod(
    SystemServerHook.java:78)
```

时间覆盖 13:57、14:20、15:12、15:43、16:16、17:12、17:42、17:44、
19:18、19:32、19:46、19:57、20:36、20:50、20:58。

最关键的一组时间线：

```text
20:58:21  OneKeyClean 时间写入失败
20:59:04  CarWith 已连接
20:59:08  网易云被自动续播
```

这直接证明 v42 的“清理后下一次上车不自动续播”功能没有生效。

### 根因

`SystemServerHook.getSystemContext()` 优先使用：

```java
AndroidAppHelper.currentApplication();
```

在 `reportOneKeyCleanEvent()` 回调中，它可能返回错误的 Application Context，
导致 `Settings.Global` 写入被 SettingsProvider 按错误 UID/包归属拒绝。
错误后被捕获并只写日志，没有改用 system context 重试。

### 用户影响

- 一键清理杀掉音乐后，下一次 CarWith 连接仍会自动拉起并播放。
- 用户会认为“清理”与“上车续播”两个功能互相打架。
- v42 Release 说明中的核心修复实际上不可用。

### 重构方向

- 优先从 `ActivityThread.currentActivityThread().getSystemContext()` 获取
  system context。
- 使用明确的 system user/UID 执行 `Settings.Global` 写入。
- 将写入结果和异常类型结构化返回，而不是只打印完整堆栈。
- 增加真实触发一键清理的系统级测试，不能只用纯策略单测。

## P1-2 音乐已经播放后仍再次下发续播

### 现象

同一现象在多次连接中稳定复现，时间都约为连接后 12 秒：

```text
17:29:18 连接
17:29:21 已下发续播，播放器进入播放
17:29:30 体验应用已放到普通列表末尾
17:29:30 网易云已有媒体会话，交给 CarWith
17:29:30 已下发续播（距开始 12209ms）

20:59:04 连接
20:59:08 已播放
20:59:16 再次下发续播（距开始 12046ms）

21:14:01 连接
21:14:04 已播放
21:14:13 再次下发续播（距开始 12366ms）
```

### 代码根因

1. `AutoPlayAllHook.java:1350-1351` 在每次连接时无条件安排 4 秒和 12 秒
   的体验应用重排。
2. 12 秒任务执行后，CarWith 再次触发媒体打开/状态刷新。
3. `handle()` 在 2208-2213 行只检查“进程存在 + 有 MediaSession”，
   没有检查目标是否已经 `STATE_PLAYING`，便再次调用 `resumeCarWith()`。
4. 600 ms 去重窗口远小于 12 秒，无法拦截这次重复。

### 用户影响

- 音乐可能在已经播放约 12 秒后收到第二条续播命令。
- 可能打断队列、重置控制面板、重复拉取封面/歌词/进度。
- CarWith 应用列表重排与媒体控制耦合，放大了 UI 和播放器抖动。

### 重构方向

- 删除连接后 4 秒、12 秒的重复体验应用重排；只在应用列表构建前执行。
- `handle()` 在任何 `resumeCarWith()` 前统一检查 `isPlaying()`。
- 引入连接级幂等键：同一目标一旦进入 `STATE_PLAYING`，本次连接不再下发续播。

## P1-3 断连可能停止多个音乐进程

### 现象

2026-11-08 21:20:57 断开连接，30 秒后 system_server 连续停止两个应用：

```text
21:21:27.551 已停止音乐进程: com.luna.music
21:21:27.599 已停止音乐进程: com.netease.cloudmusic
```

### 代码根因

`collectDisconnectTargets()` 是以下集合的并集：

- 本次连接出现过的 `SESSION_MEDIA`
- 被模块唤醒过的 `WOKE`
- 最后接受、最后唤醒、当前选择的应用
- CarWith 当前/上次媒体应用
- 当前 MediaSession 中仍处于播放状态的应用

只要延迟执行时某个目标已不在播放，SystemServerHook 就会 force-stop 它。
因此“关闭当前音乐”可能演变为结束本次连接涉及的所有音乐应用。

### 用户影响

- 用户切换过的上一个播放器也会被关闭。
- 下次连接只能冷启动，进一步放大续播等待。
- 其他本可保留的后台音乐状态被破坏。

### 重构方向

- 明确产品语义：只停止“当前播放器”，还是停止“本次连接所有播放器”。
- 若只停止当前播放器，应在断连时冻结一个唯一目标，而不是延迟后再次展开集合。
- 记录每个目标进入集合的原因，并对旧会话、用户手动切换和不可唤醒目标分别处理。

## P2-1 控制面板同步过于频繁

### 现象

2026-11-08 的 CarWith `wake.log` 中：

- `播放器已播放，控制面板同步` 共 288 次
- 其中网易云 233 次
- 20:59 到 21:05 的单个会话持续每 1 至 5 秒触发一次

### 根因

- `onMediaActuallyPlaying()` 在目标已经播放时仍调用
  `refreshCarWithMediaState()`。
- `onMediaMetadataChanged()` 在播放中也会调用同一刷新。
- 刷新函数只在同一包名 150 ms 内做去重。
- PlaybackState 的进度更新、元数据更新都会进入完整反射链路。

### 用户影响

- `ma.a`、`pa.b`、媒体控制器和 CarWith UI 被持续重复通知。
- 增加主线程工作量、日志量和功耗，可能表现为控制面板抖动或按键延迟。

### 重构方向

- PlaybackState 仅在状态位变化时同步，忽略单纯 position/updateTime 变化。
- Metadata 合并节流，例如 500 ms 至 1 s。
- 每次连接记录最后同步的快照，内容未变则不刷新。

## P2-2 切换播放器时旧音频仍活动便强制结束

### 现象

11-08 的多次播放器切换都出现：

```text
结束进程前先请播放器释放音轨
结束进程前音频已交接:
  (等了 2823~2864ms, 音频输出仍活动, 媒体会话仍在)
已结束播放器
```

涉及 Luna ↔ 网易云连续切换。

### 用户影响

- 新播放器可能尚未完全接管音轨，旧进程即被结束。
- 可能出现瞬间无声、音频重复、播放器控制状态不一致。

### 重构方向

- 将“音频已静默”或“新播放器稳定播放”作为结束旧进程的强前置条件。
- 超时后返回失败并保留旧进程，不要在音频仍活动时继续 force-stop。

## P2-3 重试任务仍一次性预排

`RETRY_DELAYS` 包含 15 个绝对时间点，`startRetryLoop()` 一次循环全部调度。
v42 的就绪门控减少了播放器未启动时的无效续播，但 MediaSession 一旦出现，
仍可能有多个任务在 600 ms 去重窗口之外重复调用 `resumeCarWith()`。

当前最明显的 12 秒重复来自体验应用重排后的 `handle()`，但根架构仍是多个并行定时器。

建议改为单一状态机，任何时刻最多只有一个待执行任务。

## P2-4 WakeReceiver 暴露面缺少认证

- Manifest：`android:exported="true"`，没有 receiver permission。
- Receiver 只检查包名非空且不含冒号。
- 未检查调用方 UID。
- 未检查目标是否为已安装媒体白名单。
- 当前 v42 源码没有主动发送该广播的调用方，按现状应优先考虑移除。

任意本机应用都可尝试拉起模块进程、发送媒体键或绑定媒体服务。

## P2-5 断连停止命令不是事务

`CarLinkStateHook.publishDisconnectStopCommand()` 分两次写目标和执行时间，
异常被吞掉，`handleDisconnect()` 随后仍无条件在 250 ms 后杀死 CarWith。

如果写入失败，用户会看到“断连关闭音乐”开启，但音乐没有被安排停止。

同时，250 ms kill Runnable 没有保存引用：

```java
mHandler.postDelayed(new Runnable() { ... killProcess(...); }, 250L);
```

连接回调不会移除它，快速闪断重连仍可能杀死刚恢复的 CarWith。

## P3-1 媒体会话回调没有解绑

`SESSION_CALLBACKS` 对每个新 Token 执行 `registerCallback()`，但整个版本没有
`unregisterCallback()` 或清理消失会话的逻辑。长时间运行和播放器反复重启会积累
失效 Callback。

## P3-2 单体类与死代码

`AutoPlayAllHook.java` 约 2792 行，包含 Hook 注册、进程唤醒、媒体会话、
续播、播放器切换、体验应用、断连目标、日志和 UI 同步。

另外：

- `lsposed_scopes` 包含 `com.tencent.qqmusic`。
- `MainHook` 只处理 `android` 和 `com.miui.carlink`。
- `QQMusicDexKitManager` 没有业务调用入口。
- `CarWithIconHook` 没有被 `MainHook` 挂载。

这些配置会误导作用域和兼容性维护。

## 历史问题，不应混入当前结论

以下已在更早版本处理或不适用于 v42：

- 2.4.7 才引入的 Launcher Activity 启动，v42 不含该逻辑。
- 旧版 `ClassNotFoundException: o0`。
- 旧版 LSPosed 开机 APK 路径不可见。
- 旧版 WakeReceiver 包查询失败日志，v42 已移除该查询。

## 建议的重构边界

1. `ResumeCoordinator`：单一状态机、单任务调度、连接级幂等。
2. `ExperienceAppController`：与媒体续播完全解耦。
3. `PlaybackSwitchCoordinator`：音频交接守卫和旧进程清理。
4. `DisconnectCoordinator`：命令事务、唯一目标、重连取消。
5. `OneKeyCleanBridge`：正确 system context、可验证写入结果。
6. `MediaSessionTracker`：Session 注册和注销成对管理。
7. `SettingsRepository`：SP、Settings.Global 和 UI 单向同步。
8. `Diagnostics`：状态变化日志，避免播放进度驱动的重复记录。

## 下一轮实机验证

1. 触发一键清理，确认 `carwithenhance_one_key_clean_at` 成功写入。
2. 一键清理后立即上车，确认本次连接不自动续播；再连接一次应恢复正常。
3. 每次连接只出现一次有效续播；音乐已经播放后 12 秒不得再次下发。
4. 断开时只停止唯一当前音乐目标。
5. 快速断开/重连不触发 250 ms 延迟杀进程。
6. 播放器切换在新播放器稳定播放后再结束旧播放器。
