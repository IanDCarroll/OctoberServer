package Filers;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class FileClerk extends FileUpdater implements Filer {

    public byte[] checkout(String name) {
        byte[] bytes = new byte[0];
        try {
            bytes = Files.readAllBytes(Paths.get(name));
        } catch (IOException e) {}
        return bytes;
    }

    public void rewrite(String name, byte[] newContents) {
        File file = new File(name);
        boolean replaceWith = false;
        try {
            toBuildOutputStream(file, replaceWith, newContents);
        } catch (FileNotFoundException e) { System.out.println(e.getMessage());}
    }

    public void delete(String name) {
        new File(name).delete();
    }
}
