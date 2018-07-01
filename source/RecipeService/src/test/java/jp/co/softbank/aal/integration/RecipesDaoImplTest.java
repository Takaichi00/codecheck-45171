package jp.co.softbank.aal.integration;

import static org.hamcrest.CoreMatchers.*; 
import static org.junit.Assert.*;
import static com.ninja_squad.dbsetup.Operations.*;

import com.ninja_squad.dbsetup.DbSetup;
import com.ninja_squad.dbsetup.destination.DriverManagerDestination;
import com.ninja_squad.dbsetup.operation.Operation;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

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
    
    private static final Operation CREATE_TABLE
        = sql("CREATE TABLE IF NOT EXISTS recipes ("
            + "  id SERIAL PRIMARY KEY,"
            + "  title varchar(100) NOT NULL,"
            + "  making_time varchar(100) NOT NULL,"
            + "  serves varchar(100) NOT NULL,"
            + "  ingredients varchar(300) NOT NULL,"
            + "  cost integer NOT NULL,"
            + "  created_at timestamp default CURRENT_TIMESTAMP,"
            + "  updated_at timestamp default CURRENT_TIMESTAMP);");
    private static final Operation INSERT_RECIPES
        = insertInto("recipes")
            .columns("id",
                     "title",
                     "making_time",
                     "serves",
                     "ingredients",
                     "cost",
                     "created_at",
                     "updated_at")
            .values(1,
                    "チキンカレー",
                    "45分",
                    "4人",
                    "玉ねぎ,肉,スパイス",
                    1000,
                    Timestamp.valueOf("2016-01-10 12:10:12"),
                    Timestamp.valueOf("2016-01-10 12:10:12"))
            .values(2,
                    "オムライス",
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
        Operation operation = sequenceOf(CREATE_TABLE, INSERT_RECIPES);
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
}
