package jp.co.softbank.aal.application.payload;

import static org.hamcrest.CoreMatchers.*; 
import static org.junit.Assert.*;

import java.util.Arrays;

import static jp.co.softbank.aal.common.TestUtils.readMessageFromFile;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

public class GetRecipeResponsePayloadTest {

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void test_正常にJSONに変換できる場合() throws Exception {
        String expected = readMessageFromFile("get_recipe/success.json");
        
        GetRecipeResponsePayload payload = new GetRecipeResponsePayload();
        payload.setMessage("Recipe details by id");
        payload.setRecipe(Arrays.asList(new RecipePayload("チキンカレー", "45分", "4人", "玉ねぎ,肉,スパイス", "1000")));
        
        ObjectMapper mapper = new ObjectMapper();
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
        
        String actual = mapper.writeValueAsString(payload);
        System.out.println(actual);
        
        assertThat(actual, is(expected));
    }

}
