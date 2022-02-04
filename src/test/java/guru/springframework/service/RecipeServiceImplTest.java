package guru.springframework.service;

import guru.springframework.model.Recipe;
import guru.springframework.repositories.RecipeRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

//import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class RecipeServiceImplTest {

    RecipeServiceImpl recipeService;

    @Mock
    RecipeRepository recipeRepository;
    private AutoCloseable closeable;

    @BeforeEach
    void setUp() {
        closeable=MockitoAnnotations.openMocks(this);
        recipeService=new RecipeServiceImpl(recipeRepository);
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
}