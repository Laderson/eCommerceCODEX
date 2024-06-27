package com.codex.EcommersCodex.controllers;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.codex.EcommersCodex.models.Usuario;
import com.codex.EcommersCodex.repository.UsuarioRepository;
import com.codex.EcommersCodex.services.UsuarioService;

import jakarta.servlet.http.HttpSession;

@Controller
public class Controlador {

    private static final Logger logger = LoggerFactory.getLogger(Controlador.class);

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping("/Login")
    public String login() {
        return "Login";
    }

    @GetMapping("/Admin_categoria")
    public String Admin_cat() {
        return "Admin_categoria";
    }

    @GetMapping("/SuperAdmin_panel")
    public String SuperAdmin_panel() {
        return "SuperAdmin_panel";
    }

    @GetMapping("/Admin_sub")
    public String Admin_sub() {
        return "Admin_sub";
    }

    @GetMapping("/Admin_producto")
    public String Admin_producto() {
        return "Admin_producto";
    }

    @GetMapping("/Admin_panel")
    public String Admin_panel(HttpSession session, Model model) {
        Usuario usuario = (Usuario) session.getAttribute("usuario");
        if (usuario != null) {
            String nombreCompleto = usuario.getNombre() + " " + usuario.getApellido();
            model.addAttribute("adminName", nombreCompleto);
        } else {
            model.addAttribute("adminName", "Administrador");
        }
        return "Admin_panel";
    }

    @GetMapping("/Home")
    public String home(Model model, HttpSession session) {
        model.addAttribute("usuario", session.getAttribute("usuario"));
        return "Home";
    }

    @GetMapping("/Wishlist")
    public String wishlist() {
        return "Wishlist";
    }

    @GetMapping("/Carrito")
    public String carrito() {
        return "carrito";
    }

    @GetMapping("/Catalogo")
    public String catalogo() {
        return "Catalogo";
    }

    public boolean isValidEmail(String email) {
        String regex = "^(.+)@(.+)$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    @PostMapping("/google-login")
    public Usuario googleLogin(@RequestBody String token) {
        return usuarioService.registrarUsuarioDesdeGoogle(token);
    }
    
    @PostMapping("/Login")
    public String loginOrRegister(@RequestParam("action") String action, 
                                  @RequestParam(value = "usuario_correo", required = false) String usuarioCorreo,
                                  @RequestParam(value = "contraseña", required = false) String contraseña,
                                  @RequestParam(value = "nombre", required = false) String nombre,
                                  @RequestParam(value = "apellido", required = false) String apellido,
                                  @RequestParam(value = "correo", required = false) String correo,
                                  @RequestParam(value = "telefono", required = false) String telefono,
                                  @RequestParam(value = "usuario", required = false) String usuario,
                                  @RequestParam(value = "contraseñaR", required = false) String contraseñaR,
                                  Model model, HttpSession session) {

        if ("register".equals(action)) {
            return register(nombre, apellido, correo, telefono, usuario, contraseñaR, model);
        } else if ("login".equals(action)) {
            return login(usuarioCorreo, contraseña, model, session);
        } else {
            model.addAttribute("error", "Acción no válida");
            return "Login";
        }
    }

    private String login(String usuarioCorreo, String contraseña, Model model, HttpSession session) {
        Usuario usuario = usuarioService.login(usuarioCorreo, contraseña);

        if (usuario != null) {
            session.setAttribute("usuario", usuario);
            logger.info("Inicio de sesión exitoso para el usuario o correo: " + usuarioCorreo);
            switch (usuario.getRol()) {
                case 1:
                    return "redirect:/agregar_administrador";
                case 2:
                    return "redirect:/Admin_panel";
                case 3:
                    return "redirect:/Home";
                default:
                    logger.warn("Rol desconocido para el usuario: " + usuarioCorreo);
                    model.addAttribute("error", "Rol desconocido");
                    return "Login";
            }
        } else {
            logger.warn("Inicio de sesión fallido para el usuario o correo: " + usuarioCorreo);
            model.addAttribute("error", "Credenciales incorrectas");
            return "Login";
        }
    }

    private String register(String nombre, String apellido, String correo, String telefono, String usuario, String contraseñaR, Model model) {
        Usuario usuarioExistente = usuarioService.login(usuario, correo);
        if (usuarioExistente != null) {
            logger.warn("El usuario o correo electrónico ya están registrados: " + usuario);
            model.addAttribute("error", "El usuario o correo electrónico ya están registrados");
            return "Login";
        }
        int rol = 3;
        String tipo_auth = "Codex";
        String estado = "Inactivo";
        Usuario nuevoUsuario = new Usuario();
        nuevoUsuario.setUsuario(usuario);
        nuevoUsuario.setContraseña(contraseñaR);
        nuevoUsuario.setCorreo(correo);
        nuevoUsuario.setApellido(apellido);
        nuevoUsuario.setNombre(nombre);
        nuevoUsuario.setTelefono(telefono);
        nuevoUsuario.setRol(rol);
        nuevoUsuario.setTipo_auth(tipo_auth);
        nuevoUsuario.setEstado(estado);
        usuarioService.registrarUsuario(nuevoUsuario);

        logger.info("Registro exitoso para el usuario: " + usuario);
        return "redirect:/Login";
    }

    @RequestMapping(value = "/Logout", method = { RequestMethod.GET, RequestMethod.POST })
    public String logout(HttpSession session) {
        Usuario usuario = (Usuario) session.getAttribute("usuario");
        
        if (usuario != null) {
            logger.info("Cerrando sesión para el usuario: " + usuario.getUsuario());
            usuario.setEstado("inactivo");
            usuarioRepository.save(usuario);
            session.removeAttribute("usuario");

            Usuario usuarioVerificado = usuarioRepository.findById(usuario.getId()).orElse(null);
            if (usuarioVerificado != null && "inactivo".equals(usuarioVerificado.getEstado())) {
                logger.info("El estado del usuario se ha actualizado correctamente a inactivo.");
            } else {
                logger.error("Error al actualizar el estado del usuario a inactivo.");
            }
        }
        return "redirect:/Login";
    }
}
