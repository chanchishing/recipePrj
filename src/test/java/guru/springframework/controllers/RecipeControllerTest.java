package guru.springframework.controllers;

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


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class RecipeControllerTest {

    RecipeController recipeController;

    @Mock
    private RecipeServiceImpl recipeService;
    @Mock
    private Model model;
    private AutoCloseable closeable;

    String testIdStr;
    Long testIdLong;

    @BeforeEach
    void setUp() {
        closeable= MockitoAnnotations.openMocks(this);
        recipeController=new RecipeController(recipeService);

        testIdStr="1";
        testIdLong=Long.valueOf(testIdStr);
    }

    @AfterEach
    void tearDown() throws Exception {
        closeable.close();
    }

    @Test
    void getRecipePage() {

        Recipe recipe=new Recipe();
        recipe.setId(testIdLong);

        when(recipeService.getRecipe(testIdLong)).thenReturn(recipe);
        ArgumentCaptor<Recipe> argumentCaptor = ArgumentCaptor.forClass(Recipe.class);

        //when
        String viewNameReturned=recipeController.getRecipePage(testIdStr,model);

        //then
        assertEquals("/recipe/show",viewNameReturned);
        verify(recipeService,times(1)).getRecipe(testIdLong);
        verify(model,times(1)).addAttribute(eq("recipe"),argumentCaptor.capture());
        Recipe returnedRecipe=argumentCaptor.getValue();
        assertEquals(testIdLong,returnedRecipe.getId());

    }

    @Test
    void mvcTest() throws Exception{
        Recipe recipe=new Recipe();
        recipe.setId(testIdLong);

        when(recipeService.getRecipe(testIdLong)).thenReturn(recipe);


        MockMvc mockMvc= MockMvcBuilders.standaloneSetup(recipeController).build();
        mockMvc.perform(get("/recipe/show/"+testIdStr))
                .andExpect(status().isOk())
                .andExpect(view().name("/recipe/show"))
                .andExpect(model().attributeExists("recipe"));
    }
}