package jp.co.softbank.aal.application;

import static org.hamcrest.CoreMatchers.*; 
import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import jp.co.softbank.aal.application.payload.GetRecipeResponsePayload;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RecipesRestControllerTest {
    
    RestTemplate restTemplate;
    
    @Before
    public void setUp() throws Exception {
        restTemplate = new RestTemplate();
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void test_レシピを一つ正常に返せる場合() {
        GetRecipeResponsePayload actual = restTemplate.getForObject("http://localhost:8080/recipes/1", GetRecipeResponsePayload.class);
        System.out.println(actual);
    }

}
