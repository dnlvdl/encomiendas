package com.encomiendas.pedidos.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.encomiendas.pedidos.model.Persona;

public interface PersonaRepository extends JpaRepository<Persona, Long> {

    Optional<Persona> findByRut(String rut);
}
