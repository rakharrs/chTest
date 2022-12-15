package util.listener;

import chessengine.ChessFrame;
import util.display.SetAdress;
import util.display.panel.OptionPanel;

import javax.swing.event.MouseInputListener;
import java.awt.*;
import java.awt.event.MouseEvent;

public class OptionPaneListener implements MouseInputListener {
    private OptionPanel panel;

    public OptionPaneListener(OptionPanel pane){
        this.panel = pane;

    }
    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        System.out.println("eX  "+e.getY());
        System.out.println("bX  "+panel.getOptionsBounds().get("test2"));

        for(String option : panel.getOptions()){
            Rectangle bounds = panel.getOptionsBounds().get(option);

            if (bounds.x + bounds.width >= e.getX() && e.getX() >= bounds.x &&
                    bounds.y - bounds.height <= e.getY() && e.getY() <= bounds.y) {

                pressedAction(option);

                break;
            }

        }
    }

    public void pressedAction(String option){
        switch (option){
            case "SINGLEPLAYER"->{
                ChessFrame.createChess(false, "localhost");

                panel.getFrame().dispose();
                break;
            }
            case "MULTIPLAYER"->{
                panel.getFrame().getCardLayout().next(panel.getFrame().getContainer());
                break;
            }
            case "HOST"->{
                ChessFrame.createChess(true, true, "localhost");

                panel.getFrame().dispose();
                break;
            }
            case "CLIENT"->{
                //ChessFrame.createChess(true, false, "localhost");
                new SetAdress("Sorato ny ip anle host");

                panel.getFrame().dispose();
                break;
            }
            case "BACK"->{
                panel.getFrame().getCardLayout().previous(panel.getFrame().getContainer());
                break;
            }
            case "EXIT", "Exit"->{
                panel.getFrame().dispose();

                break;
            }
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void mouseDragged(MouseEvent e) {

    }

    @Override
    public void mouseMoved(MouseEvent e) {

        for(String option : panel.getOptions()){
            Rectangle bounds = panel.getOptionsBounds().get(option);

            if (bounds.x + bounds.width >= e.getX() && e.getX() >= bounds.x &&
                    bounds.y - bounds.height <= e.getY() && e.getY() <= bounds.y) {

                panel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                break;
            }else{
                panel.setCursor(Cursor.getDefaultCursor());
            }

        }

    }
}
