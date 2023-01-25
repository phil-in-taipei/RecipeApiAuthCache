package RecipeApiCache.RecipeApiCache.repositories;
import RecipeApiCache.RecipeApiCache.models.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository
public interface ReviewRepo extends JpaRepository<Review, Long> {
    ArrayList<Review> findByUser_Username(String username);
}
