package com.encomiendas.pedidos.exception;

public class RecursoNoEncontradoException extends RuntimeException {

    public RecursoNoEncontradoException(String recurso, Object identificador) {
        super(recurso + " no encontrado: " + identificador);
    }
}
