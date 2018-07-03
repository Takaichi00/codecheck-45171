package jp.co.softbank.aal.domain;

import java.util.ArrayList;
import java.util.List;
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
    public Recipe updateRecipe(Recipe recipe) {
        RecipeEntity entity = dao.update(RecipeEntity.createInstance(recipe));
        
        if (entity == null) {
            return null;
        }
        
        return entity.createInstance();
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public int deleteRecipe(Integer id) {
        return dao.delete(id);
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public Recipe createRecipe(Recipe recipe) {
        RecipeEntity entity = dao.create(RecipeEntity.createInstance(recipe));
        return entity.createInstance();
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public List<Recipe> getRecipes() {
        List<Recipe> result = new ArrayList<>();
        
        List<RecipeEntity> entities = dao.findAll();
        
        for (RecipeEntity entity : entities) {
            result.add(entity.createInstance());
        }
        
        return result;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public Recipe getRecipe(Integer id) {
        RecipeEntity entity = dao.find(id);
        
        if (entity == null) {
            return null;
        }
        
        return entity.createInstance();
    }
    
}
