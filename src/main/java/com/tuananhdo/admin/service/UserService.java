package com.tuananhdo.admin.service;

import com.tuananhdo.admin.exception.UserNotFoudException;
import com.tuananhdo.admin.repository.RoleRepository;
import com.tuananhdo.admin.repository.UserRepository;
import com.tuananhdo.entity.Role;
import com.tuananhdo.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
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

    @Autowired
    private PasswordEncoder passwordEncoder;

    public List<User> listAllUsers() {
        return (List<User>) userRepository.findAll();
    }

    public List<Role> listAllRoles() {

        return (List<Role>) roleRepository.findAll();

    }

    public User getUserByUsername(String username) {
        return userRepository.getUserByUsername(username);
    }

    public User updateUserAccount(User userInForm) {
        User userInDB = userRepository.findById(userInForm.getId()).get();
        if (!userInForm.getPassword().isEmpty()) {
            userInDB.setPassword(userInForm.getPassword());
            encodePassword(userInDB);
        }
        if (userInForm.getPhotos() != null) {
            userInDB.setPhotos(userInForm.getPhotos());
        }
        userInDB.setFirstName(userInForm.getFirstName());
        userInDB.setLastName(userInForm.getLastName());
        userInDB.setEmail(userInForm.getEmail());
        return userRepository.save(userInDB);
    }

    public User save(User user) throws UserNotFoudException {
        encodePassword(user);
        return userRepository.save(user);
    }

    public void encodePassword(User user) {
        String rawPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(rawPassword);
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
