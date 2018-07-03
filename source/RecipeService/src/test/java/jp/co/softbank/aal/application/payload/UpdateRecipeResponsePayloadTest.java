package jp.co.softbank.aal.application.payload;

import static jp.co.softbank.aal.common.TestUtils.*;
import static org.hamcrest.CoreMatchers.*; 
import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class UpdateRecipeResponsePayloadTest {

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void test_正常にJSONに変換できる場合() throws Exception {
        String expected = readMessageFromFile("update_recipe/success.json");
        
        UpdateRecipeResponsePayload payload
            = new UpdateRecipeResponsePayload("Recipe successfully updated!",
                                              new RecipePayload(null,
                                                                "トマトスープレシピ",
                                                                "15分",
                                                                "5人",
                                                                "玉ねぎ, トマト, スパイス, 水",
                                                                "450"));
        
        String actual = marshall(payload);
        System.out.println(actual);
        assertThat(actual, is(expected));
    }

}
