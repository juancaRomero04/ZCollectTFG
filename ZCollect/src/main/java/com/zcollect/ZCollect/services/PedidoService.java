/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.zcollect.ZCollect.services;

import com.zcollect.ZCollect.DTOs.PedidoDTO;
import com.zcollect.ZCollect.DTOs.PedidoProductoDTO;
import com.zcollect.ZCollect.DTOs.TarjetaDTO;
import com.zcollect.ZCollect.entitites.Carrito;
import com.zcollect.ZCollect.entitites.CarritoProducto;
import com.zcollect.ZCollect.entitites.Pedido;
import com.zcollect.ZCollect.entitites.PedidoProducto;
import com.zcollect.ZCollect.entitites.PedidoProductoId;
import com.zcollect.ZCollect.entitites.Producto;
import com.zcollect.ZCollect.entitites.Usuario;
import com.zcollect.ZCollect.repositories.CarritoProductoRepository;
import com.zcollect.ZCollect.repositories.CarritoRepository;
import com.zcollect.ZCollect.repositories.PedidoProductoRepository;
import com.zcollect.ZCollect.repositories.PedidoRepository;
import com.zcollect.ZCollect.repositories.ProductoRepository;
import com.zcollect.ZCollect.repositories.UsuarioRepository;
import jakarta.mail.MessagingException;
import jakarta.transaction.Transactional;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Usuario
 */
@Service
public class PedidoService {

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private ProductoRepository productoRepository;

    @Autowired
    private CorreoService correoService;

    @Autowired
    private CarritoRepository carritoRepository;

    @Autowired
    private CarritoProductoRepository carritoProductoRepository;

    @Autowired
    private PedidoProductoRepository pedidoProductoRepository;

    @Transactional
    public PedidoDTO crearPedidoDesdeCarrito(String username, TarjetaDTO tarjetaDTO) {
        Usuario usuario = usuarioRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado"));

        List<CarritoProducto> productosEnCarrito = obtenerProductosDelCarrito(usuario);
        if (productosEnCarrito.isEmpty()) {
            throw new IllegalStateException("El carrito está vacío");
        }

        Pedido pedido = new Pedido();
        pedido.setId_pedido(UUID.randomUUID().toString());
        pedido.setFecha_ped(new Date());
        pedido.setEstado("Pendiente");
        pedido.setUsuario(usuario);

        List<PedidoProducto> pedidoProductos = new ArrayList<>();
        BigDecimal total = BigDecimal.ZERO;

        for (CarritoProducto cp : productosEnCarrito) {
            Producto p = cp.getProducto();

            // Verificar y restar stock
            if (p.getStock() < cp.getCantidadProd()) {
                throw new IllegalStateException("Stock insuficiente para el producto: " + p.getNombre());
            }
            p.setStock(p.getStock() - cp.getCantidadProd());
            productoRepository.save(p);

            PedidoProducto pedidoProducto = new PedidoProducto();
            pedidoProducto.setPedido(pedido);
            pedidoProducto.setProducto(p);
            pedidoProducto.setCantidad(cp.getCantidadProd());
            pedidoProducto.setPrecio_unitario(p.getPrecio());

            PedidoProductoId id = new PedidoProductoId(pedido.getId_pedido(), p.getId_producto());
            pedidoProducto.setId(id);

            pedidoProductos.add(pedidoProducto);

            total = total.add(p.getPrecio().multiply(new BigDecimal(cp.getCantidadProd())));
        }

        pedido.setProductos(pedidoProductos);
        pedido.setTotal(total);
        pedido.setFactura("{\"detalle\":\"Factura simulada\"}");

        pedidoRepository.save(pedido);

        // Guardar PedidoProducto
        for (PedidoProducto pp : pedidoProductos) {
            pedidoProductoRepository.save(pp);
        }

        // Limpiar el carrito
        Carrito carrito = carritoRepository.findByUsuario(usuario)
                .orElseThrow(() -> new IllegalStateException("Carrito no encontrado"));
        carritoProductoRepository.deleteAllByCarrito(carrito);

        // Enviar correo
        StringBuilder mensaje = new StringBuilder();
        mensaje.append("Gracias por tu compra.\n\n");
        mensaje.append("ID Pedido: ").append(pedido.getId_pedido()).append("\n");
        mensaje.append("Fecha: ").append(pedido.getFecha_ped()).append("\n");
        mensaje.append("Productos:\n");
        for (PedidoProducto pp : pedido.getProductos()) {
            mensaje.append("- ").append(pp.getProducto().getNombre())
                    .append(" x").append(pp.getCantidad())
                    .append(" - $").append(pp.getPrecio_unitario()).append("\n");
        }
        mensaje.append("Total: $").append(pedido.getTotal()).append("\n");

        correoService.enviarConfirmacionPedido(usuario.getEmail(), mensaje.toString());

        return mapPedidoToDTO(pedido);
    }

    public List<PedidoDTO> obtenerPedidosPorUsuario(String username) {
        List<Pedido> pedidos = pedidoRepository.findByUsuarioUsername(username);
        List<PedidoDTO> pedidosDTO = new ArrayList<>();
        for (Pedido pedido : pedidos) {
            pedidosDTO.add(mapPedidoToDTO(pedido));
        }
        return pedidosDTO;
    }

    public List<PedidoDTO> getAllPedidos() {
        List<Pedido> pedidos = pedidoRepository.findAll();
        List<PedidoDTO> pedidosDTO = new ArrayList<>();
        for (Pedido pedido : pedidos) {
            pedidosDTO.add(mapPedidoToDTO(pedido));
        }
        return pedidosDTO;
    }

    public Optional<PedidoDTO> getPedidoById(String id) {
        return pedidoRepository.findById(id).map(this::mapPedidoToDTO);
    }

    public void deletePedido(String id) {
        pedidoRepository.deleteById(id);
    }

    private List<CarritoProducto> obtenerProductosDelCarrito(Usuario usuario) {
        Optional<Carrito> carritoOpt = carritoRepository.findByUsuario(usuario);
        if (carritoOpt.isEmpty()) {
            return new ArrayList<>();
        }
        Carrito carrito = carritoOpt.get();
        return carritoProductoRepository.findByCarrito(carrito);
    }

    private PedidoDTO mapPedidoToDTO(Pedido pedido) {
        List<PedidoProductoDTO> productosDTO = new ArrayList<>();
        if (pedido.getProductos() != null) {
            for (PedidoProducto pp : pedido.getProductos()) {
                productosDTO.add(new PedidoProductoDTO(
                        pp.getProducto().getId_producto(),
                        pp.getProducto().getNombre(),
                        pp.getCantidad(),
                        pp.getPrecio_unitario()
                ));
            }
        }

        return new PedidoDTO(
                pedido.getId_pedido(),
                pedido.getFecha_ped(),
                pedido.getEstado(),
                pedido.getTotal(),
                pedido.getFactura(),
                pedido.getUsuario() != null ? pedido.getUsuario().getUsername() : null,
                productosDTO
        );
    }

    public List<Pedido> obtenerPedidosPorUsuarioId(String idUsuario) {
        return pedidoRepository.findByUsuarioId_user(idUsuario);
    }

}
