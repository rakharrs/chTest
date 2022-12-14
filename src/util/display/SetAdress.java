package util.display;

import util.listener.MakeClient;

import javax.swing.*;
import java.awt.*;

public class SetAdress extends JFrame {
    String ip;
    JTextField addrInput;

    public SetAdress(){
        setTitle("Sorato ny ip anle host");
        JTextField AddrInput = new JTextField();
        setAddrInput(AddrInput);
        JButton button = new JButton("ok");
        button.addMouseListener(new MakeClient(this));
        JPanel jp = new JPanel();
        jp.setLayout(new GridLayout(2,1));

        jp.add(getAddrInput());
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

    public JTextField getAddrInput() {
        return addrInput;
    }

    public void setAddrInput(JTextField addrInput) {
        this.addrInput = addrInput;
    }
}
