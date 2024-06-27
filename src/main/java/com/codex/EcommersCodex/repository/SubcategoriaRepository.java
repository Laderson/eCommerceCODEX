package com.codex.EcommersCodex.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.codex.EcommersCodex.models.Subcategoria;

public interface SubcategoriaRepository extends JpaRepository<Subcategoria, Long> {
    List<Subcategoria> findByCategoriaId(Long categoriaId);
}
