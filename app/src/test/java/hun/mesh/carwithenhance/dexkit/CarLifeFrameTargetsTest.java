package hun.mesh.carwithenhance.dexkit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.lang.reflect.Method;
import org.junit.Test;

public class CarLifeFrameTargetsTest {
    @Test
    public void selectsOnlyVoidLongRenderDisplayMethod() throws Exception {
        Method[] methods = RendererFixture.class.getDeclaredMethods();

        Method selected = CarLifeFrameTargets.selectRendererMethod(methods);

        assertEquals("renderDisplayMsg", selected.getName());
        assertEquals(long.class, selected.getParameterTypes()[0]);
        assertEquals(void.class, selected.getReturnType());
    }

    @Test
    public void returnsNullWhenOnlyWrongOverloadsExist() {
        Method[] methods = WrongRendererFixture.class.getDeclaredMethods();

        assertNull(CarLifeFrameTargets.selectRendererMethod(methods));
    }

    private static final class RendererFixture {
        void renderDisplayMsg(long elapsed) {
        }

        void renderDisplayMsg(int elapsed) {
        }

        String renderDisplayMsg(String elapsed) {
            return elapsed;
        }
    }

    private static final class WrongRendererFixture {
        void renderDisplayMsg(int elapsed) {
        }

        String renderDisplayMsg(long elapsed) {
            return Long.toString(elapsed);
        }
    }
}
