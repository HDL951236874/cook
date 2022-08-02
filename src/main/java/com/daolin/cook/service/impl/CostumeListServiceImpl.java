package com.daolin.cook.service.impl;

import com.daolin.cook.model.*;
import com.daolin.cook.service.CostumeListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;


@Service
public class CostumeListServiceImpl implements CostumeListService {
    @Autowired
    UserRepository userRepository;

    @Autowired
    CostumeListRepository costumeListRepository;

    @Autowired
    RecipeRepository recipeRepository;
    @Override
    public void addCostumeListByUser(String name, CostumeList costumeList) {
        User user = userRepository.findByName(name);
        Set<CostumeList> costumeLists = user.getCostumeLists();
        for(CostumeList costumeList1: costumeLists){
            if (costumeList1.getName().equals(costumeList.getName())) return;
        }
        costumeListRepository.save(costumeList);
        costumeLists.add(costumeList);
        user.setCostumeLists(costumeLists);
        userRepository.save(user);
    }

    public void deleteListByUser(String name, String ListName){
        User user = userRepository.findByName(name);
        Set<CostumeList> costumeLists = user.getCostumeLists();
        CostumeList originalList = costumeListRepository.findByName(ListName);
        costumeLists.remove(originalList);
        Set<Recipe> recipeMarkedList = user.getRecipeMarkedList();
        Set<Recipe> newRecipeMarkedList = new HashSet<>();

        for(Recipe recipe: recipeMarkedList){
            Map<String,String> list2instance = recipe.getListToInstance();
            if(list2instance.get(name).equals(ListName)){
                list2instance.remove(name);
                Set<User> userListMarked = recipe.getUserListMarked();
                userListMarked.remove(user);
                recipe.setListToInstance(list2instance);
                recipe.setUserListMarked(userListMarked);
                recipeRepository.save(recipe);
            }else {
                newRecipeMarkedList.add(recipe);
            }
            user.setRecipeMarkedList(newRecipeMarkedList);
            user.setCostumeLists(costumeLists);
            userRepository.save(user);

            costumeListRepository.delete(originalList);
        }
    }
}
