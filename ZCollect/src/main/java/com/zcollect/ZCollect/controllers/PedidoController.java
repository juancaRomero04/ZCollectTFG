/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.zcollect.ZCollect.controllers;

import com.zcollect.ZCollect.entitites.Pedido;
import com.zcollect.ZCollect.services.PedidoService;
import java.security.Principal;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Usuario
 */

@RestController
@RequestMapping("/pedidos")
public class PedidoController {
    @Autowired
    private PedidoService pedidoService;

    // Crear pedido desde el carrito del usuario actual
    @PostMapping("/crear")
    public ResponseEntity<Pedido> crearPedido(Principal principal) {
        Pedido pedido = pedidoService.crearPedidoDesdeCarrito(principal.getName());
        return ResponseEntity.ok(pedido);
    }

    // Obtener pedidos del usuario actual
    @GetMapping("/mis-pedidos")
    public ResponseEntity<List<Pedido>> obtenerMisPedidos(Principal principal) {
        List<Pedido> pedidos = pedidoService.obtenerPedidosPorUsuario(principal.getName());
        return ResponseEntity.ok(pedidos);
    }

    // Administrador: obtener todos los pedidos
    @GetMapping
    public ResponseEntity<List<Pedido>> obtenerTodosLosPedidos() {
        List<Pedido> pedidos = pedidoService.getAllPedidos();
        return ResponseEntity.ok(pedidos);
    }

    // Obtener un pedido específico
    @GetMapping("/{id}")
    public ResponseEntity<Pedido> obtenerPedidoPorId(@PathVariable String id) {
        return ResponseEntity.of(pedidoService.getPedidoById(id));
    }

    // Eliminar un pedido (admin o según política de negocio)
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarPedido(@PathVariable Long id) {
        pedidoService.eliminarPedido(id);
        return ResponseEntity.noContent().build();
    }
}
