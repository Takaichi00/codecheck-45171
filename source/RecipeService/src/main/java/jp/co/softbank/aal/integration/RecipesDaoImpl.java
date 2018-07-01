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

/**
 * レシピの永続化のためのデータアクセスオブジェクト。
 */
@Repository
public class RecipesDaoImpl implements RecipesDao {
    
    private static final Logger LOG = LoggerFactory.getLogger(RecipesDaoImpl.class);
    
    @Autowired
    JdbcTemplate jdbcTemplate;
    
    /**
     * {@inheritDoc}
     */
    @Override
    public RecipeEntity create(RecipeEntity entity) {
        // TODO Auto-generated method stub
        return null;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public List<RecipeEntity> findAll() {
        List<RecipeEntity> result = new ArrayList<>();
        
        try {
            List<Map<String, Object>> records = jdbcTemplate.queryForList("select * from recipes");
            
            for (Map<String, Object> record : records) {
                result.add(new RecipeEntity(((Integer) record.get("ID")).intValue(),
                                            (String) record.get("TITLE"),
                                            (String) record.get("MAKING_TIME"),
                                            (String) record.get("SERVES"),
                                            (String) record.get("INGREDIENTS"),
                                            ((Integer) record.get("COST")).intValue(),
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
    public RecipeEntity find(int id) {
        RecipeEntity result = null;
        
        try {
            Map<String, Object> record
                = jdbcTemplate.queryForMap("select * from recipes where id = ?", id);
            
            result = new RecipeEntity(((Integer) record.get("ID")).intValue(),
                                      (String) record.get("TITLE"),
                                      (String) record.get("MAKING_TIME"),
                                      (String) record.get("SERVES"),
                                      (String) record.get("INGREDIENTS"),
                                      ((Integer) record.get("COST")).intValue(),
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
