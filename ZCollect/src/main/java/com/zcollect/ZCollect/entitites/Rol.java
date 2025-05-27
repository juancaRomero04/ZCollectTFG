/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.zcollect.ZCollect.entitites;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 *
 * @author juanca
 */

@Entity
@Table(name = "Rol")
public class Rol {
    @Id
    @Size(min = 1, max = 36)
    private String id_role;

    @NotBlank
    @Size(max = 50)
    private String nombre;

    @OneToMany(mappedBy = "rol", cascade = CascadeType.ALL)
    private List<UserRole> userRoles;

    public Rol(String id_role, String nombre) {
        this.id_role = id_role;
        this.nombre = nombre;
        this.userRoles = new ArrayList<>();
    }

    public Rol(String id_role) {
        this.id_role = id_role;
    }

    public Rol() {
    }

    public String getId_role() {
        return id_role;
    }

    public void setId_role(String id_role) {
        this.id_role = id_role;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public List<UserRole> getUserRoles() {
        return userRoles;
    }

    public void setUserRoles(List<UserRole> userRoles) {
        this.userRoles = userRoles;
    }

    @Override
    public String toString() {
        return "Rol{" + "id_role=" + id_role + ", nombre=" + nombre + ", userRoles=" + userRoles + '}';
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 67 * hash + Objects.hashCode(this.id_role);
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
        final Rol other = (Rol) obj;
        return Objects.equals(this.id_role, other.id_role);
    }
    
}
