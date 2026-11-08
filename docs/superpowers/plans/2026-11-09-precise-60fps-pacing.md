# Precise CarLife 60FPS Pacing Implementation Plan

> **For agentic workers:** REQUIRED SUB-SKILL: Use superpowers:subagent-driven-development (recommended) or superpowers:executing-plans to implement this plan task-by-task. Steps use checkbox (`- [ ]`) syntax for tracking.

**Goal:** Reduce CarLife encoder backpressure and visible judder by pacing
60FPS with a drift-free nanosecond clock and declaring the encoder Surface as a
fixed-frame-rate source.

**Architecture:** Add a CarLife-process Xposed hook that replaces only the
`FrameRender$RenderHandler` delay calculation. A pure `FramePacerPolicy`
computes absolute nanosecond deadlines and safe missed-frame recovery. The hook
also applies `Surface.setFrameRate`, with a 17ms degraded fallback when only the
CarLife frame-rate method can be resolved.

**Tech Stack:** Java 8, Android Xposed API, LSPosed, DexKit, JUnit 4, Gradle,
Perfetto.

**Spec:** `docs/superpowers/specs/2026-11-09-precise-60fps-pacing-design.md`

## Global Constraints

- New LSPosed scope: `com.baidu.carlife.xiaomi`.
- New setting key: `hook_precise_60fps_enabled`, default `true`.
- The fix must not change 30/20/15FPS behavior.
- The module minSdk remains 29; `Surface.setFrameRate` is guarded by API 30.
- Never modify resolution, bitrate, Profile, Level, or codec selection.
- Never catch up multiple late frames in one burst.
- No per-frame normal logging.
- A disabled setting must restore original CarLife behavior after CarLife
  process restart or CarWith reconnection.
- If precise renderer resolution fails, 60FPS uses a 17ms degraded interval and
  logs `DEGRADED_17MS`.

## Review Focus

- A render takes longer than one frame period: skip the missed frame and avoid a
  burst of delayed messages.
- `changeFrameRate(60)` is called repeatedly: preserve the existing deadline
  phase instead of resetting it every time.
- Surface is absent, invalid, or API is below 30: do not crash; retry after
  connection establishment.
- User selects 30/20/15FPS or disables the setting: leave CarLife scheduling
  unchanged.
- Target resolution differs across CarLife versions: use the known target,
  DexKit fallback, then the explicit 17ms degraded mode.

---

### Task 1: Add Drift-Free Frame Policy

**Files:**
- Create: `app/src/main/java/hun/mesh/carwithenhance/policy/FramePacerPolicy.java`
- Create: `app/src/test/java/hun/mesh/carwithenhance/policy/FramePacerPolicyTest.java`

**Interfaces:**
- Consumes: monotonic nanosecond timestamps supplied by the caller.
- Produces:
  - `FramePacerPolicy()`
  - `synchronized int nextDelayMillis(int fps, long frameStartNanos, long nowNanos)`
  - `synchronized void reset()`
  - `static boolean shouldOverrideRenderer(boolean enabled, int fps)`
  - `static boolean shouldApplySurfaceFrameRate(boolean enabled, int fps, int sdkInt, boolean hasValidSurface)`
  - `static int fallbackIntervalMillis(int fps)`

- [ ] **Step 1: Write the failing policy tests**

```java
package hun.mesh.carwithenhance.policy;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class FramePacerPolicyTest {
    @Test
    public void sixtyFpsDoesNotDriftOverSixtySeconds() {
        FramePacerPolicy policy = new FramePacerPolicy();
        long nowNanos = 0L;
        long totalDelayMillis = 0L;
        int saw16 = 0;
        int saw17 = 0;

        for (int i = 0; i < 3_600; i++) {
            long frameStartNanos = nowNanos;
            int delay = policy.nextDelayMillis(60, frameStartNanos, nowNanos);
            totalDelayMillis += delay;
            nowNanos += delay * 1_000_000L;
            if (delay == 16) {
                saw16++;
            } else if (delay == 17) {
                saw17++;
            }
        }

        assertTrue(Math.abs(totalDelayMillis - 60_000L) <= 2L);
        assertTrue(saw16 > 0);
        assertTrue(saw17 > 0);
    }

    @Test
    public void repeatedSameFpsCallDoesNotResetPhase() {
        FramePacerPolicy policy = new FramePacerPolicy();
        assertEquals(17, policy.nextDelayMillis(60, 0L, 0L));
        assertEquals(17, policy.nextDelayMillis(60, 17_000_000L, 17_000_000L));
        assertEquals(16, policy.nextDelayMillis(60, 34_000_000L, 34_000_000L));
    }

    @Test
    public void lateFrameSkipsInsteadOfBursting() {
        FramePacerPolicy policy = new FramePacerPolicy();
        policy.nextDelayMillis(60, 0L, 0L);
        int delay = policy.nextDelayMillis(60, 100_000_000L, 102_000_000L);
        assertEquals(15, delay);
    }

    @Test
    public void fpsChangeResetsDeadline() {
        FramePacerPolicy policy = new FramePacerPolicy();
        policy.nextDelayMillis(60, 0L, 0L);
        assertEquals(34, policy.nextDelayMillis(30, 20_000_000L, 20_000_000L));
    }

    @Test
    public void officialNonSixtyFrameRatesStayValid() {
        assertEquals(66, FramePacerPolicy.fallbackIntervalMillis(15));
        assertEquals(50, FramePacerPolicy.fallbackIntervalMillis(20));
        assertEquals(33, FramePacerPolicy.fallbackIntervalMillis(30));
        assertEquals(17, FramePacerPolicy.fallbackIntervalMillis(60));
    }

    @Test
    public void gatesProtectUnsupportedSchedulingAndSurface() {
        assertTrue(FramePacerPolicy.shouldOverrideRenderer(true, 60));
        assertFalse(FramePacerPolicy.shouldOverrideRenderer(false, 60));
        assertFalse(FramePacerPolicy.shouldOverrideRenderer(true, 30));
        assertTrue(FramePacerPolicy.shouldApplySurfaceFrameRate(true, 60, 30, true));
        assertFalse(FramePacerPolicy.shouldApplySurfaceFrameRate(true, 60, 29, true));
        assertFalse(FramePacerPolicy.shouldApplySurfaceFrameRate(true, 60, 30, false));
        assertFalse(FramePacerPolicy.shouldApplySurfaceFrameRate(true, 30, 30, true));
    }
}
```

- [ ] **Step 2: Run the test and verify it fails**

Run:

```powershell
.\gradlew.bat testDebugUnitTest --tests hun.mesh.carwithenhance.policy.FramePacerPolicyTest
```

Expected: compilation fails because `FramePacerPolicy` does not exist.

- [ ] **Step 3: Implement the minimal policy**

Use a `nextDeadlineNanos` field, current fps, and an initialized flag.

For the first call or an fps change:

```text
stepNanos = 1_000_000_000 / fps
nextDeadlineNanos = frameStartNanos + stepNanos
```

For the same fps, advance the deadline from the previous absolute deadline.
If the observed frame start is more than one half-period away from the expected
start, reset the deadline from `frameStartNanos`. If `nowNanos` is more than one
full period beyond the deadline, skip missed periods and reset from
`frameStartNanos`; never return multiple catch-up frames.

Return the delay rounded up to milliseconds:

```text
max(0, ceil((nextDeadlineNanos - nowNanos) / 1_000_000.0))
```

Validate `fps` with `15 <= fps <= 60`; otherwise throw
`IllegalArgumentException`.

The two gate helpers return:

```text
shouldOverrideRenderer = enabled && fps == 60
shouldApplySurfaceFrameRate =
        enabled && fps == 60 && sdkInt >= 30 && hasValidSurface
```

- [ ] **Step 4: Run the policy tests and verify they pass**

Run:

```powershell
.\gradlew.bat testDebugUnitTest --tests hun.mesh.carwithenhance.policy.FramePacerPolicyTest
```

Expected: all `FramePacerPolicyTest` tests pass.

- [ ] **Step 5: Commit**

```powershell
git add app/src/main/java/hun/mesh/carwithenhance/policy/FramePacerPolicy.java app/src/test/java/hun/mesh/carwithenhance/policy/FramePacerPolicyTest.java
git commit -m "feat: add drift-free frame pacing policy"
```

---

### Task 2: Add CarLife Scope And Setting

**Files:**
- Modify: `app/src/main/res/values/arrays.xml`
- Modify: `app/src/main/java/hun/mesh/carwithenhance/config/HookConfigs.java`
- Modify: `app/src/test/java/hun/mesh/carwithenhance/config/HookConfigsTest.java`

**Interfaces:**
- Consumes: existing `HookItem` and settings-menu iteration.
- Produces:
  - `HookConfigs.PRECISE_60FPS`
  - LSPosed resource item `com.baidu.carlife.xiaomi`

- [ ] **Step 1: Extend the failing configuration test**

Add assertions that:

```java
assertEquals("hook_precise_60fps_enabled", HookConfigs.PRECISE_60FPS.key);
assertTrue(HookConfigs.PRECISE_60FPS.defaultValue);
assertNotNull(HookConfigs.findByKey("hook_precise_60fps_enabled"));
assertTrue(HookConfigs.getAllItems().contains(HookConfigs.PRECISE_60FPS));
```

- [ ] **Step 2: Run the config test and verify it fails**

Run:

```powershell
.\gradlew.bat testDebugUnitTest --tests hun.mesh.carwithenhance.config.HookConfigsTest
```

Expected: FAIL because `PRECISE_60FPS` is undefined.

- [ ] **Step 3: Add the HookItem and scope**

Add:

```java
public static final HookItem PRECISE_60FPS = new HookItem(
        "hook_precise_60fps_enabled",
        "精确60FPS帧调度",
        "修正CarLife 16ms整数排程导致的62.5FPS超速，减少编码背压与顿帧",
        true);
```

Add it to `ALL_ITEMS`. Add `com.baidu.carlife.xiaomi` to
`lsposed_scopes`.

- [ ] **Step 4: Run tests and inspect the scope resource**

Run:

```powershell
.\gradlew.bat testDebugUnitTest --tests hun.mesh.carwithenhance.config.HookConfigsTest
Select-String -Path app/src/main/res/values/arrays.xml -Pattern 'com.baidu.carlife.xiaomi'
```

Expected: test passes and exactly one scope match is printed.

- [ ] **Step 5: Commit**

```powershell
git add app/src/main/res/values/arrays.xml app/src/main/java/hun/mesh/carwithenhance/config/HookConfigs.java app/src/test/java/hun/mesh/carwithenhance/config/HookConfigsTest.java
git commit -m "feat: add CarLife pacing scope and setting"
```

---

### Task 3: Resolve CarLife Frame Targets

**Files:**
- Create: `app/src/main/java/hun/mesh/carwithenhance/dexkit/CarLifeFrameTargets.java`

**Interfaces:**
- Consumes: `ClassLoader` from `XC_LoadPackage.LoadPackageParam`.
- Produces:
  - `static CarLifeFrameTargets resolve(ClassLoader classLoader)`
  - `Class<?> recorderClass`
  - `Method changeFrameRate`
  - `Method handleConnectEstablished`
  - `Field destFrameInterval`
  - `Field encodeSurface`
  - `Class<?> renderHandlerClass`
  - `Method renderDisplayMsg`
  - `boolean hasPreciseRenderer()`

The resolver must never throw to the caller; missing targets remain `null` and
are logged by the hook.

- [ ] **Step 1: Implement known-name resolution**

Resolve these first:

```text
com.baidu.carlife.core.screen.video.Recorder
Recorder#changeFrameRate(int)
Recorder#handleConnectEstablished()
Recorder.mDestFrameInterval
Recorder.mEncSurface
com.baidu.carlife.core.screen.video.FrameRender$RenderHandler
RenderHandler#renderDisplayMsg(long)
```

Make fields and methods accessible; individual failures do not fail the whole
resolver.

- [ ] **Step 2: Add structural fallback**

Use `StructuralMethodLookup.containing("changeFrameRate frame===:", "void", 1)`
to recover the recorder method when the known class name changes. Use that
method's declaring class for fields and connection-method resolution.

Only set `renderDisplayMsg` when the resolved reflected method has exactly one
`long` parameter and a `void` return type.

- [ ] **Step 3: Add resolver logs and degraded state**

Log one summary containing:

```text
recorder=<class>, changeFrameRate=<true/false>,
renderer=<class/method or MISSING>, mode=PRECISE or DEGRADED_17MS
```

`hasPreciseRenderer()` returns true only when the renderer class and method are
both available.

- [ ] **Step 4: Compile and run a static target check**

Run:

```powershell
.\gradlew.bat :app:compileReleaseJavaWithJavac
Select-String -Path app/src/main/java/hun/mesh/carwithenhance/dexkit/CarLifeFrameTargets.java -Pattern 'changeFrameRate frame===:|DEGRADED_17MS|renderDisplayMsg'
```

Expected: compilation succeeds and all three identifiers are present.

- [ ] **Step 5: Commit**

```powershell
git add app/src/main/java/hun/mesh/carwithenhance/dexkit/CarLifeFrameTargets.java
git commit -m "feat: resolve CarLife frame pacing targets"
```

---

### Task 4: Install CarLife Pacing Hooks

**Files:**
- Create: `app/src/main/java/hun/mesh/carwithenhance/hook/CarLifeFramePacingHook.java`
- Modify: `app/src/main/java/hun/mesh/carwithenhance/MainHook.java`
- Modify: `app/src/main/java/hun/mesh/carwithenhance/hook/IHook.java` only if a
  compatible context constructor requires a shared interface change.

**Interfaces:**
- Consumes:
  - `FramePacerPolicy`
  - `CarLifeFrameTargets`
  - `HookConfigs.PRECISE_60FPS.key`
- Produces:
  - `CarLifeFramePacingHook(Context applicationContext)`
  - `void onHook(ClassLoader classLoader)`

Behavior:

- Read the setting from `Settings.Global`, defaulting to true on read failure.
- Track requested fps from `changeFrameRate(int)`.
- Override `renderDisplayMsg(long)` only when enabled and fps is 60.
- Apply `Surface.setFrameRate(fps, FIXED_SOURCE)` when API >= 30 and Surface is
  valid.
- In `DEGRADED_17MS`, set `mDestFrameInterval=17` after `changeFrameRate(60)`.

- [ ] **Step 1: Implement the hook with explicit failure isolation**

Wrap each independent target installation in its own `try/catch`:

1. `changeFrameRate` hook.
2. `handleConnectEstablished` hook.
3. `renderDisplayMsg` replacement hook.

No missing target may prevent the remaining targets from being attempted.

- [ ] **Step 2: Implement precise renderer replacement**

In the `renderDisplayMsg(long elapsedMillis)` before-callback:

```text
nowNanos = SystemClock.elapsedRealtimeNanos()
frameStartNanos = max(0, nowNanos - elapsedMillis * 1_000_000)
delayMillis = pacer.nextDelayMillis(requestedFps, frameStartNanos, nowNanos)
handler.sendEmptyMessageDelayed(2, delayMillis)
param.setResult(null)
```

Use the Hook's `thisObject` as the `Handler`. Do not call the original method
when the override succeeds.

- [ ] **Step 3: Implement Surface application and degradation**

Read `mEncSurface` by reflection. Catch invalid surfaces and
`IllegalArgumentException`. In degraded mode, reflectively set the static
`mDestFrameInterval` field to `17` only for requested fps 60.

Do not use this degraded field write when the precise renderer hook installed
successfully.

- [ ] **Step 4: Integrate the CarLife Application branch**

In `MainHook`, add:

```text
case "com.baidu.carlife.xiaomi":
    log CarLife hook startup
    hook Application.onCreate
    resolve targets and install CarLifeFramePacingHook
```

Do not initialize `CarWithDexKitManager` or media hooks in this branch.

- [ ] **Step 5: Build and inspect startup diagnostics**

Run:

```powershell
.\gradlew.bat :app:compileReleaseJavaWithJavac
.\gradlew.bat testDebugUnitTest
```

Expected: compilation and all JVM tests pass.

- [ ] **Step 6: Commit**

```powershell
git add app/src/main/java/hun/mesh/carwithenhance/hook/CarLifeFramePacingHook.java app/src/main/java/hun/mesh/carwithenhance/MainHook.java
git commit -m "feat: install precise CarLife frame pacing hooks"
```

---

### Task 5: Verify Device Behavior And Prepare Release

**Files:**
- Modify: `app/build.gradle`
- Modify: `CHANGELOG.md`
- Modify: `README.md`
- Modify: `docs/FRAME-RATE-RESTORATION.md`
- Create: `docs/PRECISE-60FPS-PACING.md`
- Create: `exports/` evidence files through device commands.

**Interfaces:**
- Consumes: installed CarLife/Enhance build and LSPosed scopes.
- Produces: device evidence, APK, updated documentation.

- [ ] **Step 1: Bump the module version**

Set:

```text
versionCode 46
versionName "2.4.3-precise-60fps"
```

- [ ] **Step 2: Build and run the full test suite**

Run:

```powershell
.\gradlew.bat testDebugUnitTest
.\gradlew.bat assembleRelease
git diff --check
```

Expected: all tests pass, release APK builds, and `git diff --check` prints no
errors.

- [ ] **Step 3: Install and enable the CarLife scope**

Install the APK, enable `com.miui.carlink`, `android`, and
`com.baidu.carlife.xiaomi` in LSPosed, restart CarLife, and reconnect CarWith.

Verify logs contain:

```text
recorder=
changeFrameRate=true
renderer=
mode=PRECISE
```

If `mode=DEGRADED_17MS`, record the unresolved target and do not claim precise
pacing success.

- [ ] **Step 4: Capture ten seconds of continuous swipe with Perfetto**

Use the existing trace procedure and query:

```sql
select count(*) from slice s
join thread_track tt on s.track_id=tt.id
join thread t using(utid)
join process p using(upid)
where p.name='com.baidu.carlife.xiaomi'
  and t.name='clhandlerRender'
  and s.name='eglSwapBuffers';
```

Required results:

- Ten-second average is `59.7–60.3FPS`.
- One-second windows are `59–61`.
- Mean interval is `16.60–16.73ms`; no fixed 16.0ms-only pattern.
- MediaCodec output is closer to 60FPS than the pre-change `52–53FPS` windows.
- TCP video send-q does not grow continuously.

- [ ] **Step 5: Verify A/B behavior**

Toggle `hook_precise_60fps_enabled` off, restart CarLife, and verify the old
62–63FPS pattern returns. Re-enable it and verify about 60FPS returns. Select
30FPS and verify the original 30FPS path remains active and is not replaced by
the 60FPS pacer.

- [ ] **Step 6: Write user documentation and evidence**

Document:

- New setting title and default.
- CarLife LSPosed scope requirement.
- Reconnection requirement after toggling.
- Degraded mode meaning.
- Before/after measured FPS and trace paths.
- Limitation that a vehicle unable to decode 1080p60 still needs 30FPS.

- [ ] **Step 7: Commit release preparation**

```powershell
git add app/build.gradle CHANGELOG.md README.md docs/FRAME-RATE-RESTORATION.md docs/PRECISE-60FPS-PACING.md
git commit -m "release: prepare precise 60fps pacing"
```

Do not commit raw device logs, private IP addresses, screenshots, credentials,
or local absolute paths to user data.
