# 删除媒体定制页 Hook 的短设计

## 意图

用户要求不要额外改媒体定制页，应用启动完全按官方逻辑执行；不是体验应用就走官方普通应用路径。

## 目标 / 非目标

- 目标：删除 `MediaCustomPageHook`、`MediaCustomPagePolicy`、对应测试以及旧 `QqMusicCustomPageHook`；`MainHook` 不再挂载任何媒体定制页 Hook。
- 非目标：不修改续播、体验应用合并排序、断连停止、QQ 音乐巅峰音乐榜保护、画质/帧率/分辨率配置。
- 失败条件：仍有 `y3.a#b` / `y3.c#d/e` 拦截或主动 `UCarFragmentManager.jumpTo`，或构建后其他 Hook 数量变化。

## 方案

唯一合理方案是删除 Hook 引用和文件后直接放行官方调用链；替代方案（继续拦截但只改体验应用）会继续偏离官方逻辑，不采用。

## 实施范围

- `MainHook.java`：移除 import 与数组项。
- 删除媒体定制页 Hook/Policy/Test 源文件和 2.3.8 APK。
- `README.md` 与历史 2.3.5 自检文档：移除当前行为描述中的定制页 Hook 文案。
- `build.gradle`：版本升至 `2.3.9-official-routing`。
- 构建、安装、重启验证；不主动推送远端。

## 验证

- `rg` 无 `MediaCustomPageHook` / `MediaCustomPagePolicy` / `QqMusicCustomPageHook` / `jumpTo` 自定义调用。
- `testDebugUnitTest assembleRelease` 通过。
- 手机安装后日志中无 `媒体定制页` / `QQ定制页` Hook 挂载，其他 Hook 仍全部挂载。
