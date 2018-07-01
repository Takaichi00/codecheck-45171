package jp.co.softbank.aal.application.payload;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.util.ArrayList;
import java.util.List;
import jp.co.softbank.aal.domain.Recipe;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 全レシピ一覧を返すエンドポイントのレスポンスメッセージを表現するクラスです。
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class GetRecipesResponsePayload {
    
    private List<RecipePayload> recipes;
    
    /**
     * {@link Recipe} クラスのインスタンスのリストから、このペイロードのインスタンスを構築して、返します。
     * 
     * @param recipeList {@link Recipe} クラスのインスタンスのリスト
     * @return このペイロードのインスタンス
     */
    public static GetRecipesResponsePayload createInstance(List<Recipe> recipeList) {
        GetRecipesResponsePayload result = new GetRecipesResponsePayload();
        List<RecipePayload> recipes = new ArrayList<>();
        
        for (Recipe recipe : recipeList) {
            recipes.add(RecipePayload.createInstance(recipe));
        }
        
        result.setRecipes(recipes);
        
        return result;
    }
}
