# Changelog

## 2.6.3 - 2026-11-27

- Restore the 2.6.1 pre-wake path: immediately prepare the last-used player
  after `displayConnected` and resume from MediaSession metadata instead of
  waiting for CarWith's native auto-play callback.
- Arm `AllAppCarResume` tracking after 2 seconds instead of 15 seconds.
- Suppress CarWith's late native auto-play when the saved player is already
  playing, buffering, or connecting, avoiding the start/stop/restart cycle.
- Keep the panel-render hook removed and retain the 2.6.2 Qishui, CarMic,
  NetEase-tab, and log-cleanup changes.

## 2.6.2 - 2026-11-26

- Reworked the resume path around CarWith's native `pa.a#g` auto-play target rewrite without custom 3/5/8-second retries.
- Added an explicit stopped-package media-key fallback for Qishui (`com.luna.music`).
- Disabled NetEase root-tab recording/restoration for `com.netease.cloudmusic`.
- Added CarLife head-unit microphone requests on the XiaoAi VR channel (`0x10024` / `0x10023`).
- Added `RuntimeLogPolicy` to remove routine registration, polling, media-tab, audio-state, and casting-mask noise while preserving errors and key events.
- Added source snapshot, release documentation, and v73 prebuilt APK under `source-v2.6.2/`, `docs/v2.6.2/`, and `releases/`.

## 2.4.6 - 2026-11-09

- Hidden the obsolete `精确60FPS帧调度` switch from CarWith Enhance settings.
- Automatic precise scheduling now activates whenever CarLife selects 60 FPS.
- The legacy `hook_precise_60fps_enabled` preference remains readable for
  compatibility but no longer controls whether pacing hooks are installed.
- Preserved the 30/20/15 FPS scheduling paths without precise overrides.

## 2.4.5 - 2026-11-09

- Preserved the selected NetEase Cloud Music root tab when returning from a
  secondary media list.
- Restored the active tab after the host view pager rebuilds its fragment
  adapter, avoiding the default return to `精彩推荐`.
- Added policy tests for valid, missing, stale, and empty tab selections.

## 2.4.4 - 2026-11-09

- Added QQ Music to LSPosed's recommended module scopes.

## 2.4.3 - 2026-11-09

- Added a CarLife process scope for precise frame scheduling.
- Replaced the 60 FPS integer-millisecond pacer with a nanosecond deadline policy.
- Added missed-frame handling that avoids catch-up bursts.
- Applied `Surface.setFrameRate(60, FIXED_SOURCE)` to the encoder input Surface.
- Added the `精确60FPS帧调度` switch, enabled by default.
- Added `DEGRADED_17MS` fallback when the precise renderer target cannot be resolved.
- Left the native 30/20/15 FPS scheduling paths unchanged.

## 2.4.2 - 2026-11-08

- Removed the injected 50 FPS menu option.
- Removed all custom frame-rate conversion hooks.
- Restored CarWith's native mapping: setting index `3` means 60 FPS.
- Revalidated the 1920x1080 car virtual display at a 60 Hz refresh configuration.

## 2.4.1 - 2026-11-08

- Added the `切换播放器播放后结束上个进程` menu switch.
- Added a `STATE_PLAYING` gate before ending the previous player.
- Force-stops the previous player when the switch is enabled and graceful release times out.
- Preserves the previous player when the switch is disabled.
- Added policy and configuration tests.

## 2.4.0 - 2026-11-08

- Replaced package-name-only panel deduplication with media snapshots.
- Added post-connection panel reconciliation.
- Added connection-scoped resume idempotence.
- Added request-scoped custom-page route correction.
- Added progress-driven lyric timing.
- Added MediaSession callback reconciliation and disconnect cleanup.
- Limited disconnect cleanup to one selected media target.
- Improved system-context handling for one-key-clean suppression.

## Earlier versions

Earlier CarWith Enhance revisions are represented by the source history and audit documents included in this repository.
