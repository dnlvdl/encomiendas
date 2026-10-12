package com.encomiendas.autenticacion.dto;

import java.util.List;

import com.encomiendas.autenticacion.model.Permiso;
import com.encomiendas.autenticacion.model.Rol;
import com.encomiendas.autenticacion.model.Usuario;

import jakarta.validation.constraints.NotBlank;

public class AuthDto {

    public record Login(
            @NotBlank String username,
            @NotBlank String password) {
    }

    public record Respuesta(
            Long id,
            String username,
            Long trabajadorId,
            List<String> roles,
            List<String> permisos) {

        public static Respuesta desde(Usuario u) {
            List<String> roles = u.getRoles().stream()
                    .map(Rol::getNombre)
                    .sorted()
                    .toList();
            List<String> permisos = u.getRoles().stream()
                    .flatMap(r -> r.getPermisos().stream())
                    .map(Permiso::getCodigo)
                    .distinct()
                    .sorted()
                    .toList();
            return new Respuesta(u.getId(), u.getUsername(), u.getTrabajadorId(), roles, permisos);
        }
    }
}
