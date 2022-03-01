package guru.springframework.controllers;

import guru.springframework.commands.IngredientCommand;
import guru.springframework.commands.RecipeCommand;
import guru.springframework.commands.UnitOfMeasureCommand;
import guru.springframework.model.Recipe;
import guru.springframework.service.IngredientServiceImpl;
import guru.springframework.service.RecipeServiceImpl;
import guru.springframework.service.UnitOfMeasureService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.Model;


import java.util.HashSet;
import java.util.Set;

import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.hasSize;


import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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
    private UnitOfMeasureService mockUnitOfMeasureService;

    @Mock
    private Model mockModel;
    private AutoCloseable closeable;


    @BeforeEach
    void setUp() {
        closeable = MockitoAnnotations.openMocks(this);
        ingredientController = new IngredientController(mockRecipeService,mockIngredientService,mockUnitOfMeasureService);

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

    @Test
    void loadIngredientToUpdate() throws Exception {
        Long uom1TestId = 9L;
        Long uom2TestId = 10L;

        IngredientCommand ingredientCommand = new IngredientCommand();
        ingredientCommand.setId(testIngredientIdLong);
        ingredientCommand.setRecipeId(testIdLong);

        UnitOfMeasureCommand uomCommand1 = new UnitOfMeasureCommand();
        uomCommand1.setId(uom1TestId);
        UnitOfMeasureCommand uomCommand2 = new UnitOfMeasureCommand();
        uomCommand2.setId(uom2TestId);
        Set<UnitOfMeasureCommand> uomTestList = new HashSet<>();
        uomTestList.add(uomCommand1);
        uomTestList.add(uomCommand2);


        when(mockIngredientService.findByRecipeIDAndIngredientId(testIdLong, testIngredientIdLong)).thenReturn(ingredientCommand);
        when(mockUnitOfMeasureService.getUomList()).thenReturn(uomTestList);


        mockMvc.perform(get("/recipe/" + testIdStr + "/ingredients/" + testIngredientIdStr + "/update"))
                .andExpect(status().isOk())
                .andExpect(view().name("/recipe/ingredient/ingredientForm"))
                .andExpect(model().attributeExists("ingredient"))
                .andExpect(model().attributeExists("uomList"));

        verify(mockIngredientService, times(1)).findByRecipeIDAndIngredientId(testIdLong, testIngredientIdLong);
        verify(mockUnitOfMeasureService, times(1)).getUomList();

    }

    @Test
    void updateIngredient() throws Exception {

        IngredientCommand mockSavedIngredient=new IngredientCommand();
        mockSavedIngredient.setRecipeId(testIdLong);
        mockSavedIngredient.setId(testIngredientIdLong);

        when(mockIngredientService.saveIngredient(any(IngredientCommand.class))).thenReturn(mockSavedIngredient);

        mockMvc.perform(post("/recipe/" + testIdStr + "/ingredient"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/recipe/" + testIdStr + "/ingredients/" + testIngredientIdStr + "/show"));


    }

    @Test
    void loadIngredientFormToAdd() throws Exception {
        Long testRecipeId=1L;
        Long uom1TestId = 9L;
        Long uom2TestId = 10L;

        UnitOfMeasureCommand uomCommand1 = new UnitOfMeasureCommand();
        uomCommand1.setId(uom1TestId);
        UnitOfMeasureCommand uomCommand2 = new UnitOfMeasureCommand();
        uomCommand2.setId(uom2TestId);
        Set<UnitOfMeasureCommand> uomTestList = new HashSet<>();
        uomTestList.add(uomCommand1);
        uomTestList.add(uomCommand2);

        Recipe mockRecipe=new Recipe();
        mockRecipe.setId(testRecipeId);

        when(mockUnitOfMeasureService.getUomList()).thenReturn(uomTestList);
        when(mockRecipeService.getRecipe(anyLong())).thenReturn(mockRecipe);

        mockMvc.perform(get("/recipe/" + testIdStr + "/ingredients/new"))
                .andExpect(status().isOk())
                .andExpect(view().name("/recipe/ingredient/ingredientForm"))
                .andExpect(model().attributeExists("ingredient"))
                .andExpect(model().attributeExists("uomList"))
                .andExpect(model().attribute("ingredient",hasProperty("recipeId",is(testIdLong))))
                .andExpect(model().attribute("uomList",hasSize(uomTestList.size())));


    }

    @Test
    void deleteIngredient() throws Exception {
        Long testRecipeId=1L;
        Long testIngredientId = 9L;

        mockMvc.perform(get("/recipe/" + String.valueOf(testRecipeId) + "/ingredients/"+ String.valueOf(testIngredientId)+"/delete"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/recipe/" + String.valueOf(testRecipeId) + "/ingredients/"));

        verify(mockIngredientService,times(1)).deleteAnIngredient(testRecipeId,testIngredientId);
    }
}