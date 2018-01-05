package FunctionalCore.Controller.SubControllers;

import FunctionalCore.Controller.ResponseGeneration.ClientErrorGenerator;
import FunctionalCore.Request;

import java.util.LinkedHashMap;

public class UriController implements SubController {
    ClientErrorGenerator clientErrorGenerator;

    public UriController(ClientErrorGenerator clientErrorGenerator) {
        this.clientErrorGenerator = clientErrorGenerator;
    }

    public boolean relevant(Request request, LinkedHashMap<String, LinkedHashMap<String, String>> routes) {
        return notInRoutes(request, routes) || badParams(request);
    }

    private boolean notInRoutes(Request request, LinkedHashMap<String, LinkedHashMap<String, String>> routes) {
        return !routes.containsKey(request.getUri());
    }

    private boolean badParams(Request request) {
        String[] params = request.getUriParams();
        for (String param : params) {
            if (bad(param)) { return true; }
        }
        return false;
    }

    private boolean bad(String param) {
        String delimiter = "=";
        boolean noKey = param.startsWith(delimiter);
        boolean noValue = param.endsWith(delimiter);
        boolean noDelimiter = !param.contains(delimiter);
        return noKey || noValue || noDelimiter;
    }

    public byte[] generate(Request request, LinkedHashMap<String, LinkedHashMap<String, String>> routes) {
        return notInRoutes(request, routes)
                ? clientErrorGenerator.generate(ClientErrorGenerator.Code.NOT_FOUND)
                : clientErrorGenerator.generate(ClientErrorGenerator.Code.BAD_REQUEST);
    }
}
