/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.zcollect.ZCollect.repositories;

import com.zcollect.ZCollect.entitites.Pedido;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 *
 * @author Usuario
 */
public interface PedidoRepository extends JpaRepository<Pedido, String>{
    List<Pedido> findByUsuarioUsername(String username);
    
    @Query("SELECT p FROM Pedido p WHERE p.usuario.id_user = :idUser")
    List<Pedido> findByUsuarioId_user(String idUser);

}
