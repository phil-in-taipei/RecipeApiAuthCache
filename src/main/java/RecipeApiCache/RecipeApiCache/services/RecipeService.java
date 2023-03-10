package RecipeApiCache.RecipeApiCache.services;
import RecipeApiCache.RecipeApiCache.exceptions.NoSuchRecipeException;
import RecipeApiCache.RecipeApiCache.models.Recipe;
import RecipeApiCache.RecipeApiCache.repositories.RecipeRepo;
//import RecipeApiCache.RecipeApiCache.repositories.RecipeRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import RecipeApiCache.RecipeApiCache.models.CustomUserDetails;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Optional;

@Service
//@CacheConfig(cacheNames = "recipes")
public class RecipeService {
    @Autowired
    RecipeRepo recipeRepo;

    @Transactional
    //@CachePut(value = "recipes", key = "#recipe.id")
    public Recipe createNewRecipe(Recipe recipe) throws IllegalStateException {
        CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        recipe.setUser(userDetails);
        recipe.validate();
        recipe = recipeRepo.save(recipe);
        recipe.generateLocationURI();
        return recipe;
    }

    //@Cacheable(value = "recipes", key = "#recipeId", sync = true)
    public Recipe getRecipeById(Long id) throws NoSuchRecipeException {
        Optional<Recipe> recipeOptional = recipeRepo.findById(id);

        if (recipeOptional.isEmpty()) {
            throw new NoSuchRecipeException("No recipe with ID " + id + " could be found");
        }

        Recipe recipe = recipeOptional.get();
        recipe.generateLocationURI();
        return recipe;
    }

    public ArrayList<Recipe> getRecipesByName(String name) throws NoSuchRecipeException {
        ArrayList<Recipe> matchingRecipes = recipeRepo.findByNameContaining(name);

        if (matchingRecipes.isEmpty()) {
            throw new NoSuchRecipeException("No recipes could be found with that name");
        }

        for (Recipe r : matchingRecipes) {
            r.generateLocationURI();
        }

        return matchingRecipes;
    }

    //@Transactional
    //@Cacheable("recipes")
    public ArrayList<Recipe> getAllRecipes() throws NoSuchRecipeException {
        ArrayList<Recipe> recipes = new ArrayList<>(recipeRepo.findAll());

        if (recipes.isEmpty()) {
            throw new NoSuchRecipeException("There are no recipes yet :( feel free to add one though");
        }

        return recipes;
    }

    @Transactional
    //@CacheEvict(value = "recipes", allEntries = true)
    public Recipe deleteRecipeById(Long id) throws NoSuchRecipeException {
        Recipe recipe = getRecipeById(id);
        recipeRepo.deleteById(id);
        return recipe;
    }

    @Transactional
    //@CacheEvict(value = "recipes", allEntries = true)
    public Recipe updateRecipe(Recipe recipe, boolean forceIdCheck) throws NoSuchRecipeException {
        try {
            if (forceIdCheck) {
                Recipe r = getRecipeById(recipe.getId());
                //make sure author never changes from original
                recipe.setUser(r.getUser());
            }

            recipe.validate();
            Recipe savedRecipe = recipeRepo.save(recipe);
            savedRecipe.generateLocationURI();
            return savedRecipe;
        } catch (NoSuchRecipeException e) {
            throw new NoSuchRecipeException("The recipe you passed in did not have an ID found in the database." +
                    " Double check that it is correct. Or maybe you meant to POST a recipe not PATCH one");
        }
    }
}
