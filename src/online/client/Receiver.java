package online.client;

import chessgame.PieceColor;
import util.BoardScene;

public class Receiver extends Thread{
    private BoardScene board;

    public Receiver(BoardScene board){
        setBoard(board);

    }

    public void run(){
        try {
            while(true){
                /*output.writeObject(getBoard().getLogic());
                output.flush();*/

                System.out.println("BEGIN");
                String req = getBoard().getClient().getInput().readUTF();
                System.out.println("NAHAVOAVAKY");
                translateResp(req);
                System.out.println("END");
            }

        }catch (Exception e){
            System.out.println(e);
            e.printStackTrace();
        }
    }

    public void translateResp(String resp) throws Exception {
        if(resp.startsWith("CHESS-BOARD")){
            System.out.println("CHESS-BOARD");
            getBoard().receiveChessPiece();
        }if(resp.startsWith("SET COLOR")){
            System.out.println("SET COLOR");

            String[] res = resp.split("//");

            PieceColor c = PieceColor.values()[Integer.parseInt(res[1])];
            getBoard().getClient().setPieceColor(c);
        }
    }

    public BoardScene getBoard() {
        return board;
    }

    public void setBoard(BoardScene board) {
        this.board = board;
    }
}
