package guru.springframework.service;

import guru.springframework.converters.RecipeCommandToRecipe;
import guru.springframework.converters.RecipeToRecipeCommand;
import guru.springframework.model.Recipe;
import guru.springframework.repositories.RecipeRepository;
import org.h2.command.Command;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

//import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class RecipeServiceImplTest {

    RecipeServiceImpl recipeService;

    @Mock
    RecipeRepository recipeRepository;

    @Mock
    RecipeCommandToRecipe commandToRecipe;

    @Mock
    RecipeToRecipeCommand recipeToCommand;

    private AutoCloseable closeable;

    @BeforeEach
    void setUp() {
        closeable=MockitoAnnotations.openMocks(this);
        recipeService= new RecipeServiceImpl(recipeRepository,
                recipeToCommand,
                commandToRecipe);
    }

    @AfterEach
    void tearDown() throws Exception{
        closeable.close();
    }

    @Test
    public void getRecipeList() {
        Recipe dummyRecipe= new Recipe();
        List<Recipe> dummyRecipeList= new ArrayList<>();
        dummyRecipeList.add(dummyRecipe);


        when(recipeRepository.findAll()).thenReturn(dummyRecipeList);

        List<Recipe> recipeList=recipeService.getRecipeList();
        assertEquals(1,recipeList.size());
        verify(recipeRepository,times(1)).findAll();
    }

    @Test
    void getRecipe() {
        Long testId=1L;

        Recipe recipe= new Recipe();
        recipe.setId(testId);
        Optional<Recipe> optionalRecipe=Optional.of(recipe);

        when(recipeRepository.findById(anyLong())).thenReturn(optionalRecipe);

        Optional<Recipe> resultRecipe=recipeRepository.findById(testId);

        assertEquals(testId,optionalRecipe.get().getId());
        assertNotNull(optionalRecipe);
        verify(recipeRepository,times(1)).findById(testId);


    }
}