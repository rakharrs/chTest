package util;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.nio.ByteBuffer;

public class JChessTexture implements Serializable, MyTexture{
    private int textID;
    private int width;
    private int height;
    private int channel;
    String imgPath;
    boolean initiated = false;

    int X;
    int Y;

    public JChessTexture(String imgPath){
        setImgPath(imgPath);

        //initTexture();
    }

    @Override
    public void drawImage(Graphics g, int width, int height){
        drawImage(g, getX(), getY(), width, height);
    }

    @Override
    public void drawImage(Graphics g, int x, int y, int width, int height){
        Image bi = null;

        try {
            bi = ImageIO.read(new File(getImgPath()));
            g.drawImage(bi, x, y, width, height, null);
        } catch (IOException e) {

        }
    }

// Fonction nilaina t@ version OpenGL
    @Override
    public void draw(int width, int height){
        draw(getX(), getY(), width, height);

    }
    @Override
    public void initTexture(){
        if(imgPath.equals("") || isInitiated()){
            //System.out.println(imgPath);
            return;
        }

        /*setTextID(glGenTextures());

        //Set Textures parameters
        /*glTextureParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_REPEAT);
        glTextureParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_REPEAT);

        //When stretching
        glTextureParameteri(GL_TEXTURE_2D,GL_TEXTURE_MIN_FILTER,GL_NEAREST);
        glTextureParameteri(GL_TEXTURE_2D,GL_TEXTURE_MAG_FILTER,GL_NEAREST);

        try (MemoryStack stack = stackPush()) {
            IntBuffer w = stack.mallocInt(1);
            IntBuffer h = stack.mallocInt(1);
            IntBuffer channel = stack.mallocInt(1);

            ByteBuffer image = stbi_load(getImgPath(), w, h, channel, 0);
            setWidth(w.get(0));
            setHeight(h.get(0));
            if(!stbi_info(getImgPath(),w,h,channel)){
                throw new RuntimeException("image info didn't load");
            }
            if(image == null){
                throw  new RuntimeException("Image didn't load");
            }
            makeTexture(image);
        }*/
        initiated = true;
    }
    public void draw(int x, int y, int width, int height){

    }

////

    public int getChannel() {
        return channel;
    }

    public int getHeight() {
        return height;
    }

    public int getTextID() {
        return textID;
    }

    public int getWidth() {
        return width;
    }

    public String getImgPath() {
        return imgPath;
    }

    public void setTextID(int textID) {
        this.textID = textID;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public void setChannel(int channel) {
        this.channel = channel;
    }

    public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
    }

    public int getX() {
        return X;
    }

    public void setX(int x) {
        X = x;
    }

    public int getY() {
        return Y;
    }

    public void setY(int y) {
        Y = y;
    }

    public boolean isInitiated() {
        return initiated;
    }

    public void setInitiated(boolean initiated) {
        this.initiated = initiated;
    }
}
