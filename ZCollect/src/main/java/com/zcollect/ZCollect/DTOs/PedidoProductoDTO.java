/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.zcollect.ZCollect.DTOs;

import java.math.BigDecimal;

/**
 *
 * @author Usuario
 */
public class PedidoProductoDTO {
    private String productoId; // id del producto
    private String nombreProducto; // nombre para mostrar
    private Integer cantidad;
    private BigDecimal precio_unitario;

    public PedidoProductoDTO() {
    }

    public PedidoProductoDTO(String productoId, String nombreProducto, Integer cantidad, BigDecimal precio_unitario) {
        this.productoId = productoId;
        this.nombreProducto = nombreProducto;
        this.cantidad = cantidad;
        this.precio_unitario = precio_unitario;
    }

    // getters y setters
    public String getProductoId() {
        return productoId;
    }

    public void setProductoId(String productoId) {
        this.productoId = productoId;
    }

    public String getNombreProducto() {
        return nombreProducto;
    }

    public void setNombreProducto(String nombreProducto) {
        this.nombreProducto = nombreProducto;
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
}
