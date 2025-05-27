/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.zcollect.ZCollect.services;

import com.zcollect.ZCollect.entitites.Reseña;
import com.zcollect.ZCollect.repositories.ReseñaRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Usuario
 */
@Service
public class ReseñaService {
    @Autowired
    private ReseñaRepository reseñaRepository;

    public List<Reseña> getAllReseñas() {
        return reseñaRepository.findAll();
    }

    public Optional<Reseña> getReseñaById(String id) {
        return reseñaRepository.findById(id);
    }

    public Reseña saveReseña(Reseña reseña) {
        return reseñaRepository.save(reseña);
    }

    public void deleteReseña(String id) {
        reseñaRepository.deleteById(id);
        
    }
    public List<Reseña> obtenerPorProducto(String productoId) {
        return reseñaRepository.findByProductoId(productoId);
    }

    public Reseña guardarReseña(Reseña reseña) {
        return reseñaRepository.save(reseña);
    }

    public void eliminarReseña(String id) {
        reseñaRepository.deleteById(id);
    }
}
