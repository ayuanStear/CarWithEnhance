package hun.mesh.carwithenhance.policy;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class LyricFallbackPolicyTest {
    private static final String PLACEHOLDER = "[00:00.00]暂无歌词";
    private static final long GRACE_MS = 1500L;

    @Test
    public void nativeLyricNeverShowsPlaceholder() {
        String nativeLyric = "[00:01.00]第一句\n[00:05.00]第二句";

        assertEquals(nativeLyric, LyricFallbackPolicy.chooseResult(
                nativeLyric, null, 0L, GRACE_MS));
    }

    @Test
    public void metadataLyricOverridesEmptyNativeResult() {
        String metadataLyric = "[00:01.00]来自元数据的歌词";

        assertEquals(metadataLyric, LyricFallbackPolicy.chooseResult(
                "", metadataLyric, 0L, GRACE_MS));
        assertEquals(metadataLyric, LyricFallbackPolicy.chooseResult(
                PLACEHOLDER, metadataLyric, 0L, GRACE_MS));
    }

    @Test
    public void placeholderWaitsUntilLyricLookupGraceExpires() {
        assertEquals("", LyricFallbackPolicy.chooseResult(
                "", null, 0L, GRACE_MS));
        assertEquals("", LyricFallbackPolicy.chooseResult(
                null, null, GRACE_MS - 1L, GRACE_MS));
        assertEquals(PLACEHOLDER, LyricFallbackPolicy.chooseResult(
                null, null, GRACE_MS, GRACE_MS));
    }

    @Test
    public void placeholderTextIsNotMistakenForRealLyrics() {
        assertFalse(LyricFallbackPolicy.isMeaningfulLyric(PLACEHOLDER));
        assertFalse(LyricFallbackPolicy.isMeaningfulLyric(
                "[00:00.00]暂无歌词\n[00:02.00]暂无歌词"));
        assertFalse(LyricFallbackPolicy.isMeaningfulLyric("暂无歌词"));
        assertTrue(LyricFallbackPolicy.isMeaningfulLyric(
                "[00:00.00]前奏\n[00:12.00]第一句"));
    }

    @Test
    public void trackChangeResetsTheNoLyricTimer() {
        assertTrue(LyricFallbackPolicy.shouldResetNoLyricTimer(null, "id-1"));
        assertTrue(LyricFallbackPolicy.shouldResetNoLyricTimer("id-1", "id-2"));
        assertFalse(LyricFallbackPolicy.shouldResetNoLyricTimer("id-1", "id-1"));
    }

    @Test
    public void onceATrackHasLyricsLaterEmptyFramesNeverShowPlaceholder() {
        assertFalse(LyricFallbackPolicy.canShowPlaceholderAfterGrace(
                true, GRACE_MS, GRACE_MS));
        assertTrue(LyricFallbackPolicy.canShowPlaceholderAfterGrace(
                false, GRACE_MS, GRACE_MS));
        assertFalse(LyricFallbackPolicy.canShowPlaceholderAfterGrace(
                false, GRACE_MS - 1L, GRACE_MS));
    }

    @Test
    public void emptyResultAfterKnownLyricsRemainsEmpty() {
        assertNull(LyricFallbackPolicy.fallbackAfterKnownLyrics());
    }
}
