/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.zcollect.ZCollect.controllers;

import com.zcollect.ZCollect.entitites.Reseña;
import com.zcollect.ZCollect.services.ReseñaService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Usuario
 */
@RestController
@RequestMapping("/reseñas")
public class ReseñaController {
    @Autowired
    private ReseñaService reseñaService;

    // Obtener reseñas por producto
    @GetMapping("/producto/{productoId}")
    public List<Reseña> obtenerPorProducto(@PathVariable String productoId) {
        return reseñaService.obtenerPorProducto(productoId);
    }

    // Crear nueva reseña
    @PostMapping
    public Reseña crearReseña(@RequestBody Reseña reseña) {
        return reseñaService.guardarReseña(reseña);
    }

    // Eliminar reseña por ID
    @DeleteMapping("/{id}")
    public void eliminarReseña(@PathVariable String id) {
        reseñaService.eliminarReseña(id);
    }
}
