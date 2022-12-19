package util.display;

import util.listener.MakeClient;

import javax.swing.*;
import java.awt.*;

public class SetAdress extends JFrame {
    String ip;
    JTextField input;

    public SetAdress(String title){
        setTitle(title);
        JTextField AddrInput = new JTextField();
        setInput(AddrInput);
        JButton button = new JButton("ok");
        //button.addMouseListener(new MakeClient(this));
        JPanel jp = new JPanel();
        jp.setLayout(new GridLayout(2,1));

        jp.add(getInput());
        jp.add(button);

        add(jp);
        setSize(200,100);
        setResizable(false);
        setVisible(true);

    }
    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public JTextField getInput() {
        return input;
    }

    public void setInput(JTextField addrInput) {
        this.input = addrInput;
    }
}
