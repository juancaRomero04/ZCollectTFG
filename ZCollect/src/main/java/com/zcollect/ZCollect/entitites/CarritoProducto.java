/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.zcollect.ZCollect.entitites;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Objects;

/**
 *
 * @author juanca
 */
@Entity
@Table(name = "Carrito_Producto")
public class CarritoProducto {

    @EmbeddedId
    private CarritoProductoId id;

    @ManyToOne
    @MapsId("carritoId")
    @JoinColumn(name = "id_carrito")
    private Carrito carrito;

    @ManyToOne
    @MapsId("productoId")
    @JoinColumn(name = "id_producto")
    private Producto producto;

    @NotNull
    @Min(1)
    @Column(name = "cantidad_prod")
    private int cantidadProd;

    @NotNull
    @DecimalMin("0.1")
    private BigDecimal precio;

    public CarritoProducto(CarritoProductoId id, Carrito carrito, Producto producto, int cantidadProd, BigDecimal precio) {
        this.id = id;
        this.carrito = carrito;
        this.producto = producto;
        this.cantidadProd = cantidadProd;
        this.precio = precio;
    }

    public CarritoProducto(CarritoProductoId id) {
        this.id = id;
    }

    public CarritoProducto() {
    }

    public CarritoProductoId getId() {
        return id;
    }

    public void setId(CarritoProductoId id) {
        this.id = id;
    }

    public Carrito getCarrito() {
        return carrito;
    }

    public void setCarrito(Carrito carrito) {
        this.carrito = carrito;
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    public int getCantidadProd() {
        return cantidadProd;
    }

    public void setCantidadProd(int cantidadProd) {
        this.cantidadProd = cantidadProd;
    }

    public BigDecimal getPrecio() {
        return precio;
    }

    public void setPrecio(BigDecimal precio) {
        this.precio = precio;
    }

    @Override
    public String toString() {
        return "CarritoProducto{"
                + "id=" + id
                + ", producto=" + (producto != null ? producto.getId_producto() : "null")
                + ", cantidadProd=" + cantidadProd
                + ", precio=" + precio
                + '}';
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 23 * hash + Objects.hashCode(this.id);
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
        final CarritoProducto other = (CarritoProducto) obj;
        return Objects.equals(this.id, other.id);
    }

}
