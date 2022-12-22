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

        this(online, hostip, GameServer.port);
    }
    public ChessFrame(boolean online, String hostip, int port) throws Exception{
        createChessFrame();
        setLayout(null);
        setSize(600,600);
        setMaximumSize(new Dimension(600,630));

        setBoard(new JBoard(this, getWidht()/8, getHeight()/8, online, hostip, port));

        /*  Création du listener de l'échiquier */

        BoardInteraction bi = new BoardInteraction(getBoard());
        getBoard().addMouseListener(bi);
        getBoard().addMouseMotionListener(bi);

        /*   Ajout de l'échiquier   */

        add(getBoard());

        setResizable(true);
        setDefaultCloseOperation(3);

        getBoard().update();
        getBoard().resetAndInitPiecesTexture();

        Toolkit.getDefaultToolkit().sync();
    }

    public static ChessFrame createChess(boolean online, String hostip){
        try {
            return new ChessFrame(online, hostip);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static ChessFrame createChess(boolean online, boolean host, String hostip){

        return createChess(online, host, hostip, GameServer.port);
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
