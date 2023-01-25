package RecipeApiCache.RecipeApiCache.models;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import javax.persistence.*;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Step implements Serializable {
    private static final long serialVersionUID = 6527855645691638321L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private int stepNumber;

    @Column(nullable = false)
    private String description;
}
