> Historical implementation report. The current prebuilt APK in this repository is the v73 log-clean build.
> Older output APKs referenced below are historical artifacts and are not included in `releases/`.

# CarWithEnhance 2.6.2 汽水音乐上车续播修复报告

## 结论

汽水没有续播不是包名改写失败，而是 CarWith 原生自动播放入口执行时，汽水进程已被停止且没有活动 `MediaSession`。CarWith 只在已有 MediaSession 时继续走 `AppMediaController`；它不会从 `PlayerService` 冷绑定并拉起汽水。

## 根因证据

- `18:07:41.295` 系统 `OneKeyClean` 强制结束汽水，`pid=17277` 被杀。
- `18:07:55.141` 模块 `DisconnectMusicKillHook` 的 15 秒清理再次对汽水执行 `forceStopPackage`，使其保持 Android stopped 状态。
- `18:07:58.319` CarWith 音频通道建立。
- `18:07:58.320` CarWith 正确自动播放 `com.luna.music`。
- 同一毫秒连续出现 `MediaServiceMgr: com.luna.music is not MediaSession`；到 `try connecting to a used app` 后没有出现 `bindService`、`UCarService` 或权限异常，说明原生死在“没有活动会话”，尚未进入绑定阶段。
- CarWith 清单没有申请 `com.luna.biz.playing.permission.MEDIA_BROWSE`；汽水 `PlayerService` 要求该权限，因此也不适合让 CarWith 直接补绑服务。
- 普通媒体键测试在 stopped 状态下失败：`PendingIntentHolder: sendAndReturnResult :-96`，汽水进程未启动。
- 显式广播携带 `FLAG_INCLUDE_STOPPED_PACKAGES` 后，汽水 `BachMediaButtonReceiver` 被成功拉起；再投递 `KEYCODE_MEDIA_PLAY` 后，汽水进入 `PLAYING`，MediaSession 建立。
- CarWith 在一个采样周期内捕获新会话并创建 `AppMediaController`：`AppMediaController: com.luna.music ... state=3`。

## 修复实现

- 新增 `QishuiMediaKeyFallbackPolicy`：只在目标为 `com.luna.music` 且没有活动 MediaSession 时选择兜底。
- 新增 `QishuiMediaKeyFallback`：
  - 不启动 Activity；
  - 不使用自建 MediaBrowser；
  - 不使用定时器或提前预唤醒；
  - 仅从 CarWith 原生 `pa.a.g(String, boolean)` 的 `beforeHookedMethod` 调用；
  - 显式向 `com.luna.music/com.luna.biz.playing.player.BachMediaButtonReceiver` 发送 `ACTION_DOWN`、`ACTION_UP` 的 `KEYCODE_MEDIA_PLAY`；
  - 使用 `FLAG_INCLUDE_STOPPED_PACKAGES | FLAG_RECEIVER_FOREGROUND`；
  - 10 秒去重，已有会话时完全不触发；
  - 原 CarWith 方法继续执行，不 `setResult`。
- `AllAppCarResume` 仅在 CarWith 自动续播开关开启时调用该兜底。

## 产物

- APK：`output/CarWithEnhance-2.6.2-qishui-fallback.apk`
- versionCode：68
- versionName：`2.6.2-qishui-media-key-fallback`
- SHA-256：`11aa7a5d43f8d003dee8dc301840b0f50d870c9a71c5c6b03fe1d61a3537f1b7`
- v67 归档：`output/archive\CarWithEnhance-2.6.1-resume-lite-v67.apk`
- v67 SHA-256：`54c7e67591b4e66f23ba4d09d77716b2f109f91e5c5589d7c6a1b3c4df7d5e48`

## 验证

- 测试先行：新增兜底测试首轮 0/3，通过后 3/3。
- APK 契约：13/13 PASS。
- 全量脚本测试：49/49 PASS。
- 签名：v1/v2/v3 验证通过（`apksigner --min-sdk-version 23`）。
- 真机安装：Success。
- 设备 versionCode：68。
- 设备 versionName：`2.6.2-qishui-media-key-fallback`。
- 设备 APK SHA-256 与本地产物一致。

## 真机端到端验证（2026-11-26 18:21）

本次 CarWith 新进程 `pid=11480` 已加载 v68。日志证据：

```text
18:21:21.868  CarMediaSessionHelper: The audio channel is established
18:21:21.872  CarWithX QishuiFallback: dispatched stopped-package media play to com.luna.music
18:21:21.872  MediaAutoPlayHelper: auto play com.luna.music
18:21:21.884  ActivityManager: Start proc 14148:com.luna.music for broadcast
              {com.luna.music/com.luna.biz.playing.player.BachMediaButtonReceiver}
              caller=com.miui.carlink
18:21:23.348  AppMediaController: com.luna.music, current media id: 7703024753767346559
18:21:23.362  AppMediaController: com.luna.music setMediaPlaybackState:
              state = 3， position = 598
18:21:38.300  CarWithX AllAppResume: recorded playing package com.luna.music
```

结果：

- 兜底广播发生在音频通道建立后 `4 ms`，没有提前出声。
- 汽水由 `BachMediaButtonReceiver` 广播冷启动，系统记录 `caller=com.miui.carlink`。
- 验证窗口内汽水 Activity 启动次数为 `0`，未显示手机端界面。
- 音频通道建立后 `1.494 s`，CarWith 已显示汽水控制器并确认 `PLAYING`。
- 当前 MediaSession：`package=com.luna.music`、`active=true`、`state=PLAYING(3)`。
- 汽水包状态：`stopped=false`。

证据目录：`local evidence (not uploaded)`
