package com.tuananhdo.admin.service;

import com.tuananhdo.admin.exception.ProjectNotFoundException;
import com.tuananhdo.admin.repository.ProjectRepository;
import com.tuananhdo.admin.repository.UserRepository;
import com.tuananhdo.entity.Project;
import com.tuananhdo.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class ProjectService {

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private UserRepository userRepository;

    private User user;



    public List<Project> listAllProjects() {
        return (List<Project>) projectRepository.findAll();
    }


    public void save(Project project) throws ProjectNotFoundException {
        if (project.getId() == null) {
            project.setCreatedTime(new Date());
        }
        project.setUpdatedTime(new Date());
        projectRepository.save(project);
    }

    
    public Project findProjectById(Long id) throws ProjectNotFoundException {
        return projectRepository.findById(id).orElseThrow(
                () -> new ProjectNotFoundException("Could not find any project with id " + id));
    }

    public void deleteProjectById(Long id) throws ProjectNotFoundException {
        Project project = projectRepository.findById(id).orElseThrow(
                () -> new ProjectNotFoundException("Could not find any project witd id :" + id));
        projectRepository.delete(project);
    }

}
