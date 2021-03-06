package com.tuananhdo.controller;

import com.tuananhdo.entity.Project;
import com.tuananhdo.entity.Task;
import com.tuananhdo.entity.Team;
import com.tuananhdo.entity.User;
import com.tuananhdo.exception.TeamNotFoundException;
import com.tuananhdo.exception.UserNotFoundException;
import com.tuananhdo.security.MyUserDetails;
import com.tuananhdo.service.ProjectService;
import com.tuananhdo.service.TaskService;
import com.tuananhdo.service.TeamService;
import com.tuananhdo.service.UserService;
import com.tuananhdo.util.Utility;
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

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.UnsupportedEncodingException;
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
    public String homePage(Model model, @AuthenticationPrincipal MyUserDetails myUserDetails) {
        List<Team> listAllTeams = teamService.listAllTeams();
        List<Project> listAllProjects = projectService.listAllProjects();
        List<Task> listAllTasks = taskService.listAllTasks();
        List<User> listAllUsers = userService.listAllUsers();
        model.addAttribute("listAllTeams", listAllTeams);
        model.addAttribute("listAllProjects", listAllProjects);
        model.addAttribute("listAllTasks", listAllTasks);
        model.addAttribute("listAllUsers", listAllUsers);
        return "admin/home";
    }

    @PostMapping("/team/send/mail")
    public String sendMail(User user, String email, HttpServletRequest request, RedirectAttributes redirectAttributes) throws UnsupportedEncodingException, MessagingException, UserNotFoundException {
        String siteURL = Utility.getSiteURL(request);
        taskService.sendMailForUsers(user, siteURL, email);
        redirectAttributes.addFlashAttribute("message", "Send e-mail successfully !");
        return "redirect:/admin/home";
    }

    private User getAuthenticationUser(MyUserDetails myUserDetails) {
        String username = myUserDetails.getUsername();
        return userService.getUserByUsername(username);
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
    public String saveTeams(@Valid @ModelAttribute("team") Team team, BindingResult bindingResult, Model model, RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            List<User> listAllUsers = userService.listAllUsers();
            model.addAttribute("listAllUsers", listAllUsers);
            return "admin/team/team_form";
        }
        teamService.saveTeam(team);
        redirectAttributes.addFlashAttribute("message", "The Team has been saved successfully !");
        return "redirect:/admin/home";
    }

    @GetMapping("/teams/edit/{id}")
    public String editTeams(@PathVariable("id") Integer id, Model model) throws TeamNotFoundException {
        Team team = teamService.getTeamById(id);
        List<User> listAllUsers = userService.listAllUsers();
        model.addAttribute("team", team);
        model.addAttribute("listAllUsers", listAllUsers);
        return "admin/team/team_form";
    }

    @GetMapping("/teams/delete/{id}")
    public String deleteTeams(@PathVariable("id") Integer id, RedirectAttributes redirectAttributes) throws TeamNotFoundException {
        teamService.deleteTeam(id);
        redirectAttributes.addFlashAttribute("message", "The Team has been deleted successfully !");
        return "redirect:/admin/home";
    }
}
