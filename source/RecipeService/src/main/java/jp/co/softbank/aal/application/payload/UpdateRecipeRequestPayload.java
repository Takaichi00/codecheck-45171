package jp.co.softbank.aal.application.payload;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import javax.validation.constraints.NotNull;
import jp.co.softbank.aal.domain.Recipe;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang.math.NumberUtils;

/**
 * 指定されたレシピを更新するエンドポイントのリクエストメッセージを表現するクラスです。
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({"title", "making_time", "serves", "ingredients", "cost"})
public class UpdateRecipeRequestPayload {
    
    @NotNull
    private String title;
    
    @NotNull
    @JsonProperty("making_time")
    private String makingTime;
    
    @NotNull
    private String serves;
    
    @NotNull
    private String ingredients;
    
    @NotNull
    private String cost;
    
    /**
     * このペイロードの情報を使用して、{@link Recipe} クラスのインスタンスを構築して、返します。
     * 
     * @param id レシピの ID
     * @return {@link Recipe} クラスのインスタンス
     */
    public Recipe createInstance(String id) {
        return new Recipe(NumberUtils.createInteger(id),
                          title,
                          makingTime,
                          serves,
                          ingredients,
                          NumberUtils.createInteger(cost));
    }
}
