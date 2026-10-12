package com.encomiendas.logistica.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.encomiendas.logistica.model.Ruta;

public interface RutaRepository extends JpaRepository<Ruta, Long> {

    Optional<Ruta> findByNombre(String nombre);

    List<Ruta> findByComunaId(Long comunaId);
}
