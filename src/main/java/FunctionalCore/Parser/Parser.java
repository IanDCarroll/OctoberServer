package FunctionalCore.Parser;

import FunctionalCore.Request;

import java.util.Arrays;

public class Parser {
    private Request request;
    private final byte[] byCRLF = "\r\n\r\n".getBytes();
    private final String byNewLine = "\n";
    private final String bySpace = " ";
    private final String byQuestionMark = "\\?";
    private final String byAmpersand = "&";
    private final int onTheFirstEncounterOnly = 2;

    public Request parse(byte[] source) {
        request = new Request();
        setRequestValuesInSource(source);
        return request;
    }

    private void setRequestValuesInSource(byte[] source) {
        byte[][] splitHeadFromBody = ByteSplitter.splitByPattern(source, byCRLF);
        setRequestValuesInHead(splitHeadFromBody[0]);
        request.setBody(splitHeadFromBody[1]);
    }

    private void setRequestValuesInHead(byte[] head) {
        String[] splitHead = new String(head).split(byNewLine);
        setRequestValuesInStartLine(splitHead[0]);
        if(splitHead.length > 1) { request.setHeaders(minusTheFirst(splitHead)); }
    }

    private String[] minusTheFirst(String[] array) {
        return Arrays.copyOfRange(array, 1, array.length);
    }

    private void setRequestValuesInStartLine(String startLine) {
        String[] splitStartLine = startLine.split(bySpace);
        if (splitStartLine.length == 3) { setRequestValuesInStartLine(splitStartLine); }
    }

    private void setRequestValuesInStartLine(String[] splitStartLine) {
        request.setMethod(splitStartLine[0]);
        setRequestValuesInUri(splitStartLine[1]);
        request.setHttpV(splitStartLine[2]);
    }

    private void setRequestValuesInUri(String uri) {
        String[] splitUriFromParams = uri.split(byQuestionMark, onTheFirstEncounterOnly);
        request.setUri(splitUriFromParams[0]);
        if(splitUriFromParams.length == 2) { setRequestUriParams(splitUriFromParams[1]); }
    }

    private void setRequestUriParams(String rawParams) {
        String[] uriParams = ParamDecoder.decode(rawParams.split(byAmpersand));
        request.setUriParams(uriParams);
    }
}
