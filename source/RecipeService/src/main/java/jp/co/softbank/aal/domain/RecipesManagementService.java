package jp.co.softbank.aal.domain;

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
    
}
