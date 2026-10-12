package com.encomiendas.logistica.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.encomiendas.logistica.model.Vehiculo;

public interface VehiculoRepository extends JpaRepository<Vehiculo, Long> {

    Optional<Vehiculo> findByPatente(String patente);

    List<Vehiculo> findByEstado(Vehiculo.Estado estado);
}
