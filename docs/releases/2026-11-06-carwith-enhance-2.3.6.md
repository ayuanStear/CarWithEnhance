# CarWith Enhance 2.3.6 运行期清理自检

日期：2026-11-06

## 版本与产物

- `versionCode`: 37
- `versionName`: 2.3.6-runtime-cleanup
- Release APK：`release/CarWithEnhance-2.3.6-runtime-cleanup.apk`
- SHA-256：`487C237D3CA5ED7B3D1AA4C628906752DE0503DC7170EA549ABA56CF032D1786`
- 手机安装 APK 与 Release APK 哈希一致

## 修复内容

- 删除 `WakeReceiver` 中不可靠的 `getPackageInfo` 检查，不再产生“包查询失败”日志。
- Manifest 增加 `MediaBrowserService` 查询和常用音乐包可见性，兼容 Android 11+ package visibility。
- 媒体服务绑定优先由 CarWith 主进程执行，避免模块广播进程重复绑定失败。
- 增加续播任务集合；续播成功、目标变化、开关关闭、用户终止或断开连接时，直接移除所有未执行的 Handler 任务。
- 删除旧轮询结束日志，避免无效后台唤醒和日志刷屏。

## 构建与测试

- `testDebugUnitTest assembleRelease`：成功
- 4 个测试套件、16 项测试、0 failures、0 errors
- APK Signature Scheme v2：验证通过

## 实机回归

- 安装后 CarWith 冷启动成功，5 个主 Hook 全部挂载。
- `QQ完美续播 Hook 注入成功，兼容回调数=1，目标类=pa.a$b`
- 车机连接后自动续播网易云，MediaSession 最终为 `PLAYING(3)`。
- 日志中未再出现：
  - `WakeReceiver 包查询失败`
  - `后台连接失败`
  - `旧轮询已结束`
- 日志中未出现本模块 Hook 注入失败、ClassNotFound、NoSuchMethod、ANR 或 Java 崩溃。

## 结果

2.3.6 运行期告警和无效轮询已清理，实机续播正常。