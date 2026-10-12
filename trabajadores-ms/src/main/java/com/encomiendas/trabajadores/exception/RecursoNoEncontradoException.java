package com.encomiendas.trabajadores.exception;

public class RecursoNoEncontradoException extends RuntimeException {

    public RecursoNoEncontradoException(String recurso, Object identificador) {
        super(recurso + " no encontrado: " + identificador);
    }
}
