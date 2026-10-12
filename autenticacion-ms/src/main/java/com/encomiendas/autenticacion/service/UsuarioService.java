package com.encomiendas.autenticacion.service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.encomiendas.autenticacion.dto.AuthDto;
import com.encomiendas.autenticacion.dto.UsuarioDto;
import com.encomiendas.autenticacion.exception.RecursoNoEncontradoException;
import com.encomiendas.autenticacion.model.Rol;
import com.encomiendas.autenticacion.model.Usuario;
import com.encomiendas.autenticacion.repository.RolRepository;
import com.encomiendas.autenticacion.repository.UsuarioRepository;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final RolRepository rolRepository;
    private final PasswordEncoder passwordEncoder;

    public UsuarioService(UsuarioRepository usuarioRepository,
                          RolRepository rolRepository,
                          PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.rolRepository = rolRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public AuthDto.Respuesta crear(UsuarioDto.Crear datos) {
        if (usuarioRepository.findByUsername(datos.username()).isPresent()) {
            throw new IllegalStateException("Ya existe un usuario con username " + datos.username());
        }

        Set<Rol> roles = datos.rolesNombres().stream()
                .map(nombre -> rolRepository.findByNombre(nombre)
                        .orElseThrow(() -> new RecursoNoEncontradoException("Rol", nombre)))
                .collect(Collectors.toSet());

        Usuario usuario = usuarioRepository.save(new Usuario(
                datos.username(),
                passwordEncoder.encode(datos.password()),
                datos.trabajadorId(),
                roles));
        return AuthDto.Respuesta.desde(usuario);
    }

    // Lista los usuarios. La clave nunca se devuelve.
    @Transactional(readOnly = true)
    public List<AuthDto.Respuesta> listar() {
        return usuarioRepository.findAll().stream()
                .map(AuthDto.Respuesta::desde)
                .toList();
    }
}
