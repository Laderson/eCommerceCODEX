package com.codex.EcommersCodex.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "categoria")
public class Categoria {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_cat;

    private String categoria;

    // Getters y setters
    public Long getId() {
        return id_cat;
    }

    public void setId(Long id_cat) {
        this.id_cat = id_cat;
    }

    public String getNombre() {
        return categoria;
    }

    public void setNombre(String categoria) {
        this.categoria = categoria;
    }
}
