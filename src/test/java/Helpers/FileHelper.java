package Helpers;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
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

    public static void make(String name, byte[] content) {
        File file = new File(name);
        try {
            FileOutputStream out = new FileOutputStream(file);
            try {
                out.write(content);
                out.close();
            } catch (IOException e) { System.out.println("IOException in Filer tests"); }
        } catch (FileNotFoundException e) { System.out.println("file not found in Filer tests"); }
    }

    public static void delete(String name) {
        File file = new File(name);
        file.delete();
    }
}
