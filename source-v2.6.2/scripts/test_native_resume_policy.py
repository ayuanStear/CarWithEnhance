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
    private static void check(String actual, String expected, String message) {
        if (!expected.equals(actual)) {
            throw new AssertionError(message + " expected=" + expected + " actual=" + actual);
        }
    }

    public static void main(String[] args) {
        check(NativeResumePolicy.selectTarget(
                true, "com.tencent.qqmusic", "com.netease.cloudmusic", true),
                "com.netease.cloudmusic", "valid saved package");
        check(NativeResumePolicy.selectTarget(
                false, "com.tencent.qqmusic", "com.netease.cloudmusic", true),
                "com.tencent.qqmusic", "disabled switch");
        check(NativeResumePolicy.selectTarget(
                true, "com.tencent.qqmusic", null, true),
                "com.tencent.qqmusic", "null saved package");
        check(NativeResumePolicy.selectTarget(
                true, "com.tencent.qqmusic", "", true),
                "com.tencent.qqmusic", "empty saved package");
        check(NativeResumePolicy.selectTarget(
                true, "com.tencent.qqmusic", "com.netease.cloudmusic", false),
                "com.tencent.qqmusic", "saved package not installed");
        check(NativeResumePolicy.selectTarget(
                true, "com.netease.cloudmusic", "com.netease.cloudmusic", true),
                "com.netease.cloudmusic", "same package");
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
