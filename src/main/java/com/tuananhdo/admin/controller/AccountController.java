package com.tuananhdo.admin.controller;

import com.tuananhdo.admin.service.UserService;
import com.tuananhdo.entity.User;
import com.tuananhdo.security.MyUserDetails;
import com.tuananhdo.util.FileUploadUltis;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Objects;

@Controller
public class AccountController {

    @Autowired
    private UserService userService;

    @GetMapping("/users/account")
    public String accountDetails(Model model, @AuthenticationPrincipal MyUserDetails myUserDetails) {
        String username = myUserDetails.getUsername();
        User users = userService.getUserByUsername(username);
        model.addAttribute("users", users);
        return "admin/user_account";
    }

    @PostMapping("/users/update/account")
    public String updateAccountDetails(User user, @AuthenticationPrincipal MyUserDetails myUserDetails, @RequestParam("image") MultipartFile multipartFile, RedirectAttributes redirectAttributes) {
        if (!multipartFile.isEmpty()) {
            String fileName = StringUtils.cleanPath(Objects.requireNonNull(multipartFile.getOriginalFilename()));
            user.setPhotos(fileName);
            User saveUser = userService.updateUserAccount(user);
            String uploadDir = "user-photos/" + saveUser.getId();
            FileUploadUltis.cleanFileDir(uploadDir);
            FileUploadUltis.saveFileDir(uploadDir, fileName, multipartFile);
            redirectAttributes.addFlashAttribute("message","The user has been saved successfully !");
        } else {
            if (user.getPhotos().isEmpty()) user.setPhotos(null);
            userService.updateUserAccount(user);
            redirectAttributes.addFlashAttribute("message","The user has been saved successfully !");
        }
        myUserDetails.setFirstName(user.getFirstName());
        myUserDetails.setLastName(user.getLastName());
        return "redirect:/users/account";
    }
}
