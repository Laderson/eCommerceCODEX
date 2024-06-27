package com.codex.EcommersCodex.controllers;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class CustomErrorController implements ErrorController {

    @RequestMapping("/error404")
    public String handleError() {
        // Aquí puedes realizar alguna lógica si es necesario
        return "error404"; // Nombre de la vista de la página de error 404
    }
}
