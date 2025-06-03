/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.zcollect.ZCollect.controllers;

import com.zcollect.ZCollect.entitites.Producto;
import com.zcollect.ZCollect.repositories.ProductoRepository;
import com.zcollect.ZCollect.services.ProductoService;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
    private ProductoService productoService;
    
    @Autowired
    private ProductoRepository productoRepository;
    //  Obtener todos los productos (para catálogo)
    @GetMapping
    public List<Producto> listarProductos() {
        return productoService.getAllProductos();
    }

    //  Ver detalle de un producto
    @GetMapping("/{id}")
    public ResponseEntity<Producto> obtenerProducto(@PathVariable String id) {
        Optional<Producto> producto = productoService.getProductoById(id);
        return producto.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    //  Crear producto (solo ADMIN)
    @PostMapping
    public Producto crearProducto(@RequestBody Producto producto) {
        return productoService.saveProducto(producto);
    }

    //  Editar producto (solo ADMIN)
    @PutMapping("/{id}")
    public ResponseEntity<Producto> actualizarProducto(@PathVariable String id, @RequestBody Producto datos) {
        return productoService.getProductoById(id).map(producto -> {
            producto.setNombre(datos.getNombre());
            producto.setDescripcion(datos.getDescripcion());
            producto.setPrecio(datos.getPrecio());
            producto.setImg_url(datos.getImg_url());
            producto.setCategoria(datos.getCategoria());
            producto.setStock(datos.getStock());
            return ResponseEntity.ok(productoService.saveProducto(producto));
        }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, String>> eliminarProducto(@PathVariable String id) {
        
        if (!productoRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        productoService.deleteProducto(id);
        Map<String, String> response = new HashMap<>();
        response.put("mensaje", "Producto eliminado correctamente");
        return ResponseEntity.ok(response); // 200 OK con JSON
    }

}
