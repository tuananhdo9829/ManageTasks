package com.tuananhdo.admin.service;

import com.tuananhdo.admin.exception.ProjectNotFoundException;
import com.tuananhdo.admin.repository.ProjectRepository;
import com.tuananhdo.admin.repository.UserRepository;
import com.tuananhdo.entity.Project;
import com.tuananhdo.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
        return (List<Project>) projectRepository.findAll();
    }

    public List<User> listAllUsers() {
        return (List<User>) userRepository.findAll();
    }


    public void save(Project project) throws ProjectNotFoundException {
        if (project.getId() == null) {
            project.setCreatedTime(new Date());
        }

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
                () -> new ProjectNotFoundException("Could not find any project witd id :" + id));
        projectRepository.delete(project);
    }

}
