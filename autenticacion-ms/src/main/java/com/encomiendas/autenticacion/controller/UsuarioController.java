package com.encomiendas.autenticacion.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.encomiendas.autenticacion.dto.AuthDto;
import com.encomiendas.autenticacion.dto.UsuarioDto;
import com.encomiendas.autenticacion.service.UsuarioService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/usuarios")
public class UsuarioController {

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @PostMapping
    public ResponseEntity<AuthDto.Respuesta> crear(@Valid @RequestBody UsuarioDto.Crear datos) {
        return ResponseEntity.status(HttpStatus.CREATED).body(usuarioService.crear(datos));
    }

    @GetMapping
    public List<AuthDto.Respuesta> listar() {
        return usuarioService.listar();
    }
}
