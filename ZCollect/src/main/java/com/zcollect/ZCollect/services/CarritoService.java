/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.zcollect.ZCollect.services;

import com.zcollect.ZCollect.entitites.Carrito;
import com.zcollect.ZCollect.entitites.CarritoProducto;
import com.zcollect.ZCollect.entitites.CarritoProductoId;
import com.zcollect.ZCollect.entitites.Producto;
import com.zcollect.ZCollect.entitites.Usuario;
import com.zcollect.ZCollect.repositories.CarritoProductoRepository;
import com.zcollect.ZCollect.repositories.CarritoRepository;
import com.zcollect.ZCollect.repositories.ProductoRepository;
import com.zcollect.ZCollect.repositories.UsuarioRepository;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;
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

    // Método para generar el ID con formato cart-<número>
    private String generarNuevoIdCarrito() {
        Optional<Carrito> ultimoCarritoOpt = carritoRepository.findTopByOrderByIdCarritoDesc();
        int nuevoNumero = 1;
        if (ultimoCarritoOpt.isPresent()) {
            String ultimoId = ultimoCarritoOpt.get().getId_carrito();
            try {
                String numeroStr = ultimoId.replace("cart-", "");
                nuevoNumero = Integer.parseInt(numeroStr) + 1;
            } catch (NumberFormatException e) {
                // Si el formato no es el esperado, reiniciamos a 1
                nuevoNumero = 1;
            }
        }
        return "cart-" + nuevoNumero;
    }

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
        Usuario usuario = usuarioRepository.findById(idUsuario)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con ID: " + idUsuario));

        Optional<Carrito> carritoExistente = carritoRepository.findByUsuario(usuario);

        if (carritoExistente.isPresent()) {
            return carritoExistente.get();
        } else {
            Carrito nuevoCarrito = new Carrito();
            nuevoCarrito.setId_carrito(generarNuevoIdCarrito());
            nuevoCarrito.setFechaC(new Date());
            nuevoCarrito.setUsuario(usuario);
            // No hacemos usuario.setCarrito(nuevoCarrito); para evitar conflictos en la sesión
            return carritoRepository.save(nuevoCarrito);
        }
    }

    public Carrito agregarProductoAlCarrito(String idUsuario, String productoId, int cantidad) {
        Usuario usuario = usuarioRepository.findById(idUsuario)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        Producto producto = productoRepository.findById(productoId)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

        Carrito carrito = carritoRepository.findByUsuario(usuario)
                .orElseGet(() -> {
                    Carrito nuevoCarrito = new Carrito();
                    nuevoCarrito.setId_carrito(generarNuevoIdCarrito());
                    nuevoCarrito.setFechaC(new Date());
                    nuevoCarrito.setUsuario(usuario);
                    // No hacer usuario.setCarrito(nuevoCarrito);
                    return carritoRepository.save(nuevoCarrito);
                });

        Optional<CarritoProducto> existenteOpt = carritoProductoRepository
                .findById_CarritoIdAndId_ProductoId(carrito.getId_carrito(), producto.getId_producto());

        if (existenteOpt.isPresent()) {
            CarritoProducto existente = existenteOpt.get();
            existente.setCantidadProd(existente.getCantidadProd() + cantidad);
            carritoProductoRepository.save(existente);
        } else {
            CarritoProductoId carritoProductoId = new CarritoProductoId(carrito.getId_carrito(), producto.getId_producto());

            CarritoProducto nuevo = new CarritoProducto();
            nuevo.setId(carritoProductoId);
            nuevo.setCarrito(carrito);
            nuevo.setProducto(producto);
            nuevo.setCantidadProd(cantidad);
            nuevo.setPrecio(producto.getPrecio());
            carritoProductoRepository.save(nuevo);
        }

        return carritoRepository.findById(carrito.getId_carrito())
                .orElseThrow(() -> new RuntimeException("Error al obtener el carrito actualizado"));
    }

    public Carrito eliminarProductoDelCarrito(String idUsuario, String idProducto) {
        Usuario usuario = usuarioRepository.findById(idUsuario)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        Carrito carrito = carritoRepository.findByUsuario(usuario)
                .orElseThrow(() -> new RuntimeException("Carrito no encontrado para el usuario"));

        Optional<CarritoProducto> carritoProductoOpt = carritoProductoRepository
                .findById_CarritoIdAndId_ProductoId(carrito.getId_carrito(), idProducto);

        carritoProductoOpt.ifPresent(carritoProductoRepository::delete);

        return carritoRepository.findById(carrito.getId_carrito())
                .orElseThrow(() -> new RuntimeException("Error al obtener el carrito actualizado"));
    }

    public void vaciarCarrito(String idUsuario) {
        Usuario usuario = usuarioRepository.findById(idUsuario)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        Carrito carrito = carritoRepository.findByUsuario(usuario)
                .orElseThrow(() -> new RuntimeException("Carrito no encontrado para el usuario"));

        carritoProductoRepository.deleteAllByCarrito(carrito);
    }

    public Carrito actualizarCantidadProducto(String idUsuario, String idProducto, int cantidad) {
        Usuario usuario = usuarioRepository.findById(idUsuario)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        Carrito carrito = carritoRepository.findByUsuario(usuario)
                .orElseThrow(() -> new RuntimeException("Carrito no encontrado para el usuario"));

        Producto producto = productoRepository.findById(idProducto)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

        Optional<CarritoProducto> carritoProductoOpt = carritoProductoRepository
                .findById_CarritoIdAndId_ProductoId(carrito.getId_carrito(), idProducto);

        if (carritoProductoOpt.isPresent()) {
            CarritoProducto carritoProducto = carritoProductoOpt.get();

            if (cantidad < 1) {
                carritoProductoRepository.delete(carritoProducto);
            } else {
                if (cantidad > producto.getStock()) {
                    throw new RuntimeException("La cantidad solicitada supera el stock disponible");
                }
                carritoProducto.setCantidadProd(cantidad);
                carritoProductoRepository.save(carritoProducto);
            }
        } else {
            if (cantidad > 0) {
                if (cantidad > producto.getStock()) {
                    throw new RuntimeException("La cantidad solicitada supera el stock disponible");
                }

                CarritoProductoId carritoProductoId = new CarritoProductoId(carrito.getId_carrito(), producto.getId_producto());

                CarritoProducto nuevo = new CarritoProducto();
                nuevo.setId(carritoProductoId);
                nuevo.setCarrito(carrito);
                nuevo.setProducto(producto);
                nuevo.setCantidadProd(cantidad);
                nuevo.setPrecio(producto.getPrecio());
                carritoProductoRepository.save(nuevo);
            }
        }

        return carritoRepository.findById(carrito.getId_carrito())
                .orElseThrow(() -> new RuntimeException("Error al obtener el carrito actualizado"));
    }

}
