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

        nuevoUsuario.setId_user(generarNuevoIdUsuario());
        nuevoUsuario.setPassword(passwordEncoder.encode(nuevoUsuario.getPassword()));
        nuevoUsuario.setFecha_registro(Date.valueOf(LocalDate.now()));

        return usuarioRepository.save(nuevoUsuario);
    }

    public Usuario getByUsername(String username) {
        return usuarioRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
    }

    public Usuario actualizarUsuario(String id, Usuario datosActualizados) {
        Optional<Usuario> usuarioOpt = usuarioRepository.findById(id);
        if (usuarioOpt.isEmpty()) {
            throw new RuntimeException("Usuario no encontrado");
        }

        Usuario existente = usuarioOpt.get();

        existente.setUsername(datosActualizados.getUsername());
        existente.setEmail(datosActualizados.getEmail());
        existente.setTelefono(datosActualizados.getTelefono());
        existente.setDireccion(datosActualizados.getDireccion());

        if (datosActualizados.getPassword() != null && !datosActualizados.getPassword().isBlank()) {
            existente.setPassword(passwordEncoder.encode(datosActualizados.getPassword()));
        }

        return usuarioRepository.save(existente);
    }

    private String generarNuevoIdUsuario() {
        List<Usuario> usuarios = usuarioRepository.findAll();
        int max = 0;

        for (Usuario u : usuarios) {
            String id = u.getId_user();
            if (id != null && id.startsWith("user-")) {
                try {
                    int num = Integer.parseInt(id.substring(5));
                    if (num > max) max = num;
                } catch (NumberFormatException e) {
                    // ignorar
                }
            }
        }

        return "user-" + (max + 1);
    }

}
