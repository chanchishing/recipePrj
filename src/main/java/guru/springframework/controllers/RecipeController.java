package guru.springframework.controllers;


import com.sun.istack.NotNull;
import guru.springframework.commands.IngredientCommand;
import guru.springframework.commands.RecipeCommand;
import guru.springframework.exceptions.NotFoundException;
import guru.springframework.model.Recipe;
import guru.springframework.service.RecipeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Slf4j
@Controller
public class RecipeController {

    private final RecipeService recipeService;

    public RecipeController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }


    @GetMapping("/recipe/{id}/show")
    public String getRecipePage(@PathVariable String id,Model model){

        log.debug("Inside RecipeController.getRecipePage()"+id);

        Recipe recipe=recipeService.getRecipe(Long.valueOf(id));
        model.addAttribute("recipe",recipe);

        return "/recipe/show";
    }


    @GetMapping("recipe/new")
    public String newRecipe(Model model) {
        model.addAttribute("recipe", new RecipeCommand());

        return "recipe/recipeform";

    }


    @PostMapping("recipe")
    public String saveOrUpdate(@ModelAttribute RecipeCommand command){
        RecipeCommand savedCommand=recipeService.saveRecipe(command);
        return("redirect:/recipe/"+savedCommand.getId()+"/show/");

    }


    @GetMapping("/recipe/{id}/update")
    public String updateRecipe(@PathVariable String id,Model model){
        RecipeCommand recipeCommand=recipeService.getRecipeCommandById(Long.valueOf(id));
        model.addAttribute("recipe", recipeCommand);
        return "/recipe/recipeform";
    }


    @GetMapping("/recipe/{id}/delete")
    public String deleteRecipe(@PathVariable String id){
        recipeService.deleteRecipeById(Long.valueOf(id));
        //model.addAttribute("recipe", recipeCommand);
        return "redirect:/";
    }

    //@ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NotFoundException.class)
    ModelAndView handleNotFoundError(Exception exception){
        ModelAndView modelAndView=new ModelAndView();

        log.error("Handling not found exception");

        modelAndView.setViewName("404error");
        modelAndView.addObject("exception", exception);
        modelAndView.setStatus(HttpStatus.NOT_FOUND);

        return modelAndView;
    }

//    @ResponseStatus(HttpStatus.BAD_REQUEST)
      @ExceptionHandler(NumberFormatException.class)
      ModelAndView handleNUmberFormatError(Exception exception){
        ModelAndView modelAndView=new ModelAndView();

        log.error("Handling number format exception");

        modelAndView.setViewName("400error");
        modelAndView.addObject("exception", exception);
        modelAndView.setStatus(HttpStatus.BAD_REQUEST);

        return modelAndView;
    }

}
