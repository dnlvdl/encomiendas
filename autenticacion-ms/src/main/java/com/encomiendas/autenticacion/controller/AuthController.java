package com.encomiendas.autenticacion.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.encomiendas.autenticacion.dto.AuthDto;
import com.encomiendas.autenticacion.service.AuthService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public AuthDto.Respuesta login(@Valid @RequestBody AuthDto.Login datos) {
        return authService.login(datos);
    }
}
