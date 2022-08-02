package com.daolin.cook.controller;

import com.daolin.cook.model.Recipe;
import com.daolin.cook.service.RecipeService;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class InformationController {
    //this class is used to show the modal. and every time the user click the A label in the list
    //it will go into this controller
    @Autowired
    RecipeService recipeService;
    @RequestMapping("/showdetails")
    @ResponseBody
    public String showInformation(@RequestParam("name") String name){
        JSONArray jsonArray = new JSONArray();
        Recipe recipe = recipeService.getRecipeByName(name);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("name",recipe.getName());
        jsonObject.put("description",recipe.getDescription());
        jsonObject.put("rate",recipe.getRate());
        jsonObject.put("price",recipe.getPrice());
        jsonObject.put("calories",recipe.getCalories());
        jsonArray.put(jsonObject);
        return jsonArray.toString();
    }
}
