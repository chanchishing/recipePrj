package guru.springframework.bootstrap;


import guru.springframework.model.Difficulty;
import guru.springframework.model.Ingredient;
import guru.springframework.model.Recipe;
import guru.springframework.model.UnitOfMeasure;
import guru.springframework.repositories.CategoryRepository;
import guru.springframework.repositories.RecipeRepository;
import guru.springframework.repositories.UnitOfMeasureRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;


@Component
public class BootStrapData implements CommandLineRunner {

    private final RecipeRepository recipeRepository;
    private final CategoryRepository categoryRepository;
    private final UnitOfMeasureRepository unitOfMeasureRepository;

    private UnitOfMeasure count;
    private UnitOfMeasure teaspoon;
    private UnitOfMeasure tablespoon;
    private UnitOfMeasure pinch;

    public BootStrapData(RecipeRepository recipeRepository, CategoryRepository categoryRepository, UnitOfMeasureRepository unitOfMeasureRepository) {
        this.recipeRepository = recipeRepository;
        this.categoryRepository = categoryRepository;
        this.unitOfMeasureRepository = unitOfMeasureRepository;

    }

    private void initUOMs() {
        this.count=unitOfMeasureRepository.findByDescription("").get();
        this.teaspoon=unitOfMeasureRepository.findByDescription("Teaspoon").get();
        this.tablespoon=unitOfMeasureRepository.findByDescription("Tablespoon").get();
        this.pinch=unitOfMeasureRepository.findByDescription("Pinch").get();

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

        Set<Ingredient> ingredients = new HashSet<Ingredient>() {{
            add(new Ingredient("avocado",new BigDecimal("2.0"),count,guacamole));
            add(new Ingredient("salt",new BigDecimal("0.25"),teaspoon,guacamole));
            add(new Ingredient("fresh lime or lemon juice",new BigDecimal("1.0"),tablespoon,guacamole));
            add(new Ingredient("minced red onion or thinly sliced green onion",new BigDecimal("4.0"),tablespoon,guacamole));
            add(new Ingredient("serrano chilis, stems and seeds removed, minced",new BigDecimal("2.0"),count,guacamole));
            add(new Ingredient("cilantro,finely chopped",new BigDecimal("2.0"),tablespoon,guacamole));
            add(new Ingredient("freshly ground black pepper",new BigDecimal("1.0"),pinch,guacamole));
            add(new Ingredient("ripe tomato, chopped",new BigDecimal("0.5"),count,guacamole));
            add(new Ingredient("Red radish or jicama slices",BigDecimal.ZERO,count,guacamole));
            add(new Ingredient("Tortilla chips",BigDecimal.ZERO,count,guacamole));

        }};

        guacamole.setIngredients(ingredients);

        return guacamole;
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println("Started in Bootstrap");

        initUOMs();
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

        //Ingredient sGCTIngredient1= new Ingredient();
        //sGCTIngredient1.setDescription("ancho chili powder");
        //sGCTIngredient1.setAmount(new BigDecimal(2));
        //sGCTIngredient1.setUom(unitOfMeasureRepository.findByDescription("Tablespoon").get());
        //sGCTIngredient1.setRecipe(spicyGCTacos);
        //spicyGCTacos.getIngredients().add(sGCTIngredient1);

        //Ingredient sGCTIngredient2= new Ingredient();
        //sGCTIngredient2.setDescription("dried oregano");
        //sGCTIngredient2.setAmount(new BigDecimal(1));
        //sGCTIngredient2.setUom(unitOfMeasureRepository.findByDescription("Teaspoon").get());
        //sGCTIngredient2.setRecipe(spicyGCTacos);
        //spicyGCTacos.getIngredients().add(sGCTIngredient2);

        recipeRepository.save(spicyGCTacos);



    }
}
