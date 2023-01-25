package RecipeApiCache.RecipeApiCache.models;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.io.Serializable;

import javax.persistence.*;

@Entity
@Table(name = "user_meta")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class UserMeta implements Serializable {

    private static final long serialVersionUID = 6527855645691638321L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String name;
}
