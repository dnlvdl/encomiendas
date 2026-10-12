package com.encomiendas.logistica.dto;

import com.encomiendas.logistica.model.Ruta;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class RutaDto {

    public record Crear(
            @NotBlank @Size(max = 100) String nombre,
            @NotNull Long comunaId) {
    }

    public record Respuesta(Long id, String nombre, Long comunaId) {

        public static Respuesta desde(Ruta r) {
            return new Respuesta(r.getId(), r.getNombre(), r.getComunaId());
        }
    }
}
