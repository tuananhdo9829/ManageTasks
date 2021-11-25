package com.tuananhdo.admin.controller;

import com.tuananhdo.admin.exception.RoleNotFoundException;
import com.tuananhdo.admin.service.RoleService;
import com.tuananhdo.entity.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class RoleController {

    @Autowired
    private RoleService roleService;

    @GetMapping("/roles/home")
    public String listAllRoles(Model model) {
        List<Role> listAllRoles = roleService.listAllRoles();
        model.addAttribute("listRoles", listAllRoles);
        return "/admin/role/role_home";
    }

    @GetMapping("/roles/new")
    public String newRole(Model model) {
        Role role = new Role();
        model.addAttribute("roles", role);
        model.addAttribute("pageTitle", "Create New Role");
        return "/admin/role/role_form";
    }

    @PostMapping("/roles/save")
    public String saveRole(Role role, RedirectAttributes redirectAttributes) {
        roleService.saveRole(role);
        redirectAttributes.addFlashAttribute("message", "The role has been saved successfully !");
        return "redirect:/roles/home";
    }

    @GetMapping("/roles/edit/{id}")
    public String editRole(@PathVariable(value = "id") Integer id, Model model) throws RoleNotFoundException {
        try {
            Role role = roleService.getRoleById(id);
            model.addAttribute("roles", role);
            model.addAttribute("pageTitle", "Edit Role With ID :" + id);
            return "/admin/role/role_form";

        } catch (RoleNotFoundException exception) {
            throw new RoleNotFoundException("Could not find any role  :" + exception.getMessage());
        }
    }

    @GetMapping("/roles/delete/{id}")
    public String deleteRole(@PathVariable(value = "id") Integer id, RedirectAttributes redirectAttributes) throws RoleNotFoundException {
        try {
            roleService.deleteRoleById(id);
            redirectAttributes.addFlashAttribute("message", "The role has been deleted successfully !");
            return "redirect:/roles/home";
        } catch (RoleNotFoundException exception) {
            throw new RoleNotFoundException("The roles not found" + id);
        }
    }
}
