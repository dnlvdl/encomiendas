package com.encomiendas.logistica.model;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "vehiculo")
public class Vehiculo {

    public enum Estado {
        DISPONIBLE, EN_MANTENCION
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 6)
    private String patente;

    @Column(nullable = false, precision = 7, scale = 2)
    private BigDecimal capacidadKg;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private Estado estado;

    protected Vehiculo() {
    }

    public Vehiculo(String patente, BigDecimal capacidadKg) {
        this.patente = patente;
        this.capacidadKg = capacidadKg;
        this.estado = Estado.DISPONIBLE;
    }

    public Long getId() {
        return id;
    }

    public String getPatente() {
        return patente;
    }

    public BigDecimal getCapacidadKg() {
        return capacidadKg;
    }

    public Estado getEstado() {
        return estado;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }
}
