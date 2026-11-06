# CarWith Enhance 2.3.9 自检记录

日期：2026-11-06

## 变更

- 删除 `MediaCustomPageHook`、`MediaCustomPagePolicy`、对应测试。
- 删除旧版 `QqMusicCustomPageHook`。
- `MainHook` 不再挂载任何媒体定制页 Hook。
- 应用启动分流恢复官方：
  - 非体验应用：`AppsRecyclerViewAdapter#E -> y3.a#b -> e5.t#j`
  - 体验应用：`y3.c#d/e`
- 续播、体验应用合并排序、断连停止、QQ音乐完美续播保护等功能保持不变。

## 版本与产物

- `versionCode`: 40
- `versionName`: 2.3.9-official-routing
- Release APK：`release/CarWithEnhance-2.3.9-official-routing.apk`
- SHA-256：`8705A3941F569FE0B320A8608807BB6A2EFB54563BF1B0DC76C9EFD486849F77`

## 构建与测试

- `testDebugUnitTest assembleRelease`：成功
- 4 个测试套件、16 项测试、0 failures、0 errors
- APK Signature Scheme v2：验证通过

## 静态检查

- 源码中无媒体定制页 Hook、Policy、测试或 `MainHook` 引用。
- `MainHook` 仅挂载 `AutoPlayHook`、`AutoPlayAllHook`、`SettingsHook`、`CarLinkStateHook`。

## 实机验证

- 手机安装版本：`versionCode=40`，`versionName=2.3.9-official-routing`
- 手机安装 APK SHA-256 与 Release APK 一致。
- CarWith 冷启动后 4 个主模块全部挂载完成。
- 日志中无 `媒体定制页` / `QQ定制页` / `MediaCustomPage` / `QqMusicCustomPage` Hook。
- 续播、体验应用合并排序、QQ音乐巅峰音乐榜保护、断连停止等其他功能日志正常。