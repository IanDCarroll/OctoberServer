import java.util.LinkedHashMap;

public class Factory {
    private int port;
    private String directory;

    public Factory(int port, String directory) {
        this.port = port;
        this.directory = directory;
    }

    public ListenerSocket buildServer() {

        System.out.format("building server with port %d and directory %s\n", port, directory);
        Parser parser = new Parser();
        ResponseGenerator responseGenerator = new ResponseGenerator();
        ConfigImporter configImporter = new ConfigImporter();
        LinkedHashMap routes = configImporter.importConfig();
        Controller controller = new Controller(responseGenerator, routes);
        ResponderSocket client = new ResponderSocket(parser, controller);
        return new ListenerSocket(port, directory, client);
    }
}
