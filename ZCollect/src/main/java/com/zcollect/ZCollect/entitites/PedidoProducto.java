/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.zcollect.ZCollect.entitites;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Objects;

/**
 *
 * @author juanca
 */
@Entity
@Table(name = "Pedido_Producto")
public class PedidoProducto {
    @EmbeddedId
    private PedidoProductoId id;
    @NotNull
    @Min(1)
    private Integer cantidad;

    @NotNull
    @DecimalMin("0.0")
    private BigDecimal precio_unitario;

    // Relaciones
    @ManyToOne
    @MapsId("pedidoId")
    @JoinColumn(name = "pedido_id")
    private Pedido pedido;

    @ManyToOne
    @MapsId("productoId")
    @JoinColumn(name = "producto_id")
    private Producto producto;

    public PedidoProducto(PedidoProductoId id, Integer cantidad, BigDecimal precio_unitario, Pedido pedido, Producto producto) {
        this.id = id;
        this.cantidad = cantidad;
        this.precio_unitario = precio_unitario;
        this.pedido = pedido;
        this.producto = producto;
    }

    public PedidoProducto(PedidoProductoId id) {
        this.id = id;
    }

    public PedidoProducto() {
    }

    public PedidoProductoId getId() {
        return id;
    }

    public void setId(PedidoProductoId id) {
        this.id = id;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    public BigDecimal getPrecio_unitario() {
        return precio_unitario;
    }

    public void setPrecio_unitario(BigDecimal precio_unitario) {
        this.precio_unitario = precio_unitario;
    }

    public Pedido getPedido() {
        return pedido;
    }

    public void setPedido(Pedido pedido) {
        this.pedido = pedido;
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    @Override
    public String toString() {
        return "PedidoProducto{" + "id=" + id + ", cantidad=" + cantidad + ", precio_unitario=" + precio_unitario + ", pedido=" + pedido + ", producto=" + producto + '}';
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 37 * hash + Objects.hashCode(this.id);
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
        final PedidoProducto other = (PedidoProducto) obj;
        return Objects.equals(this.id, other.id);
    }
    
}
