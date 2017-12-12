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
            FileOutputStream outToFile = new FileOutputStream(file, append);
            try {
                outToFile.write(payload);
                outToFile.close();
            } catch (IOException e) { System.out.println( e.getMessage()); }
        } catch (FileNotFoundException e) { System.out.println( e.getMessage()); }
    }
}
