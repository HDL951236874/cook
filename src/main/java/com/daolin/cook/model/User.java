package com.daolin.cook.model;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Entity
@Table(name = "user")
@AllArgsConstructor
@NoArgsConstructor
public class User implements Serializable {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @Column(name = "password", nullable = false)
    private String password;

    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_recipe", referencedColumnName = "name")
    private Set<Recipe> recipeList;

    @ManyToMany(mappedBy = "userListMarked", fetch = FetchType.EAGER)
    private Set<Recipe> recipeMarkedList;

    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_ingredient", referencedColumnName = "name")
    private Set<Ingredient> ingredientList;

    @ManyToMany(mappedBy = "userRateList", fetch = FetchType.EAGER)
    private Set<Recipe> ratedList;

    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_costumeList", referencedColumnName = "name")
    private Set<CostumeList> costumeLists;

//    @ElementCollection(fetch = FetchType.EAGER)
//    @CollectionTable(name="user_list")
//    @MapKeyColumn(name="propKey")
//    @Column(name="propValue")
//    private Map<String,String> listToInstance;


//    @ElementCollection(fetch = FetchType.EAGER)
//    @CollectionTable(name = "user_to_recipe",joinColumns = @JoinColumn(name = "user_id"))
//    private Set<String> recipeList;

    //    @ElementCollection(fetch = FetchType.EAGER)
//    @CollectionTable(name = "user_to_listList",joinColumns = @JoinColumn(name = "user_id"))


    public Set<Recipe> getRecipeMarkedList() {
        return recipeMarkedList;
    }

    public void setRecipeMarkedList(Set<Recipe> recipeMarkedList) {
        this.recipeMarkedList = recipeMarkedList;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Recipe> getRecipeList() {
        return recipeList;
    }

    public void setRecipeList(Set<Recipe> recipeList) {
        this.recipeList = recipeList;
    }

    public Set<Ingredient> getIngredientList() {
        return ingredientList;
    }

    public void setIngredientList(Set<Ingredient> ingredientList) {
        this.ingredientList = ingredientList;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<CostumeList> getCostumeLists() {
        return costumeLists;
    }

    public void setCostumeLists(Set<CostumeList> costumeLists) {
        this.costumeLists = costumeLists;
    }

    public Set<Recipe> getRatedList() {
        return ratedList;
    }

    public void setRatedList(Set<Recipe> ratedList) {
        this.ratedList = ratedList;
    }

}