package jp.co.softbank.aal.application.payload;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import jp.co.softbank.aal.domain.Recipe;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 単一のレシピを表現する JSON のペイロードクラスです。
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@JsonPropertyOrder({"title", "making_time", "serves", "ingredients", "cost"})
public class RecipePayload {
    
    private String title;
    
    @JsonProperty("making_time")
    private String makingTime;
    
    private String serves;
    
    private String ingredients;
    
    private String cost;
    
    /**
     * このペイロードの内容から {@link Recipe} クラスのインスタンスを作成します。
     */
    public RecipePayload(Recipe recipe) {
        this(recipe.getTitle(),
             recipe.getMakingTime(),
             recipe.getServes(),
             recipe.getIngredients(),
             Integer.toString(recipe.getCost()));
    }
}
