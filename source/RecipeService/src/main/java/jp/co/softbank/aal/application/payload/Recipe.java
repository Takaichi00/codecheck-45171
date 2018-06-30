package jp.co.softbank.aal.application.payload;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 単一のレシピを表現するクラスです。
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@JsonPropertyOrder({"title", "making_time", "serves", "ingredients", "cost"})
public class Recipe {
    
    private String title;
    
    @JsonProperty("making_time")
    private String makingTime;
    
    private String serves;
    
    private String ingredients;
    
    private String cost;
}
