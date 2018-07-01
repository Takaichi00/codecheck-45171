package jp.co.softbank.aal.application.payload;

import com.fasterxml.jackson.annotation.JsonInclude;
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
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({"id", "title", "making_time", "serves", "ingredients", "cost"})
public class RecipePayload {
    
    private Integer id;
    
    private String title;
    
    @JsonProperty("making_time")
    private String makingTime;
    
    private String serves;
    
    private String ingredients;
    
    private String cost;
    
    /**
     * {@link Recipe} クラスのインスタンスから、このペイロードのインスタンスを構築して、返します。
     * 
     * @param recipe ドメインの {@link Recipe} クラスのインスタンス
     * @return このペイロードのインスタンス
     */
    public static RecipePayload createInstance(Recipe recipe) {
        RecipePayload result = new RecipePayload(Integer.valueOf(recipe.getId()),
                                                 recipe.getTitle(),
                                                 recipe.getMakingTime(),
                                                 recipe.getServes(),
                                                 recipe.getIngredients(),
                                                 Integer.toString(recipe.getCost()));
        return result;
    }
    
}
