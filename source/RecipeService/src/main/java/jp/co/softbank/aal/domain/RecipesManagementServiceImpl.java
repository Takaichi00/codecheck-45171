package jp.co.softbank.aal.domain;

import org.springframework.stereotype.Service;

/**
 * {@link RecipesManagementService} の実装を提供する具象クラスです。
 */
@Service
public class RecipesManagementServiceImpl implements RecipesManagementService {

    /**
     * {@inheritDoc}
     */
    @Override
    public Recipe getRecipe(int id) {
        return new Recipe("チキンカレー", "45分", "4人", "玉ねぎ,肉,スパイス", 1000);
    }
}
