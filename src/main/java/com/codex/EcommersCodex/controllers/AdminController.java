package com.codex.EcommersCodex.controllers;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.codex.EcommersCodex.models.Admin;
import com.codex.EcommersCodex.services.AdminService;

import jakarta.servlet.http.HttpSession;

@Controller
public class AdminController {

    @Autowired
    private AdminService adminService;

    private static final Logger logger = LoggerFactory.getLogger(AdminController.class);

    @GetMapping("/agregar_administrador")
    public String agregar_administrador() {
        return "agregar_administrador";
    }

    @GetMapping("/ver_usuarios")
    public String verUsuarios(Model model) {
        List<Admin> usuarios = adminService.getAllUsers();
        model.addAttribute("usuarios", usuarios);
        return "ver_usuarios";
    }

    @GetMapping("/ver_administradores")
    public String verAdministradores(Model model) {
        List<Admin> administradores = adminService.getAllAdmins();
        model.addAttribute("administradores", administradores);
        return "ver_administradores";
    }

    @PostMapping("/agregar_administrador")
    public String start(@RequestParam String nombre, @RequestParam String apellido, @RequestParam String correo,
    @RequestParam String telefono, @RequestParam String usuario, @RequestParam String contraseña, Model model, HttpSession session){
        return registerAdmin(nombre, apellido, correo, telefono, usuario, contraseña, model);
    }

    private String registerAdmin(String nombre, String apellido, String correo, String telefono, String usuario, String contraseñaR, Model model) {
        Admin adminExistente = adminService.login(usuario, contraseñaR);
        if (adminExistente != null) {
            logger.warn("El usuario o correo electrónico ya están registrados: " + usuario);
            model.addAttribute("error", "El usuario o correo electrónico ya están registrados");
            return "Login";
        }

        int rol = 2;
        String tipo_auth = "Codex";
        String estado = "inactivo";
        Admin newAdmin = new Admin();
        newAdmin.setUsuario(usuario);
        newAdmin.setContraseña(contraseñaR);
        newAdmin.setCorreo(correo);
        newAdmin.setApellido(apellido);
        newAdmin.setNombre(nombre);
        newAdmin.setTelefono(telefono);
        newAdmin.setRol(rol);
        newAdmin.setTipo_auth(tipo_auth);
        newAdmin.setEstado(estado);
        adminService.registrarUsuarioAdmin(newAdmin);

        logger.info("Registro exitoso para el usuario: " + usuario);
        return "redirect:/Login";
    }
}
