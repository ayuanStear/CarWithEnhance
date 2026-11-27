# CarWithEnhance 2.6.3 pre-wake fast resume

This variant keeps the 2.6.2 Qishui/CarMic/NetEase-tab fixes and restores the
2.6.1 in-module resume path that 2.6.2 had disabled.

## Changes

- `AutoPlayAllHook.preWakeLastUsedApp()` is restored. On `displayConnected` it
  prepares the last-used player's MediaBrowserService immediately instead of
  waiting for CarWith's native auto-play callback.
- `prepareMediaSession()` and `startRetryLoop()` are restored; the retry path
  resumes from MediaSession metadata (no panel-render dependency).
- `CarWithPanelReadyHook` stays removed, so the one-line-per-second panel-render
  diagnostic writes are gone. `markPanelRendered()` remains a no-op.
- `AllAppCarResume` arms its playing-package tracker after 2 seconds instead of
  15 seconds.
- When CarWith's late native auto-play runs while the saved player is already
  playing/buffering/connecting, or its process is already running within the
  first 20 seconds after `displayConnected`, `AllAppCarResume` suppresses that
  native call. This prevents the "start, stop, restart" sequence seen around
  08:08.
- `RuntimeLogPolicy` remains enabled; errors and key resume/connection logs are
  still written.

## Build

```powershell
$env:JAVA_HOME = 'D:\AppData\development\jdk17'
$env:ANDROID_SDK_ROOT = 'D:\Android\Sdk'
$env:CARWITHX_KEYSTORE = (Resolve-Path 'source-v2.6.2\keys\carwithx-release.jks').Path
$env:CARWITHX_KEY_ALIAS = 'carwithx'
$env:CARWITHX_KEY_PASSWORD = '<your-password>'
python source-v2.6.2\scripts\build_261_resume_lite.py
python source-v2.6.2\scripts\test_261_resume_lite.py -v
```

Output: `source-v2.6.2/output/CarWithEnhance-2.6.3-prewake-fast-resume.apk`

## Expected behavior

- If the player process is warm: resume starts roughly 1-3 seconds after
  CarWith reaches `displayConnected`.
- If the player process is cold: resume still depends on the player's service
  startup (typically 5-12 seconds for NetEase).
- The Bluetooth/CarLife handshake itself (about 11-24 seconds in the observed
  logs) is outside this module and is not shortened by these changes.

## Tests

The structural test verifies the pre-wake method is non-empty, the panel hook is
absent, `AllAppCarResume` contains the already-playing suppression, and the log
filter remains wired. The three policy tests verify target selection, Qishui
fallback, and log filtering.
