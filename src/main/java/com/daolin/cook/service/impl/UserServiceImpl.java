package com.daolin.cook.service.impl;

import com.daolin.cook.model.*;
import com.daolin.cook.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.util.LambdaSafe;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;


@Service
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;

    @Override
    public User getUserByName(String name) {
        return userRepository.findByName(name);
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public void addUser(User user) {
        userRepository.save(user);
    }

    @Override
    public void deleteUserByName(String name) {
        userRepository.deleteByName(name);
    }

    @Override
    public void deleteAllUsers() {
        userRepository.deleteAll();
    }

    @Override
    public void saveUser(User user) {
        if (userRepository.findByName(user.getName()) == null) {
            userRepository.save(user);
        }
    }

    @Override
    public long getNum() {
        return userRepository.count();
    }


    @Override
    public void addFriendByUser(String follower, String followee) {
        User followerUser = userRepository.findByName(follower);
        User followeeUser = userRepository.findByName(followee);
        Set<User> followers = followeeUser.getFollowers();
        followers.add(followerUser);
        Set<User> following = followerUser.getFollowing();
        following.add(followeeUser);
        userRepository.save(followeeUser);
        userRepository.save(followerUser);
    }
}
