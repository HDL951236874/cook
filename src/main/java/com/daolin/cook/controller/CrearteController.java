package com.daolin.cook.controller;

import com.daolin.cook.model.Ingredient;
import com.daolin.cook.model.Recipe;
import com.daolin.cook.model.User;
import com.daolin.cook.service.IngredientService;
import com.daolin.cook.service.RecipeService;
import com.daolin.cook.service.UserService;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Array;
import java.util.*;

@Controller
public class CrearteController {

    @Autowired
    UserService userService;

    @Autowired
    IngredientService ingredientService;

    @Autowired
    RecipeService recipeService;

    @RequestMapping("/create/toCreate")
    public String toCreate(HttpSession session, Model model) {
        String loginUser = (String) session.getAttribute("loginUser");
        User user = userService.getUserByName(loginUser);

        Set<Recipe> recipeList = user.getRecipeList();
        Set<Ingredient> ingredientList = user.getIngredientList();
        List<Recipe> array = new ArrayList<>(recipeList);
        List<Ingredient> array2 = new ArrayList<>(ingredientList);
        model.addAttribute("create", array);
        model.addAttribute("create2", array2);
        return "users-create";
    }

    @RequestMapping("/create/toAddPage")
    public String toAdd(Model model) {
        List<Ingredient> allIngredients = ingredientService.getAllIngredients();
        model.addAttribute("allIngredients", allIngredients);
        return "recipes-add";
    }

    @RequestMapping("/create/addIngredient")
    public String addIngredient(@RequestParam("name2") String ingredientName,
                                @RequestParam("description2") String ingredientDescription,
                                @RequestParam("textarea2") String ingredientDetails, HttpSession session) {
        Ingredient ingredient = new Ingredient(null, ingredientName, ingredientDescription + "\n" + ingredientDetails,null);
        ingredientService.addIngredientByUser(ingredient, (String) session.getAttribute("loginUser"));
        return "redirect:/create/toCreate";
    }

    @RequestMapping("/create/addRecipe")
    public String addRecipe(@RequestParam("name") String recipeName,
                            @RequestParam("description") String recipeDescription,
                            @RequestParam("ingredientList") String ingredientList,
                            @RequestParam("textarea1") String howToCook,
                            @RequestParam("price") String price,
                            @RequestParam("calories") String calories,
                            @RequestParam("file") MultipartFile file,
                            HttpSession session) {
        JSONArray jsonArray = new JSONArray(ingredientList);
        Set<Ingredient> ingredientSet = new HashSet<>();
        for (int i = 0; i < jsonArray.length(); i++) {
            ingredientSet.add(ingredientService.getIngredientByName((String) jsonArray.get(i)));
        }
        Recipe recipe = new Recipe(null, recipeName, recipeDescription + "\n" + howToCook, null, Double.valueOf(price), Integer.valueOf(calories), null, ingredientSet, null, null, null);
        if (!file.isEmpty()) {
            String fileName = StringUtils.cleanPath(file.getOriginalFilename());
            if (fileName.contains("..")) {
                System.out.println("this is not a valid file");
            }
            try {
                recipe.setImage(Base64.getEncoder().encodeToString(file.getBytes()));
            }catch (IOException e){e.printStackTrace();}
        }
        recipeService.addRecipeByUser(recipe, (String) session.getAttribute("loginUser"));
        return "redirect:/create/toCreate";
    }

    @RequestMapping("/create/deleteRecipe/{name}")
    public String deleteRecipe(@PathVariable("name") String name) {
        recipeService.deleteRecipeByName(name);
        return "redirect:/create/toCreate";
    }

    @RequestMapping("/create/deleteIngredient/{name}")
    public String deleteIngredient(@PathVariable("name") String name) {
        ingredientService.deleteIngredientByName(name);
        return "redirect:/create/toCreate";
    }

}
