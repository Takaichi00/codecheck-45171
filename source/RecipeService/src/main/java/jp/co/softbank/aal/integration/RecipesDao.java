package jp.co.softbank.aal.integration;

/**
 * レシピのデータアクセスの機能を提供するインタフェースです。
 */
public interface RecipesDao {
    
    /**
     * 指定された ID に対応するレシピデータを取得します。
     * 
     * <p>指定された ID に対応するレシピデータが存在しない場合は null が戻されます。
     * 
     * @param id レシピデータを取得する ID
     * @return 指定された ID に対応するレシピデータ
     * @throws SystemException データベースアクセスエラーが発生した場合
     */
    RecipeEntity find(int id);
    
}
