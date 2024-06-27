package com.codex.EcommersCodex.services;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.codex.EcommersCodex.models.Producto;
import com.codex.EcommersCodex.repository.ProductoRepository;

@Service
public class ProductoService {

    @Autowired
    private ProductoRepository productoRepository;

    public Producto guardarProducto(Producto producto) {
        return productoRepository.save(producto);
    }

    public String guardarImagen(MultipartFile imagen) {
        String nombreArchivo = imagen.getOriginalFilename();
        String directorioImagenes = "ProyectoEcommerceCodex/EcommersCodex/src/main/resources/static/imagenes/fotos";
        try {
            Path ruta = Paths.get(directorioImagenes + "/" + nombreArchivo);
            Files.copy(imagen.getInputStream(), ruta, StandardCopyOption.REPLACE_EXISTING);
            return ruta.toString();
        } catch (IOException e) {
            throw new RuntimeException("Error al guardar la imagen", e);
        }
    }

    public List<Producto> obtenerTodosLosProductos() {
        return productoRepository.findAll();
    }
    
}
