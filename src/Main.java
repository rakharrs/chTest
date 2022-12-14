import chessengine.ChessFrame;
import util.display.FrameMenu;

import java.awt.*;

public class Main {
    public static void main(String[] args) throws  Exception{
        System.setProperty("sun.java2d.opengl", "true");

        new FrameMenu();
        //ChessFrame.createChess(false, "localhost");


    }
}
