package com.encomiendas.pedidos.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.encomiendas.pedidos.model.Sucursal;

public interface SucursalRepository extends JpaRepository<Sucursal, Long> {

    Optional<Sucursal> findByNombre(String nombre);
}
