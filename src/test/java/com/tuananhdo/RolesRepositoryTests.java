package com.tuananhdo;
import com.tuananhdo.entity.Role;
import com.tuananhdo.repository.RoleRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import java.util.Arrays;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(value = false)
public class RolesRepositoryTests {

    @Autowired
    private RoleRepository repository;

    @Test
    public void testCreateOneNewRole(){
        Role role = new Role(1,"Admin");
        Role saveRoles = repository.save(role);
        assertThat(saveRoles.getId()).isGreaterThan(0);
    }

    @Test
    public void testCreateAllRoles(){
        Role staff = new Role("Staff");
        Role manage = new Role("Manage");
        repository.saveAll(Arrays.asList(staff,manage));
    }

    @Test
    public void testListAllRoles(){
        Iterable<Role> roles = repository.findAll();
        roles.forEach(role -> System.out.println(roles));
    }

    @Test
    public void testGetRoleById(){
        Optional<Role> roleAdmin = repository.findById(1);
        System.out.println(roleAdmin);
        assertThat(roleAdmin).isNotNull();
    }

    @Test
    public void testUpdateRole(){
        Role role= repository.findById(1).get();
        role.setName("Administrator");
        repository.save(role);
    }

    @Test
    public void testDeleteRole(){
        Integer roleId = 2;
        repository.deleteById(roleId);
    }
}
