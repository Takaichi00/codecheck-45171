package jp.co.softbank.aal.integration;

import java.sql.Timestamp;
import java.util.Map;

import org.apache.commons.lang.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

/**
 * レシピの永続化のためのデータアクセスオブジェクト。
 */
@Repository
public class RecipesDaoImpl implements RecipesDao {
    
    @Autowired
    JdbcTemplate jdbcTemplate;
    
    /**
     * {@inheritDoc}
     */
    @Override
    public RecipeEntity find(int id) {
        RecipeEntity result = null;
        
        Map<String, Object> map = jdbcTemplate.queryForMap("select * from recipes where id = ?", id);
        
        result = new RecipeEntity(((Integer) map.get("ID")).intValue(),
                                   (String) map.get("TITLE"),
                                   (String) map.get("MAKING_TIME"),
                                   (String) map.get("SERVES"),
                                   (String) map.get("INGREDIENTS"),
                                   ((Integer) map.get("COST")).intValue(),
                                   (Timestamp) map.get("CREATED_AT"),
                                   (Timestamp) map.get("UPDATED_AT"));
        
        return result;
    }
}
