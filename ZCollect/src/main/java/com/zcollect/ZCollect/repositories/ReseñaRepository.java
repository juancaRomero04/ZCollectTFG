/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.zcollect.ZCollect.repositories;

import com.zcollect.ZCollect.entitites.Reseña;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 *
 * @author Usuario
 */
public interface ReseñaRepository extends JpaRepository<Reseña, String>{
    @Query("SELECT r FROM Reseña r WHERE r.producto.id_producto = :idProducto")
    List<Reseña> findByProductoId(@Param("idProducto") String idProducto);

}
