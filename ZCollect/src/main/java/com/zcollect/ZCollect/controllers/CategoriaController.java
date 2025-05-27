/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.zcollect.ZCollect.controllers;

import com.zcollect.ZCollect.entitites.Categoria;
import com.zcollect.ZCollect.entitites.Producto;
import com.zcollect.ZCollect.services.CategoriaService;
import com.zcollect.ZCollect.services.ProductoService;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
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
@RequestMapping("/categorias")
@CrossOrigin(origins = "http://localhost:4200", allowCredentials = "true")
public class CategoriaController {

    @Autowired
    private CategoriaService categoriaService;

    @Autowired
    private ProductoService productoService;

    // Obtener todas las categorías
    @GetMapping
    public List<Categoria> getAllCategorias() {
        return categoriaService.getAllCategorias();
    }

    // Obtener categoría por ID
    @GetMapping("/{id}")
    public Optional<Categoria> getCategoriaById(@PathVariable String id) {
        return categoriaService.getCategoriaById(id);
    }

    // Crear nueva categoría
    @PostMapping
    public Categoria crearCategoria(@RequestBody Categoria categoria) {
        return categoriaService.saveCategoria(categoria);
    }

    // Editar categoría existente
    @PutMapping("/{id}")
    public Categoria actualizarCategoria(@PathVariable String id, @RequestBody Categoria categoriaActualizada) {
        return categoriaService.updateCategoria(id, categoriaActualizada);
    }

    // Eliminar categoría
    @DeleteMapping("/{id}")
    public void eliminarCategoria(@PathVariable String id) {
        categoriaService.deleteCategoria(id);
    }

    @GetMapping("/{id}/productos")
    public List<Producto> getProductosByCategoria(@PathVariable String id) {
        return productoService.findByCategoriaId(id);
    }
}
