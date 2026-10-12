package com.encomiendas.logistica.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "ruta")
public class Ruta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 100)
    private String nombre;

    // Sin relación JPA: comuna vive en PedidosDB/otra base, así que solo guardamos el ID.
    @Column(nullable = false)
    private Long comunaId;

    protected Ruta() {
    }

    public Ruta(String nombre, Long comunaId) {
        this.nombre = nombre;
        this.comunaId = comunaId;
    }

    public Long getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public Long getComunaId() {
        return comunaId;
    }
}
