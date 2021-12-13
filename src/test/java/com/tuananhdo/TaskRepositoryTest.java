package com.tuananhdo;

import com.tuananhdo.admin.exception.TaskNotFoundException;
import com.tuananhdo.admin.repository.TaskRepository;
import com.tuananhdo.entity.Project;
import com.tuananhdo.entity.Task;
import com.tuananhdo.entity.TaskStatus;
import com.tuananhdo.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import java.util.Date;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(value = false)
public class TaskRepositoryTest {


    @Autowired
    private TaskRepository taskRepository;

    @Test
    public void testCreateNewTaskWithUserAndProject() {
        Integer userId = 52;
        User user = new User(userId);

        Integer projectId = 9;
        Project project = new Project(projectId);

        Task task = new Task();
        task.setProject(project);
        task.setUser(user);
        task.setName("Create New Project 2");
        task.setDescription("Create Full User and Header 2");
        task.setCreatedTime(new Date());
        task.setStatus(TaskStatus.NEW);

        Task savedTask = taskRepository.save(task);

        assertThat(savedTask).isNotNull();
        assertThat(savedTask.getId()).isGreaterThan(0);
    }

    @Test
    public void testListTask() {
        List<Task> listTasks = taskRepository.findAll();
        listTasks.stream().forEach(System.out::println);
        assertThat(listTasks.size()).isGreaterThan(0);
    }


    @Test
    public void testGetTask() throws TaskNotFoundException {
        Integer id = 2;
        Task task = taskRepository.findById(id).orElseThrow(
                () -> new TaskNotFoundException("Could not find any with task id :" + id));
        assertThat(task).isNotNull();
    }

    @Test
    public void testUpdateTask() throws TaskNotFoundException {
        Integer id = 4;
        String name = "Create New Dragon";
        String description = "Create New Dragon Pro";
        Task task = taskRepository.findById(id).orElseThrow(
                () -> new TaskNotFoundException("Could not find any with task id :" + id));
        task.setName(name);
        task.setDescription(description);
        Task updatedTask = taskRepository.save(task);
        assertThat(updatedTask.getName()).isEqualTo(name);
        assertThat(updatedTask.getDescription()).isEqualTo(description);
    }

    @Test
    public void testDeleteTask() throws TaskNotFoundException {
        Integer id = 2;
        Task task = taskRepository.findById(id).orElseThrow(
                () -> new TaskNotFoundException("Could not find id task " + id));
        taskRepository.delete(task);
        assertThat(task.getId()).isNotNull().isGreaterThan(0);
    }


}
