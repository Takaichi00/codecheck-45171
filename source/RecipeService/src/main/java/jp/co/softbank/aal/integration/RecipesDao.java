package jp.co.softbank.aal.integration;

import java.util.List;

/**
 * レシピのデータアクセスの機能を提供するインタフェースです。
 */
public interface RecipesDao {
    
    /**
     * 指定された情報でレシピデータを更新します。
     * 
     * <p>指定されたレシピデータが存在しない場合は null が戻されます。
     * 
     * @param entity 更新するレシピデータ
     * @return 更新されたレシピデータ
     * @throws SystemException データベースアクセスエラーが発生した場合
     */
    RecipeEntity update(RecipeEntity entity);
    
    /**
     * 指定された ID に対応するレシピデータを削除します。
     * 
     * @param id レシピデータを取得する ID
     * @return 処理したレシピデータの件数
     * @throws SystemException データベースアクセスエラーが発生した場合
     */
    int delete(Integer id);
    
    /**
     * 指定された情報でレシピデータを登録します。
     * 
     * @param entity 登録するレシピデータ
     * @return 登録されたレシピデータ
     * @throws SystemException データベースアクセスエラーが発生した場合
     */
    RecipeEntity create(RecipeEntity entity);
    
    /**
     * データベースに登録されている、全レシピデータの一覧を返します。
     * 
     * <p>データベースにレシピデータが１件もない場合、空のリストを返します。
     * 
     * @return 全レシピデータのリスト
     * @throws SystemException データベースアクセスエラーが発生した場合
     */
    List<RecipeEntity> findAll();
    
    /**
     * 指定された ID に対応するレシピデータを取得します。
     * 
     * <p>指定された ID に対応するレシピデータが存在しない場合は null が戻されます。
     * 
     * @param id レシピデータを取得する ID
     * @return 指定された ID に対応するレシピデータ
     * @throws SystemException データベースアクセスエラーが発生した場合
     */
    RecipeEntity find(Integer id);
}
