package com.encomiendas.pedidos.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.encomiendas.pedidos.model.Pedido;

public interface PedidoRepository extends JpaRepository<Pedido, Long> {

    Optional<Pedido> findByCodigoSeguimiento(String codigoSeguimiento);

    List<Pedido> findByEstado(Pedido.Estado estado);
}
