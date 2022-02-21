package guru.springframework.controllers;

import guru.springframework.commands.RecipeCommand;
import guru.springframework.service.RecipeServiceImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.Model;


import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class IngredientControllerTest {


    IngredientController ingredientController;

    MockMvc mockMvc;
    String testIdStr;
    Long testIdLong;

    @Mock
    private RecipeServiceImpl mockRecipeService;
    @Mock
    private Model mockModel;
    private AutoCloseable closeable;


    @BeforeEach
    void setUp() {
        closeable = MockitoAnnotations.openMocks(this);
        ingredientController = new IngredientController(mockRecipeService);

        testIdStr = "1";
        testIdLong = Long.valueOf(testIdStr);
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
}