package chessengine;

import chessgame.JBoard;
import chessgame.PieceColor;
import online.server.GameServer;
import util.listener.BoardInteraction;

import javax.swing.*;

import java.awt.*;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.NetworkInterface;

public class ChessFrame extends JFrame implements Runnable{
    private int height = 600;
    private int widht = 600;

    JBoard JBoard;

    public ChessFrame(boolean online, String hostip) throws Exception{
        createChessFrame();
        setLayout(null);
        setSize(600,600);
        setMaximumSize(new Dimension(600,630));

        setBoard(new JBoard(this, getWidht()/8, getHeight()/8, online, hostip, GameServer.port));


        BoardInteraction bi = new BoardInteraction(getBoard());
        getBoard().addMouseListener(bi);
        getBoard().addMouseMotionListener(bi);

        add(getBoard());
        //getBoard().setBounds(0, 0, 600, 600);

        setResizable(true);
        setDefaultCloseOperation(3);

        getBoard().update();
        getBoard().resetAndInitPiecesTexture();
        Toolkit.getDefaultToolkit().sync();
        //loop();
    }
    public ChessFrame(boolean online, String hostip, int port) throws Exception{
        createChessFrame();
        setLayout(null);
        setSize(600,600);
        setMaximumSize(new Dimension(600,630));

        setBoard(new JBoard(this, getWidht()/8, getHeight()/8, online, hostip, port));


        BoardInteraction bi = new BoardInteraction(getBoard());
        getBoard().addMouseListener(bi);
        getBoard().addMouseMotionListener(bi);

        add(getBoard());
        //getBoard().setBounds(0, 0, 600, 600);

        setResizable(true);
        setDefaultCloseOperation(3);

        getBoard().update();
        getBoard().resetAndInitPiecesTexture();
        Toolkit.getDefaultToolkit().sync();
        //loop();
    }

    public static ChessFrame createChess(boolean online, String hostip){
        try {
            return new ChessFrame(online, hostip);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static ChessFrame createChess(boolean online, boolean host, String hostip){
        try {
            if (host && online) {
                GameServer serv = new GameServer();
                Thread srv = new Thread(serv);
                srv.start();
                ChessFrame cf = new ChessFrame(online, "localhost");

                cf.setTitle(serv.getServer().getInetAddress().getHostAddress());
                return cf;
            }

            return new ChessFrame(online, hostip);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static ChessFrame createChess(boolean online, boolean host, String hostip, int port){
        try {
            if (host && online) {
                System.out.println("host port "+port);
                GameServer serv = new GameServer(port);
                Thread srv = new Thread(serv);
                srv.start();

                ChessFrame cf = new ChessFrame(online, "localhost", port);

                cf.setTitle(serv.getServer().getInetAddress().getHostAddress());
                return cf;
            }

            return new ChessFrame(online, hostip, port);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }



    public void createChessFrame(){
        setTitle("Chessgame");
        setSize(getWidht(), getHeight());
        setVisible(true);
    }
    public void drawSquares(Graphics g){

        int caseWith = getBoard().getCaseWidth();
        int caseHeight = getBoard().getCaseHeight();
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                int x1=caseWith*i; int x2=caseWith*(i+1);
                int y1=caseHeight*j; int y2=caseHeight*(j+1);

                if((i+j)%2==0){

                    if(getBoard().getClient() != null && getBoard().getClient().getPieceColor()== PieceColor.BLACK){

                        g.setColor(Color.WHITE);
                    }

                    g.setColor(Color.lightGray);

                }else{

                    if(getBoard().getClient() != null && getBoard().getClient().getPieceColor()==PieceColor.BLACK){

                        g.setColor(Color.lightGray);
                    }

                    g.setColor(Color.WHITE);
                }

                g.fillRect(x1, y1, x2-x1, y2-y1);
            }
        }
    }

    public JBoard getBoard() {
        return JBoard;
    }

    public void setBoard(JBoard JBoard) {
        this.JBoard = JBoard;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWidht() {
        return widht;
    }

    public void setWidht(int widht) {
        this.widht = widht;
    }

    @Override
    public void run() {

    }
}
