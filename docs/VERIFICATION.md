# Verification

## Automated checks

| Check | Result |
|---|---|
| Unit suites | 8 |
| Unit tests | 51 passed |
| Failures | 0 |
| Errors | 0 |
| `testDebugUnitTest` | passed |
| `assembleRelease` | passed |
| APK v2 signature | verified |
| Frame-rate restoration build | 2.4.2, versionCode 45 |

## Device validation

The 2.4.x build was validated on a connected Android 17 device with CarWith 4.0.23 and NetEase Cloud Music 9.6.22.

Observed behavior:

- Initial playing-state panel refresh reached the correct current track.
- Same-package track changes refreshed the panel without a package-name change.
- A lyric view started advancing from the media progress position.
- A second resume was skipped after the target was already playing.
- DexKit capability discovery remained ready after a process restart using cached targets.

## Switch-player toggle

The new switch-player behavior is covered by policy tests for:

- enabled switch and playing target: previous player eligible for cleanup;
- disabled switch: previous player preserved;
- target not yet playing: no cleanup.

The final real-car A-to-B player switch remains the hardware validation step for observing the force-stop log against the selected player.

## Release artifact

```text
releases/CarWithEnhance-2.4.2-restore-60fps.apk
```

SHA-256:

```text
16A233432E61249BA9542E4C453D903658AEA652241D9018F75FB81EA024E598
```
