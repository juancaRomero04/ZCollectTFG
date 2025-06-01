/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.zcollect.ZCollect.DTOs;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

/**
 *
 * @author Usuario
 */
public class TarjetaDTO {
    @NotBlank(message = "El número de tarjeta es obligatorio")
    @Pattern(regexp = "^(4[0-9]{12}(?:[0-9]{3})?|5[1-5][0-9]{14})$", message = "Número de tarjeta inválido") 
    private String numeroTarjeta; // Regex simple para Visa/MasterCard

    @NotBlank(message = "La fecha de caducidad es obligatoria")
    @Pattern(regexp = "(0[1-9]|1[0-2])\\/\\d{2}", message = "La fecha de caducidad debe tener el formato MM/aa")
    private String fechaExpiracion;

    @NotBlank(message = "El código CVV es obligatorio")
    @Pattern(regexp = "^[0-9]{3,4}$", message = "CVV inválido")
    private String cvv;

    @NotBlank(message = "El nombre del titular es obligatorio")
    @Size(min = 3, max = 50)
    private String nombreTitular;

    @NotBlank(message="Debes de escoger un metodo de pago")
    @Pattern(regexp = "Visa|MasterCard", message = "Método de pago inválido")
    private String tipoTarjeta;

    public String getNumeroTarjeta() {
        return numeroTarjeta;
    }

    public void setNumeroTarjeta(String numeroTarjeta) {
        this.numeroTarjeta = numeroTarjeta;
    }

    public String getFechaExpiracion() {
        return fechaExpiracion;
    }

    public void setFechaExpiracion(String fechaExpiracion) {
        this.fechaExpiracion = fechaExpiracion;
    }

    public String getCvv() {
        return cvv;
    }

    public void setCvv(String cvv) {
        this.cvv = cvv;
    }

    public String getNombreTitular() {
        return nombreTitular;
    }

    public void setNombreTitular(String nombreTitular) {
        this.nombreTitular = nombreTitular;
    }

    public String getTipoTarjeta() {
        return tipoTarjeta;
    }

    public void setTipoTarjeta(String tipoTarjeta) {
        this.tipoTarjeta = tipoTarjeta;
    }
    
    
    
}
