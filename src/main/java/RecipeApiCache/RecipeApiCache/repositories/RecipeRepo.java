package RecipeApiCache.RecipeApiCache.repositories;
import RecipeApiCache.RecipeApiCache.models.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository
public interface RecipeRepo extends JpaRepository<Recipe, Long>  {
    ArrayList<Recipe> findByUser_Username(String username);

    ArrayList<Recipe> findByNameContaining(String string);
}
