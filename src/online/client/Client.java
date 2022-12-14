package online.client;

import chessgame.PieceColor;
import online.server.GameServer;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class Client {
    Socket clientSocket;
    ObjectOutputStream output;
    ObjectInputStream input;
    PieceColor pieceColor = PieceColor.WHITE;

    public Client(String ip) throws IOException{

        setClientSocket(new Socket(ip, GameServer.port));

        setOutput(new ObjectOutputStream(getClientSocket().getOutputStream()));

        setInput(new ObjectInputStream(getClientSocket().getInputStream()));
    }

    public Socket getClientSocket() {
        return clientSocket;
    }

    public void setClientSocket(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }

    public ObjectOutputStream getOutput() {
        return output;
    }

    public void setOutput(ObjectOutputStream output) {
        this.output = output;
    }

    public ObjectInputStream getInput() {
        return input;
    }

    public void setInput(ObjectInputStream input) {
        this.input = input;
    }

    public PieceColor getPieceColor() {
        return pieceColor;
    }

    public void setPieceColor(PieceColor pieceColor) {
        this.pieceColor = pieceColor;
    }

}
