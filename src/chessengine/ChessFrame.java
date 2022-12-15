package chessengine;

import chessgame.JBoard;
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
        setSize(600,627);

        setBoard(new JBoard(this, getWidht()/8, getHeight()/8, online, hostip));


        BoardInteraction bi = new BoardInteraction(getBoard());
        getBoard().addMouseListener(bi);
        getBoard().addMouseMotionListener(bi);

        add(getBoard());
        //getBoard().setBounds(0, 0, 600, 600);

        setResizable(false);
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

    @Override
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
