package com.encomiendas.pedidos.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

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
@Table(name = "pedido")
public class Pedido {

    public enum Tamano {
        PEQUENO, MEDIANO, GRANDE
    }

    public enum Estado {
        PENDIENTE_PAGO, PAGADO, ASIGNADO, EN_TRANSITO, ENTREGADO
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 20)
    private String codigoSeguimiento;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "remitenteId", nullable = false)
    private Persona remitente;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "destinatarioId", nullable = false)
    private Persona destinatario;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "sucursalId", nullable = false)
    private Sucursal sucursal;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "destinoDireccionId", nullable = false)
    private Direccion destinoDireccion;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 10)
    private Tamano tamano;

    @Column(nullable = false, precision = 5, scale = 2)
    private BigDecimal pesoKg;

    @Column(nullable = false)
    private boolean esFragil;

    @Column(nullable = false)
    private boolean esRigido;

    @Column(nullable = false)
    private boolean esDelicado;

    @Column(nullable = false)
    private boolean declaracionAceptada;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private Estado estado;

    @JdbcTypeCode(SqlTypes.TINYINT)
    @Column
    private Integer calificacion;

    @Column(nullable = false)
    private LocalDateTime creadoEn;

    protected Pedido() {
    }

    public Pedido(String codigoSeguimiento, Persona remitente, Persona destinatario,
                  Sucursal sucursal, Direccion destinoDireccion, Tamano tamano,
                  BigDecimal pesoKg, boolean esFragil, boolean esRigido,
                  boolean esDelicado, boolean declaracionAceptada) {
        this.codigoSeguimiento = codigoSeguimiento;
        this.remitente = remitente;
        this.destinatario = destinatario;
        this.sucursal = sucursal;
        this.destinoDireccion = destinoDireccion;
        this.tamano = tamano;
        this.pesoKg = pesoKg;
        this.esFragil = esFragil;
        this.esRigido = esRigido;
        this.esDelicado = esDelicado;
        this.declaracionAceptada = declaracionAceptada;
        this.estado = Estado.PENDIENTE_PAGO;
        this.creadoEn = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public String getCodigoSeguimiento() {
        return codigoSeguimiento;
    }

    public Persona getRemitente() {
        return remitente;
    }

    public Persona getDestinatario() {
        return destinatario;
    }

    public Sucursal getSucursal() {
        return sucursal;
    }

    public Direccion getDestinoDireccion() {
        return destinoDireccion;
    }

    public Tamano getTamano() {
        return tamano;
    }

    public BigDecimal getPesoKg() {
        return pesoKg;
    }

    public boolean isEsFragil() {
        return esFragil;
    }

    public boolean isEsRigido() {
        return esRigido;
    }

    public boolean isEsDelicado() {
        return esDelicado;
    }

    public boolean isDeclaracionAceptada() {
        return declaracionAceptada;
    }

    public Estado getEstado() {
        return estado;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }

    public Integer getCalificacion() {
        return calificacion;
    }

    public void setCalificacion(Integer calificacion) {
        this.calificacion = calificacion;
    }

    public LocalDateTime getCreadoEn() {
        return creadoEn;
    }
}
