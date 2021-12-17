package com.tuananhdo.controller;


import com.tuananhdo.entity.Project;
import com.tuananhdo.entity.Task;
import com.tuananhdo.entity.User;
import com.tuananhdo.exception.TaskNotFoundException;
import com.tuananhdo.exception.UserNotFoundException;
import com.tuananhdo.service.ProjectService;
import com.tuananhdo.service.TaskService;
import com.tuananhdo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.mail.MessagingException;
import javax.validation.Valid;
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
    public String listAllTask(Model model,User user) {
        List<User> listAllUsers = userService.listAllUsers();
        List<Task> listAllTasks = taskService.listAllTasks();
        model.addAttribute("user", user);
        model.addAttribute("listAllTasks", listAllTasks);
        model.addAttribute("listAllUsers", listAllUsers);
        return "web/task_overview";
    }

    @GetMapping("/task/new")
    public String createNewTask(Model model) {
        List<Project> listAllProjects = projectService.listAllProjects();
        List<User> listAllUsers = userService.listAllUsers();
        Task task = getCurrentDateTimes();
        model.addAttribute("task", task);
        model.addAttribute("listAllProjects", listAllProjects);
        model.addAttribute("listAllUsers", listAllUsers);
        model.addAttribute("pageTitle", "Create New Task");
        model.addAttribute("titleButton", "Add Task");
        return "admin/task/task_form";
    }

    private Task getCurrentDateTimes() {
        Task task = new Task();
        task.setTimeStart(new Date());
        task.setTimeEnd(new Date());
        return task;
    }


    @PostMapping("/task/save")
    public String saveTask(@Valid @ModelAttribute("task") Task task, BindingResult bindingResult, Model model, RedirectAttributes redirectAttributes) throws TaskNotFoundException, UnsupportedEncodingException, MessagingException, ParseException, UserNotFoundException {
        if (bindingResult.hasErrors()) {
            List<Project> listAllProjects = projectService.listAllProjects();
            List<User> listAllUsers = userService.listAllUsers();
            model.addAttribute("listAllProjects", listAllProjects);
            model.addAttribute("listAllUsers", listAllUsers);
            return "admin/task/task_form";
        } else {
            taskService.save(task);
            redirectAttributes.addFlashAttribute("message", "The task has been saved successfully !");
        }
        return "redirect:/task_overview";
    }

    @GetMapping("/task/edit/{id}")
    public String editTask(@PathVariable("id") Integer id, Model model) throws TaskNotFoundException {
        Task task = taskService.getTaskById(id);
        List<Project> listAllProjects = projectService.listAllProjects();
        List<User> listAllUsers = userService.listAllUsers();
        model.addAttribute("task", task);
        model.addAttribute("listAllProjects", listAllProjects);
        model.addAttribute("listAllUsers", listAllUsers);
        model.addAttribute("pageTitle", "Edit Task ID " + id);
        model.addAttribute("titleButton", "Edit Task");

        return "admin/task/task_form";
    }

    @GetMapping("/task/delete/{id}")
    public String deleteTask(@PathVariable("id") Integer id, RedirectAttributes redirectAttributes) throws TaskNotFoundException {
        taskService.deleteTaskById(id);
        redirectAttributes.addFlashAttribute("message", "The task " + id + " has been deleted");
        return "redirect:/task_overview";
    }
}
