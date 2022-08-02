package com.daolin.cook.model;


import com.daolin.cook.service.CostumeListService;
import com.daolin.cook.service.IngredientService;
import com.daolin.cook.service.RecipeService;
import com.daolin.cook.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

@SpringBootTest
public class AddRecipe {

    @Autowired
    UserService userService;
    @Autowired
    RecipeService recipeService;

    @Autowired
    IngredientService ingredientService;

    @Autowired
    CostumeListService costumeListService;

    @Test
    public void addAdmin() {
        User user1 = new User(null, "admin", "123", null, null, null, null, null);
        User user2 = new User(null, "ruihan", "123", null, null, null, null, null);
        User user3 = new User(null, "daolin", "123", null, null, null, null, null);

        userService.saveUser(user1);
        userService.saveUser(user2);
        userService.saveUser(user3);
    }

    @Test
    public void addPreparedRecipeAndIngredient() {
        User admin = userService.getUserByName("admin");
        Ingredient ingredient = new Ingredient(null, "potato", "potato");
        Ingredient ingredient1 = new Ingredient(null, "apple", "apple");
        Ingredient ingredient2 = new Ingredient(null, "banana", "banana");
        Ingredient ingredient3 = new Ingredient(null, "peach", "peach");
        Set<Ingredient> recipe_set1 = Arrays.stream(new Ingredient[]{ingredient}).collect(Collectors.toSet());
        Set<Ingredient> recipe_set2 = Arrays.stream(new Ingredient[]{ingredient1}).collect(Collectors.toSet());
        Set<Ingredient> recipe_set3 = Arrays.stream(new Ingredient[]{ingredient2}).collect(Collectors.toSet());
        Set<Ingredient> recipe_set4 = Arrays.stream(new Ingredient[]{ingredient3}).collect(Collectors.toSet());
        Recipe recipe = new Recipe(null, ingredient.getName() + " recipe", "This is the description for potato", null, null, null,null, recipe_set1, null, null, null);
        Recipe recipe1 = new Recipe(null, ingredient1.getName() + " recipe", "This is the description for tomato", null, null, null,null, recipe_set2, null, null, null);
        Recipe recipe2 = new Recipe(null, ingredient2.getName() + " recipe", "This is the description for apple", null, null, null,null, recipe_set3, null, null, null);
        Recipe recipe3 = new Recipe(null, ingredient3.getName() + " recipe", "This is the description for peach", null, null, null,null, recipe_set4, null, null, null);

        ingredientService.addIngredientByUser(ingredient, "Admin");
        ingredientService.addIngredientByUser(ingredient1, "Admin");
        ingredientService.addIngredientByUser(ingredient2, "Admin");
        ingredientService.addIngredientByUser(ingredient3, "Admin");

        recipeService.addRecipeByUser(recipe, "Admin");
        recipeService.addRecipeByUser(recipe1, "Admin");
        recipeService.addRecipeByUser(recipe2, "Admin");
        recipeService.addRecipeByUser(recipe3, "Admin");
    }

    @Test
    public void addListTest() {
        CostumeList costumeList = new CostumeList(null, "default", "This is the list to test the function of the backend!");
        costumeListService.addCostumeListByUser("daolin", costumeList);
        CostumeList costumeList1 = new CostumeList(null, "default", "This is the list to test the function of the backend!");
        costumeListService.addCostumeListByUser("ruihan", costumeList1);
        CostumeList costumeList2 = new CostumeList(null, "default", "This is the list to test the function of the backend!");
        costumeListService.addCostumeListByUser("admin", costumeList2);
    }

    @Test
    public void markTest() {
//        Recipe potato_recipe = recipeService.getRecipeByName("potato recipe");
        recipeService.markRecipeByUser("potato recipe", "ruihan", "default");
        recipeService.markRecipeByUser("potato recipe", "daolin", "default");
    }

    @Test
    public void testDelete() {
        recipeService.deleteRecipeByName("potato recipe");
    }


}
