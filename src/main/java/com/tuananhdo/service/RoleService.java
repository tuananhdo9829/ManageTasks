package com.tuananhdo.service;

import com.tuananhdo.exception.RoleNotFoundException;
import com.tuananhdo.repository.RoleRepository;
import com.tuananhdo.entity.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class RoleService {

    @Autowired
    private RoleRepository roleRepository;


    public List<Role> listAllRoles() {

        return roleRepository.findAll();
    }

    public void saveRole(Role role) {

        roleRepository.save(role);
    }


    public Role getRoleById(Integer id) throws RoleNotFoundException {
        return roleRepository.findById(id).orElseThrow(
                () -> new RoleNotFoundException("Not found any Role ID with : " + id));
    }

    public void deleteRoleById(Integer id) throws RoleNotFoundException {
        Role role = roleRepository.findById(id).orElseThrow(
                () -> new RoleNotFoundException("Not found any Role ID with : " + id));
        roleRepository.delete(role);
    }
}
