package com.tuananhdo.service;

import com.tuananhdo.exception.UserNotFoundException;
import com.tuananhdo.repository.RoleRepository;
import com.tuananhdo.repository.UserRepository;
import com.tuananhdo.entity.Role;
import com.tuananhdo.entity.User;
import net.bytebuddy.utility.RandomString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.transaction.Transactional;
import java.io.UnsupportedEncodingException;
import java.util.List;

@Service
@Transactional
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JavaMailSender javaMailSender;

    public List<User> listAllUsers() {

        return userRepository.findAll();
    }

    public List<Role> listAllRoles() {

        return roleRepository.findAll();

    }

    public User getUserByEmail(String email) {
        return userRepository.getUserByEmail(email);
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

    public User save(User user) throws UserNotFoundException {
        boolean isUpdatingUser = (user.getId() != null);
        if (isUpdatingUser) {
            User isExisUser = userRepository.findById(user.getId()).get();
            if (user.getPassword().isEmpty()) {
                user.setPassword(isExisUser.getPassword());
            } else {
                encodePassword(user);
            }
        } else {
            encodePassword(user);
        }
        return userRepository.save(user);
    }

    public void registerUser(User user) {
        String randomCode = RandomString.make(64);
        user.setEnabled(false);
        user.setVerificationCode(randomCode);
        encodePassword(user);
        userRepository.save(user);
    }

    public boolean verify(String verificationCode) {
        User user = userRepository.findByVerificationCode(verificationCode);

        if (user == null || user.isEnabled()) {
            return false;
        } else {
            userRepository.activeUser(user.getId());
            return true;
        }
    }


    public void sendVerificationEmail(User user, String siteURL) throws MessagingException, UnsupportedEncodingException {
        String subject = "CSeam Group Team";
        String senderName = "CS Dojo Kind Roy";
        String mailContent = "<p> Dear" + user.getUsername() + ", </p>";
        mailContent += "<p>Here is link verification user</p>";

        String verifyURL = siteURL + "/verify?code=" + user.getVerificationCode();

        mailContent += "<h3><a href =\"" + verifyURL + "\">VERIFY</a></h3>";

        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage);

        mimeMessageHelper.setFrom("slodierqwe@gmail.com", senderName);
        mimeMessageHelper.setTo(user.getEmail());
        mimeMessageHelper.setSubject(subject);
        mimeMessageHelper.setText(mailContent, true);
        javaMailSender.send(mimeMessage);
    }

    public void encodePassword(User user) {
        String rawPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(rawPassword);
    }

    public User getUserById(Integer id) throws UserNotFoundException {
        return userRepository.findById(id).orElseThrow(
                () -> new UserNotFoundException("Could not find any user with ID :" + id));
    }

    public void deleteUser(Integer id) throws UserNotFoundException {
        Long countById = userRepository.countById(id);
        if (countById == null || countById == 0) {
            throw new UserNotFoundException("Could not find any user with ID : " + id);
        }
        userRepository.deleteById(id);
    }

    public void updateUserEnableStatus(Integer id, boolean status) {
        userRepository.updateEnableStatus(id, status);
    }
}
