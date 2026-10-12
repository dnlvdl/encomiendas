package com.encomiendas.logistica.model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "asignacion")
public class Asignacion {

    public enum Estado {
        ACTIVA, FINALIZADA, CANCELADA
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // pedidoId vive en PedidosDB: solo guardamos el ID, sin relación JPA.
    @Column(nullable = false)
    private Long pedidoId;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "vehiculoId", nullable = false)
    private Vehiculo vehiculo;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "rutaId", nullable = false)
    private Ruta ruta;

    // conductorId vive en TrabajadorDB: solo guardamos el ID.
    @Column(nullable = false)
    private Long conductorId;

    @Column(nullable = false)
    private LocalDateTime fechaAsignacion;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private Estado estado;

    protected Asignacion() {
    }

    public Asignacion(Long pedidoId, Vehiculo vehiculo, Ruta ruta, Long conductorId) {
        this.pedidoId = pedidoId;
        this.vehiculo = vehiculo;
        this.ruta = ruta;
        this.conductorId = conductorId;
        this.fechaAsignacion = LocalDateTime.now();
        this.estado = Estado.ACTIVA;
    }

    public Long getId() {
        return id;
    }

    public Long getPedidoId() {
        return pedidoId;
    }

    public Vehiculo getVehiculo() {
        return vehiculo;
    }

    public Ruta getRuta() {
        return ruta;
    }

    public Long getConductorId() {
        return conductorId;
    }

    public LocalDateTime getFechaAsignacion() {
        return fechaAsignacion;
    }

    public Estado getEstado() {
        return estado;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }
}
