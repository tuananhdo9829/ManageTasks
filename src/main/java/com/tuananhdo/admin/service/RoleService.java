package com.tuananhdo.admin.service;

import com.tuananhdo.admin.exception.RoleNotFoundException;
import com.tuananhdo.admin.repository.RoleRepository;
import com.tuananhdo.admin.repository.UserRepository;
import com.tuananhdo.entity.Role;
import com.tuananhdo.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleService {

    @Autowired
    private RoleRepository roleRepository;

    
    public List<Role> listAllRoles() {

        return (List<Role>) roleRepository.findAll();
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
