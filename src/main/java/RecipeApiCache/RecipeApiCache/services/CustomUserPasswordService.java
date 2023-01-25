package RecipeApiCache.RecipeApiCache.services;
import RecipeApiCache.RecipeApiCache.repositories.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsPasswordService;
import org.springframework.security.crypto.password.PasswordEncoder;
import RecipeApiCache.RecipeApiCache.models.CustomUserDetails;


@Configuration
public class CustomUserPasswordService implements UserDetailsPasswordService {

    @Autowired
    UserRepo userRepo;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Override
    public UserDetails updatePassword(UserDetails user, String newPassword) {
        CustomUserDetails customUserDetails = (CustomUserDetails) user;
        customUserDetails.setPassword(passwordEncoder.encode(newPassword));
        userRepo.save(customUserDetails);
        return customUserDetails;
    }
}
