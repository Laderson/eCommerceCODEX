package com.codex.EcommersCodex.services;


import java.util.Base64;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.codex.EcommersCodex.controllers.PasswordUtils;
import com.codex.EcommersCodex.models.Usuario;
import com.codex.EcommersCodex.repository.UsuarioRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class UsuarioService {
    @Autowired
    private UsuarioRepository usuarioRepository;

    public Usuario registrarUsuario(Usuario usuario) {
        byte[] salt = PasswordUtils.generateSalt();
        String hashedPassword = PasswordUtils.hashPassword(usuario.getContraseña(), salt);
        usuario.setContraseña(hashedPassword);
        usuario.setSalt(Base64.getEncoder().encodeToString(salt));  // Guarda el salt codificado en Base64
        System.out.println("Registro - Salt: " + usuario.getSalt());
        System.out.println("Registro - Hashed Password: " + usuario.getContraseña());
        return usuarioRepository.save(usuario);
    }

    public Usuario login(String usuarioCorreo, String contraseña) {
        Usuario usuario = usuarioRepository.findByCorreoOrUsuario(usuarioCorreo, usuarioCorreo);
        if (usuario != null) {
            String saltBase64 = usuario.getSalt();
            System.out.println("Login - Stored Salt: " + saltBase64);
            if (saltBase64 == null) {
                throw new RuntimeException("Salt is null for user: " + usuarioCorreo);
            }
            byte[] salt = Base64.getDecoder().decode(saltBase64);
            String hashedPassword = PasswordUtils.hashPassword(contraseña, salt);
            System.out.println("Login - Provided Hashed Password: " + hashedPassword);
            System.out.println("Login - Stored Hashed Password: " + usuario.getContraseña());
            if (hashedPassword.equals(usuario.getContraseña())) {
                if ("inactivo".equals(usuario.getEstado())) {
                    usuario.setEstado("activo");
                    usuarioRepository.save(usuario);
                }
                return usuario;
            }
        }
        return null;
    }

    public void cambiarEstado(Usuario usuario, String nuevoEstado) {
        usuario.setEstado(nuevoEstado);
        usuarioRepository.save(usuario);
    }

    public Usuario registrarUsuarioDesdeGoogle(String token) {
        RestTemplate restTemplate = new RestTemplate();
        String url = "https://www.googleapis.com/oauth2/v1/userinfo?access_token=" + token;
        String result = restTemplate.getForObject(url, String.class);

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode userInfo = objectMapper.readTree(result);

            String correo = userInfo.get("email").asText();
            String nombre = userInfo.get("given_name").asText();
            String apellido = userInfo.get("family_name").asText();
            String telefono = ""; // Aquí puedes manejar cómo obtener el teléfono si está disponible

            // Verificar si el usuario ya existe
            Usuario usuarioExistente = usuarioRepository.findByCorreo(correo);
            if (usuarioExistente != null) {
                return usuarioExistente;
            }

            // Crear un nuevo usuario
            Usuario nuevoUsuario = new Usuario(nombre, "", correo, apellido, nombre, telefono, 3 , "Google", "Inactivo");
            return usuarioRepository.save(nuevoUsuario);
        } catch (JsonProcessingException e) {
            return null;
        }
    }
}