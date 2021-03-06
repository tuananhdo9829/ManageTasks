package com.tuananhdo.service;


import com.tuananhdo.entity.Task;
import com.tuananhdo.entity.User;
import com.tuananhdo.exception.TaskNotFoundException;
import com.tuananhdo.exception.UserNotFoundException;
import com.tuananhdo.repository.TaskRepository;
import com.tuananhdo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.transaction.Transactional;
import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.util.List;

@Service
@Transactional
public class TaskService {

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private UserRepository userRepository;


    public List<Task> listAllTasks() {
        return taskRepository.findAll();
    }


    public Task getTaskById(Integer id) throws TaskNotFoundException {

        return taskRepository.findById(id).orElseThrow(
                () -> new TaskNotFoundException("Could not find any with task id " + id));
    }

    public Task save(Task task) throws TaskNotFoundException, ParseException, UnsupportedEncodingException, MessagingException {
        task.setDateTimeStart(task.getDateTimeStart());
        return taskRepository.save(task);
    }

    public User findUserCreatedTask(String username) {
        return userRepository.getUserByUsername(username);
    }

    public List<Task> getTaskByName(String name) {
        return taskRepository.getTaskByName(name);
    }


    public void sendMailForUsers(User user, String siteURL, String email) throws MessagingException, UnsupportedEncodingException, UserNotFoundException {
        User getEmailUser = userRepository.getUserByEmail(email);
        if (getEmailUser != null) {
            String subject = "CSeam Group Team";
            String senderName = "CS Dojo Kind Roy";
            String mailContent = "<p> Dear : " + getEmailUser.getFullName() + "</p>";
            mailContent += "<p> You have an invitation to join the project</p>";
            String claimTask = siteURL + "/task_overview";
            mailContent += "<h3><a href =\"" + claimTask + "\">Join Project</a></h3>";

            sendMail(user, subject, senderName, mailContent, javaMailSender);
        } else {
            throw new UserNotFoundException("Could not find any with email :" + email);
        }
    }

    static void sendMail(User user, String subject, String senderName, String mailContent, JavaMailSender javaMailSender) throws MessagingException, UnsupportedEncodingException {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage);
        mimeMessageHelper.setFrom("slodierqwe@gmail.com", senderName);
        mimeMessageHelper.setTo(user.getEmail());
        mimeMessageHelper.setSubject(subject);
        mimeMessageHelper.setText(mailContent, true);
        javaMailSender.send(mimeMessage);
    }


    public void deleteTaskById(Integer id) throws TaskNotFoundException {
        taskRepository.deleteById(id);
    }

}
