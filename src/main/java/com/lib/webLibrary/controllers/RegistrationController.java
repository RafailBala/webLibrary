package com.lib.webLibrary.controllers;

import com.lib.webLibrary.models.Role;
import com.lib.webLibrary.models.User;
import com.lib.webLibrary.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Collections;
import java.util.Map;

@Controller
public class RegistrationController {
    @Autowired
    private UserRepository userRepository;
    @GetMapping("/registration")
    public String registration( Model model) {
        model.addAttribute("title", "Регистрация");
        return "registration";
    }
    @PostMapping("/registration")
    public String addUser(User user, Map<String,Object> model){
     User userFromDataBase = userRepository.findByUsername(user.getUsername());
    if(userFromDataBase!=null){
        model.put("message", "Пользователь существует!");
        return "registration";
    }
    user.setActive(true);
    user.setRoles(Collections.singleton(Role.USER));
    userRepository.save(user);
    return "redirect:/login";
    }
}
