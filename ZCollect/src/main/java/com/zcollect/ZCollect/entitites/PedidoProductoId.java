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
public class PedidoProductoId implements Serializable{
    private String pedidoId;
    private String productoId;

    public PedidoProductoId(String pedidoId, String productoId) {
        this.pedidoId = pedidoId;
        this.productoId = productoId;
    }

    public PedidoProductoId() {
    }

    public String getPedidoId() {
        return pedidoId;
    }

    public void setPedidoId(String pedidoId) {
        this.pedidoId = pedidoId;
    }

    public String getProductoId() {
        return productoId;
    }

    public void setProductoId(String productoId) {
        this.productoId = productoId;
    }

    @Override
    public String toString() {
        return "PedidoProductoId{" + "pedidoId=" + pedidoId + ", productoId=" + productoId + '}';
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 71 * hash + Objects.hashCode(this.pedidoId);
        hash = 71 * hash + Objects.hashCode(this.productoId);
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
        final PedidoProductoId other = (PedidoProductoId) obj;
        if (!Objects.equals(this.pedidoId, other.pedidoId)) {
            return false;
        }
        return Objects.equals(this.productoId, other.productoId);
    }
    
}
