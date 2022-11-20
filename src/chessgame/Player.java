package chessgame;

import util.Coord2d;

public class Player {
    String name;
    int score = 0;

    PieceColor pieceColor;

    Coord2d selectedPiece = null;

    public Player(String name, PieceColor piececolor){
        setName(name);
        setPieceColor(piececolor);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Coord2d getSelectedPiece() {
        return selectedPiece;
    }

    public void setSelectedPiece(Coord2d selectedPiece) {
        this.selectedPiece = selectedPiece;
    }
    public PieceColor getPieceColor() {
        return pieceColor;
    }

    public void setPieceColor(PieceColor pieceColor) {
        this.pieceColor = pieceColor;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}
