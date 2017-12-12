package Filers;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class FileUpdater implements Appender {
    public void append(String destination, byte[] payload) {
        boolean append = true;
        File file = new File(destination);
        try {
            toBuildOutputStream(file, append, payload);
        } catch (FileNotFoundException e) { System.out.println( e.getMessage()); }
    }

    private void toBuildOutputStream(File file, boolean append, byte[] payload) throws FileNotFoundException {
        FileOutputStream outToFile = new FileOutputStream(file, append);
        try {
            toWriteThis(payload, outToFile);
        } catch (IOException e) { System.out.println( e.getMessage()); }
    }

    private void toWriteThis(byte[] payload, FileOutputStream outToFile) throws IOException {
        outToFile.write(payload);
        outToFile.close();
    }
}
