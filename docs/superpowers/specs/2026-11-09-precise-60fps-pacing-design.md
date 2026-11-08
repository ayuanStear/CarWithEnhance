# CarLife 精确 60FPS 帧调度设计

## 目标

修正 CarLife 将 60FPS 按整数毫秒排程为约 62.5FPS 的问题，减少编码器
输入超速、输出背压和车机端不均匀帧，同时保持其他用户帧率档位原样。

## 已确认根因

- CarLife 8.8.5 在
  `com.baidu.carlife.core.screen.video.Recorder#changeFrameRate(int)` 中执行
  `mDestFrameInterval = 1000 / fps`。
- 目标为 60FPS 时，该整数结果为 `16ms`，对应约 62.5FPS。
- `FrameRender$RenderHandler#renderDisplayMsg(long)` 使用该值安排下一帧。
- 长滑 Perfetto 采样显示编码输入约 62–63FPS，而硬件编码器部分统计窗口仅
  约 52–53FPS，说明输入节拍与编码器声明速率不一致并产生背压。

## 方案

采用“精确累计时间 + 编码 Surface 固定帧率”的方案，不替换 CarLife 的编码器。

### 1. 新增 CarLife 作用域

- 在 LSPosed scope 中新增 `com.baidu.carlife.xiaomi`。
- 在 `MainHook` 中增加 CarLife 分支。
- 在 CarLife `Application.onCreate` 后解析目标并挂载帧调度 Hook。
- 用户升级模块后必须在 LSPosed 中重新勾选 CarLife 作用域并重启 CarLife。

### 2. 纯策略帧时钟

新增可单测的 `FramePacerPolicy`，只负责计算下一帧延迟：

- 以 `SystemClock.elapsedRealtimeNanos()` 为时钟。
- 目标步长使用纳秒计算：`1_000_000_000 / fps`。
- 用绝对 deadline 消除长期漂移，60FPS 自然产生 16/17ms 交替延迟。
- 如果单帧迟到超过一个目标周期，跳过错过的帧并重置到当前时间，不连续补发，
  避免瞬时向编码器灌入两帧。
- 只在目标帧率变化时重置相位；相同帧率重复调用不重置。
- 支持任意 `15/20/30/50/60FPS`，但本次重点验证 60FPS。

### 3. 调度 Hook

对 `FrameRender$RenderHandler#renderDisplayMsg(long)` 做替换式 Hook：

- 精确调度可用时，不执行原始 `interval - elapsed` 整数毫秒算法。
- 由 `FramePacerPolicy` 计算延迟后，通过当前 `Handler` 发送
  `MSG_RENDER_DISPLAY=2`。
- Hook 仅当用户启用精确调度且目标帧率不小于 15 时生效。
- 用户选择 30/20/15 时保持 CarLife 原始逻辑；选择 60 时使用精确调度。

### 4. 编码 Surface 帧率

在 `Recorder#changeFrameRate(int)` 和连接初始化完成后，取得
`mEncSurface` 并调用：

```java
surface.setFrameRate(
        fps,
        Surface.FRAME_RATE_COMPATIBILITY_FIXED_SOURCE
);
```

- 仅在 API 30 及以上调用；模块 minSdk 29 保持不变。
- 用于向 SurfaceFlinger 声明固定生产者帧率，辅助系统按 60Hz 调度。
- Surface 尚未创建时不报错，在连接初始化 Hook 中再次补齐。

### 5. 跨版本解析和降级

解析顺序：

1. 首选当前已验证的类名和方法名。
2. 使用 DexKit 按字符串和方法签名定位，例如
   `changeFrameRate frame===:`、`void(int)`。
3. 如果只能找到 `changeFrameRate`，但无法找到精确调度方法：
   - 60FPS 时把调度间隔降级为 `17ms`，约 58.8FPS；
   - 保持 `Surface.setFrameRate(60)`；
   - 日志明确标记 `DEGRADED_17MS`。

降级模式宁可略低于 60FPS，也不继续产生 62.5FPS 的超速输入。

### 6. 开关

新增设置项：

```text
key: hook_precise_60fps_enabled
默认值: true
标题: 精确60FPS帧调度
说明: 修正CarLife 16ms整数排程导致的62.5FPS超速，减少编码背压与顿帧
```

- 复用现有 `HookConfigs` 和设置 UI 注入逻辑。
- 开关值继续同步到 `Settings.Global`，供 CarLife 进程读取。
- CarLife 进程启动时读取一次；修改后重新连接 CarWith 或重启 CarLife 生效。

## 非目标

- 不强制车机支持 1080p60；车机解码能力不足时仍需 30FPS 兼容回退。
- 不修改默认分辨率、码率、Profile、Level 或编码器实现。
- 不改变官方 30/20/15FPS 档位的行为。
- 不靠修改码率掩盖编码队列问题。
- 不在每一帧打印日志。

## 测试

### 单元测试

- 60FPS 运行 60 秒产生 3600 个目标周期，累计误差小于 1ms。
- 60FPS 延迟序列长期平均为 16.6667ms，并包含 16/17ms 组合。
- 迟到超过一个周期时只跳帧，不连续补发。
- 相同帧率重复设置不重置 deadline。
- 帧率从 60 切到 30 后立即重置相位。
- 30/20/15FPS 输出正确目标间隔。

### 构建测试

- `assembleRelease` 成功。
- JUnit 策略测试全部通过。
- 静态检查确认 CarLife scope、MainHook 分支、设置项和默认值均存在。

### 真机验证

前置条件：

- LSPosed 同时启用 `com.miui.carlink`、`android`、`com.baidu.carlife.xiaomi`。
- CarWith 用户帧率选择索引 3，即官方 60FPS。

使用 10 秒连续滑动，抓取 Perfetto：

- `eglSwapBuffers` 一秒窗口稳定在 59–61 帧。
- 10 秒平均帧率 59.7–60.3FPS。
- 帧间隔长期平均 16.667ms，无持续漂移。
- 不再出现全部间隔集中在约 16.0ms、折合 62.5FPS 的旧模式。
- MediaCodec 5 秒输出窗口应接近输入目标，且不劣于修复前。
- TCP 视频连接 send-q 不持续增长。

如果精确输入已经达到 60FPS，但车机仍明显卡顿，则记录为车机解码或链路容量
问题，不在本修复中继续提高手机端发送速率。

## 风险

- 新增 CarLife 作用域需要用户手动授权。
- 不同 CarLife 版本的内部类名可能变化，依赖 DexKit 和降级路径。
- Surface 固定帧率是系统调度提示，不保证所有厂商设备严格按 60Hz 呈现。
- CarLife 动态切换帧率时可能短暂重置相位，下一帧应重新建立 deadline。

## 验收

- 手机端编码输入从约 62.5FPS 收敛到约 60FPS。
- 60FPS 档位的可见顿挫减少，且不改变分辨率与画质。
- 关闭新增开关后行为回到当前原版 CarLife。
- 30/20/15FPS 档位行为不受影响。
