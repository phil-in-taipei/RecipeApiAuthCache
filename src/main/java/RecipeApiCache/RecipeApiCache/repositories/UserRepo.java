package RecipeApiCache.RecipeApiCache.repositories;
import RecipeApiCache.RecipeApiCache.models.CustomUserDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepo extends JpaRepository<CustomUserDetails, Long> {
    CustomUserDetails findByUsername(String username);
}
