package jp.co.softbank.aal.integration;

/**
 * レシピのデータアクセスの機能を提供するインタフェースです。
 */
public interface RecipesDao {

    /**
     * 指定された ID に対応するレシピデータを取得します。
     * 
     * @param id レシピデータを取得する ID
     * @return 指定された ID に対応するレシピデータ
     */
    RecipeEntity find(int id);
}
