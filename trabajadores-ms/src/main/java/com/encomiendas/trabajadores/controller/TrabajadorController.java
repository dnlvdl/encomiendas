package com.encomiendas.trabajadores.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.encomiendas.trabajadores.dto.TrabajadorDto;
import com.encomiendas.trabajadores.service.TrabajadorService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/trabajadores")
public class TrabajadorController {

    private final TrabajadorService trabajadorService;

    public TrabajadorController(TrabajadorService trabajadorService) {
        this.trabajadorService = trabajadorService;
    }

    @PostMapping
    public ResponseEntity<TrabajadorDto.Respuesta> crear(@Valid @RequestBody TrabajadorDto.Crear datos) {
        return ResponseEntity.status(HttpStatus.CREATED).body(trabajadorService.crear(datos));
    }

    @GetMapping
    public List<TrabajadorDto.Respuesta> listar() {
        return trabajadorService.listar();
    }

    @GetMapping("/{id}")
    public TrabajadorDto.Respuesta buscarPorId(@PathVariable Long id) {
        return trabajadorService.buscarPorId(id);
    }

    // Sin body: cada llamada invierte el estado activo del trabajador.
    @PatchMapping("/{id}/activo")
    public TrabajadorDto.Respuesta alternarActivo(@PathVariable Long id) {
        return trabajadorService.alternarActivo(id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> borrar(@PathVariable Long id) {
        trabajadorService.borrar(id);
        return ResponseEntity.noContent().build();
    }
}
