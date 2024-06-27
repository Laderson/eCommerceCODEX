package com.codex.EcommersCodex.models;

import jakarta.persistence.*;
import java.util.Set;

@Entity
@Table(name = "usuario")
public class Usuario {
    private String usuario, contraseña, correo, apellido, nombre, telefono, tipo_auth, estado, salt;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private int rol;

    @OneToMany(mappedBy = "usuario")
    private Set<ProdWish> prodWishes;

    public Usuario() {}

    public Usuario(String usuario, String contraseña, String correo, String apellido, String nombre, String telefono, int rol, String tipo_auth, String estado) {
        this.usuario = usuario;
        this.contraseña = contraseña;
        this.correo = correo;
        this.apellido = apellido;
        this.nombre = nombre;
        this.telefono = telefono;
        this.rol = rol;
        this.tipo_auth = tipo_auth;
        this.estado = estado;
    }

    // Getters y setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getContraseña() {
        return contraseña;
    }

    public void setContraseña(String contraseña) {
        this.contraseña = contraseña;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public int getRol() {
        return rol;
    }

    public void setRol(int rol) {
        this.rol = rol;
    }

    public String getTipo_auth() {
        return tipo_auth;
    }

    public void setTipo_auth(String tipo_auth) {
        this.tipo_auth = tipo_auth;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public Set<ProdWish> getProdWishes() {
        return prodWishes;
    }

    public void setProdWishes(Set<ProdWish> prodWishes) {
        this.prodWishes = prodWishes;
    }
}
