package jp.co.softbank.aal.domain;

/**
 * レシピの管理を司るドメインクラスです。
 */
public interface RecipesManagementService {

    /**
     * 指定された id に対応するレシピを取得します。
     * 
     * @param id レシピを取得する id
     * @return id に対応するレシピ
     */
    Recipe getRecipe(int id);
}
