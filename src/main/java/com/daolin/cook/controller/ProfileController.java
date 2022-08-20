package com.daolin.cook.controller;

import com.daolin.cook.model.User;
import com.daolin.cook.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;

@Controller
public class ProfileController {

    @Autowired
    UserService userService;

    @RequestMapping("/profile/toProfile")
    public String toProfile(Model model, HttpSession httpSession) {
        Object userName = httpSession.getAttribute("loginUser");

        User user = userService.getUserByName((String) userName);
        model.addAttribute("user", user);
        return "users-profile";
    }

    @RequestMapping("/profile/update")
    public String updateProfile(HttpSession session, @RequestParam("email") String email) {
        String loginUser = (String) session.getAttribute("loginUser");
        User user = userService.getUserByName(loginUser);
        user.setEmail(email);
        userService.saveUser(user);
        return "redirect:/profile/toProfile";
    }


}
