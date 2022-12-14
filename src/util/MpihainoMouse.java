package util;/*package util;


import java.nio.DoubleBuffer;

public class MpihainoMouse {
    public static Coord2d getMousePos(long window) {
        DoubleBuffer xBuffer = BufferUtils.createDoubleBuffer(1);
        DoubleBuffer yBuffer = BufferUtils.createDoubleBuffer(1);

        GLFW.glfwGetCursorPos(window, xBuffer, yBuffer);

        double x = xBuffer.get(0);
        double y = yBuffer.get(0);

        return new Coord2d(x, y);
    }
}*/
