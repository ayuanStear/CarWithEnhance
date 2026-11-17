package hun.mesh.carwithenhance.hook;

import android.os.Bundle;
import android.os.SystemClock;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedBridge;
import hun.mesh.carwithenhance.media.CarWithMediaCapabilities;
import hun.mesh.carwithenhance.policy.LyricFallbackPolicy;
import hun.mesh.carwithenhance.utils.XLog;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Preserves native lyrics and only supplies a placeholder after a track has
 * remained without lyrics for a short lookup grace period.
 */
public class LyricSourceHook implements IHook {
    private static final String[] LYRIC_KEYS = {
            "lyricInfo",
            "rawLyric",
            "android.media.metadata.LYRICS",
            "lyric",
            "lrc"
    };
    private static final String MEDIA_ID_KEY = "android.media.metadata.MEDIA_ID";
    private static final long LYRIC_LOOKUP_GRACE_MS = 1500L;
    private static final Map<String, Long> EMPTY_SINCE = new ConcurrentHashMap<>();
    private static final Set<String> LYRICS_SEEN = ConcurrentHashMap.newKeySet();
    private static volatile String sLastMediaId;

    @Override
    public void onHook(ClassLoader classLoader) {
        java.lang.reflect.Method lyricSource =
                CarWithMediaCapabilities.INSTANCE.getLyricSourceMethod();
        if (lyricSource == null) {
            XLog.e(">> [歌词] 未解析到歌词入口，保留官方原生歌词行为");
            return;
        }
        Class<?> metadataClass = lyricSource.getParameterTypes().length == 1
                ? lyricSource.getParameterTypes()[0]
                : null;
        final Field bundleField = resolveBundleField(metadataClass);
        try {
            XposedBridge.hookMethod(lyricSource, new XC_MethodHook() {
                @Override
                protected void afterHookedMethod(MethodHookParam param) {
                    try {
                        Object metadata = param.args != null && param.args.length > 0
                                ? param.args[0]
                                : null;
                        if (metadata == null) {
                            return;
                        }
                        Bundle bundle = readBundle(metadata, bundleField);
                        if (bundle == null) {
                            return;
                        }
                        String metadataLyric = readMetadataLyric(bundle);
                        Object nativeResult = param.getResult();
                        String nativeLyric = nativeResult instanceof String
                                ? (String) nativeResult
                                : null;
                        String mediaId = mediaIdentity(bundle);
                        boolean metadataHasLyrics =
                                LyricFallbackPolicy.isMeaningfulLyric(metadataLyric);
                        boolean nativeHasLyrics =
                                LyricFallbackPolicy.isMeaningfulLyric(nativeLyric);
                        if (metadataHasLyrics || nativeHasLyrics) {
                            LYRICS_SEEN.add(mediaId);
                            removePlaceholderState(bundle);
                            if (metadataHasLyrics && !metadataLyric.equals(nativeLyric)) {
                                param.setResult(metadataLyric);
                            }
                            return;
                        }

                        if (LYRICS_SEEN.contains(mediaId)) {
                            String fallback = LyricFallbackPolicy.fallbackAfterKnownLyrics();
                            if (fallback == null && !"".equals(nativeLyric)) {
                                param.setResult("");
                            }
                            return;
                        }

                        long now = SystemClock.elapsedRealtime();
                        if (LyricFallbackPolicy.shouldResetNoLyricTimer(
                                sLastMediaId, mediaId)) {
                            sLastMediaId = mediaId;
                            EMPTY_SINCE.put(mediaId, Long.valueOf(now));
                        }
                        Long emptySince = EMPTY_SINCE.get(mediaId);
                        if (emptySince == null) {
                            EMPTY_SINCE.put(mediaId, Long.valueOf(now));
                            emptySince = Long.valueOf(now);
                        }
                        long elapsed = now - emptySince.longValue();
                        if (!LyricFallbackPolicy.canShowPlaceholderAfterGrace(
                                false, elapsed, LYRIC_LOOKUP_GRACE_MS)) {
                            if (!"".equals(nativeLyric)) {
                                param.setResult("");
                            }
                            return;
                        }
                        String chosen = LyricFallbackPolicy.chooseResult(
                                nativeLyric,
                                metadataLyric,
                                elapsed,
                                LYRIC_LOOKUP_GRACE_MS);
                        if (chosen.equals(nativeLyric)) {
                            return;
                        }
                        param.setResult(chosen);
                    } catch (Throwable th) {
                        XLog.e(">> [歌词] 歌词占位处理失败", th);
                    }
                }
            });
        } catch (Throwable th) {
            XLog.e(">> [歌词] lyricInfo Hook 注册失败", th);
        }
    }

    private static Field resolveBundleField(Class<?> metadataClass) {
        if (metadataClass == null) {
            return null;
        }
        Class<?> cursor = metadataClass;
        while (cursor != null && cursor != Object.class) {
            Field found = null;
            for (Field field : cursor.getDeclaredFields()) {
                if (Modifier.isStatic(field.getModifiers())
                        || field.isSynthetic()
                        || field.getType() != Bundle.class) {
                    continue;
                }
                if (found != null) {
                    return null;
                }
                found = field;
            }
            if (found != null) {
                try {
                    found.setAccessible(true);
                } catch (Throwable ignored) {
                }
                return found;
            }
            cursor = cursor.getSuperclass();
        }
        return null;
    }

    private static Bundle readBundle(Object metadata, Field bundleField) {
        if (bundleField == null) {
            return null;
        }
        try {
            Object value = bundleField.get(metadata);
            return value instanceof Bundle ? (Bundle) value : null;
        } catch (Throwable ignored) {
            return null;
        }
    }

    private static String readMetadataLyric(Bundle bundle) {
        for (String key : LYRIC_KEYS) {
            String value = bundle.getString(key);
            if (LyricFallbackPolicy.isMeaningfulLyric(value)) {
                return value;
            }
        }
        return null;
    }

    private static String mediaIdentity(Bundle bundle) {
        String mediaId = bundle.getString(MEDIA_ID_KEY);
        if (mediaId == null || mediaId.trim().isEmpty()) {
            mediaId = bundle.getString("android.media.metadata.TITLE", "");
        }
        if (mediaId == null || mediaId.trim().isEmpty()) {
            return "unknown";
        }
        if (LyricFallbackPolicy.shouldResetNoLyricTimer(sLastMediaId, mediaId)) {
            LYRICS_SEEN.clear();
            sLastMediaId = mediaId;
        }
        return mediaId;
    }

    private static void removePlaceholderState(Bundle bundle) {
        String mediaId = bundle.getString(MEDIA_ID_KEY);
        if (mediaId == null || mediaId.isEmpty()) {
            mediaId = sLastMediaId;
        }
        if (mediaId != null) {
            EMPTY_SINCE.remove(mediaId);
        }
    }
}
