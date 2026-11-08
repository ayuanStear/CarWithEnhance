# Changelog

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
