package chessgame;

import online.client.Client;
import online.client.Receiver;
import util.*;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.Serializable;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;

public class JBoard extends JPanel implements Serializable, BoardScene {
    chessLogic logic;

    private boolean online;
    private boolean shouldInit = false;
    private Coord2d selectedPiece = null;
    //Texture background = new Texture("assets/img/menu/chessimg.png");

    private Player player1 = new Player("Player1", PieceColor.WHITE); //By default white
    private Player player2 = new Player("Player2", PieceColor.BLACK); //By default black

    Client client;
    public boolean isFinished = false;

    private Player turn = player1;
    private int caseWidth;
    private int caseHeight;

    private Receiver receiver;
    JFrame frame;

    public boolean flag = false;


    public JBoard(JFrame frame, int caseWidth, int caseHeight, boolean online, String hostIp, int port) throws Exception {

        //index 0 -> black && index 1 -> white
        isFinished = false;
        setFrame(frame);
        setLogic(new chessLogic(new Piece[8][8]));

        setOnline(online);
        setSize(600,600);


        if(isOnline()){
            try{

                System.out.println("port : "+port);

                setClient(new Client(hostIp, port));
                this.receiver = new Receiver(this);
                receiver.start();

            }catch (Exception exc){
                exc.printStackTrace();
                JOptionPane.showMessageDialog(new JFrame(), "tsisy host \n Nivadika singleplayer game");
                setOnline(false);
            }
        }

        initPieces(true);
        initPiecesTexture();


        setCaseWidth(caseWidth);
        setCaseHeight(caseHeight);

        resetAndInitPiecesTexture();

    }


    public JBoard(){
        logic = new chessLogic(new Piece[8][8]);
        initPieces(false);
        isFinished = false;
        //move(getPieces()[1][0], new Coord2d(0, 2));

        setSelectedPiece(null);
    }

    public void move(Piece piece, Coord2d coord) throws  Exception{
        if(!getLogic().couldAttack(piece, coord)){
            throw new Exception("can't attack");
        }
        getPieces()[coord.getX()][coord.getY()] = piece;

        System.out.println(piece.getX() + "&& " + piece.getY());
        getPieces()[piece.getX()][piece.getY()] = new Piece(PieceColor.UNSET, PieceType.NONE, "");


    }
    public void forceMove(Piece piece, Coord2d coord) throws  Exception{

        getPieces()[coord.getX()][coord.getY()] = piece;
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

        if(isCastling(piece, coord)){
            if(coord.getX() == piece.getX() + 2){
                forceMove(getLogic().getPiece(7, piece.getY()), new Coord2d(piece.getX()+1, piece.getY()));

            }else if(coord.getX() == piece.getX() - 2){
                forceMove(getLogic().getPiece(0, piece.getY()), new Coord2d(piece.getX()-1, piece.getY()));

            }
        }

        getPieces()[piece.getX()][piece.getY()] = new Piece(PieceColor.UNSET, PieceType.NONE, "");

        setSelectedPiece(null);


        resetPiecesTexture();


        checkPromotion(piece, coord);

        swapTurn();

    }

    public void turnMove(Player player, Piece piece, Coord2d coord) throws Exception {
        if(player == getTurn()){

            //this.initPieceCoord();

            move(player, piece, coord);

            this.initPieceCoord();

            if(getFrame() != null){
                int checkmateBLACK = getLogic().isCheckmate(getTurn().getPieceColor());

                System.out.println("checkmate: " + checkmateBLACK);
                if(checkmateBLACK != 1){
                    if(checkmateBLACK == 0){

                        getFrame().setTitle("CHECKMATE "+ getLogic().getOpponentColor(getTurn().getPieceColor()).toString() +" WIN !");

                    }else if(checkmateBLACK == -1){

                        getFrame().setTitle("STALEMATE !");
                    }

                    isFinished = true;

                }

            }




        }
    }

    public boolean isCastling(Piece piece, Coord2d target){
        if(piece.getType()==PieceType.KING && piece.getX()==4){
            if(target.getX() == piece.getX() + 2 || target.getX() == piece.getX() - 2){
                return true;
            }
        }
        return false;
    }
    public void drawSelectedMove(Graphics g){
        if(getSelectedPiece() == null){
            return ;
        }
        int caseWith = getCaseWidth();
        int caseHeight = getCaseHeight();
        ArrayList<Coord2d> moves = getLogic().getLegalMove(getSelectedPiece().getX(), getSelectedPiece().getY());

        for (int i = 0; i < moves.size(); i++) {
            int x1 = caseWith * moves.get(i).getX();
            int x2 = caseWith * (moves.get(i).getX() + 1);
            int y1 = caseHeight * (Math.abs(moves.get(i).getY() - 7));
            int y2 = caseWith * (Math.abs(moves.get(i).getY() - 1 - 7));

            if(getClient() != null && getClient().getPieceColor()==PieceColor.BLACK){

                y1 = caseHeight * moves.get(i).getY();
                y2 = caseWith * (moves.get(i).getY() + 1);
            }

            g.setColor(Color.GRAY);
            g.fillRect(x1 + (caseWith/4), y1 + (caseHeight/4), x2-x1-(caseWith/2), y2-y1-(caseHeight/2));

        }
    }

    public void drawSquares(Graphics g){
        int caseWith = getCaseWidth();
        int height = getCaseHeight();
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                int x1=caseWith*i; int x2=caseWith*(i+1);
                int y1=caseHeight*j; int y2=caseHeight*(j+1);

                if((i+j)%2==0){

                    if(getClient() != null && getClient().getPieceColor()==PieceColor.WHITE){

                        g.setColor(Color.WHITE);
                    }else{

                        g.setColor(Color.lightGray);
                    }


                }else{

                    if(getClient() != null && getClient().getPieceColor()==PieceColor.WHITE){

                        g.setColor(Color.lightGray);
                    }else{

                        g.setColor(Color.WHITE);
                    }

                }

                g.fillRect(x1, y1, x2-x1, y2-y1);
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

    public void initPieces(boolean wTexture){
        for (int i = 0; i < 8; i++) {
            for (int j = 2; j <= 5; j++) {
                getPieces()[i][j] = new Piece(PieceColor.UNSET, PieceType.NONE, "");
            }
        }
        initBlack(wTexture);
        initWhite(wTexture);
        initPieceCoord();
    }

    public void initBlack(boolean wTexture){
        initBlackPawns(wTexture);

            getPieces()[0][7] = new Piece(PieceColor.BLACK, PieceType.ROOK,"assets/img/pieces/bR.png");
            getPieces()[7][7] = new Piece(PieceColor.BLACK, PieceType.ROOK,"assets/img/pieces/bR.png");

            getPieces()[1][7] = new Piece(PieceColor.BLACK, PieceType.KNIGHT,"assets/img/pieces/bN.png");
            getPieces()[6][7] = new Piece(PieceColor.BLACK, PieceType.KNIGHT,"assets/img/pieces/bN.png");

            getPieces()[2][7] = new Piece(PieceColor.BLACK, PieceType.BISHOP,"assets/img/pieces/bB.png");
            getPieces()[5][7] = new Piece(PieceColor.BLACK, PieceType.BISHOP,"assets/img/pieces/bB.png");

            getPieces()[3][7] = new Piece(PieceColor.BLACK, PieceType.QUEEN,"assets/img/pieces/bQ.png");
            getPieces()[4][7] = new Piece(PieceColor.BLACK, PieceType.KING,"assets/img/pieces/bK.png");
    }

    public void initWhite(boolean wTexture){
        initWhitePawns(wTexture);

            getPieces()[0][0] = new Piece(PieceColor.WHITE, PieceType.ROOK,"assets/img/pieces/wR.png");
            getPieces()[7][0] = new Piece(PieceColor.WHITE, PieceType.ROOK,"assets/img/pieces/wR.png");

            getPieces()[1][0] = new Piece(PieceColor.WHITE, PieceType.KNIGHT,"assets/img/pieces/wN.png");
            getPieces()[6][0] = new Piece(PieceColor.WHITE, PieceType.KNIGHT,"assets/img/pieces/wN.png");

            getPieces()[2][0] = new Piece(PieceColor.WHITE, PieceType.BISHOP,"assets/img/pieces/wB.png");
            getPieces()[5][0] = new Piece(PieceColor.WHITE, PieceType.BISHOP,"assets/img/pieces/wB.png");

            getPieces()[3][0] = new Piece(PieceColor.WHITE, PieceType.QUEEN,"assets/img/pieces/wQ.png");
            getPieces()[4][0] = new Piece(PieceColor.WHITE, PieceType.KING,"assets/img/pieces/wK.png");

    }

    public void initBlackPawns(boolean wTexture){
        for (int i = 0; i < getPieces().length; i++) {
                getPieces()[i][6]=new Piece(PieceColor.BLACK, PieceType.PAWN, "assets/img/pieces/bP.png");

        }
    }
    public void initPiecesTexture(){
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                //getPieces()[i][j].initTexture();
                getPieces()[i][j].getTexture().initTexture();
            }
        }
    }

    public void initWhitePawns(boolean wTexture){
        for (int i = 0; i < getPieces().length; i++) {
                getPieces()[i][1]=new Piece(PieceColor.WHITE, PieceType.PAWN, "assets/img/pieces/wP.png");

        }
    }

    public void drawPieces(){
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if(getPieces()[i][j].getType() != PieceType.NONE){
                    if(getPieces()[i][j] != getLogic().getPiece(getSelectedPiece())){
                        getPieces()[i][j].getTexture().draw(getCaseWidth(),getCaseHeight());

                    }
                }
            }
        }
    }

    public void drawPieces(Graphics g){

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if(getPieces()[i][j].getType() != PieceType.NONE){
                    if(getPieces()[i][j] != getLogic().getPiece(getSelectedPiece())){
                        int x = i*getCaseWidth();
                        int y = Math.abs((j*getCaseHeight())-(7*getCaseHeight()));
                        if(getClient() != null && getClient().getPieceColor()==PieceColor.BLACK){
                            y = j*getCaseHeight();

                        }

                        getPieces()[i][j].getTexture().drawImage(g, x, y,getCaseWidth(), getCaseHeight());

                    }
                }
            }
        }
    }

    public void resetPiecesTexture(){
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if(getPieces()[i][j].getType() != PieceType.NONE){
                    int x = i*getCaseWidth();
                    int y = Math.abs((j*getCaseHeight())-(7*getCaseHeight()));
                    if(getClient() != null && getClient().getPieceColor()==PieceColor.BLACK){
                        y = j*getCaseHeight();

                    }

                    getPieces()[i][j].getTexture().setX(x);
                    getPieces()[i][j].getTexture().setY(y);
                }
            }
        }
    }
    public void resetAndInitPiecesTexture(){
        resetPiecesTexture();
        initPiecesTexture();
    }

    public void initPT(){
        if(shouldInit){
            resetAndInitPiecesTexture();
            setShouldInit(false);
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


    public boolean isOnline() {
        return online;
    }

    public void setOnline(boolean online) {
        this.online = online;
    }



    public void sendRequest(String request) throws Exception {

        getClient().getOutput().writeUTF(request);

        getClient().getOutput().flush();

        if(getTurn().getPieceColor()!=getClient().getPieceColor()){
            setSelectedPiece(null);
        }
    }

    public void receiveNewTurn() throws Exception{
        int color = getClient().getInput().readInt();

        setTurn(color);
    }

    public void receiveChessPiece() throws Exception{
        setLogic((chessLogic) getClient().getInput().readObject());

        setShouldInit(true);

        initPiecesTexture();

        update();
        //setShouldInit(false);
        //resetAndInitPiecesTexture();
        //initPiecesTexture();
    }

    public void setTurn(int turnColor){
        Player p = getPlayerByColor(turnColor);
        setTurn(p);
    }

    public void checkPromotion(Piece piece, Coord2d newPos){
        int Y = newPos.getY();

        if(piece.getType() == PieceType.PAWN){
            if(piece.getColor() == PieceColor.BLACK && Y == 0){
                piece.setType(PieceType.QUEEN);
                piece.setTexture(new JChessTexture("assets/img/pieces/bQ.png"));
            }else if(piece.getColor() == PieceColor.WHITE && Y == 7){
                piece.setType(PieceType.QUEEN);
                piece.setTexture(new JChessTexture("assets/img/pieces/wQ.png"));
            }
        }

    }

    public void swapTurn(){
        if(this.getTurn() == this.getPlayer1()){
            this.setTurn(this.getPlayer2());
        }else{
            this.setTurn(this.getPlayer1());
        }
        getLogic().setTurn(getTurn().getPieceColor());
    }

    public Player getPlayerByColor(int color){
        if(getPlayer1().getPieceColor() == PieceColor.values()[color]){
            return getPlayer1();
        }
        return getPlayer2();
    }

    @Override
    public void paint(Graphics g){
        this.drawSquares(g);
        this.drawSelectedMove(g);
        this.drawPieces(g);
        drawSelectedPiece(g);
        Toolkit.getDefaultToolkit().sync();
    }
    public void update(){
        initPT();
        //System.out.println(getLogic().isInCheck(PieceColor.BLACK));
        setTurn(getLogic().getTurn().ordinal());


        if(!isFinished){

            getFrame().setTitle("Turn for "+getTurn().getPieceColor().toString());
        }

        repaint();

        this.initPieceCoord();
    }

    public void update(Graphics g){

    }

    public void update(long window){

    }

    public void drawSelectedPiece(){
        if(getSelectedPiece() != null){
            getLogic().getPiece(getSelectedPiece()).getTexture().draw(getCaseWidth(),getCaseHeight());
        }
    }

    public void drawSelectedPiece(Graphics g){
        if(getSelectedPiece() != null){
            getLogic().getPiece(getSelectedPiece()).getTexture().drawImage(g, getCaseWidth(),getCaseHeight());
        }
    }

    /*public void loop(){
        long lastTime = System.nanoTime();
        double amountOfTicks = 30.0;
        double ns = 1000000000 / amountOfTicks;
        double delta = 0;
        long timer = System.currentTimeMillis();
        int frames = 0;
        while(true) {
            long now = System.nanoTime();
            delta += (now - lastTime) / ns;
            lastTime = now;
            while(delta >= 1) {
                update();
                Toolkit.getDefaultToolkit().sync();
                delta--;

            }
            setVisible(true);
            frames++;

            if(System.currentTimeMillis() - timer > 1000) {
                timer += 1000;
                frames = 0;
            }
        }

    }*/

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    public boolean isShouldInit() {
        return shouldInit;
    }

    public void setShouldInit(boolean shouldInit) {
        this.shouldInit = shouldInit;
    }

    public JFrame getFrame() {
        return frame;
    }

    public void setFrame(JFrame frame) {
        this.frame = frame;
    }
}

