/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.zcollect.ZCollect.entitites;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 *
 * @author juanca
 */
@Entity
@Table(name = "Producto")
public class Producto {

    @Id
    @Size(min = 1, max = 36)
    private String id_producto;

    @NotBlank
    @Size(max = 100)
    private String nombre;

    @Size(max = 1000)
    private String descripcion;

    @NotNull
    @DecimalMin(value = "0.0", inclusive = false)
    private BigDecimal precio;

    @NotNull
    @Min(0)
    private int stock;

    @Size(max = 255)
    private String img_url;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_cat", nullable = false)
    @JsonIgnoreProperties("productos")
    private Categoria categoria;

    @OneToMany(mappedBy = "producto", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<PedidoProducto> pedidos;

    @OneToMany(mappedBy = "producto", cascade = CascadeType.ALL)
    @JsonBackReference
    private List<CarritoProducto> carritos;

    @OneToMany(mappedBy = "producto", cascade = CascadeType.ALL)
    private List<Reseña> reseñas;

    public Producto(String id_producto, String nombre, String descripcion, BigDecimal precio, int stock, String img_url, Categoria categoria) {
        this.id_producto = id_producto;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.precio = precio;
        this.stock = stock;
        this.img_url = img_url;
        this.categoria = categoria;
        this.pedidos = new ArrayList<>();
        this.carritos = new ArrayList<>();
        this.reseñas = new ArrayList<>();
    }

    public Producto(String id_producto) {
        this.id_producto = id_producto;
    }

    public Producto() {
    }

    public String getId_producto() {
        return id_producto;
    }

    public void setId_producto(String id_producto) {
        this.id_producto = id_producto;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public BigDecimal getPrecio() {
        return precio;
    }

    public void setPrecio(BigDecimal precio) {
        this.precio = precio;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public String getImg_url() {
        return img_url;
    }

    public void setImg_url(String img_url) {
        this.img_url = img_url;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    public List<PedidoProducto> getPedidos() {
        return pedidos;
    }

    public void setPedidos(List<PedidoProducto> pedidos) {
        this.pedidos = pedidos;
    }

    public List<CarritoProducto> getCarritos() {
        return carritos;
    }

    public void setCarritos(List<CarritoProducto> carritos) {
        this.carritos = carritos;
    }

    public List<Reseña> getReseñas() {
        return reseñas;
    }

    public void setReseñas(List<Reseña> reseñas) {
        this.reseñas = reseñas;
    }

    @Override
    public String toString() {
        return "Producto{id_producto='" + id_producto + "', nombre='" + nombre + "', precio=" + precio + ", stock=" + stock + "}";
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 97 * hash + Objects.hashCode(this.id_producto);
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
        final Producto other = (Producto) obj;
        return Objects.equals(this.id_producto, other.id_producto);
    }

}
