package com.encomiendas.logistica.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.encomiendas.logistica.model.Asignacion;

public interface AsignacionRepository extends JpaRepository<Asignacion, Long> {

    List<Asignacion> findByVehiculoId(Long vehiculoId);

    Optional<Asignacion> findByPedidoIdAndEstado(Long pedidoId, Asignacion.Estado estado);
}
