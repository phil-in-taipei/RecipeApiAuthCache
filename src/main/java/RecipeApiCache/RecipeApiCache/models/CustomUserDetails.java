package RecipeApiCache.RecipeApiCache.models;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.io.Serializable;


@Entity
@Getter
@Setter
//@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CustomUserDetails implements Serializable, UserDetails {

    private static final long serialVersionUID = 6527855645691638321L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    public String username;

    @Column(nullable = false)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    @Column(nullable = false)
    private boolean isAccountNonExpired = true;

    @Column(nullable = false)
    private boolean isAccountNonLocked = true;

    @Column(nullable = false)
    private boolean isCredentialsNonExpired = true;

    @Column(nullable = false)
    private boolean isEnabled = true;

    public CustomUserDetails(String username, String password, Collection<Role> authorities, UserMeta userMeta) {
        this.username = username;
        this.password = password;
        this.authorities = authorities;
        this.userMeta = userMeta;
    }

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(
            name = "userId",
            nullable = false
    )
    private Collection<Role> authorities = new ArrayList<>();

    @OneToOne(cascade = CascadeType.PERSIST, optional = false)
    private UserMeta userMeta;
}
