package FunctionalCore;

public class ResponseGenerator {
    public Response response;

    public ResponseGenerator() {
        this.response = new Response();
    }

    public byte[] generate200() {
        final String[] code200 = { "200", "OK" };
        return assembleResponse(code200).getBytes();
    }

    public byte[] generate404() {
        final String[] code404 = { "404", "Not Found" };
        return assembleResponse(code404).getBytes();
    }

    private String assembleResponse(String[] code) {
        final String crlf = "\r\n\r\n";
        return assembleStartLine(code) + crlf;
    }

    public String assembleStartLine(String[] code) {
        final String httpV = "HTTP/1.1";
        final String sp = " ";
        return httpV + sp + code[0] + sp + code[1];
    }
}
