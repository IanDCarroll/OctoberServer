package FunctionalCore.Controller.SubControllers;

import Filers.FileClerk;
import FunctionalCore.Controller.ResponseGeneration.SuccessGenerator;
import FunctionalCore.Request;

import java.util.LinkedHashMap;

public class MethodController implements SubController {
    private FileClerk fileClerk;
    private SuccessGenerator successGenerator;

    public MethodController(FileClerk fileClerk, SuccessGenerator successGenerator) {
        this.fileClerk = fileClerk;
        this.successGenerator = successGenerator;
    }

    public boolean relevant(Request request, LinkedHashMap<String, LinkedHashMap<String, String>> routes) {
        return true;
    }

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

    private byte[] head(Request request) { return successGenerator.generateHead(SuccessGenerator.Code.OK, request.getUri()); }

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
        String eTag = generateETag(request);
        fileClerk.append(request.getUri(), request.getBody());
        return successGenerator.generate(SuccessGenerator.Code.NO_CONTENT, request.getUri(), eTag);
    }

    private String generateETag(Request request) {
        String eTag = "badE7a9";
        String prefix = "If-Match: ";
        String header = request.getHeader(prefix);
        if (!header.isEmpty()) { eTag = header.replace(prefix, ""); }
        return eTag;
    }

    private byte[] get(Request request) {
        return successGenerator.generate(SuccessGenerator.Code.OK, request.getUri(), request.getUriParams());
    }
}
