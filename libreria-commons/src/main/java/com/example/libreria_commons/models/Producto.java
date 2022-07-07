package com.example.libreria_commons.models;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
public class Producto implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre;
    private Double precio;
    @Temporal(TemporalType.DATE)
    private Date createdAt;
    @Transient
    private String puerto;

    public Producto() {
    }

    public Producto(Long id, String nombre, Double precio, Date createdAt) {
        this.id = id;
        this.nombre = nombre;
        this.precio = precio;
        this.createdAt = createdAt;
    }

    public void setPuerto(String puerto) {
        this.puerto = puerto;
    }

    public String getPuerto() {
        return puerto;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setPrecio(Double precio) {
        this.precio = precio;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Long getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public Double getPrecio() {
        return precio;
    }

    public Date getCreatedAt() {
        return createdAt;
    }
}