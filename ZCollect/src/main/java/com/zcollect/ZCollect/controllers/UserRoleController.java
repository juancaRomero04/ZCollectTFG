/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.zcollect.ZCollect.controllers;

import com.zcollect.ZCollect.entitites.UserRole;
import com.zcollect.ZCollect.entitites.UserRoleId;
import com.zcollect.ZCollect.services.UserRoleService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Usuario
 */
@RestController
@RequestMapping("/user-roles")
public class UserRoleController {
     @Autowired
    private UserRoleService userRoleService;

    @GetMapping
    public List<UserRole> getAllUserRoles() {
        return userRoleService.getAllUserRoles();
    }

    @PostMapping
    public UserRole assignRoleToUser(@RequestBody UserRole userRole) {
        return userRoleService.save(userRole);
    }

    @DeleteMapping("/{id}")
    public void removeUserRole(@PathVariable UserRoleId id) {
        userRoleService.deleteById(id);
    }
}
