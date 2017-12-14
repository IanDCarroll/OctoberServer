package Filers;

public interface Filer {
    byte[] checkout(String file);
    void rewrite(String file, byte[] newContents);
    void delete(String file);
    String lengthOf(String file);
}
