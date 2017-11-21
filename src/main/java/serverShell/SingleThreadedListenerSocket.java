package serverShell;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;

public class SingleThreadedListenerSocket {
    private ServerSocket listener;

    public SingleThreadedListenerSocket(int port) {
        System.out.format("Listener Socket port: %d\n", port);
        try {
            this.listener = new ServerSocket(port);
        } catch (IOException e) {
            System.out.println("IOException while trying to initialize Server Socket; check if the port is occupied.");
        }
    }

    public Socket getClientConnection() {
        try {
            return acceptAnIncomingCall();
        } catch (IOException e) {
            throw new IllegalArgumentException("IOException while trying to listen for requests");
        }
    }

    private Socket acceptAnIncomingCall() throws IOException {
        return listener.accept();
    }
}