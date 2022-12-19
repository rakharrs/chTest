package util.listener;

import chessengine.ChessFrame;
import formAPI.inc.Mytextfield;
import util.display.SetAdress;
import util.display.panel.AddrPostInputForm;
import util.display.panel.OptionPanel;

import javax.swing.*;
import javax.swing.event.MouseInputListener;
import java.awt.event.MouseEvent;

public class MakeClient implements MouseInputListener {
    private AddrPostInputForm pane;
    OptionPanel optionPanel;

    public MakeClient(OptionPanel optionPanel, AddrPostInputForm field){
        setOptionPanel(optionPanel);
        pane = field;

    }
    @Override
    public void mouseClicked(MouseEvent e) {
        Mytextfield textf = (Mytextfield) pane.getForm().getChamps().get(0).getComponent();
        Mytextfield textPort = (Mytextfield) pane.getForm().getChamps().get(1).getComponent();

        int port = Integer.parseInt(textPort.getText());
        System.out.println("client port "+port +" et ip : "+ textf.getText());
        ChessFrame.createChess(true, false, textf.getText(), port);

        getOptionPanel().getFrame().dispose();
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

    public OptionPanel getOptionPanel() {
        return optionPanel;
    }

    public void setOptionPanel(OptionPanel optionPanel) {
        this.optionPanel = optionPanel;
    }
}
