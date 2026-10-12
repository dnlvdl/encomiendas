package com.encomiendas.pedidos.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.encomiendas.pedidos.model.Pedido;

import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class PedidoDto {

    public record Crear(
            @NotNull Long remitenteId,
            @NotNull Long destinatarioId,
            @NotNull Long sucursalId,
            @NotNull Long destinoDireccionId,
            @NotNull Pedido.Tamano tamano,
            @NotNull @DecimalMin("0.01") @DecimalMax("50.00") BigDecimal pesoKg,
            boolean esFragil,
            boolean esRigido,
            boolean esDelicado,
            @AssertTrue(message = "debe aceptar la declaración") boolean declaracionAceptada) {
    }

    public record CambioEstado(
            @NotNull Pedido.Estado estado,
            @Size(max = 200) String observacion) {
    }

    public record Respuesta(
            Long id,
            String codigoSeguimiento,
            Pedido.Estado estado,
            Pedido.Tamano tamano,
            BigDecimal pesoKg,
            Long destinoDireccionId,
            LocalDateTime creadoEn) {

        public static Respuesta desde(Pedido p) {
            return new Respuesta(
                    p.getId(),
                    p.getCodigoSeguimiento(),
                    p.getEstado(),
                    p.getTamano(),
                    p.getPesoKg(),
                    p.getDestinoDireccion().getId(),
                    p.getCreadoEn());
        }
    }
}
