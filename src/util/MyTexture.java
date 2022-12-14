package util;

import java.awt.*;

public interface MyTexture {
    String imgPath = null;
    boolean initiated = false;
    int X = 0;
    int Y = 0;

    public void initTexture();
    public void draw(int width, int height);
    public void drawImage(Graphics g, int width, int height);
    public void drawImage(Graphics g, int x, int y, int width, int height);
    public void setX(int x);
    public void setY(int y);
    public int getX();
    public int getY();
}
