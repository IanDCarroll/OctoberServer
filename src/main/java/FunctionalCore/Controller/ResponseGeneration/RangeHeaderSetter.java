package FunctionalCore.Controller.ResponseGeneration;

import Filers.FileClerk;

public class RangeHeaderSetter {
    FileClerk fileClerk;

    public RangeHeaderSetter(FileClerk fileClerk) {
        this.fileClerk = fileClerk;
    }

    public Response setRange(Response response, String uri) {
        String rangeValue = "bytes */" + fileLengthOf(uri);
        return setHeaderTo(response, rangeValue);
    }

    public Response setRange(Response response, String uri, int[] rangeTuple) {
        String rangeValue = "bytes " + String.valueOf(rangeTuple[0]) +
                "-" + String.valueOf(rangeTuple[1]) +
                "/" + fileLengthOf(uri);
        return setHeaderTo(response, rangeValue);
    }

    private String fileLengthOf(String uri) {
        return String.valueOf(fileClerk.checkout(uri).length);
    }

    private Response setHeaderTo(Response response, String rangeValue) {
        response.setHeader(Response.Header.CONTENT_RANGE, rangeValue);
        return response;
    }
}
