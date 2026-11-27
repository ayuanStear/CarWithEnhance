#!/usr/bin/env python3
"""Build the 2.6.2 native-resume module with the Qishui stopped-package fallback."""

from __future__ import annotations

import hashlib
import json
import os
import re
import shutil
import subprocess
import sys
import time
import zipfile
from pathlib import Path
from xml.etree import ElementTree as ET

if hasattr(sys.stdout, "reconfigure"):
    sys.stdout.reconfigure(encoding="utf-8", errors="backslashreplace")
    sys.stderr.reconfigure(encoding="utf-8", errors="backslashreplace")

ROOT = Path(__file__).resolve().parents[1]
SAMPLES = ROOT / "samples"
TOOLS = ROOT / "tools"
WORK = ROOT / "work" / "261-resume-lite" / "build-current"
OUTPUT = ROOT / "output"
KEYS = ROOT / "keys"
BASE_APK = SAMPLES / "CarWithEnhance-2.6.1-last-app.apk"
APKTOOL = TOOLS / "apktool_2.12.1.jar"
XPOSED_API = TOOLS / "xposed-api-82.jar"
KEYSTORE = Path(os.environ.get("CARWITHX_KEYSTORE", str(KEYS / "carwithx-release.jks")))
KEY_ALIAS = os.environ.get("CARWITHX_KEY_ALIAS", "carwithx")
KEY_PASSWORD = os.environ.get("CARWITHX_KEY_PASSWORD", "")
FINAL_APK = OUTPUT / "CarWithEnhance-2.6.3-prewake-fast-resume.apk"
PACKAGE_NAME = "hun.mesh.carwithenhance"
VERSION_CODE = "74"
VERSION_NAME = "2.6.3-qishui-carmic-tabfix-prewake-fast-resume"
ANDROID_NS = "http://schemas.android.com/apk/res/android"
A = f"{{{ANDROID_NS}}}"

HELPER_SOURCES = [
    ROOT / "sources" / "carwake-dexkit" / "AllAppCarResume.java",
    ROOT / "sources" / "carwake-dexkit" / "NativeResumePolicy.java",
    ROOT / "sources" / "carwake-dexkit" / "QishuiMediaKeyFallbackPolicy.java",
    ROOT / "sources" / "carwake-dexkit" / "QishuiMediaKeyFallback.java",
    ROOT / "sources" / "carwake-dexkit" / "CarMicHook.java",
    ROOT / "sources" / "carwake-dexkit" / "RuntimeLogPolicy.java",
]
HELPER_STUBS = ROOT / "stubs" / "dexkit-api"


def log(message: str) -> None:
    print(f"[261-resume-lite] {message}", flush=True)


def run(command: list[str], cwd: Path | None = None) -> subprocess.CompletedProcess[str]:
    log(" ".join(command))
    proc = subprocess.run(
        command,
        cwd=str(cwd) if cwd else None,
        text=True,
        encoding="utf-8",
        errors="backslashreplace",
        capture_output=True,
    )
    if proc.stdout.strip():
        print(proc.stdout.rstrip(), flush=True)
    if proc.returncode != 0:
        if proc.stderr.strip():
            print(proc.stderr.rstrip(), file=sys.stderr, flush=True)
        raise RuntimeError(f"command failed ({proc.returncode})")
    if proc.stderr.strip():
        print(proc.stderr.rstrip(), flush=True)
    return proc


def safe_new_directory(path: Path) -> None:
    resolved = path.resolve()
    allowed = (ROOT / "work" / "261-resume-lite").resolve()
    if allowed not in resolved.parents:
        raise RuntimeError(f"unsafe work directory: {resolved}")
    if resolved.exists():
        shutil.rmtree(resolved)
    resolved.mkdir(parents=True, exist_ok=True)


def find_android_tool(name: str) -> Path:
    sdk = Path(os.environ.get("ANDROID_SDK_ROOT", Path.home() / "AppData/Local/Android/Sdk"))
    for version_dir in sorted((sdk / "build-tools").iterdir(), key=lambda p: p.name, reverse=True):
        for suffix in (".exe", ".bat", ""):
            candidate = version_dir / f"{name}{suffix}"
            if candidate.is_file():
                return candidate
    raise FileNotFoundError(f"Android build tool not found: {name}")


def replace_method(text: str, signature: str, replacement: str) -> str:
    wrapper = f".method {signature}"
    start = text.find(wrapper)
    if start < 0:
        raise RuntimeError(f"method not found: {signature}")
    end = text.find(".end method", start)
    if end < 0:
        raise RuntimeError(f"method end not found: {signature}")
    end += len(".end method")
    return text[:start] + replacement.rstrip() + text[end:]


def compile_helper() -> Path:
    sdk = Path(os.environ.get("ANDROID_SDK_ROOT", Path.home() / "AppData/Local/Android/Sdk"))
    android_jar = sdk / "platforms" / "android-36" / "android.jar"
    javac = Path(shutil.which("javac") or "javac")
    jar = Path(shutil.which("jar") or "jar")
    d8 = find_android_tool("d8")
    if not android_jar.is_file():
        raise FileNotFoundError(android_jar)
    if not XPOSED_API.is_file():
        raise FileNotFoundError(XPOSED_API)
    for source in HELPER_SOURCES:
        if not source.is_file():
            raise FileNotFoundError(source)

    stub_src = HELPER_STUBS / "defpackage"
    stub_out = WORK / "helper-stubs"
    helper_out = WORK / "helper-classes"
    d8_out = WORK / "helper-d8"
    stub_out.mkdir(parents=True, exist_ok=True)
    helper_out.mkdir(parents=True, exist_ok=True)
    d8_out.mkdir(parents=True, exist_ok=True)

    stub_files = sorted(stub_src.glob("*.java"))
    if not stub_files:
        raise RuntimeError(f"no stub sources under {stub_src}")
    run([str(javac), "-source", "8", "-target", "8", "-encoding", "UTF-8", "-d", str(stub_out), *map(str, stub_files)])
    classpath = os.pathsep.join((str(android_jar), str(stub_out), str(XPOSED_API)))
    run([str(javac), "-source", "8", "-target", "8", "-encoding", "UTF-8", "-cp", classpath, "-d", str(helper_out), *map(str, HELPER_SOURCES)])
    helper_jar = WORK / "resume-lite-helper.jar"
    run([str(jar), "cf", str(helper_jar), "-C", str(helper_out), "."])
    run([
        "cmd", "/c", str(d8), "--min-api", "29", "--lib", str(android_jar),
        "--classpath", str(stub_out), "--classpath", str(XPOSED_API),
        "--output", str(d8_out), str(helper_jar),
    ])
    result = d8_out / "classes.dex"
    if not result.is_file():
        raise RuntimeError("d8 did not produce helper classes.dex")
    return result


def patch_manifest(path: Path) -> None:
    ET.register_namespace("android", ANDROID_NS)
    tree = ET.parse(path)
    root = tree.getroot()
    if root.get("package") != PACKAGE_NAME:
        raise RuntimeError("unexpected base package")
    root.set(A + "versionCode", VERSION_CODE)
    root.set(A + "versionName", VERSION_NAME)
    app = root.find("application")
    if app is None:
        raise RuntimeError("manifest application missing")
    for meta in app.findall("meta-data"):
        if meta.get(A + "name") == "xposeddescription":
            meta.set(A + "value", "CarWith增强稳定性优化版 · 上车续播 · 必要日志")
    tree.write(path, encoding="utf-8", xml_declaration=True)


def patch_main_hook_dispatch(smali: Path) -> None:
    path = smali / "hun" / "mesh" / "carwithenhance" / "MainHook.smali"
    text = path.read_text(encoding="utf-8")
    anchor = "    invoke-static {p1}, Lhun/mesh/carwithenhance/hook/CarWakeVoiceHook;->install(Lde/robv/android/xposed/callbacks/XC_LoadPackage$LoadPackageParam;)V"
    insert = (
        "    const-string v1, \"com.baidu.carlife.xiaomi\"\n\n"
        "    invoke-virtual {v0, v1}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z\n\n"
        "    move-result v1\n\n"
        "    if-eqz v1, :cond_carmic\n\n"
        "    iget-object v1, p1, Lde/robv/android/xposed/callbacks/XC_LoadPackage$LoadPackageParam;->classLoader:Ljava/lang/ClassLoader;\n\n"
        "    invoke-static {v1}, LCarMicHook;->install(Ljava/lang/ClassLoader;)V\n\n"
        "    return-void\n\n"
        "    :cond_carmic\n\n"
        "    invoke-static {p1}, Lhun/mesh/carwithenhance/hook/CarWakeVoiceHook;->install(Lde/robv/android/xposed/callbacks/XC_LoadPackage$LoadPackageParam;)V"
    )
    if anchor not in text:
        raise RuntimeError("MainHook CarWakeVoiceHook dispatch anchor missing")
    text = text.replace(anchor, insert, 1)
    path.write_text(text, encoding="utf-8")


def patch_main_hook(smali: Path) -> None:
    path = smali / "hun" / "mesh" / "carwithenhance" / "MainHook$1.smali"
    text = path.read_text(encoding="utf-8")
    replacement = '''.method public beforeHookedMethod(Lde/robv/android/xposed/XC_MethodHook$MethodHookParam;)V
    .locals 6

    iget-object p1, p1, Lde/robv/android/xposed/XC_MethodHook$MethodHookParam;->thisObject:Ljava/lang/Object;

    check-cast p1, Landroid/app/Application;

    sget-object v0, Lhun/mesh/carwithenhance/dexkit/CarWithDexKitManager;->INSTANCE:Lhun/mesh/carwithenhance/dexkit/CarWithDexKitManager;

    iget-object v1, p0, Lhun/mesh/carwithenhance/MainHook$1;->val$loadPackageParam:Lde/robv/android/xposed/callbacks/XC_LoadPackage$LoadPackageParam;

    invoke-virtual {v0, v1, p1}, Lhun/mesh/carwithenhance/dexkit/CarWithDexKitManager;->initAndResolve(Lde/robv/android/xposed/callbacks/XC_LoadPackage$LoadPackageParam;Landroid/content/Context;)V

    sget-object v0, Lhun/mesh/carwithenhance/media/CarWithMediaCapabilities;->INSTANCE:Lhun/mesh/carwithenhance/media/CarWithMediaCapabilities;

    iget-object v1, p0, Lhun/mesh/carwithenhance/MainHook$1;->val$classLoader:Ljava/lang/ClassLoader;

    invoke-virtual {v0, p1, v1}, Lhun/mesh/carwithenhance/media/CarWithMediaCapabilities;->resolve(Landroid/content/Context;Ljava/lang/ClassLoader;)V

    iget-object v0, p0, Lhun/mesh/carwithenhance/MainHook$1;->val$classLoader:Ljava/lang/ClassLoader;

    invoke-static {v0}, LAllAppCarResume;->install(Ljava/lang/ClassLoader;)V

    const/16 v0, 0x6

    new-array v1, v0, [Lhun/mesh/carwithenhance/hook/IHook;

    new-instance v2, Lhun/mesh/carwithenhance/hook/AutoPlayHook;

    invoke-direct {v2}, Lhun/mesh/carwithenhance/hook/AutoPlayHook;-><init>()V

    const/4 v3, 0x0

    aput-object v2, v1, v3

    new-instance v2, Lhun/mesh/carwithenhance/hook/AutoPlayAllHook;

    invoke-direct {v2}, Lhun/mesh/carwithenhance/hook/AutoPlayAllHook;-><init>()V

    const/4 v4, 0x1

    aput-object v2, v1, v4

    new-instance v2, Lhun/mesh/carwithenhance/hook/MediaRouteHook;

    invoke-direct {v2}, Lhun/mesh/carwithenhance/hook/MediaRouteHook;-><init>()V

    const/4 v4, 0x2

    aput-object v2, v1, v4

    new-instance v2, Lhun/mesh/carwithenhance/hook/MediaTabSelectionHook;

    invoke-direct {v2}, Lhun/mesh/carwithenhance/hook/MediaTabSelectionHook;-><init>()V

    const/4 v4, 0x3

    aput-object v2, v1, v4

    new-instance v2, Lhun/mesh/carwithenhance/hook/SettingsHook;

    invoke-direct {v2}, Lhun/mesh/carwithenhance/hook/SettingsHook;-><init>()V

    const/4 v4, 0x4

    aput-object v2, v1, v4

    new-instance v2, Lhun/mesh/carwithenhance/hook/CarLinkStateHook;

    invoke-direct {v2, p1}, Lhun/mesh/carwithenhance/hook/CarLinkStateHook;-><init>(Landroid/content/Context;)V

    const/4 v4, 0x5

    aput-object v2, v1, v4

    :goto_0
    if-ge v3, v0, :cond_0

    aget-object p1, v1, v3

    invoke-virtual {p1}, Ljava/lang/Object;->getClass()Ljava/lang/Class;

    move-result-object v2

    invoke-virtual {v2}, Ljava/lang/Class;->getSimpleName()Ljava/lang/String;

    move-result-object v2

    :try_start_0
    iget-object v4, p0, Lhun/mesh/carwithenhance/MainHook$1;->val$classLoader:Ljava/lang/ClassLoader;

    invoke-interface {p1, v4}, Lhun/mesh/carwithenhance/hook/IHook;->onHook(Ljava/lang/ClassLoader;)V
    :try_end_0
    .catchall {:try_start_0 .. :try_end_0} :catchall_0

    goto :goto_1

    :catchall_0
    move-exception p1

    new-instance v4, Ljava/lang/StringBuilder;

    const-string v5, ">> [CarWith Enhance] \\u6302\\u8f7d "

    invoke-direct {v4, v5}, Ljava/lang/StringBuilder;-><init>(Ljava/lang/String;)V

    invoke-virtual {v4, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v2

    const-string v4, " \\u5f02\\u5e38\\u5931\\u8d25: "

    invoke-virtual {v2, v4}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v2

    invoke-virtual {v2}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v2

    invoke-static {v2, p1}, Lhun/mesh/carwithenhance/utils/XLog;->e(Ljava/lang/String;Ljava/lang/Throwable;)V

    :goto_1
    add-int/lit8 v3, v3, 0x1

    goto :goto_0

    :cond_0
    return-void
.end method'''
    path.write_text(replace_method(text, "public beforeHookedMethod(Lde/robv/android/xposed/XC_MethodHook$MethodHookParam;)V", replacement), encoding="utf-8")


def patch_media_tab_selection(smali: Path) -> None:
    path = smali / "hun" / "mesh" / "carwithenhance" / "hook" / "MediaTabSelectionHook.smali"
    text = path.read_text(encoding="utf-8")
    helper = """.method private static isNetEaseTabMemoryDisabled()Z
    .locals 2

    invoke-static {}, Lhun/mesh/carwithenhance/hook/MediaTabSelectionHook;->currentMediaPackage()Ljava/lang/String;

    move-result-object v0

    const-string v1, "com.netease.cloudmusic"

    invoke-virtual {v1, v0}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v0

    return v0
.end method


"""
    anchor = ".method private static rememberCurrentItem(Ljava/lang/Object;)V"
    if helper not in text:
        if anchor not in text:
            raise RuntimeError("MediaTabSelectionHook rememberCurrentItem anchor missing")
        text = text.replace(anchor, helper + anchor, 1)
    guard = """    invoke-static {}, Lhun/mesh/carwithenhance/hook/MediaTabSelectionHook;->isNetEaseTabMemoryDisabled()Z

    move-result v0

    if-eqz v0, :cond_tab_memory_continue

    return-void

    :cond_tab_memory_continue
"""
    for signature, locals_count in (
        ("private static rememberCurrentItem(Ljava/lang/Object;)V", 6),
        ("private static restoreSelection(Ljava/lang/Object;Ljava/lang/String;)V", 10),
        ("private static scheduleRestore(Ljava/lang/Object;)V", 5),
    ):
        header = f".method {signature}\n    .locals {locals_count}\n\n"
        start = text.find(header)
        if start < 0:
            raise RuntimeError(f"MediaTabSelectionHook method header missing: {signature}")
        end = text.find(".end method", start)
        method = text[start:end]
        if "isNetEaseTabMemoryDisabled()Z" not in method:
            text = text[:start] + header + guard + "\n" + text[start + len(header):]
    path.write_text(text, encoding="utf-8")


def patch_auto_play_all(smali: Path) -> None:
    path = smali / "hun" / "mesh" / "carwithenhance" / "hook" / "AutoPlayAllHook.smali"
    text = path.read_text(encoding="utf-8")
    on_hook = '''.method public onHook(Ljava/lang/ClassLoader;)V
    .locals 1

    sput-object p1, Lhun/mesh/carwithenhance/hook/AutoPlayAllHook;->sCl:Ljava/lang/ClassLoader;

    invoke-static {p1}, Lhun/mesh/carwithenhance/hook/AutoPlayAllHook;->hookCastState(Ljava/lang/ClassLoader;)V

    invoke-static {p1}, Lhun/mesh/carwithenhance/hook/AutoPlayAllHook;->hookMediaAppOpen(Ljava/lang/ClassLoader;)V

    invoke-static {p1}, Lhun/mesh/carwithenhance/hook/AutoPlayAllHook;->hookDockIconPreservation(Ljava/lang/ClassLoader;)V

    invoke-static {p1}, Lhun/mesh/carwithenhance/hook/AutoPlayAllHook;->hookMediaAppPref(Ljava/lang/ClassLoader;)V

    invoke-static {p1}, Lhun/mesh/carwithenhance/hook/AutoPlayAllHook;->hookSessionAvailability(Ljava/lang/ClassLoader;)V

    invoke-static {p1}, Lhun/mesh/carwithenhance/hook/AutoPlayAllHook;->hookExperienceApps(Ljava/lang/ClassLoader;)V

    invoke-static {p1}, Lhun/mesh/carwithenhance/hook/AutoPlayAllHook;->hookOwnPrefs(Ljava/lang/ClassLoader;)V

    return-void
.end method'''
    text = replace_method(text, "public onHook(Ljava/lang/ClassLoader;)V", on_hook)

    # Keep the 2.6.1 pre-wake/resume path alive. Only the panel-render
    # callback is disabled because this variant resumes from MediaSession
    # metadata and no longer registers CarWithPanelReadyHook.
    noops = [
        ("public static preWakeForCarOpen(Ljava/lang/String;Ljava/lang/String;)V",
         '''.method public static preWakeForCarOpen(Ljava/lang/String;Ljava/lang/String;)V
    .locals 0

    return-void
.end method'''),
        ("static markPanelRendered(Ljava/lang/String;)V",
         '''.method static markPanelRendered(Ljava/lang/String;)V
    .locals 0

    return-void
.end method'''),
    ]
    for signature, replacement in noops:
        text = replace_method(text, signature, replacement)
    path.write_text(text, encoding="utf-8")


def patch_runtime_logs(smali: Path) -> None:
    auto_path = smali / "hun" / "mesh" / "carwithenhance" / "hook" / "AutoPlayAllHook.smali"
    auto_text = auto_path.read_text(encoding="utf-8")
    auto_signature = ".method public static log(Landroid/content/Context;Ljava/lang/String;)V\n    .locals 2\n"
    auto_guard = (
        auto_signature
        + "\n    invoke-static {p1}, LRuntimeLogPolicy;->keepAutoPlayLog(Ljava/lang/String;)Z\n\n"
        + "    move-result v0\n\n"
        + "    if-nez v0, :cond_log_keep\n\n"
        + "    return-void\n\n"
        + "    :cond_log_keep\n"
    )
    if auto_signature not in auto_text:
        raise RuntimeError("AutoPlayAllHook.log signature missing")
    if "keepAutoPlayLog" not in auto_text:
        auto_text = auto_text.replace(auto_signature, auto_guard, 1)
    auto_path.write_text(auto_text, encoding="utf-8")

    xlog_path = smali / "hun" / "mesh" / "carwithenhance" / "utils" / "XLog.smali"
    xlog_text = xlog_path.read_text(encoding="utf-8")
    xlog_signature = ".method public static i(Ljava/lang/String;)V\n    .locals 2\n"
    xlog_guard = (
        xlog_signature
        + "\n    invoke-static {p0}, LRuntimeLogPolicy;->keepXLog(Ljava/lang/String;)Z\n\n"
        + "    move-result v0\n\n"
        + "    if-nez v0, :cond_xlog_keep\n\n"
        + "    return-void\n\n"
        + "    :cond_xlog_keep\n"
    )
    if xlog_signature not in xlog_text:
        raise RuntimeError("XLog.i signature missing")
    if "keepXLog" not in xlog_text:
        xlog_text = xlog_text.replace(xlog_signature, xlog_guard, 1)
    xlog_path.write_text(xlog_text, encoding="utf-8")


def delete_removed_classes(smali: Path) -> None:
    hook_dir = smali / "hun" / "mesh" / "carwithenhance" / "hook"
    for pattern in ("LyricSourceHook*.smali", "CarWithPanelReadyHook*.smali"):
        for path in hook_dir.glob(pattern):
            path.unlink()
    lyric_policy = smali / "hun" / "mesh" / "carwithenhance" / "policy" / "LyricFallbackPolicy.smali"
    if lyric_policy.exists():
        lyric_policy.unlink()
    # Keep $11/$24: they are the retry scheduler and the delayed pause-key
    # runnable used by the restored pre-wake path. $1/$10 belonged to the old
    # native auto-play hook and panel-render callback, both intentionally gone.
    for name in ("AutoPlayAllHook$1.smali", "AutoPlayAllHook$10.smali"):
        path = hook_dir / name
        if path.exists():
            path.unlink()


def merge_apk(base_apk: Path, helper_dex: Path, output_apk: Path) -> None:
    helper = helper_dex.read_bytes()
    with zipfile.ZipFile(base_apk) as source, zipfile.ZipFile(output_apk, "w") as target:
        names = set(source.namelist())
        if "classes.dex" not in names:
            raise RuntimeError("base APK has no classes.dex")
        if "classes2.dex" in names:
            raise RuntimeError("base APK already has classes2.dex")
        for info in source.infolist():
            name = info.filename
            if name.startswith("META-INF/") and name.upper().endswith((".RSA", ".DSA", ".EC", ".SF", "MANIFEST.MF")):
                continue
            target.writestr(info, source.read(name))
        info = zipfile.ZipInfo("classes2.dex", time.localtime()[:6])
        info.compress_type = zipfile.ZIP_STORED
        info.external_attr = 0o644 << 16
        target.writestr(info, helper)


def build() -> int:
    for required in (BASE_APK, APKTOOL, XPOSED_API, KEYSTORE):
        if not required.is_file():
            raise FileNotFoundError(required)
    if not KEY_PASSWORD:
        raise RuntimeError("CARWITHX_KEY_PASSWORD is required")
    OUTPUT.mkdir(parents=True, exist_ok=True)
    safe_new_directory(WORK)

    helper_dex = compile_helper()
    base_dir = WORK / "base"
    run(["java", "-jar", str(APKTOOL), "d", "-f", str(BASE_APK), "-o", str(base_dir)])
    patch_manifest(base_dir / "AndroidManifest.xml")
    patch_main_hook_dispatch(base_dir / "smali")
    patch_main_hook(base_dir / "smali")
    patch_media_tab_selection(base_dir / "smali")
    patch_auto_play_all(base_dir / "smali")
    patch_runtime_logs(base_dir / "smali")
    delete_removed_classes(base_dir / "smali")

    rebuilt = WORK / "rebuilt-unsigned.apk"
    run(["java", "-jar", str(APKTOOL), "b", str(base_dir), "-o", str(rebuilt)])
    merged = WORK / "merged-unsigned.apk"
    merge_apk(rebuilt, helper_dex, merged)

    zipalign = find_android_tool("zipalign")
    aligned = WORK / "aligned.apk"
    run([str(zipalign), "-f", "-P", "16", "4", str(merged), str(aligned)])

    apksigner = find_android_tool("apksigner")
    if FINAL_APK.exists():
        FINAL_APK.unlink()
    run([
        "cmd", "/c", str(apksigner), "sign",
        "--ks", str(KEYSTORE), "--ks-key-alias", KEY_ALIAS,
        "--ks-pass", f"pass:{KEY_PASSWORD}", "--key-pass", f"pass:{KEY_PASSWORD}",
        "--v1-signing-enabled", "true", "--v2-signing-enabled", "true",
        "--v3-signing-enabled", "true", "--v4-signing-enabled", "false",
        "--out", str(FINAL_APK), str(aligned),
    ])
    run([str(zipalign), "-c", "-P", "16", "4", str(FINAL_APK)])
    run(["cmd", "/c", str(apksigner), "verify", "--verbose", "--min-sdk-version", "29", "--print-certs", str(FINAL_APK)])

    digest = hashlib.sha256(FINAL_APK.read_bytes()).hexdigest()
    info = {
        "output": str(FINAL_APK),
        "sha256": digest,
        "size": FINAL_APK.stat().st_size,
        "package": PACKAGE_NAME,
        "versionCode": int(VERSION_CODE),
        "versionName": VERSION_NAME,
        "removed": ["LyricSourceHook", "LyricFallbackPolicy", "CarWithPanelReadyHook", "ResumeGate", "routine hook/status/audio-state log noise"],
        "resume": "restored 2.6.1 pre-wake + metadata resume; native target rewrite kept as fallback; native auto-play suppressed when the saved player is already playing; Qishui stopped-package media-key fallback; CarLife VR channel requests head-unit microphone",
        "logging": "routine hook/status/audio-state logs filtered; errors and key media/microphone events retained",
        "built_at": time.strftime("%Y-%m-%dT%H:%M:%S%z"),
    }
    (OUTPUT / f"{FINAL_APK.stem}.build.json").write_text(json.dumps(info, ensure_ascii=False, indent=2) + "\n", encoding="utf-8")
    (OUTPUT / f"{FINAL_APK.stem}.SHA256SUMS.txt").write_text(
        f"{digest}  {FINAL_APK.name}\n", encoding="ascii"
    )
    log(f"FINAL: {FINAL_APK}")
    log(f"SHA256: {digest}")
    return 0


if __name__ == "__main__":
    try:
        raise SystemExit(build())
    except Exception as exc:
        print(f"BUILD FAILED: {exc}", file=sys.stderr)
        raise
