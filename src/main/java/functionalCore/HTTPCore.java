package functionalCore;

public class HTTPCore implements functionalCore.Core {
    Parser parser;
    Controller controller;

    public HTTPCore(Parser parser, Controller controller) {
        this.parser = parser;
        this.controller = controller;
    }

    @Override
    public byte[] craftResponseTo(byte[] request) {
        return "HTTP/1.1 200 OK\r\n\r\n".getBytes();
    }
}
