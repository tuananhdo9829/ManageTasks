package com.tuananhdo.security;

import com.tuananhdo.admin.error.UserNotFoudException;
import com.tuananhdo.entity.User;
import com.tuananhdo.admin.repository.UserRepository;
import lombok.SneakyThrows;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @SneakyThrows
    @Override
    public UserDetails loadUserByUsername(String username) {
        User user = userRepository.getUserByUsername(username);
        if (user != null) {
            return new MyUserDetails(user);
        }
        throw new UserNotFoudException("Could not find any user with username : " + username);
    }
}
