//package com.tuananhdo.security.oauth2;
//
//import com.tuananhdo.entity.AuthenticationType;
//import com.tuananhdo.entity.User;
//import com.tuananhdo.service.UserService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
//import org.springframework.stereotype.Component;
//
//import javax.servlet.ServletException;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.io.IOException;
//
//@Component
//public class OAuth2LoginSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {
//
//    @Autowired private UserService userService;
//    @Override
//    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws ServletException, IOException {
//        CustomerOAuth2User oAuth2User = (CustomerOAuth2User) authentication.getPrincipal();
//        String name = oAuth2User.getName();
//        String email = oAuth2User.getEmail();
//
//        System.out.println("OAuth2LoginSuccessHandler: " + name + "email " + email);
//
//        User user = userService.getUserByEmail(email);
//        if (user == null){
//            userService.addNewUserUponOAuthLogin(name,email);
//        }else {
//            userService.updateAuthentication(user,AuthenticationType.GOOGLE);
//        }
//        super.onAuthenticationSuccess(request, response, authentication);
//    }
//}
