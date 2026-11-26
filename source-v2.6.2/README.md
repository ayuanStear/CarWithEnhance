# CarWithEnhance v2.6.2 Source Snapshot

This directory contains the helper and patch source used to build the v2.6.2 CarWithEnhance variant. It is a patch-source snapshot for a user-supplied CarWith base APK; it is not a redistribution of the proprietary base application.

## Included changes

- Native auto-play target rewrite through `pa.a#g`, with no custom retry timer.
- One explicit stopped-package media-key fallback for Qishui (`com.luna.music`).
- NetEase root-tab memory disabled for `com.netease.cloudmusic`.
- CarLife head-unit microphone commands `0x10024` / `0x10023` on the XiaoAi VR channel.
- `RuntimeLogPolicy` filtering for routine registration, polling, media-tab, audio-state, and casting-mask log noise.
- Error paths (`XLog.e` and `AutoPlayAllHook.logErr`) remain unfiltered.

## Layout

```text
sources/carwake-dexkit/   Java helper and resolver sources
stubs/dexkit-api/         Compile-only DexKit/Android stubs
scripts/                  Reproducible build and policy tests
samples/                  Place the base APK here (not committed)
tools/                    Place required jars here (not committed)
keys/                     Provide the signing key externally (not committed)
```

## Build

1. Install JDK 17 and Android SDK 36 with build-tools containing `d8`, `zipalign`, and `apksigner`.
2. Put the base APK at `samples/CarWithEnhance-2.6.1-last-app.apk`.
3. Put `apktool_2.12.1.jar` and `xposed-api-82.jar` in `tools/`.
4. Set `CARWITHX_KEYSTORE`, `CARWITHX_KEY_ALIAS`, and `CARWITHX_KEY_PASSWORD`.
5. Run:

```powershell
python scripts/build_261_resume_lite.py
python scripts/test_261_resume_lite.py -v
```

The build script patches only the decompiled base APK and emits a signed APK under `output/`. The expected source identity for this snapshot is:

```text
versionCode: 73
versionName: 2.6.2-qishui-media-key-fallback-carmic-tabfix-logclean-r3
SHA-256: 544a3962cd498d20bd170c489a7d70470bfa9db105f569ca7b51654ea01003f9
```

## Focused tests

```powershell
python scripts/test_native_resume_policy.py -v
python scripts/test_qishui_media_key_fallback.py -v
python scripts/test_runtime_log_policy.py -v
```

## Not included

- CarWith/CarLife base APKs, decompiled third-party trees, or native libraries.
- Signing keystores, passwords, device logs, reports, or personal configuration.
- The prior 3/5/8-second retry and `ResumeGate` implementation; they were intentionally removed from this variant.
