/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.zcollect.ZCollect.repositories;

import com.zcollect.ZCollect.entitites.Carrito;
import com.zcollect.ZCollect.entitites.Usuario;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author Usuario
 */
public interface CarritoRepository extends JpaRepository<Carrito, String>{
    Optional<Carrito> findByUsuario(Usuario usuario);

}
