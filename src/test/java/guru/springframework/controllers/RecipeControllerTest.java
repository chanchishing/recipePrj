package guru.springframework.controllers;

import guru.springframework.commands.RecipeCommand;
import guru.springframework.model.Recipe;
import guru.springframework.service.RecipeServiceImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.Model;


import static org.hamcrest.Matchers.instanceOf;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class RecipeControllerTest {

    RecipeController recipeController;

    @Mock
    private RecipeServiceImpl mockRecipeService;
    @Mock
    private Model mockModel;
    private AutoCloseable closeable;

    MockMvc mockMvc;
    String testIdStr;
    Long testIdLong;

    @BeforeEach
    void setUp() {
        closeable = MockitoAnnotations.openMocks(this);
        recipeController = new RecipeController(mockRecipeService);

        testIdStr = "1";
        testIdLong = Long.valueOf(testIdStr);
        mockMvc = MockMvcBuilders.standaloneSetup(recipeController).build();

    }

    @AfterEach
    void tearDown() throws Exception {
        closeable.close();
    }

    @Test
    void getRecipePage() {

        Recipe recipe = new Recipe();
        recipe.setId(testIdLong);

        when(mockRecipeService.getRecipe(testIdLong)).thenReturn(recipe);
        ArgumentCaptor<Recipe> argumentCaptor = ArgumentCaptor.forClass(Recipe.class);

        //when
        String viewNameReturned = recipeController.getRecipePage(testIdStr, mockModel);

        //then
        assertEquals("/recipe/show", viewNameReturned);
        verify(mockRecipeService, times(1)).getRecipe(testIdLong);
        verify(mockModel, times(1)).addAttribute(eq("recipe"), argumentCaptor.capture());
        Recipe returnedRecipe = argumentCaptor.getValue();
        assertEquals(testIdLong, returnedRecipe.getId());

    }

    @Test
    void mvcTest() throws Exception {
        Recipe recipe = new Recipe();
        recipe.setId(testIdLong);

        when(mockRecipeService.getRecipe(testIdLong)).thenReturn(recipe);

        mockMvc.perform(get("/recipe/" + testIdStr + "/show/"))
                .andExpect(status().isOk())
                .andExpect(view().name("/recipe/show"))
                .andExpect(model().attributeExists("recipe"));
    }

    @Test
    void testNewRecipeFormLoad() throws Exception {

        mockMvc.perform(get("/recipe/new"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("recipe"))
                .andExpect(model().attribute("recipe", instanceOf(RecipeCommand.class)))
                .andExpect(view().name("recipe/recipeform"));

    }

    @Test
    void testPostNewRecipeForm() throws Exception {


        RecipeCommand outputCommand = new RecipeCommand();
        outputCommand.setId(testIdLong);

        when(mockRecipeService.saveRecipe(any())).thenReturn(outputCommand);

        mockMvc.perform(post("/recipe")
//                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
//                        .param("id",testIdStr)
//                        .param("description","new description")
                )
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/recipe/" + testIdStr + "/show/"));


    }

    @Test
    void testUpdateContextPath() throws Exception {
        RecipeCommand recipeCommand = new RecipeCommand();
        recipeCommand.setId(testIdLong);

        when(mockRecipeService.getRecipeCommandById(testIdLong)).thenReturn(recipeCommand);

        mockMvc.perform(get("/recipe/" + testIdStr + "/update/"))
                .andExpect(status().isOk())
                .andExpect(view().name("/recipe/recipeform"))
                .andExpect(model().attributeExists("recipe"));

    }
}