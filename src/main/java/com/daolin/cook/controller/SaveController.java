package com.daolin.cook.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

@Controller
public class SaveController {
    @RequestMapping("/search/save")
    @ResponseBody
    public void saveRecipe(@RequestParam("name") String name, HttpSession session) {
        session.setAttribute("recipeSelected", name);
    }
}
