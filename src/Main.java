import chessengine.ChessFrame;
import util.display.FrameMenu;
import util.display.SetAdress;

import java.awt.*;

public class Main {
    public static void main(String[] args) throws  Exception{
        System.setProperty("sun.java2d.opengl", "true");

        //new SetAdress("test");
        new FrameMenu();
    }
}
