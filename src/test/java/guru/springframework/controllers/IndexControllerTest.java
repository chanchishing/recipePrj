package guru.springframework.controllers;

import guru.springframework.service.RecipeServiceImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.Model;

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

        //verify "index" is returned from indexController.getIndexPage()
        assertEquals("index",indexController.getIndexPage(model));

        //check recipeService.getRecipeList() is called once
        verify(recipeService,times(1)).getRecipeList();

        //check model.addAttribute("recipes",<List>) is called once
        verify(model,times(1)).addAttribute(eq("recipes"),anyList());
    }
}