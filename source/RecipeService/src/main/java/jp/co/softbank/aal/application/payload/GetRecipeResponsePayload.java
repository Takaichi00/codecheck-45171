package jp.co.softbank.aal.application.payload;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 指定された id に対応するレシピを返すエンドポイントのレスポンスメッセージ
 * を表現するクラスです。
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class GetRecipeResponsePayload {
    
    private String message;
    
    private List<Recipe> recipe;
}
