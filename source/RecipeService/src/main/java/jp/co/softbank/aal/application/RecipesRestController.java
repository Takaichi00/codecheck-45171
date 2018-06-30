package jp.co.softbank.aal.application;

import jp.co.softbank.aal.application.payload.GetRecipeResponsePayload;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


/**
 * 
 */
@RestController
@RequestMapping("/recipes")
public class RecipesRestController {
    
    @RequestMapping(method = RequestMethod.GET,
                    produces = MediaType.APPLICATION_JSON_VALUE,
                    value = "{id}")
    public GetRecipeResponsePayload getRecipe(@PathVariable("id") String id) {
        return null;
    }
    
}
