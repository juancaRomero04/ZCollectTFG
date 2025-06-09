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
            producto.setId_producto(generarNuevoIdProducto());
        }

        return productoRepository.save(producto);
    }

    public void deleteProducto(String id) {
        productoRepository.deleteById(id);
    }

    public List<Producto> findByCategoriaId(String id) {
        return productoRepository.findByCategoria(id);
    }

    private String generarNuevoIdProducto() {
        List<Producto> productos = productoRepository.findAll();
        int max = 0;

        for (Producto p : productos) {
            String id = p.getId_producto();
            if (id != null && id.startsWith("prod-")) {
                try {
                    int num = Integer.parseInt(id.substring(5));
                    if (num > max) max = num;
                } catch (NumberFormatException e) {
                    // ignorar
                }
            }
        }

        return "prod-" + (max + 1);
    }
}
