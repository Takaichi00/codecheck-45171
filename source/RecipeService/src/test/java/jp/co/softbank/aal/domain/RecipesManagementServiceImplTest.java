package jp.co.softbank.aal.domain;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import jp.co.softbank.aal.integration.RecipeEntity;
import jp.co.softbank.aal.integration.RecipesDao;

import org.assertj.core.util.Arrays;
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
public class RecipesManagementServiceImplTest {
    
    @MockBean
    RecipesDao dao;
    
    @Autowired
    RecipesManagementServiceImpl service;
    
    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }
    
    @Test
    public void test_レシピを正常に作成できる場合() {
        when(dao.create(new RecipeEntity(null,
                                         "トマトスープ",
                                         "15分",
                                         "5人",
                                         "玉ねぎ, トマト, スパイス, 水",
                                         450,
                                         null,
                                         null)))
            .thenReturn(new RecipeEntity(3,
                                         "トマトスープ",
                                         "15分",
                                         "5人",
                                         "玉ねぎ, トマト, スパイス, 水",
                                         450,
                                         Timestamp.valueOf("2018-01-10 12:10:12"),
                                         Timestamp.valueOf("2018-01-10 12:10:12")));
        
        Recipe actual = service.createRecipe(new Recipe(null,
                                                        "トマトスープ",
                                                        "15分",
                                                        "5人",
                                                        "玉ねぎ, トマト, スパイス, 水",
                                                        450));
        
        Recipe expected = new Recipe(3, "トマトスープ", "15分", "5人", "玉ねぎ, トマト, スパイス, 水", 450);
        assertThat(actual, is(expected));
    }
    
    @Test
    public void test_全レシピ一覧を正常に返せる場合() {
        List<RecipeEntity> entiries = new ArrayList<>();
        entiries.add(new RecipeEntity(1,
                                      "チキンカレー",
                                      "45分",
                                      "4人",
                                      "玉ねぎ,肉,スパイス",
                                      1000,
                                      Timestamp.valueOf("2016-01-10 12:10:12"),
                                      Timestamp.valueOf("2016-01-10 12:10:12")));
        entiries.add(new RecipeEntity(2,
                                      "オムライス",
                                      "30分",
                                      "2人",
                                      "玉ねぎ,卵,スパイス,醤油",
                                      700,
                                      Timestamp.valueOf("2016-01-11 13:10:12"),
                                      Timestamp.valueOf("2016-01-11 13:10:12")));
        when(dao.findAll()).thenReturn(entiries);
        
        List<Recipe> actual = service.getRecipes();
        
        List<Recipe> expected = new ArrayList<>();
        expected.add(new Recipe(1, "チキンカレー", "45分", "4人", "玉ねぎ,肉,スパイス", 1000));
        expected.add(new Recipe(2, "オムライス", "30分", "2人", "玉ねぎ,卵,スパイス,醤油", 700));
        assertThat(actual, is(expected));
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
        
        Recipe actual = service.getRecipe(1);
        
        Recipe expected = new Recipe(1, "チキンカレー", "45分", "4人", "玉ねぎ,肉,スパイス", 1000);
        assertThat(actual, is(expected));
    }
    
}
