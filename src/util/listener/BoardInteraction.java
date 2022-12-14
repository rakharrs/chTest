package util.listener;

import chessgame.JBoard;
import chessgame.Piece;
import chessgame.PieceColor;
import util.Coord2d;

import javax.swing.event.MouseInputListener;
import java.awt.event.MouseEvent;
import java.nio.IntBuffer;


public class BoardInteraction implements MouseInputListener {

    private JBoard JBoard;

    public BoardInteraction(JBoard JBoard){
        setBoard(JBoard);
    }

    public void getXYPos(MouseEvent e, IntBuffer xbuffer, IntBuffer ybuffer){
        int X = (e.getX())/(600/8);
        int Y = Math.abs((e.getY())/(600/8) - 7);

        if(getBoard().getClient() != null && getBoard().getClient().getPieceColor()==PieceColor.BLACK){
            Y = (e.getY())/(600/8);
        }

        xbuffer.put(0, X);
        ybuffer.put(0, Y);
    }

    public JBoard getBoard() {
        return JBoard;
    }

    public void setBoard(JBoard JBoard) {
        this.JBoard = JBoard;
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        Piece piece = getBoard().getLogic().getPiece(getBoard().getSelectedPiece());

        if(piece != null && piece.getColor() != PieceColor.UNSET){
            piece.getTexture().setX(e.getX()-40);

            piece.getTexture().setY(e.getY()-25);
            //piece.getTexture().setY(Math.abs(e.getY()-600)-25);
        }

        getBoard().flag = true;
        getBoard().update();

    }

    @Override
    public void mouseMoved(MouseEvent e) {

    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        IntBuffer Xbuffer = IntBuffer.allocate(1);
        IntBuffer Ybuffer = IntBuffer.allocate(1);

        getXYPos(e, Xbuffer, Ybuffer);
        System.out.println(e.getY());

        int X = Xbuffer.get(0);
        int Y = Ybuffer.get(0);

        if(!getBoard().isFlag() && getBoard().getLogic().isPiece(X, Y) &&
                getBoard().getLogic().pieceColor(X, Y) == getBoard().getTurn().getPieceColor()){

            if(!getBoard().isOnline()){
                getBoard().setSelectedPiece(new Coord2d(X,Y));
            }else if(getBoard().getLogic().pieceColor(X, Y) == getBoard().getClient().getPieceColor()){
                getBoard().setSelectedPiece(new Coord2d(X,Y));
            }

        }
        getBoard().update();

        getBoard().flag = true;

    }


    @Override
    public void mouseReleased(MouseEvent e) {

        IntBuffer Xbuffer = IntBuffer.allocate(1);
        IntBuffer Ybuffer = IntBuffer.allocate(1);

        getBoard().flag = false;

        getXYPos(e, Xbuffer, Ybuffer);

        int X = Xbuffer.get(0);
        int Y = Ybuffer.get(0);

        if(getBoard().getSelectedPiece() != null){
            try {
                Piece piece = getBoard().getLogic().getPiece(getBoard().getSelectedPiece());

                if(getBoard().isOnline()){
                    String t = "SELECT//"+getBoard().getTurn().getPieceColor().ordinal()+"//"+
                            piece.getX()+"/"+piece.getY()+"//"+X+"/"+Y;

                    getBoard().sendRequest(t);
                }else{
                    getBoard().turnMove(getBoard().getTurn(),piece , new Coord2d(X, Y));
                }
                getBoard().resetAndInitPiecesTexture();

            } catch (Exception exception) {

            }
        }

        System.out.println("X:  " + X);
        System.out.println("Y:  " + Y);
        System.out.println("piece:  "+getBoard().getLogic().getPiece(X, Y).getType());
        System.out.println("Color:  "+getBoard().getLogic().getPiece(X, Y).getColor());
        getBoard().update();
        getBoard().resetAndInitPiecesTexture();
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
