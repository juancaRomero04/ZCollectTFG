/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.zcollect.ZCollect.entitites;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.Date;
import java.util.Objects;

/**
 *
 * @author juanca
 */
@Entity
@Table(name = "Reseña")
public class Reseña {
    @Id
    @Size(min = 1, max = 36)
    private String id_res;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "id_user")
    @JsonBackReference("usuario-reseñas")
    private Usuario usuario;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "id_producto")
    @JsonBackReference("producto-reseñas")
    private Producto producto;

    @Size(max = 1000)
    private String comentario;

    @Min(1)
    @Max(5)
    private int calificacion;

    @NotNull
    @Temporal(TemporalType.DATE)
    private Date fecha_rese;

    public Reseña(String id_res, Usuario usuario, Producto producto, String comentario, int calificacion, Date fecha_rese) {
        this.id_res = id_res;
        this.usuario = usuario;
        this.producto = producto;
        this.comentario = comentario;
        this.calificacion = calificacion;
        this.fecha_rese = fecha_rese;
    }

    public Reseña(String id_res) {
        this.id_res = id_res;
    }

    public Reseña() {
    }

    public String getId_res() {
        return id_res;
    }

    public void setId_res(String id_res) {
        this.id_res = id_res;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public int getCalificacion() {
        return calificacion;
    }

    public void setCalificacion(int calificacion) {
        this.calificacion = calificacion;
    }

    public Date getFecha_rese() {
        return fecha_rese;
    }

    public void setFecha_rese(Date fecha_rese) {
        this.fecha_rese = fecha_rese;
    }

    @Override
    public String toString() {
        return "Rese\u00f1a{" + "id_res=" + id_res + ", usuario=" + usuario + ", producto=" + producto + ", comentario=" + comentario + ", calificacion=" + calificacion + ", fecha_rese=" + fecha_rese + '}';
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 71 * hash + Objects.hashCode(this.id_res);
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
        final Reseña other = (Reseña) obj;
        return Objects.equals(this.id_res, other.id_res);
    }
    
    
    
}
