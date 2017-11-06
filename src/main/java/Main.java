public class Main {
    public static void main(String[] args) {
        Factory factory = Factory.constructYourself(args);
        ListenerSocket server = factory.buildServer();
        server.start();
    }
}
