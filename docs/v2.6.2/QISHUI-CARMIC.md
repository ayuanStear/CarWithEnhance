> Historical implementation report. The current prebuilt APK in this repository is the v73 log-clean build.
> Older output APKs referenced below are historical artifacts and are not included in `releases/`.

# CarWithEnhance 2.6.2 Qishui + CarMic 安装报告

## 产物

- APK：`output/CarWithEnhance-2.6.2-qishui-carmic.apk`
- versionCode：69
- versionName：`2.6.2-qishui-media-key-fallback-carmic`
- SHA-256：`335a669f469d7a72bb1ce17184f1d624e89d152e8cf3e039337c95c9775edc1e`
- 设备内 SHA-256：一致
- 安装时间：2026-11-26 20:08:27

## 车机麦克风实现

- 仅在 `com.baidu.carlife.xiaomi` 进程安装。
- Hook：`com.baidu.carlife.mixing.wrappers.SystemAudioCallbackWrapper#onAudioStateChange(int,int)`
- `ch=3, state=1`：发送 `0x00010024`，请求车机麦克风。
- `ch=3, state=0`：发送 `0x00010023`，释放车机麦克风。
- 发送入口：`com.baidu.carlife.sdk.CarlifeCoreSDK#getInstance()` → `sendEmptyCMDToHu(int)`。

## 真机验证

- LSPosed 管理器显示 `com.baidu.carlife.xiaomi` 已勾选。
- 强制重启 CarLife 后日志确认：
  - `CarWithX CarMic: head-unit microphone hook installed`
  - LSPosed 框架在 `com.baidu.carlife.xiaomi` 进程加载模块。
- 通过 `am startservice` 直接启动 `VoiceService` 不会产生真实 CarLife VR 通道事件，因此尚未看到 `0x10024`。
- 真正的车机小爱唤醒时，应出现：
  - `sent head-unit microphone start command 0x10024`
  - 结束时 `sent head-unit microphone stop command 0x10023`

## 说明

之前连接车机仍使用手机麦克风，是因为已安装版本没有 `CarMicHook`，同时 CarLife 作用域未参与模块加载。现在两项均已补齐。
