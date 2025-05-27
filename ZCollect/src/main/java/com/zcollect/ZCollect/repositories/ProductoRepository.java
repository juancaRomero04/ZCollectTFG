/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.zcollect.ZCollect.repositories;

import com.zcollect.ZCollect.entitites.Producto;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 *
 * @author Usuario
 */
public interface ProductoRepository extends JpaRepository<Producto, String> {

    @Query("SELECT p FROM Producto p WHERE p.categoria.id_cat = :idCat")
    List<Producto> findByCategoria(@Param("idCat") String idCat);
    

}
