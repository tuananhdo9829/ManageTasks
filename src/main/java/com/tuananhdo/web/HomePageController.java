package com.tuananhdo.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomePageController {

    @GetMapping("/web/home")
    public String home() {

        return "web/home";
    }
}
