package com.encomiendas.trabajadores.dto;

import java.time.LocalDate;

import com.encomiendas.trabajadores.model.Trabajador;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class TrabajadorDto {

    public record Crear(
            @NotNull @Pattern(regexp = "[0-9]{7,8}", message = "debe tener 7 u 8 dígitos numéricos") String rut,
            @NotNull @Pattern(regexp = "[0-9Kk]", message = "debe ser un dígito o K") String dv,
            @NotBlank @Size(max = 100) String primerNombre,
            @Size(max = 100) String segundoNombre,
            @NotBlank @Size(max = 100) String primerApellido,
            @NotBlank @Size(max = 100) String segundoApellido,
            @NotNull @Pattern(regexp = "9[0-9]{8}", message = "debe tener 9 dígitos y comenzar con 9") String celular,
            @NotNull @Email @Size(max = 150) String correo,
            @NotNull Long cargoId,
            @NotNull LocalDate fechaIngreso,
            @Size(max = 20) String licencia) {
    }

    public record CambioActivo(
            @NotNull Boolean activo) {
    }

    public record Respuesta(
            Long id,
            String rut,
            String dv,
            String primerNombre,
            String segundoNombre,
            String primerApellido,
            String segundoApellido,
            String celular,
            String correo,
            Long cargoId,
            boolean activo,
            LocalDate fechaIngreso,
            String licencia) {

        public static Respuesta desde(Trabajador t) {
            return new Respuesta(t.getId(), t.getRut(), t.getDv(), t.getPrimerNombre(),
                    t.getSegundoNombre(), t.getPrimerApellido(), t.getSegundoApellido(),
                    t.getCelular(), t.getCorreo(), t.getCargoId(), t.isActivo(),
                    t.getFechaIngreso(), t.getLicencia());
        }
    }
}
