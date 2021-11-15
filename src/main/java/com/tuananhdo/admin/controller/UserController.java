package com.tuananhdo.admin.controller;

import com.tuananhdo.entity.User;
import com.tuananhdo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/")
    public String listAllUsers(Model model) {
        List<User> listAllUsers = userService.listAllUsers();
        model.addAttribute("listUsers", listAllUsers);
        return "admin/home";
    }

    @GetMapping("/newUsers")
    public String createNewUsers(Model model) {
        model.addAttribute("user",new User());
        return "admin/user_form";
    }

    @PostMapping("/saveUser")
    public String saveUser(Model model, RedirectAttributes redirectAttributes,User user) {
        userService.save(user);
        model.addAttribute("users",user);
        redirectAttributes.addFlashAttribute("User has been saved successfully!");
        return "redirect:/home";
    }
}
