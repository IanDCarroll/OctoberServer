package Filers;

import java.io.File;

public class DirectoryFileClerk extends FileClerk {

    public DirectoryFileClerk(String publicDir) { super(publicDir); }

    @Override
    public byte[] checkout(String uri) {
        File resource = new File(fsName(uri));
        return itsADirectory(resource)
                ? htmlFormat(directoryContents(resource))
                : super.checkout(uri);
    }

    private boolean itsADirectory(File resource) {
        return resource.isDirectory();
    }

    private byte[] htmlFormat(String[] directoryContents) {
        StringBuilder document = new StringBuilder();
        document.append(htmlOpening());
        document.append(htmlContents(directoryContents));
        document.append(htmlClosing());
        return document.toString().getBytes();
    }

    private String htmlOpening() {
        return "<!DOCTYPE html>\n" +
                "<html>\n" +
                "<head>\n" +
                "<title></title>\n" +
                "</head>\n" +
                "<body>";
    }

    private String htmlContents(String[] directoryContents) {
        StringBuilder contents = new StringBuilder();
        for (String resource : directoryContents) {
            contents.append(anchorFormat(resource));
        }
        return contents.toString();
    }

    private String htmlClosing() {
        return "\n</body>\n</html>";
    }

    private String anchorFormat(String resource) {
        String anchor = "\n<a href=\"/" + resource + "\">" + resource + "</a>";
        return anchor;
    }

    private String[] directoryContents(File directory) {
        return directory.list();
    }
}
