package FunctionalCore.Parser;

import java.util.Arrays;

public class Lumberjack {
    private static final byte[] crlf = "\r\n\r\n".getBytes();

    public static String[] splitByNewLine(String source) {
        String byNewline = "\n";
        return split(source, byNewline);
    }

    public static String[] splitBySpace(String source) {
        String bySpace = " ";
        return split(source, bySpace);
    }

    public static byte[][] splitByCRLF(byte[] source) {
        for(int i = 0; i < source.length; i++) {
            byte[] sample = Arrays.copyOfRange(source, i, i+crlf.length);
            if (itsCRLF(sample)) { return split(source, i, crlf.length); }
        }
        return new byte[][] { source, new byte[0] };
    }

    private static boolean itsCRLF(byte[] sample) {
        return Arrays.equals(sample, crlf);
    }

    private static String[] split(String source, String atThisTarget) {
        return source.split(atThisTarget);
    }

    private static byte[][] split(byte[] source, int start, int thickness) {
        byte[] head = Arrays.copyOfRange(source, 0, start);
        byte[] body = Arrays.copyOfRange(source, start+thickness, source.length);
        return new byte[][] { head, body };
    }
}
