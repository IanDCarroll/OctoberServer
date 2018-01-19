package FunctionalCore.Controller.SubControllers;

import FunctionalCore.Controller.SubControllers.MethodSubControllers.*;
import FunctionalCore.Request;

import java.util.LinkedHashMap;

public class MethodController implements SubController {
    private static MethodSubController getController;
    private static MethodSubController headController;
    private static MethodSubController postController;
    private static MethodSubController putController;
    private static MethodSubController deleteController;
    private static MethodSubController patchController;
    public enum Method {
        GET("GET", getController),
        HEAD("HEAD", headController),
        POST("POST", postController),
        PUT("PUT", putController),
        DELETE("DELETE", deleteController),
        PATCH("PATCH", patchController);
        public String name;
        public MethodSubController methodClass;

        Method(String name, MethodSubController methodClass) {
            this.name = name;
            this.methodClass = methodClass;
        }
    }

    public MethodController(GetController getController,
                            HeadController headController,
                            PostController postController,
                            PutController putController,
                            DeleteController deleteController,
                            PatchController patchController) {
        this.getController = getController;
        this.headController = headController;
        this.postController = postController;
        this.putController = putController;
        this.deleteController = deleteController;
        this.patchController = patchController;
    }

    public boolean relevant(Request request, LinkedHashMap<String, LinkedHashMap<String, String>> routes) {
        return theHTTPVersionIsSet(request);
    }

    private boolean theHTTPVersionIsSet(Request request) { return !request.getHttpV().equals("Not Set"); }

    public byte[] generate(Request request, LinkedHashMap<String, LinkedHashMap<String, String>> routes) {
        for (Method method : Method.values()) {
            if (method.name.equals(request.getMethod())) { return method.methodClass.fulfill(request); }
        }
        return Method.GET.methodClass.fulfill(request);
    }
}
