package FunctionalCore.Controller.SubControllers;

import Filers.FileClerk;
import FunctionalCore.Controller.ResponseGeneration.ETagGenerator;
import FunctionalCore.Controller.ResponseGeneration.SuccessGenerator;
import FunctionalCore.Request;

import java.util.LinkedHashMap;

public class MethodController implements SubController {
    private FileClerk fileClerk;
    private ETagGenerator eTagGenerator;

    public MethodController(FileClerk fileClerk, ETagGenerator eTagGenerator) {
        this.fileClerk = fileClerk;
        this.eTagGenerator =   eTagGenerator;
    }

    public boolean relevant(Request request, LinkedHashMap<String, LinkedHashMap<String, String>> routes) {
        return theHTTPVersionIsSet(request);
    }

    private boolean theHTTPVersionIsSet(Request request) { return !request.getHttpV().equals("Not Set"); }

    public byte[] generate(Request request, LinkedHashMap<String, LinkedHashMap<String, String>> routes) {
        return findMethod(request);
    }

    private byte[] findMethod(Request request) {
        String method = request.getMethod();
        if (method.equals("GET")) { return get(request); }
        if (method.equals("HEAD")) { return head(request); }
        if (method.equals("POST")) { return post(request); }
        if (method.equals("PUT")) { return put(request); }
        if (method.equals("DELETE")) { return delete(request); }
        if (method.equals("PATCH")) { return patch(request); }
        return get(request);
    }

    private byte[] head(Request request) { return eTagGenerator.generateHead(SuccessGenerator.Code.OK, request.getUri()); }

    private byte[] post(Request request) {
        fileClerk.append(request.getUri(), request.getBody());
        return get(request);
    }

    private byte[] put(Request request) {
        fileClerk.rewrite(request.getUri(), request.getBody());
        return get(request);
    }

    private byte[] delete(Request request) {
        fileClerk.delete(request.getUri());
        return get(request);
    }

    private byte[] patch(Request request) {
        fileClerk.append(request.getUri(), request.getBody());
        return eTagGenerator.generate(SuccessGenerator.Code.NO_CONTENT, request);
    }

    private byte[] get(Request request) {
        return eTagGenerator.generate(SuccessGenerator.Code.OK, request.getUri(), request.getUriParams());
    }
}
