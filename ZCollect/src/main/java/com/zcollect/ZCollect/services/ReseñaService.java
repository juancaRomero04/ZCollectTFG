/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.zcollect.ZCollect.services;

import com.zcollect.ZCollect.DTOs.ReseñaDTO;
import com.zcollect.ZCollect.entitites.Reseña;
import com.zcollect.ZCollect.repositories.ReseñaRepository;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
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

    public void deleteReseña(String id) {
        reseñaRepository.deleteById(id);
    }

    public List<Reseña> obtenerPorProducto(String productoId) {
        return reseñaRepository.findByProductoId(productoId);
    }

    // ✅ Método que retorna las reseñas como DTO con info del usuario
    public List<ReseñaDTO> obtenerDTOsPorProducto(String productoId) {
        return reseñaRepository.findByProductoId(productoId)
                .stream()
                .map(r -> new ReseñaDTO(
                        r.getId_res(),
                        r.getComentario(),
                        r.getCalificacion(),
                        r.getFecha_rese(),
                        r.getUsuario().getUsername(),
                        r.getUsuario().getEmail()
                ))
                .collect(Collectors.toList());
    }

    public Reseña guardarReseña(Reseña reseña) {
        reseña.setId_res(generarIdUnico());
        return reseñaRepository.save(reseña);
    }

    public void eliminarReseña(String id) {
        reseñaRepository.deleteById(id);
    }

    private String generarIdUnico() {
        List<Reseña> todas = reseñaRepository.findAll();
        int max = 0;

        for (Reseña r : todas) {
            if (r.getId_res() != null && r.getId_res().startsWith("res-")) {
                try {
                    int num = Integer.parseInt(r.getId_res().substring(4));
                    if (num > max) max = num;
                } catch (NumberFormatException e) {
                    // ignorar ids no válidas
                }
            }
        }

        return "res-" + (max + 1);
    }
}
