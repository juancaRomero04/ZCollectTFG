/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.zcollect.ZCollect.services;

import com.zcollect.ZCollect.entitites.Usuario;
import com.zcollect.ZCollect.repositories.UsuarioRepository;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 *
 * @author Usuario
 */
@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public List<Usuario> getAllUsuarios() {
        return usuarioRepository.findAll();
    }

    public Optional<Usuario> getUsuarioById(String id) {
        return usuarioRepository.findById(id);
    }

    public Usuario saveUsuario(Usuario usuario) {
        return usuarioRepository.save(usuario);
    }

    public void deleteUsuario(String id) {
        usuarioRepository.deleteById(id);
    }

    public Usuario autenticarUsuario(String username, String password) {
        Optional<Usuario> usuarioOpt = usuarioRepository.findByUsername(username);
        if (usuarioOpt.isPresent()) {
            Usuario usuario = usuarioOpt.get();
            if (passwordEncoder.matches(password, usuario.getPassword())) {
                return usuario;
            }
        }
        return null;
    }

    public Usuario registrarUsuario(Usuario nuevoUsuario) {
        if (usuarioRepository.existsByEmail(nuevoUsuario.getEmail())) {
            throw new RuntimeException("El correo ya está registrado");
        }

        long totalUsuarios = usuarioRepository.count();
        String nuevoId = "user-" + (totalUsuarios + 1);
        while (usuarioRepository.existsById(nuevoId)) {
            totalUsuarios++;
            nuevoId = "user-" + (totalUsuarios + 1);
        }

        nuevoUsuario.setId_user(nuevoId);
        nuevoUsuario.setPassword(passwordEncoder.encode(nuevoUsuario.getPassword()));
        nuevoUsuario.setFecha_registro(Date.valueOf(LocalDate.now()));
        return usuarioRepository.save(nuevoUsuario);
    }

    public Usuario getByUsername(String username) {
        return usuarioRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
    }

}
