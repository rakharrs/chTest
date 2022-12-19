package util.listener;

import chessengine.ChessFrame;
import formAPI.inc.Mytextfield;
import util.display.panel.OptionPanel;
import util.display.panel.PortForm;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class MakeHost implements MouseListener {
    private PortForm portForm;
    OptionPanel optionPanel;

    public MakeHost(OptionPanel panel, PortForm pf){
        setOptionPanel(panel);
        setPortForm(pf);
    }

    @Override
    public void mouseClicked(MouseEvent e) {

        Mytextfield textPort = (Mytextfield) portForm.getForm().getChamps().get(1).getComponent();
        int port = Integer.parseInt(textPort.getText());

        ChessFrame.createChess(true, true, "localhost", port);

        getOptionPanel().getFrame().dispose();
        getPortForm().dispose();
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

    public PortForm getPortForm() {
        return portForm;
    }

    public void setPortForm(PortForm portForm) {
        this.portForm = portForm;
    }

    public OptionPanel getOptionPanel() {
        return optionPanel;
    }

    public void setOptionPanel(OptionPanel optionPanel) {
        this.optionPanel = optionPanel;
    }
}
