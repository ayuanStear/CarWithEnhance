# CarWith Enhance

CarWith Enhance is an Xposed/LSPosed module for Xiaomi CarWith. It focuses on media panel reliability, resume coordination, custom media pages, lyric timing, disconnect behavior, and user-controlled player switching.

Legacy Gradle source release:

```text
versionCode: 49
versionName: 2.4.6-auto-precise-60fps
package: hun.mesh.carwithenhance
```

## Latest prebuilt APK (v2.6.2)

- APK: [`releases/CarWithEnhance-2.6.2-qishui-carmic-tabfix-logclean.apk`](releases/CarWithEnhance-2.6.2-qishui-carmic-tabfix-logclean.apk)
- versionCode: `73`
- versionName: `2.6.2-qishui-media-key-fallback-carmic-tabfix-logclean-r3`
- SHA-256: `544a3962cd498d20bd170c489a7d70470bfa9db105f569ca7b51654ea01003f9`
- Documentation: [`docs/v2.6.2/`](docs/v2.6.2/)

## Latest patch source snapshot (v2.6.2)

The helper and patch source used for the latest CarWithX v2.6.2 build is under
[`source-v2.6.2/`](source-v2.6.2/). It covers native auto-play target rewrite,
Qishui media-key fallback, CarLife head-unit microphone requests, NetEase tab
protection, and informational log cleanup. The proprietary CarWith base APK,
signing keys, logs, and device-specific outputs are intentionally excluded.

## Features

- Snapshot-based media panel refresh for initial resume and same-package track changes.
- Connection-scoped resume idempotence, preventing a player that is already playing from receiving another resume command.
- Request-scoped correction for transient custom media page routing failures.
- Restores the selected NetEase root tab after returning from a detail page.
- Progress-driven lyric clock for CarWith custom media card views.
- Menu switch: `切换播放器播放后结束上个进程`.
- Restores CarWith's original frame-rate menu and mapping: setting index `3` means 60 FPS.
- Automatically applies precise CarLife pacing whenever 60 FPS is selected.
- Uses nanosecond deadlines and fixed-rate encoder Surface hints without a user switch.
- QQ Music is listed as a recommended LSPosed scope.
- Safe MediaSession callback lifecycle and stale-controller cleanup.
- Disconnect cleanup that selects one current media target.
- Verified system-context write for the one-key-clean resume suppression.
- Structural DexKit capability lookup instead of relying only on one CarWith obfuscation revision.

## Switch-player behavior

The switch-player option is enabled by default.

When enabled, CarWith Enhance waits until the newly selected player reaches `PlaybackState.STATE_PLAYING`, then requests a graceful stop and force-stops the previous player if necessary.

When disabled, the previous player process is preserved and only automatic switch-back is suppressed.

## Requirements

- JDK 17
- Android SDK 36
- Android device with Xposed/LSPosed
- CarWith scope: `com.miui.carlink`
- system_server scope: `android`
- CarLife scope: `com.baidu.carlife.xiaomi`
- QQ Music scope: `com.tencent.qqmusic`

## Build

```powershell
.\gradlew.bat testDebugUnitTest assembleRelease
```

Release output:

```text
app/build/outputs/apk/release/app-release.apk
```

## Legacy prebuilt APK (v2.4.6)

The legacy Gradle release is available at:

```text
releases/CarWithEnhance-2.4.6-auto-precise-60fps.apk
```

SHA-256:

```text
54D9B2F40F9204406F8DA887D72E5AF23EECFDB2CA238176501CE7A8B8BC0399
```

## Installation

1. Install the APK with `adb install -r` or a file manager.
2. Enable the module in LSPosed.
3. Select `com.miui.carlink`, `android`, `com.baidu.carlife.xiaomi`, and
   `com.tencent.qqmusic` recommended scopes.
4. Restart CarLife/CarWith and reconnect the vehicle.

## Documentation

- [Bug audit](docs/BUG-AUDIT.md)
- [Media issue root-cause audit](docs/MEDIA-ISSUES-AUDIT.md)
- [Architecture and implementation](docs/IMPLEMENTATION.md)
- [Verification](docs/VERIFICATION.md)
- [Switch-player kill option](docs/SWITCH-PLAYER-KILL-TOGGLE.md)
- [Frame-rate restoration](docs/FRAME-RATE-RESTORATION.md)
- [Precise 60 FPS pacing](docs/PRECISE-60FPS-PACING.md)
- [NetEase root tab restore](docs/NETEASE-TAB-RESTORE.md)

## Repository scope

This repository contains only the original CarWith Enhance module source, project documentation, and the module APK. It does not distribute CarWith's proprietary APK, full decompiled third-party source, device logs, credentials, or personal configuration.
