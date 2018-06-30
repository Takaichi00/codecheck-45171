package jp.co.softbank.aal.application;

import static org.hamcrest.CoreMatchers.*; 
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.sql.Timestamp;

import jp.co.softbank.aal.application.payload.GetRecipeResponsePayload;
import jp.co.softbank.aal.application.payload.RecipePayload;
import jp.co.softbank.aal.integration.RecipeEntity;
import jp.co.softbank.aal.integration.RecipesDao;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RecipesRestControllerTest {
    
    @MockBean
    RecipesDao dao;
    
    @Autowired
    RecipesRestController controller;
    
    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void test_レシピを一つ正常に返せる場合() {
        when(dao.find(1)).thenReturn(new RecipeEntity(1,
                                     "チキンカレー",
                                     "45分",
                                     "4人",
                                     "玉ねぎ,肉,スパイス",
                                     1000,
                                     Timestamp.valueOf("2016-01-10 12:10:12"),
                                     Timestamp.valueOf("2016-01-10 12:10:12")));
        
        GetRecipeResponsePayload actual = controller.getRecipe("1");
        
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
