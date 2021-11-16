package com.tuananhdo.admin.controller;

import com.tuananhdo.admin.error.UserNotFoudException;
import com.tuananhdo.entity.Role;
import com.tuananhdo.entity.User;
import com.tuananhdo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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

    @GetMapping("/users/new")
    public String createNewUsers(Model model) {
        List<Role> listAllRoles = userService.listAllRoles();
        model.addAttribute("users", new User());
        model.addAttribute("listRoles", listAllRoles);
        model.addAttribute("pageTitle", "Create New User");
        return "admin/user_form";
    }

    @PostMapping("/users/save")
    public String saveUser(Model model, RedirectAttributes redirectAttributes, User users) {
        try {
            userService.save(users);
            model.addAttribute("users", users);
            redirectAttributes.addFlashAttribute("message", "The user has been saved successfully !");
        } catch (UserNotFoudException exception) {
            redirectAttributes.addFlashAttribute("User not found" + exception.getMessage());
        }
        return "redirect:/";
    }

    @GetMapping("/users/edit/{id}")
    public String editUser(Model model, @PathVariable(name = "id") Integer id) throws UserNotFoudException {
        try {
            User users = userService.getUserById(id);
            List<Role> listAllRoles = userService.listAllRoles();
            model.addAttribute("users", users);
            model.addAttribute("listRoles", listAllRoles);
            model.addAttribute("pageTitle", "Edit User ID : (" + id + ") ");
            return "admin/user_form";
        } catch (UserNotFoudException ex) {
            throw new UserNotFoudException("message" + ex.getMessage());
        }
    }

    @GetMapping("/users/delete/{id}")
    public String deleteUser(@PathVariable(name = "id") Integer id, RedirectAttributes redirectAttributes) {
        try {
            userService.deleteUser(id);
            redirectAttributes.addFlashAttribute("message", "The User ID " + id + " has been deleted successfully!");
        } catch (UserNotFoudException ex) {
            redirectAttributes.addFlashAttribute("message", ex.getMessage());
        }
        return "redirect:/";
    }

    @GetMapping("/users/{id}enabled{status}")
    public String updateUserEnabledStatus(@PathVariable(name = "id") Integer id, @PathVariable(name = "status") boolean enabled, RedirectAttributes redirectAttributes) {
        userService.updateUserEnableStatus(id, enabled);
        String status = enabled ? "enabled" : "disabled";
        String message = "The User ID" + id + "has been " + status;
        redirectAttributes.addFlashAttribute("message", message);
        return "redirect:/";
    }
}
