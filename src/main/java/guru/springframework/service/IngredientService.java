package guru.springframework.service;

import guru.springframework.commands.IngredientCommand;

public interface IngredientService {

    IngredientCommand findByRecipeIDAndIngredientId(Long recipeId, Long ingredientId);

    IngredientCommand saveIngredient(IngredientCommand ingredientCommand);
}
