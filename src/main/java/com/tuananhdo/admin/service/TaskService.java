package com.tuananhdo.admin.service;


import com.tuananhdo.admin.exception.TaskNotFoundException;
import com.tuananhdo.admin.repository.TaskRepository;
import com.tuananhdo.entity.Task;
import com.tuananhdo.entity.TaskStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;

@Service
@Transactional
public class TaskService {

    @Autowired
    private TaskRepository taskRepository;

    public List<Task> listAllTasks() {
        return taskRepository.findAll();
    }


}
