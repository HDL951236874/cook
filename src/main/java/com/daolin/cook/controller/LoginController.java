package com.daolin.cook.controller;


import com.daolin.cook.model.User;
import com.daolin.cook.model.UserRepository;
import com.daolin.cook.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.HashSet;
import java.util.List;

@Controller
public class LoginController {
    @Autowired
    UserService userService;


    @RequestMapping("/user/login")
    public String login(@RequestParam("username") String username, @RequestParam("password") String password, Model model, HttpSession session) {
        List<User> allUsers = userService.getAllUsers();
        for (User user : allUsers) {
            if (user.getName().equals(username) && user.getPassword().equals(password)) {
                session.setAttribute("loginUser", username);
                return "redirect:/main/toMain";
            }
        }
        model.addAttribute("msg", "username or password is incorrect.");
        return "pages-login";
    }

    @RequestMapping("/user/register")
    public String toRegister() {
        return "pages-register";
    }


    @RequestMapping("/user/registerUser")
    public String registerUser(@RequestParam("email") String email,
                               @RequestParam("username") String username,
                               @RequestParam("password") String password,
                               Model model) {
        List<User> allUsers = userService.getAllUsers();
        for (User user : allUsers) {
            if (user.getName().equals(username)) {
                model.addAttribute("msg", "this user name has been used by other user.");
                return "pages-register";
            }
        }
        User user = new User(null, username, password, email, null,null, null, null,
                null, null, null, null);
        userService.saveUser(user);
        return "pages-login";
    }
}
