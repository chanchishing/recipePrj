package guru.springframework.controllers;


import guru.springframework.commands.RecipeCommand;
import guru.springframework.model.Recipe;
import guru.springframework.service.RecipeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@Slf4j
@Controller
public class RecipeController {

    private final RecipeService recipeService;

    public RecipeController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @GetMapping
    @RequestMapping("/recipe/{id}/show")
    public String getRecipePage(@PathVariable String id,Model model){

        log.debug("Inside RecipeController.getRecipePage()"+id);

        Recipe recipe=recipeService.getRecipe(Long.valueOf(id));
        model.addAttribute("recipe",recipe);

        return "/recipe/show";
    }

    @GetMapping
    @RequestMapping("recipe/new")
    public String newRecipe(Model model) {
        model.addAttribute("recipe", new RecipeCommand());

        return "recipe/recipeform";

    }

    @PostMapping
    @RequestMapping("recipe")
    public String saveOrUpdate(@ModelAttribute RecipeCommand command){
        RecipeCommand savedCommand=recipeService.saveRecipe(command);
        return("redirect:/recipe/"+savedCommand.getId()+"/show/");

    }

    @GetMapping
    @RequestMapping("/recipe/{id}/update")
    public String updateRecipe(@PathVariable String id,Model model){
        RecipeCommand recipeCommand=recipeService.getRecipeCommandById(Long.valueOf(id));
        model.addAttribute("recipe", recipeCommand);
        return "/recipe/recipeform";
    }

    @GetMapping
    @RequestMapping("/recipe/{id}/delete")
    public String deleteRecipe(@PathVariable String id){
        recipeService.deleteRecipeById(Long.valueOf(id));
        //model.addAttribute("recipe", recipeCommand);
        return "redirect:/";
    }

}
