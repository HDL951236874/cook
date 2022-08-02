package com.daolin.cook.model;

import com.daolin.cook.model.Ingredient;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IngredientRepository extends JpaRepository<Ingredient, Long> {
    Ingredient findByName(String name);
    void deleteByName(String name);

}
