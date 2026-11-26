#!/usr/bin/env python3
"""Behavior tests for the v2.6.2 informational log filter."""

from __future__ import annotations

import shutil
import subprocess
import tempfile
import unittest
from pathlib import Path


ROOT = Path(__file__).resolve().parents[1]
POLICY = ROOT / "sources" / "carwake-dexkit" / "RuntimeLogPolicy.java"

HARNESS = r"""
public final class RuntimeLogPolicyHarness {
    private static void check(boolean actual, boolean expected, String label) {
        if (actual != expected) {
            throw new AssertionError(label + " expected=" + expected + " actual=" + actual);
        }
    }

    public static void main(String[] args) {
        check(RuntimeLogPolicy.keepAutoPlayLog(null), false, "null autoplay log");
        check(RuntimeLogPolicy.keepAutoPlayLog(""), false, "empty autoplay log");
        check(RuntimeLogPolicy.keepAutoPlayLog("连接状态钩子已注册（仅续播）"), false, "hook registration");
        check(RuntimeLogPolicy.keepAutoPlayLog("媒体根页签已恢复: com.netease.cloudmusic index=3/4"), false, "tab restore");
        check(RuntimeLogPolicy.keepAutoPlayLog("媒体路由请求: com.netease.cloudmusic"), false, "route request");
        check(RuntimeLogPolicy.keepAutoPlayLog("车机界面状态刷新（卡片状态刷新）"), false, "panel refresh");
        check(RuntimeLogPolicy.keepAutoPlayLog("音频状态(车机连接时): 媒体音量=65/150"), false, "audio state");
        check(RuntimeLogPolicy.keepAutoPlayLog("体验应用已放到普通列表末尾: 49 个"), false, "experience ordering");
        check(RuntimeLogPolicy.keepAutoPlayLog("CarWith 已连接"), true, "connected");
        check(RuntimeLogPolicy.keepAutoPlayLog("连接已断开，仅复位续播状态"), true, "disconnect");
        check(RuntimeLogPolicy.keepAutoPlayLog("媒体会话准备超时"), true, "resume timeout");
        check(RuntimeLogPolicy.keepAutoPlayLog("媒体路由保持官方结果"), true, "route result");
        check(RuntimeLogPolicy.keepAutoPlayLog("检测到播放器真正开始播放"), true, "playback start");

        check(RuntimeLogPolicy.keepXLog(null), false, "null xlog");
        check(RuntimeLogPolicy.keepXLog("CarWake: mask ucar_casting_state=0 in com.miui.voicetrigger"), false, "casting mask");
        check(RuntimeLogPolicy.keepXLog("CarWake: asr-start mute hook installed in com.miui.carlink"), false, "hook install");
        check(RuntimeLogPolicy.keepXLog("CarWake: car-audio silence hooks installed in com.miui.carlink"), false, "plural hook install");
        check(RuntimeLogPolicy.keepXLog("CarWake: docker voice first round start"), true, "voice round");
        check(RuntimeLogPolicy.keepXLog("CarWake: mute watchdog fired, audio restored"), true, "watchdog");
        check(RuntimeLogPolicy.keepXLog("CarWake: asr timeout, retry limit reached, give up this turn"), true, "timeout error");
        System.out.println("runtime-log-policy-ok");
    }
}
"""


class RuntimeLogPolicyTests(unittest.TestCase):
    def test_informational_noise_is_filtered_and_critical_events_are_kept(self) -> None:
        self.assertTrue(POLICY.is_file(), f"missing {POLICY}")
        javac = shutil.which("javac")
        java = shutil.which("java")
        self.assertTrue(javac, "javac not found")
        self.assertTrue(java, "java not found")
        with tempfile.TemporaryDirectory(prefix="runtime-log-policy-") as temp:
            root = Path(temp)
            harness = root / "RuntimeLogPolicyHarness.java"
            harness.write_text(HARNESS, encoding="utf-8")
            compiled = subprocess.run(
                [javac, "-encoding", "UTF-8", "-d", str(root), str(POLICY), str(harness)],
                text=True,
                capture_output=True,
            )
            self.assertEqual(compiled.returncode, 0, compiled.stdout + compiled.stderr)
            ran = subprocess.run(
                [java, "-Dfile.encoding=UTF-8", "-cp", str(root), "RuntimeLogPolicyHarness"],
                text=True,
                capture_output=True,
            )
            self.assertEqual(ran.returncode, 0, ran.stdout + ran.stderr)
            self.assertIn("runtime-log-policy-ok", ran.stdout)


if __name__ == "__main__":
    unittest.main(verbosity=2)
