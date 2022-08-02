package com.daolin.cook.controller;


import com.daolin.cook.model.IngredientRepository;
import com.daolin.cook.model.Recipe;
import com.daolin.cook.service.IngredientService;
import com.daolin.cook.service.RecipeService;
import com.daolin.cook.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
public class MainController {
    @Autowired
    UserService userService;

    @Autowired
    RecipeService recipeService;

    @Autowired
    IngredientService ingredientService;

    @RequestMapping("/main/toMain")
    public String toMain(Model model){
        List<Recipe> allRecipes = recipeService.getAllRecipes();
        model.addAttribute("userNum", String.valueOf(userService.getNum()));
        model.addAttribute("recipeNum", String.valueOf(recipeService.getNum()));
        model.addAttribute("ingredientNum", String.valueOf(ingredientService.getNum()));
        model.addAttribute("allRecipes",allRecipes);
        return "index";
    }

}
