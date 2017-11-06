import java.io.IOException;
import java.net.ServerSocket;

public class ListenerSocket {
    // on init takes a Client Socket as a param
    // implements Reactive Programming to manage Server Streams and concurrency
    // on .accept initializes a new client socket
    public ResponderSocket client;

    public ListenerSocket(int port, String directory, ResponderSocket client) {
        this.client = client;
    }

    public void start() {
        System.out.println("starting server");
    }
}
