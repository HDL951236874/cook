package com.daolin.cook.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Entity
@Table(name = "recipe")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Recipe implements Serializable {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "rate")
    private Double rate;

    @Column(name = "price")
    private Double price;

    @Column(name = "calories")
    private Integer calories;

    @Lob
    @Column(name = "image", columnDefinition = "MEDIUMBLOB")
    private String image;

    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "recipe_ingredient", referencedColumnName = "name")
    private Set<Ingredient> ingredientList;

    @ManyToMany
    @JoinTable(
            name = "recipe_user",
            joinColumns = @JoinColumn(name = "recipe_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private Set<User> userListMarked = new HashSet<>();

    @ManyToMany
    @JoinTable(
            name = "recipe_user_rate",
            joinColumns = @JoinColumn(name = "recipe_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private Set<User> userRateList = new HashSet<>();

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name="listtoinstance")
    @MapKeyColumn(name="propKey")
    @Column(name="propValue")
    private Map<String,String> listToInstance;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Ingredient> getIngredientList() {
        return ingredientList;
    }

    public void setIngredientList(Set<Ingredient> ingredientList) {
        this.ingredientList = ingredientList;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getRate() {
        return rate;
    }


    public void setRate(Double rate) {
        this.rate = rate;
    }

    public Map<String, String> getListToInstance() {
        return listToInstance;
    }

    public void setListToInstance(Map<String, String> listToInstance) {
        this.listToInstance = listToInstance;
    }

    public Set<User> getUserListMarked() {
        return userListMarked;
    }

    public void setUserListMarked(Set<User> userListMarked) {
        this.userListMarked = userListMarked;
    }

    public Set<User> getUserRateList() {
        return userRateList;
    }

    public void setUserRateList(Set<User> userRateList) {
        this.userRateList = userRateList;
    }

}