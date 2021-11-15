package com.tuananhdo;

import com.tuananhdo.entity.Role;
import com.tuananhdo.entity.User;
import com.tuananhdo.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import javax.persistence.EntityManager;


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
        User user = new User("tuananhdo", "tuananhdo@gmail.com", "Tuan", "Do", "password");
        Role roleAdmin = entityManager.find(Role.class, 1);
        user.addRole(roleAdmin);
        User savedUser = userRepository.save(user);
        assertThat(savedUser.getId()).isGreaterThan(0);
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
        User user = userRepository.findById(6).get();
        user.setEmail("tuananhdoPro@gmail.com");
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
        Integer id = 6;
        userRepository.deleteById(id);
    }

    @Test
    public void testGetUserByEmail(){
        String email = "tuananhdoPro@gmail.com";
        User user = userRepository.getUserByEmail(email);
        assertThat(user).isNotNull();
    }

    @Test
    public void testCountById(){
        Integer id = 6;
        Long countById = userRepository.countById(id);
        assertThat(countById).isNotNull().isGreaterThan(0);
    }

    @Test
    public void testEnabledStatus(){
        Integer id = 6;
        userRepository.updateEnableStatus(id,true);
    }

    @Test
    public void testDisableStatus(){
        Integer id = 6;
        userRepository.updateEnableStatus(id,true);
    }
}
