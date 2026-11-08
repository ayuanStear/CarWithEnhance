package hun.mesh.carwithenhance.policy;

/** Decides when CarWith must rebind its media panel. */
public final class MediaSnapshotPolicy {
    private MediaSnapshotPolicy() {
    }

    public static boolean shouldRefresh(MediaSnapshot previous, MediaSnapshot current) {
        if (current == null) {
            return false;
        }
        if (previous == null) {
            return true;
        }
        if (!previous.packageName.equals(current.packageName)) {
            return true;
        }
        if (!previous.mediaId.equals(current.mediaId)
                || !previous.title.equals(current.title)
                || !previous.artist.equals(current.artist)
                || !previous.album.equals(current.album)
                || previous.durationMillis != current.durationMillis
                || !previous.artworkToken.equals(current.artworkToken)) {
            return true;
        }
        return previous.playbackState != current.playbackState
                || Float.compare(previous.playbackSpeed, current.playbackSpeed) != 0
                || previous.actions != current.actions;
    }

    /**
     * Keeps the last known song while CarWith temporarily reports empty metadata
     * during controller replacement.
     */
    public static MediaSnapshot coalesce(MediaSnapshot previous, MediaSnapshot current) {
        if (previous == null) {
            return current;
        }
        if (current == null) {
            return previous;
        }
        if (previous.packageName.equals(current.packageName)
                && previous.hasMetadataIdentity()
                && !current.hasMetadataIdentity()) {
            return new MediaSnapshot(
                    previous.packageName,
                    previous.mediaId,
                    previous.title,
                    previous.artist,
                    previous.album,
                    previous.durationMillis,
                    previous.artworkToken,
                    current.playbackState,
                    current.playbackSpeed,
                    current.actions);
        }
        return current;
    }
}
