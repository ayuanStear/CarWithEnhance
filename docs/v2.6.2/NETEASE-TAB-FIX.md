> Historical implementation report. The current prebuilt APK in this repository is the v73 log-clean build.
> Older output APKs referenced below are historical artifacts and are not included in `releases/`.

# CarWithEnhance 2.6.2 Tabfix 报告

## 问题定位

- 现象：网易云手动切歌后，CarWith 从定制媒体页进入原生播放页。
- 日志证据：
  - `媒体根页签已记录: com.netease.cloudmusic index=3/4`
  - `媒体根页签已恢复: com.netease.cloudmusic index=3/4 (原值=0)`
- 结论：`MediaTabSelectionHook` 在切歌后恢复了网易云的 index 3 根页签，该页签对应原生播放页；不是媒体路由失败。

## 修复

- 只对 `com.netease.cloudmusic` 禁用根页签记录与恢复。
- 不修改其他媒体 App 的页签记忆。
- 保留 Qishui fallback、原生续播和 CarLife 车机麦克风 Hook。

## 产物

- APK：`output/CarWithEnhance-2.6.2-qishui-carmic-tabfix.apk`
- versionCode：70
- versionName：`2.6.2-qishui-media-key-fallback-carmic-tabfix`
- SHA-256：`19ed2a59458fabbb659f78753274f50b69d9abc61d54866f222e3a77946cba97`
- 设备内版本和 SHA-256：一致
- 结构测试：15/15 PASS
- v1/v2/v3 签名验证：通过

## 早期安装状态

- 已安装到 Xiaomi 17 Pro。
- CarWith 新进程已加载 versionCode 70。
- 后续已完成真实车机连接后的“网易云手动切歌”回归，结果见下文。

## 安装后回归

- 设备内 versionCode：70。
- 设备内 versionName：`2.6.2-qishui-media-key-fallback-carmic-tabfix`。
- 设备内 SHA-256：`19ed2a59458fabbb659f78753274f50b69d9abc61d54866f222e3a77946cba97`，与构建产物一致。
- 通过 ADB 发送 `KEYCODE_MEDIA_NEXT` 后，日志中只出现 `媒体路由请求: com.netease.cloudmusic`，没有再出现新的 `媒体根页签已恢复 index=3/4`。
- 旧日志中的 `index=3` 记录停在修复前 20:09:57；修复后 20:20:38 及之后的切歌测试未再触发页签恢复。

## 用户实测

- 2026-11-26 20:22:53 网易云发生切歌事件。
- 20:20:38 之后日志没有再出现 `媒体根页签已恢复`，修复前固定在 20:09:57 触发的 `index=3/4` 恢复已消失。
- 抓取当前 CarWith 虚拟屏，页面仍停留在网易云定制页“我喜欢的音乐”，未进入原生播放页。
- 结论：网易云切歌跳原生页问题在 versionCode 70 上已实际修复。
