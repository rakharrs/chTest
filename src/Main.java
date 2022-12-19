import chessengine.ChessFrame;
import online.server.GameServer;
import util.display.FrameMenu;
import util.display.SetAdress;
import util.display.panel.AddrPostInputForm;

import java.awt.*;

public class Main {
    public static void main(String[] args) throws  Exception{
        System.setProperty("sun.java2d.opengl", "true");

        //new AddrPostInputForm();
        new FrameMenu();
    }
}
