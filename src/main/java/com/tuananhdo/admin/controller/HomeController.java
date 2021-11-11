package com.tuananhdo.admin.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {


    @GetMapping("/")
    public String home() {

        return "admin/home";
    }

    @GetMapping("/home")
    public String webHome() {

        return "web/home";
    }

    @GetMapping("/projects_overview")
    public String projectOverview() {

        return "admin/project_overview";
    }


    @GetMapping("/tasks_overview")
    public String taskOverview() {

        return "admin/task_overview";
    }

}
