package com.encomiendas.logistica.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.encomiendas.logistica.model.Asignacion;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;

public class AsignacionDto {

    public record Crear(
            @NotNull Long pedidoId,
            @NotNull Long vehiculoId,
            @NotNull Long rutaId,
            @NotNull Long conductorId,
            @NotNull @DecimalMin("0.01") BigDecimal pesoKg) {
    }

    public record Respuesta(
            Long id,
            Long pedidoId,
            Long vehiculoId,
            Long rutaId,
            Long conductorId,
            LocalDateTime fechaAsignacion,
            Asignacion.Estado estado) {

        public static Respuesta desde(Asignacion a) {
            return new Respuesta(
                    a.getId(),
                    a.getPedidoId(),
                    a.getVehiculo().getId(),
                    a.getRuta().getId(),
                    a.getConductorId(),
                    a.getFechaAsignacion(),
                    a.getEstado());
        }
    }
}
