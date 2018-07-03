package jp.co.softbank.aal.application;

import static jp.co.softbank.aal.common.TestUtils.*;
import static org.hamcrest.CoreMatchers.*; 
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.Arrays;
import java.util.List;
import jp.co.softbank.aal.application.payload.CreateRecipeRequestPayload;
import jp.co.softbank.aal.application.payload.CreateRecipeResponsePayload;
import jp.co.softbank.aal.application.payload.DeleteRecipeResponsePayload;
import jp.co.softbank.aal.application.payload.ErrorResponse;
import jp.co.softbank.aal.application.payload.GetRecipeResponsePayload;
import jp.co.softbank.aal.application.payload.GetRecipesResponsePayload;
import jp.co.softbank.aal.application.payload.RecipePayload;
import jp.co.softbank.aal.application.payload.UpdateRecipeRequestPayload;
import jp.co.softbank.aal.application.payload.UpdateRecipeResponsePayload;
import jp.co.softbank.aal.common.SystemException;
import jp.co.softbank.aal.domain.Recipe;
import jp.co.softbank.aal.domain.RecipesManagementService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

@RunWith(SpringRunner.class)
@WebMvcTest(RecipesRestController.class)
public class RecipesRestControllerTest {
    
    @MockBean
    private RecipesManagementService service;
    
    @Autowired
    private MockMvc mockMvc;
    
    @Before
    public void setUp() throws Exception {
    }
    
    @After
    public void tearDown() throws Exception {
    }
    
    @Test
    public void test_指定されたレシピを正常に更新できる場合() throws Exception {
        when(service.updateRecipe(new Recipe(3,
                                             "トマトスープレシピ",
                                             "15分",
                                             "5人",
                                             "玉ねぎ, トマト, スパイス, 水",
                                             450)))
                    .thenReturn(new Recipe(3,
                                           "トマトスープレシピ",
                                           "15分",
                                           "5人",
                                           "玉ねぎ, トマト, スパイス, 水",
                                           450));
        
        UpdateRecipeResponsePayload expected
            = new UpdateRecipeResponsePayload("Recipe successfully updated!",
                                              new RecipePayload(null,
                                                                "トマトスープレシピ",
                                                                "15分",
                                                                "5人",
                                                                "玉ねぎ, トマト, スパイス, 水",
                                                               "450"));
        
        UpdateRecipeRequestPayload request
            = new UpdateRecipeRequestPayload("トマトスープレシピ",
                                             "15分",
                                             "5人",
                                             "玉ねぎ, トマト, スパイス, 水",
                                             "450");
        
        mockMvc.perform(patch("/recipes/3")
                        .content(marshall(request))
                        .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(content().json(marshall(expected)));
    }
    
    @Test
    public void test_レシピの更新でバリデーションエラーが発生する場合() throws Exception {
        UpdateRecipeRequestPayload request = new UpdateRecipeRequestPayload();
        
        ErrorResponse expected = new ErrorResponse("Recipe update failed!",
                                                   "title, making_time, serves, ingredients, cost");
        
        mockMvc.perform(patch("/recipes/3")
                        .content(marshall(request))
                        .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
               .andExpect(status().isBadRequest())
               .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
               .andExpect(content().json(marshall(expected)));
    }
    
    @Test
    public void test_指定されたレシピを正常に削除できる場合() throws Exception {
        when(service.deleteRecipe(1)).thenReturn(1);
        
        DeleteRecipeResponsePayload expected
            = new DeleteRecipeResponsePayload("Recipe successfully removed!");
        
        mockMvc.perform(delete("/recipes/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(content().json(marshall(expected)));
    }
    
    @Test
    public void test_指定されたIDに対応するレシピが存在せず削除できない場合() throws Exception {
        when(service.deleteRecipe(2)).thenReturn(0);
        
        ErrorResponse expected = new ErrorResponse("No Recipe found", null);
        
        mockMvc.perform(delete("/recipes/2"))
               .andExpect(status().isNotFound())
               .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
               .andExpect(content().json(marshall(expected)));
    }
    
    @Test
    public void test_指定されたレシピの削除でシステムエラーが発生した場合() throws Exception {
        when(service.deleteRecipe(1))
            .thenThrow(new SystemException("database access error is occurred."));
        
        ErrorResponse expected = new ErrorResponse("database access error is occurred.", null);
        
        mockMvc.perform(delete("/recipes/1"))
               .andExpect(status().isInternalServerError())
               .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
               .andExpect(content().json(marshall(expected)));
    }
    
    @Test
    public void test_レシピの作成が正常に処理できる場合() throws Exception {
        when(service.createRecipe(new Recipe(null,
                                             "トマトスープ",
                                             "15分",
                                             "5人",
                                             "玉ねぎ, トマト, スパイス, 水",
                                             450)))
            .thenReturn(new Recipe(3,
                                   "トマトスープ",
                                   "15分",
                                   "5人",
                                   "玉ねぎ, トマト, スパイス, 水",
                                   450));
        
        CreateRecipeResponsePayload expected
            = new CreateRecipeResponsePayload("Recipe successfully created!",
                                              new RecipePayload(null,
                                                               "トマトスープ",
                                                               "15分",
                                                               "5人",
                                                               "玉ねぎ, トマト, スパイス, 水",
                                                               "450"));
        
        CreateRecipeRequestPayload request
            = new CreateRecipeRequestPayload("トマトスープ",
                                             "15分",
                                             "5人",
                                             "玉ねぎ, トマト, スパイス, 水",
                                             "450");
        
        mockMvc.perform(post("/recipes")
                        .content(marshall(request))
                        .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
               .andExpect(status().isOk())
               .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
               .andExpect(content().json(marshall(expected)));
    }
    
    @Test
    public void test_レシピの作成でバリデーションエラーが発生する場合() throws Exception {
        CreateRecipeRequestPayload request = new CreateRecipeRequestPayload();
        
        ErrorResponse expected = new ErrorResponse("Recipe creation failed!",
                                                   "title, making_time, serves, ingredients, cost");
        
        mockMvc.perform(post("/recipes")
                        .content(marshall(request))
                        .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
               .andExpect(status().isBadRequest())
               .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
               .andExpect(content().json(marshall(expected)));
    }
    
    @Test
    public void test_レシピの作成でシステムエラーが発生した場合() throws Exception {
        when(service.createRecipe(new Recipe(null,
                                             "トマトスープ",
                                             "15分",
                                             "5人",
                                             "玉ねぎ, トマト, スパイス, 水",
                                             450)))
            .thenThrow(new SystemException("database access error is occurred."));
        
        ErrorResponse expected = new ErrorResponse("database access error is occurred.", null);
        
        CreateRecipeRequestPayload request
            = new CreateRecipeRequestPayload("トマトスープ",
                                             "15分",
                                             "5人",
                                             "玉ねぎ, トマト, スパイス, 水",
                                             "450");
        
        mockMvc.perform(post("/recipes")
                        .content(marshall(request))
                        .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
               .andExpect(status().isInternalServerError())
               .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
               .andExpect(content().json(marshall(expected)));
    }
    
    @Test
    public void test_全レシピ一覧を正常に返せる場合() throws Exception {
        when(service.getRecipes()).thenReturn(Arrays.asList(
                new Recipe(1, "チキンカレー", "45分", "4人", "玉ねぎ,肉,スパイス", 1000),
                new Recipe(2, "オムライス", "30分", "2人", "玉ねぎ,卵,スパイス,醤油", 700)
                ));
        
        GetRecipesResponsePayload expected = new GetRecipesResponsePayload();
        List<RecipePayload> recipes = Arrays.asList(
            new RecipePayload(Integer.valueOf(1), "チキンカレー", "45分", "4人", "玉ねぎ,肉,スパイス", "1000"),
            new RecipePayload(Integer.valueOf(2), "オムライス", "30分", "2人", "玉ねぎ,卵,スパイス,醤油", "700")
        );
        expected.setRecipes(recipes);
        
        mockMvc.perform(get("/recipes"))
               .andExpect(status().isOk())
               .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
               .andExpect(content().json(marshall(expected)));
    }
    
    @Test
    public void test_全レシピ一覧の取得でシステムエラーが発生した場合() throws Exception {
        when(service.getRecipes())
            .thenThrow(new SystemException("database access error is occurred."));
        
        ErrorResponse expected = new ErrorResponse("database access error is occurred.", null);
        
        mockMvc.perform(get("/recipes"))
               .andExpect(status().isInternalServerError())
               .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
               .andExpect(content().json(marshall(expected)));
    }
    
    @Test
    public void test_レシピを一つ正常に返せる場合() throws Exception {
        when(service.getRecipe(1)).thenReturn(new Recipe(1,
                                                         "チキンカレー",
                                                         "45分",
                                                         "4人",
                                                         "玉ねぎ,肉,スパイス",
                                                         1000));
        
        GetRecipeResponsePayload expected
            = new GetRecipeResponsePayload("Recipe details by id",
                                           new RecipePayload(null,
                                                             "チキンカレー",
                                                             "45分",
                                                             "4人",
                                                             "玉ねぎ,肉,スパイス",
                                                             "1000"));
        
        mockMvc.perform(get("/recipes/1"))
               .andExpect(status().isOk())
               .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
               .andExpect(content().json(marshall(expected)));
    }
    
    @Test
    public void test_指定されたIDに対応するレシピが存在せず参照できない場合() throws Exception {
        when(service.getRecipe(2)).thenReturn(null);
        
        ErrorResponse expected = new ErrorResponse("No Recipe found", null);
        
        mockMvc.perform(get("/recipes/2"))
               .andExpect(status().isNotFound())
               .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
               .andExpect(content().json(marshall(expected)));
    }
    
    @Test
    public void test_指定されたレシピの取得でシステムエラーが発生した場合() throws Exception {
        when(service.getRecipe(1))
            .thenThrow(new SystemException("database access error is occurred."));
        
        ErrorResponse expected = new ErrorResponse("database access error is occurred.", null);
        
        mockMvc.perform(get("/recipes/1"))
               .andExpect(status().isInternalServerError())
               .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
               .andExpect(content().json(marshall(expected)));
    }
    
}
