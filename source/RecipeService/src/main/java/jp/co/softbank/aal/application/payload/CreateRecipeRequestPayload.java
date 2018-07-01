package jp.co.softbank.aal.application.payload;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * レシピを作成するエンドポイントのリクエストメッセージを表現するクラスです。
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({"id", "title", "making_time", "serves", "ingredients", "cost"})
public class CreateRecipeRequestPayload {
    
    private String title;
    
    @JsonProperty("making_time")
    private String makingTime;
    
    private String serves;
    
    private String ingredients;
    
    private String cost;
    
}
