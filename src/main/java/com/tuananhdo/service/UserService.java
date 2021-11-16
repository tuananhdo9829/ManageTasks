package com.tuananhdo.service;

import com.tuananhdo.admin.error.UserNotFoudException;
import com.tuananhdo.entity.Role;
import com.tuananhdo.entity.User;
import com.tuananhdo.repository.RoleRepository;
import com.tuananhdo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@Transactional
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    public List<User> listAllUsers() {
        return (List<User>) userRepository.findAll();
    }

    public List<Role> listAllRoles() {
        return (List<Role>) roleRepository.findAll();

    }

    public void save(User user) throws UserNotFoudException {
        userRepository.save(user);
    }

    public User getUserById(Integer id) throws UserNotFoudException {
        try {
            return userRepository.findById(id).get();
        } catch (NoSuchElementException exception) {
            throw new UserNotFoudException("Could not find any user with ID : " + id);
        }
    }

    public void deleteUser(Integer id) throws UserNotFoudException {
        Long countById = userRepository.countById(id);
        if (countById == null || countById == 0) {
            throw new UserNotFoudException("Could not find any user with ID : " + id);
        }
        userRepository.deleteById(id);
    }

    public void updateUserEnableStatus(Integer id, boolean status) {
        userRepository.updateEnableStatus(id, status);
    }
}
