package util.display.panel;

import util.display.FrameMenu;

import javax.swing.*;
import java.awt.*;
import java.awt.font.TextAttribute;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class OptionPanel extends JPanel {
    FrameMenu frame;
    int optionsWidth;
    int optionsHeight;
    ArrayList<String> options = new ArrayList<>();
    private Map<String, Rectangle> optionsBounds = null;

    public OptionPanel(FrameMenu frame, String... options){
        setFrame(frame);
        setSize(600,627);

        for (int i = 0; i < options.length; i++) {
            getOptions().add(options[i]);
        }
        setVisible(true);
    }
@Override
    public void paint(Graphics g){
        getFrame().getBackgroundImg().drawImage(g, getWidth(), 0, -getWidth(), getHeight());
        g.setColor(Color.WHITE);

        Map<TextAttribute, Object> attributes = new HashMap<>();

        attributes.put(TextAttribute.FAMILY, Font.ITALIC);
        attributes.put(TextAttribute.WEIGHT, TextAttribute.WEIGHT_BOLD);

        attributes.put(TextAttribute.SIZE, 20);

        g.setFont(Font.getFont(attributes));

        /*for (int i = 0; i < getOptions().size(); i++) {
            g.drawString(getOptions().get(i), 25, 70*(i+1));
        }*/
        paintOptionsBounds(g);
    }

    public void paintOptionsBounds(Graphics g){
        Graphics2D g2d = (Graphics2D) g.create();

        if(optionsBounds == null){
            optionsBounds = new HashMap<>(getOptions().size());

            int width = 0;
            int height = 0;
            for (String text : getOptions()) {
                Dimension dim = getPreferredSize(g2d, text);
                width = Math.max(width, dim.width);
                height = Math.max(height, dim.height);
            }

            int x = 25;

            /*int totalHeight = (height + 10) * getOptions().size();
            totalHeight += 5 * (getOptions().size() - 1);*/

            int y = height+10;

            for (String text : getOptions()) {
                optionsBounds.put(text, new Rectangle(x, y, width, height-10));
                y += height + 10 + 5;
            }
        }
        for (String text : getOptions()) {
            Rectangle bounds = optionsBounds.get(text);

            //painter.paint(g2d, text, bounds, isSelected, isFocused);
            paintTextOptions(g2d, text, bounds);
        }


    }
    public void paintTextOptions(Graphics2D g2d, String text, Rectangle bounds){
        FontMetrics fm = g2d.getFontMetrics();
        g2d.setColor(Color.WHITE);

        int x = bounds.x ;
        int y = bounds.y ;

        g2d.drawString(text, x, y);
    }

    public void setTextOptionsDim(Graphics g){
        //super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g.create();

        int width = 0;
        int height = 0;
        for (String text : getOptions()) {
            Dimension dim = getPreferredSize(g2d, text);
            width = Math.max(width, dim.width);
            height = Math.max(height, dim.height);
        }
        setOptionsWidth(width);
        setOptionsHeight(height);
    }

    public Dimension getPreferredSize(Graphics2D g2d, String text) {
        return g2d.getFontMetrics().getStringBounds(text, g2d).getBounds().getSize();
    }

    public void paintOptions(Graphics g){
        g.setColor(Color.WHITE);

        Map<TextAttribute, Object> attributes = new HashMap<>();

        attributes.put(TextAttribute.FAMILY, Font.DIALOG);
        attributes.put(TextAttribute.WEIGHT, TextAttribute.WEIGHT_SEMIBOLD);
        attributes.put(TextAttribute.SIZE, 12);

        g.setFont(Font.getFont(attributes));

        for (int i = 0; i < getOptions().size(); i++) {
            g.drawString(getOptions().get(i), 25, 15*(i+1));
        }

    }

    public ArrayList<String> getOptions() {
        return options;
    }

    public void setOptions(ArrayList<String> options) {
        this.options = options;
    }

    public FrameMenu getFrame() {
        return frame;
    }

    public void setFrame(FrameMenu frame) {
        this.frame = frame;
    }

    public int getOptionsWidth() {
        return optionsWidth;
    }

    public void setOptionsWidth(int optionsWidth) {
        this.optionsWidth = optionsWidth;
    }

    public int getOptionsHeight() {
        return optionsHeight;
    }

    public void setOptionsHeight(int optionsHeight) {
        this.optionsHeight = optionsHeight;
    }

    public Map<String, Rectangle> getOptionsBounds() {
        return optionsBounds;
    }
}
