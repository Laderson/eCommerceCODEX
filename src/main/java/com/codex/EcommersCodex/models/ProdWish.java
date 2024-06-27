package com.codex.EcommersCodex.models;

import jakarta.persistence.*;

@Entity
@Table(name = "prod_wish")
public class ProdWish {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "id_wish")
    private Long idWish;

    @Column(name = "id_prod")
    private Long idProd;

    @ManyToOne
    @JoinColumn(name = "id_usu", referencedColumnName = "id")
    private Usuario usuario;

    // Getters y setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIdWish() {
        return idWish;
    }

    public void setIdWish(Long idWish) {
        this.idWish = idWish;
    }

    public Long getIdProd() {
        return idProd;
    }

    public void setIdProd(Long idProd) {
        this.idProd = idProd;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
}
