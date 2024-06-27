package com.codex.EcommersCodex.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "producto")
public class Producto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_prod")
    private Long id_prod;

    @Column(name = "id_cat")
    private Long id_cat;

    @Column (name = "id_subc")
    private Long id_subc;

    @Column(name = "nombre_prod")
    private String nombreProd;

    @Column(name = "precio")
    private Double precio;

    @Column(name = "cantidad")
    private Integer cantidad;

    @Column(name = "descripcion")
    private String descripcion;

    @Column(name = "imagen_url")
    private String imagenUrl;

    // Getters y setters

    public Long getIdPro() {
        return id_prod;
    }

    public void setIdPro(Long id_prod) {
        this.id_prod = id_prod;
    }

    public Long getCategoria() {
        return id_cat;
    }

    public void setCategoria(Long id_cat) {
        this.id_cat = id_cat;
    }

    public String getNombreProd() {
        return nombreProd;
    }

    public void setNombreProd(String nombreProd) {
        this.nombreProd = nombreProd;
    }

    public Double getPrecio() {
        return precio;
    }

    public void setPrecio(Double precio) {
        this.precio = precio;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getImagenUrl() {
        return imagenUrl;
    }

    public void setImagenUrl(String imagenUrl) {
        this.imagenUrl = imagenUrl;
    }

    public Long getId_subc() {
        return id_subc;
    }

    public void setId_subc(Long id_subc) {
        this.id_subc = id_subc;
    }
}
