package com.daolin.cook.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

import com.daolin.cook.controller.SearchController;


@Controller
public class ShareController {
    @RequestMapping("/share/{name}")
    public String shareRecipe(@PathVariable("name") String name, Model model, HttpSession session) {
        String formerPage = (String) session.getAttribute("formerPage");
        String recipeName = (String) session.getAttribute("recipeSelected");
        String listName = (String) session.getAttribute("listName");
        //do the share operation
//        session.removeAttribute("recipeSelected");
        if ("create".equals(formerPage)) {
            return "redirect:/create/toCreate";
        }
        if ("list".equals(formerPage)) {
            return "redirect:/list/"+listName;
        }
        return "index";
    }
}
