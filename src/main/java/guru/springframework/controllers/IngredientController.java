package guru.springframework.controllers;

import guru.springframework.commands.RecipeCommand;
import guru.springframework.service.RecipeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.test.web.servlet.MockMvc;

@Slf4j
@Controller
public class IngredientController {

    private final RecipeService recipeService;

    MockMvc mockMvc;
    String testIdStr;
    Long testIdLong;

    public IngredientController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @GetMapping
    @RequestMapping("/recipe/{id}/ingredients")
    public String listIngredients(@PathVariable String id, Model model){
        //RecipeCommand recipeCommand=recipeService.getRecipeCommandById(Long.valueOf(id));
        //model.addAttribute("recipe", recipeCommand);

        RecipeCommand recipeCommand=recipeService.getRecipeCommandById(Long.valueOf(id));
        model.addAttribute("recipe", recipeCommand);
        return "/recipe/ingredient/list";
    }
}
