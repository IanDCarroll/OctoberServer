package Helpers;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class FileHelper {
    public static String read(String name) {
        byte[] bytes = new byte[0];
        try {
            bytes = Files.readAllBytes(Paths.get(name));
        } catch (IOException e) {}
        return new String(bytes);
    }
}
