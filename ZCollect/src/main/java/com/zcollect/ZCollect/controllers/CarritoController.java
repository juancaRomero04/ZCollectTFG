/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.zcollect.ZCollect.controllers;

import com.zcollect.ZCollect.entitites.Carrito;
import com.zcollect.ZCollect.services.CarritoService;
import com.zcollect.ZCollect.services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Usuario
 */
@RestController
@RequestMapping("/carrito")
public class CarritoController {
    @Autowired
    private CarritoService carritoService;


    // Obtener el carrito de un usuario
    @GetMapping("/{idUsuario}")
    public Carrito verCarrito(@PathVariable String idUsuario) {
        return carritoService.obtenerCarritoPorUsuario(idUsuario);
    }

    // Añadir producto al carrito
    @PostMapping("/agregar")
    public Carrito agregarProducto(@RequestParam String idUsuario,
                                   @RequestParam String idProducto,
                                   @RequestParam int cantidad) {
        return carritoService.agregarProductoAlCarrito(idUsuario, idProducto, cantidad);
    }

    // Eliminar producto del carrito
    @DeleteMapping("/eliminar")
    public Carrito eliminarProducto(@RequestParam String idUsuario,
                                    @RequestParam String idProducto) {
        return carritoService.eliminarProductoDelCarrito(idUsuario, idProducto);
    }

    // Vaciar carrito
    @DeleteMapping("/vaciar/{idUsuario}")
    public void vaciarCarrito(@PathVariable String idUsuario) {
        carritoService.vaciarCarrito(idUsuario);
    }
}
