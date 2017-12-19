package Mocks;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;

public class MockPrintStreamDealer {
    private static byte[] bytes = new byte[100];
    private static int count = 0;

    public static String getEntry() {
        return new String(bytes);
    }

    private static OutputStream outputStream = new OutputStream() {
        public void write(int b) throws IOException {
            bytes[count] = (byte)b;
            count += 1;
        }
    };
    private static boolean autoFlush = true;
    public static PrintStream printStream = new PrintStream(outputStream, autoFlush);
}
