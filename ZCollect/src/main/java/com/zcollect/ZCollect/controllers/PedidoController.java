/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.zcollect.ZCollect.controllers;

import com.zcollect.ZCollect.DTOs.PedidoDTO;
import com.zcollect.ZCollect.DTOs.TarjetaDTO;
import com.zcollect.ZCollect.entitites.Pedido;
import com.zcollect.ZCollect.services.PedidoService;
import jakarta.validation.Valid;
import java.security.Principal;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
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
@RequestMapping("/pedidos")
@CrossOrigin(origins = "http://localhost:4200", allowCredentials = "true")
public class PedidoController {

    @Autowired
    private PedidoService pedidoService;

    @PostMapping("/crear")
    public ResponseEntity<?> crearPedido(Principal principal, @Valid @RequestBody TarjetaDTO tarjetaDTO) {
        if (principal == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Debe estar autenticado para crear un pedido");
        }

        // Validar fecha de expiración de la tarjeta (MM/aa)
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/yy");
            YearMonth fechaExp = YearMonth.parse(tarjetaDTO.getFechaExpiracion(), formatter);
            YearMonth ahora = YearMonth.now();

            if (fechaExp.isBefore(ahora)) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("La fecha de expiración de la tarjeta no puede ser anterior a la fecha actual.");
            }
        } catch (DateTimeParseException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Formato inválido para la fecha de expiración de la tarjeta.");
        }

        try {
            PedidoDTO pedidoDTO = pedidoService.crearPedidoDesdeCarrito(principal.getName(), tarjetaDTO);
            return ResponseEntity.ok(pedidoDTO);
        } catch (IllegalArgumentException | IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al enviar correo: " + e.getMessage());
        }
    }

    @GetMapping("/mis-pedidos")
    public ResponseEntity<?> obtenerMisPedidos(Principal principal) {
        if (principal == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Debe estar autenticado para ver sus pedidos");
        }

        List<PedidoDTO> pedidos = pedidoService.obtenerPedidosPorUsuario(principal.getName());
        return ResponseEntity.ok(pedidos);
    }

    @GetMapping
    public ResponseEntity<List<PedidoDTO>> obtenerTodosLosPedidos() {
        return ResponseEntity.ok(pedidoService.getAllPedidos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> obtenerPedidoPorId(@PathVariable String id) {
        Optional<PedidoDTO> pedidoDTO = pedidoService.getPedidoById(id);
        if (pedidoDTO.isPresent()) {
            return ResponseEntity.ok(pedidoDTO.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Pedido no encontrado");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarPedido(@PathVariable String id) {
        pedidoService.deletePedido(id);
        return ResponseEntity.noContent().build();
    }

}
