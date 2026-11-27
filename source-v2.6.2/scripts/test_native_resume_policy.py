#!/usr/bin/env python3
"""Executable contract tests for native resume target selection."""

from __future__ import annotations

import shutil
import subprocess
import tempfile
import unittest
from pathlib import Path


ROOT = Path(__file__).resolve().parents[1]
POLICY = ROOT / "sources" / "carwake-dexkit" / "NativeResumePolicy.java"

HARNESS = r'''
public final class NativeResumePolicyHarness {
    private static void check(Object actual, Object expected, String message) {
        if (!expected.equals(actual)) {
            throw new AssertionError(message + " expected=" + expected + " actual=" + actual);
        }
    }

    private static void check(boolean actual, boolean expected, String message) {
        if (actual != expected) {
            throw new AssertionError(message + " expected=" + expected + " actual=" + actual);
        }
    }

    public static void main(String[] args) {
        String pkg = "com.netease.cloudmusic";
        check(NativeResumePolicy.selectTarget(true, "com.tencent.qqmusic", pkg, true),
                pkg, "valid saved package");
        check(NativeResumePolicy.selectTarget(false, "com.tencent.qqmusic", pkg, true),
                "com.tencent.qqmusic", "disabled switch");
        check(NativeResumePolicy.selectTarget(true, "com.tencent.qqmusic", null, true),
                "com.tencent.qqmusic", "null saved package");
        check(NativeResumePolicy.selectTarget(true, "com.tencent.qqmusic", "", true),
                "com.tencent.qqmusic", "empty saved package");
        check(NativeResumePolicy.selectTarget(true, "com.tencent.qqmusic", pkg, false),
                "com.tencent.qqmusic", "saved package not installed");
        check(NativeResumePolicy.shouldHandleNativeCall(true, false, false, true),
                true, "audio-ready native path");
        check(NativeResumePolicy.shouldHandleNativeCall(false, true, true, true),
                true, "queued duplicate of completed prewake");
        check(NativeResumePolicy.shouldHandleNativeCall(false, true, false, false),
                false, "unrelated queued duplicate is ignored");
        check(NativeResumePolicy.shouldHandleNativeCall(false, false, false, false),
                false, "unconnected queued duplicate is ignored");
        check(NativeResumePolicy.shouldSuppressNativeCall(true, true, false),
                true, "completed module resume suppresses native duplicate");
        check(NativeResumePolicy.shouldSuppressNativeCall(false, true, false),
                false, "failed or pending prewake retains native fallback");
        check(NativeResumePolicy.shouldSuppressNativeCall(true, false, true),
                true, "already playing suppresses duplicate");
        check(NativeResumePolicy.shouldSuppressNativeCall(false, false, true),
                false, "not playing does not suppress native resume");
        check(NativeResumePolicy.shouldSuppressNativeCall(false, true, true),
                false, "disconnected delayed callback is not suppressed");
        check(NativeResumePolicy.shouldAllowRetry(true, true, false, false), true,
                "pending resume may retry within the connection window");
        check(NativeResumePolicy.shouldAllowRetry(true, true, true, false), false,
                "successful resume must not retry");
        check(NativeResumePolicy.shouldAllowRetry(true, true, false, true), false,
                "already playing must not retry");
        check(NativeResumePolicy.shouldAllowRetry(true, false, false, false), false,
                "expired window stops retries");
        check(NativeResumePolicy.shouldAllowRetry(false, true, false, false), false,
                "disabled switch stops retries");
        check(NativeResumePolicy.isPrewakeCurrent(1000L, 2000L), true,
                "same connection can suppress native duplicate");
        check(NativeResumePolicy.isPrewakeCurrent(1000L, 31001L), false,
                "old prewake cannot block native fallback");
        check(NativeResumePolicy.isPrewakeCurrent(0L, 1000L), false,
                "missing connection timestamp");
        System.out.println("native-resume-policy-ok");
    }
}
'''



class NativeResumePolicyTests(unittest.TestCase):
    def test_select_target_contract(self) -> None:
        self.assertTrue(POLICY.is_file(), f"missing {POLICY}")
        javac = shutil.which("javac")
        java = shutil.which("java")
        self.assertTrue(javac, "javac not found")
        self.assertTrue(java, "java not found")
        with tempfile.TemporaryDirectory(prefix="native-resume-policy-") as temp:
            root = Path(temp)
            harness = root / "NativeResumePolicyHarness.java"
            harness.write_text(HARNESS, encoding="utf-8")
            compile_result = subprocess.run(
                [javac, "-d", str(root), str(POLICY), str(harness)],
                text=True,
                capture_output=True,
            )
            self.assertEqual(
                compile_result.returncode,
                0,
                compile_result.stdout + compile_result.stderr,
            )
            run_result = subprocess.run(
                [java, "-cp", str(root), "NativeResumePolicyHarness"],
                text=True,
                capture_output=True,
            )
            self.assertEqual(
                run_result.returncode,
                0,
                run_result.stdout + run_result.stderr,
            )
            self.assertIn("native-resume-policy-ok", run_result.stdout)


if __name__ == "__main__":
    unittest.main(verbosity=2)
