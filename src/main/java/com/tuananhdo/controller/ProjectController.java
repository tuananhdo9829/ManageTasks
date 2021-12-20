package com.tuananhdo.controller;

import com.tuananhdo.entity.Project;
import com.tuananhdo.entity.User;
import com.tuananhdo.exception.ProjectNotFoundException;
import com.tuananhdo.security.MyUserDetails;
import com.tuananhdo.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.text.ParseException;
import java.util.Date;
import java.util.List;

@Controller
public class ProjectController {

    @Autowired
    private ProjectService projectService;


    @GetMapping("/projects/home")
    public String getAllProjects(Model model) {
        List<Project> listAllProjects = projectService.listAllProjects();
        List<User> listAllUsers = projectService.listAllUsers();
        model.addAttribute("user", new User());
        model.addAttribute("listAllProjects", listAllProjects);
        model.addAttribute("listAllUsers", listAllUsers);
        return "/admin/project/project_home";
    }

    @GetMapping("/projects/new")
    public String newProjects(Model model) {
        List<User> listAllUsers = projectService.listAllUsers();
        Project project = getCurrentDateTime();
        model.addAttribute("listAllUsers", listAllUsers);
        model.addAttribute("projects", project);
        model.addAttribute("pageTitle", "Create New Projects");
        return "/admin/project/project_form";
    }

    private Project getCurrentDateTime() {
        Project project = new Project();
        project.setTimeStart(new Date());
        project.setTimeEnd(new Date());
        return project;
    }

    @PostMapping("/projects/save")
    public String save(@AuthenticationPrincipal MyUserDetails myUserDetails, @Valid @ModelAttribute("projects") Project project, BindingResult bindingResult, Model model, RedirectAttributes redirectAttributes) throws ProjectNotFoundException {
        try {
            if (bindingResult.hasErrors()) {
                List<User> listAllUsers = projectService.listAllUsers();
                model.addAttribute("listAllUsers", listAllUsers);
                return "/admin/project/project_form";
            }
            User findUserCreatedProject = findUserCreatedProject(project, myUserDetails);
            project.setUpdatedBy(findUserCreatedProject.getUsername());
            projectService.save(project);
            redirectAttributes.addFlashAttribute("message", "The project has been saved successfully !");
            return "redirect:/projects/home";

        } catch (ProjectNotFoundException | ParseException exception) {
            throw new ProjectNotFoundException(exception.getMessage());
        }
    }

    private User findUserCreatedProject(Project project, MyUserDetails myUserDetails) {
        String username = myUserDetails.getUsername();
        User userCreatedProject = projectService.findProjectCreateByUser(username);
        if (username != null) {
            project.setCreatedBy(userCreatedProject.getUsername());
        }
        return userCreatedProject;
    }


    @GetMapping("/projects/edit/{id}")
    public String editProject(Model model, @PathVariable(value = "id") Integer id) throws ProjectNotFoundException {
        Project projects = projectService.findProjectById(id);
        List<User> listAllUsers = projectService.listAllUsers();
        model.addAttribute("pageTitle", "Edit Project ID :" + id);
        model.addAttribute("projects", projects);
        model.addAttribute("listAllUsers", listAllUsers);
        return "/admin/project/project_form";
    }

    @GetMapping("/projects/delete/{id}")
    public String deleteProject(@PathVariable(value = "id") Integer id) throws ProjectNotFoundException {
        try {
            projectService.deleteProjectById(id);
        } catch (ProjectNotFoundException exception) {
            throw new ProjectNotFoundException("Could not delete because not found id :  " + id);
        }
        return "redirect:/projects/home";
    }
}
