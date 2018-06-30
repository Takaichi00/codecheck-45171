package jp.co.softbank.aal.application;

import static com.ninja_squad.dbsetup.Operations.deleteAllFrom;
import static com.ninja_squad.dbsetup.Operations.insertInto;
import static com.ninja_squad.dbsetup.Operations.sequenceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

import java.sql.Timestamp;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import com.ninja_squad.dbsetup.DbSetup;
import com.ninja_squad.dbsetup.destination.DriverManagerDestination;
import com.ninja_squad.dbsetup.operation.Operation;

import jp.co.softbank.aal.application.payload.GetRecipeResponsePayload;
import jp.co.softbank.aal.application.payload.RecipePayload;

public class RecipesRestControllerTestIT {
    
    private static final Operation DELETE_ALL = deleteAllFrom("recipes");
    private static final Operation INSERT_RECIPES
        = insertInto("recipes")
            .columns("id", "title", "making_time", "serves", "ingredients", "cost", "created_at", "updated_at")
            .values(1, "チキンカレー", "45分", "4人", "玉ねぎ,肉,スパイス", 1000, Timestamp.valueOf("2016-01-10 12:10:12"), Timestamp.valueOf("2016-01-10 12:10:12"))
//            .values(2, "オムライス", "30分", "2人", "玉ねぎ,卵,スパイス,醤油", 700, Timestamp.valueOf("2016-01-11 13:10:12"), Timestamp.valueOf("2016-01-11 13:10:12"))
            .build();
    
    RestTemplate restTemplate = new RestTemplate();
    
    @Before
    public void setUp() throws Exception {
        Operation operation = sequenceOf(DELETE_ALL, INSERT_RECIPES);
        DbSetup dbSetup = new DbSetup(new DriverManagerDestination("jdbc:postgresql://localhost:5432/aaldb", "recipe", "password"), operation);
        dbSetup.launch();
    }
    
    @After
    public void tearDown() throws Exception {
    }
    
    @Test
    public void test_レシピを一つ正常に返せる場合() {
        GetRecipeResponsePayload actual = restTemplate.getForObject("http://localhost:8080/recipes/1", GetRecipeResponsePayload.class);
        GetRecipeResponsePayload expected
            = new GetRecipeResponsePayload("Recipe details by id",
                                           new RecipePayload(null,
                                                             "チキンカレー",
                                                             "45分",
                                                             "4人",
                                                             "玉ねぎ,肉,スパイス",
                                                             "1000"));
        assertThat(actual, is(expected));
    }

}
