package com.tuananhdo.service;

import com.tuananhdo.exception.ProjectNotFoundException;
import com.tuananhdo.repository.ProjectRepository;
import com.tuananhdo.repository.UserRepository;
import com.tuananhdo.entity.Project;
import com.tuananhdo.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.text.ParseException;
import java.util.Date;
import java.util.List;

@Service
@Transactional
public class ProjectService {

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private UserRepository userRepository;


    public List<Project> listAllProjects() {

        return projectRepository.findAll();
    }

    public List<User> listAllUsers() {

        return userRepository.findAll();
    }


    public void save(Project project) throws ProjectNotFoundException, ParseException {
        project.setCreatedTime(new Date());
        project.setTimeStartOfProject(project.getTimeStartOfProject());
        project.setTimeEndOfProject(project.getTimeEndOfProject());
        project.setUpdatedTime(new Date());
        projectRepository.save(project);
    }


    public User findProjectCreateByUser(String username) {
        return userRepository.getUserByUsername(username);
    }


    public Project findProjectById(Integer id) throws ProjectNotFoundException {
        return projectRepository.findById(id).orElseThrow(
                () -> new ProjectNotFoundException("Could not find any project with id " + id));
    }

    public void deleteProjectById(Integer id) throws ProjectNotFoundException {
        Project project = projectRepository.findById(id).orElseThrow(
                () -> new ProjectNotFoundException("Could not find any project with id :" + id));
        projectRepository.delete(project);
    }

}
