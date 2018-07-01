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
    private Integer id;
    private String title;
    private String makingTime;
    private String serves;
    private String ingredients;
    private Integer cost;
    private Timestamp createdAt;
    private Timestamp updatedAt;
    
    /**
     * {@link Recipe} ドメインクラスのインスタンスの情報を使用して、{@link RecipeEntity}
     * エンティティのインスタンスを構築します。
     * 
     * @param recipe {@link Recipe} ドメインクラスのインスタンス
     * @return {@link RecipeEntity} エンティティのインスタンス
     */
    public static RecipeEntity createInstance(Recipe recipe) {
        return new RecipeEntity(null,
                                recipe.getTitle(),
                                recipe.getMakingTime(),
                                recipe.getServes(),
                                recipe.getIngredients(),
                                recipe.getCost(),
                                null,
                                null);
    }
    
    /**
     * このクラスの情報を使用してドメインのクラスを作成します。
     * 
     * @return {@link Recipe} ドメインクラスのインスタンス
     */
    public Recipe createInstance() {
        return new Recipe(id, title, makingTime, serves, ingredients, cost);
    }
}
