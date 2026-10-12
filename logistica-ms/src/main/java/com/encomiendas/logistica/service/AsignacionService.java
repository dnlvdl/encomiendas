package com.encomiendas.logistica.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.encomiendas.logistica.dto.AsignacionDto;
import com.encomiendas.logistica.exception.RecursoNoEncontradoException;
import com.encomiendas.logistica.model.Asignacion;
import com.encomiendas.logistica.model.Ruta;
import com.encomiendas.logistica.model.Vehiculo;
import com.encomiendas.logistica.repository.AsignacionRepository;
import com.encomiendas.logistica.repository.RutaRepository;
import com.encomiendas.logistica.repository.VehiculoRepository;

@Service
public class AsignacionService {

    private final AsignacionRepository asignacionRepository;
    private final VehiculoRepository vehiculoRepository;
    private final RutaRepository rutaRepository;

    public AsignacionService(AsignacionRepository asignacionRepository,
                             VehiculoRepository vehiculoRepository,
                             RutaRepository rutaRepository) {
        this.asignacionRepository = asignacionRepository;
        this.vehiculoRepository = vehiculoRepository;
        this.rutaRepository = rutaRepository;
    }

    @Transactional
    public AsignacionDto.Respuesta crear(AsignacionDto.Crear datos) {
        if (asignacionRepository.findByPedidoIdAndEstado(datos.pedidoId(), Asignacion.Estado.ACTIVA).isPresent()) {
            throw new IllegalStateException("El pedido " + datos.pedidoId() + " ya tiene una asignación activa");
        }

        Vehiculo vehiculo = vehiculoRepository.findById(datos.vehiculoId())
                .orElseThrow(() -> new RecursoNoEncontradoException("Vehículo", datos.vehiculoId()));
        Ruta ruta = rutaRepository.findById(datos.rutaId())
                .orElseThrow(() -> new RecursoNoEncontradoException("Ruta", datos.rutaId()));

        if (vehiculo.getEstado() != Vehiculo.Estado.DISPONIBLE) {
            throw new IllegalStateException("El vehículo " + vehiculo.getPatente() + " no está DISPONIBLE");
        }

        boolean ocupado = asignacionRepository.findByVehiculoId(vehiculo.getId()).stream()
                .anyMatch(a -> a.getEstado() == Asignacion.Estado.ACTIVA);
        if (ocupado) {
            throw new IllegalStateException("El vehículo " + vehiculo.getPatente() + " ya tiene una asignación activa");
        }

        if (datos.pesoKg().compareTo(vehiculo.getCapacidadKg()) > 0) {
            throw new IllegalArgumentException("El peso " + datos.pesoKg() + " kg supera la capacidad del vehículo ("
                    + vehiculo.getCapacidadKg() + " kg)");
        }

        Asignacion asignacion = asignacionRepository.save(
                new Asignacion(datos.pedidoId(), vehiculo, ruta, datos.conductorId()));
        return AsignacionDto.Respuesta.desde(asignacion);
    }

    @Transactional(readOnly = true)
    public List<AsignacionDto.Respuesta> listar() {
        return asignacionRepository.findAll().stream()
                .map(AsignacionDto.Respuesta::desde)
                .toList();
    }
}
