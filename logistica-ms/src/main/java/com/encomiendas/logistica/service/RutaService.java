package com.encomiendas.logistica.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.encomiendas.logistica.dto.RutaDto;
import com.encomiendas.logistica.model.Ruta;
import com.encomiendas.logistica.repository.RutaRepository;

@Service
public class RutaService {

    private final RutaRepository rutaRepository;

    public RutaService(RutaRepository rutaRepository) {
        this.rutaRepository = rutaRepository;
    }

    @Transactional
    public RutaDto.Respuesta crear(RutaDto.Crear datos) {
        if (rutaRepository.findByNombre(datos.nombre()).isPresent()) {
            throw new IllegalArgumentException("Ya existe una ruta llamada " + datos.nombre());
        }
        Ruta ruta = rutaRepository.save(new Ruta(datos.nombre(), datos.comunaId()));
        return RutaDto.Respuesta.desde(ruta);
    }

    @Transactional(readOnly = true)
    public List<RutaDto.Respuesta> listar() {
        return rutaRepository.findAll().stream()
                .map(RutaDto.Respuesta::desde)
                .toList();
    }
}
