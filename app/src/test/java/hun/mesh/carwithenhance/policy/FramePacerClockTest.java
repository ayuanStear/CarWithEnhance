package hun.mesh.carwithenhance.policy;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class FramePacerClockTest {
    @Test
    public void frameStartSubtractsRenderDuration() {
        assertEquals(98_000_000L,
                FramePacerPolicy.frameStartNanos(100_000_000L, 2L));
    }

    @Test
    public void nonPositiveElapsedUsesCurrentTime() {
        assertEquals(100_000_000L,
                FramePacerPolicy.frameStartNanos(100_000_000L, 0L));
        assertEquals(100_000_000L,
                FramePacerPolicy.frameStartNanos(100_000_000L, -1L));
    }

    @Test
    public void excessiveElapsedDoesNotUnderflow() {
        assertEquals(0L,
                FramePacerPolicy.frameStartNanos(100L, Long.MAX_VALUE));
    }
}
