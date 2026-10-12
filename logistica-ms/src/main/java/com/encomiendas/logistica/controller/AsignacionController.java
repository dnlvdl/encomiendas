package com.encomiendas.logistica.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.encomiendas.logistica.dto.AsignacionDto;
import com.encomiendas.logistica.service.AsignacionService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/asignaciones")
public class AsignacionController {

    private final AsignacionService asignacionService;

    public AsignacionController(AsignacionService asignacionService) {
        this.asignacionService = asignacionService;
    }

    @PostMapping
    public ResponseEntity<AsignacionDto.Respuesta> crear(@Valid @RequestBody AsignacionDto.Crear datos) {
        return ResponseEntity.status(HttpStatus.CREATED).body(asignacionService.crear(datos));
    }

    @GetMapping
    public List<AsignacionDto.Respuesta> listar() {
        return asignacionService.listar();
    }
}
