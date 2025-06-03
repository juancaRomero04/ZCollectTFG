/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.zcollect.ZCollect.services;

import com.zcollect.ZCollect.entitites.Producto;
import com.zcollect.ZCollect.repositories.ProductoRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Usuario
 */
@Service
public class ProductoService {

    @Autowired
    private ProductoRepository productoRepository;

    public List<Producto> getAllProductos() {
        return productoRepository.findAll();
    }

    public Optional<Producto> getProductoById(String id) {
        return productoRepository.findById(id);
    }

    public Producto saveProducto(Producto producto) {
        if (producto.getId_producto() == null || producto.getId_producto().isEmpty()) {
            long totalProductos = productoRepository.count();
            String nuevoId = "prod-" + (totalProductos + 1);

            // Verifica si ya existe ese ID (por si hubo eliminaciones)
            while (productoRepository.existsById(nuevoId)) {
                totalProductos++;
                nuevoId = "prod-" + (totalProductos + 1);
            }

            producto.setId_producto(nuevoId);
        }

        return productoRepository.save(producto);
    }

    public void deleteProducto(String id) {
        productoRepository.deleteById(id);
    }

    public List<Producto> findByCategoriaId(String id) {
        return productoRepository.findByCategoria(id);
    }
}
