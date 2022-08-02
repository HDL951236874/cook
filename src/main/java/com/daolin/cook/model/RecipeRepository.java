package com.daolin.cook.model;

import com.daolin.cook.model.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RecipeRepository extends JpaRepository<Recipe, Long> {
    Recipe findByName(String name);

//    @Query(value = "select * from recipe where user_recipe = ?1", nativeQuery = true)
//    List<Recipe> findRecipesByName(String name);
    void deleteByName(String name);
}
