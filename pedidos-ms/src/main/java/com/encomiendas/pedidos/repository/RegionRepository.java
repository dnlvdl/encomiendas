package com.encomiendas.pedidos.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.encomiendas.pedidos.model.Region;

public interface RegionRepository extends JpaRepository<Region, Long> {

    Optional<Region> findByNombre(String nombre);
}
