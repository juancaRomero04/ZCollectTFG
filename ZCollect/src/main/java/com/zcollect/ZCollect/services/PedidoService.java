/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.zcollect.ZCollect.services;

import com.zcollect.ZCollect.entitites.Carrito;
import com.zcollect.ZCollect.entitites.CarritoProducto;
import com.zcollect.ZCollect.entitites.Pedido;
import com.zcollect.ZCollect.entitites.PedidoProducto;
import com.zcollect.ZCollect.entitites.Usuario;
import com.zcollect.ZCollect.repositories.CarritoRepository;
import com.zcollect.ZCollect.repositories.PedidoProductoRepository;
import com.zcollect.ZCollect.repositories.PedidoRepository;
import com.zcollect.ZCollect.repositories.UsuarioRepository;
import java.util.Date;
import java.util.List;
import java.util.Optional;
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
    private CarritoRepository carritoRepository;

    @Autowired
    private PedidoProductoRepository pedidoProductoRepository;
    public List<Pedido> getAllPedidos() {
        return pedidoRepository.findAll();
    }

    public Optional<Pedido> getPedidoById(String id) {
        return pedidoRepository.findById(id);
    }

    public Pedido savePedido(Pedido pedido) {
        return pedidoRepository.save(pedido);
    }

    public void deletePedido(String id) {
        pedidoRepository.deleteById(id);
    }
    public Pedido crearPedidoDesdeCarrito(String username) {
        Usuario usuario = usuarioRepository.findByUsername(username).orElseThrow();
        Carrito carrito = carritoRepository.findByUsuario(usuario).orElseThrow();

        if (carrito.getProductos().isEmpty()) {
            throw new IllegalStateException("El carrito está vacío.");
        }

        Pedido pedido = new Pedido();
        pedido.setUsuario(usuario);
        Date fechaActual=new Date();
        
        pedido.setFecha_ped(fechaActual);

        // Guardar pedido para obtener su ID antes de los productos
        Pedido pedidoGuardado = pedidoRepository.save(pedido);

        for (CarritoProducto cp : carrito.getProductos()) {
            PedidoProducto pp = new PedidoProducto();
            pp.setPedido(pedidoGuardado);
            pp.setProducto(cp.getProducto());
            pp.setCantidad(cp.getCantidadProd());
            pedidoProductoRepository.save(pp);
        }

        // Limpiar carrito
        carrito.getProductos().clear();
        carritoRepository.save(carrito);

        return pedidoGuardado;
    }
    public List<Pedido> obtenerPedidosPorUsuario(String username) {
        return pedidoRepository.findByUsuarioUsername(username);
    }
    public void eliminarPedido(Long id) {
        pedidoRepository.deleteById(String.valueOf(id));
    }
}
