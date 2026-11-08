# CarWith Enhance

CarWith Enhance is an Xposed/LSPosed module for Xiaomi CarWith. It focuses on media panel reliability, resume coordination, custom media pages, lyric timing, disconnect behavior, and user-controlled player switching.

Current release:

```text
versionCode: 49
versionName: 2.4.6-auto-precise-60fps
package: hun.mesh.carwithenhance
```

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

## Prebuilt APK

The prebuilt release is available at:

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
