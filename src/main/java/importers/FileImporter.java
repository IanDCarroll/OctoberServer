package importers;

import java.util.LinkedHashMap;

public interface FileImporter {
    LinkedHashMap importAsHash(String filePath);
}
