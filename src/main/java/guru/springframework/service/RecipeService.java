package guru.springframework.service;

import guru.springframework.commands.IngredientCommand;
import guru.springframework.commands.RecipeCommand;
import guru.springframework.model.Recipe;


import java.util.List;
import java.util.Set;

public interface RecipeService {

    List<Recipe> getRecipeList();

    Recipe getRecipe(Long id);

    RecipeCommand saveRecipe(RecipeCommand command);

    RecipeCommand getRecipeCommandById(Long id);

    void deleteRecipeById(Long id);

}
