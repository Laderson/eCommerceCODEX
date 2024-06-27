package com.codex.EcommersCodex.services;

import java.util.Base64;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.codex.EcommersCodex.controllers.PasswordUtils;
import com.codex.EcommersCodex.models.Admin;
import com.codex.EcommersCodex.repository.AdminRepository;

@Service
public class AdminService {

    @Autowired
    private AdminRepository adminRepository;

    public List<Admin> getAllUsers() {
        return adminRepository.findAll();
    }

    public List<Admin> getAllAdmins() {
        return adminRepository.findAll();
    }

    public Admin registrarUsuarioAdmin(Admin admin) {
        byte[] salt = PasswordUtils.generateSalt();
        String hashedPassword = PasswordUtils.hashPassword(admin.getContraseña(), salt);
        admin.setContraseña(hashedPassword);
        admin.setSalt(Base64.getEncoder().encodeToString(salt));  // Guarda el salt codificado en Base64
        System.out.println("Registro Admin - Salt: " + admin.getSalt());
        System.out.println("Registro Admin - Hashed Password: " + admin.getContraseña());
        return adminRepository.save(admin);
    }

    public Admin login(String usuarioCorreo, String contraseña) {
        Admin admin = adminRepository.findByCorreoOrUsuario(contraseña, usuarioCorreo);
        if (admin != null) {
            String saltBase64 = admin.getSalt();
            System.out.println("Login - Stored Salt: " + saltBase64);
            if (saltBase64 == null) {
                throw new RuntimeException("Salt is null for user: " + usuarioCorreo);
            }
            byte[] salt = Base64.getDecoder().decode(saltBase64);
            String hashedPassword = PasswordUtils.hashPassword(contraseña, salt);
            System.out.println("Login - Provided Hashed Password: " + hashedPassword);
            System.out.println("Login - Stored Hashed Password: " + admin.getContraseña());
            if (hashedPassword.equals(admin.getContraseña())) {
                if ("inactivo".equals(admin.getEstado())) {
                    admin.setEstado("activo");
                    adminRepository.save(admin);
                }
                return admin;
            }
        }
        return null;
    }
}
