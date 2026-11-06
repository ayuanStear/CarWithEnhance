# CarWith Enhance 2.3.5 自检记录

日期：2026-11-06

## 版本

- `versionCode`: 36
- `versionName`: 2.3.5-qq-compat
- Release APK：`release/CarWithEnhance-2.3.5-qq-compat.apk`
- SHA-256：`2A20D2F0399223652E7D7681F5C292C3EF9D51AC654FD9B26080C7847C51D436`
- 手机安装 APK 哈希与 Release APK 一致

## 开关职责

- `hook_autoplay_enabled` 恢复为「QQ音乐完美续播」，只控制 QQ 音乐的巅峰音乐榜队列拦截。
- 全应用上车续播只读取 CarWith 的 `prefer_auto_play`，即「设置 → 个性化 → 上车续播」。
- `prefer_auto_play` 默认值与官方一致为 `false`。
- 原配置键保持不变，升级不会丢失已有开关值。

## 兼容措施

- 优先使用 CarWithDexKit 动态解析 QQ 媒体列表回调类。
- DexKit 回退按多种历史/当前参数签名查询 `MediaAutoPlayHelper` 特征。
- 最后回退旧版类名 `pa.a$b`。
- Hook 不绑定单一方法名：扫描目标类内所有 `void` 且同时包含 `List`、`String` 参数的兼容回调。
- 播放器包名从回调对象及其 `this$0` 内部类字段动态读取。
- 全应用续播继续使用 `MediaAutoPlayHelper.g(String, boolean)` 动态解析。

## 构建与测试

- `testDebugUnitTest assembleRelease`：成功
- 4 个测试套件、16 项测试、0 failures、0 errors
- APK Signature Scheme v2：验证通过
- APK 中存在：`QQ音乐完美续播`、`巅峰音乐榜`、`QqAutoPlayPolicy`、`resolveQqPlaylistCallback`
- APK 中不存在旧菜单标题：`全应用上车续播`

## 实机 Hook 结果

- `QQ完美续播 Hook 注入成功，兼容回调数=1，目标类=pa.a$b`
- MEDIA_AUTO_PLAY -> `pa.a#g`
- MEDIA_APP_OPEN -> `e5.t#j`
- EXPERIENCE_TO_NORMAL -> `com.carwith.launcher.apps.AppsViewModel#q`
- 历史版本曾包含 QQ 定制页 StartAppHelper Hook；2.3.9 起已删除，当前按官方启动逻辑执行
- 5 个主 Hook 全部挂载完成
- 未出现 `ClassNotFoundException: o0/u0` 或本模块 Hook 挂载失败

## 待实车确认

1. 关闭「设置 → 个性化 → 上车续播」：重新连接后不应执行全应用续播，即使模块里的「QQ音乐完美续播」仍开启。
2. 开启官方上车续播、关闭「QQ音乐完美续播」：QQ 音乐恢复官方巅峰音乐榜行为。
3. 开启官方上车续播和「QQ音乐完美续播」：QQ 不应自动跳到巅峰音乐榜。

自检通过，等待实车确认后再提交并推送远端。