package jp.co.softbank.aal.application;

import static jp.co.softbank.aal.common.Messages.GET_RECIPE_OK;
import static jp.co.softbank.aal.common.Messages.RECIPE_NOT_FOUND;

import jp.co.softbank.aal.application.payload.GetRecipeResponsePayload;
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
     * ID で指定されたレシピを一つを返すエンドポイント。
     * 
     * @param id 取得したいレシピの ID
     * @return 指定された ID に対するレシピのペイロード
     */
    @RequestMapping(method = RequestMethod.GET,
                    value = "{id}")
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
