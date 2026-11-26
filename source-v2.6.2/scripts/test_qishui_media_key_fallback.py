#!/usr/bin/env python3
"""Behavior and source contracts for the Qishui cold-start media-key fallback."""

from __future__ import annotations

import shutil
import subprocess
import tempfile
import unittest
from pathlib import Path


ROOT = Path(__file__).resolve().parents[1]
POLICY = ROOT / "sources" / "carwake-dexkit" / "QishuiMediaKeyFallbackPolicy.java"
FALLBACK = ROOT / "sources" / "carwake-dexkit" / "QishuiMediaKeyFallback.java"
HOOK = ROOT / "sources" / "carwake-dexkit" / "AllAppCarResume.java"

HARNESS = r'''
public final class QishuiMediaKeyFallbackPolicyHarness {
    private static void check(boolean actual, boolean expected, String message) {
        if (actual != expected) {
            throw new AssertionError(message + " expected=" + expected + " actual=" + actual);
        }
    }

    public static void main(String[] args) {
        check(QishuiMediaKeyFallbackPolicy.shouldDispatch("com.luna.music", false),
                true, "stopped Qishui should use media key");
        check(QishuiMediaKeyFallbackPolicy.shouldDispatch("com.luna.music", true),
                false, "live Qishui session should stay on native path");
        check(QishuiMediaKeyFallbackPolicy.shouldDispatch("com.netease.cloudmusic", false),
                false, "other apps must not use Qishui fallback");
        check(QishuiMediaKeyFallbackPolicy.shouldDispatch(null, false),
                false, "null package");
        check(QishuiMediaKeyFallbackPolicy.shouldDispatch("", false),
                false, "empty package");
        System.out.println("qishui-media-key-policy-ok");
    }
}
'''


class QishuiMediaKeyFallbackTests(unittest.TestCase):
    def test_policy_only_selects_qishui_without_live_session(self) -> None:
        self.assertTrue(POLICY.is_file(), f"missing {POLICY}")
        javac = shutil.which("javac")
        java = shutil.which("java")
        self.assertTrue(javac, "javac not found")
        self.assertTrue(java, "java not found")
        with tempfile.TemporaryDirectory(prefix="qishui-policy-") as temp:
            root = Path(temp)
            harness = root / "QishuiMediaKeyFallbackPolicyHarness.java"
            harness.write_text(HARNESS, encoding="utf-8")
            compiled = subprocess.run(
                [javac, "-d", str(root), str(POLICY), str(harness)],
                text=True,
                capture_output=True,
            )
            self.assertEqual(compiled.returncode, 0, compiled.stdout + compiled.stderr)
            ran = subprocess.run(
                [java, "-cp", str(root), "QishuiMediaKeyFallbackPolicyHarness"],
                text=True,
                capture_output=True,
            )
            self.assertEqual(ran.returncode, 0, ran.stdout + ran.stderr)
            self.assertIn("qishui-media-key-policy-ok", ran.stdout)

    def test_fallback_is_explicit_stopped_package_play_broadcast(self) -> None:
        self.assertTrue(FALLBACK.is_file(), f"missing {FALLBACK}")
        source = FALLBACK.read_text(encoding="utf-8")
        for required in (
            "com.luna.music",
            "com.luna.biz.playing.player.BachMediaButtonReceiver",
            "Intent.ACTION_MEDIA_BUTTON",
            "setComponent",
            "FLAG_INCLUDE_STOPPED_PACKAGES",
            "FLAG_RECEIVER_FOREGROUND",
            "KeyEvent.ACTION_DOWN",
            "KeyEvent.ACTION_UP",
            "KeyEvent.KEYCODE_MEDIA_PLAY",
            "sendBroadcast",
            "QishuiMediaKeyFallbackPolicy.shouldDispatch",
        ):
            with self.subTest(required=required):
                self.assertIn(required, source)
        self.assertNotIn("startActivity", source)
        self.assertNotIn("MediaBrowser", source)

    def test_hook_invokes_fallback_from_native_audio_ready_autoplay_entry(self) -> None:
        source = HOOK.read_text(encoding="utf-8")
        self.assertIn("QishuiMediaKeyFallback.resumeIfNeeded(context, selected)", source)
        hook_pos = source.index("protected void beforeHookedMethod")
        call_pos = source.index("QishuiMediaKeyFallback.resumeIfNeeded(context, selected)")
        install_pos = source.index("installed native target rewrite")
        self.assertLess(hook_pos, call_pos)
        self.assertLess(call_pos, install_pos)


if __name__ == "__main__":
    unittest.main(verbosity=2)
