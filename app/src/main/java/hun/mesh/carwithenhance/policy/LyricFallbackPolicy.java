package hun.mesh.carwithenhance.policy;

/** Pure decisions for preserving native lyrics and showing a late placeholder. */
public final class LyricFallbackPolicy {
    public static final String PLACEHOLDER_LRC = "[00:00.00]暂无歌词";

    private LyricFallbackPolicy() {
    }

    public static String chooseResult(
            String nativeResult,
            String metadataLyric,
            long elapsedMs,
            long graceMs) {
        if (isMeaningfulLyric(nativeResult)) {
            return nativeResult;
        }
        if (isMeaningfulLyric(metadataLyric)) {
            return metadataLyric;
        }
        if (graceMs <= 0L || elapsedMs < 0L || elapsedMs >= graceMs) {
            return PLACEHOLDER_LRC;
        }
        return "";
    }

    public static boolean isMeaningfulLyric(String lyric) {
        if (lyric == null || lyric.trim().isEmpty()) {
            return false;
        }
        String[] lines = lyric.split("\\r?\\n");
        for (String line : lines) {
            String text = line.replaceAll("^\\[[^]]+\\]", "").trim();
            if (text.isEmpty()
                    || "暂无歌词".equals(text)
                    || "无歌词".equals(text)
                    || "纯音乐，请欣赏".equals(text)
                    || "此歌曲没有填词，请欣赏".equals(text)) {
                continue;
            }
            return true;
        }
        return false;
    }

    public static boolean shouldResetNoLyricTimer(
            String previousMediaId, String currentMediaId) {
        if (currentMediaId == null || currentMediaId.isEmpty()) {
            return false;
        }
        return !currentMediaId.equals(previousMediaId);
    }

    public static boolean canShowPlaceholderAfterGrace(
            boolean lyricsSeenForTrack,
            long elapsedMs,
            long graceMs) {
        return !lyricsSeenForTrack
                && graceMs > 0L
                && elapsedMs >= graceMs;
    }

    public static String fallbackAfterKnownLyrics() {
        return null;
    }
}
