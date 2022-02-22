package guru.springframework.controllers;

import guru.springframework.commands.IngredientCommand;
import guru.springframework.commands.RecipeCommand;
import guru.springframework.service.IngredientServiceImpl;
import guru.springframework.service.RecipeServiceImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.Model;


import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class IngredientControllerTest {


    IngredientController ingredientController;

    MockMvc mockMvc;
    String testIdStr,testIngredientIdStr;
    Long testIdLong,testIngredientIdLong;



    @Mock
    private RecipeServiceImpl mockRecipeService;

    @Mock
    private IngredientServiceImpl mockIngredientService;

    @Mock
    private Model mockModel;
    private AutoCloseable closeable;


    @BeforeEach
    void setUp() {
        closeable = MockitoAnnotations.openMocks(this);
        ingredientController = new IngredientController(mockRecipeService,mockIngredientService);

        testIdStr = "1";
        testIdLong = Long.valueOf(testIdStr);
        testIngredientIdStr = "2";
        testIngredientIdLong = Long.valueOf(testIngredientIdStr);

        mockMvc = MockMvcBuilders.standaloneSetup(ingredientController).build();

    }

    @AfterEach
    void tearDown() throws Exception {
        closeable.close();
    }

    @Test
    void listIngredients() throws Exception{
        RecipeCommand recipeCommand = new RecipeCommand();
        recipeCommand.setId(testIdLong);

        when(mockRecipeService.getRecipeCommandById(testIdLong)).thenReturn(recipeCommand);

        mockMvc.perform(get("/recipe/" + testIdStr + "/ingredients/"))
                .andExpect(status().isOk())
                .andExpect(view().name("/recipe/ingredient/list"))
                .andExpect(model().attributeExists("recipe"));


        verify(mockRecipeService, times(1)).getRecipeCommandById(testIdLong);
    }

    @Test
    void ShowIngredient() throws Exception {

        IngredientCommand ingredientCommand = new IngredientCommand();
        ingredientCommand.setId(testIngredientIdLong);
        ingredientCommand.setRecipeId(testIdLong);

        when(mockIngredientService.findByRecipeIDAndIngredientId(testIdLong,testIngredientIdLong)).thenReturn(ingredientCommand);

        mockMvc.perform(get("/recipe/" + testIdStr + "/ingredients/" + testIngredientIdStr + "/show"))
                .andExpect(status().isOk())
                .andExpect(view().name("/recipe/ingredient/show"))
                .andExpect(model().attributeExists("ingredient"));

        verify(mockIngredientService, times(1)).findByRecipeIDAndIngredientId(testIdLong,testIngredientIdLong);
    }
}