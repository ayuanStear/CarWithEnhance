# CarWithEnhance 媒体问题基线审计

> 本文是 2.4.0 重构前的根因审计。对应修复与验证请参阅 `IMPLEMENTATION.md` 和 `VERIFICATION.md`。

## 1. 结论摘要

本轮以手机当前安装版本、精确反编译源码和 2026-11-08 实车日志为依据：

- CarWithEnhance：`2.3.9-resume-cleanup`，`versionCode=42`
- CarWith：`4.0.23-20261016`，`versionCode=104000023`
- 网易云音乐：`9.6.22`
- Android：17 / SDK 37

四个用户问题的判断如下：

| ID | 问题 | 结论 | 证据等级 |
|---|---|---|---|
| U-001 | 上车续播后歌曲已播放，控制面板未刷新 | 增强模块的面板同步去重只看包名和 150 ms，首轮同步可能拿到旧/空元数据，真正的新元数据随后被丢弃 | 源码可证，待补快照日志实车确认 |
| U-002 | 同包切歌后控制面板未刷新 | 切歌不改变包名；新歌曲元数据在 150 ms 内到达时会被旧包名去重条件吞掉 | 源码可证，待补快照日志实车确认 |
| U-003 | 网易云定制页偶发被原生页替换 | 当前 v42 不包含主动切换媒体定制页的 Hook；CarWith 官方路由依据 `MediaServiceMgr.i(pkg)` 动态判断，条件短暂不满足时会进入原生 embedding 页面 | 源码可证；本次日志未捕获事故时刻的路由事件 |
| U-004 | 歌词晚于实际播放进度 | CarWith 定制卡片页的每秒进度回调只更新 SeekBar 和时间文本，不调用歌词时间轴；歌词只在 Metadata 回调时定位 | 源码直接证实 |

另外，本次日志还确认：

- 连接后约 12 秒会在音乐已经播放时再次下发续播。
- 当天“播放器已播放，控制面板同步”出现 288 次，其中网易云 233 次。
- 一键清理时间写入失败，清理后的本次连接抑制实际未生效。
- 断连延迟任务可能同时停止网易云和 Luna 两个播放器。
- 切换播放器时，旧播放器音频仍活动就可能被 force-stop。

## 2. 面板不刷新与同包切歌

### 2.1 现有数据流

CarWith 官方链路：

```text
MediaController.Callback
  -> na.c#t0(metadata)
  -> pa.b#t(pkg, metadata)
  -> la.d#j(pkg, metadata)
  -> BaseMusicView 更新标题、封面和歌词

MediaController.Callback
  -> na.c#u0(state)
  -> pa.b#u(pkg, state)
  -> la.d#k(pkg, state)
  -> BaseMusicView 更新播放状态
```

增强模块当前额外执行：

```text
MediaController.Callback
  -> AutoPlayAllHook#onMediaActuallyPlaying/onMediaMetadataChanged
  -> refreshCarWithMediaState
  -> ma.a#c(controller)
  -> ma.a#f(pkg)
  -> pa.b#d(pkg)
  -> pa.b#h(pkg)
```

`pa.b#d(pkg)` 会让已注册的 `la.c` 回调重新执行
`BaseMusicView#v(pkg)`。该函数重新绑定 `na.c`，随后
`na.c#q0(callback)` 会把当前 metadata、playback state、封面、进度和
歌词一次性推给视图。因此刷新入口本身是正确的，问题在“何时允许刷新”。

### 2.2 根因

源码：

- `AutoPlayAllHook.java:1010-1012`
- `AutoPlayAllHook.java:983-992`
- `AutoPlayAllHook.java:949-957`

当前去重条件是：

```java
if (pkg.equals(sLastPanelSyncPkg)
        && startedAt - sLastPanelSyncAt < 150L) {
    return;
}
```

这个条件只比较包名，不比较歌曲、元数据或播放状态。以下事件通常几乎同时
到达：

1. `PlaybackState=PLAYING`，但 metadata 仍为空或上一首。
2. 新歌曲 metadata。
3. 新封面。
4. 新手歌词。

第 1 条先占用 150 ms 窗口，第 2 至 4 条对新视图最关键的更新随后被同一个
包名去重直接丢弃。如果之后播放器不再产生 Metadata 回调，视图就会长期停留
在旧歌或空状态。

上车续播还有第二个放大因素：

```text
播放状态先变为 PLAYING
  -> 第一次强制刷新
  -> 此时 CarWith 的 na.c 尚未收齐 metadata
  -> 150 ms 内真正 metadata 到达
  -> 被去重
```

同包切歌的机制与上车续播相同，而且更稳定：包名始终不变，因此不能把包名
当作“是否为新内容”的标识。

### 2.3 同时存在的串扰

连接后约 12 秒，`onCarConnected()` 还会执行体验应用重排。日志中该重排后
紧接一次新的续播请求：

```text
21:14:04 已下发续播，进入播放
21:14:05 续播已生效
21:14:13 体验应用已放到普通列表末尾
21:14:13 再次下发续播，距开始 12366ms
```

这会让旧 metadata、旧播放状态再次参与面板刷新，和 150 ms 去重缺陷叠加。

### 2.4 修复方向

不要继续扩大固定毫秒窗口。应建立媒体快照：

- identity：package、mediaId、title、artist、album、duration。
- presentation：cover URI/token、lyric version。
- playback：state、speed、actions。
- position 不参与“是否刷新面板”的内容身份，只用于歌词时钟。

只有内容身份或播放状态变化时才重新绑定 CarWith 控制器。相同快照不重复
刷新；同一包名的新歌曲必须刷新。

## 3. 歌词延迟

### 3.1 源码证据

`MusicViewA#setCurrentProgress(long)` 位于：

`com/carwith/launcher/card/v2/widget/MusicViewA.java:1775`

函数体只做两件事：

```java
seekBar.setProgress((int) (position / 1000));
currentTimeText.setText(...);
```

它没有调用 `MinLrcView#setCurrentLryByTime(position)`。

歌词推进只在 `MusicViewA#A0(metadata, position)` 内发生：

```java
this.f5457l.setLrcData(listC);
this.f5457l.setCurrentLryByTime(position);
```

调用位置：

- `MusicViewA.java:799-824`
- `MusicViewA.java:1677-1680`

`A0()` 只由 Metadata 回调路径 `r(metadata, position)` 进入。普通每秒进度
回调 `la.d#l(long)` 最终只进入 `setCurrentProgress(long)`。

结论：

- CarWith 定制卡片页存在明确的“进度条前进、歌词时间轴不前进”断点。
- 网易云原生车机页是网易云自己绘制歌词，不能由该源码直接证明。
- 当前日志没有分别标注定制页歌词和原生页歌词，因此实机复现时仍需分屏确认。

### 3.2 修复方向

在定制卡片进度回调之后，把同一个播放位置传给该视图持有的歌词控件：

```text
setCurrentProgress(position)
  -> 原逻辑更新 SeekBar/时间
  -> setCurrentLryByTime(position)
```

实现不能只硬编码当前版本类名。优先按以下结构定位：

1. 类持有带 `setCurrentLryByTime(long)` 和 `setLrcData(List)` 的字段。
2. 类存在 `setCurrentProgress(long)`，其祖先结构包含媒体卡片状态。
3. 通过 DexKit 查询特征字符串或方法签名，结果按宿主版本缓存。
4. 找不到或匹配多个时标记 `AMBIGUOUS` 并禁用该修复，不盲目 Hook 第一项。

原生车机链路不应加入固定歌词偏移。若事故只在原生页出现，需要校准
时间戳来源，而不是把 1～3 秒的固定延迟写死。

## 4. 网易云定制页被原生页替换

### 4.1 当前 v42 没有主动切换页面的 Hook

当前源码中未发现 `MediaCustomPageHook`、`MediaCustomPagePolicy` 或主动调用
`UCarFragmentManager.jumpTo()` 的业务代码。v42 的 `getFragmentTagFromPackageName`
Hook 只做后台预唤醒，不修改返回值。

因此这次“回退原生页”不是 v42 的旧定制页 Hook 主动造成的。

### 4.2 CarWith 官方路由

反编译源码：

- `e5/t.java:141-174`：`MediaUiUtil.startMediaApp`。
- `ra/a.java:108-117`：`MediaServiceMgr.i(pkg)`。
- `UCarFragmentManager.java:61-83, 137-166`。

关键分支：

```java
} else if (ra.a.c().i(str)) {
    UCarFragmentManager.jumpTo(str, str2);
} else {
    NavigationRouting.getInstance().go(
        new EmbeddingNavigationParams(...EMBEDDING_APP...)
    );
}
```

`ra.a.i(pkg)` 主要取决于：

```java
d0.R().x(pkg) == 1
```

`d0.y()` 又取决于 CarWith 应用白名单中该包的 `castType`。如果云控白名单尚未
加载、会话短暂丢失或分类状态变化，同一次点击就可能从定制页分支落入原生
embedding 分支。这能解释“偶尔”而不是“每次都发生”。

### 4.3 日志缺口

当前日志能证明 v42 已移除旧页面 Hook，但没有捕获事故时刻的以下事件：

```text
MediaUiUtil: startMediaApp 1.0 / 2.0
UCarFragmentManager: navigateByBundle routeName=...
UCarFragmentManager: converted to tag=media_fragment_tag:...
BaseMusicView: media app removed: com.netease.cloudmusic
```

所以“官方路由判定短暂失败”是高置信度推断，不是本次日志的直接闭环证据。
下一次实车复现应记录上述边界，不需要再抓整份系统日志。

### 4.4 修复方向

建议使用带就绪门控的“定制页粘性”，而不是无条件恢复旧 Hook：

- 仅对用户选中的当前媒体应用生效。
- 仅当 CarWith 已有该包的 `na.c` 且 metadata/session 有效时纠偏。
- 用户主动选择原生页或 QQ 的“原生定制偏好”优先。
- 页面已经正确时只记录，不重复 `jumpTo`。
- 路由目标缺失或存在多个候选时降级为官方行为。

直接对 `ra.a.i(pkg)` 全局返回 true 风险较高：会话尚未就绪时可能进入空白
定制页，并影响非页面路由判断。

## 5. 日志附带确认的其他缺陷

### 5.1 约 12 秒重复续播

源码：

- `AutoPlayAllHook.java:1350-1351` 无条件排 4 秒和 12 秒任务。
- `AutoPlayAllHook.java:2208-2213` 在已有 Session 时直接续播，没有检查
  `STATE_PLAYING`。
- `DUPLICATE_RESUME_WINDOW_MS=600` 无法覆盖 12 秒。

修复原则：连接级幂等，同一目标进入播放后本次连接不再下发续播。

### 5.2 面板同步风暴

日志中单会话每 1～5 秒同步一次。`onMediaActuallyPlaying()` 会把 position
更新导致的重复 `STATE_PLAYING` 事件当成“刚开始播放”，再次做完整重绑定。

修复原则：

- 播放状态按 state/speed/actions 去重。
- position 更新只推进歌词，不重绑面板。
- Metadata 按歌曲身份和封面/歌词版本去重。

### 5.3 一键清理无效

日志中出现 16 次：

```text
[一键清理] 记录清理时间失败
SecurityException: Package android does not belong to 10152
```

说明 v42 的“清理后本次上车不续播”没有生效。应使用明确的 system context，
并验证 `Settings.Global` 写入结果。

### 5.4 断连停止范围过大

21:21:27 连续停止：

```text
com.luna.music
com.netease.cloudmusic
```

当前断连目标是多个历史/当前集合的并集。应先明确产品语义，默认建议只停止
一个“当前播放器”目标，并冻结本次连接的 generation。

### 5.5 音频仍活动时结束旧播放器

多次日志：

```text
结束进程前音频已交接:
  (等了 2823~2864ms, 音频输出仍活动, 媒体会话仍在)
已结束播放器
```

当前超时后继续 force-stop，不符合“新播放器已稳定接管”的前置条件。

## 6. 建议的通用实现边界

不把混淆类名当作长期接口。通过 DexKit + 结构特征建立能力层：

| 能力 | 结构定位 |
|---|---|
| 媒体元数据分发 | 方法参数为 `String + MediaMetadataCompat`，调用媒体回调集合 |
| 播放状态分发 | 方法参数为 `String + PlaybackStateCompat` |
| 当前媒体控制器 | 方法返回单例，字段保存包名到控制器的 Map |
| 定制页路由 | 返回 `String`，结果含 `media_fragment_tag:` |
| 歌词控件 | 持有 `List<lyric>`，存在 `setCurrentLryByTime(long)` |
| 卡片进度入口 | 媒体 View 子类中的 `setCurrentProgress(long)` |

建议把策略和 Hook 分开：

```text
MediaSnapshotPolicy       纯 Java，可单测
ResumeGenerationPolicy    纯 Java，可单测
MediaRoutePolicy          纯 Java，可单测
LyricClockPolicy          纯 Java，可单测
CarWithMediaCapabilities  DexKit/反射，仅负责找到目标
MediaPanelCoordinator     Android/Xposed 边界
```

## 7. 实施前待确认

唯一会影响产品行为的选项是网易云定制页策略：

1. 推荐：仅在“当前媒体 Session 已就绪且用户没有主动切原生页”时自动纠偏。
2. 始终优先定制页：实现简单，但可能在 Session 未就绪时出现空白页。
3. 保持官方路由：不修问题 3，只增加诊断日志。

面板刷新、同包切歌和定制页歌词三项不需要改变用户操作习惯，可按推荐方案实施。

