package hun.mesh.carwithenhance.policy;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class MediaSnapshotPolicyTest {
    private static MediaSnapshot snapshot(
            String mediaId,
            String title,
            String artwork,
            int state,
            long actions) {
        return new MediaSnapshot(
                "com.netease.cloudmusic",
                mediaId,
                title,
                "artist",
                "album",
                180_000L,
                artwork,
                "lyrics",
                state,
                1.0f,
                actions);
    }

    @Test
    public void firstSnapshotRefreshesPanel() {
        assertTrue(MediaSnapshotPolicy.shouldRefresh(
                null, snapshot("1", "First", "cover-1", 3, 1L)));
    }

    @Test
    public void nextTrackInSamePackageRefreshesPanel() {
        MediaSnapshot first = snapshot("1", "First", "cover-1", 3, 1L);
        MediaSnapshot second = snapshot("2", "Second", "cover-2", 3, 1L);

        assertTrue(MediaSnapshotPolicy.shouldRefresh(first, second));
    }

    @Test
    public void lyricsChangeRefreshesPanelEvenWhenTitleUnchanged() {
        MediaSnapshot first = snapshot("1", "First", "cover-1", 3, 1L);
        MediaSnapshot second = new MediaSnapshot(
                "com.netease.cloudmusic",
                "1",
                "First",
                "artist",
                "album",
                180_000L,
                "cover-1",
                "lyrics-v2",
                3,
                1.0f,
                1L);

        assertTrue(MediaSnapshotPolicy.shouldRefresh(first, second));
    }

    @Test
    public void playbackStateAndActionsChangesRefreshPanel() {
        MediaSnapshot paused = snapshot("1", "First", "cover-1", 2, 1L);
        MediaSnapshot playing = snapshot("1", "First", "cover-1", 3, 2L);

        assertTrue(MediaSnapshotPolicy.shouldRefresh(paused, playing));
    }

    @Test
    public void duplicateSnapshotDoesNotRefreshPanel() {
        MediaSnapshot first = snapshot("1", "First", "cover-1", 3, 1L);
        MediaSnapshot duplicate = snapshot("1", "First", "cover-1", 3, 1L);

        assertFalse(MediaSnapshotPolicy.shouldRefresh(first, duplicate));
    }

    @Test
    public void playingSnapshotKeepsStableMetadataWhenControllerBrieflyReportsNone() {
        MediaSnapshot stable = snapshot("1", "First", "cover-1", 3, 1L);
        MediaSnapshot transientEmpty = new MediaSnapshot(
                "com.netease.cloudmusic",
                "",
                "",
                "",
                "",
                0L,
                "",
                "",
                3,
                1.0f,
                1L);

        MediaSnapshot coalesced = MediaSnapshotPolicy.coalesce(stable, transientEmpty);

        assertEquals("1", coalesced.mediaId);
        assertEquals("First", coalesced.title);
        assertEquals(3, coalesced.playbackState);
        assertFalse(MediaSnapshotPolicy.shouldRefresh(stable, coalesced));
    }

    @Test
    public void transientEmptyMetadataDoesNotHidePlaybackStateChange() {
        MediaSnapshot playing = snapshot("1", "First", "cover-1", 3, 1L);
        MediaSnapshot transientPaused = new MediaSnapshot(
                "com.netease.cloudmusic",
                "",
                "",
                "",
                "",
                0L,
                "",
                "",
                2,
                1.0f,
                2L);

        MediaSnapshot coalesced = MediaSnapshotPolicy.coalesce(playing, transientPaused);

        assertEquals("First", coalesced.title);
        assertEquals(2, coalesced.playbackState);
        assertEquals(2L, coalesced.actions);
        assertTrue(MediaSnapshotPolicy.shouldRefresh(playing, coalesced));
    }
}
