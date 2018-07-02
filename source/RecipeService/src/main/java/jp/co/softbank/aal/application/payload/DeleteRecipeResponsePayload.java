package jp.co.softbank.aal.application.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 指定されたレシピを削除するエンドポイントのレスポンスメッセージを表現するクラスです。
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class DeleteRecipeResponsePayload {
    private String message;
}
