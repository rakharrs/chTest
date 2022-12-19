package online.server;

import chessgame.JBoard;
import chessgame.PieceColor;
import util.BoardScene;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class GameServer implements Runnable{

    private ServerSocket server;
    private final BoardScene board;

    private ArrayList<ClientHandler> clients;
    public static final int port = 7243;
    private int portPers;
    boolean full = false;


    public GameServer(){
        setPort(port);
        this.board = new JBoard();
        setClients(new ArrayList<>());
    }

    public GameServer(int newPort){
        setPort(newPort);
        System.out.println(getPort());
        this.board = new JBoard();
        setClients(new ArrayList<>());
    }

    @Override
    public void run() {
        try {
            server = new ServerSocket(getPort());

            /*while(getClients().size() < 2){

            }*/
            System.out.println("ok");

                System.out.println("Waiting Connection");
                Socket client = server.accept();

                System.out.println("Connection accepted");

                ClientHandler handler = new ClientHandler(this, client, board, PieceColor.WHITE);

                handler.getOutput().writeUTF("SET COLOR//" + PieceColor.WHITE.ordinal());
                handler.getOutput().flush();
                handler.getOutput().reset();

                System.out.println("WHITE connected");

                getClients().add(handler);



                System.out.println("Waiting Connection");
                Socket client2 = server.accept();

                System.out.println("Connection accepted");

                ClientHandler handler2 = new ClientHandler(this, client2, board, PieceColor.BLACK);

                handler2.getOutput().writeUTF("SET COLOR//" + PieceColor.BLACK.ordinal());
                handler2.getOutput().flush();
                handler2.getOutput().reset();

                System.out.println("BLACK connected");

                getClients().add(handler2);

                setFull(true);

                handler.start();
                handler2.start();


                System.out.println("handler started");

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public int[] parseInteger(String... strings){
        int[] val = new int[strings.length];

        for (int i = 0; i < strings.length; i++) {
            val[i] = Integer.parseInt(strings[i]);
        }

        return val;
    }

    public ArrayList<ClientHandler> getClients() {
        return clients;
    }

    public void setClients(ArrayList<ClientHandler> clients) {
        this.clients = clients;
    }

    public ServerSocket getServer() {
        return server;
    }

    public void setServer(ServerSocket server) {
        this.server = server;
    }

    public BoardScene getBoard() {
        return board;
    }

    public boolean isFull() {
        return full;
    }

    public void setFull(boolean full) {
        this.full = full;
    }

    public int getPort() {
        return portPers;
    }

    public void setPort(int portPers) {
        this.portPers = portPers;
    }
}
