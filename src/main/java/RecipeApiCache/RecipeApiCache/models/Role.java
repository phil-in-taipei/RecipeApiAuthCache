package RecipeApiCache.RecipeApiCache.models;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import java.io.Serializable;


import javax.persistence.*;


@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Role implements Serializable, GrantedAuthority {
    private static final long serialVersionUID = 1621252305998890689L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Roles role;

    public Role(Roles role) {
        this.role = role;
    }

    @JsonIgnore
    public String getAuthority() {
        return role.name();
    }

    public enum Roles {
        ROLE_USER,
        ROLE_ADMIN
    }
}
