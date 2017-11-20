package functionalCore;

public class HTTPCore implements functionalCore.Core {
    Parser parser;
    Controller controller;

    public HTTPCore(Parser parser, Controller controller) {
        this.parser = parser;
        this.controller = controller;
    }

    @Override
    public byte[] respondTo(byte[] request) {
        return new byte[0];
    }
}
