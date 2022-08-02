package com.daolin.cook.service;

import com.daolin.cook.model.Ingredient;
import com.daolin.cook.model.Recipe;
import com.daolin.cook.model.RecipeRepository;

import java.util.List;

public interface RecipeService {
    Recipe getRecipeByName(String name);

    List<Recipe> getAllRecipes();

    void markRecipeByUser(String recipeName, String name, String listName);

    void addRecipeByUser(Recipe recipe, String name);

    void deleteRecipeByName(String name);

    void unmarkRecipeByUser(Recipe recipe, String name);

    void deleteAllRecipes();

    long getNum();

    void rateRecipeByUser(String name, String recipeName, Integer score);
}
