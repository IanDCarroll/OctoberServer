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
    {
        root.put("allowed-methods", "GET,OPTIONS");
        expected.put("/", root);
    }

    @Test
    void importAsHashImportsAHashOfHashes() {
        //Given
        String ymlPath = "src/test/java/Mocks/mock_routes.yml";
        //When
        LinkedHashMap actual = subject.importAsHash(ymlPath);
        //Then
        assertEquals(expected.get("/"), actual.get("/"));
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