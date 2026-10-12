package com.encomiendas.autenticacion.model;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "usuario")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 50)
    private String username;

    @Column(nullable = false, length = 100)
    private String passwordHash;

    // trabajadorId vive en TrabajadorDB: solo guardamos el ID.
    @Column(nullable = false)
    private Long trabajadorId;

    @Column(nullable = false)
    private boolean activo;

    @Column(nullable = false)
    private LocalDateTime creadoEn;

    @Column
    private LocalDateTime ultimoAcceso;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "usuario_rol",
            joinColumns = @JoinColumn(name = "usuarioId"),
            inverseJoinColumns = @JoinColumn(name = "rolId"))
    private Set<Rol> roles = new HashSet<>();

    protected Usuario() {
    }

    public Usuario(String username, String passwordHash, Long trabajadorId, Set<Rol> roles) {
        this.username = username;
        this.passwordHash = passwordHash;
        this.trabajadorId = trabajadorId;
        this.roles = roles;
        this.activo = true;
        this.creadoEn = LocalDateTime.now();
    }

    public void registrarAcceso() {
        this.ultimoAcceso = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public Long getTrabajadorId() {
        return trabajadorId;
    }

    public boolean isActivo() {
        return activo;
    }

    public LocalDateTime getCreadoEn() {
        return creadoEn;
    }

    public LocalDateTime getUltimoAcceso() {
        return ultimoAcceso;
    }

    public Set<Rol> getRoles() {
        return roles;
    }
}
