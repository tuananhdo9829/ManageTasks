package com.tuananhdo;

import com.tuananhdo.entity.Role;
import com.tuananhdo.entity.User;
import com.tuananhdo.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.annotation.Rollback;

import javax.persistence.EntityManager;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;


@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(value = false)
public class UserRepositoryTests {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EntityManager entityManager;

    @Test
    public void testCreateOneUserWithOneRole() {
        User user = new User("tuananhdo", "tuananhdo9829@gmail.com", "Tuan", "Do", "$2a$10$zL0Z9wUeH1JerwZKLLmTzeftfWq4lGCdHyg8McC0zHLXKxaUyhhka",true);
        Role roleAdmin = entityManager.find(Role.class, 1);
        user.addRole(roleAdmin);
        User savedUser = userRepository.save(user);
        assertThat(savedUser.getId()).isGreaterThan(0);
    }

    @Test
    public void testCreateFourUserWithOneRole() {
        User staff1 = new User("staff5", "staff1@gmail.com", "staff1", "staff1", "$2a$10$zL0Z9wUeH1JerwZKLLmTzeftfWq4lGCdHyg8McC0zHLXKxaUyhhka",true);
        User staff2 = new User("staff6", "staff1@gmail.com", "staff1", "staff1", "$2a$10$zL0Z9wUeH1JerwZKLLmTzeftfWq4lGCdHyg8McC0zHLXKxaUyhhka",true);
        User staff3 = new User("staff7", "staff1@gmail.com", "staff1", "staff1", "$2a$10$zL0Z9wUeH1JerwZKLLmTzeftfWq4lGCdHyg8McC0zHLXKxaUyhhka",true);
        User staff4 = new User("staff8", "staff1@gmail.com", "staff1", "staff1", "$2a$10$zL0Z9wUeH1JerwZKLLmTzeftfWq4lGCdHyg8McC0zHLXKxaUyhhka",true);
        Role roleStaff = entityManager.find(Role.class, 2);
        staff1.addRole(roleStaff);
        staff2.addRole(roleStaff);
        staff3.addRole(roleStaff);
        staff4.addRole(roleStaff);
        userRepository.saveAll(Arrays.asList(staff1, staff2, staff3, staff4));
    }

    @Test
    public void testCreateTwoUserWithOneRole() {
        User staff1 = new User("staff10", "staff1@gmail.com", "staff1", "staff1", "$2a$10$zL0Z9wUeH1JerwZKLLmTzeftfWq4lGCdHyg8McC0zHLXKxaUyhhka",true);
        User staff2 = new User("staff12", "staff1@gmail.com", "staff1", "staff1", "$2a$10$zL0Z9wUeH1JerwZKLLmTzeftfWq4lGCdHyg8McC0zHLXKxaUyhhka",true);
        Role roleStaff = entityManager.find(Role.class, 2);
        staff1.addRole(roleStaff);
        staff2.addRole(roleStaff);
        userRepository.saveAll(Arrays.asList(staff1, staff2));
    }

    @Test
    public void testListAllUser() {
        Iterable<User> listUsers = userRepository.findAll();
        listUsers.forEach(System.out::println);
    }

    @Test
    public void testFindByIdUser() {
        User user = userRepository.findById(6).get();
        System.out.println(user);
        assertThat(user).isNotNull();
    }

    @Test
    public void testUpdateUserDetails() {
        User user = userRepository.findById(97).get();
        user.setEmail("tuananhdo@gmail.com");
        user.setEnabled(true);
        user.setUsername("anhtuando");
        userRepository.save(user);
    }

    @Test
    public void testUpdateUsersRoles() {
        User user = userRepository.findById(6).get();
        Role roleStaff = new Role(4);
        Role roleManage = new Role(3);
        user.getRoles().remove(roleManage);
        user.addRole(roleStaff);
        userRepository.save(user);
    }


    @Test
    public void testDeleteUser() {
        Integer id = 18;
        User user = userRepository.findById(id).orElseThrow(
                () -> new UsernameNotFoundException("Could not find any user id " + id));
        userRepository.delete(user);
        assertThat(user.getId()).isNotNull().isGreaterThan(0);
    }

    @Test
    public void testGetUserByEmail() {
        String email = "tuananhdoPro@gmail.com";
        User user = userRepository.getUserByEmail(email);
        assertThat(user).isNotNull();
    }

    @Test
    public void testCountById() {
        Integer id = 6;
        Long countById = userRepository.countById(id);
        assertThat(countById).isNotNull().isGreaterThan(0);
    }

    @Test
    public void testEnabledStatus() {
        Integer id = 18;
        User user = userRepository.findById(id).orElseThrow(
                () -> new UsernameNotFoundException("Could not find any user id " + id));
        userRepository.updateEnableStatus(18, true);
        assertThat(user.isEnabled()).isTrue();
    }

    @Test
    public void testDisableStatus() {
        Integer id = 18;
        User user = userRepository.findById(id).orElseThrow(
                () -> new UsernameNotFoundException("Could not find any user id " + id));
        userRepository.updateEnableStatus(18, false);
        assertThat(user.isEnabled()).isFalse();
    }
}
