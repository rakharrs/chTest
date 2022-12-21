package chessgame;

import util.Coord2d;

import java.awt.*;
import java.io.Serializable;
import java.util.ArrayList;

public class chessLogic implements Serializable {
    Piece[][] pieces;
    PieceColor turn = PieceColor.WHITE;

    public chessLogic(Piece[][] pieces){
        setPieces(pieces);
    }

    public boolean couldAttack(Piece piece, Coord2d coord){
        ArrayList<Coord2d> possibilities = getLegalMove(piece.getX(), piece.getY());
        System.out.println(piece.getX()+"//"+piece.getY());
        for (int i = 0; i < possibilities.size(); i++) {
            if(possibilities.get(i).getX() == coord.getX() && possibilities.get(i).getY() == coord.getY()){
                return true;
            }
        }
        return  false;
    }

    public ArrayList<Coord2d> canAttack(Piece piece){

        return canAttack(piece.getX(), piece.getY());
    }
    public ArrayList<Coord2d> canAttack(int x, int y){
        ArrayList<Coord2d> val = new ArrayList<>();
        Piece piece = getPiece(x, y);
        final boolean isWhite = piece.getColor() == PieceColor.WHITE;
        int pawnUpDirection = isWhite ? 1 : -1;

        switch (piece.getType()){
            case PAWN -> {
                if(!isPiece(x, y + pawnUpDirection)){
                    if(!isPiece(x, y + (pawnUpDirection*2)) && y == 1 || !isPiece(x, y + (pawnUpDirection*2)) && y == 6){
                        if(x < 8 && x >= 0 && y + (pawnUpDirection*2) < 8 && y + (pawnUpDirection*2) >= 0){

                            val.add(new Coord2d(x, y + (pawnUpDirection*2)));
                        }
                    }
                    if(x < 8 && x >= 0 && y + pawnUpDirection < 8 && y + pawnUpDirection >= 0){

                        val.add(new Coord2d(x, y + pawnUpDirection));
                    }
                }

                if(isPieceEnemy(x + 1, y + pawnUpDirection, piece))
                    val.add(new Coord2d(x + 1, y + pawnUpDirection));
                if(isPieceEnemy(x - 1, y + pawnUpDirection, piece))
                    val .add(new Coord2d(x - 1, y + pawnUpDirection));
                return val;

            }
            case KNIGHT -> {
                return knightPossibleMove(x, y);

            }
            case BISHOP -> {
                return bishopPossibleMove(x, y);

            }
            case ROOK -> {
                return RookPossibleMove(x, y);
            }
            case QUEEN -> {

                val.addAll(bishopPossibleMove(x, y));

                val.addAll(RookPossibleMove(x, y));
                return val;
            }
            case KING -> {
                return kingPossibleMove(x, y);
            }


        }
        return val;
    }

    public Piece getPiece(int x, int y){
        if(x < 8 && x >= 0 && y < 8 && y >= 0){
            return getPieces()[x][y];
        }
        return null;
    }

    public Piece getPiece(Coord2d coo){
        if(coo != null && coo.getX() < 8 && coo.getX() >= 0 && coo.getY() < 8 && coo.getY() >= 0){
            return getPieces()[coo.getX()][coo.getY()];
        }
        return null;
    }


    public boolean isPieceEnemy(int x, int y, Piece piece){
        if(isPiece(x, y) && !getPiece(x, y).sameColor(piece)){
            return true;
        }
        return false;
    }

    public boolean isPiece(int x, int y){
        Piece piece = getPiece(x, y);

        if(piece != null && piece.getType() != PieceType.NONE){
            return true;
        }
        return false;
    }

    public PieceColor pieceColor(int x, int y){
        Piece piece = getPiece(x, y);
        if(piece != null){
            return piece.getColor();
        }
        return PieceColor.UNSET;

    }
    public Piece[][] getPieces() {
        return pieces;
    }

    public ArrayList<Coord2d> knightPossibleMove(int x, int y){
        ArrayList<Coord2d> val = new ArrayList<>();
        Piece piece = getPiece(x, y);
        if(!piece.sameColor(getPiece(x + 1, y + 2)) && x + 1 < 8 && x + 1 >= 0 && y + 2 < 8 && y + 2 >= 0)
            val.add(new Coord2d(x + 1, y + 2));
        if(!piece.sameColor(getPiece(x - 1, y + 2)) && x-1 < 8 && x-1 >= 0 && y + 2 < 8 && y + 2 >= 0)
            val.add(new Coord2d(x - 1, y + 2));
        if(!piece.sameColor(getPiece(x + 2, y + 1)) && x + 2 < 8 && x + 2 >= 0 && y + 1 < 8 && y + 1 >= 0)
            val.add(new Coord2d(x + 2, y + 1));
        if(!piece.sameColor(getPiece(x - 2, y + 1)) && x -2 < 8 && x -2 >= 0 && y + 1 < 8 && y + 1 >= 0)
            val.add(new Coord2d(x - 2, y + 1));

        if(!piece.sameColor(getPiece(x + 1, y - 2)) && x + 1 < 8 && x + 1 >= 0 && y - 2 < 8 && y - 2 >= 0)
            val.add(new Coord2d(x + 1, y - 2));
        if(!piece.sameColor(getPiece(x - 1, y - 2)) && x - 1 < 8 && x - 1 >= 0 && y - 2 < 8 && y - 2 >= 0)
            val.add(new Coord2d(x - 1, y - 2));
        if(!piece.sameColor(getPiece(x + 2, y - 1)) && x + 2 < 8 && x + 2 >= 0 && y - 1 < 8 && y - 1 >= 0)
            val.add(new Coord2d(x + 2, y - 1));
        if(!piece.sameColor(getPiece(x - 2, y - 1)) && x - 2 < 8 && x - 2 >= 0 && y - 1 < 8 && y - 1 >= 0)
            val.add(new Coord2d(x - 2, y - 1));

        return val;
    }

    public ArrayList<Coord2d> kingPossibleMove(int x, int y){
        ArrayList<Coord2d> val = new ArrayList<>();
        ArrayList<Coord2d> castle = canCastle(x, y);
        if(castle!=null && castle.size() > 0){

            val.addAll(canCastle(x, y));
        }
        for (int i = -1; i <= 1; i++) {
            if( x + i < 8 && x + i >= 0 && y + 1 < 8 && y + 1 >= 0){

                if(isPiece(x + i, y + 1)) {
                    if (pieceColor(x, y) != pieceColor(x + i, y + 1)){
                        val.add(new Coord2d(x + i, y + 1));
                    }

                }else
                    val.add(new Coord2d(x + i, y + 1));
            }
        }

        for (int i = -1; i <= 1; i++) {
            if( x + i < 8 && x + i >= 0 && y - 1 < 8 && y - 1 >= 0){

                if(isPiece(x + i, y - 1)) {
                    if (pieceColor(x, y) != pieceColor(x + i, y - 1)){
                        val.add(new Coord2d(x + i, y - 1));
                    }

                }else
                    val.add(new Coord2d(x + i, y - 1));
            }
        }

        if(x + 1 < 8 && x + 1 >= 0 && y < 8 && y >= 0){
            if(isPiece(x + 1, y)){
                if(pieceColor(x, y) != pieceColor(x + 1, y))
                    val.add(new Coord2d(x + 1, y));
            }else
                val.add(new Coord2d(x + 1, y));
        }
        if(x - 1 < 8 && x - 1 >= 0 && y < 8 && y >= 0){

            if(isPiece(x - 1, y)){
                if(pieceColor(x, y) != pieceColor(x - 1, y))
                    val.add(new Coord2d(x - 1, y));
            }else
                val.add(new Coord2d(x - 1, y));
        }

        return val;
    }

    public int isCheckmate(PieceColor color) throws Exception {
        ArrayList<Piece> pieces = getPieces(color);

        for(Piece piece : pieces){
            ArrayList<Coord2d> moves = getLegalMove(piece);
            if(moves.size() > 0 || getLegalMove(getKing(color)).size() > 0){

                return 1;               // Mbola tsy checkmate fa afaka mitohy ny lalao
            }
        }

        if(isInCheck(color)){
            return 0;                   // Echec et mate

        }

        return -1;                      // Stalemate
    }
    public ArrayList<Coord2d> canCastle(int x, int y){
        ArrayList<Coord2d> val = new ArrayList<>();
        if(isInCheck(getPiece(x, y).getColor())){
            return null;
        }
        if(getPiece(x, y).getType()!=PieceType.KING || y!=0 && y!=7)
            return null;

        if(x==4){
            if(getPiece(0,y).getType()==PieceType.ROOK){
                for (int i = 1; x-i > 0; i++) {
                    if(isPiece(x-i,y)){
                        break;
                    }
                    if( x - i - 1 == 0)
                        val.add(new Coord2d(x-2, y));
                }
            }
            if(getPiece(7,y).getType()==PieceType.ROOK){
                for (int i = 1; x+i < 7; i++) {
                    if(isPiece(x+i,y)){
                        break;
                    }
                    if( x + i + 1 == 7)
                        val.add(new Coord2d(x+2, y));
                }
            }
        }

        return val;
    }
    public ArrayList<Coord2d> bishopPossibleMove(int x, int y){
        ArrayList<Coord2d> val = new ArrayList<>();

        for (int i = 1; i < 8; i++) {
            int pX = x + i; int pY = y + i;
            Piece piece = getPiece(pX, pY);
            if (piece == null) {
                break;
            }
            if(piece.getType() != PieceType.NONE){
                if(piece.getColor() != getPiece(x, y).getColor()){
                    val.add(new Coord2d(pX, pY));
                }
                break;
            }
            val.add(new Coord2d(pX, pY));
        }

        for (int i = 1; i < 8; i++) {
            int pX = x + i; int pY = y - i;
            Piece piece = getPiece(pX, pY);
            if (piece == null) {
                break;
            }
            if(piece.getType() != PieceType.NONE){
                if(piece.getColor() != getPiece(x, y).getColor()){
                    val.add(new Coord2d(pX, pY));
                }
                break;
            }
            val.add(new Coord2d(pX, pY));
        }

        for (int i = 1; i < 8; i++) {
            int pX = x - i; int pY = y - i;
            Piece piece = getPiece(pX, pY);
            if (piece == null) {
                break;
            }
            if(piece.getType() != PieceType.NONE){
                if(piece.getColor() != getPiece(x, y).getColor()){
                    val.add(new Coord2d(pX, pY));
                }
                break;
            }
            val.add(new Coord2d(pX, pY));
        }

        for (int i = 1; i < 8; i++) {
            int pX = x - i; int pY = y + i;
            Piece piece = getPiece(pX, pY);
            if (piece == null) {
                break;
            }
            if(piece.getType() != PieceType.NONE){
                if(piece.getColor() != getPiece(x, y).getColor()){
                    val.add(new Coord2d(pX, pY));
                }
                break;
            }
            val.add(new Coord2d(pX, pY));
        }

        return val;
    }

    public ArrayList<Coord2d> RookPossibleMove(int x, int y){
        ArrayList<Coord2d> val = new ArrayList<>();
        for (int i = 1; i < 8; i++) {
            int pX = x + i; int pY = y;
            Piece piece = getPiece(pX, pY);
            if (piece == null) {
                break;
            }
            if(piece.getType() != PieceType.NONE){
                if(piece.getColor() != getPiece(x, y).getColor()){
                    val.add(new Coord2d(pX, pY));
                }
                break;
            }
            val.add(new Coord2d(pX, pY));
        }

        for (int i = 1; i < 8; i++) {
            int pX = x - i; int pY = y;
            Piece piece = getPiece(pX, pY);
            if (piece == null) {
                break;
            }
            if(piece.getType() != PieceType.NONE){
                if(piece.getColor() != getPiece(x, y).getColor()){
                    val.add(new Coord2d(pX, pY));
                }
                break;
            }
            val.add(new Coord2d(pX, pY));
        }

        for (int i = 1; i < 8; i++) {
            int pX = x; int pY = y - i;
            Piece piece = getPiece(pX, pY);
            if (piece == null) {
                break;
            }
            if(piece.getType() != PieceType.NONE){
                if(piece.getColor() != getPiece(x, y).getColor()){
                    val.add(new Coord2d(pX, pY));
                }
                break;
            }
            val.add(new Coord2d(pX, pY));
        }

        for (int i = 1; i < 8; i++) {
            int pX = x; int pY = y + i;
            Piece piece = getPiece(pX, pY);
            if (piece == null) {
                break;
            }
            if(piece.getType() != PieceType.NONE){
                if(piece.getColor() != getPiece(x, y).getColor()){
                    val.add(new Coord2d(pX, pY));
                }
                break;
            }
            val.add(new Coord2d(pX, pY));
        }

        return val;
    }

    public boolean isInCheck(PieceColor color){
        try {
            Piece king = getKing(color);
            PieceColor opponentColor = (color == PieceColor.WHITE) ? PieceColor.BLACK : PieceColor.WHITE;

            // Check if any of the opponent's pieces can attack the king's position
            ArrayList<Piece> opponentPieces = getPieces(opponentColor);
            for(Piece oppPiece : opponentPieces){

                ArrayList<Coord2d> moves = canAttack(oppPiece);
                for(Coord2d move : moves){

                    if(move.getX() == king.getX() && move.getY() == king.getY()){
                        return true;
                    }
                }
            }

        }catch (Exception e){

            System.out.println(e);
            System.out.println(e.getMessage());
        }

        return false;
    }

    public ArrayList<Coord2d> getLegalMove(Piece piece){

        return getLegalMove(piece.getX(), piece.getY());
    }

    public ArrayList<Coord2d> getLegalMove(int x, int y){
        ArrayList<Coord2d> val = new ArrayList<>();
        Piece piece = getPiece(x, y);
        ArrayList<Coord2d> moves = canAttack(x, y);
        PieceColor oppColor = getOpponentColor(piece.getColor());
        System.out.println("Color : "+piece.getColor().toString());
        System.out.println("OppColor : "+oppColor.toString());

        for(Coord2d move : moves){
            int mX = move.getX(); int mY = move.getY();

            PieceColor mC = getPieces()[mX][mY].getColor();
            PieceType mT = getPieces()[mX][mY].getType();

            PieceColor pC = getPieces()[x][y].getColor();
            PieceType pT = getPieces()[x][y].getType();

            getPieces()[mX][mY].setType(piece.getType());
            getPieces()[mX][mY].setColor(piece.getColor());

            getPieces()[x][y].setType(PieceType.NONE);
            getPieces()[x][y].setColor(PieceColor.UNSET);

            if(!isInCheck(pC)){

                val.add(move);
            }

            getPieces()[mX][mY].setColor(mC);
            getPieces()[mX][mY].setType(mT);

            getPieces()[x][y].setColor(pC);
            getPieces()[x][y].setType(pT);
        }

        return val;
    }

    public ArrayList<Piece> getPieces(PieceColor color){
        ArrayList<Piece> val = new ArrayList<>();

        for (int i = 0; i < getPieces().length; i++) {
            for (int j = 0; j < getPieces()[i].length; j++) {

                if(getPieces()[i][j].getColor()==color && getPieces()[i][j].getType()!=PieceType.KING){

                    val.add(getPieces()[i][j]);
                }

            }

        }
        return val;
    }

    public PieceColor getOpponentColor(PieceColor color){

        if(color == PieceColor.UNSET){
            return PieceColor.UNSET;
        }

        PieceColor opponentColor = (color == PieceColor.WHITE) ? PieceColor.BLACK : PieceColor.WHITE;

        return opponentColor;

    }

    public Piece getKing(PieceColor color) throws Exception{
        for (int i = 0; i < getPieces().length; i++) {
            for (int j = 0; j < getPieces()[i].length; j++) {
                if(getPieces()[i][j].getType() == PieceType.KING && getPieces()[i][j].getColor() == color){
                    return getPieces()[i][j];
                }
            }
        }
        throw new Exception("There is no "+color.toString()+" King !");
    }
    public PieceColor getTurn() {
        return turn;
    }

    public void setTurn(PieceColor turn) {
        this.turn = turn;
    }

    public void setPieces(Piece[][] pieces) {
        this.pieces = pieces;
    }
}
