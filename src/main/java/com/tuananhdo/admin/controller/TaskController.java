package com.tuananhdo.admin.controller;


import com.tuananhdo.admin.exception.TaskNotFoundException;
import com.tuananhdo.admin.service.ProjectService;
import com.tuananhdo.admin.service.TaskService;
import com.tuananhdo.admin.service.UserService;
import com.tuananhdo.entity.Project;
import com.tuananhdo.entity.Task;
import com.tuananhdo.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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

}
