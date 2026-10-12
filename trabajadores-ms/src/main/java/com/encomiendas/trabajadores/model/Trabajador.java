package com.encomiendas.trabajadores.model;

import java.time.LocalDate;

import org.hibernate.annotations.Nationalized;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "trabajador")
public class Trabajador {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 8)
    private String rut;

    @Column(nullable = false, length = 1)
    private String dv;

    @Nationalized
    @Column(nullable = false, length = 100)
    private String primerNombre;

    @Nationalized
    @Column(length = 100)
    private String segundoNombre;

    @Nationalized
    @Column(nullable = false, length = 100)
    private String primerApellido;

    @Nationalized
    @Column(nullable = false, length = 100)
    private String segundoApellido;

    @Column(nullable = false, length = 9)
    private String celular;

    @Nationalized
    @Column(nullable = false, length = 150)
    private String correo;

    // cargoId identifica si es conductor o administrativo. El nombre del cargo no vive en esta tabla.
    @Column(nullable = false)
    private Long cargoId;

    @Column(nullable = false)
    private boolean activo;

    @Column(nullable = false)
    private LocalDate fechaIngreso;

    // Solo los conductores tienen licencia; puede quedar null.
    @Column(length = 20)
    private String licencia;

    protected Trabajador() {
    }

    public Trabajador(String rut, String dv, String primerNombre, String segundoNombre,
                      String primerApellido, String segundoApellido, String celular,
                      String correo, Long cargoId, LocalDate fechaIngreso, String licencia) {
        this.rut = rut;
        this.dv = dv;
        this.primerNombre = primerNombre;
        this.segundoNombre = segundoNombre;
        this.primerApellido = primerApellido;
        this.segundoApellido = segundoApellido;
        this.celular = celular;
        this.correo = correo;
        this.cargoId = cargoId;
        this.fechaIngreso = fechaIngreso;
        this.licencia = licencia;
        this.activo = true;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }

    public Long getId() {
        return id;
    }

    public String getRut() {
        return rut;
    }

    public String getDv() {
        return dv;
    }

    public String getPrimerNombre() {
        return primerNombre;
    }

    public String getSegundoNombre() {
        return segundoNombre;
    }

    public String getPrimerApellido() {
        return primerApellido;
    }

    public String getSegundoApellido() {
        return segundoApellido;
    }

    public String getCelular() {
        return celular;
    }

    public String getCorreo() {
        return correo;
    }

    public Long getCargoId() {
        return cargoId;
    }

    public boolean isActivo() {
        return activo;
    }

    public LocalDate getFechaIngreso() {
        return fechaIngreso;
    }

    public String getLicencia() {
        return licencia;
    }
}
