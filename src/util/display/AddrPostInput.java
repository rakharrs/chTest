package util.display;

import formAPI.display.Formulaire;

public class AddrPostInput {
    String address;
    int port;

    public AddrPostInput(){

    }
    public AddrPostInput(String address, int port){

        setAddress(address);
        setPort(port);
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }
}
