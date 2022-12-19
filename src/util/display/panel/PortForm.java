package util.display.panel;

import formAPI.display.Formulaire;
import util.display.AddrPostInput;
import util.listener.MakeClient;
import util.listener.MakeHost;

import javax.swing.*;

public class PortForm extends JFrame {
    Formulaire form;
    OptionPanel optionPanel;

    public PortForm(OptionPanel optionPanel) throws Exception {
        setOptionPanel(optionPanel);
        Formulaire formulaire = new Formulaire(new AddrPostInput());
        setForm(formulaire);
        add(getForm());
        getForm().getChamps().get(0).setVisibility(false);
        getForm().getChamps().get(0).setDefaultText("Port (Ohatra : 2556)");
        getForm().reloadChamp();
        getForm().getButton().addMouseListener(new MakeHost(getOptionPanel(),this));
        setSize(370,175);
        setVisible(true);
    }

    public Formulaire getForm() {
        return form;
    }

    public void setForm(Formulaire form) {
        this.form = form;
    }

    public OptionPanel getOptionPanel() {
        return optionPanel;
    }

    public void setOptionPanel(OptionPanel optionPanel) {
        this.optionPanel = optionPanel;
    }
}
