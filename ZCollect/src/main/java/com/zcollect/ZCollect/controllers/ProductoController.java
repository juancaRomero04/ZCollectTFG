/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.zcollect.ZCollect.controllers;

import com.zcollect.ZCollect.entitites.Producto;
import com.zcollect.ZCollect.repositories.ProductoRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Usuario
 */
@RestController
@RequestMapping("/productos")
@CrossOrigin(origins = "http://localhost:4200", allowCredentials = "true")
public class ProductoController {
    @Autowired
    private ProductoRepository productoRepository;

    //  Obtener todos los productos (para catálogo)
    @GetMapping
    public List<Producto> listarProductos() {
        return productoRepository.findAll();
    }
    //  Ver detalle de un producto
    @GetMapping("/{id}")
    public ResponseEntity<Producto> obtenerProducto(@PathVariable String id) {
        Optional<Producto> producto = productoRepository.findById(id);
        return producto.map(ResponseEntity::ok)
                       .orElse(ResponseEntity.notFound().build());
    }

    //  Crear producto (solo ADMIN)
    @PostMapping
    public Producto crearProducto(@RequestBody Producto producto) {
        return productoRepository.save(producto);
    }

    //  Editar producto (solo ADMIN)
    @PutMapping("/{id}")
    public ResponseEntity<Producto> actualizarProducto(@PathVariable String id, @RequestBody Producto datos) {
        return productoRepository.findById(id).map(producto -> {
            producto.setNombre(datos.getNombre());
            producto.setDescripcion(datos.getDescripcion());
            producto.setPrecio(datos.getPrecio());
            producto.setImg_url(datos.getImg_url());
            producto.setCategoria(datos.getCategoria());
            return ResponseEntity.ok(productoRepository.save(producto));
        }).orElse(ResponseEntity.notFound().build());
    }

    //  Eliminar producto (solo ADMIN)
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarProducto(@PathVariable String id) {
        if (!productoRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        productoRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
