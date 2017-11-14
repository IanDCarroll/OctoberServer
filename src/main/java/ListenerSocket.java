import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;

public class ListenerSocket {
    private ServerSocket listener;

    public ListenerSocket(int port) {
        try {
            this.listener = new ServerSocket(port);
        } catch (IOException e) {
            System.out.println("IOException while trying to initialize Server Socket");
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
