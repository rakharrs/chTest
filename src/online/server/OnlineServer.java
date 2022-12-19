package online.server;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketAddress;
import java.util.ArrayList;

public class OnlineServer extends Thread{

    private ArrayList<GameServer> servers;
    private static InetAddress address;

    ServerSocket server;

    public static final int port = 9799;

    public OnlineServer(GameServer... servers){

        for (int i = 0; i < servers.length; i++) {

            this.servers.add(servers[i]);
        }
    }

    public OnlineServer(ArrayList<GameServer> servers){

        setServers(servers);
    }

    @Override
    public void run(){
        try {
            Socket client = getServer().accept();

            for (int i = 0; i < getServers().size(); i++) {
                if(!getServers().get(i).full){
                    client.connect(getServers().get(0).getServer().getLocalSocketAddress());

                }
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void initServers(){
        for (int i = 0; i < 3; i++) {

            GameServer serv = new GameServer();

            getServers().add(serv);

            new Thread(serv).start();
        }
    }

    public ArrayList<GameServer> getServers() {
        return servers;
    }

    public void setServers(ArrayList<GameServer> servers) {
        this.servers = servers;
    }

    public static InetAddress getAddress() {
        return address;
    }

    public static void setAddress(InetAddress address) {
        OnlineServer.address = address;
    }

    public ServerSocket getServer() {
        return server;
    }

    public void setServer(ServerSocket server) {
        this.server = server;
    }
}
