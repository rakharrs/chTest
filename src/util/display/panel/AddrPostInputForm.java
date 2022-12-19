package util.display.panel;

import formAPI.display.Formulaire;
import util.display.AddrPostInput;
import util.listener.MakeClient;
import util.listener.OptionPaneListener;

import javax.swing.*;

public class AddrPostInputForm extends JFrame {
    Formulaire form;
    OptionPanel optionPanel;

    public AddrPostInputForm(OptionPanel optionPanel) throws Exception {
        setOptionPanel(optionPanel);
        Formulaire formulaire = new Formulaire(new AddrPostInput());
        setForm(formulaire);

        add(getForm());
        getForm().getButton().addMouseListener(new MakeClient(getOptionPanel(),this));
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
