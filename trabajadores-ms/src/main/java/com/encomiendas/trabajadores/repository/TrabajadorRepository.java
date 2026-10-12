package com.encomiendas.trabajadores.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.encomiendas.trabajadores.model.Trabajador;

public interface TrabajadorRepository extends JpaRepository<Trabajador, Long> {

    Optional<Trabajador> findByRut(String rut);
}
