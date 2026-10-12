package com.encomiendas.autenticacion.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.encomiendas.autenticacion.model.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    Optional<Usuario> findByUsername(String username);
}
