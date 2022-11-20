package chessgame;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL46;
import util.Coord2d;
import util.MpihainoMouse;
import util.Scene;
import util.Texture;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.glfw.GLFW.glfwShowWindow;
import static org.lwjgl.opengl.GL46.*;
import static org.lwjgl.stb.STBEasyFont.stb_easy_font_print;

public class Board extends Scene {
    chessLogic logic;

    private Coord2d selectedPiece;
    Texture background = new Texture("assets/img/menu/chessimg.png");

    private Player player1 = new Player("Player1", PieceColor.WHITE); //By default white
    private Player player2 = new Player("Player2", PieceColor.BLACK); //By default black

    private Player turn = player1;
    private int caseWidth;
    private int caseHeight;

    public Board(int caseWidth, int caseHeight){

        //index 0 -> black && index 1 -> white
        setLogic(new chessLogic(new Piece[8][8]));

        initPieces();

        setCaseWidth(caseWidth);
        setCaseHeight(caseHeight);

    }


    public Board(){
        logic = new chessLogic(new Piece[8][8]);
        initPieces();
        //move(getPieces()[1][0], new Coord2d(0, 2));

        setSelectedPiece(null);
    }

    public void move(Piece piece, Coord2d coord) throws  Exception{
        if(!getLogic().couldAttack(piece, coord)){
            throw new Exception("can't attack");
        }
        getPieces()[coord.getX()][coord.getY()] = piece;
        //piece.setXY(coord.getX(), coord.getY());
        System.out.println(piece.getX() + "&& " + piece.getY());
        getPieces()[piece.getX()][piece.getY()] = new Piece(PieceColor.UNSET, PieceType.NONE, "");


    }
    public void move(Player player, Piece piece, Coord2d coord) throws  Exception{
        if(!getLogic().couldAttack(piece, coord)){
            throw new Exception("can't attack");
        }

        player.setScore(player.getScore() + (getPieces()[coord.getX()][coord.getY()].getType().ordinal()));
        getPieces()[coord.getX()][coord.getY()] = piece;


        System.out.println("player1 - score:  " + getPlayer1().getScore());
        System.out.println("player2 - score:  " + getPlayer2().getScore());
        getPieces()[piece.getX()][piece.getY()] = new Piece(PieceColor.UNSET, PieceType.NONE, "");


    }

    public ByteBuffer drawText(String text){
        int s = 256; //Take whatever size suits you.
        BufferedImage b = new BufferedImage(s, s, BufferedImage.TYPE_4BYTE_ABGR);
        Graphics2D g = b.createGraphics();
        g.drawString(text, 0, 0);

        int co = b.getColorModel().getNumComponents();

        byte[] data = new byte[co * s * s];
        b.getRaster().getDataElements(0, 0, s, s, data);

        ByteBuffer pixels = BufferUtils.createByteBuffer(data.length);
        pixels.put(data);
        pixels.rewind();

        return pixels;
    }

    public void drawSelectedMove(Player player){
        if(player.getSelectedPiece() == null){
            return ;
        }

        if(getLogic().getPiece(player.selectedPiece.getX(),player.selectedPiece.getY()).getColor() == player.getPieceColor()){
            int caseWith = getCaseWidth();
            int caseHeight = getCaseHeight();
            ArrayList<Coord2d> moves = getLogic().canAttack(player.getSelectedPiece().getX(), player.getSelectedPiece().getY());
            //System.out.println(getSelectedPiece().getX() + " && " + getSelectedPiece().getY());
            for (int i = 0; i < moves.size(); i++) {
                int x1 = caseWith * moves.get(i).getX();
                int x2 = caseWith * (moves.get(i).getX() + 1);
                int y1 = caseHeight * moves.get(i).getY();
                int y2 = caseWith * (moves.get(i).getY() + 1);
                GL46.glBegin(GL_QUADS);

                GL46.glColor4f(0.4f, 0.4f, 0.4f, 1f);
                GL46.glVertex2i(x1 + (caseWith/4),y1+(caseHeight/4));
                GL46.glVertex2i(x2 - (caseWith/4),y1+(caseHeight/4));
                GL46.glVertex2i(x2-(caseWith/4),y2-(caseHeight/4));
                GL46.glVertex2i(x1+(caseWith/4),y2-(caseHeight/4));

                GL46.glEnd();

            }
        }

    }
    public void drawSelectedMove(){
        if(getSelectedPiece() == null){
            return ;
        }
        int caseWith = getCaseWidth();
        int caseHeight = getCaseHeight();
        ArrayList<Coord2d> moves = getLogic().canAttack(getSelectedPiece().getX(), getSelectedPiece().getY());
        //System.out.println(getSelectedPiece().getX() + " && " + getSelectedPiece().getY());
        for (int i = 0; i < moves.size(); i++) {
            int x1 = caseWith * moves.get(i).getX();
            int x2 = caseWith * (moves.get(i).getX() + 1);
            int y1 = caseHeight * moves.get(i).getY();
            int y2 = caseWith * (moves.get(i).getY() + 1);
            GL46.glBegin(GL_QUADS);

            GL46.glColor4f(0.5f, 0.5f, 0.5f, 1f);
            GL46.glVertex2i(x1 + (caseWith/4),y1+(caseHeight/4));
            GL46.glVertex2i(x2 - (caseWith/4),y1+(caseHeight/4));
            GL46.glVertex2i(x2-(caseWith/4),y2-(caseHeight/4));
            GL46.glVertex2i(x1+(caseWith/4),y2-(caseHeight/4));

            GL46.glEnd();

        }

    }
    public void drawSquares(){
        int caseWith = getCaseWidth();
        int height = getCaseHeight();
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                int x1=caseWith*i; int x2=caseWith*(i+1);
                int y1=caseHeight*j; int y2=caseHeight*(j+1);
                GL46.glBegin(GL_QUADS);
                if((i+j)%2==0){
                    GL46.glColor4f(0.7f, 0.7f, 0.7f, 1f);
                }else{
                    GL46.glColor4f(1.0f, 1.0f, 1.0f, 1f);
                }

                GL46.glVertex2i(x1,y1);
                GL46.glVertex2i(x2,y1);
                GL46.glVertex2i(x2,y2);
                GL46.glVertex2i(x1,y2);

                GL46.glEnd();
            }
        }
    }

    public void initPieceCoord(){
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                getPieces()[i][j].setXY(i,j);
            }
        }
    }

    public void initPieces(){
        for (int i = 0; i < 8; i++) {
            for (int j = 2; j <= 5; j++) {
                getPieces()[i][j] = new Piece(PieceColor.UNSET, PieceType.NONE, "");
            }
        }
        initBlack();
        initWhite();
        initPieceCoord();
    }

    public void initBlack(){
        initBlackPawns();
        getPieces()[0][7] = new Piece(PieceColor.BLACK, PieceType.ROOK,"assets/img/pieces/bR.png");
        getPieces()[7][7] = new Piece(PieceColor.BLACK, PieceType.ROOK,"assets/img/pieces/bR.png");

        getPieces()[1][7] = new Piece(PieceColor.BLACK, PieceType.KNIGHT,"assets/img/pieces/bN.png");
        getPieces()[6][7] = new Piece(PieceColor.BLACK, PieceType.KNIGHT,"assets/img/pieces/bN.png");

        getPieces()[2][7] = new Piece(PieceColor.BLACK, PieceType.BISHOP,"assets/img/pieces/bB.png");
        getPieces()[5][7] = new Piece(PieceColor.BLACK, PieceType.BISHOP,"assets/img/pieces/bB.png");

        getPieces()[3][7] = new Piece(PieceColor.BLACK, PieceType.QUEEN,"assets/img/pieces/bQ.png");
        getPieces()[4][7] = new Piece(PieceColor.BLACK, PieceType.KING,"assets/img/pieces/bK.png");
    }

    public void initWhite(){
        initWhitePawns();
        getPieces()[0][0] = new Piece(PieceColor.WHITE, PieceType.ROOK,"assets/img/pieces/wR.png");
        getPieces()[7][0] = new Piece(PieceColor.WHITE, PieceType.ROOK,"assets/img/pieces/wR.png");

        getPieces()[1][0] = new Piece(PieceColor.WHITE, PieceType.KNIGHT,"assets/img/pieces/wN.png");
        getPieces()[6][0] = new Piece(PieceColor.WHITE, PieceType.KNIGHT,"assets/img/pieces/wN.png");

        getPieces()[2][0] = new Piece(PieceColor.WHITE, PieceType.BISHOP,"assets/img/pieces/wB.png");
        getPieces()[5][0] = new Piece(PieceColor.WHITE, PieceType.BISHOP,"assets/img/pieces/wB.png");

        getPieces()[3][0] = new Piece(PieceColor.WHITE, PieceType.QUEEN,"assets/img/pieces/wQ.png");
        getPieces()[4][0] = new Piece(PieceColor.WHITE, PieceType.KING,"assets/img/pieces/wK.png");
    }

    public void initBlackPawns(){
        for (int i = 0; i < getPieces().length; i++) {
            getPieces()[i][6]=new Piece(PieceColor.BLACK, PieceType.PAWN, "assets/img/pieces/bP.png");
        }
    }

    public void initWhitePawns(){
        for (int i = 0; i < getPieces().length; i++) {
            getPieces()[i][1]=new Piece(PieceColor.WHITE, PieceType.PAWN, "assets/img/pieces/wP.png");
        }
    }

    public void drawPieces(){
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if(getPieces()[i][j].getType() != PieceType.NONE){
                    getPieces()[i][j].getTexture().draw(i*getCaseWidth(),j*getCaseHeight(),getCaseWidth(),getCaseHeight());
                }
            }
        }
    }



    public void setCaseHeight(int caseHeight) {
        this.caseHeight = caseHeight;
    }
    public void setCaseWidth(int caseWidth) {
        this.caseWidth = caseWidth;
    }

    public int getCaseHeight() {
        return caseHeight;
    }
    public int getCaseWidth() {
        return caseWidth;
    }

    public Piece[][] getPieces() {
        return getLogic().getPieces();
    }

    public Coord2d getSelectedPiece() {
        return selectedPiece;
    }

    public chessLogic getLogic() {
        return logic;
    }

    public void setLogic(chessLogic logic) {
        this.logic = logic;
    }

    public void setSelectedPiece(Coord2d selectedPiece) {
        this.selectedPiece = selectedPiece;
    }

    public Player getPlayer1() {
        return player1;
    }

    public void setPlayer1(Player player1) {
        this.player1 = player1;
    }

    public Player getPlayer2() {
        return player2;
    }

    public void setPlayer2(Player player2) {
        this.player2 = player2;
    }

    public void setTurn(Player turn) {
        this.turn = turn;
    }

    public Player getTurn() {
        return turn;
    }
@Override
    public void update(long window){
        Coord2d mousePos;
        int newMouseState = glfwGetMouseButton(window, GLFW_MOUSE_BUTTON_1);
        if(newMouseState == GLFW_PRESS){

            mousePos = MpihainoMouse.getMousePos(window);
            int X = (mousePos.getX()-50)/(500/8);
            int Y = Math.abs((((mousePos.getY()-50)/(500/8))-7));
            if(this.getLogic().isPiece(X, Y) &&
                    this.getLogic().pieceColor(X, Y) == this.getTurn().getPieceColor()){
                this.setSelectedPiece(new Coord2d((mousePos.getX()-50)/(500/8),Math.abs((((mousePos.getY()-50)/(500/8))-7))));
            }else{
                if(this.getSelectedPiece() != null){
                    try {
                        this.move(this.getTurn(),this.getLogic().getPiece(this.getSelectedPiece()), new Coord2d(X, Y));
                        if(this.getTurn() == this.getPlayer1()){
                            this.setTurn(this.getPlayer2());
                        }else{
                            this.setTurn(this.getPlayer1());
                        }

                    } catch (Exception e) {

                    }

                }
            }
            System.out.println("X:  " + X);
            System.out.println("Y:  " + Y);
            System.out.println("piece:  "+this.getLogic().getPiece(X, Y).getType());
            System.out.println("Color:  "+this.getLogic().getPiece(X, Y).getColor());


        }

        double now = glfwGetTime();
        GL46.glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

        GL46.glViewport(100, 100, 1000, 1000);
        //location and dimension of area to draw on within the display frame.
        GL46.glLoadIdentity();
        //rotate the following object about the z axis
        //GL11.glRotatef(rotation,0,0,1);


        this.drawSquares();
        //test.draw(300,300,this.getCaseWidth(),this.getCaseHeight());
        this.drawPieces();


        this.drawSelectedMove();

        glfwSwapBuffers(window); // swap the color buffers

        // Poll for window events. The key callback above will only be
        // invoked during this call.

        glfwPollEvents();

        this.initPieceCoord();
    }
}
