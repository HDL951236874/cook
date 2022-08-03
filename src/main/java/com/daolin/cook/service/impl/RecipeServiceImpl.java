package com.daolin.cook.service.impl;

import com.daolin.cook.model.*;
import com.daolin.cook.service.RecipeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;


@Service
@Transactional
public class RecipeServiceImpl implements RecipeService {

    @Autowired
    RecipeRepository recipeRepository;

    @Autowired
    UserRepository userRepository;

    @Override
    public Recipe getRecipeByName(String name) {
        return recipeRepository.findByName(name);
    }

    @Override
    public List<Recipe> getAllRecipes() {
        return recipeRepository.findAll();
    }

    @Override
    public void markRecipeByUser(String recipeName, String name, String listName) {
        User user = userRepository.findByName(name);
        Set<Recipe> recipeMarkedList = user.getRecipeMarkedList();
        Recipe recipe = recipeRepository.findByName(recipeName);
        Set<User> userListMarked = recipe.getUserListMarked();
        Set<CostumeList> costumeLists = user.getCostumeLists();
        List<String> listNames = new ArrayList<>();
        costumeLists.forEach(x -> {
            listNames.add(x.getName());
        });
        if (!listNames.contains(listName)) return;
        if (recipeMarkedList.contains(recipe)) return;
        recipeMarkedList.add(recipe);
        userListMarked.add(user);
//        Map<String, String> listToInstance = user.getListToInstance();
//        listToInstance.put(recipe.getName(),listName);
        Map<String, String> listToInstance = recipe.getListToInstance();
        listToInstance.put(user.getName(), listName);
        user.setRecipeMarkedList(recipeMarkedList);
        recipe.setUserListMarked(userListMarked);
        recipe.setListToInstance(listToInstance);

        recipeRepository.save(recipe);
        userRepository.save(user);

    }

    @Override
    public void addRecipeByUser(Recipe recipe, String name) {
        //cannot add the ingredient with same name
        if (recipeRepository.findByName(recipe.getName()) == null) {
            recipeRepository.save(recipe);
        } else return;
        User user = userRepository.findByName(name);
        Set<Recipe> recipeList = user.getRecipeList();
        recipeList.add(recipe);
        //here should be a relation between the ingredient amd the recipe.
        user.setRecipeList(recipeList);

        userRepository.save(user);
    }

    @Override
    public void deleteRecipeByName(String name) {
        recipeRepository.deleteByName(name);
    }

    public void unmarkRecipeByUser(Recipe recipe, String name) {
        User user = userRepository.findByName(name);
        Set<Recipe> recipeMarkedList = user.getRecipeMarkedList();
        recipeMarkedList.remove(recipe);

        Set<User> userListMarked = recipe.getUserListMarked();
        userListMarked.remove(user);

        user.setRecipeMarkedList(recipeMarkedList);
        recipe.setUserListMarked(userListMarked);

        recipeRepository.save(recipe);
        userRepository.save(user);
    }

    @Override
    public void deleteAllRecipes() {
        recipeRepository.deleteAll();
    }

    @Override
    public long getNum() {
        return recipeRepository.count();
    }

    @Override
    public void rateRecipeByUser(String name, String recipeName, Integer score) {
        User user = userRepository.findByName(name);
        Recipe recipe = recipeRepository.findByName(recipeName);

        Set<Recipe> ratedList = user.getRatedList();
        Set<User> userRateList = recipe.getUserRateList();

        if(!(ratedList.contains(recipe) ||userRateList.contains(user))){
            ratedList.add(recipe);
            userRateList.add(user);
        }

        user.setRatedList(ratedList);
        recipe.setUserRateList(userRateList);

        Double rate = recipe.getRate();
        Double newRate = rate==null?(score)/(recipe.getUserRateList().size()):(score+rate)/(recipe.getUserRateList().size());
        recipe.setRate(newRate);

        recipeRepository.save(recipe);
        userRepository.save(user);
    }
}
