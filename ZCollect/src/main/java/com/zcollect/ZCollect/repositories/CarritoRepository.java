/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.zcollect.ZCollect.repositories;

import com.zcollect.ZCollect.entitites.Carrito;
import com.zcollect.ZCollect.entitites.Usuario;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 *
 * @author Usuario
 */
public interface CarritoRepository extends JpaRepository<Carrito, String>{
    Optional<Carrito> findByUsuario(Usuario usuario);
    @Query("SELECT c FROM Carrito c ORDER BY c.id_carrito DESC")
    Optional<Carrito> findTopByOrderByIdCarritoDesc();
}
