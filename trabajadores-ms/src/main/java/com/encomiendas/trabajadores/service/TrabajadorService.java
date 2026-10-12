package com.encomiendas.trabajadores.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.encomiendas.trabajadores.dto.TrabajadorDto;
import com.encomiendas.trabajadores.exception.RecursoNoEncontradoException;
import com.encomiendas.trabajadores.model.Trabajador;
import com.encomiendas.trabajadores.repository.TrabajadorRepository;

@Service
public class TrabajadorService {

    private final TrabajadorRepository trabajadorRepository;
    private final Long cargoConductorId;

    public TrabajadorService(TrabajadorRepository trabajadorRepository,
                             @Value("${trabajadores.cargo.conductor-id}") Long cargoConductorId) {
        this.trabajadorRepository = trabajadorRepository;
        this.cargoConductorId = cargoConductorId;
    }

    @Transactional
    public TrabajadorDto.Respuesta crear(TrabajadorDto.Crear datos) {
        if (trabajadorRepository.findByRut(datos.rut()).isPresent()) {
            throw new IllegalStateException("Ya existe un trabajador con rut " + datos.rut());
        }

        // Conductor: la licencia es obligatoria. Administrativo: no se pide y queda vacía.
        boolean esConductor = cargoConductorId.equals(datos.cargoId());
        if (esConductor && (datos.licencia() == null || datos.licencia().isBlank())) {
            throw new IllegalArgumentException("Los conductores deben tener número de licencia");
        }
        String licencia = esConductor ? datos.licencia() : null;

        Trabajador trabajador = trabajadorRepository.save(new Trabajador(
                datos.rut(), datos.dv(), datos.primerNombre(), datos.segundoNombre(),
                datos.primerApellido(), datos.segundoApellido(), datos.celular(),
                datos.correo(), datos.cargoId(), datos.fechaIngreso(), licencia));
        return TrabajadorDto.Respuesta.desde(trabajador);
    }

    @Transactional(readOnly = true)
    public List<TrabajadorDto.Respuesta> listar() {
        return trabajadorRepository.findAll().stream()
                .map(TrabajadorDto.Respuesta::desde)
                .toList();
    }

    @Transactional(readOnly = true)
    public TrabajadorDto.Respuesta buscarPorId(Long id) {
        return TrabajadorDto.Respuesta.desde(buscar(id));
    }

    // Invierte el estado: activo pasa a inactivo y viceversa.
    @Transactional
    public TrabajadorDto.Respuesta alternarActivo(Long id) {
        Trabajador trabajador = buscar(id);
        trabajador.setActivo(!trabajador.isActivo());
        return TrabajadorDto.Respuesta.desde(trabajador);
    }

    // Borrar: la fila se elimina de la tabla.
    @Transactional
    public void borrar(Long id) {
        trabajadorRepository.delete(buscar(id));
    }

    private Trabajador buscar(Long id) {
        return trabajadorRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Trabajador", id));
    }
}
