package FunctionalCore.Controller;

public class Validator {
    private final String emptyString = "";

    public String getHeader(String[] headers, String key) {
        for(String header : headers) {
            if(header.startsWith(key)) return header;
        }
        return emptyString;
    }
}
