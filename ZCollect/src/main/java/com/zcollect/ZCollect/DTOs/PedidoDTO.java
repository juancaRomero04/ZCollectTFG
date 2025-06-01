/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.zcollect.ZCollect.DTOs;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Usuario
 */
public class PedidoDTO {
    private String id_pedido;
    private Date fecha_ped;
    private String estado;
    private BigDecimal total;
    private String factura;
    private String username; // para identificar el usuario (en vez de entidad completa)
    private List<PedidoProductoDTO> productos;

    public PedidoDTO() {
    }

    public PedidoDTO(String id_pedido, Date fecha_ped, String estado, BigDecimal total, String factura, String username, List<PedidoProductoDTO> productos) {
        this.id_pedido = id_pedido;
        this.fecha_ped = fecha_ped;
        this.estado = estado;
        this.total = total;
        this.factura = factura;
        this.username = username;
        this.productos = productos;
    }

    // getters y setters
    public String getId_pedido() {
        return id_pedido;
    }

    public void setId_pedido(String id_pedido) {
        this.id_pedido = id_pedido;
    }

    public Date getFecha_ped() {
        return fecha_ped;
    }

    public void setFecha_ped(Date fecha_ped) {
        this.fecha_ped = fecha_ped;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public String getFactura() {
        return factura;
    }

    public void setFactura(String factura) {
        this.factura = factura;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public List<PedidoProductoDTO> getProductos() {
        return productos;
    }

    public void setProductos(List<PedidoProductoDTO> productos) {
        this.productos = productos;
    }
}
