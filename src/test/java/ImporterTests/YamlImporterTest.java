package ImporterTests;

import Importers.FileImporter;
import Importers.YamlImporter;
import org.junit.jupiter.api.Test;

import java.util.LinkedHashMap;

import static org.junit.jupiter.api.Assertions.*;

class YamlImporterTest {
    private FileImporter subject = new YamlImporter();
    private LinkedHashMap expected = new LinkedHashMap();
    private LinkedHashMap root = new LinkedHashMap();
    private LinkedHashMap file1 = new LinkedHashMap();
    private LinkedHashMap coffee = new LinkedHashMap();
    {
        root.put("allowed-methods", "GET,OPTIONS");
        file1.put("allowed-methods", "GET,PUT,POST,HEAD,DELETE,OPTIONS");
        file1.put("authorization", "admin:12345");
        coffee.put("allowed-methods", "GET,HEAD");
        coffee.put("redirect-uri", "/tea-earl-grey-hot");
        expected.put("/", root);
        expected.put("/file1", file1);
        expected.put("/coffee", coffee);
    }

    @Test
    void importAsHashImportsAHashOfHashes() {
        //Given
        String ymlPath = "src/test/java/Mocks/mock_routes.yml";
        //When
        LinkedHashMap actual = subject.importAsHash(ymlPath);
        //Then
        assertEquals(expected, actual);
    }

    @Test
    void importAsHashImportsAHashThatCanBeAccessedForLeafValues() {
        //Given
        String ymlPath = "src/test/java/Mocks/mock_routes.yml";
        //When
        LinkedHashMap<String, LinkedHashMap<String, String>> actual = subject.importAsHash(ymlPath);
        LinkedHashMap<String, String> actualRoot = actual.get("/");
        String actualRootAllowedMethods = actualRoot.get("allowed-methods");
        //Then
        assertEquals(root.get("allowed-methods"), actualRootAllowedMethods);
    }

    @Test
    void importAsHashImportsAHashThatCanBeAccessedForRedirectValues() {
        //Given
        String ymlPath = "src/test/java/Mocks/mock_routes.yml";
        //When
        LinkedHashMap<String, LinkedHashMap<String, String>> actual = subject.importAsHash(ymlPath);
        LinkedHashMap<String, String> actualRoot = actual.get("/");
        String actualRootRedirectUri = actualRoot.get("redirect-uri");
        //Then
        assertEquals(root.get("redirect-uri"), actualRootRedirectUri);
    }

}