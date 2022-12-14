package chessgame;

import util.MyTexture;
import util.JChessTexture;

import java.io.Serializable;

public class Piece implements Serializable {
    private PieceColor color;
    private PieceType type;
    private MyTexture texture;

    String texturePath;

    public int x;
    public int y;

    public Piece(){

    }
    public Piece(Piece piece){
        setColor(piece.getColor());
        setType(piece.getType());
        setTexture(piece.getTexture());
        setXY(piece.getX(), piece.getY());
    }

    public Piece(PieceColor color, PieceType type){
        setColor(color);
        setType(type);
    }
    public Piece(PieceColor color, PieceType type, String texturePath){
        setColor(color);
        setType(type);
        setTexturePath(texturePath);
        setTexture(new JChessTexture(getTexturePath()));
        //setTexture(new JChessTexture(texturePath));
    }


    public boolean sameColor(Piece piece){
        if(piece != null && piece.getColor() == this.getColor())
            return true;
        return false;
    }

    public PieceColor getColor() {
        return color;
    }

    public PieceType getType() {
        return type;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setColor(PieceColor color) {
        this.color = color;
    }

    public void setTexture(MyTexture texture) {
        this.texture = texture;
    }
    public void setType(PieceType type) {
        this.type = type;
    }

    public MyTexture getTexture() {
        return texture;
    }

    public String getTexturePath() {
        return texturePath;
    }

    public void setTexturePath(String texturePath) {
        this.texturePath = texturePath;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }
    public void setXY(int x, int y){
        setX(x);
        setY(y);
    }
}
