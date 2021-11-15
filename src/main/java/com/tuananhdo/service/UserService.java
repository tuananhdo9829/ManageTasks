package com.tuananhdo.service;

import com.tuananhdo.entity.Role;
import com.tuananhdo.entity.User;
import com.tuananhdo.repository.RoleRepository;
import com.tuananhdo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    public List<User> listAllUsers(){
        return (List<User>) userRepository.findAll();
    }

    public List<Role>listRoles() {
        return (List<Role>) roleRepository.findAll();
    }

    public void save(User user){
        userRepository.save(user);
    }
}
