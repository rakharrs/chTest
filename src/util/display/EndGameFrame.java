package util.display;

import util.listener.EndGameListener;

import javax.swing.*;

public class EndGameFrame extends JFrame {
    JButton button;
    public EndGameFrame(){
        button.addMouseListener(new EndGameListener(this));
        add(button);
        setVisible(true);
    }

    public JButton getButton() {
        return button;
    }

    public void setButton(JButton button) {
        this.button = button;
    }
}
