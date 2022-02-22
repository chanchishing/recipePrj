package guru.springframework.service;


import guru.springframework.commands.IngredientCommand;
import guru.springframework.converters.IngredientToIngredientCommand;
import guru.springframework.model.Ingredient;
import guru.springframework.model.Recipe;
import guru.springframework.repositories.RecipeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Set;

@Slf4j
@Service
public class IngredientServiceImpl implements IngredientService{
    private final RecipeRepository recipeRepository;
    private final IngredientToIngredientCommand ingredientToCommand;

    public IngredientServiceImpl(RecipeRepository recipeRepository, IngredientToIngredientCommand ingredientToCommand) {
        this.recipeRepository = recipeRepository;
        this.ingredientToCommand = ingredientToCommand;
    }

    @Override
    public IngredientCommand findByRecipeIDAndIngredientId(Long recipeId, Long ingredientId) {
        Recipe recipe=recipeRepository.findById(recipeId).get();
        Set<Ingredient> ingredientSet=recipe.getIngredients();

        Ingredient ingredient=ingredientSet.stream().filter(element->element.getId()==ingredientId).findFirst().get();

        return ingredientToCommand.convert(ingredient);
    }
}
