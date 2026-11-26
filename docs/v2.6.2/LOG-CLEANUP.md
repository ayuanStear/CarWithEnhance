# CarWithEnhance 2.6.2 日志清理报告

## 目标

去掉高频、重复、无诊断价值的日志，保留连接状态、续播结果、媒体路由结果、关键错误和车机麦克风命令。

## 实现

- 新增 `RuntimeLogPolicy`，只过滤信息日志，不触碰错误日志。
- `AutoPlayAllHook.log()`：过滤体验应用列表轮询、钩子注册、媒体根页签记录/恢复、媒体路由重复请求、车机界面状态刷新、音频状态轮询等噪声。
- `XLog.i()`：过滤 `mask ucar_casting_state=`、`hook installed` 和启动阶段目标解析噪声。
- `XLog.e()`、`AutoPlayAllHook.logErr()` 保持原样。
- `CarMicHook` 的 `0x10024/0x10023` 车机麦克风命令日志未改动。
- 保留：
  - `CarWith 已连接`
  - `断开处理`
  - `已跳转定制媒体页`
  - `媒体会话准备超时`
  - `媒体路由保持官方结果`
  - `CarWithX AllAppResume: installed native target rewrite`
  - `[通用Hook] 已解析 MEDIA_AUTO_PLAY`
  - 汽水 fallback 实际执行日志
  - 所有失败、异常、权限和反射错误

## 构建产物

- APK：`releases/CarWithEnhance-2.6.2-qishui-carmic-tabfix-logclean.apk`
- versionCode：73
- versionName：`2.6.2-qishui-media-key-fallback-carmic-tabfix-logclean-r3`
- SHA-256：`544a3962cd498d20bd170c489a7d70470bfa9db105f569ca7b51654ea01003f9`
- 大小：668736 bytes

## 验证

- `python source-v2.6.2/scripts/test_261_resume_lite.py -v`：16/16 PASS。
- 结构测试确认：
  - `RuntimeLogPolicy` 已进入 APK。
  - `AutoPlayAllHook.log` 和 `XLog.i` 使用 `if-nez` 正确分支：策略返回 false 时直接返回。
  - `XLog.e(String)`、`XLog.e(String, Throwable)` 和 `logErr` 未被过滤。
  - 车机麦克风 Hook 仍存在且 MainHook 仍加载。
- 设备：Xiaomi 17 Pro。
- 设备内 versionCode=73，versionName=`2.6.2-qishui-media-key-fallback-carmic-tabfix-logclean-r3`，APK SHA-256 与本地一致。
- 已重启 `com.miui.carlink` 和 `com.miui.voicetrigger` 进程加载新代码。
- `wake.log` 中最新保留的关键日志为 `CarWith 已连接`；重启后未再新增 `体验应用...`、`已有钩子已注册`、`媒体根页签...`、`音频状态...` 等例行噪声。
- logcat 中仅保留目标解析 `MEDIA_AUTO_PLAY`、`installed native target rewrite` 等少量必要启动诊断；新 voicetrigger 进程启动后未再出现 `mask ucar_casting_state=` 噪声。

## 备注

- 本次只清理日志，没有重新引入 3/5/8 秒重试、ResumeGate 或自定义 MediaBrowser 播放逻辑。
- 网易云页签保护、上车续播、汽水 media-key fallback 和车机麦克风 Hook 均保持原逻辑。
