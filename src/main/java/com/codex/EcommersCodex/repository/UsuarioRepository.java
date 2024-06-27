package com.codex.EcommersCodex.repository;

import com.codex.EcommersCodex.models.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Usuario findByUsuarioAndContraseña(String usuario, String contraseña);
    Usuario findByCorreoAndContraseña(String correo, String contraseña);
    Usuario findByUsuarioOrCorreo(String usuario, String correo);
    Usuario findByCorreo(String correo);
    Usuario findByCorreoOrUsuario(String correo, String usuario);
    List<Usuario> findByRol(int rol);  // Método para encontrar usuarios por rol
}
