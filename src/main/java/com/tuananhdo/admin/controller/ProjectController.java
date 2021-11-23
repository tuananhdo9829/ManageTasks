package com.tuananhdo.admin.controller;

import com.tuananhdo.admin.exception.ProjectNotFoundException;
import com.tuananhdo.admin.service.ProjectService;
import com.tuananhdo.entity.Project;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class ProjectController {

    @Autowired
    private ProjectService projectService;

    @GetMapping("/projects/home")
    public String getAllProjects(Model model) {
        List<Project> listProjects = projectService.listAllProjects();
        model.addAttribute("listProjects", listProjects);
        return "/admin/project/project_home";
    }

    @GetMapping("/projects/new")
    public String newProjects(Model model) {
        model.addAttribute("projects", new Project());
        model.addAttribute("pageTitle", "Create New Projects");
        return "/admin/project/project_form";
    }

    @PostMapping("/projects/save")
    public String save(Model model, Project project, RedirectAttributes redirectAttributes) throws ProjectNotFoundException {
        try {
            projectService.save(project);
            redirectAttributes.addFlashAttribute("message", "The project has been saved successfully !");
            return "redirect:/projects/home";

        } catch (ProjectNotFoundException exception) {
            throw new ProjectNotFoundException(exception.getMessage());
        }
    }

    @GetMapping("/projects/edit/{id}")
    public String editProject(Model model, @PathVariable(value = "id") Long id, RedirectAttributes redirectAttributes) throws ProjectNotFoundException {
        Project project = projectService.findProjectById(id);
        model.addAttribute("pageTitle", "Edit Project ID :" + id);
        model.addAttribute("projects", project);
        redirectAttributes.addFlashAttribute("message", "The projects has been save successfully !");
        return "/admin/project/project_form";
    }

    @GetMapping("/projects/delete/{id}")
    public String deleteProject(@PathVariable(value = "id") Long id) throws ProjectNotFoundException {
        try {
            projectService.deleteProjectById(id);
        } catch (ProjectNotFoundException exception) {
               throw new ProjectNotFoundException("Could not delete because not found id :  "+id);
        }
        return "redirect:/projects/home";
    }
}
