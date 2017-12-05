package FunctionalCore;

import java.awt.*;
import java.util.Arrays;

public class Parser {
    private Request request;
    private final byte[] crlf = "\r\n\r\n".getBytes();

    public Request parse(byte[] source) {
        request = new Request();
        byte[][] decapitated = splitByCRLF(source);
        String[] head = splitByNewLine(new String(decapitated[0]));
        String[] startLine = splitBySpace(head[0]);
        request.setBody(decapitated[1]);
        addAllHeaders(head);
        request.setMethod(startLine[0]);
        request.setUri(startLine[1]);
        request.setHttpV(startLine[2]);
        return request;
    }

    private void addAllHeaders(String[] head) {
        for(int i = 1; i < head.length; i++) {
            request.addHeader(head[i]);
        }
    }

    private byte[][] splitByCRLF(byte[] source) {
        for(int i = 0; i < source.length; i++) {
            byte[] sample = Arrays.copyOfRange(source, i, i+crlf.length);
            if (itsCRLF(sample)) { return split(source, i, crlf.length); }
        }
        return new byte[][] { source, new byte[0] };
    }

    private boolean itsCRLF(byte[] sample) {
        return Arrays.equals(sample, crlf);
    }

    private String[] splitByNewLine(String source) {
        String byNewline = "\n";
        return split(source, byNewline);
    }

    private String[] splitBySpace(String source) {
        String bySpace = " ";
        return split(source, bySpace);
    }

    private String[] split(String source, String atThisTarget) {
        return source.split(atThisTarget);
    }

    private byte[][] split(byte[] source, int start, int thickness) {
        byte[] head = Arrays.copyOfRange(source, 0, start);
        byte[] body = Arrays.copyOfRange(source, start+thickness, source.length);
        return new byte[][] { head, body };
    }
}
