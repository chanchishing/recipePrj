package guru.springframework.controllers;

import guru.springframework.model.Recipe;
import guru.springframework.service.RecipeServiceImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.Model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class IndexControllerTest {

    IndexController indexController;

    @Mock
    private RecipeServiceImpl recipeService;
    @Mock
    private Model model;
    private AutoCloseable closeable;

    @BeforeEach
    void setUp() {
        closeable= MockitoAnnotations.openMocks(this);
        indexController=new IndexController(recipeService);
    }

    @AfterEach
    void tearDown() throws Exception {
        closeable.close();
    }

    @Test
    void getIndexPage() {

        //given
        List<Recipe> recipes=new ArrayList<>();
        recipes.add(new Recipe());
        recipes.add(new Recipe());

        when(recipeService.getRecipeList()).thenReturn(recipes);

        ArgumentCaptor<List<Recipe>> argumentCaptor = ArgumentCaptor.forClass(List.class);

        //when
        String viewNameReturned=indexController.getIndexPage(model);

        //then a) verify "index" is returned from indexController.getIndexPage()
        assertEquals("index",viewNameReturned);

        //then b) check recipeService.getRecipeList() is called once
        verify(recipeService,times(1)).getRecipeList();

        //then c) check model.addAttribute("recipes",<List>) is called once and 2 recipes is added
        verify(model,times(1)).addAttribute(eq("recipes"),argumentCaptor.capture());
        List<Recipe> listInController = argumentCaptor.getValue();
        assertEquals(2,listInController.size());

    }
}