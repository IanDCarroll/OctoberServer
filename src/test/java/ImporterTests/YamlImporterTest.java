package ImporterTests;

import Importers.YamlImporter;
import Importers.FileImporter;
import org.junit.jupiter.api.Test;

import java.util.LinkedHashMap;

import static org.junit.jupiter.api.Assertions.*;

class YamlImporterTest {
    private FileImporter subject = new YamlImporter();
    private LinkedHashMap expected = new LinkedHashMap();
    {
        expected.put("/", "GET OPTIONS");
        expected.put("/file1", "GET PUT POST HEAD DELETE OPTIONS");
        expected.put("/coffee", "GET HEAD");
    }

    @Test
    void importAsHashImportsASimpleYMLFileAsAHash() {
        //Given
        String ymlPath = "src/test/java/Mocks/mock_routes.yml";
        //When
        LinkedHashMap actual = subject.importAsHash(ymlPath);
        //Then
        assertEquals(expected, actual);
    }

}