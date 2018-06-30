package jp.co.softbank.aal.common;

import java.io.FileInputStream;
import java.io.IOException;

import org.apache.commons.io.IOUtils;

public final class TestUtils {
    
    public static String readMessageFromFile(String path) throws IOException {
        String result = null;
        
        try (FileInputStream input = new FileInputStream("src/test/resources/" + path)) {
            result = IOUtils.toString(input, "UTF-8");
        }
        
        return result;
    }
    
    private TestUtils() {
        
    }
}
