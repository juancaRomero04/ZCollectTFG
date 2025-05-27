/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.zcollect.ZCollect.DTOs;

import com.zcollect.ZCollect.entitites.Usuario;

/**
 *
 * @author Usuario
 */
public class UsuarioResponseDTO {
    private String id;
    private String username;
    private String email;

    public UsuarioResponseDTO(Usuario usuario) {
        this.id = usuario.getId_user();
        this.username = usuario.getUsername();
        this.email = usuario.getEmail();
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
    
}
