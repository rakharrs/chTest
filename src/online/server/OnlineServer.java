package online.server;

import java.net.InetAddress;
import java.util.ArrayList;

public class OnlineServer extends Thread{

    private ArrayList<GameServer> servers;
    private static InetAddress address;

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
}
