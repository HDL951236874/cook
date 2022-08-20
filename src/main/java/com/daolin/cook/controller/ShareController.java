package com.daolin.cook.controller;

import com.daolin.cook.model.Ingredient;
import com.daolin.cook.model.Message;
import com.daolin.cook.model.Recipe;
import com.daolin.cook.model.User;
import com.daolin.cook.service.MessageService;
import com.daolin.cook.service.RecipeService;
import com.daolin.cook.service.UserService;
import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

import com.daolin.cook.controller.SearchController;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashSet;
import java.util.Set;


@Controller
public class ShareController {

    @Autowired
    UserService userService;

    @Autowired
    RecipeService recipeService;


    @Autowired
    MessageService messageService;

    @ResponseBody
    @RequestMapping("/share/saveFriend")
    public void saveFriend(@RequestParam("name") String name, HttpSession httpSession){
        httpSession.setAttribute("friendName",name);
    }

    @RequestMapping("/shareMessage")
    public String shareMessage(@RequestParam("name")String name,
                             @RequestParam("description")String description,
                             HttpSession httpSession){
        String friendName = (String)httpSession.getAttribute("friendName");
        String loginUser = (String)httpSession.getAttribute("loginUser");
        User user = userService.getUserByName(loginUser);
        messageService.sendMessageByUser(friendName, new Message(null, user.getName(), user.getEmail(), name, description));
        return "redirect:/friend/tofriend";
    }

    @RequestMapping("/share")
    public String shareRecipe(@RequestParam("name") String name,
                              @RequestParam("description") String description,
                              @RequestParam("friendList") String friendList,
                              Model model, HttpSession session) {
        String formerPage = (String) session.getAttribute("formerPage");
        String recipeName = (String) session.getAttribute("recipeSelected");
        String listName = (String) session.getAttribute("listName");
        //this part is to build the message
        JSONArray jsonArray = new JSONArray(friendList);
        String loginUser = (String) session.getAttribute("loginUser");
        Recipe recipe = recipeService.getRecipeByName(recipeName);
        User user = userService.getUserByName(loginUser);
        for (int i = 0; i < jsonArray.length(); i++) {
            String userName = (String) jsonArray.get(i);
            //name, email, title, description, recipe
            Message message = new Message(null, user.getName(), user.getEmail(), name, description, recipe);
            messageService.sendMessageByUser(userName,message);
        }
        System.out.println(formerPage);
        if ("create".equals(formerPage)) {
            return "redirect:/create/toCreate";
        }
        if ("list".equals(formerPage)) {
            return "redirect:/list/" + listName;
        }
        return "index";
    }
}
