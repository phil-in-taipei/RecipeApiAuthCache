package RecipeApiCache.RecipeApiCache.services;
import RecipeApiCache.RecipeApiCache.models.Role;
import org.springframework.stereotype.Service;
import java.util.ArrayList;

@Service
public class GrantedAuthorityService {
    public ArrayList<Role> getGrantedAuthoritiesByUserId(Long userId) {
        return new ArrayList<>();
    }
}
