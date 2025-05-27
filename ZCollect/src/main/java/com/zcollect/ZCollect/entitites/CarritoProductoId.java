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
public class CarritoProductoId implements Serializable{
    private String carritoId;
    private String productoId;

    public CarritoProductoId(String carritoId, String productoId) {
        this.carritoId = carritoId;
        this.productoId = productoId;
    }

    public CarritoProductoId() {
    }

    public String getCarritoId() {
        return carritoId;
    }

    public void setCarritoId(String carritoId) {
        this.carritoId = carritoId;
    }

    public String getProductoId() {
        return productoId;
    }

    public void setProductoId(String productoId) {
        this.productoId = productoId;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 23 * hash + Objects.hashCode(this.carritoId);
        hash = 23 * hash + Objects.hashCode(this.productoId);
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
        final CarritoProductoId other = (CarritoProductoId) obj;
        if (!Objects.equals(this.carritoId, other.carritoId)) {
            return false;
        }
        return Objects.equals(this.productoId, other.productoId);
    }
    
}
