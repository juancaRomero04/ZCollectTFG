/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.zcollect.ZCollect.repositories;

import com.zcollect.ZCollect.entitites.UserRole;
import com.zcollect.ZCollect.entitites.UserRoleId;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author Usuario
 */
public interface UserRoleRepository extends JpaRepository<UserRole, UserRoleId>{
    
}
