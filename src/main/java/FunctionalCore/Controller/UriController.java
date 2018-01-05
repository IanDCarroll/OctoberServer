package FunctionalCore.Controller;

import FunctionalCore.Controller.ResponseGeneration.ClientErrorGenerator;
import FunctionalCore.Request;

import java.util.LinkedHashMap;

public class UriController implements SubController {
    ClientErrorGenerator clientErrorGenerator;

    public UriController(ClientErrorGenerator clientErrorGenerator) {
        this.clientErrorGenerator = clientErrorGenerator;
    }

    public boolean relevant(Request request, LinkedHashMap<String, LinkedHashMap<String, String>> routes) {
        return !routes.containsKey(request.getUri());
    }

    public byte[] generate(Request request, LinkedHashMap<String, LinkedHashMap<String, String>> routes) {
        return clientErrorGenerator.generate(ClientErrorGenerator.Code.NOT_FOUND);
    }
}
