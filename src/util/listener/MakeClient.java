package util.listener;

import chessengine.ChessFrame;
import util.display.SetAdress;

import javax.swing.*;
import javax.swing.event.MouseInputListener;
import java.awt.event.MouseEvent;

public class MakeClient implements MouseInputListener {
    private SetAdress pane;

    public MakeClient(SetAdress field){
        pane = field;

    }
    @Override
    public void mouseClicked(MouseEvent e) {
        ChessFrame.createChess(true, false, pane.getAddrInput().getText());

        pane.dispose();
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

    @Override
    public void mouseDragged(MouseEvent e) {

    }

    @Override
    public void mouseMoved(MouseEvent e) {

    }
}
