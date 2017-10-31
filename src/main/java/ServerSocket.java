public class ServerSocket {
    // on init takes a Client Socket as a param
    // implements Reactive Programming to manage Server Streams and concurrency
    // on .accept initializes a new client socket
    public ClientSocket client;

    public ServerSocket(int port, String directory, ClientSocket client) {
        System.out.println("server socket initialized");
        this.client = client;
    }

    public void start() {
        System.out.println("starting server");
    }
}
