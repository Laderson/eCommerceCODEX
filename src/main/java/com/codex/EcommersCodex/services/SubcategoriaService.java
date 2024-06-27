package com.codex.EcommersCodex.services;

import com.codex.EcommersCodex.models.Subcategoria;
import com.codex.EcommersCodex.repository.SubcategoriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SubcategoriaService {
    @Autowired
    private SubcategoriaRepository subcategoriaRepository;

    public List<Subcategoria> obtenerTodasLasSubcategorias() {
        return subcategoriaRepository.findAll();
    }

    public List<Subcategoria> obtenerSubcategoriasPorCategoria(Long categoriaId) {
        return subcategoriaRepository.findByCategoriaId(categoriaId);
    }

    public Optional<Subcategoria> obtenerSubcategoriaPorId(Long id) {
        return subcategoriaRepository.findById(id);
    }

    public Subcategoria guardarSubcategoria(Subcategoria subcategoria) {
        return subcategoriaRepository.save(subcategoria);
    }

    public void eliminarSubcategoria(Long id) {
        subcategoriaRepository.deleteById(id);
    }
}

