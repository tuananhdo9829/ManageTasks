package com.tuananhdo.controller;

import com.tuananhdo.entity.Project;
import com.tuananhdo.entity.Task;
import com.tuananhdo.entity.Team;
import com.tuananhdo.entity.User;
import com.tuananhdo.exception.TeamNotFoundException;
import com.tuananhdo.service.ProjectService;
import com.tuananhdo.service.TaskService;
import com.tuananhdo.service.TeamService;
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
public class HomeController {

    @Autowired
    private ProjectService projectService;
    @Autowired
    private TaskService taskService;
    @Autowired
    private TeamService teamService;
    @Autowired
    private UserService userService;

    @GetMapping("/admin/home")
    public String homePage(Model model) {
        List<Team> listAllTeams = teamService.listAllTeams();
        List<Project> listAllProjects = projectService.listAllProjects();
        List<Task> listAllTasks = taskService.listAllTasks();
        model.addAttribute("listAllTeams", listAllTeams);
        model.addAttribute("listAllProjects", listAllProjects);
        model.addAttribute("listAllTasks", listAllTasks);
        return "admin/home";
    }

    @GetMapping("/teams/new")
    public String newTeams(Model model) {
        Team team = new Team();
        List<Project> listAllProjects = projectService.listAllProjects();
        List<User> listAllUsers = userService.listAllUsers();
        model.addAttribute("team", team);
        model.addAttribute("listAllProjects", listAllProjects);
        model.addAttribute("listAllUsers", listAllUsers);
        return "admin/team/team_form";
    }


    @PostMapping("/teams/save")
    public String saveTeams(Team team, RedirectAttributes redirectAttributes) {
        teamService.saveTeam(team);
        redirectAttributes.addFlashAttribute("message", "The Team has been saved successfully !");
        return "redirect:/admin/home";
    }

    @GetMapping("/teams/edit/{id}")
    public String editTeams(@PathVariable("id") Integer id, Model model) throws TeamNotFoundException {
        Team team = teamService.getTeamById(id);
        model.addAttribute("team", team);
        return "admin/team/team_form";
    }

    @GetMapping("/teams/delete/{id}")
    public String deleteTeams(@PathVariable("id") Integer id, RedirectAttributes redirectAttributes) throws TeamNotFoundException {
        teamService.deleteTeam(id);
        redirectAttributes.addFlashAttribute("message", "The Team has been deleted successfully !");
        return "redirect:/admin/home";
    }
}
