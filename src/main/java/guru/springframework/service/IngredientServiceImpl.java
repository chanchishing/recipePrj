package guru.springframework.service;


import guru.springframework.commands.IngredientCommand;
import guru.springframework.converters.IngredientCommandToIngredient;
import guru.springframework.converters.IngredientToIngredientCommand;
import guru.springframework.model.Ingredient;
import guru.springframework.model.Recipe;
import guru.springframework.repositories.RecipeRepository;
import guru.springframework.repositories.UnitOfMeasureRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;
import java.util.Set;

@Slf4j
@Service
public class IngredientServiceImpl implements IngredientService{
    private final RecipeRepository recipeRepository;
    private final UnitOfMeasureRepository uomRepository;
    private final IngredientToIngredientCommand ingredientToCommand;
    private final IngredientCommandToIngredient commandToIngredient;

    public IngredientServiceImpl(RecipeRepository recipeRepository, UnitOfMeasureRepository uomRepository, IngredientToIngredientCommand ingredientToCommand, IngredientCommandToIngredient commandToIngredient) {
        this.recipeRepository = recipeRepository;
        this.uomRepository = uomRepository;
        this.ingredientToCommand = ingredientToCommand;
        this.commandToIngredient = commandToIngredient;
    }

    @Override
    public IngredientCommand findByRecipeIDAndIngredientId(Long recipeId, Long ingredientId) {
        Recipe recipe=recipeRepository.findById(recipeId).get();
        Set<Ingredient> ingredientSet=recipe.getIngredients();

        Ingredient ingredient=ingredientSet.stream().filter(element->element.getId()==ingredientId).findFirst().get();

        return ingredientToCommand.convert(ingredient);
    }

    @Transactional
    @Override
    public IngredientCommand saveIngredient(IngredientCommand ingredientCommand) {
        Recipe recipe;

        //Check recipe of Ingredient is an existing recipe
        try {
            recipe = recipeRepository.findById(ingredientCommand.getRecipeId()).orElseThrow();
        } catch (NoSuchElementException noElement) {
            log.error("Recipe Not Found");
            throw noElement;
        } catch (Exception e){
            throw e;
        }

        recipe.getIngredients().stream().filter(element->element.getId()==ingredientCommand.getId()).findFirst().ifPresentOrElse(
                //ingredient is an existing ingredient of recipe
                (ingredient) -> {
                    ingredient.setDescription(ingredientCommand.getDescription());
                    ingredient.setAmount(ingredientCommand.getAmount());
                    try { //make sure uom already exists in DB
                        ingredient.setUom(uomRepository.findById(ingredientCommand.getUom().getId()).orElseThrow());
                    } catch (NoSuchElementException noElement){
                        log.error("Unit of Measure not found");
                        throw noElement;
                    } catch (Exception e){
                        throw e;
                    }},
                //ingredient is not an existing ingredient, also need to check uom already exist
                () ->{
                    try { //make sure uom already exists in DB
                        uomRepository.findById(ingredientCommand.getUom().getId()).orElseThrow();
                    } catch (NoSuchElementException noElement){
                        log.error("Unit of Measure not found");
                        throw noElement;
                    } catch (Exception e){
                        throw e;
                    }
                    recipe.addIngredient(commandToIngredient.convert(ingredientCommand));
                }
        );

        Recipe savedRecipe=recipeRepository.save(recipe);

        return ingredientToCommand.convert(
                savedRecipe.getIngredients().stream().filter(
                        ingredient -> ( ingredient.getRecipe().getId()==ingredientCommand.getRecipeId() &&
                                        ingredient.getDescription()==ingredientCommand.getDescription() &&
                                        ingredient.getAmount()==ingredientCommand.getAmount() &&
                                        ingredient.getUom().getId()==ingredientCommand.getUom().getId()))
                        .findFirst().get()
                );
    }
}
