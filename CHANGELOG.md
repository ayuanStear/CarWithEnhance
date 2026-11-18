# Changelog

## 2.5.8 - 2026-11-18

- Fix cold-start resume for non-QQ players: the panel-render attribution now
  falls back to the package being awaited instead of the live media-manager
  package, which is empty while the player is still waking.
- Stop requiring `isShown()` on the dock/home music card before marking the
  panel rendered (map-first layouts keep the card attached but not shown).
- Log every panel render event and every unattributable render callback for
  easier cold-start diagnosis.

## 2.5.7 - 2026-11-18

- Fix disconnect pause/release logs always reporting 0 ms (elapsed time was
  measured from the same instant twice).
- Only run the disconnect stop flow on a real connected-to-disconnected
  transition instead of on every disconnect callback.

## 2.5.6 - 2026-11-18

- Fix lyrics showing only a single line: the lyric hook no longer overwrites
  CarWith's native full `LYRICS_WHOLE` with a shorter metadata lyric. Native
  full lyrics always win; the metadata fallback is only used when native
  lyrics are missing.

## 2.5.5 - 2026-11-18

- Restore panel-first cold-start resume: playback is dispatched only after the
  CarWith music card / media page has really rendered, never via a play-key
  force wake while the panel is still blank.
- Remove the play-before-panel force wake (bind-success play key and the 1.5 s
  no-session play key) that made audio start before the panel refreshed.
- Use a pause key only as a neutral wake for services that cannot be bound or
  started, so a dead player still gets a MediaSession without starting audio.

## 2.5.4 - 2026-11-17

- Reconcile the CarWith media panel after a timeout-based resume even if the
  panel was not rendered when playback started.
- Retry panel synchronization at 0.25, 0.6, 1.2, 2.5, and 5 seconds without
  issuing another resume request.
- Stop panel reconciliation when a render callback belonging to the current
  playback cycle is observed, avoiding stale panel timestamps from the
  previous track.
- Cancel reconciliation tasks on disconnect or connection-generation changes.
- Add a panel render-cycle policy test and verify the fixed panel on the car
  virtual display.

## 2.5.3 - 2026-11-17

- Detect rendered media panels through the current `MusicViewA` signature
  callbacks instead of relying only on legacy `BaseMusicView`/`CardMusicView`
  bind names.
- Require an attached, visible and sized media view before treating a panel as
  rendered, and deduplicate repeated render callbacks.
- Preserve the official CarWith resume request before applying the module's
  fallback wake path, reducing duplicate NetEase process starts.
- Add a 3-second cold-start grace window and post-grace fallback attempts at
  3.4 and 4.2 seconds so slower players such as Luna Music can still resume.
- Avoid automatic resume when the user manually opens a media app, while
  keeping custom media routing ahead of native pages.
- Remove obsolete resume state, high-frequency diagnostic logs, and dead
  panel-pause branches.

## 2.5.2 - 2026-11-17

- Hook the legacy `CardMusicView` and both `i`/`j` metadata callbacks, so a
  rendered home music card is detected on every CarWith card variant.
- Cold-start resume now keeps waiting instead of falling back to play after a
  short render timeout; a late panel render still triggers the resume.
- Native media-app launch guard now suppresses the native Activity even when
  the target MediaSession is not ready yet, prepares it, then opens the custom
  media page.
- Consume repeat media-app clicks while the custom media page is already active
  instead of letting the host fall through to its native branch.
- Added route/native-guard diagnostics for the remaining NetEase cases.

## 2.5.1 - 2026-11-16

- Resume playback only after CarWith has actually rendered the music card or
  media page, instead of starting audio as soon as a MediaSession appears.
- Prepare a dead player's MediaBrowserService silently before resuming; no
  media-play key is sent during cold start.
- Intercept native media-app launches and route supported players back to the
  custom CarWith media page.
- Pre-bind NetEase Cloud Music's `UCarService` before opening its media page so
  the custom page no longer falls back to the native `PlayerActivity`.
- Added panel-ready policy tests for metadata and render timeouts.

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
