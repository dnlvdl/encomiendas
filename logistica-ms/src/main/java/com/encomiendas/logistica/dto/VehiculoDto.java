package com.encomiendas.logistica.dto;

import java.math.BigDecimal;

import com.encomiendas.logistica.model.Vehiculo;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public class VehiculoDto {

    public record Crear(
            @NotNull @Pattern(regexp = "[A-Z0-9]{6}", message = "debe tener 6 caracteres en mayúsculas o números") String patente,
            @NotNull @DecimalMin("0.01") BigDecimal capacidadKg) {
    }

    public record CambioEstado(
            @NotNull Vehiculo.Estado estado) {
    }

    public record Respuesta(
            Long id,
            String patente,
            BigDecimal capacidadKg,
            Vehiculo.Estado estado) {

        public static Respuesta desde(Vehiculo v) {
            return new Respuesta(v.getId(), v.getPatente(), v.getCapacidadKg(), v.getEstado());
        }
    }
}
