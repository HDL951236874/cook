package com.daolin.cook.service;

import com.daolin.cook.model.Ingredient;
import com.daolin.cook.model.Recipe;

import java.util.List;

public interface IngredientService {
    Ingredient getIngredientByName(String name);

    List<Ingredient> getAllIngredients();

    void addIngredientByUser(Ingredient ingredient, String name);

    void deleteIngredientByName(String name);

    void deleteAllIngredients();

    long getNum();

}
