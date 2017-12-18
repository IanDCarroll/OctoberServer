package Importers;

import org.yaml.snakeyaml.Yaml;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.LinkedHashMap;

public class NewYamlImporter implements FileImporter {
    private Yaml yaml = new Yaml();
    private InputStream input = null;

    @Override
    public LinkedHashMap<String, LinkedHashMap<String, String>> importAsHash(String filePath) {
        try {
            input = new FileInputStream(new File(filePath));
        } catch (FileNotFoundException e) { System.out.println(e.getMessage()); }
        return yaml.load(input);
    }
}
