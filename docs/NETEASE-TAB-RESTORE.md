# NetEase Root Tab Restore

## Symptom

When a NetEase Cloud Music detail page is opened from `我的音乐`, `歌单广场`,
or `播客`, pressing Back rebuilt the host media view pager and selected
`精彩推荐` instead of the root tab that owned the detail page.

## Root cause

`MediaViewPager` destroys its current `ViewPager` when the detail Fragment is
shown. On return, CarWith creates a new `ViewPager` and restores
`MediaStatePagerAdapter` state. The restored Fragment references have already
been removed, producing:

```text
Fragment no longer exists for key f0
```

The host then rebuilds the four root pages and falls back to page `0`, which is
`精彩推荐`.

## QQ Music comparison

QQ Music uses `QPlayIPCService`, which caches the root folder list in
`mRootFolderList`. Its service object remains the source of truth while the
page Fragment and ViewPager are rebuilt.

CarWith Enhance applies the same boundary to the UI state:

- remember the selected root page index in memory;
- key it by the current media package, such as `com.netease.cloudmusic`;
- restore it after a new `MediaStatePagerAdapter` is attached;
- never rely on the old ViewPager object or its stale saved Fragment state.

## Implementation

`MediaTabSelectionHook` hooks the stable AndroidX `ViewPager` methods
`setCurrentItem` and `setAdapter`. It only stores and restores state when the
adapter belongs to CarWith's media page.

`CarWithMediaCapabilities.getCurrentMediaPackage()` supplies the package key
through the existing structural media-manager capability. This keeps state
isolated between NetEase, QQ Music, and other media applications.

`MediaTabSelectionPolicy` rejects missing or out-of-range saved indexes and
retains the host's current page in that case.

## Verified behavior

Runtime logs captured these transitions across new ViewPager instances:

```text
我的音乐: 3 -> 0 -> 3
歌单广场: 2 -> 0 -> 2
播客:     1 -> 0 -> 1
```

The final car display showed `我的音乐` selected after returning from
`我喜欢的音乐`.
