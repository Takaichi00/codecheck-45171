package jp.co.softbank.aal.application;

import static com.ninja_squad.dbsetup.Operations.*;
import static jp.co.softbank.aal.common.TestUtils.*;
import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import com.ninja_squad.dbsetup.DbSetup;
import com.ninja_squad.dbsetup.destination.DriverManagerDestination;
import com.ninja_squad.dbsetup.operation.Operation;
import java.net.URI;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.List;

import jp.co.softbank.aal.application.payload.CreateRecipeRequestPayload;
import jp.co.softbank.aal.application.payload.CreateRecipeResponsePayload;
import jp.co.softbank.aal.application.payload.ErrorResponse;
import jp.co.softbank.aal.application.payload.GetRecipeResponsePayload;
import jp.co.softbank.aal.application.payload.GetRecipesResponsePayload;
import jp.co.softbank.aal.application.payload.RecipePayload;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;

@Ignore
public class RecipesRestControllerTestIT {
    
    private static final Operation DROP_TABLE = sql(readSql("V101__drop_table.sql"));
    private static final Operation CREATE_TABLE = sql(readSql("V102__create_table.sql"));
    private static final Operation INSERT_RECIPES
        = insertInto("recipes")
            .columns("title",
                     "making_time",
                     "serves",
                     "ingredients",
                     "cost",
                     "created_at",
                     "updated_at")
            .values("チキンカレー",
                    "45分",
                    "4人",
                    "玉ねぎ,肉,スパイス",
                    1000,
                    Timestamp.valueOf("2016-01-10 12:10:12"),
                    Timestamp.valueOf("2016-01-10 12:10:12"))
            .values("オムライス",
                    "30分",
                    "2人",
                    "玉ねぎ,卵,スパイス,醤油",
                    700,
                    Timestamp.valueOf("2016-01-11 13:10:12"),
                    Timestamp.valueOf("2016-01-11 13:10:12"))
            .build();
    
    private TestRestTemplate client = new TestRestTemplate();
    
    @Before
    public void setUp() throws Exception {
        Operation operation = sequenceOf(DROP_TABLE, CREATE_TABLE, INSERT_RECIPES);
        DbSetup dbSetup
            = new DbSetup(new DriverManagerDestination("jdbc:postgresql://localhost:5432/aaldb",
                                                       "recipe",
                                                       "password"),
                          operation);
        dbSetup.launch();
    }
    
    @After
    public void tearDown() throws Exception {
    }
    
    @Test
    public void test_レシピの作成が正常に処理できる場合() throws Exception {
        RequestEntity<CreateRecipeRequestPayload> request = RequestEntity.post(new URI("http://localhost:8080/recipes"))
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .body(new CreateRecipeRequestPayload("トマトスープ",
                                                     "15分",
                                                     "5人",
                                                     "玉ねぎ, トマト, スパイス, 水",
                                                     "450"));
        ResponseEntity<CreateRecipeResponsePayload> actual
            = client.exchange(request, CreateRecipeResponsePayload.class);
        
        CreateRecipeResponsePayload expected
            = new CreateRecipeResponsePayload("Recipe successfully created!",
                                              new RecipePayload(null,
                                                               "トマトスープ",
                                                               "15分",
                                                               "5人",
                                                               "玉ねぎ, トマト, スパイス, 水",
                                                               "450"));
        
        assertThat(actual.getBody(), is(expected));
        assertThat(actual.getStatusCode(), is(HttpStatus.OK));
        assertThat(actual.getHeaders().getContentType(), is(MediaType.APPLICATION_JSON_UTF8));
    }
    
    @Test
    public void test_全レシピ一覧を正常に返せる場合() throws Exception {
        RequestEntity request = RequestEntity.get(new URI("http://localhost:8080/recipes")).build();
        ResponseEntity<GetRecipesResponsePayload> actual
            = client.exchange(request, GetRecipesResponsePayload.class);
        
        GetRecipesResponsePayload expected = new GetRecipesResponsePayload();
        List<RecipePayload> recipes = Arrays.asList(
            new RecipePayload(Integer.valueOf(1), "チキンカレー", "45分", "4人", "玉ねぎ,肉,スパイス", "1000"),
            new RecipePayload(Integer.valueOf(2), "オムライス", "30分", "2人", "玉ねぎ,卵,スパイス,醤油", "700")
        );
        expected.setRecipes(recipes);
        
        assertThat(actual.getBody(), is(expected));
        assertThat(actual.getStatusCode(), is(HttpStatus.OK));
        assertThat(actual.getHeaders().getContentType(), is(MediaType.APPLICATION_JSON_UTF8));
    }
    
    @Test
    public void test_レシピを一つ正常に返せる場合() throws Exception {
        RequestEntity request = RequestEntity.get(new URI("http://localhost:8080/recipes/1")).build();
        ResponseEntity<GetRecipeResponsePayload> actual
            = client.exchange(request, GetRecipeResponsePayload.class);
        
        GetRecipeResponsePayload expected
            = new GetRecipeResponsePayload("Recipe details by id",
                                           new RecipePayload(null,
                                                             "チキンカレー",
                                                             "45分",
                                                             "4人",
                                                             "玉ねぎ,肉,スパイス",
                                                             "1000"));
        assertThat(actual.getBody(), is(expected));
        assertThat(actual.getStatusCode(), is(HttpStatus.OK));
        assertThat(actual.getHeaders().getContentType(), is(MediaType.APPLICATION_JSON_UTF8));
    }
    
    @Test
    public void test_IDで指定されたレシピが参照できない場合() throws Exception {
        RequestEntity request = RequestEntity.get(new URI("http://localhost:8080/recipes/3")).build();
        ResponseEntity<ErrorResponse> actual
            = client.exchange(request, ErrorResponse.class);
        
        ErrorResponse expected = new ErrorResponse("No Recipe found", null);
        
        assertThat(actual.getBody(), is(expected));
        assertThat(actual.getStatusCode(), is(HttpStatus.NOT_FOUND));
        assertThat(actual.getHeaders().getContentType(), is(MediaType.APPLICATION_JSON_UTF8));
    }
}
