/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.zcollect.ZCollect.services;

import com.zcollect.ZCollect.entitites.PedidoProducto;
import com.zcollect.ZCollect.entitites.PedidoProductoId;
import com.zcollect.ZCollect.repositories.PedidoProductoRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Usuario
 */
@Service
public class PedidoProductoService {
    @Autowired
    private PedidoProductoRepository pedidoProductoRepository;

    public List<PedidoProducto> getAllPedidoProductos() {
        return pedidoProductoRepository.findAll();
    }

    public Optional<PedidoProducto> getById(PedidoProductoId id) {
        return pedidoProductoRepository.findById(id);
    }

    public PedidoProducto save(PedidoProducto pedidoProducto) {
        return pedidoProductoRepository.save(pedidoProducto);
    }

    public void deleteById(PedidoProductoId id) {
        pedidoProductoRepository.deleteById(id);
    }
}
