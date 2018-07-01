package jp.co.softbank.aal.domain;

import jp.co.softbank.aal.integration.RecipeEntity;
import jp.co.softbank.aal.integration.RecipesDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * {@link RecipesManagementService} の実装を提供する具象クラスです。
 */
@Service
public class RecipesManagementServiceImpl implements RecipesManagementService {
    
    @Autowired
    RecipesDao dao;
    
    /**
     * {@inheritDoc}
     */
    @Override
    public Recipe getRecipe(int id) {
        RecipeEntity entity = dao.find(id);
        
        if (entity == null) {
            return null;
        }
        
        return entity.createDomain();
    }
    
}
