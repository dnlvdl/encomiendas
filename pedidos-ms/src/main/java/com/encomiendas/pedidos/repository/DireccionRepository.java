package com.encomiendas.pedidos.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.encomiendas.pedidos.model.Direccion;

public interface DireccionRepository extends JpaRepository<Direccion, Long> {

    List<Direccion> findByComunaId(Long comunaId);
}
