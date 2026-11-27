#!/usr/bin/env python3
"""Structural acceptance tests for the 2.6.3 pre-wake fast-resume APK."""

from __future__ import annotations

import hashlib
import os
import shutil
import subprocess
import unittest
import zipfile
from pathlib import Path

from androguard.core.apk import APK
from loguru import logger
logger.remove()
from androguard.core.dex import DEX


ROOT = Path(__file__).resolve().parents[1]
OUTPUT_DIR = Path(os.environ.get("CARWITHX_OUTPUT_DIR", ROOT / "output"))
APK_PATH = Path(os.environ.get("CARWITHX_APK", OUTPUT_DIR / "CarWithEnhance-2.6.3-prewake-fast-resume.apk"))
EXPECTED_PACKAGE = "hun.mesh.carwithenhance"
EXPECTED_VERSION_CODE = "74"
EXPECTED_VERSION_NAME = "2.6.3-qishui-carmic-tabfix-prewake-fast-resume"

def find_method(cls, name: str, descriptor_part: str = ""):
    for method in cls.get_methods():
        if method.get_name() == name and descriptor_part in method.get_descriptor():
            return method
    return None


class ResumeLiteApkTests(unittest.TestCase):
    @classmethod
    def setUpClass(cls) -> None:
        if not APK_PATH.is_file():
            raise AssertionError(f"APK not found: {APK_PATH}")
        cls.apk = APK(str(APK_PATH))
        with zipfile.ZipFile(APK_PATH) as archive:
            cls.dex_names = sorted(n for n in archive.namelist() if n.endswith(".dex"))
            cls.dexes = {name: DEX(archive.read(name)) for name in cls.dex_names}
        cls.classes = {
            cls_name.get_name(): (dex_name, cls_name)
            for dex_name, dex in cls.dexes.items()
            for cls_name in dex.get_classes()
        }

    def test_manifest_identity_and_no_activity(self) -> None:
        self.assertEqual(self.apk.get_package(), EXPECTED_PACKAGE)
        self.assertEqual(self.apk.get_androidversion_code(), EXPECTED_VERSION_CODE)
        self.assertEqual(self.apk.get_androidversion_name(), EXPECTED_VERSION_NAME)
        root = self.apk.get_android_manifest_xml()
        self.assertEqual(root.findall(".//activity"), [])

    def test_removed_hook_classes_are_absent(self) -> None:
        forbidden = {
            name for name in self.classes
            if "LyricSourceHook" in name or "CarWithPanelReadyHook" in name or "LyricFallbackPolicy" in name
        }
        self.assertEqual(forbidden, set())

    def test_native_resume_helper_is_present(self) -> None:
        required = {
            "LAllAppCarResume;",
            "LNativeResumePolicy;",
            "LQishuiMediaKeyFallback;",
            "LQishuiMediaKeyFallbackPolicy;",
        }
        for name in required:
            with self.subTest(name=name):
                self.assertIn(name, self.classes)
        self.assertNotIn("LResumeGate;", self.classes)


    def test_runtime_log_filter_is_wired_and_errors_are_untouched(self) -> None:
        self.assertIn("LRuntimeLogPolicy;", self.classes)
        policy_dex = self.dexes[self.classes["LRuntimeLogPolicy;"][0]]
        policy_strings = set(policy_dex.get_strings())
        for message in (
            "媒体根页签",
            "mask ucar_casting_state=",
            "hook installed in ",
            "hooks installed in ",
            "已注册",
        ):
            self.assertIn(message, policy_strings)

        _, auto_cls = self.classes["Lhun/mesh/carwithenhance/hook/AutoPlayAllHook;"]
        log_method = find_method(auto_cls, "log")
        self.assertIsNotNone(log_method)
        log_instructions = list(log_method.get_code().get_bc().get_instructions())
        log_ins = [ins.get_output() for ins in log_instructions]
        self.assertTrue(any("RuntimeLogPolicy;->keepAutoPlayLog" in out for out in log_ins))
        self.assertTrue(any(ins.get_name() == "if-nez" for ins in log_instructions))
        self.assertFalse(any(ins.get_name() == "if-eqz" for ins in log_instructions))
        self.assertTrue(any("writeFile" in out for out in log_ins))

        log_err_method = find_method(auto_cls, "logErr")
        self.assertIsNotNone(log_err_method)
        log_err_ins = [ins.get_output() for ins in log_err_method.get_code().get_bc().get_instructions()]
        self.assertFalse(any("RuntimeLogPolicy" in out for out in log_err_ins))

        _, xlog_cls = self.classes["Lhun/mesh/carwithenhance/utils/XLog;"]
        info_method = find_method(xlog_cls, "i")
        self.assertIsNotNone(info_method)
        info_instructions = list(info_method.get_code().get_bc().get_instructions())
        info_ins = [ins.get_output() for ins in info_instructions]
        self.assertTrue(any("RuntimeLogPolicy;->keepXLog" in out for out in info_ins))
        self.assertTrue(any(ins.get_name() == "if-nez" for ins in info_instructions))
        self.assertFalse(any(ins.get_name() == "if-eqz" for ins in info_instructions))

        for descriptor in ("Ljava/lang/String;)V", "Ljava/lang/String; Ljava/lang/Throwable;)V"):
            error_method = find_method(xlog_cls, "e", descriptor)
            self.assertIsNotNone(error_method, descriptor)
            error_ins = [ins.get_output() for ins in error_method.get_code().get_bc().get_instructions()]
            self.assertFalse(any("RuntimeLogPolicy" in out for out in error_ins), descriptor)


    def test_net_ease_tab_memory_is_disabled(self) -> None:
        _, cls = self.classes["Lhun/mesh/carwithenhance/hook/MediaTabSelectionHook;"]
        method = find_method(cls, "isNetEaseTabMemoryDisabled")
        self.assertIsNotNone(method)
        dex = self.dexes[self.classes["Lhun/mesh/carwithenhance/hook/MediaTabSelectionHook;"][0]]
        self.assertIn("com.netease.cloudmusic", set(dex.get_strings()))
        for name in ("rememberCurrentItem", "restoreSelection", "scheduleRestore"):
            target = find_method(cls, name)
            self.assertIsNotNone(target)
            instructions = [ins.get_output() for ins in target.get_code().get_bc().get_instructions()]
            self.assertTrue(any("isNetEaseTabMemoryDisabled" in out for out in instructions), name)

    def test_qishui_fallback_uses_explicit_media_play_receiver(self) -> None:
        dex_name, cls = self.classes["LQishuiMediaKeyFallback;"]
        method = find_method(cls, "resumeIfNeeded", "Ljava/lang/String;")
        self.assertIsNotNone(method)
        self.assertEqual(
            method.get_descriptor(),
            "(Landroid/content/Context; Ljava/lang/String;)V",
        )
        instructions = [ins.get_output() for ins in method.get_code().get_bc().get_instructions()]
        self.assertTrue(any("QishuiMediaKeyFallbackPolicy;->shouldDispatch" in ins for ins in instructions))
        strings = set(self.dexes[dex_name].get_strings())
        self.assertIn("android.intent.action.MEDIA_BUTTON", strings)
        self.assertIn("com.luna.music", strings)
        self.assertIn("com.luna.biz.playing.player.BachMediaButtonReceiver", strings)
    def test_main_hook_installs_success_latched_resume(self) -> None:
        dex_name, cls = self.classes["Lhun/mesh/carwithenhance/MainHook$1;"]
        method = find_method(cls, "beforeHookedMethod")
        self.assertIsNotNone(method)
        instructions = [ins.get_output() for ins in method.get_code().get_bc().get_instructions()]
        self.assertTrue(any("AllAppCarResume;->install" in ins for ins in instructions))

    def test_car_mic_hook_is_present_and_wired(self) -> None:
        self.assertIn("LCarMicHook;", self.classes)
        _, main_hook = self.classes["Lhun/mesh/carwithenhance/MainHook;"]
        method = find_method(main_hook, "handleLoadPackage")
        self.assertIsNotNone(method)
        outputs = [ins.get_output() for ins in method.get_code().get_bc().get_instructions()]
        self.assertTrue(any("LCarMicHook;->install" in out for out in outputs))
        _, helper = self.classes["LCarMicHook;"]
        strings = set(self.dexes[self.classes["LCarMicHook;"][0]].get_strings())
        self.assertIn("com.baidu.carlife.mixing.wrappers.SystemAudioCallbackWrapper", strings)
        self.assertIn("com.baidu.carlife.sdk.CarlifeCoreSDK", strings)
    def test_generic_auto_play_resolver_contract(self) -> None:
        _, cls = self.classes["Lhun/mesh/carwithenhance/dexkit/GenericAutoPlayHooks;"]
        method = find_method(cls, "resolveMediaAutoPlay")
        self.assertIsNotNone(method)
        self.assertEqual(method.get_descriptor(), "(Ljava/lang/ClassLoader;)[Ljava/lang/Object;")
    def test_helper_uses_261_resolver_without_default_package_duplicate(self) -> None:
        dex_name, cls = self.classes["LAllAppCarResume;"]
        dex = self.dexes[dex_name]
        strings = set(dex.get_strings())
        self.assertTrue(any("GenericAutoPlayHooks" in value for value in strings))
        self.assertNotIn("LCarWakeDexKitTargets;", self.classes)
        self.assertNotIn("LCarWakeDexKitFactory;", self.classes)
        self.assertNotIn("Lu0;", self.classes)
        self.assertNotIn("Lv0;", self.classes)

    def test_old_resume_hook_is_not_installed(self) -> None:
        dex_name, cls = self.classes["Lhun/mesh/carwithenhance/hook/AutoPlayAllHook;"]
        method = find_method(cls, "onHook")
        self.assertIsNotNone(method)
        instructions = [ins.get_output() for ins in method.get_code().get_bc().get_instructions()]
        self.assertFalse(any("resolveMediaAutoPlay" in ins for ins in instructions))
        self.assertFalse(any("hookAllMethods" in ins for ins in instructions))
        self.assertTrue(any("hookExperienceApps" in ins for ins in instructions))

    def test_prewarm_and_retry_are_restored(self) -> None:
        _, cls = self.classes["Lhun/mesh/carwithenhance/hook/AutoPlayAllHook;"]
        prewake = find_method(cls, "preWakeLastUsedApp")
        self.assertIsNotNone(prewake)
        prewake_out = [ins.get_output() for ins in prewake.get_code().get_bc().get_instructions()]
        self.assertTrue(any("prepareMediaSession" in out for out in prewake_out))
        self.assertTrue(any("startRetryLoop" in out for out in prewake_out))

        prepare = find_method(cls, "prepareMediaSession", "Ljava/lang/String; Z")
        self.assertIsNotNone(prepare)
        prepare_out = [ins.get_output() for ins in prepare.get_code().get_bc().get_instructions()]
        self.assertTrue(any("bindBrowseService" in out or "startPlayerService" in out
                            for out in prepare_out))

        retry = find_method(cls, "startRetryLoop")
        self.assertIsNotNone(retry)
        retry_out = [ins.get_output() for ins in retry.get_code().get_bc().get_instructions()]
        self.assertTrue(any("schedule" in out for out in retry_out))


    def test_panel_render_callback_is_absent(self) -> None:
        _, cls = self.classes["Lhun/mesh/carwithenhance/hook/AutoPlayAllHook;"]
        method = find_method(cls, "markPanelRendered")
        self.assertIsNotNone(method)
        outputs = [ins.get_output() for ins in method.get_code().get_bc().get_instructions()]
        self.assertFalse(any("AutoPlayAllHook$10" in out for out in outputs))
        self.assertNotIn("Lhun/mesh/carwithenhance/hook/AutoPlayAllHook$10;", self.classes)
    def test_native_target_rewrite_contract(self) -> None:
        _, dex = self.classes["LAllAppCarResume;"][0], self.dexes[self.classes["LAllAppCarResume;"][0]]
        strings = set(dex.get_strings())
        self.assertTrue(any("native auto-play target " in value for value in strings))
        self.assertTrue(any("installed native target rewrite" in value for value in strings))
        self.assertFalse(any("resume attempt " in value for value in strings))
        self.assertFalse(any("STATE_PLAYING confirmed" in value for value in strings))
        self.assertFalse(any("all remaining retries canceled" in value for value in strings))
        self.assertTrue(any("native auto-play suppressed, already playing " in value
                            for value in strings))
        _, resume_cls = self.classes["LAllAppCarResume;"]
        self.assertIsNotNone(find_method(resume_cls, "isPackagePlaying"))

        hook_classes = [
            cls for name, (_, cls) in self.classes.items()
            if name.startswith("LAllAppCarResume$")
        ]
        self.assertTrue(any(
            cls.get_superclassname() == "Lde/robv/android/xposed/XC_MethodHook;"
            for cls in hook_classes
        ))
        self.assertFalse(any(
            cls.get_superclassname() == "Lde/robv/android/xposed/XC_MethodReplacement;"
            for cls in hook_classes
        ))


    def test_sha256sums_file_matches_apk(self) -> None:
        digest = hashlib.sha256(APK_PATH.read_bytes()).hexdigest().upper()
        sums = (OUTPUT_DIR / "CarWithEnhance-2.6.3-prewake-fast-resume.SHA256SUMS.txt").read_text(
            encoding="ascii"
        )
        self.assertIn(digest, sums.upper())
        self.assertIn(APK_PATH.name, sums)

    def test_signature_schemes(self) -> None:
        sdk = Path(os.environ.get("ANDROID_SDK_ROOT", "D:/Android/Sdk"))
        apksigner = None
        for version_dir in sorted((sdk / "build-tools").iterdir(), key=lambda p: p.name, reverse=True):
            candidate = version_dir / "apksigner.bat"
            if candidate.is_file():
                apksigner = candidate
                break
        self.assertIsNotNone(apksigner)
        result = subprocess.run(
            ["cmd", "/c", str(apksigner), "verify", "--verbose", "--min-sdk-version", "23", str(APK_PATH)],
            text=True,
            capture_output=True,
        )
        self.assertEqual(result.returncode, 0, result.stdout + result.stderr)
        self.assertIn("Verified using v3 scheme", result.stdout)


if __name__ == "__main__":
    unittest.main(verbosity=2)
