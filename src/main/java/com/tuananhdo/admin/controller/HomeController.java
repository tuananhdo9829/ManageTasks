package com.tuananhdo.admin.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {


    @GetMapping("users/projects_overview")
    public String projectOverview() {

        return "admin/project_overview";
    }


    @GetMapping("users/tasks_overview")
    public String taskOverview() {

        return "admin/task_overview";
    }

}
