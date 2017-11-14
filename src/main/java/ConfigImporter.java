import org.yaml.snakeyaml.Yaml;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.LinkedHashMap;

public class ConfigImporter {
    private final String configFile;

    public ConfigImporter(String configFile) {
        this.configFile = configFile;
    }

    public LinkedHashMap importConfig() {

        Yaml yaml = new Yaml();

        InputStream input = null;
        try {
            input = new FileInputStream(new File(configFile));
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        }
        return yaml.load(input);
    }
}
