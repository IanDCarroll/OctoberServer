package FunctionalCore;

import FunctionalCore.Controller.Controller;
import FunctionalCore.Parser.Parser;

public class HTTPCore implements FunctionalCore.Core {
    Parser parser;
    Controller controller;

    public HTTPCore(Parser parser, Controller controller) {
        this.parser = parser;
        this.controller = controller;
    }

    @Override
    public byte[] craftResponseTo(byte[] request) {
        Request parsedRequest = parser.parse(request);
        return controller.getAppropriateResponse(parsedRequest);
    }
}
