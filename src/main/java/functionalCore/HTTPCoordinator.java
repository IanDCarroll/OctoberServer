package functionalCore;

public class HTTPCoordinator implements CoreCoordinator {
    Parser parser;
    Controller controller;

    public HTTPCoordinator(Parser parser, Controller controller) {
        this.parser = parser;
        this.controller = controller;
    }

    @Override
    public byte[] respondTo(byte[] request) {
        return new byte[0];
    }
}
