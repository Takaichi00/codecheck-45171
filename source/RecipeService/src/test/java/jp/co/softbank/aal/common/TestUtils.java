package jp.co.softbank.aal.common;

import java.io.FileInputStream;
import java.io.IOException;

import org.apache.commons.io.IOUtils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

public final class TestUtils {
    
    public static String readSql(String path) {
        String result = null;
        
        try (FileInputStream input = new FileInputStream("src/main/resources/db/migration/" + path)) {
            result = IOUtils.toString(input, "UTF-8");
            
        } catch (IOException e) {
            //
        }
        
        return result;
    }
    
    public static String readMessageFromFile(String path) throws IOException {
        String result = null;
        
        try (FileInputStream input = new FileInputStream("src/test/resources/" + path)) {
            result = IOUtils.toString(input, "UTF-8");
        }
        
        return result;
    }
    
    
    public static String marshall(Object obj) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
        return mapper.writeValueAsString(obj);
    }
    
    private TestUtils() {
        
    }
    
}
