/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.zcollect.ZCollect.services;

import com.zcollect.ZCollect.entitites.Categoria;
import com.zcollect.ZCollect.repositories.CategoriaRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Usuario
 */
@Service
public class CategoriaService {
    @Autowired
    private CategoriaRepository categoriaRepository;

    public List<Categoria> getAllCategorias() {
        return categoriaRepository.findAll();
    }

    public Optional<Categoria> getCategoriaById(String id) {
        return categoriaRepository.findById(id);
    }

    public Categoria saveCategoria(Categoria categoria) {
        return categoriaRepository.save(categoria);
    }

    public void deleteCategoria(String id) {
        categoriaRepository.deleteById(id);
    }
    public Categoria updateCategoria(String id, Categoria categoriaActualizada) {
        Optional<Categoria> optional = categoriaRepository.findById(id);
        if (optional.isPresent()) {
            Categoria categoria = optional.get();
            categoria.setNombre(categoriaActualizada.getNombre());
            return categoriaRepository.save(categoria);
        } else {
            return null;
        }
    }
}
