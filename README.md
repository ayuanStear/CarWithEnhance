# CarWith Enhance

An Xposed / LSPosed module for Xiaomi CarWith. It improves media panel reliability, resume behavior, custom media page routing, voice wake, and player switching.

## Version

```text
versionCode: 53
versionName: 2.5.0
package:    hun.mesh.carwithenhance
minSdk:     29
targetSdk:  36
```

## Features

### User switches (CarWith Enhance settings)

- **QQ Music perfect resume** — prevents QQ Music from jumping to its ranking playlist when CarWith auto-resumes, keeping the current playing state.
- **Experience apps as a normal list** — moves experience apps to the end of the normal app list; official apps such as maps are not marked as experience apps.
- **Kill previous player after switch** — once a newly selected player actually starts playing, gracefully stop and force-stop the previous player process. When disabled, the previous process is kept.
- **Close music after disconnect** — ends CarWith immediately on disconnect and closes the current music player after a configurable delay, skipping when the app is foreground or still playing.

### Core hooks

- `AutoPlayHook` — QQ Music perfect resume.
- `AutoPlayAllHook` — all-app resume on car connect, media panel refresh, and previous-player cleanup after switching.
- `MediaRouteHook` — forces the custom media page when official routing transiently falls back to the native embedded page.
- `MediaTabSelectionHook` — restores the selected root tab after returning from a media detail page (fixes NetEase returning to `精彩推荐`).
- `SettingsHook` — injects the CarWith Enhance settings menu.
- `CarLinkStateHook` — connection-state tracking for disconnect cleanup.
- `CarWakeVoiceHook` — while CarWith is casting, redirects voice wake to the car Xiaomi assistant (ported from CarWake v1.18), including audio mute/restore during voice interaction.
- `DisconnectMusicKillHook` (system_server) — delayed music stop after disconnect.

### Voice wake (CarWake)

While CarWith is casting, a voice wake is redirected to the car Xiaomi assistant, equivalent to tapping the Xiaomi icon. During voice interaction, media audio is muted and then restored:

- music silence window: max 5 s
- restore about 0.8 s after the assistant TTS ends
- 10 s watchdog as a final fallback

## Architecture

- **DexKit structural lookup** — `StructuralMethodLookup`, `CarWithMediaCapabilities`, and `GenericAutoPlayHooks` locate host classes/methods by signature and string literal instead of hardcoding obfuscated names; every lookup falls back to a stable name when the structural query is ambiguous.
- **Policy / hook separation** — pure Java policy classes (media snapshot, resume readiness, route, panel readiness, playback) keep decisions unit-testable.
- **Graceful degradation** — missing or ambiguous capabilities fall back to official CarWith behavior.

## Requirements

- JDK 17
- Android SDK 36
- Xposed API 82 (`compileOnly`)
- Root + LSPosed

## Scope

- `com.miui.carlink`
- `android` (system_server)
- `com.miui.voiceassist`
- `com.miui.voicetrigger`
- `com.baidu.carlife.xiaomi`
- `com.tencent.qqmusic`

## Build

```bash
export ANDROID_HOME=/path/to/android-sdk
./gradlew assembleRelease
```

The release APK is written to `app/build/outputs/apk/release/app-release.apk`.

## Releases

See `releases/` for the signed APKs and `SHA256SUMS.txt`.
