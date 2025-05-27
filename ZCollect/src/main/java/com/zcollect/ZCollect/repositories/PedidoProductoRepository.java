/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.zcollect.ZCollect.repositories;

import com.zcollect.ZCollect.entitites.PedidoProducto;
import com.zcollect.ZCollect.entitites.PedidoProductoId;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author Usuario
 */
public interface PedidoProductoRepository extends JpaRepository<PedidoProducto, PedidoProductoId>{
    
}
