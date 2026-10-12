package com.encomiendas.logistica.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.encomiendas.logistica.dto.VehiculoDto;
import com.encomiendas.logistica.exception.RecursoNoEncontradoException;
import com.encomiendas.logistica.model.Vehiculo;
import com.encomiendas.logistica.repository.VehiculoRepository;

@Service
public class VehiculoService {

    private final VehiculoRepository vehiculoRepository;

    public VehiculoService(VehiculoRepository vehiculoRepository) {
        this.vehiculoRepository = vehiculoRepository;
    }

    @Transactional
    public Vehiculo crear(VehiculoDto.Crear datos) {
        if (vehiculoRepository.findByPatente(datos.patente()).isPresent()) {
            throw new IllegalArgumentException("Ya existe un vehículo con patente " + datos.patente());
        }
        return vehiculoRepository.save(new Vehiculo(datos.patente(), datos.capacidadKg()));
    }

    @Transactional(readOnly = true)
    public List<Vehiculo> listar() {
        return vehiculoRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Vehiculo buscarPorId(Long id) {
        return vehiculoRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Vehículo", id));
    }

    @Transactional
    public Vehiculo cambiarEstado(Long id, VehiculoDto.CambioEstado datos) {
        Vehiculo vehiculo = buscarPorId(id);
        vehiculo.setEstado(datos.estado());
        return vehiculo;
    }
}
