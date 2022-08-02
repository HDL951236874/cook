package com.daolin.cook.service.impl;

import com.daolin.cook.model.Ingredient;
import com.daolin.cook.model.IngredientRepository;
import com.daolin.cook.model.User;
import com.daolin.cook.model.UserRepository;
import com.daolin.cook.service.IngredientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Transactional
@Service
public class IngredientServiceImpl implements IngredientService {
    @Autowired
    IngredientRepository ingredientRepository;

    @Autowired
    UserRepository userRepository;

    @Override
    public Ingredient getIngredientByName(String name) {
        return ingredientRepository.findByName(name);
    }

    @Override
    public List<Ingredient> getAllIngredients() {
        return ingredientRepository.findAll();
    }


    @Override
    public void addIngredientByUser(Ingredient ingredient, String name) {
        //cannot add the ingredient with same name
        if(ingredientRepository.findByName(ingredient.getName())==null){
            ingredientRepository.save(ingredient);
        }else return;
        User user = userRepository.findByName(name);
        Set<Ingredient> ingredientList = user.getIngredientList();
        ingredientList.add(ingredient);

        user.setIngredientList(ingredientList);

        userRepository.save(user);
    }

    @Override
    public void deleteIngredientByName(String name) {
        ingredientRepository.deleteByName(name);
    }

    @Override
    public void deleteAllIngredients() {
        ingredientRepository.deleteAll();
    }

    @Override
    public long getNum() {
        return ingredientRepository.count();
    }
}
