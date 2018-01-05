package FunctionalCore.Controller;

import FunctionalCore.Request;
import java.util.LinkedHashMap;

public interface SubController {
    boolean relevant(Request request, LinkedHashMap<String, LinkedHashMap<String, String>> routes);

    byte[] generate(Request request, LinkedHashMap<String, LinkedHashMap<String, String>> routes);
}
