package com.encomiendas.logistica.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.encomiendas.logistica.dto.RutaDto;
import com.encomiendas.logistica.service.RutaService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/rutas")
public class RutaController {

    private final RutaService rutaService;

    public RutaController(RutaService rutaService) {
        this.rutaService = rutaService;
    }

    @PostMapping
    public ResponseEntity<RutaDto.Respuesta> crear(@Valid @RequestBody RutaDto.Crear datos) {
        return ResponseEntity.status(HttpStatus.CREATED).body(rutaService.crear(datos));
    }

    @GetMapping
    public List<RutaDto.Respuesta> listar() {
        return rutaService.listar();
    }
}
