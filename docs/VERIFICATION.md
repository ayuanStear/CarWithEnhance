# Verification

## Automated checks

| Check | Result |
|---|---|
| Unit suites | 11 |
| Unit tests | 66 passed |
| Failures | 0 |
| Errors | 0 |
| `testDebugUnitTest` | passed |
| `assembleRelease` | passed |
| APK v2 signature | verified |
| Release build | 2.4.4, versionCode 47 |

## Device validation

The 2.4.x build was validated on a connected Android 17 device with CarWith 4.0.23 and NetEase Cloud Music 9.6.22.

Observed behavior:

- Initial playing-state panel refresh reached the correct current track.
- Same-package track changes refreshed the panel without a package-name change.
- A lyric view started advancing from the media progress position.
- A second resume was skipped after the target was already playing.
- DexKit capability discovery remained ready after a process restart using cached targets.
- LSPosed loaded CarWith Enhance inside `com.baidu.carlife.xiaomi`.
- CarLife pacing resolved `mode=PRECISE`.
- The encoder input Surface logged a fixed 60 FPS rate in the 60 FPS mode.
- Disabling `hook_precise_60fps_enabled` removed all pacing hooks after restart.
- A diagnostic 60 FPS trace produced 59-61 frames per second, with a
  `16.6674ms` mean interval and `0.5678ms` standard deviation.
- LSPosed displays QQ Music (`com.tencent.qqmusic`) with the `推荐应用` label.

## Switch-player toggle

The new switch-player behavior is covered by policy tests for:

- enabled switch and playing target: previous player eligible for cleanup;
- disabled switch: previous player preserved;
- target not yet playing: no cleanup.

The final real-car A-to-B player switch remains the hardware validation step for observing the force-stop log against the selected player.

## Release artifact

```text
releases/CarWithEnhance-2.4.4-qqmusic-recommended.apk
```

SHA-256:

```text
D6EB8507ACA095CCB69E928EBF21936881E9F970F1584FB1DE4B2E28974DA069
```
