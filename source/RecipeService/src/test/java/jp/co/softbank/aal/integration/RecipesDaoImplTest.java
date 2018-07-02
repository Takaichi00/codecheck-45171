package jp.co.softbank.aal.integration;

import static com.ninja_squad.dbsetup.Operations.*;
import static jp.co.softbank.aal.common.TestUtils.*;
import static org.hamcrest.CoreMatchers.*; 
import static org.junit.Assert.*;

import com.ninja_squad.dbsetup.DbSetup;
import com.ninja_squad.dbsetup.destination.DriverManagerDestination;
import com.ninja_squad.dbsetup.operation.Operation;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.time.DateFormatUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RecipesDaoImplTest {
    
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
    
    @Autowired
    private RecipesDaoImpl dao;
    
    @Before
    public void setUp() throws Exception {
        Operation operation = sequenceOf(DROP_TABLE, CREATE_TABLE, INSERT_RECIPES);
        DbSetup dbSetup = new DbSetup(
                new DriverManagerDestination("jdbc:h2:mem:test;MODE=PostgreSQL;DB_CLOSE_DELAY=-1",
                                             "sa",
                                             "sa"),
                operation);
        dbSetup.launch();
    }
    
    @After
    public void tearDown() throws Exception {
    }
    
    @Test
    public void test_正常にデータが1件削除できる場合() {
        List<RecipeEntity> expected = new ArrayList<>();
        expected.add(new RecipeEntity(2,
                                      "オムライス",
                                      "30分",
                                      "2人",
                                      "玉ねぎ,卵,スパイス,醤油",
                                      700,
                                      Timestamp.valueOf("2016-01-11 13:10:12"),
                                      Timestamp.valueOf("2016-01-11 13:10:12")));
        
        int executed = dao.delete(1);
        assertThat(executed, is(1));
        
        List<RecipeEntity> actual = dao.findAll();
        assertThat(actual, is(expected));
    }
    
    @Test
    public void test_正常にデータの登録ができる場合() {
        RecipeEntity actual = dao.create(new RecipeEntity(null,
                                                         "トマトスープ",
                                                         "15分",
                                                         "5人",
                                                         "玉ねぎ, トマト, スパイス, 水",
                                                         450,
                                                         null,
                                                         null));
        
        assertThat(actual.getId(), is(3));
        assertThat(actual.getTitle(), is("トマトスープ"));
        assertThat(actual.getMakingTime(), is("15分"));
        assertThat(actual.getServes(), is("5人"));
        assertThat(actual.getIngredients(), is("玉ねぎ, トマト, スパイス, 水"));
        assertThat(actual.getCost(), is(450));
        assertThat(DateFormatUtils.format(actual.getCreatedAt(), "yyyy-MM-dd"),
                   is(DateFormatUtils.format(new Date(), "yyyy-MM-dd")));
        assertThat(DateFormatUtils.format(actual.getUpdatedAt(), "yyyy-MM-dd"),
                   is(DateFormatUtils.format(new Date(), "yyyy-MM-dd")));
    }
    
    @Test
    public void test_正常に全データがロードできる場合() {
        List<RecipeEntity> expected = new ArrayList<>();
        expected.add(new RecipeEntity(1,
                                      "チキンカレー",
                                      "45分",
                                      "4人",
                                      "玉ねぎ,肉,スパイス",
                                      1000,
                                      Timestamp.valueOf("2016-01-10 12:10:12"),
                                      Timestamp.valueOf("2016-01-10 12:10:12")));
        expected.add(new RecipeEntity(2,
                                      "オムライス",
                                      "30分",
                                      "2人",
                                      "玉ねぎ,卵,スパイス,醤油",
                                      700,
                                      Timestamp.valueOf("2016-01-11 13:10:12"),
                                      Timestamp.valueOf("2016-01-11 13:10:12")));
        
        List<RecipeEntity> actual = dao.findAll();
        
        assertThat(actual, is(expected));
    }
    
    @Test
    public void test_正常にデータが1件ロードできる場合() {
        RecipeEntity expected = new RecipeEntity(1,
                                                 "チキンカレー",
                                                 "45分",
                                                 "4人",
                                                 "玉ねぎ,肉,スパイス",
                                                 1000,
                                                 Timestamp.valueOf("2016-01-10 12:10:12"),
                                                 Timestamp.valueOf("2016-01-10 12:10:12"));
        RecipeEntity actual = dao.find(1);
        
        assertThat(actual, is(expected));
    }
    
}
