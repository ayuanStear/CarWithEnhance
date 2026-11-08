package hun.mesh.carwithenhance.policy;

/** Guards card lyric-clock updates without imposing a fixed playback offset. */
public final class LyricClockPolicy {
    private LyricClockPolicy() {
    }

    public static boolean shouldAdvance(
            long previousPositionMillis,
            long currentPositionMillis,
            boolean mediaConnected) {
        return mediaConnected
                && currentPositionMillis >= 0L
                && currentPositionMillis != previousPositionMillis;
    }
}
