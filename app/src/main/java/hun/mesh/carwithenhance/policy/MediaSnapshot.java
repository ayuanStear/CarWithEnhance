package hun.mesh.carwithenhance.policy;

/** Immutable media content and playback identity. Position is intentionally excluded. */
public final class MediaSnapshot {
    public final String packageName;
    public final String mediaId;
    public final String title;
    public final String artist;
    public final String album;
    public final long durationMillis;
    public final String artworkToken;
    public final String lyricsToken;
    public final int playbackState;
    public final float playbackSpeed;
    public final long actions;

    public MediaSnapshot(
            String packageName,
            String mediaId,
            String title,
            String artist,
            String album,
            long durationMillis,
            String artworkToken,
            String lyricsToken,
            int playbackState,
            float playbackSpeed,
            long actions) {
        this.packageName = safe(packageName);
        this.mediaId = safe(mediaId);
        this.title = safe(title);
        this.artist = safe(artist);
        this.album = safe(album);
        this.durationMillis = Math.max(0L, durationMillis);
        this.artworkToken = safe(artworkToken);
        this.lyricsToken = safe(lyricsToken);
        this.playbackState = playbackState;
        this.playbackSpeed = playbackSpeed;
        this.actions = actions;
    }

    public boolean hasMetadataIdentity() {
        return !mediaId.isEmpty()
                || !title.isEmpty()
                || !artist.isEmpty()
                || !album.isEmpty();
    }

    private static String safe(String value) {
        return value == null ? "" : value;
    }
}
