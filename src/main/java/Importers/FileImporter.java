package Importers;

import java.util.LinkedHashMap;

public interface FileImporter {
    LinkedHashMap<String,  LinkedHashMap<String, String>> importAsHash(String filePath);
}
