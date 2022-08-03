package com.daolin.cook.controller;

import com.daolin.cook.model.*;
import com.daolin.cook.service.AlgorithmService;
import com.daolin.cook.service.MessageService;
import com.daolin.cook.service.RecipeService;
import com.daolin.cook.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Controller
public class IndexController {

    @Autowired
    UserService userService;
    @Autowired
    RecipeService recipeService;
    @Autowired
    MessageService messageService;

    @Autowired
    AlgorithmService algorithmService;

    @RequestMapping("/message/toIndex")
    public String toIndex(Model model, HttpSession session) {
        String loginUser = (String) session.getAttribute("loginUser");
        User user = userService.getUserByName(loginUser);
        List<Message> messageList = new ArrayList<>(user.getMessageList());
        model.addAttribute("messageList", messageList);
        return "users-message";
    }

    @RequestMapping("/message/toDetail/{id}")
    public String toDetail(@PathVariable("id") String id, Model model) {
        Integer idL = Integer.valueOf(id);
        Message message = messageService.getServiceById(idL);
        model.addAttribute("message", message);
//        model.addAttribute("messageFrom",message.getName());
//        model
        return "message-detail";
    }

    @RequestMapping("/message/search/{name}")
    public String goSearch(@PathVariable("name") String query, HttpSession session, Model model) {
        List<Recipe> allRecipes = recipeService.getAllRecipes();
        ArrayList<Recipe> recipeMatch = new ArrayList<>();
        for (Recipe recipe : allRecipes) {
            if (algorithmService.notStringMatch(recipe.getName(), query)) {
                recipeMatch.add(recipe);
            }
        }
        String loginUser = (String) session.getAttribute("loginUser");
        User user = userService.getUserByName(loginUser);
        List<CostumeList> costumeLists = new ArrayList<>(user.getCostumeLists());
        model.addAttribute("costumeLists", costumeLists);
        model.addAttribute("recipeMatch", recipeMatch);
        session.setAttribute("keyword", query);
        return "users-search";

    }
}
