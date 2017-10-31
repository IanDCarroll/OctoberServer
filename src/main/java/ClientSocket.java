public class ClientSocket {
    // on init accepts a controller as a parameter
    // accepts a byte[] from a client
    // returns a response byte[] to the client by means of the Controller
    public ByteConverter byteConverter;
    public Parser parser;
    public Controller controller;
    public DeParser deParser;


    public ClientSocket(ByteConverter byteConverter, Parser parser, Controller controller, DeParser deParser) {
        System.out.println("client socket initialized");
        this.byteConverter = byteConverter;
        this.parser = parser;
        this.controller = controller;
        this.deParser = deParser;
    }
}
