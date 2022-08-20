package com.daolin.cook.controller;


import com.daolin.cook.model.CostumeList;
import com.daolin.cook.model.Recipe;
import com.daolin.cook.model.User;
import com.daolin.cook.service.CostumeListService;
import com.daolin.cook.service.RecipeService;
import com.daolin.cook.service.UserService;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Controller
public class ListController {

    @Autowired
    UserService userService;

    @Autowired
    RecipeService recipeService;

    @Autowired
    CostumeListService costumeListService;

    @RequestMapping("/list/toList")
    public String toList(HttpSession httpSession, Model model,HttpSession session) {
        String loginUser = (String) httpSession.getAttribute("loginUser");
        User user = userService.getUserByName(loginUser);
        Set<CostumeList> costumeLists = user.getCostumeLists();
        List<CostumeList> costumeLists1 = new ArrayList<>(costumeLists);
        model.addAttribute("costumeList", costumeLists1);
        model.addAttribute("formerPage","list");
        session.setAttribute("formerPage", "create");

        return "users-userList";
    }

    @RequestMapping("/list/{name}")
    public String showList(@PathVariable("name") String name, HttpSession session, Model model) {
        String loginUser = (String) session.getAttribute("loginUser");
        User user = userService.getUserByName(loginUser);
        Set<Recipe> recipeMarkedList = user.getRecipeMarkedList();

        List<Recipe> recipeList = new ArrayList<>();
        for (Recipe recipe : recipeMarkedList) {
            if (recipe.getListToInstance().get(loginUser).equals(name)) {
                recipeList.add(recipe);
            }
        }
        model.addAttribute("recipes", recipeList);
        List<User> following = new ArrayList<>(user.getFollowing());
        session.setAttribute("listName", name);
        model.addAttribute("friendList",following);
        return "users-list";
    }

    @RequestMapping("/list/unmarkRecipe/{name}")
    public String unmarkRecipe(@PathVariable("name") String name, HttpSession session, Model model) {
        Recipe recipe = recipeService.getRecipeByName(name);
        String userName = (String) session.getAttribute("loginUser");
        recipeService.unmarkRecipeByUser(recipe, userName);
        return showList((String) session.getAttribute("listName"), session, model);
    }

    @RequestMapping("/list/addList")
    public String addList(@RequestParam("name") String name,
                          @RequestParam("description") String description,
                          HttpSession session) {
        costumeListService.addCostumeListByUser((String) session.getAttribute("loginUser"), new CostumeList(null, name, description));
        return "redirect:/list/toList";
    }

    @RequestMapping("/list/deleteList/{name}")
    public String deleteList(@PathVariable("name") String name, HttpSession session) {
        costumeListService.deleteListByUser((String) session.getAttribute("loginUser"), name);
        return "redirect:/list/toList";
    }

    @RequestMapping("/list/save")
    @ResponseBody
    public void saveRecipe(@RequestParam("name") String recipeName, HttpSession session) {
        System.out.println(recipeName);
        session.setAttribute("recipeForRate", recipeName);
    }

    @RequestMapping("/list/rate")
    public String rateList(HttpSession session, @RequestParam("score") String score, Model model) {
        String loginUser = (String) session.getAttribute("loginUser");
        String recipeName = (String) session.getAttribute("recipeForRate");
        System.out.println(recipeName);
        recipeService.rateRecipeByUser(loginUser, recipeName, Integer.valueOf(score));
        String listName = (String) session.getAttribute("listName");
        return showList(listName, session, model);
    }
}
