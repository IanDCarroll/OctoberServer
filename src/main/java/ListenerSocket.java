import java.io.IOException;
import java.net.ServerSocket;
import java.net.SocketException;

public class ListenerSocket {
    // implements Reactive Programming to manage Server Streams and concurrency
    private ResponderSocket responder;
    private int port;

    public ListenerSocket(int port, String directory, ResponderSocket responder) {
        this.port = port;
        this.responder = responder;
    }

    public void start() {
        System.out.println("starting server");
        try {
            toStartListening();
        } catch (IOException e) {
            System.out.println("IOException while trying to initialize Server Socket");
        }
    }

    private void toStartListening() throws IOException {
        ServerSocket listener = new ServerSocket(port);
        try {
            toAcceptAnIncomingCall(listener);
        } catch (SocketException e) {
            System.out.println("SocketException while trying to listen for requests");
        }
    }

    private void toAcceptAnIncomingCall(ServerSocket listener) throws IOException {
        responder.respondTo(listener.accept());
    }
}
