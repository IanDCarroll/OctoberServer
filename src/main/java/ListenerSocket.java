import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;

public class ListenerSocket {
    private int port;

    public ListenerSocket(int port) {
        this.port = port;
    }

    public Socket getClientConnection() {
        System.out.println("starting server");
        Socket clientConnection = new Socket();
        try {
            clientConnection = startListening();
        } catch (IOException e) {
            System.out.println("IOException while trying to initialize Server Socket");
        }
        return clientConnection;
    }

    private Socket startListening() throws IOException {
        ServerSocket listener = new ServerSocket(port);
        Socket clientConnection = new Socket();
        try {
            clientConnection = acceptAnIncomingCall(listener);
        } catch (SocketException e) {
            System.out.println("SocketException while trying to listen for requests");
        }
        return clientConnection;
    }

    private Socket acceptAnIncomingCall(ServerSocket listener) throws SocketException {
        Socket clientConnection = new Socket();
        try {
            clientConnection = listener.accept();
        } catch (IOException e) {
            System.out.println("IOException while trying to accept an incoming call");
        }
        return clientConnection;
    }
}
