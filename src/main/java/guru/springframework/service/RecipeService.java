package guru.springframework.service;

import guru.springframework.commands.RecipeCommand;
import guru.springframework.model.Recipe;

import java.util.List;

public interface RecipeService {

    List<Recipe> getRecipeList();

    Recipe getRecipe(Long id);

    RecipeCommand saveRecipe(RecipeCommand command);
}
