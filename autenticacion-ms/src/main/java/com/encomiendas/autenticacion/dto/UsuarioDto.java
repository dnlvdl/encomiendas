package com.encomiendas.autenticacion.dto;

import java.util.List;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class UsuarioDto {

    public record Crear(
            @NotBlank @Size(max = 50) String username,
            @NotBlank @Size(min = 6, max = 100) String password,
            @NotNull Long trabajadorId,
            @NotEmpty List<String> rolesNombres) {
    }
}
