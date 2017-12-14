package Filers;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class FileUpdater implements Appender {
    String publicDir;

    public FileUpdater(String publicDir) {
        this.publicDir = publicDir;
    }

    public void append(String uri, byte[] payload) {

        boolean append = true;
        File file = new File(fsName(uri));
        try {
            toBuildOutputStream(file, append, payload);
        } catch (FileNotFoundException e) { System.out.println(e.getMessage()); }
    }

    void toBuildOutputStream(File file, boolean append, byte[] payload) throws FileNotFoundException {
        FileOutputStream outToFile = new FileOutputStream(file, append);
        try {
            toWriteThis(payload, outToFile);
        } catch (IOException e) { System.out.println(e.getMessage()); }
    }

    private void toWriteThis(byte[] payload, FileOutputStream outToFile) throws IOException {
        outToFile.write(payload);
        outToFile.close();
    }

    String fsName(String uri) {
        return publicDir + uri;
    }
}
