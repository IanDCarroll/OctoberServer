package FunctionalCore.Controller;

public class HeaderGenerator {

    public static void setBasics(Response response) {
        String length = response.bodyLength();
        response.setHeaders(new String[]{ "Content-Length", length, "Content-Type", "text/plain" });
    }

    public static void setContentRange(Response response, int start, int end) {
        String range = "bytes " + String.valueOf(start) + "-" + String.valueOf(end) + "/" + response.bodyLength();
        setContentRange(response, range);
    }

    public static void setContentRange(Response response) {
        String range = "bytes */" + response.bodyLength();
        setContentRange(response, range);
    }

    private static void setContentRange(Response response, String range) {
        response.setHeaders(new String[]{"Content-Range", range});
    }

    public static void setAllow(Response response, String permittedMethods) {
        String commaSeparatedPermissions = permittedMethods.replace(" ", ",");
        String[] allowHeader = { "Allow", commaSeparatedPermissions };
        response.setHeaders(allowHeader);
    }
}
