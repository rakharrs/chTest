package util.exception;

import javax.swing.*;

public class ErroMessage extends Thread{
    private String Message;
    public ErroMessage(String message){
        this.Message = message;
    }

    @Override
    public void run() {
        JOptionPane.showMessageDialog(new JFrame(), this.Message);
    }
}
