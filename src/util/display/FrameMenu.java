package util.display;

import util.JChessTexture;
import util.MyTexture;
import util.display.panel.OptionPanel;
import util.listener.OptionPaneListener;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class FrameMenu extends JFrame{
    CardLayout cardLayout;
    Container container;
    MyTexture backgroundImg = new JChessTexture("assets/img/menu/chessimg.png");

    public FrameMenu(){
        setSize(600,627);
        CardLayout cardLayout = new CardLayout();
        setLayout(cardLayout);

        container = getContentPane();

        setCardLayout(cardLayout);

        OptionPanel popo = new OptionPanel(this, "SINGLEPLAYER", "MULTIPLAYER", "EXIT");
        OptionPanel popo2 = new OptionPanel(this, "HOST", "CLIENT", "BACK", "EXIT");
        popo.addMouseMotionListener(new OptionPaneListener(popo));
        popo.addMouseListener(new OptionPaneListener(popo));

        popo2.addMouseMotionListener(new OptionPaneListener(popo2));
        popo2.addMouseListener(new OptionPaneListener(popo2));
        //setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
        add(popo);
        add(popo2);

        setResizable(false);

        setDefaultCloseOperation(3);
        //add(b);
        setVisible(true);
        //repaint();
    }

    public MyTexture getBackgroundImg() {
        return backgroundImg;
    }

    public void setBackground(MyTexture background) {
        this.backgroundImg = background;
    }

    public CardLayout getCardLayout() {
        return cardLayout;
    }

    private void setCardLayout(CardLayout cardLayout) {
        this.cardLayout = cardLayout;
    }

    public void setBackgroundImg(MyTexture backgroundImg) {
        this.backgroundImg = backgroundImg;
    }

    public Container getContainer() {
        return container;
    }

    public void setContainer(Container container) {
        this.container = container;
    }
}
