package com.tuananhdo.admin.controller;


import com.tuananhdo.admin.exception.TaskNotFoundException;
import com.tuananhdo.admin.service.ProjectService;
import com.tuananhdo.admin.service.TaskService;
import com.tuananhdo.admin.service.UserService;
import com.tuananhdo.entity.Project;
import com.tuananhdo.entity.Task;
import com.tuananhdo.entity.User;
import com.tuananhdo.util.Utility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.util.Date;
import java.util.List;

@Controller
public class TaskController {


    @Autowired
    private TaskService taskService;

    @Autowired
    private ProjectService projectService;

    @Autowired
    private UserService userService;

    @GetMapping("/task_overview")
    public String listAllTask(Model model) {
        List<Task> listAllTasks = taskService.listAllTasks();
        model.addAttribute("listAllTasks", listAllTasks);
        return "admin/task/task_overview";
    }

    @GetMapping("/single_task")
    public String singleTask(Model model) {

        return "admin/task/single_task_overview";
    }


    @GetMapping("/task/new")
    public String createNewTask(Model model) {
        List<Project> listProjects = projectService.listAllProjects();
        List<User> listAllUsers = userService.listAllUsers();
        Task task = getCurrentDateTimes();
        model.addAttribute("task", task);
        model.addAttribute("listProject", listProjects);
        model.addAttribute("listAllUsers", listAllUsers);
        model.addAttribute("pageTitle", "Create New Task");
        return "admin/task/task_form";
    }

    private Task getCurrentDateTimes() {
        Task task = new Task();
        task.setTimeStart(new Date());
        task.setTimeEnd(new Date());
        return task;
    }


    @PostMapping("/task/save")
    public String saveTask(Task task, RedirectAttributes redirectAttributes) throws TaskNotFoundException, UnsupportedEncodingException, MessagingException, ParseException {
        taskService.save(task);
        redirectAttributes.addFlashAttribute("message", "The task has been saved successfully !");
        return "redirect:/task_overview";
    }


    @PostMapping("/task/send/mail")
    public String sendMail(User user, RedirectAttributes redirectAttributes, HttpServletRequest request) throws TaskNotFoundException, UnsupportedEncodingException, MessagingException, ParseException {
        String siteURL = Utility.getSiteURL(request);
        taskService.sendMailForUsers(user, siteURL);
        redirectAttributes.addFlashAttribute("message", "The task has been saved successfully !");
        return "redirect:/task_overview";
    }


    @GetMapping("/task/edit/{id}")
    public String editTask(@PathVariable("id") Integer id, Model model) throws TaskNotFoundException {
        Task task = taskService.getTaskById(id);
        List<Project> listProjects = projectService.listAllProjects();
        List<User> listAllUsers = userService.listAllUsers();
        model.addAttribute("task", task);
        model.addAttribute("listProject", listProjects);
        model.addAttribute("listAllUsers", listAllUsers);
        model.addAttribute("pageTitle", "Edit Task ID " + id);
        return "admin/task/task_form";
    }

}
