package guru.springframework.service;

import guru.springframework.model.Recipe;
import guru.springframework.repositories.RecipeRepository;
import org.springframework.stereotype.Service;


import java.util.List;

@Service
public class RecipeServiceImpl implements RecipeService {
    private final RecipeRepository recipeRepository;

    public RecipeServiceImpl(RecipeRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
    }

    @Override
    public List<Recipe> getRecipeList(){
        return (List<Recipe>) recipeRepository.findAll();
    }
}
