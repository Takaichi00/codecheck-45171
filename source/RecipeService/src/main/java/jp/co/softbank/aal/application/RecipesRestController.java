package jp.co.softbank.aal.application;

import static jp.co.softbank.aal.common.Constants.CREATE_RECIPE_NG;
import static jp.co.softbank.aal.common.Constants.CREATE_RECIPE_OK;
import static jp.co.softbank.aal.common.Constants.DELETE_RECIPE_OK;
import static jp.co.softbank.aal.common.Constants.GET_RECIPE_OK;
import static jp.co.softbank.aal.common.Constants.RECIPE_NOT_FOUND;
import static jp.co.softbank.aal.common.Constants.REQUIRED_FIELDS;

import java.util.List;
import jp.co.softbank.aal.application.payload.BadRequestException;
import jp.co.softbank.aal.application.payload.CreateRecipeRequestPayload;
import jp.co.softbank.aal.application.payload.CreateRecipeResponsePayload;
import jp.co.softbank.aal.application.payload.DeleteRecipeResponsePayload;
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
import org.springframework.http.MediaType;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
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
     * ID で指定されたレシピを削除するエンドポイント。
     * 
     * @param id 削除したいレシピの ID
     * @return 削除処理の結果のレシピのペイロード
     */
    @RequestMapping(method = RequestMethod.DELETE,
                    value = "{id}",
                    produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public DeleteRecipeResponsePayload deleteRecipe(@PathVariable("id") String id) {
        int result = 0;
        
        try {
            result = service.deleteRecipe(NumberUtils.createInteger(id));
            
        } catch (SystemException e) {
            throw new InternalServerException(e.getMessage());
        }
        
        if (result == 0) {
            throw new NotFoundException(RECIPE_NOT_FOUND);
        }
        
        return new DeleteRecipeResponsePayload(DELETE_RECIPE_OK);
    }
    
    /**
     * レシピを作成するエンドポイント。
     * 
     * @param request 作成するレシピのペイロード
     * @return 作成結果のペイロード
     */
    @RequestMapping(method = RequestMethod.POST,
                    consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
                    produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public CreateRecipeResponsePayload createRecipe(@RequestBody
                                                    @Validated
                                                    CreateRecipeRequestPayload request,
                                                    BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new BadRequestException(CREATE_RECIPE_NG, REQUIRED_FIELDS);
        }
        
        Recipe recipe = null;
        
        try {
            recipe = service.createRecipe(request.createInstance());
            
        } catch (SystemException e) {
            throw new InternalServerException(e.getMessage());
        }
        
        RecipePayload recipePayload = RecipePayload.createInstance(recipe);
        recipePayload.setId(null);
        return new CreateRecipeResponsePayload(CREATE_RECIPE_OK, recipePayload);
    }
    
    /**
     * 全レシピの一覧を返すエンドポイント。
     * 
     * @return 全レシピの一覧のペイロード
     */
    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public GetRecipesResponsePayload getRecipes() {
        List<Recipe> recipes = null;
        
        try {
            recipes = service.getRecipes();
            
        } catch (SystemException e) {
            throw new InternalServerException(e.getMessage());
        }
        
        return GetRecipesResponsePayload.createInstance(recipes);
    }
    
    /**
     * ID で指定されたレシピを一つを返すエンドポイント。
     * 
     * @param id 取得したいレシピの ID
     * @return 指定された ID に対するレシピのペイロード
     */
    @RequestMapping(method = RequestMethod.GET,
                    value = "{id}",
                    produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public GetRecipeResponsePayload getRecipe(@PathVariable("id") String id) {
        Recipe recipe = null;
        
        try {
            recipe = service.getRecipe(NumberUtils.createInteger(id));
            
        } catch (SystemException e) {
            throw new InternalServerException(e.getMessage());
        }
        
        if (recipe == null) {
            throw new NotFoundException(RECIPE_NOT_FOUND);
        }
        
        RecipePayload recipePayload = RecipePayload.createInstance(recipe);
        recipePayload.setId(null);
        return new GetRecipeResponsePayload(GET_RECIPE_OK, recipePayload);
    }
}
