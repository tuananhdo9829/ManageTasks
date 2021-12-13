package com.tuananhdo.security;

import com.tuananhdo.exception.UserNotFoundException;
import com.tuananhdo.entity.User;
import com.tuananhdo.repository.UserRepository;
import lombok.SneakyThrows;
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
        throw new UserNotFoundException("Could not find any user with username : " + username);
    }
}
