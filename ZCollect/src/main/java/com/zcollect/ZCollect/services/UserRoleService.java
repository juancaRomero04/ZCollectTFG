/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.zcollect.ZCollect.services;

import com.zcollect.ZCollect.entitites.UserRole;
import com.zcollect.ZCollect.entitites.UserRoleId;
import com.zcollect.ZCollect.repositories.UserRoleRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Usuario
 */
@Service
public class UserRoleService {
    @Autowired
    private UserRoleRepository userRoleRepository;

    public List<UserRole> getAllUserRoles() {
        return userRoleRepository.findAll();
    }

    public Optional<UserRole> getById(UserRoleId id) {
        return userRoleRepository.findById(id);
    }

    public UserRole save(UserRole userRole) {
        return userRoleRepository.save(userRole);
    }

    public void deleteById(UserRoleId id) {
        userRoleRepository.deleteById(id);
    }
}
