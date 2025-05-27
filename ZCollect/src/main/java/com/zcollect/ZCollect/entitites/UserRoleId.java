/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.zcollect.ZCollect.entitites;

import jakarta.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

/**
 *
 * @author juanca
 */
@Embeddable
public class UserRoleId implements Serializable{
    private String userId;
    private String roleId;

    public UserRoleId(String userId, String roleId) {
        this.userId = userId;
        this.roleId = roleId;
    }

    public UserRoleId() {
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 71 * hash + Objects.hashCode(this.userId);
        hash = 71 * hash + Objects.hashCode(this.roleId);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final UserRoleId other = (UserRoleId) obj;
        if (!Objects.equals(this.userId, other.userId)) {
            return false;
        }
        return Objects.equals(this.roleId, other.roleId);
    }
    
}
