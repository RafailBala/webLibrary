package com.lib.webLibrary.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {
//@RequestParam(name="name", required=false, defaultValue="World") String name,
    @GetMapping("/home")
    public String home( Model model) {
        model.addAttribute("title", "Главная старница");
        return "home";
    }
    @GetMapping("/about")
    public String about( Model model) {
        model.addAttribute("title", "Страница про нас");
        return "about";
    }
    @GetMapping("/")
    public String hom( Model model) {
        model.addAttribute("title", "Начальная страница");
        return "start";
    }
}
