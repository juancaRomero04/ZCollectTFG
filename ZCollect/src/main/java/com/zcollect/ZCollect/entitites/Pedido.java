/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.zcollect.ZCollect.entitites;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 *
 * @author juanca
 */
@Entity
@Table(name = "Pedido")
public class Pedido {
    @Id
    @Size(min = 1, max = 36)
    private String id_pedido;

    @NotNull
    @Temporal(TemporalType.DATE)
    private Date fecha_ped;

    @NotNull
    @Pattern(regexp = "Pendiente|Enviado|Entregado")
    private String estado;

    @NotNull
    @DecimalMin("0.0")
    private BigDecimal total;

    @Lob
    private String factura; // Almacenarás JSON como String

    // Relaciones
    @ManyToOne
    @JoinColumn(name = "id_user")
    @JsonBackReference("pedido-usuario")
    private Usuario usuario;

    @OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL)
    private List<PedidoProducto> productos;

    public Pedido(String id_pedido, Date fecha_ped, String estado, BigDecimal total, String factura, Usuario usuario) {
        this.id_pedido = id_pedido;
        this.fecha_ped = fecha_ped;
        this.estado = estado;
        this.total = total;
        this.factura = factura;
        this.usuario = usuario;
        this.productos = new ArrayList<>();
    }

    public Pedido(String id_pedido) {
        this.id_pedido = id_pedido;
    }

    public Pedido() {
    }

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

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public List<PedidoProducto> getProductos() {
        return productos;
    }

    public void setProductos(List<PedidoProducto> productos) {
        this.productos = productos;
    }

    @Override
    public String toString() {
        return "Pedido{" + "id_pedido=" + id_pedido + ", fecha_ped=" + fecha_ped + ", estado=" + estado + ", total=" + total + ", factura=" + factura + ", usuario=" + usuario + ", productos=" + productos + '}';
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + Objects.hashCode(this.id_pedido);
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
        final Pedido other = (Pedido) obj;
        return Objects.equals(this.id_pedido, other.id_pedido);
    }
    
    
    
}
