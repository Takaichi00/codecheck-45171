package jp.co.softbank.aal.integration;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import jp.co.softbank.aal.common.SystemException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * レシピの永続化のためのデータアクセスオブジェクト。
 */
@Repository
public class RecipesDaoImpl implements RecipesDao {
    
    private static final Logger LOG = LoggerFactory.getLogger(RecipesDaoImpl.class);
    
    private static final String CREATE_RECIPE
        = "insert into recipes(title, making_time , serves, ingredients, cost) "
          + "values(?, ?, ?, ?, ?)";
    private static final String SELECT_LAST_ID = "select lastval()";
    private static final String SELECT_ALL_RECIPES = "select * from recipes";
    private static final String SELECT_RECIPE_BY_ID = "select * from recipes where id = ?";
    
    @Autowired
    JdbcTemplate jdbcTemplate;
    
    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public RecipeEntity create(RecipeEntity entity) {
        Integer id = null;
        
        try {
            jdbcTemplate.update(CREATE_RECIPE,
                                entity.getTitle(),
                                entity.getMakingTime(),
                                entity.getServes(),
                                entity.getIngredients(),
                                entity.getCost());
            id = jdbcTemplate.queryForObject(SELECT_LAST_ID, Integer.class);
            
        } catch (DataAccessException e) {
            LOG.error("database access error is occurred.", e);
            throw new SystemException("database access error is occurred.", e);
            
        }
        
        return find(id);
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public List<RecipeEntity> findAll() {
        List<RecipeEntity> result = new ArrayList<>();
        
        try {
            List<Map<String, Object>> records = jdbcTemplate.queryForList(SELECT_ALL_RECIPES);
            
            for (Map<String, Object> record : records) {
                result.add(new RecipeEntity((Integer) record.get("ID"),
                                            (String) record.get("TITLE"),
                                            (String) record.get("MAKING_TIME"),
                                            (String) record.get("SERVES"),
                                            (String) record.get("INGREDIENTS"),
                                            (Integer) record.get("COST"),
                                            (Timestamp) record.get("CREATED_AT"),
                                            (Timestamp) record.get("UPDATED_AT")));
            }
        
        } catch (DataAccessException e) {
            LOG.error("database access error is occurred.", e);
            throw new SystemException("database access error is occurred.", e);
            
        }
        
        return result;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public RecipeEntity find(Integer id) {
        RecipeEntity result = null;
        
        try {
            Map<String, Object> record
                = jdbcTemplate.queryForMap(SELECT_RECIPE_BY_ID, id);
            
            result = new RecipeEntity((Integer) record.get("ID"),
                                      (String) record.get("TITLE"),
                                      (String) record.get("MAKING_TIME"),
                                      (String) record.get("SERVES"),
                                      (String) record.get("INGREDIENTS"),
                                      (Integer) record.get("COST"),
                                      (Timestamp) record.get("CREATED_AT"),
                                      (Timestamp) record.get("UPDATED_AT"));
            
        } catch (EmptyResultDataAccessException e) {
            LOG.info("recipe (id={}) is not found.", id);
            
        } catch (DataAccessException e) {
            LOG.error("database access error is occurred.", e);
            throw new SystemException("database access error is occurred.", e);
            
        }
        
        return result;
    }
    
}
