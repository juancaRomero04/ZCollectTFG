/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.zcollect.ZCollect.services;

import com.zcollect.ZCollect.entitites.CarritoProducto;
import com.zcollect.ZCollect.entitites.CarritoProductoId;
import com.zcollect.ZCollect.repositories.CarritoProductoRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Usuario
 */
@Service
public class CarritoProductoService {
    @Autowired
    private CarritoProductoRepository carritoProductoRepository;

    public List<CarritoProducto> getAllCarritoProductos() {
        return carritoProductoRepository.findAll();
    }

    public Optional<CarritoProducto> getById(CarritoProductoId id) {
        return carritoProductoRepository.findById(id);
    }

    public CarritoProducto save(CarritoProducto carritoProducto) {
        return carritoProductoRepository.save(carritoProducto);
    }

    public void deleteById(CarritoProductoId id) {
        carritoProductoRepository.deleteById(id);
    }
}
