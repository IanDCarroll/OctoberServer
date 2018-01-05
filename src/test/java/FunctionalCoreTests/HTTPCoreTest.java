package FunctionalCoreTests;

import Filers.FileClerk;
import FunctionalCore.Controller.*;
import FunctionalCore.Controller.ResponseGeneration.*;
import FunctionalCore.Controller.ResponseGeneration.ResponseSetter.BodySetter;
import FunctionalCore.Controller.ResponseGeneration.ResponseSetter.HeaderSetters.*;
import FunctionalCore.Controller.ResponseGeneration.ResponseSetter.StartLineSetter;
import FunctionalCore.HTTPCore;
import FunctionalCore.Parser.Parser;
import Loggers.FileLogger;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.LinkedHashMap;

import static org.junit.jupiter.api.Assertions.*;

class HTTPCoreTest {
    private HTTPCore subject;
    private LinkedHashMap<String, LinkedHashMap<String, String>> mockRoutes;
    private LinkedHashMap<String, String> mockRouteAttributes;
    private String publicDir;
    private FileClerk fileClerk;

    @BeforeEach
    void setup() {
        Parser parser = new Parser();
        mockRoutes = new LinkedHashMap();
        mockRouteAttributes = new LinkedHashMap();
        publicDir = System.getProperty("user.dir") + "/src/test/java/Mocks";
        fileClerk = new FileClerk(publicDir);
        FileLogger fileLogger = new FileLogger(fileClerk);
        StartLineSetter sls = new StartLineSetter();
        BodySetter bs = new BodySetter(fileClerk);
        TeaPotGenerator tg = new TeaPotGenerator(sls, bs);
        TeaPotController t = new TeaPotController(tg);
        ClientErrorGenerator ceg = new ClientErrorGenerator(sls);
        UriController u = new UriController(ceg);
        AuthenticateHeaderSetter authHS = new AuthenticateHeaderSetter();
        AuthGenerator ag = new AuthGenerator(sls, authHS);
        AuthController a = new AuthController(ag);
        AllowHeaderSetter allowHS = new AllowHeaderSetter();
        OptionsGenerator og = new OptionsGenerator(sls, allowHS);
        OptionsController o = new OptionsController(og);
        LocationHeaderSetter lhs = new LocationHeaderSetter();
        RedirectionGenerator rdg = new RedirectionGenerator(sls, lhs);
        RedirectionController d = new RedirectionController(rdg);
        SetCookieHeaderSetter schs = new SetCookieHeaderSetter();
        ETagHeaderSetter eths = new ETagHeaderSetter();
        SuccessGenerator sg = new SuccessGenerator(sls, bs, schs, eths);
        CookieController c = new CookieController(sg);
        RangeHeaderSetter rhs = new RangeHeaderSetter(fileClerk);
        RangeGenerator rg = new RangeGenerator(sls, bs, rhs);
        RangeController r = new RangeController(fileClerk, rg);
        MethodController m = new MethodController(fileClerk, sg);
        Controller controller = new Controller(mockRoutes, fileLogger, t, u, a, o, d, c, r, m, ceg);
        subject = new HTTPCore(parser, controller);
    }

    @Test
    void coreReturnsA200ResponseWhenTheRootIsRequested() {
        //Given
        mockRouteAttributes.put("allowed-methods", "GET");
        mockRoutes.put("/", mockRouteAttributes);
        byte[] request = "GET / HTTP/1.1\r\n\r\n".getBytes();
        //When
        String actual = new String(subject.craftResponseTo(request));
        //Then
        String expected = "HTTP/1.1 200 OK";
        assertTrue(actual.contains(expected));
    }
}