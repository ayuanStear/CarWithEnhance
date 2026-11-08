# Architecture and Implementation

## Goals

The refactor uses feature-based capability discovery and small pure-Java policy classes. CarWith obfuscated class names remain compatibility fallbacks, not permanent interfaces.

## Media panel

`MediaSnapshotPolicy` compares:

- package
- media ID
- title
- artist
- album
- duration
- cover identifier
- playback state
- playback speed
- playback actions

Playback position is intentionally excluded from panel identity. Position is used only by the lyric clock.

If CarWith briefly reports empty metadata, the previous song identity is retained while the current playback state is copied into the snapshot.

## Resume generation

`ResumeReadinessPolicy` prevents another resume when:

- the target is already playing; or
- the target was confirmed playing during the current connection; or
- another media application is playing.

## Custom media page

`MediaRoutePolicy` only permits route correction when:

- a media application start call is active;
- the requested package is the current media package;
- the media session and metadata are ready;
- the package is custom-page capable;
- the user has not selected the native QQ Music page.

## Lyric clock

`LyricProgressHook` hooks structural `setCurrentProgress(long)` implementations. It scans the media view instance for a lyric control exposing `setCurrentLryByTime(long)` and forwards the same playback position on the UI thread.

## Switch-player process cleanup

`MediaPlaybackPolicy.shouldKillPreviousAfterSwitch()` requires:

- the menu option is enabled;
- the new player is in `STATE_PLAYING`;
- the previous and current package names are valid and different.

When enabled, the old player is gracefully stopped first. If its media session remains active, it is force-stopped after the grace period because the user explicitly enabled the option.

When disabled, the old process is retained and automatic switch-back is suppressed.

## Capability discovery

`CarWithMediaCapabilities` and `StructuralMethodLookup` use DexKit signatures, return types, parameter counts, method names, and stable log strings to locate host behavior. Capability resolution is cached by the host version and module build time.

Missing or ambiguous capabilities degrade to the host's official behavior instead of hooking a random candidate.
