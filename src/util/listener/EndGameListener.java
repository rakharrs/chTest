package util.listener;

import util.display.EndGameFrame;
import util.display.FrameMenu;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class EndGameListener implements MouseListener {
    EndGameFrame egf;
    public EndGameListener(EndGameFrame EGF){
        egf = EGF;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        new FrameMenu();
        egf.dispose();
    }

    @Override
    public void mousePressed(MouseEvent e) {

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
}
