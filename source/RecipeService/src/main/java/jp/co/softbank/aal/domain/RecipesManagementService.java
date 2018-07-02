package jp.co.softbank.aal.domain;

import java.util.List;

/**
 * レシピの管理を司るドメインのインタフェースです。
 */
public interface RecipesManagementService {
    
    /**
     * 指定された情報でレシピを作成します。
     * 
     * @param recipe 作成するレシピの情報
     * @return 作成が完了したレシピの情報
     * @throws SystemException システムエラーが発生した場合
     */
    Recipe createRecipe(Recipe recipe);
    
    /**
     * レシピ REST API サービスにおける、全レシピの一覧を返します。
     * 
     * <p>サービスに１件もレシピが登録されていない場合、空のリストを返します。
     * 
     * @return 全レシピの一覧のリスト
     * @throws SystemException システムエラーが発生した場合
     */
    List<Recipe> getRecipes();
    
    /**
     * 指定された ID に対応するレシピを取得します。
     * 
     * <p>指定された ID に対応するレシピデータが存在しない場合は null が戻されます。
     * 
     * @param id レシピを取得する ID
     * @return ID に対応するレシピ
     * @throws SystemException システムエラーが発生した場合
     */
    Recipe getRecipe(int id);
    
}
