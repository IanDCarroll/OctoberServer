package Filers;

public interface Fileable {
    byte[] checkout(String file);
    void rewrite(String file, byte[] newContents);
    void delete(String file);
}
