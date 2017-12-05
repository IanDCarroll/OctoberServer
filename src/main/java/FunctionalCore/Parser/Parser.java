package FunctionalCore.Parser;

import FunctionalCore.Request;

import java.util.Arrays;

public class Parser {
    private Request request;
    private final byte[] crlf = "\r\n\r\n".getBytes();
    private final String newLine = "\n";
    private final String space = " ";
    private final String questionMark = "\\?";
    private final String ampersand = "&";
    private final int onTheFirstEncounterOnly = 2;

    public Request parse(byte[] source) {
        byte[][] splitSource = ByteSplitter.splitByPattern(source, crlf);
        String[] splitHead = new String(splitSource[0]).split(newLine, onTheFirstEncounterOnly);
        request = new Request();
        assignBody(splitSource[1]);
        assignStartLine(splitHead[0]);
        if(splitHead.length == 2) { assignAllHeaders(splitHead[1]); }
        return request;
    }

    private void assignBody(byte[] body) { request.setBody(body); }

    private void assignStartLine(String startLine) {
        String[] splitStartLine = startLine.split(space);
        if (splitStartLine.length == 3) {
            request.setMethod(splitStartLine[0]);
            assignUri(splitStartLine[1]);
            request.setHttpV(splitStartLine[2]);
        }
    }

    private void assignUri(String uri) {
        String[] splitUri = uri.split(questionMark, onTheFirstEncounterOnly);
        request.setUri(splitUri[0]);
        if(splitUri.length == 2) { assignAllParams(splitUri[1]);}
    }

    private void assignAllParams(String params) {
        String[] splitParams = params.split(ampersand);
        for(String param : splitParams) {
            request.addUriParam(param);
        }
    }

    private void assignAllHeaders(String headers) {
        String[] splitHeaders = headers.split(newLine);
        for(String header : splitHeaders) {
            request.addHeader(header);
        }
    }
}
