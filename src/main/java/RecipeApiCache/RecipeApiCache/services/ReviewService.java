package RecipeApiCache.RecipeApiCache.services;
import RecipeApiCache.RecipeApiCache.exceptions.NoSuchReviewException;
import RecipeApiCache.RecipeApiCache.exceptions.NoSuchRecipeException;

import RecipeApiCache.RecipeApiCache.repositories.ReviewRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import RecipeApiCache.RecipeApiCache.models.CustomUserDetails;
import RecipeApiCache.RecipeApiCache.models.Recipe;
import RecipeApiCache.RecipeApiCache.models.Review;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ReviewService {
    @Autowired
    ReviewRepo reviewRepo;

    @Autowired
    RecipeService recipeService;

    @Cacheable("reviews")
    public List<Review> getAllReviews() throws NoSuchReviewException {
        List<Review> reviews = reviewRepo.findAll();

        if (reviews.isEmpty()) {
            throw new NoSuchReviewException("There are no reviews to be found");
        }

        return reviews;
    }


    //@Cacheable(value = "reviews", key = "#id", sync = true)
    @CachePut(value = "reviews", key = "#id")
    public Review getReviewById(Long id) throws NoSuchReviewException {
        Optional<Review> review = reviewRepo.findById(id);

        if (review.isEmpty()) {
            throw new NoSuchReviewException("The review with ID " + id + " could not be found");
        }

        return review.get();
    }

    public ArrayList<Review> getReviewByRecipeId(Long recipeId) throws NoSuchRecipeException, NoSuchReviewException {
        Recipe recipe = recipeService.getRecipeById(recipeId);

        ArrayList<Review> reviews = new ArrayList<>(recipe.getReviews());

        if (reviews.isEmpty()) {
            throw new NoSuchReviewException("There are no reviews for this recipe");
        }

        return reviews;
    }

    public ArrayList<Review> getReviewByUsername(String username) throws NoSuchReviewException {
        ArrayList<Review> reviews = reviewRepo.findByUser_Username(username);

        if (reviews.isEmpty()) {
            throw new NoSuchReviewException("No reviews could be found for username " + username);
        }

        return reviews;
    }

    // won't work because it returns recipe and not review
    //@CachePut(value = "review", key = "#reviewId") // recipeId
    public Recipe postNewReview(Review review, Long recipeId) throws NoSuchRecipeException {
        Recipe recipe = recipeService.getRecipeById(recipeId);
        recipe.getReviews().add(review);
        recipeService.updateRecipe(recipe, false);
        return recipe;
    }

    @CacheEvict(value = "reviews", allEntries = true)
    public Review deleteReviewById(Long id) throws NoSuchReviewException {
        Review review = getReviewById(id);

        if (null == review) {
            throw new NoSuchReviewException("The review you are trying to delete does not exist");
        }

        reviewRepo.deleteById(id);
        return review;
    }

    @CachePut(value = "reviews", key = "#reviewToUpdate.id")
    public Review updateReviewById(Review reviewToUpdate) throws NoSuchReviewException {
        try {
            Review review = getReviewById(reviewToUpdate.getId());
        } catch (NoSuchReviewException e) {
            throw new NoSuchReviewException("The review you are trying to update. Maybe you meant to create one? If not," +
                    "Please double check the ID you passed in");
        }

        reviewRepo.save(reviewToUpdate);
        return reviewToUpdate;
    }

}
