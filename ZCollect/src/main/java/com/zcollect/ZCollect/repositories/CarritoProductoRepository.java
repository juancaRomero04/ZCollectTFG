/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.zcollect.ZCollect.repositories;

import com.zcollect.ZCollect.entitites.Carrito;
import com.zcollect.ZCollect.entitites.CarritoProducto;
import com.zcollect.ZCollect.entitites.CarritoProductoId;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author Usuario
 */
public interface CarritoProductoRepository extends JpaRepository<CarritoProducto, CarritoProductoId> {

    void deleteById_CarritoIdAndId_ProductoId(String carritoId, String productoId);
    void deleteById_CarritoId(String carritoId);
    Optional<CarritoProducto> findById_CarritoIdAndId_ProductoId(String carritoId, String productoId);
    void deleteAllByCarrito(Carrito carrito);
}
