package RecipeApiCache.RecipeApiCache.models;
import lombok.*;
import org.hibernate.Hibernate;
import java.io.Serializable;


import javax.persistence.*;
import java.util.Objects;

@Entity
@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Ingredient implements Serializable {
    private static final long serialVersionUID = 6527855645691638321L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String amount;

    private String state;
}
