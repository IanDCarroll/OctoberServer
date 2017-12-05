package FunctionalCore.Parser;

import FunctionalCore.Request;

import java.util.Arrays;

public class Parser {
    private Request request;
    private final byte[] crlf = "\r\n\r\n".getBytes();

    public Request parse(byte[] source) {
        request = new Request();
        byte[] rawHead = parseOutBody(source);
        String[] headers = parseOutStartLine(rawHead);
        addAllHeaders(headers);
        return request;
    }

    private byte[] parseOutBody(byte[] raw) {
        byte[][] decapitated = Lumberjack.splitByCRLF(raw);
        request.setBody(decapitated[1]);
        return decapitated[0];
    }

    private String[] parseOutStartLine(byte[] rawHead) {
        String[] head = Lumberjack.splitByNewLine(new String(rawHead));
        parseStartLine(head[0]);
        return Arrays.copyOfRange(head, 1, head.length);
    }

    private void parseStartLine(String startLine) {
        parseStartLine(Lumberjack.splitBySpace(startLine));

    }

    private void parseStartLine(String[] startLine) {
        if(startLine.length == 3) {
            parseStartLine(startLine[0], startLine[1], startLine[2]);
        }
    }

    private void parseStartLine(String method, String rawUri, String httpV) {
        parseMethod(method);
        parseUri(rawUri);
        parseHttpV(httpV);
    }

    private void parseMethod(String method) { request.setMethod(method); }
    private void parseUri(String rawUri) {
        request.setUri(rawUri);
    }
    private void parseHttpV(String httpV) { request.setHttpV(httpV); }

    private void addAllHeaders(String[] headers) {
        for(String header : headers) {
            request.addHeader(header);
        }
    }
}
