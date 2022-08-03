package com.daolin.cook.service;

import com.daolin.cook.model.Ingredient;
import com.daolin.cook.model.Recipe;
import com.daolin.cook.model.User;
import org.w3c.dom.ls.LSInput;

import java.util.List;

public interface UserService {

    User getUserByName(String name);

    List<User> getAllUsers();

    void addUser(User user);

    void deleteUserByName(String name);

    void deleteAllUsers();

    void saveUser(User user);

    long getNum();

    void addFriendByUser(String follower, String followee);

}
