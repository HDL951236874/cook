package com.daolin.cook.controller;

import com.daolin.cook.model.User;
import com.daolin.cook.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.HttpMediaTypeException;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Controller
public class FriendController {

    @Autowired
    UserService userService;


    @RequestMapping("/friend/tofriend")
    public String toFriend(HttpSession httpSession, Model model) {
        String loginUser = (String) httpSession.getAttribute("loginUser");
        List<User> allUsers = userService.getAllUsers();
        User user = userService.getUserByName(loginUser);
        Set<User> following = user.getFollowing();
        ArrayList<User> newUser = new ArrayList<>();
        for (User user1 : allUsers) {
            if (user1.getName().equals(loginUser) || following.contains(user1)) {
                continue;
            }
            newUser.add(user1);
        }
        model.addAttribute("userList", newUser);
        model.addAttribute("friendList", following);
        return "users-friend";
    }

    @RequestMapping("/friend/addfriend/{name}")
    public String addFriend(@PathVariable("name") String name, HttpSession httpSession) {
        String loginUser = (String) httpSession.getAttribute("loginUser");
        userService.addFriendByUser(loginUser, name);
        return "redirect:/friend/tofriend";
    }

}
