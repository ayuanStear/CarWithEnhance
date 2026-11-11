# Native Lyric Fallback Hardening Implementation Plan

> **For agentic workers:** REQUIRED SUB-SKILL: Use superpowers:executing-plans to implement this plan task-by-task.

**Goal:** Remove race conditions and dead code in `codex/native-lyric-fallback` while preserving native lyric fallback.

**Architecture:** Keep native CarWith media routing, add a pure `PanelReadyPolicy` for panel identity and timeout decisions, track panel wait per package, remove removed-panel-sync dead code, and make lyric source lookup structural.

**Tech Stack:** Java 8, JUnit 4, Xposed/LSPosed, Android Gradle Plugin.

**Spec:** review findings from commit `4439a0d`; this plan implements all confirmed items.

## Global Constraints

- Java source compatibility remains 8.
- Existing package/class naming and Android LSPosed scopes unchanged.
- No external lyric provider is introduced in this pass.
- All tests must run via `gradlew testDebugUnitTest assembleRelease`.

## Review Focus

- New waiting target must not inherit another package's timeout.
- Cold-start resume must wait for panel identity.
- Empty/stale metadata must not count as panel identity.
- Lyric source lookup must degrade safely when host structure changes.
- Removed panel sync code must not remain as misleading dead code.

## Tasks

### Task 1: Add PanelReadyPolicy

**Files:**
- Create: `app/src/main/java/hun/mesh/carwithenhance/policy/PanelReadyPolicy.java`
- Test: `app/src/test/java/hun/mesh/carwithenhance/policy/PanelReadyPolicyTest.java`

**Interfaces:**
- Produces:
  - `static boolean hasPanelIdentity(String mediaId, String title, String artist)`
  - `static boolean panelWaitExceeded(long startElapsedRealtime, long nowElapsedRealtime, long maxWaitMs)`

- [ ] Write failing tests for media-id identity and per-call timeout behavior.
- [ ] Implement policy.
- [ ] Run tests to pass.

### Task 2: Replace global panel wait with per-package state

**Files:**
- Modify: `app/src/main/java/hun/mesh/carwithenhance/hook/AutoPlayAllHook.java`

- [ ] Remove `sWaitStartAt` and use `Map<String, Long> PANEL_WAIT_STARTED`.
- [ ] Use `PanelReadyPolicy.panelWaitExceeded`.
- [ ] Reset/clear wait state when package changes and when resume succeeds.
- [ ] Make cold-start path use `resumeAfterPanelIfReady`.
- [ ] Remove dead panel snapshot helpers and imports.
- [ ] Run full tests and build.

### Task 3: Make lyric source lookup structural

**Files:**
- Modify: `app/src/main/java/hun/mesh/carwithenhance/media/CarWithMediaCapabilities.java`
- Modify: `app/src/main/java/hun/mesh/carwithenhance/hook/LyricSourceHook.java`

- [ ] Resolve `na.c` lyric-source method via structural query, with native fallback.
- [ ] Call public `MediaMetadataCompat.getString("lyricInfo")`.
- [ ] Preserve current behavior when lyricInfo is absent.
- [ ] Run tests and build.

### Task 4: Verification and release preparation

- [ ] Run full test suite.
- [ ] Build release APK.
- [ ] Verify APK signature and hash.
- [ ] Install to connected device.
