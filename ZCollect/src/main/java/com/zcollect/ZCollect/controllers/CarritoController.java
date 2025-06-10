/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.zcollect.ZCollect.controllers;

import com.zcollect.ZCollect.entitites.Carrito;
import com.zcollect.ZCollect.services.CarritoService;
import com.zcollect.ZCollect.services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Usuario
 */
@RestController
@RequestMapping("/carrito")
@CrossOrigin(origins = "http://localhost:4200", allowCredentials = "true")
public class CarritoController {

    @Autowired
    private CarritoService carritoService;

    // Obtener el carrito de un usuario
    @GetMapping("/usuario/{idUsuario}")
    public ResponseEntity<Carrito> verCarrito(@PathVariable String idUsuario) {
        Carrito carrito = carritoService.obtenerCarritoPorUsuario(idUsuario);
        return ResponseEntity.ok(carrito);
    }

    @PostMapping("/carrito")
    public ResponseEntity<?> guardarCarrito(@RequestBody Carrito carrito) {
        Carrito carritoExistente = carritoService.getCarritoById(carrito.getId_carrito()).orElse(null);

        if (carritoExistente != null) {
            carritoExistente.setProductos(carrito.getProductos());
            carritoService.saveCarrito(carritoExistente);
            return ResponseEntity.ok(carritoExistente);
        } else {
            carritoService.saveCarrito(carrito);
            return ResponseEntity.status(HttpStatus.CREATED).body(carrito);
        }
    }

    @PostMapping("/usuario/{idUsuario}/agregar")
    public ResponseEntity<Carrito> agregarProducto(
            @PathVariable String idUsuario,
            @RequestParam String idProducto,
            @RequestParam int cantidad) {
        Carrito carritoActualizado = carritoService.agregarProductoAlCarrito(idUsuario, idProducto, cantidad);
        return ResponseEntity.ok(carritoActualizado);
    }

    // Eliminar producto del carrito
    @DeleteMapping("/usuario/{idUsuario}/producto/{idProducto}")
    public ResponseEntity<Carrito> eliminarProducto(
            @PathVariable String idUsuario,
            @PathVariable String idProducto) {
        Carrito carritoActualizado = carritoService.eliminarProductoDelCarrito(idUsuario, idProducto);
        return ResponseEntity.ok(carritoActualizado);
    }

    // Vaciar carrito completo
    @DeleteMapping("/usuario/{idUsuario}/vaciar")
    public ResponseEntity<Void> vaciarCarrito(@PathVariable String idUsuario) {
        carritoService.vaciarCarrito(idUsuario);
        return ResponseEntity.noContent().build();
    }

    // Actualizar cantidad producto
    @PostMapping("/actualizarCantidad")
    public ResponseEntity<Carrito> actualizarCantidad(@RequestParam String idUsuario,
            @RequestParam String idProducto,
            @RequestParam int cantidad) {
        Carrito carritoActualizado = carritoService.actualizarCantidadProducto(idUsuario, idProducto, cantidad);
        return ResponseEntity.ok(carritoActualizado);
    }
    @PutMapping("/actualizar/{idUsuario}/{idProducto}")
    public ResponseEntity<Carrito> actualizarCantidadProducto(
            @PathVariable String idUsuario,
            @PathVariable String idProducto,
            @RequestParam int cantidad) {
        try {
            Carrito carritoActualizado = carritoService.actualizarCantidadProducto(idUsuario, idProducto, cantidad);
            return ResponseEntity.ok(carritoActualizado);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }
}
