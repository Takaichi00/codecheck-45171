package jp.co.softbank.aal.integration;

import java.util.List;

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
    
    /**
     * データベースに登録されている、全レシピデータの一覧を返します。
     * 
     * @return 全レシピデータのリスト
     * @throws SystemException データベースアクセスエラーが発生した場合
     */
    List<RecipeEntity> findAll();
}
