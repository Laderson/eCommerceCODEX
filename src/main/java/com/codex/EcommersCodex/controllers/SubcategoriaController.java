package com.codex.EcommersCodex.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.codex.EcommersCodex.models.Categoria;
import com.codex.EcommersCodex.models.Subcategoria;
import com.codex.EcommersCodex.services.CategoriaService;
import com.codex.EcommersCodex.services.SubcategoriaService;

@RestController
@RequestMapping("/api/subcategoria")
public class SubcategoriaController {
    @Autowired
    private SubcategoriaService subcategoriaService;
    
    @Autowired
    private CategoriaService categoriaService;

    @GetMapping
    public ResponseEntity<List<Subcategoria>> obtenerSubcategoriasPorCategoria(@RequestParam(required = false) Long categoriaId) {
        List<Subcategoria> subcategorias;
        if (categoriaId != null) {
            subcategorias = subcategoriaService.obtenerSubcategoriasPorCategoria(categoriaId);
        } else {
            subcategorias = subcategoriaService.obtenerTodasLasSubcategorias();
        }
        return new ResponseEntity<>(subcategorias, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Subcategoria> obtenerSubcategoriaPorId(@PathVariable Long id) {
        Optional<Subcategoria> subcategoria = subcategoriaService.obtenerSubcategoriaPorId(id);
        return subcategoria.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Subcategoria> crearSubcategoria(@RequestBody Subcategoria subcategoria) {
        try {
            Optional<Categoria> categoria = categoriaService.obtenerCategoriaPorId(subcategoria.getCategoria().getId());
            if (!categoria.isPresent()) {
                return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
            }
            subcategoria.setCategoria(categoria.get());
            Subcategoria nuevaSubcategoria = subcategoriaService.guardarSubcategoria(subcategoria);
            return new ResponseEntity<>(nuevaSubcategoria, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Subcategoria> actualizarSubcategoria(@PathVariable Long id, @RequestBody Subcategoria subcategoriaDetalles) {
        Optional<Subcategoria> subcategoria = subcategoriaService.obtenerSubcategoriaPorId(id);
        if (subcategoria.isPresent()) {
            Subcategoria subcategoriaActualizada = subcategoria.get();
            subcategoriaActualizada.setNombre(subcategoriaDetalles.getNombre());
            if (subcategoriaDetalles.getCategoria() != null && subcategoriaDetalles.getCategoria().getId() != null) {
                Optional<Categoria> categoria = categoriaService.obtenerCategoriaPorId(subcategoriaDetalles.getCategoria().getId());
                if (categoria.isPresent()) {
                    subcategoriaActualizada.setCategoria(categoria.get());
                } else {
                    return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
                }
            }
            return ResponseEntity.ok(subcategoriaService.guardarSubcategoria(subcategoriaActualizada));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarSubcategoria(@PathVariable Long id) {
        if (subcategoriaService.obtenerSubcategoriaPorId(id).isPresent()) {
            subcategoriaService.eliminarSubcategoria(id);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
