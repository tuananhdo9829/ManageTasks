package com.tuananhdo.controller;

import com.tuananhdo.exception.UserNotFoundException;
import com.tuananhdo.service.UserService;
import com.tuananhdo.entity.User;
import com.tuananhdo.util.Utility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;

@Controller
public class LoginController {

    @Autowired
    private UserService userService;

    @GetMapping("/login")
    public String loginPage() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || authentication instanceof AnonymousAuthenticationToken) {
            return "admin/login";
        }
        return "redirect:/admin/home";
    }

    @GetMapping("/sign_up")
    public String signUp(Model model) {
        model.addAttribute("pageTitle", "Create Account");
        model.addAttribute("user", new User());
        return "admin/sign_up/sign_up";
    }

    @PostMapping("/sign_up")
    public String signUp(User user, HttpServletRequest request, RedirectAttributes redirectAttributes) throws UnsupportedEncodingException, MessagingException, UserNotFoundException {
        String siteURL = Utility.getSiteURL(request);
        userService.registerUser(user);
        userService.sendVerificationEmail(user, siteURL);
        redirectAttributes.addFlashAttribute("message","Registration Succeeded ! Please check your email");
        return "admin/sign_up/sign_up_success";
    }

    @GetMapping("/verify")
    public String verifyUser(@Param("code") String code, Model model) {
        boolean verify = userService.verify(code);
        String pageTitle = verify ? "Verification Succeeded" : "Verification Failed !";
        model.addAttribute("pageTitle", pageTitle);
        return "admin/sign_up/" + (verify ? "verify_success" :"verify_fail");
    }

    @GetMapping("/demo")
    public String pageAdmin() {

        return "admin/sign_up/demoadmin";
    }
}
