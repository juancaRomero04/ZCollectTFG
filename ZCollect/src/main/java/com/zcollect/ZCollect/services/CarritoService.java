/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.zcollect.ZCollect.services;

import com.zcollect.ZCollect.entitites.Carrito;
import com.zcollect.ZCollect.entitites.CarritoProducto;
import com.zcollect.ZCollect.entitites.Producto;
import com.zcollect.ZCollect.entitites.Usuario;
import com.zcollect.ZCollect.repositories.CarritoProductoRepository;
import com.zcollect.ZCollect.repositories.CarritoRepository;
import com.zcollect.ZCollect.repositories.ProductoRepository;
import com.zcollect.ZCollect.repositories.UsuarioRepository;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Usuario
 */
@Service
public class CarritoService {

    @Autowired
    private CarritoRepository carritoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private ProductoRepository productoRepository;

    @Autowired
    private CarritoProductoRepository carritoProductoRepository;

    public Optional<Carrito> getCarritoById(String id) {
        return carritoRepository.findById(id);
    }

    public Carrito saveCarrito(Carrito carrito) {
        return carritoRepository.save(carrito);
    }

    public void deleteCarrito(String id) {
        carritoRepository.deleteById(id);
    }

    public Carrito obtenerCarritoPorUsuario(String idUsuario) {
        Optional<Usuario> usuarioOpt = usuarioRepository.findById(idUsuario);
        if (!usuarioOpt.isPresent()) {
            throw new RuntimeException("Usuario no encontrado con ID: " + idUsuario);
        }

        Usuario usuario = usuarioOpt.get();

        Optional<Carrito> carritoExistente = carritoRepository.findByUsuario(usuario);

        if (carritoExistente.isPresent()) {
            return carritoExistente.get();
        } else {
            // Si no hay carrito, creamos uno nuevo
            Carrito nuevoCarrito = new Carrito();
            nuevoCarrito.setUsuario(usuario);
            return carritoRepository.save(nuevoCarrito);
        }
    }

    public Carrito agregarProductoAlCarrito(String username, String productoId, int cantidad) {
        Usuario usuario = usuarioRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        Producto producto = productoRepository.findById(productoId)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

        Carrito carrito = carritoRepository.findByUsuario(usuario)
                .orElseGet(() -> {
                    Carrito nuevoCarrito = new Carrito();
                    nuevoCarrito.setUsuario(usuario);
                    return carritoRepository.save(nuevoCarrito);
                });

        Optional<CarritoProducto> existenteOpt = carritoProductoRepository
                .findById_CarritoIdAndId_ProductoId(carrito.getId_carrito(), producto.getId_producto());

        if (existenteOpt.isPresent()) {
            CarritoProducto existente = existenteOpt.get();
            existente.setCantidadProd(existente.getCantidadProd() + cantidad);
            carritoProductoRepository.save(existente);
        } else {
            CarritoProducto nuevo = new CarritoProducto();
            nuevo.setCarrito(carrito);
            nuevo.setProducto(producto);
            nuevo.setCantidadProd(cantidad);
            carritoProductoRepository.save(nuevo);
        }

        return carritoRepository.findById(carrito.getId_carrito())
                .orElseThrow(() -> new RuntimeException("Error al obtener el carrito actualizado"));
    }

    public Carrito eliminarProductoDelCarrito(String username, String productoId) {
        Usuario usuario = usuarioRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        Carrito carrito = carritoRepository.findByUsuario(usuario)
                .orElseThrow(() -> new RuntimeException("Carrito no encontrado"));

        carritoProductoRepository.findById_CarritoIdAndId_ProductoId(carrito.getId_carrito(), productoId)
                .ifPresent(carritoProductoRepository::delete);

        return carritoRepository.findById(carrito.getId_carrito())
                .orElseThrow(() -> new RuntimeException("Error al obtener el carrito actualizado"));
    }

    public void vaciarCarrito(String username) {
        Usuario usuario = usuarioRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        Carrito carrito = carritoRepository.findByUsuario(usuario)
                .orElseThrow(() -> new RuntimeException("Carrito no encontrado"));

        carritoProductoRepository.deleteById_CarritoId(carrito.getId_carrito());
    }
}
