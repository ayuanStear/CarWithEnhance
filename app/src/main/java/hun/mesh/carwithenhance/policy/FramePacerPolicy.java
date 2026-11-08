package hun.mesh.carwithenhance.policy;

public final class FramePacerPolicy {
    private static final long NANOS_PER_MILLI = 1_000_000L;

    private int currentFps;
    private long nextDeadlineNanos;
    private boolean initialized;

    public synchronized int nextDelayMillis(int fps, long frameStartNanos, long nowNanos) {
        validateFps(fps);

        long stepNanos = 1_000_000_000L / fps;
        if (!initialized || currentFps != fps) {
            initialized = true;
            currentFps = fps;
            nextDeadlineNanos = frameStartNanos + stepNanos;
        } else {
            long expectedStartNanos = nextDeadlineNanos;
            long halfStepNanos = stepNanos / 2L;
            boolean frameStartDrifted = frameStartNanos < expectedStartNanos - halfStepNanos
                    || frameStartNanos > expectedStartNanos + halfStepNanos;
            boolean rendererMissedFrame = nowNanos > expectedStartNanos + stepNanos;
            if (frameStartDrifted || rendererMissedFrame) {
                nextDeadlineNanos = frameStartNanos + stepNanos;
            } else {
                nextDeadlineNanos = expectedStartNanos + stepNanos;
            }
        }

        long remainingNanos = nextDeadlineNanos - nowNanos;
        if (remainingNanos <= 0L) {
            return 0;
        }
        long delayMillis = (remainingNanos - 1L) / NANOS_PER_MILLI + 1L;
        return delayMillis > Integer.MAX_VALUE ? Integer.MAX_VALUE : (int) delayMillis;
    }

    public synchronized void reset() {
        currentFps = 0;
        nextDeadlineNanos = 0L;
        initialized = false;
    }

    public static boolean shouldOverrideRenderer(int fps) {
        return fps == 60;
    }

    public static boolean shouldApplySurfaceFrameRate(
            int fps, int sdkInt, boolean hasValidSurface) {
        return fps == 60 && sdkInt >= 30 && hasValidSurface;
    }

    public static boolean shouldResetForFrameRateChange(int previousFps, int nextFps) {
        return previousFps != nextFps;
    }

    public static int fallbackIntervalMillis(int fps) {
        validateFps(fps);
        return fps == 60 ? 17 : 1_000 / fps;
    }

    public static long frameStartNanos(long nowNanos, long elapsedMillis) {
        if (elapsedMillis <= 0L) {
            return nowNanos;
        }
        if (elapsedMillis > Long.MAX_VALUE / NANOS_PER_MILLI) {
            return 0L;
        }
        long elapsedNanos = elapsedMillis * NANOS_PER_MILLI;
        return Math.max(0L, nowNanos - elapsedNanos);
    }

    private static void validateFps(int fps) {
        if (fps < 15 || fps > 60) {
            throw new IllegalArgumentException("Unsupported frame rate: " + fps);
        }
    }
}
