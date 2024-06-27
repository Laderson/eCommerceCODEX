package com.codex.EcommersCodex.repository;

import com.codex.EcommersCodex.models.Admin;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AdminRepository extends JpaRepository<Admin, Long> {
    Admin findByCorreoOrUsuario(String correo, String usuario);
    List<Admin> findByRol(int rol);
}
