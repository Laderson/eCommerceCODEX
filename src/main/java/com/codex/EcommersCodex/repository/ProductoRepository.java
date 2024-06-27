package com.codex.EcommersCodex.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.codex.EcommersCodex.models.Producto;

public interface ProductoRepository extends JpaRepository<Producto, Long> {
    
}
