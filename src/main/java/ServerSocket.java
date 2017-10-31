public class ServerSocket {
    // on init takes a Client Socket as a param
    // implements Reactive Programming to manage Server Streams and concurrency
    // on .accept initializes a new client socket
    ServerSocket(int port, String directory) {
        System.out.println("server socket initialized");
    }

    public void start() {
        System.out.println("starting server");
    }
}
