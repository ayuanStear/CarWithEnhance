# Precise 60 FPS Pacing

## Problem

CarLife 8.8.5 calculated its 60 FPS interval as:

```text
1000 / 60 = 16 milliseconds
```

That schedules approximately 62.5 FPS while the MediaCodec format still
declares 60 FPS. Perfetto showed the old path producing about 62-63 encoder
input frames per second and hardware codec output windows around 52-53 FPS.

## Implementation

The Xposed module now loads in the CarLife process and:

- Uses `FramePacerPolicy` to maintain absolute nanosecond frame deadlines.
- Converts the remaining deadline to 15/16/17ms Handler delays as needed.
- Skips missed periods rather than sending a burst of catch-up frames.
- Replaces only 60 FPS rendering delays; native 30/20/15 FPS paths are unchanged.
- Calls `Surface.setFrameRate(60, FRAME_RATE_COMPATIBILITY_FIXED_SOURCE)` on the
  encoder input Surface.
- Exposes `精确60FPS帧调度` in the CarWith Enhance settings menu, enabled by
  default.

The setting is stored under:

```text
hook_precise_60fps_enabled
```

Changing it takes effect after CarLife restarts or CarWith reconnects.

## Cross-Version Behavior

The resolver first uses the verified CarLife 8.8.5 classes:

```text
Recorder#changeFrameRate(int)
FrameRender$RenderHandler#renderDisplayMsg(long)
```

If the recorder class changes, DexKit finds a `void(int)` method containing the
literal `changeFrameRate frame===:`.

If the precise renderer cannot be resolved, 60 FPS mode uses a degraded 17ms
interval instead of continuing to overspeed the encoder. The startup log then
reports `DEGRADED_17MS`.

## Scope And Installation

Enable all of these in LSPosed:

```text
com.miui.carlink
android
com.baidu.carlife.xiaomi
```

After installing or updating, restart CarLife/CarWith and reconnect the vehicle.

Expected CarLife startup log:

```text
recorder=com.baidu.carlife.core.screen.video.Recorder
changeFrameRate=true
renderer=com.baidu.carlife.core.screen.video.FrameRender$RenderHandler#renderDisplayMsg
mode=PRECISE
```

The 60 FPS path also logs:

```text
编码 Surface 固定帧率=60
```

## Verification

The release unit suite covers long-term timing drift, missed frames, repeated
frame-rate updates, reset behavior, invalid frame rates, and Surface/API gates.

For device validation, a diagnostic build temporarily held CarLife at 60 FPS
during ten seconds of continuous swipe. Perfetto measured:

```text
per-second frames: 59-61
743 intervals
mean interval: 16.6674ms
standard deviation: 0.5678ms
maximum interval: 20.3551ms
```

That diagnostic-only forced-60 code was removed before building the release.
The release continues to follow CarLife's normal dynamic 30/60 FPS switching.

MediaCodec output improved from the old 52-53 FPS windows toward 55-60 FPS in
the same diagnostic run, although vehicle decoder compatibility still determines
whether 1080p60 is visibly smooth.

## Limitations

- `Surface.setFrameRate` is a scheduling hint; it cannot guarantee every vendor
  display will present every frame on time.
- A head unit that cannot decode 1080p60 smoothly may still need the official
  30 FPS setting.
- The module does not change resolution, bitrate, codec profile, or level.
