# Changelog

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
