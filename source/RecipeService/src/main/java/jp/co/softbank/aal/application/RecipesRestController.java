package jp.co.softbank.aal.application;

import static jp.co.softbank.aal.common.Messages.GET_RECIPE_OK;
import static jp.co.softbank.aal.common.Messages.RECIPE_NOT_FOUND;

import java.util.Arrays;
import java.util.List;

import jp.co.softbank.aal.application.payload.GetRecipeResponsePayload;
import jp.co.softbank.aal.application.payload.GetRecipesResponsePayload;
import jp.co.softbank.aal.application.payload.InternalServerException;
import jp.co.softbank.aal.application.payload.NotFoundException;
import jp.co.softbank.aal.application.payload.RecipePayload;
import jp.co.softbank.aal.common.SystemException;
import jp.co.softbank.aal.domain.Recipe;
import jp.co.softbank.aal.domain.RecipesManagementService;
import org.apache.commons.lang.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * レシピ REST API のエンドポイントの機能を提供します。
 */
@RestController
@RequestMapping("/recipes")
public class RecipesRestController {
    
    @Autowired
    RecipesManagementService service;
    
    /**
     * 全レシピ一覧を返すエンドポイント。
     * 
     * @return 全レシピ一覧のペイロード
     */
    @RequestMapping(method = RequestMethod.GET)
    public GetRecipesResponsePayload getRecipes() {
        GetRecipesResponsePayload result = new GetRecipesResponsePayload();
        List<RecipePayload> recipes = Arrays.asList(
            new RecipePayload(Integer.valueOf(1), "チキンカレー", "45分", "4人", "玉ねぎ,肉,スパイス", "1000"),
            new RecipePayload(Integer.valueOf(2), "オムライス", "30分", "2人", "玉ねぎ,卵,スパイス,醤油", "700")
        );
        result.setRecipes(recipes);
        
        return result;
    }
    
    /**
     * ID で指定されたレシピを一つを返すエンドポイント。
     * 
     * @param id 取得したいレシピの ID
     * @return 指定された ID に対するレシピのペイロード
     */
    @RequestMapping(method = RequestMethod.GET, value = "{id}")
    public GetRecipeResponsePayload getRecipe(@PathVariable("id") String id) {
        Recipe recipe = null;
        
        try {
            recipe = service.getRecipe(NumberUtils.toInt(id));
            
        } catch (SystemException e) {
            throw new InternalServerException(e.getMessage());
        }
        
        if (recipe == null) {
            throw new NotFoundException(RECIPE_NOT_FOUND);
        }
        
        RecipePayload recipePayload = new RecipePayload(recipe);
        recipePayload.setId(null);
        return new GetRecipeResponsePayload(GET_RECIPE_OK, recipePayload);
    }
}
