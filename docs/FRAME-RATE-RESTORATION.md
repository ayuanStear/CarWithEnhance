# Frame-Rate Restoration

## Result

CarWith Enhance now leaves CarWith's frame-rate menu and conversion logic unchanged.

The stock CarWith mapping is restored:

```text
0 = 15 FPS
1 = 20 FPS
2 = 30 FPS
3 = 60 FPS
```

The current device still stores:

```text
user_setting_framerate=3
```

After removing the custom hooks, this value is interpreted directly by CarWith
as 60 FPS. Version 2.4.3 additionally corrects CarLife's integer-millisecond
frame scheduling without changing the native frame-rate menu.

## Removed code

- Custom `50FPS` JSON menu injection.
- Custom `50FPS` label replacement.
- New-host and legacy frame-rate conversion hooks.
- `readFpsIndex()`, `fiftyFpsLabel()`, `findFpsOptions()`, and `insertFiftyFpsItem()`.
- Related `Gson`, frame-rate helper, and preference constants.

## Device verification

- CarWith virtual display: `com.miui.carlink`
- Resolution: `1920x1080`
- Render frame rate: `60.0`
- Refresh-rate override: `60.0`
- Startup log contains no `50FPS` menu or frame-rate conversion activity.
- CarLife frame pacing resolves to `mode=PRECISE`.
- Encoder input Surface is set to a fixed 60 FPS source in the 60 FPS mode.

## Release

```text
versionCode: 46
versionName: 2.4.3-precise-60fps
```

APK SHA-256:

```text
5D97B3F77F03223909CA257A486DB0AADE09E9A324EC2DCDD427D9A1131B3CF5
```
