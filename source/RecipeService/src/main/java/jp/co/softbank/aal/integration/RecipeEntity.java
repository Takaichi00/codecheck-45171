package jp.co.softbank.aal.integration;

import java.sql.Timestamp;

import jp.co.softbank.aal.domain.Recipe;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 単一のレシピを表現するエンティティクラスです。
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class RecipeEntity {
    private int id;
    private String title;
    private String makingTime;
    private String serves;
    private String ingredients;
    private int cost;
    private Timestamp createdAt;
    private Timestamp updatedAt;
    
    /**
     * このクラスの情報を使用してドメインのクラスを作成します。
     * 
     * @return {@link Recipe} ドメインクラスのインスタンス
     */
    public Recipe createDomain() {
        return new Recipe(id, title, makingTime, serves, ingredients, cost);
    }
}
