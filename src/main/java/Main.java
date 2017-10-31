public class Main {
    public static void main(String[] args) {
        Factory factory = Factory.constructYourself(args);
        ServerSocket server = factory.buildServer();
        server.start();
    }
}
