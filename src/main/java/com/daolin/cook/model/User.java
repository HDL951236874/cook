package com.daolin.cook.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.context.annotation.ComponentScan;

import javax.persistence.*;
import java.io.Serializable;
import java.util.*;

@Entity
@Table(name = "user")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class User implements Serializable {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "email")
    private String email;

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

    @ManyToMany(mappedBy = "following", cascade = CascadeType.ALL)
    private Set<User> followers = new HashSet<User>();

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "UserRel",
            joinColumns = {@JoinColumn(name = "UserId")},
            inverseJoinColumns = {@JoinColumn(name = "ParentId")})
    private Set<User> following = new HashSet<User>();

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

}