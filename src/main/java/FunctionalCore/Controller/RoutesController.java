package FunctionalCore.Controller;

import FunctionalCore.Request;

import java.util.LinkedHashMap;

public class RoutesController implements SubController {
    public boolean relevant(Request request, LinkedHashMap<String, LinkedHashMap<String, String>> routes) { return false; }

    public byte[] generate(Request request, LinkedHashMap<String, LinkedHashMap<String, String>> routes) { return new byte[0]; }
}
