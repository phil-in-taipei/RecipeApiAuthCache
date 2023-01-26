package RecipeApiCache.RecipeApiCache.models;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.persistence.*;
import java.io.Serializable;
import java.net.URI;
import java.net.URISyntaxException;


@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = "recipe")
public class Review implements Serializable {

    private static final long serialVersionUID = 6527855645691638321L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn
    @JsonIgnore
    private CustomUserDetails user;


    @Column(nullable = false)
    private int rating;

    private String description;

    @ManyToOne
    @JoinColumn(
            name = "recipeId",
            nullable = false,
            foreignKey = @ForeignKey
    )
    @JsonIgnore
    private Recipe recipe;

    @Transient
    @JsonIgnore
    private URI locationURI;

    public void setRating(int rating) {
        if(rating <= 0 || rating > 10) {
            throw new IllegalStateException("Rating must be between 0 and 10");
        }
        this.rating = rating;
    }

    public void generateLocationURI() {
        try {
            locationURI = new URI(
                    ServletUriComponentsBuilder.fromCurrentContextPath()
                            .path("/reviews/")
                            .path(String.valueOf(id))
                            .toUriString());
        }catch (URISyntaxException e) {
            //Exception should stop here.
        }
    }

    public String getAuthor() {
        return user.getUsername();
    }

}
