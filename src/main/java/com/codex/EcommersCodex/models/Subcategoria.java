package com.codex.EcommersCodex.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "sub_categoria")
public class Subcategoria {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    @Column(name = "id_subc")
    private Long id_subC;

    @ManyToOne
    @JoinColumn(name = "id_cat", nullable = false)
    private Categoria categoria;

    @Column(name = "sub_categoria")
    private String sub_categoria;

    // Getters y setters
    public Long getId() {
        return id_subC;
    }

    public void setId(Long id_subC) {
        this.id_subC = id_subC;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    public String getNombre() {
        return sub_categoria;
    }

    public void setNombre(String sub_categoria) {
        this.sub_categoria = sub_categoria;
    }
}
