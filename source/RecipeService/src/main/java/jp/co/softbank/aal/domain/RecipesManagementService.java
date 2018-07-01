package jp.co.softbank.aal.domain;

import java.util.List;

/**
 * レシピの管理を司るドメインのインタフェースです。
 */
public interface RecipesManagementService {
    
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
    
    /**
     * レシピ REST API サービスにおける、全レシピの一覧を返します。
     * 
     * @return 全レシピの一覧のリスト
     */
    List<Recipe> getRecipes();
}
