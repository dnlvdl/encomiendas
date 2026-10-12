package com.encomiendas.pedidos.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.encomiendas.pedidos.model.HistorialEstado;

public interface HistorialEstadoRepository extends JpaRepository<HistorialEstado, Long> {

    List<HistorialEstado> findByPedidoIdOrderByFechaAsc(Long pedidoId);
}
