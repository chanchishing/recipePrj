package guru.springframework.bootstrap;


import guru.springframework.model.Difficulty;
import guru.springframework.model.Ingredient;
import guru.springframework.model.Recipe;
import guru.springframework.repositories.CategoryRepository;
import guru.springframework.repositories.RecipeRepository;
import guru.springframework.repositories.UnitOfMeasureRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;


@Component
public class BootStrapData implements CommandLineRunner {

    private final RecipeRepository recipeRepository;
    private final CategoryRepository categoryRepository;
    private final UnitOfMeasureRepository unitOfMeasureRepository;

    public BootStrapData(RecipeRepository recipeRepository, CategoryRepository categoryRepository, UnitOfMeasureRepository unitOfMeasureRepository) {
        this.recipeRepository = recipeRepository;
        this.categoryRepository = categoryRepository;
        this.unitOfMeasureRepository = unitOfMeasureRepository;
    }

    private Recipe initGuacamole() {

        Recipe guacamole = new Recipe();

        guacamole.setDescription("Prefect Guacamole");
        guacamole.setCookTime(10);
        guacamole.setDifficulty(Difficulty.EASY);
        guacamole.setPrepTime(10);
        guacamole.setServings(4);
        guacamole.setUrl("https://www.simplyrecipes.com/recipes/perfect_guacamole/");
        guacamole.getCategories().add(categoryRepository.findByDescription("Mexican").get());
        guacamole.getCategories().add(categoryRepository.findByDescription("American").get());



        Ingredient gIngredient1= new Ingredient();
        gIngredient1.setDescription("avocado");
        gIngredient1.setAmount(new BigDecimal("2.0"));
        gIngredient1.setUom(unitOfMeasureRepository.findByDescription("").get());
        gIngredient1.setRecipe(guacamole);
        guacamole.getIngredients().add(gIngredient1);

        Ingredient gIngredient2= new Ingredient();
        gIngredient2.setDescription("salt");
        gIngredient2.setAmount(new BigDecimal("0.5"));
        gIngredient2.setUom(unitOfMeasureRepository.findByDescription("Teaspoon").get());
        gIngredient2.setRecipe(guacamole);
        guacamole.getIngredients().add(gIngredient2);

        return guacamole;
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println("Started in Bootstrap");


        recipeRepository.save(initGuacamole());

        Recipe spicyGCTacos = new Recipe();
        spicyGCTacos.setDescription("Spicy Grilled Chicken Tacos");
        spicyGCTacos.setCookTime(15);
        spicyGCTacos.setDifficulty(Difficulty.KIND_OF_HARD);
        spicyGCTacos.setPrepTime(20);
        spicyGCTacos.setServings(6);
        spicyGCTacos.setUrl("https://www.simplyrecipes.com/recipes/spicy_grilled_chicken_tacos/");
        spicyGCTacos.getCategories().add(categoryRepository.findByDescription("Mexican").get());
        //spicyGCTacos.getCategories().add(categoryRepository.findByDescription("American").get());

        Ingredient sGCTIngredient1= new Ingredient();
        sGCTIngredient1.setDescription("ancho chili powder");
        sGCTIngredient1.setAmount(new BigDecimal(2));
        sGCTIngredient1.setUom(unitOfMeasureRepository.findByDescription("Tablespoon").get());
        sGCTIngredient1.setRecipe(spicyGCTacos);
        spicyGCTacos.getIngredients().add(sGCTIngredient1);

        Ingredient sGCTIngredient2= new Ingredient();
        sGCTIngredient2.setDescription("dried oregano");
        sGCTIngredient2.setAmount(new BigDecimal(1));
        sGCTIngredient2.setUom(unitOfMeasureRepository.findByDescription("Teaspoon").get());
        sGCTIngredient2.setRecipe(spicyGCTacos);
        spicyGCTacos.getIngredients().add(sGCTIngredient2);

        recipeRepository.save(spicyGCTacos);



    }
}
