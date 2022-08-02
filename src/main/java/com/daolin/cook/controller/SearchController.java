package com.daolin.cook.controller;

import com.daolin.cook.model.CostumeList;
import com.daolin.cook.model.Recipe;
import com.daolin.cook.model.RecipeRepository;
import com.daolin.cook.model.User;
import com.daolin.cook.service.AlgorithmService;
import com.daolin.cook.service.RecipeService;
import com.daolin.cook.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Controller
public class SearchController {

    @Autowired
    RecipeService recipeService;

    @Autowired
    AlgorithmService algorithmService;

    @Autowired
    UserService userService;

    @RequestMapping("/search/toSearch")
    public String toSearch() {
        return "users-search";
    }

    @RequestMapping("/search/searchResult")
//    @RequestMapping("/search/searchResult/{query}")
    public String search(@RequestParam("query") String query, Model model, HttpSession httpSession) {
//        System.out.println(query);
        List<Recipe> allRecipes = recipeService.getAllRecipes();
        ArrayList<Recipe> recipeMatch = new ArrayList<>();
        for (Recipe recipe : allRecipes) {
            if (algorithmService.notStringMatch(recipe.getName(), query)) {
                recipeMatch.add(recipe);
            }
        }
        String loginUser = (String) httpSession.getAttribute("loginUser");
        User user = userService.getUserByName(loginUser);
        List<CostumeList> costumeLists = new ArrayList<>(user.getCostumeLists());
        model.addAttribute("costumeLists", costumeLists);
        model.addAttribute("recipeMatch", recipeMatch);
        httpSession.setAttribute("keyword", query);
        return "users-search";
    }

    @RequestMapping("/search/showList")
    @ResponseBody
    public List<CostumeList> modalShow(Model model, HttpSession session) {
        String loginUser = (String) session.getAttribute("loginUser");
        User user = userService.getUserByName(loginUser);
        List<CostumeList> costumeLists = new ArrayList<>(user.getCostumeLists());
        model.addAttribute("costumeLists", costumeLists);
//        System.out.println("111111111111111111111111");
        return costumeLists;
    }

//    @RequestMapping("/search/showList2")
//    public String modalShow2(Model model, HttpSession session){
//        String loginUser = (String) session.getAttribute("loginUser");
//        User user = userService.getUserByName(loginUser);
//        List<CostumeList> costumeLists = new ArrayList<>(user.getCostumeLists());
//        model.addAttribute("costumeLists",costumeLists);
////        System.out.println("111111111111111111111111");
//        return "users-search";
//    }


    @RequestMapping("/search/mark/{name}")
    public String markToList(@PathVariable("name") String name, Model model, HttpSession session) {
//        System.out.println(name);
        String keyword = (String) session.getAttribute("keyword");
//        System.out.println(keyword);
        recipeService.markRecipeByUser((String) session.getAttribute("recipeSelected"),
                (String) session.getAttribute("loginUser"),
                name);
        session.removeAttribute("recipeSelected");
        search(keyword, model, session);
        return "users-search";
    }

    @RequestMapping("/search/save")
    @ResponseBody
    public void saveRecipe(@RequestParam("name") String name, HttpSession session) {
        session.setAttribute("recipeSelected", name);
    }
}
