package com.codex.EcommersCodex.controllers;

import com.codex.EcommersCodex.models.Usuario;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpSession;

@RestController
public class UserController {

    @GetMapping("/api/currentUserId")
    public Long getCurrentUserId(HttpSession session) {
        Usuario usuario = (Usuario) session.getAttribute("usuario");
        if (usuario != null) {
            return usuario.getId();
        }
        return null;
    }
}
