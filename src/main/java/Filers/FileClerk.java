package Filers;

public class FileClerk extends FileUpdater implements Fileable {
    public byte[] checkout(String name) {
        return new byte[0];
    }

    public void rewrite(String name, byte[] newContents) {}

    public void delete(String name) {}
}
