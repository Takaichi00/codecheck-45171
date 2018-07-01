package jp.co.softbank.aal.integration;

import java.sql.Timestamp;
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
    public RecipeEntity find(int id) {
        RecipeEntity result = null;
        
        try {
            Map<String, Object> map
                = jdbcTemplate.queryForMap("select * from recipes where id = ?", id);
            
            result = new RecipeEntity(((Integer) map.get("ID")).intValue(),
                                       (String) map.get("TITLE"),
                                       (String) map.get("MAKING_TIME"),
                                       (String) map.get("SERVES"),
                                       (String) map.get("INGREDIENTS"),
                                       ((Integer) map.get("COST")).intValue(),
                                       (Timestamp) map.get("CREATED_AT"),
                                       (Timestamp) map.get("UPDATED_AT"));
            
        } catch (EmptyResultDataAccessException e) {
            LOG.info("recipe (id={}) is not found.", id);
            
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
    public List<RecipeEntity> findAll() {
        // TODO Auto-generated method stub
        return null;
    }
    
}
