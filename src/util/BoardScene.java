package util;

import chessgame.Piece;
import chessgame.PieceColor;
import chessgame.Player;
import chessgame.chessLogic;
import online.client.Client;
import online.client.Receiver;

import javax.swing.*;

public interface BoardScene extends Scene{
    chessLogic logic = null;

    //Texture background = new Texture("assets/img/menu/chessimg.png");

    Client client = null;

    Player turn = null;
    int caseWidth = 0;
    int caseHeight = 0;

    Receiver receiver = null;

    public abstract Client getClient();
    public abstract chessLogic getLogic();
    public abstract Player getTurn();

    public abstract void turnMove(Player player, Piece piece, Coord2d coord) throws Exception;
    public abstract Player getPlayerByColor(int i);
    public abstract void initPieceCoord();
    public abstract void initPiecesTexture();
    public abstract void receiveChessPiece() throws Exception;
    public abstract JFrame getFrame();
}
