package com.tuananhdo.admin.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
@Controller
public class ErrorController {

    @GetMapping("/404")
    public String Error404(){
        return "error/404";
    }
}
