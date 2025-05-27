/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.zcollect.ZCollect.DTOs;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

/**
 *
 * @author Usuario
 */
public class RegisterRequest {

    @NotBlank(message = "El nombre de usuario es obligatorio")
    @Size(max = 50, message = "El nombre de usuario debe tener máximo 50 caracteres")
    private String username;
    @NotBlank(message = "El email es obligatorio")
    @Pattern(
            regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$",
            message = "El correo electrónico no es válido"
    )
    @Size(max = 100, message = "El email debe tener máximo 100 caracteres")
    private String email;
    @NotBlank(message = "La contraseña es obligatoria")
    @Size(min = 8, max = 255, message = "La contraseña debe tener entre 8 y 255 caracteres")
    private String password;
    @NotBlank(message = "El telefono es obligatoria")
    @Pattern(
            regexp = "^[0-9]{9}$",
            message = "El número de teléfono no es valido"
    )
    private String telefono;
    @NotBlank(message = "La direccion es obligatoria")
    @Size(max = 255, message = "La dirección debe tener máximo 255 caracteres")
    private String direccion;

    public RegisterRequest(String username, String email, String password, String telefono, String direccion) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.telefono = telefono;
        this.direccion = direccion;
    }

    public RegisterRequest() {
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

}
