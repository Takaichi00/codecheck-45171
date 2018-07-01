package jp.co.softbank.aal.application.payload;

import static jp.co.softbank.aal.common.TestUtils.*;
import static org.hamcrest.CoreMatchers.*; 
import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class GetRecipesResponsePayloadTest {

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void test_正常にJSONに変換できる場合() throws Exception {
        String expected = readMessageFromFile("get_recipes/success.json");
        
        GetRecipesResponsePayload payload = new GetRecipesResponsePayload();
        List<RecipePayload> recipes = Arrays.asList(
            new RecipePayload(Integer.valueOf(1), "チキンカレー", "45分", "4人", "玉ねぎ,肉,スパイス", "1000"),
            new RecipePayload(Integer.valueOf(2), "オムライス", "30分", "2人", "玉ねぎ,卵,スパイス,醤油", "700"),
            new RecipePayload(Integer.valueOf(3), "トマトスープ", "15分", "5人", "玉ねぎ, トマト, スパイス, 水", "450")
        );
        payload.setRecipes(recipes);
        
        String actual = marshall(payload);
        System.out.println(actual);
        assertThat(actual, is(expected));
    }

}
