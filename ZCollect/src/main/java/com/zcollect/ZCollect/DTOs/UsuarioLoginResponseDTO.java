/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.zcollect.ZCollect.DTOs;

import com.zcollect.ZCollect.entitites.Usuario;
import java.util.List;
import java.util.stream.Collectors;

/**
 *
 * @author Usuario
 */
public class UsuarioLoginResponseDTO {
    private String id;
    private String username;
    private String email;
    private List<String> roles;  

    public UsuarioLoginResponseDTO(Usuario usuario) {
        this.id = usuario.getId_user();
        this.username = usuario.getUsername();
        this.email = usuario.getEmail();
        // Extraemos los nombres de roles de la entidad Usuario
        this.roles = usuario.getRoles().stream()
            .map(userRole -> userRole.getRol().getNombre())
            .collect(Collectors.toList());
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }
    
}
