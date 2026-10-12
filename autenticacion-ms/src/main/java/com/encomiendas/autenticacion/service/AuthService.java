package com.encomiendas.autenticacion.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.encomiendas.autenticacion.dto.AuthDto;
import com.encomiendas.autenticacion.exception.CredencialesInvalidasException;
import com.encomiendas.autenticacion.model.Usuario;
import com.encomiendas.autenticacion.repository.UsuarioRepository;

@Service
public class AuthService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthService(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // Mismo mensaje para usuario inexistente o clave incorrecta, para no revelar cuál falló.
    @Transactional
    public AuthDto.Respuesta login(AuthDto.Login datos) {
        Usuario usuario = usuarioRepository.findByUsername(datos.username())
                .orElseThrow(CredencialesInvalidasException::new);

        if (!usuario.isActivo() || !passwordEncoder.matches(datos.password(), usuario.getPasswordHash())) {
            throw new CredencialesInvalidasException();
        }

        usuario.registrarAcceso();
        return AuthDto.Respuesta.desde(usuario);
    }
}
