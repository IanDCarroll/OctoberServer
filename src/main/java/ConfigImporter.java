import org.yaml.snakeyaml.Yaml;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.LinkedHashMap;

public class ConfigImporter implements Importable {
    private static final String CONFIG_FILE = "src/main/java/routes_config.yml";

    public LinkedHashMap importConfig() {

        Yaml yaml = new Yaml();

        InputStream input = null;
        try {
            input = new FileInputStream(new File(CONFIG_FILE));
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        }
        LinkedHashMap routes = yaml.load(input);
        return routes;

    }
}
