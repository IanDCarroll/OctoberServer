package FunctionalCore.Controller;

import Filers.FileClerk;

public class HeaderGenerator {

    public static void setBasics(Response response) {
        String length = response.bodyLength();
        String[] basicHeaders = { "Content-Length", length, "Content-Type", "text/plain" };
        response.setHeaders(basicHeaders);
    }

    public static void setContentRange(Response response, int start, int end, String length) {
        String range = "bytes " + String.valueOf(start) + "-" + String.valueOf(end) + "/" + length;
        setContentRangeValue(response, range);
    }

    public static void setContentRange(Response response, String length) {
        String range = "bytes */" + length;
        setContentRangeValue(response, range);
    }

    private static void setContentRangeValue(Response response, String range) {
        String[] contentRangeHeader = { "Content-Range", range};
        response.setHeaders(contentRangeHeader);
    }

    public static void setAllow(Response response, String permittedMethods) {
        String commaSeparatedPermissions = permittedMethods.replace(" ", ",");
        String[] allowHeader = { "Allow", commaSeparatedPermissions };
        response.setHeaders(allowHeader);
    }

    public static void setLocation(Response response, String redirectToThisUri) {
        String[] locationHeader = { "Location", redirectToThisUri };
        response.setHeaders(locationHeader);
    }
}
