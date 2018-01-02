package FunctionalCore.Controller.ResponseGeneration.ResponseSetter.HeaderSetters;

import FunctionalCore.Controller.ResponseGeneration.Response;

public class ContentTypeHeaderSetter {
    public enum Type {
        HTML_TEXT(".html", "text/html"),
        PLAIN_TEXT(".txt", "text/plain"),
        JPEG_IMAGE(".jpeg", "image/jpeg"),
        PNG_IMAGE(".png", "image/png"),
        GIF_IMAGE(".gif", "image/gif");
        public String key;
        public String value;
        Type(String key, String value) {
            this.key = key;
            this.value = value;
        }
    }

    public Response setContentType(Response response, String uri) {
        response.setHeader(Response.Header.CONTENT_TYPE, getAppropriateType(uri));
        return response;
    }

    private String getAppropriateType(String uri) {
        for (Type type : Type.values()) {
            if (uri.endsWith(type.key)) { return type.value; }
        }
        return Type.HTML_TEXT.value;
    }
}
