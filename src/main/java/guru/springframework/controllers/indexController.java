package guru.springframework.controllers;


import guru.springframework.model.Recipe;
import guru.springframework.service.RecipeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Slf4j
@Controller
public class indexController {

    private final RecipeService recipeService;

    public indexController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @RequestMapping({"","/","/index"})
    public String getIndexPage(Model model){

        List<Recipe> recipeList=recipeService.getRecipeList();

        //System.out.println("Number of Recipe:" + recipeList.size());
        log.debug("Number of Recipe:" + recipeList.size());
        model.addAttribute("recipes",recipeList);

        return "index";
    }
}
