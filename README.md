# CarWith Enhance · 断开后延迟关闭音乐

基于 **CarWith Enhance 2.2.0（disconnect-kill-music）** 的二次修改版本。

把原有的「断开连接后关闭音乐」行为改成：

> **断开连接后延迟 N 秒再关闭音乐播放器；如果播放器在前台或正在播放，则跳过关闭。**

延迟秒数可在 CarWith 设置面板里手动输入（默认 5 秒，0 = 立即）。

---

## 功能说明

- **延迟关闭**：CarWith 断开后不再立即强杀音乐播放器，而是延迟指定秒数后再关闭。
- **前台跳过**：如果目标播放器进程处于前台（`ActivityManager` 的 `IMPORTANCE_FOREGROUND`），跳过关闭。
- **播放中跳过**：如果目标播放器正在播放（`MediaSession` 的 `PlaybackState.STATE_PLAYING`），跳过关闭。
- **设置面板**：
  - 保留原有「断开连接后关闭音乐」总开关。
  - 新增「断开后延迟关闭秒数」输入框：
    - 显示当前值（摘要 + 输入框回显）。
    - 可手动输入，自动只保留数字（非数字会被去除，空输入按 0）。
    - 总开关关闭时自动隐藏。

---

## 改动文件（smali 补丁）

以下为反编译后修改/新增的 smali 文件（已加注释）：

| 文件 | 说明 |
| --- | --- |
| `CarLinkStateHook.smali` | 断开入口改为延迟调度；新增 `doCloseMusicPlayers()` 实际关闭逻辑；新增桥接方法 `c` |
| `CarLinkStateHook$3.smali` | 新增：延迟关闭用的 `Runnable` |
| `AutoPlayAllHook.smali` | 新增 `isForeground()` 与 `shouldSkipDisconnectKill()` |
| `SettingsHook.smali` | 新增静态字段 `sDelayPref` 与 `setDelayPrefVisible()` |
| `SettingsHook$4.smali` | 设置面板新增「断开后延迟关闭秒数」输入框 |
| `SettingsHook$4$1.smali` | 总开关变化时联动隐藏/显示延迟输入框 |
| `SettingsHook$4$2.smali` | 新增：延迟输入框的 `OnPreferenceChangeListener`（数字过滤 + 存值 + 更新摘要） |

### 核心存储键

- `hook_disconnect_kill_music_enabled`（布尔，总开关）
- `hook_disconnect_kill_delay_sec`（整数，延迟秒数，默认 5）

---

## 代码逻辑概述

### 1. 延迟调度（`CarLinkStateHook.closeMusicPlayerIfNeeded()`）

断开时读取总开关与延迟秒数，然后用 `Handler.postDelayed` 延迟执行真正关闭：

```
延迟 ms = hook_disconnect_kill_delay_sec * 1000
```

### 2. 真正关闭（`CarLinkStateHook.doCloseMusicPlayers()`）

遍历 `AutoPlayAllHook.collectDisconnectTargets()` 返回的播放器包名，逐个判断：

```
if (shouldSkipDisconnectKill(pkg))  -> 跳过
else                                -> forceStop(pkg, "disconnect")
```

### 3. 跳过判断（`AutoPlayAllHook.shouldSkipDisconnectKill()`）

```
return isForeground(pkg) || isPlaying(pkg);
```

- `isForeground`：`ActivityManager.getRunningAppProcesses()` 中 `importance == IMPORTANCE_FOREGROUND(100)`。
- `isPlaying`：`MediaSessionManager.getActiveSessions()` 中 `PlaybackState.getState() == STATE_PLAYING(3)`。

---

## 如何重新打包

> 需要：`apktool`、`uber-apk-signer`（或 `apksigner`）、Java。

1. 准备原始 APK：`CarWithEnhance 2.2.0-disconnect-kill-music`。
2. 反编译：

   ```bash
   java -jar apktool.jar d -f CarWithEnhance.apk -o decoded
   ```

3. 用本仓库 `smali/` 下的文件覆盖 `decoded/smali*` 对应路径：

   - `smali/hun/mesh/carwithenhance/hook/*`（除 `AutoPlayAllHook.smali`）
   - `smali_classes2/hun/mesh/carwithenhance/hook/AutoPlayAllHook.smali`

4. 重新打包：

   ```bash
   java -jar apktool.jar b decoded -o patched.apk
   ```

5. 签名（v1/v2/v3）：

   ```bash
   java -jar uber-apk-signer.jar -a patched.apk --allowResign
   ```

6. 安装到手机（需 LSPosed / root）：

   ```bash
   adb install -r patched-aligned-debugSigned.apk
   # 或在 MIUI/HyperOS 被限制时：
   # adb push patched-aligned-debugSigned.apk /data/local/tmp/app.apk
   # adb shell su -c "pm install -r /data/local/tmp/app.apk"
   ```

7. 在 LSPosed 里启用模块并勾选作用域（`com.miui.carlink`、`系统框架/system`），重启生效。

---

## 使用方法

1. 打开 CarWith 手机端设置 → **CarWith Enhance** 分类。
2. 「断开连接后关闭音乐」= 总开关（关闭后延迟输入框隐藏）。
3. 「断开后延迟关闭秒数」= 手动输入延迟秒数（0 = 立即，默认 5）。

---

## 免责声明

本项目仅用于个人学习与自用手机（小米 CarWith）功能增强。原始模块 `CarWith Enhance` 由 `Mesh_Hun` 开发；本仓库仅基于其 2.2.0 版本做局部功能修改。