package com.encomiendas.logistica.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.encomiendas.logistica.dto.VehiculoDto;
import com.encomiendas.logistica.model.Vehiculo;
import com.encomiendas.logistica.service.VehiculoService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/vehiculos")
public class VehiculoController {

    private final VehiculoService vehiculoService;

    public VehiculoController(VehiculoService vehiculoService) {
        this.vehiculoService = vehiculoService;
    }

    @PostMapping
    public ResponseEntity<VehiculoDto.Respuesta> crear(@Valid @RequestBody VehiculoDto.Crear datos) {
        Vehiculo vehiculo = vehiculoService.crear(datos);
        return ResponseEntity.status(HttpStatus.CREATED).body(VehiculoDto.Respuesta.desde(vehiculo));
    }

    @GetMapping
    public List<VehiculoDto.Respuesta> listar() {
        return vehiculoService.listar().stream()
                .map(VehiculoDto.Respuesta::desde)
                .toList();
    }

    @GetMapping("/{id}")
    public VehiculoDto.Respuesta buscarPorId(@PathVariable Long id) {
        return VehiculoDto.Respuesta.desde(vehiculoService.buscarPorId(id));
    }

    @PatchMapping("/{id}/estado")
    public VehiculoDto.Respuesta cambiarEstado(@PathVariable Long id,
                                               @Valid @RequestBody VehiculoDto.CambioEstado datos) {
        return VehiculoDto.Respuesta.desde(vehiculoService.cambiarEstado(id, datos));
    }
}
