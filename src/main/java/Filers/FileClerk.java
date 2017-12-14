package Filers;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class FileClerk extends FileUpdater implements Filer {

    public FileClerk(String publicDir) {
        super(publicDir);
    }

    public byte[] checkout(String uri) {
        byte[] bytes = new byte[0];
        try {
            bytes = Files.readAllBytes(Paths.get(fsName(uri)));
        } catch (IOException e) {}
        return bytes;
    }

    public void rewrite(String uri, byte[] newContents) {
        File file = new File(fsName(uri));
        boolean replaceWith = false;
        try {
            toBuildOutputStream(file, replaceWith, newContents);
        } catch (FileNotFoundException e) { System.out.println(e.getMessage());}
    }

    public void delete(String uri) {
        new File(fsName(uri)).delete();
    }
}
