package importers;

import org.yaml.snakeyaml.Yaml;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.LinkedHashMap;

public class YamlImporter implements FileImporter {

    @Override
    public LinkedHashMap importAsHash(String filePath) {

        Yaml yaml = new Yaml();

        InputStream input = null;
        try {
            input = new FileInputStream(new File(filePath));
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        }
        return yaml.load(input);
    }
}
