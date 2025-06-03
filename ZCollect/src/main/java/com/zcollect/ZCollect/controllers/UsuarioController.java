/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.zcollect.ZCollect.controllers;

import com.zcollect.ZCollect.DTOs.LoginRequest;
import com.zcollect.ZCollect.DTOs.RegisterRequest;
import com.zcollect.ZCollect.DTOs.UsuarioLoginResponseDTO;
import com.zcollect.ZCollect.DTOs.UsuarioResponseDTO;
import com.zcollect.ZCollect.entitites.Rol;
import com.zcollect.ZCollect.entitites.UserRole;
import com.zcollect.ZCollect.entitites.UserRoleId;
import com.zcollect.ZCollect.entitites.Usuario;
import com.zcollect.ZCollect.repositories.RolRepository;
import com.zcollect.ZCollect.repositories.UsuarioRepository;
import com.zcollect.ZCollect.services.UsuarioService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Usuario
 */
@RestController
@RequestMapping("/usuarios")
@CrossOrigin(origins = "http://localhost:4200", allowCredentials = "true")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private RolRepository rolRepository;
    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping("/registro")
    public ResponseEntity<?> registrarUsuario(@Valid @RequestBody RegisterRequest dto) {
        try {
            Usuario usuario = new Usuario();
            usuario.setUsername(dto.getUsername());
            usuario.setEmail(dto.getEmail());
            usuario.setPassword(dto.getPassword());
            usuario.setTelefono(dto.getTelefono());
            usuario.setDireccion(dto.getDireccion());

            Usuario registrado = usuarioService.registrarUsuario(usuario);

            Rol rolUser = rolRepository.findByNombre("ROLE_USER")
                    .orElseThrow(() -> new RuntimeException("Rol ROLE_USER no encontrado"));

            UserRole userRole = new UserRole();
            userRole.setId(new UserRoleId(registrado.getId_user(), rolUser.getId_role()));
            userRole.setUsuario(registrado);
            userRole.setRol(rolUser);
            registrado.getRoles().add(userRole);

            usuarioService.saveUsuario(registrado);

            return ResponseEntity.ok(new UsuarioResponseDTO(registrado)); // SOLO devuelve DTO
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    // Ver todos los usuarios (solo admin debería poder)
    @GetMapping
    public List<Usuario> listarUsuarios() {
        return usuarioService.getAllUsuarios();
    }

    // Obtener usuario por ID
    @GetMapping("/{id}")
    public Usuario obtenerUsuario(@PathVariable String id) {
        return usuarioService.getUsuarioById(id).orElse(null);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest, HttpServletRequest request) {
        try {
            // Usa el AuthenticationManager para autenticar
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequest.getUsername(),
                            loginRequest.getPassword()
                    )
            );

            // Forzar creación de sesión con el usuario autenticado
            request.getSession(true).setAttribute("SPRING_SECURITY_CONTEXT",
                    new org.springframework.security.core.context.SecurityContextImpl(authentication));

            Usuario usuario = usuarioService.getByUsername(loginRequest.getUsername());
            UsuarioLoginResponseDTO dto = new UsuarioLoginResponseDTO(usuario);
            return ResponseEntity.ok(dto);

        } catch (AuthenticationException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("error", "Credenciales inválidas"));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarUsuario(@PathVariable String id, Authentication authentication) {
        System.out.println("Eliminar usuario con ID: " + id);
        if (authentication == null) {
            System.out.println("Authentication es null, no autenticado");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("error", "No autenticado"));
        }
        System.out.println("Usuario autenticado: " + authentication.getName());

        Optional<Usuario> usuarioOpt = usuarioService.getUsuarioById(id);
        if (usuarioOpt.isEmpty()) {
            System.out.println("Usuario no encontrado con ID: " + id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", "Usuario no encontrado"));
        }

        Usuario usuario = usuarioOpt.get();

        boolean esAdmin = authentication.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));

        if (!esAdmin && !usuario.getUsername().equals(authentication.getName())) {
            System.out.println("No autorizado para eliminar este usuario: " + authentication.getName());
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(Map.of("error", "No autorizado para eliminar este usuario"));
        }

        try {
            usuarioService.deleteUsuario(id);
            System.out.println("Usuario eliminado correctamente: " + id);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Error al eliminar usuario: " + e.getMessage()));
        }

        return ResponseEntity.ok(Map.of("mensaje", "Usuario eliminado correctamente"));
    }
    
}
