package guru.springframework.service;

import guru.springframework.commands.IngredientCommand;
import guru.springframework.commands.RecipeCommand;
import guru.springframework.converters.IngredientToIngredientCommand;
import guru.springframework.converters.UnitOfMeasureToUnitOfMeasureCommand;
import guru.springframework.model.Ingredient;
import guru.springframework.model.Recipe;
import guru.springframework.repositories.RecipeRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class IngredientServiceImplTest {

    IngredientServiceImpl ingredientService;

    @Mock
    RecipeRepository mockRecipeRepository;


    IngredientToIngredientCommand IngredientToCommand=new IngredientToIngredientCommand(new UnitOfMeasureToUnitOfMeasureCommand());

    private AutoCloseable closeable;

    @BeforeEach
    void setUp() {
        closeable=MockitoAnnotations.openMocks(this);
        ingredientService= new IngredientServiceImpl(mockRecipeRepository, IngredientToCommand);
    }

    @AfterEach
    void tearDown() throws Exception{
        closeable.close();
    }

    @Test
    void findByRecipeIDAndIngredientId() {
        Long testRecipeId=1L;
        Long testIngredientId=2L;

        Recipe recipe=new Recipe();
        recipe.setId(testRecipeId);

        Ingredient ingredient=new Ingredient();
        ingredient.setId(testIngredientId);
        ingredient.setRecipe(recipe);
        recipe.getIngredients().add(ingredient);
        Optional<Recipe> recipeOptional=Optional.of(recipe);

        IngredientCommand ingredientCommand=new IngredientCommand();
        ingredientCommand.setRecipeId(testRecipeId);
        ingredientCommand.setId(testIngredientId);

        when(mockRecipeRepository.findById(any())).thenReturn(recipeOptional);

        IngredientCommand command=ingredientService.findByRecipeIDAndIngredientId(testRecipeId,testIngredientId);
        verify(mockRecipeRepository,times(1)).findById(testRecipeId);
        assertEquals(testRecipeId,command.getRecipeId());
        assertEquals(testIngredientId,command.getId());



    }
}