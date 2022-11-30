package online;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ConnectionManager extends Thread{
    boolean host;
    Socket client;
    ServerSocket listener = null;
    DataInputStream din;
    DataOutputStream dout;

    public ConnectionManager(boolean host){
        setHost(host);
        if(isHost()){
            ServerSocket listener = null;
            try {
                listener = new ServerSocket(7243);
                setListener(listener);
                this.start();

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }else{
            Socket client = null;
            try {

                client = new Socket("localhost",7243);
                setClient(client);
                setDataIO();

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void run(){
        if(isHost()){
            try {
                Socket client = getListener().accept();
                setClient(client);
                setDataIO();

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void setDataIO() throws IOException{
            DataInputStream input = new DataInputStream(getClient().getInputStream());
            DataOutputStream output = new DataOutputStream(getClient().getOutputStream());

            setDin(input);
            setDout(output);

    }

    public ServerSocket getListener() {
        return listener;
    }

    public void setListener(ServerSocket listener) {
        this.listener = listener;
    }

    public boolean isHost() {
        return host;
    }

    public void setHost(boolean host) {
        this.host = host;
    }

    public Socket getClient() {
        return client;
    }

    public void setClient(Socket client) {
        this.client = client;
    }

    public DataInputStream getDin() {
        return din;
    }

    public void setDin(DataInputStream din) {
        this.din = din;
    }

    public DataOutputStream getDout() {
        return dout;
    }

    public void setDout(DataOutputStream dout) {
        this.dout = dout;
    }
}
