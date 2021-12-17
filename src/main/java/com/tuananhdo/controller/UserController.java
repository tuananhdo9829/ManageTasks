package com.tuananhdo.controller;

import com.tuananhdo.entity.Project;
import com.tuananhdo.exception.UserNotFoundException;
import com.tuananhdo.service.ProjectService;
import com.tuananhdo.service.UserService;
import com.tuananhdo.entity.Role;
import com.tuananhdo.entity.User;
import com.tuananhdo.util.FileUploadUlti;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.List;
import java.util.Objects;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private ProjectService projectService;


    @GetMapping("/users/home")
    public String listAllUsers(Model model) {
        List<Project> listAllProjects = projectService.listAllProjects();
        List<User> listAllUsers = userService.listAllUsers();
        model.addAttribute("listAllProjects", listAllProjects);
        model.addAttribute("listAllUsers", listAllUsers);
        return "admin/project/project_home";
    }

    @GetMapping("/users/new")
    public String createNewUsers(Model model) {
        List<Role> listAllRoles = userService.listAllRoles();
        User user = new User();
        user.setEnabled(true);
        model.addAttribute("users", user);
        model.addAttribute("listAllRoles", listAllRoles);
        model.addAttribute("pageTitle", "Create New User");
        return "admin/user/user_form";
    }

    @PostMapping("/users/save")
    public String saveUser(@Valid @ModelAttribute("users") User users, BindingResult bindingResult, Model model, @RequestParam("image") MultipartFile multipartFile, RedirectAttributes redirectAttributes) {

//        if (bindingResult.hasErrors()) {
//            List<Role> listAllRoles = userService.listAllRoles();
//            model.addAttribute("listAllRoles", listAllRoles);
//            model.addAttribute("pageTitle", "Create New User");
//            return "admin/user/user_form";
//        }
        try {
            if (!multipartFile.isEmpty()) {
                String fileName = StringUtils.cleanPath(Objects.requireNonNull(multipartFile.getOriginalFilename()));
                users.setPhotos(fileName);
                User savedUser = userService.save(users);
                String uploadDir = "user-photos/" + savedUser.getId();
                FileUploadUlti.cleanFileDir(uploadDir);
                FileUploadUlti.saveFileDir(uploadDir, fileName, multipartFile);
                redirectAttributes.addFlashAttribute("message", "The user has been saved successfully !");
            }
        } catch (UserNotFoundException ex) {
            redirectAttributes.addFlashAttribute("message" + ex.getMessage());
        }

        return "redirect:/users/home";
    }

    @GetMapping("/users/edit/{id}")
    public String editUser(Model model, @PathVariable(name = "id") Integer id) throws UserNotFoundException {
        try {
            User users = userService.getUserById(id);
            List<Role> listAllRoles = userService.listAllRoles();
            model.addAttribute("users", users);
            model.addAttribute("listAllRoles", listAllRoles);
            model.addAttribute("pageTitle", "Edit User ID : (" + id + ") ");
            return "admin/user/user_form";
        } catch (UserNotFoundException ex) {
            throw new UserNotFoundException("message" + ex.getMessage());
        }
    }

    @GetMapping("/users/delete/{id}")
    public String deleteUser(@PathVariable(name = "id") Integer id, RedirectAttributes redirectAttributes) {
        try {
            userService.deleteUser(id);
            String dirName = "user-photos/" + id;
            FileUploadUlti.deleteFileDir(dirName);
            redirectAttributes.addFlashAttribute("message", "The User ID " + id + " has been deleted successfully!");
        } catch (UserNotFoundException ex) {
            redirectAttributes.addFlashAttribute("message", ex.getMessage());
        }
        return "redirect:/users/home";
    }

    @GetMapping("/users/{id}enabled{status}")
    public String updateUserEnabledStatus(@PathVariable(name = "id") Integer id, @PathVariable(name = "status") boolean enabled, RedirectAttributes redirectAttributes) {
        userService.updateUserEnableStatus(id, enabled);
        String status = enabled ? "enabled" : "disabled";
        String message = "The User ID" + id + "has been " + status;
        redirectAttributes.addFlashAttribute("message", message);
        return "redirect:/users/home";
    }
}
