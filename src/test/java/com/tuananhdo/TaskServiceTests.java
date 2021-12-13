package com.tuananhdo;

import com.tuananhdo.entity.Project;
import com.tuananhdo.entity.Task;
import com.tuananhdo.entity.TaskStatus;
import com.tuananhdo.entity.User;
import com.tuananhdo.exception.TaskNotFoundException;
import com.tuananhdo.repository.TaskRepository;
import com.tuananhdo.service.TaskService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.mail.MessagingException;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;


import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@ExtendWith(MockitoExtension.class)
@ExtendWith(SpringExtension.class)
public class TaskServiceTests {

    @MockBean
    private TaskRepository taskRepository;

    @InjectMocks
    private TaskService taskService;


    @Test
    public void testGetTasks() {
        Integer projectId = 9;
        Integer userId = 1;
        Project project = new Project(projectId);
        User user = new User(userId);
        List<Task> tasks = taskRepository.findAll();
        Mockito.when(tasks).thenReturn(
                Stream.of(new Task(1, "Fix Authentication Duck App", "Change Duck App to Bird App", TaskStatus.TODO, project, user)).collect(Collectors.toList()));
        assertEquals(1, taskService.listAllTasks().size());
    }


    @Test
    public void testCheckVerifyThrowExceptionTask() {
        Integer id = 50;
        Mockito.when(taskRepository.findById(Mockito.any(Integer.class))).thenReturn(Optional.empty());
        Mockito.verify(taskRepository).findById(Mockito.any(Integer.class));
        assertThatThrownBy(() -> taskService.getTaskById(id)).isInstanceOf(TaskNotFoundException.class);
    }

    @Test
    public void testSaveTask() throws TaskNotFoundException, ParseException, UnsupportedEncodingException, MessagingException {
        Integer projectId = 9;
        Integer userId = 1;
        Project project = new Project(projectId);
        User user = new User(userId);
        Task task = new Task(30, "Update Bird App ", "This is Bird App !!", TaskStatus.TODO, project, user);
        Mockito.when(taskRepository.save(task)).thenReturn(task);
        assertEquals(task, taskService.save(task));
    }

    @Test
    public void getTaskByName() {
        String name = "Duck App";
        Mockito.when(taskRepository.getTaskByName(name))
                .thenReturn(Stream.of(new Task(28, "Change Duck App to Bird App")).collect(Collectors.toList()));
        assertEquals(1, taskService.getTaskByName(name).size());
    }

    @Test
    public void testDeleteTaskById() throws TaskNotFoundException {
        Integer projectId = 9;
        Integer userId = 1;
        Project project = new Project(projectId);
        User user = new User(userId);
        Task task = new Task(30, "Update Bird App ", "This is Bird App !!", TaskStatus.TODO, project, user);
        taskService.deleteTaskById(task.getId());
        Mockito.verify(taskRepository, Mockito.times(1)).deleteById(task.getId());
    }
}
