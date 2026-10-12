package com.encomiendas.pedidos.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.encomiendas.pedidos.model.Comuna;

public interface ComunaRepository extends JpaRepository<Comuna, Long> {

    List<Comuna> findByRegionId(Long regionId);

    Optional<Comuna> findByRegionIdAndNombre(Long regionId, String nombre);
}
